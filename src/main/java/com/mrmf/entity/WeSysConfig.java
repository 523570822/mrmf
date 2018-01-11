package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 系统业务参数表（业务参数随时可能扩充）
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeSysConfig extends DataEntity {

	private double allzhekou;// 平台预存现金统一店铺折扣(%)
	// private double allfandian;// 平台预存现金消费用户返点(%)
	private int man1;// 满30
	private int fan1;// 返4
	private int man2;// 满50
	private int fan2;// 返10
	private int man3;// 满100
	private int fan3;// 返30
	private double saoMaFan; //扫码返现

	public double getSaoMaFan() {
		return saoMaFan;
	}

	public void setSaoMaFan(double saoMaFan) {
		this.saoMaFan = saoMaFan;
	}

	private String phone; // 平台运营者手机号

	public double getAllzhekou() {
		return allzhekou;
	}

	public void setAllzhekou(double allzhekou) {
		this.allzhekou = allzhekou;
	}

	public int getMan1() {
		return man1;
	}

	public void setMan1(int man1) {
		this.man1 = man1;
	}

	public int getFan1() {
		return fan1;
	}

	public void setFan1(int fan1) {
		this.fan1 = fan1;
	}

	public int getMan2() {
		return man2;
	}

	public void setMan2(int man2) {
		this.man2 = man2;
	}

	public int getFan2() {
		return fan2;
	}

	public void setFan2(int fan2) {
		this.fan2 = fan2;
	}

	public int getMan3() {
		return man3;
	}

	public void setMan3(int man3) {
		this.man3 = man3;
	}

	public int getFan3() {
		return fan3;
	}

	public void setFan3(int fan3) {
		this.fan3 = fan3;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
