package com.mrmf.entity.bean;

public class KuncunAlert {
	private String type;  //物品类别
	private double number; //现在数量;
	private double alertNumber;//预警数量 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getNumber() {
		return number;
	}
	public void setNumber(double number) {
		this.number = number;
	}
	public double getAlertNumber() {
		return alertNumber;
	}
	public void setAlertNumber(double alertNumber) {
		this.alertNumber = alertNumber;
	}
}
