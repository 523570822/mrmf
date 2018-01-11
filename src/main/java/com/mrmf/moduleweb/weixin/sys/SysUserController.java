package com.mrmf.moduleweb.weixin.sys;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrmf.entity.user.Userpart;
import com.mrmf.service.user.userpart.UserpartService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.*;
import com.osg.framework.web.context.MAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.sys.SysUser.SysUserService;
import com.osg.entity.DataEntity;
import com.osg.entity.FlipInfo;
import com.osg.framework.web.security.token.TokenManager;

/**
 * 系统用户管理
 */
@Controller
@RequestMapping("/weixin/sys/user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private OrganService organService;

	@Autowired
	private UserpartService userpartService;

	/**
	 * 注册用户管理主页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userToQuery")
	public ModelAndView userToQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/sys/user/queryUser");
		return mv;
	}

	/**
	 * 推荐用户界面
	 * @return
	 */
	@RequestMapping("/query")
	public ModelAndView toQuery(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/sys/user/query");
		return mv;
	}

//	/**
//	 * 修改用户员工状态
//	 * @param userId
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/enable/{userId}")
//	@ResponseBody
//	public ReturnStatus enable(@PathVariable String userId) throws Exception{
//		String internalStaff = "0";
//		return sysUserService.UpdateState(userId,internalStaff);
//	}
//	@RequestMapping("/disable/{userId}")
//	@ResponseBody
//	public ReturnStatus disable(@PathVariable String userId) throws Exception{
//		String internalStaff = "1";
//		return sysUserService.UpdateState(userId,internalStaff);
//	}
	/**
	 * 注册用户管理数据获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUser")
	@ResponseBody
	public FlipInfo<User> queryUser(HttpServletRequest request) throws Exception {
		FlipInfo<User> fpi = new FlipPageInfo<User>(request);
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi = sysUserService.queryUser(fpi);
		return fpi;
	}

	/**
	 * 查看用户详情
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lookUser")
	public ModelAndView lookUser(HttpServletRequest request, String userId) throws Exception {
		User user = sysUserService.queryByUserId(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("weixin/sys/user/lookUser");
		return mv;
	}

	/**
	 * 查看用户预约信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lookUserOrder")
	public ModelAndView lookUserOrder(HttpServletRequest request, String userId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId", userId);
		mv.setViewName("weixin/sys/user/lookUserOrder");
		return mv;
	}

	/**
	 * 获取用户预约信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUserOrder")
	@ResponseBody
	public FlipInfo<WeOrganOrder> queryUserOrder(HttpServletRequest request, String userId) throws Exception {
		FlipInfo<WeOrganOrder> fpi = new FlipPageInfo<WeOrganOrder>(request);
		fpi = sysUserService.queryUserOrder(fpi, userId);
		return fpi;
	}

	/**
	 * 启用注册用户
	 */
	@RequestMapping(value = "/setUser")
	public void setUser(@RequestParam String userIds, HttpServletRequest request, HttpServletResponse response) {
		try {
			sysUserService.editUserStatus(userIds, 1 + "");
			response.getWriter().print("启用成功！");// 使用成功！
		} catch (Exception e) {
			try {
				response.getWriter().print("启用失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			} // 使用成功！
			e.printStackTrace();
		}
	}

	/**
	 * 禁用注册用户
	 */
	@RequestMapping(value = "/delUser")
	public void delUser(@RequestParam String userIds, HttpServletRequest request, HttpServletResponse response) {
		try {
			sysUserService.editUserStatus(userIds, 0 + "");
			response.getWriter().print("禁用成功！");// 使用成功！
		} catch (Exception e) {
			try {
				response.getWriter().print("禁用失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			} // 使用成功！
			e.printStackTrace();
		}
	}

	/**
	 * 注册技师管理主页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/staffToQuery")
	public ModelAndView staffToQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/sys/staff/queryStaff");
		return mv;
	}

	/**
	 * 注册技师管理数据获取
	 * 
	 * @param request
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryStaff")
	@ResponseBody
	public FlipInfo<Staff> queryStaff(HttpServletRequest request) throws Exception {
		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");

		Account account = tokenManager.getCurrentAccount();
		List<String> ids = organService.queryAdminOrganIds(account);
		if (ids.size() > 0) {
			fpi.getParams().put("in:organId|array", org.apache.commons.lang3.StringUtils.join(ids.toArray(), ','));
		}
		fpi = sysUserService.queryStaff(fpi);
		return fpi;
	}

	/**
	 * 查看技师详情
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lookStaff")
	public ModelAndView lookStaff(HttpServletRequest request, String staffId) throws Exception {
		Staff staff = sysUserService.queryByStaffId(staffId);
		List<Organ> organL = sysUserService.queryOrganListByStaffId(staffId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("staff", staff);
		mv.addObject("organL", organL);
		mv.setViewName("weixin/sys/staff/lookStaff");
		return mv;
	}

	/**
	 * 启用注册技师
	 */
	@RequestMapping(value = "/editStaffFaceScore", method = RequestMethod.POST)
	@ResponseBody
	public String editStaffFaceScore(HttpServletRequest request, HttpServletResponse response) {
		try {
			String staffId = request.getParameter("staffId");
			Integer faceScore = Integer
					.parseInt(request.getParameter("faceScore") != null && request.getParameter("faceScore") != ""
							? request.getParameter("faceScore") : "0");
			sysUserService.editStaffFaceScore(staffId, faceScore);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	/**
	 * 启用注册技师
	 */
	@RequestMapping(value = "/setStaff")
	public void setStaff(@RequestParam String userIds, HttpServletRequest request, HttpServletResponse response) {
		try {
			sysUserService.editUserStatus(userIds, 1 + "");
			response.getWriter().print("启用成功！");// 使用成功！
		} catch (Exception e) {
			try {
				response.getWriter().print("启用失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			} // 使用成功！
			e.printStackTrace();
		}
	}

	/**
	 * 禁用注册技师
	 */
	@RequestMapping(value = "/delStaff")
	public void delStaff(@RequestParam String userIds, HttpServletRequest request, HttpServletResponse response) {
		try {
			sysUserService.editUserStatus(userIds, 0 + "");
			response.getWriter().print("禁用成功！");// 使用成功！
		} catch (Exception e) {
			try {
				response.getWriter().print("禁用失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			} // 使用成功！
			e.printStackTrace();
		}
	}

	/**
	 * 注册技师管理数据获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportStaffManagement")
	@ResponseBody
	public FlipInfo<Staff> exportStaffManagement(String phone, String organ, String name, String sex, Integer startYear,
			Integer endYear, HttpServletRequest request, HttpServletResponse response) throws Exception {

		File template = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/template_staffname_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));

		// fpi = staffService.queryAll(fpi);
		List<Staff> projects = sysUserService.exportStaff(phone, organ, name, sex, startYear, endYear);

		Map dataSet = new HashMap();
		dataSet.put("user", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "技师表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出注册用户
	 * 
	 * @Description: TODO
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @return ModelAndView
	 * @throws @author
	 * @date 2016-12-28
	 */
	@RequestMapping(value = "/exportUserManagement")

	public ModelAndView exportUserManagement(@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime, String phone, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		File template = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/template_usermanagement_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));

		// FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		// fpi = staffService.queryAll(fpi);
		List<User> projects = sysUserService.exportUserManagement(phone, startTime, endTime);

		Map dataSet = new HashMap();
		dataSet.put("user", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "注册用户.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/toMember")
	public ModelAndView toMember(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/sys/user/member");
		return mv;
	}
	@RequestMapping("/toMenConsume")
	public ModelAndView toMenConsume(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/sys/user/menConsume");
		return mv;
	}

	@RequestMapping("/toMemberByFpi")
	@ResponseBody
	public FlipInfo<Userpart> toMemberByFpi(HttpServletRequest request) throws Exception {
		/*Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		*//*String organId = (String) MAppContext.getSessionVariable("organId");*//*
		if (isOrganAdmin == null && !isOrganAdmin) { // 企业管理员
			throw new BaseException("当前登录信息缺失！");
		}*/
		String organName=request.getParameter("organName");
		FlipInfo<Userpart> fpi = new FlipPageInfo<Userpart>(request);
		// eq:createTime|date#and=2016-09-12 lte:createTime|date+1
		// gte:createTime|date
		String date = (String) fpi.getParams().get("eq:createTime|date#and");
		if (!StringUtils.isEmpty(date)) {
			fpi.getParams().remove("eq:createTime|date#and");
			String[] startAndEnd = DateUtil.getDateStartEnd(date);
			fpi.getParams().put("gte:createTime|date", startAndEnd[0]);
			fpi.getParams().put("lte:createTime|date+1", startAndEnd[1]);
		}
		fpi.getParams().remove("organName");
			return sysUserService.queryByFpi(fpi,organName);
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
