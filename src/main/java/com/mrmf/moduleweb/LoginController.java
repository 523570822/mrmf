package com.mrmf.moduleweb;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.Account;
import com.mrmf.entity.Function;
import com.mrmf.entity.LoginLog;
import com.mrmf.entity.Organ;
import com.mrmf.entity.bean.KuncunAlert;
import com.mrmf.entity.kucun.WWupin;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.function.Function2Service;
import com.mrmf.service.function.FunctionService;
import com.mrmf.service.kucun.KucunService;
import com.mrmf.service.loginLog.LoginLogService;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.security.token.TokenManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
	public static Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private Function2Service function2Service;

	@Autowired
	private OrganService organService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginLogService loginLogService;
	@Autowired
	private KucunService kucunService;
	
	@RequestMapping(value = "/toLogin")
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/login");
		return mv;
	}
//登陆后入口
	@RequestMapping(value = "/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 清除全部session数据
		HttpSession session = request.getSession();
		if (session != null) {
			Enumeration<String> enu = session.getAttributeNames();
			while (enu.hasMoreElements()) {
				String key = enu.nextElement();
				session.setAttribute(key, null);
			}
		}
		Account account = tokenManager.getCurrentAccount();
		request.setAttribute("ffaccount", account);
		String ip = getIpAddr(request);
		String organId = "0"; // 默认为admin
		boolean logined = false;
		if ("admin".equals(account.getAccountType())) { // 超级管理员
			List<String> roleIds = account.getRoleIds();
			request.setAttribute("functions", function2Service.getFunction2Menu(roleIds)); // 初始化功能菜单
			mv.setViewName("login/indexOfAdmin");
			logined = true;
		} else if ("organ".equals(account.getAccountType())) { // 店铺管理员
			List<String> roleIds = account.getRoleIds();
			organId = account.getEntityID();
			Organ organ = organService.queryById(organId);
			
			if (organ == null) {
				request.setAttribute("errorMessage", "数据错误，请联系管理员！");
				return toLogin(request, response);
			}

			if (organ.getValid() == 0) {
				request.setAttribute("errorMessage", "公司账号尚未启用，请联系商务人员！");
				return toLogin(request, response);
			}

			String verifyCode = request.getParameter("verifyCode");

			if (!organ.getIps().contains(ip)) {
				if (!StringUtils.isEmpty(verifyCode)) {
					ReturnStatus status = accountService.verify(organ.getPhone(), verifyCode);
					if (!status.isSuccess()) {
						request.setAttribute("errorMessage", status.getMessage());
						request.setAttribute("needVerify", true);
						return toLogin(request, response);
					} else {
						organService.addIp(organ.get_id(), ip);
					}
				} else {
					request.setAttribute("errorMessage", "IP地址不在信任列表中，请进行验证！");
					request.setAttribute("needVerify", true);
					return toLogin(request, response);
				}
			}
			List<WWupin> wWupins = kucunService.findWWupins(organId);
			List<KuncunAlert> kuncunAlerts = new ArrayList<KuncunAlert>();
			if(wWupins != null) {
				for (WWupin wWupin : wWupins) {
					KuncunAlert kuncunAlert = kucunService.calculateAlert(wWupin);
					if(kuncunAlert != null) {
						kuncunAlerts.add(kuncunAlert);
					}
				}
			}
			List<Function> funtions = functionService.getFunctionMenu(roleIds);
			boolean flag = false;
			for (Function function : funtions) {
				if(function != null) {
					if("物品类别".equalsIgnoreCase(function.getName())) { 
						flag = true;
					}
					if(function.getFunctionList()!= null && function.getFunctionList().size() > 0) {
						List<Function> subFuntions = function.getFunctionList();
						for (Function function2 : subFuntions) {
							if("物品类别".equalsIgnoreCase(function2.getName())) { 
								 flag = true;
							}
						}
					}
				}
			}
			request.setAttribute("flag", flag); // 初始化功能菜单
			request.setAttribute("functions", funtions); // 初始化功能菜单
			request.getSession().setAttribute("isOrganAdmin", true); // 设置店铺管理员参数
			request.getSession().setAttribute("organId", account.getEntityID()); // 设置管理公司id
			request.setAttribute("kuncunAlerts", JSONObject.toJSON(kuncunAlerts));
			request.setAttribute("organ", organ);
			mv.setViewName("login/indexOfOrgan");
			logined = true;
		} else {
			mv = toLogin(request, response);
		}
		if (logined) { // 插入登陆日志
			LoginLog loginLog = new LoginLog();
			loginLog.setAccountName(account.getAccountName());
			loginLog.setOrganId(organId);
			loginLog.setIp(ip);
			loginLog.setFunctionId("0"); // 登陆系统
			loginLog.setMemo("登陆系统");
			loginLogService.upsert(loginLog);
		}
		return mv;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@RequestMapping(value = "/sendVerifyCode")
	@ResponseBody
	public ReturnStatus sendVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accountName = request.getParameter("accountName");
		if (StringUtils.isEmpty(accountName)) {
			return new ReturnStatus(false, "用户名不能为空");
		}

		Organ organ = organService.queryByAdminId(accountName);

		if (organ == null) {
			return new ReturnStatus(false, "用户名不存在");
		}

		if (organ.getValid() == 0) {
			return new ReturnStatus(false, "公司账号尚未启用，请联系商务人员！");
		}

		if (StringUtils.isEmpty(organ.getPhone())) {
			return new ReturnStatus(false, "公司验证手机号信息缺失，请联系商务人员！");
		}

		accountService.verifycode(organ.getPhone());

		return new ReturnStatus(true);
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/welcome");
		return mv;
	}

	@RequestMapping(value = "/gc", method = RequestMethod.GET)
	public ModelAndView gc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("gc");
		return mv;
	}
}
