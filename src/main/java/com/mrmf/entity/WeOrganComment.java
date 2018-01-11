package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 店铺用户评价
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeOrganComment extends DataEntity {

	private String orderId;// 评价对应的预约id
	private String userId;// 用户id
	private String organId;// 店铺id
	private String staffId;// 技师id（如果为预约技师服务时存在此id）
	private String content;// 评价内容
	private int starZan;// 赞星级，最高5星
	private int starQiu;// 糗星级，最高5星
	private int starFaceScore;// 颜值星级，最高5星（预约技师时有效）
	
	//非存储字段
	private String userName;
	private String userImg;
	private String commentTime;
	private String organName;
	private String staffName;
	
	
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStarZan() {
		return starZan;
	}

	public void setStarZan(int starZan) {
		this.starZan = starZan;
	}

	public int getStarQiu() {
		return starQiu;
	}

	public void setStarQiu(int starQiu) {
		this.starQiu = starQiu;
	}

	public int getStarFaceScore() {
		return starFaceScore;
	}

	public void setStarFaceScore(int starFaceScore) {
		this.starFaceScore = starFaceScore;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	
}
