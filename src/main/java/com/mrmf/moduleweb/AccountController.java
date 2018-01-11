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

import com.mrmf.entity.Account;
import com.mrmf.entity.Role;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.role.RoleService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.security.token.TokenManager;

/**
 * 账号管理相关
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private AccountService accountService;

	@Autowired
	private RoleService roleService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Account> query(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}
		FlipInfo<Account> fpi = new FlipPageInfo<Account>(request);
		fpi.getParams().put("entityID", organId);
		fpi.getParams().put("accountType", "organ");
		fpi = accountService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String accountId, HttpServletRequest request)
			throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("当前登录企业信息缺失！");
		}

		Account account;
		if (!StringUtils.isEmpty(accountId)) {
			account = accountService.queryById(accountId);
		} else {
			account = new Account();
		}

		List<Role> roleList = roleService.queryByOrganId(organId);
		request.setAttribute("roleList", roleList);

		request.setAttribute("ffaccount", account);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Account account, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = account.get_id();
		ReturnStatus status = accountService.upsert(account);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			account.set_id(originId);// 恢复之前的id
			request.setAttribute("ffaccount", account);
			request.setAttribute("returnStatus", status);
			mv.setViewName("account/upsert");
		}

		return mv;
	}

	@RequestMapping("/toChangePasswd")
	public ModelAndView toChangePasswd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account/changePasswd");
		return mv;
	}

	@RequestMapping("/changePasswd")
	public ModelAndView changePasswd(String oldPasswd, String newPasswd, HttpServletRequest request) throws Exception {
		Account account = tokenManager.getCurrentAccount();
		ReturnStatus status = accountService.changePasswd(account.get_id(), oldPasswd, newPasswd);
		request.setAttribute("returnStatus", status);

		return toChangePasswd(request);
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
