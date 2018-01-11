package com.mrmf.entity.coupon;

import com.osg.entity.DataEntity;
import com.osg.entity.util.AfterDateSerializer;
import com.osg.entity.util.EHDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * 优惠券发放和使用记录表
 * Created by 蔺哲 on 2017/7/21.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ConponRecord extends DataEntity {
    private String myCouponId;//优惠券Id
    private String type;//0代表发放记录，1代表使用记录
    private Date usedTime;//使用时间
    private String userId;//用户id
    private String userName;//用户姓名
    private String shopName;//店名
    private String shopId;//店id
    private String couponType;//代金券类型
    private String bigSortId;//服务大类
    private String bigSortName;//大类名称
    private double moneyOrRatio;//代金卷金额或折扣
    private Integer minConsume;//最低消费
    private Integer moneyType;//代金券金额或比例的类型  0代表金额，1代表比例
    private String description;// 代金券描述
    private String isUsed;//是否使用 默认为'0'
    @JsonSerialize(using = AfterDateSerializer.class)
    private Date endTime;//截至时间
    private String moneyTypeString;//代金券金额或比例类型
    public ConponRecord(MyCoupon coupon,String type,String name){
        if (this.createTime == null || "".equals(this.createTime)) {
            this.createTime = new Date();
        }
        this.myCouponId = coupon.get_id();
        this.type = type;
        this.userId = coupon.getUserId();
        this.shopName = coupon.getShopName();
        this.shopId = coupon.getShopId();
        this.couponType = coupon .getCouponType();
        this.bigSortId = coupon.getBigSortId();
        this.bigSortName = coupon.getBigSortName();
        this.moneyOrRatio = coupon.getMoneyOrRatio();
        this.minConsume = coupon.getMinConsume();
        this.moneyType = coupon.getMoneyType();
        this.description = coupon.getDescription();
        this.isUsed = coupon.getIsUsed();
        this.endTime = coupon.getEndTime();
        this.userName = name;
    }
    public ConponRecord(){}

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public String getMoneyTypeString() {
        return moneyTypeString;
    }

    public void setMoneyTypeString(String moneyTypeString) {
        this.moneyTypeString = moneyTypeString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMyCouponId() {
        return myCouponId;
    }

    public void setMyCouponId(String myCouponId) {
        this.myCouponId = myCouponId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getBigSortId() {
        return bigSortId;
    }

    public void setBigSortId(String bigSortId) {
        this.bigSortId = bigSortId;
    }

    public String getBigSortName() {
        return bigSortName;
    }

    public void setBigSortName(String bigSortName) {
        this.bigSortName = bigSortName;
    }

    public double getMoneyOrRatio() {
        return moneyOrRatio;
    }

    public void setMoneyOrRatio(double moneyOrRatio) {
        this.moneyOrRatio = moneyOrRatio;
    }

    public Integer getMinConsume() {
        return minConsume;
    }

    public void setMinConsume(Integer minConsume) {
        this.minConsume = minConsume;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
