package com.mrmf.module.wx;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Usersort;
import com.mrmf.entity.wxpay.PayCommonUtil;
import com.mrmf.entity.wxpay.XMLUtil;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Configure;
import com.mrmf.service.common.WxgetInfo;
import com.mrmf.service.organPosition.OrganPosition;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffMyService;
import com.mrmf.service.userPay.UserPayService;
import com.mrmf.service.userService.UserService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.weuser.WeUserService;
import com.osg.entity.Entity;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.osg.framework.web.springmvc.error.handler.WebExceptionHandler.logger;

/**
 * 支付回调
 * 
 * @author weitong
 * 
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private UserPayService userPayService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private WeUserService weUserService;
	@Autowired
	private WeComonService weCommonService;
	@Autowired
	private WxgetInfo wxgetInfo;
	@Autowired
	private UserMyService userMyService;
	@Autowired
	private UserService userService;
	@Autowired
	private StaffMyService staffMyService;
	@Autowired
	private WeOrganService weOrganService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private OrganPosition organPosition;


	/**
	 * 支付输入金额页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toPay", method = RequestMethod.GET)
	public ModelAndView toPay(@RequestParam(required = true) String organId, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");

		Organ organ = weOrganService.queryOrganById(organId);
		Usercard sysQuery = userPayService.sysQuery(organId);
		if (sysQuery == null || sysQuery.get_id() == "") {
			String msg = "平台会员卡不存在";
			return new ModelAndView("redirect:/w/home/toHomePage.do?msg=" + msg);
		} else {
			mv.addObject("organ", organ);
			mv.addObject("organId", organId);
			mv.addObject("userId", user.get_id());
			mv.setViewName("user/userpay/userpay");

			String url = Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString();
			Map<String, Object> sign = redisService.getWechatPositioningMessage(url, "user");
			mv.addObject("sign", sign);
			return mv;
		}
	}


	/**
	 * 微信访问主页的控制类
	 */
	@RequestMapping(value = "/wxSaoMaToPay.do", method = RequestMethod.GET)
	public ModelAndView userIndex(String code, String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);//
		String organId = state;
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("userInfo");
		User user = null;
		String oppenid = "";
		String unionid = "";
		if (userInfo == null) {
			Map<String, Object> user_token = wxgetInfo.getAccess_token(code, "user");
			userInfo = wxgetInfo.getUserInfo(user_token);
			session.setAttribute("userInfo", userInfo);
		}
		if (userInfo != null) {
			if (userInfo.get("openid") != null) {
				oppenid = userInfo.get("openid").toString();
			}
			if (userInfo.get("unionid") != null) {
				unionid = userInfo.get("unionid").toString();
			}
			ReturnStatus status = weCommonService.isExist(oppenid, unionid, "user");

			if (status.isSuccess()) {
				user = weUserService.queryUserByOpenId(oppenid);
				request.setAttribute("user", user);
				session.setAttribute("user", user);
				session.setAttribute("city", "北京");
				session.setAttribute("cityId", "1667920738524089172");
				if (user != null && "".equals(user.getAvatar())) {
					InputStream in = weCommonService.returnBitMap((String) userInfo.get("headimgurl"));
					String imgName = weCommonService.downImg(in, request);
					String url = request.getSession().getServletContext().getRealPath("") + "/module/resources/down/" + imgName;
					File imgFile = new File(url);
					InputStream inFile = new FileInputStream(imgFile);
					String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
					String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
					in.close();
					inFile.close();
					userMyService.updateImg(user.get_id(), ossId);
				}
			} else {
				GpsPoint gpsPoint = new GpsPoint();
				double longitude;
				double latitude;
				Object lo = session.getAttribute("longitude");
				Object la = session.getAttribute("latitude");
				if (lo != null) {
					longitude = Double.parseDouble(lo.toString());
					gpsPoint.setLongitude(longitude);
				}
				if (la != null) {
					latitude = Double.parseDouble(la.toString());
					gpsPoint.setLongitude(latitude);
				}
				userInfo.put("gpsPoint", gpsPoint);
				InputStream in = weCommonService.returnBitMap((String) userInfo.get("headimgurl"));
				String imgName = weCommonService.downImg(in, request);
				String url = request.getSession().getServletContext().getRealPath("") + "/module/resources/down/" + imgName;
				File imgFile = new File(url);
				InputStream inFile = new FileInputStream(imgFile);
				String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
				String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
				in.close();
				inFile.close();
				userInfo.put("headimgurl", ossId);
				status = weCommonService.saveUser(userInfo);
				if (status.isSuccess()) {
					user = weUserService.queryUserByOpenId(oppenid);
					request.setAttribute("user", user);
					session.setAttribute("user", user);
				}
			}
			if (user == null) {
				user = (User) session.getAttribute("user");
				user = weUserService.queryUserById(user.get_id());
			}
			Organ organ = weOrganService.queryOrganById(organId);
			Usercard sysQuery = userPayService.sysQuery(organId);
			if (sysQuery == null || sysQuery.get_id() == "") {
				String msg = "平台会员卡不存在";
				return new ModelAndView("redirect:/w/home/toHomePage.do?msg=" + msg);
			} else if (user == null || user.get_id() == "") {
				String msg = "用户不存在";
				return new ModelAndView("redirect:/w/home/toHomePage.do?msg=" + msg);
			} else {
				mv.addObject("organ", organ);
				mv.addObject("organId", organId);
				mv.addObject("userId", user.get_id());
				mv.setViewName("user/userpay/userpay");
				request.setAttribute("user", user);
				String url = Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString();
				Map<String, Object> sign = redisService.getWechatPositioningMessage(url, "user");
				mv.addObject("sign", sign);
				return mv;
			}
		}
		String msg = "信息错误";
		return new ModelAndView("redirect:/w/home/toHomePage.do?msg=" + msg);
	}

	/**
	 * 扫码技师端的控制类
	 */
	@RequestMapping(value = "/wxSaoMaToPayStaff.do", method = RequestMethod.GET)
	public ModelAndView toUserIndex(String code, String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String ids = state;
		String[] idArray = ids.split("a");
		String organId = idArray[0];
		String staffId = idArray[1];
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("userInfo");
		User user = null;
		String oppenid = "";
		String unionid = "";
		if (userInfo == null) {
			Map<String, Object> user_token = wxgetInfo.getAccess_token(code, "user");
			userInfo = wxgetInfo.getUserInfo(user_token);
			session.setAttribute("userInfo", userInfo);
		}
		if (userInfo != null) {
			if (userInfo.get("openid") != null) {
				oppenid = userInfo.get("openid").toString();
			}
			if (userInfo.get("unionid") != null) {
				unionid = userInfo.get("unionid").toString();
			}
			ReturnStatus status = weCommonService.isExist(oppenid, unionid, "user");

			if (status.isSuccess()) {
				user = weUserService.queryUserByOpenId(oppenid);
				request.setAttribute("user", user);
				session.setAttribute("user", user);
				session.setAttribute("city", "北京");
				session.setAttribute("cityId", "1667920738524089172");
				if (user != null && "".equals(user.getAvatar())) {
					InputStream in = weCommonService.returnBitMap((String) userInfo.get("headimgurl"));
					String imgName = weCommonService.downImg(in, request);
					String url = request.getSession().getServletContext().getRealPath("") + "/module/resources/down/" + imgName;
					File imgFile = new File(url);
					InputStream inFile = new FileInputStream(imgFile);
					String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
					String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
					in.close();
					inFile.close();
					userMyService.updateImg(user.get_id(), ossId);
				}
			} else {
				GpsPoint gpsPoint = new GpsPoint();
				double longitude;
				double latitude;
				Object lo = session.getAttribute("longitude");
				Object la = session.getAttribute("latitude");
				if (lo != null) {
					longitude = Double.parseDouble(lo.toString());
					gpsPoint.setLongitude(longitude);
				}
				if (la != null) {
					latitude = Double.parseDouble(la.toString());
					gpsPoint.setLongitude(latitude);
				}
				userInfo.put("gpsPoint", gpsPoint);
				InputStream in = weCommonService.returnBitMap((String) userInfo.get("headimgurl"));
				String imgName = weCommonService.downImg(in, request);
				String url = request.getSession().getServletContext().getRealPath("") + "/module/resources/down/" + imgName;
				File imgFile = new File(url);
				InputStream inFile = new FileInputStream(imgFile);
				String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
				String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
				in.close();
				inFile.close();
				userInfo.put("headimgurl", ossId);
				status = weCommonService.saveUser(userInfo);
				if (status.isSuccess()) {
					user = weUserService.queryUserByOpenId(oppenid);
					request.setAttribute("user", user);
					session.setAttribute("user", user);
				}
			}
			if (user == null) {
				user = (User) session.getAttribute("user");
				user = weUserService.queryUserById(user.get_id());
			}
			Organ organ = weOrganService.queryOrganById(organId);
			Staff staff = staffMyService.getStaff(staffId);

			if (user == null || user.get_id() == "") {
				String msg = "用户不存在";
				return new ModelAndView("redirect:/w/home/toHomePage.do?msg=" + msg);
			} else if (StringUtils.isEmpty(user.getPhone())) {
				request.setAttribute("state", state);
				mv.setViewName("user/userpay/binderPhone");
				return mv;
			} else {
				mv.addObject("organ", organ);
				mv.addObject("organId", organId);
				mv.addObject("userId", user.get_id());
				mv.addObject("staff",staff);
				mv.setViewName("user/userpay/userpayToStaff");
				request.setAttribute("user", user);
				String url = Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString();
				Map<String, Object> sign = redisService.getWechatPositioningMessage(url, "user");
				mv.addObject("sign", sign);
				return mv;
			}
		}
		String msg = "信息错误";
		return new ModelAndView("redirect:/w/home/toHomePage.do?msg=" + msg);
	}



	//支付信息
	@RequestMapping("/saveOrder")
	@ResponseBody
	public ReturnStatus saveOrder(String userId, String organId, double price, HttpServletRequest request) throws Exception {
		ReturnStatus result = userPayService.saveUserPayOrder(userId, organId, price, request);
		return result;
	}

	//支付信息
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
	public ModelAndView cancelOrder(String orderId, HttpServletRequest request) throws Exception {
		ReturnStatus result = userPayService.cancelUserPayOrder(orderId);
		return new ModelAndView("redirect:/w/home/toHomePage.do?msg=");
	}

	//订单支付取消
	@RequestMapping(value = "/cancelMyOrder", method = RequestMethod.GET)
	public ModelAndView cancelMyOrder(String payorderId, String orderId, HttpServletRequest request) throws Exception {
		ReturnStatus result = userPayService.cancelUserPayOrder(payorderId);
		return new ModelAndView("redirect:/w/userMy/orderPay.do?orderId=" + orderId);
	}

	//在线充值取消
	@RequestMapping(value = "/cancelPayOnline", method = RequestMethod.GET)
	public ModelAndView cancelPayOnline(String payorderId, String userId, String cardId, String cardType, HttpServletRequest request) throws Exception {
		ReturnStatus result = userPayService.cancelUserPayOrder(payorderId);
		return new ModelAndView("redirect:/w/pay/toPayOnline.do?orderId=" + userId + "&userId=" + userId + "&cardId=" + cardId + "&cardType=" + cardType);
	}

	//去会员卡在线充值页面
	@RequestMapping(value = "/toPayOnline", method = RequestMethod.GET)
	public ModelAndView toPayOnline(String userId, String cardId, String cardType, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/userpay/payonline");
		Userincard card = userService.getInCard(cardId);
		Organ organ = weOrganService.queryOrganById(card.getOrganId());
		Usersort usersort = userPayService.getUsersortById(card.getMembersort());
		request.setAttribute("usersort", usersort);
		request.setAttribute("userId", userId);
		request.setAttribute("cardId", cardId);
		request.setAttribute("organ", organ);
		request.setAttribute("card", card);
		request.setAttribute("cardType", cardType);
		String url = Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString();
		Map<String, Object> sign = redisService.getWechatPositioningMessage(url, "user");
		mv.addObject("sign", sign);
		return mv;
	}

	//在线充值
	@RequestMapping("/payOnline")
	@ResponseBody
	public ReturnStatus payOnline(String userId, String cardId, double price, HttpServletRequest request) throws Exception {
		ReturnStatus result = userPayService.saveUserpayOnline(userId, cardId, price, request);
		return result;
	}

	//订单支付
	@RequestMapping("/payOrder")
	@ResponseBody
	public ReturnStatus payOrder(String userId, String organId, double price, String orderId, HttpServletRequest request) throws Exception {
		ReturnStatus result = userPayService.saveUserPayOrder(userId, organId, price, orderId, request);
		return result;
	}

	/**
	 * 微信支付回调，内容介绍见
	 * https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_7&index=3
	 *
	 * @param xmlObject
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wechatpay", method = {RequestMethod.POST,
			RequestMethod.GET})
	public void handleWeChat(@RequestBody String xmlObject,
							 HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
			outSteam.close();
			inStream.close();
			Map<Object, Object> map = XMLUtil.doXMLParse(xmlObject);
			if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {

				try {
					//获取微信支付信息表
					String orderNo = map.get("out_trade_no").toString();
					ReturnStatus result = null;
					System.out.println("刚才生成的id" + orderNo);
					WeUserPayOrder order = userPayService.getWeUserPayOrder(orderNo);
					PositionOrder positionOrder = organPosition.getPositonById(orderNo);
					if(order != null){//如果通过orderNo获取到order证明是userpay回来的 走下面逻辑
						if (order.getType() == 1) {//微信充值
							if (order.getState() == 0) {
								result = userPayService.payOnlineSuccess(map);
							} else {
								result = new ReturnStatus(true);
							}
						} else if (order.getType() == 3) {//微信订单技师分账模式
							System.out.println("~~~~~~~~~~~~~~~~11111111111111111111~~~~~~~~~");
							if (order.getState() == 0) {
								System.out.println("~~~~~~~~~~~~~~~~进入技师分账模式~~~~~~~~~");
								try{
									result = userPayService.payForStaffSuccess(map);
								}catch (Exception e){
									e.printStackTrace();
								}
							} else {
								result = new ReturnStatus(true);
							}
						} else if (order.getType() == 0) {//微信订单支付
							logger.info("userPayOrder###########################"+order.getState());

							if (order.getState() == 0) {
								result = userPayService.paySuccess(map);
							} else {
								result = new ReturnStatus(true);
							}
						} else if (order.getType() == 2) { //微信红包支付
							if (order.getState() == 0) {
								result = userPayService.payHongbaoSuccess(map);
							} else {
								result = new ReturnStatus(true);
							}
						}
					}else if(positionOrder != null){ //是工位租赁返回的

						if(1 == positionOrder.getState()) {
							result = organPosition.staffPaySuccess(map);
						}else{
							result = new ReturnStatus(true);
						}
					}

					if (result.isSuccess()) {
						response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();

				}

			} else if (map.get("result_code").toString().equalsIgnoreCase("FAIL")) {
				String orderNo = map.get("out_trade_no").toString();
				try {
					ReturnStatus result = userPayService.cancelUserPayOrder(orderNo); //取消用户支付订单
					ReturnStatus returnStatus = userPayService.cancelPositionOrder(orderNo); //取消技师支付订单
					if (result != null && result.isSuccess()) {
						String return_msg = map.get("return_msg").toString();
						response.getWriter().write(PayCommonUtil.setXML("FAIL", return_msg));
					}else if(returnStatus != null && returnStatus.isSuccess()){
						String return_msg = map.get("return_msg").toString();
						response.getWriter().write(PayCommonUtil.setXML("FAIL", return_msg));
					}
				} catch (BaseException e) {
					System.out.println(e);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}

	}

	public String getTime(Date date, String reg) {
		SimpleDateFormat fom = new SimpleDateFormat(reg);
		String time = fom.format(date);
		return time;
	}

	//发送红包
	@RequestMapping("/saveRedPacket")
	@ResponseBody
	public ReturnStatus saveRedPacket(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Staff staff = (Staff) session.getAttribute("staff");
		String scope = request.getParameter("scope");
		String count = request.getParameter("count");
		String money = request.getParameter("money");
		String desc = request.getParameter("desc");
		ReturnStatus status = staffMyService.saveRedPacket(staff.get_id(), scope, count, money, desc, request); //这里是保存红包
		if (status.isSuccess() && !StringUtils.isEmpty(status.getMessage())) {
			status = userPayService.saveHongBaoOrder(staff.get_id(), status.getMessage(), request);
		}
		return status;
	}

	//普通模式订单支付(钱包余额支付)
	@RequestMapping("/balancePayOrder")
	@ResponseBody
	public ReturnStatus balancePayOrder(String userId, String organId, double price, String orderId, HttpServletRequest request) throws Exception {
		String couponId = request.getParameter("couponId");
		ReturnStatus result = userPayService.saveUserBalancePayOrder(userId, organId, price,orderId, couponId,request);
		return result;

    }

	//工位模式订单支付(钱包余额支付)
	@RequestMapping("/balancePayOrderToStaff")
	@ResponseBody
	public ReturnStatus balancePayOrderToStaff(String userId, String organId, double price, String orderId,String staffId, HttpServletRequest request) throws Exception {
		String couponId = request.getParameter("couponId");
		ReturnStatus result = userPayService.saveUserBalancePayOrderToStaff(userId, organId, price,orderId,staffId, couponId,request);
		return result;

	}


}

