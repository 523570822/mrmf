package com.mrmf.module.staff;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.*;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.organPisition.StaffProfit;
import com.mrmf.entity.organPisition.FormPositionBean;
import com.mrmf.entity.wxpay.CommonUtil;
import com.mrmf.service.organPosition.OrganPosition;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.QRCodeUtil;
import com.osg.framework.web.cache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.CashUtil;
import com.mrmf.service.common.Configure;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffMyService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.usermy.UserMyService;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.PositionUtil;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/staffMy")
public class StaffMyController {
    @Autowired
    private StaffMyService staffMyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMyService userMyService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private OrganPosition organPosition;
    @Autowired
    private WeComonService weComonService;
    @Autowired
    private WeOrganService weOrganService;
    private static Logger logger = Logger.getLogger(StaffMyController.class);

    /**
     * 工位租金支付
     * @param bean
     */
    //工位租赁模式支付(钱包余额支付)
    @RequestMapping("/staffPayPositionOrder")
    @ResponseBody
    public ReturnStatus staffPayPositionOrder(PositionOrder positionOrder, FormPositionBean bean, HttpServletRequest request) throws Exception {
        ReturnStatus result = organPosition.staffPayPositionOrder(positionOrder,bean,request);
        return result;

    }

    /**
     * 跳转界面 我的二维码
     *
     * @param staffId
     * @param req
     * @return
     */
    @RequestMapping(value = "/code")
    public ModelAndView toCode(@RequestParam(required = false) String staffId, HttpServletRequest req) {
        Staff staff = staffMyService.getStaff(staffId);
        req.setAttribute("staff", staff);
        return new ModelAndView("staff/mine/myCode");
    }

    /**
     * 获取技师所在店铺
     */
    @RequestMapping(value = "/organList")
    @ResponseBody
    public ReturnStatus organList(@RequestParam(required = false) String staffId,HttpServletRequest request) throws Exception {;
        List<String> organIds = organPosition.getPositionInfo(staffId);
        List<Organ> organList = new ArrayList<>( );
        for(String organId:organIds){
            Organ organ = weOrganService.queryOrganById(organId);
            if(!organList.contains(organ)){
                organList.add(organ);
            }
        }
        if (0 == organList.size( )) {
            return new ReturnStatus(false,"对不起，您所在的店铺没有开通租赁模式！");
        } else if (1 == organList.size( )) {
            return new ReturnStatus(1);
        } else {
            return new ReturnStatus(2);

        }
    }

    /**
     *公司只有一个 直接生成
     */
    @RequestMapping(value = "/toMycode")
    @ResponseBody
    public ModelAndView toMycode(String staffId,HttpServletRequest request ) throws BaseException, ParseException {
        List<String> organIds = organPosition.getPositionInfo(staffId);
        List<Organ> organList = new ArrayList<>( );
        for(String organId:organIds){
            Organ organ = weOrganService.queryOrganById(organId);
            if(!organList.contains(organ)){
                organList.add(organ);
            }
        }
        Staff staff = staffMyService.getStaff(staffId);
        request.setAttribute("staff", staff);
        request.setAttribute("organ", organList.get(0));
        return new ModelAndView("staff/mine/myCode");

    }

    /**
     *公司超过1个 去选择公司
     */
    @RequestMapping(value = "/toChooseCode")
    @ResponseBody
    public ModelAndView toChooseCode(String staffId,HttpServletRequest request ) throws BaseException, ParseException {

        List<String> organIds = organPosition.getPositionInfo(staffId);
        List<Organ> organList = new ArrayList<>( );
        for(String organId:organIds){
            Organ organ = weOrganService.queryOrganById(organId);
            if(!organList.contains(organ)){
                organList.add(organ);
            }
        }
        request.setAttribute("staffId", staffId);
        request.setAttribute("organ", organList);

        return new ModelAndView("staff/mine/chooseOrgan");
    }


    /**
     * 跳转我的二维码界面
     *
     */
    @RequestMapping(value = "/toMyCodes")
    public ModelAndView toMyCode(HttpServletRequest req) {
        String organId = req.getParameter("organId");
        String staffId = req.getParameter("staffId");
        Staff staff = staffMyService.getStaff(staffId);
        Organ organ = staffMyService.findOneOrgan(organId);
        req.setAttribute("organ", organ);
        req.setAttribute("staff", staff);
        return new ModelAndView("staff/mine/myCode");
    }

    //生成店铺二维码
    @RequestMapping("/toTwoCode")
    public ModelAndView toTwoCode(@RequestParam(required = false) String staffId,HttpServletRequest request)throws Exception{
        String TOKEN = "";
        try {
            WeToken weToken = redisService.getTonkenInfo("user");
            TOKEN = weToken.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("t推荐人userID"+staffId);
        JSONObject action_info = new JSONObject();
        JSONObject scene_id = new JSONObject();

        int time = weComonService.getTime();
        String str = "18"+time;
        int rNum = Integer.parseInt(str);
        System.out.println(rNum);
        scene_id.put("scene_id", rNum);
        Staff staff = staffService.queryStaffById(staffId);
        action_info.put("scene", scene_id);
        cacheManager.save(rNum+"",staff);
        System.out.println("推荐redis 存储rum ="+rNum);
        System.out.println("redis 店铺rnum+user"+staff);
        JSONObject paramJSON = new JSONObject();
        paramJSON.put("expire_seconds",1800);
        paramJSON.put("action_name","QR_SCENE");
        paramJSON.put("action_info",action_info);

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + TOKEN + "";//返回类型是二维码，scope获取用户信息类型
        String s = doPostJson(url, paramJSON.toString());
        JSONObject parse = (JSONObject) JSONObject.parse(s);
        String ticket = parse.get("ticket").toString();
        String ticket1 = CommonUtil.urlEncodeUTF8(ticket);
        String url2 = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket1 + "";
        ModelAndView mv = new ModelAndView();
        mv.addObject("showqrcode",url2);
        mv.setViewName("staff/mine/myTwoCode");
        return mv;
    }
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }
    /**
     * 生成二维码
     *
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(value = "/mycode")
    public ReturnStatus makeCode(HttpServletRequest req, HttpServletResponse res) {
        String organId = req.getParameter("organId");
        String staffId = req.getParameter("staffId");
        String stateId = organId + "a" + staffId;
        try {
            InputStream is = new FileInputStream(new File(req.getRealPath("icon_logo.png")));
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Configure.staffAppID+"&redirect_uri=";//wxbc28c13034c811ed
            url += URLEncoder.encode(Configure.DOMAIN_URL + "/mrmf/w/pay/wxSaoMaToPayStaff.do", "GBK");
            url += "&response_type=code&scope=snsapi_userinfo&state=" + stateId + "#wechat_redirect";
            QRCodeUtil.encodeQRCode(url, res.getOutputStream( ), 2, is);
            is.close( );
            return null;
        } catch (IOException e) {
            return new ReturnStatus(false, e.getMessage( ));
        }

    }

    @RequestMapping("/toLastPage")
    public ModelAndView toLastPage(@RequestParam(required = false) String staffId, String status, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        request.setAttribute("staffId", staffId);
        long messageCount = staffService.findMessageCount(staff.get_id( ));
        request.setAttribute("messageCount", messageCount);
        if (status.equals("orderDetail")) {
            return myOrder(staff.get_id( ), request);
        } else if (status.equals("myOrder")) {
            return new ModelAndView("staff/mine/mine");
        } else if (status.equals("myEarn")) {
            return new ModelAndView("staff/mine/mine");
        } else if (status.equals("withdraw")) {
            return myEarn(request);
        } else if (status.equals("setup")) {
            return new ModelAndView("staff/mine/mine");
        } else if (status.equals("feedBack")) {
            return setup(request);
        }
        return null;
    }

    @RequestMapping("/myEstimation")
    public ModelAndView myEstimation(@RequestParam(required = false) String staffId, String page, String size, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff2 = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staff2.get_id( ));
        request.setAttribute("staff", staff);
        return new ModelAndView("staff/myEstimation/myEstimation");
    }

    //获取评价列
    @RequestMapping("/estimationList")
    @ResponseBody
    public FlipInfo<WeOrganComment> estimationList(@RequestParam(required = false) String staffId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipPageInfo<WeOrganComment> flp = new FlipPageInfo<WeOrganComment>(request);
        flp.getParams( ).remove("staffId");
        FlipInfo<WeOrganComment> comment = staffMyService.getMyComment(staff.get_id( ), flp);
        return comment;
    }

    @RequestMapping("/myMessage")
    public ModelAndView myMessage(HttpServletRequest request) throws Exception {
        return new ModelAndView("staff/myMessage/myMessage");
    }

    @RequestMapping("/myMessageList")
    @ResponseBody
    public FlipInfo<WeMessage> myMessageList(@RequestParam(required = false) String staffId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipPageInfo<WeMessage> flp = new FlipPageInfo<WeMessage>(request);
        flp.getParams( ).remove("staffId");
        flp.getParams( ).remove("type");
        FlipInfo<WeMessage> message = staffMyService.myMessageList(staff.get_id( ), type, flp);
        return message;
    }

    @RequestMapping("/conformOrder")
    public ModelAndView conformOrder(@RequestParam(required = false) String orderId, HttpServletRequest request) throws Exception {
        staffMyService.conformOrder(orderId);
        return new ModelAndView("staff/myMessage/myMessage");
    }

    @RequestMapping("/toRefusePage")
    public ModelAndView toRefusePage(@RequestParam(required = false) String orderId, HttpServletRequest request) throws Exception {
        request.setAttribute("orderId", orderId);
        return new ModelAndView("staff/myOrder/deny");
    }

    @RequestMapping("/refuseOrder")
    public ModelAndView refuseOrder(@RequestParam(required = false) String orderId, String refuseComment, HttpServletRequest request) throws Exception {
        staffMyService.refuseOrder(orderId, refuseComment);
        return null;
    }

    @RequestMapping("/toCustomerPage")
    public ModelAndView toCustomerPage(@RequestParam(required = false) String staffId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        request.setAttribute("staffId", staffId);
        Map fpiMap = staffMyService.getCustomerData(staff.get_id( ));
        request.setAttribute("userlist", fpiMap.get("staffUserlist"));
        request.setAttribute("staffUserlist", fpiMap.get("staffUserlist"));
        request.setAttribute("staffFocuslist", fpiMap.get("staffFocuslist"));
        request.setAttribute("ordertotal", fpiMap.get("ordertotal"));
        request.setAttribute("focustotal", fpiMap.get("focustotal"));
        //差会员
        return new ModelAndView("staff/myCustomer/myCustomer");
    }

    //跳转我的客户页面
    @RequestMapping("/toMyCustomer")
    public ModelAndView toMyCustomer(@RequestParam(required = true) String staffId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        Map<String, Integer> map = staffMyService.getCustomerCount(staff.get_id( ));
        request.setAttribute("staffId", staffId);
        request.setAttribute("map", map);
        return new ModelAndView("staff/myCustomer/myCustomer");
    }

    //关注客户
    @RequestMapping("/myCustomer_follow")
    @ResponseBody
    public FlipInfo<User> myCustomer(@RequestParam(required = false) String staffId, String type, String page, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipInfo<User> fpi = new FlipPageInfo<User>(request);
        fpi.getParams( ).remove("staffId");
        fpi.getParams( ).remove("type");
        FlipInfo<User> users = staffMyService.getCustomers(staff.get_id( ), type, fpi);
        return users;
    }

    //预约客户
    @RequestMapping("/myCustomer_appoint")
    @ResponseBody
    public FlipInfo<User> myCustomerAppoint(@RequestParam(required = false) String staffId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipInfo<User> fpi = new FlipPageInfo<User>(request);
        fpi.getParams( ).remove("staffId");
        fpi.getParams( ).remove("type");
        FlipInfo<User> users = staffMyService.getCustomersAppoint(staff.get_id( ), type, fpi);
        return users;
    }

    //会员客户
    @RequestMapping("/myCustomer_member")
    @ResponseBody
    public FlipInfo<User> myCustomerMember(@RequestParam(required = false) String staffId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipInfo<User> fpi = new FlipPageInfo<User>(request);
        fpi.getParams( ).remove("staffId");
        fpi.getParams( ).remove("type");
        FlipInfo<User> users = staffMyService.getCustomersMember(staff.get_id( ), type, fpi);
        return users;
    }

    //会员客户消费详情
    @RequestMapping("/customerDetail")
    public ModelAndView customerDetail(@RequestParam(required = false) String userId, String staffId, String type, String organId, HttpServletRequest request) throws Exception {
        User user = staffMyService.getMemberDetail(userId);
        request.setAttribute("user", user);
        request.setAttribute("userId", userId);
        request.setAttribute("staffId", staffId);
        request.setAttribute("organId", organId);
        request.setAttribute("type", type);
        return new ModelAndView("staff/myCustomer/customerDetail");
    }

    //会员客户消费详情(订单查看)
    @RequestMapping("/customerDetail2")
    public ModelAndView customerDetail2(@RequestParam(required = false) String userId, String staffId, String page, String size, HttpServletRequest request) throws Exception {
        request.setAttribute("userId", userId);
        User user = staffMyService.getMemberDetail(userId);
        request.setAttribute("user", user);
        request.setAttribute("staffId", staffId);
        return new ModelAndView("staff/myCustomer/customerDetail2");
    }

    //会员消费记录
    @RequestMapping("/customList")
    @ResponseBody
    public FlipInfo<WeOrganOrder> customList(@RequestParam(required = false) String userId, HttpServletRequest request) throws Exception {
        FlipPageInfo<WeOrganOrder> fip = new FlipPageInfo<WeOrganOrder>(request);
        fip.getParams( ).remove("userId");
        fip.setSortField("orderTime");
        fip.setSortOrder("desc");
        FlipInfo<WeOrganOrder> weOrganOrder = staffMyService.customList(userId, fip);
        return weOrganOrder;
    }

    //跳转我的订单
    @RequestMapping("/myOrder")
    public ModelAndView myOrder(@RequestParam(required = false) String staffId, HttpServletRequest request) throws Exception {

        request.setAttribute("staffId", staffId);
        return new ModelAndView("staff/myOrder/orderList");
    }

    //我的订单列表
    @RequestMapping("/myOrderList")
    @ResponseBody
    public FlipInfo<WeOrganOrder> myOrderList(@RequestParam(required = false) String staffId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipPageInfo<WeOrganOrder> flp = new FlipPageInfo<WeOrganOrder>(request);
        flp.getParams( ).remove("staffId");
        flp.getParams( ).remove("type");
        flp.setSortField("createTime");
        flp.setSortOrder("desc");
        FlipInfo<WeOrganOrder> orders = staffMyService.getOrders(staff.get_id( ), type, flp);
        return orders;
    }

    //订单详情
    @RequestMapping("/orderDetail")
    public ModelAndView orderDetail(@RequestParam(required = false) String staffId, String orderId, HttpServletRequest request) throws Exception {
        WeOrganOrder order = staffMyService.getOrderDetail(orderId);
        request.setAttribute("staffId", staffId);
        request.setAttribute("order", order);
        return new ModelAndView("staff/myOrder/orderDetail");
    }

    //TODO 消息订单处理
    @RequestMapping("/messageToOrderDetail")
    public ModelAndView messageToOrderDetail(@RequestParam(required = false) String staffId, String orderId, HttpServletRequest request) throws Exception {
        WeOrganOrder order = staffMyService.getOrderDetail(orderId);
        request.setAttribute("staffId", staffId);
        request.setAttribute("order", order);
        return new ModelAndView("staff/myMessageOrder/orderDetail");
    }

    @RequestMapping("/messageToOrderDetailByComfirt")
    public ModelAndView messageToOrderDetailByComfirt(@RequestParam(required = false) String staffId, String orderId, HttpServletRequest request) throws Exception {
        WeOrganOrder order = staffMyService.getOrderDetail(orderId);
        request.setAttribute("staffId", staffId);
        request.setAttribute("order", order);
        return new ModelAndView("staff/myMessageOrder/orderDetail");
    }

    //跳转拒接订单
    @RequestMapping("/toRefuseOrder")
    public ModelAndView toRefuseOrder(@RequestParam(required = false) String staffId, String orderId, HttpServletRequest request) throws Exception {
        request.setAttribute("staffId", staffId);
        request.setAttribute("orderId", orderId);
        return new ModelAndView("staff/myOrder/deny");
    }

    //跳转拒接订单
    @RequestMapping("/toRefuseMessageOrder")
    public ModelAndView toRefuseMessageOrder(@RequestParam(required = false) String staffId, String orderId, HttpServletRequest request) throws Exception {
        request.setAttribute("staffId", staffId);
        request.setAttribute("orderId", orderId);
        return new ModelAndView("staff/myMessageOrder/deny");
    }

    //拒接订单保存
    @RequestMapping("/refuseOrderSave")
    @ResponseBody
    public ReturnStatus refuseOrderSave(@RequestParam(required = false) String orderId, String staffId, String refuseComment, HttpServletRequest request) throws Exception {
        WeOrganOrder order = staffMyService.getOrderDetail(orderId);
        ReturnStatus status = staffMyService.refuseOrderSave(orderId, refuseComment);
        if (status.isSuccess( )) {
            //通知客户
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date( ));
            Account account = staffMyService.getAccountById(order.getUserId( ), "user");
            WxTemplate tempToUser = redisService.getWxTemplate("喵 用户您好，商家拒绝了您的订单！(⊙︿⊙)", status.getMessage( ), refuseComment, time, null, null, "您可以尝试预约其它时间");
            redisService.send_template_message(account.getAccountName( ), "user", Configure.STAFF_REFUSE_ORDER, tempToUser);
            return status;
        } else {
            return status;
        }
    }

    //确认订单
    @RequestMapping("/confirmOrder")
    @ResponseBody
    public ReturnStatus confirmOrder(@RequestParam(required = false) String orderId, String staffId, HttpServletRequest request) throws Exception {
        ReturnStatus status2 = new ReturnStatus(true);
        WeOrganOrder order = staffMyService.getOrderDetail(orderId);
        if (order != null) {
            Organ organ = userMyService.getOrganById(order.getOrganId( ));
            ReturnStatus status = staffMyService.confirmOrder(orderId);
            if (status.isSuccess( )) {
                Account account = staffMyService.getAccountById(order.getUserId( ), "user");
                WxTemplate tempToUser = redisService.getWxTemplate("喵，您的预约订单已成功确认啦！", order.get_id( ), organ.getName( ), order.getTitle( ), order.getOrderTime( ), null, "请您按时到店享受小喵的贴心服务，不要忘记呦！");
                redisService.send_template_message(account.getAccountName( ), "user", Configure.PAY_SUCCESS, tempToUser);
                return status;
            } else {
                return status;
            }
        } else {
            status2.setSuccess(false);
            status2.setMessage("订单不存在");
            return status2;
        }
    }
    /**
     * 去支付或预约页面
     * @param request
     * @param bean
     * @return
     */
    @RequestMapping("/toRentPay")
    public ModelAndView toRentPay(HttpServletRequest request, FormPositionBean bean){
        ModelAndView mv = new ModelAndView();
        if(bean.getTimeList().size()>0){//对前台集合进行非空处理
            List<String> newStr = new ArrayList<>();
            for(String str : bean.getTimeList()){
                if(!StringUtils.isEmpty(str)){
                    newStr.add(str);
                }
            }
            bean.setTimeList(newStr);
        }
        //时间list排序
        List<String> timeList = bean.getTimeList();
        Collections.sort(timeList);
        //验证所选日期是否还有工位

        String timeString = com.osg.framework.util.StringUtils.timeListToString(timeList);//前台时间段
       /* logger.info("得到前台选择时间区间------开始");
        for(String time:timeList){
            logger.info(time);
        }
        logger.info("得到前台选择时间区间------结束");
        logger.info("得到前台选择时间点"+timeString);*/
        OrganPositionSetting organPositionSetting = organPosition.queryPosition(bean.getOrganId());
        Staff staff = organPosition.queryStaff(bean.getStaffId());
        Organ organ = organPosition.queryOrgan(bean.getOrganId());

        PositionOrder positionOrder = new PositionOrder();
        positionOrder.setOrganName(organ.getName());
        positionOrder.setOrganId(organ.get_id());
        positionOrder.setStaffId(staff.get_id());
        positionOrder.setStaffName(staff.getName());
        positionOrder.setTimeString(timeString);
        positionOrder.setLeaseType(organPositionSetting.getLeaseType());//工位类型
        positionOrder.setCreateTime(new Date());
        positionOrder.setTotalDay(timeList.size());
        positionOrder.setState(0);
        if(organPositionSetting.getLeaseType()==0){
            double leaseMoney = organPositionSetting.getLeaseMoney();
            positionOrder.setLeaseMoney(leaseMoney);//单日租金
            positionOrder.setTotalMoney(leaseMoney*timeList.size());//订单总租金
            positionOrder.setWxMoney(0.0);
            positionOrder.setMyMoney(0.0);
        }
        //得到预约开始时间
        positionOrder.setBeginTime(DateUtil.formatDate(timeList.get(0)));
        //得到预约结束时间
        positionOrder.setEndTime(DateUtil.formatDate(timeList.get(timeList.size()-1)));
        Map<String, Object> sign = null;
        if (StringUtils.isEmpty(request.getQueryString())) {
            sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(), "staff");
        } else {
            sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "staff");
        }
        mv.addObject("sign",sign);
        mv.addObject("bean",bean);
        request.setAttribute("positionOrder",positionOrder);
        mv.addObject("staffMoney",staff.getTotalIncome());
        mv.setViewName("staff/myPosition/toPay");//去结算页面
        return mv;
    }


    //跳转我的收益
    @RequestMapping("/myEarn")
    public ModelAndView myEarn(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        String flag = request.getParameter("flag");
        Staff staff2 = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staff2.get_id( ));
        request.setAttribute("staff", staff);
        request.setAttribute("flag",flag);
        return new ModelAndView("staff/myEarning/myEarning");
    }

    //我的设置
    @RequestMapping("/setup")
    public ModelAndView setup(HttpServletRequest request) throws Exception {
        return new ModelAndView("staff/mine/setup");
    }

    //信息反馈
    @RequestMapping("/myFeedBack")
    public ModelAndView myFeedBack(@RequestParam(required = false) String staffId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff2 = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staff2.get_id( ));
        request.setAttribute("staffId", staffId);
        request.setAttribute("staff", staff);
        return new ModelAndView("staff/mine/my_feedback");
    }

    //保存信息反馈
    @RequestMapping("/saveFeedBack")
    public ModelAndView saveFeedBack(@RequestParam(required = false) String staffId, String fbcontent, String contact, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        ReturnStatus status = staffMyService.saveFeedBack(staff.get_id( ), fbcontent, contact);
        if (status.isSuccess( )) {
            return setup(request);
        }
        return myFeedBack(staffId, request);
    }

    //收益列表
    @RequestMapping("/earnList")
    @ResponseBody
    public FlipInfo<WeStaffIncome> earnList(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipInfo<WeStaffIncome> income = staffMyService.getIncomeList(staff.get_id( ));
        return income;
    }

    //收益列表
    @RequestMapping("/staffPercentage")
    @ResponseBody
    public FlipInfo<Userpart> staffPercentage(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        Staff staff = (Staff) session.getAttribute("staff");
        FlipInfo<Userpart> income = staffMyService.getPercentageList(staff.get_id( ));
        return income;
    }


    //跳转提现
    @RequestMapping("/withdraw")
    public ModelAndView withdraw(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView( );
        HttpSession session = request.getSession( );
        Staff staff2 = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staff2.get_id( ));
        if (! StringUtils.isEmpty(staff.getPayPassword( ))) {
            request.setAttribute("staff", staff);
            mv.setViewName("staff/myEarning/withdraw");
        } else {
            mv.setViewName("staff/myEarning/setPayPassword");
        }
        return mv;
    }

    @RequestMapping(value = "/setPayPassword", method = RequestMethod.POST)
    @ResponseBody
    public ReturnStatus setPayPassword(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession( );
        Staff staffSession = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staffSession.get_id( ));
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (! StringUtils.isEmpty(staff.getPhone( ))) { //已经设置过手机号
            if (phone.equals(staff.getPhone( ))) {
                if (accountService.verify(phone, code).isSuccess( )) {
                    //设置支付密码
                    if (password.equals(confirmPassword)) {
                        password = passwordEncoder.encodePassword(password, staff.get_id( ));
                        staffMyService.updatePassword(staff.get_id( ), password);
                        return new ReturnStatus(1);
                    } else {
                        return new ReturnStatus(4, "两次密码输入的不一致,请重新输入");
                    }
                } else {
                    //验证码验证失败
                    return new ReturnStatus(2, "验证码验证失败,请重新获取");
                }
            } else {
                //和绑定的不一致
                return new ReturnStatus(3, "你输入的手机号和绑定的不一致不能设置支付密码。");
            }
        } else {
            return new ReturnStatus(5, "程序内部错误，请反馈到任性猫平台！");
        }
    }

    //跳转我的收藏
    @RequestMapping("/myCollection")
    public ModelAndView myCollection(@RequestParam(required = false) String userId, HttpServletRequest request) throws Exception {

        request.setAttribute("userId", userId);
        return new ModelAndView("user/userhome/my_collection");
    }

    //案例收藏
    @RequestMapping("/collectionCaseList")
    @ResponseBody
    public FlipInfo<WeStaffCase> collectionCaseList(@RequestParam(required = false) String userId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        User user = (User) session.getAttribute("user");
        FlipPageInfo<WeStaffCase> flp = new FlipPageInfo<WeStaffCase>(request);
        flp.getParams( ).remove("userId");
        flp.getParams( ).remove("type");
        FlipInfo<WeStaffCase> weCase = staffMyService.getCaseList(user.get_id( ), flp);
        return weCase;
    }

    //技师收藏
    @RequestMapping("/collectionStaffList")
    @ResponseBody
    public FlipInfo<Staff> collectionStaffList(@RequestParam(required = false) String userId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        User user = (User) session.getAttribute("user");
        FlipPageInfo<Staff> flp = new FlipPageInfo<Staff>(request);
        flp.getParams( ).remove("userId");
        flp.getParams( ).remove("type");
        FlipInfo<Staff> staff = staffMyService.getStaffList(user.get_id( ), flp);
        return staff;
    }

    //店铺收藏
    @RequestMapping("/collectionOrganList")
    @ResponseBody
    public FlipInfo<Organ> collectionOrganList(@RequestParam(required = false) String userId, String type, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession( );
        User user = (User) session.getAttribute("user");
        FlipPageInfo<Organ> flp = new FlipPageInfo<Organ>(request);
        flp.getParams( ).remove("userId");
        flp.getParams( ).remove("type");
        double longitude = 0;
        double latitude = 0;
        Object lo = session.getAttribute("longitude");
        Object la = session.getAttribute("latitude");
        if (lo != null) {
            longitude = Double.parseDouble(lo.toString( ));
        }
        if (la != null) {
            latitude = Double.parseDouble(la.toString( ));
        }
        GpsPoint gps = PositionUtil.bd09_To_Gcj02(latitude, longitude);
        FlipInfo<Organ> organ = staffMyService.getOrganList(gps.getLongitude( ), gps.getLatitude( ), user.get_id( ), flp);
        return organ;
    }

    //收益兑换规则
    @RequestMapping("/withdrawRule")
    public ModelAndView withdrawRule(@RequestParam(required = false) String staffId, HttpServletRequest request) throws Exception {
        request.setAttribute("staffId", staffId);
        return new ModelAndView("staff/myEarning/withdraw_rule");
    }

    //跳转发红包页面
    @RequestMapping("/sendRedPacket")
    public ModelAndView sendRedPacket(/*@RequestParam(required=false)String scope,*/HttpServletRequest request, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("staff/myhongbao/redPacket");
        Staff staff = (Staff) session.getAttribute("staff");
        Map<String, Object> map = staffMyService.getRedCount(staff.get_id( ));
        String scope = request.getParameter("scope");
        String sendTime = request.getParameter("sendTime");
        String count = request.getParameter("count");
        String money = request.getParameter("money");
        String desc = request.getParameter("desc");
        mv.addObject("sendTime", sendTime);
        mv.addObject("count", count);
        mv.addObject("money", money);
        mv.addObject("desc", desc);
        if (StringUtils.isEmpty(scope)) {
            mv.addObject("scope", null);
        } else {
            mv.addObject("scope", scope);
        }
        request.setAttribute("map", map);
        Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI( ) + "?" + request.getQueryString( ), "staff");
        request.setAttribute("sign", sign);
        request.setAttribute("msg", "");
        return mv;
    }

    //跳转发红包范围
    @RequestMapping("/toScope")
    public ModelAndView toScope(HttpServletRequest request, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("staff/myhongbao/scope");
        String scope = request.getParameter("scope");
        String sendTime = request.getParameter("sendTime");
        String count = request.getParameter("count");
        String money = request.getParameter("money");
        String desc = request.getParameter("desc");
        mv.addObject("scope", scope);
        mv.addObject("sendTime", sendTime);
        mv.addObject("count", count);
        mv.addObject("money", money);
        mv.addObject("desc", desc);
        return mv;
    }

    //跳转红包所属店铺
    @RequestMapping("/selectOrgan")
    public ModelAndView selectOrgan(@RequestParam(required = false) String organId, HttpServletRequest request, HttpSession session) throws Exception {
        Staff staff = (Staff) session.getAttribute("staff");
        List<Organ> organList = staffMyService.getMyOrgans(staff.get_id( ));
        request.setAttribute("organList", organList);
        return new ModelAndView("staff/myhongbao/select_organ");
    }


    //跳转已发红包
    @RequestMapping("/haveSentRedPacketList")
    public ModelAndView haveSentRedPacketList(@RequestParam(required = false) HttpServletRequest request, HttpSession session) throws Exception {
        return new ModelAndView("staff/myhongbao/redPacketList");
    }

    //获取已发红包列表
    @RequestMapping("/redPacketList")
    @ResponseBody
    public FlipInfo<WeRed> redPacketList(@RequestParam(required = false) String page, HttpServletRequest request, HttpSession session) throws Exception {
        Staff staff = (Staff) session.getAttribute("staff");
        FlipPageInfo<WeRed> flp = new FlipPageInfo<WeRed>(request);
        flp.setSortField("createTime");
        flp.setSortOrder("desc");
        FlipInfo<WeRed> redPacketList = staffMyService.getRedPacketList(staff.get_id( ), flp);
        return redPacketList;
    }

    //跳转已发红包详情
    @RequestMapping("/redPacketDetail")
    public ModelAndView redPacketDetail(@RequestParam(required = false) String redId, HttpServletRequest request, HttpSession session) throws Exception {
        WeRed weRed = staffMyService.getRedPacketById(redId);
        request.setAttribute("weRed", weRed);
        return new ModelAndView("staff/myhongbao/redPacketDetail");
    }

    //获取已抢红包用户列表
    @RequestMapping("/getRedUserList")
    @ResponseBody
    public FlipInfo<WeRedRecord> getRedUserList(@RequestParam(required = false) String redId, String page, HttpServletRequest request, HttpSession session) throws Exception {
        FlipPageInfo<WeRedRecord> flp = new FlipPageInfo<WeRedRecord>(request);
        flp.getParams( ).remove("redId");
        flp.setSortField("createTime");
        flp.setSortOrder("desc");
        FlipInfo<WeRedRecord> redRecord = staffMyService.getRedUserList(redId, flp);
        return redRecord;
    }

    @RequestMapping("/toCashWeChat")
    @ResponseBody
    public int toCashWeChat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            Staff staffSession = (Staff) session.getAttribute("staff");
            Staff staff = staffMyService.getStaff(staffSession.get_id( ));
            String staffId = staff.get_id( );
            String money = request.getParameter("money");
            double moneyTemp = Double.parseDouble(money);
            if (staff.getTotalIncome( ) < moneyTemp) {
                return 3; // 你的余额不足了提现
            }

            int amount = 100 * Integer.parseInt(money);
            String password = request.getParameter("password");
            if (StringUtils.isEmpty(staffId)) {
                throw new BaseException("技师的Id为空！！");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BaseException("密码为空！！");
            }
            password = passwordEncoder.encodePassword(password, staffId);
            if (password.equals(staff.getPayPassword( ))) {
                //Account account = staffMyService.findOneAccount(staffId);
                Map<String, Object> staffInfo = (Map<String, Object>) session.getAttribute("staffInfo");
                String openId = staffInfo.get("openid").toString( );
                Account account = staffMyService.findOneAccount(staffId, openId);
                if (account == null) {
                    throw new BaseException("account里为空！！");
                }
                WeUserWalletHis weUserWalletHis = new WeUserWalletHis( );
                weUserWalletHis.setIdIfNew( );
                weUserWalletHis.setDesc("技师提现");
                weUserWalletHis.setAmount(Integer.parseInt(money) * - 1);
                weUserWalletHis.setUserId(staffId);
                weUserWalletHis.setCreateTime(new Date( ));
                weUserWalletHis.setOrderId("2");

                int senderType = 2;// 表示技师
                int status = 0;    //表示未完成
                int type = 1;    //表示提现
                boolean payStatus = userMyService.queryWePayLog(staffId, senderType, status, type);
                if (payStatus) {
                    return 5; //有未完成的支付
                }
                String time = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date( ));
                WePayLog wePayLog = new WePayLog( );
                wePayLog.setIdIfNew( );
                wePayLog.setUserId(staffId);
                wePayLog.setStatus(0);
                wePayLog.setSenderType(1);
                wePayLog.setMessage("喵,您于" + time + " 成功申请提现喽~" + money + "元");
                wePayLog.setType(1);
                wePayLog.setMoney(- moneyTemp);
                wePayLog.setCreateTimeIfNew( );
                userMyService.saveWePayLog(wePayLog);
                Map<String, String> returnResult = CashUtil.CashToWeChatByStaff(weUserWalletHis.get_id( ), account.getAccountName( ), amount, staff.getName( ) + "你已经成功提现 " + money + "元", request.getRemoteAddr( ));
                if (returnResult != null) {
                    if (! StringUtils.isEmpty(returnResult.get("err_code"))) {
                        return 4;
                    }
                }
                WePayLog wePayLogNew = userMyService.queryWePayLogById(wePayLog.get_id( ));
                wePayLogNew.setStatus(1);
                userMyService.saveWePayLog(wePayLogNew);

                userMyService.saveWeUserWalletHis(weUserWalletHis);
                staffMyService.updateStaffIncome(staffId, staff.getTotalIncome( ) - Double.parseDouble(money));
                WeStaffIncome weStaffIncome = new WeStaffIncome( );
                weStaffIncome.setCreateTime(new Date( ));
                weStaffIncome.setAmount(- moneyTemp);
                weStaffIncome.setTitle("技师提现");
                weStaffIncome.setStaffId(staffId);
                staffMyService.saveStaffIncome(weStaffIncome);
                //提现成功推送消息
                WxTemplate tempToStaff = redisService.getWxTemplate("喵,您于" + time + " 成功申请提现喽~", money + "元", "0元", money + "元", "任性猫网站提现", null, "喵小二提醒您：提现已成功，请您注意查收哦~~");
                redisService.send_template_message(account.getAccountName( ), "staff", Configure.WITHDRAW_STAFF, tempToStaff);
                return 1;
            } else {
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    //TODO 技师端设置支付密码
    //跳转验证手机号
    @RequestMapping("/toVerifyPhone")
    public ModelAndView toBindPhone(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView( );
        mv.addObject("backFlag", 1);
        mv.setViewName("staff/myEarning/setPayPassword");
        return mv;
    }

    //TODO 技师端修改手机号
    @RequestMapping(value = "/toVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public String toVerifyCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(phone)) {
            return "手机号不能为空！";
        }
        if (StringUtils.isEmpty(code)) {
            return "验证码不能为空！";
        }
        Staff staffSession = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staffSession.get_id( ));
        if (phone.equals(staff.getPhone( ))) {
            ReturnStatus status = accountService.verify(phone, code);
            if (status.isSuccess( )) {
                return "验证成功";
            } else {
                return "验证失败";
            }
        } else {
            return "和你之前绑定的手机号不一致，不能完成修改手机号！";
        }
    }

    //跳转设置密码
    @RequestMapping("/toSetSafe")
    public ModelAndView toSetSafe(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView( );
        mv.setViewName("staff/mine/set_pwd");
        return mv;
    }

    //第二次输入密码
    @RequestMapping("/toPwd2")
    public ModelAndView toPwd2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView mv = new ModelAndView( );
        Staff staff = (Staff) session.getAttribute("staff");
        String pwd = request.getParameter("pwd");
        pwd = passwordEncoder.encodePassword(pwd, staff.get_id( ));
        mv.addObject("pwd", pwd);
        mv.setViewName("staff/mine/set_pwd2");
        return mv;
    }

    //保存密码
    @RequestMapping("/toSetPassword")
    public void toSetHomeByPwd(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Staff staff = (Staff) session.getAttribute("staff");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        pwd2 = passwordEncoder.encodePassword(pwd2, staff.get_id( ));
        if (pwd1.equals(pwd2)) {
            staffMyService.updatePassword(staff.get_id( ), pwd2);
            response.getWriter( ).print("success");
        } else {
            response.getWriter( ).print("noConsist");
        }
    }

    //跳转输入提现密码
    @RequestMapping("/toInputPwd")
    public ModelAndView toInputPwd(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        ModelAndView mv = new ModelAndView( );
        String money = request.getParameter("money");
        mv.addObject("money", money);
        mv.setViewName("staff/myEarning/input_pwd");
        return mv;
    }

    /**
     * 判断是否设置过支付密码
     */
    //跳转输入提现密码
    @RequestMapping("/isPayPassword")
    @ResponseBody
    public boolean isPayPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        Staff staffSession = (Staff) session.getAttribute("staff");
        Staff staff = staffMyService.getStaff(staffSession.get_id( ));
        if (StringUtils.isEmpty(staff.getPayPassword( ))) {
            return true;
        } else {
            return false;
        }
    }
    //跳转我的工位租赁
    @RequestMapping("/toStation")
    public ModelAndView toStation(HttpServletRequest request,@RequestParam(required = false) String staffId) throws IOException {
        ModelAndView mv = new ModelAndView( );
        request.setAttribute("staffId",staffId);
        mv.setViewName("staff/mine/staffStation");
        return mv;
    }
    //我的工位租赁
    @RequestMapping("/myStationList")
    @ResponseBody
    public FlipInfo<PositionOrder> myStationList(@RequestParam(required=false)String staffId, HttpServletRequest request)throws Exception{
        FlipPageInfo<PositionOrder> flp=new FlipPageInfo<PositionOrder>(request);
        flp.getParams().remove("staffId");
        flp.getParams().remove("type");
        flp.setSortField("createTime");
        flp.setSortOrder("desc");
        FlipInfo<PositionOrder> stations=staffMyService.getStationList(staffId,flp);
        return stations;
    }
    //跳转租赁详情
    @RequestMapping("/myStationDetail")
    public ModelAndView myStationDetail(HttpServletRequest request,@RequestParam(required = false) String positionOrderId) throws IOException {
        ModelAndView mv = new ModelAndView( );
//        request.setAttribute("organId",organId);
//        request.setAttribute("staffId",staffId);
        request.setAttribute("positionOrderId",positionOrderId);
        PositionOrder stationDetail=staffMyService.getStationDetails(positionOrderId);
        mv.addObject("stationDetail",stationDetail);
        mv.setViewName("staff/mine/myStationDetail");
        return mv;
    }
    //工位租赁详情
    @RequestMapping("/toStationList")
    @ResponseBody
    public TreeMap<String, Integer> toStationList(@RequestParam(required=false)String positionOrderId, HttpServletRequest request)throws Exception{
        TreeMap<String, Integer> map=new TreeMap<String, Integer>();
        FlipPageInfo<OrganPositionDetails> flp=new FlipPageInfo<OrganPositionDetails>(request);
        flp.getParams().remove("positionOrderId");
        flp.getParams().remove("type");
        flp.setSortOrder("desc");
        TreeMap<String, Integer> stations=staffMyService.queryStationList(positionOrderId,flp);

        return stations;
    }
    //我的收益
    @RequestMapping("/toMyProfit")
    public ModelAndView toMyProfit(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String staffId = request.getParameter("staffId");
        Staff staff = staffMyService.getStaff(staffId);
        mv.addObject("staff",staff);
        mv.setViewName("staff/myEarning/myProfit");
        return mv;
    }
    //查询我的收入
    @RequestMapping("/queryMyProfit")
    @ResponseBody
    public FlipInfo queryMyProfit(HttpServletRequest request,String staffId){
        FlipInfo fpi=new FlipPageInfo<>(request);
        List dateList = new ArrayList();//最终回调的数据属性对象
        List<String> orderIdList = new ArrayList<>();//店铺订单id
        List<String> payIdList = new ArrayList<>();//支付订单id
        List<StaffProfit> staffProfits = new ArrayList<>();//页面展示对象
        TreeMap timeMap = new TreeMap(Collections.reverseOrder());//回传list下map对应的时间
        Map<String,WeUserPayOrder> payOrderMap = new TreeMap<String,WeUserPayOrder>();//支付表ID对象
        Map<String,WeUserPayFenzhang> fenZhangMap = new TreeMap<String,WeUserPayFenzhang>();//分账表对象
        String staffName = request.getParameter("name");
        String appDate = request.getParameter("appDate");
        fpi.getParams().remove("appDate");
        fpi.getParams().remove("staffId");
        fpi.getParams().remove("type");
        fpi.getParams().remove("pages");
        fpi.getParams().remove("name");
        //找到技师id的相关订单，时间排序
        fpi = staffMyService.queryStaffProfit(fpi,staffId,appDate);
        List<WeOrganOrder> orderList = fpi.getData();

       for(WeOrganOrder organOrder:orderList){
           String payDate = organOrder.getPayTime();
           if(StringUtils.isEmpty(payDate)){
               payDate = "1900-01-01 00:00";
               organOrder.setPayTime(payDate);
           }
           String time = DateUtil.formatString(payDate);
           timeMap.put(time,time);
           orderIdList.add(organOrder.get_id());
       }

        //店铺订单id查 支付订单
        List<WeUserPayOrder> payOrderList = staffMyService.queryPayOrder(orderIdList);
        for(WeUserPayOrder payOrder:payOrderList){
            payIdList.add(payOrder.get_id());
            payOrderMap.put(payOrder.getOrganOrderId(),payOrder);
        }

        //支付订单id 查 分账表
        List<WeUserPayFenzhang> userPayFenzhangs = staffMyService.queryFenzhang(payIdList);
        for(WeUserPayFenzhang payFenzhang:userPayFenzhangs){
            fenZhangMap.put(payFenzhang.getOrderId(),payFenzhang);
        }

        //完成页面展示对象的存放
        for(WeOrganOrder organOrder :orderList){
            String organOrderId = organOrder.get_id();
            WeUserPayOrder payOrder = payOrderMap.get(organOrderId);
            WeUserPayFenzhang fenzhang = fenZhangMap.get(payOrder.get_id());
            String serviveName = organOrder.getTitle();
            String payTime = organOrder.getPayTime();
            double staffAmount = fenzhang.getStaffAmount();
            StaffProfit staffProfit = new StaffProfit(serviveName,staffName,payTime,staffAmount);
            staffProfits.add(staffProfit);
        }
        Iterator timeIt = timeMap.keySet().iterator();
        while (timeIt.hasNext()){
            Map map = new HashMap();
            String time = (String) timeIt.next();
            String monthDay = DateUtil.getMonthDay(time,1);
            map.put("time",monthDay);
            double  totalMoney = 0;
            List<StaffProfit> mapList = new ArrayList<>();
            for(StaffProfit staffProfit:staffProfits){
                String payTimee = DateUtil.formatString(staffProfit.getTime());
                if(payTimee.equals(time)){//如果是同一天，就放当前map的list下
                    //String orderTimme = DateUtil.getMonthDay(orderTime,0);
                    mapList.add(staffProfit);
                    //mapList.get(mapList.size()-1).setTime(orderTimme);
                    totalMoney+=staffProfit.getProfit();
                }
            }
            map.put("totalMoney",totalMoney);
            map.put("mapList",mapList);
            if(!timeIt.hasNext()){
                //最后一条时间 查对应的一天所有订单，聚合钱
                List<String> lastOrderIdList = new ArrayList<String>();
                List<String> lastPayIdList = new ArrayList<String>();
                try {
                    //查询最后一天的所有订单
                    List<WeOrganOrder> moneyOrder = staffMyService.queryWeOrganOrderOfOneDay(staffId,time);
                    for(WeOrganOrder organOrder:moneyOrder){
                        lastOrderIdList.add(organOrder.get_id());
                    }
                    //得到用户支付订单
                    List<WeUserPayOrder> moneyPayOrderList = staffMyService.queryPayOrder(lastOrderIdList);
                    for(WeUserPayOrder payOrder:moneyPayOrderList){
                        lastPayIdList.add(payOrder.get_id());
                    }
                    //
                    List<WeUserPayFenzhang> fenZhangMoney = staffMyService.queryFenzhang(lastPayIdList);
                    double lastMoney = 0;
                    for(WeUserPayFenzhang payFenzhang:fenZhangMoney){
                        lastMoney+=payFenzhang.getStaffAmount();
                    }
                    map.put("totalMoney",lastMoney);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            dateList.add(map);
        }
        fpi.setData(dateList);
        return  fpi;
    }
}