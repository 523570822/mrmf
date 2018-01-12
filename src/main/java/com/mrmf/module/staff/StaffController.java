package com.mrmf.module.staff;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mrmf.entity.*;
import com.mrmf.entity.organPisition.*;
import com.mrmf.service.VipMember.VipMemberService;
import com.mrmf.service.organPosition.OrganPosition;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.DayBean;
import com.mrmf.service.userPay.UserPayService;
import com.mrmf.service.wes.WeSService;
import com.mrmf.service.weuser.WeUserService;
import com.osg.framework.BaseException;
import com.osg.framework.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.user.Smallsort;
import com.mrmf.service.common.Configure;
import com.mrmf.service.common.WxgetInfo;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffMyService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.userService.UserService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.osg.entity.Entity;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.GpsUtil;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.PositionUtil;

@Controller
@RequestMapping("/staff")
public class StaffController {
	@Autowired
	private StaffMyService staffMyService;
	@Autowired
	private WeUserService weUserService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private WeComonService weCommonService;
	@Autowired
	private UserService userService;
	@Autowired
	private WeOrganService weOrganService;
	@Autowired
	private WxgetInfo wxgetInfo;
	@Autowired
	private RedisService redisService;
    @Autowired
    private OrganPosition organPosition;
	@Autowired
	private UserPayService userPayService;
	@Autowired
	private WeSService weSService;
    @Autowired
    private VipMemberService vipMemberService;
	@RequestMapping("/wxlogin")
	public ModelAndView login(@RequestParam(required=false)String code,String state,HttpServletRequest request)throws Exception {
		ModelAndView mv=new ModelAndView();
		String staffState="";
		HttpSession session=request.getSession(true);//
		Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("staffInfo");
		if(userInfo==null){
	//		Map<String,Object> token=wxgetInfo.getAccess_token(code,"staff");
		//	userInfo=wxgetInfo.getUserInfo(token);
			ReturnStatus status=null;
			session.setAttribute("staffInfo", userInfo);
			String oppenid="";
			String unionid="";
			if(userInfo!=null)
			{

				if(userInfo.get("openid")!=null){
					oppenid=userInfo.get("openid").toString();
				}
				if(userInfo.get("unionid")!=null){
					unionid=userInfo.get("unionid").toString();
				}
				status = weCommonService.isExist(oppenid, unionid, "staff");
			}else {
				status= new ReturnStatus(false,"该微信号第一次关注");
			}
			if (status.isSuccess()) {

				Staff staff=staffService.getStaff(oppenid);
				request.setAttribute("staff", staff);
				session.setAttribute("staff", staff);
				session.setAttribute("city", "北京市");
				session.setAttribute("cityId", "1667920738524089172");
				if(staff!=null){
					if(staff.getOrganId()==null&&(staff.getWeOrganIds()==null||staff.getWeOrganIds().size()==0)){
						if(staffService.queryWeOrganStaffVerifyNoVerify(staff.get_id()).isSuccess()){//判断是否存在待审核的信息如果存在就是已申请的如果不存在就是解约的
							staffState="isAdd"; //用户已经申请了加入店铺等待审核
						}else{
							staffState="noAdd";  //申请了加入店铺但是已解约从新申请
						}
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
				}
				if(!StringUtils.isEmpty(staffState)){
					request.setAttribute("staffState", staffState);
					session.setAttribute("staffState", staffState);
					mv.setViewName("staff/login/addorganinfo");//跳转到提示页面提示该微信号已经申请了加入店铺
				}else{
					request.setAttribute("staff", staff);
					session.setAttribute("staff", staff);
					mv.setViewName("staff/mainPage");
				}

			} else {
				staffState="noPhone";
				session.setAttribute("staffState", staffState);
				mv.setViewName("/staff/login/binderPhone");
			}
		}else{
			Map<String,Object> token=wxgetInfo.getAccess_token(code,"staff");
			userInfo=wxgetInfo.getUserInfo(token);
			Staff staff=staffService.getStaff(userInfo.get("openid").toString());
			if (staff == null){
				mv.setViewName("/staff/login/binderPhone");
				Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "organ");
				mv.addObject("sign", sign);
				return mv;
			}
			staffState=(String)session.getAttribute("staffState");
			if(!StringUtils.isEmpty(staffState)){
				if("isAdd".equals(staffState)){
					mv.setViewName("staff/login/addorganinfo");//跳转到提示页面提示该微信号已经申请了加入店铺
				}else if("noPhone".equals(staffState)){
					mv.setViewName("/staff/login/binderPhone");
				}else if("noAdd".equals(staffState)){
					mv.setViewName("staff/login/addorganinfo");//跳转到提示页面提示该微信号已经申请了加入店铺
				}else if("exist".equals(staffState)){
					mv.setViewName("staff/mainPage");
				}
			}else{
				session.setAttribute("staff", staff);
				ReturnStatus status=staffService.isComplementMes(staff.get_id());//查看技师信息是否完整
				mv.addObject("status", status);
				mv.setViewName("staff/mainPage");
			}

		}
		Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"staff");
		mv.addObject("sign", sign);
		return mv;
	}

	@RequestMapping("/wxAdd")
	public ModelAndView wxAdd(@RequestParam(required=false)String code,String state,HttpServletRequest request)throws Exception {
		ModelAndView mv=new ModelAndView();
		HttpSession session=request.getSession(true);//
		Map<String,Object> userInfo=null;
		userInfo=(Map<String,Object>)session.getAttribute("addInfo");
		if(userInfo==null){
			Map<String,Object> token=wxgetInfo.getAccess_token(code,"staff");
			 userInfo=wxgetInfo.getUserInfo(token);
			session.setAttribute("addInfo", userInfo);
			mv=common(userInfo,request);
		}else{
			mv=common(userInfo,request);
		}

		return mv;
	}
	public ModelAndView common(Map<String,Object> userInfo,HttpServletRequest request)throws Exception{
		ModelAndView mv=new ModelAndView();
		HttpSession session=request.getSession(true);
		ReturnStatus status=null;
		String staffState="";
		String oppenid="";
		String unionid="";
		if(userInfo!=null) {
			if(userInfo.get("openid")!=null){
				oppenid=userInfo.get("openid").toString();
			}
			if(userInfo.get("unionid")!=null){
				unionid=userInfo.get("unionid").toString();
			}
			 status = weCommonService.isExist(oppenid, unionid, "staff");
		}
		status = weCommonService.isExist(oppenid,unionid, "staff");
		if (status.isSuccess()) {//该技师已经存在微信账户
			 Staff staff=staffService.getStaff(userInfo.get("openid").toString());
			 if(staff!=null){
				 if(staff.getOrganId()==null&&(staff.getWeOrganIds()==null||staff.getWeOrganIds().size()==0)){
					 if(staffService.queryWeOrganStaffVerifyNoVerify(staff.get_id()).isSuccess()){//判断是否存在待审核的信息如果存在就是已申请的如果不存在就是解约的
						 staffState="isAdd"; //用户已经申请了加入店铺等待审核
						}else{
							staffState="noAdd";//申请了加入店铺但是已解约从新申请
						}
				 }else if(staff.getOrganId()==null&&!(staff.getWeOrganIds()==null||staff.getWeOrganIds().size()==0)){
					 staffState="exist";//用户已经加入店铺请直接进入任性猫
				 }else if(staff.getOrganId()!=null){
					 staffState="exist";//用户已经加入店铺请直接进入任性猫
				 }
			 }
			 request.setAttribute("staffState", staffState);
			 session.setAttribute("staffState", staffState);
			 if("noAdd".equals(staffState)){
				 mv.setViewName("/staff/login/addOrgan");//跳转到添加店铺页面
			 }else{
				 mv.setViewName("staff/login/addorganinfo");//跳转到提示页面提示该微信号已经申请了加入店铺
			 }

		} else {//没有微信账户
			//跳转添加店铺页面
			staffState="nonexist";
			mv.setViewName("/staff/login/addOrgan");//跳转到添加店铺页面
		}
		return mv;
	}

	//我的店铺
		@RequestMapping("/wxAddOrgan")
		@ResponseBody
		public Map<String,String> wxAddOrgan(@RequestParam(required=true)String phone,String code,String organId,String organName,HttpServletRequest request)throws Exception {
			HttpSession session = request.getSession();
			Map<String,String> msg=new HashMap<String,String>();
			Map<String,Object> userInfo=(Map<String, Object>) session.getAttribute("addInfo");
			//Map<String,Object> userInfo=new HashMap<String,Object>();
			ReturnStatus status=null;
			Staff staff=null;
			if(userInfo!=null){
				//Random ran=new Random();
				//String oppenid="oppenid_zlj"+ran.nextInt(100);
				//String unionid="unionid_zlj";
				String oppenid="";
				String unionid="";
				String logo="";
				String nickname="";
				String sex="";
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
				if(userInfo.get("sex")!=null){
					sex=userInfo.get("sex").toString();
				}
			    status=weCommonService.verify(oppenid, unionid, phone, code,logo, nickname,sex, gpsPoint,organId,organName);
				if(status.isSuccess()){
					msg.put("msg", "绑定成功");
					staff=staffService.getStaff(oppenid);
					session.setAttribute("staff", staff);
					msg.put("staffId", staff.get_id());
				} else {
					msg.put("msg", status.getMessage());
				}
			}
			return msg;
		}
		//技师加入店铺后跳转到提示页面
		@RequestMapping("/toAddorganinfo")
		public ModelAndView toAddorganinfo(@RequestParam(required=false)String state,HttpServletRequest request)throws Exception{
			ModelAndView mv=new ModelAndView();
			request.setAttribute("staffState", state);
			mv.setViewName("staff/login/addorganinfo");
			return mv;
		}
		//我的店铺
		@RequestMapping("/searchOrgan")
		@ResponseBody
		public FlipInfo<Organ> searchOrgan(@RequestParam(required=true)String keyWord,HttpServletRequest request)throws Exception {
			HttpSession session = request.getSession();
			FlipPageInfo<Organ> fpi=new FlipPageInfo<Organ>(request);
			FlipInfo<Organ> organs=null;
			fpi.getParams().remove("keyWord");
			fpi.setPage(1);
			fpi.setSize(5);
			fpi.getParams().put("regex:name", keyWord);
			organs=weCommonService.searchOrgan(fpi);
			return organs;
		}
		@RequestMapping("/getPhoneCode")
		@ResponseBody
		public String getPhoneCode(String phone,String type,HttpServletRequest request) throws Exception {
				ReturnStatus status=weCommonService.verifycode(phone);
				if(status.isSuccess()){
					return "验证码已发送";
				}
			return status.getMessage();
		}
	//跳转首页
	@RequestMapping("/toMainPage")
	public ModelAndView toMainPage(@RequestParam(required=false)String staffId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		Staff staff=staffService.getById(staff2.get_id());
		request.setAttribute("staff", staff);
		request.setAttribute("staffId", staffId);
		Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"staff");
		request.setAttribute("sign", sign);
		/*ReturnStatus status=staffService.isComplementMes(staff.get_id());
		request.setAttribute("status", status);*/
		return new ModelAndView("staff/mainPage");
	}
	//返回上一页
	@RequestMapping("/tolastPage")
	public ModelAndView tomainPage(@RequestParam(required=false)String status, String staffId,String cityId,HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff = (Staff)session.getAttribute("staff");
		request.setAttribute("staff", staff);
		if (status.equals("stores")) {
			return myStore(staff.get_id(), request);
		}else if (status.equals("mystore")) {
			return toMainPage(staff.get_id(), request);
		}else if (status.equals("myDetail")) {
			return toMainPage(staff.get_id(), request);
		}else if (status.equals("example")) {
			return toMainPage(staff.get_id(), request);
		}else if (status.equals("statistics")) {
			return signIn(request,session);
		}else if (status.equals("tolastPage")) {
			return toMainPage(staff.get_id(), request);
		}else if (status.equals("schedule")) {
			return toMainPage(staff.get_id(), request);
		}else if (status.equals("addExample")) {
			return exampleList(staff.get_id(), request);
		}else if (status.equals("store")) {
			return stores(staff.get_id(), null, null, cityId, request);
		}else if (status.equals("selectType")) {
			return addExample(request);
		}else if (status.equals("adjustLocation")) {
			return signIn(request,session);
		}else if (status.equals("myRecommendation")) {
			return toMainPage(staff.get_id(), request);
		}
		return null;
	}
	//我的店铺
	@RequestMapping("/myStore")
	public ModelAndView myStore(@RequestParam(required=false) String staffId,HttpServletRequest request)throws Exception {
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		request.setAttribute("staffId", staff.get_id());
		return new ModelAndView("staff/myStore/myStore");
	}
	//我的店铺
	@RequestMapping("/myStoreList")
	@ResponseBody
	public FlipInfo<WeOrganStaffVerify> myStoreList(@RequestParam(required=true)String staffId,int page,HttpServletRequest request,HttpSession session )throws Exception {
		Staff staff=(Staff)session.getAttribute("staff");
		FlipPageInfo<WeOrganStaffVerify> fpi=new FlipPageInfo<WeOrganStaffVerify>(request);
		fpi.getParams().remove("staffId");
		fpi.setSortField("state");
		fpi.setSortOrder("DESC");
		FlipInfo<WeOrganStaffVerify> organs=staffService.getMyStore(staff.get_id(),fpi);
		return organs;
	}
	//跳转店铺
	@RequestMapping("/storesList")
	@ResponseBody
	public FlipInfo<Organ> storesList(String staffId,String cityId,String districtId, String regionId,int page,HttpServletRequest request,HttpSession session)throws Exception {
		Staff staff=(Staff)session.getAttribute("staff");
		FlipInfo<Organ> fpi=staffService.getOrgans(regionId,districtId,regionId,request,staff.get_id());
		List<WeBDistrict> wdList=staffService.getDistrict(cityId);
		request.setAttribute("wdList", wdList);
		request.setAttribute("staffId", staffId);
		return fpi;
	}
	//获取店铺列表
	@RequestMapping(value = "/stores",produces = "text/plain;charset=UTF-8")
	public ModelAndView stores(@RequestParam(required=false)String staffId,String regionId,String districtId,String cityId,HttpServletRequest request)throws Exception {
		Map<String, List> map = weOrganService.queryDistrictList(cityId, districtId);
        String organName = request.getParameter("organName");
		request.setAttribute("map", map);
		request.setAttribute("staffId", staffId);
		request.setAttribute("organName", organName);
        request.setAttribute("regionId", regionId);
		request.setAttribute("region",weSService.queryRegionById(regionId));
		return new ModelAndView("staff/myStore/stores");
	}
	@RequestMapping(value = "/rexOrganName",produces = "text/plain;charset=UTF-8")
	public ModelAndView rexOrganName(HttpServletRequest request,String organName,String cityId,String staffId,String regionId,String districtId){
        request.setAttribute("districtId",districtId);
        request.setAttribute("regionId",regionId);
		request.setAttribute("catyId", cityId);
		request.setAttribute("staffId", staffId);
		request.setAttribute("organName", organName);
		return new ModelAndView("staff/myStore/rexOrganName");
	}
	//获取区域
	@RequestMapping("/district")
	public ModelAndView district(@RequestParam String cityId,HttpServletRequest request)throws Exception {
		List<WeBDistrict> wdList=staffService.getDistrict(cityId);
		request.setAttribute("wdList", wdList);
		return new ModelAndView("");
	}

	//获取商圈
	@RequestMapping("/region")
	@ResponseBody
	public Map<String,List> ragion(@RequestParam(required=false)String cityId, String districtId,HttpServletRequest request)throws Exception {
		Map<String,List> map=weOrganService.queryDistrictList(cityId, districtId);
		return map;
	}

	//获取店铺详细信息
	@RequestMapping("/store")
	public ModelAndView store(@RequestParam(required = false)String staffId,String organId,HttpServletRequest request)throws Exception {
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		String isJoin="0",distance;//技师未加入
		double dis=0,
	    longitude,
	    latitude;
		Organ organ=staffService.getOrgan(organId);
		Staff staff = staffService.getById(staff2.get_id());
		GpsPoint gpsPoint = staff.getGpsPoint();
		if(gpsPoint == null){
			gpsPoint = new GpsPoint();
		}
		if (session.getAttribute("longitude") !=null) {
			longitude=Double.parseDouble(session.getAttribute("longitude").toString());
			gpsPoint.setLongitude(longitude);
		}
		if (session.getAttribute("latitude") !=null) {
			latitude=Double.parseDouble(session.getAttribute("latitude").toString());
			gpsPoint.setLatitude(latitude);
		}
		WeOrganStaffVerify wsv=staffService.getVerifyStatus(staff2.get_id(),organId);
		if (staff !=null) {
			if (wsv ==null) {
				isJoin="0";//未加入
			}else if (wsv !=null && wsv.getState()==0) {
				isJoin="2";//审核中
			}else if (wsv !=null && wsv.getState()==1) {
				isJoin="1";//已加入
			}else if (wsv !=null && wsv.getState()==-2) {
				isJoin="3";//已经解约
			}
			GpsPoint organGps = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
			dis = GpsUtil.distance(gpsPoint,organGps);
			if (dis<1) {
				dis*=1000;
				 distance=new DecimalFormat("#.00").format(dis)+"";
			}else {
				 distance=new DecimalFormat("#.00").format(dis)+"k";
			}
			if (distance.equals(".00")) {
				distance="0";
			}
			request.setAttribute("distance", distance);
		}
		if (staff.getFollowOrganIds() !=null && staff.getFollowOrganIds().contains(organ.get_id())) {
			request.setAttribute("isCollect", "yes");
		}else {
			request.setAttribute("isCollect", "no");
		}
		request.setAttribute("organ", organ);
		request.setAttribute("isJoin", isJoin);
		request.setAttribute("staffId", staffId);
		return new ModelAndView("staff/myStore/store");
	}

 	//获取店铺详细信息
	@RequestMapping("/storeDetail")
	public ModelAndView storeDetail(@RequestParam(required = false)String staffId,String organId,HttpServletRequest request)throws Exception {
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		String isJoin="-1",distance;//技师未加入
		double dis=0,
	    longitude,
	    latitude;
		Organ organ = staffService.getOrgan(organId);
		Staff staff = staffService.getById(staff2.get_id());
		GpsPoint gpsPoint = staff.getGpsPoint();
		if(gpsPoint == null){
			gpsPoint = new GpsPoint();
		}
		if (session.getAttribute("longitude") !=null) {
			longitude=Double.parseDouble(session.getAttribute("longitude").toString());
			gpsPoint.setLongitude(longitude);
		}
		if (session.getAttribute("latitude") !=null) {
			latitude=Double.parseDouble(session.getAttribute("latitude").toString());
			gpsPoint.setLatitude(latitude);
		}
		WeOrganStaffVerify wsv=staffService.getVerifyStatus(staff2.get_id(),organId);
		if (staff !=null) {
			if (wsv ==null ) {
				isJoin="0";
			}else if(wsv.getState()!=1) {
				isJoin="0";
			}else {
				isJoin="1";
			}
			GpsPoint organGps = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
			dis = GpsUtil.distance(gpsPoint,organGps);
			if (dis<1) {
				dis*=1000;
				 distance=new DecimalFormat("#.00").format(dis)+"";
			}else {
				 distance=new DecimalFormat("#.00").format(dis)+"k";
			}
			if (distance.equals(".00")) {
				distance="0";
			}
			request.setAttribute("distance", distance);
		}
		if (staff.getFollowOrganIds() !=null && staff.getFollowOrganIds().contains(organ.get_id())) {
			request.setAttribute("isCollect", "yes");
		}else {
			request.setAttribute("isCollect", "no");
		}
		request.setAttribute("organ", organ);
		request.setAttribute("isJoin", isJoin);
		request.setAttribute("staffId", staffId);
		return new ModelAndView("staff/myStore/storeDetail");
	}
	//技师收藏店铺
	@RequestMapping("/follow")
	@ResponseBody
	public int follow(@RequestParam(required=false)String staffId,String organId,String followType,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		Integer followCount= staffService.followOrgan(staff.get_id(),organId,followType);
		return followCount;
	}
	//店铺技师列表
	@RequestMapping("/storeStaffList")
	@ResponseBody
	public FlipInfo<Staff> storeStaffList(@RequestParam(required=false)String organId,HttpServletRequest request)throws Exception{
		FlipPageInfo<Staff> flp=new FlipPageInfo<>(request);
		flp.getParams().remove("organId");
		flp.setSize(6);
		FlipInfo<Staff> staffList= staffService.storeStaffList(organId,flp);
		return staffList;
	}
	//申请加入/解约店铺
	@RequestMapping("/isJoin")
	@ResponseBody
	public ReturnStatus isJoin(@RequestParam(required=false) String staffId,String organId,String status,HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff=(Staff)session.getAttribute("staff");
		ReturnStatus state=staffService.isJoin(staff.get_id(),organId,status);
		return state;
	}
	//获取个人信息
	@RequestMapping("/myDetail")
	public ModelAndView myDetail(@RequestParam(required=false)String staffId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		String url = Configure.DOMAIN_URL + request.getRequestURI();
		Staff staff2=(Staff)session.getAttribute("staff");
		Staff staff = staffService.getById(staff2.get_id());
		if (staff.getIdcard() !=null && !staff.getIdcard().equals("") && staff.getIdcard().length()==18) {
			String idcard = staff.getIdcard();
			String newIdCard=idcard.substring(0, 7)+"*****"+idcard.substring(idcard.length()-6, idcard.length());
			staff.setIdcard(newIdCard);
		}
		request.setAttribute("staff", staff);
		request.setAttribute("staffId", staffId);
		Map<String, Object> sign = redisService.getWechatPositioningMessage(url,"user");
    	mv.addObject("sign", sign);
		mv.setViewName("staff/mine/myDetail");
		return mv;
	}
	//修改个人信息(查看)
	@RequestMapping("/changeMessLoad")
	public ModelAndView changeMessLoad(@RequestParam(required=false)String staffId,String status,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		Staff staff = staffService.getById(staff2.get_id());
		request.setAttribute("staff", staff);
		if (status.equals("nick")) {
			return new ModelAndView("staff/mine/change_nick");
		}else if (status.equals("name")) {
			return new ModelAndView("staff/mine/change_name");
		}else if (status.equals("phone")) {
			return new ModelAndView("staff/mine/bind_phone");
		}else if (status.equals("idcard")) {
			return new ModelAndView("staff/mine/change_idcard");
		}else if (status.equals("certNumber")) {
			return new ModelAndView("staff/mine/change_certNumber");
		}else if (status.equals("home")) {
			return new ModelAndView("staff/mine/change_home");
		}else if (status.equals("sex")) {
			return new ModelAndView("staff/mine/change_sex");
		}else if (status.equals("jishiTechang")) {
			List<Code> codes=staffService.findtype();
			request.setAttribute("codes", codes);
			return new ModelAndView("staff/mine/change_jishiTechang");
		}else if (status.equals("workYears")) {
			return new ModelAndView("staff/mine/change_workYears");
		}
		return null;
	}


	//修改个人信息(保存)
	@RequestMapping("/isHaveStaffPhone")
	@ResponseBody
	public Boolean isHaveStaffPhone(String phone,HttpServletRequest request)throws Exception{
		return staffMyService.isHaveStaffPhone(phone);
	}
	//修改个人信息(保存)
	@RequestMapping("/changeMessSave")
	public ModelAndView changeMessSave(@RequestParam(required=false)String staffId,String status,String val,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		//修改member手机号
        vipMemberService.updateMemberByPhone(staff2.getPhone(),val);
		staffService.getAndSaveById(staff2.get_id(),status,val);
		Staff staff = staffService.getById(staff2.get_id());
		if (staff.getIdcard() !=null && !staff.getIdcard().equals("") && staff.getIdcard().length()==18) {
			String idcard = staff.getIdcard();
			String newIdCard=idcard.substring(0, 7)+"*****"+idcard.substring(idcard.length()-6, idcard.length());
			staff.setIdcard(newIdCard);
		}
		request.setAttribute("staff", staff);
		return new ModelAndView("staff/mine/myDetail");
	}
	//保存图片
	@RequestMapping("/savePhoto")
	@ResponseBody
	public void savePhoto(@RequestParam(required=false) String staffId,String logo,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		staffService.getAndSavePhoto(staff.get_id(),logo);
	}
	//典型案例
	@RequestMapping("/exampleList")
	public ModelAndView exampleList(@RequestParam(required=false)String staffId,HttpServletRequest request)throws Exception{
		request.setAttribute("staffId", staffId);
		return new ModelAndView("staff/myExample/exampleList");
	}

	//典型案例
	@RequestMapping("/changePhone")
	public ModelAndView exampleList(HttpServletRequest request)throws Exception{
		return new ModelAndView("staff/mine/change_phone");
	}
	//典型案例列表
	@RequestMapping("/toExampleList")
	@ResponseBody
	public FlipInfo<WeStaffCase> toExampleList(@RequestParam(required=false)String staffId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		FlipInfo<WeStaffCase> exampleList=staffService.getStaffCases(staff.get_id(),request);
		request.setAttribute("staffId", staffId);
		return exampleList;
	}
	//案例详情
	@RequestMapping("/example")
	public ModelAndView example(@RequestParam(required=false)String staffId,String exampleId,HttpServletRequest request)throws Exception{
		WeStaffCase staffCase=staffService.getExample(exampleId);
		String price=""+staffCase.getPrice();
		String[] split = price.split("\\.");
		request.setAttribute("staffId", staffId);
		request.setAttribute("staffCase", staffCase);
		request.setAttribute("number", split[0]);
		request.setAttribute("decimal", split[1]);
		return new ModelAndView("staff/myExample/example");
	}
	//跳转添加案例页面
	@RequestMapping("/addExample")
	public ModelAndView addExample(HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView("staff/myExample/addExample");
		String staffId = request.getParameter("staffId");
		String title = request.getParameter("title");
		String codeId = request.getParameter("codeId");
		String desc = request.getParameter("desc");
		String price = request.getParameter("price");
		String consumeTime = request.getParameter("consumeTime");
		Code code = staffService.findCodeById(codeId);
		request.setAttribute("staffId", staffId);
		request.setAttribute("title", title);
		request.setAttribute("codeId", codeId);
		request.setAttribute("desc", desc);
		request.setAttribute("price", price);
		request.setAttribute("consumeTime", consumeTime);
		request.setAttribute("code", code);
		Map<String, Object> sign;
		if(StringUtils.isEmpty(request.getQueryString())) {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(),"staff");
		} else {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"staff");
		}
		mv.addObject("sign", sign);
		return mv;
	}

	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(HttpServletRequest request)throws Exception{
		String serverId = request.getParameter("serverId");
		WeToken weToken = redisService.getTonkenInfo("staff");
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
	//保存案例
	@RequestMapping("/addExampleSave")
	@ResponseBody
	public String addExampleSave(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		List<String> list=new ArrayList<String>();
		String type = request.getParameter("type");
		String realType = request.getParameter("real_type");
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String images = request.getParameter("images");
		if(!StringUtils.isEmpty(images)) {
			String[] imgArr = images.split(",");
			for(int i= 0 ;i<imgArr.length;i++) {
				list.add(imgArr[i]);
			}
		}
		String price = request.getParameter("price");
		String consumeTime = request.getParameter("consumeTime");
		staffService.addExample(staff.get_id(),type,realType,title,desc,price,consumeTime,list);
		return "true";
	}

	//编辑保存案例
	@RequestMapping("/editAndSaveExam")
	@ResponseBody
	public ReturnStatus editAndSaveExam(@RequestParam(required=false)String staffCaseId,String title,String desc,double price,int consumeTime,HttpServletRequest request)throws Exception{
		ReturnStatus status=staffService.editAndSaveExam(staffCaseId,title,desc,price,consumeTime);
		return status;
	}
	//删除案例
	@RequestMapping("/deleteExample")
	@ResponseBody
	public ReturnStatus deleteExample(@RequestParam(required=false)String staffCaseId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		ReturnStatus status=staffService.deleteExample(staffCaseId,staff.get_id());
		return status;
	}
	//日程管理
	@RequestMapping("/schedule")
	public ModelAndView schedule(@RequestParam(required = false)String staffId,String organId,String organName,Integer day,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		staffMyService.setWeStaffCalendar(staff.get_id());
		List<WeekDay> weekDays=weCommonService.queryScheduleTitle();
		if(day==null){
			day=weekDays.get(0).getDaytime();
		}
		WeStaffCalendar weStaffCalendar= staffService.getWeStaffSchedule(staff.get_id(),day);
		if (weStaffCalendar !=null) {
			Organ organ=staffService.getOrgan(weStaffCalendar.getOrganId());
			request.setAttribute("organ", organ);
		}
		if (organId !=null) {
			Organ organ = staffService.getOrgan(organId);
			request.setAttribute("organ", organ);
			request.setAttribute("organId", organId);
		}
		request.setAttribute("weekDays", weekDays);
		request.setAttribute("staffId", staffId);
		request.setAttribute("weStaffCalendar", weStaffCalendar);
		request.setAttribute("day", day);
		return new ModelAndView("staff/schedule/schedule");
	}
	//工位租赁
	@RequestMapping("/toRent")
	public ModelAndView toRent(@RequestParam(required = false) String staffId, String cityId, String city, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		request.setAttribute("staffId", staffId);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city",city);
		List<Code> codeList = weCommonService.queryTypeList("organType");
		request.setAttribute("codeList", codeList);
		mv.setViewName("staff/rent/rentOrgan_list");
		Map<String, List> map = weOrganService.queryDistrictList(cityId, "");
		request.setAttribute("map", map);
		//如果用户地理位置为空则添加用户上一次登陆的位置
		//User user=(User)session.getAttribute("user");
		Staff staff = staffService.queryStaffById(staffId);
		Object lo = session.getAttribute("longitude");
		Object la = session.getAttribute("latitude");
		if (lo == null) {
			session.setAttribute("longitude", staff.getGpsPoint().getLongitude());
			//longitude1=Double.parseDouble(longitude);
			//session.setAttribute("longitude", longitude1);
		}
		if (la == null) {
			session.setAttribute("latitude", staff.getGpsPoint().getLatitude());
			//latitude1=Double.parseDouble(latitude);
			//session.setAttribute("latitude", latitude1);
		}
//		List<OrganPositionSetting> organPosition = weCommonService.queryOrganPositionList("organType");

		return mv;

	}
	//选择店铺
	@RequestMapping("/selectOrgan")
	public ModelAndView selectOrgan(@RequestParam(required = false)String staffId,String day,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
		fpi.getParams().remove("staffId");
		fpi.getParams().remove("day");
		int parseInt = Integer.parseInt(day);
		FlipInfo<Organ> organs=staffService.getOrgans(staff.get_id(),fpi);
		request.setAttribute("staffId", staffId);
		request.setAttribute("organs", organs);
		request.setAttribute("day", parseInt);
		return new ModelAndView("staff/schedule/selectOrgan");
	}
	//日程保存
	@RequestMapping("/scheduleSave")
	@ResponseBody
	public String scheduleSave(@RequestParam(required = false)String staffId,String organId,Integer day,int index,boolean selected,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		if (organId == null ||organId.equals("")) {
			return "false";
		}
		ReturnStatus status=staffService.scheduleSave(staff.get_id(),organId,day,index,selected);
		if (status.isSuccess()) {
			return "true";
		}
		return "false";
	}
	//跳转繁忙
	@RequestMapping("/setBusyTime")
	public ModelAndView setBusyTime(@RequestParam(required = false)String staffId,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		Staff staff = staffService.getById(staff2.get_id());
		request.setAttribute("staff", staff);
		request.setAttribute("staffId", staffId);
		return new ModelAndView("staff/schedule/setBusyTime");
	}
	//设置繁忙
	@RequestMapping("/saveBusyTime")
	public ModelAndView saveBusyTime(@RequestParam(required = false)String staffId,String busyTimeStart,String busyTimeEnd,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		ReturnStatus status=staffService.saveBustTime(staff.get_id(),busyTimeStart,busyTimeEnd);
		request.setAttribute("staffId", staffId);
		return schedule(staffId, null, null, null, request);
	}

	//跳转询价列表
	@RequestMapping("/askPrice")
	public ModelAndView askPrice(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staffSession=(Staff)session.getAttribute("staff");
		Staff staff = staffService.getById(staffSession.get_id());
		if (staff.getOpenInquiry() !=null) {
			if (staff.getOpenInquiry()==true) {
				request.setAttribute("openInquiry", true);
			}else {
				request.setAttribute("openInquiry", false);
			}
		} else {
			request.setAttribute("openInquiry", false);
		}
		return new ModelAndView("staff/askPrice/askPriceList");
	}
	//跳转询价列表显示
	@RequestMapping("/askPriceList")
	@ResponseBody
	public FlipInfo<WeUserInquiry> askPriceList(String staffId,double longitude,double latitude,HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		if (session.getAttribute("longitude") !=null) {
			longitude=Double.parseDouble(session.getAttribute("longitude").toString());
		}
		if (session.getAttribute("latitude") !=null) {
			latitude=Double.parseDouble(session.getAttribute("latitude").toString());
		}

		FlipInfo<WeUserInquiry> fpi = new FlipPageInfo<WeUserInquiry>(request);
		fpi.getParams().remove("staffId");
		fpi.getParams().remove("longitude");
		fpi.getParams().remove("latitude");
		FlipInfo<WeUserInquiry> weUserInquiry= staffService.askPrice(staff2.get_id(),fpi,longitude,latitude);
		request.setAttribute("staffId", staffId);
		return weUserInquiry;
	}
	//开启询价
	@RequestMapping("/openInquiry")
	public ModelAndView openInquiry(HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff=(Staff)session.getAttribute("staff");
		staffService.openInquiry(staff.get_id(),"open");
	    return askPrice(request);

	}
	//关闭询价
	@RequestMapping("/closeInquiry")
	public ModelAndView closeInquiry(HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff=(Staff)session.getAttribute("staff");
		staffService.openInquiry(staff.get_id(),"close");
        return askPrice(request);
	}

	//询价详情
	@RequestMapping("/askPriceDetail")
	public ModelAndView askPriceDetail(String inquiryId,HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView("staff/askPrice/askPriceDetail");
		WeUserInquiry weUserInquiry= staffService.askPriceById(inquiryId);
		mv.addObject("weUserInquiry", weUserInquiry);
		return mv;
	}
	//保存报价
	@RequestMapping("/askPriceSave")
	@ResponseBody
	public ReturnStatus askPriceSave(@RequestParam(required=false)String inquiryId,Double myPrice,String mypriceDesc,HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff=(Staff)session.getAttribute("staff");
		ReturnStatus status= staffService.askPriceSave(staff.get_id(),inquiryId,myPrice,mypriceDesc);
		return status;
	}
	//获取附近店铺
	@RequestMapping("/getNearOrgan")
	public ModelAndView getNearOrgan(@RequestParam(required=false)String staffId,HttpServletRequest request,HttpSession session)throws Exception{
		double longitude =0,latitude=0;
		if (session.getAttribute("longitude") !=null) {
			longitude=Double.parseDouble(session.getAttribute("longitude").toString());
		}
		if (session.getAttribute("latitude") !=null) {
			latitude=Double.parseDouble(session.getAttribute("latitude").toString());
		}
		FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
		fpi.getParams().remove("staffId");
		fpi.getParams().remove("longitude");
		fpi.getParams().remove("latitude");
		staffService.getNearOrgan(fpi,longitude,latitude);
		request.setAttribute("staffId", staffId);
		request.setAttribute("fpi", fpi);
		return new ModelAndView("staff/askPrice/askPriceList");
	}
	//签到
	@RequestMapping("/signIn")
	public ModelAndView signIn(HttpServletRequest request,HttpSession session)throws Exception{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String day=new SimpleDateFormat("MM-dd").format(date);
		String time=new SimpleDateFormat("HH:mm").format(date);
		int week=cal.get(Calendar.DAY_OF_WEEK);
		Staff staff = (Staff)session.getAttribute("staff");
		request.setAttribute("staffId", staff.get_id());
		request.setAttribute("day", day);
		request.setAttribute("time", time);
		request.setAttribute("week", week);
		request.setAttribute("isSuccess", 0);
		return new ModelAndView("staff/signIn/signIn");
	}
	//签到店铺显示
	@RequestMapping("/organList")
	@ResponseBody
	public FlipInfo<Organ> organList(@RequestParam(required=false)String organName,HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff=(Staff)session.getAttribute("staff");
		/*double longitude = 0;
		double latitude = 0;
		if (session.getAttribute("longitude") !=null) {
			 longitude=Double.parseDouble(session.getAttribute("longitude").toString());
		}
		if (session.getAttribute("latitude") !=null) {
			 latitude=Double.parseDouble(session.getAttribute("latitude").toString());
		}
		GpsPoint gpsPoint = PositionUtil.bd09_To_Gcj02(latitude, longitude);*/
		FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
		fpi.getParams().remove("longitude");
		fpi.getParams().remove("latitude");
		fpi.getParams().remove("staffId");
		fpi.getParams().remove("organName");
		FlipInfo<Organ> organs= staffService.getNearOrgans(staff.get_id(),fpi,organName);
		return organs;
	}
	//地点微调
	@RequestMapping("/adjustLocation")
	public ModelAndView adjustLocation(@RequestParam(required=false)String staffId,String organName,HttpServletRequest request,HttpSession session)throws Exception{
		double longitude =0,latitude=0;
		if (session.getAttribute("longitude") !=null) {
			longitude=Double.parseDouble(session.getAttribute("longitude").toString());
		}
		if (session.getAttribute("latitude") !=null) {
			latitude=Double.parseDouble(session.getAttribute("latitude").toString());
		}
		request.setAttribute("staffId", staffId);
		request.setAttribute("longitude", longitude);
		request.setAttribute("latitude", latitude);
		return new ModelAndView("staff/signIn/searchOrgan");
	}

	//签到保存
	@RequestMapping("/signInSave")
	@ResponseBody
	public ReturnStatus signInSave(@RequestParam(required=false)String staffId,String organId,Double longitude,Double latitude,HttpServletRequest request,HttpSession session)throws Exception{
		Staff staff=(Staff)session.getAttribute("staff");
		if (session.getAttribute("longitude") !=null) {
			longitude=Double.parseDouble(session.getAttribute("longitude").toString());
		}
		if (session.getAttribute("latitude") !=null) {
			latitude=Double.parseDouble(session.getAttribute("latitude").toString());
		}
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String day=new SimpleDateFormat("MM-dd").format(date);
		String time=new SimpleDateFormat("HH:mm").format(date);
		int week=cal.get(Calendar.DAY_OF_WEEK);
		String organName=null;
		Organ organ = staffService.getOrgan(organId);
		if (organ !=null) {
			organName=organ.getName();
		}
		ReturnStatus reStatus= staffService.saveSign(staff.get_id(),organId,organName,longitude,latitude);
		request.setAttribute("staffId", staffId);
		request.setAttribute("day", day);
		request.setAttribute("time", time);
		request.setAttribute("week", week);
		request.setAttribute("isSuccess", 1);
		return reStatus;

	}
	//签到统计
	@RequestMapping("/signStatistics")
	public ModelAndView signStatistics(@RequestParam(required=false)String staffId, String timeDesc,HttpServletRequest request)throws Exception{
		request.setAttribute("staffId", staffId);
		return new ModelAndView("staff/signIn/signStatistics");
	}
	//签到统计列表
	@RequestMapping("/signStatisticsList")
	@ResponseBody
	public FlipInfo<WeStaffSign> signStatisticsList(@RequestParam(required=false)String staffId, String timeSort,String time,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		FlipInfo<WeStaffSign> fpi = new FlipPageInfo<WeStaffSign>(request);
		fpi.getParams().remove("staffId");
		fpi.getParams().remove("timeSort");
		fpi.getParams().remove("time");
		if (timeSort != null && !timeSort.equals("")) {
			fpi.setSortField("createTime");
			fpi.setSortOrder(timeSort);
		}

		FlipInfo<WeStaffSign> signList= staffService.getSignStatistics(staff.get_id(),time,fpi);
		return signList;
	}
	//选择类型
	@RequestMapping("/selectType")
	public ModelAndView selectType(HttpServletRequest request)throws Exception{
		List<Code> types = staffService.findTypes();
		String codeId = request.getParameter("codeId");
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String price = request.getParameter("price");
		String consumeTime = request.getParameter("consumeTime");
		request.setAttribute("types", types);
		request.setAttribute("codeId", codeId);
		request.setAttribute("title", title);
		request.setAttribute("desc", desc);
		request.setAttribute("price", price);
		request.setAttribute("consumeTime", consumeTime);
		return new ModelAndView("staff/myExample/change_type");
	}

	//类型选择跳回输入页
	@RequestMapping("/enquiryPrice")
	public ModelAndView enquiryPrice(@RequestParam(required=false)String userId,String codeId,String staffId,HttpServletRequest request)throws Exception{
		Code code=userService.getCodeById(codeId);
		request.setAttribute("code", code);
		request.setAttribute("userId", userId);
		request.setAttribute("staffId", staffId);
		return new ModelAndView("staff/myExample/addExample");
	}


	//我的页面
	@RequestMapping("/mine")
	public ModelAndView mine(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		String status = request.getParameter("status");
		String sysMessage = request.getParameter("sysMessage");
		if(!StringUtils.isEmpty(status) && status.equals("message")){
			if(!StringUtils.isEmpty(sysMessage) && sysMessage.equals("message")){
				staffService.updateMessageToRead(staff.get_id(), 2);
			}
			staffService.updateMessageToRead(staff.get_id(), 1);
		}
		long messageCount = staffService.findMessageCount(staff.get_id());
		request.setAttribute("messageCount", messageCount);
		return new ModelAndView("staff/mine/mine");
	}

	//跳转自我推荐
	@RequestMapping("/myRecommendation")
	public ModelAndView myRecommendation(@RequestParam(required=false)String staffId,HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView("staff/myRecommendation");
		HttpSession session = request.getSession();
		Staff staff2=(Staff)session.getAttribute("staff");
		Staff staff = staffService.getById(staff2.get_id());
		request.setAttribute("staffId", staffId);
		request.setAttribute("staff", staff);
		Map<String, Object> sign;
		if(StringUtils.isEmpty(request.getQueryString())) {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(),"staff");
		} else {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"staff");
		}
		mv.addObject("sign", sign);
		return mv;
	}
	//保存自我推荐
	@RequestMapping(value="/saveRecommendation", method = RequestMethod.POST)
	@ResponseBody
	public String saveRecommendation(HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		String logo0 = request.getParameter("logo0");
		String logo1 = request.getParameter("logo1");
		String logo2 = request.getParameter("logo2");
		String textArea = request.getParameter("textArea");
		ReturnStatus status = staffService.editSave(staff.get_id(), textArea,logo0,logo1,logo2);
		if (status.isSuccess()) {
			return "true";
		}
		return "false";
	}
	//跳转价格管理
	@RequestMapping("/tariff")
	public ModelAndView tariff(HttpServletRequest request)throws Exception{
		return new ModelAndView("staff/tariff");
	}

	//价目列表
	@RequestMapping("/tariffList")
	@ResponseBody
	public FlipInfo<Smallsort> tariffList(String page,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		Staff staff=(Staff)session.getAttribute("staff");
		FlipPageInfo<Smallsort> flp=new FlipPageInfo<Smallsort>(request);
		FlipInfo<Smallsort> tariffList=staffService.getTariffList(staff.get_id(),flp);
		return tariffList;
	}
	@RequestMapping("/toverifycode")
	@ResponseBody
	public Map<String,String> toverifycode(String phone,String type,String code,HttpServletRequest request) throws Exception {
			HttpSession session=request.getSession(true);
			Map<String,Object> userInfo=(Map<String, Object>) session.getAttribute("staffInfo");
			Map<String,String> msg=new HashMap<String,String>();
			ReturnStatus status=null;
			Staff staff=null;
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
					session.setAttribute("staffState", "");
					msg.put("msg", "绑定成功");
					staff=staffService.getStaff(oppenid);
					staff=staffService.bindOrgan(staff);
					session.setAttribute("staff", staff);
					request.setAttribute("staff", staff);
					msg.put("staffId", staff.get_id());
				}else{
					msg.put("msg", status.getMessage());
				}
			}
		    return msg;
	}
	@RequestMapping("/toSearchOrgan")
	public ModelAndView toSearchOrgan(@RequestParam(required = false) String staffId, String cityId, String city, String search, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		request.setAttribute("staffId", staffId);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("search", search);

		//如果用户地理位置为空则添加用户上一次登陆的位置
		//User user=(User)session.getAttribute("user");
		Staff staff = staffService.queryStaffById(staffId);
		Object lo = session.getAttribute("longitude");
		Object la = session.getAttribute("latitude");
		if (lo == null) {
			session.setAttribute("longitude", staff.getGpsPoint().getLongitude());
			//longitude1=Double.parseDouble(longitude);
			//session.setAttribute("longitude", longitude1);
		}
		if (la == null) {
			session.setAttribute("latitude", staff.getGpsPoint().getLatitude());
			//latitude1=Double.parseDouble(latitude);
			//session.setAttribute("latitude", latitude1);
		}
		mv.setViewName("staff/rent/search_store_list");
		return mv;
	}

    /**
     * 去店铺工位展示页面
     * @param organId
     * @param staffId
     * @param distance
     * @param cityId
     * @param city
     * @param search
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/toOrganDetail")
	public ModelAndView toOrganDetail(@RequestParam(required = false) String organId, String staffId, String distance, String cityId, String city, String search, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		city = URLDecoder.decode(city, "utf-8");
		request.setAttribute("organId", organId);
		request.setAttribute("staffId", staffId);
		request.setAttribute("cityId", cityId);
		request.setAttribute("city", city);
		request.setAttribute("search", search);
		request.setAttribute("distance", distance);
		String type = request.getParameter("type");
		Staff staff = staffService.queryStaffById(staffId);
		request.setAttribute("staff", staff);
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
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI(), "staff");
		} else {
			sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(), "staff");
		}
		OrganPositionSetting setting = organPosition.queryPosition(organId);
		mv.addObject("setting",setting);
		mv.addObject("sign", sign);
		mv.setViewName("staff/rent/store_detail");
		return mv;
	}
	@RequestMapping("/back")
	public ModelAndView back()throws Exception {

		return new ModelAndView("/staff/mainPage");
	}
    /**
     * 去当前店面选择日期租赁
     * @param request
     * @return
     */
    @RequestMapping("/toDate")
    public ModelAndView toDate(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String organId = request.getParameter("organId");
        String staffId = request.getParameter("staffId");
		request.setAttribute("staffId",staffId);

        //得到当前年，月
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH)+1;
        int currentYear = calendar.get(Calendar.YEAR);
        //得到3个月后的 年 月
		int nextYear = currentYear;
		int nextYearMonth = currentMonth+2;
		if(currentMonth+2>12){
			nextYearMonth = currentMonth-12;
			nextYear+=1;
		}
        //得到下下月的最后一天
        int nextMonthDay = DateUtil.getDaysByYearMonth(nextYear,nextYearMonth);
        Date beginTime = DateUtil.getDateToStr(currentYear,currentMonth,1,0);
        Date endTime = DateUtil.getDateToStr(nextYear,nextYearMonth,nextMonthDay,1);

        List<OrganPositionDetails> organPositionDetails = organPosition.queryOnePosition(organId,beginTime,endTime);//得到未来四个月
		List<PositionOrder> positionOrderList = organPosition.queryOrderList(staffId,beginTime,endTime);//得到技师指定日期区间的订单们
		//得到技师订单id的集合
		List<String> orderIdList = new ArrayList<>();
		for(PositionOrder positionOrder:positionOrderList){
			orderIdList.add(positionOrder.get_id());
		}
		//得到订单集合对应的租赁记录
		List<OrganPositionDetails> detaisList = organPosition.queryDetailsByOrderIdList(orderIdList);
		List<String> staffDetailsTimeList = new ArrayList<>();
		for(OrganPositionDetails details:detaisList){
			String detailsTime = DateUtil.format(details.getTime());
			staffDetailsTimeList.add(detailsTime);
		}
		//实例化map 每一个map对应一个月   ii表示起止月份
        Map map = new TreeMap();
        int ii = currentMonth;
        for(int i = ii;i<=ii+3;i++){//循环3个月
            List<String> list = new ArrayList();
            int monthLastDay = DateUtil.getDaysByYearMonth(currentYear,currentMonth);
            Date fristDay = DateUtil.getDateToStr(currentYear,currentMonth,1,0);
            Date lastDay = DateUtil.getDateToStr(currentYear,currentMonth,monthLastDay,1);
            for(OrganPositionDetails ops:organPositionDetails){
                Date opsDate = ops.getTime();//找到当天数据时间对应的天数，i++
                if(!opsDate.after(lastDay)&&!opsDate.before(fristDay)){//每条数据的日期与当前循环月的比较
                    list.add(DateFormatUtils.format(ops.getTime(),"yyyy-MM-dd"));//存一条数据
                }
            }
            //不再标准的工具类：传入年，月，店月租赁记录，当前时间，当前技师租赁记录
            List<DayBean> days = DateUtil.sumDate(currentYear,currentMonth,list,new Date(),staffDetailsTimeList);
            map.put(i,days);
            if(currentMonth==12){
                currentYear+=1;
                currentMonth=1;
            }else{
                currentMonth+=1;
            }
        }
        OrganPositionSetting organPositionSetting = organPosition.queryPosition(organId);//得到工位配置
        mv.addObject("map",map);
        mv.addObject("organPositionSetting",organPositionSetting);
        mv.addObject("organPositionDetails",organPositionDetails);
        //mv.setViewName("staff/rent/date");
		mv.setViewName("staff/myPosition/positionDate");
        return mv ;
    }



    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        // 解决_id字段注入问题，去除“_”前缀处理
        binder.setFieldMarkerPrefix(null);
    }
	@RequestMapping("/organList1")
	@ResponseBody
	public FlipInfo<Organ> organList1(String paixu,String type, String city, String district, String region, double longitude, double latitude, String distance, double maxDistance, String followCount, int page, int pagesize, HttpServletRequest request) throws Exception {
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
		String positonType = request.getParameter("positionType");
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
		fpi.getParams().remove("paixu");
		fpi.getParams().put("weixin|boolean", "true");
		fpi.getParams().put("valid|integer", "1");
		if (!"".equals(followCount)) {
			fpi.setSortField("followCount");
			fpi.setSortOrder("DESC");
			organList = staffService.queryOrganListByfollowCount(gps.getLongitude(), gps.getLatitude(), fpi);
		}
		if (!"".equals(distance)) {
			organList = staffService.queryOrganListByUser(gps.getLongitude(), gps.getLatitude(), maxDistance, fpi);
		}
		FlipInfo<Organ> organList1 = staffService.queryOrganPosition(organList);
		if(paixu.equals("down")){
			List<Organ> list= organList1.getData();
			for (int i = 0; i < list.size(); i++)
			{
				for (int j = i; j <list.size(); j++)
				{
					double f,s=0;
					if(list.get(i).getUnit().equals("km")){
						f=list.get(i).getDistance()*1000;
					}else {
						f=list.get(i).getDistance();
					}
					if(list.get(j).getUnit().equals("km")){
						s=list.get(j).getDistance()*1000;
					}else {
						s=list.get(j).getDistance();
					}
					if (f<s)
					{
						Organ temp = list.get(i);
						list.set(i,list.get(j));
						list.set(j,temp);
					}
				}
			}
			FlipInfo<Organ> organList2=new FlipInfo<>();
			organList2.setData(list);
			return organList2;
		}
		return organList1;
	}
	@RequestMapping("/toResult")
	public ModelAndView toResult(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String orderId = request.getParameter("orderId");
		PositionOrder positionOrder = organPosition.getPositonById(orderId);
		mv.addObject("positionOrder",positionOrder);
		mv.setViewName("staff/myPosition/result");
		return mv;
	}

	/**
	 * 取消支付 跳转首页
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping("/cancelPositionOrder")
	public ModelAndView cancelPositionOrder(HttpServletRequest request) throws BaseException {
		String orderId = request.getParameter("orderId");
		userPayService.cancelPositionOrder(orderId);
		return new ModelAndView("staff/mainPage");
	}
}
