package com.mrmf.entity.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateSerializer;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 消费记录 原userpart表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Userpart extends DataEntity {

	private String kaidanId; // 开单id
	private String staffId1;// 员工1
	private String staffId2;// 员工2
	private String staffId3;// 员工3
	private Boolean dian1;// 是否点员工1
	private Boolean dian2;// 是否点员工2
	private Boolean dian3;// 是否点员工3
	private Boolean quan1;// 是否劝员工1
	private Boolean quan2;// 是否劝员工2
	private Boolean quan3;// 是否劝员工3
	private String havemoney1;// 提成员工1
	private String havemoney2;// 提成员工2
	private String havemoney3;// 提成员工3
	private Double somemoney1;// 提成金额1
	private Double somemoney2;// 提成金额2
	private Double somemoney3;// 提成金额3
	private double yeji1;// 业绩员工1
	private double yeji2;// 业绩员工2
	private double yeji3;// 业绩员工3
	private double money1;// 成交金额
	private double money2;// 实缴金额
	private double money3;// 找零
	private double money4;// 卡余额
	private double money5;// 抹零
	private double money6;// 折扣
	private double money_leiji;// 累计消费金额
	private String bigsort;// 大类
	private String smallsort;// 服务项目
	private Boolean flag2;// 是否交款
	private int coin;// 积分
	private Boolean delete_flag;// 是否删除
	private String userId;// 会员用户id
	private String cardno;// 卡表面号
	private String cardId; // 会员主卡id
	private String id_2;// 子卡编码
	private String incardId; // 会员卡id
	private String inincardId;// 子卡id
	private String jingshou;// 经手人
	private Boolean guazhang_flag;// 是否挂账
	private String organId;// 公司id，消费公司id
	private String parentId;// 上级公司id
	private Boolean charge_fag;// 是否小活
	private double money_wupin;// 物品金额
	private Date cometime;// 办卡时间
	private String money_lijuan;// 代金券
	private double money_lijti;// 代金券提成
	private double money_li_money;// 代金券金额
	private String money_yinhang;// 银行卡
	private double money_yinhangti;// 银行卡提成
	private double money_yinhang_money;// 银行卡金额
	private String money_other;// 其他
	private double money_otherti;// 其他提成
	private double money_other_money;// 其他金额
	private double money_cash;// 现金
	private Boolean flag1;// 是否美容会员
	private Boolean flag_sheng;// 是否是生客
	private Boolean miandan;// 是否免单
	private double miandanMoney;//免单金额
	private Boolean laibin_flag;// 是否是来宾
	private double song_money;// 赠送金额
	private double nowSongMoney;//本次赠送余额

	private String fenzhangId;// 微信分账id，WeUserPayFenzhang表id
	private double money_xiaofei;// 消费金额（存钱打折卡消费）
	private int cishu;// 次数（次数卡消费）
	private int shengcishu;// 剩余次数（次数卡消费）
	private int type;// 消费类型，0，非会员消费、办会员卡，1，会员卡内金额/次数消费，2，微信分账消费，3，会员卡充值，4，补缴欠费，5，提现，10，办子卡，11，子卡消费
	private double tixian;// 提现金额
	private Boolean weixinCharge; // 是否微信充值
	private String ownerOrganId;// 办卡公司id
	private String userpartId; // 用于产品选择关联，预置生成的userpartId
	private String xiaopiao; // 打印小票号，流水号

	// 冗余存储字段
	private String name; // 会员姓名
	private String phone; // 会员电话
	private String membersort; // 会员类型
	private Date birthday; // 会员生日
	private String place; // 会员居住地

	private Date law_day; // 会员卡有效期
	private String sex; // 会员性别
	private String love; // 爱好

	private String usersortType;// 会员卡类型 1000:非会员;1001:单纯打折卡;1002:存钱打折卡;1003:次数卡
	private List<String> images; // 会员照片
	private String doc; // VIP会员描述文档

	// 非存储字段
	private String createTimeFormat;// 时间格式化
	private String organName;// 店铺名
	private String passwd;// 会员卡密码，仅用于接收前端提交数据
	private int allcishu; // 总次数
	private double percentage;// 提成
	private String smallsortName; // 服务项目名称
	private String bigsortName;
	private int law_date;// 有效天数
	private double money_qian;// 欠费
	private int xu_cishu;// 续费次数
	private String usersortName; // 会员类别名称

	private double nowMoney4; // 目前卡余额
	private double nowSong_money;// 赠送金额
	private int nowShengcishu; // 目前卡剩余次数
	private double danci_money; // 次数卡单次款额
	private String miandanName;
	private String guazhangName;
	private String staff1Name;
	private String staff2Name;
	private String staff3Name;
	private String deleteName;
	private double songMoney;

	public double getNowSongMoney() {
		return nowSongMoney;
	}

	public void setNowSongMoney(double nowSongMoney) {
		this.nowSongMoney = nowSongMoney;
	}

	public double getMiandanMoney() {
		return miandanMoney;
	}

	public void setMiandanMoney(double miandanMoney) {
		this.miandanMoney = miandanMoney;
	}

	public double getSongMoney() {
		return songMoney;
	}

	public void setSongMoney(double songMoney) {
		this.songMoney = songMoney;
	}

	public String getMiandanName() {
		return miandanName;
	}

	public void setMiandanName(String miandanName) {
		this.miandanName = miandanName;
	}

	public String getGuazhangName() {
		return guazhangName;
	}

	public void setGuazhangName(String guazhangName) {
		this.guazhangName = guazhangName;
	}

	public String getStaff1Name() {
		return staff1Name;
	}

	public void setStaff1Name(String staff1Name) {
		this.staff1Name = staff1Name;
	}

	public String getStaff2Name() {
		return staff2Name;
	}

	public void setStaff2Name(String staff2Name) {
		this.staff2Name = staff2Name;
	}

	public String getStaff3Name() {
		return staff3Name;
	}

	public void setStaff3Name(String staff3Name) {
		this.staff3Name = staff3Name;
	}

	public String getDeleteName() {
		return deleteName;
	}

	public void setDeleteName(String deleteName) {
		this.deleteName = deleteName;
	}

	public String getKaidanId() {
		return kaidanId;
	}

	public void setKaidanId(String kaidanId) {
		this.kaidanId = kaidanId;
	}

	private String organLogo;// 店铺logo

	public String getStaffId1() {
		return staffId1;
	}

	public String getBigsortName() {
		return bigsortName;
	}

	public void setBigsortName(String bigsortName) {
		this.bigsortName = bigsortName;
	}

	public void setStaffId1(String staffId1) {
		this.staffId1 = staffId1;
	}

	public String getStaffId2() {
		return staffId2;
	}

	public void setStaffId2(String staffId2) {
		this.staffId2 = staffId2;
	}

	public String getStaffId3() {
		return staffId3;
	}

	public void setStaffId3(String staffId3) {
		this.staffId3 = staffId3;
	}

	public Boolean getDian1() {
		if (dian1 == null) {
			return false;
		}
		return dian1;
	}

	public void setDian1(Boolean dian1) {
		this.dian1 = dian1;
	}

	public Boolean getDian2() {
		if (dian2 == null) {
			return false;
		}
		return dian2;
	}

	public void setDian2(Boolean dian2) {
		this.dian2 = dian2;
	}

	public Boolean getDian3() {
		if (dian3 == null) {
			return false;
		}
		return dian3;
	}

	public void setDian3(Boolean dian3) {
		this.dian3 = dian3;
	}

	public Boolean getQuan1() {
		return quan1;
	}

	public void setQuan1(Boolean quan1) {
		this.quan1 = quan1;
	}

	public Boolean getQuan2() {
		return quan2;
	}

	public void setQuan2(Boolean quan2) {
		this.quan2 = quan2;
	}

	public Boolean getQuan3() {
		return quan3;
	}

	public void setQuan3(Boolean quan3) {
		this.quan3 = quan3;
	}

	public String getHavemoney1() {
		return havemoney1;
	}

	public void setHavemoney1(String havemoney1) {
		this.havemoney1 = havemoney1;
	}

	public String getHavemoney2() {
		return havemoney2;
	}

	public void setHavemoney2(String havemoney2) {
		this.havemoney2 = havemoney2;
	}

	public String getHavemoney3() {
		return havemoney3;
	}

	public void setHavemoney3(String havemoney3) {
		this.havemoney3 = havemoney3;
	}

	public Double getSomemoney1() {
		if(null==somemoney1){
			return new Double(0.0);
		}
		if(somemoney1.isNaN()){
			return 0.0;
		}
		return new BigDecimal(somemoney1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSomemoney1(Double somemoney1) {
		this.somemoney1 = somemoney1;
	}

	public Double getSomemoney2() {
		if(null==somemoney2){
			return new Double(0.0);
		}
		if(somemoney2.isNaN()){
			return 0.0;
		}
		return new BigDecimal(somemoney2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSomemoney2(Double somemoney2) {
		this.somemoney2 = somemoney2;
	}

	public double getSomemoney3() {
		if(null==somemoney3){
			return new Double(0.0);
		}
		if(somemoney3.isNaN()){
			return 0.0;
		}
		return new BigDecimal(somemoney3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSomemoney3(Double somemoney3) {
		this.somemoney3 = somemoney3;
	}

	public double getYeji1() {
		return new BigDecimal(yeji1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setYeji1(double yeji1) {
		this.yeji1 = yeji1;
	}

	public double getYeji2() {
		return new BigDecimal(yeji2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setYeji2(double yeji2) {
		this.yeji2 = yeji2;
	}

	public double getYeji3() {
		return new BigDecimal(yeji3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setYeji3(double yeji3) {
		this.yeji3 = yeji3;
	}

	public double getMoney1() {
		return new BigDecimal(money1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney1(double money1) {
		this.money1 = money1;
	}

	public double getMoney2() {
		return new BigDecimal(money2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney2(double money2) {
		this.money2 = money2;
	}

	public double getMoney3() {
		if ("NaN".equals(money3 + ""))
			return 0;
		else
			return new BigDecimal(money3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney3(double money3) {
		this.money3 = money3;
	}

	public double getMoney4() {
		return new BigDecimal(money4).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney4(double money4) {
		this.money4 = money4;
	}

	public double getMoney5() {
		return new BigDecimal(money5).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney5(double money5) {
		this.money5 = money5;
	}

	public double getMoney6() {
		return new BigDecimal(money6).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney6(double money6) {
		this.money6 = money6;
	}

	public double getMoney_leiji() {
		return new BigDecimal(money_leiji).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_leiji(double money_leiji) {
		this.money_leiji = money_leiji;
	}

	public String getBigsort() {
		return bigsort;
	}

	public void setBigsort(String bigsort) {
		this.bigsort = bigsort;
	}

	public String getSmallsort() {
		return smallsort;
	}

	public void setSmallsort(String smallsort) {
		this.smallsort = smallsort;
	}

	public Boolean getFlag2() {
		return flag2;
	}

	public void setFlag2(Boolean flag2) {
		this.flag2 = flag2;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public Boolean getDelete_flag() {
		if (delete_flag == null) {
			return false;
		}
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getId_2() {
		return id_2;
	}

	public void setId_2(String id_2) {
		this.id_2 = id_2;
	}

	public String getIncardId() {
		return incardId;
	}

	public void setIncardId(String incardId) {
		this.incardId = incardId;
	}

	public String getInincardId() {
		return inincardId;
	}

	public void setInincardId(String inincardId) {
		this.inincardId = inincardId;
	}

	public String getJingshou() {
		return jingshou;
	}

	public void setJingshou(String jingshou) {
		this.jingshou = jingshou;
	}

	public Boolean getGuazhang_flag() {
		if (guazhang_flag == null) {
			return false;
		}
		return guazhang_flag;
	}

	public void setGuazhang_flag(Boolean guazhang_flag) {
		this.guazhang_flag = guazhang_flag;
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

	public Boolean getCharge_fag() {
		return charge_fag;
	}

	public void setCharge_fag(Boolean charge_fag) {
		this.charge_fag = charge_fag;
	}

	public double getMoney_wupin() {
		return new BigDecimal(money_wupin).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_wupin(double money_wupin) {
		this.money_wupin = money_wupin;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getCometime() {
		return cometime;
	}

	public void setCometime(Date cometime) {
		this.cometime = cometime;
	}

	public String getMoney_lijuan() {
		return money_lijuan;
	}

	public void setMoney_lijuan(String money_lijuan) {
		this.money_lijuan = money_lijuan;
	}

	public double getMoney_lijti() {
		return new BigDecimal(money_lijti).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_lijti(double money_lijti) {
		this.money_lijti = money_lijti;
	}

	public double getMoney_li_money() {
		return new BigDecimal(money_li_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_li_money(double money_li_money) {
		this.money_li_money = money_li_money;
	}

	public String getMoney_yinhang() {
		return money_yinhang;
	}

	public void setMoney_yinhang(String money_yinhang) {
		this.money_yinhang = money_yinhang;
	}

	public double getMoney_yinhangti() {
		return new BigDecimal(money_yinhangti).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_yinhangti(double money_yinhangti) {
		this.money_yinhangti = money_yinhangti;
	}

	public double getMoney_yinhang_money() {
		return new BigDecimal(money_yinhang_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_yinhang_money(double money_yinhang_money) {
		this.money_yinhang_money = money_yinhang_money;
	}

	public String getMoney_other() {
		return money_other;
	}

	public void setMoney_other(String money_other) {
		this.money_other = money_other;
	}

	public double getMoney_otherti() {
		return new BigDecimal(money_otherti).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_otherti(double money_otherti) {
		this.money_otherti = money_otherti;
	}

	public double getMoney_other_money() {
		return new BigDecimal(money_other_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_other_money(double money_other_money) {
		this.money_other_money = money_other_money;
	}

	public double getMoney_cash() {
		return new BigDecimal(money_cash).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_cash(double money_cash) {
		this.money_cash = money_cash;
	}

	public Boolean getFlag1() {
		return flag1;
	}

	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}

	public Boolean getFlag_sheng() {
		return flag_sheng;
	}

	public void setFlag_sheng(Boolean flag_sheng) {
		this.flag_sheng = flag_sheng;
	}

	public Boolean getMiandan() {
		if (miandan == null)
			return false;
		else
			return miandan;
	}

	public void setMiandan(Boolean miandan) {
		this.miandan = miandan;
	}

	public Boolean getLaibin_flag() {
		return laibin_flag;
	}

	public void setLaibin_flag(Boolean laibin_flag) {
		this.laibin_flag = laibin_flag;
	}

	public double getSong_money() {
		return new BigDecimal(song_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSong_money(double song_money) {
		this.song_money = song_money;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMembersort() {
		return membersort;
	}

	public void setMembersort(String membersort) {
		this.membersort = membersort;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@JsonSerialize(using = EHDateSerializer.class)
	public Date getLaw_day() {
		return law_day;
	}

	public void setLaw_day(Date law_day) {
		this.law_day = law_day;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLove() {
		return love;
	}

	public void setLove(String love) {
		this.love = love;
	}

	public String getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganLogo() {
		return organLogo;
	}

	public void setOrganLogo(String organLogo) {
		this.organLogo = organLogo;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getFenzhangId() {
		return fenzhangId;
	}

	public void setFenzhangId(String fenzhangId) {
		this.fenzhangId = fenzhangId;
	}

	public double getMoney_xiaofei() {
		return new BigDecimal(money_xiaofei).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_xiaofei(double money_xiaofei) {
		this.money_xiaofei = money_xiaofei;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getTixian() {
		return new BigDecimal(tixian).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTixian(double tixian) {
		this.tixian = tixian;
	}

	public int getAllcishu() {
		return allcishu;
	}

	public void setAllcishu(int allcishu) {
		this.allcishu = allcishu;
	}

	public double getPercentage() {
		return new BigDecimal(percentage).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getSmallsortName() {
		return smallsortName;
	}

	public void setSmallsortName(String smallsortName) {
		this.smallsortName = smallsortName;
	}

	public int getLaw_date() {
		return law_date;
	}

	public void setLaw_date(int law_date) {
		this.law_date = law_date;
	}

	public double getMoney_qian() {
		return new BigDecimal(money_qian).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_qian(double money_qian) {
		this.money_qian = money_qian;
	}

	public int getXu_cishu() {
		return xu_cishu;
	}

	public void setXu_cishu(int xu_cishu) {
		this.xu_cishu = xu_cishu;
	}

	public Boolean getWeixinCharge() {
		return weixinCharge;
	}

	public void setWeixinCharge(Boolean weixinCharge) {
		this.weixinCharge = weixinCharge;
	}

	public String getXiaopiao() {
		return xiaopiao;
	}

	public void setXiaopiao(String xiaopiao) {
		this.xiaopiao = xiaopiao;
	}

	public String getUserpartId() {
		return userpartId;
	}

	public void setUserpartId(String userpartId) {
		this.userpartId = userpartId;
	}

	public String getOwnerOrganId() {
		return ownerOrganId;
	}

	public void setOwnerOrganId(String ownerOrganId) {
		this.ownerOrganId = ownerOrganId;
	}

	public String getUsersortName() {
		return usersortName;
	}

	public void setUsersortName(String usersortName) {
		this.usersortName = usersortName;
	}

	public String getUsersortType() {
		return usersortType;
	}

	public void setUsersortType(String usersortType) {
		this.usersortType = usersortType;
	}

	public List<String> getImages() {
		if (images == null)
			images = new ArrayList<String>();
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public double getNowMoney4() {
		return new BigDecimal(nowMoney4).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setNowMoney4(double nowMoney4) {
		this.nowMoney4 = nowMoney4;
	}

	public double getNowSong_money() {
		return new BigDecimal(nowSong_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setNowSong_money(double nowSong_money) {
		this.nowSong_money = nowSong_money;
	}

	public double getDanci_money() {
		return new BigDecimal(danci_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setDanci_money(double danci_money) {
		this.danci_money = danci_money;
	}

	public int getNowShengcishu() {
		return nowShengcishu;
	}

	public void setNowShengcishu(int nowShengcishu) {
		this.nowShengcishu = nowShengcishu;
	}

}
