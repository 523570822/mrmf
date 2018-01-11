package com.mrmf.entity.kucun;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 品牌信息表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WPinpai extends DataEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String organId;//公司id（总公司id）
	private String name;//品牌名称
	private String zjfCode;//助记符
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
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
	
	

}
