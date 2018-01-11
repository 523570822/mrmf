package com.mrmf.moduleweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.mrmf.entity.Function;
import com.mrmf.entity.Role;
import com.mrmf.service.function.FunctionService;
import com.mrmf.service.role.RoleService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 角色管理相关
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("role/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Role> query(HttpServletRequest request) throws Exception {
		FlipInfo<Role> fpi = new FlipPageInfo<Role>(request);
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
		fpi = roleService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String roleId, HttpServletRequest request)
			throws Exception {
		Role role;
		if (!StringUtils.isEmpty(roleId)) {
			role = roleService.queryById(roleId);
		} else {
			role = new Role();
		}

		List<Function> functionList = functionService.query();

		// 添加根节点
		Function root = new Function();
		root.set_id("0");
		root.setParentId("0");
		root.setName("菜单树");
		functionList.add(root);

		request.setAttribute("fffunction", functionList);

		request.setAttribute("ffrole", role);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("role/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Role role, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = role.get_id();
		ReturnStatus status = roleService.upsert(role);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			role.set_id(originId);// 恢复之前的id
			request.setAttribute("ffrole", role);
			request.setAttribute("returnStatus", status);
			mv.setViewName("role/upsert");
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
