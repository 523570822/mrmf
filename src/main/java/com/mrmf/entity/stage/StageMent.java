package com.mrmf.entity.stage;

import com.osg.entity.AndroidPoint;
import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StageMent extends DataEntity {
    private String organId; // 所属公司id
    private String name; // 名称
    private String status;//镜台状态（0：开启中，1 关闭中）
    private List<AndroidPoint> androidPoints;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        if (status == null || "".equals(status)) {
            this.status ="1";
        }else {
            this.status = status;
        }

    }



}

