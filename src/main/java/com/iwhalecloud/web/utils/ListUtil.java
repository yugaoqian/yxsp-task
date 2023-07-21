package com.iwhalecloud.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ListUtil {


    private static List<String> mergeLists(List<String> list1, List<String> list2) {
        List<String> mergedList = new ArrayList<>(list1);
        mergedList.addAll(list2);
        Map<String, String> map = mergedList.stream()
                .collect(Collectors.toMap(s -> s, s -> s, (s1, s2) -> s1));
        return new ArrayList<>(map.values());
    }

    private static List<Map<String, Object>> mergedList(List<Map<String, Object>> list1, List<Map<String, Object>> list2) {
        List<Map<String, Object>> mergedList = new ArrayList<>(list1);
        mergedList.addAll(list2);
        mergedList = mergedList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> (String) p.get("code")))), ArrayList::new));

        return mergedList;
    }

    public static List<Map<String, Object>> removeRepeatMapByKey(List<Map<String, Object>> list, String mapKey) {
        if (list == null || list.size() == 0) {
            return null;
        }

        //把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        List<Map<String, Object>> listMap = new ArrayList<>();

        Map<String, Map> msp = new HashMap<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            Map map = list.get(i);
            String id = (String) map.get(mapKey);
            map.remove(mapKey);
            msp.put(id, map);
        }

        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for (String key : mspKey) {
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }

        return listMap;
    }

    public static List<Map<String, Object>> removeRepeatMapByKey1(List<Map<String, Object>> list, String mapKey) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        //把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        List<Map<String, Object>> listMap = new ArrayList<>();

        Map<String, Map> msp = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            String id = (String) map.get(mapKey);
            map.remove(mapKey);
            msp.put(id, map);
        }

        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for (String key : mspKey) {
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }

        return listMap;
    }

    public static Collection<Map> removeRepeat(List<Map<String, Object>> lists, String mapKey) {
        HashMap<String, Map> nMap = new HashMap<>();
        for (Map<String, Object> item : lists) {
            String value = (String) item.get(mapKey);
            if (!nMap.containsKey(value)) {
                nMap.put(value, item);
            }
        }

        Collection<Map> values = nMap.values();
        return values;
    }


    public static Collection<Map> removeRepeatTactics(List<Map<String, Object>> lists, String mapKey) {
        HashMap<String, Map> nMap = new HashMap<>();
        for (Map<String, Object> item : lists) {
            String value = (String) item.get(mapKey);
            if (nMap.containsKey(value)) {
                Map m = nMap.get(value);
                String tacticsName = (String) item.get("tactics_name");
                String tacticsDesc = (String) item.get("tactics_desc");
                Integer oNum = (Integer) m.get("num");
                m.put("tactics_name" + oNum, tacticsName);
                m.put("tactics_desc" + oNum, tacticsDesc);
                m.put("num", oNum + 1);
                nMap.put(value, m);
            } else {
                item.put("num", 1);
                nMap.put(value, item);
            }
        }

        Collection<Map> values = nMap.values();
        return values;
    }


    public static void main(String[] args) {
        /*Map<String, Map> msp = new HashMap<String, Map>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", "1123");
        map1.put("name", "张三");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("id", "2");
        map2.put("name", "李四");
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("id", "1123");
        map3.put("name", "王五");
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("id", "3");
        map4.put("name", "赵六");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        System.out.println("初始数据：" + list.toString());
        System.out.println("去重之后：" + removeRepeatMapByKey(list, "id"));*/


        ArrayList<Map<String, Object>> lists = new ArrayList<>();
        HashMap<String, Object> maps1 = new HashMap<>();
        maps1.put("user_no", "001");
        maps1.put("tactics_name", "策略名称1");
        maps1.put("tactics_desc", "策略描述1");

        HashMap<String, Object> maps2 = new HashMap<>();
        maps2.put("user_no", "001");
        maps2.put("tactics_name", "策略名称2");
        maps2.put("tactics_desc", "策略描述2");

        HashMap<String, Object> maps3 = new HashMap<>();
        maps3.put("user_no", "002");
        maps3.put("tactics_name", "策略名称1");
        maps3.put("tactics_desc", "策略描述1");

        HashMap<String, Object> maps4 = new HashMap<>();
        maps4.put("user_no", "002");
        maps4.put("tactics_name", "策略名称2");
        maps4.put("tactics_desc", "策略描述2");

        HashMap<String, Object> maps5 = new HashMap<>();
        maps5.put("user_no", "002");
        maps5.put("tactics_name", "策略名称3");
        maps5.put("tactics_desc", "策略描述3");

        lists.add(maps1);
        lists.add(maps2);
        lists.add(maps3);
        lists.add(maps4);
        lists.add(maps5);

        System.out.println("原始数据：" + lists);

        Collection<Map> list1 = removeRepeatTactics(lists, "user_no");
//        Collection<Map> list1 = removeRepeat(lists, "user_no");
        System.out.println("处理后数据：" + list1);


    }


}
