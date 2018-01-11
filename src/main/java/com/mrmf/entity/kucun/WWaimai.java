package com.mrmf.entity.kucun;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 外卖表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WWaimai extends DataEntity {

	private String kaidanId; // 开单id（或会员主卡id - 会员卡消费外卖时）
	private String organId;
	private String wupinId; // 物品id
	private String outstoreId; // 出库id

	private String buyname; // 购买人姓名
	private double money1;// 外卖金额总价
	private String staffId1; // 员工1
	private double ticheng1; // 员工1提成金额
	private double yeji1; // 业绩1
	private String staffId2; // 员工2
	private double ticheng2; // 员工2提成金额
	private double yeji2; // 业绩2
	private double num; // 购买数量
	private double money2;// 单价
	private double base_money; // 进价
	private double base_all;// 总价(money1-money_qian)
	private double weight;// 净含量
	private double weight_all;// 总量

	private double zhekou; // 外卖折扣
	private double money_qian;// 剩余款额（欠款）
	private String name;// 姓名
	private String phone;// 电话

	private boolean flag;// 余款是否还清（money_qian是否归还）
	private boolean dinggou; // 订购
	private boolean miandan; // 是否免单
	private boolean delete_flag; // 是否删除
	private boolean guazhang_flag; // 是否挂账

	private String xiaopiao; // 打印小票号，流水号
	private boolean isCard; // 是否会员卡外卖

	// 以下为冗余存储字段
	private String wupinCode;// 物品代码
	private String wupinName; // 物品名称
	private String guige;// 规格
	private String isCardName;//是否卡外卖

	public String getIsCardName() {
		return isCardName;
	}

	public void setIsCardName(String isCardName) {
		this.isCardName = isCardName;
	}

	public String getKaidanId() {
		return kaidanId;
	}

	public void setKaidanId(String kaidanId) {
		this.kaidanId = kaidanId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getWupinId() {
		return wupinId;
	}

	public void setWupinId(String wupinId) {
		this.wupinId = wupinId;
	}

	public String getOutstoreId() {
		return outstoreId;
	}

	public void setOutstoreId(String outstoreId) {
		this.outstoreId = outstoreId;
	}

	public String getBuyname() {
		return buyname;
	}

	public void setBuyname(String buyname) {
		this.buyname = buyname;
	}

	public double getMoney1() {
		return money1;
	}

	public void setMoney1(double money1) {
		this.money1 = money1;
	}

	public double getTicheng1() {
		return ticheng1;
	}

	public void setTicheng1(double ticheng1) {
		this.ticheng1 = ticheng1;
	}

	public double getYeji1() {
		return yeji1;
	}

	public void setYeji1(double yeji1) {
		this.yeji1 = yeji1;
	}

	public String getStaffId1() {
		return staffId1;
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

	public double getTicheng2() {
		return ticheng2;
	}

	public void setTicheng2(double ticheng2) {
		this.ticheng2 = ticheng2;
	}

	public double getYeji2() {
		return yeji2;
	}

	public void setYeji2(double yeji2) {
		this.yeji2 = yeji2;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getMoney2() {
		return money2;
	}

	public void setMoney2(double money2) {
		this.money2 = money2;
	}

	public double getBase_money() {
		return base_money;
	}

	public void setBase_money(double base_money) {
		this.base_money = base_money;
	}

	public double getBase_all() {
		return base_all;
	}

	public void setBase_all(double base_all) {
		this.base_all = base_all;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight_all() {
		return weight_all;
	}

	public void setWeight_all(double weight_all) {
		this.weight_all = weight_all;
	}

	public double getZhekou() {
		return zhekou;
	}

	public void setZhekou(double zhekou) {
		this.zhekou = zhekou;
	}

	public double getMoney_qian() {
		return money_qian;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Boolean getDinggou() {
		return dinggou;
	}

	public void setDinggou(Boolean dinggou) {
		this.dinggou = dinggou;
	}

	public Boolean getMiandan() {
		return miandan;
	}

	public void setMiandan(Boolean miandan) {
		this.miandan = miandan;
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public Boolean getGuazhang_flag() {
		return guazhang_flag;
	}

	public void setGuazhang_flag(Boolean guazhang_flag) {
		this.guazhang_flag = guazhang_flag;
	}
	
	public Boolean getIsCard() {
		return isCard;
	}

	public void setIsCard(Boolean isCard) {
		this.isCard = isCard;
	}

	public String getXiaopiao() {
		return xiaopiao;
	}

	public void setXiaopiao(String xiaopiao) {
		this.xiaopiao = xiaopiao;
	}

	public String getWupinCode() {
		return wupinCode;
	}

	public void setWupinCode(String wupinCode) {
		this.wupinCode = wupinCode;
	}

	public String getWupinName() {
		return wupinName;
	}

	public void setWupinName(String wupinName) {
		this.wupinName = wupinName;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

}
