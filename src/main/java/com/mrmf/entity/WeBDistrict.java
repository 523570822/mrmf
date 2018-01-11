package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 区域
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeBDistrict extends DataEntity {

	private String name;// 区域名称
	private String cityId; // 所属城市id
	private int order; // 排序编码，从小到大排列

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getParentId() {
		return cityId;
	}
	
	/**
	 * 区域类型
	 * 
	 * @return
	 */
	public int getType() {
		return 2;
	}

}
