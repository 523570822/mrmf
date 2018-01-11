package com.mrmf.moduleweb.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeSysCardChargeHis;
import com.mrmf.entity.user.Usercard;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.rank.RankService;
import com.mrmf.service.usercard.UsercardService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;

/**
 * 平台店铺会员卡充值相关
 */
@Controller
@RequestMapping("/weixin/charge")
public class WeChargeController {

	@Autowired
	private UsercardService usercardService;
	@Autowired
	RankService rankservice;
	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private OrganService organService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<WeBCity> city = rankservice.cityList();
		request.setAttribute("ffcitys", city);
		mv.setViewName("weixin/charge/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Usercard> query(HttpServletRequest request, String city, String district, String region,
			String organName, String organAbname) throws Exception {
		FlipInfo<Usercard> fpi = new FlipPageInfo<Usercard>(request);
		List<Organ> organList = usercardService.queryOrganList(city, district, region, organName, organAbname);
		String organIds = "";

		// 根据管理员能管辖的区域范围过滤
		Account account = tokenManager.getCurrentAccount();
		List<String> ids = organService.queryAdminOrganIds(account);
		for (Organ organ : organList) {
			if (ids.size() == 0 || ids.contains(organ.get_id()))
				organIds += organ.get_id() + ",";
		}
		if (organIds.length() > 0) {
			organIds = organIds.substring(0, organIds.length() - 1);
			fpi.getParams().put("in:organId|array", organIds);
			fpi.getParams().remove("city");
			fpi.getParams().remove("district");
			fpi.getParams().remove("region");
			fpi.getParams().remove("organName");
			fpi.getParams().remove("organAbname");
			fpi = usercardService.sysQuerySysCard(fpi);
		}

		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam String organId, HttpServletRequest request) throws Exception {
		request.setAttribute("ffusercard", usercardService.sysQuery(organId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/charge/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(@RequestParam String organId, @RequestParam String _id, @RequestParam double charge,
			HttpServletRequest request) throws Exception {
		ReturnStatus returnStatus = usercardService.sysCharge(_id, charge);
		if (returnStatus.isSuccess()) {
			return toQuery(request);
		} else {
			request.setAttribute("ffusercard", usercardService.sysQuery(organId));
			request.setAttribute("returnStatus", returnStatus);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("weixin/charge/upsert");
			return mv;
		}
	}

	@RequestMapping("/toQueryHis")
	public ModelAndView toQueryHis(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/charge/queryHis");
		return mv;
	}

	@RequestMapping("/queryHis")
	@ResponseBody
	public FlipInfo<WeSysCardChargeHis> queryHis(HttpServletRequest request) throws Exception {
		FlipInfo<WeSysCardChargeHis> fpi = new FlipPageInfo<WeSysCardChargeHis>(request);
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
		fpi = usercardService.sysQueryChargeHis(fpi);
		return fpi;
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
