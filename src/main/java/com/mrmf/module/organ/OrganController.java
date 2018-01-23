package com.mrmf.module.organ;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.*;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.wxpay.CommonUtil;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.CashUtil;
import com.mrmf.service.common.Configure;
import com.mrmf.service.common.WxgetInfo;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.weuser.WeUserService;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.PositionUtil;
import com.osg.framework.util.QRCodeUtil;
import com.osg.framework.web.cache.CacheManager;
import com.osg.framework.util.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * 店铺管理相关
 */
@Controller
@RequestMapping("/organ")
public class OrganController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WeComonService weCommonService;
    @Autowired
    private WeOrganService weOrganService;
    @Autowired
    private WxgetInfo wxgetInfo;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WeUserService weUserService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserMyService userMyService;
    @Autowired
    private CacheManager cacheManager;


    Logger logger = Logger.getLogger("CashUtil");

    /**
     * 校验是否第一次关注如果第一次关注跳转到绑定手机号页面否则到店铺首页面
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toCheck")
    public ModelAndView toQuery(@RequestParam(required = false) String code, String state, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String organstate = "";
        HttpSession session = request.getSession(true);//
        Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("organInfo");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!organInfo+"+userInfo);
        if (userInfo == null) {
            Map<String, Object> token = wxgetInfo.getAccess_token(code, "organ");
            userInfo = wxgetInfo.getUserInfo(token);
            ReturnStatus status = null;
            session.setAttribute("organInfo", userInfo);
            if (userInfo != null) {
                String oppenid = "";
                String unionid = "";
                if (userInfo.get("openid") != null) {
                    oppenid = userInfo.get("openid").toString();
                }
                if (userInfo.get("unionid") != null) {
                    unionid = userInfo.get("unionid").toString();
                }
                status = weCommonService.isExist(oppenid, unionid, "organ");
            }
            if (status.isSuccess()) {//该微信号存在account中
                Organ organ = weOrganService.queryOrgan(userInfo.get("openid").toString());
                request.setAttribute("organ", organ);
                session.setAttribute("organ", organ);
                session.setAttribute("city", "北京市");
                session.setAttribute("cityId", "1667920738524089172");
                mv.setViewName("organ/store/index");
            } else {
                organstate = "nophone";
                session.setAttribute("organstate", organstate);
                mv.setViewName("organ/organlogin/binderPhone");
            }
        }else{
            Organ organ = weOrganService.queryOrgan(userInfo.get("openid").toString());
            if(organ == null){
                mv.setViewName("organ/organlogin/binderPhone");
                Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "organ");
                mv.addObject("sign", sign);
                return mv;
            }else {
                request.setAttribute("organ", organ);
                session.setAttribute("organ", organ);
                mv.setViewName("organ/store/index");
            }
        }
        Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "organ");
        mv.addObject("sign", sign);
        //判断是否有未读消息
        Organ organ = weOrganService.queryOrgan(userInfo.get("openid").toString());
        if (organ != null) {
            List<WeMessage> messages = weOrganService.existMessageNoRead(organ.get_id());
            if (messages != null && messages.size() > 0) {
                request.setAttribute("message", true);
            }
        }
        organstate = (String) session.getAttribute("organstate");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+organstate);
        return mv;
    }

    /**
     * 跳转到修改状态页面
     *
     * @param organId
     * @param organState
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toChangeState")
    public ModelAndView toChangeState(@RequestParam(required = false) String organId, String organState, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("organState", organState);
        mv.setViewName("organ/store/fm");
        return mv;
    }

    /**
     * 修改店铺状态
     *
     * @param organId
     * @param organState
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/changeState")
    public ModelAndView changeState(@RequestParam(required = false) String organId, String organState, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("organState", organState);
        Organ organ = weOrganService.queryOrganById(organId);
        organ.setState(Integer.parseInt(organState));
        weOrganService.updateOrgan(organ);
        request.setAttribute("organ", organ);
        mv.setViewName("organ/store/index");
        return mv;
    }

    @RequestMapping("/schedule")
    public ModelAndView schedule(@RequestParam(required = false) String organId, String organState, Integer day, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        //request.setAttribute("organId", organId);
        List<WeekDay> weekDays = weCommonService.queryScheduleTitle();
        if (day == null) {
            day = weekDays.get(0).getDaytime();
        }
        WeOrganCalendar weOrganCalendar = weOrganService.queryWeOrganCalendarByOrganId(organId, day);
        request.setAttribute("weekDays", weekDays);
        request.setAttribute("weOrganCalendar", weOrganCalendar);
        request.setAttribute("organId", organId);
        request.setAttribute("day", day);

        mv.setViewName("organ/store/schedule");
        return mv;
    }

    @RequestMapping("/saveSchedule")
    @ResponseBody
    public String saveSchedule(@RequestParam(required = false) String organId, Integer day, Integer index, boolean selected, HttpServletRequest request) throws Exception {
        ReturnStatus status = weOrganService.updateWeOrganCalendarByOrganId(organId, day, index, selected);
        if (status.isSuccess()) {
            return "true";
        }
        return "false";
    }

    @RequestMapping("/toIndex")
    public ModelAndView toIndex(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("organ/store/index");
        Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "organ");
        mv.addObject("sign", sign);
        //判断是否有未读消息
        List<WeMessage> messages = weOrganService.existMessageNoRead(organId);
        if (messages != null && messages.size() > 0) {
            request.setAttribute("message", true);
        }
        return mv;
    }

    @RequestMapping("/toBusytime")
    public ModelAndView toBusytime(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("organ/store/busytime");
        return mv;
    }


    @RequestMapping("/saveBusytime")
    @ResponseBody
    public String saveBusytime(@RequestParam(required = false) String organId, String busyTimeStart, String busyTimeEnd, HttpServletRequest request) throws Exception {
        Organ organ = weOrganService.queryOrganById(organId);
        organ.setBusyTimeStart(busyTimeStart);
        organ.setBusyTimeEnd(busyTimeEnd);
        ReturnStatus status = weOrganService.updateOrgan(organ);
        if (status.isSuccess()) {
            return "true";
        }
        return "false";
    }

    /**
     * 会员的消费详情还没有实现
     *
     * @param organId
     * @param userId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toxfdetail")
    public ModelAndView toxfdetail(@RequestParam(required = false) String organId, String userId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        Organ organ = weOrganService.queryOrganById(organId);

        request.setAttribute("organ", organ);
        mv.setViewName("organ/store/toxfdetail");
        return mv;
    }

    @RequestMapping("/toOrganList")
    public ModelAndView toOrganList(@RequestParam(required = false) String userId, String cityId, String city, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(true);
        request.setAttribute("userId", userId);
        request.setAttribute("cityId", cityId);
        request.setAttribute("city", city);
        List<Code> codeList = weCommonService.queryTypeList("organType");
        request.setAttribute("codeList", codeList);
        mv.setViewName("user/userstore/store_list");
        Map<String, List> map = weOrganService.queryDistrictList(cityId, "");
        request.setAttribute("map", map);
        //如果用户地理位置为空则添加用户上一次登陆的位置
        //User user=(User)session.getAttribute("user");
        User user = weUserService.queryUserById(userId);
        Object lo = session.getAttribute("longitude");
        Object la = session.getAttribute("latitude");
        if (lo == null) {
            session.setAttribute("longitude", user.getGpsPoint().getLongitude());
            //longitude1=Double.parseDouble(longitude);
            //session.setAttribute("longitude", longitude1);
        }
        if (la == null) {
            session.setAttribute("latitude", user.getGpsPoint().getLatitude());
            //latitude1=Double.parseDouble(latitude);
            //session.setAttribute("latitude", latitude1);
        }


        return mv;
    }

    @RequestMapping("/organList")
    @ResponseBody
    public FlipInfo<Organ> organList(String type, String city, String district, String region, double longitude, double latitude, String distance, double maxDistance, String followCount, int page, int pagesize, HttpServletRequest request) throws Exception {
        FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
//	type 店铺类型
//	city 城市
//	district 区域
//	region 商圈
//	longitude 经度
//	latitude 纬度
//	maxDistance 搜索半径
//	followCount 关注（排序条件如果不为空就按关注的倒叙排序）
        FlipInfo<Organ> organList = null;
        fpi.setSize(pagesize);
        //city="北京";
//	if("北京".equals(city)){
//		city=city+"市";
//	}
    /*double longitude1;
    double latitude1;
	longitude1=Double.parseDouble(longitude);
	latitude1=Double.parseDouble(latitude);*/
        GpsPoint gps = PositionUtil.bd09_To_Gcj02(latitude, longitude);
        fpi.getParams().remove("distance");
        fpi.getParams().remove("followCount");
        fpi.getParams().remove("maxDistance");
        fpi.getParams().remove("longitude");
        fpi.getParams().remove("latitude");
        fpi.getParams().remove("pagesize");
        if (!"".equals(type) && !"所有类型".equals(type)) {
            fpi.getParams().put("all:type", type);
        } else {
            fpi.getParams().remove("type");
        }
        if (!"".equals(city)) {
            fpi.getParams().remove("city");
            fpi.getParams().put("regex:city", city);
        } else {
            fpi.getParams().remove("city");
        }
        if (!"".equals(district)) {
            fpi.getParams().put("district", district);
        } else {
            fpi.getParams().remove("district");
        }
        if (!"".equals(region)) {
            fpi.getParams().put("region", region);
        } else {
            fpi.getParams().remove("region");
        }
        fpi.getParams().put("weixin|boolean", "true");
        fpi.getParams().put("valid|integer", "1");
        if (!"".equals(followCount)) {
            fpi.setSortField("followCount");
            fpi.setSortOrder("DESC");
            organList = weOrganService.queryOrganListByfollowCount(gps.getLongitude(), gps.getLatitude(), fpi);
        }
        if (!"".equals(distance)) {
            organList = weOrganService.queryOrganListByUser(gps.getLongitude(), gps.getLatitude(), maxDistance, fpi);
        }



        //FlipInfo<Organ> organList1 = weOrganService.queryOrganPosition(organList);
//        mongoTesemplate.find(Query.query(Criteria.where("organId").in(new ArrayList<String>())),Organ.class);
//        for(Organ org:organList.getData()){
//            //  met.findOne("organId").is(org.get_id()),organPosition;
//            //店面加几个不存数据库的属性
//            //org.setsXXX(orgp.get)
//        }
        return organList;
    }

    @RequestMapping("/toOrganDetail")
    public ModelAndView toOrganDetail(@RequestParam(required = false) String organId, String userId, String distance, String cityId, String city, String search, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(true);
        city = URLDecoder.decode(city, "utf-8");
        request.setAttribute("organId", organId);
        request.setAttribute("userId", userId);
        request.setAttribute("cityId", cityId);
        request.setAttribute("city", city);
        request.setAttribute("search", search);
        request.setAttribute("distance", distance);
        String type = request.getParameter("type");
        User user = weUserService.queryUserById(userId);
        if (user != null) {
            for (String oid : user.getFavorOrganIds()) {
                if (organId.equals(oid)) {
                    user.setFavorTheOrganId(true);
                }
            }
        }
        request.setAttribute("user1", user);
        Organ organ = weOrganService.queryOrganById(organId);
        List<String> imgs = new ArrayList<String>();
        imgs.add(organ.getLogo());
        if (organ.getImages() != null) {
            for (String img : organ.getImages()) {
                String[] preImg = img.split("\\|");
                imgs.add(preImg[0]);
            }
        }
        organ.setImages(imgs);
        request.setAttribute("organ", organ);
        mv.addObject("type", type);
        FlipInfo<Smallsort> fpi = new FlipInfo<Smallsort>();
        fpi.getParams().put("organId", organId);
        FlipInfo<Smallsort> smallsortfpi = weOrganService.queryOrganSmallsort(fpi);
        if (smallsortfpi != null && smallsortfpi.getData().size() > 0) {
            request.setAttribute("smallsort", true);
        } else {
            request.setAttribute("smallsort", false);
        }
        Map<String, Object> sign = null;
        if (StringUtils.isEmpty(request.getQueryString())) {
            sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(), "user");
        } else {
            sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "user");
        }
        mv.addObject("sign", sign);
        request.setAttribute("organId", organId);
        String userAppID = Configure.userAppID;
        String encode = URLEncoder.encode(Configure.DOMAIN_URL, "GBK");
        request.setAttribute("userAppID", userAppID);
        request.setAttribute("encode", encode);
        mv.setViewName("user/userstore/store_detail");
        return mv;
    }

    @RequestMapping("/favorTheOrganId")
    @ResponseBody
    public String favorTheOrganId(String organId, String state, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        ReturnStatus status = null;
        if (user != null) {
            status = weCommonService.saveUserFavor(organId, "3", user.get_id(), state);
        }
        if (status.isSuccess()) {
            return "true";
        }
        return "false";
    }


    @RequestMapping("/toOrganDetailMap")
    public ModelAndView toOrganDetailMap(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("user/userstore/appoint_store_map");
        return mv;
    }

    @RequestMapping("/organStaffList")
    @ResponseBody
    public FlipInfo<Staff> organStaffList(String organId, int page, HttpServletRequest request) throws Exception {
        FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
        FlipInfo<Staff> staffList = null;
        fpi.setSize(8);
        fpi.getParams().remove("organId");
        //fpi.getParams().put("weixin|boolean","true");
        staffList = weCommonService.queryStaffListByOrganId(organId, fpi);

        return staffList;
    }

    @RequestMapping("/toAppointInfo")
    public ModelAndView toAppointInfo(@RequestParam(required = false) String organId, String orderServiceId, String orderService, String select_time, String userId, String city, String cityId, String search, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("userId", userId);
        request.setAttribute("city", city);
        request.setAttribute("cityId", cityId);
        request.setAttribute("orderServiceId", orderServiceId);
        request.setAttribute("orderService", orderService);
        request.setAttribute("select_time", select_time);
        request.setAttribute("search", search);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        HttpSession session = request.getSession(true);
        User u = (User) session.getAttribute("user");
        User user = weUserService.queryUserById(u.get_id());
        String phone = user.getPhone();
        if (StringUtils.isEmpty(phone)) {
            mv.setViewName("user/userlogin/binderPhone");
        } else {
            mv.setViewName("user/userstore/appoint_info");
        }
        return mv;
    }

    @RequestMapping("/toAppointTime")
    public ModelAndView toAppointTime(@RequestParam(required = false) String organId, String userId, Integer day, String orderServiceId, String orderService, String city, String cityId, String select_time, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("userId", userId);
        request.setAttribute("orderServiceId", orderServiceId);
        request.setAttribute("orderService", orderService);
        request.setAttribute("city", city);
        request.setAttribute("cityId", cityId);
        request.setAttribute("select_time", select_time);
        List<WeekDay> weekDays = weCommonService.queryScheduleTitle();

        if (day == null) {
            day = weekDays.get(0).getDaytime();
        }
        WeOrganCalendar weOrganCalendar = weOrganService.queryWeOrganCalendarByOrganId(organId, day);
        request.setAttribute("weekDays", weekDays);
        request.setAttribute("weOrganCalendar", weOrganCalendar);
        request.setAttribute("organId", organId);
        request.setAttribute("day", day);
        mv.setViewName("user/userstore/appoint_time");
        return mv;
    }

    @RequestMapping("/toOrganType")
    public ModelAndView toOrganType(@RequestParam(required = false) String organId, String userId, Integer day, String orderServiceId, String select_time, String orderService, String city, String cityId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("userId", userId);
        request.setAttribute("city", city);
        request.setAttribute("cityId", cityId);
        request.setAttribute("orderServiceId", orderServiceId);
        request.setAttribute("orderService", orderService);
        request.setAttribute("select_time", select_time);
        List<Bigsort> typeList = weOrganService.queryOrganType(organId);
        request.setAttribute("typeList", typeList);
        mv.setViewName("user/userstore/select_type");
        return mv;
    }

    @RequestMapping("/saveAppointTime")
    public ModelAndView saveAppointTime(@RequestParam(required = false) String organId, String userId, String select_time, String orderServiceId, String orderService, String city, String cityId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("userId", userId);
        request.setAttribute("orderServiceId", orderServiceId);
        request.setAttribute("orderService", orderService);
        request.setAttribute("select_time", select_time);
        request.setAttribute("city", city);
        request.setAttribute("cityId", cityId);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("user/userstore/appoint_info");
        return mv;
    }

    @RequestMapping("/saveOrganType")
    public ModelAndView saveOrganType(@RequestParam(required = false) String organId, String userId, String select_time, String orderServiceId, String orderService, String city, String cityId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("userId", userId);
        request.setAttribute("orderServiceId", orderServiceId);
        request.setAttribute("orderService", orderService);
        request.setAttribute("select_time", select_time);
        request.setAttribute("city", city);
        request.setAttribute("cityId", cityId);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("user/userstore/appoint_info");
        return mv;
    }

    @RequestMapping("/saveAppointInfo")
    @ResponseBody
    public String saveAppointInfo(@RequestParam(required = false) String organId, String userId, String orderServiceId, String selectTime, HttpServletRequest request) throws Exception {
        WeOrganOrder weOrganOrder = new WeOrganOrder();
        Bigsort bigsort = weCommonService.getBigsortById(orderServiceId);
        weOrganOrder.setIdIfNew();
        weOrganOrder.setOrganId(organId);
        weOrganOrder.setUserId(userId);
        weOrganOrder.setType(1);
        weOrganOrder.setOrderService(orderServiceId);
        String str = "";
        str = selectTime.substring(0, 4);
        String time = "";
        time += str + "-";
        str = selectTime.substring(4, 6);
        time += str + "-";
        str = selectTime.substring(6, selectTime.length());
        time += str;
        weOrganOrder.setOrderTime(time);
        weOrganOrder.setServiveName(bigsort.getName());
        weOrganOrder.setState(2);
        weOrganOrder.setTitle(bigsort.getName());
        weOrganOrder.setNewCreate();
        ReturnStatus status = weCommonService.saveWeOrganOrder(weOrganOrder);
        if (status.isSuccess()) {
            //预约完店铺之后判断用户是否预约过店铺如果没有预约过店铺就将店铺插入用户表
            User u = weUserService.queryUserById(userId);
            boolean flag = false;
            for (String orderHisId : u.getOrderHisIds()) {
                if (organId.equals(orderHisId)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                u.getOrderHisIds().add(organId);
                weCommonService.saveUser(u);
            }
            //用户预约完店铺给店铺添加消息
            HttpSession session = request.getSession(true);
            WeMessage msg = new WeMessage();
            User user = (User) session.getAttribute("user");
            Map<String, Object> userInfo = (Map) session.getAttribute("userInfo");
            Organ organ = weOrganService.queryOrganById(organId);
            msg.setIdIfNew();
            msg.setFromType("user");
            msg.setFromId(user.get_id());
            msg.setFromName(user.getNick());
            msg.setToType("organ");
            msg.setToId(organ.get_id());
            msg.setToName(organ.getName());
            msg.setType("1");
            msg.setContent(user.getNick() + " 预约了您的店铺");
            msg.setReadFalg(false);
            String organTime = getTime(new Date(), "yyyy/MM/dd HH:mm");
            msg.setCreateTimeFormat(organTime);
            status = weCommonService.saveMessage(msg);
            //用户预约完信息给用户添加消息
            WeMessage userMsg = new WeMessage();
            userMsg.setIdIfNew();
            userMsg.setFromType("organ");
            userMsg.setFromId(organ.get_id());
            userMsg.setFromName(organ.getName());
            userMsg.setToType("user");
            userMsg.setToId(user.get_id());
            userMsg.setToName(user.getNick());
            userMsg.setType("1");
            userMsg.setContent("您的预约信息已成功发送给 " + organ.getName());
            userMsg.setReadFalg(false);
            String userTime = getTime(new Date(), "yyyy/MM/dd HH:mm");
            userMsg.setCreateTimeFormat(userTime);
            status = weCommonService.saveMessage(userMsg);

            //用户通知
            WxTemplate tempToUser = redisService.getWxTemplate("喵，您的预约订单已成功确认啦！", weOrganOrder.get_id(), organ.getName(), bigsort.getName(), time, null, "请您按时到店享受小喵的贴心服务，不要忘记呦！");
            Account account = accountService.getAccountByEntityID(user.get_id(), "user");
            redisService.send_template_message(account.getAccountName(), "user", Configure.PAY_SUCCESS, tempToUser);
            List<Account> accounts = accountService.getAccountsByEntityID(organ.get_id(), "organ");
            for (Account accountTemp : accounts) {
                //店铺通知
                WxTemplate tempToOrgan = redisService.getWxTemplate("喵，有新的客户预约，请及时查看呦！", user.getNick(), bigsort.getName(), time, null, null, "请根据用户预约的时间和项目合理的安排时间与技师。");
                redisService.send_template_message(accountTemp.getAccountName(), "organ", Configure.ORDERED_ORGAN_REMIND, tempToOrgan);
            }
            return "true";
        }
        return "false";
    }

    @RequestMapping("/toOrderList")
    public ModelAndView toOrderList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/order");

        return mv;
    }

    @RequestMapping("/organOrderList")
    @ResponseBody
    public FlipInfo<WeOrganOrder> organOrderList(String organId, int page, HttpServletRequest request) throws Exception {
        FlipInfo<WeOrganOrder> fpi = new FlipPageInfo<WeOrganOrder>(request);
        FlipInfo<WeOrganOrder> organOrderList = null;
        fpi.setSize(4);
        fpi.getParams().remove("organId");
        organOrderList = weCommonService.queryWeOrganOrderListById(1, organId, "3,10", fpi);

        return organOrderList;
    }

    @RequestMapping("/toOrderDetail")
    public ModelAndView toOrderDetail(@RequestParam(required = false) String orderId, String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("orderId", orderId);
        request.setAttribute("organId", organId);
        WeOrganOrder weOrganOrder = null;
        weOrganOrder = weCommonService.queryWeOrganOrderById(orderId);
        request.setAttribute("weOrganOrder", weOrganOrder);
        mv.setViewName("organ/store/orderdetail");
        return mv;
    }

    @RequestMapping("/toMessageList")
    public ModelAndView toMessageList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/myMessage");
        return mv;
    }

    @RequestMapping("/toStaffList")
    public ModelAndView toStaffList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/technician_list");
        return mv;
    }

    @RequestMapping("/toEarnList")
    public ModelAndView toEarnList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String organ = request.getParameter("organ");
        if(StringUtils.isEmpty(organ)){
            request.setAttribute("organId", organId);
        }else {
            request.setAttribute("organId", organ);
        }

        //获取当天店铺收益的总金额
        //double sumMoney=weOrganService.queryOrganEarnSumMoney(organId);
        Map<String, String> earn = weOrganService.organEarnInfo(organId);
        request.setAttribute("earn", earn);
        mv.setViewName("organ/store/myEarning");
        return mv;
    }
    @RequestMapping("/toSonList")
    public ModelAndView toSonList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
        fpi.getParams().remove("organId");
        fpi.getParams().remove("organState");
        fpi = weOrganService.queryOrganByParentId(fpi,organId);
        //前台没用分页
        request.setAttribute("list", fpi.getData());
        mv.setViewName("organ/store/sonEarn");
        return mv;
    }

    @RequestMapping("/organMessageList")
    @ResponseBody
    public FlipInfo<WeMessage> organMessageList(String organId, int page, String type, HttpServletRequest request) throws Exception {
        FlipInfo<WeMessage> fpi = new FlipPageInfo<WeMessage>(request);
        FlipInfo<WeMessage> organMessageList = null;
        fpi.setSize(6);
        fpi.getParams().put("toType", "organ");
        fpi.getParams().put("toId", organId);
        fpi.getParams().put("type", type);
        fpi.getParams().remove("organId");
        fpi.setSortField("createTimeFormat");
        fpi.setSortOrder("DESC");
        //将未读消息设置为已读
        organMessageList = weOrganService.queryOrganMessageList(fpi);
        weOrganService.readMessage(organId, type);
        return organMessageList;
    }

    @RequestMapping("/organEarnList")
    @ResponseBody
    public FlipInfo<Userpart> organEarnList(String organId, int page, HttpServletRequest request) throws Exception {
        FlipInfo<Userpart> fpi = new FlipPageInfo<Userpart>(request);
        FlipInfo<Userpart> organEarngeList = null;
        fpi.setSize(6);
        //fpi.getParams().put("toType","organ");
        //fpi.getParams().put("organId", organId);
        //fpi.getParams().put("type", type);
        //fpi.getParams().remove("organId");
        //fpi.setSortField("createTimeFormat");
        //fpi.setSortOrder("DESC");
        fpi.getParams().remove("organId");
        organEarngeList = weOrganService.queryOrganEarnList(organId, fpi);
        //organMessageList=weOrganService.queryOrganMessageList(fpi);
        return organEarngeList;
    }

    @RequestMapping("/toRatedList")
    public ModelAndView toRatedList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("organ/store/organrated");
        return mv;
    }

    @RequestMapping("/toUserRatedList")
    public ModelAndView toUserRatedList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("user/userstore/organrated");
        return mv;
    }

    @RequestMapping("/organRatedList")
    @ResponseBody
    public FlipInfo<WeOrganComment> organRatedList(String organId, int page, HttpServletRequest request) throws Exception {
        FlipInfo<WeOrganComment> fpi = new FlipPageInfo<WeOrganComment>(request);
        FlipInfo<WeOrganComment> organCommentList = null;
        fpi.setSize(6);
        fpi.getParams().remove("organId");
        fpi.setSortField("createTime");
        fpi.setSortOrder("DESC");
        organCommentList = weCommonService.queryWeCommentById(1, organId, fpi);
        return organCommentList;
    }

    @RequestMapping("/toCustomList")
    public ModelAndView toCustomList(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        Map<String, Integer> map = weOrganService.queryfavorOrganUserCount(organId);
        request.setAttribute("favorOrgan", map.get("favorOrgan"));
        request.setAttribute("vip", map.get("vip"));
        request.setAttribute("orderHisIds", map.get("orderHisIds"));
        mv.setViewName("organ/store/custom");
        return mv;
    }

    @RequestMapping("/toOrganSet")
    public ModelAndView toOrganSet(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/organ_set");
        return mv;
    }

    @RequestMapping("/toFeedBack")
    public ModelAndView toFeedBack(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        Organ organ = weOrganService.queryOrganById(organId);
        request.setAttribute("organ", organ);
        mv.setViewName("organ/store/my_feedback");
        return mv;
    }

    @RequestMapping("/toForUs")
    public ModelAndView toForUs(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/my_us");
        return mv;
    }
    //生成店铺二维码
    @RequestMapping("/toTwoCode")
    public ModelAndView toTwoCode(@RequestParam(required = false) String organId,HttpServletRequest request)throws Exception{
        String TOKEN = "";
        try {
            WeToken weToken = redisService.getTonkenInfo("user");
            TOKEN = weToken.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("t推荐人userID"+organId);
        JSONObject action_info = new JSONObject();
        JSONObject scene_id = new JSONObject();
        int time = weCommonService.getTime();
        String str = "19"+time;
        int rNum = Integer.parseInt(str);
        System.out.println(rNum);
        scene_id.put("scene_id", rNum);
        Organ organ = weOrganService.queryOrganById(organId);
        action_info.put("scene", scene_id);

       cacheManager.save(rNum+"",organ);

        System.out.println("推荐人redis 存储rum ="+rNum);
        System.out.println("redis 推荐人rnum+user"+organ);
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
        mv.setViewName("organ/store/twoCode");
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


    @RequestMapping("/toPayPhone")
    public ModelAndView toPayPhone(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/set_pay_pwd");
        return mv;
    }

    @RequestMapping("/toSetPwdOne")
    public ModelAndView toSetPwdOne(@RequestParam(required = false) String organId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        mv.setViewName("organ/store/set_pay_pwd1");
        return mv;
    }

    @RequestMapping("/toSetPwdTwo")
    public ModelAndView toSetPwdTwo(@RequestParam(required = false) String organId, String pwd, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        request.setAttribute("pwd", pwd);
        mv.setViewName("organ/store/set_pay_pwd2");
        return mv;
    }

    @RequestMapping("/savePwd")
    @ResponseBody
    public Map<String, String> savePwd(@RequestParam(required = false) String organId, String pwd1, String pwd2, HttpServletRequest request) throws Exception {
        Map<String, String> msg = new HashMap<String, String>();
        ReturnStatus status = null;
        request.setAttribute("organId", organId);
        if (!"".equals(pwd1) && !"".equals(pwd2) && pwd1.equals(pwd2)) {
            status = weOrganService.savePwd(pwd2, organId);
            if (status.isSuccess()) {
                msg.put("msg", "更换成功");
                msg.put("organId", organId);
            }
        } else {
            msg.put("msg", "密码不正确");
        }
        return msg;
    }

    @RequestMapping("/toverifycodepwd")
    @ResponseBody
    public Map<String, String> toverifycodepwd(String organId, String phone, String code, HttpServletRequest request) throws Exception {

        Map<String, String> msg = new HashMap<String, String>();
        ReturnStatus status = null;

        status = weOrganService.verify(phone, code, organId);
        //status=new ReturnStatus(true);
        if (status.isSuccess()) {
            msg.put("msg", "验证成功");
            msg.put("organId", organId);
        } else {
            msg.put("msg", status.getMessage());
        }
        return msg;
    }

    @RequestMapping("/saveFeedBack")
    @ResponseBody
    public String saveFeedBack(@RequestParam(required = false) String organId, String fbcontent, String amount, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        request.setAttribute("organId", organId);
        Organ organ = weOrganService.queryOrganById(organId);
        WeUserFeedback wfb = new WeUserFeedback();
        wfb.setIdIfNew();
        wfb.setUserId(organId);
        wfb.setDesc(fbcontent);
        wfb.setContact(amount);
        wfb.setType("organ");
        wfb.setUserName(organ.getName());
        wfb.setCreateTimeIfNew();
        ReturnStatus status = weCommonService.saveWeUserFeedback(wfb);
        if (status.isSuccess()) {
            //mv.setViewName("organ/store/organ_set");
            return "意见反馈成功";
        }
        return "意见反馈失败";
    }


    @RequestMapping("/organCustomList")
    @ResponseBody
    public FlipInfo<User> organCustomList(String organId, int page, int type, HttpServletRequest request) throws Exception {
        FlipInfo<User> fpi = new FlipPageInfo<User>(request);
        FlipInfo<User> organUserList = null;
        fpi.setSize(6);
        fpi.getParams().remove("organId");
        fpi.getParams().remove("type");
        organUserList = weOrganService.queryOrganUserList(type, organId, fpi);
        return organUserList;
    }

    @RequestMapping("/toOrganMapList")
    public ModelAndView toOrganMapList(@RequestParam(required = false) String userId, String cityId, String city, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(true);
        request.setAttribute("userId", userId);
        List<Code> codeList = weCommonService.queryTypeList("organType");
        request.setAttribute("codeList", codeList);
        Map<String, List> map = weOrganService.queryDistrictList(cityId, "");
        request.setAttribute("map", map);

        User user = (User) session.getAttribute("user");
        Object lo = session.getAttribute("longitude");
        Object la = session.getAttribute("latitude");
        if (lo == null) {
            session.setAttribute("longitude", user.getGpsPoint().getLongitude());
            //longitude1=Double.parseDouble(longitude);
            //session.setAttribute("longitude", longitude1);
        }
        if (la == null) {
            session.setAttribute("latitude", user.getGpsPoint().getLatitude());
            //latitude1=Double.parseDouble(latitude);
            //session.setAttribute("latitude", latitude1);
        }


        mv.setViewName("user/userstore/store_list_map");
        return mv;
    }

    @RequestMapping("/districtList")
    @ResponseBody
    public Map<String, List> districtList(String cityId, String districtId, HttpServletRequest request) throws Exception {
        Map<String, List> map = weOrganService.queryDistrictList(cityId, districtId);
        return map;
    }

    @RequestMapping("/getPhoneCode")
    @ResponseBody
    public String getPhoneCode(String phone, String type, HttpServletRequest request) throws Exception {
        ReturnStatus status = weCommonService.verifycode(phone, type);
        if (status.isSuccess()) {
            return "验证码已发送";
        }
        return status.getMessage();
    }
    /**
     * 通过手机号获得验证码	getCodeByPhone
     */
    @RequestMapping(value ="/getTest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  getCodeByPhone1(HttpServletRequest request, HttpServletResponse response) {
        //'phone':phone,'type':'user'

        Map<String, Object> map = new HashMap<String, Object>();

            map.put("code","0");
            map.put("message","测试123！");
            map.put("data","");

        return map;

    }
    @RequestMapping("/getUserLocation")
    @ResponseBody
    public String getUserLocation(String latitude, String longitude, String userId, String type, String city, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);
        session.setAttribute("city", city);
        WeBCity weCity = weCommonService.findCityIdByCity(city);
        if (weCity != null) {
            session.setAttribute("cityId", weCity.get_id());
        }
        double longitude1;
        double latitude1;
        if (StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
            return "false";
        }

        Map<String, String> map = weCommonService.wgs84ToBaiDu(longitude, latitude);

        Object lo = session.getAttribute("longitude");
        Object la = session.getAttribute("latitude");
        if (lo == null) {
            longitude1 = Double.parseDouble(map.get("longitude"));
            //longitude1=Double.parseDouble(longitude);
            session.setAttribute("longitude", longitude1);
        }
        if (la == null) {
            latitude1 = Double.parseDouble(map.get("latitude"));
            //latitude1=Double.parseDouble(latitude);
            session.setAttribute("latitude", latitude1);
        }
        weOrganService.saveLocation(userId, type, map.get("latitude"), map.get("longitude"));

        if ("staff".equals(type)) {
            session.setAttribute("staffLocation", true);
        } else if ("user".equals(type)) {
            session.setAttribute("userLocation", true);
        }
        //weOrganService.saveLocation(userId,type,longitude, latitude);
        return "true";
    }

    @RequestMapping("/toverifycode")
    @ResponseBody
    public Map<String, String> toverifycode(String phone, String type, String code, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);
        Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("organInfo");
        //Map<String,Object> userInfo=new HashMap<String,Object>();
        Map<String, String> msg = new HashMap<String, String>();
        ReturnStatus status = null;
        Organ organ = null;
        if (userInfo != null) {
            //Random ran=new Random();
            //String oppenid="oppenid_zlj"+ran.nextInt(100);
            //String unionid="unionid_zlj";
            String oppenid = "";
            String unionid = "";
            String logo = "";
            String nickname = "";
            GpsPoint gpsPoint = new GpsPoint();
            double longitude;
            double latitude;
            Object lo = session.getAttribute("longitude");
            Object la = session.getAttribute("latitude");
            if (lo != null) {
                longitude = Double.parseDouble(lo.toString());
                gpsPoint.setLongitude(longitude);
            }
            if (la != null) {
                latitude = Double.parseDouble(la.toString());
                gpsPoint.setLongitude(latitude);
            }
            if (userInfo.get("openid") != null) {
                oppenid = userInfo.get("openid").toString();
            }
            if (userInfo.get("unionid") != null) {
                unionid = userInfo.get("unionid").toString();
            }
            if (userInfo.get("headimgurl") != null) {
                logo = userInfo.get("headimgurl").toString();
            }
            if (userInfo.get("nickname") != null) {
                nickname = userInfo.get("nickname").toString();
            }
            status = weCommonService.verify(oppenid, unionid, phone, code, type, logo, nickname, gpsPoint);
            if (status.isSuccess()) {
                session.setAttribute("organstate", "");
                msg.put("msg", "绑定成功");
                organ = weOrganService.queryOrgan(oppenid);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!+修改绑定后的organ"+organ);
                session.setAttribute("organ",organ);
                request.setAttribute("organ",organ);
                msg.put("organId", organ.get_id());
            } else {
                msg.put("msg", status.getMessage());
            }
        }


        return msg;


    }

    //跳转价格管理
    @RequestMapping("/tariff")
    public ModelAndView tariff(String organId, HttpServletRequest request) throws Exception {
        request.setAttribute("organId", organId);
        return new ModelAndView("user/userstore/tariff");
    }

    //价目列表
    @RequestMapping("/tariffList")
    @ResponseBody
    public FlipInfo<Smallsort> tariffList(String page, String organId, HttpServletRequest request) throws Exception {
        FlipPageInfo<Smallsort> flp = new FlipPageInfo<Smallsort>(request);
        FlipInfo<Smallsort> tariffList = weOrganService.queryOrganSmallsort(flp);
        return tariffList;
    }

    @RequestMapping("/code")
    public ModelAndView organCode(String organId, HttpServletRequest request) throws Exception {
        request.setAttribute("organId", organId);
        Organ organ = weOrganService.queryOrganById(organId);
        String organAppID = Configure.organAppID;
        String encode = URLEncoder.encode(Configure.DOMAIN_URL, "GBK");
        request.setAttribute("organ", organ);
        request.setAttribute("organAppID", organAppID);
        request.setAttribute("encode", encode);
        return new ModelAndView("user/userstore/code");
    }

    @RequestMapping(value = "/qr/{organId}")
    @ResponseBody
    public ReturnStatus qrimage(@PathVariable String organId, HttpServletRequest req, HttpServletResponse res) {
        try {
            InputStream is = new FileInputStream(new File(req.getRealPath("icon_logo.png")));
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Configure.organAppID+"&redirect_uri=";
            url += URLEncoder.encode(Configure.DOMAIN_URL + "/mrmf/w/pay/wxSaoMaToPay.do", "GBK");
            url += "&response_type=code&scope=snsapi_userinfo&state=" + organId + "#wechat_redirect";
            // 老的地址 url = Configure.DOMAIN_URL + "/mrmf/w/pay/toPay?organId=" +
            // organId;
            QRCodeUtil.encodeQRCode(url, res.getOutputStream(), 2, is);
            is.close();
            return null;
        } catch (IOException e) {
            return new ReturnStatus(false, e.getMessage());
        }
    }

    /**
     * 跳转店铺搜索页面
     *
     * @param userId
     * @param cityId
     * @param city
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toSearchOrgan")
    public ModelAndView toSearchOrgan(@RequestParam(required = false) String userId, String cityId, String city, String search, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(true);
        request.setAttribute("userId", userId);
        request.setAttribute("cityId", cityId);
        request.setAttribute("city", city);
        request.setAttribute("search", search);

        //如果用户地理位置为空则添加用户上一次登陆的位置
        //User user=(User)session.getAttribute("user");
        User user = weUserService.queryUserById(userId);
        Object lo = session.getAttribute("longitude");
        Object la = session.getAttribute("latitude");
        if (lo == null) {
            session.setAttribute("longitude", user.getGpsPoint().getLongitude());
            //longitude1=Double.parseDouble(longitude);
            //session.setAttribute("longitude", longitude1);
        }
        if (la == null) {
            session.setAttribute("latitude", user.getGpsPoint().getLatitude());
            //latitude1=Double.parseDouble(latitude);
            //session.setAttribute("latitude", latitude1);
        }
        mv.setViewName("user/userstore/search_store_list");
        return mv;
    }

    @RequestMapping("/searchOrganList")
    @ResponseBody
    public FlipInfo<Organ> searchOrganList(String search, int page, int pagesize, double longitude, double latitude, HttpServletRequest request) throws Exception {
        FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);

        FlipInfo<Organ> organList = null;
        fpi.setSize(pagesize);
        GpsPoint gps = PositionUtil.bd09_To_Gcj02(latitude, longitude);
        fpi.getParams().remove("search");
        fpi.getParams().remove("pagesize");
        fpi.getParams().put("weixin|boolean", "true");
        fpi.getParams().put("valid|integer", "1");
        fpi.getParams().remove("longitude");
        fpi.getParams().remove("latitude");
        fpi.getParams().put("regex:name", search);
        fpi = weOrganService.queryOrganListByUser(gps.getLongitude(), gps.getLatitude(), -1, fpi);
        return fpi;
    }

    @InitBinder
    public void InitBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        // 解决_id字段注入问题，去除“_”前缀处理
        binder.setFieldMarkerPrefix(null);
    }

    public String getTime(Date date, String reg) {
        SimpleDateFormat fom = new SimpleDateFormat(reg);
        String time = fom.format(date);
        return time;
    }

    /**
     * 我的钱包
     */
    @RequestMapping("/wallet")
    public ModelAndView toWallet(HttpServletRequest request) throws Exception{
        try{
            ModelAndView mv = new ModelAndView();
            HttpSession session =request.getSession();
            Organ organSession = (Organ)session.getAttribute("organ");
            String organId = organSession.get_id();
            Organ organ = weOrganService.queryOrganById(organId);
            DecimalFormat df = new DecimalFormat("#.00");
            organ.setWalletAmount(Double.parseDouble(df.format(organ.getWalletAmount())));
            mv.addObject("organ", organ);
            mv.setViewName("organ/store/wallet");
            return mv;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询交易记录
     *
     * @param request request
     * @param page 分页
     * @return 界面
     */

    @RequestMapping("/toExpense")
    @ResponseBody
    public FlipInfo<WeUserWalletHis> toExpense(@RequestParam(required = true) String id,
                                               @RequestParam int page,HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        Organ organ = (Organ)session.getAttribute("organ");
        FlipInfo<WeUserWalletHis> weUserWalletHis = new FlipInfo<WeUserWalletHis>();
        weUserWalletHis.setPage(page);
        weUserWalletHis.setSortField("createTime");
        weUserWalletHis.setSortOrder("desc");
        weUserWalletHis = userMyService.queryWallet(weUserWalletHis,organ.get_id());
        return weUserWalletHis;
    }


    /**
     * 设置支付密码
     *
     * @param request request
     * @return return
     */
    @RequestMapping(value = "/setPayPassword", method = RequestMethod.POST)
    @ResponseBody
    public ReturnStatus setPayPassword( HttpServletRequest request) throws BaseException {
        ModelAndView mv = new ModelAndView();
        HttpSession session  = request.getSession();
        Organ organSession = (Organ)session.getAttribute("organ");
        String organId = organSession.get_id();
        Organ organ = weOrganService.queryOrganById(organId);

        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!com.osg.framework.util.StringUtils.isEmpty(organ.getPhone())) { //已经设置过手机号
            if (phone.equals(organ.getPhone())) {
                if (accountService.verify(phone, code).isSuccess()) {
                    //设置支付密码
                    if (password.equals(confirmPassword)) {
                        password = passwordEncoder.encodePassword(password, organ.get_id());
                        weOrganService.updatePwd(organ.get_id(), password);
                            return new ReturnStatus(1, "恭喜你设置成功");
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
            //没有绑定过手机号
            if (weOrganService.organVerify(phone, code, organ.get_id()).isSuccess()) {
                Organ u = weOrganService.queryOrganByPhone(phone);
                if (u != null) {
                    request.setAttribute("organ", u);
                }
                if (password.equals(confirmPassword)) {
                    password = passwordEncoder.encodePassword(password, organ.get_id());
                    weOrganService.updatePhone(u.get_id(), phone);
                    weOrganService.updatePwd(u.get_id(), password);
                    return new ReturnStatus(1, "恭喜你设置成功");
                } else {
                    return new ReturnStatus(4, "两次密码输入的不一致,请重新输入");
                }
            } else {
                return new ReturnStatus(2, "验证码验证失败,请重新获取");
            }
        }
    }

    /**
     * 我的提现记录界面
     */
    @RequestMapping("/toInputMoney")
    public ModelAndView toInputMoney(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session  = request.getSession();
        Organ organSession = (Organ)session.getAttribute("organ");
        String phone= organSession.getPhone();
        Organ organ = weOrganService.queryOrganById(organSession.get_id());
        User user = weUserService.findUserByPhone(phone);
        mv.addObject("user",user);
        if (!StringUtils.isEmpty(organ.getPayPassword())) {
            mv.setViewName("organ/store/apply_cash");
        } else {
            mv.setViewName("organ/store/set_PayPassword");
        }
        return mv;
    }

    /**
     * 我的转赠记录界面
     */
    @RequestMapping("/donationRecord")
    public ModelAndView donationRecord( ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("organ/store/donation_record");
        return mv;
    }

    /**
     * 跳转到我转赠界面
     * @param request request
     * @return mv
     */
    @RequestMapping("/toSetPayPassword")
    public ModelAndView toSetPayPassword(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("organ/store/set_PayPassword");
        String donationPhone = request.getParameter("phone");
        if(!com.osg.framework.util.StringUtils.isEmpty(donationPhone)) {
            mv.addObject("donationPhone", donationPhone);
        }
        return mv;
    }

    /**
     * 确认转增信息
     * @param request request
     * @return mv
     */
    @RequestMapping("/commitDonation")
    @ResponseBody
    public ReturnStatus commitDonation(HttpServletRequest request) throws Exception {
        String phone  = String.valueOf(request.getParameter("phone"));
        HttpSession session =  request.getSession();
        Organ organSession =(Organ) session.getAttribute("organ");
        User user = weUserService.findUserByPhone(organSession.getPhone());
        if(phone.equals(user.getPhone())) {
            return new ReturnStatus(3,"喵,对不起,不能转赠给自己");
        }
        Organ organ = weOrganService.queryOrganById(organSession.get_id());
        if(organ != null) {
            if(!com.osg.framework.util.StringUtils.isEmpty(organ.getPayPassword())) {
                return new ReturnStatus(1);
            } else {
                return new ReturnStatus(4);
            }
        } else  {
            return new ReturnStatus(2,"被转增人未绑定手机号请在任性猫公众号中点击我的-个人信息里完成手机号绑定");
        }
    }

    /**
     * 跳转到我转赠界面
     */
    @RequestMapping("/myDonation")
    public ModelAndView myDonation( ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("organ/store/my_donation");
        return mv;
    }


    /**
     * 提现到微信
     */

    @RequestMapping("/toCashWeChat")
    @ResponseBody
    public int toCashWeChat( HttpServletRequest request) {
        try {
            HttpSession session  = request.getSession();
            Organ organSession = (Organ)session.getAttribute("organ");
            String organId = organSession.get_id();
            String money = request.getParameter("money");
            Organ organ = weOrganService.queryOrganById(organId);
            double moneyTemp = Double.parseDouble(money);
            if(organ.getWalletAmount() <= 0) {
                return 3; // 你的余额不足了提现
            }
            if(organ.getWalletAmount() < moneyTemp) {
                return 3; // 你的余额不足了提现
            }
            int senderType = 3;// 表示店铺
            int status = 0;    //表示未完成
            int type =1;    //表示提现

            boolean payStatus = weOrganService.queryWePayLog(organId,senderType,status,type);
            if(payStatus) return 5; //有未完成的支付

            int amount = 100 * Integer.parseInt(money);
            String password  = request.getParameter("password");
            if (com.osg.framework.util.StringUtils.isEmpty(organId)) {
                throw new BaseException("用户userId为空！！");
            }
            if (com.osg.framework.util.StringUtils.isEmpty(password)) {
                throw new BaseException("密码为空！！");
            }
            password  = passwordEncoder.encodePassword(password, organId);
            if(password.equals(organ.getPayPassword())) {
                Account account = accountService.getAccountByEntityID(organId, "organ");
                if(account == null) {
                    throw new BaseException("该用户account表里openId为空！");
                }
                WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
                weUserWalletHis.setIdIfNew();
                weUserWalletHis.setDesc("店铺提现");
                weUserWalletHis.setAmount(Integer.parseInt(money) * -1);
                weUserWalletHis.setUserId(organId);
                weUserWalletHis.setCreateTime(new Date());
                weUserWalletHis.setOrderId("5");
                String time=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
                WePayLog wePayLog = new WePayLog();
                wePayLog.setIdIfNew();
                wePayLog.setUserId(organId);
                wePayLog.setStatus(0);
                wePayLog.setSenderType(3);
                wePayLog.setMessage("喵,您于"+time+" 成功申请提现喽~"+money+"元");
                wePayLog.setType(1);
                wePayLog.setMoney(-moneyTemp);
                wePayLog.setCreateTimeIfNew();
                weOrganService.saveWePayLog(wePayLog);
                Map<String, String> returnResult = CashUtil.CashToWeChatByOrgan(weUserWalletHis.get_id(),account.getAccountName(), amount, organ.getAbname() + "你已经成功提现 " + money + "元", request.getRemoteAddr());
                logger.info("体现结果"+returnResult);
                if(returnResult != null) {
                    if(!com.osg.framework.util.StringUtils.isEmpty(returnResult.get("err_code"))) {
                        return 4;
                    }
                }
                WePayLog wePayLogNew = weOrganService.queryWePayLogById(wePayLog.get_id());
                wePayLogNew.setMessage(wePayLogNew.getMessage()+"微信返回结果:"+returnResult);
                wePayLogNew.setStatus(1);
                weOrganService.saveWePayLog(wePayLogNew);
                weOrganService.saveWeUserWalletHis(weUserWalletHis);
                weOrganService.updateUserWalletAmount(organId,organ.getWalletAmount()- Double.parseDouble(money));
                //提现成功推送消息
                WxTemplate tempToUser = redisService.getWxTemplate("喵,您于"+time+" 成功申请提现喽~", money+"元","0元", money+"元","任性猫网站提现",null, "喵小二提醒您：提现已成功，请您注意查收哦~~");
                redisService.send_template_message(account.getAccountName(), "organ", Configure.WITHDRAW_USER, tempToUser);
                return 1;
            } else {
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 确认提现
     * @param request request
     * @return mv
     */

    @RequestMapping("/isPayPassword")
    @ResponseBody
    public int isPayPassword(HttpServletRequest request) throws BaseException {
        HttpSession session  = request.getSession();
        Organ organSession = (Organ)session.getAttribute("organ");
        String organId = organSession.get_id();
        String money = request.getParameter("money");
        Organ organ = weOrganService.queryOrganById(organId);
        if(organ.getWalletAmount()-Double.parseDouble(money) < 0) {
            return 2;
        }
        if(com.osg.framework.util.StringUtils.isEmpty(organ.getPayPassword())) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 通过手机号获得验证码	getCodeByPhone
     */
    @RequestMapping(value ="/getCodeByPhone", method = RequestMethod.POST)
    @ResponseBody
    public String getCodeByPhone(HttpServletRequest request) {
        String phone =String.valueOf(request.getParameter("phone"));
        if(accountService.verifycode(phone).isSuccess()) {
            return "获取验证码成功！";
        }else {
            return "获取验证码失败！";
        }
    }
    /**
     * 验证手机号和验证码
     * @param request request
     * @return msg
     */
    @RequestMapping(value ="/toVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public String toVerifyCode(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Organ organSession = (Organ)session.getAttribute("organ");
        Organ organ = weOrganService.queryOrganById(organSession.get_id());
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        if(organ.getPhone() == null) {
            if(weOrganService.organVerify(phone, code, organSession.get_id()).isSuccess()){
                Organ u=weOrganService.queryOrganByPhone(phone);
                if(u!=null){
                    session.setAttribute("organ", u);
                    request.setAttribute("organ", u);
                }
                return "绑定成功";
            }else {
                return "验证失败";
            }
        }

        if(phone.equals(organ.getPhone())) {
            if(accountService.verify(phone, code).isSuccess()) {
                return "绑定成功";
            } else {
                return "验证失败";
            }
        }
        return "手机号和绑定的不一致手机，不能设置支付密码！";
    }

    /**
     * 跳转到输入金额界面
     * @param request request
     * @return mv
     */
    @RequestMapping("/comfirtDonation")
    public ModelAndView comfirtDonation(HttpServletRequest request) throws BaseException {
        ModelAndView mv = new ModelAndView();
        String phone = request.getParameter("phone");
        HttpSession session =  request.getSession();
        Organ organSession = (Organ)session.getAttribute("organ");
        String organId = organSession.get_id();
        User user = weUserService.findUserByPhone(phone);
        Organ o = weOrganService.queryOrganById(organId);
        mv.addObject("receiveUser", user);
        mv.addObject("organ", o);
        mv.setViewName("organ/store/comfirt_donation");
        return mv;
    }

    /**
     * 输入提现密码界面
     */
    @RequestMapping("/inputPwd")
    public ModelAndView inputPwd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String money = request.getParameter("money");
        mv.addObject("money", money);
        mv.setViewName("organ/store/input_pwd");
        return mv;
    }

    /**
     * 提现确认密码
     * @param request request
     * @return mv
     */
    @RequestMapping("/toComPwd")
    public ModelAndView toComPwd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        double amount = Double.parseDouble(request.getParameter("amount"));
        String receiveUserId = request.getParameter("userId");
        mv.addObject("amount", amount);
        mv.addObject("receiveUserId", receiveUserId);
        mv.setViewName("organ/store/com_pwd");
        return mv;
    }

    /** 提交用户输入的金额
	 * @param request request
	 * @param response response
	 * @return   0 表示转赠失败  1 表示转赠成功    2.表示密码错误      3.表示余额不足   4.表示你还没设置支付密码
	 * 	 */
    @RequestMapping("/comDonation")
    @ResponseBody
    public int comDonation(HttpServletRequest request,HttpServletResponse response) {
        try {
            double amount = Double.parseDouble(request.getParameter("amount"));
            String password = request.getParameter("password");
            String receiveUserId = request.getParameter("receiveUserId");
            HttpSession session = request.getSession();
            Organ organSession = (Organ) session.getAttribute("organ");
            Organ organ = weOrganService.queryOrganById(organSession.get_id());
            password = passwordEncoder.encodePassword(password, organ.get_id());
            String payPassword = organ.getPayPassword();
            if (payPassword != null ) {
                if (password.equals(payPassword)) {
                    double walletAmount = organ.getWalletAmount();
                    walletAmount = walletAmount - amount;
                    if (walletAmount > 0) {
                        //这里添加更新余额逻辑
                        weOrganService.updateUserWalletAmount(organSession.get_id(), walletAmount);
                        WeUserWalletHis weDonation = new WeUserWalletHis();
                        weDonation.setNewId();
                        weDonation.setNewCreate();
                        weDonation.setUserId(receiveUserId);
                        weDonation.setSendUserId(organ.get_id());
                        weDonation.setAmount(amount);
                        weDonation.setDesc("店铺 " + organ.getAbname() + " 钱包转赠");
                        //这里添加更新余额逻辑
                        User receiveUser = weUserService.queryUserById(receiveUserId);
                        double rWalletAmount = receiveUser.getWalletAmount() + amount;
                        userMyService.updateUserWalletAmount(receiveUserId, rWalletAmount);
                        userMyService.saveDonationRecord(weDonation);
                        weDonation.setNewId();
                        weDonation.setUserId(organ.get_id());
                        weDonation.setSendUserId(receiveUserId);
                        weDonation.setAmount(-amount);
                        weDonation.setDesc("向用户" + receiveUser.getNick() + " 转赠");
                        userMyService.saveDonationRecord(weDonation);
                        //用户通知
                        //添加消息通知
                        //转增方通知
                        Account account = accountService.getAccountByEntityID(organ.get_id(), "organ");
                        WxTemplate tempToUser = redisService.getWxTemplate("喵，" + organ.getAbname() + "，您的转增交易已成功", "任性猫钱包余额转增", amount + "", "0", getTime(new Date(), "yyyy-MM-dd HH:mm:ss"), null, "感谢您的使用！");
                        redisService.send_template_message(account.getAccountName(), "user", Configure.DONATION_INFO, tempToUser);
                        //收账方
                        account = accountService.getAccountByEntityID(receiveUser.get_id(), "user");
                        tempToUser = redisService.getWxTemplate("喵，" + receiveUser.getNick() + "，您收到一笔转增交易", "任性猫钱包余额转增", amount + "", "0", getTime(new Date(), "yyyy-MM-dd HH:mm:ss"), null, "感谢您的使用！");
                        redisService.send_template_message(account.getAccountName(), "user", Configure.DONATION_INFO, tempToUser);
                        return 1;
                    } else {
                        return 3;
                    }
                } else {
                    return 2;
                }
            } else {
                return 4;
            }
        } catch (Exception e) {
            return 0;
        }
    }

        /**
         * 去设置支付密码页面
         * @param request
         * @param response
         * @return
         */
        @RequestMapping("/toMyPayPassword")
        public ModelAndView toMyPayPassword (HttpServletRequest request, HttpServletResponse response) throws
        BaseException {
            ModelAndView mv = new ModelAndView("organ/store/my_PayPassword");
            HttpSession session = request.getSession();
            Organ organSession = (Organ) session.getAttribute("organ");
            Organ organ = weOrganService.queryOrganById(organSession.get_id());
            mv.addObject("organId",organ.get_id());
            if (!com.osg.framework.util.StringUtils.isEmpty(organ.getPayPassword())) {
                mv.addObject("havePassword", true);
            } else {
                mv.addObject("havePassword", false);
            }
            return mv;
        }

    /**
     * 跳转到主页面
     */
    @RequestMapping("/backIndex")
    public ModelAndView backIndex () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("organ/store/index");
        return mv;
    }

    /**
     * 跳转到主页面
     */
    @RequestMapping("/backWallet")
    public ModelAndView backWallet () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("organ/store/wallet");
        return mv;
    }

    @RequestMapping("/toWalletRule")
    public ModelAndView toWalletRule(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("organ/store/wallet_rule");
        return mv;
    }


      /*
            @RequestMapping("/toExpenseList")
            public ModelAndView toExpenseList( ) throws Exception {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("organ/store/expense_record");
                return mv;
            }


        @RequestMapping("/expenseRecord")
        @ResponseBody
        public FlipInfo<WeOrganOrder> expenseRecord(String page,String size,
                                                    HttpServletRequest request) throws Exception {
            FlipPageInfo<WeOrganOrder> flp=new FlipPageInfo<WeOrganOrder>(request);
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            flp.getParams().remove("userId");
            flp.getParams().remove("size");
            flp.setSize(10);
            flp.setSortField("orderTime");
            flp.setSortOrder("desc");
            FlipInfo<WeOrganOrder> fpi = userMyService.expenseRecord(user.get_id(),page,size,flp);
            return fpi;
        }
        */

    }


