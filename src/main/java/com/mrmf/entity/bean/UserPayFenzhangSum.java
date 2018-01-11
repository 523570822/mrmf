package com.mrmf.entity.bean;

import java.math.BigDecimal;

public class UserPayFenzhangSum {
	private double totalOrgan;
	private double totalSys;
	private double totalUser;
	private double total;

	private double cardRest; // 店铺平台卡余额

	public double getTotalOrgan() {
		return new BigDecimal(totalOrgan).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalOrgan(double totalOrgan) {
		this.totalOrgan = totalOrgan;
	}

	public double getTotal() {
		return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotalSys() {
		return new BigDecimal(totalSys).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalSys(double totalSys) {
		this.totalSys = totalSys;
	}

	public double getTotalUser() {
		return new BigDecimal(totalUser).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalUser(double totalUser) {
		this.totalUser = totalUser;
	}

	public double getCardRest() {
		return new BigDecimal(cardRest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCardRest(double cardRest) {
		this.cardRest = cardRest;
	}

}
