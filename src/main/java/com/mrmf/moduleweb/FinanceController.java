package com.mrmf.moduleweb;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeSysCardChargeHis;
import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.service.finance.FinanceService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.usermy.UserMyService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;

/**
 * 公司信息管理相关
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {
	@Autowired
	private FinanceService financeService;
	@Autowired
	private UserMyService userMyService;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private OrganService organService;

	@RequestMapping("/uservallet/toQueryUserVallet")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/uservallet/query");
		return mv;
	}

	@RequestMapping("/uservallet/queryUserVallet")
	@ResponseBody
	public FlipInfo<WeUserWalletHis> queryUserVallet(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		String userName = request.getParameter("userName");
		FlipInfo<WeUserWalletHis> fpi = new FlipPageInfo<WeUserWalletHis>(request);
		fpi.setSortField("userId");
		fpi.setSortOrder("AES");
		fpi.getParams().remove("userName");
		if (!StringUtils.isEmpty(userName)) {
			List<String> userIds = financeService.queryUserIds(userName);
			fpi.getParams().put("in:userId|list", userIds.toString());
		}
		fpi = financeService.queryUserVallet(fpi);
		for (WeUserWalletHis weUserWalletHis : fpi.getData()) {
			if (weUserWalletHis != null) {
				BigDecimal bg = new BigDecimal(weUserWalletHis.getAmount());
				double roundAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				weUserWalletHis.setAmount(roundAmount);
				String userId = weUserWalletHis.getUserId();
				User user = userMyService.queryUserById(userId);
				if (user != null) {
					weUserWalletHis.setUserName(user.getNick());
				}
			}
		}
		return fpi;
	}

	@RequestMapping("/uservallet/exportUserVallet")
	public ModelAndView exportUserVallet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		File template = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/template_weuserwallethis_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		String startTimeStr = request.getParameter("gte:createTime|date");
		String endTimeStr = request.getParameter("lte:createTime|date");
		String userName = request.getParameter("userName");
		List<WeUserWalletHis> weUserWalletHis = financeService.queryUserWalletHisAsExport(userName, organId,
				startTimeStr, endTimeStr);
		Map dataSet = new HashMap();
		dataSet.put("weUserWalletHis", JsonUtils.fromJson(JsonUtils.toJson(weUserWalletHis), List.class));
		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response, 1);
			ds.download(outputExcel, "用户钱包记录表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*--------------红包记录表-----------------*/
	@RequestMapping("/redpacket/toQueryStaffRedPacket")
	public ModelAndView toQueryStaffRedPacket(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/redpacket/query");
		return mv;
	}

	@RequestMapping("/redpacket/queryStaffRedPacket")
	@ResponseBody
	public FlipInfo<WeRed> queryStaffRedPacket(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		String staffName = request.getParameter("staffName");
		FlipInfo<WeRed> fpi = new FlipPageInfo<WeRed>(request);
		fpi.setSortField("staffId");
		fpi.setSortOrder("AES");
		fpi.getParams().remove("staffName");
		if (!StringUtils.isEmpty(staffName)) {
			List<String> staffIds = financeService.queryStaffIds(staffName);
			fpi.getParams().put("in:senderId|list", staffIds.toString());
		}
		fpi.getParams().put("ne:senderId", "0");
		fpi = financeService.queryStaffWeRed(fpi);
		for (WeRed weRed : fpi.getData()) {
			if (weRed != null) {
				Staff staff = userMyService.findStaffById(weRed.getSenderId());
				Organ organ = userMyService.findOrganById(weRed.getOrganId());
				weRed.setStaffName(staff.getName());
				weRed.setOrganName(organ.getName());
			}
		}
		return fpi;
	}

	@RequestMapping("/redpacket/exportStaffRedPacket")
	public ModelAndView exportStaffRedPacket(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		File template = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/template_staffredpacket_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		String startTimeStr = request.getParameter("gte:createTime|date");
		String endTimeStr = request.getParameter("lte:createTime|date");
		String staffName = request.getParameter("staffName");
		List<WeRed> staffRedPacket = financeService.queryWeRedsAsExport(staffName, startTimeStr, endTimeStr);

		Map dataSet = new HashMap();
		dataSet.put("staffRedPacket", JsonUtils.fromJson(JsonUtils.toJson(staffRedPacket), List.class));
		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response, 1);
			ds.download(outputExcel, "技师红包记录表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*--------------平台卡记录表-----------------*/
	@RequestMapping("/syscard/toQuerySyscard")
	public ModelAndView toQuerySyscard(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/syscard/query");
		return mv;
	}

	@RequestMapping("/syscard/querySyscard")
	@ResponseBody
	public FlipInfo<WeSysCardChargeHis> querySyscard(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		String organName = request.getParameter("organName");
		FlipInfo<WeSysCardChargeHis> fpi = new FlipPageInfo<WeSysCardChargeHis>(request);
		fpi.setSortField("organId");
		fpi.setSortOrder("AES");
		fpi.getParams().remove("organName");
		if (!StringUtils.isEmpty(organName)) {
			List<String> organIds = financeService.queryOrganIds(organName);
			fpi.getParams().put("in:organId|list", organIds.toString());
		}

		Account account = tokenManager.getCurrentAccount();
		List<String> ids = organService.queryAdminOrganIds(account);
		if (ids.size() > 0) {
			fpi.getParams().put("in:organId|array", org.apache.commons.lang3.StringUtils.join(ids.toArray(), ','));
		}

		fpi = financeService.querySysCard(fpi);
		for (WeSysCardChargeHis weSysCardChargeHis : fpi.getData()) {
			if (weSysCardChargeHis != null) {
				Organ organ = userMyService.findOrganById(weSysCardChargeHis.getOrganId());
				if (organ != null) {
					weSysCardChargeHis.setOrganName(organ.getName());
				}
			}
		}
		return fpi;
	}

	@RequestMapping("/syscard/exportSyscard")
	public ModelAndView exportSyscard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		File template = new File(
				request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_sysycard_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		String startTimeStr = request.getParameter("gte:createTime|date");
		String endTimeStr = request.getParameter("lte:createTime|date");
		String organName = request.getParameter("organName");
		List<WeSysCardChargeHis> sysCard = financeService.querySyscardAsExport(organName, startTimeStr, endTimeStr);

		Map dataSet = new HashMap();
		dataSet.put("sysCard", JsonUtils.fromJson(JsonUtils.toJson(sysCard), List.class));
		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response, 1);
			ds.download(outputExcel, "平台卡充值记录表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*--------------微信分账记录表-----------------*/
	@RequestMapping("/weFenZhang/toQueryFenZhang")
	public ModelAndView toQueryFenZhang(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("finance/weFenZhang/query");
		return mv;
	}

	@RequestMapping("/weFenZhang/queryFenZhang")
	@ResponseBody
	public FlipInfo<WeUserPayFenzhang> queryFenZhang(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		String userName = request.getParameter("userName");
		FlipInfo<WeUserPayFenzhang> fpi = new FlipPageInfo<WeUserPayFenzhang>(request);
		fpi.setSortField("userId");
		fpi.setSortOrder("AES");
		fpi.getParams().remove("userName");
		if (!StringUtils.isEmpty(userName)) {
			List<String> userIds = financeService.queryUserIds(userName);
			fpi.getParams().put("in:userId|list", userIds.toString());
		}

		Account account = tokenManager.getCurrentAccount();
		List<String> ids = organService.queryAdminOrganIds(account);
		if (ids.size() > 0) {
			fpi.getParams().put("in:organId|array", org.apache.commons.lang3.StringUtils.join(ids.toArray(), ','));
		}

		fpi = financeService.queryFenZhang(fpi);
		return fpi;
	}

	@RequestMapping("/weFenZhang/exportFenZhang")
	public ModelAndView exportFenZhang(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		File template = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/template_fengzhang_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));
		String startTimeStr = request.getParameter("gte:createTime|date");
		String endTimeStr = request.getParameter("lte:createTime|date");
		String userName = request.getParameter("userName");
		List<WeUserPayFenzhang> fenZhang = financeService.queryFenZhangAsExport(userName, startTimeStr, endTimeStr);

		Map dataSet = new HashMap();
		dataSet.put("fenZhang", JsonUtils.fromJson(JsonUtils.toJson(fenZhang), List.class));
		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response, 1);
			ds.download(outputExcel, "用户分账记录表.xls");
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
