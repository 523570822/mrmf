package com.mrmf.entity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;
import com.osg.entity.geo.GeoDistance;

/**
 * 用户询价记录
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserInquiry extends DataEntity implements GeoDistance {

	private String userId;// 询价用户id
	private String type;// 询价类型/项目，比如：男士剪发
	private String desc;// 询价描述
	private List<String> images;// 描述图片列表
	private String quoteId;// 采纳的报价id
	private String orderId;// 生成的订单/预约id
	private GpsPoint gpsPoint; // 用户发布询价时所在的gps位置
	// createTime 询价时间
	// updateTime 接受报价并做预约的时间
	
	private String createTimeFormat;//格式化时间
	// 非存储字段
	private double distance; // 距离，单位：公里

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

}
