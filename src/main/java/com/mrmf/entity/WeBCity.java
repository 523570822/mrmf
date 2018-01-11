package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 城市
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeBCity extends DataEntity {

	private String name;// 城市名称
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

	public String getParentId() {
		return "0";
	}
	
	/**
	 * 城市类型
	 * 
	 * @return
	 */
	public int getType() {
		return 1;
	}

}
