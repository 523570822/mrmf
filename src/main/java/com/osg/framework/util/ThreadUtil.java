package com.osg.framework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程变量容器
 * 
 * @author xiangf
 *
 */
public class ThreadUtil {

    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<Map<String,Object>>();

    /**
     * 存线程变量
     * @param key
     * @param obj
     */
    public static void putThreadVariable(String key, Object obj) {
        Map<String,Object> vm = threadLocal.get();
        if (vm == null) {
            vm = new HashMap<String,Object>();
            threadLocal.set(vm);
        }
        vm.put(key, obj);
    }

    /**
     * 取线程变量
     * @param key
     * @return
     */
    public static Object getThreadVariable(String key) {
        Map<String,Object> vm = threadLocal.get();
        if (vm == null) {
            return null;
        } else {
            return vm.get(key);
        }
    }

    /**
     * 移除线程变量
     * @param key
     * @return
     */
    public static Object removeThreadVariable(String key) {
        Map<String,Object> vm = threadLocal.get();
        if (vm == null) {
            return null;
        } else {
            return vm.remove(key);
        }
    }

    /**
     * 清空线程变量
     */
    public static void clearThreadVariable() {
        Map<String,Object> vm = threadLocal.get();
        if (vm != null) {
            vm.clear();
            vm = null;
        }
    }
}
