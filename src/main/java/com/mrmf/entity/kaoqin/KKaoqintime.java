package com.mrmf.entity.kaoqin;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KKaoqintime extends DataEntity {
	private String organId;  // 公司id
	private String staffId;  //技师的id
	private int type;   //1表示  表示考勤的时间戳           2表示 打卡次查询的时间戳
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getStaffId() {
		return staffId;
	}
	
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
