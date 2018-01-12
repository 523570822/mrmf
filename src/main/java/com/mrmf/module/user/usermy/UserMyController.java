package com.mrmf.module.user.usermy;

import com.alibaba.fastjson.JSONObject;
import com.mrmf.entity.*;
import com.mrmf.entity.coupon.MyCoupon;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.wxpay.CommonUtil;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.VipMember.VipMemberService;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.CashUtil;
import com.mrmf.service.common.Configure;
import com.mrmf.service.coupon.CouponGrantService;
import com.mrmf.service.coupon.CouponService;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.user.bigsort.BigsortService;
import com.mrmf.service.userPay.UserPayService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.westaff.WeStaffService;
import com.mrmf.service.weuser.WeUserService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.cache.CacheManager;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户我的相关
 */
@Controller
@RequestMapping("/userMy")
public class UserMyController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired 
	private UserMyService userMyService;
	@Autowired
	private WeUserService weUserService;
	@Autowired
	private AccountService accountService;
	@Autowired 
	private RedisService redisService;
	@Autowired
	private UserPayService userPayService;
	@Autowired
	private WeOrganService weOrganService;
	@Autowired
	private WeStaffService weStaffService;
	@Autowired
	private CouponGrantService couponGrantService;
	@Autowired
	private BigsortService bigsortService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private WeComonService weComonService;
	@Autowired
	private VipMemberService vipMemberService;


	@RequestMapping("/toExpenseList")
	public ModelAndView toExpenseList(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/expense_record");
		return mv;
	}
	//返回上一页
	@RequestMapping("/toLastPage")
	public ModelAndView toLastPage(@RequestParam(required=false)String userId,String status,HttpServletRequest request)throws Exception{
		if (status.equals("myOrder")) {
			return toUserMyHome(request,null);
		}else if (status.equals("orderDetail")) {
			return myOrder(request);
		}else if (status.equals("compensate")) {
			return toUserMyHome(request, null);
		}else if (status.equals("compensateDetail")) {
			return compensate(userId, request);
		}else if (status.equals("createCompensate")) {
			return compensate(userId, request);
		}
		return null;
	}
	/**
	 * 用户端-我的消费记录
	 */
	@RequestMapping("/expenseRecord")
	@ResponseBody
	public FlipInfo<WeOrganOrder> expenseRecord(String page,String size,
			HttpServletRequest request) throws Exception {
		FlipPageInfo<WeOrganOrder> flp=new FlipPageInfo<WeOrganOrder>(request);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		flp.getParams().remove("userId");
		flp.getParams().remove("size");
		flp.setSize(10);
		flp.setSortField("orderTime");
		flp.setSortOrder("desc");
		FlipInfo<WeOrganOrder> fpi = userMyService.expenseRecord(user.get_id(),page,size,flp);
		return fpi;
	}
	@RequestMapping("/toMyCollect")
	public ModelAndView toMyCollect(@RequestParam(required = false) String userId,String type,HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		request.setAttribute("userId", userId);
		mv.setViewName("user/usermy/hairstyle_collect");
		if(type == null || "1".equals(type)){//案例		
			request.setAttribute("type", "1");
		}else if("2".equals(type)){//技师
			request.setAttribute("type", "2");
		}else if("3".equals(type)){//店铺
			request.setAttribute("type", "3");
		}	
		return mv;
	}	
	/**
	 * 用户端-我的收藏
	 */
	@RequestMapping("/myCollect")
	@ResponseBody
	public FlipInfo<UserCollect> myCollect(String userId,String type,String longitude,String latitude,
			String page,String size,HttpServletRequest request) throws Exception {
		FlipInfo<UserCollect> fpi = userMyService.mycollect(userId,type,longitude,latitude,page,size);
		return fpi;
	}
	/**
	 * 用户端-评价
	 */
	@RequestMapping("/evaluateOrder")
	@ResponseBody
	public ReturnStatus evaluateOrder(@RequestParam(required = false) String userId,String orderId,String zanCount,String qiuCount,
			String faceScore,String comment,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ReturnStatus status =userMyService.evaluateOrder(user.get_id(),orderId,zanCount,qiuCount,faceScore,comment);
		return status;
	}
	//跳转评价店铺
	@RequestMapping("toEvaluateOrgan")
	public ModelAndView toEvaluateOrgan(@RequestParam(required = false)String orderId,HttpServletRequest request )throws Exception{
		request.setAttribute("orderId", orderId);
		String staffId = request.getParameter("staffId");
		String money = request.getParameter("money");
		if(!StringUtils.isEmpty(money) && !StringUtils.isEmpty(staffId)) {
			HttpSession session = request.getSession();
			User userSession = (User)session.getAttribute("user");
			double moneyTemp = Double.parseDouble(money);
			userMyService.userAwardStaff(staffId, moneyTemp);
			userMyService.updateUserWallet(userSession.get_id(),moneyTemp);
			userMyService.saveStaffWalletHis(userSession.get_id(), 0-moneyTemp, "打赏技师", "0");
			WeStaffIncome weStaffIncome = new WeStaffIncome();
			weStaffIncome.setCreateTimeIfNew();
			weStaffIncome.setUserId(userSession.get_id());
			weStaffIncome.setStaffId(staffId);
			weStaffIncome.setTitle("用户打赏");
		    weStaffIncome.setAmount(moneyTemp);
			userMyService.saveStaffIncome(weStaffIncome);
		}
		WeOrganOrder order=userMyService.getOrderDetail(orderId);
		request.setAttribute("type", order.getType());
		return new ModelAndView("user/usermy/my_orderComment_organ");
	}
	/**
	 * 用户端-评价订单页面
	 */
	@RequestMapping("/toEvaluateOrder")
	public ModelAndView toEvaluateOrder(@RequestParam(required = false) String userId,String orderId,HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		request.setAttribute("userId", userId);
		request.setAttribute("orderId", orderId);
		WeOrganOrder order = userMyService.getOrderDetail(orderId);
		request.setAttribute("type", order.getType());
		if (order !=null && order.getStaffId() !=null && !order.getStaffId().equals("") ) {//有技师
			mv.setViewName("user/usermy/my_orderComment");
			return mv;
		}else if (order !=null && (order.getStaffId() ==null || order.getStaffId().equals(""))) {//无技师
			mv.setViewName("user/usermy/my_orderComment_organ");
			return mv;
		}
		return orderDetail(orderId, request);
	}
	/**
	 * 跳转到我的首页界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toUserMyHome")
    public ModelAndView toUserMyHome(HttpServletRequest request,HttpServletResponse response) {
    	ModelAndView mv = new ModelAndView();
    	HttpSession session = request.getSession();
    	User userSession = (User)session.getAttribute("user");
    	User user = userMyService.queryUserById(userSession.get_id());
    	/*获取上次的地理位置*/
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String status = request.getParameter("status");
		String sysMessage = request.getParameter("sysMessage");
		if(!StringUtils.isEmpty(status) && status.equals("message")) {
			if(!StringUtils.isEmpty(sysMessage)) {
				userMyService.updateSysMessageRead(user.get_id());
			}
			userMyService.updateMessageRead(user.get_id());
		}
		//findOldLocation(longitude,latitude,request);
		long messageCount = userMyService.findUserMessageCount(userSession.get_id());
		String TOKEN = "oOLdqxPrqWc6sNxCWzn-4lJgEzzfe_vtx8Ex-5_8HmKFhBgOIZKXQyqOWbJ1LAYZHO30MI8V4kHmcEv-EPrs2KXfriMv8hVIVNPRsN17dvrS4zNonEuKAvfhrwd9ITU6NAAdAEAREE";
		request.setAttribute("token",TOKEN);
		mv.addObject("messageCount", messageCount);
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/my_home");
		return mv;
    }
	/**
	 * 获取用户的上次位置
	 * @param longitude
	 * @param latitude
	 * @param request
	 */
	private void findOldLocation(String longitude, String latitude,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		if(StringUtils.isEmpty(longitude) || longitude=="0" || StringUtils.isEmpty(latitude) || latitude =="0") {
			longitude = user.getGpsPoint().getLongitude()+"";
			latitude = user.getGpsPoint().getLatitude()+"";
			session.setAttribute("longitude", Double.parseDouble(longitude));
			session.setAttribute("latitude", Double.parseDouble(latitude));
		}
	}
	/**
	 * 跳转到设置用户个人信息的主界面
	 */
	@RequestMapping("/toInfoSet")
	public ModelAndView toInfoSet(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSessoin= (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSessoin.get_id());
		mv.addObject("user", user);
		mv.setViewName("user/usermy/my_set");
		return mv;
	}
	/**
	 * 去设置头像界面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSetHeader")
	public ModelAndView toSetHeader(@RequestParam(required = true) String id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(id);
		mv.addObject("user", user);
		mv.setViewName("user/usermy/change_header");
		return mv;
	}
	/**
	 * 设置完头像返回
	 * @param id
	 * @param imgSrc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSetHomeByHeader")
	public ModelAndView toSetHomeByHeader(@RequestParam(required = true) String id,
			@RequestParam String imgSrc,HttpServletRequest request,HttpServletResponse response) {
		if(imgSrc != null && !imgSrc.equals("")) {
			userMyService.updateImg(id, imgSrc);
		}
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(id);
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/my_set");
		return mv;
	}
	/**
	 * 去设置昵称
	 */
	@RequestMapping("/toSetNick")
	public ModelAndView toSetNick(@RequestParam(required = true) String id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(id);
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/change_nick");
		return mv;
	}
	/**
	 * 设置昵称
	 */
	@RequestMapping("/toSetHomeByNick")
	public ModelAndView toSetHomeByNick(@RequestParam(required = true) String id,
			@RequestParam String nameVal,HttpServletRequest request,HttpServletResponse response){
		if(nameVal != null && !nameVal.equals("")) {
			try {
				nameVal = new String(URLDecoder.decode(nameVal,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			userMyService.updateNick(id, nameVal);
		}
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(id);
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/my_set");
		return mv;
	}
	/**
	 * 去设置手机号界面
	 */
	@RequestMapping("/toSetPhone")
	public ModelAndView toSetPhone(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/change_phone");
		return mv;
	}
	@RequestMapping("/sendCode")
	public ModelAndView sendCode(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
		if(!StringUtils.isEmpty(user.getPhone())) {
			mv.addObject("isHavePhone", true);
		}
		mv.setViewName("user/usermy/send_code");
		return mv;
	}
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCode")
	@ResponseBody
	public String getCode(HttpServletRequest request,HttpServletResponse response) {
		String phone =String.valueOf(request.getParameter("phone"));
		if(accountService.verifycode(phone).isSuccess()) {
			return "获取验证码成功！";
		}else {
			return "获取验证码失败！";
		}
	}
	
	/**
	 * 填写验证码  验证是否成功
	 * @param request
	 * @param response
	 * @return
	 */
	//TODO 用户端修改手机号
	@RequestMapping("/toVerifyCodeByPhone")
	@ResponseBody
	public ReturnStatus toVerifyCodeByPhone(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		if(StringUtils.isEmpty(phone)) {
			return new ReturnStatus(0,"手机号不能为空！");
		}
		if(StringUtils.isEmpty(code)) {
			return new ReturnStatus(0,"验证码不能为空！");
		}
		if(user.getPhone() == null) {
			//如果手机号为空就得查看时候后台已经添加了用户故此调用此方法进行用户信息整合
			if(userMyService.userVerify(phone, code, userSession.get_id()).isSuccess()){
				User u=userMyService.queryUserByPhone(phone);
				if(u!=null){
					session.setAttribute("user", u);
				}
				couponGrantService.grantCouponByuserUuidAndType(user.get_id(),"注册",-1,"");
				return new ReturnStatus(1,"绑定成功");
			}else {
				return new ReturnStatus(0,"绑定失败");
			}
		} else {
			if(phone.equals(user.getPhone())) {
				if(accountService.verify(phone, code).isSuccess()) {
					return new ReturnStatus(2,"验证成功");
				} else {
					return new ReturnStatus(0,"验证失败");
				}
			} else {
				return new ReturnStatus(0,"和之前绑定的手机号不一致，不能修改手机号");
			}
			
		}
	}
	/**
	 * 设置手机号
	 */
	@RequestMapping("/toSetHomeByPhone")
	@ResponseBody
	public int toSetHomeByPhone(@RequestParam String phoneVal,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		if(!StringUtils.isEmpty(phoneVal)) {
			if(userMyService.isHaveUserPhone(phoneVal)) {
				return 2; //表示已经有用户使用
			} else {
				//修改member手机号
				vipMemberService.updateMemberByPhone(phoneVal,userSession.getPhone());
				userMyService.updatePhone(userSession.get_id(), phoneVal);
				return 1; //表示修改成功！
			}
		} else {
			return 3; //手机号不能为空啊！
		}
	}
	/**
	 * 去设置邮箱界面
	 */
	@RequestMapping("/toSetEmail")
	public ModelAndView toSetEmail(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/change_email");
		return mv;
	}
	/**
	 * 设置邮箱
	 */
	@RequestMapping("/toSetHomeByEmail")
	public ModelAndView toSetHomeByEmail(/*@RequestParam(required = true) String id,*/
			@RequestParam String email,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		if(email != null) {
			try {
				email = new String(URLDecoder.decode(email,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			userMyService.updateEmail(userSession.get_id(), email);
		}
		
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(userSession.get_id());
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/my_set");
		return mv;
	}
	
	/**
	 * 跳转到设置生日界面
	 */
	@RequestMapping("/toSetBirthday")
	public ModelAndView toSetBirthday(@RequestParam(required = true) String id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(id);
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/change_birthday");
		return mv;
	}
	/**
	 * 设置生日
	 * @param id
	 * @param birthday
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSetHomeByBirthday")
	public ModelAndView toSetHomeByBirthday(@RequestParam(required = true) String id,
			@RequestParam String birthday,HttpServletRequest request,HttpServletResponse response){
		if(birthday != null && !birthday.equals("")) {
			try {
				birthday = new String(URLDecoder.decode(birthday,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			userMyService.updateBirthDay(id, birthday);
		}
		ModelAndView mv = new ModelAndView();
		User user = userMyService.queryUserById(id);
		/*SimpleDateFormat dateFormater=new SimpleDateFormat("yyyy-MM-dd");
		user.setBirthday(dateFormater.format(user.getBirthday()));*/
    	mv.addObject("user", user);
		mv.setViewName("user/usermy/my_set");
		return mv;
	}
	/**
	 * 设置支付密码
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("/toSetSafe") 
	public ModelAndView toSetSafe(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/set_pwd");
		return mv;
	}
	@RequestMapping("/toBindPhone") 
	public ModelAndView toBindPhone(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/bind_phone");
		return mv;
	}
	
	
	/**
	 * 去设置支付密码页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toMyPayPassword") 
	public ModelAndView toMyPayPassword(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/usermy/my_PayPassword");
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		if(!StringUtils.isEmpty(user.getPayPassword())) {
			mv.addObject("havePassword", true);
		} else {
			mv.addObject("havePassword", false);
		}
		return mv;
	}
	
	//TODO  设置支付密码
	@RequestMapping(value ="/setMyPayPassword", method = RequestMethod.POST)
	@ResponseBody
	public ReturnStatus setMyPayPassword(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		if(!StringUtils.isEmpty(user.getPhone())) { //已经设置过手机号
			if(phone.equals(user.getPhone())) {
				if(accountService.verify(phone, code).isSuccess()) {
					//设置支付密码
					if(password.equals(confirmPassword)) {
						password = passwordEncoder.encodePassword(password, userSession.get_id());
						userMyService.updatePwd(user.get_id(), password);
						return new ReturnStatus(1);
					} else {
						return new ReturnStatus(4,"两次密码输入的不一致,请重新输入");
					}
				} else {
					//验证码验证失败
					return new ReturnStatus(2,"验证码验证失败,请重新获取");
				}
			} else {
				//和绑定的不一致
				return new ReturnStatus(3,"你输入的手机号和绑定的不一致不能设置支付密码。");
			}
		} else {
			//没有绑定过手机号
			if(userMyService.userVerify(phone, code, userSession.get_id()).isSuccess()){
				User u=userMyService.queryUserByPhone(phone);
				if(u!=null){
					session.setAttribute("user", u);
				}
				if(password.equals(confirmPassword)) {
					password = passwordEncoder.encodePassword(password, userSession.get_id());
					userMyService.updatePhone(u.get_id(), phone);
					userMyService.updatePwd(u.get_id(), password);
				    return new ReturnStatus(1);
				} else {
					return new ReturnStatus(4,"两次密码输入的不一致,请重新输入");
				}
			} else {
				return new ReturnStatus(2,"验证码验证失败,请重新获取");
			}
		}
	}
	/**
	 * 通过手机号获得验证码	getCodeByPhone
	 */
	@RequestMapping(value ="/getCodeByPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCodeByPhone(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();


		String phone =String.valueOf(request.getParameter("phone"));
		if(accountService.verifycode(phone).isSuccess()) {

			map.put("code","0");
			map.put("message","获取验证码成功！");
			map.put("data","");



		}else {

			map.put("code","1");
			map.put("message","获取验证码失败！");
			map.put("data","");

		}
		return map;
	}
	/**
	 * 验证手机号和验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/toVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public String toVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		String phone = request.getParameter("phone").toString();
		String code = request.getParameter("code").toString();
		if(user.getPhone() == null) {
			if(userMyService.userVerify(phone, code, userSession.get_id()).isSuccess()){
				User u=userMyService.queryUserByPhone(phone);
				if(u!=null){
					session.setAttribute("user", u);
				}
				return "绑定成功";
			}else {
				return "验证失败";
			}
		}

		if(phone.equals(user.getPhone())) {
			if(accountService.verify(phone, code).isSuccess()) {
				return "绑定成功";
			} else {
				return "验证失败";
			}
		}
		return "手机号和绑定的不一致手机，不能设置支付密码！";
	}
	
	@RequestMapping("/toPwd2")
	public ModelAndView toPwd2(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		String pwd = request.getParameter("pwd");
		pwd = passwordEncoder.encodePassword(pwd, userSession.get_id());
    	mv.addObject("pwd", pwd);
		mv.setViewName("user/usermy/set_pwd2");
		return mv;
	}
	/**
	 * 修改密码成功以后 返回用户信息主页
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/toSetHomeByPwd")
	public void toSetHomeByPwd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		String pwd1 = request.getParameter("pwd1");
		String pwd2 = request.getParameter("pwd2");
		pwd2 = passwordEncoder.encodePassword(pwd2, userSession.get_id());
		if(pwd1.equals(pwd2)) {
			userMyService.updatePwd(userSession.get_id(), pwd2);
			response.getWriter().print("success");
		} else {
			response.getWriter().print("noConsist");
		}
	}
	/**
	 * 去我的钱包页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toMyWallet")
	public ModelAndView toMyWallet(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
		DecimalFormat df=new DecimalFormat("#.00"); 
		user.setWalletAmount(Double.parseDouble(df.format(user.getWalletAmount())));
		mv.addObject("user",user);
		mv.setViewName("user/usermy/my_wallet");
		return mv;
	}
	
	
	/**
	 * 去我的钱包页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toWalletRule")
	public ModelAndView toWalletRule(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/wallet_rule");
		return mv;
	}
	
	@RequestMapping("/toExpense")
	@ResponseBody
	public FlipInfo<WeUserWalletHis> toExpense(@RequestParam(required = true) String id,
		@RequestParam int page,HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		FlipInfo<WeUserWalletHis> weUserWalletHis = new FlipInfo<WeUserWalletHis>();
		weUserWalletHis.setPage(page);
		weUserWalletHis.setSortField("createTime");
		weUserWalletHis.setSortOrder("desc");
		weUserWalletHis = userMyService.queryWallet(weUserWalletHis,user.get_id());
		return weUserWalletHis;
	}

	//跳转我的优惠券
	@RequestMapping("/myCoupon")
	public ModelAndView myCoupon(@RequestParam(required=false)String userId,HttpServletRequest request)throws Exception{
		request.setAttribute("userId", userId);
		return new ModelAndView("staff/myOrder/couponList");
	}
	//我的优惠券列表
	@RequestMapping("/myCouponList")
	@ResponseBody
	public FlipInfo<MyCoupon> myCouponList(@RequestParam(required=false)String userId,String type,HttpServletRequest request)throws Exception{
//		HttpSession session=request.getSession();
//		User user=(User)session.getAttribute("user");
		FlipPageInfo<MyCoupon> flp=new FlipPageInfo<MyCoupon>(request);
		flp.getParams().remove("userId");
		flp.getParams().remove("type");
		flp.setSortField("createTime");
		flp.setSortOrder("desc");
		FlipInfo<MyCoupon> coupons=userMyService.getCoupon(userId,type,flp);
		return coupons;
	}

	//生成我 的二维码
	@RequestMapping("/toMyOrCode")
	public ModelAndView makeCode(@RequestParam(required = false) String userId,HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession(true);
		String TOKEN = "";
		try {
			WeToken weToken = redisService.getTonkenInfo("user");
			TOKEN = weToken.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject action_info = new JSONObject();
		JSONObject scene_id = new JSONObject();
		int time = weComonService.getTime();
		String str = "17"+time;
		int rNum = Integer.parseInt(str);
		System.out.println(rNum);
		scene_id.put("scene_id", rNum);
		User user = weUserService.queryUserById(userId);
		action_info.put("scene", scene_id);
		cacheManager.save(rNum+"",user);
		System.out.println("推荐redis 存储rum ="+rNum);
		System.out.println("redis 店铺rnum+user"+user);
		JSONObject paramJSON = new JSONObject();
		paramJSON.put("expire_seconds",1800);
		paramJSON.put("action_name","QR_SCENE");
		paramJSON.put("action_info",action_info);

		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + TOKEN + "";//返回类型是二维码，scope获取用户信息类型
		String s = doPostJson(url, paramJSON.toString());
		JSONObject parse = (JSONObject) JSONObject.parse(s);
		String ticket = parse.get("ticket").toString();
		String ticket1 = CommonUtil.urlEncodeUTF8(ticket);
		String url2 = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket1 + "";
		ModelAndView mv = new ModelAndView();
		mv.addObject("showqrcode",url2);
		mv.setViewName("user/usermy/recommend");
		return mv;
	}

	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}


	//跳转我的订单
	@RequestMapping("/myOrder")
	public ModelAndView myOrder(HttpServletRequest request)throws Exception{
		String orderId  = request.getParameter("orderId");
		String priceStr = request.getParameter("price");
		if(!StringUtils.isEmpty(orderId) && !StringUtils.isEmpty(priceStr)) {
			double price = Double.parseDouble(priceStr);
			userMyService.updateOrderPrice(orderId,price);
		}
		return new ModelAndView("user/usermy/myOrder/orderList");
	}
	//我的订单列表
	@RequestMapping("/myOrderList")
	@ResponseBody
	public FlipInfo<WeOrganOrder> myOrderList(@RequestParam(required=false)String userId,String type,HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		FlipPageInfo<WeOrganOrder> flp=new FlipPageInfo<WeOrganOrder>(request);
		flp.getParams().remove("userId");
		flp.getParams().remove("type");
		flp.setSortField("createTime");
		flp.setSortOrder("desc");
		FlipInfo<WeOrganOrder> orders=userMyService.getOrders(user.get_id(),type,flp);
		return orders;
	}
	
	//我的订单列表
	@RequestMapping("/myFinishedOrderList")
	@ResponseBody
	public FlipInfo<WeOrganOrder> myFinishedOrderList(HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		FlipInfo<WeOrganOrder> flp=new FlipPageInfo<WeOrganOrder>(request);
		flp.setSortField("createTime");
		flp.setSortOrder("desc");
		FlipInfo<WeOrganOrder> orders=userMyService.getFinishedOrders(user.get_id(),flp);
		for(WeOrganOrder weOrganOrder:orders.getData()) {
			Organ organ = userMyService.findOrganById(weOrganOrder.getOrganId());
			weOrganOrder.setOrganAddress(organ.getAddress());
			weOrganOrder.setOrganLogo(organ.getLogo());
			weOrganOrder.setOrganName(organ.getName());
		}
		return orders;
	}
	
	//订单详情
	@RequestMapping("/orderDetail")
	public ModelAndView orderDetail(String orderId,HttpServletRequest request)throws Exception{
		String type = request.getParameter("type");
		if(!StringUtils.isEmpty(orderId) && !StringUtils.isEmpty(type)) {
			//表示预约的案例和技师
			if(type.equals("2") || type.equals("3")) {
				WeOrganOrder order=userMyService.getOrderDetail(orderId);
				request.setAttribute("order", order);
				return new ModelAndView("user/usermy/myOrder/orderDetail");
				
			} else if(type.equals("1")) {
				//预约店铺
				WeOrganOrder order=userMyService.getOrderDetail(orderId);
				if(order != null) {
					Organ organ = weUserService.findOrganById(order.getOrganId());
					request.setAttribute("organ", organ);
				}
				request.setAttribute("order", order);
				return new ModelAndView("user/usermy/myOrder/storeOrderDetail");
			}
		} 
		return null;
	}
	//跳转订单支付
	@RequestMapping("/orderPay")
	public ModelAndView orderPay(@RequestParam String orderId,HttpServletRequest request)throws Exception{
		WeOrganOrder order=userMyService.getOrderDetail(orderId);
		request.setAttribute("order", order);//查到当前订单信息存session
		User user=weUserService.queryUserById(order.getUserId());
		request.setAttribute("user", user);//通过订单得到用户对象,存session
		String couponId = request.getParameter("couponId");
		String price = request.getParameter("price");
		String url = Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString();
		Usercard sysQuery = userPayService.sysQuery(order.getOrganId());
		if(sysQuery==null){
		    request.setAttribute("sysQuery", "false");
		}
		if(price!=null){
			request.setAttribute("price",price);
		}
		if(couponId!=null){//如果有优惠券id就查
			MyCoupon mycoupon = couponService.queryMyCouponById(couponId);
			request.setAttribute("mycoupon",mycoupon);
		}
		Map<String, Object> sign = redisService.getWechatPositioningMessage(url,"user");
		request.setAttribute("sign", sign);
		Organ organ = weOrganService.queryOrganById(order.getOrganId( ));
		if("0" .equals(organ.getOrganPositionState()) && 2 == order.getType()){//预约的是技师 且店铺开通租赁模式
			return new ModelAndView("user/usermy/myOrder/my_orderToStaffPay");
		}
		return new ModelAndView("user/usermy/myOrder/my_orderPay");
	}
	//取消订单
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public ReturnStatus cancelOrder(@RequestParam(required=false)String userId,String orderId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		WeOrganOrder order = userMyService.getOrderById(orderId);
		ReturnStatus status=userMyService.cancelOrder(orderId);
		request.setAttribute("userId", userId);
		if (status.isSuccess()) {
			String time=new SimpleDateFormat("yyyy 年MM 月dd 日  HH:mm").format(new Date());
			//给客户通知
			Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
	        WxTemplate tempToUser = redisService.getWxTemplate("喵，您的订单已成功取消",order.get_id(),time, null, null,null, "没能为您服务，喵小二好伤心呢~~");
	        redisService.send_template_message(userInfo.get("openid").toString(), "user", Configure.ORDER_CANCEL_USER, tempToUser);
	        if (order.getStaffId() !=null) {//给技师通知
	        	Account account = accountService.getAccountByEntityID(order.getStaffId(), "staff");
	        	WxTemplate tempToStaff = redisService.getWxTemplate("亲爱的技师{"+order.getStaffName()+"}，你的订单于"+time+"取消。",order.get_id(),user.getNick(), user.getPhone(),order.getOrderTime() ,order.getTitle()+" 1人", "喵小二感谢您的支持与信任。");
	        	if (account !=null) {
	        		redisService.send_template_message(account.getAccountName(),"staff", Configure.ORDER_CANCEL_STAFF, tempToStaff);
				}
			}else {//给店铺通知
				Account account = accountService.getAccountByEntityID(order.getOrganId(), "organ");
				WxTemplate tempToOrgan = redisService.getWxTemplate("喵，您有一笔订单取消啦",order.get_id(),user.getNick(), user.getPhone(),order.getOrderTime() ,order.getTitle()+" 1人", "喵小二感谢您的支持与信任。");
				if (account !=null) {
					redisService.send_template_message(account.getAccountName(),"organ", Configure.ORDER_CANCEL_ORGAN, tempToOrgan);
				}
			}
			return status;
		}
		return status;
	}
	//扫码支付成功添加订单并评价
	@RequestMapping("/ScanToEvaluate")
	public ModelAndView ScanToEvaluate(@RequestParam(required=false)String payOrderId,HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ReturnStatus status=userMyService.scanSaveOrder(payOrderId);
		if (status.isSuccess()) {
			return toEvaluateOrder(user.get_id(), status.getMessage(), request);
		}
		return new ModelAndView("redirect:/w/home/toHomePage");
	}
	//跳转我的消息
	@RequestMapping("/myMessage")
	public ModelAndView myMessage(@RequestParam(required = false) String userId,HttpServletRequest request)throws Exception {
		request.setAttribute("userId", userId);
		return new ModelAndView("user/usermy/myMessage");
	}
	//我的消息列表
	@RequestMapping("/myMessageList")
	@ResponseBody
	public FlipInfo<WeMessage> myMessageList(String type,HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		FlipInfo<WeMessage> message=userMyService.myMessageList(user.get_id(),type);
		return message;
	}
	
	//跳转申请赔付
	@RequestMapping("/compensate")
	public ModelAndView compensate(@RequestParam(required = false) String userId,HttpServletRequest request)throws Exception {
	    request.setAttribute("userId", userId);
		return new ModelAndView("user/usermy/compensate");
	}
	
	//赔付列表
	@RequestMapping("/compensateList")
	@ResponseBody
	public FlipInfo<WeUserCompensate> compensateList(@RequestParam(required=false)String userId,String type,HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		FlipPageInfo<WeUserCompensate> flp=new FlipPageInfo<WeUserCompensate>(request);
		flp.getParams().remove("userId");
		flp.getParams().remove("type");
		flp.setSortField("state");
		flp.setSortOrder("desc");
		FlipInfo<WeUserCompensate> compensate=userMyService.compensateList(user.get_id(),type,flp);
		return compensate;
	}
	//跳转赔付规则说明
	@RequestMapping("/compensateRules")
	public ModelAndView compensateRules(@RequestParam(required = false)HttpServletRequest request)throws Exception {
		return new ModelAndView("user/usermy/compensate_rules");
	}
	//申请处理结果
	@RequestMapping("/compensateResult")
	public ModelAndView compensateResult(@RequestParam(required = false) String userId,String compensateId,HttpServletRequest request)throws Exception {
		WeUserCompensate compensate= userMyService.getCompensate(compensateId);
		request.setAttribute("userId", userId);
		request.setAttribute("compensate", compensate);
		return new ModelAndView("user/usermy/compensate_detail");
	}
	//发起赔付
	@RequestMapping("/createCompensate")
	public ModelAndView createCompensate(@RequestParam(required = false) String userId,HttpServletRequest request)throws Exception {
		request.setAttribute("userId", userId);
		return new ModelAndView("user/usermy/create_compensate");
	}
	//跳转项目
	@RequestMapping("/toSelectOrder")
	public ModelAndView toSelectOrder(@RequestParam(required=false)String userId,HttpServletRequest request)throws Exception{
		request.setAttribute("userId", userId);
		return new ModelAndView("user/usermy/to_select_order");
	}
	//搜索赔付项目
	@RequestMapping("/searchCompensate")
	@ResponseBody
	public String searchCompensate(@RequestParam(required=false)String orderId,HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		WeUserCompensate compensate = userMyService.getMyCompensate(user.get_id(),orderId);
		if (compensate !=null) {
			return "false";
		}else {
			return "true";
		}
	}
	//跳转赔付者
	@RequestMapping("/toSelectProvider")
	public ModelAndView toSelectProvider(@RequestParam(required=false)String userId,String orderId,HttpServletRequest request)throws Exception{
		request.setAttribute("orderId", orderId);
		request.setAttribute("userId", userId);
		return new ModelAndView("user/usermy/to_select_provider");
	}
	//跳转赔付类型
	@RequestMapping("/selectType")
	public ModelAndView selectType(@RequestParam(required=false)String userId,String orderId,String target,HttpServletRequest request)throws Exception{
		FlipInfo<Code> fpi = new FlipPageInfo<Code>(request);
		fpi.getParams().remove("userId");
		fpi.getParams().remove("target");
		fpi.getParams().remove("orderId");
		FlipInfo<Code> types = userMyService.selectType(fpi);
		request.setAttribute("types", types);
		request.setAttribute("orderId", orderId);
		request.setAttribute("userId", userId);
		request.setAttribute("target", target);
		return new ModelAndView("user/usermy/change_type");
	}
	//选择跳回
	@RequestMapping("/returnCompensate")
	public ModelAndView selectType(@RequestParam(required=false)String orderId,String userId,String target,String codeId,HttpServletRequest request,HttpSession session)throws Exception{
		WeOrganOrder order= userMyService.getOrderById(orderId);
		if(order != null) {
			if(order.getType() ==1) {
			   request.setAttribute("target", 1);
			} else {
			   request.setAttribute("target", 2);
			}
		}
		Code code=userMyService.getCodeById(codeId);
		request.setAttribute("userId", userId);
		request.setAttribute("order", order);
		request.setAttribute("code", code);
		return new ModelAndView("user/usermy/create_compensate");
	}
	//保存赔付
	@RequestMapping("/saveCompensate")
	public ModelAndView saveCompensate(@RequestParam(required=true)String userId,String orderId,String codeId,String target,String desc,HttpServletRequest request)throws Exception{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
	    List<String> logos = new ArrayList<String>();
	    String logo = request.getParameter("logo");
	    if(!StringUtils.isEmpty(logo)) {
	    	logos.add(logo);
	    }
	    String logo1 = request.getParameter("logo1");
	    if(!StringUtils.isEmpty(logo1)) {
	    	logos.add(logo1);
	    }
	    String logo2 = request.getParameter("logo2");
	    if(!StringUtils.isEmpty(logo2)) {
	    	logos.add(logo2);
	    }
	    String logo3 = request.getParameter("logo3");
	    if(!StringUtils.isEmpty(logo3)) {
	    	logos.add(logo3);
	    }
		ReturnStatus status= userMyService.saveCompensate(user.get_id(),orderId,codeId,target,desc,logos);
		if (status.isSuccess()) {
	     	return compensate(userId, request);
		}
		return createCompensate(userId, request);
	}
	
	@RequestMapping("/toSetSys")
	public ModelAndView toSetSys(HttpServletRequest request){
		return new ModelAndView("user/usermy/set_sys");
	}
	
	@RequestMapping("/toMyFeedBack")
	public ModelAndView toMyFeedBack(HttpServletRequest request){
		return new ModelAndView("user/usermy/my_feedback");
	}
	/**
	 * 提交评论
	 * @param request
	 * @return
	 */
	@RequestMapping("/comFeedBack")
	public ModelAndView comFeedBack(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		String comment = request.getParameter("comment");
		String phone = request.getParameter("phone");
		HttpSession session =  request.getSession();
		User user=(User)session.getAttribute("user");
		WeUserFeedback weUserFeedback = new WeUserFeedback();
		weUserFeedback.setIdIfNew();
		weUserFeedback.setUserId(user.get_id());
		weUserFeedback.setDesc(comment);
		weUserFeedback.setContact(phone);
		weUserFeedback.setType("user");
		weUserFeedback.setCreateTimeIfNew();
		weUserFeedback.setUserName(user.getNick());
		userMyService.saveFeedBack(weUserFeedback);
		String feedback = (String)request.getParameter("feedback");
		if(!StringUtils.isEmpty(feedback)){
			return new ModelAndView("redirect:/w/home/toHomePage.do");
		}
		long messageCount = userMyService.findUserMessageCount(user.get_id());
		mv.addObject("messageCount", messageCount);
		mv.setViewName("user/usermy/my_home");
		return mv;
	}
	/**
	 * 跳转到关于我们的静态页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toMyUs")
	public ModelAndView toMyUs(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/my_us");
		return mv;
	}
	
	/**
	 * 跳转到我转赠界面
	 */
	@RequestMapping("/myDonation")
	public ModelAndView myDonation(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/my_donation");
		return mv;
}
	
	
	/**
	 * 跳转到输入金额界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/comfirtDonation")
	public ModelAndView comfirtDonation(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		String phone = request.getParameter("phone");
		HttpSession session =  request.getSession();
		User userSession =(User) session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		if(!StringUtils.isEmpty(phone)) {
			User receiveUser = weUserService.findUserByPhone(phone);
			mv.addObject("receiveUser", receiveUser);
			mv.addObject("walletAmount", user.getWalletAmount());
		} 
		mv.setViewName("user/usermy/comfirt_donation");
		return mv;
	}
	/**
	 * 跳转到我转赠界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/commitDonation")
	@ResponseBody
	public ReturnStatus commitDonation(HttpServletRequest request,HttpServletResponse response){
		String phone  = String.valueOf(request.getParameter("phone"));
		HttpSession session =  request.getSession();
		User userSession =(User) session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		if(phone.equals(user.getPhone())) {
			return new ReturnStatus(3,"喵,对不起,不能转赠给自己");
		}
		User receiveUser = weUserService.findUserByPhone(phone);
		if(receiveUser != null) {
			if(!StringUtils.isEmpty(user.getPayPassword())) {
				return new ReturnStatus(1);
			} else {
				return new ReturnStatus(4);
			}
		} else  {
			return new ReturnStatus(2,"被转增人未绑定手机号请在任性猫公众号中点击我的-个人信息里完成手机号绑定");
		} 
	} 
	
	/**
	 * 跳转到我转赠界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSetPayPassword")
	public ModelAndView toSetPayPassword(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("user/usermy/set_PayPassword");
		String donationPhone = request.getParameter("phone");
		if(!StringUtils.isEmpty(donationPhone)) {
			mv.addObject("donationPhone", donationPhone);
		}
		return mv;
	} 
	/**
	 * 设置支付密码
	 * @param request
	 * @param response
	 * @return
	 */
	//TODO 设置支付密码
	@RequestMapping(value ="/setPayPassword", method = RequestMethod.POST)
	@ResponseBody
	public ReturnStatus setPayPassword(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		String donationPhone = request.getParameter("donationPhone");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		if(!StringUtils.isEmpty(user.getPhone())) { //已经设置过手机号
			if(phone.equals(user.getPhone())) {
				if(accountService.verify(phone, code).isSuccess()) {
					//设置支付密码
					if(password.equals(confirmPassword)) {
						password = passwordEncoder.encodePassword(password, userSession.get_id());
						userMyService.updatePwd(user.get_id(), password);
						if(!StringUtils.isEmpty(donationPhone)) {
							return new ReturnStatus(1,donationPhone);
						} else {
							return new ReturnStatus(5);
						}
					} else {
						return new ReturnStatus(4,"两次密码输入的不一致,请重新输入");
					}
				} else {
					//验证码验证失败
					return new ReturnStatus(2,"验证码验证失败,请重新获取");
				}
			} else {
				//和绑定的不一致
				return new ReturnStatus(3,"你输入的手机号和绑定的不一致不能设置支付密码。");
			}
		} else {
			//没有绑定过手机号
			if(userMyService.userVerify(phone, code, userSession.get_id()).isSuccess()){
				User u=userMyService.queryUserByPhone(phone);
				if(u!=null){
					session.setAttribute("user", u);
				}
				if(password.equals(confirmPassword)) {
					password = passwordEncoder.encodePassword(password, userSession.get_id());
					userMyService.updatePhone(u.get_id(), phone);
					userMyService.updatePwd(u.get_id(), password);
					if(!StringUtils.isEmpty(donationPhone)) {
						return new ReturnStatus(1,donationPhone);
					} else {
						return new ReturnStatus(5);
					}
				} else {
					return new ReturnStatus(4,"两次密码输入的不一致,请重新输入");
				}
			} else {
				return new ReturnStatus(2,"验证码验证失败,请重新获取");
			}
		}
	}
	/**
	 * 提交用户输入的金额
	 * @param request
	 * @param response
	 * @return   0 表示转赠失败  1 表示转赠成功    2.表示密码错误      3.表示余额不足   4.表示你还没设置支付密码
	 * 	 */
	@RequestMapping("/comDonation")
	@ResponseBody
	public int comDonation(HttpServletRequest request,HttpServletResponse response){
		try {
			double amount = Double.parseDouble(request.getParameter("amount"));
			String password = request.getParameter("password");
			String receiveUserId = request.getParameter("receiveUserId");
			HttpSession session = request.getSession();
			User userSession  = (User)session.getAttribute("user");
			User user = weUserService.queryUserById(userSession.get_id());
			password = passwordEncoder.encodePassword(password, userSession.get_id());
			String payPassword = user.getPayPassword();
			if(payPassword != null) {
				if(password.equals(payPassword)) {
					double walletAmount = user.getWalletAmount();
					walletAmount =  walletAmount - amount;
					if(walletAmount>0) {
						//这里添加更新余额逻辑
						userMyService.updateUserWalletAmount(userSession.get_id(), walletAmount);
						WeUserWalletHis  weDonation  =  new WeUserWalletHis();
						weDonation.setNewId();
						weDonation.setNewCreate();
						weDonation.setUserId(receiveUserId);
						weDonation.setSendUserId(user.get_id());
						weDonation.setAmount(amount);
						weDonation.setDesc("用户 "+user.getNick()+" 钱包转赠");
						//这里添加更新余额逻辑
						User receiveUser = weUserService.queryUserById(receiveUserId);
						double rWalletAmount = receiveUser.getWalletAmount() + amount;
						userMyService.updateUserWalletAmount(receiveUserId, rWalletAmount);
						userMyService.saveDonationRecord(weDonation);
						weDonation.setNewId();
						weDonation.setUserId(user.get_id());
						weDonation.setSendUserId(receiveUserId);
						weDonation.setAmount(-amount);
						weDonation.setDesc("向用户"+receiveUser.getNick()+" 转赠");
						userMyService.saveDonationRecord(weDonation);
						//用户通知
						//添加消息通知
						//转增方通知
						Account account = accountService.getAccountByEntityID(user.get_id(), "user");
						WxTemplate tempToUser = redisService.getWxTemplate("喵，"+user.getNick()+"，您的转增交易已成功", "任性猫钱包余额转增",amount+"","0",  getTime(new Date(),"yyyy-MM-dd HH:mm:ss"),null, "感谢您的使用！");
						redisService.send_template_message(account.getAccountName(), "user", Configure.DONATION_INFO, tempToUser);
						//收账方
						 account = accountService.getAccountByEntityID(receiveUser.get_id(), "user");
						 tempToUser = redisService.getWxTemplate("喵，"+receiveUser.getNick()+"，您收到一笔转增交易", "任性猫钱包余额转增",amount+"","0",  getTime(new Date(),"yyyy-MM-dd HH:mm:ss"),null, "感谢您的使用！");
						redisService.send_template_message(account.getAccountName(), "user", Configure.DONATION_INFO, tempToUser);
						return 1;
					} else {
						return 3;
					}
				} else {
					 return 2;
				}
			} else {
				 return 4;
			}
		} catch (Exception e) {
			return 0;
		}
	} 
	/**
	 * 确认密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toComPwd") 
	public ModelAndView toComPwd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		double amount = Double.parseDouble(request.getParameter("amount"));
		String receiveUserId = request.getParameter("userId");
		mv.addObject("amount", amount);
		mv.addObject("receiveUserId", receiveUserId);
		mv.setViewName("user/usermy/com_pwd");
		return mv;
	}
	/**
	 * 我的钱包页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/myWallet") 
	public ModelAndView myWallet(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = weUserService.queryUserById(userSession.get_id());
		mv.addObject("user", user);
		mv.setViewName("user/usermy/my_wallet");
		return mv;
	}
	/**
	 * 我的转赠记录界面
	 */
	@RequestMapping("/donationRecord") 
	public ModelAndView donationRecord(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/donation_record");
		return mv;
	}
	/**
	 * 我的提现记录界面
	 */
	@RequestMapping("/toInputMoney") 
	public ModelAndView toInputMoney(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session  = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
		mv.addObject("user", user);
		if(!StringUtils.isEmpty(user.getPayPassword())) {
			mv.setViewName("user/usermy/apply_cash");
		} else {
			mv.setViewName("user/usermy/set_PayPassword");
		}
		return mv;
	}
	/**
	 * 输入提现密码界面
	 */
	@RequestMapping("/inputPwd") 
	public ModelAndView inputPwd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String money = request.getParameter("money");
		mv.addObject("money", money);
		mv.setViewName("user/usermy/input_pwd");
		return mv;
	}
	
	/**
	 * 提现到微信
	 */
	@RequestMapping("/toCashWeChat") 
	@ResponseBody
	public int toCashWeChat(HttpServletRequest request,HttpServletResponse response) {
		try {
			HttpSession session  = request.getSession();
			User userSession = (User)session.getAttribute("user");
			String userId = userSession.get_id();
			String money = request.getParameter("money");
			User user = userMyService.queryUserById(userId);
			double moneyTemp = Double.parseDouble(money);
			if(user.getWalletAmount() <= 0) {
				return 3; // 你的余额不足了提现
			}
			if(user.getWalletAmount() < moneyTemp) {
				return 3; // 你的余额不足了提现
			}
			int senderType = 1;// 表示用户
			int status = 0;    //表示未完成
			int type =1;    //表示提现
			
			boolean payStatus = userMyService.queryWePayLog(userId,senderType,status,type);
			if(payStatus) return 5; //有未完成的支付
			
			int amount = 100 * Integer.parseInt(money);
			String password  = request.getParameter("password");
			if (StringUtils.isEmpty(userId)) {
				throw new BaseException("用户userId为空！！");
			}
			if (StringUtils.isEmpty(password)) {
				throw new BaseException("密码为空！！");
			}
			password  = passwordEncoder.encodePassword(password, userId);
			if(password.equals(user.getPayPassword())) {
				Account account = userMyService.findOneAccount(userId);
				if(account == null) {
					throw new BaseException("该用户account表里openId为空！");
				}
				WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
				weUserWalletHis.setIdIfNew();
				weUserWalletHis.setDesc("用户提现");
				weUserWalletHis.setAmount(Integer.parseInt(money) * -1);
				weUserWalletHis.setUserId(userId);
				weUserWalletHis.setCreateTime(new Date());
				weUserWalletHis.setOrderId("1");
				String time=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
				WePayLog wePayLog = new WePayLog();
				wePayLog.setIdIfNew();
				wePayLog.setUserId(userId);
				wePayLog.setStatus(0);
				wePayLog.setSenderType(1);
				wePayLog.setMessage("喵,您于"+time+" 成功申请提现喽~"+money+"元");
				wePayLog.setType(1);
				wePayLog.setMoney(-moneyTemp);
				wePayLog.setCreateTimeIfNew();
				userMyService.saveWePayLog(wePayLog);
				Map<String, String> returnResult = CashUtil.CashToWeChat(weUserWalletHis.get_id(),account.getAccountName(), amount, user.getNick() + "你已经成功提现 " + money + "元", request.getRemoteAddr());
				if(returnResult != null) {
					if(!StringUtils.isEmpty(returnResult.get("err_code"))) {
						return 4;
					}
				}
				WePayLog wePayLogNew = userMyService.queryWePayLogById(wePayLog.get_id());
				wePayLogNew.setMessage(wePayLogNew.getMessage()+"微信返回结果:"+returnResult);
				wePayLogNew.setStatus(1);
				userMyService.saveWePayLog(wePayLogNew);
				userMyService.saveWeUserWalletHis(weUserWalletHis);
				userMyService.updateUserWalletAmount(userId,user.getWalletAmount()- Double.parseDouble(money));
				//提现成功推送消息
		    	WxTemplate tempToUser = redisService.getWxTemplate("喵,您于"+time+" 成功申请提现喽~", money+"元","0元", money+"元","任性猫网站提现",null, "喵小二提醒您：提现已成功，请您注意查收哦~~");
		    	redisService.send_template_message(account.getAccountName(), "user", Configure.WITHDRAW_USER, tempToUser);
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	@RequestMapping("/isPayPassword") 
	@ResponseBody
	public int isPayPassword(HttpServletRequest request,HttpServletResponse response) {
		String money = request.getParameter("money");
		HttpSession session  = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
		if(user.getWalletAmount()-Double.parseDouble(money) < 0) {
			return 2;
		}
		if(StringUtils.isEmpty(user.getPayPassword())) {
			return 1;
		} else {
			return 0;
		}
	}
	public String getTime(Date date,String reg){
		SimpleDateFormat fom=new SimpleDateFormat(reg);
		String time=fom.format(date);
		return time;
	}
	/**
	 * 跳转到红包界面
	 */
	@RequestMapping("/toRedPack") 
	public ModelAndView toRedPack(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/usermy/red_pack");
		return mv;
	}
	
	@RequestMapping("/getRedPack") 
	@ResponseBody
	public ReturnStatus getRedPack(HttpServletRequest request) {
		ReturnStatus returnStatus = null;
		HttpSession session  = request.getSession();
		User user = (User)session.getAttribute("user");
		double longitude = (double) session.getAttribute("longitude");
		double latitude = (double) session.getAttribute("latitude");
		List<WeRed> weReds =  userMyService.findWeReds(longitude, latitude, user.get_id());
		try {
			returnStatus = userMyService.getWeRed(weReds, user.get_id());
			return returnStatus;
		} catch (BaseException e) {
			e.printStackTrace();
			returnStatus = new ReturnStatus(-1,"程序内部有错误");  //表示领取红包发生了异常
			return returnStatus;
		}
	}
	@RequestMapping("/redDetail") 
	public ModelAndView redDetail(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String redPackId = request.getParameter("redPackId");
		WeRedRecord  weRedRecord = userMyService.findWeRedRecord(redPackId);
		List<WeRedRecord> weRedRecords =  userMyService.findWeRedRecords(weRedRecord.getRedId());
		WeRed weRed = userMyService.findRedPack(weRedRecord.getRedId());
		Staff staff = weUserService.queryStaffById(weRed.getSenderId());
		Organ organ = weUserService.findOrganById(weRed.getOrganId());
		mv.addObject("staff", staff);
		mv.addObject("organ", organ);
		mv.addObject("weRed", weRed);
		mv.addObject("weRedRecord", weRedRecord);
		mv.addObject("weRedRecords", weRedRecords);
		mv.addObject("redPackId", redPackId);
		mv.setViewName("user/usermy/red_detail");
		return mv;
	}
	//跳转到打赏技师
	@RequestMapping("/toRewardStaff") 
	public ModelAndView toRewardStaff(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String orderId = request.getParameter("orderId");
		mv.addObject("orderId", orderId);
		WeOrganOrder order=userMyService.getOrderDetail(orderId);
		Staff staff = userMyService.findStaffById(order.getStaffId());
		mv.addObject("staff", staff);
		mv.setViewName("user/usermy/reward_satff");
		return mv;
	}
	
	//跳转到打赏技师
	@RequestMapping("/verifyWallet") 
	@ResponseBody
	public boolean verifyWallet(HttpServletRequest request) {
		String money = request.getParameter("money");
		double moneyTemp =  Double.parseDouble(money);
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		User user = userMyService.queryUserById(userSession.get_id());
		if(user.getWalletAmount() > moneyTemp) {
			return true;
		} else {
			return false;
		}
	}



	@RequestMapping("/game")
	public ModelAndView game(@RequestParam(required=false)String userId,HttpServletRequest request)throws Exception{
		return new ModelAndView("user/usermy/index");
	}



	//游戏响应
	@RequestMapping("/startGame")
	@ResponseBody
	public ReturnStatus startGame()throws Exception{
		return new ReturnStatus(1);
	}
}
