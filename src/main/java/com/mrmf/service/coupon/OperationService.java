package com.mrmf.service.coupon;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.coupon.Coupon;
import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.osg.entity.FlipInfo;

import java.util.List;

/**
 * 优惠券发放配置，优惠券相关操作
 * Created by 蔺哲 on 2017/3/9.
 */
public interface OperationService {
    public CouponGrant queryCouponGrant(String grantId);//查找发放配置
    public String upDateGrant(CouponGrant grant);//保存发放配置
    public String AddCoupon(Coupon coupon);//保存优惠券
    public FlipInfo<Organ> queryOrgan(FlipInfo<Organ> fpi);//模糊查询店面
    public List<Bigsort> queryBig(String organId,String typeName);//查找服务大类
    public List<Smallsort> querySmall(String bigcode);//查找服务小类
    public Coupon queryOneCoupon(String couponId);//根据id查找优惠券
    public Organ queryShopType(String organId);//查询单个店面
    public List<Code> queryCode(String type);//查询平台类型的大类
}
