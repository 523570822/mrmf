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

import com.mrmf.entity.staff.StaffFloatTicheng;
import com.mrmf.service.staff.ticheng.StaffFloatTichengService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 浮动提成
 */
@Controller
@RequestMapping("/staff/staffFloatTicheng")
public class StaffFloatTichengController {

	@Autowired
	private StaffFloatTichengService staffFloatTichengService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/staffFloatTicheng/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<StaffFloatTicheng> query(HttpServletRequest request) throws Exception {
		FlipInfo<StaffFloatTicheng> fpi = new FlipPageInfo<StaffFloatTicheng>(request);
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
		fpi = staffFloatTichengService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String staffFloatTichengId, HttpServletRequest request)
			throws Exception {
		StaffFloatTicheng staffFloatTicheng;
		if (!StringUtils.isEmpty(staffFloatTichengId)) {
			staffFloatTicheng = staffFloatTichengService.queryById(staffFloatTichengId);
		} else {
			staffFloatTicheng = new StaffFloatTicheng();
		}

		request.setAttribute("ffstaffFloatTicheng", staffFloatTicheng);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("staff/staffFloatTicheng/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(StaffFloatTicheng staffFloatTicheng, BindingResult results, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = staffFloatTicheng.get_id();
		ReturnStatus status = staffFloatTichengService.upsert(staffFloatTicheng);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			staffFloatTicheng.set_id(originId);// 恢复之前的id
			request.setAttribute("ffstaffFloatTicheng", staffFloatTicheng);
			request.setAttribute("returnStatus", status);
			mv.setViewName("staff/staffFloatTicheng/upsert");
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
