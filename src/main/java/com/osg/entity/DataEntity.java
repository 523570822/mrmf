package com.osg.entity;

import java.util.Date;

import com.osg.entity.util.EHDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.util.EHDateTimeSerializer;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DataEntity extends Entity {
	@JsonSerialize(using = EHDateSerializer.class)
	protected Date createTime;

	protected Date updateTime;

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setNewCreate() {
		this.createTime = new Date();
	}

	public void setCreateTimeIfNew() {
		if (this.createTime == null || "".equals(this.createTime)) {
			this.createTime = new Date();
		}
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateTimeIfNew() {
		if (updateTime == null || "".equals(updateTime)) {
			this.updateTime = new Date();
		}
	}

	public boolean isEmpty(String value) {
		if (value == null || "".equals(value) || "null".equals(value) || "undefiend".equals(value)) {
			return true;
		}
		return false;
	}
}
