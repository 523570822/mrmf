package com.mrmf.entity.sqlEntity;

/**
 * Created by 蔺哲 on 2017/9/1.
 */
public class Orders {
    private String id;
    private String phone;
    private int price;
    private String consignee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
}
