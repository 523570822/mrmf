package com.mrmf.moduleweb.staff;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.staff.StaffTicheng;
import com.mrmf.service.staff.staffpost.StaffpostService;
import com.mrmf.service.staff.ticheng.StaffTichengService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 员工提成设置/分配业绩
 */
@Controller
@RequestMapping("/staff/staffTicheng")
public class StaffTichengController {

	@Autowired
	private StaffTichengService staffTichengService;

	@Autowired
	private SmallsortService smallsortService;

	@Autowired
	private StaffpostService staffpostService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("smallsorts", smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/staffTicheng/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<StaffTicheng> query(HttpServletRequest request) throws Exception {
		FlipInfo<StaffTicheng> fpi = new FlipPageInfo<StaffTicheng>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils.isEmpty((String) fpi.getParams().get("organId")))
					fpi.getParams().put("organId", organId);
			}
		}
		fpi = staffTichengService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String staffTichengId, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("ffsmallsorts", smallsortService.findAll(organId));
				request.setAttribute("staffposts", staffpostService.findAll(organId));
			}
		}

		StaffTicheng staffTicheng;
		if (!StringUtils.isEmpty(staffTichengId)) {
			staffTicheng = staffTichengService.queryById(staffTichengId);
		} else {
			staffTicheng = new StaffTicheng();
		}

		request.setAttribute("ffstaffTicheng", staffTicheng);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/staffTicheng/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(StaffTicheng staffTicheng, BindingResult results, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = staffTicheng.get_id();
		ReturnStatus status = staffTichengService.upsert(staffTicheng);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			staffTicheng.set_id(originId);// 恢复之前的id
			request.setAttribute("ffstaffTicheng", staffTicheng);
			request.setAttribute("returnStatus", status);
			mv.setViewName("staff/staffTicheng/upsert");
		}

		return mv;
	}

	@RequestMapping("/toUpsertBatch")
	public ModelAndView toUpsertBatch(@RequestParam(required = false) String staffTichengId, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("ffsmallsorts", smallsortService.findAll(organId));
				request.setAttribute("staffposts", staffpostService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/staffTicheng/upsertBatch");
		return mv;
	}

	@RequestMapping("/upsertBatch")
	public ModelAndView upsertBatch(String[] smallsortIds, StaffTicheng staffTicheng, BindingResult results,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		ReturnStatus status = staffTichengService.upsertBatch(smallsortIds, staffTicheng);

		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			request.setAttribute("ffstaffTicheng", staffTicheng);
			request.setAttribute("returnStatus", status);
			mv.setViewName("staff/staffTicheng/upsert");
		}

		return mv;
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
