package com.osg.entity;

import java.io.Serializable;

/**
 *   镜台设备编码
 */
public class AndroidPoint implements Serializable {

	private String devicedId; //设备唯一编码
	private String floor; //设备编号（A,B）

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDevicedId() {
		return devicedId;
	}

	public void setDevicedId(String devicedId) {
		this.devicedId = devicedId;
	}
}
