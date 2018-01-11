package com.mrmf.entity.bean;

import java.math.BigDecimal;

import com.osg.entity.DataEntity;

public class StaffRank extends DataEntity {
	private double totalPrice;

	private String staffId;

	private String staffName;
	private String organName;
	
	private int orderNum;//存放技师超过30元订单的个数

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public double getTotalPrice() {
		return new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

}
