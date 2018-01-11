package com.mrmf.moduleweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganSetting;
import com.mrmf.service.common.Configure;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.QRCodeUtil;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;

/**
 * 公司信息管理相关
 */
@Controller
@RequestMapping("/organ")
public class OrganController {

	@Autowired
	private OrganService organService;

	@Autowired
	private TokenManager tokenManager;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(@RequestParam(required = false) String parentId, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				parentId = organId;
			}
		}
		if (!StringUtils.isEmpty(parentId) && !"0".equals(parentId)) {
			Organ organ = organService.queryById(parentId);
			request.setAttribute("parentOrgan", organ);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("organ/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Organ> query(HttpServletRequest request) throws Exception {
		FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);

		Account account = tokenManager.getCurrentAccount();
		if ("admin".equals(account.getAccountType())) {
			List<String> cityList = account.getCityList();
			List<String> districtList = account.getDistrictList();
			if (cityList.size() > 0) {
				fpi.getParams().put("in:city|array",
						org.apache.commons.lang3.StringUtils.join(cityList.toArray(), ','));
			}
			if (districtList.size() > 0) {
				fpi.getParams().put("in:district|array",
						org.apache.commons.lang3.StringUtils.join(districtList.toArray(), ','));
			}
		}
		fpi = organService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String organId, HttpServletRequest request)
			throws Exception {
		boolean isOgAdmin = false;
		if (!"true".equals(request.getParameter("isNew"))) {
			Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
			String oid = (String) MAppContext.getSessionVariable("organId");
			if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
				isOgAdmin = true;
				if (StringUtils.isEmpty(oid)) {
					throw new BaseException("当前登录企业信息缺失！");
				} else if (StringUtils.isEmpty(organId)) {
					organId = oid;
				}
			}
		}
		Organ organ;
		if (!StringUtils.isEmpty(organId)) {
			organ = organService.queryById(organId);
		} else {
			organ = new Organ();
			if (isOgAdmin)
				organ.setValid(0);
			else
				organ.setValid(1);
		}
		request.setAttribute("fforgan", organ);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("organ/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Organ organ, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = organ.get_id();
		ReturnStatus status = organService.upsert(organ);
		if (status.isSuccess()) {
			Boolean isOrganAdmin = (Boolean) request.getSession().getAttribute("isOrganAdmin");
			String oid = (String) MAppContext.getSessionVariable("organId");
			if (isOrganAdmin != null && isOrganAdmin && oid.equals(originId)) { // 企业管理员修改本组织信息跳转
				request.setAttribute("fforgan", organ);
				mv.setViewName("organ/upsert");
			} else { // 超级管理员跳转
				mv.setViewName("organ/query");
			}

		} else {
			organ.set_id(originId);// 恢复之前的id
			request.setAttribute("fforgan", organ);
			request.setAttribute("returnStatus", status);
			mv.setViewName("organ/upsert");
		}
		return mv;
	}

	@RequestMapping("/changeState")
	@ResponseBody
	public ReturnStatus changeState(int state, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) request.getSession().getAttribute("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) {
			return organService.changeState(organId, state);
		}

		return new ReturnStatus(false, "仅公司管理员可操作");
	}

	@RequestMapping("/toChangeCardOrganIds")
	public ModelAndView toChangeCardOrganIds(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) request.getSession().getAttribute("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) {
			Organ organ = organService.queryById(organId);
			if ("0".equals(organ.getParentId())) {
				request.setAttribute("fforgan", organ);
				request.setAttribute("organList", organService.queryByParentId(organId));
				ModelAndView mv = new ModelAndView();
				mv.setViewName("organ/changeCardOrganIds");
				return mv;
			} else {
				throw new BaseException("仅总公司管理员可操作");
			}
		} else
			throw new BaseException("仅公司管理员可操作");
	}

	@RequestMapping("/changeCardOrganIds")
	public ModelAndView changeCardOrganIds(String[] cardOrganIds, HttpServletRequest request) throws Exception {
		if (cardOrganIds == null) {
			cardOrganIds = new String[0];
		}
		Boolean isOrganAdmin = (Boolean) request.getSession().getAttribute("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) {
			ReturnStatus status = organService.changeCardOrganIds(organId, Arrays.asList(cardOrganIds));
			if (status.isSuccess())
				return toQuery(null, request);
			else
				throw new BaseException(status.getMessage());
		} else
			throw new BaseException("仅公司管理员可操作");
	}

	@RequestMapping("/toMap")
	public ModelAndView toMap(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("organ/map");
		return mv;
	}

	@RequestMapping("/enable/{organId}")
	@ResponseBody
	public ReturnStatus enable(@PathVariable String organId) throws Exception {
		ReturnStatus status = organService.enable(organId);
		return status;
	}

	@RequestMapping("/disable/{organId}")
	@ResponseBody
	public ReturnStatus disable(@PathVariable String organId) throws Exception {
		ReturnStatus status = organService.disable(organId);
		return status;
	}

	@RequestMapping(value = "/qr/{organId}")
	@ResponseBody
	public ReturnStatus qrimage(@PathVariable String organId, HttpServletRequest req, HttpServletResponse res) {
		try {
			InputStream is = new FileInputStream(new File(req.getRealPath("icon_logo.png")));
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Configure.organAppID+"&redirect_uri=";
			url += URLEncoder.encode(Configure.DOMAIN_URL + "/mrmf/w/pay/wxSaoMaToPay.do", "GBK");
			url += "&response_type=code&scope=snsapi_userinfo&state=" + organId + "#wechat_redirect";
			// 老的地址 url = Configure.DOMAIN_URL + "/mrmf/w/pay/toPay?organId=" +
			// organId;
			QRCodeUtil.encodeQRCode(url, res.getOutputStream(), 2, is);
			is.close();
			return null;
		} catch (IOException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	@RequestMapping("/print")
	public ModelAndView print(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("organ/print");
		return mv;
	}

	@RequestMapping("/toSetting")
	public ModelAndView toSetting(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}

		request.setAttribute("ffsetting", organService.querySetting(organId));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("organ/setting");
		return mv;
	}

	@RequestMapping("/upsertSetting")
	public ModelAndView upsertSetting(OrganSetting setting, BindingResult results, HttpServletRequest request)
			throws Exception {
		ReturnStatus status = organService.upsertSetting(setting);
		if (!status.isSuccess()) {
			request.setAttribute("returnStatus", status);
		}
		return toSetting(request);
	}

	@RequestMapping("/toSettingTichengLiushui")
	public ModelAndView toSettingTichengLiushui(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}

		request.setAttribute("ffsetting", organService.querySetting(organId));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("organ/settingTichengLiushui");
		return mv;
	}

	@RequestMapping("/upsertSettingTichengLiushui")
	public ModelAndView upsertSettingTichengLiushui(String _id, String[] tichengLiushui, HttpServletRequest request)
			throws Exception {
		if (tichengLiushui == null) {
			tichengLiushui = new String[0];
		}
		ReturnStatus status = organService.upsertSettingTichengLiushui(_id, Arrays.asList(tichengLiushui));
		if (!status.isSuccess()) {
			request.setAttribute("returnStatus", status);
			return toSettingTichengLiushui(request);
		} else {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("staff/staffFloatTicheng/query");
			return mv;
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
