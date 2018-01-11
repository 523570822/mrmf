package com.osg.framework.web.security.token;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;

import com.mrmf.entity.Account;
import com.osg.framework.web.AppContext;
import com.osg.framework.web.cache.CacheConstant;
import com.osg.framework.web.cache.CacheConstant.CacheEnum;
import com.osg.framework.web.cache.CacheManager;
import com.osg.framework.web.security.VerifyContext;

/**
 * Implements simple token manager, that keeps a single token for each user.
 * 
 * If user logs in again, older token is invalidated.
 * 
 */

public class TokenManagerCache implements TokenManager {

	private static final Logger logger = LoggerFactory.getLogger(TokenManagerCache.class);

	private CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	private String generateToken() {
		byte[] tokenBytes = new byte[32];
		new SecureRandom().nextBytes(tokenBytes);
		try {
			return URLEncoder.encode(new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String generateToken(HttpServletRequest request) {
		return request.getSession().getId();
	}

	public TokenInfo createNewToken(UserDetails userDetails, HttpServletRequest request) {
		String token = generateToken(request);
		logger.debug("User {} ,create a new token {}", userDetails.getUsername(), token);
		TokenInfo tokenInfo = new TokenInfo(token, userDetails);
		return tokenInfo;
	}

	public UserDetails removeToken(String token) {
		if (token != null && !"null".equals(token)) {
			UserDetails userDetails = getUserDetails(token);
			if (userDetails != null) {
				boolean result = cacheManager.removeObject(CacheEnum.TOKEN, token);
				if (result) {
					logger.debug("Cache remove token:{} success!");
				} else {
					logger.debug("Cache remove token:{} failed!");
				}
			}
			return userDetails;
		}
		return null;
	}

	public VerifyContext getUserDetails(String token) {
		Object userDetails = cacheManager.getObject(CacheEnum.TOKEN, token);
		if (userDetails != null) {
			return (VerifyContext) userDetails;
		} else {
			logger.debug("token not found ...{}", token);
		}
		return null;
	}

	public boolean checkToken(String token) {
		if (getUserDetails(token) != null) {
			return true;
		}
		return false;
	}

	public Cookie deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookiename) {
		/*
		 * Cookie tokencookie = this.getCookie(request,cookiename);
		 * if(tokencookie != null){ tokencookie.setMaxAge(-1);
		 * response.addCookie(tokencookie); } return tokencookie;
		 */
		return null;
	}

	private Cookie getCookie(HttpServletRequest request, String cookiename) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookiename.equalsIgnoreCase(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	public String getCookieString(HttpServletRequest request, String cookiename) {
		Cookie tokencookie = getCookie(request, cookiename);
		try {
			if (tokencookie != null) {
				return URLDecoder.decode(tokencookie.getValue(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Account getCurrentAccount() {
		Cookie cookie = this.getCookie(AppContext.request(), CacheConstant.HEADER_TOKEN);
		if (cookie == null)
			cookie = this.getCookie(AppContext.request(), "JSESSIONID");
		if (cookie != null) {
			return (Account) getUserDetails(cookie.getValue()).getUser().getEntity();
		}
		return null;
	}
}
