package com.mrmf.moduleweb.user;

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

import com.mrmf.entity.user.Usersort;
import com.mrmf.service.code.CodeService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 用户类型表
 */
@Controller
@RequestMapping("/user/usersort")
public class UsersortController {

	@Autowired
	private CodeService codeService;

	@Autowired
	private UsersortService usersortService;

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
				request.setAttribute("usersortTypes", codeService.queryByType("usersortType"));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usersort/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Usersort> query(HttpServletRequest request) throws Exception {
		FlipInfo<Usersort> fpi = new FlipPageInfo<Usersort>(request);
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
		fpi = usersortService.query(fpi);
		return fpi;
	}

	@RequestMapping("/queryById")
	@ResponseBody
	public Usersort queryById(String usersort, HttpServletRequest request) throws Exception {
		return usersortService.queryById(usersort);
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String usersortId, HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("usersortTypes", codeService.queryByType("usersortType"));
				request.setAttribute("smallsorts", smallsortService.findAll(organId));
			}
		}

		Usersort usersort;
		if (!StringUtils.isEmpty(usersortId)) {
			usersort = usersortService.queryById(usersortId);
		} else {
			usersort = new Usersort();
			usersort.setWaimaizhekou(100); // 外卖折扣默认100%
		}

		request.setAttribute("ffusersort", usersort);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usersort/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Usersort usersort, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String usersortId = usersort.get_id();
		ReturnStatus status = usersortService.upsert(usersort);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			usersort.set_id(usersortId);// 恢复之前的id
			request.setAttribute("ffusersort", usersort);
			request.setAttribute("returnStatus", status);
			mv.setViewName("user/usersort/upsert");
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
