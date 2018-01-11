package com.mrmf.entity.kaoqin;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 考勤班次定义
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KBancidingyi extends DataEntity {
	private String organId;// 公司id
	private String names;// 考勤项目名称
	private String time_a1;// 考勤上班时间
	private Boolean kaoqin_a1;// 上班是否考勤
	private String time_a2;// 考勤下班时间
	private Boolean kaoqin_a2;// 下班是否考勤
	private Boolean kuatian;// 是否跨天考勤
	private Boolean delete_flag;// 是否删除，0：否，1：是

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getTime_a1() {
		return time_a1;
	}

	public void setTime_a1(String time_a1) {
		this.time_a1 = time_a1;
	}

	public Boolean getKaoqin_a1() {
		return kaoqin_a1;
	}

	public void setKaoqin_a1(Boolean kaoqin_a1) {
		if(kaoqin_a1 != null) {
			this.kaoqin_a1 = kaoqin_a1;
		} else {
			this.kaoqin_a1 = false;
		}
	}

	public String getTime_a2() {
		return time_a2;
	}

	public void setTime_a2(String time_a2) {
		this.time_a2 = time_a2;
	}

	public Boolean getKaoqin_a2() {
		return kaoqin_a2;
	}

	public void setKaoqin_a2(Boolean kaoqin_a2) {
		if(kaoqin_a2 != null) {
			this.kaoqin_a2 = kaoqin_a2;
		} else {
			this.kaoqin_a2 = false;
		}
	}

	public Boolean getKuatian() {
		return kuatian;
	}

	public void setKuatian(Boolean kuatian) {
		if(kuatian != null) {
			this.kuatian = kuatian;
		} else {
			this.kuatian = false;
		}
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

}
