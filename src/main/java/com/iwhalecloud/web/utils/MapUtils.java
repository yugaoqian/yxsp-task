package com.iwhalecloud.web.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapUtils {
    /**
     * 获取MAP里面的字符串，若不存在返回空字符串
     *
     * @param map
     * @param key
     * @return
     */
    public static String getStringValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) {
            return "";
        }
        if (map.get(key) == null) {
        	return "";
        }
        return map.get(key).toString();
    }

    public static int getIntValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) {
            return 0;
        }
        try {
            return Integer.parseInt(map.get(key).toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long getLongValue(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) {
            return 0;
        }
        try {
            return Long.parseLong(map.get(key).toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Map<String, Object> newParam() {
        return new HashMap<String, Object>();
    }
    
    public static boolean isEmpty(Map map) {
        if (null == map) {
            return true;
        }
        return map.isEmpty();
    }
    
	/**
    *
    * Map转String
    * @param map
    * @return
    */
   public static String getMapToString(Map<String,Object> map){
       Set<String> keySet = map.keySet();
       //将set集合转换为数组
       String[] keyArray = keySet.toArray(new String[keySet.size()]);
       //给数组排序(升序)
       Arrays.sort(keyArray);
       //因为String拼接效率会很低的，所以转用StringBuilder
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < keyArray.length; i++) {
           // 参数值为空，则不参与签名 这个方法trim()是去空格
           if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
               sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
           }
           if(i != keyArray.length-1){
               sb.append(",");
           }
       }
       return sb.toString();
   }
   
   /**
    * 
    * String转map
    * @param str
    * @return
    */
   public static Map<String,Object> getStringToMap(String str){
       //根据逗号截取字符串数组
       String[] str1 = str.split(",");
       //创建Map对象
       Map<String,Object> map = new HashMap<String,Object>();
       //循环加入map集合
       for (int i = 0; i < str1.length; i++) {
           //根据":"截取字符串数组
           String[] str2 = str1[i].split(":");
           //str2[0]为KEY,str2[1]为值
           map.put(str2[0],str2[1]);
       }
       return map;
   }

    /**
     * 将map中Key值全部转换为小写
     */
    public static Map<String, Object> transformLowerCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toLowerCase();
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }
}
