package com.mrmf.moduleweb.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.service.code.CodeService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.staff.ticheng.StaffTichengService;
import com.mrmf.service.user.UserService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 会员管理
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CodeService codeService;

	@Autowired
	private UserService memberService;

	@Autowired
	private StaffTichengService staffTichengService;

	@Autowired
	private UsersortService usersortService; // 会员类型

	@Autowired
	private SmallsortService smallsortService; // 服务项目

	@Autowired
	private StaffService staffService;

	@Autowired
	private OrganService organService;

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				request.setAttribute("ffusersorts", usersortService.findAll(organId));
				request.setAttribute("ffsmallsorts", smallsortService.findAllValid(organId));
				request.setAttribute("ffstaffs", staffService.findAll(organId));
				request.setAttribute("ffstaffTichengs", staffTichengService.findAll(organId));
				request.setAttribute("organSetting", organService.querySetting(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/main");
		return mv;
	}

	@RequestMapping("/toImportUser")
	public ModelAndView toImportUser(HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/import");
		return mv;
	}

	@RequestMapping("/importUser/{fileId}")
	public ModelAndView importUser(@PathVariable String fileId, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		ReturnStatus status = memberService.importUser(organId, fileId);
		if (!status.isSuccess()) {
			request.setAttribute("returnStatus", status);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/import");
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
