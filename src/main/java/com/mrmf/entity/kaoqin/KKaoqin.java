package com.mrmf.entity.kaoqin;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 考勤记录表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KKaoqin extends DataEntity {
	private String organId;// 公司id
	private String staffId;// 员工id
	private String staffName;// 员工姓名
	private String leibieId;// 考勤类别id
	private String leibieName;// 考勤类别名称
	private int card;// 迟到时间（分钟）
	private int code;// 考勤状态,1迟到，2早退，3旷工
	private double money_koufa;// 罚款
	private Date day;   //那天的考勤记录
	
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

	public String getLeibieId() {
		return leibieId;
	}

	public void setLeibieId(String leibieId) {
		this.leibieId = leibieId;
	}

	public String getLeibieName() {
		return leibieName;
	}

	public void setLeibieName(String leibieName) {
		this.leibieName = leibieName;
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public double getMoney_koufa() {
		return money_koufa;
	}

	public void setMoney_koufa(double money_koufa) {
		this.money_koufa = money_koufa;
	}
	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
}
