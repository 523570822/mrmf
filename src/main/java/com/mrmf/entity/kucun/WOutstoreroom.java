package com.mrmf.entity.kucun;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * 入库信息表
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WOutstoreroom extends DataEntity {
	private String parentOrganId;// 上级公司id，0为总公司
	private String organId;// 公司id
	private String wupinId;// 物品类别id
	private String mingcheng;// 产品名称
	private String code;// 产品编码（条码号）

	private double price;// 进货价格
	private double num;// 进货数量
	private double price_all;// 总价
	private String note;// 备注
	private String place1;// 产品产地
	private String staff;// 进货人
	private String place2;// 产品公司地址
	private String phone;// 电话
	private String lingyong;// 领用人
	private Boolean flag;// 外卖出库
	private Boolean delete_flag;// 是否删除
	private double weight;// 每瓶净含量
	private double weight_all;// 总量
	private String bumen;// 部门
	private String zjfCode;// 助记符
	private Date come_time;// 出库时间
	private Boolean guazhang_flag;// 是否挂账
	private String danhao;// 单号，外卖或产品出库时，为外卖(WWaimai)或产品(WUsewupin)出库id
	private String danwei;// 单位
	private String danweiname;// 进货单位名称
	private String zupanhao;// 组盘号
	private String pinpai;// 品牌
	private String guige;// 规格
	private Boolean shenhe;// 出库审核
	private double price_xs;// 销售价格
	private double price_all_xs;// 销售总价
	private Boolean shenhe_fen;// 分公司入库审核

	private String userpartId; // 关联消费记录id(使用产品出库时使用)
	private String kaidanId; // 关联开单记录id(外卖出库时使用)

	// 非存储字段
	private String shenheName;// 展示审核状态
	private String wupinName;// 物品类别名称
	private String bumenName;// 部门名称
	private String danweiName1;// 单位名称
	private String pinpaiName;// 品牌名称
	private String guizhangName;// 是否挂账
	private String flagName;// 是否外卖出库
	private String shenhefenName;// 分公司是否审核

	public String getShenhefenName() {
		return shenhefenName;
	}

	public void setShenhefenName(String shenhefenName) {
		this.shenhefenName = shenhefenName;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public String getParentOrganId() {
		return parentOrganId;
	}

	public String getGuizhangName() {
		return guizhangName;
	}

	public void setGuizhangName(String guizhangName) {
		this.guizhangName = guizhangName;
	}

	public void setParentOrganId(String parentOrganId) {
		this.parentOrganId = parentOrganId;
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

	public String getMingcheng() {
		return mingcheng;
	}

	public void setMingcheng(String mingcheng) {
		this.mingcheng = mingcheng;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getPrice_all() {
		return price_all;
	}

	public void setPrice_all(double price_all) {
		this.price_all = price_all;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPlace1() {
		return place1;
	}

	public void setPlace1(String place1) {
		this.place1 = place1;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getPlace2() {
		return place2;
	}

	public void setPlace2(String place2) {
		this.place2 = place2;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLingyong() {
		return lingyong;
	}

	public void setLingyong(String lingyong) {
		this.lingyong = lingyong;
	}

	public Boolean getFlag() {
		if (flag == null)
			flag = false;
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Boolean getDelete_flag() {
		if (delete_flag == null)
			delete_flag = false;
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
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

	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	@JsonSerialize(using = EHDateTimeSerializer.class)
	public Date getCome_time() {
		return come_time;
	}

	public void setCome_time(Date come_time) {
		this.come_time = come_time;
	}

	public Boolean getGuazhang_flag() {
		if (guazhang_flag == null)
			guazhang_flag = false;
		return guazhang_flag;
	}

	public void setGuazhang_flag(Boolean guazhang_flag) {
		this.guazhang_flag = guazhang_flag;
	}

	public String getDanhao() {
		return danhao;
	}

	public void setDanhao(String danhao) {
		this.danhao = danhao;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getDanweiname() {
		return danweiname;
	}

	public void setDanweiname(String danweiname) {
		this.danweiname = danweiname;
	}

	public String getZupanhao() {
		return zupanhao;
	}

	public void setZupanhao(String zupanhao) {
		this.zupanhao = zupanhao;
	}

	public String getPinpai() {
		return pinpai;
	}

	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public Boolean getShenhe() {
		if (shenhe == null)
			shenhe = false;
		return shenhe;
	}

	public void setShenhe(Boolean shenhe) {
		this.shenhe = shenhe;
	}

	public double getPrice_xs() {
		return price_xs;
	}

	public void setPrice_xs(double price_xs) {
		this.price_xs = price_xs;
	}

	public double getPrice_all_xs() {
		return price_all_xs;
	}

	public void setPrice_all_xs(double price_all_xs) {
		this.price_all_xs = price_all_xs;
	}

	public String getUserpartId() {
		return userpartId;
	}

	public void setUserpartId(String userpartId) {
		this.userpartId = userpartId;
	}

	public String getKaidanId() {
		return kaidanId;
	}

	public void setKaidanId(String kaidanId) {
		this.kaidanId = kaidanId;
	}

	public Boolean getShenhe_fen() {
		if (shenhe_fen == null)
			shenhe_fen = false;
		return shenhe_fen;
	}

	public void setShenhe_fen(Boolean shenhe_fen) {
		this.shenhe_fen = shenhe_fen;
	}

	public String getShenheName() {
		return shenheName;
	}

	public void setShenheName(String shenheName) {
		this.shenheName = shenheName;
	}

	public String getWupinName() {
		return wupinName;
	}

	public void setWupinName(String wupinName) {
		this.wupinName = wupinName;
	}

	public String getBumenName() {
		return bumenName;
	}

	public void setBumenName(String bumenName) {
		this.bumenName = bumenName;
	}

	public String getDanweiName1() {
		return danweiName1;
	}

	public void setDanweiName1(String danweiName1) {
		this.danweiName1 = danweiName1;
	}

	public String getPinpaiName() {
		return pinpaiName;
	}

	public void setPinpaiName(String pinpaiName) {
		this.pinpaiName = pinpaiName;
	}

}
