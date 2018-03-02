package com.mrmf.entity.stage;

import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * 镜台收费类别
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Video extends DataEntity{


    private String  name;//视频名称
    private String[]  organIds;//符合播放条件的视频
    private String city;// 城市，比如：北京



    private String district; // 区，比如：海淀区
    private String region; // 商圈，比如：回龙观
    private String videoUrl;//图片
    private String flag;  // 是否删除
    protected Date startTime;

    protected Date endTime;



    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String[] getOrganIds() {
        return organIds;
    }

    public void setOrganIds(String[] organIds) {
        this.organIds = organIds;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
