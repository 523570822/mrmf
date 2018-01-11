package com.mrmf.entity.user;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateSerializer;
import com.osg.framework.util.DateUtil;

/**
 * 会员卡类型 原membersort表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Usersort extends DataEntity {

	private String organId;// 所属公司
	private String zjfCode;// 助记符
	private String name1;// 会员卡类型名称（续费金额或次数）
	private String name2;// 次数卡指定项目id
	private double money;// 存钱打折卡、次数卡、单纯打折卡（不计入余额）金额
	private int cishu;// 次数卡次数
	private double zhekou;// 折扣，如:0.8(八折)
	private String flag1;// 卡类型识别号，优惠类别 1000:非会员;1001:单纯打折卡;1002:存钱打折卡;1003:次数卡
	private double danci_money;// 单次款额
	private double ticheng;// 办卡提成1
	private double ticheng2;// 办卡提成2
	private double ticheng3;// 办卡提成3
	private double bilv;// 存钱比率
	private double coin_bilv;// 积分比率
	private double coin_money;// 积分金额
	private int coin_give;// 办卡送积分
	private double xufei_ti1;// 续费提成1
	private double xufei_ti2;// 续费提成2
	private double xufei_ti3;// 续费提成3
	private double guding_ticheng;// 总提成比率
	private double waimaizhekou;// 外卖折扣
	private int law_date;// 有效天数
	private Boolean flag_putong;// 是否是默认显示
	private Boolean delete_flag;// 是否删除
	//新增成本
	private double price_chengben;
	//类型固定提成改为三个提成
	private double tc1;
	private double tc2;
	private double tc3;
	private double xtc1;
	private double xtc2;
	private double xtc3;
	// 以下为非存储字段
	private String flag1Name; // 卡类型名称
	private String name2Name; // 服务项目名称

	public double getXtc1() {
		return xtc1;
	}

	public void setXtc1(double xtc1) {
		this.xtc1 = xtc1;
	}

	public double getXtc2() {
		return xtc2;
	}

	public void setXtc2(double xtc2) {
		this.xtc2 = xtc2;
	}

	public double getXtc3() {
		return xtc3;
	}

	public void setXtc3(double xtc3) {
		this.xtc3 = xtc3;
	}

	public double getPrice_chengben() {
		return price_chengben;
	}

	public void setPrice_chengben(double price_chengben) {
		this.price_chengben = price_chengben;
	}

	public double getTc1() {
		return tc1;
	}

	public void setTc1(double tc1) {
		this.tc1 = tc1;
	}

	public double getTc2() {
		return tc2;
	}

	public void setTc2(double tc2) {
		this.tc2 = tc2;
	}

	public double getTc3() {
		return tc3;
	}

	public void setTc3(double tc3) {
		this.tc3 = tc3;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getCishu() {
		return cishu;
	}

	public void setCishu(int cishu) {
		this.cishu = cishu;
	}

	public double getZhekou() {
		return zhekou;
	}

	public void setZhekou(double zhekou) {
		this.zhekou = zhekou;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public double getDanci_money() {
		return danci_money;
	}

	public void setDanci_money(double danci_money) {
		this.danci_money = danci_money;
	}

	public double getTicheng() {
		return ticheng;
	}

	public void setTicheng(double ticheng) {
		this.ticheng = ticheng;
	}

	public double getTicheng2() {
		return ticheng2;
	}

	public void setTicheng2(double ticheng2) {
		this.ticheng2 = ticheng2;
	}

	public double getTicheng3() {
		return ticheng3;
	}

	public void setTicheng3(double ticheng3) {
		this.ticheng3 = ticheng3;
	}

	public double getBilv() {
		return bilv;
	}

	public void setBilv(double bilv) {
		this.bilv = bilv;
	}

	public double getCoin_bilv() {
		return coin_bilv;
	}

	public void setCoin_bilv(double coin_bilv) {
		this.coin_bilv = coin_bilv;
	}

	public double getCoin_money() {
		return coin_money;
	}

	public void setCoin_money(double coin_money) {
		this.coin_money = coin_money;
	}

	public int getCoin_give() {
		return coin_give;
	}

	public void setCoin_give(int coin_give) {
		this.coin_give = coin_give;
	}

	public double getXufei_ti1() {
		return xufei_ti1;
	}

	public void setXufei_ti1(double xufei_ti1) {
		this.xufei_ti1 = xufei_ti1;
	}

	public double getXufei_ti2() {
		return xufei_ti2;
	}

	public void setXufei_ti2(double xufei_ti2) {
		this.xufei_ti2 = xufei_ti2;
	}

	public double getXufei_ti3() {
		return xufei_ti3;
	}

	public void setXufei_ti3(double xufei_ti3) {
		this.xufei_ti3 = xufei_ti3;
	}

	public double getGuding_ticheng() {
		return guding_ticheng;
	}

	public void setGuding_ticheng(double guding_ticheng) {
		this.guding_ticheng = guding_ticheng;
	}

	public double getWaimaizhekou() {
		return waimaizhekou;
	}

	public void setWaimaizhekou(double waimaizhekou) {
		this.waimaizhekou = waimaizhekou;
	}

	public int getLaw_date() {
		return law_date;
	}

	public void setLaw_date(int law_date) {
		this.law_date = law_date;
	}

	@JsonSerialize(using = EHDateSerializer.class)
	public Date getLaw_day() {
		if (getLaw_date() == 0) // 为0表示无有效期，永久有效
			return null;
		else
			return DateUtil.addDate(DateUtil.currentDate(), getLaw_date());
	}

	public Boolean getFlag_putong() {
		return flag_putong;
	}

	public void setFlag_putong(Boolean flag_putong) {
		this.flag_putong = flag_putong;
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	public String getFlag1Name() {
		return flag1Name;
	}

	public void setFlag1Name(String flag1Name) {
		this.flag1Name = flag1Name;
	}

	public String getName2Name() {
		return name2Name;
	}

	public void setName2Name(String name2Name) {
		this.name2Name = name2Name;
	}

}
