package com.mrmf.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;

/**
 * 会员主卡 原member表会员卡相关信息部分
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Usercard extends DataEntity {

	private String userId;// 用户id
	private String organId;// 公司id
	private String cardno;// 卡表面号
	private String membersort;// 会员卡类型
	private String cardLevel;// 会员卡级别
	private String passwd;// 密码
	private int cishu;// 次数
	private int shengcishu;// 剩余次数
	private double danci_money;// 单次款额
	private Date law_day;// 有效期

	private double money_leiji;// 累计消费金额
	private double money4;// 卡余额

	private double money_qian;// 欠费
	private int xu_cishu;// 续费次数
	private int come_num;// 来的次数，当店铺平台卡时此字段为消费次数
	private int flag_duanxin;// 是否发送短信,0:不发送,1:发送
	private int jifen; // 积分
	private Date comeday;// 光临日期
	private Date lastcomeday;// 最后来消费日期

	// 冗余存储字段
	private String organName; // 店铺名称，用于平台卡查询
	private String organAbname; // 店铺简称，用于平台卡查询

	// 以下为非存储字段
	private String zhekou;// 折扣
	private String coin;// 积分
	private String createTimeFormat;// 办卡日期
	private String lawTimeFormat;// 有效日期

	private String parentId;
	private String contactMan;
	private String phone;
	private String master;

	private int organValid; // 有效状态，0:无效；1:有效

	private double userPayTotal; // 用户支付总额（平台店铺卡消费查询时使用）
	private Boolean organIsNotPrepay; // 店铺是否为非预存模式

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
		return new BigDecimal(danci_money).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setDanci_money(double danci_money) {
		this.danci_money = danci_money;
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

	public double getMoney4() {
		return new BigDecimal(money4).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMoney4(double money4) {
		this.money4 = money4;
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

	public String getZhekou() {
		return zhekou;
	}

	public void setZhekou(String zhekou) {
		this.zhekou = zhekou;
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

	public int getCome_num() {
		return come_num;
	}

	public void setCome_num(int come_num) {
		this.come_num = come_num;
	}

	public int getFlag_duanxin() {
		return flag_duanxin;
	}

	public void setFlag_duanxin(int flag_duanxin) {
		this.flag_duanxin = flag_duanxin;
	}

	public int getJifen() {
		return jifen;
	}

	public void setJifen(int jifen) {
		this.jifen = jifen;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getCreateTimeFormat() {
		return createTimeFormat;
	}

	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}

	public String getLawTimeFormat() {
		return lawTimeFormat;
	}

	public void setLawTimeFormat(String lawTimeFormat) {
		this.lawTimeFormat = lawTimeFormat;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getComeday() {
		return comeday;
	}

	public void setComeday(Date comeday) {
		this.comeday = comeday;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getLastcomeday() {
		return lastcomeday;
	}

	public void setLastcomeday(Date lastcomeday) {
		this.lastcomeday = lastcomeday;
	}

	public String getOrganAbname() {
		return organAbname;
	}

	public void setOrganAbname(String organAbname) {
		this.organAbname = organAbname;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public int getOrganValid() {
		return organValid;
	}

	public void setOrganValid(int organValid) {
		this.organValid = organValid;
	}

	public double getUserPayTotal() {
		return userPayTotal;
	}

	public void setUserPayTotal(double userPayTotal) {
		this.userPayTotal = userPayTotal;
	}

	public Boolean getOrganIsNotPrepay() {
		if(organIsNotPrepay == null)
			organIsNotPrepay = false;
		return organIsNotPrepay;
	}

	public void setOrganIsNotPrepay(Boolean organIsNotPrepay) {
		this.organIsNotPrepay = organIsNotPrepay;
	}

}
