package com.mrmf.service.coupon;

import com.mrmf.entity.User;
import com.osg.entity.ReturnStatus;
import org.omg.CORBA.DoubleHolder;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by liuzhen on 17/3/15.
 */

public interface CouponGrantService {
    /**
     * 发放优惠券
     * @param userId
     * @param type  关注、注册、分享、消费
     */
    @Async
    public ReturnStatus grantCouponByuserUuidAndType(String userId, String type, double price, String shopId);

    /**
     * 分享抢红包
     */
    public void grabRedWallet(User user, String shareUserId);

}
