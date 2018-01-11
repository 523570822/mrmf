package com.mrmf.moduleweb;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.Staff;
import com.mrmf.entity.kaoqin.KBancidingyi;
import com.mrmf.entity.kaoqin.KKaoqin;
import com.mrmf.entity.kaoqin.KKaoqinleibie;
import com.mrmf.entity.kaoqin.KKaoqintime;
import com.mrmf.entity.kaoqin.KPaiban;
import com.mrmf.entity.kaoqin.KQingjiadengji;
import com.mrmf.entity.staff.Staffpost;
import com.mrmf.service.kaoqin.KaoqinService;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
/**
 * 考勤控制类
 * @author yangshaodong
 */
@Controller
@RequestMapping("/kaoqin")
public class KaoqinController {
	@Autowired 
	private KaoqinService kaoqinService;
	/**
	 * 去显示界面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toBancidingyi")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kaoqin/bancidingyi/query");
		return mv;
	}
	
	/**
	 * 添加班次
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBanci",method = RequestMethod.GET)
	public ModelAndView addBanci(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String banciId = request.getParameter("banciId");
		if(!StringUtils.isEmpty(banciId)) {
			KBancidingyi  bancidingyi =  kaoqinService.queryBancidingyiById(banciId);
			String [] time1 = bancidingyi.getTime_a1().split(":");
			String time_a1_h = time1[0];
			String time_a1_m = time1[1];
			String [] time2 = bancidingyi.getTime_a2().split(":");
			String time_a2_h = time2[0];
			String time_a2_m = time2[1];
			mv.addObject("time_a1_h", time_a1_h);
			mv.addObject("time_a1_m", time_a1_m);
			mv.addObject("time_a2_h", time_a2_h);
			mv.addObject("time_a2_m", time_a2_m);
			mv.addObject("bancidingyi", bancidingyi);
		}
		mv.setViewName("kaoqin/bancidingyi/upsert");
		return mv;
	}
	/**
	 * 添加和修改班次
	 * @param bancidingyi
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBanci",method = RequestMethod.POST)
	public ModelAndView  addBanci(KBancidingyi bancidingyi,BindingResult results,HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String banciId = request.getParameter("banciId");
		if(StringUtils.isEmpty(banciId)) { 
			bancidingyi.setIdIfNew();
		} else {
			bancidingyi.set_id(banciId);
		}
		String time_a1_h = request.getParameter("time_a1_h");
		String time_a2_h = request.getParameter("time_a2_h");
		String time_a1_m = request.getParameter("time_a1_m");
		String time_a2_m = request.getParameter("time_a2_m");
		String organId = (String) MAppContext.getSessionVariable("organId");
		bancidingyi.setDelete_flag(false);
		bancidingyi.setTime_a1(time_a1_h +":"+time_a1_m);
		bancidingyi.setTime_a2(time_a2_h +":"+time_a2_m);
		bancidingyi.setOrganId(organId);
		bancidingyi.setKaoqin_a1(bancidingyi.getKaoqin_a1());
		bancidingyi.setKaoqin_a2(bancidingyi.getKaoqin_a2());
		bancidingyi.setKuatian(bancidingyi.getKuatian());
		kaoqinService.saveBanci(bancidingyi);
		mv.setViewName("kaoqin/bancidingyi/query");
		return mv;
	}
	/**
	 * 假删除班次
	 * @param banciId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBanci",method = RequestMethod.GET)
	public ModelAndView deleteBanci(@RequestParam(required = true) String banciId, HttpServletRequest request) throws Exception {
		kaoqinService.deleteBanci(banciId);
		return toQuery(request); //跳转到班次界面
	}
	
	
	/**
	 * 查询班次
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBanci")
	@ResponseBody
	public FlipInfo<KBancidingyi> queryBanci(HttpServletRequest request) throws Exception {
		FlipInfo<KBancidingyi> fpi = new FlipPageInfo<KBancidingyi>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.queryBancidingyi(fpi);
		return fpi;
	}
	/**
	 * 跳转到考勤类别界面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toKaoqinleibie")
	public ModelAndView toKaoqinleibie(HttpServletRequest request) throws BaseException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kaoqin/kaoqinleibie/query");
		return mv;
	}
	/**
	 * 查询所有的考勤类别
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryKaoqinleibie")
	@ResponseBody
	public FlipInfo<KKaoqinleibie> queryKaoqinleibie(HttpServletRequest request) throws BaseException {
		FlipInfo<KKaoqinleibie> fpi = new FlipPageInfo<KKaoqinleibie>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.queryKaoqinleibie(fpi);
		return fpi;
	}
	/**
	 * 添加和修改类别
	 * @param kaoqinleibie
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upsertKaoqinleibie",method=RequestMethod.POST)
	public ModelAndView  upsertKaoqinleibie(KKaoqinleibie kaoqinleibie,BindingResult results,HttpServletRequest request) throws Exception {
		    ModelAndView mv = new ModelAndView();
		 	String organId = (String) MAppContext.getSessionVariable("organId");
			String kaoqinleibieId = request.getParameter("kaoqinleibieId");
			if(StringUtils.isEmpty(kaoqinleibieId)) {
				kaoqinleibie.setIdIfNew();
				kaoqinleibie.setDelete_flag(false);
				kaoqinleibie.setOrganId(organId);
			    kaoqinService.saveKaoqinleibie(kaoqinleibie);
			} else {
				kaoqinleibie.set_id(kaoqinleibieId);
				kaoqinleibie.setOrganId(organId);
				kaoqinleibie.setDelete_flag(false);
				kaoqinService.updateKaoqinleibie(kaoqinleibie);
			}
			mv.setViewName("kaoqin/kaoqinleibie/query");
			return mv;	
	}
	/**
	 * 删除考勤类别
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteKaoqinleibie")
	public ModelAndView deleteKaoqinleibie(HttpServletRequest request) throws BaseException {
		String kaoqinleibieId = request.getParameter("kaoqinleibieId");
		kaoqinService.deleteKaoqinleibie(kaoqinleibieId);
		return toKaoqinleibie(request);
	}
	/**
	 * 修改考勤类别
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upsertKaoqinleibie")
	public ModelAndView updateKaoqinleibie(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String kaoqinleibieId = request.getParameter("kaoqinleibieId");
		if(!StringUtils.isEmpty(kaoqinleibieId)) {
			KKaoqinleibie  kaoqinleibie  = kaoqinService.findKaoqinleibieById(kaoqinleibieId);
			request.setAttribute("ffkaoqinleibie", kaoqinleibie);
		}
		//kaoqinService.initKaoqin();
		//TODO 初始化所有公司考勤
		mv.setViewName("kaoqin/kaoqinleibie/upsert");
		return mv;
	}
	//员工日常管理
	@RequestMapping("/toWorkereveryday")
	public ModelAndView toWorkereveryday(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kaoqin/workereveryday/query");
		return mv;
	}
	/**
	 * 查询所有的请假登记
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryQingjiadengji")
	@ResponseBody
	public FlipInfo<KQingjiadengji> queryQingjiadengji(HttpServletRequest request) throws BaseException {
		FlipInfo<KQingjiadengji> fpi = new FlipPageInfo<KQingjiadengji>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.queryQingjiadengji(fpi);
		return fpi;
	}
	/**
	 * 查询所有的请假登记
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upsertQingjiadengji",method=RequestMethod.GET)
	public ModelAndView upsertQingjiadengji(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String organId = (String) MAppContext.getSessionVariable("organId");
		String qingjiadengjiId = request.getParameter("qingjiadengjiId");
		if(!StringUtils.isEmpty(qingjiadengjiId)) {
			KQingjiadengji  qingjiadengji  = kaoqinService.findQingjiadengjiById(qingjiadengjiId);
			request.setAttribute("ffqingjiadengji",qingjiadengji);
		} 
		List<Staff> staffs = kaoqinService.findWorkStaff(organId);
		List<KKaoqinleibie> kaoqinleibies = kaoqinService.findKaoqinleibie(organId);
		mv.addObject("staffs", staffs);
		mv.addObject("kaoqinleibies", kaoqinleibies);
		mv.setViewName("kaoqin/workereveryday/upsert");
		return mv;
	}
	
	//upsertQingjiadengji
	/**
	 * 添加和修改请假登记
	 * @param kaoqinleibie
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upsertQingjiadengji",method=RequestMethod.POST)
	public ModelAndView  upsertQingjiadengji(KQingjiadengji qingjiadengji,BindingResult results,HttpServletRequest request) throws Exception {
		 	String organId = (String) MAppContext.getSessionVariable("organId");
			String qingjiadengjiId = request.getParameter("qingjiadengjiId");
			if(StringUtils.isEmpty(qingjiadengjiId)) {
				qingjiadengji.setIdIfNew();
				qingjiadengji.setCreateTime(new Date());
			} else {
				qingjiadengji.set_id(qingjiadengjiId);
			}
			Staff staff = kaoqinService.findWorkStaff(organId,qingjiadengji.getNames());
			KKaoqinleibie kaoqinleibie = kaoqinService.findKaoqinleibie(organId,qingjiadengji.getType1());
			qingjiadengji.setStaffNames(staff.getName());
			qingjiadengji.setType1Names(kaoqinleibie.getNames());
			qingjiadengji.setDelete_flag(false);
			qingjiadengji.setOrganId(organId);
			kaoqinService.upsertQingjiadengji(qingjiadengji);
			return toWorkereveryday(request);	
	}
	/**
	 * 删除请假登记
	 */
	/**
	 * 删除考勤类别
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteQingjiadengji")
	public ModelAndView deleteQingjiadengji(HttpServletRequest request) throws BaseException {
		String qingjiadengjiId = request.getParameter("qingjiadengjiId");
		kaoqinService.deleteQingjiadengji(qingjiadengjiId);
		return toWorkereveryday(request);
	}
	//员工日常管理
	@RequestMapping("/toEverydaySearch")
	public ModelAndView toEverydaySearch(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("kaoqin/everydaysearch/query");
		return mv;
	}
	//findQingjiadengji
	/**
	 * 查询所有的请假登记
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findQingjiadengji")
	@ResponseBody
	public FlipInfo<KQingjiadengji> findQingjiadengji(HttpServletRequest request) throws BaseException {
		FlipInfo<KQingjiadengji> fpi = new FlipPageInfo<KQingjiadengji>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.findQingjiadengji(fpi);
		for (KQingjiadengji qingjiadengji : fpi.getData()) {
			Staff staff = kaoqinService.findWorkStaff(organId,qingjiadengji.getNames());
			KKaoqinleibie kaoqinleibie = kaoqinService.findKaoqinleibie(organId,qingjiadengji.getType1());
			qingjiadengji.setStaffNames(staff.getName());
			qingjiadengji.setType1Names(kaoqinleibie.getNames());
		}
		return fpi;
	}
    /**
     * 日常排班管理
     * @param request
     * @return
     */
	@RequestMapping("/toPaiban")
	public ModelAndView toPaiban(HttpServletRequest request) {
	     ModelAndView mv = new ModelAndView();
	     String organId = (String) MAppContext.getSessionVariable("organId");
	     String paibanId = request.getParameter("paibanId");
	     List<KBancidingyi> kBancidingyis =  kaoqinService.findBancidingyis(organId);
	     mv.addObject("kBancidingyis", kBancidingyis);
	     if(!StringUtils.isEmpty(paibanId)) {
	    	 KPaiban kPaiBan =  kaoqinService.findPaiBanById(paibanId);
	    	 kaoqinService.convertToOneName(kPaiBan);
	    	 Staff staff =  kaoqinService.findStaffById(kPaiBan.getStaffId());
	     	 String yearmonth = String.valueOf(kPaiBan.getYearmonth()-1);
	    	 String year = yearmonth.substring(0,4);
	    	 String month = yearmonth.substring(4,yearmonth.length());
	    	 request.setAttribute("year", year);
	    	 request.setAttribute("month", month);
	    	 request.setAttribute("staff", staff);
	    	 request.setAttribute("paibanId", paibanId);
	    	 request.setAttribute("kPaiBanJSON", JSONObject.toJSON(kPaiBan));
	    	 mv.setViewName("kaoqin/paiban/updatePaiban");
	     } else {
			 mv.setViewName("kaoqin/paiban/paiban");
	     } 
		 return mv;
	}
	
	/**
	 * 查询技师
	 */
	@RequestMapping("queryStaff")
	@ResponseBody
	public FlipInfo<Staff> queryStaff(HttpServletRequest request) {
		String organId = (String) MAppContext.getSessionVariable("organId");
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
	    fpi = kaoqinService.findWorkStaffByPage(fpi,organId);
		for(Staff staff:fpi.getData()) {
			String dutyId = staff.getDutyId();
			Staffpost staffpost= kaoqinService.findDutyName(dutyId);
		    if(staffpost != null) {
		    	staff.setDutyName(staffpost.getName());
		    }
		}
		return fpi;
	}
	
	/**
	 * 查询技师
	 */
	@RequestMapping("queryOneStaff")
	@ResponseBody
	public FlipInfo<Staff> queryOneStaff(HttpServletRequest request) {
		String organId = (String) MAppContext.getSessionVariable("organId");
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
	    String staffId = request.getParameter("staffId");
    	fpi.getParams().remove("staffId");
    	fpi.getParams().put("organId", organId);
    	fpi.getParams().put("_id", staffId);
    	fpi = kaoqinService.findPaiBanStaff(fpi);
		for(Staff staff:fpi.getData()) {
			String dutyId = staff.getDutyId();
			Staffpost staffpost= kaoqinService.findDutyName(dutyId);
		    if(staffpost != null) {
		    	staff.setDutyName(staffpost.getName());
		    }
		}
		return fpi;
	}
	@RequestMapping(value = "/upsertPaiban",method=RequestMethod.POST)
	@ResponseBody
	public boolean upsertPaiban(KPaiban kPaiban,BindingResult results,HttpServletRequest request){
		String staffIds = request.getParameter("staffIds");
		 String staffNames = request.getParameter("staffNames");
		if(StringUtils.isEmpty(kPaiban.get_id())) {
			 try {
				 String[] ids= staffIds.split(",");
				 String[] names= staffNames.split(",");
				 for (int i = 0; i < ids.length; i++) {
					 	 if(!kaoqinService.isExistPaiban(ids[i])) {
					 		 existAndSetKaoqintime(kPaiban.getOrganId(),ids[i], 1);
							 existAndSetKaoqintime(kPaiban.getOrganId(),ids[i], 2);
					 	 }
					 	 KPaiban kPaibanTemp = kaoqinService.getPaiBan(ids[i],kPaiban.getYearmonth());
						 if(kPaibanTemp != null) {
							 kPaiban.set_id(kPaibanTemp.get_id());;
						 } else {
							 kPaiban.setNewId();
						 }
						 kPaiban.setStaffId(ids[i]);
						 kPaiban.setStaffName(names[i]);
						 kPaiban.setCreateTime(new Date());;
						 kaoqinService.savePaiban(kPaiban);
				 }
				 return true;
			} catch (Exception e) {
			     return false;
			}
		 } else {
			 kPaiban.setStaffId(staffIds);
			 kPaiban.setStaffName(staffNames);
			 kPaiban.setCreateTimeIfNew();
			 kaoqinService.savePaiban(kPaiban);
			 return true;
		 }	
	}
	
	/**
	 * 查询和设置是否有该类型的时间戳
	 * @param organId
	 * @param staffId
	 * @param type
	 */
	private void existAndSetKaoqintime(String organId,String staffId,int type) {
		 if(!kaoqinService.queryKKaoqintime(organId, staffId,type)) {
			 KKaoqintime kKaoqintime = new KKaoqintime();
			 kKaoqintime.setNewId();
			 kKaoqintime.setOrganId(organId);
			 kKaoqintime.setStaffId(staffId);
			 kKaoqintime.setCreateTimeIfNew();
			 kKaoqintime.setType(type);
			 kaoqinService.saveKKaoqintime(kKaoqintime);
		 } 
	}
	
	/**
	 * 跳转到查询排班界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toQueryPaiban",method=RequestMethod.GET)
	public ModelAndView toQueryPaiban(HttpServletRequest request) {
	     ModelAndView mv = new ModelAndView();
		 mv.setViewName("kaoqin/paiban/query");
		 return mv;
	}
	/**
	 * 查询排班
	 * @param request
	 * @return
	 */
	//queryPaiBanList
	@RequestMapping("/queryPaiBanList")
	@ResponseBody
	public FlipInfo<KPaiban> queryPaiBanList(HttpServletRequest request) {
		FlipInfo<KPaiban> fpi = new FlipPageInfo<KPaiban>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.findPaiBan(fpi,organId);
		return fpi;
	}
	
	/**
	 * 迟到查询
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChiDao",method=RequestMethod.GET)
	public ModelAndView toChiDao(HttpServletRequest request) throws ParseException {
	     ModelAndView mv = new ModelAndView();
	     String organId = (String) MAppContext.getSessionVariable("organId");
	     kaoqinService.setKaoqin(organId);
		 mv.setViewName("kaoqin/chidaozaotui/queryChiDao");
		 return mv;
	}
	/**
	 * 设置签到情况
	 * @param organId
	 * @throws ParseException
	 */
	public void setStaffSignCondtions(String organId) throws ParseException  {
		 List<Staff> staffs = kaoqinService.findWorkStaff(organId);
		 if(staffs != null) {
			 for (Staff staff:staffs) {
		    	 KKaoqintime kaoqintime = kaoqinService.queryKaoqintime(organId, staff.get_id(),2);  //找到
		    	 if(kaoqintime != null ) {
			    	 Date startTime = kaoqintime.getCreateTime();
			    	 Date endTime = new Date();
			    	 Date endTimeRecord = endTime;
			    	 Calendar cal = Calendar.getInstance();
			    	 cal.setTime(endTime);
			    	 cal.add(Calendar.DATE, -1);
			    	 endTime = cal.getTime();
			    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			    	 String startTimeStr = dateFormat.format(startTime);
					 String endTimeStr = dateFormat.format(endTime);
					 startTime = dateFormat.parse(startTimeStr);
					 endTime = dateFormat.parse(endTimeStr);
					 if(startTime.compareTo(endTime) <= 0) {
						 kaoqinService.queryStaffSignAndSetPaibanState(startTime, endTimeRecord, organId, staff.get_id());
						 kaoqinService.updateKaoqintime(organId,staff.get_id(),endTimeRecord,2);
				     }
		    	 }
			 }
		 }
	}
	/**
	 * 跳转到早退查询界面
	 * @throws Exception 
	 */
	@RequestMapping(value = "/toZaoTui",method=RequestMethod.GET)
	public ModelAndView toZaoTui(HttpServletRequest request) throws Exception {
	     ModelAndView mv = new ModelAndView();
	     String organId = (String) MAppContext.getSessionVariable("organId");
	     kaoqinService.setKaoqin(organId);
		 mv.setViewName("kaoqin/chidaozaotui/queryZaoTui");
		 return mv;
	}
	/**
	 * 早退查询
	 */
	@RequestMapping(value = "/queryZaotui")
	@ResponseBody
	public FlipInfo<KKaoqin> queryZaotui(HttpServletRequest request) {
		FlipInfo<KKaoqin> fpi = new FlipPageInfo<KKaoqin>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.findZaotui(fpi);
		return fpi;
	}
	/**
	 * 迟到查询
	 */
	@RequestMapping(value = "/queryChiDao")
	@ResponseBody
	public FlipInfo<KKaoqin> queryChiDao(HttpServletRequest request) {
		FlipInfo<KKaoqin> fpi = new FlipPageInfo<KKaoqin>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.findChiDao(fpi);
		return fpi;
	}
	/**
	 * 跳转到旷工查询界面
	 */
	@RequestMapping(value = "/toKuangGong",method=RequestMethod.GET)
	public ModelAndView toKuangGong(HttpServletRequest request) throws Exception {
	     ModelAndView mv = new ModelAndView();
	     String organId = (String) MAppContext.getSessionVariable("organId");
	     kaoqinService.setKaoqin(organId);
		 mv.setViewName("kaoqin/kuanggong/queryKuangGong");
		 return mv;
	}
	/**
	 * 查询旷工情况
	 * 	/kaoqin/toKuangGong.do
	 */
	@RequestMapping(value = "/queryKuangGong")
	@ResponseBody
	public FlipInfo<KKaoqin> queryKuangGong(HttpServletRequest request) {
		FlipInfo<KKaoqin> fpi = new FlipPageInfo<KKaoqin>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.findKuangGong(fpi);
		return fpi;
	}
	/**
	 * 跳转到员工签到查询界面
	 */
	@RequestMapping(value = "/toStaffSign",method=RequestMethod.GET)
	public ModelAndView toStaffSign(HttpServletRequest request) throws Exception {
	     ModelAndView mv = new ModelAndView();
	     String organId = (String) MAppContext.getSessionVariable("organId");
	     setStaffSignCondtions(organId);
		 mv.setViewName("kaoqin/staffsign/queryStaffSign");
		 return mv;
	}
	/**
	 * 查询排班
	 * @param request
	 * @return
	 */
	//queryPaiBanList
	@RequestMapping("/queryStaffSign")
	@ResponseBody
	public FlipInfo<KPaiban> queryStaffSign(HttpServletRequest request) {
		FlipInfo<KPaiban> fpi = new FlipPageInfo<KPaiban>(request);
		String organId = (String) MAppContext.getSessionVariable("organId");
		fpi.getParams().put("organId", organId);
		fpi = kaoqinService.queryStaffSign(fpi,organId);
		return fpi;
	}
	
	/**
	 * 格式化日期
	 * @param request
	 */
	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		//解决_id字段注入问题，去除“_”前缀处理
		binder.setFieldMarkerPrefix(null);
	}
}
