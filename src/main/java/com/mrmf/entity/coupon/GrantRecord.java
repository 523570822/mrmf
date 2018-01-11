package com.mrmf.entity.coupon;

import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by 蔺哲 on 2017/3/7.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GrantRecord extends DataEntity {
    private String recordId;//记录id
    private String couponId;//代金券id
    private String userID;//用户id
    private String business;//业务
    private String couponType;
    private String productClass;//代金产品
    private Date startTime;
    private Date endTime;//截至时间
    private int minMoney;//代金卷最小金额
    private int maxMoney;//代金卷最大金额
    private double minRatio;//比例小
    private double maxRatio;//比例大
    private int minConsume;//最低消费
    private String seate;//是否生效

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

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

    public int getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(int maxMoney) {
        this.maxMoney = maxMoney;
    }

    public double getMinRatio() {
        return minRatio;
    }

    public void setMinRatio(double minRatio) {
        this.minRatio = minRatio;
    }

    public double getMaxRatio() {
        return maxRatio;
    }

    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    public int getMinConsume() {
        return minConsume;
    }

    public void setMinConsume(int minConsume) {
        this.minConsume = minConsume;
    }

    public String getSeate() {
        return seate;
    }

    public void setSeate(String seate) {
        this.seate = seate;
    }
}
