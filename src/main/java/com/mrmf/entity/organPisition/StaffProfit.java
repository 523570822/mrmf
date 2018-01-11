package com.mrmf.entity.organPisition;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by 蔺哲 on 2017/4/19.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StaffProfit {
    private String title;//服务名称
    private String staffName;//技师昵称
    private String time;//分到钱的时间 hh mm
    private double profit;//本次服务收到的金额

    public StaffProfit(String title, String staffName, String time, double profit){
        this.setTitle(title);
        this.setStaffName(staffName);
        this.setTime(time);
        this.setProfit(profit);
    }
    public StaffProfit(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
