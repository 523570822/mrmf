package com.mrmf.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SensitiveWords extends DataEntity{

private String word;//敏感词
private Boolean del_flag;//是否有效


public Boolean getDel_flag() {
	return del_flag;
}

public void setDel_flag(Boolean del_flag) {
	this.del_flag = del_flag;
}

public String getWord() {
	return word;
}

public void setWord(String word) {
	this.word = word;
}

}
