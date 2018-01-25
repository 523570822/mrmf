package com.mrmf.entity;

import com.osg.entity.DataEntity;
import com.osg.entity.GpsPoint;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司信息（店铺、门店，包括总公司、子公司），通过parentId建立总公司和子公司关联 原organ表
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organ extends DataEntity  {

	private String parentId; // 父公司id，0为根节点（总部）
	private String name; // 名称
	private String abname; // 简称
	private String master; // 管理人
	private String contactMan; // 联系人
	private String phone; // 电话
	private String tel; // 座机号码
	private String zip; // 邮政编码
	private String fax; // 传真
	private String cert;// 店铺营业执照

	private String bankAccountName; // 账户名称
	private String bankKaihu; // 开户行
	private String bankAccount; // 银行账号

	private String address; // 地址
	private String namehead; // 编码字头
	private String ab; // 助记符
	private Boolean deleteFlag; // 是否删除
	// private String netHao; // 网络标识号
	private String note1; // 打印备注1
	private String note2; // 打印备注2
	private String note3; // 打印备注3
	private String note4; // 打印备注4

	private String adminId;// 管理员id/账号
	private Boolean canCharge; // 是否可以微信会员卡充值
	private Boolean isNotPrepay; // 否按照非预存模式走，默认为false，即预存模式
	private Boolean wxBanding;//是否允许绑定微信

	private String shopPhone;//商城管理员手机号
	private List<String> ips;// 授权登录过的ip地址
	private int valid;// 有效状态，0:无效；1:有效
	private List<String> cardOrganIds;// 会员卡通用店铺id列表

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	// 非存储字段，与前台页面交互使用
	private String passwd; // 管理员口令
	private Double shopPrice;//商城预存款  展示排序

	// 以下为微信补充字段
	private Boolean weixin; // 是否开通微信服务
	private int state;// 店铺繁忙状态，0：空闲；1：一般；2：繁忙
	private String busyTimeStart;// 繁忙时间段起始时间
	private String busyTimeEnd;// 繁忙时间段结束时间
	private List<String> type;// 店铺类型，美容、美发、美足、美甲等
	private String logo; // 店铺头像图片
	private String certImage;// 营业执照图片
	private String idcardImage;// 负责人身份证照片
	private String city;// 城市，比如：北京
	private String district; // 区，比如：海淀区
	private String region; // 商圈，比如：回龙观
	private GpsPoint gpsPoint; // gps经纬度对象
	private int zanCount;// 赞数量
	private int qiuCount; // 糗数量
	private int followCount;// 关注/收藏数量
	private String desc;// 简介
	private String discountInfo;// 优惠信息
	private double deposit; // 保证金
	private double walletAmount;// 钱包余额
	private String payPassword;// 支付密码
	private int evaluateCount;// 评价数量
	private List<String> images; // 店铺展示图片
	private String organPositionState;//工位状态  0-开启  1-关闭
	private int organState ; //新增 店铺状态 ,专门给技师分帐 0 普通状态  1 分帐/租金状态

	public int getOrganState( ) {
		return organState;
	}

	public void setOrganState(int organState) {
		this.organState = organState;
	}

	// 非存储字段
	private double distance; // 距离，单位：公里
	private String unit;// 距离单位
	private int level;// 列表赞等级
	private Integer delNum;// 店铺删除的平台卡数量
	private Integer num;// 店铺正常使用平台卡数量
	private Integer leaseType;//租赁类型 0-租金模式  1-分成模式
	private Integer leaseMoney;//租金
	private int state1;//配置状态 0-已开启  1-已关闭
    private Integer num1;//工位数量


	public Double getShopPrice() {
		if(null==shopPrice){
			shopPrice=0D;
		}
		return shopPrice;
	}

	public void setShopPrice(Double shopPrice) {
		this.shopPrice = shopPrice;
	}

	public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getLeaseMoney() {
		return leaseMoney;
	}

	public void setLeaseMoney(Integer leaseMoney) {
		this.leaseMoney = leaseMoney;
	}

	public void setLeaseType(Integer leaseType) {
		this.leaseType = leaseType;
	}

	public Integer getLeaseType() {
		return leaseType;
	}

	public int getState1() {
		return state1;
	}

	public void setState1(int state1) {
		this.state1 = state1;
	}
	public String getOrganPositionState() {
		return organPositionState;
	}

	public void setOrganPositionState(String organPositionState) {
		this.organPositionState = organPositionState;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbname() {
		return abname;
	}

	public void setAbname(String abname) {
		this.abname = abname;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankKaihu() {
		return bankKaihu;
	}

	public void setBankKaihu(String bankKaihu) {
		this.bankKaihu = bankKaihu;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNamehead() {
		return namehead;
	}

	public void setNamehead(String namehead) {
		this.namehead = namehead;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getNote3() {
		return note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

	public String getNote4() {
		return note4;
	}

	public void setNote4(String note4) {
		this.note4 = note4;
	}

	public Boolean getCanCharge() {
		if (canCharge == null)
			canCharge = false;
		return canCharge;
	}

	public void setCanCharge(Boolean canCharge) {
		this.canCharge = canCharge;
	}

	public Boolean getIsNotPrepay() {
		if (isNotPrepay == null)
			isNotPrepay = false;
		return isNotPrepay;
	}

	public void setIsNotPrepay(Boolean isNotPrepay) {
		this.isNotPrepay = isNotPrepay;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Boolean getWeixin() {
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

	public List<String> getType() {
		if (type == null)
			type = new ArrayList<String>();
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCertImage() {
		return certImage;
	}

	public void setCertImage(String certImage) {
		this.certImage = certImage;
	}

	public String getIdcardImage() {
		return idcardImage;
	}

	public void setIdcardImage(String idcardImage) {
		this.idcardImage = idcardImage;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public GpsPoint getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(GpsPoint gpsPoint) {
		this.gpsPoint = gpsPoint;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public int getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(int evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public List<String> getImages() {
		if (images == null)
			images = new ArrayList<String>();
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getIps() {
		if (ips == null)
			ips = new ArrayList<>();
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public List<String> getCardOrganIds() {
		if (cardOrganIds == null)
			cardOrganIds = new ArrayList<>();
		return cardOrganIds;
	}

	public void setCardOrganIds(List<String> cardOrganIds) {
		this.cardOrganIds = cardOrganIds;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Boolean getZongbu() { // 返回是否是总部
		return "0".equals(parentId);
	}

	public Integer getDelNum() {
		if (delNum == null) {
			return 0;
		}
		return delNum;
	}

	public void setDelNum(Integer delNum) {
		this.delNum = delNum;
	}

	public Integer getNum() {
		if (num == null) {
			return 0;
		}
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	public Boolean getWxBanding() {
		return wxBanding;
	}

	public void setWxBanding(Boolean wxBanding) {
		this.wxBanding = wxBanding;
	}


}
