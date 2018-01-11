package com.mrmf.entity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 平台角色信息 原founction_grou表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Role2 extends DataEntity {

	private String name; // 名称
	private List<String> functionIds; // 关联的功能id列表

	// 非存储字段
	private List<String> functionNames; // 关联的功能名称列表

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
