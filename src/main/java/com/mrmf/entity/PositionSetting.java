package com.mrmf.entity;

import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 区间配置的集合，不单独存储
 * Created by 蔺哲 on 2017/3/28.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PositionSetting extends DataEntity {
    private Integer qu1;//区间起
    private Integer qu2;//区间至
    private Double organAmount;//店面分成
    private Double staffAmount;//技师分成
    private Double adminAmount;//平台分成

    public Integer getQu1() {
        return qu1;
    }

    public void setQu1(Integer qu1) {
        this.qu1 = qu1;
    }

    public Integer getQu2() {
        return qu2;
    }

    public void setQu2(Integer qu2) {
        this.qu2 = qu2;
    }

    public Double getOrganAmount() {
        return organAmount;
    }

    public void setOrganAmount(Double organAmount) {
        this.organAmount = organAmount;
    }

    public Double getStaffAmount() {
        return staffAmount;
    }

    public void setStaffAmount(Double staffAmount) {
        this.staffAmount = staffAmount;
    }

    public Double getAdminAmount() {
        return adminAmount;
    }

    public void setAdminAmount(Double adminAmount) {
        this.adminAmount = adminAmount;
    }
}
