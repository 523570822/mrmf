package com.mrmf.entity;

import com.mrmf.entity.coupon.MyCoupon;
import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * 店铺工位设置
 * Created by 蔺哲 on 2017/3/27.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrganPositionSetting extends DataEntity {
    private String organId;//店铺Id
    private Integer leaseType;//租赁类型 0-租金模式  1-分成模式
    private Integer leaseMoney;//租金
    private Integer num;//工位数量
    private List<String> images;//工位图片介绍
    private String describe;//工位描述  例如提供设施
    private List<PositionSetting> positionSettingsList;//区间配置对象的集合
    private int state;//配置状态 0-已开启  1-已关闭

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Integer getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(Integer leaseType) {
        this.leaseType = leaseType;
    }

    public Integer getLeaseMoney() {
        return leaseMoney;
    }

    public void setLeaseMoney(Integer leaseMoney) {
        this.leaseMoney = leaseMoney;
    }

    public List<PositionSetting> getPositionSettingsList() {
        return positionSettingsList;
    }

    public void setPositionSettingsList(List<PositionSetting> positionSettingsList) {
        this.positionSettingsList = positionSettingsList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
