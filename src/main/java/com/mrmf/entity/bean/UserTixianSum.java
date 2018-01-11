package com.mrmf.entity.bean;

import java.math.BigDecimal;

public class UserTixianSum {
	private double total;

	public double getTotal() {
		return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
