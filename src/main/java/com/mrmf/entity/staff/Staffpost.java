package com.mrmf.entity.staff;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 岗位表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Staffpost extends DataEntity {

	private String organId; // 公司id
	private String name;// 岗位名称

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

}
