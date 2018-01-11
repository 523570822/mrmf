package com.mrmf.entity.organPisition;

import com.mrmf.entity.PositionSetting;
import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * 租赁行为表
 * Created by 蔺哲 on 2017/4/13.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PositionOrder extends DataEntity {
    private String organId;//店铺id
    private String organName;//店铺名称
    private String staffId;//技师id
    private String staffName;//技师名称
    private String timeString;//时间段
    private Integer leaseType;//租赁模式 0租赁 1分帐
    //private Date createTime;//创建时间
    private Double totalMoney;//总金额
    private Double leaseMoney;//单日租金
    private Integer totalDay;//总天数
    private Integer state;//审核状态  --0代表待审核  1代表已通过  2 未通过 3微信支付未完成
    private Date beginTime;//预约开始时间
    private Date endTime;//预约结束时间
    private Double wxMoney;//微信支付的金额
    private Double myMoney;//钱包支付的金额
    private List<PositionSetting> fenZhangList;  //分帐区间集合

    //非存储字段
    private String logo; // 店铺头像图片;
    private String address; // 店铺地址
    private Integer timeState;//未开始0 进行中1 已结束2
    private String city;// 城市，比如：北京
    private String district; // 区，比如：海淀区
    public  String getLogo() {
        return logo;
    }public void   setLogo(String logo) {
        this.logo = logo;
    }
    public Date getBeginTime() {
        return beginTime;
    }

    public List<PositionSetting> getFenZhangList() {
        return fenZhangList;
    }

    public void setFenZhangList(List<PositionSetting> fenZhangList) {
        this.fenZhangList = fenZhangList;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getWxMoney() {
        return wxMoney;
    }

    public void setWxMoney(Double wxMoney) {
        this.wxMoney = wxMoney;
    }

    public Double getMyMoney() {
        return myMoney;
    }

    public void setMyMoney(Double myMoney) {
        this.myMoney = myMoney;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Integer getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(Integer leaseType) {
        this.leaseType = leaseType;
    }


    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getLeaseMoney() {
        return leaseMoney;
    }

    public void setLeaseMoney(Double leaseMoney) {
        this.leaseMoney = leaseMoney;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTimeState() {
        return timeState;
    }

    public void setTimeState(Integer timeState) {
        this.timeState = timeState;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
