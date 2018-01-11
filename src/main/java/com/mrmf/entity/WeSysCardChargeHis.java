package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 店铺平台卡充值记录
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeSysCardChargeHis extends DataEntity {

	private String organId; // 店铺id
	private double amount; // 充值金额

	
	//非存储字段
	private String organName; // 店铺名称
	
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
