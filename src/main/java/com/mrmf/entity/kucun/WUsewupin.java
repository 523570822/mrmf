package com.mrmf.entity.kucun;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 产品使用表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WUsewupin extends DataEntity {

	private String organId;
	private String wupinId; // 物品id
	private String userpartId; // 关联消费记录id

	private double yongliang;// 用量
	private double money1;// 价钱
	private String smallsort;// 服务项目
	private String staff1Id; // 员工1

	private Boolean delete_flag; // 是否删除

	// 以下为非存储字段
	private String wupinName; // 物品名称
	private String id_2; // 会员编码
	private String cardno; // 卡表面号
	private String smallsortName; // 服务项目名称
	private String staff1Name; // 员工1姓名

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

	public String getUserpartId() {
		return userpartId;
	}

	public void setUserpartId(String userpartId) {
		this.userpartId = userpartId;
	}

	public double getYongliang() {
		return yongliang;
	}

	public void setYongliang(double yongliang) {
		this.yongliang = yongliang;
	}

	public double getMoney1() {
		return money1;
	}

	public void setMoney1(double money1) {
		this.money1 = money1;
	}

	public String getSmallsort() {
		return smallsort;
	}

	public void setSmallsort(String smallsort) {
		this.smallsort = smallsort;
	}

	public String getStaff1Id() {
		return staff1Id;
	}

	public void setStaff1Id(String staff1Id) {
		this.staff1Id = staff1Id;
	}

	public Boolean getDelete_flag() {
		if (delete_flag == null)
			delete_flag = false;
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getId_2() {
		return id_2;
	}

	public void setId_2(String id_2) {
		this.id_2 = id_2;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getWupinName() {
		return wupinName;
	}

	public void setWupinName(String wupinName) {
		this.wupinName = wupinName;
	}

	public String getSmallsortName() {
		return smallsortName;
	}

	public void setSmallsortName(String smallsortName) {
		this.smallsortName = smallsortName;
	}

	public String getStaff1Name() {
		return staff1Name;
	}

	public void setStaff1Name(String staff1Name) {
		this.staff1Name = staff1Name;
	}

}
