package com.osg.framework.web.security.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    Logger logger =LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

    private static final String FORBID_URI ="/login.html";    

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        System.out.println(" *** UnauthorizedEntryPoint.commence: " + request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        //response.sendRedirect(request.getContextPath() + FORBID_URI);
    }
}
