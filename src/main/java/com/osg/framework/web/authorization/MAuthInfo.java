package com.osg.framework.web.authorization;

import java.util.Map;

/**
 * 授权信息
 * 
 * @author xiangf
 * @date 2014-04-14 @Copyright(c) Dajia shequ
 * 
 */
public class MAuthInfo {

	/**
	 * 客户端ID
	 */
	private String clientID;

	/**
	 * 用户ID
	 */
	private String personID;

	/**
	 * 关联实体ID
	 */
	private String entityID;

	/**
	 * 用户姓名
	 */
	private String personName;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * token
	 */
	private String token;

	/**
	 * 全部授权信息
	 */
	private Map<?, ?> authInfo;

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public Map<?, ?> getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(Map<?, ?> authInfo) {
		this.authInfo = authInfo;
	}

}
