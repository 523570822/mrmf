package com.mrmf.entity.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 跨机构会员卡消费、续费对账表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserpartCrossOrgan extends DataEntity {

	private String incardId;// 会员卡id
	private String inincardId;// 会员子卡id
	private String userpartId; // 对应消费或充值的消费记录id
	private String organId;// 公司id
	private String ownerOrganId;// 办卡公司id
	private double amount;// 发生金额，跨机构消费金额，正数为应由办卡公司向消费公司支付（会员卡消费），负数相反（会员卡充值）
	private int status; // 消费公司处理状态，0：未处理；1：已处理
	private int ownerStatus; // 办卡公司处理状态，0：未处理；1：已处理
	private String memo; // 备注描述

	private String organName;
	private String ownerOrganName;
	
	private String statusName;
	private String ownerStatusName;
	
	

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getOwnerStatusName() {
		return ownerStatusName;
	}

	public void setOwnerStatusName(String ownerStatusName) {
		this.ownerStatusName = ownerStatusName;
	}

	public String getIncardId() {
		return incardId;
	}

	public void setIncardId(String incardId) {
		this.incardId = incardId;
	}

	public String getInincardId() {
		return inincardId;
	}

	public void setInincardId(String inincardId) {
		this.inincardId = inincardId;
	}

	public String getUserpartId() {
		return userpartId;
	}

	public void setUserpartId(String userpartId) {
		this.userpartId = userpartId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOwnerOrganId() {
		return ownerOrganId;
	}

	public void setOwnerOrganId(String ownerOrganId) {
		this.ownerOrganId = ownerOrganId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOwnerStatus() {
		return ownerStatus;
	}

	public void setOwnerStatus(int ownerStatus) {
		this.ownerStatus = ownerStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOwnerOrganName() {
		return ownerOrganName;
	}

	public void setOwnerOrganName(String ownerOrganName) {
		this.ownerOrganName = ownerOrganName;
	}

}
