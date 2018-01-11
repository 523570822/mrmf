package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 用户意见反馈
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserFeedback extends DataEntity {

	private String userId;// 用户id
	private String desc;// 反馈意见描述
	private String contact;// 联系方式
	private String userName;// 反馈用户姓名
	private String type; // 反馈用户类型，user-用户;staff-技师;organ-店铺
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
