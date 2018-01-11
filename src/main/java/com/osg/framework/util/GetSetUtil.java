package com.osg.framework.util;

import java.lang.reflect.Method;
/**
 * 利用反射机制调用对象中的       get和 set 方法
 * @author yangshaodong
 *
 */
public class GetSetUtil {
		 /**
	     * @param obj
	     *            操作的对象
	     * @param att
	     *            操作的属性
	     * */
	   public static Object getter(Object obj, String att) {
	        try {
	            Method method = obj.getClass().getMethod("get" + att);
	            return method.invoke(obj);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	    /**
	     * @param obj
	     *            操作的对象
	     * @param att
	     *            操作的属性
	     * @param value
	     *            设置的值
	     * @param type
	     *            参数的属性
	     * */
	    public static void setter(Object obj, String att, Object value,
	            Class<?> type) {
	        try {
	            Method method = obj.getClass().getMethod("set" + att, type);
	            method.invoke(obj, value);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
