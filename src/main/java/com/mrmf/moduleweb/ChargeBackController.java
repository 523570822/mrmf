package com.mrmf.moduleweb;

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

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.kucun.KucunService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.user.charge.ChargeBackService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

@Controller
@RequestMapping("chargeback")
public class ChargeBackController {
	@Autowired
	private ChargeBackService chargeBackService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private UsersortService usersortService; // 会员类型
	@Autowired
	private SmallsortService smallsortService; // 服务项目
	@Autowired
	private BigsortService bigsortService;

	@Autowired
	private KucunService kucunService;
	@Autowired
	private OrganService organService;

	@RequestMapping("/toQuery")
	public ModelAndView toPinpaiQuery(HttpServletRequest request) throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/query/chargeback");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public <T> List<T> vipUserQuery(String xiaopiao, String searchType, HttpServletRequest request) throws Exception {
		// FlipInfo<User> fpi = new FlipPageInfo<User>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {

			}

		}
		// searchType user非会员
		if ("user".equals(searchType)) {
			List<Userpart> userpartList = chargeBackService.queryUserPartUser(xiaopiao,organId);
			return (List<T>) userpartList;
		} else if ("vipuser".equals(searchType)) {
			List<Userpart> userpartList = chargeBackService.queryUserPartVipUser(xiaopiao,organId);
			return (List<T>) userpartList;
		} else if ("takeout".equals(searchType)) {
			List<WWaimai> waimaiList = chargeBackService.queryWaimai(xiaopiao,organId);
			return (List<T>) waimaiList;
		} else if ("incard".equals(searchType)) {
			List<Userpart> userpartList = chargeBackService.queryInCard(xiaopiao,organId);
			return (List<T>) userpartList;
		} else if ("salecard".equals(searchType)) {
			List<Userpart> userpartList = chargeBackService.querySaleCard(xiaopiao,organId);
			return (List<T>) userpartList;
		}
		return null;
	}

	@RequestMapping("/charge")
	@ResponseBody
	public ReturnStatus sendMessage(@RequestParam(value = "chargeIds[]") String[] chargeIds, String selectType)
			throws Exception {
		if ("takeout".equals(selectType)) {
			return chargeBackService.chargeWaiMai(chargeIds);
		} else {
			return chargeBackService.chargeUserPart(chargeIds);
		}
	}

	@RequestMapping("/toQueryUserpart")
	public ModelAndView toQueryUserpart(HttpServletRequest request) throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		request.setAttribute("ffstaffs", staffService.findAll(organId));
		request.setAttribute("ffsmallsort", smallsortService.findAll(organId));
		request.setAttribute("fforganstaff", staffService.findAll(organId));
		request.setAttribute("ffmembersort", usersortService.findAll(organId));
		request.setAttribute("ffbigsort", bigsortService.findAll(organId));

		ModelAndView mv = new ModelAndView("user/query/chargebackUserpart");
		return mv;
	}

	@RequestMapping("/queryUserpart")
	@ResponseBody
	public FlipInfo<Userpart> queryUserpart(HttpServletRequest request) throws Exception {
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
		String date = (String) fpi.getParams().get("eq:updateTime|date#and");
		if (!StringUtils.isEmpty(date)) {
			fpi.getParams().remove("eq:updateTime|date#and");
			String[] startAndEnd = DateUtil.getDateStartEnd(date);
			fpi.getParams().put("gte:updateTime|date", startAndEnd[0]);
			fpi.getParams().put("lte:updateTime|date+1", startAndEnd[1]);
		}

		fpi.getParams().put("organId", organId); // 只能查询本公司信息

		return chargeBackService.queryUserpart(fpi);

	}

	@RequestMapping("/toQueryWaimai")
	public ModelAndView toQueryWaimai(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		request.setAttribute("ffleibies", kucunService.queryWWupinList(parentId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		request.setAttribute("organSetting", organService.querySetting(organId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/query/chargebackWaimai");
		return mv;
	}

	@RequestMapping("/queryWaimai")
	@ResponseBody
	public FlipInfo<WWaimai> queryWaimai(HttpServletRequest request) throws Exception {
		FlipInfo<WWaimai> fpi = new FlipPageInfo<WWaimai>(request);
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
		return chargeBackService.queryWaimai(fpi);
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
