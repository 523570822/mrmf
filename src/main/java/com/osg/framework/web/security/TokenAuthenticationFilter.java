package com.osg.framework.web.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.filter.GenericFilterBean;

import com.mrmf.entity.Account;
import com.osg.entity.ReturnStatus;
import com.osg.entity.error.MError;
import com.osg.entity.error.MErrorCode;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.cache.CacheConstant;
import com.osg.framework.web.cache.CacheConstant.CacheEnum;
import com.osg.framework.web.cache.CacheManager;
import com.osg.framework.web.security.auth.AuthenticationService;
import com.osg.framework.web.security.token.TokenInfo;
import com.osg.framework.web.security.token.TokenManager;

/**
 * Takes care of HTTP request/response pre-processing for login/logout and token
 * check. Login can be performed on any URL, logout only on specified
 * {@link #logoutLink}. All the interaction with Spring Security should be
 * performed via {@link AuthenticationService}.
 * <p>
 * {@link SecurityContextHolder} is used here only for debug outputs. While this
 * class is configured to be used by Spring Security (configured filter on
 * FORM_LOGIN_FILTER position), but it doesn't really depend on it at all.
 */
public final class TokenAuthenticationFilter extends GenericFilterBean {
	Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	/**
	 * Request attribute that indicates that this filter will not continue with
	 * the chain. Handy after login/logout, etc.
	 */
	private static final String REQUEST_ATTR_DO_NOT_CONTINUE = "MyAuthenticationFilter-doNotContinue";

	private CacheManager cacheManager;

	private final AuthenticationService authenticationService;
	private final TokenManager tokenManager;
	private final PasswordEncoder passwordEncoder;

	private final String loginLink;
	private final String logoutLink;
	private final String refreshLink;

	/*
	 * public TokenAuthenticationFilter(AuthenticationService
	 * authenticationService, String logoutLink) { this.authenticationService =
	 * authenticationService; this.logoutLink = logoutLink; }
	 */

	public TokenAuthenticationFilter(AuthenticationService authenticationService, TokenManager tokenManager,
			PasswordEncoder passwordEncoder, String loginLink, String logoutLink, String refreshLink) {
		super();
		this.authenticationService = authenticationService;
		this.tokenManager = tokenManager;
		this.passwordEncoder = passwordEncoder;
		this.loginLink = loginLink;
		this.logoutLink = logoutLink;
		this.refreshLink = refreshLink;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = tokenManager.getCookieString(httpRequest, CacheConstant.HEADER_TOKEN);
		if (StringUtils.isEmpty(token)) {
			token = tokenManager.getCookieString(httpRequest, "JSESSIONID");
			if (!StringUtils.isEmpty(token)) {
				// 创建Cookie对象
				Cookie cookie = new Cookie(CacheConstant.HEADER_TOKEN, token);
				// 设置生命周期以秒为单位
				cookie.setMaxAge(8 * 60 * 60);
				HttpServletResponse hr = (HttpServletResponse) response;
				// 添加Cookie
				hr.addCookie(cookie);
			}
		}
		String serverPath = httpRequest.getServletPath();
		if (serverPath.equals("") || "/moduleweb/resources/api.jsp".equals(serverPath) || serverPath.endsWith(".woff")
				|| serverPath.endsWith(".woff2") || serverPath.endsWith(".ttf")) {
			((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
			// do nothing
		} else if (httpRequest.getServletPath().equals(loginLink)) {
			doNotContinueWithRequestProcessing(httpRequest);
			checkLoginAnDoSomething(httpRequest, httpResponse, token);
		} else if (checkLogout(httpRequest, httpResponse, token)
				|| checkRefreshToken(httpRequest, httpResponse, token)) {
			doNotContinueWithRequestProcessing(httpRequest);
		} else {
			// 检查token是否正确(可能清楚缓存导致的token验证不通过)
			if (!checkToken(httpRequest, httpResponse, token)) {
				// 检查cookie里的用户名密码是否正确
				String username = tokenManager.getCookieString(httpRequest, CacheConstant.HEADER_USERNAME);
				String password = tokenManager.getCookieString(httpRequest, CacheConstant.HEADER_PASSWORD);
				if (null == username || null == password
						|| !checkUsernameAndPassword(username, password, httpRequest, httpResponse, token)
								.isSuccess()) {
					doNotContinueWithRequestProcessing(httpRequest);
					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
			} else {
				VerifyContext verifyContext = (VerifyContext) tokenManager.getUserDetails(token);
				Account account = (Account) verifyContext.getUser().getEntity();
				if ("organ".equals(account.getAccountType())
						&& httpRequest.getSession().getAttribute("organId") == null) { // 店铺管理员
					httpRequest.getSession().setAttribute("isOrganAdmin", true); // 设置店铺管理员参数
					httpRequest.getSession().setAttribute("organId", account.getEntityID()); // 设置管理公司id
				}
			}
		}

		if (canRequestProcessingContinue(httpRequest)) {
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	/*
	 * example: Authorization=Basic YWRtaW46MTIzNDU2 (admin:123456)
	 */
	private boolean checkLoginAnDoSomething(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String token) throws IOException {
		String authorization = httpRequest.getHeader(CacheConstant.AUTHORIZATION);
		ReturnStatus result = null;
		if (authorization != null) {
			result = checkBasicAuthorization(authorization, httpRequest, httpResponse, token);
		}

		if (null == result) {
			result = new ReturnStatus(false);
			result.getErrors().add(new MError(MErrorCode.e9000));
			result.setMessage(MErrorCode.e9000.desc());
		}

		String body;
		try {
			body = JsonUtils.toJson(result);// JsonParserFactory.getParser().toJson(result).toString();
			httpResponse.setContentType("text/html;charset=UTF-8");
			httpResponse.getWriter().write(body);
		} catch (Exception e) {
			throw new IOException(e);
		}

		return result.isSuccess();
	}

	private ReturnStatus checkBasicAuthorization(String authorization, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, String token) throws IOException {
		StringTokenizer tokenizer = new StringTokenizer(authorization);
		if (tokenizer.countTokens() < 2) {
			return null;
		}
		if (!tokenizer.nextToken().equalsIgnoreCase(CacheConstant.BASIC_AUTH_PREFIX)) {
			return null;
		}

		String base64 = tokenizer.nextToken();
		String loginPassword = new String(Base64.decode(base64.getBytes(StandardCharsets.UTF_8)));
		tokenizer = new StringTokenizer(loginPassword, ":");
		String username = tokenizer.nextToken();
		String pwd = tokenizer.nextToken();
		String password = passwordEncoder.encodePassword(pwd, null);
		ReturnStatus status = checkUsernameAndPassword(username, password, httpRequest, httpResponse, token);
		// 登录成功后返回登录状态
		return status;
	}

	private ReturnStatus checkUsernameAndPassword(String username, String password, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, String oldtoken) throws IOException {
		ReturnStatus returnResult = new ReturnStatus(false);
		TokenInfo tokenInfo = authenticationService.authenticate(username, password, httpRequest);
		if (tokenInfo != null && tokenInfo.getUserDetails() != null) {
			VerifyContext verifyContext = (VerifyContext) tokenInfo.getUserDetails();
			Account account = (Account) verifyContext.getUser().getEntity();
			if ("organ".equals(account.getAccountType())) { // 店铺管理员
				httpRequest.getSession().setAttribute("isOrganAdmin", true); // 设置店铺管理员参数
				httpRequest.getSession().setAttribute("organId", account.getEntityID()); // 设置管理公司id
			}
			if (account.isLogin()) {
				if (null != oldtoken && !"".equals(oldtoken) && !"null".equals(oldtoken)
						&& !"undefined".equals(oldtoken)) {
					logger.info("tokenInfo.setToken use the oldtoken Token :{}", tokenInfo.getToken());
					tokenInfo.setToken(oldtoken);
				}
				logger.info("the new Token :{},entity:{}", tokenInfo.getToken(), tokenInfo);
				this.cacheManager.saveObject(CacheEnum.TOKEN, tokenInfo.getToken(), tokenInfo.getUserDetails(),
						CacheConstant.USER_SESSION_TIME);
				logger.info("the token:{}, save object:{}", tokenInfo.getToken(),
						this.cacheManager.getObject(CacheEnum.TOKEN, tokenInfo.getToken()));
				returnResult.setEntity(account);
				returnResult.setSuccess(true);
			} else {
				returnResult.getErrors().add(new MError(MErrorCode.e9001));
				returnResult.setMessage(MErrorCode.e9001.desc());
			}
			httpResponse.setHeader(CacheConstant.HEADER_TOKEN, tokenInfo.getToken());
		} else {
			logger.error("User {} ,Password {} Unauthorized!", username, password);
			returnResult.getErrors().add(new MError(MErrorCode.e9000));
			returnResult.setMessage(MErrorCode.e9000.desc());
		}
		return returnResult;
	}

	/** Returns true, if request contains valid authentication token. */
	private boolean checkToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String token)
			throws IOException {
		if (null != token && authenticationService.checkToken(token)) {
			logger.info(" *** " + CacheConstant.HEADER_TOKEN + " valid for: "
					+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return true;
		} else {
			return false;
		}
	}

	private boolean checkLogout(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String token) {
		if (httpRequest.getServletPath().equals(logoutLink)) {
			authenticationService.logout(token);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This is set in cases when we don't want to continue down the filter
	 * chain. This occurs for any {@link HttpServletResponse#SC_UNAUTHORIZED}
	 * and also for login or logout.
	 */
	private void doNotContinueWithRequestProcessing(HttpServletRequest httpRequest) {
		httpRequest.setAttribute(REQUEST_ATTR_DO_NOT_CONTINUE, "");
	}

	private boolean canRequestProcessingContinue(HttpServletRequest httpRequest) {
		return httpRequest.getAttribute(REQUEST_ATTR_DO_NOT_CONTINUE) == null;
	}

	public boolean checkRefreshToken(HttpServletRequest request, HttpServletResponse response, String token)
			throws IOException {
		if (request.getServletPath().equals(refreshLink)) {
			UserDetails userDetails = this.authenticationService.getUserDeatils(token);
			checkUsernameAndPassword(userDetails.getUsername(), userDetails.getPassword(), request, response, token);
			return true;
		} else {
			return false;
		}
	}

}
