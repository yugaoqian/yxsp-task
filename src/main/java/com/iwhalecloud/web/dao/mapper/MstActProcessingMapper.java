package com.iwhalecloud.web.dao.mapper;

import com.iwhalecloud.web.dao.entity.MstActProcessing;

import java.util.List;
import java.util.Map;

public interface MstActProcessingMapper extends BaseMapper<MstActProcessing> {

    List<MstActProcessing> getPendingProcessingInfo();

    void updateState(Map<String, Object> paramMap);

    List<Map<String, Object>> selectActInfo(Map<String, Object> paramMap);

    List<Map<String, Object>> selectTaskUserInfo(Map<String, Object> paramMap);

    List<Map<String, Object>> selectSubdisInfo(Map<String, Object> paramMap);

    List<Map<String, Object>> selectGroupInfo(Map<String, Object> paramMap);

    List<Map<String, Object>> selectTacticsInfo(Map<String, Object> paramMap);

    List<Map<String, Object>> selectTacticsCondition(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserList(Map<String, Object> paramMap);

    List<Map<String, Object>> selectSceneLabel(Map<String, Object> paramMap);

    List<Map<String, Object>> getImportUser(Map<String, Object> paramMap);

    void insertGroupUserDeal(Map<String, Object> paramMap);

    void insertFlowPool(Map<String, Object> paramMap);

    void insertFlowInst(Map<String, Object> paramMap);

    void insertTaskStrategy(Map<String, Object> paramMap);

    void insertTaskTrace(Map<String, Object> paramMap);
}