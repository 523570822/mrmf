package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 用户微信支付信息表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserPayOrder extends DataEntity {
	private String userId;// 用户id
	private String organId;// 店铺id
	private String staffId;//技师id
	private String sysCardId;// 平台在店铺的预存卡id
	private String organOrderId;// 订单id（对应WeOrganOrder表）
	private int isPayByScan;   //是否为扫码支付 0 否  1 是

	private double price;// 消费金额
	private double wePrice;//微信支付的金额
	private int state;// 状态 0：未支付；1：已支付；2：取消支付

	private String couponId;//我的优惠券的id
	private double expense;//折扣前需要支付的金额

	private double benefit;//优惠券抵扣金额

	private String incardId;// 充值的会员卡id
	private int type;// 类型 0：平台卡相关支付；1：会员卡充值支付；2：红包支付 3技师分帐模式

	// 以下为非存储字段
	private String userName;
	private String organOrderTitle; // 订单标题，对应WeOrganOrder表的title
	private String phone;//用户电话号码
	private String organName; //店铺名称

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public int getIsPayByScan() {
		return isPayByScan;
	}

	public void setIsPayByScan(int isPayByScan) {
		this.isPayByScan = isPayByScan;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getSysCardId() {
		return sysCardId;
	}

	public void setSysCardId(String sysCardId) {
		this.sysCardId = sysCardId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrganOrderId() {
		return organOrderId;
	}

	public void setOrganOrderId(String organOrderId) {
		this.organOrderId = organOrderId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganOrderTitle() {
		return organOrderTitle;
	}

	public void setOrganOrderTitle(String organOrderTitle) {
		this.organOrderTitle = organOrderTitle;
	}

	public String getIncardId() {
		return incardId;
	}

	public void setIncardId(String incardId) {
		this.incardId = incardId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getWePrice() {
		return wePrice;
	}

	public void setWePrice(double wePrice) {
		this.wePrice = wePrice;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public double getBenefit() {
		return benefit;
	}

	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}

}
