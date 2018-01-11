package com.osg.framework.web.springmvc.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.osg.entity.error.MErrorCode;
import com.osg.framework.util.PropertiesLoader;
import com.osg.framework.util.type.BaseTypeUtil;
import com.osg.framework.web.authorization.AuthService;
import com.osg.framework.web.authorization.MAuthInfo;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.exception.MAuthorizedException;
import com.osg.framework.web.http.util.MHttpRequestParameterUtil;

/**
 * 权限拦截器 适用于大家社区OAuth权限验证（依赖MContextInterceptor提供上下文环境变量）
 * 
 * @author xiangf
 * @date 2014-04-14 @Copyright(c) Dajia shequ
 */
public class MAuthInterceptor implements HandlerInterceptor {

	private static Log log = LogFactory.getLog(MAuthInterceptor.class);
	private static final String PARAM_ACCESS_TOKEN = "access_token";

	@Autowired
	private AuthService authService;

	private MAuthInterceptorExtention extention;

	private static List<String> noLoginUriList = new ArrayList<String>();

	static {
		Properties props = PropertiesLoader
				.loadXml(MAuthInterceptor.class.getClassLoader().getResourceAsStream("no-login.xml"));
		if (props != null) {
			String uris = props.getProperty("uri");
			if (uris != null) {
				String[] uri = uris.split("\\|");
				for (int i = 0; i < uri.length; i++) {
					noLoginUriList.add(uri[i]);
				}
			}
		}
	}

	/**
	 * 权限验证
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {

		String uri = arg0.getRequestURI();
		String ctxPath = arg0.getContextPath() + "/r/";
		boolean isNotLogin = false;
		if (uri.startsWith(ctxPath)) {
			String res = uri.substring(ctxPath.length());
			for (String url : noLoginUriList) {
				if (url.endsWith("*")) {
					if (res.startsWith(url.substring(0, url.length() - 1))) {
						isNotLogin = true;
						break;
					}
				} else if (noLoginUriList.contains(res)) {
					isNotLogin = true;
					break;
				}
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("MAuthInterceptor - preHandle start.");
		}
		// 1. 从http request中获取access_token，判断是否有token
		String token = MHttpRequestParameterUtil.getReqParamAsString(PARAM_ACCESS_TOKEN);
		if (BaseTypeUtil.isEmptyString(token))
			token = (String)MAppContext.getSessionVariable(PARAM_ACCESS_TOKEN);
		if (log.isDebugEnabled()) {
			log.debug("MAuthInterceptor - access_token:" + token);
		}

		if (isNotLogin && BaseTypeUtil.isEmptyString(token)) {
			return true;
		}

		if (BaseTypeUtil.isEmptyString(token)) {
			throw new MAuthorizedException(MErrorCode.e1001);
		}

		// 2. 验证token是否与session中缓存的一致
		MAuthInfo authInfo = null;
		if (token.equals(MAppContext.getSessionVariable(PARAM_ACCESS_TOKEN)) && MAppContext.getAuthInfo() != null) {
			// 一致则验证通过
			return true;
		} else {
			// 不一致重新验证(验证失败抛出异常)
			authInfo = authService.getAuthInfo(token);
			if (extention != null) {
				extention.postHandle(authInfo);
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("MAuthInterceptor - auth_info:" + authInfo.toString());
		}

		// 3. 验证完成，保存用户身份信息
		MAppContext.setAuthInfo(authInfo);
		MAppContext.putSessionVariable(PARAM_ACCESS_TOKEN, token);
		if (log.isDebugEnabled()) {
			log.debug("MAuthInterceptor - preHandle end.");
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public MAuthInterceptorExtention getExtention() {
		return extention;
	}

	public void setExtention(MAuthInterceptorExtention extention) {
		this.extention = extention;
	}
}
