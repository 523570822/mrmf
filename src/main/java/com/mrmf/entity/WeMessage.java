package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 通用消息表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeMessage extends DataEntity {

	private String fromType; // 消息发送方类型，user-用户;staff-技师;organ-店铺;rxm-平台
	private String fromId; // 消息发送方id;平台ID：0
	private String fromName; // 消息发送方姓名 平台：任性猫平台
	private String toType; // 消息接收方类型，user-用户;staff-技师;organ-店铺
	private String toId; // 消息接收方id
	private String toName; // 消息接收方姓名
	private String type; // 消息类型(1:普通消息；2：系统消息)
	private String content; // 消息内容
	private Boolean readFalg;//是否读取消息
	
	private String createTimeFormat;//时间格式化

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public Boolean getReadFalg() {
		if(readFalg==null)
			return false;
		return readFalg;
	}

	public void setReadFalg(Boolean readFalg) {
		this.readFalg = readFalg;
	}
	
}
