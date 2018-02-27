package com.osg.entity;

import java.io.Serializable;

/**
 *   镜台设备编码
 */
public class AndroidPoint implements Serializable {

	private String devicedId; //设备唯一编码
	private String floor; //设备编号（A,B）
	private String name;//设备名称
	private String status;//镜台状态（0：开启中，1 关闭中）
	// 需要节目分类字段   稍后追加
	public String getStatus() {

		if (status == null || "".equals(status)) {
			this.status ="1";
		}else {
			this.status = status;
		}
		return status;
	}

	public void setStatus(String status) {

		if (status == null || "".equals(status)) {
			this.status ="1";
		}else {
			this.status = status;
		}

	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
