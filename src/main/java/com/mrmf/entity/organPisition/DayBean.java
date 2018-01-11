package com.mrmf.entity.organPisition;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * 前台展示日历属性，不存入数据库
 * Created by 蔺哲 on 2017/4/10.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DayBean {
    private Integer title;//日标题
    private String state;//日状态
    private String time;//所在时间
    private Integer month;//所在月份
    private Integer flag;//店铺日工位租赁数量

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
