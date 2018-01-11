package com.mrmf.entity.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 蔺哲 on 2017/7/28.
 */
public class CouponRecordSum {
    private Date _id;
    private double total;
    public CouponRecordSum(Date date,Double total){
        this._id = date;
        this.total = total;
    }
    public CouponRecordSum(){}
    public Date get_id() {
        return _id;
    }

    public void set_id(Date _id) {
        this._id = _id;
    }

    public double getTotal() {
        return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
