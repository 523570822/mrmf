package com.mrmf.entity.kucun;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 退货表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WStoretuihuo extends DataEntity {
	private String parentOrganId;// 上级公司id，0为总公司
	private String organId;// 公司id
	private String code;// 产品编码（条码号）
	private String wupinId;// 物品类别id
	private String mingcheng;// 产品名称
	private double price;// 单价
	private double num;// 数量
	private double price_all;// 总价
	private String note;// 备注
	private String staff;// 退货人
	private Boolean flag;// 外卖出库
	private double weight;// 每瓶净含量
	private double weight_all;// 总量
	private String bumen;// 部门
	
	//非存储字段
	private String wupinName;//物品名称
	private String bumenName;//部门名称
	private String flagName;//外卖出库显示

	public String getParentOrganId() {
		return parentOrganId;
	}

	public void setParentOrganId(String parentOrganId) {
		this.parentOrganId = parentOrganId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWupinId() {
		return wupinId;
	}

	public void setWupinId(String wupinId) {
		this.wupinId = wupinId;
	}

	public String getMingcheng() {
		return mingcheng;
	}

	public void setMingcheng(String mingcheng) {
		this.mingcheng = mingcheng;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getPrice_all() {
		return price_all;
	}

	public void setPrice_all(double price_all) {
		this.price_all = price_all;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight_all() {
		return weight_all;
	}

	public void setWeight_all(double weight_all) {
		this.weight_all = weight_all;
	}

	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}

	public String getWupinName() {
		return wupinName;
	}

	public void setWupinName(String wupinName) {
		this.wupinName = wupinName;
	}

	public String getBumenName() {
		return bumenName;
	}

	public void setBumenName(String bumenName) {
		this.bumenName = bumenName;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

}
