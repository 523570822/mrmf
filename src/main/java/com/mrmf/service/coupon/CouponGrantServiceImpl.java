package com.mrmf.service.coupon;

import com.mrmf.entity.Account;
import com.mrmf.entity.User;
import com.mrmf.entity.WxShare;
import com.mrmf.entity.coupon.ConponRecord;
import com.mrmf.entity.coupon.Coupon;
import com.mrmf.entity.coupon.CouponGrant;
import com.mrmf.entity.coupon.MyCoupon;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Configure;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.wxOAuth2.WXOAuth2Service;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.web.cache.CacheManager;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by liuzhen on 17/3/15.
 */

@Service("couponGrantService")
public class CouponGrantServiceImpl implements CouponGrantService {
    Logger _logger = Logger.getLogger(this.getClass());
    @Autowired
    private EMongoTemplate mongoTemplate;
    @Autowired
    private WXOAuth2Service wxoAuth2Service;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AccountService accountService;
    @Override
    @Async
    public ReturnStatus grantCouponByuserUuidAndType(String userId, String type, double price, String shopId) {//入参 type为bussiness
        try{
            System.out.println("grantCouponByuserUuidAndType userId"+userId);
            if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(type)){
                Query CouponGrantQuery = new Query(Criteria.where("business").is(type).and("type").is("平台").and("endTime").gte(new Date()).and("state").is("是"));
                List<CouponGrant> grantList = new ArrayList<>();
                grantList = mongoTemplate.find(CouponGrantQuery,CouponGrant.class);//平台下查询grant
                if(StringUtils.isNotEmpty(shopId)){
                    Query shopQuery = new Query(Criteria.where("business").is(type).and("type").is("店面").and("endTime").gte(new Date()).and("state").is("是"));
                    shopQuery.addCriteria(Criteria.where("shopId").is(shopId));
                    List list = new ArrayList();
                    list = mongoTemplate.find(CouponGrantQuery, CouponGrant.class);// 带消费查询
                    for (int index=0; index < list.size(); index++){
                        grantList.add((CouponGrant) list.get(index));//平台下所有业务查询
                    }
                }
                List<CouponGrant> couponGrantList = new ArrayList<>();
                List<Coupon> couponList = new ArrayList<>();



                if (grantList.size() > 0) {
                    if (price > 0) {
                        for (int index = 0; index < grantList.size(); index++) {
                            int minCondition = ObjectUtils.firstNonNull(grantList.get(index).getMinCondition(), 0);
                            int maxCondition = ObjectUtils.firstNonNull(grantList.get(index).getMaxCondition(), Integer.MAX_VALUE);
                            if (price >= minCondition && price <= maxCondition) {
                                couponGrantList.add(grantList.get(index));
                            }
                        }
                    } else {
                        for (int index = 0; index < grantList.size(); index++) {
                            couponGrantList.add(grantList.get(index));
                        }

                    }
                }

                if (couponGrantList.size() > 0){
                    int rNum =(int)(Math.random()*couponGrantList.size());
                    CouponGrant couponGrant = couponGrantList.get(rNum);
                    couponList = mongoTemplate.find(Query.query(Criteria.where("grantId").is(couponGrant.get_id()).and("state").is("是")),Coupon.class);
                    if (couponList.size() > 0){
                        for (int i = 0 ;i < couponGrant.getSingleReceive();i++){
                            //发放优惠券
                            grantCoupon(userId,couponList,type);

                        }
                    }
                }

            }
            return new ReturnStatus(true);
        }catch (Exception e){
            return new ReturnStatus(false);
        }
    }

    //发放优惠券
    public void grantCoupon(String userId,List<Coupon> totalCouponList,String type){
        System.out.println("grantCoupon totalCouponList"+totalCouponList);
        try{
            User inviater = new User();
            Query query = new Query(Criteria.where("_id").is(userId));
            User user = mongoTemplate.findOne(query,User.class);
            _logger.equals("user.getInvitor()  "+user.getInvitor()+"and type "+type);

            if(user != null){
                if (StringUtils.isNotEmpty(user.getInvitor()) && user.getInvitor()!=null && "关注".equals(type)){
                    inviater = mongoTemplate.findOne(new Query(Criteria.where("_id").is(user.getInvitor())),User.class);
                }
                Update updateUser = new Update();
                Update updateInviater = new Update();
                if (totalCouponList != null&& totalCouponList.size() > 0){
                    int rNum =(int)(Math.random()*totalCouponList.size());
                    Coupon coupon = totalCouponList.get(rNum);
                    MyCoupon myCoupon = new MyCoupon();
                    MyCoupon inviateCoupon = new MyCoupon();
                    if (coupon.getFavourableType() == 0){
                        //金额
                        int min = Integer.parseInt(coupon.getMinValue());
                        int max = Integer.parseInt(coupon.getMaxValue());
                        Random random = new Random();
                        int money = random.nextInt(max-min)+min;
                        BigDecimal b = new BigDecimal(money);
                        double moneyOrRatio = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        myCoupon.setMoneyOrRatio(moneyOrRatio);
                        inviateCoupon.setMoneyOrRatio(moneyOrRatio);
                    }else {
                        double min = Double.parseDouble(coupon.getMinValue());
                        Double max = Double.parseDouble(coupon.getMaxValue());
                        Random random = new Random();
                        double money = min+((max - min)* random.nextDouble());
                        //DecimalFormat df = new DecimalFormat("######0.00");
                        BigDecimal b = new BigDecimal(money);
                        double moneyOrRatio = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        myCoupon.setMoneyOrRatio(moneyOrRatio);
                        inviateCoupon.setMoneyOrRatio(moneyOrRatio);
                    /*//折扣
                    double min = Double.parseDouble(coupon.getMinValue());
                    double max = Double.parseDouble(coupon.getMaxValue());
                    double ds = (max - min);
                    int is = (int)(ds*1000);
                    Random random = new Random();
                    int money = random.nextInt(is)+(int)min*100;
                    myCoupon.setMoneyOrRatio(money+"%");*/
                    }

                    myCoupon.setCouponId(coupon.get_id());
                    myCoupon.setMoneyType(coupon.getFavourableType());
                    myCoupon.setUserId(userId);
                    myCoupon.setBigSortId(coupon.getBigSort());
                    myCoupon.setBigSortName(coupon.getBigSortName());
                    myCoupon.setGrantId(coupon.getGrantId());
                    myCoupon.setShopId(coupon.getShopId());
                    myCoupon.setDescription(type+"赠送");
                    myCoupon.setShopName(coupon.getShopName());
                    myCoupon.setCouponType(coupon.getCouponType());
                    myCoupon.setIsUsed("0");
                    myCoupon.setEndTime(coupon.getEndTime());
                    myCoupon.setTypeName(coupon.getTypeName());
                    myCoupon.setMinConsume(coupon.getMinConsume());
                    mongoTemplate.save(myCoupon);

                    //保存优惠券发放记录
                    saveRecord(myCoupon,"0",user.getName());

                    //给用户发送优惠券消息
//                    Account account1 = accountService.getAccountByEntityID(user.get_id(),"user");
//                    try {
//                        WxTemplate  tempToUser1 = redisService.getWxTemplate("喵：你有一张优惠券","10元","2017-04-10",
//                                null,null,null,"详情点击我的优惠券查看");
//                        redisService.send_template_message(account1.getAccountName(), "user", Configure.GRANT_COUPON, tempToUser1);
//                    }catch (Exception e){
//                        System.out.println("用户优惠券消息通知失败！" + e.getMessage());
//                    }
                    if ("关注".equals(type)){
                        inviateCoupon.setCouponId(coupon.get_id());
                        inviateCoupon.setMoneyType(coupon.getFavourableType());
                        inviateCoupon.setUserId(user.getInvitor());
                        inviateCoupon.setBigSortId(coupon.getBigSort());
                        inviateCoupon.setBigSortName(coupon.getBigSortName());
                        inviateCoupon.setGrantId(coupon.getGrantId());
                        inviateCoupon.setShopId(coupon.getShopId());
                        inviateCoupon.setDescription(type+"赠送");
                        inviateCoupon.setShopName(coupon.getShopName());
                        inviateCoupon.setCouponType(coupon.getCouponType());
                        inviateCoupon.setIsUsed("0");
                        inviateCoupon.setEndTime(coupon.getEndTime());
                        inviateCoupon.setTypeName(coupon.getTypeName());
                        inviateCoupon.setMinConsume(coupon.getMinConsume());
                        mongoTemplate.save(inviateCoupon);

                        //保存优惠券发放记录
                        saveRecord(myCoupon,"0",user.getName());

//                        //给分享人发送消息
//                        try {
//                            Account account = accountService.getAccountByEntityID(inviater.get_id(), "user");
//                            WxTemplate  tempToUser = redisService.getWxTemplate("喵：你有一张优惠券","10元","2017-04-10",
//                                    null,null,null,"详情点击我的优惠券查看");
//                            redisService.send_template_message(account.getAccountName(), "user", Configure.GRANT_COUPON, tempToUser);
//                        }catch (Exception e){
//                            System.out.println("推荐人消息通知失败！" + e.getMessage());
//                        }
                    }
                    List<String> userCouponList = user.getCouponIdList();
                    List<String> inviaterCouponList = new ArrayList<>();
                    if(inviater != null){
                        inviaterCouponList = inviater.getCouponIdList();
                    }
                    if(userCouponList==null){
                        userCouponList = new ArrayList();
                    }
                    _logger.info("inviaterCouponListOne"+inviaterCouponList.size());
                    userCouponList.add(myCoupon.get_id());
                    inviaterCouponList.add(inviateCoupon.get_id());
                    updateUser.set("couponIdList",userCouponList);
                    _logger.info("inviaterCouponListTwo"+inviaterCouponList.size());
                    updateInviater.set("couponIdList",inviaterCouponList);
                    mongoTemplate.updateFirst(query,updateUser,User.class);
                    if ("关注".equals(type)){
                        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(user.getInvitor())),updateInviater,User.class);
                    }
                }
            }else {
                _logger.info("用户不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("grantCoupon发放优惠券失败");
        }
    }


    /**
     * 分享抢红包
     */
    @Override
    public void grabRedWallet(User user,String shareUserId) {
        if (user != null){
            List<CouponGrant> couponGrantList = mongoTemplate.find(new Query(Criteria.where("business").is("分享").and("type").is("平台").and("endTime").gte(new Date()).and("state").is("是")),CouponGrant.class);
            List<Coupon> finalTotalCouponList = new ArrayList<>();//最终要发放的红包(现金+优惠券)列表
            if(couponGrantList != null && couponGrantList.size() > 0){
                int rNum =(int)(Math.random()*couponGrantList.size());
                CouponGrant couponGrant = couponGrantList.get(rNum);
                List<Coupon> couponList = mongoTemplate.find(new Query(Criteria.where("grantId").is(couponGrant.get_id())),Coupon.class);
                List<Coupon> tempCashList = new ArrayList<>();
                for (int index = 0; index < couponList.size(); index ++){
                    Coupon coupon = couponList.get(index);
                    if ("现金".equals(coupon.getCouponType())){
                        tempCashList.add(coupon);
                    }
                }
                int cashRNum =(int)(Math.random()*couponGrant.getMaxCashNum());//随机生成现金红包数量
                for (int rnum = 0; rnum < cashRNum; rnum ++){
                    finalTotalCouponList.add(tempCashList.get(cashRNum));
                }
                int couponrNum =(int)(Math.random()*(couponGrant.getTotalNumber() - couponGrant.getMaxCashNum()));//随机生成优惠券数量
                for (int rnum = 0; rnum < couponrNum; rnum ++){
                    finalTotalCouponList.add(couponList.get(rnum));
                }
            }
            //开抢
            List<Coupon> isUsedCouponList = new ArrayList<>();
            for (int index = 0; index < finalTotalCouponList.size(); index ++){
                Coupon isUsedCoupon = finalTotalCouponList.get(index);
                if (isUsedCoupon.getMaxTime() == (isUsedCoupon.getIsUsedTime()+isUsedCoupon.getMaxTime())){
                    isUsedCouponList.add(isUsedCoupon);
                }
            }
            for (int del = 0; del < isUsedCouponList.size(); del ++){
                finalTotalCouponList.remove(del);
            }
            if (finalTotalCouponList.size() > 0){
                int rNum =(int)(Math.random()*finalTotalCouponList.size());
                Coupon coupon = finalTotalCouponList.get(rNum);
                MyCoupon myCoupon = new MyCoupon();
                if (coupon.getFavourableType() == 0){
                    //金额
                    int min = Integer.parseInt(coupon.getMinValue());
                    int max = Integer.parseInt(coupon.getMaxValue());
                    Random random = new Random();
                    int money = random.nextInt(max-min)+min;
                    myCoupon.setMoneyOrRatio(money);
                }else {
                    double min = Double.parseDouble(coupon.getMinValue());
                    Double max = Double.parseDouble(coupon.getMaxValue());
                    Random random = new Random();
                    double money = min+((max - min)* random.nextDouble());
                    myCoupon.setMoneyOrRatio(money);
                }
                myCoupon.setCouponId(coupon.get_id());
                myCoupon.setMoneyType(coupon.getFavourableType());
                myCoupon.setUserId(user.get_id());
                myCoupon.setBigSortId(coupon.getBigSort());
                myCoupon.setBigSortName(coupon.getBigSortName());
                myCoupon.setGrantId(coupon.getGrantId());
                myCoupon.setShopId(coupon.getShopId());
                myCoupon.setDescription("抢红包获得");
                myCoupon.setShopName(coupon.getShopName());
                myCoupon.setCouponType(coupon.getCouponType());
                myCoupon.setIsUsed("0");
                myCoupon.setEndTime(coupon.getEndTime());
                myCoupon.setTypeName(coupon.getTypeName());
                myCoupon.setMinConsume(coupon.getMinConsume());
                mongoTemplate.save(myCoupon);
                //保存发放记录
                saveRecord(myCoupon,"0",user.getName());

                Update update = new Update();
                update.set("isUsedTime",coupon.getMaxTime()-1);
                mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(coupon.get_id())),update,Coupon.class);
                WxShare wxShare = (WxShare) cacheManager.get("share"+shareUserId);
                wxShare.setUserId(user.get_id());
                wxShare.getShareUserList().add(user);
                mongoTemplate.save(wxShare);
                cacheManager.save("share"+shareUserId,wxShare);
            }else {
                _logger.info("被抢光了");
            }
        }else {
            _logger.info("用户不存在");
        }
    }

    /**
     * 保存发放记录信息
     * @param
     * @param type
     */
    public void saveRecord(MyCoupon myCoupon,String type,String name){
        ConponRecord conponRecord = new ConponRecord(myCoupon,type,name);
        mongoTemplate.save(conponRecord);
    }
}
