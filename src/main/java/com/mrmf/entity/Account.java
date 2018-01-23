package com.mrmf.entity;

import com.osg.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号信息 原oprator和opermafun表合并
 */
public class Account extends DataEntity {

	public static final String TYPE_ADMIN = "admin";
	public static final String TYPE_ORGAN_ADMIN = "organ";
	public static final String TYPE_STAFF = "staff";
	public static final String TYPE_USER = "user";

	public static final String STATUS_FAILED = "0";
	public static final String STATUS_ACTIVE = "1";
	public static final String STATUS_LOCK = "5";

	private String accountName; // 登陆用户名（为账号、微信号openid）



	//private String openid;//微信号openid
	private String accountPwd; // 微信号时为openid前10位md5
	private String status;// 状态，0禁用；1启用
	private String accountType;// 账号类型，admin:超级管理员;organ:店铺管理员;staff:技师;user:用户
	private String entityID;// 对应实体id:user普通用户类型对应user表id;organ类型对应organ表id;staff类型对应staff表id
	private String weUnionId; // 微信unionId，同一微信用户关注不同公众号时此id相同
	private List<String> roleIds;// 账号拥有的角色id列表（accountType为organ时使用）

	private List<String> cityList; // 管理员可管理的城市(accountType为admin时使用)
	private List<String> districtList; // 管理员可管理的区域(accountType为admin时使用)

	private String inviterId;// 邀请人ID (分享使用)

	// 非存储字段
	private List<String> roleNames;

	public String getInviterId() {
		return inviterId;
	}

	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public String getWeUnionId() {
		return weUnionId;
	}

	public void setWeUnionId(String weUnionId) {
		this.weUnionId = weUnionId;
	}

	/*public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}*/
	public List<String> getRoleIds() {
		if (roleIds == null)
			roleIds = new ArrayList<>();
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getRoleNames() {
		if (roleNames == null)
			roleNames = new ArrayList<String>();
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	public List<String> getCityList() {
		if (cityList == null)
			cityList = new ArrayList<String>();
		return cityList;
	}

	public void setCityList(List<String> cityList) {
		this.cityList = cityList;
	}

	public List<String> getDistrictList() {
		if (districtList == null)
			districtList = new ArrayList<String>();
		return districtList;
	}

	public void setDistrictList(List<String> districtList) {
		this.districtList = districtList;
	}

	public boolean isLogin() {
		boolean result = true;
		if (null == status || "0".equals(status)) {
			result = false;
		}
		return result;
	}

}
