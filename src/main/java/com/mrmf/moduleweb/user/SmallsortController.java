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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.user.Smallsort;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 服务价目表
 */
@Controller
@RequestMapping("/user/smallsort")
public class SmallsortController {

	@Autowired
	private BigsortService bigsortService;

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
				request.setAttribute("bigsorts", bigsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/smallsort/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Smallsort> query(HttpServletRequest request) throws Exception {
		FlipInfo<Smallsort> fpi = new FlipPageInfo<Smallsort>(request);
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
		fpi = smallsortService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String smallsortId, HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("bigsorts", bigsortService.findAll(organId));
			}
		}

		Smallsort smallsort;
		if (!StringUtils.isEmpty(smallsortId)) {
			smallsort = smallsortService.queryById(smallsortId);
		} else {
			smallsort = new Smallsort();
			smallsort.setValid(true);
		}

		request.setAttribute("ffsmallsort", smallsort);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/smallsort/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Smallsort smallsort, BindingResult results, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = smallsort.get_id();
		ReturnStatus status = smallsortService.upsert(smallsort);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			smallsort.set_id(originId);// 恢复之前的id
			request.setAttribute("ffsmallsort", smallsort);
			request.setAttribute("returnStatus", status);
			mv.setViewName("user/smallsort/upsert");
		}

		return mv;
	}

	@RequestMapping("/enable/{id}")
	@ResponseBody
	public ReturnStatus enable(@PathVariable String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				return smallsortService.enable(organId, id);
			}
		} else {
			throw new BaseException("不是企业管理员！");
		}
	}

	@RequestMapping("/disable/{id}")
	@ResponseBody
	public ReturnStatus disable(@PathVariable String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				return smallsortService.disable(organId, id);
			}
		} else {
			throw new BaseException("不是企业管理员！");
		}
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
