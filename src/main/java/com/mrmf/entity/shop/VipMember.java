package com.mrmf.entity.shop;

import com.mrmf.entity.User;
import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by 蔺哲 on 2017/6/27.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VipMember extends DataEntity {
    private String organId;//店铺id
    private String phone;//vip手机号
    private String userId;//用户id
    private boolean state;// false-不可用 true-可用
    //冗余字段
    private String name;//用户名称
    private User user;//用户对象
    public VipMember(){
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
