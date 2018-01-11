package com.mrmf.module.user.userprice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Account;
import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeCarousel;
import com.mrmf.entity.WeStaffCalendar;
import com.mrmf.entity.WeToken;
import com.mrmf.entity.WeUserInquiry;
import com.mrmf.entity.WeUserInquiryQuote;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.WeekDay;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.Usersort;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Configure;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.userService.UserService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weuser.WeUserService;
import com.osg.entity.Entity;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.PositionUtil;
import com.osg.framework.util.StringUtils;

@Controller
@RequestMapping("/user")
public class EnquiryPriceController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private WeComonService weCommonService;
	@Autowired 
	private RedisService redisService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserMyService userMyService;
	@Autowired
	private  WeUserService weUserService;
	//跳转询价列表
	@RequestMapping("/enquiryPriceList")
	public ModelAndView enquiryPriceList(@RequestParam(required=false)String order,String userId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		WeUserInquiry weUserInquiry=userService.getInquiry(user.get_id());
		if (weUserInquiry == null) {
			mv.setViewName("user/userprice/enquiryPrice");
			Map<String, Object> sign;
			if(StringUtils.isEmpty(request.getQueryString())) {
				sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(),"user");
			} else {
				sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");
			}
			mv.addObject("sign", sign);
			return mv;
		}else {
			request.setAttribute("inquiryId", weUserInquiry.get_id());
			return new ModelAndView("user/userprice/userPriceReply");
		}
	}
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(HttpServletRequest request)throws Exception{
		String serverId = request.getParameter("serverId");
		WeToken weToken = redisService.getTonkenInfo("user");
		URL url = new URL(new StringBuilder("https://api.weixin.qq.com/cgi-bin/media/get?access_token=").
		    		append(weToken.getToken()).append("&media_id=").append(serverId).toString());
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");
	    InputStream is = url.openStream();
		String imgName = weCommonService.downImg(is,request);
		String urlPath=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
		File imgFile=new File(urlPath);
		InputStream inFile=new FileInputStream(imgFile);
		String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
		String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
		is.close();
		inFile.close();
	    return ossId;
	}
	
	//跳转上一页
	@RequestMapping("/tolastPage")
	public ModelAndView tolastPage(@RequestParam(required=false)String status,String userId,HttpServletRequest request)throws Exception{
		if (status.equals("quoteDetail")) {
			return enquiryPriceList(null, userId, request);
		}else if (status.equals("quoteList")) {
			return toHomePage(userId, request);
		}
		return null;
	}
	//跳转首页
	@RequestMapping("/toHomePage")
	public ModelAndView toHomePage(@RequestParam(required=true)String userId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		User user2=userService.getUserById(user.get_id());
		request.setAttribute("user", user2);
		List<WeCarousel> weCarousels = weUserService.findCarousels();
		request.setAttribute("weCarousels", weCarousels);
		return new ModelAndView("user/userhome/home_page");
	}
	//跳转询价列表显示
	@RequestMapping("/toQuoteList")
	@ResponseBody
	public FlipInfo<WeUserInquiryQuote> toQuoteList(@RequestParam(required=false)String orderStatus,String inquiryId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		FlipInfo<WeUserInquiryQuote> fpi = new FlipPageInfo<WeUserInquiryQuote>(request);
		fpi.getParams().remove("userId");
		fpi.getParams().remove("orderStatus");
		if (orderStatus !=null && !orderStatus.equals("")) {
			if (orderStatus.equals("price")) {//价格
				fpi.setSortField("price");
				fpi.setSortOrder("asc");
			}else if (orderStatus.equals("distance")) {//距离
				fpi.setSortField("distance");
				fpi.setSortOrder("asc");
			}else if (orderStatus.equals("hot")) {//热度
				fpi.setSortField("followCount");
				fpi.setSortOrder("desc");
			}
		}
		FlipInfo<WeUserInquiryQuote> enquiryQuote=userService.getEnquiryList(user.get_id(),inquiryId,fpi);
		return enquiryQuote;
	}
	//保存询价
	@RequestMapping("/saveEnquiry")
	@ResponseBody
	public String saveEnquiry(HttpServletRequest request,HttpSession session)throws Exception{
		User user = (User)session.getAttribute("user");
		String logo0 = request.getParameter("logo0");
		String logo1 = request.getParameter("logo1");
		String logo2 = request.getParameter("logo2");
		String codeId = request.getParameter("codeId");
		Code code = staffService.findCodeById(codeId);
		String desc = request.getParameter("desc");
		String longitude = String.valueOf(session.getAttribute("longitude"));
		String latitude = String.valueOf(session.getAttribute("latitude"));
		Double longitudeD = null;
		Double latitudeD = null;
		if(!StringUtils.isEmpty(longitude)) {
			longitudeD = Double.parseDouble(longitude);
		}
        if(!StringUtils.isEmpty(longitude)) {
        	latitudeD = Double.parseDouble(latitude);
		}
		ReturnStatus returnStatus=userService.saveEnquiry(user.get_id(),longitudeD,latitudeD,logo0,logo1,logo2,code.getName(),desc);
		if (returnStatus.isSuccess()) {
			return "true";
		}
		return "false";
	}
	//删除询价
	@RequestMapping("/deleteEnquiry")
	public ModelAndView deleteEnquiry(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		/*获取上次的地理位置*/
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		//findOldLocation(longitude,latitude,request);
		
		ReturnStatus returnStatus=userService.deleteEnquiry(user.get_id());
		if (returnStatus.isSuccess()) {
			return new ModelAndView("user/userprice/enquiryPrice");
		}
		return enquiryPriceList(null,user.get_id(), request);
	}
	
	
	/**
	 * 获取用户的上次位置
	 * @param longitude
	 * @param latitude
	 * @param request
	 */
	private void findOldLocation(String longitude, String latitude,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		if(StringUtils.isEmpty(longitude) || longitude=="0" || StringUtils.isEmpty(latitude) || latitude =="0") {
			longitude = user.getGpsPoint().getLongitude()+"";
			latitude = user.getGpsPoint().getLatitude()+"";
			session.setAttribute("longitude", Double.parseDouble(longitude));
			session.setAttribute("latitude", Double.parseDouble(latitude));
		}
	}
	//询价详情
	@RequestMapping("/enquiryDetail")
	public ModelAndView enquiryDetail(@RequestParam(required=false)String quoteId,String userId,String organId,String codeId,HttpServletRequest request)throws Exception{
		WeUserInquiryQuote weUserInquiryQuote =userService.getQuoteById(quoteId);
		Staff staff = staffService.getById(weUserInquiryQuote.getStaffId());
		if (organId !=null) {
			Organ organ = staffService.getOrgan(organId);
			request.setAttribute("organ", organ);
		}
		ReturnStatus status=userService.getStaffCalendar(weUserInquiryQuote.getStaffId());
		if (status.isSuccess()) {
			request.setAttribute("staffState", "true");
		}else {
			request.setAttribute("staffState", "false");
		}
		request.setAttribute("weUserInquiryQuote", weUserInquiryQuote);
		request.setAttribute("staff", staff);
		request.setAttribute("userId", userId);
		return new ModelAndView("user/userprice/enquiryQuoteDetail");
	}
	//预约店铺
	@RequestMapping("/toAppointTime")
	public ModelAndView toAppointTime(@RequestParam(required = true)String staffId,@RequestParam(required = true)String userId,@RequestParam(required = true)String replyId,HttpServletRequest request, 
			HttpServletResponse response) {
		List<WeekDay> weekDays=weCommonService.queryScheduleTitle();
		int day=weekDays.get(0).getDaytime();
		int day7=weekDays.get(6).getDaytime();
		List<WeStaffCalendar> weStaffCalendars= staffService.findWeStaffSchedule(staffId,day,day7);
		if (weStaffCalendars !=null) {
			for(WeStaffCalendar weStaffCalendar :weStaffCalendars) {
				Organ organ=staffService.getOrgan(weStaffCalendar.getOrganId());
				weStaffCalendar.setOrganName(organ.getName());
				String monthDay = String.valueOf(weStaffCalendar.getDay()).substring(4);
				weStaffCalendar.setMonthDay(monthDay);
				String week = WeekDay.getWeekByDate(weStaffCalendar.getDay(), day);
				weStaffCalendar.setWeek(week);
			}
		}
		Staff staff = staffService.getById(staffId);
		if (staff !=null) {
			int start=0,end=0;
			if (staff.getBusyTimeStart() !=null && !staff.getBusyTimeStart().equals("")) {
				start = Integer.parseInt(staff.getBusyTimeStart().substring(0, 2));
			}
			if (staff.getBusyTimeEnd()!=null && !staff.getBusyTimeEnd().equals("")) {
				end = Integer.parseInt(staff.getBusyTimeEnd().substring(0, 2));
			}
			request.setAttribute("start", start);
			request.setAttribute("end", end);
		}
		request.setAttribute("staffId", staffId);
		request.setAttribute("replyId", replyId);
		request.setAttribute("weStaffCalendars", weStaffCalendars);
		request.setAttribute("day", day);
		request.setAttribute("userId", userId);
		return new ModelAndView("user/userprice/appoint_time");
	}
	//返回预约列表
	@RequestMapping("/reAppoint")
	public ModelAndView reAppoint(@RequestParam(required=true)String organId,String userId,String time1,String timeNum,String replyId,String organName,HttpServletRequest request)throws Exception{
		String day=request.getParameter("day");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(day+" "+time1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = sdf.format(date);
		WeUserInquiryQuote weUserInquiryQuote =userService.getQuoteById(replyId);
		if (weUserInquiryQuote !=null) {
			Staff staff=userService.getStaffById(weUserInquiryQuote.getStaffId());
			request.setAttribute("staff", staff);
		}
		request.setAttribute("weUserInquiryQuote", weUserInquiryQuote);
		request.setAttribute("date", dateStr);
		request.setAttribute("userId", userId);
		request.setAttribute("day", day);
		request.setAttribute("organName", organName);
		request.setAttribute("organId", organId);
		request.setAttribute("timeNum", timeNum);
		return new ModelAndView("user/userprice/enquiryQuoteDetail");
	}
	//选择类型
	@RequestMapping("/selectType")
	public ModelAndView selectType(HttpServletRequest request)throws Exception{
		List<Code> types = staffService.findTypes();
		String codeId = request.getParameter("codeId");
		String type = request.getParameter("type");
		String desc = request.getParameter("desc");
		String staffId = request.getParameter("staffId");
		request.setAttribute("types", types);
		request.setAttribute("type", type);
		request.setAttribute("codeId", codeId);
		request.setAttribute("desc", desc);
		request.setAttribute("staffId", staffId);
		return new ModelAndView("user/userprice/change_type");
	}
	//选择类型
	@RequestMapping("/enquiryPrice")
	public ModelAndView enquiryPrice(HttpServletRequest request)throws Exception{
		String codeId = request.getParameter("codeId");
		String desc = request.getParameter("desc");
		String staffId = request.getParameter("staffId");
		Code code = staffService.findCodeById(codeId);
		request.setAttribute("code", code);
		request.setAttribute("desc", desc);
		request.setAttribute("staffId", staffId);
		if (staffId !=null && !staffId.equals("")) {
			request.setAttribute("staffId", staffId);
			
			return new ModelAndView("staff/myExample/addExample");
		}
		Map<String, Object> sign;
		ModelAndView mv = new ModelAndView("user/userprice/enquiryPrice");
		if(StringUtils.isEmpty(request.getQueryString())) {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(),"user");
		} else {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");
		}
		mv.addObject("sign", sign);
		return mv;
	}
	//保存预约
	@RequestMapping("/appointSave")
	public ModelAndView appointSave(@RequestParam(required=true)String replyId,String organId,String staffId,String quoteId,String orderTime,double price,String type,int timeNum,int day,HttpServletRequest request )throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		WeUserInquiry inquiry = userService.getMyInquiry(quoteId);
		ReturnStatus status= userService.appointSave(user.get_id(),organId,staffId,quoteId,orderTime,price,timeNum,day,replyId);
		if (status.isSuccess()) {
			//给客户发送消息
			String time="";
			Organ organ = userMyService.getOrganById(organId);
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(orderTime);
			time=new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm").format(date);
			Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
			WxTemplate tempToUser = redisService.getWxTemplate("喵，请耐心等待，您的预约正在确认中。", status.getMessage(),inquiry.getType() ,time , organ.getAddress(),price+"元起", "喵，您的预约信息已成功推送给技师，请等待技师确认。");
			redisService.send_template_message(userInfo.get("openid").toString(), "user", Configure.ORDERED_ORGAN_SUCCESS, tempToUser);
			//给技师通知
			Account account = accountService.getAccountByEntityID(staffId, "staff");
	        WxTemplate tempToStaff = redisService.getWxTemplate("喵，有新的客户预约啦~~",user.getNick(),user.getPhone(), orderTime, inquiry.getType(),null, "喵小妹提醒您：请根据您的繁忙程度进行确认。");
	        redisService.send_template_message(account.getAccountName(),"staff", Configure.APPOINT_STAFF_REMIND, tempToStaff);
			request.setAttribute("user", user);
			return new ModelAndView("user/userhome/home_page");
		}
		return enquiryDetail(quoteId, null, organId, null, request);
	}
	
	//我的会员卡
	@RequestMapping("/myvipCard")
	public ModelAndView myvipCard(HttpServletRequest request)throws Exception{
		return new ModelAndView("user/usermy/myvip/my_vip_card");
	}
	
	
	//查看子卡
	@RequestMapping("/myvipInCard")
	public ModelAndView myvipInCard(String cardId,HttpServletRequest request,HttpSession session)throws Exception{
		if (!StringUtils.isEmpty(cardId)) {
			request.setAttribute("cardId", cardId);
		}
		return new ModelAndView("user/usermy/myvip/my_incard");
	}
	//会员卡列表
	@RequestMapping("/myvipCardList")
	@ResponseBody
	public FlipInfo<Userincard> myvipCardList(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		FlipInfo<Userincard> fpi = new FlipPageInfo<Userincard>(request);
		fpi.getParams().remove("userId");
		FlipInfo<Userincard> userCard=userService.getVIPCard(user.get_id(),fpi);
		return userCard;
	}
	//会员卡子卡列表
	@RequestMapping("/myvipInCardList")
	@ResponseBody
	public FlipInfo<Userinincard> myvipInCardList(@RequestParam(required=false)String userId,String cardId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		FlipInfo<Userinincard> fpi = new FlipPageInfo<Userinincard>(request);
		fpi.getParams().remove("userId");
		fpi.getParams().remove("cardId");
		FlipInfo<Userinincard> userinCard=userService.getVIPInCard(user.get_id(),cardId,fpi);
		request.setAttribute("userId", userId);
		return userinCard;
	}
	
	//会员卡详情
	@RequestMapping("/myCardDetail")
	public ModelAndView myCardDetail(String cardId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (!StringUtils.isEmpty(cardId)) {
			Userincard userCard=userService.getCard(cardId);
			Userinincard userinincard = userService.findInInCard(userCard.get_id(),user.get_id());
			request.setAttribute("userinincard", userinincard);
			request.setAttribute("userCard", userCard);
		//	session.setAttribute("cardId", userCard.get_id());
			Usersort usersort=userService.getUsersort(userCard.getMembersort());
			Organ organ = userMyService.getOrganById(userCard.getOrganId());
			if(organ.getCanCharge()){
				request.setAttribute("canCharge", true);
			}else{
				request.setAttribute("canCharge", false);
			}
			request.setAttribute("usersort", usersort);
		}
		
		return new ModelAndView("user/usermy/myvip/my_card_detail");
	}
	//会员卡子卡详情
	@RequestMapping("/myInCardDetail")
	public ModelAndView myInCardDetail(@RequestParam(required=false)String userId,String cardId,String incardId,HttpServletRequest request,HttpSession session)throws Exception{
		Userinincard userincard=userService.getIninCard(incardId);
		request.setAttribute("userId", userId);
		request.setAttribute("userincard", userincard);
		request.setAttribute("cardId", cardId);
		return new ModelAndView("user/usermy/myvip/my_incard_detail");
	}
	
	//会员卡详情记录
	@RequestMapping("/cardDetailList")
	@ResponseBody
	public FlipInfo<Userincard> cardDetailList(@RequestParam(required=false)String userId,String cardId,HttpServletRequest request)throws Exception{
		FlipPageInfo<Userincard> fpi=new FlipPageInfo<Userincard>(request);
		fpi.getParams().remove("userId");
		fpi.getParams().remove("cardId");
		FlipInfo<Userincard> usercard= userService.getcardInfo(userId,cardId,fpi);
		return usercard;
	}
	//子卡详情记录
	@RequestMapping("/inCardDetailList")
	@ResponseBody
	public FlipInfo<Userinincard> inCardDetailList(String incardId,HttpServletRequest request)throws Exception{
		FlipInfo<Userinincard> usercards= userService.getIncardInfo(incardId);
		return usercards;
	}
	
	//会员消费记录
	@RequestMapping("/customList")
	@ResponseBody
	public FlipInfo<Userpart> customList(String cardId,String cardType,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		FlipPageInfo<Userpart> fpi=new FlipPageInfo<Userpart>(request);
		fpi.getParams().remove("cardId");
		fpi.getParams().remove("cardType");
		fpi.setSortField("createTime");
		fpi.setSortOrder("desc");
		FlipInfo<Userpart> userpart= userService.getCustomList(user.get_id(),cardId,cardType,fpi);
		return userpart;
	}
	
	//会员充值记录
	@RequestMapping("/rechargeList")
	@ResponseBody
	public FlipInfo<Userpart> rechargeList(@RequestParam(required=false)String userId,String cardId,String cardType,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		FlipPageInfo<Userpart> fpi=new FlipPageInfo<Userpart>(request);
		fpi.getParams().remove("userId");
		fpi.getParams().remove("cardId");
		fpi.getParams().remove("cardType");
		fpi.setSize(5);
		fpi.setSortField("createTime");
		fpi.setSortOrder("desc");
		FlipInfo<Userpart> userpart= userService.getRechargeList(user.get_id(),cardId,cardType,fpi);
		return userpart;
	}
	
	//门店详情
	@RequestMapping("/cardStore")
	public ModelAndView cardStore(@RequestParam(required=false)String userId,String cardId,HttpServletRequest request,HttpSession session)throws Exception{
		request.setAttribute("userId", userId);
		request.setAttribute("cardId", cardId);
		return new ModelAndView("user/usermy/myvip/card_stores");
	}
	
	//会员卡门店列表
	@RequestMapping("/cardStoreList")
	@ResponseBody
	public FlipInfo<Organ> cardStoreList(String cardId,String card,HttpServletRequest request,HttpSession session)throws Exception{
		double longitude = 0,latitude = 0;
		if (session.getAttribute("longitude") !=null) {
			longitude = Double.parseDouble(session.getAttribute("longitude").toString());
		}
		if (session.getAttribute("latitude") !=null) {
			latitude = Double.parseDouble(session.getAttribute("latitude").toString());
		}
		GpsPoint gpsPoint = PositionUtil.bd09_To_Gcj02(latitude, longitude);
		FlipPageInfo<Organ> fpi=new FlipPageInfo<Organ>(request);
		fpi.getParams().remove("userId");
		fpi.getParams().remove("cardId");
		fpi.getParams().remove("card");
		FlipInfo<Organ> organs= userService.getCardStoreList(cardId,card,gpsPoint.getLongitude(),gpsPoint.getLatitude(),fpi);
		return organs;
	}
	//跳回
	@RequestMapping("/toMyHome")
	public ModelAndView toMyHome(@RequestParam(required=false)String userId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		request.setAttribute("user", user);
		return new ModelAndView("user/usermy/my_home");
	}
}
