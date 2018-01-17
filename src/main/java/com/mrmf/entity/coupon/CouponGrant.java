package com.mrmf.entity.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.util.Date;
/**
 * 代金券配置表
 * Created by 蔺哲 on 2017/3/7.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CouponGrant extends DataEntity {
    private String grantId;//发放配置表id
    private String type;//代金券发放类型
    private String record;//店面名称
    private String recordId;//店面id
    private String business;//业务(注册，分享)
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;//开始时间
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;//截至时间
    private Integer minCondition;//最小消费
    private Integer maxCondition;//最大消费
    private Integer singleReceive;//单人领取次数
    private Integer totalReceive;//总领取次数
    private double  totalNumber;//发放的总张数
    private double  maxCashNum;//现金红包最大张数
    private String state;


    public double getMaxCashNum() {
        return maxCashNum;
    }

    public void setMaxCashNum(double maxCashNum) {
        this.maxCashNum = maxCashNum;
    }

    public double getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(double totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getGrantId() {
        return grantId;
    }

    public void setGrantId(String grantId) {
        this.grantId = grantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    public Integer getMinCondition() {
        return minCondition;
    }

    public void setMinCondition(Integer minCondition) {
        this.minCondition = minCondition;
    }

    public Integer getMaxCondition() {
        return maxCondition;
    }

    public void setMaxCondition(Integer maxCondition) {
        this.maxCondition = maxCondition;
    }

    public Integer getSingleReceive() {
        return singleReceive;
    }

    public void setSingleReceive(Integer singleReceive) {
        this.singleReceive = singleReceive;
    }

    public Integer getTotalReceive() {
        return totalReceive;
    }

    public void setTotalReceive(Integer totalReceive) {
        this.totalReceive = totalReceive;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
