package com.mrmf.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mrmf.entity.coupon.Coupon;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;
import com.osg.entity.geo.GeoDistance;
import com.osg.entity.util.EHDateTimeSerializer;
import org.springframework.data.redis.connection.srp.SrpScriptReturnConverter;

/**
 * 用户信息 原member表
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User extends DataEntity implements GeoDistance {

	// 以下为微信相关字段
	private String organId;
	private String name;// 姓名
	private String phone;// 手机号in
	private String nick;// 昵称（来自微信）
	private String avatar;// 头像（来自微信）
	private String email;// 邮箱
	private GpsPoint gpsPoint;// 用户GPS定位经纬度信息
	private String city;// 用户选择的城市
	private List<String> favorOrganIds;// 收藏店铺id列表
	private List<String> favorStaffIds;// 收藏技师id列表
	private List<String> favorCaseIds;// 收藏发型案例id列表
	private List<String> orderHisIds;// 预约过的店铺和技师id列表
	private double walletAmount;// 钱包余额
	private String payPassword;// 支付密码，_id+password做md
	private String status;// 状态，0禁用；1启用

	private String invitor;// 邀请人（邀请此用户的技师id）
	private Date inviteDate;// 邀请时间
	private String accountType;//邀请人类型
	// 以下为后台业务补充字段
	private List<String> images; // 会员照片
	private String sex; // 性别
	private Date birthday; // 生日
	private String place;// 地址
	private String love;// 备注
	private int delete_flag;// 是否删除,0:未删除，1:已删除
	private List<String> organIds;// 公司id列表
	private List<String> parentIds;// 上级公司id列表
	private String doc; // 关联文档(用于描述用户)

	private String importId; // 用户信息导入公司id

	// 非存储字段
	private double distance; // 距离，单位：公里
	private int orderNum;// 查询客户订单数量
	private String userType;
	private boolean favorTheOrganId;// 存储当前用户是否收藏了指定店铺
	private String orderTime;// 存储当前用户预约订单的时间
	private boolean isMember;// 是否是会员
	private double price;
	private String organName;

	private List<String> couponIdList;//优惠券列表

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public List<String> getCouponIdList() {
		return couponIdList;
	}

	public User(){
		_id = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
		if(couponIdList == null){
			couponIdList = new ArrayList<>();
		}
	}
	
	public void setCouponIdList(List<String> couponIdList) {
		this.couponIdList = couponIdList;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getFavorOrganIds() {
		if (favorOrganIds == null)
			favorOrganIds = new ArrayList<>();
		return favorOrganIds;
	}

	public void setFavorOrganIds(List<String> favorOrganIds) {
		this.favorOrganIds = favorOrganIds;
	}

	public List<String> getFavorStaffIds() {
		if (favorStaffIds == null)
			favorStaffIds = new ArrayList<>();
		return favorStaffIds;
	}

	public void setFavorStaffIds(List<String> favorStaffIds) {
		this.favorStaffIds = favorStaffIds;
	}

	public List<String> getFavorCaseIds() {
		if (favorCaseIds == null)
			favorCaseIds = new ArrayList<>();
		return favorCaseIds;
	}

	public void setFavorCaseIds(List<String> favorCaseIds) {
		this.favorCaseIds = favorCaseIds;
	}

	public List<String> getOrderHisIds() {
		if (orderHisIds == null)
			orderHisIds = new ArrayList<>();
		return orderHisIds;
	}

	public void setOrderHisIds(List<String> orderHisIds) {
		this.orderHisIds = orderHisIds;
	}

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getImages() {
		if (images == null)
			images = new ArrayList<String>();
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLove() {
		return love;
	}

	public void setLove(String love) {
		this.love = love;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}

	public List<String> getOrganIds() {
		if (organIds == null)
			organIds = new ArrayList<>();
		return organIds;
	}

	public void setOrganIds(List<String> organIds) {
		this.organIds = organIds;
	}

	public List<String> getParentIds() {
		if (parentIds == null)
			parentIds = new ArrayList<>();
		return parentIds;
	}

	public void setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isFavorTheOrganId() {
		return favorTheOrganId;
	}

	public void setFavorTheOrganId(boolean favorTheOrganId) {
		this.favorTheOrganId = favorTheOrganId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(boolean isMember) {
		this.isMember = isMember;
	}

	public String getInvitor() {
		return invitor;
	}

	public void setInvitor(String invitor) {
		this.invitor = invitor;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getInviteDate() {
		return inviteDate;
	}

	public void setInviteDate(Date inviteDate) {
		this.inviteDate = inviteDate;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

}
