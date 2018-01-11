package com.osg.framework.web.log;

/**
 * 监控日志对象
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 *
 */
public class MMonitorLog {
	
	/**
	 * 日志记录ID
	 */
	private String logId;

	/**
	 * 接口被调用的时间，格式：yyyy-MM-dd HH:mm:ss
	 */
	private String invokeTime;
	
	/**
	 * 请求耗时，单位：毫秒
	 */
	private long timeSpent;
	
	/**
	 * 底层支撑业务接口请求耗时，单位：毫秒
	 */
	private long rmiTimeSpent;

	/**
	 * 接口地址
	 */
	private String path;
	
	/**
	 * 请求方式（GET/POST）
	 */
	private String operation;
	
	/**
	 * 客户端IP
	 */
	private String ip;
	
	/**
	 * 客户端应用ID
	 */
	private String clientId;
	
	/**
	 * Session ID
	 */
	private String sessionId;
	
	/**
	 * 客户端类型
	 */
	private String clientType;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	private String userName;
	
	private String communityId;

	public String getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(String invokeTime) {
		this.invokeTime = invokeTime;
	}

	public long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}

	public long getRmiTimeSpent() {
		return rmiTimeSpent;
	}

	public void setRmiTimeSpent(long rmiTimeSpent) {
		this.rmiTimeSpent = rmiTimeSpent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
}
