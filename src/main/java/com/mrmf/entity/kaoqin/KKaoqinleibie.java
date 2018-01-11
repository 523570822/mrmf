package com.mrmf.entity.kaoqin;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 考勤类别
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KKaoqinleibie extends DataEntity {
	private String organId;// 公司id
	private String names;// 定义名称，如：迟到1分钟、早退1分钟、旷工1天、病假、休假、事假
	private int code;// 0-请假类型，1-迟到，2-早退，3-旷工 
	private String note;// 备注
	private Boolean delete_flag;// 是否删除，0：否，1：是
	private int base1;// 基数1（分钟）
	private double money1;// 扣罚1（元）
	private int base2;// 基数2（分钟）
	private double money2;// 扣罚2（元）
	private int base3;// 基数3（分钟）
	private double money3;// 一次性扣罚（元）
	private Boolean guding_flag;// 是否一次性扣款（按分钟扣款）

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public int getBase1() {
		return base1;
	}

	public void setBase1(int base1) {
		this.base1 = base1;
	}

	public double getMoney1() {
		return money1;
	}

	public void setMoney1(double money1) {
		this.money1 = money1;
	}

	public int getBase2() {
		return base2;
	}

	public void setBase2(int base2) {
		this.base2 = base2;
	}

	public double getMoney2() {
		return money2;
	}

	public void setMoney2(double money2) {
		this.money2 = money2;
	}

	public int getBase3() {
		return base3;
	}

	public void setBase3(int base3) {
		this.base3 = base3;
	}

	public double getMoney3() {
		return money3;
	}

	public void setMoney3(double money3) {
		this.money3 = money3;
	}

	public Boolean getGuding_flag() {
		return guding_flag;
	}

	public void setGuding_flag(Boolean guding_flag) {
		this.guding_flag = guding_flag;
	}

}
