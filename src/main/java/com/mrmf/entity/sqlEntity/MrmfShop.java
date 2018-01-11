package com.mrmf.entity.sqlEntity;

import com.osg.framework.util.DateUtil;

import java.sql.Timestamp;

/**
 * Created by 蔺哲 on 2017/9/27.
 */
public class MrmfShop {
    private Long id;
    private Double commission;
    private Double prestore;
    private String organId;
    private Long memberId;
    private Timestamp createDate;
    private Timestamp modifyDate;
    private Long version;

    public MrmfShop(){}

    public MrmfShop(String organId,Long memberId){
        this.setOrganId(organId);
        this.setMemberId(memberId);
        this.setCreateDate(DateUtil.currentTimestamp());
        this.setModifyDate(DateUtil.currentTimestamp());
        this.setVersion(0L);
        this.setPrestore(0.0);
        this.setCommission(0.0);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getPrestore() {
        return prestore;
    }

    public void setPrestore(Double prestore) {
        this.prestore = prestore;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

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
}
