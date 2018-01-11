package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 技师签约审核意见表
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeOrganStaffVerify extends DataEntity {

	private String organId; // 店铺id
	private String organName; // 店铺名称（冗余存储）
	private String staffId; // 技师id
	private String staffName; // 技师姓名（冗余存储）
	private int state; // 审核状态；0:未审核；1:通过，-1:不通过   -2:解约了的店铺
	private String memo; // 审核意见

	//非存储字段
	private String logo;//店铺logo
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
