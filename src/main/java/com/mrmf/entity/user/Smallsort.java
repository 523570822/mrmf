package com.mrmf.entity.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 店面价目表（服务项目表） 原smallsort表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Smallsort extends DataEntity {

	private String organId;// 所属公司id
	private String bigcode;// 大类编码
	private String zjfCode; // 助记符
	private String name;// 项目名称，如美发
	private double price;// 单价
	private double havemoney;// 提成金额
	private Boolean delete_flag;// 是否删除
	private Boolean charge_flag;// 小活标记
	private int small_time;// 做活时间，单位分钟
	private double price_chengben;// 成本
	private Boolean piliang_flag;// 批量设置提成识别
	private String jieshao;// 项目介绍
	private Boolean valid; // 是否有效

	// 以下为非存储字段
	private String bigsortName; // 大类名称

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getBigcode() {
		return bigcode;
	}

	public void setBigcode(String bigcode) {
		this.bigcode = bigcode;
	}

	public String getZjfCode() {
		return zjfCode;
	}

	public void setZjfCode(String zjfCode) {
		this.zjfCode = zjfCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getHavemoney() {
		return havemoney;
	}

	public void setHavemoney(double havemoney) {
		this.havemoney = havemoney;
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public Boolean getCharge_flag() {
		return charge_flag;
	}

	public void setCharge_flag(Boolean charge_flag) {
		this.charge_flag = charge_flag;
	}

	public int getSmall_time() {
		return small_time;
	}

	public void setSmall_time(int small_time) {
		this.small_time = small_time;
	}

	public double getPrice_chengben() {
		return price_chengben;
	}

	public void setPrice_chengben(double price_chengben) {
		this.price_chengben = price_chengben;
	}

	public Boolean getPiliang_flag() {
		return piliang_flag;
	}

	public void setPiliang_flag(Boolean piliang_flag) {
		this.piliang_flag = piliang_flag;
	}

	public String getJieshao() {
		return jieshao;
	}

	public void setJieshao(String jieshao) {
		this.jieshao = jieshao;
	}

	public String getBigsortName() {
		return bigsortName;
	}

	public void setBigsortName(String bigsortName) {
		this.bigsortName = bigsortName;
	}

	public Boolean getValid() {
		if (valid == null)
			valid = false;
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}
