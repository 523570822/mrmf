package com.mrmf.moduleweb;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBRegion;
import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.bean.StaffRank;
import com.mrmf.service.rank.RankService;
import com.mrmf.service.sys.SysUser.SysUserService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

@Controller
@RequestMapping("/rank")
public class RankController {
	@Autowired
	RankService rankservice;
	/**
	 * 跳转到用户排行
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/toQuery")
	public ModelAndView toUserRankQuery(HttpServletRequest request)throws Exception{
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("rank/user/query");
		return mv;
	}
	/**
	 * 用户排行查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/query")
	@ResponseBody
	public FlipInfo<WeUserPayOrder> userquery(HttpServletRequest request)
			throws Exception {
		FlipInfo<WeUserPayOrder> fpi = new FlipPageInfo<WeUserPayOrder>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
//		Map<String,Date> dates=null;
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				

			}
		}else{
			if(!StringUtils.isEmpty((String)fpi.getParams().get("condition"))){
				List<User> users=rankservice.queryUser((String)fpi.getParams().get("condition"));
				String userIds="";
				if(users!=null&&users.size()>0){
					for(User user:users){
						userIds+=user.get_id()+",";
					}
				}
				if(!"".equals(userIds)){
					userIds=userIds.substring(0,userIds.length()-1);
					fpi.getParams().put("in:userId|array",userIds);

				}
			}
			/*if(StringUtils.isEmpty((String)fpi.getParams().get("hid_month"))){
				Calendar cal = Calendar.getInstance();
				int year=cal.get(Calendar.YEAR);
				int month=cal.get(Calendar.MONTH);
				 dates=getLastDayOfMonth(year,month);
			}else{
				int month=Integer.parseInt((String)fpi.getParams().get("hid_month"));
				int year=Integer.parseInt((String)fpi.getParams().get("hid_year"));
				dates=getLastDayOfMonth(year,month);
			}*/
		}
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fitst=sdf.format(dates.get("first"));
		String last=sdf.format(dates.get("last"));
		fpi.getParams().remove("hid_month");
		fpi.getParams().remove("hid_year");
		
		fpi.getParams().put("gte:createTime|date", fitst);
		fpi.getParams().put("lte:createTime|date+1", last);*/
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi.getParams().remove("condition");
		fpi.getParams().put("state|integer", "1");
		fpi = rankservice.queryUserRank(fpi);
		return fpi;
	}
	/**
	 * 给用户发送消息
	 * @param orderId
	 * @param activity
	 * @param prize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/sendMessage")
	@ResponseBody
	public ReturnStatus removePinpai(String orderId,String activity,String prize)
			throws Exception {
		return rankservice.sendMessageToUser(orderId,activity,prize);
	}
	
	@RequestMapping("/staff/query")
	@ResponseBody
	public List<StaffRank> staffquery(HttpServletRequest request,String condition,Date startTime,Date endTime,String city,String district,String region,Integer limit)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				

			}
		}else{
			
		}
		if(limit==null){
			limit=100;
		}
		String organIds = "";
		boolean organFlag = false;
		if(!StringUtils.isEmpty(city)||!StringUtils.isEmpty(district)||!StringUtils.isEmpty(region)){
			List<Organ> organs = rankservice.queryOrgan(city, district, region);
			for(Organ organ:organs){
				organIds+=organ.get_id()+",";
			}
			if(!"".equals(organIds)){
				organIds=organIds.substring(0,organIds.length()-1);
				//fpi.getParams().put("in:staffId|array",staffIds);

			}
			organFlag = true;
		}
			String staffIds="";
			List<Staff> staffs=rankservice.queryStaff(condition,organIds,organFlag);
			if(staffs!=null&&staffs.size()>0){
				for(Staff staff:staffs){
					staffIds+=staff.get_id()+",";
				}
			}
			if(!"".equals(staffIds)){
				staffIds=staffIds.substring(0,staffIds.length()-1);
				//fpi.getParams().put("in:staffId|array",staffIds);

			}
		boolean flag=false;
		if(!StringUtils.isEmpty(condition)||!StringUtils.isEmpty(city)||!StringUtils.isEmpty(district)||!StringUtils.isEmpty(region)){
			flag=true;
		}
		return rankservice.queryStaffRankList(startTime,endTime,staffIds,flag,limit);
		
	}
	@RequestMapping("/staff/recharge")
	@ResponseBody
	public ReturnStatus recharge(String staffId,double price)
			throws Exception {
		return rankservice.rechargeToStaff(staffId,price);
		//return null;
	}
	@RequestMapping("/user/recharge")
	@ResponseBody
	public ReturnStatus userrecharge(String rorderId,double price)
			throws Exception {
		return rankservice.rechargeToUser(rorderId,price);
		//return null;
	}
	@RequestMapping("/staff/toQuery")
	public ModelAndView toStaffRankQuery(HttpServletRequest request)throws Exception{
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		List<WeBCity> city = rankservice.cityList();
		request.setAttribute("ffcitys", city);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("rank/staff/query");
		return mv;
	}
	public static Map<String,Date> getLastDayOfMonth(int year,int month) throws ParseException
	{
	    Calendar cal = Calendar.getInstance();
	    //设置年份
	    cal.set(Calendar.YEAR,year);
	    //设置月份
	    cal.set(Calendar.MONTH, month);
	    //获取某月最大天数
	    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    //设置日历中月份的最大天数
	    cal.set(Calendar.DAY_OF_MONTH, lastDay);
	    //格式化日期
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String lastDayOfMonth = sdf.format(cal.getTime());
	    cal.set(Calendar.DAY_OF_MONTH,1);
	    String firstDayOfMonth=sdf.format(cal.getTime());
	    Date first=sdf1.parse(firstDayOfMonth+" 00:00:00");
	    Date last=sdf1.parse(lastDayOfMonth+" 00:00:00");
	    Map<String,Date> map=new HashMap<String,Date>();
	    map.put("first", first);
	    map.put("last", last);
	    return map;
	}
	@RequestMapping("/exportUserRank")
	public ModelAndView exportUserRank(String condition,@RequestParam(required=false)Date startTime,@RequestParam(required=false)Date endTime,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		File template=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_userrank_export.xls"));
		File outputExcel=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		//FlipInfo<WeUserPayOrder> fpi = new FlipPageInfo<WeUserPayOrder>(request);
		List <WeUserPayOrder> projects=rankservice.exportUserRank(condition, startTime, endTime);
		Map dataSet=new HashMap();
		dataSet.put("user",JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));
		try{
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "用户排行.xls");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/exportStaffRank")
	public List<StaffRank> exportStaffRank(String condition,String city,String district,String region,Integer limit,@RequestParam(required=false)Date startTime,@RequestParam(required=false)Date endTime,
		HttpServletRequest request,HttpServletResponse response)throws Exception{
		File template=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_staffrank_export.xls"));
		File outputExcel=new File(request.getSession().getServletContext().getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				

			}
		}else{
			
		}
		if(limit==null){
			limit=100;
		}
		String organIds = "";
		boolean organFlag = false;
		if(!StringUtils.isEmpty(city)||!StringUtils.isEmpty(district)||!StringUtils.isEmpty(region)){
			List<Organ> organs = rankservice.queryOrgan(city, district, region);
			for(Organ organ:organs){
				organIds+=organ.get_id()+",";
			}
			if(!"".equals(organIds)){
				organIds=organIds.substring(0,organIds.length()-1);
				//fpi.getParams().put("in:staffId|array",staffIds);

			}
			organFlag = true;
		}
			String staffIds="";
			List<Staff> staffs=rankservice.queryStaff(condition,organIds,organFlag);
			if(staffs!=null&&staffs.size()>0){
				for(Staff staff:staffs){
					staffIds+=staff.get_id()+",";
				}
			}
			if(!"".equals(staffIds)){
				staffIds=staffIds.substring(0,staffIds.length()-1);
				//fpi.getParams().put("in:staffId|array",staffIds);

			}
		boolean flag=false;
		if(!StringUtils.isEmpty(condition)||!StringUtils.isEmpty(city)||!StringUtils.isEmpty(district)||!StringUtils.isEmpty(region)){
			flag=true;
		}
		//return rankservice.queryStaffRankList(startTime,endTime,staffIds,flag,limit);
		
		//FlipInfo<WeUserPayOrder> fpi = new FlipPageInfo<WeUserPayOrder>(request);
		List <StaffRank> projects= rankservice.queryStaffRankList(startTime,endTime,staffIds,flag,limit);
		Map dataSet=new HashMap();
		dataSet.put("staff",JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));
		try{
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "技师排行.xls");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		// 解决_id字段注入问题，去除“_”前缀处理
		binder.setFieldMarkerPrefix(null);
	}
}
