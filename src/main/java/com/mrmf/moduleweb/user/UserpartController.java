package com.mrmf.moduleweb.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

import com.mrmf.entity.Organ;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Kaidan;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.user.UserService;
import com.mrmf.service.user.incard.IncardService;
import com.mrmf.service.user.inincard.InincardService;
import com.mrmf.service.user.kaidan.KaidanService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.userpart.UserpartService;
import com.mrmf.service.user.usersort.UsersortService;
import com.mrmf.service.waimai.WaimaiService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.DownloadResponse;
import com.osg.framework.util.ExcelUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 消费相关
 */
@Controller
@RequestMapping("/user/userpart")
public class UserpartController {

	@Autowired
	private UserpartService userpartService;

	@Autowired
	private OrganService organService;

	@Autowired
	private KaidanService kaidanService;

	@Autowired
	private UsersortService usersortService; // 会员类型

	@Autowired
	private SmallsortService smallsortService; // 服务项目

	@Autowired
	private StaffService staffService;

	@Autowired
	private InincardService inincardService;

	@Autowired
	private IncardService incardService;

	@Autowired
	private UserService memberService;

	@Autowired
	private WaimaiService waimaiService;

	private static Logger logger = Logger.getLogger(UserpartController.class);

	@RequestMapping("/query2")
	@ResponseBody
	public List<Userpart> query2(@RequestParam(required = false) String condition, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		return userpartService.queryByCondition(organId, condition);
	}

	@RequestMapping("/query")
	@ResponseBody
	public List<Userpart> query(String kaidanId, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		return userpartService.query(organId, kaidanId);
	}

	@RequestMapping("/queryByFpi")
	@ResponseBody
	public FlipInfo<Userpart> queryByFpi(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		FlipInfo<Userpart> fpi = new FlipPageInfo<Userpart>(request);
		// eq:createTime|date#and=2016-09-12 lte:createTime|date+1
		// gte:createTime|dat
		String date = (String) fpi.getParams().get("eq:createTime|date#and");
		if (!StringUtils.isEmpty(date)) {
			fpi.getParams().remove("eq:createTime|date#and");
			String[] startAndEnd = DateUtil.getDateStartEnd(date);
			fpi.getParams().put("gte:createTime|date", startAndEnd[0]);
			fpi.getParams().put("lte:createTime|date+1", startAndEnd[1]);
		}
		String parOrganId = (String) fpi.getParams().get("organId");
		if (StringUtils.isEmpty(parOrganId)) {
			fpi.getParams().put("organId", organId); // 只能查询本公司信息
			return userpartService.queryByFpi(fpi);
		} else {
			List<Organ> organList = organService.queryOrganListByParentId(organId);
			boolean flag = false;
			if (organList != null && organList.size() > 0) {
				for (Organ organ : organList) {
					if (parOrganId.equals(organ.get_id())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					return userpartService.queryByFpi(fpi);
				}
			}
		}
		return fpi;

	}

	@RequestMapping("/download")
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		FlipInfo<Userpart> fpi = new FlipPageInfo<Userpart>(request);
		// eq:createTime|date#and=2016-09-12 lte:createTime|date+1
		// gte:createTime|date
		String date = (String) fpi.getParams().get("eq:createTime|date#and");
		String queryType = (String) fpi.getParams().get("queryType");
		fpi.getParams().remove("queryType");
		if (!StringUtils.isEmpty(date)) {
			fpi.getParams().remove("eq:createTime|date#and");
			String[] startAndEnd = DateUtil.getDateStartEnd(date);
			fpi.getParams().put("gte:createTime|date", startAndEnd[0]);
			fpi.getParams().put("lte:createTime|date+1", startAndEnd[1]);
		}
		List<Userpart> list = null;
		String parOrganId = (String) fpi.getParams().get("organId");
		if (StringUtils.isEmpty(parOrganId)) {
			fpi.getParams().put("organId", organId); // 只能查询本公司信息
			list = userpartService.queryUserPartByFpi(fpi);
		} else {
			List<Organ> organList = organService.queryOrganListByParentId(organId);
			boolean flag = false;
			if (organList != null && organList.size() > 0) {
				for (Organ organ : organList) {
					if (parOrganId.equals(organ.get_id())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					list = userpartService.queryUserPartByFpi(fpi);
				}
			}
		}
		String URL = "";
		String fileName = "";
		if (!StringUtils.isEmpty(queryType) && "vip".equals(queryType)) {
			URL = "/WEB-INF/template/template_userpart_export.xls";
			fileName = "会员明细.xls";
		} else if (!StringUtils.isEmpty(queryType) && "xiaofei".equals(queryType)) {
			URL = "/WEB-INF/template/template_userpart_export_xiaofei.xls";
			fileName = "会员消费明细.xls";
			for(Userpart u:list){
				u.setMoney_xiaofei(u.getMoney_xiaofei()-u.getMoney5());
			}
		} else if (!StringUtils.isEmpty(queryType) && "zhekou".equals(queryType)) {
			URL = "/WEB-INF/template/template_userpart_export_zhekou.xls";
			fileName = "折扣卡消费明细.xls";
		} else if (!StringUtils.isEmpty(queryType) && "xufei".equals(queryType)) {
			URL = "/WEB-INF/template/template_userpart_export_xufei.xls";
			fileName = "会员续费明细.xls";
		}
		File template = new File(request.getSession().getServletContext().getRealPath(URL));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));

		Map dataSet = new HashMap();
		dataSet.put("userpart", JsonUtils.fromJson(JsonUtils.toJson(list), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/queryByIncard")
	@ResponseBody
	public List<Userpart> queryByIncard(String incardId, int type, boolean all, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		String typeFlag = request.getParameter("typeFlag");
		if("true".equals(typeFlag)){
			List<Userpart> result = userpartService.queryByIncardId(incardId, type, all,true);
			return result;
		}
		List<Userpart> result = userpartService.queryByIncardId(incardId, type, all,false);
		return result;
	}

	@RequestMapping("/upsert")
	@ResponseBody
	public ReturnStatus upsert(Userpart userpart, BindingResult results, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		userpart.setOrganId(organId);

		return userpartService.upsert(userpart);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public ReturnStatus remove(String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		return userpartService.remove(organId, id);
	}

	@RequestMapping("/jiezhang")
	@ResponseBody
	public ReturnStatus jiezhang(@RequestParam(required = false) String kaidanId, String[] userpartIds,
			double money_yinhang_money, double money_li_money, String money_lijuan, double money_cash, double money3)
					throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		return userpartService.jiezhang(organId, userpartIds, kaidanId, money_yinhang_money, money_li_money,
				money_lijuan, money_cash, money3);
	}

	/**
	 * 打印
	 * 
	 * @param type
	 * @param ids
	 *            打印的消费记录，userpart表
	 * @param wmids
	 *            打印的外卖id，wWaimai表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/print")
	public ModelAndView print(@RequestParam(required = false) int type, @RequestParam(required = false) String[] ids,
			@RequestParam(required = false) String[] wmids, HttpServletRequest request) throws Exception {
		if (ids == null && wmids == null) {
			throw new BaseException("请选择要打印小票的消费信息或外卖信息！");
		}
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		String xiaopiao = null;
		Kaidan kaidan = null;
		Userpart userpart = null;
		String incardId = null;
		String inincardId = null;
		if (ids != null && ids.length > 0) {
			List<Userpart> userparts = userpartService.queryByIds(Arrays.asList(ids));
			if (userparts.size() > 0) {
				userpart = userparts.get(0);
				kaidan = kaidanService.queryById(userpart.getKaidanId());
				request.setAttribute("kaidan", kaidan);
			}
			for (Userpart up : userparts) {
				if (StringUtils.isEmpty(incardId) && up.getType() == 1) // 会员卡消费
					incardId = up.getIncardId();
				if (StringUtils.isEmpty(inincardId) && up.getType() == 11) // 子卡消费
					inincardId = up.getInincardId();
			}
			if (userparts.size() > 0) {
				// request.setAttribute("xiaopiaoCode",
				// userpartService.getXiaopiaoCode(organId,
				// Arrays.asList(ids)));
				xiaopiao = userparts.get(0).getXiaopiao();
				request.setAttribute("xiaopiaoCode", xiaopiao);
			}
			request.setAttribute("userparts", userparts);
		}
		if (wmids != null && wmids.length > 0) {
			List<WWaimai> waimais = waimaiService.queryByIds(xiaopiao, Arrays.asList(wmids));
			if (kaidan == null && waimais.size() > 0) {
				WWaimai waimai = waimais.get(0);
				kaidan = kaidanService.queryById(waimai.getKaidanId());
				request.setAttribute("kaidan", kaidan);
			}
			if (xiaopiao == null && waimais.size() > 0) {
				// request.setAttribute("xiaopiaoCode",
				// userpartService.getXiaopiaoCode(organId,
				// Arrays.asList(ids)));
				xiaopiao = waimais.get(0).getXiaopiao();
				request.setAttribute("xiaopiaoCode", xiaopiao);
			}
			request.setAttribute("waimais", waimais);
		}

		request.setAttribute("usersorts", usersortService.findAll(organId));
		request.setAttribute("smallsorts", smallsortService.findAll(organId));
		request.setAttribute("staffs", staffService.findAll(organId));

		request.setAttribute("date", DateUtil.format(DateUtil.currentDate(), DateUtil.YMDHMS_PATTERN));
		request.setAttribute("organ", organService.queryById(organId));
		request.setAttribute("organSetting", organService.querySetting(organId));

		ModelAndView mv = null;
		if (type == 0) { // 非会员消费、办会员卡小票打印
			mv = new ModelAndView("user/print_xiaofei");
		} else if (type == 1) { // 会员卡消费小票打印
			if (userpart != null) {
				request.setAttribute("incard", incardService.queryById(userpart.getIncardId()));
				request.setAttribute("user", memberService.queryById(userpart.getUserId()));
			}
			mv = new ModelAndView("user/print_xiaofei_incard");
		} else if (type == 2) { // 子卡消费小票打印
			if (userpart != null) {
				request.setAttribute("incard", incardService.queryById(userpart.getIncardId()));
				request.setAttribute("inincard", inincardService.queryById(userpart.getInincardId()));
				request.setAttribute("user", memberService.queryById(userpart.getUserId()));
			}
			mv = new ModelAndView("user/print_xiaofei_inincard");
		} else if (type == 3) { // 会员卡续费打印
			if (userpart != null) {
				request.setAttribute("incard", incardService.queryById(userpart.getIncardId()));
				request.setAttribute("user", memberService.queryById(userpart.getUserId()));
			}
			mv = new ModelAndView("user/print_xufei");
		} else if (type == 10) { // 会员卡和子卡消费小票打印
			if (userpart != null) {
				if (!StringUtils.isEmpty(incardId))
					request.setAttribute("incard", incardService.queryById(incardId));
				if (!StringUtils.isEmpty(inincardId))
					request.setAttribute("inincard", inincardService.queryById(inincardId));
				request.setAttribute("user", memberService.queryById(userpart.getUserId()));
			}
			mv = new ModelAndView("user/print_xiaofei_incardwithinincard");
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
