package com.mrmf.entity.organPisition;

import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 店铺工位详细状态
 * Created by 蔺哲 on 2017/4/1.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrganPositionDetails  extends DataEntity {
    private String positionOrderId;//订单id
    private String organId;//店铺Id
    private Integer num;//店铺工位编号
    private Date time;//日期
    private Integer state;//租赁状态 0-审核 1-生效 2-不生效
    public OrganPositionDetails(){

    }
    public OrganPositionDetails(String positionOrderId,String organId,int num,Date time,Integer state){
       this.setPositionOrderId(positionOrderId);
       this.setOrganId(organId);
       this.setNum(num);
       this.setTime(time);
       this.setState(state);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPositionOrderId() {
        return positionOrderId;
    }

    public void setPositionOrderId(String positionOrderId) {
        this.positionOrderId = positionOrderId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
