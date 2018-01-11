package com.mrmf.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.framework.util.StringUtils;

/**
 * 功能信息 原fanmacell和contrcell表合并
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Function extends DataEntity {

	private String name; // 名称
	private String parentId; // 父节点id，0为根节点
	private String action; // 功能地址（如xxx.do），可为空（比如父级菜单节点）
	private int order; // 排序编码，从小到大排列
	private String code; // 标识代码，用于菜单图标显示
	private Boolean isZongbuOnly; // 是否仅总部可用的功能
	private Boolean isFenbuOnly; // 是否仅分部可用的功能

	// 非存储字段
	private List<Function> functionList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<Function> getFunctionList() {
		if (functionList == null)
			functionList = new ArrayList<>();
		// 菜单排序
		Collections.sort(functionList, new Comparator<Function>() {
			@Override
			public int compare(Function o1, Function o2) {
				if (o1.getOrder() == o2.getOrder())
					return 0;
				else
					return o1.getOrder() < o2.getOrder() ? -1 : 1;
			}
		});
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	public Boolean getIsZongbuOnly() {
		if (isZongbuOnly == null)
			return false;
		else
			return isZongbuOnly;
	}

	public void setIsZongbuOnly(Boolean isZongbuOnly) {
		this.isZongbuOnly = isZongbuOnly;
	}

	public Boolean getIsFenbuOnly() {
		if (isFenbuOnly == null)
			return false;
		else
			return isFenbuOnly;
	}

	public void setIsFenbuOnly(Boolean isFenbuOnly) {
		this.isFenbuOnly = isFenbuOnly;
	}

	public String getActionUrl() {
		if (!StringUtils.isEmpty(action)) {
			if (action.indexOf('?') != -1) {
				return action + "&_fid=" + _id;
			} else {
				return action + "?_fid=" + _id;
			}
		} else {
			return "";
		}
	}

}
