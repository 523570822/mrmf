package com.mrmf.entity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;
import com.osg.entity.geo.GeoDistance;

/**
 * 技师典型案例
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeStaffCase extends DataEntity implements GeoDistance {

	private String staffId;// 技师id
	private String type; // 案例类型，美容、美发、美足、美甲等
	private String realType; // 用于存储 hairType-发型；meiJiaType 美甲 meiRongType 美容 //
								// zuliaoType 足疗

	private String title; // 标题
	private String desc; // 案例介绍
	private List<String> logo; // 案例主图
	private double price; // 案例服务价格
	private int consumeTime; // 服务预计耗时（单位：分钟）
	private int followCount; // 关注/收藏数量
	private Boolean weixin;// 是否微信端显示，同步自技师staff的weixin
	private String city; // 所在城市（技师主公司city）
	private GpsPoint gpsPoint; // 所在位置（技师主公司gpsPoint）

	// 非存储字段
	private double distance; // 距离，单位：公里
	private String staffName;//技师名称
	
	

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/** ? 缺少赞的 */

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getLogo() {
		if (logo == null)
			logo = new ArrayList<>();
		return logo;
	}

	public void setLogo(List<String> logo) {
		this.logo = logo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(int consumeTime) {
		this.consumeTime = consumeTime;
	}

	public int getFollowCount() {
		return followCount;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	public String getRealType() {
		return realType;
	}

	public void setRealType(String realType) {
		this.realType = realType;
	}

	public Boolean getWeixin() {
		if (weixin == null)
			return false;
		else
			return weixin;
	}

	public void setWeixin(Boolean weixin) {
		this.weixin = weixin;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	@Override
	public String toString() {
		return "WeStaffCase [staffId=" + staffId + ", type=" + type + ", title=" + title + ", desc=" + desc + ", logo="
				+ logo + ", price=" + price + ", consumeTime=" + consumeTime + ", followCount=" + followCount + "]";
	}
}
