package com.mrmf.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.osg.entity.DataEntity;
import com.osg.entity.util.EHDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * vip商品审核记录表
 * Created by 蔺哲 on 2017/10/10.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CheckVipGoodsHistory extends DataEntity{
    private String userName;
    private String phone;
    private String organId;//店铺id
    private Double price;//商品扣的钱
    private String goodsName;//商品名称
    private Long vipGoodsHistoryId;//审核商品订单id
    private int state;//审核状态
    private String msg;//描述
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;
    public CheckVipGoodsHistory(){}
    public CheckVipGoodsHistory(String organId,Double price,String goodsName,Long vipGoodsHistoryId,int state,String msg,String userName,String phone,Date checkTime){
        this.organId = organId;
        this.price = price;
        this.goodsName = goodsName;
        this.vipGoodsHistoryId = vipGoodsHistoryId;
        this.createTime = new Date();
        this.state = state;
        this.msg = msg;
        this.userName = userName;
        this.phone = phone;
        this.checkTime = checkTime;
    }
    @JsonSerialize(using = EHDateTimeSerializer.class)
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getVipGoodsHistoryId() {
        return vipGoodsHistoryId;
    }

    public void setVipGoodsHistoryId(Long vipGoodsHistoryId) {
        this.vipGoodsHistoryId = vipGoodsHistoryId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
