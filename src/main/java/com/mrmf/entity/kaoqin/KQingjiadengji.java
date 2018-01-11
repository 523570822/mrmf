package com.mrmf.entity.kaoqin;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 请假登记
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KQingjiadengji extends DataEntity {

	private String organId;// 公司id
	private String names;// 姓名（管理staff表id）
	private Date date1;// 请假开始日期（请假日期）
	private String type1;// 请假类型（关联kaoqinleibie表id）
	private Boolean delete_flag;// 是否删除，0：否，1：是
	private Date date3;// 结束日期
	private Boolean zao_flag;// 是否早班
	private Boolean wan_flag;// 是否晚班
	private String reason;// 请假理由
	private double money_koufa; //扣款金额
	//两个冗余字段存储  方便查询
	private String staffNames;// 技师的名字
	private String type1Names;// 请假类型名字
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public Boolean getZao_flag() {
		return zao_flag;
	}

	public void setZao_flag(Boolean zao_flag) {
		this.zao_flag = zao_flag;
	}

	public Boolean getWan_flag() {
		return wan_flag;
	}

	public void setWan_flag(Boolean wan_flag) {
		this.wan_flag = wan_flag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getMoney_koufa() {
		return money_koufa;
	}

	public void setMoney_koufa(double money_koufa) {
		this.money_koufa = money_koufa;
	}
	
	public String getStaffNames() {
		return staffNames;
	}

	public void setStaffNames(String staffNames) {
		this.staffNames = staffNames;
	}

	public String getType1Names() {
		return type1Names;
	}

	public void setType1Names(String type1Names) {
		this.type1Names = type1Names;
	}
}
