package com.osg.framework.web;

import java.io.Serializable;

public class OConstants implements Serializable {

	public static final String HTTP_SESSION = "HTTP_SESSION";
	public static final String CONTROLLER_LOG = "CONTROLLER_LOG";
	public static final String HTTP_REQUEST_URI = "HTTP_REQUEST_URI";
	public static final String HTTP_CONTEXT_PATH = "HTTP_CONTEXT_PATH";

	/**
	 * 表单数据提交JSON字符串的线程存储KEY
	 */
	public static final String THREAD_CONTEXT_JSONSTR_KEY = "THREAD_CONTEXT_JSONSTR_KEY";
	/**
	 * 表单数据提交JSON字符串转换java对象后的线程存储KEY
	 */
	public static final String THREAD_CONTEXT_JSONOBJ_KEY = "THREAD_CONTEXT_JSONOBJ_KEY";
}
