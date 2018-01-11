package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WePayLog extends DataEntity {
	private String userId;  // 用户id
	private int status;     // 支付状态 0 表示未完成 1 表示完成
	private int senderType; // 操作人类型    1    用户          2     技师
	private String message; // 描述信息
	private int type;       // 类型 1 表示提现 0 表示支付
	private double money;   // 金额 支付是正数， 提现是负数

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSenderType() {
		return senderType;
	}

	public void setSenderType(int senderType) {
		this.senderType = senderType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}
