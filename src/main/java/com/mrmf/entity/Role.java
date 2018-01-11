package com.mrmf.entity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 角色信息 原founction_grou表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Role extends DataEntity {

	private String name; // 名称
	private String organId; // 所属公司id，0为系统内置角色
	private List<String> functionIds; // 关联的功能id列表
	private Boolean jiage; // 是否可以更改价格
	private Boolean jiezhang; // 是否可以结账
	private Boolean tiqu; // 是否可以提取现金
	private Boolean zika; // 是否可以创建子卡

	// 非存储字段
	private List<String> functionNames; // 关联的功能名称列表

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

	public List<String> getFunctionIds() {
		if (functionIds == null)
			functionIds = new ArrayList<>();
		return functionIds;
	}

	public void setFunctionIds(List<String> functionIds) {
		this.functionIds = functionIds;
	}

	public List<String> getFunctionNames() {
		if (functionNames == null)
			functionNames = new ArrayList<>();
		return functionNames;
	}

	public void setFunctionNames(List<String> functionNames) {
		this.functionNames = functionNames;
	}

	public Boolean getJiage() {
		return jiage;
	}

	public void setJiage(Boolean jiage) {
		this.jiage = jiage;
	}

	public Boolean getJiezhang() {
		return jiezhang;
	}

	public void setJiezhang(Boolean jiezhang) {
		this.jiezhang = jiezhang;
	}

	public Boolean getTiqu() {
		return tiqu;
	}

	public void setTiqu(Boolean tiqu) {
		this.tiqu = tiqu;
	}

	public Boolean getZika() {
		return zika;
	}

	public void setZika(Boolean zika) {
		this.zika = zika;
	}

}
