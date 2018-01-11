package com.mrmf.entity.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔺哲 on 2017/7/26.
 */
public class StatisticsBean {
    private int num;
    private String date;
    private String status;
    private Date dayDate;
    public StatisticsBean(){}
    public StatisticsBean(Date dayDate, String status){
        this.dayDate=dayDate;
        this.status=status;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
