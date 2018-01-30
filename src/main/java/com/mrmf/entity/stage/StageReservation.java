package com.mrmf.entity.stage;

import java.util.Date;
import java.util.List;

/**
 * 技师支付成功镜台记录页面
 */
public class StageReservation {
    private String  stageId; // 镜台ID
    private List<String> devicedId; //所属镜台的设备码
    private String phone;    //技师手机号
    private Date startTime;// 该技师的开始使用时间
    private Date endTime;//  该技师结束使用时间

    private String money; //当时支付的总金额

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public List<String> getDevicedId() {
        return devicedId;
    }

    public void setDevicedId(List<String> devicedId) {
        this.devicedId = devicedId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
