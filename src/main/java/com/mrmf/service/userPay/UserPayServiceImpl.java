package com.mrmf.service.userPay;

import com.mrmf.entity.*;
import com.mrmf.entity.bean.UserTixianSum;
import com.mrmf.entity.coupon.ConponRecord;
import com.mrmf.entity.coupon.MyCoupon;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.Usersort;
import com.mrmf.entity.wxpay.CommonUtil;
import com.mrmf.entity.wxpay.PayCommonUtil;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.entity.wxpay.XMLUtil;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Arith;
import com.mrmf.service.common.Configure;
import com.mrmf.service.coupon.CouponGrantService;
import com.mrmf.service.organPosition.OrganPosition;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.user.incard.IncardService;
import com.mrmf.service.userService.UserService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.wesysconfig.WeSysConfigService;
import com.mrmf.service.weuser.WeUserService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.Constants;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.SMSUtil;
import com.osg.framework.util.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("userPayService")
public class UserPayServiceImpl implements UserPayService {

    @Autowired
    private EMongoTemplate mongoTemplate;
    @Autowired
    private WeSysConfigService weSysConfigService;
    @Autowired
    private WeOrganService weOrganService;
    @Autowired
    private WeUserService weUserService;
    @Autowired
    private WeComonService weCommonService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private IncardService incardService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMyService userMyService;
    @Autowired
    private CouponGrantService couponGrantService;
    @Autowired
    private OrganPosition organPosition;
    private ReturnStatus res;

    @Override
    public ReturnStatus saveUserPayOrder(String userId, String organId, double price, HttpServletRequest request) throws BaseException {

        ReturnStatus result = new ReturnStatus(true);

        Usercard sysQuery = this.sysQuery(organId);
        if (sysQuery.getMoney4( ) < price) {
            result.setSuccess(false);
            result.setMessage("卡余额不足");
        } else {
            WeUserPayOrder userPayOrder = new WeUserPayOrder( );
            userPayOrder.setNewId( );
            userPayOrder.setNewCreate( );
            userPayOrder.setUserId(userId);
            userPayOrder.setOrganId(organId);
            userPayOrder.setPrice(price);
            userPayOrder.setSysCardId(sysQuery.get_id( ));
            mongoTemplate.insert(userPayOrder);

            SortedMap<Object, Object> params = getPrepay_id(request, userPayOrder);
            params.put("userPayOrder", userPayOrder);
            result.setParams(params);
            result.setMessage(userPayOrder.get_id( ));
        }
        return result;
    }

    public Long queryUserPayOrder(String userId) {
        Long orderCount = mongoTemplate.count(Query.query(Criteria.where("userId").is(userId)), WeUserPayOrder.class);
        return orderCount;
    }

    public WeSysConfig querySaoMAFan( ) {
        WeSysConfig weSysConfig = mongoTemplate.findOne(Query.query(Criteria.where("_id").is("4651453728848330637")), WeSysConfig.class);
        return weSysConfig;
    }

    public Organ queryOrgan(String organId) {
        Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId)), Organ.class);
        return organ;
    }

    public User queryUser(String userId) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), User.class);
        return user;
    }

    public SortedMap<Object, Object> getPrepay_id(HttpServletRequest request, WeUserPayOrder userPayOrder) {
        SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
        String oppenid = "";
        HttpSession session = request.getSession(true);
        Map<String, Object> userInfo = (Map) session.getAttribute("userInfo");
        if (userInfo != null) {
            if (userInfo.get("openid") != null) {
                oppenid = userInfo.get("openid").toString( );
            }
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>( );
            parameters.put("appid", Configure.userAppID);
            parameters.put("mch_id", Configure.MCH_ID);
            parameters.put("nonce_str", PayCommonUtil.CreateNoncestr( ));
            parameters.put("body", "美容美发消费");
            parameters.put("out_trade_no", userPayOrder.get_id( ));
            if ("8389925757731126064".equals(userPayOrder.getOrganId( ))) {
                parameters.put("total_fee", "1");// 测试
            } else {
                parameters.put("total_fee", String.format("%.0f", userPayOrder.getWePrice( ) * 100));// 微信支付的钱数
            }
            parameters.put("spbill_create_ip", request.getRemoteAddr( ));
            parameters.put("notify_url", Configure.NOTIFY_URL);
            parameters.put("trade_type", "JSAPI");
            parameters.put("openid", oppenid);
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            String requestXML = PayCommonUtil.getRequestXml(parameters);
            String results = CommonUtil.httpsRequest(Configure.UNIFIED_ORDER_URL, "POST", requestXML);
            try {
                Map<String, String> map = XMLUtil.doXMLParse(results);

                params.put("appId", Configure.userAppID);
                params.put("timeStamp", Long.toString(new Date( ).getTime( )));
                params.put("nonceStr", PayCommonUtil.CreateNoncestr( ));
                params.put("package", "prepay_id=" + map.get("prepay_id"));
                params.put("signType", Configure.SIGN_TYPE);
                String paySign = PayCommonUtil.createSign("UTF-8", params);
                params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
                params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
                String userAgent = request.getHeader("user-agent");
                char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
                params.put("agent", new String(new char[]{agent}));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。

            } catch (JDOMException e) {
            } catch (IOException e) {
            }
        }
        return params;
    }
    public SortedMap<Object, Object> getPrepay_id(HttpServletRequest request, PositionOrder positionOrder) {
        SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
        String oppenid = "";
        HttpSession session = request.getSession(true);
        Map<String,Object> staffInfo=(Map<String,Object>)session.getAttribute("staffInfo");
        if (staffInfo != null) {
            if (staffInfo.get("openid") != null) {
                oppenid = staffInfo.get("openid").toString( );
            }
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>( );
            parameters.put("appid", Configure.staffAppID);
            parameters.put("mch_id", Configure.MCH_ID_STAFF);
            parameters.put("nonce_str", PayCommonUtil.CreateNoncestr( ));
            parameters.put("body", "工位租赁支付");
            parameters.put("out_trade_no", positionOrder.get_id( ));
            parameters.put("total_fee", String.format("%.0f", positionOrder.getWxMoney() * 100));// 微信支付的钱数
            parameters.put("spbill_create_ip", request.getRemoteAddr( ));
            parameters.put("notify_url", Configure.NOTIFY_URL);
            parameters.put("trade_type", "JSAPI");

            parameters.put("openid", oppenid);
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            String requestXML = PayCommonUtil.getRequestXml(parameters);
            String results = CommonUtil.httpsRequest(Configure.UNIFIED_ORDER_URL, "POST", requestXML);
            try {
                Map<String, String> map = XMLUtil.doXMLParse(results);
                params.put("appId", Configure.staffAppID);
                params.put("timeStamp", Long.toString(new Date( ).getTime( )));
                params.put("nonceStr", PayCommonUtil.CreateNoncestr( ));
                params.put("package", "prepay_id=" + map.get("prepay_id"));
                params.put("signType", Configure.SIGN_TYPE);
                String paySign = PayCommonUtil.createSign("UTF-8", params);
                params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
                params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
                String userAgent = request.getHeader("user-agent");
                char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
                params.put("agent", new String(new char[]{agent}));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。


            } catch (JDOMException e) {
            } catch (IOException e) {
            }
        }
        return params;
    }

    public SortedMap<Object, Object> getPrepay_id_staff(HttpServletRequest request, WeUserPayOrder userPayOrder) {
        SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
        String oppenid = "";
        HttpSession session = request.getSession(true);
        Map<String, Object> staffInfo = (Map<String, Object>) session.getAttribute("staffInfo");
        if (staffInfo != null) {
            if (staffInfo.get("openid") != null) {
                oppenid = staffInfo.get("openid").toString( );
            }
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>( );
            parameters.put("appid", Configure.staffAppID);
            parameters.put("mch_id", Configure.MCH_ID_STAFF);
            parameters.put("nonce_str", PayCommonUtil.CreateNoncestr( ));
            parameters.put("body", "红包支付");
            parameters.put("out_trade_no", userPayOrder.get_id( ));
            parameters.put("total_fee", String.format("%.0f", userPayOrder.getWePrice( ) * 100));// 微信支付的钱数
            parameters.put("spbill_create_ip", request.getRemoteAddr( ));
            parameters.put("notify_url", Configure.NOTIFY_URL);
            parameters.put("trade_type", "JSAPI");
            parameters.put("openid", oppenid);
            System.out.println("sunze oppenid" + oppenid);
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            String requestXML = PayCommonUtil.getRequestXml(parameters);
            String results = CommonUtil.httpsRequest(Configure.UNIFIED_ORDER_URL, "POST", requestXML);
            try {
                Map<String, String> map = XMLUtil.doXMLParse(results);
                params.put("appId", Configure.staffAppID);
                params.put("timeStamp", Long.toString(new Date( ).getTime( )));
                params.put("nonceStr", PayCommonUtil.CreateNoncestr( ));
                params.put("package", "prepay_id=" + map.get("prepay_id"));
                params.put("signType", Configure.SIGN_TYPE);
                String paySign = PayCommonUtil.createSign("UTF-8", params);
                params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
                params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
                String userAgent = request.getHeader("user-agent");
                char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
                params.put("agent", new String(new char[]{agent}));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
            } catch (JDOMException e) {
            } catch (IOException e) {
            }
            return params;
        } else {
            return null;
        }
    }

    @Override
    public Usercard sysQuery(String organId) throws BaseException {
        if (StringUtils.isEmpty(organId)) {
            throw new BaseException("公司id不能为空！");
        }
        Criteria criteria = new Criteria( );
        criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("userId").is("0"));
        Usercard syscard = mongoTemplate.findOne(new Query(criteria), Usercard.class);
        return syscard;
    }

    private static int getUserAmount(int price, WeSysConfig weSysConfig) {
        int userAmount = 0;
        int man1 = weSysConfig.getMan1( );
        int fan1 = weSysConfig.getFan1( );
        int man2 = weSysConfig.getMan2( );
        int fan2 = weSysConfig.getFan2( );
        int man3 = weSysConfig.getMan3( );
        int fan3 = weSysConfig.getFan3( );
        if (man3 > 0 && price >= man3) {
            userAmount = price / man3 * fan3;
            userAmount += getUserAmount(price % man3, weSysConfig);
        } else if (man2 > 0 && price >= man2) {
            userAmount = price / man2 * fan2;
            userAmount += getUserAmount(price % man2, weSysConfig);
        } else if (man1 > 0 && price >= man1) {
            userAmount = price / man1 * fan1;
            userAmount += getUserAmount(price % man1, weSysConfig);
        }
        return userAmount;
    }

    @Override
    public ReturnStatus payOnlineSuccess(Map<Object, Object> map) throws BaseException {
        ReturnStatus result = new ReturnStatus(false);
        // System.out.println("已经开始处理成功之后的业务");

        // 更新微信订单表状态
        String orderNo = map.get("out_trade_no").toString( );
        WeUserPayOrder userPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderNo)), WeUserPayOrder.class);
        userPayOrder.setState(1);
        userPayOrder.setUpdateTimeIfNew( );
        mongoTemplate.save(userPayOrder);
        Userincard incard = userService.getInCard(userPayOrder.getIncardId( ));
        Usersort usersort = getUsersortById(incard.getMembersort( ));
        Userpart userPart = new Userpart( );
        userPart.setIdIfNew( );
        userPart.setIncardId(userPayOrder.getIncardId( ));
        userPart.setMoney_cash(userPayOrder.getPrice( ));
        userPart.setWeixinCharge(true);
        if ("1003".equals(usersort.getFlag1( ))) {
            userPart.setCishu(usersort.getCishu( ));
            userPart.setLaw_date(usersort.getLaw_date( ));
        } else {
            userPart.setCishu(0);
            userPart.setLaw_date(0);
        }
        userPart.setCreateTimeIfNew( );

        mongoTemplate.save(userPart);
        ReturnStatus status = incardService.xufei(userPart);
        if (status.isSuccess( )) {
            WeUserCardCharge cardCharge = new WeUserCardCharge( );
            cardCharge.setIdIfNew( );
            cardCharge.setUserId(userPayOrder.getUserId( ));
            cardCharge.setOrganId(userPayOrder.getOrganId( ));
            cardCharge.setIncardId(userPayOrder.getIncardId( ));
            cardCharge.setMoney1(userPayOrder.getPrice( ));
            cardCharge.setState(0);
            cardCharge.setOrganState(0);
            cardCharge.setCreateTimeIfNew( );
            mongoTemplate.save(cardCharge);
        }
        Organ organ = weOrganService.queryOrganById(userPayOrder.getOrganId( ));

        // 添加消息
        WeMessage msg = new WeMessage( );
        User payuser = weUserService.queryUserById(userPayOrder.getUserId( ));
        msg.setIdIfNew( );
        msg.setFromType("user");
        msg.setFromId(payuser.get_id( ));
        msg.setFromName(payuser.getNick( ));
        msg.setToType("organ");
        msg.setToId(organ.get_id( ));
        msg.setToName(organ.getName( ));
        msg.setType("1");
        msg.setReadFalg(false);
        msg.setContent(payuser.getNick( ) + " 会员卡充值成功了");
        String organTime = getTime(new Date( ), "yyyy/dd/MM HH:mm");
        msg.setCreateTimeFormat(organTime);
        weCommonService.saveMessage(msg);
        // 用户支付完信息给用户添加消息
        WeMessage userMsg = new WeMessage( );
        userMsg.setIdIfNew( );
        userMsg.setFromType("organ");
        userMsg.setFromId(organ.get_id( ));
        userMsg.setFromName(organ.getName( ));
        userMsg.setToType("user");
        userMsg.setToId(payuser.get_id( ));
        userMsg.setToName(payuser.getNick( ));
        userMsg.setType("1");
        userMsg.setReadFalg(false);
        userMsg.setContent("店铺 " + organ.getName( ) + " 的会员卡充值成功");
        String userTime = getTime(new Date( ), "yyyy/dd/MM HH:mm");
        userMsg.setCreateTimeFormat(userTime);
        weCommonService.saveMessage(userMsg);

        // 用户通知
        Account account = accountService.getAccountByEntityID(payuser.get_id( ), "user");
        // System.out.println("到了用户通知"+account.getAccountName());

        double nowPrice = 0;
        if ("1002".equals(usersort.getFlag1( ))) {
            nowPrice = incard.getMoney4( );
        } else if ("1003".equals(usersort.getFlag1( ))) {
            nowPrice = incard.getShengcishu( ) * incard.getDanci_money( );
        }
        // Usercard usercard=queryUserCardById(incard.getCardId());
        WxTemplate tempToUser = redisService.getWxTemplate("喵，您已充值成功啦~", organ.getName( ), usersort.getName1( ), String.valueOf(userPayOrder.getPrice( )), String.valueOf(nowPrice), organTime, "感谢您对喵小妹的信任~~");
        try {
            redisService.send_template_message(account.getAccountName( ), "user", Configure.PAYONLINE_USER_INFO, tempToUser);
        } catch (Exception e) {
            System.out.println("消息通知失败！" + e.getMessage( ));
        }
        // 给店铺发送消息

        account = accountService.getAccountByEntityID(organ.get_id( ), "organ");
        WxTemplate tempToOrgan = redisService.getWxTemplate("Big boss，金主儿的订单已成功支付！", String.valueOf(userPayOrder.getPrice( )), "微信支付", organ.getName( ), userPayOrder.get_id( ), organTime, "如想看雪看星星看月亮，从诗词歌赋聊到人生哲学请致电400-088-2224");
        try {
            redisService.send_template_message(account.getAccountName( ), "organ", Configure.PAYONLINE_ORGAN_INFO, tempToOrgan);
        } catch (Exception e) {
            System.out.println("消息通知失败！" + e.getMessage( ));
        }

        result.setSuccess(true);
        return result;
    }

    /**
     * 工位租赁模式下 支付成功
     * @param map
     * @return
     * @throws BaseException
     */
    @Override
    public ReturnStatus payForStaffSuccess(Map<Object, Object> map) throws BaseException, ParseException {
        System.out.println("~~~~~~~~~~~~~~~~已经进入工位租赁~~~~~~~~~");
        ReturnStatus result = new ReturnStatus(false);
        // 更新微信订单表状态
        String orderNo = map.get("out_trade_no").toString( );
        System.out.println("~~~~~~~~~~~~~~~~获取id~~~~~~~~~" + orderNo);
        WeUserPayOrder userPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderNo)), WeUserPayOrder.class);
        System.out.println("~~~~~~~~~~~~~~~~userPayOrder~~~~~~~~~" + userPayOrder);
        // 获取当前支付用户的基本信息做日志输出
        User thisUser = userService.getUserById(userPayOrder.getUserId( ));
        System.out.println("~~~~~~~~~~~~~~~~获取用户~~~~~~~~~" + thisUser);

        // 支付成功之后对用户钱包进行清零
        if (userPayOrder.getWePrice( ) > 0) {
            System.out.println("~~~~~~~~~~~~~~~~获取用户支付金额~~~~~~~~~" + userPayOrder.getWePrice( ));
            thisUser.setWalletAmount(0);
            mongoTemplate.save(thisUser);
        }

        userPayOrder.setState(1);
        userPayOrder.setUpdateTimeIfNew( );
        String orderId = userPayOrder.getOrganOrderId( );

        String staffId = userPayOrder.getStaffId();

        System.out.println("~~~~~~~~~~~~~~~~staffId~~~~~~~~~" + staffId);

        if (! StringUtils.isEmpty(orderId)) {
            WeOrganOrder order = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeOrganOrder.class);
            order.setState(3);
            order.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            mongoTemplate.save(order);
        }
        mongoTemplate.save(userPayOrder);
        //更新优惠券使用状态
        if (! StringUtils.isEmpty(userPayOrder.getCouponId( ))) {
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(userPayOrder.getCouponId( ))), Update.update("isUsed", "1"), MyCoupon.class);
            //保存优惠券消费记录
            ConponRecord conponRecord = mongoTemplate.findOne(Query.query(Criteria.where("myCouponId").is(userPayOrder.getCouponId())),ConponRecord.class);
            conponRecord.setUsedTime(new Date());
            conponRecord.setIsUsed("1");
            mongoTemplate.save(conponRecord);
        }
        //获取店铺和技师
        Organ organ = weOrganService.queryOrganById(userPayOrder.getOrganId());
        Staff staff = staffService.queryById(staffId);

        System.out.println("~~~~~~~~~~~~~~~~开始插入分帐表~~~~~~~~~");
        // 微信支付分账表插入数据
        WeSysConfig weSysConfig = weSysConfigService.query( );

        double userAmount = 0;
        double price = userPayOrder.getPrice( );
        //计算优惠信息
        int p = (int) price;
        userAmount = getUserAmount(p, weSysConfig);
        userAmount = new BigDecimal(userAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );//满减返现金额
        double balancePrice = new BigDecimal(price).subtract(new BigDecimal(userAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );//分帐金额

        //分帐数据准备
        double sysAmount = 0;
        double organAmount = 0;
        double staffAmount = 0;
        double balancePriceTotal = 0;//当天店铺中某技师的总收入

        //根据店铺id获取店铺此时的状态 租金or工位
        OrganPositionSetting ops = weOrganService.getOrganPositionSetting(organ.get_id());
        System.out.println("~~~~~~~~~~~~~~~~获取到店铺此时的状态~~~~~~~~~");
        if(ops != null){
            if(0 == ops.getLeaseType()){//租金模式
                Integer leaseMoney = ops.getLeaseMoney();//获取租金
                //获取当天范围内一个技师的总收入
                long current=System.currentTimeMillis();//当前时间毫秒数
                long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
                long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
                Timestamp time1 = new Timestamp(zero);//今天零点零分零秒
                Timestamp time2 = new Timestamp(twelve);//今天23点59分59
                //查询条件
                Query query = new Query();
                List<Criteria> criteria = new ArrayList<>();
                List<WeOrganOrder> staffId1 = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId)), WeOrganOrder.class);
                List<String> list = new ArrayList<>();
                for(WeOrganOrder staffList:staffId1){
                    list.add(staffList.get_id( ));
                }//所有技师的订单
                criteria.add(Criteria.where("createTime").gt(time1));
                criteria.add(Criteria.where("createTime").lt(time2));//时间
                criteria.add(Criteria.where("state").is(1));//已支付
                if(userPayOrder.getOrganOrderId() != null){
                    criteria.add(Criteria.where("organOrderId").in(list));
                }
                criteria.add(Criteria.where("organId").is(userPayOrder.getOrganId()));
                Criteria criteria1 = new Criteria();
                criteria1.andOperator(criteria.toArray(new Criteria[criteria.size()]));
                //查询当天同一个技师 同一个店铺 的总订单

                List<WeUserPayOrder> orderList = mongoTemplate.find(Query.query(criteria1).with(new Sort(Sort.Direction.ASC,"createTime")), WeUserPayOrder.class);

                //遍历集合 获取本次消费之外的消费总和
                for(int i =0;i <orderList.size()-1;i++){
                    WeUserPayOrder order = orderList.get(i);
                    if(0 != order.getPrice() ){
                        balancePriceTotal += order.getPrice();
                    }
                }

                double balancePriceTotal1 = Arith.add(balancePriceTotal,balancePrice);//用来判断分帐金额所处的范围
                //从position表中获取分帐区间，保证数据准确

                //根据organId staffid获取 所有满足的工位
                List<PositionSetting> opsList = new ArrayList<>();

                 List<Criteria> criterias = new ArrayList<>();
                criterias.add(Criteria.where("state").in(1,3));//预约或者支付完成
                criterias.add(Criteria.where("organId").is(userPayOrder.getOrganId()));
                criterias.add(Criteria.where("staffId").is(userPayOrder.getStaffId()));
                Criteria criteria2 = new Criteria();
                criteria2.andOperator(criterias.toArray(new Criteria[criterias.size()]));
                List<PositionOrder> positionOrders = mongoTemplate.find(Query.query(criteria2),PositionOrder.class);
                if(0 != positionOrders.size()){
                    //去details表查符合要求的数据
                    for(PositionOrder positionOrder :positionOrders){
                        Date dateStart = DateUtil.currentDate(new Date());
                        Date dateEnd = DateUtil.addDate(dateStart, 1);
                        List<Criteria> criteria3 = new ArrayList<>();
                        criteria3.add(Criteria.where("time").gte(dateStart));
                        criteria3.add(Criteria.where("time").lt(dateEnd));//时间
                        criteria3.add(Criteria.where("positionOrderId").is(positionOrder.get_id()));//时间
                        Criteria criteria4 = new Criteria();
                        criteria4.andOperator(criteria3.toArray(new Criteria[criteria3.size()]));

                        List<OrganPositionDetails> detailss = mongoTemplate.find(Query.query(criteria4), OrganPositionDetails.class);
                        if(detailss != null && detailss.size() > 0){
                             opsList = positionOrder.getFenZhangList();

                        }


                    }

                }

                int index = 0;
                int end = 0;
                for(int i = 0; i<opsList.size();i++){
                    if(balancePriceTotal1 >= opsList.get(i).getQu1()){
                        end++;
                    }
                    if(balancePriceTotal >= opsList.get(i).getQu1()){
                        index++;
                    }

                }
                PositionSetting ps = opsList.get(end-1);
                Double organShare = ps.getOrganAmount( );
                Double staffShare = ps.getStaffAmount( );
                double qu1 = ps.getQu1();
                if( index == end){
                    organAmount = new BigDecimal(balancePrice).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    staffAmount = new BigDecimal(balancePrice).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    sysAmount = new BigDecimal(balancePrice).subtract((new BigDecimal(organAmount))).subtract((new BigDecimal(staffAmount))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                }else {
                    double balancePrice1 = Arith.sub(balancePriceTotal1, qu1);
                    double balancePrice2 = Arith.sub(balancePrice, balancePrice1);
                    organAmount = new BigDecimal(balancePrice1).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    staffAmount = new BigDecimal(balancePrice1).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    double sub1 =0;
                    double organ1=0;
                    double staff1=0;
                    for(int x = end-2 ; x >=index-1 ;x --){//从大到小开始计算
                        PositionSetting ps1 = opsList.get(x);
                        Double organShare1 = ps1.getOrganAmount( );
                        Double staffShare1 = ps1.getStaffAmount( );
                        sub1 = Arith.sub(opsList.get(x).getQu2( ), opsList.get(x).getQu1( ));//边界值
                        balancePrice2 = Arith.sub(balancePrice2,sub1);//判断27-10-10=7 最后一次分7，其余都分边界相减
                        if(index-1 == x){
                            balancePrice2 +=sub1;//最后一次会多减【0】时的边界值
                            organ1 += new BigDecimal(balancePrice2).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                            staff1 += new BigDecimal(balancePrice2).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                        }else{
                            organ1 += new BigDecimal(sub1).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                            staff1 += new BigDecimal(sub1).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                        }

                    }
                    organAmount = new BigDecimal(organAmount).add((new BigDecimal(organ1))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    staffAmount = new BigDecimal(staffAmount).add((new BigDecimal(staff1))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    sysAmount = new BigDecimal(balancePrice).subtract((new BigDecimal(organAmount))).subtract((new BigDecimal(staffAmount))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                }
                System.out.println("1111111111111111111店铺分帐      "+organAmount+"      222222222222222技师分帐   "+staffAmount+    "         3333333333333333333333系统分帐"+sysAmount);
            }else {
                System.out.println("~~~~~~~~~~~~~~~~开始进入分帐模式 开始 开始~~~~~~~~~");
                long current=System.currentTimeMillis();//当前时间毫秒数
                long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
                long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
                Timestamp time1 = new Timestamp(zero);//今天零点零分零秒
                Timestamp time2 = new Timestamp(twelve);//今天23点59分59
                //查询条件
                Query query = new Query();
                List<Criteria> criteria = new ArrayList<>();
                List<WeOrganOrder> staffId1 = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId)), WeOrganOrder.class);
                List<String> list = new ArrayList<>();
                for(WeOrganOrder staffList:staffId1){
                    list.add(staffList.get_id( ));
                }//所有技师的订单
                criteria.add(Criteria.where("createTime").gt(time1));
                criteria.add(Criteria.where("createTime").lt(time2));//时间
                criteria.add(Criteria.where("state").is(1));//已支付
                if(userPayOrder.getOrganOrderId() != null){
                    criteria.add(Criteria.where("organOrderId").in(list));
                }
                criteria.add(Criteria.where("organId").is(userPayOrder.getOrganId()));
                Criteria criteria1 = new Criteria();
                criteria1.andOperator(criteria.toArray(new Criteria[criteria.size()]));
                //查询当天同一个技师 同一个店铺 的总订单

                List<WeUserPayOrder> orderList = mongoTemplate.find(Query.query(criteria1).with(new Sort(Sort.Direction.ASC,"createTime")), WeUserPayOrder.class);

                //遍历集合 获取本次消费之外的消费总和
                for(int i =0;i <orderList.size()-1;i++){
                    WeUserPayOrder order = orderList.get(i);
                    if(0 != order.getPrice() ){
                        balancePriceTotal += order.getPrice();
                    }
                }

                double balancePriceTotal1 = Arith.add(balancePriceTotal,balancePrice);//用来判断分帐金额所处的范围

                List<PositionSetting> opsList = new ArrayList<>();

                List<Criteria> criterias = new ArrayList<>();
                criterias.add(Criteria.where("state").is(1).orOperator(Criteria.where("state").is(3)));//预约或者支付完成
                criterias.add(Criteria.where("organId").is(userPayOrder.getOrganId()));
                criterias.add(Criteria.where("staffId").is(userPayOrder.getStaffId()));
                Criteria criteria2 = new Criteria();
                criteria2.andOperator(criterias.toArray(new Criteria[criterias.size()]));
                List<PositionOrder> positionOrders = mongoTemplate.find(Query.query(criteria2),PositionOrder.class);
                if(0 != positionOrders.size()){
                    //去details表查符合要求的数据
                    for(PositionOrder positionOrder :positionOrders){
                        Date dateStart = DateUtil.currentDate(new Date());
                        Date dateEnd = DateUtil.addDate(dateStart, 1);
                        List<Criteria> criteria3 = new ArrayList<>();
                        criteria3.add(Criteria.where("time").gte(dateStart));
                        criteria3.add(Criteria.where("time").lt(dateEnd));//时间
                        criteria3.add(Criteria.where("positionOrderId").is(positionOrder.get_id()));//时间
                        Criteria criteria4 = new Criteria();
                        criteria4.andOperator(criteria3.toArray(new Criteria[criteria3.size()]));

                        List<OrganPositionDetails> detailss = mongoTemplate.find(Query.query(criteria4), OrganPositionDetails.class);
                        if(detailss != null && detailss.size() > 0){
                            opsList = positionOrder.getFenZhangList();

                        }


                    }

                }
                int index = 0;
                int end = 0;
                for(int i = 0; i<opsList.size();i++){
                    if(balancePriceTotal1 >= opsList.get(i).getQu1()){
                        end++;
                    }
                    if(balancePriceTotal >= opsList.get(i).getQu1()){
                        index++;
                    }

                }
                PositionSetting ps = opsList.get(end-1);
                Double organShare = ps.getOrganAmount( );
                Double staffShare = ps.getStaffAmount( );
                double qu1 = ps.getQu1();
                if( index == end){
                    organAmount = new BigDecimal(balancePrice).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    staffAmount = new BigDecimal(balancePrice).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    sysAmount = new BigDecimal(balancePrice).subtract((new BigDecimal(organAmount))).subtract((new BigDecimal(staffAmount))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                }else {
                    double balancePrice1 = Arith.sub(balancePriceTotal1, qu1);
                    double balancePrice2 = Arith.sub(balancePrice, balancePrice1);
                    organAmount = new BigDecimal(balancePrice1).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    staffAmount = new BigDecimal(balancePrice1).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    double sub1 =0;
                    double organ1=0;
                    double staff1=0;
                    for(int x = end-2 ; x >=index-1 ;x --){//从大到小开始计算
                        PositionSetting ps1 = opsList.get(x);
                        Double organShare1 = ps1.getOrganAmount( );
                        Double staffShare1 = ps1.getStaffAmount( );
                        sub1 = Arith.sub(opsList.get(x).getQu2( ), opsList.get(x).getQu1( ));//边界值
                        balancePrice2 = Arith.sub(balancePrice2,sub1);//判断27-10-10=7 最后一次分7，其余都分边界相减
                        if(index-1 == x){
                            balancePrice2 +=sub1;//最后一次会多减【0】时的边界值
                            organ1 += new BigDecimal(balancePrice2).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                            staff1 += new BigDecimal(balancePrice2).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                        }else{
                            organ1 += new BigDecimal(sub1).divide((new BigDecimal(100))).multiply(new BigDecimal(organShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                            staff1 += new BigDecimal(sub1).divide((new BigDecimal(100))).multiply(new BigDecimal(staffShare1*10)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                        }

                    }
                    organAmount = new BigDecimal(organAmount).add((new BigDecimal(organ1))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    staffAmount = new BigDecimal(staffAmount).add((new BigDecimal(staff1))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                    sysAmount = new BigDecimal(balancePrice).subtract((new BigDecimal(organAmount))).subtract((new BigDecimal(staffAmount))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
                }
                System.out.println("1111111111111111111店铺分帐      "+organAmount+"      222222222222222技师分帐   "+staffAmount+    "         3333333333333333333333系统分帐"+sysAmount);
            }

        } else {
            result.setMessage("系统内部错误");
        }

        double staffWalletAmount = new BigDecimal(staff.getTotalIncome( )).add(new BigDecimal(staffAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
        staff.setTotalIncome(staffWalletAmount);
        mongoTemplate.save(staff); //更新技师钱包余额;

        //添加首次消费返现的逻辑 店铺+  后台平台卡-
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userPayOrder.getUserId( ))), User.class);
        long orderCount = 0;

        Criteria countCriteria = Criteria.where("userId").is(user.get_id( )).and("state").is(1);
        orderCount = mongoTemplate.count(Query.query(countCriteria), WeUserPayOrder.class);
            //判断当前用户是否是第一次消费
        if ((1 == orderCount) && ("organ".equals(user.getAccountType())) && (userPayOrder.getOrganId().equals(user.getInvitor()))) {
            //获取返现金额 并给店铺返现
            double amount = new BigDecimal(organ.getWalletAmount( )).add(new BigDecimal(weSysConfig.getSaoMaFan( ))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );

            //更新店铺余额
            weOrganService.updateUserWalletAmount(organ.get_id( ), amount);
            //更新历史记录 店铺端
            WeUserWalletHis weUserWalletHis = new WeUserWalletHis( );
            weUserWalletHis.setNewId( );
            weUserWalletHis.setNewCreate( );
            weUserWalletHis.setUserId(organ.get_id( ));
            weUserWalletHis.setSendUserId(user.get_id( ));
            weUserWalletHis.setAmount(weSysConfig.getSaoMaFan( ));
            weUserWalletHis.setDesc("店铺自荐用户首次消费奖励 " + weSysConfig.getSaoMaFan( ) + "元");
            mongoTemplate.insert(weUserWalletHis);// 添加店铺返现记录

            organAmount = Arith.add(organAmount, weSysConfig.getSaoMaFan( )); //更新店铺分帐金额
            sysAmount = Arith.sub(sysAmount, weSysConfig.getSaoMaFan( ));//更新平台分帐金额
        }


        //更新分账信息
        WeUserPayFenzhang userPayFenzhang = new WeUserPayFenzhang( );
        userPayFenzhang.setNewId(); //分帐id
        userPayFenzhang.setNewCreate();  //分帐时间
        userPayFenzhang.setOrderId(orderNo);  //订单id
        userPayFenzhang.setUserId(userPayOrder.getUserId( ));  //用户id
        userPayFenzhang.setOrganId(userPayOrder.getOrganId( ));  //店铺id
        userPayFenzhang.setPrice(userPayOrder.getPrice( ));  //用户支付金额
        userPayFenzhang.setOrganAmount(organAmount); //店铺钱包余额 已经分帐
        userPayFenzhang.setStaffAmount(staffAmount);//技师钱包余额 已经分帐
        userPayFenzhang.setUserAmount(userAmount);  //用户钱包余额 已经分帐
        userPayFenzhang.setSysAmount(sysAmount);
        userPayFenzhang.setState(1);  //店铺分帐处理状态 凡是工位模式下都为1
        userPayFenzhang.setPayWeixin(userPayOrder.getWePrice( ));// 微信支付金额
        userPayFenzhang.setPayWallet(Arith.sub(userPayOrder.getPrice( ), userPayOrder.getWePrice( )));// 用户钱包支付金额
        userPayFenzhang.setCity(organ.getCity( )); //店铺所在城市
        userPayFenzhang.setDistrict(organ.getDistrict( ));
        userPayFenzhang.setRegion(organ.getRegion( ));
        userPayFenzhang.setExpense(userPayOrder.getExpense());
        userPayFenzhang.setWalletAmount(organAmount);
        userPayFenzhang.setBenefit(userPayOrder.getBenefit());
        userPayFenzhang.setCardCount(0);  // 分账当时的店铺平台卡消费次数(本次消费之后)
        userPayFenzhang.setCardMoney4(0); //分账当时的店铺平台卡余额(本次消费之后)
        userPayFenzhang.setSysCardId("0"); // 0表示非预存消费分账

        if ((1 == orderCount) && ("organ".equals(user.getAccountType())) && (userPayOrder.getOrganId().equals(user.getInvitor()))) {
            organAmount = new BigDecimal(organAmount).subtract((new BigDecimal(weSysConfig.getSaoMaFan( )))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
            userPayFenzhang.setFirstAmount(weSysConfig.getSaoMaFan( ));
        }
        mongoTemplate.insert(userPayFenzhang); //更新分帐表


        //更新店铺钱包信息
        Organ organs = mongoTemplate.findById(organ.get_id(),Organ.class);
        organs.setWalletAmount(new BigDecimal(organ.getWalletAmount()).add(new BigDecimal(organAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( ));
        mongoTemplate.save(organs);

        // 生成店铺全包余额变动记录
        WeUserWalletHis weOrganWalletHis = new WeUserWalletHis( );
        weOrganWalletHis.setNewId( );
        weOrganWalletHis.setNewCreate( );
        weOrganWalletHis.setOrderId("10");
        weOrganWalletHis.setUserId(organ.get_id( ));
        weOrganWalletHis.setAmount(organAmount);
        weOrganWalletHis.setDesc("店铺提成");
        mongoTemplate.insert(weOrganWalletHis);

        // 技师钱包记录变更
        if (staffAmount != 0) {
            WeUserWalletHis staffWalletHis = new WeUserWalletHis( );
            staffWalletHis.setNewId( );
            staffWalletHis.setNewCreate( );
            staffWalletHis.setUserId(staff.get_id( ));//写哪个呢 技师还是店铺
            staffWalletHis.setOrderId("12");  //技师用户提成
            staffWalletHis.setAmount(staffAmount);
            staffWalletHis.setDesc("技师提成");
            mongoTemplate.insert(staffWalletHis);
        }

        // 用户钱包插入记录
        if (Arith.sub(userPayOrder.getPrice( ), userPayOrder.getWePrice( )) != 0) {
            WeUserWalletHis weUserWalletConsume = new WeUserWalletHis( );
            weUserWalletConsume.setNewId( );
            weUserWalletConsume.setNewCreate( );
            weUserWalletConsume.setUserId(userPayOrder.getUserId( ));
            weUserWalletConsume.setAmount(- (Arith.sub(userPayOrder.getPrice( ), userPayOrder.getWePrice( ))));
            weUserWalletConsume.setDesc("消费支付");
            mongoTemplate.insert(weUserWalletConsume);// 添加用户消费记录
        }
        WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
        weUserWalletHis.setNewId();
        weUserWalletHis.setNewCreate();
        weUserWalletHis.setUserId(userPayOrder.getUserId());
        weUserWalletHis.setAmount(userAmount);
        weUserWalletHis.setDesc("消费返现");
        mongoTemplate.insert(weUserWalletHis);// 添加用户返现记录


        //插入消费记录表
        Userpart userpart = new Userpart();
        userpart.setUserId(user.get_id());
        userpart.setMoney1(userPayFenzhang.getOrganAmount()); // 应交
        userpart.setMoney2(userPayFenzhang.getOrganAmount()); // 实际缴费
        userpart.setOrganId(userPayFenzhang.getOrganId());
        userpart.setFenzhangId(userPayFenzhang.get_id());
        userpart.setMoney_xiaofei(userPayFenzhang.getOrganAmount());
        userpart.setStaffId1(staffId);
        userpart.setFlag2(true); // 是否交款
        userpart.setGuazhang_flag(false); // 是否挂账
        userpart.setDelete_flag(false);
        userpart.setType(2); // 微信分账消费
        mongoTemplate.save(userpart);


        System.out.println("用户" + thisUser.getNick( ) + "支付成功了,支付金额" + userPayOrder.getPrice( ) + "用户余额" + user.getWalletAmount( ) + "返现" + userAmount + "@@@@@@@@@@@@@@@@@@@@@@@@");
        double walletAmount = new BigDecimal(user.getWalletAmount( )).add(new BigDecimal(userAmount)).doubleValue( );
        user.setWalletAmount(walletAmount);
        mongoTemplate.save(user); //更新用户钱余额


        System.out.println("用户" + thisUser.getNick( ) + "支付成功了,返现之后的钱包余额为：" + user.getWalletAmount( ) + "@@@@@@@@@@@@@@@@@@@@@@@@");

        User payuser = weUserService.queryUserById(userPayOrder.getUserId( ));
        String organTime = getTime(new Date( ), "yyyy/MM/dd HH:mm");
        // 用户支付完信息给用户添加消息
        WeMessage userMsg = new WeMessage( );
        userMsg.setIdIfNew( );
        userMsg.setFromType("organ");
        userMsg.setFromId(organ.get_id( ));
        userMsg.setFromName(organ.getName( ));
        userMsg.setToType("user");
        userMsg.setToId(payuser.get_id( ));
        userMsg.setToName(payuser.getNick( ));
        userMsg.setType("1");
        userMsg.setReadFalg(false);
        userMsg.setContent("店铺 " + organ.getName( ) + " 的订单您已经成功支付");
        String userTime = getTime(new Date( ), "yyyy/MM/dd HH:mm");
        userMsg.setCreateTimeFormat(userTime);
        weCommonService.saveMessage(userMsg);
        // 用户通知
        // 给技师返现
        if (! StringUtils.isEmpty(user.getInvitor( )) && ("staff".equals(user.getAccountType())) && user.getInviteDate( ) != null) {
            if (! userMyService.greaterThanYear(new Date( ), user.getInviteDate( ))) {
                userMyService.moneyToStaff(user.getInvitor( ), userPayOrder.getPrice( ), user.get_id( ));
            }
        }
        Account account = accountService.getAccountByEntityID(user.get_id( ), "user");
        WxTemplate tempToUser = redisService.getWxTemplate("喵，您的订单支付已成功啦~", orderNo, String.valueOf(userPayOrder.getPrice( )), organ.getName( ), organTime, null, "感谢您对小喵的信任，这次支付为您返现" + userAmount + "元已充值到钱包，喵小二恭候您再次光临~");
        try {
            redisService.send_template_message(account.getAccountName( ), "user", Configure.PAY_USER_REMIND, tempToUser);
        } catch (Exception e) {
            System.out.println("消息通知失败！" + e.getMessage( ));
            try {
                redisService.send_template_message(account.getAccountName( ), "user", Configure.PAY_USER_REMIND, tempToUser);
            } catch (Exception e1) {
                System.out.println("第二次消息通知失败！" + e.getMessage( ));
            }
        }
        // 判断是扫码支付还是订单支付
        if (StringUtils.isEmpty(userPayOrder.getOrganOrderId( ))) {
            account = accountService.getAccountByEntityID(userPayOrder.getOrganId( ), "organ");
            // 给店铺发送消息通知
            tempToUser = redisService.getWxTemplate("喵，用户订单支付成功啦~~", user.getNick( ), String.valueOf(userPayOrder.getPrice( )), organTime, null, null, "老板小兜装不下啦~~嘻嘻");
            try {
                redisService.send_template_message(account.getAccountName( ), "organ", Configure.ORDER_ORGAN_SUCCESS, tempToUser);
                saveMessage("user", user.get_id( ), user.getNick( ), "organ", organ.get_id( ), organ.getName( ), "1", user.getNick( ) + "订单支付成功啦~~老板小兜装不下啦~~嘻嘻");
            } catch (Exception e) {
                System.out.println("消息通知失败！" + e.getMessage( ));
            }
        } else {
            WeOrganOrder order = weCommonService.queryWeOrganOrder(userPayOrder.getOrganOrderId( ));
            // 预约的是技师
            // 给技师发送消息通知
            tempToUser = redisService.getWxTemplate("喵，预约订单支付成功喽~", String.valueOf(userPayOrder.getPrice( )), orderNo, null, null, null, "你好，喵粉已支付成功。");
            account = accountService.getAccountByEntityID(order.getStaffId( ), "staff");
            try {
                redisService.send_template_message(account.getAccountName( ), "staff", Configure.ORDER_STAFF_SUCCESS, tempToUser);
                saveMessage("user", user.get_id( ), user.getNick( ), "staff", staff.get_id( ), staff.getNick( ), "1", user.getNick( ) + " 的订单支付成功啦~~你好，喵粉已支付成功。");
            } catch (Exception e) {
                System.out.println("消息通知失败！" + e.getMessage( ));
            }
            // 再给店铺一个收款到账的消息通知
            account = accountService.getAccountByEntityID(userPayOrder.getOrganId( ), "organ");
            tempToUser = redisService.getWxTemplate("喵，用户预约" + staff.getName( ) + "的订单支付成功啦~~", user.getNick( ), String.valueOf(userPayOrder.getPrice( )), organTime, null, null, "老板小兜装不下啦~~嘻嘻");
            try {
                redisService.send_template_message(account.getAccountName( ), "organ", Configure.ORDER_ORGAN_SUCCESS, tempToUser);
                saveMessage("user", user.get_id( ), user.getNick( ), "organ", organ.get_id( ), organ.getName( ), "1", user.getNick( ) + " 的订单支付成功啦~~老板小兜装不下啦~~嘻嘻");
            } catch (Exception e) {
                System.out.println("消息通知失败！" + e.getMessage( ));
            }

        }
        couponGrantService.grantCouponByuserUuidAndType(payuser.get_id( ), "消费", userPayOrder.getPrice( ), orderId);
        // System.out.println("发送消息结束");
        result.setSuccess(true);
        return result;
    }

    @Override
    public ReturnStatus staffPaySuccess(Map<Object, Object> map)throws BaseException{

         return null;
    }



    @Override
    public ReturnStatus paySuccess(Map<Object, Object> map) throws BaseException {

        ReturnStatus result = new ReturnStatus(false);

        // 更新微信订单表状态
        String orderNo = map.get("out_trade_no").toString( );
        WeUserPayOrder userPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderNo)), WeUserPayOrder.class);
        // 获取当前支付用户的基本信息做日志输出
        User thisUser = userService.getUserById(userPayOrder.getUserId( ));
        System.out.println("用户" + thisUser.getNick( ) + "支付成功了,支付金额" + userPayOrder.getPrice( ) + "@@@@@@@@@@@@@@@@@@@@@@@@");
        // 支付成功之后对用户钱包进行清零

        if (userPayOrder.getWePrice( ) > 0) {
            thisUser.setWalletAmount(0);
            mongoTemplate.save(thisUser);
        }

        userPayOrder.setState(1);
        userPayOrder.setUpdateTimeIfNew( );
        String orderId = userPayOrder.getOrganOrderId( );
        if (! StringUtils.isEmpty(orderId)) {
            WeOrganOrder order = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeOrganOrder.class);
            order.setState(3);
            mongoTemplate.save(order);
        }
        mongoTemplate.save(userPayOrder);
        //更新优惠券使用状态
        if (! StringUtils.isEmpty(userPayOrder.getCouponId( ))) {
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(userPayOrder.getCouponId( ))), Update.update("isUsed", "1"), MyCoupon.class);
            //保存优惠券消费记录
            ConponRecord conponRecord = mongoTemplate.findOne(Query.query(Criteria.where("myCouponId").is(userPayOrder.getCouponId())),ConponRecord.class);
            conponRecord.setUsedTime(new Date());
            conponRecord.setIsUsed("1");
            mongoTemplate.save(conponRecord);
        }
        Organ organ = weOrganService.queryOrganById(userPayOrder.getOrganId( ));
        // 更新会员卡信息
        Criteria criteria = new Criteria( );
        criteria.andOperator(Criteria.where("organId").is(userPayOrder.getOrganId( )), Criteria.where("userId").is("0"));


        // 微信支付分账表插入数据
        WeSysConfig weSysConfig = weSysConfigService.query( );

        double userAmount = 0;
        double price = userPayOrder.getPrice( );
        long orderCount = 0;

        int p = (int) price;
        userAmount = getUserAmount(p, weSysConfig);
        userAmount = new BigDecimal(userAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );


        double organAmount = new BigDecimal(userPayOrder.getPrice( )).multiply(new BigDecimal(weSysConfig.getAllzhekou( ))).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
        double sysAmount = new BigDecimal(userPayOrder.getPrice( )).subtract(new BigDecimal(organAmount)).subtract(new BigDecimal(userAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );// 平台分账金额

        Usercard syscard = null;
        if (! organ.getIsNotPrepay( )) { // 预存模式
            syscard = mongoTemplate.findOne(new Query(criteria), Usercard.class);
            syscard.setUpdateTime(new Date( ));

            double money4 = new BigDecimal(syscard.getMoney4( )).subtract(new BigDecimal(organAmount)).doubleValue( );
            syscard.setMoney4(money4);
            if (money4 <= 200) { // 店铺平台卡余额小于200，短信通知平台管理员
                String phone = weSysConfig.getPhone( );
                if (! StringUtils.isEmpty(phone)) {
                    Map<String, String> params = new HashMap<>( );
                    params.put("name", organ.getName( ));
                    params.put("money", "200");

                    ReturnStatus s = SMSUtil.send(phone, Constants.getProperty("sms.sysCardMoneyNotEnoughTemplate"), params);
                    if (s.isSuccess( )) {
                        // ignore it
                    }
                }
            }
            mongoTemplate.save(syscard);
        }

        //添加首次消费返现的逻辑 店铺+  后台平台卡-
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userPayOrder.getUserId( ))), User.class);

            Criteria countCriteria = Criteria.where("userId").is(user.get_id( )).and("state").is(1);
            orderCount = mongoTemplate.count(Query.query(countCriteria), WeUserPayOrder.class);

        if ((1 == orderCount) && ("organ".equals(user.getAccountType())) && (userPayOrder.getOrganId().equals(user.getInvitor()))) {
            //获取返现金额 并给店铺返现
            double amount = new BigDecimal(organ.getWalletAmount( )).add(new BigDecimal(weSysConfig.getSaoMaFan( ))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue( );
            //更新店铺余额
            weOrganService.updateUserWalletAmount(organ.get_id(), amount);
            //更新历史记录 店铺端
            WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
            weUserWalletHis.setNewId();
            weUserWalletHis.setNewCreate();
            weUserWalletHis.setUserId(organ.get_id());
            weUserWalletHis.setSendUserId(user.get_id());
            weUserWalletHis.setAmount(weSysConfig.getSaoMaFan());
            weUserWalletHis.setDesc("店铺自荐用户首次消费奖励 " + weSysConfig.getSaoMaFan() + "元");
            mongoTemplate.insert(weUserWalletHis);// 添加店铺返现记录

            organAmount = Arith.add(organAmount, weSysConfig.getSaoMaFan()); //更新店铺分帐金额
            sysAmount = Arith.sub(sysAmount, weSysConfig.getSaoMaFan());//更新平台分帐金额
        }
        //更新分帐信息
        WeUserPayFenzhang userPayFenzhang = new WeUserPayFenzhang( );
        userPayFenzhang.setNewId( ); //分帐id
        userPayFenzhang.setNewCreate( );  //分帐时间
        userPayFenzhang.setOrderId(orderNo);  //订单id
        userPayFenzhang.setUserId(userPayOrder.getUserId( ));  //用户id
        userPayFenzhang.setOrganId(userPayOrder.getOrganId( ));  //店铺id
        userPayFenzhang.setPrice(userPayOrder.getPrice( ));  //用户支付金额
        userPayFenzhang.setOrganAmount(organAmount); //店铺钱包余额 已经分帐
        userPayFenzhang.setUserAmount(userAmount);  //用户钱包余额 已经分帐
        userPayFenzhang.setSysAmount(sysAmount); //平台分帐 已经分帐
        userPayFenzhang.setState(0);  //店铺分帐处理状态 0 未完成
        userPayFenzhang.setPayWeixin(userPayOrder.getWePrice( ));// 微信支付金额
        userPayFenzhang.setPayWallet(Arith.sub(userPayOrder.getPrice( ), userPayOrder.getWePrice( )));// 用户钱包支付金额
        userPayFenzhang.setCity(organ.getCity( )); //店铺所在城市
        userPayFenzhang.setDistrict(organ.getDistrict( ));
        userPayFenzhang.setRegion(organ.getRegion( ));
        userPayFenzhang.setExpense(userPayOrder.getExpense());
        userPayFenzhang.setBenefit(userPayOrder.getBenefit());



        if (organ.getIsNotPrepay( )) { // 非预存模式
            userPayFenzhang.setCardCount(0);  // 分账当时的店铺平台卡消费次数(本次消费之后)
            userPayFenzhang.setCardMoney4(0); //分账当时的店铺平台卡余额(本次消费之后)
            userPayFenzhang.setSysCardId("0"); // 0表示非预存消费分账

        } else if (syscard != null) { // 预存模式
            userPayFenzhang.setCardCount(syscard.getCome_num( )); // 消费后店铺平台卡总消费次数
            userPayFenzhang.setCardMoney4(syscard.getMoney4( )); // 消费后店铺平台卡余额
            userPayFenzhang.setSysCardId(syscard.get_id( ));

        }
        // 消费次数处理
        long count = mongoTemplate.count(Query.query(Criteria.where("userId").is(userPayFenzhang.getUserId( ))), WeUserPayFenzhang.class);
        userPayFenzhang.setCardCount((int) (count + 1));


        if ((1 == orderCount) && ("organ".equals(user.getAccountType())) && (userPayOrder.getOrganId().equals(user.getInvitor()))) {
            organAmount = Arith.sub(organAmount, weSysConfig.getSaoMaFan( ));
            userPayFenzhang.setFirstAmount(weSysConfig.getSaoMaFan( ));
        }
        if (organ.getIsNotPrepay( )) { // 非预存模式
            userPayFenzhang.setWalletAmount(organAmount);
            userPayFenzhang.setPrepayAmount(0);
        } else if (syscard != null) { // 预存模式
            userPayFenzhang.setWalletAmount(0);
            userPayFenzhang.setPrepayAmount(organAmount);
        }

        mongoTemplate.insert(userPayFenzhang); //更新分帐

        if (organ.getIsNotPrepay( )) { // 非预存模式，店铺部分分成放入店铺钱包余额
            mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(organ.get_id( ))), new Update( ).inc("walletAmount", organAmount), Organ.class);
            // 生成店铺全包余额变动记录
            WeUserWalletHis weOrganWalletHis = new WeUserWalletHis( );
            weOrganWalletHis.setNewId( );
            weOrganWalletHis.setNewCreate( );
            weOrganWalletHis.setOrderId("10");
            weOrganWalletHis.setUserId(organ.get_id( ));
            weOrganWalletHis.setAmount(organAmount);
            weOrganWalletHis.setDesc("用户微信支付");
            mongoTemplate.insert(weOrganWalletHis);
        }

        // 用户钱包插入记录
        if (Arith.sub(userPayOrder.getPrice( ), userPayOrder.getWePrice( )) != 0) {
            WeUserWalletHis weUserWalletConsume = new WeUserWalletHis( );
            weUserWalletConsume.setNewId( );
            weUserWalletConsume.setNewCreate( );
            weUserWalletConsume.setUserId(userPayOrder.getUserId( ));
            weUserWalletConsume.setAmount(- (Arith.sub(userPayOrder.getPrice( ), userPayOrder.getWePrice( ))));
            weUserWalletConsume.setDesc("消费支付");
            mongoTemplate.insert(weUserWalletConsume);// 添加用户消费记录
        }

        System.out.println("用户" + thisUser.getNick( ) + "支付成功了,支付金额" + userPayOrder.getPrice( ) + "用户余额" + user.getWalletAmount( ) + "返现" + userAmount + "@@@@@@@@@@@@@@@@@@@@@@@@");
        double walletAmount = new BigDecimal(user.getWalletAmount( )).add(new BigDecimal(userAmount)).doubleValue( );
        user.setWalletAmount(walletAmount);
        mongoTemplate.save(user);
        System.out.println("用户" + thisUser.getNick( ) + "支付成功了,返现之后的钱包余额为：" + user.getWalletAmount( ) + "@@@@@@@@@@@@@@@@@@@@@@@@");

		/*
         * WeMessage msg = new WeMessage();
		 *
		 * msg.setIdIfNew(); msg.setFromType("user");
		 * msg.setFromId(payuser.get_id()); msg.setFromName(payuser.getNick());
		 * msg.setToType("organ"); msg.setToId(organ.get_id());
		 * msg.setToName(organ.getName()); msg.setType("2");
		 * msg.setContent(payuser.getNick() + " 订单支付成功");
		 *
		 * msg.setCreateTimeFormat(organTime); weCommonService.saveMessage(msg);
		 */
        User payuser = weUserService.queryUserById(userPayOrder.getUserId( ));
        String organTime = getTime(new Date( ), "yyyy/MM/dd HH:mm");
        // 用户支付完信息给用户添加消息
        WeMessage userMsg = new WeMessage( );
        userMsg.setIdIfNew( );
        userMsg.setFromType("organ");
        userMsg.setFromId(organ.get_id( ));
        userMsg.setFromName(organ.getName( ));
        userMsg.setToType("user");
        userMsg.setToId(payuser.get_id( ));
        userMsg.setToName(payuser.getNick( ));
        userMsg.setType("1");
        userMsg.setReadFalg(false);
        userMsg.setContent("店铺 " + organ.getName( ) + " 的订单您已经成功支付");
        String userTime = getTime(new Date( ), "yyyy/MM/dd HH:mm");
        userMsg.setCreateTimeFormat(userTime);
        weCommonService.saveMessage(userMsg);
        // 用户通知
        // 给技师返现
        if (! StringUtils.isEmpty(user.getInvitor( )) && ("staff".equals(user.getAccountType())) && user.getInviteDate( ) != null) {
            if (! userMyService.greaterThanYear(new Date( ), user.getInviteDate( ))) {
                userMyService.moneyToStaff(user.getInvitor( ), userPayOrder.getPrice( ), user.get_id( ));
            }
        }

        Account account = accountService.getAccountByEntityID(user.get_id( ), "user");
        WxTemplate tempToUser = redisService.getWxTemplate("喵，您的订单支付已成功啦~", orderNo, String.valueOf(userPayOrder.getPrice( )), organ.getName( ), organTime, null, "感谢您对小喵的信任，这次支付为您返现" + userAmount + "元已充值到钱包，喵小二恭候您再次光临~");
        try {
            redisService.send_template_message(account.getAccountName( ), "user", Configure.PAY_USER_REMIND, tempToUser);
        } catch (Exception e) {
            System.out.println("消息通知失败！" + e.getMessage( ));
            try {
                redisService.send_template_message(account.getAccountName( ), "user", Configure.PAY_USER_REMIND, tempToUser);
            } catch (Exception e1) {
                System.out.println("第二次消息通知失败！" + e.getMessage( ));
            }
        }
        // 判断是扫码支付还是订单支付
        if (StringUtils.isEmpty(userPayOrder.getOrganOrderId( ))) {
            account = accountService.getAccountByEntityID(userPayOrder.getOrganId( ), "organ");
            // 给店铺发送消息通知
            tempToUser = redisService.getWxTemplate("喵，用户订单支付成功啦~~", user.getNick( ), String.valueOf(userPayOrder.getPrice( )), organTime, null, null, "老板小兜装不下啦~~嘻嘻");
            try {
                redisService.send_template_message(account.getAccountName( ), "organ", Configure.ORDER_ORGAN_SUCCESS, tempToUser);
                saveMessage("user", user.get_id( ), user.getNick( ), "organ", organ.get_id( ), organ.getName( ), "1", user.getNick( ) + "订单支付成功啦~~老板小兜装不下啦~~嘻嘻");
            } catch (Exception e) {
                System.out.println("消息通知失败！" + e.getMessage( ));
            }
        } else {
            WeOrganOrder order = weCommonService.queryWeOrganOrder(userPayOrder.getOrganOrderId( ));
            // 判断是预约的店铺还是预约的技师
            if (StringUtils.isEmpty(order.getStaffId( ))) {
                // 预约的店铺
                // 给店铺发送消息通知
                account = accountService.getAccountByEntityID(userPayOrder.getOrganId( ), "organ");
                tempToUser = redisService.getWxTemplate("喵，用户订单支付成功啦~~", user.getNick( ), String.valueOf(userPayOrder.getPrice( )), organTime, null, null, "老板小兜装不下啦~~嘻嘻");
                try {
                    redisService.send_template_message(account.getAccountName( ), "organ", Configure.ORDER_ORGAN_SUCCESS, tempToUser);
                    saveMessage("user", user.get_id( ), user.getNick( ), "organ", organ.get_id( ), organ.getName( ), "1", user.getNick( ) + " 的订单支付成功啦~~老板小兜装不下啦~~嘻嘻");
                } catch (Exception e) {
                    System.out.println("消息通知失败！" + e.getMessage( ));
                }
            } else {
                // 预约的技师
                // 给技师发送消息通知
                tempToUser = redisService.getWxTemplate("喵，预约订单支付成功喽~", String.valueOf(userPayOrder.getPrice( )), orderNo, null, null, null, "你好，喵粉已支付成功。");
                account = accountService.getAccountByEntityID(order.getStaffId( ), "staff");
                Staff staff = staffService.queryById(account.getEntityID( ));
                try {
                    redisService.send_template_message(account.getAccountName( ), "staff", Configure.ORDER_STAFF_SUCCESS, tempToUser);
                    saveMessage("user", user.get_id( ), user.getNick( ), "staff", staff.get_id( ), staff.getNick( ), "1", user.getNick( ) + " 的订单支付成功啦~~你好，喵粉已支付成功。");
                } catch (Exception e) {
                    System.out.println("消息通知失败！" + e.getMessage( ));
                }
                // 再给店铺一个收款到账的消息通知
                account = accountService.getAccountByEntityID(userPayOrder.getOrganId( ), "organ");
                tempToUser = redisService.getWxTemplate("喵，用户预约" + staff.getName( ) + "的订单支付成功啦~~", user.getNick( ), String.valueOf(userPayOrder.getPrice( )), organTime, null, null, "老板小兜装不下啦~~嘻嘻");
                try {
                    redisService.send_template_message(account.getAccountName( ), "organ", Configure.ORDER_ORGAN_SUCCESS, tempToUser);
                    saveMessage("user", user.get_id( ), user.getNick( ), "organ", organ.get_id( ), organ.getName( ), "1", user.getNick( ) + " 的订单支付成功啦~~老板小兜装不下啦~~嘻嘻");
                } catch (Exception e) {
                    System.out.println("消息通知失败！" + e.getMessage( ));
                }
            }
        }
        couponGrantService.grantCouponByuserUuidAndType(payuser.get_id( ), "消费", userPayOrder.getPrice( ), orderId);
        // System.out.println("发送消息结束");
        result.setSuccess(true);
        return result;
    }

    /**
     * 订单支付
     */
    @Override
    public ReturnStatus saveUserPayOrder(String userId, String organId, double price, String orderId, HttpServletRequest request) throws BaseException {
        ReturnStatus result = new ReturnStatus(true);

        Usercard sysQuery = this.sysQuery(organId);
        if (sysQuery.getMoney4( ) < price) {
            result.setSuccess(false);
            result.setMessage("卡余额不足");
        } else {
            WeUserPayOrder userPayOrder = new WeUserPayOrder( );
            userPayOrder.setNewId( );
            userPayOrder.setNewCreate( );
            userPayOrder.setUserId(userId);
            userPayOrder.setOrganId(organId);
            userPayOrder.setPrice(price);
            userPayOrder.setSysCardId(sysQuery.get_id( ));
            userPayOrder.setOrganOrderId(orderId);
            mongoTemplate.insert(userPayOrder);

            SortedMap<Object, Object> params = getPrepay_id(request, userPayOrder);
            result.setParams(params);
            params.put("userPayOrder", userPayOrder);
        }
        return result;
    }

    /**
     * 在线支付
     */
    @Override
    public ReturnStatus saveUserpayOnline(String userId, String cardId, double price, HttpServletRequest request) throws BaseException {
        ReturnStatus result = new ReturnStatus(true);

        WeUserPayOrder userPayOrder = new WeUserPayOrder( );
        Userincard card = userService.getInCard(cardId);
        userPayOrder.setNewId( );
        userPayOrder.setNewCreate( );
        userPayOrder.setUserId(userId);
        userPayOrder.setOrganId(card.getOrganId( ));
        userPayOrder.setPrice(price);
        // userPayOrder.setSysCardId(sysQuery.get_id());
        // userPayOrder.setOrganOrderId(orderId);
        userPayOrder.setIncardId(cardId);
        userPayOrder.setType(1);
        mongoTemplate.insert(userPayOrder);

        SortedMap<Object, Object> params = getPrepay_id(request, userPayOrder);
        result.setParams(params);
        params.put("userPayOrder", userPayOrder);

        return result;
    }

    @Override
    public ReturnStatus cancelUserPayOrder(String orderNo) throws BaseException {
        ReturnStatus result = new ReturnStatus(false);
        WeUserPayOrder userPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderNo)), WeUserPayOrder.class);
        if(userPayOrder != null){
            userPayOrder.setState(2);
            userPayOrder.setUpdateTimeIfNew( );
            mongoTemplate.save(userPayOrder);
            result.setSuccess(true);
        }
        return result;
    }

    @Override
    public ReturnStatus cancelPositionOrder(String orderNo)  {
        ReturnStatus result = new ReturnStatus(false);
        PositionOrder positionOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderNo)), PositionOrder.class);
        if( positionOrder != null){
            positionOrder.setState(3); //微信支付未完成
            positionOrder.setWxMoney(0.0); //设置支付金额为0
            mongoTemplate.save(positionOrder); //预约订单表

            Update update = new Update( );
            update.set("state", 3); //修改状态
            mongoTemplate.updateMulti(Query.query(Criteria.where("positionOrderId").is(orderNo)), update, OrganPositionDetails.class);

            result.setSuccess(true);
        }
        return result;
    }

    @Override
    public WeUserPayOrder getWeUserPayOrder(String orderId) {
        WeUserPayOrder order = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeUserPayOrder.class);
        return order;
    }

    public String getTime(Date date, String reg) {
        SimpleDateFormat fom = new SimpleDateFormat(reg);
        String time = fom.format(date);
        return time;
    }

    @Override
    public Usersort getUsersortById(String usersortId) {
        Usersort usersort = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(usersortId)), Usersort.class);
        return usersort;
    }

    public ReturnStatus saveMessage(String fromType, String fromId, String fromName, String toType, String toId, String toName, String type, String content) {
        WeMessage message = new WeMessage( );
        message.setIdIfNew( );
        message.setFromId(fromId);
        message.setFromName(fromName);
        message.setToType(toType);
        message.setToId(toId);
        message.setToName(toName);
        message.setType(type);
        message.setContent(content);
        message.setReadFalg(false);
        String organTime = getTime(new Date( ), "yyyy/MM/dd HH:mm");
        message.setCreateTimeFormat(organTime);
        weCommonService.saveMessage(message);
        return new ReturnStatus(true);
    }

    @Override
    public FlipInfo<WeUserWalletHis> findTixian(FlipInfo<WeUserWalletHis> pp) throws BaseException {
        pp.setSortField("createTime");
        pp.setSortOrder("DESC");

        mongoTemplate.findByPage(null, pp, WeUserWalletHis.class);
        getWalletHisDetail(pp.getData( ));
        return pp;
    }

    private List<WeUserWalletHis> getWalletHisDetail(List<WeUserWalletHis> l) {
        List<String> userIds = new ArrayList<>( );
        List<String> staffIds = new ArrayList<>( );
        for (WeUserWalletHis w : l) {
            if ("1".equals(w.getOrderId( ))) {
                userIds.add(w.getUserId( ));
            } else if ("2".equals(w.getOrderId( ))) {
                staffIds.add(w.getUserId( ));
            }
        }
        Map<String, User> userMap = new HashMap<>( );
        Map<String, Staff> staffMap = new HashMap<>( );
        if (userIds.size( ) > 0) {
            List<User> users = mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)), User.class);
            for (User u : users) {
                userMap.put(u.get_id( ), u);
            }
        }
        if (staffIds.size( ) > 0) {
            List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("_id").in(staffIds)), Staff.class);
            for (Staff s : staffs) {
                staffMap.put(s.get_id( ), s);
            }
        }

        for (WeUserWalletHis w : l) {
            User u = userMap.get(w.getUserId( ));
            Staff s = staffMap.get(w.getUserId( ));
            if (u != null) {
                w.setUserName(StringUtils.isEmpty(u.getName( )) ? u.getNick( ) : u.getName( ));
                w.setUserPhone(u.getPhone( ));
            } else if (s != null) {
                w.setUserName(s.getName( ));
                w.setUserPhone(s.getPhone( ));
            }

        }

        return l;
    }

    @Override
    public UserTixianSum tixianSum(String type, Date startTime, Date endTime) throws BaseException {
        Criteria criteria = new Criteria( );
        List<Criteria> criterias = new ArrayList<>( );
        if (! StringUtils.isEmpty(type)) {
            criterias.add(Criteria.where("orderId").in(Arrays.asList(type.split(","))));
        } else {
            criterias.add(Criteria.where("orderId").in("1", "2"));
        }
        if (startTime != null) {
            criterias.add(Criteria.where("createTime").gte(startTime));
        }
        if (endTime != null) {
            criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
        }
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria.andOperator(criterias.toArray(new Criteria[criterias.size( )]))), Aggregation.group("orderId").sum("amount").as("total"));

        AggregationResults<UserTixianSum> ar = mongoTemplate.aggregate(aggregation, WeUserWalletHis.class, UserTixianSum.class);
        List<UserTixianSum> list = ar.getMappedResults( );
        UserTixianSum sum = new UserTixianSum( );
        for (UserTixianSum s : list) {
            sum.setTotal(sum.getTotal( ) + s.getTotal( ));
        }
        return sum;
    }


    @Override
    public ReturnStatus saveUserBalancePayOrderToStaff(String userId, String organId, double price, String orderId, String staffId, String couponId, HttpServletRequest request) throws Exception {
        ReturnStatus result = new ReturnStatus(true);
        double expense = price;//得到优惠券的支付金额
        double benefit = 0.00;
        if (! StringUtils.isEmpty(couponId)) {
            MyCoupon myCoupon = mongoTemplate.findById(couponId, MyCoupon.class);
            if ("1".equals(myCoupon.getIsUsed( ))) {
                result.setSuccess(false);
                result.setMessage("优惠券已被使用");
                return result;
            }
            if (myCoupon.getEndTime( ).getTime( ) < new Date( ).getTime( )) {
                result.setSuccess(false);
                result.setMessage("优惠券已过期");
                return result;
            }
            Double moneyOrRatio = myCoupon.getMoneyOrRatio();//优惠的钱或比例
            if (myCoupon.getMoneyType( ) == 0) {//0代表优惠金额
                price = price - moneyOrRatio;
                benefit = moneyOrRatio.doubleValue();
            } else if (myCoupon.getMoneyType( ) == 1) {//1代表折扣
                price = price * moneyOrRatio;
                benefit = expense - price;
            }
        }
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);

        //判断余额是否足够
        if (user.getWalletAmount( ) < price) {
            WeUserPayOrder userPayOrder = new WeUserPayOrder( );
            userPayOrder.setNewId( );
            userPayOrder.setNewCreate( );
            userPayOrder.setUserId(userId);
            userPayOrder.setOrganId(organId);
            userPayOrder.setPrice(price);// 总价格
            // 微信支付的钱数
            userPayOrder.setWePrice((new BigDecimal(price).subtract(new BigDecimal(user.getWalletAmount( )))).doubleValue( ));
            userPayOrder.setStaffId(staffId);
            userPayOrder.setIsPayByScan(1);//默认 PayOrderType 1 扫码支付
            userPayOrder.setType(3); //微信支付技师分账模式
            if (! StringUtils.isEmpty(couponId)) {//当使用优惠券才存这2个属性
                userPayOrder.setCouponId(couponId);
                userPayOrder.setExpense(expense);
                userPayOrder.setBenefit(benefit);
            }else {
                userPayOrder.setExpense(price);//当不使用时 存总金额
            }
            if (! StringUtils.isEmpty(orderId)) { //有orderId 为订单支付
                userPayOrder.setOrganOrderId(orderId);
                userPayOrder.setIsPayByScan(0);
            }
            //保存订单信息
            mongoTemplate.save(userPayOrder);
            //调用微信支付
            SortedMap<Object, Object> params = getPrepay_id(request, userPayOrder);
            params.put("userPayOrder", userPayOrder);

            result.setParams(params);
            result.setMessage(userPayOrder.get_id( ));
        } else {
            // 插入微信支付订单信息
            WeUserPayOrder userPayOrder = new WeUserPayOrder( );
            userPayOrder.setNewId( );
            userPayOrder.setNewCreate( );
            userPayOrder.setUserId(userId);
            userPayOrder.setOrganId(organId);
            userPayOrder.setPrice(price);
            userPayOrder.setWePrice(0);// 微信支付金额
            userPayOrder.setType(0);
            userPayOrder.setStaffId(staffId);
            userPayOrder.setIsPayByScan(1); //扫码支付
            if (! StringUtils.isEmpty(couponId)) {//当使用优惠券才存这2个属性
                userPayOrder.setCouponId(couponId);
                userPayOrder.setExpense(expense);
                userPayOrder.setBenefit(benefit);
            }else {
                userPayOrder.setExpense(price);//当不使用时 存总金额
            }
            if (! StringUtils.isEmpty(orderId)) {
                userPayOrder.setOrganOrderId(orderId);
                userPayOrder.setIsPayByScan(0);
            }

            mongoTemplate.save(userPayOrder);

            if (user != null) {
                double amount = Arith.sub(user.getWalletAmount( ), price);
                user.setWalletAmount(amount);
                mongoTemplate.save(user);
            }
            SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
            params.put("out_trade_no", userPayOrder.get_id( ));
            ReturnStatus res = payForStaffSuccess(params);
            if (! res.isSuccess( )) {
                result.setSuccess(false);
                result.setMessage("支付失败");
                user.setWalletAmount(user.getWalletAmount( ) + price);
                mongoTemplate.save(user);
            } else {
                params.put("userPayOrder", userPayOrder);
                result.setParams(params);
                result.setMessage(userPayOrder.get_id( ));
            }

        }
        return result;

    }


    @Override
    public ReturnStatus saveUserBalancePayOrder(String userId, String organId, double price, String orderId, String couponId, HttpServletRequest request) throws BaseException {
        ReturnStatus result = new ReturnStatus(true);
        double expense = price;//得到优惠券的支付金额
        double benefit = 0.00;
        if (! StringUtils.isEmpty(couponId)) {
            MyCoupon myCoupon = mongoTemplate.findById(couponId, MyCoupon.class);
            if ("1".equals(myCoupon.getIsUsed( ))) {
                result.setSuccess(false);
                result.setMessage("优惠券已被使用");
                return result;
            }
            if (myCoupon.getEndTime( ).getTime( ) < new Date( ).getTime( )) {
                result.setSuccess(false);
                result.setMessage("优惠券已过期");
                return result;
            }
            Double moneyOrRatio = myCoupon.getMoneyOrRatio();//优惠的钱或比例
            if (myCoupon.getMoneyType( ) == 0) {//0代表优惠金额
                price = price - moneyOrRatio;
                benefit = moneyOrRatio.doubleValue();
            } else if (myCoupon.getMoneyType( ) == 1) {//1代表折扣
                price = price * moneyOrRatio;
                benefit = expense - price;
            }
        }
        Organ organ = weOrganService.queryOrganById(organId);
        Usercard sysQuery = this.sysQuery(organId);
        if (! organ.getIsNotPrepay( ) && sysQuery.getMoney4( ) < price) {

            result.setSuccess(false);
            result.setMessage("平台会员卡余额不足");
        } else {
            // 判断用户钱包余额是否能支付
            // 修改用户钱包余额
            User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
            if (user.getWalletAmount( ) < price) {// 余额不足的话差价调用微信支付
                // result.setSuccess(false);
                // result.setMessage("余额不足");
                // 创建微信支付信息金额为总金额
                WeUserPayOrder userPayOrder = new WeUserPayOrder( );
                userPayOrder.setNewId( );
                userPayOrder.setNewCreate( );
                userPayOrder.setUserId(userId);
                userPayOrder.setOrganId(organId);
                userPayOrder.setPrice(price);// 总价格
                // 微信支付的钱数
                userPayOrder.setWePrice((new BigDecimal(price).subtract(new BigDecimal(user.getWalletAmount( )))).doubleValue( ));
                userPayOrder.setSysCardId(sysQuery.get_id( ));

                userPayOrder.setIsPayByScan(1);//默认 PayOrderType 1 扫码支付
                userPayOrder.setType(0);
                if (! StringUtils.isEmpty(couponId)) {//当使用优惠券才存这2个属性
                    userPayOrder.setCouponId(couponId);
                    userPayOrder.setExpense(expense);
                    userPayOrder.setBenefit(benefit);
                }else {
                    userPayOrder.setExpense(price);//当不使用时 存总金额
                }
                if (! StringUtils.isEmpty(orderId)) { //有orderId 为订单支付
                    userPayOrder.setOrganOrderId(orderId);
                    userPayOrder.setIsPayByScan(0);
                }
                mongoTemplate.save(userPayOrder);
                // 下面是钱包余额支付
                // if(user!=null){ 这里不对用户余额进行扣除，在微信支付成功之后回调页面进行扣除
                // user.setWalletAmount(0);
                // mongoTemplate.save(user);
                // }
                // 下面是调用微信支付
                SortedMap<Object, Object> params = getPrepay_id(request, userPayOrder);
                params.put("userPayOrder", userPayOrder);
                result.setParams(params);
                result.setMessage(userPayOrder.get_id( ));
            } else {// 余额支付
                // 插入微信支付订单信息
                WeUserPayOrder userPayOrder = new WeUserPayOrder( );
                userPayOrder.setNewId( );
                userPayOrder.setNewCreate( );
                userPayOrder.setUserId(userId);
                userPayOrder.setOrganId(organId);
                userPayOrder.setPrice(price);
                userPayOrder.setWePrice(0);// 微信支付金额
                userPayOrder.setSysCardId(sysQuery.get_id( ));
                userPayOrder.setType(0);
                userPayOrder.setIsPayByScan(1); //扫码支付
                if (! StringUtils.isEmpty(couponId)) {//当使用优惠券才存这2个属性
                    userPayOrder.setCouponId(couponId);
                    userPayOrder.setExpense(expense);
                    userPayOrder.setBenefit(benefit);
                }else {
                    userPayOrder.setExpense(price);//当不使用时 存总金额
                }
                if (! StringUtils.isEmpty(orderId)) {
                    userPayOrder.setOrganOrderId(orderId);
                    userPayOrder.setIsPayByScan(0);
                }

                mongoTemplate.save(userPayOrder);

                if (user != null) {
                    // user.setWalletAmount(user.getWalletAmount()-price);
                    // user.setWalletAmount((new
                    // BigDecimal(user.getWalletAmount()).subtract(new
                    // BigDecimal(price))).doubleValue());
                    double amount = Arith.sub(user.getWalletAmount( ), price);
                    user.setWalletAmount(amount);
                    mongoTemplate.save(user);
                }
                SortedMap<Object, Object> params = new TreeMap<Object, Object>( );
                params.put("out_trade_no", userPayOrder.get_id( ));
                ReturnStatus res = paySuccess(params);
                if (! res.isSuccess( )) {
                    result.setSuccess(false);
                    result.setMessage("支付失败");
                    user.setWalletAmount(user.getWalletAmount( ) + price);
                    mongoTemplate.save(user);
                } else {
                    params.put("userPayOrder", userPayOrder);
                    result.setParams(params);
                    result.setMessage(userPayOrder.get_id( ));
                }
            }
        }
        return result;
    }


    @Override
    public Usercard queryUserCardById(String cardId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cardId)), Usercard.class);
    }

    // 红包微信支付
    @Override
    public ReturnStatus saveHongBaoOrder(String staffId, String hongbaoId, HttpServletRequest request) {
        ReturnStatus status = new ReturnStatus(true);
        Staff staff = mongoTemplate.findById(staffId, Staff.class);
        WeRed weRed = mongoTemplate.findById(hongbaoId, WeRed.class);
        if (staff != null && weRed != null) {
            // 钱包不足余额支付
            WeUserPayOrder userPayOrder = new WeUserPayOrder( );
            userPayOrder.setNewId( );
            userPayOrder.setNewCreate( );
            userPayOrder.setUserId(staffId);
            userPayOrder.setOrganId(staff.getOrganId( ));
            userPayOrder.setPrice(weRed.getAmount( ));// 总价格
            // 微信支付的钱数
            userPayOrder.setWePrice(Arith.sub(weRed.getAmount( ), staff.getTotalIncome( )));
            userPayOrder.setOrganOrderId(weRed.get_id( ));
            userPayOrder.setType(2);
            mongoTemplate.insert(userPayOrder);
            // 下面是钱包余额支付
            staff.setTotalIncome(0);
            mongoTemplate.save(staff);
            // 下面是调用微信支付
            SortedMap<Object, Object> params = getPrepay_id_staff(request, userPayOrder);
            params.put("userPayOrder", userPayOrder);
            status.setParams(params);
            status.setMessage(hongbaoId);
            return status;
        } else {
            status.setSuccess(false);
            return status;
        }
    }

    // 红包微信支付成功回调
    @Override
    public ReturnStatus payHongbaoSuccess(Map<Object, Object> map) {
        ReturnStatus result = new ReturnStatus(false);
        // 更新微信订单表状态
        String orderNo = map.get("out_trade_no").toString( );
        WeUserPayOrder userPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderNo)), WeUserPayOrder.class);
        userPayOrder.setState(1);
        userPayOrder.setUpdateTimeIfNew( );
        if (! StringUtils.isEmpty(userPayOrder.getOrganOrderId( ))) {
            WeRed weRed = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userPayOrder.getOrganOrderId( ))), WeRed.class);
            weRed.setState(1);
            mongoTemplate.save(weRed);
        }
        mongoTemplate.save(userPayOrder);
        result.setSuccess(true);
        return result;
    }
}
