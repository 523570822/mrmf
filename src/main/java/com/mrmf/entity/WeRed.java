package com.mrmf.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;
import com.osg.entity.geo.GeoDistance;

/**
 * 发送红包
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeRed extends DataEntity implements GeoDistance {

	private int type;// 红包类型，1:平台红包;2:技师红包
	private String senderId;// type=2时为技师id  系统红包 0
	private String organId;// 企业id，当type=2时，为技师所在店铺id
	private int scope;// 发送范围，1.所有技师服务过的会员；2.所有关注过技师的客户；3.全部消费过的用户
	private GpsPoint gpsPoint;// 红包发送的gps定位，搜索范围内用户可领取红包
	private int range;// 红包辐射范围，单位：公里。-1为不限制，默认10
	private int count;// 红包个数
	private int restCount; //红包剩余个数
	private double amount; //总金额
	private double restAmount;//剩余金额
	private String desc;// 红包描述
	private int state; // 红包状态，0：无效（未支付）；1:进行中；2:已结束
	private List<Double> smallReds;  //将红包分成的份额

	// 非存储字段
	private double distance; // 距离，单位：公里
	private String staffName;  //技师名称
	private String organName;  //店铺名称

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getRestCount() {
		return restCount;
	}

	public void setRestCount(int restCount) {
		this.restCount = restCount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(double restAmount) {
		this.restAmount = restAmount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public List<Double> getSmallReds() {
		return smallReds;
	}
	public void setSmallReds(List<Double> smallReds) {
		this.smallReds = smallReds;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
}
