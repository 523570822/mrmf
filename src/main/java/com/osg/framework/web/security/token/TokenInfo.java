package com.osg.framework.web.security.token;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public final class TokenInfo {

    private final long created = System.currentTimeMillis();
    private String token;
    private final UserDetails userDetails;

    public TokenInfo(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
    	this.token = token;
    }

    @Override
    public String toString() {
        return "TokenInfo{" + "token='" + token + '\'' + ", userDetails" + userDetails + ", created="
                + new Date(created) + '}';
    }

	public UserDetails getUserDetails() {
		return userDetails;
	}   
    
}
