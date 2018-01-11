package com.mrmf.entity.bean;

import java.math.BigDecimal;

public class UserpartCrossOrganSum {
	private double totalAmount;

	public double getTotalAmount() {
		return new BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
