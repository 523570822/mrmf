package com.mrmf.entity.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 会员卡电子卡号递增记录，每个公司有且只有一条记录，从1开始递增
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ElecCardNum extends DataEntity {

	private String organId;// 公司id
	private int seq; // 自增序列

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
