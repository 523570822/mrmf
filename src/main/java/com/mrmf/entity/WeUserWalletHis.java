package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 钱包变动记录，用户钱包变动记录，充值 提现 消费 收益等
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserWalletHis extends DataEntity {

	private String userId;// 用户id
	private double amount; // 金额（充值为正数，消费为负数）
	private String desc; // 描述（如充值，提现）
	private String orderId; // 如果为消费，则存储对应的weOrganOrder表订单id,
	// 0 用户打赏技师 1为用户提现，2为技师提现 3 用户消费返现 4 用户打赏
	// 10：店铺用户微信支付提成；11：店铺钱包提现 12：技师微信支付提成

	private String cardId;// 会员卡id
	private String sendUserId;// 转增

	// 非存储字段
	private String createTimeFormart;
	private String userName; // 用户名称
	private String userPhone; // 用户电话

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreateTimeFormart() {
		return createTimeFormart;
	}

	public void setCreateTimeFormart(String createTimeFormart) {
		this.createTimeFormart = createTimeFormart;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}
