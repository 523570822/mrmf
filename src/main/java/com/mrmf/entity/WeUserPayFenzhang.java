package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 微信支付分账表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserPayFenzhang extends DataEntity {

	private String orderId;// 微信订单id

	private String userId;// 用户id
	private String organId;// 店铺id
	private String sysCardId;// 平台在店铺的预存卡id
	private double price;// 消费金额
	private double payWeixin; // 微信支付金额
	private double payWallet; // 钱包支付金额
	private double organAmount;// 店铺分账金额
	private double sysAmount;// 平台分账金额
	private double userAmount;// 用户返点金额
	private double organWalletAmount;//新增店铺红包分帐金额（与店铺分帐金额互斥）
	private double staffAmount;//新增技师分帐金额

	private int state;// 店铺分账处理状态，0-未处理；1-已处理

	private double cardMoney4;// 分账当时的店铺平台卡余额(本次消费之后)
	private int cardCount; // 分账当时的店铺平台卡消费次数(本次消费之后)
	private String city;// 城市，比如：北京
	private String district; // 区，比如：海淀区
	private String region; // 商圈，比如：回龙观
    //新增字段
	private double expense;//折扣前需要支付的金额=price+benefit


	private double benefit;//优惠券抵扣金额

	// 以下为非存储字段
	private String userName; // 用户名称
	private String stateName; // 状态名称
	private String organName;// 店铺名称

	private double prepayAmount;
	private double walletAmount;
	private double firstAmount;
	public double getExpense( ) {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public double getBenefit( ) {
		return benefit;
	}

	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}

	public double getStaffAmount( ) {
		return staffAmount;
	}

	public void setStaffAmount(double staffAmount) {
		this.staffAmount = staffAmount;
	}

	public double getOrganWalletAmount() {
		return organWalletAmount;
	}

	public void setOrganWalletAmount(double organWalletAmount) {
		this.organWalletAmount = organWalletAmount;
	}

	public double getPrepayAmount() {
		return prepayAmount;
	}

	public void setPrepayAmount(double prepayAmount) {
		this.prepayAmount = prepayAmount;
	}

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}

	public double getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(double firstAmount) {
		this.firstAmount = firstAmount;
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

	public double getPayWeixin() {
		return payWeixin;
	}

	public void setPayWeixin(double payWeixin) {
		this.payWeixin = payWeixin;
	}

	public double getPayWallet() {
		return payWallet;
	}

	public void setPayWallet(double payWallet) {
		this.payWallet = payWallet;
	}

	public double getOrganAmount() {
		return organAmount;
	}

	public void setOrganAmount(double organAmount) {
		this.organAmount = organAmount;
	}

	public double getSysAmount() {
		return sysAmount;
	}

	public void setSysAmount(double sysAmount) {
		this.sysAmount = sysAmount;
	}

	public double getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(double userAmount) {
		this.userAmount = userAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getCardMoney4() {
		return cardMoney4;
	}

	public void setCardMoney4(double cardMoney4) {
		this.cardMoney4 = cardMoney4;
	}

	public int getCardCount() {
		return cardCount;
	}

	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
