package com.mrmf.entity;

import com.osg.entity.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by liuzhen on 17/4/12.
 */
public class Bak extends DataEntity{
    private String name;//实体名
    private Date  cretateTime;
    public static final String TYPE_ADMIN = "admin";
    public static final String TYPE_ORGAN_ADMIN = "organ";
    public static final String TYPE_STAFF = "staff";
    public static final String TYPE_USER = "user";

    public static final String STATUS_FAILED = "0";
    public static final String STATUS_ACTIVE = "1";
    public static final String STATUS_LOCK = "5";

    private String accountName; // 登陆用户名（为账号、微信号openid）
    private String accountPwd; // 微信号时为openid前10位md5
    private String status;// 状态，0禁用；1启用
    private String accountType;// 账号类型，admin:超级管理员;organ:店铺管理员;staff:技师;user:用户
    private String entityID;// 对应实体id:user普通用户类型对应user表id;organ类型对应organ表id;staff类型对应staff表id
    private String weUnionId; // 微信unionId，同一微信用户关注不同公众号时此id相同
    private List<String> roleIds;// 账号拥有的角色id列表（accountType为organ时使用）

    private List<String> cityList; // 管理员可管理的城市(accountType为admin时使用)
    private List<String> districtList; // 管理员可管理的区域(accountType为admin时使用)

    public Bak(){
        createTime = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getCretateTime() {
        return cretateTime;
    }

    public void setCretateTime(Date cretateTime) {
        this.cretateTime = cretateTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getWeUnionId() {
        return weUnionId;
    }

    public void setWeUnionId(String weUnionId) {
        this.weUnionId = weUnionId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public List<String> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<String> districtList) {
        this.districtList = districtList;
    }
}
