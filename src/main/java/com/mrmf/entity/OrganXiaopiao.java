package com.mrmf.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 店面小票表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrganXiaopiao extends DataEntity {
	private String organId;// 公司id
	private String checksum; // 校验码，根据打印小票的userpart id排序后做md5，保证相同小票打印一致性
	private List<String> userparts; // 小票包含哪些消费记录
	private int code; // 打印码，每日从1开始
	private int date; // 日期码，格式yyyymm

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public List<String> getUserparts() {
		return userparts;
	}

	public void setUserparts(List<String> userparts) {
		this.userparts = userparts;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

}
