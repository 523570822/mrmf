package com.mrmf.service.VipMember;

import com.mrmf.entity.Organ;
import com.mrmf.entity.shop.CheckVipGoodsHistory;
import com.mrmf.entity.shop.VipMember;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 蔺哲 on 2017/6/27.
 */
public interface VipMemberService {
    public FlipInfo<VipMember> query(FlipInfo<VipMember> fpi);
    public ReturnStatus upsert(VipMember vipMember);
    public ReturnStatus updateState(String vipMemberId, String state);
    public VipMember queryById(String vipMemberId);
    public ReturnStatus shenhe(HttpServletRequest request);
    public FlipInfo queryList(FlipInfo fpi);
    public ReturnStatus updateOrganMember(Organ organ,String newPhone);
    public double findPrestore(String organId);
    public boolean updateMemberByPhone(String oldPhone,String newPhone);
    public FlipInfo<CheckVipGoodsHistory> queryHistory(FlipInfo fpi);
}
