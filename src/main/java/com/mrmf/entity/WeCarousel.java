package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
/**
 * 微信端首页轮播图实体类
 * @author yangshaodong
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeCarousel extends DataEntity{
	private String img;    // 轮播图片
	private Boolean flag;  // 是否删除
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
}
