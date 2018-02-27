
package com.mrmf.entity.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 开单表，原member_kaidan表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Kaidan extends DataEntity {

	private String kaidan_id;// 开单号
	private String organId;// 公司id
	private Boolean guazhang_flag;// 是否挂账

	private double yujiesuan_money;// 预结算金额

	public String getKaidan_id() {
		return kaidan_id;
	}

	public void setKaidan_id(String kaidan_id) {
		this.kaidan_id = kaidan_id;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Boolean getGuazhang_flag() {
		return guazhang_flag;
	}

	public void setGuazhang_flag(Boolean guazhang_flag) {
		this.guazhang_flag = guazhang_flag;
	}

	public double getYujiesuan_money() {
		return yujiesuan_money;
	}

	public void setYujiesuan_money(double yujiesuan_money) {
		this.yujiesuan_money = yujiesuan_money;
	}

}
