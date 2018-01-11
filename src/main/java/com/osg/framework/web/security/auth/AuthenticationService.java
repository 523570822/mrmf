package com.osg.framework.web.security.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import com.osg.framework.web.security.token.TokenInfo;

public interface AuthenticationService {

    TokenInfo authenticate(String login, String password, HttpServletRequest request);

    boolean checkToken(String token);

    void logout(String token);

    UserDetails currentUser();
    
    UserDetails getUserDeatils(String token);
}
