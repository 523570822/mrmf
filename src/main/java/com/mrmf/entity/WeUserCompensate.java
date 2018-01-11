package com.mrmf.entity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 赔付记录
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserCompensate extends DataEntity {

	private String userId;// 赔付发起用户id
	private String orderId;// 订单id
	private String service;// 赔付项目，如美发
	private String providerId;// 店铺/技师id
	private String providerName;// 店铺/技师名称（冗余存储）
	private String type;// 赔付类型，如服务态度类投诉
	private String desc;// 赔付描述
	private List<String> images;// 赔付描述图片列表
	private int state;// 赔付处理状态,0:待处理;1:已处理
	private String result;// 处理结果，如店铺退钱，重新免费服务一次，用户责任等
	private String resultDesc;// 处理结果描述
	private String phone;      //赔付用户的电话
	// createTime 赔付发起时间

	private String target;//赔付者类型(1店铺 2 技师)
	private String logo;//店铺/技师logo
	private String timeFormat;//时间格式化
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getImages() {
		if (images == null)
			images = new ArrayList<>();
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
