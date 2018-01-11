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
import com.mrmf.entity.Role2;
import com.mrmf.service.account.Account2Service;
import com.mrmf.service.role.Role2Service;
import com.mrmf.service.wes.WeSService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;

/**
 * 平台账号管理相关
 */
@Controller
@RequestMapping("/account2")
public class Account2Controller {

	@Autowired
	private Account2Service accountService;

	@Autowired
	private Role2Service roleService;

	@Autowired
	private WeSService weSService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account2/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Account> query(HttpServletRequest request) throws Exception {
		FlipInfo<Account> fpi = new FlipPageInfo<Account>(request);
		// fpi.getParams().put("entityID", "0");
		fpi.getParams().put("accountType", "admin");
		fpi = accountService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String accountId, HttpServletRequest request)
			throws Exception {
		Account account;
		if (!StringUtils.isEmpty(accountId)) {
			account = accountService.queryById(accountId);
		} else {
			account = new Account();
		}

		List<Role2> roleList = roleService.queryAll();
		request.setAttribute("roleList", roleList);

		request.setAttribute("cityList", weSService.queryCity());
		request.setAttribute("districtList", weSService.queryDistrictAll());

		request.setAttribute("ffaccount", account);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account2/upsert");
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
			mv.setViewName("account2/upsert");
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
