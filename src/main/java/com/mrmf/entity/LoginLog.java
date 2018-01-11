package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 登陆日志
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LoginLog extends DataEntity {

	private String accountName; // 登陆账号
	// createTime 登陆时间
	private String organId;// 公司id，0为平台
	private String ip; // 登陆客户端ip地址

	private String memo; // 操作描述
	private String functionId; // 访问功能id
	private String functionName; // 访问功能名称

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

}
