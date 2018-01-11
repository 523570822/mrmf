package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

import java.util.Date;

/**
 * 店铺预约/订单
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeOrganOrder extends DataEntity {

	private String userId; // 用户id
	private String organId;// 店铺id
	private int type;// 预约/订单类型：1:店铺;2:技师;3:询价预约
	private String staffId;// 技师id（如果为预约技师时存在此id）
	private String orderService;// 预约项目（type为2时为技师典型案例id，3时为询价id）
	private double orderPrice;// 订单金额
	private String orderTime;// 预约时间 datetime
	private int state;// 预约状态，1:已预约;2:已确认;3:已支付;10:已评价(已完成);-1:已拒绝;0：已取消
	private String rejectReason;// 拒绝原因
	private String title; //订单的服务类型

	private String payTime; //订单支付时间
	
	//后台使用
	private String organName;//店铺名称
	private String staffName;//技师名称
	private String serviveName;//预约项目名称
	//非存储字段
	private String organLogo;
	private String staffLogo;
	private String organAddress;
	private String userNick;
	private String userImg;
	private String userName;
	private String userPhone;
	private String userType;
	private int starZan;
	private String content;//评价内容
	private boolean isFirstOrder;
	private boolean isMember;
	private String orderTimeFormat;
	private String serverType;//服务类型(1美发 2美容 3美甲 4美足);

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getStarZan() {
		return starZan;
	}

	public void setStarZan(int starZan) {
		this.starZan = starZan;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganLogo() {
		return organLogo;
	}

	public void setOrganLogo(String organLogo) {
		this.organLogo = organLogo;
	}

	public String getOrganAddress() {
		return organAddress;
	}

	public void setOrganAddress(String organAddress) {
		this.organAddress = organAddress;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getOrderService() {
		return orderService;
	}

	public void setOrderService(String orderService) {
		this.orderService = orderService;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrderTimeFormat() {
		return orderTimeFormat;
	}

	public void setOrderTimeFormat(String orderTimeFormat) {
		this.orderTimeFormat = orderTimeFormat;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getServiveName() {
		return serviveName;
	}

	public void setServiveName(String serviveName) {
		this.serviveName = serviveName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean getIsFirstOrder() {
		return isFirstOrder;
	}

	public void setIsFirstOrder(boolean isFirstOrder) {
		this.isFirstOrder = isFirstOrder;
	}

	public boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(boolean isMember) {
		this.isMember = isMember;
	}

	public String getStaffLogo() {
		return staffLogo;
	}

	public void setStaffLogo(String staffLogo) {
		this.staffLogo = staffLogo;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}	
	
	
}
