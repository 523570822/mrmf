package com.mrmf.moduleweb.weixin;

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

import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.bean.UserPayFenzhangSum;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.staff.ticheng.StaffTichengService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.weuserpay.WeUserPayFenzhangService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 微信订单查询
 */
@Controller
@RequestMapping("/weixin/userpayFenzhang")
public class WeUserPayFenzhangController {

	@Autowired
	private WeUserPayFenzhangService weUserPayFenzhangService;

	@Autowired
	private StaffTichengService staffTichengService;

	@Autowired
	private SmallsortService smallsortService; // 服务项目

	@Autowired
	private StaffService staffService;

	@Autowired
	private OrganService organService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/userpay/fenzhangQuery");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<WeUserPayFenzhang> query(HttpServletRequest request) throws Exception {
		FlipInfo<WeUserPayFenzhang> fpi = new FlipPageInfo<WeUserPayFenzhang>(request);
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
		fpi = weUserPayFenzhangService.query(fpi);
		return fpi;
	}

	@RequestMapping("/handleFenzhangEnter")
	public ModelAndView handleFenzhangEnter(String fenzhangId, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		request.setAttribute("ffsmallsorts", smallsortService.findAllValid(organId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		request.setAttribute("ffstaffTichengs", staffTichengService.findAll(organId));

		request.setAttribute("ffuserpart", weUserPayFenzhangService.handleFenzhangEnter(fenzhangId));
		request.setAttribute("fffenzhang", weUserPayFenzhangService.queryById(fenzhangId));

		request.setAttribute("organSetting", organService.querySetting(organId));
		ModelAndView mv = new ModelAndView("weixin/userpay/fenzhangHandle");
		return mv;
	}

	@RequestMapping("/handleFenzhang")
	public ModelAndView handleFenzhang(Userpart userpart, BindingResult results, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		ReturnStatus status = weUserPayFenzhangService.handleFenzhang(userpart);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			request.setAttribute("ffuserpart", userpart);
			request.setAttribute("returnStatus", status);
			mv.setViewName("weixin/userpay/fenzhangHandle");
		}

		return mv;
	}

	@RequestMapping("/totalOrgan")
	@ResponseBody
	public UserPayFenzhangSum totalOrgan(@RequestParam(required = false) String organId,
			@RequestParam(required = false) Integer state, @RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String oid = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			organId = oid;
		}
		return weUserPayFenzhangService.totalOrgan(organId, state, startTime, endTime);
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
