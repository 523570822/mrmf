package com.mrmf.entity.staff;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 员工提成设置/分配业绩
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StaffTicheng extends DataEntity {

	private String organId; // 公司id
	private String smallsort;// 服务项目
	private String staffpost;// 岗位
	private double tichengCashPercent;// 现金提成（%）
	private double tichengCash;// 现金固定提成（元）
	private double tichengCardPercent;// 卡提成（%）
	private double tichengCard;// 卡固顶提成（元）
	private double yeji;// 分业绩
	private Boolean float_flag;// 浮动标记(true/false)
	private Boolean removeAmount;// 去除固定金额计算提成(true/false)

	// 非存储字段
	private String smallsortName; // 服务项目名称
	private String staffpostName;// 岗位名称

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getSmallsort() {
		return smallsort;
	}

	public void setSmallsort(String smallsort) {
		this.smallsort = smallsort;
	}

	public double getTichengCashPercent() {
		return tichengCashPercent;
	}

	public void setTichengCashPercent(double tichengCashPercent) {
		this.tichengCashPercent = tichengCashPercent;
	}

	public double getTichengCash() {
		return tichengCash;
	}

	public void setTichengCash(double tichengCash) {
		this.tichengCash = tichengCash;
	}

	public double getTichengCardPercent() {
		return tichengCardPercent;
	}

	public void setTichengCardPercent(double tichengCardPercent) {
		this.tichengCardPercent = tichengCardPercent;
	}

	public double getTichengCard() {
		return tichengCard;
	}

	public void setTichengCard(double tichengCard) {
		this.tichengCard = tichengCard;
	}

	public double getYeji() {
		return yeji;
	}

	public void setYeji(double yeji) {
		this.yeji = yeji;
	}

	public Boolean getFloat_flag() {
		return float_flag;
	}

	public void setFloat_flag(Boolean float_flag) {
		this.float_flag = float_flag;
	}

	public Boolean getRemoveAmount() {
		return removeAmount;
	}

	public void setRemoveAmount(Boolean removeAmount) {
		this.removeAmount = removeAmount;
	}

	public String getSmallsortName() {
		return smallsortName;
	}

	public void setSmallsortName(String smallsortName) {
		this.smallsortName = smallsortName;
	}

	public String getStaffpost() {
		return staffpost;
	}

	public void setStaffpost(String staffpost) {
		this.staffpost = staffpost;
	}

	public String getStaffpostName() {
		return staffpostName;
	}

	public void setStaffpostName(String staffpostName) {
		this.staffpostName = staffpostName;
	}

}
