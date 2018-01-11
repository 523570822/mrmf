package com.mrmf.entity;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;
import com.osg.entity.geo.GeoDistance;

/**
 * 员工信息，包括技师、助理等 原staff表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Staff extends DataEntity implements GeoDistance {
	private String organId; // 所属公司id
	private String parentId; // 所属总公司id
	private String dutyId; // 岗位id
	private String name; // 名称
	private String sex; // 性别
	private String birthday; // 生日，格式：yyyy-MM-dd
	private String home; // 住址
	private String idcard; // 身份证号
	private String accessDay; // 入职日期
	private String phone; // 电话
	private Boolean card; // 是否发了员工卡
	private double money;
	private double moneyAll;
	private int flag;// 离职状态,0:在职;1:离职
	private String leaveDate; // 离职日期，格式：yyyy-MM-dd
	private Boolean deleteFlag; // 是否删除
	private String zjfCode; // 助记符
	private String liushui; // 流水varchar20
	private String ticheng; // 提成varchar1
	private String gongzi; // 工资varchar1
	private String bumenId; // 所属部门id
	private int zuohuoNum; // 做活数量
	private String faxing; // char1发型
	private Boolean faxingshiFlag; // 是否发型师
	private Boolean zhuliFlag; // 是否助理
	private String jishiTechang; // 技师特长
	private String certNumber;// 资格证号
	private int level;// 级别：1~5级
	private String status;// 状态，0禁用；1启用；2待审核

	// 以下为微信业务相关补充字段
	private Boolean weixin; // 是否开通微信服务
	private int state;// 店铺繁忙状态，1：空闲；2：一般；3：繁忙
	private String busyTimeStart;// 繁忙时间段起始时间
	private String busyTimeEnd;// 繁忙时间段结束时间
	private String logo; // 技师头像图片
	private String nick;// 昵称
	private GpsPoint gpsPoint; // gps经纬度对象
	private int faceScore;// 颜值
	private int zanCount;// 赞数量
	private int qiuCount; // 糗数量
	private int followCount;// 关注/收藏数量
	private double totalIncome;// 总收益金额
	private String desc;// 简介
	private List<String> descImages;// 照片风采数组
	private int startPrice;// 服务起始价格（根据典型案例价格最小值取得）
	private List<String> weOrganIds;// 微信签约店铺id列表
	private List<String> followOrganIds;// 收藏店铺
	private int workYears; // 工作年限  
	private Boolean openInquiry; // 是否开启询价服务
	private String payPassword; // 支付密码，_id+password做md5
	private Integer evaluateCount;// 评价数量

	//1：分帐状态（平台店铺技师 1 3 6分帐）   2：租金状态 {平台店铺技师 1 3 6（个人消费大于X的部分） 平台技师 1 9 （注意两种分帐 金额=消费金额-返现金额 ）
	private int staffWorkState ;  //新增技师状态 对应Organ表organState字段，1时分为1和2
	private double staffWalletAmount; //技师钱包


	// 非存储字段
	private double distance; // 距离，单位：公里
	private String unit;// 距离单位
	private String organName;// 店铺名称
	private Integer userNum;
	private String bumenName; // 部门名称

	public int getStaffWorkState( ) {
		return staffWorkState;
	}

	public void setStaffWorkState(int staffWorkState) {
		this.staffWorkState = staffWorkState;
	}

	private String dutyName; // 岗位名称
	private double price;// 服务最低价
	private int zanLevel;// 列表赞等级

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public double getStaffWalletAmount( ) {
		return staffWalletAmount;
	}

	public void setStaffWalletAmount(double staffWalletAmount) {
		this.staffWalletAmount = staffWalletAmount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getDutyId() {
		return dutyId;
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAccessDay() {
		return accessDay;
	}

	public void setAccessDay(String accessDay) {
		this.accessDay = accessDay;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getCard() {
		return card;
	}

	public void setCard(Boolean card) {
		this.card = card;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getMoneyAll() {
		return moneyAll;
	}

	public void setMoneyAll(double moneyAll) {
		this.moneyAll = moneyAll;
	}

	public Boolean getDeleteFlag() {
		if(deleteFlag==null){
			deleteFlag=false;
		}
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	public String getLiushui() {
		return liushui;
	}

	public void setLiushui(String liushui) {
		this.liushui = liushui;
	}

	public String getTicheng() {
		return ticheng;
	}

	public void setTicheng(String ticheng) {
		this.ticheng = ticheng;
	}

	public String getGongzi() {
		return gongzi;
	}

	public void setGongzi(String gongzi) {
		this.gongzi = gongzi;
	}

	public String getBumenId() {
		return bumenId;
	}

	public void setBumenId(String bumenId) {
		this.bumenId = bumenId;
	}

	public int getZuohuoNum() {
		return zuohuoNum;
	}

	public void setZuohuoNum(int zuohuoNum) {
		this.zuohuoNum = zuohuoNum;
	}

	public String getFaxing() {
		return faxing;
	}

	public void setFaxing(String faxing) {
		this.faxing = faxing;
	}

	public Boolean getFaxingshiFlag() {
		return faxingshiFlag;
	}

	public void setFaxingshiFlag(Boolean faxingshiFlag) {
		this.faxingshiFlag = faxingshiFlag;
	}

	public Boolean getZhuliFlag() {
		return zhuliFlag;
	}

	public void setZhuliFlag(Boolean zhuliFlag) {
		this.zhuliFlag = zhuliFlag;
	}

	public String getJishiTechang() {
		return jishiTechang;
	}

	public void setJishiTechang(String jishiTechang) {
		this.jishiTechang = jishiTechang;
	}

	public Boolean getWeixin() {
		if (weixin == null)
			return false;
		else
			return weixin;
	}

	public void setWeixin(Boolean weixin) {
		this.weixin = weixin;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getBusyTimeStart() {
		return busyTimeStart;
	}

	public void setBusyTimeStart(String busyTimeStart) {
		this.busyTimeStart = busyTimeStart;
	}

	public String getBusyTimeEnd() {
		return busyTimeEnd;
	}

	public void setBusyTimeEnd(String busyTimeEnd) {
		this.busyTimeEnd = busyTimeEnd;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public int getFaceScore() {
		return faceScore;
	}

	public void setFaceScore(int faceScore) {
		this.faceScore = faceScore;
	}

	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}

	public int getQiuCount() {
		return qiuCount;
	}

	public void setQiuCount(int qiuCount) {
		this.qiuCount = qiuCount;
	}

	public int getFollowCount() {
		return followCount;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getDescImages() {
		if (descImages == null)
			descImages = new ArrayList<>();
		return descImages;
	}

	public void setDescImages(List<String> descImages) {
		this.descImages = descImages;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public List<String> getWeOrganIds() {
		if (weOrganIds == null)
			weOrganIds = new ArrayList<>();
		return weOrganIds;
	}

	public void setWeOrganIds(List<String> weOrganIds) {
		this.weOrganIds = weOrganIds;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getWorkYears() {
		return workYears;
	}

	public void setWorkYears(int workYears) {
		this.workYears = workYears;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Boolean getOpenInquiry() {
		return openInquiry;
	}

	public void setOpenInquiry(Boolean openInquiry) {
		this.openInquiry = openInquiry;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBumenName() {
		return bumenName;
	}

	public void setBumenName(String bumenName) {
		this.bumenName = bumenName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public List<String> getFollowOrganIds() {
		return followOrganIds;
	}

	public void setFollowOrganIds(List<String> followOrganIds) {
		this.followOrganIds = followOrganIds;
	}

	public int getZanLevel() {
		return zanLevel;
	}

	public void setZanLevel(int zanLevel) {
		this.zanLevel = zanLevel;
	}
}
