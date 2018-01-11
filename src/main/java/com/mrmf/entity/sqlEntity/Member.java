package com.mrmf.entity.sqlEntity;

/**
 * 商城用户表
 * Created by 蔺哲 on 2017/9/16.
 */
public class Member {
    private Long id;
    private Double price;
    private String phone;
    private Double prestore;

    public Double getPrestore() {
        return prestore;
    }

    public void setPrestore(Double prestore) {
        this.prestore = prestore;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
