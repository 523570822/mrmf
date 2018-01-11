package com.osg.framework.web.security.auth;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.osg.framework.web.security.token.TokenInfo;
import com.osg.framework.web.security.token.TokenManager;

public class AuthenticationServiceDefault implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceDefault.class);

    @Autowired
    private ApplicationContext applicationContext;

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public AuthenticationServiceDefault(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @PostConstruct
    public void init() {
        System.out.println(" *** AuthenticationServiceImpl.init with: " + applicationContext);
    }

    public TokenInfo authenticate(String loginname, String password ,HttpServletRequest request) {
        logger.debug("Authenticate user:{} , password:{}", loginname, password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginname, password);
        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (authentication.getPrincipal() != null) {
                UserDetails userContext = (UserDetails) authentication.getPrincipal();
                TokenInfo newToken = tokenManager.createNewToken(userContext,request);
                if (newToken == null) {
                    logger.error("Authenticate create new token is null,userConetxt is {}", userContext);
                    return null;
                } else {
                    logger.info("Authenticate create new token{}", newToken);
                }
                return newToken;
            } else {
                logger.debug("Authenticate {}", authentication.toString());
            }
        } catch (AuthenticationException e) {
            logger.error("Authenticate - FAILED: :{}", e.getMessage());
        }
        return null;
    }

    public boolean checkToken(String token) {
        logger.debug("Check Token {}", token);
        UserDetails userDetails = tokenManager.getUserDetails(token);
        logger.debug("Get UserDetais {} by token", userDetails);
        if (userDetails == null) {
            return false;
        }
        UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(securityToken);
        return true;
    }

    public void logout(String token) {
        logger.debug("Remove Token:{}", token);
        UserDetails logoutUser = tokenManager.removeToken(token);
        logger.debug("Logout: {} ", logoutUser);
        SecurityContextHolder.clearContext();
    }

    public UserDetails currentUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            logger.debug("Get CurrentUser is Null");
            return null;
        }
        UserDetails details = (UserDetails)authentication.getPrincipal();
        return details;
    }

    public UserDetails getUserDeatils(String token) {
        return tokenManager.getUserDetails(token);
    }

}
