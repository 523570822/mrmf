package com.mrmf.moduleweb.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.bean.UserTixianSum;
import com.mrmf.service.user.weuserpay.WeUserPayOrderService;
import com.mrmf.service.userPay.UserPayService;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 微信支付相关
 */
@Controller
@RequestMapping("/weixin/userpay")
public class WeUserPayOrderController {

	@Autowired
	private WeUserPayOrderService weUserPayOrderService;

	@Autowired
	private UserPayService userPayService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/userpay/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<WeUserPayOrder> query(HttpServletRequest request) throws Exception {
		FlipInfo<WeUserPayOrder> fpi = new FlipPageInfo<WeUserPayOrder>(request);
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
		fpi = weUserPayOrderService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toQueryTixian")
	public ModelAndView toQueryTixian(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/userpay/queryTixian");
		return mv;
	}

	@RequestMapping("/queryTixian")
	@ResponseBody
	public FlipInfo<WeUserWalletHis> queryTixian(HttpServletRequest request) throws Exception {
		FlipInfo<WeUserWalletHis> fpi = new FlipPageInfo<WeUserWalletHis>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			throw new BaseException("平台级操作！");
		}
		fpi = userPayService.findTixian(fpi);
		return fpi;
	}

	@RequestMapping("/queryTixianSum")
	@ResponseBody
	public UserTixianSum queryTixianSum(@RequestParam(required = false) String type,
			@RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime)
					throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			throw new BaseException("平台级操作！");
		}
		return userPayService.tixianSum(type, startTime, endTime);
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
