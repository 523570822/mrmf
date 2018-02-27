package com.mrmf.entity.stage;

import com.osg.entity.AndroidPoint;
import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StageMent extends DataEntity {
    private String organId; // 所属公司id


    private List<AndroidPoint> androidPoints;//镜台设备编码



    public List<AndroidPoint> getAndroidPoints() {
        return androidPoints;
    }

    public void setAndroidPoints(List<AndroidPoint> androidPoints) {
        this.androidPoints = androidPoints;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }









}

