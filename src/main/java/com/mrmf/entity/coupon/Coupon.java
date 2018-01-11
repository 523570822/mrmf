package com.mrmf.entity.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * Created by Lin on 2017/3/7.
 * 代金券表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Coupon extends DataEntity {
    private String couponType;//代金券类型
    private String shopName;//店面名字
    private String shopId;//店面id
    private String grantId;//发放配置ID
    private String typeName;//店铺类型
    private String bigSort;//服务大类
    private String bigSortName;//大类名称
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;//开始时间
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;//截至时间
    private Integer favourableType;//代金类型
    private String minValue;//代金卷最小金额
    private String maxValue;//代金卷最大金额
    private Integer minConsume;//最低消费
    private String state;//是否生效  是或否
    private Boolean donate;//是否允许转增
    private int  maxTime;//优惠券被最大领取次数
    private int  isUsedTime;//被抢的次数
    public int getIsUsedTime() {
        return isUsedTime;
    }
    public void setIsUsedTime(int isUsedTime) {
        this.isUsedTime = isUsedTime;
    }

    public int getMaxTime() {
        return maxTime;
    }
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public Boolean getDonate() {
        return donate;
    }
    public void setDonate(Boolean donate) {
        this.donate = donate;
    }
    public Integer getFavourableType() {
        return favourableType;
    }
    public void setFavourableType(Integer favourableType) {
        this.favourableType = favourableType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBigSortName() {
        return bigSortName;
    }

    public void setBigSortName(String bigSortName) {
        this.bigSortName = bigSortName;
    }


    public String getBigSort() {
        return bigSort;
    }

    public void setBigSort(String bigSort) {
        this.bigSort = bigSort;
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

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    @JsonSerialize(using = EHDateTimeSerializer.class)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @JsonSerialize(using = EHDateTimeSerializer.class)
    public Date getEndTime() {
        return endTime;
    }
    @JsonSerialize(using = EHDateTimeSerializer.class)
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getMinConsume() {
        return minConsume;
    }

    public void setMinConsume(Integer minConsume) {
        this.minConsume = minConsume;
    }

}