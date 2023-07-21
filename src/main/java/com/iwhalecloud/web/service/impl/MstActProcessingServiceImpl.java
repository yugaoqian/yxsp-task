package com.iwhalecloud.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.iwhalecloud.web.dao.entity.MstActProcessing;
import com.iwhalecloud.web.dao.mapper.MstActProcessingMapper;
import com.iwhalecloud.web.service.MstActProcessingService;
import com.iwhalecloud.web.utils.ListUtil;
import com.iwhalecloud.web.utils.MapUtils;
import com.iwhalecloud.web.utils.StackTraceUtil;
import com.iwhalecloud.web.utils.TaskProcessUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MstActProcessingServiceImpl implements MstActProcessingService {

    @Resource
    private MstActProcessingMapper mstActProcessingMapper;

    @Override
    public void taskExecute() {
        List<MstActProcessing> lists = mstActProcessingMapper.getPendingProcessingInfo();
        taskExecute(lists);
    }


    // 处理单个任务数据
    private void taskExecute(List<MstActProcessing> lists) {
        if (CollUtil.isEmpty(lists)) {
            // 如果数据处理完成则跳出循环
            return;
        }
        try {
            for (MstActProcessing actProcessing : lists) {
                ExecutorService executorService = TaskProcessUtil.getOrInitExecutors("taskName", lists.size());
                executorService.submit(() -> doProcessData(actProcessing));
            }

        } catch (Exception e) {
            log.error("线程池处理异常！", e);
        }
    }

    // 处理数据
    @Transactional(rollbackFor = Exception.class)
    public void doProcessData(MstActProcessing actProcessing) {
        log.info("--每批次详细处理--");
        Map param = new HashMap();
        try {
            param.put("actId", actProcessing.getActId());
            List<Map<String, Object>> subdisList = mstActProcessingMapper.selectSubdisInfo(param);

            List<Map<String, Object>> actList = mstActProcessingMapper.selectActInfo(param);
            Map<String, Object> actInfo = actList.get(0);

            List<Map<String, Object>> userInfoList = mstActProcessingMapper.selectTaskUserInfo(actInfo);
            Map<String, Object> userInfo = userInfoList.get(0);

            //处理场景用户
            List<Map<String, Object>> groupList = doProcessGroup(param, subdisList);

            Map map = new HashMap();
            map.put("task_worker_id", MapUtils.getStringValue(userInfo,"user_id"));
            map.put("task_worker_name", MapUtils.getStringValue(userInfo,"user_name"));
            map.put("task_worker_pos_id", MapUtils.getStringValue(userInfo,"pos_id"));
            map.put("task_worker_pos_name", MapUtils.getStringValue(userInfo,"pos_name"));
            map.put("actId", actInfo.get("exe_scene_id"));
            map.put("exp_date", actInfo.get("exp_date"));
            map.put("list", groupList);
            //插入活动明细表
            mstActProcessingMapper.insertGroupUserDeal(map);

            //插入工单池表
            mstActProcessingMapper.insertFlowPool(map);

            //插入工单实例表
            mstActProcessingMapper.insertFlowInst(map);

            //插入工单策略话术表
            mstActProcessingMapper.insertTaskStrategy(map);

            //插入工单轨迹表
            mstActProcessingMapper.insertTaskTrace(map);

        } catch (Exception e) {
            log.error("子任务线程执行异常！", e);
            String stackTraceStr = StackTraceUtil.getStackTrace(e);
            param.put("errorMsg", stackTraceStr);
        } finally {
            param.put("id", actProcessing.getId());
            if ((param.containsKey("errorMsg"))) {
                param.put("state", "30");
            } else {
                param.put("state", "20");
            }
            mstActProcessingMapper.updateState(param);
        }
    }

    //处理所有场景用户数据
    public List<Map<String, Object>> doProcessGroup(Map<String, Object> param, List subdisList) {
        log.info("--处理场景--");
        List resultList = new ArrayList();
        List<Map<String, Object>> groupList = mstActProcessingMapper.selectGroupInfo(param);

        for (Map<String, Object> info : groupList) {
            List mergeList = new ArrayList();

            //获取场景下导入数据
            Map map = new HashMap();
            map.put("groupId", MapUtils.getStringValue(info,"id"));
            map.put("groupName", MapUtils.getStringValue(info,"group_name"));
            map.put("userType", MapUtils.getStringValue(info,"user_type"));
            map.put("list", subdisList);
            List importList = getImportUser(map);

            mergeList.addAll(importList);

            //获取场景下所有的用户明细
            List userList = doProcessTactics(map, subdisList);

            mergeList.addAll(userList);

            //把所有的场景下用户添加到一个list
            if (CollectionUtils.isEmpty(importList)) {
                importList = userList;
            } else {
                importList.addAll(userList);
            }

            //剔重导入和筛选的用户明细
            Collection<Map> tmpMap = ListUtil.removeRepeat(importList, "user_no");
            List infoList = new ArrayList(tmpMap);

            resultList.addAll(infoList);
        }
        return resultList;
    }

    //处理场景下的所有策略用户数据
    public List<Map<String, Object>> doProcessTactics(Map<String, Object> param, List subdisList) {
        log.info("--处理策略--");
        List dateList = new ArrayList();

        List<Map<String, Object>> tacticsList = mstActProcessingMapper.selectTacticsInfo(param);

        for (Map<String, Object> info : tacticsList) {

           // List<Map<String, Object>> tacticsList = mstActProcessingMapper.selectTacticsCondition(param);
            Map map = new HashMap();
            map.put("group_id", MapUtils.getStringValue(param,"groupId"));
            map.put("group_name", MapUtils.getStringValue(param,"groupName"));
            map.put("tactics_name", MapUtils.getStringValue(info,"tactics_name"));
            map.put("tactics_desc", MapUtils.getStringValue(info,"tactics_desc"));
            map.put("tactics_tag_sql", MapUtils.getStringValue(info,"tactics_tag_sql"));
            map.put("list", subdisList);
            List tacList = getUserList(map);
            dateList.addAll(tacList);
        }

        //剔重同一场景不同策略下user_no相同的用户明细，并按策略序号排好顺序
        Collection<Map> tmpMap = ListUtil.removeRepeatTactics(dateList, "user_no");

        List resultList = new ArrayList(tmpMap);

        return resultList;
    }

    //根据策略获取用户明细
    public List<Map<String, Object>> getUserList(Map<String, Object> param) {
        return mstActProcessingMapper.selectUserList(param);
    }

    //根据策略获取用户明细
    public List<Map<String, Object>> getImportUser(Map<String, Object> param) {
        return mstActProcessingMapper.getImportUser(param);
    }

}

