package com.mrmf.entity.organPisition;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * Created by 蔺哲 on 2017/4/10.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FormPositionBean {
    private String organId;
    private String staffId;
    private int num;
    private List<String> timeList;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }
}
