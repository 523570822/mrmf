package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeToken extends DataEntity{
private String token;//用户token
private String user_type;//用户类型 organ user staff
private long createTokenTime; // 创建token的时间戳
private String ticket;//用户ticket
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public String getUser_type() {
	return user_type;
}
public void setUser_type(String user_type) {
	this.user_type = user_type;
}

public long getCreateTokenTime() {
	return createTokenTime;
}
public void setCreateTokenTime(long createTokenTime) {
	this.createTokenTime = createTokenTime;
}
public String getTicket() {
	return ticket;
}
public void setTicket(String ticket) {
	this.ticket = ticket;
}
public String toString(){
	return token+"::"+user_type+"::"+createTokenTime+"::"+ticket;
}

}
