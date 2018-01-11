package com.mrmf.entity.bean;

import java.math.BigDecimal;

public class CardPayOnlineSum {

	private String _id;
	private double total;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public double getTotal() {
		return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
