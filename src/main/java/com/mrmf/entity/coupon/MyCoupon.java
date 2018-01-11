package com.mrmf.entity.coupon;

import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateSerializer;
import com.osg.entity.util.EHDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by liuzhen on 17/3/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MyCoupon extends DataEntity {
    private String couponId;//代金卷UUid
    private String userId;
    private String shopName;//店面名字
    private String shopId;//店面id
    private String grantId;//发放配置ID
    private String typeName;//店铺类型
    private String couponType;//代金券类型
    private String bigSortId;//服务大类
    private String bigSortName;//大类名称
    private Date startTime;
    @JsonSerialize(using = EHDateSerializer.class)
    private Date endTime;//截至时间
    private double moneyOrRatio;//代金卷金额或折扣
    private Integer minConsume;//最低消费
    private Integer moneyType;//代金券金额或比例的类型  0代表金额，1代表比例
    /*private Date   createTime;*/
    private String description;// 代金券描述
    private String isUsed;//是否使用 默认为'0'

    public MyCoupon(){
        isUsed = "0";
        createTime = new Date();
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getMoneyOrRatio() {
        return moneyOrRatio;
    }

    public void setMoneyOrRatio(double moneyOrRatio) {
        this.moneyOrRatio = moneyOrRatio;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
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

    public String getGrantId() {
        return grantId;
    }

    public void setGrantId(String grantId) {
        this.grantId = grantId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Integer getMinConsume() {
        return minConsume;
    }

    public void setMinConsume(Integer minConsume) {
        this.minConsume = minConsume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
}
