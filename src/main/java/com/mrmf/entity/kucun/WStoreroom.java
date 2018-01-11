package com.mrmf.entity.kucun;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 库存表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WStoreroom extends DataEntity {
	private String parentOrganId;// 上级公司id，0为总公司
	private String organId;// 公司id
	private String wupinId;// 物品类别id
	private String mingcheng;// 产品名称
	private double price;// 单价
	private double num;// 数量
	private double price_all;// 总价
	private String note;// 备注
	private Boolean flag;// 外卖出库
	private double weight;// 每瓶净含量
	private double weight_all;// 总量
	private String bumen;// 部门
	private String zjfCode;// 助记符
	private int jingjie;// 报警数量
	private Date useful_life;// 有效期
	private int jingjiedate;// 报警日期(有效期提前几天报警)
	private String pinpai;// 品牌
	private String guige;// 规格
	private String id2;// 子卡号
	
	//非存储字段
	private String bumenName;//部门名称
	private String pinpaiName;//品牌 名称
	private String wupinName;//物品类别
	
	
	
	
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

	public String getPinpaiName() {
		return pinpaiName;
	}

	public void setPinpaiName(String pinpaiName) {
		this.pinpaiName = pinpaiName;
	}

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

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	public int getJingjie() {
		return jingjie;
	}

	public void setJingjie(int jingjie) {
		this.jingjie = jingjie;
	}
	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getUseful_life() {
		return useful_life;
	}

	public void setUseful_life(Date useful_life) {
		this.useful_life = useful_life;
	}

	public int getJingjiedate() {
		return jingjiedate;
	}

	public void setJingjiedate(int jingjiedate) {
		this.jingjiedate = jingjiedate;
	}

	public String getPinpai() {
		return pinpai;
	}

	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

}
