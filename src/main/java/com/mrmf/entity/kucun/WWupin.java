package com.mrmf.entity.kucun;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 物品类别表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WWupin extends DataEntity {
	private String organId;// 公司id（总公司id）
	private String code;// 产品编码（条码号）
	private String mingcheng;// 产品名称
	private double price_xs;// 销售价格
	private Map<String, Double> price_xss;// 各子公司产品销售价格定义，key为子公司id，value为子公司价格

	private double price;// 进货价格
	private double price_ch;// 出货价格
	private String bumen;// 部门
	private int ticheng;// 员工提成(%)
	private double jingjie;// 报警数量
	private int jingjieday;// 报警天数
	private String zjfCode;// 助记符
	private double weight;// 每瓶净含量
	private String danwei;// 单位(瓶、盒等,wupinUnit)
	private String guige;// 规格
	private String zupanhao;// 组盘号
	private String pinpai;// 品牌

	// 冗余字段
	private String bumenName;// 部门名称
	private String danweiName;// 单位名称
	private String pinpaiName;// 品牌名称

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMingcheng() {
		return mingcheng;
	}

	public void setMingcheng(String mingcheng) {
		this.mingcheng = mingcheng;
	}

	public double getPrice_xs() {
		return price_xs;
	}

	public void setPrice_xs(double price_xs) {
		this.price_xs = price_xs;
	}

	public Map<String, Double> getPrice_xss() {
		if (price_xss == null) {
			price_xss = new HashMap<String, Double>();
		}
		return price_xss;
	}

	public void setPrice_xss(Map<String, Double> price_xss) {
		this.price_xss = price_xss;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice_ch() {
		return price_ch;
	}

	public void setPrice_ch(double price_ch) {
		this.price_ch = price_ch;
	}

	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}

	public int getTicheng() {
		return ticheng;
	}

	public void setTicheng(int ticheng) {
		this.ticheng = ticheng;
	}

	public double getJingjie() {
		return jingjie;
	}

	public void setJingjie(double jingjie) {
		this.jingjie = jingjie;
	}

	public int getJingjieday() {
		return jingjieday;
	}

	public void setJingjieday(int jingjieday) {
		this.jingjieday = jingjieday;
	}

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
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

	public String getBumenName() {
		return bumenName;
	}

	public void setBumenName(String bumenName) {
		this.bumenName = bumenName;
	}

	public String getDanweiName() {
		return danweiName;
	}

	public void setDanweiName(String danweiName) {
		this.danweiName = danweiName;
	}

	public String getPinpaiName() {
		return pinpaiName;
	}

	public void setPinpaiName(String pinpaiName) {
		this.pinpaiName = pinpaiName;
	}

}
