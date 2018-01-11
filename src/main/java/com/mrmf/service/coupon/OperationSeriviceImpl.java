package com.mrmf.service.coupon;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.coupon.Coupon;
import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.osg.entity.FlipInfo;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 蔺哲 on 2017/3/9.
 */
@Service
public class OperationSeriviceImpl implements OperationService{
    @Autowired
    EMongoTemplate mongoTemplate;

    /**
     * 根据配置id查到详细信息
     * @param grantId
     * @return
     */
    public CouponGrant queryCouponGrant(String grantId){
        CouponGrant grant = mongoTemplate.findById(grantId,CouponGrant.class);
        return grant;
    }

    /**
     * 修改配置
     * @param grant
     * @return
     */
    public String upDateGrant(CouponGrant grant){
        mongoTemplate.save(grant);
        return grant.get_id();
    }

    /**
     * 新增和修改优惠券
     * @param coupon
     * @return
     */
    public  String AddCoupon(Coupon coupon){
        if (StringUtils.isEmpty(coupon.get_id())) {
            mongoTemplate.save(coupon);
            return coupon.get_id();
        }else{
            mongoTemplate.save(coupon);
            return coupon.get_id();
        }
    }

    /**
     * 模糊查询店面
     * @return
     */
    public FlipInfo<Organ> queryOrgan(FlipInfo<Organ> fpi){
        fpi.setSortField("createTime");
        fpi.setSortOrder("DESC");
        fpi = mongoTemplate.findByPage(Query.query(Criteria.where("valid").is(1)), fpi, Organ.class);
        return fpi;
    }
    /**
     * 查询店铺
     */
    public Organ queryShopType(String organId){
        Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)),Organ.class);
        return organ;
    }
    /**
     * 查询大类
     * @param organId
     * @return
     */
    public List<Bigsort> queryBig(String organId,String typeName){
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("typeName").is(typeName));
        return mongoTemplate.find(Query.query(criteria),Bigsort.class);
    }

    /**
     * 查询小类
     * @param bigcode
     * @return
     */
    public List<Smallsort> querySmall(String bigcode){
        return mongoTemplate.find(Query.query(Criteria.where("bigcode").is(bigcode)),Smallsort.class);
    }

    /**
     * 根据id查找优惠券
     * @param couponId
     * @return
     */
    public Coupon queryOneCoupon(String couponId){
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(couponId)),Coupon.class);
    }
    /**
     * 根据类型查询服务大类
     */
    public List<Code> queryCode(String type){
        return mongoTemplate.find(Query.query(Criteria.where("type").is(type)),Code.class);
    }
}
