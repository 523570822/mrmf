package com.mrmf.entity.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 服务大类 原bigsort表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Bigsort extends DataEntity {

	private String organId; // 所属公司id
	private String name;// 大类名称，如洗剪吹
	private String typeName;//所属的店面类型
	private String codeName;//平台大类名称
	private String codeId;//平台大类ID
	private Boolean delete_flag;// 是否删除
	private Boolean baobiao_flag;// 是否出现在报表
	private Boolean meifa_flag;// 技师业绩报表是否显示
	private Boolean jszd_flag;// 技师诊断报表是否显示

	public String getTypeName() {
		return typeName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}

	public Boolean getBaobiao_flag() {
		return baobiao_flag;
	}

	public void setBaobiao_flag(Boolean baobiao_flag) {
		this.baobiao_flag = baobiao_flag;
	}

	public Boolean getMeifa_flag() {
		return meifa_flag;
	}

	public void setMeifa_flag(Boolean meifa_flag) {
		this.meifa_flag = meifa_flag;
	}

	public Boolean getJszd_flag() {
		return jszd_flag;
	}

	public void setJszd_flag(Boolean jszd_flag) {
		this.jszd_flag = jszd_flag;
	}

}
