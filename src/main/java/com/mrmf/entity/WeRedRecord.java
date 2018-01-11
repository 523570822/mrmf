package com.mrmf.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 技师红包领取记录
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeRedRecord extends DataEntity {

	private String redId;// 红包id
	private String userId;// 抢到红包用户id
	private String userNick;// 抢到红包用户昵称（冗余存储）
	private String userAvatar;// 抢到红包用户头像（冗余存储）
	private double amount;// 抢到红包金额
	private int day; // 红包领取日期，格式yyyyMMdd的int

	
	public String getRedId() {
		return redId;
	}

	public void setRedId(String redId) {
		this.redId = redId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	@Override
	public String toString() {
		return "WeRedRecord [redId=" + redId + ", userId=" + userId
				+ ", userNick=" + userNick + ", userAvatar=" + userAvatar
				+ ", amount=" + amount + ", day=" + day +  "]";
	}
	
}
