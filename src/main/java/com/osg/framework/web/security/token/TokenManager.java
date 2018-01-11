package com.osg.framework.web.security.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetails;

import com.mrmf.entity.Account;
import com.osg.framework.web.security.VerifyContext;

public interface TokenManager {
	/**
	 * 创建一个新的token(sessionid)
	 * @param userDetails
	 * @param request
	 * @return
	 */
    public TokenInfo createNewToken(UserDetails userDetails, HttpServletRequest request);

    public UserDetails removeToken(String token);

    public VerifyContext getUserDetails(String token);
    
    public boolean checkToken(String token);
    
    public Cookie deleteCookie(HttpServletRequest request,HttpServletResponse response,String cookiename);
    
    public String getCookieString(HttpServletRequest request,String cookiename);
    
    public Account getCurrentAccount();
}
