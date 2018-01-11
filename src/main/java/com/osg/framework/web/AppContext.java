package com.osg.framework.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author 张一杰
 * @date 2011-8-22下午6:20:36 @Copyright(c) 奔跑组织
 */
public class AppContext {

	private static ApplicationContext mvcContext;
	private static ServletContext context;

	public static ServletContext getContext() {
		return context;
	}

	public static void setContext(ServletContext context) {
		AppContext.context = context;
	}

	/**
	 * 获取mvcContext
	 * 
	 * @return mvcContext
	 */
	public static ApplicationContext getMvcContext() {
		return mvcContext;
	}

	/**
	 * 设置mvcContext
	 * 
	 * @param mvcContext
	 *            mvcContext
	 */
	public static void setMvcContext(ApplicationContext mvcContext) {
		AppContext.mvcContext = mvcContext;
	}

	public static void init(ServletContext sc, ApplicationContext ac, ApplicationContext mc) throws ServletException {
		mvcContext = mc;
		context = sc;
	}

	public static HttpServletRequest request() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

}
