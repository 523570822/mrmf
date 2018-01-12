package com.mrmf.module.user.userhome;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.*;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Configure;
import com.mrmf.service.common.WxgetInfo;
import com.mrmf.service.coupon.CouponGrantService;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffMyService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.weuser.WeUserService;
import com.mrmf.service.wxOAuth2.WXOAuth2Service;
import com.osg.entity.*;
import com.osg.framework.BaseException;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.cache.CacheManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 用户端首页相关的类
 * @author yangshaodong
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired 
	private WeUserService weUserService;
	@Autowired
	private StaffService staffService;
	@Autowired 
	private WeComonService weCommonService;
	@Autowired 
	private WxgetInfo wxgetInfo;
	@Autowired
	private UserMyService userMyService;
	@Autowired
	private AccountService accountService;
	@Autowired 
	private RedisService redisService;
	@Autowired 
	private WeOrganService weOrganService;
	@Autowired
	private StaffMyService staffMyService;
	@Autowired
	private CouponGrantService couponGrantService;
	@Autowired
	private WXOAuth2Service wxoAuth2Service;
	@Autowired
	private CacheManager cacheManager;

	private static Logger logger = Logger.getLogger(HomeController.class);
	/**
	 * 
	 * 访问主页的控制类
	 */
	@RequestMapping(value = "/toHomePage", method = RequestMethod.GET)
	public ModelAndView toHome(@RequestParam(required = false)String msg, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String url = Configure.DOMAIN_URL + request.getRequestURI();
		if (msg != null) {
			url = url + "?" + request.getQueryString();
		}
		Map<String, Object> sign = redisService.getWechatPositioningMessage(url,"user");
		mv.addObject("sign", sign);
		mv.addObject("msg", msg);
		List<WeCarousel> weCarousels = weUserService.findCarousels();
		mv.addObject("weCarousels", weCarousels);
		mv.setViewName("user/userhome/home_page");
		return mv;
	}



	/**
	 *	跳转到搜索案例界面
	 * @throws BaseException
	 */
	@RequestMapping(value ="/toQueryStaff", method = RequestMethod.GET)
	public ModelAndView toQueryStaff(HttpServletRequest request, HttpServletResponse response) throws BaseException{
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String userId = request.getParameter("userId");
		User user = weUserService.queryUserById(userId);
		Object lo = session.getAttribute("longitude");
		Object la = session.getAttribute("latitude");
		if (lo == null) {
			session.setAttribute("longitude", user.getGpsPoint( ).getLongitude( ));
			//longitude1=Double.parseDouble(longitude);
			//session.setAttribute("longitude", longitude1);
		}
		if (la == null) {
			session.setAttribute("latitude", user.getGpsPoint( ).getLatitude( ));
			//latitude1=Double.parseDouble(latitude);
			//session.setAttribute("latitude", latitude1);
		}
		request.setAttribute("userId",userId);
		mv.setViewName("user/userhome/search_staff");
		return mv;
	}

	/**
	 *  搜索技师  通过技师的name
	 */
	@RequestMapping(value ="/queryStaff", method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<Staff> queryStaffByName(HttpServletRequest request, HttpServletResponse response) throws Exception {

		FlipInfo<Staff> fpi = new FlipInfo<Staff>();
		String page1 = request.getParameter("page");
		int page = Integer.parseInt(request.getParameter("page"));
		fpi.setPage(page);
		String name = request.getParameter("name");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String maxDistance = request.getParameter("maxDistance");
		String band = request.getParameter("band");
		String clickCount = request.getParameter("clickCount");
		int count = Integer.parseInt(clickCount)%2;
		String distance = request.getParameter("distance");
		String price = request.getParameter("startPrice");
		String followCount = request.getParameter("followCount");
		Query query = new Query();
		if(longitude!= null&&!longitude.equals("")&&latitude!= null && !latitude.equals("")) {
			double longitude1 = Double.parseDouble(longitude);
			double latitude1 = Double.parseDouble(latitude);
			double maxDistance1 = Double.parseDouble(maxDistance);

			fpi = weUserService.queryStaffByName(query,longitude1, latitude1, maxDistance1,fpi,name);

			return fpi;
		} else {
			throw new RuntimeException("不能定位当前的位置");
		}
	}
	/**
	 * 去访问 案例的类
	 */
	@RequestMapping(value = "/toBeautyHair", method = RequestMethod.GET)
	public ModelAndView toBeautyHair(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		/*获取上次的地理位置*/
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		//findOldLocation(longitude,latitude,request);
		String type = request.getParameter("type");
		if(type !=null) {
		    type = new String(URLDecoder.decode(type,"utf-8"));
		}
		String homeType = request.getParameter("homeType");
		if(homeType !=null) {
			homeType = new String(URLDecoder.decode(homeType,"utf-8"));
		}
		List<Code> types = weUserService.findBeautyType("hairType");
		mv.addObject("types", types);
		HttpSession session = request.getSession(true);
		session.setAttribute("homeType",homeType);
		mv.setViewName("user/userhome/beautiful_hair");
		return mv;
	}
	/**
	 * 去访问 案例的类
	 */
	@RequestMapping(value = "/toTypeProgram", method = RequestMethod.GET)
	public ModelAndView toTypeProgram(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		/*获取上次的地理位置*/
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		//findOldLocation(longitude,latitude,request);
		String homeType = request.getParameter("homeType");
		if(homeType !=null) {
			homeType = new String(URLDecoder.decode(homeType,"utf-8"));
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("homeType",homeType);
		mv.setViewName("user/userhome/type_program");
		return mv;
	}
	/**
	 *  经典案例列表的类
	 */
	@RequestMapping(value = "/typeProgramList", method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<WeStaffCase> typeProgramList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String city =  String.valueOf(session.getAttribute("city"));
		double longitude =  Double.parseDouble(String.valueOf(session.getAttribute("longitude")));
		double latitude =  Double.parseDouble(String.valueOf(session.getAttribute("latitude")));
		String hotOrder = request.getParameter("hotOrder");
		String priceOrder = request.getParameter("priceOrder");
		String type = request.getParameter("type");
		int page = 1;
		String tempPage = request.getParameter("page");
		if(tempPage != null && !tempPage.equals("")) {
		    page = Integer.parseInt(tempPage);
		}
		FlipInfo<WeStaffCase> fpiWeStaffCases = new FlipInfo<WeStaffCase>();
		fpiWeStaffCases.setPage(page);
		fpiWeStaffCases.setSize(30);
		fpiWeStaffCases = weUserService.queryTypeProgramALL(fpiWeStaffCases, type, hotOrder,priceOrder,longitude,latitude,city);
		return fpiWeStaffCases;
	}
	
	
	/**
	 *  经典案例列表的类
	 */
	@RequestMapping(value = "/beautyHairList", method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<WeStaffCase> beautyHairList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hotOrder = request.getParameter("hotOrder");
		String priceOrder = request.getParameter("priceOrder");
		String type = request.getParameter("type");
		int priceCount = Integer.parseInt(String.valueOf(request.getParameter("priceCount")));
		HttpSession session = request.getSession();
		String city =  String.valueOf(session.getAttribute("city"));
		double longitude =  Double.parseDouble(String.valueOf(session.getAttribute("longitude")));
		double latitude =  Double.parseDouble(String.valueOf(session.getAttribute("latitude")));
		//User userSession = (User)session.getAttribute("user")
		List<String> typeList=new ArrayList<String>();
		if(type !=null && !type.equals("")) {
			if(type.equals("所有类型")) {
				List<Code> allTypeList = weUserService.findCodeByType("hairType");

				for (Code c:allTypeList){
					typeList.add(c.getName());
				}
			}
		}
		int page = 1;
		String tempPage = request.getParameter("page");
		if(tempPage != null && !tempPage.equals("")) {
		    page = Integer.parseInt(tempPage);
		}FlipInfo<WeStaffCase> fpiWeStaffCases = new FlipInfo<WeStaffCase>();
		fpiWeStaffCases.setPage(page);
		fpiWeStaffCases.setSize(50);
		fpiWeStaffCases = weUserService.queryBeautyALL(fpiWeStaffCases, type, hotOrder,priceOrder,longitude,latitude,city,priceCount,typeList);
		return fpiWeStaffCases;
	}
	/** 
	 *	跳转到搜索案例界面
	 * @throws BaseException 
	 */
	@RequestMapping(value ="/toQueryCase", method = RequestMethod.GET)
    public ModelAndView toCaseByName(HttpServletRequest request, HttpServletResponse response) throws BaseException{
	    ModelAndView mv = new ModelAndView();
	    String homeType = request.getParameter("homeType");
	   if(!StringUtils.isEmpty(homeType)) {
	    	try {
	    		homeType =URLDecoder.decode(homeType, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	    	HttpSession session = request.getSession(true);
			session.setAttribute("homeType",homeType);
	    } else {
	    	throw new BaseException("homeType,这个不能为空！！");
	    }
		mv.setViewName("user/userhome/search_case");
	    return mv;
    }
	
	/** 
	 *  搜索案例  通过案例的title
	 */
	@RequestMapping(value ="/queryCase", method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<WeStaffCase> queryCaseByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		HttpSession session  = request.getSession();
		String homeType =String.valueOf(session.getAttribute("homeType"));
		FlipInfo<WeStaffCase> fpiWeStaffCases = new FlipInfo<WeStaffCase>();
		int page = Integer.parseInt(request.getParameter("page"));
		fpiWeStaffCases.setPage(page);
		fpiWeStaffCases.setSize(6);
		fpiWeStaffCases= weUserService.queryBeautyCase(fpiWeStaffCases,title,homeType);
		return fpiWeStaffCases;
	}
	
	
	/**
	 * 案例详情页面toCaseDes
	 */
	@RequestMapping(value ="/toCaseDes", method = RequestMethod.GET)
	public ModelAndView toCaseDes(@RequestParam(required = true)String caseId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
//		User user = weUserService.queryUserById(userSession.get_id());
		String homeType = request.getParameter("homeType");
		String type = request.getParameter("type");
		String organId = request.getParameter("organId");
		String cityId = request.getParameter("cityId");
		String city = request.getParameter("city");
		String come= request.getParameter("come");
		String distance=request.getParameter("distance");
		request.setAttribute("distance", distance);
		request.setAttribute("come", come);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("organId", organId);
		request.setAttribute("userId", userSession.get_id());
		if(!StringUtils.isEmpty(homeType)) {
			homeType = URLDecoder.decode(homeType,"utf-8");
			session.setAttribute("homeType", homeType);
		}
		
//		if(user.getFavorCaseIds()!=null) {
//			List<String> caseIds =user.getFavorCaseIds();
//			if(caseIds.contains(caseId)) {
//				mv.addObject("collect", "取消收藏");
//			} else {
//				mv.addObject("collect", "收藏");
//			}
//		} else {
//			mv.addObject("collect", "收藏");
//		}
		WeStaffCase weStaffCase =weCommonService.queryWeStaffCaseById(caseId);
	    Staff staff = weUserService.queryStaffById(weStaffCase.getStaffId());
	    staffMyService.setWeStaffCalendar(staff.get_id());
	    int evaluateCount=0;
	    if(staff.getEvaluateCount() == null || staff.getEvaluateCount()==0) {
	    	evaluateCount = 1;
	    } else {
	    	evaluateCount = staff.getEvaluateCount();
	    }
	    int count = (int)Math.ceil(staff.getZanCount()/evaluateCount);
	    if(count >= 5) {
	    	count =  5;
	    }
	    
	    boolean isService  = weUserService.findStaffServiceTime(weStaffCase.getStaffId());
	    Organ organ = weUserService.findOrganById(staff.getOrganId());
		mv.addObject("weStaffCase", weStaffCase);
		mv.addObject("organ", organ);
		mv.addObject("staff", staff);
		mv.addObject("count", count);
		mv.addObject("isService", isService);
		mv.addObject("type", type);
		mv.setViewName("user/userhome/case_des");
		return mv;
	}
	
	/**
	 * 返回评论的列表 -->案例技师
	 * @param staffId
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/commentList")
	@ResponseBody
	public FlipInfo<WeOrganComment> organRatedList(String staffId,int page,HttpServletRequest request) throws Exception {
		FlipInfo<WeOrganComment> fpi = new FlipPageInfo<WeOrganComment>(request);
		FlipInfo<WeOrganComment> organCommentList=null;
		fpi.getParams().remove("staffId");
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		organCommentList=weCommonService.queryWeCommentById(2, staffId, fpi);
		return organCommentList;
	}
	/**
	 * 跳转到评论界面
	 * @param staffId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toComment", method = RequestMethod.GET)
	public ModelAndView queryComment(@RequestParam(required = true)String staffId,HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("staffId", staffId);
		mv.setViewName("user/userhome/user_comment");	
		return mv;
	}
	/**
	 * 预约信息
	 */
	@RequestMapping(value ="/appointInfo", method = RequestMethod.GET)
	public ModelAndView appointInfo(@RequestParam(required = true)String caseId,HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		WeStaffCase weStaffCase = weUserService.queryCaseById(caseId);
		mv.addObject("weStaffCase", weStaffCase);
		
		String type = request.getParameter("type");
		String organId = request.getParameter("organId");
		String cityId = request.getParameter("cityId");
		String city = request.getParameter("city");
		String come= request.getParameter("come");
		String distance=request.getParameter("distance");
		request.setAttribute("distance", distance);
		request.setAttribute("come", come);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("organId", organId);
		request.setAttribute("type", type);
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		request.setAttribute("userId", userSession.get_id());
		
		if(weStaffCase != null) {
			Staff staff = weUserService.queryStaffById(weStaffCase.getStaffId());
			mv.addObject("weStaffCase", weStaffCase);
			mv.addObject("staff", staff);
			if(!StringUtils.isEmpty(staff.get_id())||(staff.getWeOrganIds()!=null&&staff.getWeOrganIds().size()>=0)){
				mv.addObject("appointCase", "ok");
			}
		}
		
		mv.setViewName("user/userhome/appoint_info");
		return mv;
	}
	/**
	 * 去选择预约时间和 店铺
	 * @param staffId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toAppointTime", method = RequestMethod.GET)
	public ModelAndView toAppointTime(@RequestParam(required = true)String staffId,@RequestParam(required = true)String caseId,HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		List<WeekDay> weekDays=weCommonService.queryScheduleTitle();
		int day=weekDays.get(0).getDaytime();
		int day7=weekDays.get(6).getDaytime();
		List<WeStaffCalendar> weStaffCalendars= staffService.findWeStaffSchedule(staffId,day,day7);
		
		if (weStaffCalendars !=null) {
			for(WeStaffCalendar weStaffCalendar :weStaffCalendars) {
				Organ organ=staffService.getOrgan(weStaffCalendar.getOrganId());
				if(organ!=null){
				weStaffCalendar.setOrganName(organ.getName());
				}else{
					weStaffCalendar.setOrganName("任性猫");
				}
				String monthDay = String.valueOf(weStaffCalendar.getDay()).substring(4);
				weStaffCalendar.setMonthDay(monthDay);
				String week = WeekDay.getWeekByDate(weStaffCalendar.getDay(), day);
				weStaffCalendar.setWeek(week);
				
			}
		}
	
		String type = request.getParameter("type");
		String organId = request.getParameter("organId");
		String cityId = request.getParameter("cityId");
		String city = request.getParameter("city");
		String come= request.getParameter("come");
		String distance=request.getParameter("distance");
		request.setAttribute("distance", distance);
		request.setAttribute("come", come);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("organId", organId);
		request.setAttribute("type", type);
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		request.setAttribute("userId", userSession.get_id());
		
		request.setAttribute("staffId", staffId);
		request.setAttribute("weStaffCalendars", weStaffCalendars);
		request.setAttribute("day", day);
		request.setAttribute("caseId", caseId);
		mv.setViewName("user/userhome/appoint_time");
		return mv;
	}
	
	/**
	 * 去选择预约时间和 店铺
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toAppointTimeByStaff", method = RequestMethod.POST)
	public ModelAndView toAppointTimeByStaff(HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String staffId=request.getParameter("staffId");
		String type=request.getParameter("type");
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
		request.setAttribute("type", type);
		request.setAttribute("weStaffCalendars", weStaffCalendars);
		request.setAttribute("day", day);
		mv.setViewName("user/userhome/appoint_time");
		return mv;
	}
	
	/**
	 * 选择好时间以后 返回预约信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	//reAppoint
	@RequestMapping(value ="/reAppointStaff", method = RequestMethod.POST)
	public ModelAndView reAppointStaff(HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String type=request.getParameter("type");
		String day=request.getParameter("day");
		String staffId=request.getParameter("staffId");
		String organId=request.getParameter("organId");
		String time1=request.getParameter("time1");
		String organName = request.getParameter("organName");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(day+" "+time1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = sdf.format(date);
		if(staffId != null && !staffId.equals("")) {
			Staff staff = weUserService.queryStaffById(staffId);
			mv.addObject("staff", staff);
		}
		mv.addObject("date", dateStr);
		mv.addObject("organName", organName);
		mv.addObject("organId", organId);
		mv.addObject("type", type);
		mv.setViewName("user/userhome/appoint_detail");
		return mv;
	}
	/**
	 * 选择好了类型以后返回到预约界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/reAppointByType", method = RequestMethod.POST) 
	public ModelAndView reAppointByType(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String appointTime = request.getParameter("appointTime");
		String organName = request.getParameter("organName");
		String type = request.getParameter("type");
		String organId = request.getParameter("organId");
		String staffId = request.getParameter("staffId");
		Staff staff = weUserService.queryStaffById(staffId);
		mv.addObject("staff", staff);
		mv.addObject("date", appointTime);
		mv.addObject("organName", organName);
		mv.addObject("organId", organId);
		mv.addObject("type", type);
		mv.setViewName("user/userhome/appoint_detail");
		return mv;
	}
	/**
	 * 去选择服务类型
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toSelectType", method = RequestMethod.POST) 
	public ModelAndView toSelectType(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String appointTime = request.getParameter("appointTime");
		String organName = request.getParameter("organName");
		String organId = request.getParameter("organId");
		String staffId = request.getParameter("staffId");
		/*List<String> types = new ArrayList<String>();
		List<WeStaffCase> weStaffCases = weUserService.findWeStaffCases(staffId);
		for (WeStaffCase weStaffCase:weStaffCases) {
			String type = weStaffCase.getType();
			if(!types.contains(type)) {
				types.add(type);
			}
		}*/
		List<Bigsort> codes = weUserService.findBigSortByOrganId(organId);
		mv.addObject("appointTime", appointTime);
		mv.addObject("organName", organName);
		mv.addObject("organId", organId);
		mv.addObject("staffId", staffId);
		mv.addObject("codes", codes);
		mv.setViewName("user/userhome/select_type");
		return mv;
	}
	/**
	 * 用户  -->收藏案例
	 * @param caseId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/saveCollect", method = RequestMethod.POST)
	@ResponseBody
	public String saveCollect(@RequestParam String caseId,HttpServletRequest request, 
			HttpServletResponse response) {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			weCommonService.saveUserFavor(caseId, "1", user.get_id(), "1");
			WeStaffCase weStaffCase = weCommonService.queryWeStaffCaseById(caseId);
			return weStaffCase.getFollowCount()+"";
	}
	/**
	 * 取消收藏
	 * @param caseId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/cancelCollect", method = RequestMethod.POST)
	@ResponseBody
	public String cancelCollect(@RequestParam String caseId,HttpServletRequest request, 
		HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		weCommonService.saveUserFavor(caseId, "1", user.get_id(), "2");
		WeStaffCase weStaffCase = weCommonService.queryWeStaffCaseById(caseId);
		return weStaffCase.getFollowCount()+"";
	}
	
	/**
	 * 用户  -->收藏技师
	 * @param staffId 技师的Id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/collectStaff", method = RequestMethod.POST)
	@ResponseBody
	public String collectStaff(@RequestParam String staffId,HttpServletRequest request, 
			HttpServletResponse response) {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			weCommonService.saveUserFavor(staffId, "2", user.get_id(), "1");
			Staff staff = weUserService.queryStaffById(staffId);
			return staff.getFollowCount()+"";
	}
	/**
	 * 取消收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/canColStaff", method = RequestMethod.POST)
	@ResponseBody
	public String canColStaff(@RequestParam String staffId,HttpServletRequest request, 
		HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		weCommonService.saveUserFavor(staffId, "2", user.get_id(), "2");
		Staff staff = weUserService.queryStaffById(staffId);
		return staff.getFollowCount()+"";
	}
	
	/**
	 * 查询某天的日常安排
	 * @param request
	 * @param response
	 */
	@RequestMapping(value ="/queyTime", method = RequestMethod.POST)
	@ResponseBody
	public WeStaffCalendar queyTime(HttpServletRequest request, HttpServletResponse response) {
		String day = request.getParameter("day");
		String staffId = request.getParameter("staffId");
		String organId = request.getParameter("organId");
		if(day != null &&!day.equals("") && staffId != null &&!staffId.equals("")&& organId != null && !organId.equals("")) {
			WeStaffCalendar weStaffCalendar = staffService.findScheduleTime(day, staffId, organId);
			return weStaffCalendar;
		} 
		return null;
	}
	/**
	 * 选择好时间以后 返回预约信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	//reAppoint
	@RequestMapping(value ="/reAppoint", method = RequestMethod.POST)
	public ModelAndView toAppointTime(HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		String organId = request.getParameter("organId");
		String cityId = request.getParameter("cityId");
		String city = request.getParameter("city");
		String come= request.getParameter("come");
		String distance=request.getParameter("distance");
		request.setAttribute("distance", distance);
		request.setAttribute("come", come);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("organId", organId);
		request.setAttribute("type", type);
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		request.setAttribute("userId", userSession.get_id());
		String day=request.getParameter("day");
		String time1=request.getParameter("time1");
		String caseId=request.getParameter("caseId");
		String organName = request.getParameter("organName");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(day+" "+time1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = sdf.format(date);
		WeStaffCase weStaffCase = weUserService.queryCaseById(caseId);
		mv.addObject("weStaffCase", weStaffCase);
		if(weStaffCase != null) {
			Staff staff = weUserService.queryStaffById(weStaffCase.getStaffId());
			mv.addObject("weStaffCase", weStaffCase);
			mv.addObject("staff", staff);
		}
		mv.addObject("date", dateStr);
		mv.addObject("organName", organName);
		mv.addObject("organId", organId);
		mv.setViewName("user/userhome/appoint_info");
		return mv;
	}
	
	/**
	 *  去美丽之星界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toBeautyStar", method = RequestMethod.GET)
	public ModelAndView beautyStar(HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String sort = request.getParameter("sort");
		/*获取上次的地理位置*/
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		//findOldLocation(longitude,latitude,request);
		mv.addObject("sort", sort);
		mv.setViewName("user/userhome/beautiful_star");
		return mv;
	}
	/**
	 * 获取技师列表
	 * @param request
	 * @param response
	 * @return  
	 * @throws Exception
	 */
	@RequestMapping("/staffList")
	@ResponseBody
	public FlipInfo<Staff> staffList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		FlipInfo<Staff> fpi = new FlipInfo<Staff>();
	    int page = Integer.parseInt(request.getParameter("page"));
		fpi.setPage(page);
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String maxDistance = request.getParameter("maxDistance");
		String sex = request.getParameter("sex");
		String band = request.getParameter("band");
		String clickCount = request.getParameter("clickCount");
		int count = Integer.parseInt(clickCount)%2;
		String distance = request.getParameter("distance");
		String price = request.getParameter("startPrice");
		String followCount = request.getParameter("followCount");
		Query query = new Query();
		if(longitude!= null&&!longitude.equals("")&&latitude!= null && !latitude.equals("")) {
			double longitude1 = Double.parseDouble(longitude);
			double latitude1 = Double.parseDouble(latitude);
			double maxDistance1 = Double.parseDouble(maxDistance);
			if(sex!= null&& !sex.equals("") && !sex.equals("不限")) {
				query.addCriteria(Criteria.where("sex").is(sex));
			}
			if(band!= null&&!band.equals("")&& !band.equals("不限")) {
				int flag = 0;
				if(band.equals("一级"))
					flag=1;
				if(band.equals("二级"))
					flag=2;
				if(band.equals("三级"))
					flag=3;
				if(band.equals("四级"))
					flag=4;
				if(band.equals("五级"))
					flag=5;
				query.addCriteria(Criteria.where("level").is(flag));
			}
			if(distance!= null&& !distance.equals("")) {
				fpi = weUserService.queryStaffByUser(query,longitude1, latitude1, maxDistance1,fpi);
       		} 
			
 			if(price!= null&&!price.equals("") && count == 0) {
 				fpi.setSortField("startPrice");   //价格排序
 				fpi.setSortOrder("ASC");
 				fpi = weUserService.queryStaffALL(query, fpi,longitude1, latitude1);
    		} else if(price!= null&&!price.equals("") && count == 1) {
 				fpi.setSortField("startPrice");   //价格排序
 				fpi.setSortOrder("DESC");
 				fpi = weUserService.queryStaffALL(query, fpi,longitude1, latitude1);
        	}
			if(followCount!= null&&!followCount.equals("")) {
				fpi.setSortField("followCount"); //热度排序
				fpi.setSortOrder("DESC");
				fpi = weUserService.queryStaffALL(query,fpi,longitude1, latitude1);
			}
			return fpi;
		} else {
			throw new RuntimeException("不能定位当前的位置");
		}
	}
	/**
	 * 获取用户的上次位置
	 * @param longitude
	 * @param latitude
	 * @param request
	 */
	private void findOldLocation(String longitude, String latitude, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		if (StringUtils.isEmpty(longitude) || longitude == "0" || StringUtils.isEmpty(latitude) || latitude == "0") {
			longitude = user.getGpsPoint().getLongitude() + "";
			latitude = user.getGpsPoint().getLatitude() + "";
			session.setAttribute("longitude", Double.parseDouble(longitude));
			session.setAttribute("latitude", Double.parseDouble(latitude));
		}
	}

	/**
	 *  去美丽之星详情界面
	 * @param request
	 * @param response
	 */
	@RequestMapping(value ="/staffDetail", method = RequestMethod.GET)
	public ModelAndView beautyStar(@RequestParam(required =true)String staffId,
			HttpServletRequest request, 
			HttpServletResponse response) {
		System.out.println("技师详情de User_______________________________________");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		System.out.println("技师详情de userSession"+userSession);
//		String st = (String) session.getAttribute("longitude");
		double longitude = Double.parseDouble(String.valueOf(session.getAttribute("longitude")));
		double latitude = Double.parseDouble(String.valueOf(session.getAttribute("latitude")));
		String type = request.getParameter("type");
		String organId = request.getParameter("organId");
		String cityId = request.getParameter("cityId");
		String city = request.getParameter("city");
		String come= request.getParameter("come");
		String distance=request.getParameter("distance");
		request.setAttribute("distance", distance);
		request.setAttribute("come", come);
		if (city !=null && !"".equals(city)) {
			try {
				city=URLDecoder.decode(city,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		User user = weUserService.queryUserById(userSession.get_id());
		System.out.println("技师详情de User_______________________________________"+user);
		List<String> staffIds = new ArrayList<>();
		if(user != null){
			staffIds = user.getFavorStaffIds();
		}

		if(staffIds !=null){
			if(staffIds.contains(staffId)) {
				mv.addObject("collect", "取消收藏");
			} else {
				mv.addObject("collect", "收藏");
			}
	    } else {
	    	mv.addObject("collect", "收藏");
	    }
		Staff staff = weUserService.queryStaffByIdAndDistance(staffId, longitude, latitude);
		boolean isService = false;
		if(staff != null){
			if(staff.getEvaluateCount() == null || staff.getEvaluateCount() ==0) {
				staff.setEvaluateCount(1);
			}
			int temp = (int) Math.ceil(staff.getZanCount()/(staff.getEvaluateCount()*1.0));
			mv.addObject("flowers", temp);
			staffMyService.setWeStaffCalendar(staffId);
			isService  = weUserService.findStaffServiceTime(staff.get_id());
		}
		System.out.println("技师详情de User_______________________________________"+staff);

		/*boolean isHaveWeStaffCase = weUserService.isHaveWeStaffCase(staffId);
	    mv.addObject("isHaveWeStaffCase", isHaveWeStaffCase);*/
	    mv.addObject("isService", isService);
		mv.addObject("staff", staff);
		mv.addObject("type", type);
		mv.addObject("organId", organId);
		mv.addObject("cityId", cityId);
		mv.addObject("city", city);
		mv.setViewName("user/userhome/staff_detail");
		return mv;
	}
	
	/**
	 *  去美丽之星详情界面2
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/staffDetail2", method = RequestMethod.GET)
	public ModelAndView beautyStar2(@RequestParam(required =true)String staffId,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		String organId = request.getParameter("organId");
		String cityId = request.getParameter("cityId");
		String city = request.getParameter("city");
		String come= request.getParameter("come");
		String distance=request.getParameter("distance");
		request.setAttribute("distance", distance);
		request.setAttribute("come", come);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("organId", organId);
		request.setAttribute("userId", userSession.get_id());
		double longitude = Double.parseDouble(String.valueOf(session.getAttribute("longitude")));
		double latitude = Double.parseDouble(String.valueOf(session.getAttribute("latitude")));
		User user = weUserService.queryUserById(userSession.get_id());
		List<String> staffIds = user.getFavorStaffIds();
		if(staffIds != null) {
			if(staffIds.contains(staffId)) {
				mv.addObject("collect", "取消收藏");
			} else {
				mv.addObject("collect", "收藏");
			}
		} else {
			mv.addObject("collect", "收藏");
		}
		Staff staff = weUserService.queryStaffByIdAndDistance(staffId, longitude, latitude);
		if(staff.getEvaluateCount() == null || staff.getEvaluateCount() ==0) {
			staff.setEvaluateCount(1);
		}
		staffMyService.setWeStaffCalendar(staffId);
		int temp = (int) Math.ceil(staff.getZanCount()/(staff.getEvaluateCount()*1.0));
		mv.addObject("flowers", temp);
		boolean isService  = weUserService.findStaffServiceTime(staff.get_id());
		/*boolean isHaveWeStaffCase = weUserService.isHaveWeStaffCase(staffId);
	    mv.addObject("isHaveWeStaffCase", isHaveWeStaffCase);*/
	    mv.addObject("isService", isService);
		mv.addObject("staff", staff);
		mv.setViewName("user/userhome/staff_detail2");
		return mv;
	}
	/**
	 * 去显示店铺位置界面
	 * @param organId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/goOrganMap", method = RequestMethod.GET)
	public ModelAndView goOrganMap(@RequestParam(required =true) String organId,HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		double longitude =Double.parseDouble(session.getAttribute("longitude").toString());
		double latitude =Double.parseDouble(session.getAttribute("latitude").toString());
		Organ organ = weUserService.findOrganById(organId);
		mv.addObject("organ", organ);
		mv.addObject("longitude", longitude);
		mv.addObject("latitude", latitude);
		mv.setViewName("user/userhome/store_address");
		return mv;
	}
	/**
	 * 查询案例
	 * @param staffId
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/queryCases", method = RequestMethod.POST)
	@ResponseBody
	public FlipInfo<WeStaffCase> queryCases(@RequestParam(required =true)String staffId,@RequestParam(required =true)int page,HttpServletRequest request, 
			HttpServletResponse response) {
		FlipInfo<WeStaffCase> weStaffCases = new FlipInfo<WeStaffCase>();
		weStaffCases.setPage(page);
		weStaffCases = weUserService.queryCaseByStaffId(staffId,weStaffCases);
		return weStaffCases;
	}
	//TODO 预约案例
	/**
	 * 提交预约案例和技师
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/commitApoint",method= RequestMethod.POST)
	public void commitApoint(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			String time="";
			Organ organ = new Organ();
			String staffId =  request.getParameter("staffId");
			String organId =  request.getParameter("organId");
			String appointTime =  request.getParameter("appointTime");
			String caseId =  request.getParameter("caseId");
			WeStaffCase weStaffCase = weUserService.queryCaseById(caseId);
			String title = weStaffCase.getType();
			double orderPrice = Double.parseDouble(request.getParameter("orderPrice"));
			User userSession = (User)session.getAttribute("user");
			User user = weUserService.queryUserById(userSession.get_id());
			String phone =user.getPhone();
			String userId = userSession.get_id();
			if(phone !=null && !phone.equals("")) {
				WeOrganOrder weOrganOrder = new WeOrganOrder();
			    weOrganOrder.setIdIfNew();
			    if(staffId != null && !staffId.equals("")) {
			    	weOrganOrder.setStaffId(staffId);
			    }
			    if(organId != null && !organId.equals("")) {
			    	weOrganOrder.setOrganId(organId);
			    	organ=userMyService.getOrganById(organId);
			    }
			    if(appointTime != null && !appointTime.equals("")){
			    	weOrganOrder.setOrderTime(appointTime);
			    	try {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(appointTime);
						time=new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    }
			    if(caseId != null && !caseId.equals("")) {
			    	weOrganOrder.setOrderService(caseId);
			    }
			    if(userId != null && !userId.equals("")) {
			    	weOrganOrder.setUserId(userId);
			    }
			    if(title != null && !title.equals("")){
			    	weOrganOrder.setTitle(title);
			    }
			    weOrganOrder.setOrderPrice(orderPrice);
			    weOrganOrder.setState(1);
			    weOrganOrder.setType(2);
			    weOrganOrder.setNewCreate();
		    	weUserService.addAppoint(weOrganOrder);
		    	//预约成功以后给客户的消息
		    	Map<String,Object> userInfo = (Map)session.getAttribute("userInfo");
		    	WxTemplate tempToUser = redisService.getWxTemplate("喵，请耐心等待，您的预约正在确认中。", weOrganOrder.get_id(),weOrganOrder.getTitle() , time, organ.getAddress(),weOrganOrder.getOrderPrice()+"元起", "喵，您的预约信息已成功推送给技师，请等待技师确认。");
		    	redisService.send_template_message(userInfo.get("openid").toString(), "user", Configure.USER_APPOINT_SUCCESS, tempToUser);
		    	
		    	// 预约成功以后给技师的消息
		    	Account account = accountService.getAccountByEntityID(staffId, "staff");
				//技师通知
		        WxTemplate tempToStaff = redisService.getWxTemplate("喵，有新的客户预约啦~~",user.getNick(),user.getPhone(), appointTime, weStaffCase.getType(),null, "喵小妹提醒您：请根据您的繁忙程度进行确认。");
		       
		        redisService.send_template_urlmessage(Configure.DOMAIN_URL+"/mrmf/w/staffMy/messageToOrderDetail?staffId="+staffId+"&orderId="+weOrganOrder.get_id(),account.getAccountName(),"staff", Configure.APPOINT_STAFF_REMIND, tempToStaff);
		    	System.out.println(Configure.DOMAIN_URL+"/mrmf/w/staffMy/messageToOrderDetail?staffId="+staffId+"&orderId="+weOrganOrder.get_id());
		        response.getWriter().print(weOrganOrder.get_id());
			} else {
				response.getWriter().print("nophone");
			}
		    
		} catch (Exception e) {
			try {
				
				response.getWriter().print("failure");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	@RequestMapping(value="/toBindPhone",method= RequestMethod.GET)
	public ModelAndView toBindPhone(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("user/userlogin/binderPhone");
	}
	//TODO 预约技师
	/**
	 * 提交预约技师
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/commitApStaff",method= RequestMethod.POST)
	public void commitApStaff(HttpServletRequest request,HttpServletResponse response) {
		 try {
			String staffId =  request.getParameter("staffId");
			String organId =  request.getParameter("organId");
			String appointTime =  request.getParameter("appointTime");
			String type =  request.getParameter("type");
			double orderPrice = Double.parseDouble(request.getParameter("orderPrice"));
			HttpSession session = request.getSession();
			/*String ctxPath = String.valueOf(session.getAttribute("ctxPath"));*/
			User userSession = (User)session.getAttribute("user");
			String userId = userSession.get_id();
			User user = weUserService.queryUserById(userId);
			String phone =user.getPhone();
			if(phone !=null && !phone.equals("")) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(appointTime);
				String time=new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm").format(date);
			    WeOrganOrder weOrganOrder = new WeOrganOrder();
			    weOrganOrder.setNewId();
			    weOrganOrder.setNewCreate();
			    if(staffId != null && !staffId.equals("")) {
			    	weOrganOrder.setStaffId(staffId);
			    }
			    if(organId != null && !organId.equals("")) {
			    	weOrganOrder.setOrganId(organId);
			    }
			    if(appointTime != null && !appointTime.equals("")){
			    	weOrganOrder.setOrderTime(appointTime);
			    }
			    if(type != null && !type.equals("")) {
			    	weOrganOrder.setTitle(type);
			    }
			    if(userId != null && !userId.equals("")) {
			    	weOrganOrder.setUserId(userId);
			    }
			    weOrganOrder.setOrderPrice(orderPrice);
			    Organ organ=userMyService.getOrganById(organId);
			    weOrganOrder.setState(1);
			    weOrganOrder.setType(2);
			    ReturnStatus status=weUserService.verifyOrder(weOrganOrder);
			    if (status.isSuccess()) {
			    	weUserService.addAppoint(weOrganOrder);
			    	//预约成功以后给客户的消息
			    	Map<String,Object> userInfo = (Map)session.getAttribute("userInfo");
			    	WxTemplate tempToUser = redisService.getWxTemplate("喵，请耐心等待，您的预约正在确认中。", weOrganOrder.get_id(),weOrganOrder.getTitle() , time, organ.getAddress(),weOrganOrder.getOrderPrice()+"元起", "喵，您的预约信息已成功推送给技师，请等待技师确认。");
			    	redisService.send_template_message(userInfo.get("openid").toString(), "user", Configure.USER_APPOINT_SUCCESS, tempToUser);
			    	// 预约成功以后给技师的消息
			    	Account account = accountService.getAccountByEntityID(staffId, "staff");
			    	//技师通知
			    	WxTemplate tempToStaff = redisService.getWxTemplate("喵，有新的客户预约啦~~",user.getNick(),user.getPhone(), appointTime,type,null, "喵小妹提醒您：请根据您的繁忙程度进行确认。");
			    	
			    	redisService.send_template_urlmessage(Configure.DOMAIN_URL+"/mrmf/w/staffMy/messageToOrderDetail?staffId="+staffId+"&orderId="+weOrganOrder.get_id(),account.getAccountName(),"staff", Configure.APPOINT_STAFF_REMIND, tempToStaff);
			    	System.out.println(Configure.DOMAIN_URL+"/mrmf/w/staffMy/messageToOrderDetail?staffId="+staffId+"&orderId="+weOrganOrder.get_id());
			    	response.getWriter().print(weOrganOrder.get_id()); 
				}else {
					response.getWriter().print("repeat");
				}
		    } else {
		    	response.getWriter().print("nophone"); 
		    }
		} catch (Exception e) {
			try {
				response.getWriter().print("failure");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	

	/**
	 * 
	 * 微信访问主页的控制类
	 */
	@RequestMapping(value = "/userIndex.do", method = RequestMethod.GET)
	public ModelAndView userIndex(String code,String state,HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = null;
		try {
			mv = new ModelAndView();
			HttpSession session=request.getSession(true);
			Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
			logger.info("已经存在userInfo:"+userInfo);
			if(userInfo==null){
                logger.info("不存在userInfo:"+userInfo);
               // Map<String,Object> user_token=wxgetInfo.getAccess_token(code,"user");
             //   userInfo=wxgetInfo.getUserInfo(user_token);
                logger.info("userInfo:"+userInfo);
                ReturnStatus status=null;
                String oppenid="";
                String unionid="";
                session.setAttribute("userInfo", userInfo);
                if(userInfo!=null)
                {
                    if(userInfo.get("openid")!=null){
                        oppenid=userInfo.get("openid").toString();
                        session.setAttribute("openid", oppenid);
                    }
                    if(userInfo.get("unionid")!=null){
                        unionid=userInfo.get("unionid").toString();
                    }
                    status=weCommonService.isExist(oppenid, unionid, "user");
                }else{
					status=new ReturnStatus(false,"该微信号第一次关注");
				}
                if(status.isSuccess()){
                    System.out.println("0000000000state______________________________________________"+state);
                    User user=weUserService.queryUserByOpenId(oppenid);
                    GpsPoint gpsPoint=new GpsPoint();
                    double longitude;
                    double latitude;
                    Object lo=session.getAttribute("longitude");
                    Object la=session.getAttribute("latitude");
                    if(lo!=null){
                        longitude=Double.parseDouble(lo.toString());
                        gpsPoint.setLongitude(longitude);
                    }
                    if(la!=null){
                        latitude=Double.parseDouble(la.toString());
                        gpsPoint.setLongitude(latitude);
                    }
                    request.setAttribute("user", user);
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.get_id());
                    session.setAttribute("city", "北京市");
                    session.setAttribute("cityId", "1667920738524089172");
                    userInfo.put("gpsPoint", gpsPoint);
                    if(user!=null&&"".equals(user.getAvatar())&&userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")){
                        InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
                        String imgName = weCommonService.downImg(in,request);
                        String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
                        File imgFile=new File(url);
                        InputStream inFile=new FileInputStream(imgFile);
                        String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
                        String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
                        in.close();
                        inFile.close();
                        userMyService.updateImg(user.get_id(),ossId);
                    }
                    userMyService.updateUser(user.get_id(),userInfo);
                }else{
                    GpsPoint gpsPoint=new GpsPoint();
                    double longitude;
                    double latitude;
                    session.setAttribute("city", "北京市");
                    session.setAttribute("cityId", "1667920738524089172");
                    Object lo=session.getAttribute("longitude");
                    Object la=session.getAttribute("latitude");
                    if(lo!=null){
                        longitude=Double.parseDouble(lo.toString());
                        gpsPoint.setLongitude(longitude);
                    }
                    if(la!=null){
                        latitude=Double.parseDouble(la.toString());
                        gpsPoint.setLongitude(latitude);
                    }
                    userInfo.put("gpsPoint", gpsPoint);
                    if(userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")) {
                        InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
                        String imgName = weCommonService.downImg(in,request);
                        String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
                        File imgFile=new File(url);
                        InputStream inFile=new FileInputStream(imgFile);
                        String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
                        String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
                        in.close();
                        inFile.close();
                        userInfo.put("headimgurl", ossId);
                    }
                    if(!StringUtils.isEmpty(state) && !state.equals("123")) {
                        String accountID = "";
                        String accountType = "";
                        String[] states = state.split("_");
                        if (states != null && states.length > 1){
                            accountID = states[1];
                            accountType = states[0];
                        }
                        userInfo.put("accountType",accountType);
                        userInfo.put("invitor", accountID);
                        userInfo.put("inviteDate", new Date());
                    }
                    status=weCommonService.saveUser(userInfo);
                    if(status.isSuccess()){
                        User user=weUserService.queryUserByOpenId(oppenid);
                        logger.info("最终的user:"+user);
                        request.setAttribute("user", user);
                        session.setAttribute("user", user);
                        session.setAttribute("userId", user.get_id());
                        couponGrantService.grantCouponByuserUuidAndType(user.get_id(),"关注",-1,"");
                        userMyService.updateUser(user.get_id(),userInfo);
                    }
                }
            }else {
                String oppenid = "";
                if(userInfo.get("openid")!=null){
                    oppenid=userInfo.get("openid").toString();
                    session.setAttribute("openid", oppenid);
                }
                User user = weUserService.queryUserByOpenId(oppenid);
                logger.info("最终的user:" + user);
                request.setAttribute("user", user);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.get_id());
                userMyService.updateUser(user.get_id(),userInfo);
            }
			logger.info("最终的userInfo:"+userInfo);
			String oppenid=userInfo.get("openid").toString();
			User user = weUserService.queryUserByOpenId(oppenid);
			userMyService.updateUser(user.get_id(),userInfo);
			Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");
			mv.addObject("sign", sign);
			List<WeCarousel> weCarousels = weUserService.findCarousels();
			mv.addObject("weCarousels", weCarousels);
			mv.setViewName("user/userhome/home_page");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 *
	 * APP  用户注册
	 */
	@RequestMapping(value = "/userRegist.do", method = RequestMethod.POST)
	public  Map<String, Object> userRegist(String phone,String code,String mail,String password,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		mail="11";
		try {

			HttpSession session=request.getSession(true);

		//	Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
			Map<String,Object> userInfo=new HashMap<String, Object>();;
	//		logger.info("已经存在userInfo:"+userInfo);
			userInfo.put("oppenid",phone);
			userInfo.put("unionid","asdf");
			userInfo.put("password",password);




				// Map<String,Object> user_token=wxgetInfo.getAccess_token(code,"user");
				//   userInfo=wxgetInfo.getUserInfo(user_token);
				logger.info("userInfo:"+userInfo);
				ReturnStatus status=null;
				String oppenid="";
				String unionid="";






					if(userInfo.get("openid")!=null){
						oppenid=userInfo.get("openid").toString();
						session.setAttribute("openid", oppenid);
					}
					if(userInfo.get("unionid")!=null){
						unionid=userInfo.get("unionid").toString();
					}


					status=weCommonService.isExist(oppenid, unionid, "user");


  				if(accountService.verify(phone, code).isSuccess()) {

			} else {

					map.put("code","1");
					map.put("message","验证码有误");
					map.put("data","");
					return map;
			}


				if(status.isSuccess()||userMyService.isHaveUserPhone(phone)){
				//用户存在直接返回已经存在
					map.put("code","1");
					map.put("message","用户已经存在");
					map.put("data","");
					return map;
				}else{

				//用户不存在开始注册
					GpsPoint gpsPoint=new GpsPoint();
					double longitude;
					double latitude;
					session.setAttribute("city", "北京市");
					session.setAttribute("cityId", "1667920738524089172");
					Object lo=session.getAttribute("longitude");
					Object la=session.getAttribute("latitude");
					if(lo!=null){
						longitude=Double.parseDouble(lo.toString());
						gpsPoint.setLongitude(longitude);
					}
					if(la!=null){
						latitude=Double.parseDouble(la.toString());
						gpsPoint.setLongitude(latitude);
					}
					userInfo.put("gpsPoint", gpsPoint);
					if(userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")) {
						InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
						String imgName = weCommonService.downImg(in,request);
						String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
						File imgFile=new File(url);
						InputStream inFile=new FileInputStream(imgFile);
						String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
						String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
						in.close();
						inFile.close();
						userInfo.put("headimgurl", ossId);
					}
				/*	if(!StringUtils.isEmpty(state) && !state.equals("123")) {
						String accountID = "";
						String accountType = "";
						String[] states = state.split("_");
						if (states != null && states.length > 1){
							accountID = states[1];
							accountType = states[0];
						}
						userInfo.put("accountType",accountType);
						userInfo.put("invitor", accountID);

					}*/
					userInfo.put("inviteDate", new Date());
					status=weCommonService.saveUser(userInfo);
					if(status.isSuccess()){
						User user=weUserService.queryUserByOpenId(oppenid);
						logger.info("最终的user:"+user);
						request.setAttribute("user", user);
						session.setAttribute("user", user);
						session.setAttribute("userId", user.get_id());
						couponGrantService.grantCouponByuserUuidAndType(user.get_id(),"关注",-1,"");
						userMyService.updateUser(user.get_id(),userInfo);

						map.put("code","0");
						map.put("message","用户注冊成功");
						map.put("data","");
					}


				}

			logger.info("最终的userInfo:"+userInfo);
			 oppenid=userInfo.get("openid").toString();
			User user = weUserService.queryUserByOpenId(oppenid);
			userMyService.updateUser(user.get_id(),userInfo);
		//	Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");

		//	List<WeCarousel> weCarousels = weUserService.findCarousels();
			session.setAttribute("userInfo", userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	/**
	 *
	 * 微信访问主页的控制类
	 */
	@RequestMapping(value = "/userLogin.do", method = RequestMethod.GET)
	public ModelAndView userLogin(String code,String state,HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = null;
		try {
			mv = new ModelAndView();
			HttpSession session=request.getSession(true);
			Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
			logger.info("已经存在userInfo:"+userInfo);
			if(userInfo==null){
				logger.info("不存在userInfo:"+userInfo);
				// Map<String,Object> user_token=wxgetInfo.getAccess_token(code,"user");
				//   userInfo=wxgetInfo.getUserInfo(user_token);
				logger.info("userInfo:"+userInfo);
				ReturnStatus status=null;
				String oppenid="";
				String unionid="";
				session.setAttribute("userInfo", userInfo);
				if(userInfo!=null)
				{
					if(userInfo.get("openid")!=null){
						oppenid=userInfo.get("openid").toString();
						session.setAttribute("openid", oppenid);
					}
					if(userInfo.get("unionid")!=null){
						unionid=userInfo.get("unionid").toString();
					}
					status=weCommonService.isExist(oppenid, unionid, "user");
				}else{
					status=new ReturnStatus(false,"该微信号第一次关注");
				}
				if(status.isSuccess()){
					System.out.println("0000000000state______________________________________________"+state);
					User user=weUserService.queryUserByOpenId(oppenid);
					GpsPoint gpsPoint=new GpsPoint();
					double longitude;
					double latitude;
					Object lo=session.getAttribute("longitude");
					Object la=session.getAttribute("latitude");
					if(lo!=null){
						longitude=Double.parseDouble(lo.toString());
						gpsPoint.setLongitude(longitude);
					}
					if(la!=null){
						latitude=Double.parseDouble(la.toString());
						gpsPoint.setLongitude(latitude);
					}
					request.setAttribute("user", user);
					session.setAttribute("user", user);
					session.setAttribute("userId", user.get_id());
					session.setAttribute("city", "北京市");
					session.setAttribute("cityId", "1667920738524089172");
					userInfo.put("gpsPoint", gpsPoint);
					if(user!=null&&"".equals(user.getAvatar())&&userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")){
						InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
						String imgName = weCommonService.downImg(in,request);
						String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
						File imgFile=new File(url);
						InputStream inFile=new FileInputStream(imgFile);
						String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
						String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
						in.close();
						inFile.close();
						userMyService.updateImg(user.get_id(),ossId);
					}
					userMyService.updateUser(user.get_id(),userInfo);
				}else{
					GpsPoint gpsPoint=new GpsPoint();
					double longitude;
					double latitude;
					session.setAttribute("city", "北京市");
					session.setAttribute("cityId", "1667920738524089172");
					Object lo=session.getAttribute("longitude");
					Object la=session.getAttribute("latitude");
					if(lo!=null){
						longitude=Double.parseDouble(lo.toString());
						gpsPoint.setLongitude(longitude);
					}
					if(la!=null){
						latitude=Double.parseDouble(la.toString());
						gpsPoint.setLongitude(latitude);
					}
					userInfo.put("gpsPoint", gpsPoint);
					if(userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")) {
						InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
						String imgName = weCommonService.downImg(in,request);
						String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
						File imgFile=new File(url);
						InputStream inFile=new FileInputStream(imgFile);
						String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
						String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
						in.close();
						inFile.close();
						userInfo.put("headimgurl", ossId);
					}
					if(!StringUtils.isEmpty(state) && !state.equals("123")) {
						String accountID = "";
						String accountType = "";
						String[] states = state.split("_");
						if (states != null && states.length > 1){
							accountID = states[1];
							accountType = states[0];
						}
						userInfo.put("accountType",accountType);
						userInfo.put("invitor", accountID);
						userInfo.put("inviteDate", new Date());
					}
					status=weCommonService.saveUser(userInfo);
					if(status.isSuccess()){
						User user=weUserService.queryUserByOpenId(oppenid);
						logger.info("最终的user:"+user);
						request.setAttribute("user", user);
						session.setAttribute("user", user);
						session.setAttribute("userId", user.get_id());
						couponGrantService.grantCouponByuserUuidAndType(user.get_id(),"关注",-1,"");
						userMyService.updateUser(user.get_id(),userInfo);
					}
				}
			}else {
				String oppenid = "";
				if(userInfo.get("openid")!=null){
					oppenid=userInfo.get("openid").toString();
					session.setAttribute("openid", oppenid);
				}
				User user = weUserService.queryUserByOpenId(oppenid);
				logger.info("最终的user:" + user);
				request.setAttribute("user", user);
				session.setAttribute("user", user);
				session.setAttribute("userId", user.get_id());
				userMyService.updateUser(user.get_id(),userInfo);
			}
			logger.info("最终的userInfo:"+userInfo);
			String oppenid=userInfo.get("openid").toString();
			User user = weUserService.queryUserByOpenId(oppenid);
			userMyService.updateUser(user.get_id(),userInfo);
			Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");
			mv.addObject("sign", sign);
			List<WeCarousel> weCarousels = weUserService.findCarousels();
			mv.addObject("weCarousels", weCarousels);
			mv.setViewName("user/userhome/home_page");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}



	/**
	 * 发送验证码
	 * @param phone
	 * @param type
	 * @param code
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toverifycode")
	@ResponseBody
	public Map<String,String> toverifycode(String phone,String type,String code,HttpServletRequest request) throws Exception {
			HttpSession session=request.getSession(true);
			Map<String,Object> userInfo=(Map<String, Object>) session.getAttribute("userInfo");
			Map<String,String> msg=new HashMap<String,String>();
			ReturnStatus status=null;
			User user=null;
			if(userInfo!=null){
				String oppenid="";
				String unionid="";
				String logo="";
				String nickname="";
				GpsPoint gpsPoint=new GpsPoint();
				double longitude;
				double latitude;
				Object lo=session.getAttribute("longitude");
				Object la=session.getAttribute("latitude");
				if(lo!=null){
					longitude=Double.parseDouble(lo.toString());
					gpsPoint.setLongitude(longitude);
				}
				if(la!=null){
					latitude=Double.parseDouble(la.toString());
					gpsPoint.setLongitude(latitude);
				}
				if(userInfo.get("openid")!=null){
					oppenid=userInfo.get("openid").toString();
				}
				if(userInfo.get("unionid")!=null){
					unionid=userInfo.get("unionid").toString();
				}
				if(userInfo.get("headimgurl")!=null){
					logo=userInfo.get("headimgurl").toString();
				}
				if(userInfo.get("nickname")!=null){
					nickname=userInfo.get("nickname").toString();
				}
				
				status=weCommonService.verify(oppenid, unionid, phone, code, type, logo, nickname, gpsPoint);
				if(status.isSuccess()){
					msg.put("msg", "绑定成功");
					user=weUserService.queryUserByOpenId(oppenid);
					session.setAttribute("user", user);
					msg.put("userId", user.get_id());
				}else{
					msg.put("msg", status.getMessage());
				}
			}
		   return msg;
	}
	@RequestMapping(value ="/tofeedback")
	public ModelAndView tofeedback(String code,String state,
			HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session=request.getSession(true);//
		Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
		if(userInfo==null){
		Map<String,Object> user_token=wxgetInfo.getAccess_token(code,"user");
		userInfo=wxgetInfo.getUserInfo(user_token);
		ReturnStatus status=null;
		String oppenid="";
		String unionid="";
		session.setAttribute("userInfo", userInfo);
		if(userInfo!=null)
		{
			if(userInfo.get("openid")!=null){
				oppenid=userInfo.get("openid").toString();
			}
			if(userInfo.get("unionid")!=null){
				unionid=userInfo.get("unionid").toString();
			}
			status=weCommonService.isExist(oppenid, unionid, "user");
		}
		if(status.isSuccess()){
			User user=weUserService.queryUserByOpenId(oppenid);
			request.setAttribute("user", user);
			session.setAttribute("user", user);
			session.setAttribute("city", "北京市");
			session.setAttribute("cityId", "1667920738524089172");
			if(user!=null&&"".equals(user.getAvatar())&&userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")){
				InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
				String imgName = weCommonService.downImg(in,request);
				String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
				File imgFile=new File(url);
				InputStream inFile=new FileInputStream(imgFile);
				String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
				String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
				in.close();
				inFile.close();
				userMyService.updateImg(user.get_id(),ossId);
			}
		}else{
			GpsPoint gpsPoint=new GpsPoint();
			double longitude;
			double latitude;
			session.setAttribute("city", "北京市");
			session.setAttribute("cityId", "1667920738524089172");
			Object lo=session.getAttribute("longitude");
			Object la=session.getAttribute("latitude");
			if(lo!=null){
				longitude=Double.parseDouble(lo.toString());
				gpsPoint.setLongitude(longitude);
			}
			if(la!=null){
				latitude=Double.parseDouble(la.toString());
				gpsPoint.setLongitude(latitude);
			}
			userInfo.put("gpsPoint", gpsPoint);
			if(userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")) {
				InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
				String imgName = weCommonService.downImg(in,request);
				String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
				File imgFile=new File(url);
				InputStream inFile=new FileInputStream(imgFile);
				String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
				String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
				in.close();
				inFile.close();
				userInfo.put("headimgurl", ossId);
			}
			if(!StringUtils.isEmpty(state) && !state.equals("123")) {
				String accountID = "";
				String accountType = "";
				String[] states = state.split("_");
				System.out.println("state"+state);
				if (states != null && states.length > 1){
					accountID = states[1];
					accountType = states[0];
				}
				userInfo.put("accountType",accountType);
				userInfo.put("invitor", accountID);
				userInfo.put("inviteDate", new Date());
			}
			status=weCommonService.saveUser(userInfo);
			if(status.isSuccess()){
				User user=weUserService.queryUserByOpenId(oppenid);
				request.setAttribute("user", user);
				session.setAttribute("user", user);
			}
		}
		}
		mv.setViewName("user/usermy/feedback");
		return mv;
	}
	/**
	 *  去预约技师界面
	 * @param request
	 * @param response
	 * @return longitude=83.391786289962&latitude=34.9077741797592
	 */
	@RequestMapping(value ="/toAppointDetail", method = RequestMethod.GET)
	public ModelAndView toAppointDetail(@RequestParam(required =true)String staffId,
			HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Staff staff= weUserService.queryStaffById(staffId);
		mv.addObject("staff", staff);
		mv.setViewName("user/userhome/appoint_detail");
		return mv;
	}
	/**
	 *  去选择城市界面
	 * @param request
	 * @param response 
	 */
	@RequestMapping(value ="/toSelectCity", method = RequestMethod.GET)
	public ModelAndView toSelectCity(@RequestParam String city,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			city = URLDecoder.decode(city,"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("后台解码城市异常！！");
		}   
		mv.addObject("city", city);
		List<WeBCity> cityList=weUserService.cityList();
		mv.addObject("cityList", cityList);
		mv.setViewName("user/userhome/select_city");
		return mv;
	}
	
	/**
	 * 选择城市以后跳转到主页面
	 * @param city  选择的城市
	 * @return  要跳转的界面
	 */
	@RequestMapping(value ="/toHomePageByCity", method = RequestMethod.GET)
	public ModelAndView toHomePageByCity(@RequestParam String city,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			city = URLDecoder.decode(city,"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("后台解码城市异常！！");
		}   
		
		HttpSession session = request.getSession();
		session.setAttribute("city", city);
		session.setAttribute("seleltCity", true);
		String cityId=weOrganService.queryCityId(city);
		session.setAttribute("cityId", cityId);
		List<WeCarousel> weCarousels = weUserService.findCarousels();
		mv.addObject("weCarousels", weCarousels);
		mv.setViewName("user/userhome/home_page");
		Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");
		mv.addObject("sign", sign);
		mv.addObject("msg", "");
		return mv;
	}
	/**
	 * 上传图片
	 * @param file
	 * @param isPublic
	 * @return
	 */
	public String oSSupload(MultipartFile file, String isPublic) {
		try {

			// oos 云存储
			String etag = "";
			String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(file.getOriginalFilename());
			if (isPublic != null) {
				etag = OSSFileUtil.upload(file.getInputStream(), file.getSize(), ossId, OSSFileUtil.pubBucketName);
			} else {
				etag = OSSFileUtil.upload(file.getInputStream(), file.getSize(), ossId, OSSFileUtil.privBucketName);
			}

			if (etag != null) {
				return ossId;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过手机号获得验证码	getCodeByPhone
	 */
	@RequestMapping(value ="/getCodeByPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  getCodeByPhone(HttpServletRequest request, HttpServletResponse response) {
		//'phone':phone,'type':'user'
		String phone =String.valueOf(request.getParameter("phone"));
		String type = String.valueOf(request.getParameter("type"));
		Map<String, Object> map = new HashMap<String, Object>();
		if(weCommonService.verifycode(phone, type).isSuccess()) {
			map.put("code","0");
			map.put("message","获取验证码成功！");
			map.put("data","");
		}else {
			map.put("code","0");
			map.put("message","获取验证码成功！");
			map.put("data","");
		}
		return map;

	}

	//TODO 预约技师时候的手机号验证
	/**
	 * 手机号绑定验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public String toVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		if(StringUtils.isEmpty(phone)) {
			return "手机号不能为空！";
		}
		if(StringUtils.isEmpty(code)) {
			return "验证码不能为空！";
		}
		if(userMyService.userVerify(phone, code, userSession.get_id()).isSuccess()){
			User u=userMyService.queryUserByPhone(phone);
			if(u!=null){
				session.setAttribute("user", u);
			}
			return "绑定成功";
		}else {
			return "绑定失败";
		}
	}


	/**
	 * 分享授权登陆
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/oauth2wx", method = RequestMethod.GET)
    @ResponseBody
	public void Oauth2shareCoupon(HttpServletRequest request, HttpServletResponse response,String inviaterId) throws IOException {
		WxShare wxShare = new WxShare(inviaterId);
		List<User> list = new ArrayList<>();
		wxShare.setShareUserList(list);
		cacheManager.save("share"+inviaterId,wxShare);
        System.out.println("userInfo_______________"+ JSON.toJSONString(inviaterId));
        String oppenid=inviaterId;
        logger.info("当前用户的openid____________________________________:"+JSONObject.toJSONString(oppenid));
		String backUrl = "http://test.wangtiansoft.cn/mrmf/w/home/oauth2me.do";
		String redirect_uri = "";
		try {
			redirect_uri = URLEncoder.encode(backUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String oauth2Url = "http://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Configure.userAppID+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_userinfo&state="+oppenid+"#wechat_redirect";
        logger.info("oauth2Url:"+oauth2Url);
		response.sendRedirect(oauth2Url);
	}


    /**
     * 分享成功发放优惠券
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="/grantCouponByShare", method = RequestMethod.POST)
    @ResponseBody
    public String grantCouponByShare(HttpServletRequest request, HttpServletResponse response, String inviaterId) {
        if (couponGrantService.grantCouponByuserUuidAndType(inviaterId,"分享",-1,"").isSuccess()){
            return "发放成功";
        }else {
            return "发放失败";
        }
    }


//--------------------------------------------------------------测试授权使用------------------------------------------------------------------------------------------
	/**
	 * 授权回调请求处理
	 * @return
	 */
	@RequestMapping(value ="/oauth2me.do")
	public void oAuth2Url(HttpServletRequest request,HttpServletResponse response, String code, String state){
        logger.info("回调了___________________");
		AccessToken accessToken = wxoAuth2Service.getOAuth2TokenByCode(code,state);
			if (accessToken != null){
				getMemberGuidByCode(accessToken,response,state);
			}
	}

	/**
	 * 调用接口获取用户信息
	 * @param token
	 * @return
	 */
	public String getMemberGuidByCode(AccessToken token, HttpServletResponse response,String inviatorId){
		User user = wxoAuth2Service.getOAuth2UserInfoByToken(token);
		WxShare wxShare = (WxShare) cacheManager.get("share"+inviatorId);
		if (wxShare != null){
			List<User> list = wxShare.getShareUserList();
			for (int index = 0; index < list.size(); index ++){
				User tempUser = list.get(index);
				if (tempUser.get_id() == user.get_id()){
					try {
						response.sendRedirect("http://test.wangtiansoft.cn/mrmf/w/home/toQueding?userId="+inviatorId);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "用户已经抢过这个红包";
				}
			}
			couponGrantService.grabRedWallet(user,inviatorId);
			try {
				response.sendRedirect("http://test.wangtiansoft.cn/mrmf/w/home/toQueding?userId="+inviatorId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("授权登陆的user____________________________________:"+JSONObject.toJSONString(user));
		return "";
	}
	@RequestMapping(value = "/toQueding", method = RequestMethod.GET)
	public ModelAndView toQueding(HttpServletRequest request, HttpServletResponse response,String userId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId",userId);
		mv.setViewName("user/usermy/myOrder/queding");
		return mv;
	}
	@RequestMapping(value = "/toQiangRed", method = RequestMethod.GET)
	public ModelAndView toQiangRed(HttpServletRequest request, HttpServletResponse response,String userId) throws Exception {
		ModelAndView mv = new ModelAndView();
		WxShare wxShare = (WxShare) cacheManager.get("share"+userId);
		mv.addObject("userList",wxShare.getShareUserList());
		mv.setViewName("user/usermy/myOrder/qiangquanList");
		return mv;
	}
}














