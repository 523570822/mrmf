package com.mrmf.moduleweb.user;

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

import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.staff.ticheng.StaffTichengService;
import com.mrmf.service.user.incard.IncardService;
import com.mrmf.service.user.inincard.InincardService;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.userpart.UserpartService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 会员卡相关
 */
@Controller
@RequestMapping("/user/userIncard")
public class UserIncardController {

	@Autowired
	private IncardService incardService;

	@Autowired
	private InincardService inincardService;

	@Autowired
	private UserpartService userpartService; // 消费信息

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

	/**
	 * 进入会员卡消费界面
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toJiezhang")
	public ModelAndView toJiezhang(String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		Userincard incard = incardService.queryById(id);
		/*//改变前台钱,有错改这里
        Userpart userpart = userpartService.queryByIncardId(incard.get_id());
        request.setAttribute("moneyLz",userpart.getNowMoney4());*/
		if (incard.getLaw_day() != null) {
			if (DateUtil.format(DateUtil.currentDate()).compareTo(DateUtil.format(incard.getLaw_day())) > 1)
				throw new BaseException("会员卡已过有效期！");
		}

		List<Userinincard> inincardList = inincardService.query(id);
		request.setAttribute("ffinincardList", inincardList);

		request.setAttribute("ffusersorts", usersortService.findAll(organId));
		request.setAttribute("ffsmallsorts", smallsortService.findAllValid(organId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		request.setAttribute("ffstaffTichengs", staffTichengService.findAll(organId));

		request.setAttribute("ffincard", incard);
		request.setAttribute("organSetting", organService.querySetting(organId));

		ModelAndView mv;
		if (inincardList.size() > 0) {
			mv = new ModelAndView("user/usercard/jiezhang_zika");
		} else {
			mv = new ModelAndView("user/usercard/jiezhang");
		}
		return mv;
	}

	@RequestMapping("/queryById")
	@ResponseBody
	public Userincard queryById(String id) throws Exception {
		return incardService.queryById(id);
	}

	/**
	 * 会员卡消费结账
	 * 
	 * @param incardId
	 * @param xiaocishu
	 * @param xianjin
	 * @param huaka
	 * @param money_cash
	 * @param money_yinhang_money
	 * @param money3
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jiezhang")
	@ResponseBody
	public ReturnStatus jiezhang(String incardId, @RequestParam(required = false) String xiaocishu,
			@RequestParam(required = false) String xianjin, @RequestParam(required = false) String huaka,
			@RequestParam(required = false) String money_cash,
			@RequestParam(required = false) String money_yinhang_money, @RequestParam(required = false) String money3,
			String passwd) throws Exception {
		int xiaocishu2 = 0;
		if (!StringUtils.isEmpty(xiaocishu)) {
			xiaocishu2 = Integer.parseInt(xiaocishu);
		}
		double xianjin2 = 0;
		if (!StringUtils.isEmpty(xianjin)) {
			xianjin2 = Double.parseDouble(xianjin);
		}
		double huaka2 = 0;
		if (!StringUtils.isEmpty(huaka)) {
			huaka2 = Double.parseDouble(huaka);
		}
		double money_cash2 = 0;
		if (!StringUtils.isEmpty(money_cash)) {
			money_cash2 = Double.parseDouble(money_cash);
		}
		double money_yinhang_money2 = 0;
		if (!StringUtils.isEmpty(money_yinhang_money)) {
			money_yinhang_money2 = Double.parseDouble(money_yinhang_money);
		}
		double money32 = 0;
		if (!StringUtils.isEmpty(money3)) {
			money32 = Double.parseDouble(money3);
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

		return incardService.jiezhang(incardId, xiaocishu2, xianjin2, huaka2, money_cash2, money_yinhang_money2,
				money32, passwd);
	}

	/**
	 * 主卡和子卡统一结账
	 * 
	 * @param incardId
	 * @param inincardId
	 * @param xiaocishu
	 * @param xianjin
	 * @param huaka
	 * @param money_cash
	 * @param money_yinhang_money
	 * @param money3
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jiezhangWithInincard")
	@ResponseBody
	public ReturnStatus jiezhangWithInincard(@RequestParam(required = false) String incardId,
			@RequestParam(required = false) String inincardId, @RequestParam(required = false) String xiaocishu,
			@RequestParam(required = false) String xianjin, @RequestParam(required = false) String huaka,
			@RequestParam(required = false) String money_cash,
			@RequestParam(required = false) String money_yinhang_money, @RequestParam(required = false) String money3,
			String passwd) throws Exception {
		int xiaocishu2 = 0;
		if (!StringUtils.isEmpty(xiaocishu)) {
			xiaocishu2 = Integer.parseInt(xiaocishu);
		}
		double xianjin2 = 0;
		if (!StringUtils.isEmpty(xianjin)) {
			xianjin2 = Double.parseDouble(xianjin);
		}
		double huaka2 = 0;
		if (!StringUtils.isEmpty(huaka)) {
			huaka2 = Double.parseDouble(huaka);
		}
		double money_cash2 = 0;
		if (!StringUtils.isEmpty(money_cash)) {
			money_cash2 = Double.parseDouble(money_cash);
		}
		double money_yinhang_money2 = 0;
		if (!StringUtils.isEmpty(money_yinhang_money)) {
			money_yinhang_money2 = Double.parseDouble(money_yinhang_money);
		}
		double money32 = 0;
		if (!StringUtils.isEmpty(money3)) {
			money32 = Double.parseDouble(money3);
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

		ReturnStatus status = new ReturnStatus(true);
		// 子卡结账
		if (!StringUtils.isEmpty(inincardId)) {
			status = inincardService.jiezhang(inincardId, xiaocishu2, xianjin2, money_cash2, money32, passwd);
			if (!status.isSuccess())
				return status;
		}
		if (!StringUtils.isEmpty(incardId)) {
			return incardService.jiezhang(incardId, xiaocishu2, xianjin2, huaka2, money_cash2, money_yinhang_money2,
					money32, passwd);
		} else
			return status;
	}

	/**
	 * 进入续费界面
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toXufei")
	public ModelAndView toXufei(String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		request.setAttribute("ffusersorts", usersortService.findAll(organId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));

		request.setAttribute("ffincard", incardService.queryById(id));
		request.setAttribute("organSetting", organService.querySetting(organId));

		ModelAndView mv = new ModelAndView("user/usercard/xufei");
		return mv;
	}

	/**
	 * 续费
	 * 
	 * @param userpart
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/xufei")
	@ResponseBody
	public ReturnStatus xufei(Userpart userpart, BindingResult results, HttpServletRequest request) throws Exception {
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
		return incardService.xufei(userpart);
	}

	/**
	 * 续费删除
	 * 
	 * @param userpartId
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/xufeiRemove")
	@ResponseBody
	public ReturnStatus xufeiRemove(String userpartId, String code) throws Exception {
		return incardService.xufeiRemove(userpartId, code);
	}

	/**
	 * 发送会员卡相关敏感操作短信验证码给用户
	 * 
	 * @param incardId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/verifycode")
	@ResponseBody
	public ReturnStatus verifycode(String incardId) throws Exception {
		return incardService.verifycode(incardId);
	}

	/**
	 * 发送会员卡相关敏感操作短信验证码给店铺
	 * 
	 * @param incardId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/verifycodeOrgan")
	@ResponseBody
	public ReturnStatus verifycodeOrgan(String incardId) throws Exception {
		return incardService.verifycodeOrgan(incardId);
	}

	/**
	 * 修改卡号
	 * 
	 * @param incardId
	 * @param id_2
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeId_2")
	@ResponseBody
	public ReturnStatus changeId_2(String incardId, String id_2) throws Exception {
		return incardService.changeId_2(incardId, id_2);
	}

	/**
	 * 修改会员卡密码
	 * 
	 * @param incardId
	 * @param passwd
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changePasswd")
	@ResponseBody
	public ReturnStatus changePasswd(String incardId, String passwd, String code) throws Exception {
		return incardService.changePasswd(incardId, passwd, code);
	}

	/**
	 * 删除会员卡
	 * 
	 * @param incardId
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public ReturnStatus remove(String incardId, String code) throws Exception {
		return incardService.remove(incardId, code);
	}

	/**
	 * 进入更改卡类型和补缴欠费界面
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toQianfei")
	public ModelAndView toQianfei(String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		request.setAttribute("ffusersorts", usersortService.findAll(organId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));

		Userpart userpart = userpartService.queryById(id);
		userpart.setSomemoney1(0.0);
		userpart.setSomemoney2(0.0);
		userpart.setSomemoney3(0.0);
		userpart.setYeji1(0);
		userpart.setYeji2(0);
		userpart.setYeji3(0);
		userpart.setMoney_cash(0);
		userpart.setMoney_yinhang_money(0);
		request.setAttribute("ffuserpart", userpart);

		ModelAndView mv = new ModelAndView("user/usercard/qianfei");
		return mv;
	}

	/**
	 * 补缴欠费
	 * 
	 * @param userpart
	 * @param results
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qianfei")
	@ResponseBody
	public ReturnStatus qianfei(Userpart userpart, BindingResult results, HttpServletRequest request) throws Exception {
		String partId = request.getParameter("userPartId");
		return incardService.qianfei(userpart,partId);
	}

	/**
	 * 更改卡类型
	 * 
	 * @param incardId
	 * @param usersortId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeCardType")
	@ResponseBody
	public ReturnStatus changeCardType(String incardId, String usersortId) throws Exception {
		return incardService.changeCardType(incardId, usersortId);
	}

	/**
	 * 会员卡提现
	 * 
	 * @param incardId
	 * @param tixian
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tixian")
	@ResponseBody
	public ReturnStatus tixian(String incardId, double tixian) throws Exception {
		return incardService.tixian(incardId, tixian);
	}

	@RequestMapping("/getElecCardNum")
	@ResponseBody
	public String getElecCardNum() throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		return incardService.getElecCardNum(organId);
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
