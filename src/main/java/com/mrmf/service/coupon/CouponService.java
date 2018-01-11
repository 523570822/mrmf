package com.mrmf.service.coupon;

import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.coupon.MyCoupon;
import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

import java.util.List;

/**
 * Created by Lin on 2017/3/5.
 */
public interface CouponService {
    public void Query();
    public List<CouponGrant> QueryGrant(String bus);
    public String addCoupon(CouponGrant coupon);
    public List QueryCoupon(String business);

    /**
     * 禁用发放配置
     * @param couponGrant
     * @return
     */
    public ReturnStatus disable(String couponGrant);
    public ReturnStatus disable2(String coupon);

    /**
     * 开启发放配置
     * @param couponGrant
     * @return
     */
    public ReturnStatus enable(String couponGrant);
    public ReturnStatus enable2(String coupon);

    /**
     * 查询符合当前消费的优惠券
     * @param userId
     * @param bigsort
     * @return
     */
    public List<MyCoupon> queryMyCoupon(String userId, Bigsort bigsort,double price);

    /**
     * 根据id查我的优惠券
     * @param MyCoupomId
     * @return
     */
    public MyCoupon queryMyCouponById(String MyCoupomId);
}
