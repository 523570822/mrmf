package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 部门信息
 * 原bumen表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Bumen extends DataEntity {

	private String name; // 名称
	private String organId; // 所属公司id
	private Boolean deleteFlag; // 是否删除
	private Boolean outputFlag; // 是否是否导出数据
	private Boolean flag; // 统计报表是否展示

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Boolean getOutputFlag() {
		return outputFlag;
	}

	public void setOutputFlag(Boolean outputFlag) {
		this.outputFlag = outputFlag;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
