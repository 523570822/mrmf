package com.mrmf.entity.sqlEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.osg.entity.util.EHDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by 蔺哲 on 2017/9/18.
 */
public class Prestore {
    private Long id;
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private Double money;
    private String organId;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonSerialize(using = EHDateTimeSerializer.class)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
