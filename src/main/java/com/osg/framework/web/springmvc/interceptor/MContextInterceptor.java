package com.osg.framework.web.springmvc.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.osg.framework.util.JsonUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.exception.MIllegalParamException;

/**
 * 系统上下文初始化拦截器，需放在所有拦截器之前
 * 
 * @author xiangf
 * @date 2014-04-14 @Copyright(c) Dajia shequ
 */
public class MContextInterceptor implements HandlerInterceptor {

	private static Log log = LogFactory.getLog(MContextInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("MContextInterceptor - preHandle start.");
		}

		// 1. 将session放到线程变量中，供全局使用
		MAppContext.putHttpSession(arg0.getSession());

		// 2. 放入Spring ApplicationContext
		MAppContext.setAppContext(
				WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getSession().getServletContext()));
		// 3. 解析请求参数，放入线程变量中，供全局使用
		if ("POST".equalsIgnoreCase(arg0.getMethod()) && arg0.getContentType() != null
				&& arg0.getContentType().toLowerCase().contains("application/json")) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				JsonNode rootNode = JsonUtils.getObjectMapper().readTree(arg0.getInputStream());
				Iterator<String> it = rootNode.getFieldNames();
				while (it.hasNext()) {
					String name = it.next();
					JsonNode child = rootNode.path(name);
					if (child.isValueNode() || child.isContainerNode()) {
						map.put(name, child);
					} else {
						continue;
					}
				}
			} catch (java.io.EOFException ioe) {
				// ignore it
			} catch (Exception e) {
				throw MIllegalParamException.getIllegalFormatException(e);
			}
			MAppContext.setReqParamMap(map);
		} else {
			Map<Object, Object> params = new HashMap<Object, Object>();
			try {
				@SuppressWarnings("unchecked")
				Iterator<Entry<String, String[]>> i = arg0.getParameterMap().entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry<?, ?> entry = i.next();
					String[] paramValues = (String[]) entry.getValue();
					if (paramValues != null) {
						params.put(entry.getKey(), paramValues.length >= 1 ? paramValues[0] : null);
					}
				}
			} catch (Exception e) {
				throw MIllegalParamException.getIllegalFormatException(e);
			}
			MAppContext.setReqParamMap(params);
		}

		if (log.isDebugEnabled()) {
			log.debug("MContextInterceptor - preHandle end.");
		}
		return true;
	}
}
