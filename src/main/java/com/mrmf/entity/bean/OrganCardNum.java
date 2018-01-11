package com.mrmf.entity.bean;

import java.math.BigDecimal;

import com.osg.entity.DataEntity;

public class OrganCardNum extends DataEntity {
	
	private int delNum; //删除的数量
	private int num;   //正常使用的数量
	

	public int getDelNum() {
		return delNum;
	}

	public void setDelNum(int delNum) {
		this.delNum = delNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	

	
}
