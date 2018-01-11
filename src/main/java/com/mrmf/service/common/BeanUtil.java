package com.mrmf.service.common;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/9/22.
 */
public class BeanUtil {
    public static String getCollectionName(Class<?> clazz, String country){
        return country + "_" + clazzNameFirstLowerCase(clazz);
    }
    public static String clazzNameFirstLowerCase(Class<?> clazz){
        String clazzName = clazz.getSimpleName();
        return clazzName.substring(0,1).toLowerCase() + clazzName.substring(1);
    }

	public static List<List<String>> mapToList(List<Map<String, String>> mapList, String[] keys) {
		List<List<String>> result = new ArrayList<List<String>>();
		for(String key : keys){
			List<String> keyList = new ArrayList<String>();
			for(int i = 0; i<mapList.size(); i++){
				keyList.add(String.valueOf(mapList.get(i).get(key)));
			}
			result.add(keyList);
		}
		return result;
	}
	public static <T> List<String> beanToList(List<T> beanList, String key, Class<T> clazz) {
		try {
			List<String> keyList = new ArrayList<String>();
			for(int i = 0; i<beanList.size(); i++){
				String method = loadGetMethodName(key);
				keyList.add(String.valueOf(clazz.getMethod(method).invoke(beanList.get(i))));
			}
			return keyList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static <T> List<String> beanToDistinctList(List<T> beanList, String key, Class<T> clazz) {
		try {
			Set<String> keyList = new HashSet<>();


			for(int i = 0; i<beanList.size(); i++){
				String method = loadGetMethodName(key);
				keyList.add(String.valueOf(clazz.getMethod(method).invoke(beanList.get(i))));
			}
			return new ArrayList<>(keyList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static <T> T loadBeanByFieldValue(List<T> physicalDatas, String fieldName, String fieldValue, Class<T> clazz) {
		try {
			T result = null ;
			for (T t : physicalDatas) {
				String getMethodName = loadGetMethodName(fieldName);
				String beanFiledValue = String.valueOf(clazz.getMethod(getMethodName).invoke(t));
				if(StringUtils.equals(beanFiledValue, fieldValue)){
					result = t;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String loadGetMethodName(String filedName){
		return "get"+filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
	}
}
