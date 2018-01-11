package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;

/**
 * 商圈
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeBRegion extends DataEntity {

	private String name;// 商圈名称
	private String cityId; // 所属城市id
	private String districtId; // 所属区域id
	private GpsPoint gpsPoint; // 商圈gps定位
	private int order; // 排序编码，从小到大排列

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public String getParentId() {
		return districtId;
	}
	
	/**
	 * 商圈类型
	 * 
	 * @return
	 */
	public int getType() {
		return 3;
	}

}
