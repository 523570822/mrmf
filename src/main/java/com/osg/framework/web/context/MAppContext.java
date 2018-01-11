package com.osg.framework.web.context;

/**
 * 上下文变量存取工具，用于保存访问线程、session及应用级变量
 * 
 * @author xiangf
 * @date 2014-04-14
 * @Copyright(c) Dajia shequ
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.osg.framework.util.ThreadUtil;
import com.osg.framework.web.authorization.MAuthInfo;
import com.osg.framework.web.log.MMonitorLog;

public class MAppContext {

	private static final Map<String, Object> GLOBALMAP = new ConcurrentHashMap<String, Object>();
	public static final String HTTP_SESSION = "HTTP_SESSION";
	public static final String SESSION_AUTH_INFO = "SESSION_AUTH_INFO";
	public static final String LOG = "MONITOR_LOG";
	public static final String SPRING_APPCONTEXT = "SPRING_APPCONTEXT";
	public static final String REQ_PARAM = "REQ_PARAM";

	// ----- 通用类 -----
	/**
	 * 保存Spring ApplicationContext至线程变量
	 * 
	 * @param ac
	 */
	public static void setAppContext(ApplicationContext ac) {
		MAppContext.putThreadVariable(SPRING_APPCONTEXT, ac);
		MAppContext.putGlobalVariable(SPRING_APPCONTEXT, ac);
	}

	/**
	 * 获取Spring ApplicationContext
	 * 
	 * @return
	 */
	public static ApplicationContext getAppContext() {
		ApplicationContext ac = (ApplicationContext) MAppContext
				.getThreadVariable(SPRING_APPCONTEXT);
		if (ac == null) {
			ac = (ApplicationContext) MAppContext
					.getGlobalVariable(SPRING_APPCONTEXT);
		}
		return ac;
	}

	/**
	 * 存线程变量
	 * 
	 * @param key
	 * @param variable
	 */
	public static void putThreadVariable(String key, Object variable) {
		ThreadUtil.putThreadVariable(key, variable);
	}

	/**
	 * 获取线程变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object getThreadVariable(String key) {
		return ThreadUtil.getThreadVariable(key);
	}

	/**
	 * 移除线程变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object removeThreadVariable(String key) {
		return ThreadUtil.removeThreadVariable(key);
	}

	/**
	 * 清空线程变量
	 */
	public static void clearThreadVariable() {
		ThreadUtil.clearThreadVariable();
	}

	/**
	 * 存session变量
	 * 
	 * @param key
	 * @param variable
	 */
	public static void putSessionVariable(String key, Object variable) {
		HttpSession session = getHttpSession();
		if (session != null) {
			session.setAttribute(key, variable);
		}
	}

	/**
	 * 获取session变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object getSessionVariable(String key) {
		HttpSession session = getHttpSession();
		if (session != null) {
			return session.getAttribute(key);
		}
		return null;
	}

	/**
	 * 移除session变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object removeSessionVariable(String key) {
		Object obj = getSessionVariable(key);
		getHttpSession().removeAttribute(key);
		return obj;
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public static HttpSession getHttpSession() {
		return (HttpSession) getThreadVariable(HTTP_SESSION);
	}

	/**
	 * 存放session
	 * 
	 * @param session
	 */
	public static void putHttpSession(HttpSession session) {
		putThreadVariable(HTTP_SESSION, session);
	}

	/**
	 * 存全局变量
	 * 
	 * @param key
	 * @param variable
	 */
	public static void putGlobalVariable(String key, Object variable) {
		GLOBALMAP.put(key, variable);
	}

	/**
	 * 获取全局变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object getGlobalVariable(String key) {
		return GLOBALMAP.get(key);
	}

	/**
	 * 移除全局变量
	 * 
	 * @param key
	 * @return
	 */
	public static Object removeGlobalVariable(String key) {
		return GLOBALMAP.remove(key);
	}

	/**
	 * 清空全局变量
	 */
	public static void clearGlobalVariable() {
		GLOBALMAP.clear();
	}

	// ----- 业务类 -----

	/**
	 * 存放授权信息（优先使用session作为上下文、其次使用线程变量）
	 * 
	 * @param auth
	 */
	public static void setAuthInfo(MAuthInfo auth) {
		if (MAppContext.getHttpSession() != null) {
			MAppContext.putSessionVariable(SESSION_AUTH_INFO, auth);
		} else {
			MAppContext.putThreadVariable(SESSION_AUTH_INFO, auth);
		}
	}

	/**
	 * 获取授权信息
	 * 
	 * @return 授权信息
	 */
	public static MAuthInfo getAuthInfo() {
		if (MAppContext.getHttpSession() != null) {
			return (MAuthInfo) MAppContext
					.getSessionVariable(SESSION_AUTH_INFO);
		} else {
			return (MAuthInfo) MAppContext.getThreadVariable(SESSION_AUTH_INFO);
		}
	}

	/**
	 * 清除授权信息
	 */
	public static void removeAuthInfo() {
		MAppContext.removeSessionVariable(SESSION_AUTH_INFO);
		MAppContext.removeThreadVariable(SESSION_AUTH_INFO);
	}

	/**
	 * 获取当前登录用户ID
	 * 
	 * @return
	 */
	public static String getCurrentPersonId() {
		return MAppContext.getAuthInfo().getPersonID();
	}

	/**
	 * 获取当前登录用户姓名
	 * 
	 * @return
	 */
	public static String getCurrentPersonName() {
		return MAppContext.getAuthInfo().getPersonName();
	}

	/**
	 * 存放日志信息
	 * 
	 * @param log
	 *            日志对象
	 */
	public static void setLog(MMonitorLog log) {
		MAppContext.putThreadVariable(LOG, log);
	}

	/**
	 * 获取日志信息
	 * 
	 * @return 日志对象
	 */
	public static MMonitorLog getLog() {
		return (MMonitorLog) MAppContext.getThreadVariable(LOG);
	}

	/**
	 * 存放Http请求参数
	 * 
	 * @param param
	 *            请求参数
	 */
	public static void setReqParamMap(Map<?, ?> param) {
		MAppContext.putThreadVariable(REQ_PARAM, param);
	}

	/**
	 * 获取Http请求参数
	 * 
	 * @param param
	 *            请求参数
	 */
	public static Map<?, ?> getReqParamMap() {
		return (Map<?, ?>) MAppContext.getThreadVariable(REQ_PARAM);
	}
}
