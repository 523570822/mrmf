package com.mrmf.entity.staff;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 浮动提成表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StaffFloatTicheng extends DataEntity {

	private String organId; // 公司id
	private int floatType; // 浮动类别, 0-业绩分段固定提成；1-业绩最高额取最高提成
	private double yeji1;// 业绩1
	private double yeji2;// 业绩2
	private double ticheng;// 提成（%）
	// private String tichengType;// 提成类别, floatTichengType

	// 非存储字段
	private String floatTypeName; // 浮动类别名称
	// private String tichengTypeName; // 提成类别名称

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public double getYeji1() {
		return yeji1;
	}

	public void setYeji1(double yeji1) {
		this.yeji1 = yeji1;
	}

	public double getYeji2() {
		return yeji2;
	}

	public void setYeji2(double yeji2) {
		this.yeji2 = yeji2;
	}

	public double getTicheng() {
		return ticheng;
	}

	public void setTicheng(double ticheng) {
		this.ticheng = ticheng;
	}

	public int getFloatType() {
		return floatType;
	}

	public void setFloatType(int floatType) {
		this.floatType = floatType;
	}

	public String getFloatTypeName() {
		return floatTypeName;
	}

	public void setFloatTypeName(String floatTypeName) {
		this.floatTypeName = floatTypeName;
	}

}
