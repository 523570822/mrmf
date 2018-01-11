package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 消息表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Message extends DataEntity {

	private String key; // 消息发送key，公司为公司id，平台为0
	private String content; // 消息内容
	private String url;// 消息处理url地址，可为空
	private int state; // 0:未读;1:已读
	// createTime 消息发生时间

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
