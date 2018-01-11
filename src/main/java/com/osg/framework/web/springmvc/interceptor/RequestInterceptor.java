package com.osg.framework.web.springmvc.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mrmf.entity.Account;
import com.mrmf.entity.Function;
import com.mrmf.entity.Function2;
import com.mrmf.entity.LoginLog;
import com.mrmf.service.function.Function2Service;
import com.mrmf.service.function.FunctionService;
import com.mrmf.service.loginLog.LoginLogService;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	public static Logger logger = Logger.getLogger(RequestInterceptor.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	private static TokenManager tokenManager;
	private static LoginLogService loginLogService;
	private static FunctionService functionService;
	private static Function2Service function2Service;

	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}

		bos.close();
		return bos.toByteArray();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Map fillMap = new HashMap();
		Enumeration attEnu = request.getAttributeNames();
		while (attEnu.hasMoreElements()) {
			String attName = (String) attEnu.nextElement();
			Object fillForm = request.getAttribute(attName);
			if (attName.startsWith("ff")) { // 表单自动回填
				attName = attName.substring(2);
				fillMap.put(attName, fillForm);
			}
		}
		if (fillMap.size() > 0) {
			String fillMapStr = mapper.writeValueAsString(fillMap);
			request.setAttribute("_FILL_MAP", fillMapStr.replace("</script>", "<//script>"));
		}

		String fid = request.getParameter("_fid");
		if (!StringUtils.isEmpty(fid)) {
			if (tokenManager == null)
				tokenManager = MAppContext.getAppContext().getBean("tokenManager", TokenManager.class);
			if (loginLogService == null)
				loginLogService = MAppContext.getAppContext().getBean("loginLogService", LoginLogService.class);

			Account account = tokenManager.getCurrentAccount();
			String organId = "0";
			String ip = getIpAddr(request);
			LoginLog loginLog = new LoginLog();
			loginLog.setFunctionId(fid);
			if ("admin".equals(account.getAccountType())) { // 超级管理员
				if (function2Service == null)
					function2Service = MAppContext.getAppContext().getBean("function2Service", Function2Service.class);

				if (function2Service != null) {
					Function2 f = function2Service.queryById(fid);
					if (f != null) {
						loginLog.setFunctionName(f.getName());
					}
				}

			} else if ("organ".equals(account.getAccountType())) { // 店铺管理员
				organId = account.getEntityID();
				if (functionService == null)
					functionService = MAppContext.getAppContext().getBean("functionService", FunctionService.class);
				if (functionService != null) {
					Function f = functionService.queryById(fid);
					if (f != null) {
						loginLog.setFunctionName(f.getName());
					}
				}
			}

			loginLog.setMemo("进入模块" + loginLog.getFunctionName());
			loginLog.setAccountName(account.getAccountName());
			loginLog.setOrganId(organId);
			loginLog.setIp(ip);
			loginLogService.upsert(loginLog);
		}

		super.postHandle(request, response, handler, modelAndView);
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
		try {
			if (request.getRequestURI().indexOf("toLogin") != -1) {
				if (ex == null) {

				}
			}
		} catch (Exception e) {
			// ignore
		}
	}
}
