package com.mrmf.entity.sqlEntity;

import java.sql.Timestamp;

/**
 * 商城消息表
 * Created by 蔺哲 on 2017/9/16.
 */
public class Information {
    private Timestamp createDate;
    private Timestamp modifyDate;
    private Long version;
    private String title;

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
