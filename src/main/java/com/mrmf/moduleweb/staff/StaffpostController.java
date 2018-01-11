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

import com.mrmf.entity.staff.Staffpost;
import com.mrmf.service.staff.staffpost.StaffpostService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 岗位
 */
@Controller
@RequestMapping("/staff/staffpost")
public class StaffpostController {

	@Autowired
	private StaffpostService staffpostService;

	@Autowired
	private SmallsortService smallsortService;

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
		mv.setViewName("staff/staffpost/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Staffpost> query(HttpServletRequest request) throws Exception {
		FlipInfo<Staffpost> fpi = new FlipPageInfo<Staffpost>(request);
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
		fpi = staffpostService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String staffpostId, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("smallsorts", smallsortService.findAll(organId));
			}
		}

		Staffpost staffpost;
		if (!StringUtils.isEmpty(staffpostId)) {
			staffpost = staffpostService.queryById(staffpostId);
		} else {
			staffpost = new Staffpost();
		}

		request.setAttribute("ffstaffpost", staffpost);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/staffpost/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Staffpost staffpost, BindingResult results, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = staffpost.get_id();
		ReturnStatus status = staffpostService.upsert(staffpost);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			staffpost.set_id(originId);// 恢复之前的id
			request.setAttribute("ffstaffpost", staffpost);
			request.setAttribute("returnStatus", status);
			mv.setViewName("staff/staffpost/upsert");
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
