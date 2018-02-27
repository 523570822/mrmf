package com.mrmf.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 子卡 原memberincard表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Userinincard extends DataEntity {
	private String userId;// 用户id
	private String organId;// 公司id
	private String cardId;// 会员关联id
	private String incardId;// 主卡id
	private String cardno;// 卡表面号
	private String id_2;// 子卡卡号
	private String membersort;// 会员卡类型
	private int allcishu;// 总次数
	private int shengcishu;// 剩余次数
	private double danci_money;// 单次款额
	private int coin;// 积分
	private Boolean delete_flag;// 是否删除
	private String jingshou;// 经手人
	private Boolean guazhang_flag;// 是否挂账
	private String bigsort;// 大类
	private String smallsort;// 服务项目
	private Boolean charge_big;// 是否来宾
	private Boolean charge_small;// 是否保底提成
	private Boolean charge_flag;// 是否小活
	private int card_num;// 卡数量
	private double zhekou;// 折扣，如:0.8(八折)
	private Date come_day;// 关顾日期
	private double money4; // 卡余额
	private Date law_day;// 有效期
	private double money_leiji;// 累计消费金额
	private double money_qian;// 欠费
	private int xu_cishu;// 续费次数
	private double money1;// 办卡金额
	private Boolean miandan; // 是否免单
	private String name; // 会员姓名

	// 非存储字段
	private String flag1; // 卡类型识别号，优惠类别 1000:非会员;1001:单纯打折卡;1002:存钱打折卡;1003:次数卡

	private String cardLevel;// 会员卡级别

	private String xiaopiao; // 建卡时的流水号

	// 非存储字段
	private String organName;// 店铺名
	private String createTimeFormat;// 创建时间
	private String lawDayFormat;// 有效期
	private String cardType;// 卡类型名
	private String usersortName;
	private String deleteName;
	private String miandanName;
	private String smallsortName; // 项目名称
	private Double huansuanMoney; //导出换算金钱

	public Double getHuansuanMoney() {
		return huansuanMoney;
	}

	public void setHuansuanMoney(Double huansuanMoney) {
		this.huansuanMoney = huansuanMoney;
	}

	public String getDeleteName() {
		return deleteName;
	}

	public void setDeleteName(String deleteName) {
		this.deleteName = deleteName;
	}

	public String getMiandanName() {
		return miandanName;
	}

	public void setMiandanName(String miandanName) {
		this.miandanName = miandanName;
	}

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

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIncardId() {
		return incardId;
	}

	public void setIncardId(String incardId) {
		this.incardId = incardId;
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

	public String getMembersort() {
		return membersort;
	}

	public void setMembersort(String membersort) {
		this.membersort = membersort;
	}

	public int getAllcishu() {
		return allcishu;
	}

	public void setAllcishu(int allcishu) {
		this.allcishu = allcishu;
	}

	public int getShengcishu() {
		return shengcishu;
	}

	public void setShengcishu(int shengcishu) {
		this.shengcishu = shengcishu;
	}

	public double getDanci_money() {
		return new BigDecimal(danci_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setDanci_money(double danci_money) {
		this.danci_money = danci_money;
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

	public String getJingshou() {
		return jingshou;
	}

	public void setJingshou(String jingshou) {
		this.jingshou = jingshou;
	}

	public Boolean getGuazhang_flag() {
		return guazhang_flag;
	}

	public void setGuazhang_flag(Boolean guazhang_flag) {
		this.guazhang_flag = guazhang_flag;
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

	public Boolean getCharge_big() {
		return charge_big;
	}

	public void setCharge_big(Boolean charge_big) {
		this.charge_big = charge_big;
	}

	public Boolean getCharge_small() {
		return charge_small;
	}

	public void setCharge_small(Boolean charge_small) {
		this.charge_small = charge_small;
	}

	public Boolean getCharge_flag() {
		return charge_flag;
	}

	public void setCharge_flag(Boolean charge_flag) {
		this.charge_flag = charge_flag;
	}

	public int getCard_num() {
		return card_num;
	}

	public void setCard_num(int card_num) {
		this.card_num = card_num;
	}

	public double getZhekou() {
		return new BigDecimal(zhekou).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setZhekou(double zhekou) {
		this.zhekou = zhekou;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getCome_day() {
		return come_day;
	}

	public void setCome_day(Date come_day) {
		this.come_day = come_day;
	}

	public double getMoney4() {
		return new BigDecimal(money4).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney4(double money4) {
		this.money4 = money4;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getLaw_day() {
		return law_day;
	}

	public void setLaw_day(Date law_day) {
		this.law_day = law_day;
	}

	public double getMoney_leiji() {
		return new BigDecimal(money_leiji).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_leiji(double money_leiji) {
		this.money_leiji = money_leiji;
	}

	public double getMoney_qian() {
		return new BigDecimal(money_qian).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney_qian(double money_qian) {
		this.money_qian = money_qian;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public String getLawDayFormat() {
		return lawDayFormat;
	}

	public void setLawDayFormat(String lawDayFormat) {
		this.lawDayFormat = lawDayFormat;
	}

	public int getXu_cishu() {
		return xu_cishu;
	}

	public void setXu_cishu(int xu_cishu) {
		this.xu_cishu = xu_cishu;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public double getMoney1() {
		return new BigDecimal(money1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney1(double money1) {
		this.money1 = money1;
	}

	public Boolean getMiandan() {
		if (miandan == null) {
			return false;
		}
		return miandan;
	}

	public void setMiandan(Boolean miandan) {
		this.miandan = miandan;
	}

	public String getUsersortName() {
		return usersortName;
	}

	public void setUsersortName(String usersortName) {
		this.usersortName = usersortName;
	}

	public String getXiaopiao() {
		return xiaopiao;
	}

	public void setXiaopiao(String xiaopiao) {
		this.xiaopiao = xiaopiao;
	}

	public String getSmallsortName() {
		return smallsortName;
	}

	public void setSmallsortName(String smallsortName) {
		this.smallsortName = smallsortName;
	}

}
