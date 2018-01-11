package com.mrmf.entity.organPisition;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 用于页面展示，不存入数据库
 * Created by 蔺哲 on 2017/4/7.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PositionBean {
    private boolean toDaySeate;//当天租赁情况
    private Integer currentMonth;//当月可租赁天数
    private Integer nextMonth;//下月可租赁天数
    private Integer num;//工位编号

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public boolean isToDaySeate() {
        return toDaySeate;
    }

    public void setToDaySeate(boolean toDaySeate) {
        this.toDaySeate = toDaySeate;
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth) {
        this.currentMonth = currentMonth;
    }

    public Integer getNextMonth() {
        return nextMonth;
    }

    public void setNextMonth(Integer nextMonth) {
        this.nextMonth = nextMonth;
    }
}
