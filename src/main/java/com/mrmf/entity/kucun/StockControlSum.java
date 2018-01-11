package com.mrmf.entity.kucun;

public class StockControlSum {
	private double inSum;// 总入库量
	private double inPriceSum;// 总入库量价格
	private double outSum;// 总出库量
	private double outPriceSum;// 总出库价格
	private double tuiHuoSum;// 退货数量
	private double tuiHuoPriceSum;// 退货价格

	public double getInSum() {
		return inSum;
	}

	public void setInSum(double inSum) {
		this.inSum = inSum;
	}

	public double getInPriceSum() {
		return inPriceSum;
	}

	public void setInPriceSum(double inPriceSum) {
		this.inPriceSum = inPriceSum;
	}

	public double getOutSum() {
		return outSum;
	}

	public void setOutSum(double outSum) {
		this.outSum = outSum;
	}

	public double getOutPriceSum() {
		return outPriceSum;
	}

	public void setOutPriceSum(double outPriceSum) {
		this.outPriceSum = outPriceSum;
	}

	public double getTuiHuoSum() {
		return tuiHuoSum;
	}

	public void setTuiHuoSum(double tuiHuoSum) {
		this.tuiHuoSum = tuiHuoSum;
	}

	public double getTuiHuoPriceSum() {
		return tuiHuoPriceSum;
	}

	public void setTuiHuoPriceSum(double tuiHuoPriceSum) {
		this.tuiHuoPriceSum = tuiHuoPriceSum;
	}

}
