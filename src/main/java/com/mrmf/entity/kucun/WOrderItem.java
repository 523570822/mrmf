package com.mrmf.entity.kucun;

import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by Mj on 2017/6/22.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WOrderItem extends DataEntity {
    private String orderId;
    private double price;
    private double num;
    private double price_all;
    private String brand;
    private boolean auditStatus;
    private String goodsName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getPrice_all() {
        return price_all;
    }

    public void setPrice_all(double price_all) {
        this.price_all = price_all;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
