package com.osg.framework.web.security;

import java.util.Arrays;
import java.util.Objects;

public class VerifyObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String fullName;
	private String password;
	private String[] roles;
	private Object entity;

	public VerifyObject() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public VerifyObject(String userName, String fullName, String password,
			String... roles) {
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		return this == o || o != null && o instanceof VerifyObject
				&& Objects.equals(userName, ((VerifyObject) o).userName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(userName);
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "VerifyObject [userName=" + userName + ", fullName=" + fullName
				+ ", password=" + password + ", roles="
				+ Arrays.toString(roles) + ", entity=" + entity + "]";
	}
}
