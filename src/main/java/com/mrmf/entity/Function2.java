package com.mrmf.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.framework.util.StringUtils;

/**
 * 平台功能信息 原fanmacell和contrcell表合并
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Function2 extends DataEntity {

	private String name; // 名称
	private String parentId; // 父节点id，0为根节点
	private String action; // 功能地址（如xxx.do），可为空（比如父级菜单节点）
	private int order; // 排序编码，从小到大排列
	private String code; // 标识代码，用于菜单图标显示

	// 非存储字段
	private List<Function2> functionList;

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

	public List<Function2> getFunctionList() {
		if (functionList == null)
			functionList = new ArrayList<>();
		// 菜单排序
		Collections.sort(functionList, new Comparator<Function2>() {
			@Override
			public int compare(Function2 o1, Function2 o2) {
				if (o1.getOrder() == o2.getOrder())
					return 0;
				else
					return o1.getOrder() < o2.getOrder() ? -1 : 1;
			}
		});
		return functionList;
	}

	public void setFunctionList(List<Function2> functionList) {
		this.functionList = functionList;
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
