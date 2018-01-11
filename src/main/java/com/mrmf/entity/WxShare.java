package com.mrmf.entity;

import com.osg.entity.DataEntity;
import com.osg.entity.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liuzhen on 17/4/27.
 */
public class WxShare extends DataEntity{
    private String userId;
    private String inviatorId;
    private String createdTime;
    private String shareTime;//分享次数
    private List<User> shareUserList;
    public WxShare(String inviatorId){
        this.inviatorId = inviatorId;
        this.shareTime = "3";
        createTime = new Date();
    }

    public List<User> getShareUserList() {
        return shareUserList;
    }

    public void setShareUserList(List<User> shareUserList) {
        this.shareUserList = shareUserList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInviatorId() {
        return inviatorId;
    }

    public void setInviatorId(String inviatorId) {
        this.inviatorId = inviatorId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }
}
