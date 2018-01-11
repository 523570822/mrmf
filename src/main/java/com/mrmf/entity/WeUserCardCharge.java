package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 会员卡微信充值记录
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserCardCharge extends DataEntity {
	private String userId;
	private String organId;
	private String incardId;// 会员卡id
	private double money1;// 充值金额
	private int state;// 平台处理状态：0，已支付；1，已处理
	private int organState;// 店铺处理状态 0:已支付1:已处理

	private String organName;
	private String userName;
	private String bankAccountName; // 账户名称
	private String bankKaihu; // 开户行
	private String bankAccount; // 银行账号

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOrganState() {
		return organState;
	}

	public void setOrganState(int organState) {
		this.organState = organState;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getIncardId() {
		return incardId;
	}

	public void setIncardId(String incardId) {
		this.incardId = incardId;
	}

	public double getMoney1() {
		return money1;
	}

	public void setMoney1(double money1) {
		this.money1 = money1;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankKaihu() {
		return bankKaihu;
	}

	public void setBankKaihu(String bankKaihu) {
		this.bankKaihu = bankKaihu;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

}
