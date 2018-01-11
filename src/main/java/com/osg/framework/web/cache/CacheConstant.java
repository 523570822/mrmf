package com.osg.framework.web.cache;

public class CacheConstant {
    public static final int USER_SESSION_TIME = 60 * 60 * 2;
    
    public enum CacheEnum{
    	TOKEN,
    	ENTITY,
        WECHAT_TOKEN_TIME,
    	;
    } 
    
    public static final String TOKEN_PREFIX = "TOKEN_";
    public static final String ENTITY_PREFIX = "ENTITY_";
    public static final String HEADER_TOKEN = "X-Auth-Token";
    public static final String HEADER_TOKEN_ENCODE = "X-Auth-Token-Encode";
    public static final String HEADER_USERID = "X-Userid";
    public static final String HEADER_USERNAME = "X-Username";
    public static final String HEADER_PASSWORD = "X-Password";
    public static final String BASIC_AUTH_PREFIX = "Basic";
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 微信缓存相关
     */
    public static final String WECHAT_TOKEN_KEY = "WECHAT_TOKEN_CACHE_KEY";
    public static final int WECHAT_TOKEN_TIME = 60 * 100;//100分钟
    
    public static final String WECHAT_OAUTH_TOKEN_KEY = "WECHAT_OAUTH_TOKEN_CACHE_KEY";
    public static final int WECHAT_OAUTH_TOKEN_TIME = 60 * 5;//5分钟
    
    public static final String WECHAT_JSTICKET_CACHE_KEY = "WECHAT_JSTICKET_CACHE_KEY";
    public static final int WECHAT_JSTICKET_TOKEN_TIME = 60 * 100;//100分钟
}
