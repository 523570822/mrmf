package com.mrmf.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeUserCard {
private String userId;   //用户ID
private String organId;  //店铺ID
private String cardno;    //卡表面号
private String membersort; //会员卡类型
private String passwd;     //密码
private int cishu;         //次数
private int shengcishu;     //剩余次数
private double danci_money;  //单次款额
private Date law_day;        //有效期



public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getOrganId() {
	return organId;
}
public void setOrganId(String organId) {
	this.organId = organId;
}
public String getCardno() {
	return cardno;
}
public void setCardno(String cardno) {
	this.cardno = cardno;
}
public String getMembersort() {
	return membersort;
}
public void setMembersort(String membersort) {
	this.membersort = membersort;
}
public String getPasswd() {
	return passwd;
}
public void setPasswd(String passwd) {
	this.passwd = passwd;
}
public int getCishu() {
	return cishu;
}
public void setCishu(int cishu) {
	this.cishu = cishu;
}
public int getShengcishu() {
	return shengcishu;
}
public void setShengcishu(int shengcishu) {
	this.shengcishu = shengcishu;
}
public double getDanci_money() {
	return danci_money;
}
public void setDanci_money(double danci_money) {
	this.danci_money = danci_money;
}
public Date getLaw_day() {
	return law_day;
}
public void setLaw_day(Date law_day) {
	this.law_day = law_day;
}


}
