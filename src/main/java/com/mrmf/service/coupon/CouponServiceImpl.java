package com.mrmf.service.coupon;


import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.coupon.Coupon;
import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.coupon.MyCoupon;
import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lin on 2017/3/5.
 */
@Service("couponService")
public class CouponServiceImpl implements CouponService {
    @Autowired
    EMongoTemplate mongoTemplate;
    @Override
    public void Query() {
        System.out.println("被调用");
    }

    /**
     * 查发放配置
     * @param
     * @return
     */
    public List<CouponGrant> QueryGrant(String bus) {
        //fpi = mongoTemplate.findByPage(null, fpi, CouponGrant.class);
        List list = new ArrayList<CouponGrant>();
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"_id")));
        if(StringUtils.isEmpty(bus)){
            list = mongoTemplate.find(query,CouponGrant.class);
        }else{
            list = mongoTemplate.find(Query.query(Criteria.where("business").is(bus)).with(new Sort(new Sort.Order(Sort.Direction.DESC,"_id"))),CouponGrant.class);
        }
        //System.out.println("集合长度"+list.size());
        return list;
    }

    /**
     * 保存发放配置
     * @param
     */
    public String addCoupon(CouponGrant couponGrant){
        if(StringUtils.isEmpty(couponGrant.get_id())){
            couponGrant.setStartTime(new Date());
            couponGrant.setState("是");
        }
        mongoTemplate.save(couponGrant);
        String aa = couponGrant.get_id();
        return aa;
    }
    /**
     * 根据配发id显示代金券
     */
    public List QueryCoupon(String grantId){
        //System.err.println("根据配发id显示代金券");
        List list = mongoTemplate.find(Query.query(Criteria.where("grantId").is(grantId)).with(new Sort(new Sort.Order(Sort.Direction.DESC,"_id"))),Coupon.class);
        return list;
    }

    /**
     * 禁用发放配置
     * @param couponGrant
     * @return
     */
    public ReturnStatus disable(String couponGrant){
        ReturnStatus result;
        CouponGrant grant = mongoTemplate.findById(couponGrant, CouponGrant.class);
        if (grant == null) {
            result = new ReturnStatus(false);
            result.setMessage("指定id的公司信息不存在");
        } else {
            grant.setState("否");
            mongoTemplate.save(grant);
            result = new ReturnStatus(true);
            result.setEntity(grant); // 返回更新后的对象
        }

        return result;
    }
    public ReturnStatus disable2(String coupon){
        ReturnStatus result;
        Coupon cou = mongoTemplate.findById(coupon, Coupon.class);
        if (cou == null) {
            result = new ReturnStatus(false);
            result.setMessage("指定id的公司信息不存在");
        } else {
            cou.setState("否");
            mongoTemplate.save(cou);
            result = new ReturnStatus(true);
            result.setEntity(cou); // 返回更新后的对象
        }

        return result;
    }


    /**
     * 开启发放配
     * @param couponGrant
     * @return
     */
    public ReturnStatus enable(String couponGrant){
        ReturnStatus result;
        CouponGrant grant = mongoTemplate.findById(couponGrant, CouponGrant.class);
        if (grant == null) {
            result = new ReturnStatus(false);
            result.setMessage("指定id的公司信息不存在");
        } else {
            grant.setState("是");
            mongoTemplate.save(grant);
            result = new ReturnStatus(true);
            result.setEntity(grant); // 返回更新后的对象
        }

        return result;
    }
    public ReturnStatus enable2(String coupon){
        ReturnStatus result;
        Coupon cou = mongoTemplate.findById(coupon, Coupon.class);
        if (cou == null) {
            result = new ReturnStatus(false);
            result.setMessage("指定id的公司信息不存在");
        } else {
            cou.setState("是");
            mongoTemplate.save(cou);
            result = new ReturnStatus(true);
            result.setEntity(cou); // 返回更新后的对象
        }

        return result;
    }

    /**
     * 支付时查询符合条件的优惠券
     * @param userId
     * @param bigsort
     * @return
     */
    public List<MyCoupon> queryMyCoupon(String userId, Bigsort bigsort,double price){
        Criteria orCriteria = new Criteria();
        //orCriteria.orOperator(Criteria.where("moneyType").is(1),Criteria.where("moneyOrRatio").lte(price));
        Criteria criteria = new Criteria();
        Query query = new Query();
        criteria.and("userId").is(userId).and("couponType").in("平台","店面").and("shopId").in(bigsort.getOrganId(),"")
                .and("typeName").in(bigsort.getTypeName(),"通用类型").and("bigSortId").in(bigsort.getCodeId(),bigsort.get_id(),"通用大类").and("minConsume").lte(price)
                .and("endTime").gte(new Date()).and("isUsed").is("0");
        //criteria.andOperator(orCriteria);
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC,"moneyOrRatio"));
        List list= mongoTemplate.find(Query.query(criteria),MyCoupon.class);
        return list;
    }

    /**
     * 查id符合的优惠券
     * @param MyCoupomId
     * @return
     */
    public MyCoupon queryMyCouponById(String MyCoupomId){
        return mongoTemplate.findById(MyCoupomId,MyCoupon.class);
    }
}
