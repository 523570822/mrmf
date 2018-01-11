package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;

/**
 * 技师日程
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeStaffCalendar extends DataEntity implements Cloneable {
	private String staffId;// 技师id
	private String organId;//服务店铺id
	
	private int day;// 设置天，格式yyyyMMdd
	private boolean time0; // 时间00:00
	private boolean time1; // 时间01:00
	private boolean time2; // 时间02:00
	private boolean time3; // 时间03:00
	private boolean time4; // 时间04:00
	private boolean time5; // 时间05:00
	private boolean time6; // 时间06:00
	private boolean time7; // 时间07:00
	private boolean time8; // 时间08:00
	private boolean time9; // 时间09:00
	private boolean time10; // 时间10:00
	private boolean time11; // 时间11:00
	private boolean time12; // 时间12:00
	private boolean time13; // 时间13:00
	private boolean time14; // 时间14:00
	private boolean time15; // 时间15:00
	private boolean time16; // 时间16:00
	private boolean time17; // 时间17:00
	private boolean time18; // 时间18:00
	private boolean time19; // 时间19:00
	private boolean time20; // 时间20:00
	private boolean time21; // 时间21:00
	private boolean time22; // 时间22:00
	private boolean time23; // 时间23:00
	
	//非存储字段
	private String organName;  //  店铺的名字
	private String monthDay;   //  存储月日
	private String week;      //   星期几
	
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getMonthDay() {
		return monthDay;
	}
	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isTime0() {
		return time0;
	}

	public void setTime0(boolean time0) {
		this.time0 = time0;
	}

	public boolean isTime1() {
		return time1;
	}

	public void setTime1(boolean time1) {
		this.time1 = time1;
	}

	public boolean isTime2() {
		return time2;
	}

	public void setTime2(boolean time2) {
		this.time2 = time2;
	}

	public boolean isTime3() {
		return time3;
	}

	public void setTime3(boolean time3) {
		this.time3 = time3;
	}

	public boolean isTime4() {
		return time4;
	}

	public void setTime4(boolean time4) {
		this.time4 = time4;
	}

	public boolean isTime5() {
		return time5;
	}

	public void setTime5(boolean time5) {
		this.time5 = time5;
	}

	public boolean isTime6() {
		return time6;
	}

	public void setTime6(boolean time6) {
		this.time6 = time6;
	}

	public boolean isTime7() {
		return time7;
	}

	public void setTime7(boolean time7) {
		this.time7 = time7;
	}

	public boolean isTime8() {
		return time8;
	}

	public void setTime8(boolean time8) {
		this.time8 = time8;
	}

	public boolean isTime9() {
		return time9;
	}

	public void setTime9(boolean time9) {
		this.time9 = time9;
	}

	public boolean isTime10() {
		return time10;
	}

	public void setTime10(boolean time10) {
		this.time10 = time10;
	}

	public boolean isTime11() {
		return time11;
	}

	public void setTime11(boolean time11) {
		this.time11 = time11;
	}

	public boolean isTime12() {
		return time12;
	}

	public void setTime12(boolean time12) {
		this.time12 = time12;
	}

	public boolean isTime13() {
		return time13;
	}

	public void setTime13(boolean time13) {
		this.time13 = time13;
	}

	public boolean isTime14() {
		return time14;
	}

	public void setTime14(boolean time14) {
		this.time14 = time14;
	}

	public boolean isTime15() {
		return time15;
	}

	public void setTime15(boolean time15) {
		this.time15 = time15;
	}

	public boolean isTime16() {
		return time16;
	}

	public void setTime16(boolean time16) {
		this.time16 = time16;
	}

	public boolean isTime17() {
		return time17;
	}

	public void setTime17(boolean time17) {
		this.time17 = time17;
	}

	public boolean isTime18() {
		return time18;
	}

	public void setTime18(boolean time18) {
		this.time18 = time18;
	}

	public boolean isTime19() {
		return time19;
	}

	public void setTime19(boolean time19) {
		this.time19 = time19;
	}

	public boolean isTime20() {
		return time20;
	}

	public void setTime20(boolean time20) {
		this.time20 = time20;
	}

	public boolean isTime21() {
		return time21;
	}

	public void setTime21(boolean time21) {
		this.time21 = time21;
	}

	public boolean isTime22() {
		return time22;
	}

	public void setTime22(boolean time22) {
		this.time22 = time22;
	}

	public boolean isTime23() {
		return time23;
	}

	public void setTime23(boolean time23) {
		this.time23 = time23;
	}
	
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
}
