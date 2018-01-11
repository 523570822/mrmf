package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;

/**
 * 技师签到明细
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeStaffSign extends DataEntity {

	private String staffId;// 技师id
	private GpsPoint gpsPoint;// 签到GPS定位经纬度坐标
	private String organId;// 签到店铺id
	private String organName;// 签到店铺名称（冗余存储）
	
	private String createTimeFormat;//格式化时间

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}
}
