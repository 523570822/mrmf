package com.mrmf.entity.kaoqin;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 工资表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KPay extends DataEntity {
	private String organId;// 公司id
	private String staffId;// 员工id
	private String staffName;// 员工名称
	private double base_pay;// 基本工资
	private double prize_pay;// 奖金
	private double benefits_pay;// 考勤工资
	private double add_pay;// 提成工资
	private double deduct_pay;// 扣除额
	private double all_pay;// 应得工资
	private Date pay_date;// 支付日期
	private Boolean delte_flag;// 是否删除
	private String flag1;// 签名

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public double getBase_pay() {
		return base_pay;
	}

	public void setBase_pay(double base_pay) {
		this.base_pay = base_pay;
	}

	public double getPrize_pay() {
		return prize_pay;
	}

	public void setPrize_pay(double prize_pay) {
		this.prize_pay = prize_pay;
	}

	public double getBenefits_pay() {
		return benefits_pay;
	}

	public void setBenefits_pay(double benefits_pay) {
		this.benefits_pay = benefits_pay;
	}

	public double getAdd_pay() {
		return add_pay;
	}

	public void setAdd_pay(double add_pay) {
		this.add_pay = add_pay;
	}

	public double getDeduct_pay() {
		return deduct_pay;
	}

	public void setDeduct_pay(double deduct_pay) {
		this.deduct_pay = deduct_pay;
	}

	public double getAll_pay() {
		return all_pay;
	}

	public void setAll_pay(double all_pay) {
		this.all_pay = all_pay;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Boolean getDelte_flag() {
		return delte_flag;
	}

	public void setDelte_flag(Boolean delte_flag) {
		this.delte_flag = delte_flag;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

}
