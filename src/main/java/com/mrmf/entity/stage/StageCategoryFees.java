package com.mrmf.entity.stage;

import com.osg.entity.DataEntity;

/**
 * 镜台收费类别
 */
public class StageCategoryFees  extends DataEntity{

 private String  name;//类别名称
    private Double leaseMoney;//单日镜台租金
    private Double platRentSharing;//平台租金分成
    private Double shopRentSharing;//店铺租金分成
    private Double staffEarningsSharing;//技师收益分成
    private Double platEarningsSharing;//平台收益分成
    private Double shopEarningsSharing;//店铺收益分成.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLeaseMoney() {
        return leaseMoney;
    }

    public void setLeaseMoney(Double leaseMoney) {
        this.leaseMoney = leaseMoney;
    }

    public Double getPlatRentSharing() {
        return platRentSharing;
    }

    public void setPlatRentSharing(Double platRentSharing) {
        this.platRentSharing = platRentSharing;
    }

    public Double getShopRentSharing() {
        return shopRentSharing;
    }

    public void setShopRentSharing(Double shopRentSharing) {
        this.shopRentSharing = shopRentSharing;
    }

    public Double getStaffEarningsSharing() {
        return staffEarningsSharing;
    }

    public void setStaffEarningsSharing(Double staffEarningsSharing) {
        this.staffEarningsSharing = staffEarningsSharing;
    }

    public Double getPlatEarningsSharing() {
        return platEarningsSharing;
    }

    public void setPlatEarningsSharing(Double platEarningsSharing) {
        this.platEarningsSharing = platEarningsSharing;
    }

    public Double getShopEarningsSharing() {
        return shopEarningsSharing;
    }

    public void setShopEarningsSharing(Double shopEarningsSharing) {
        this.shopEarningsSharing = shopEarningsSharing;
    }
}
