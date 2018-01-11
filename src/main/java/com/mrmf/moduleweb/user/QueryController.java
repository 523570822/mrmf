package com.mrmf.moduleweb.user;

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
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Organ;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.userpart.UserpartService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.framework.BaseException;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 会员查询相关
 */
@Controller
@RequestMapping("/user/query")
public class QueryController {

	@Autowired
	private UserpartService userpartService;

	@Autowired
	private OrganService organService;

	@Autowired
	private UsersortService usersortService; // 会员类型

	@Autowired
	private SmallsortService smallsortService; // 服务项目

	@Autowired
	private StaffService staffService;

	@Autowired
	private BigsortService bigsortService;

	/**
	 * 会员查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/huiyuan")
	public ModelAndView huiyuan(HttpServletRequest request) throws Exception {

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
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView("user/query/huiyuan");
		return mv;
	}

	/**
	 * 非会员消费明细查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/feiHuiyuanXiaofei")
	public ModelAndView feiHuiyuanXiaofei(HttpServletRequest request) throws Exception {

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
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView("user/query/feiHuiyuanXiaofei");
		return mv;
	}

	/**
	 * 会员消费明细查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/huiyuanXiaofei")
	public ModelAndView huiyuanXiaofei(HttpServletRequest request) throws Exception {

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
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView("user/query/huiyuanXiaofei");
		return mv;
	}

	/**
	 * 折扣卡消费明细查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/zhekouXiaofei")
	public ModelAndView zhekouXiaofei(HttpServletRequest request) throws Exception {

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
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView("user/query/zhekouXiaofei");
		return mv;
	}

	/**
	 * 会员续费查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/huiyuanXufei")
	public ModelAndView huiyuanXufei(HttpServletRequest request) throws Exception {

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
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView("user/query/huiyuanXufei");
		return mv;
	}

	/**
	 * 子卡查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/zika")
	public ModelAndView zika(HttpServletRequest request) throws Exception {

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
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView("user/query/zika");
		return mv;
	}

	/**
	 * 会员各项消费查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/xiaofei")
	public ModelAndView xiaofei(HttpServletRequest request) throws Exception {

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
		ModelAndView mv = new ModelAndView("user/query/xiaofei");
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
