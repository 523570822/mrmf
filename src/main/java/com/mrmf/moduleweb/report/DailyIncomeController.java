package com.mrmf.moduleweb.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrmf.service.user.userpart.UserpartService;
import com.osg.entity.DataEntity;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.report.DailyIncomeService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.framework.BaseException;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 收益日报相关
 */
@Controller
@RequestMapping("/dailyIncome")
public class DailyIncomeController {

	@Autowired
	private DailyIncomeService dailyIncomeService;

	@Autowired
	private UsersortService usersortService; // 会员类型

	@Autowired
	private SmallsortService smallsortService; // 服务项目

	@Autowired
	private StaffService staffService;

	@Autowired
	private UserpartService userpartService;
	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		request.setAttribute("ffusersorts", usersortService.findAll(organId));
		request.setAttribute("ffsmallsorts", smallsortService.findAll(organId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/dailyIncome");
		return mv;
	}

	@RequestMapping("/queryUser")
	@ResponseBody
	public List<Userpart> queryUser(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return dailyIncomeService.queryUser(organId, startTime, endTime);
	}

	/**
	 * 会员详细消费记录
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUserCard")
	@ResponseBody
	public List<Userpart> queryUserCard(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		return dailyIncomeService.queryUserCard(organId, startTime, endTime);
	}

	@RequestMapping("/queryUserCardNew")
	@ResponseBody
	public List<Userpart> queryUserCardNew(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return dailyIncomeService.queryUserCardNew(organId, startTime, endTime);
	}

	@RequestMapping("/queryUserCardXufei")
	@ResponseBody
	public List<Userpart> queryUserCardXufei(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return dailyIncomeService.queryUserCardXufei(organId, startTime, endTime);
	}

	@RequestMapping("/queryZengsong")
	@ResponseBody
	public List<Userpart> queryZengsong(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return dailyIncomeService.queryZengsong(organId, startTime, endTime);
	}

	@RequestMapping("/queryWaimai")
	@ResponseBody
	public List<WWaimai> queryWaimai(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return dailyIncomeService.queryWaimai(organId, startTime, endTime);
	}
	@RequestMapping("/exportUser")
	public ModelAndView exportUser(@RequestParam(required = false) Date startTime,
								   @RequestParam(required = false) Date endTime,@RequestParam(required = false) String queryType, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		List list = new ArrayList<>();
		Map dataSet = new HashMap();

		String URL = "";
		String fileName = "";

		if (!StringUtils.isEmpty(queryType) && "1".equals(queryType)) {
			list = dailyIncomeService.queryUser(organId, startTime, endTime);
			URL = "/WEB-INF/template/template_daily1_export.xls";
			fileName = "普通会员记录.xls";
		} else if (!StringUtils.isEmpty(queryType) && "2".equals(queryType)) {
			list = dailyIncomeService.queryUserCard(organId, startTime, endTime);
			URL = "/WEB-INF/template/template_daily2_export.xls";
			fileName = "会员详细消费记录.xls";
		} else if (!StringUtils.isEmpty(queryType) && "3".equals(queryType)) {
			list = dailyIncomeService.queryUserCardNew(organId, startTime, endTime);
			URL = "/WEB-INF/template/template_daily3_export.xls";
			fileName = "会员办卡记录.xls";
		} else if (!StringUtils.isEmpty(queryType) && "4".equals(queryType)) {
			list = dailyIncomeService.queryZengsong(organId, startTime, endTime);
			List list1 = dailyIncomeService.queryUserCardZK(organId, startTime, endTime);
			userpartService.getDetails(list1);
			dataSet.put("zk", JsonUtils.fromJson(JsonUtils.toJson(list1), List.class));
			URL = "/WEB-INF/template/template_daily4_export.xls";
			fileName = "折扣卡消费记录.xls";
		}else if (!StringUtils.isEmpty(queryType) && "5".equals(queryType)) {
			list = dailyIncomeService.queryUserCardXufei(organId, startTime, endTime);
			URL = "/WEB-INF/template/template_daily5_export.xls";
			fileName = "会员续费记录.xls";
		}else if (!StringUtils.isEmpty(queryType) && "6".equals(queryType)) {
			list = dailyIncomeService.queryWaimai(organId, startTime, endTime);
			URL = "/WEB-INF/template/template_daily6_export.xls";
			fileName = "外卖记录.xls";
		}
		if(!StringUtils.isEmpty(queryType) && !("6".equals(queryType))){
			userpartService.getDetails(list);
		}
		File template = new File(request.getSession().getServletContext().getRealPath(URL));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));


		dataSet.put("userpart", JsonUtils.fromJson(JsonUtils.toJson(list), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, fileName);
		} catch (Exception e) {
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
