package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 通用代码表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Code extends DataEntity {

	private String type; // 代码类型,如：hairType-发型；meiJiaType-美甲 meiRongType-美容
							// zuLiaoType 足疗 compensateType-赔付类型等
	private String name;// 代码类型下项目名称
	private String zjfCode;// 助记符
	private int orderCode; // 显示顺序，从大到小倒序排序

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	public int getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

	@Override
	public String toString() {
		return "Code [type=" + type + ", name=" + name + ", orderCode=" + orderCode + "]";
	}

}
