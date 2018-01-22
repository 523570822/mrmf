package com.mrmf.module.app;


import com.mrmf.entity.User;
import com.mrmf.entity.UserCollect;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.WxgetInfo;
import com.mrmf.service.coupon.CouponGrantService;
import com.mrmf.service.redis.RedisService;
import com.mrmf.service.staff.StaffMyService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.usermy.UserMyService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weorgan.WeOrganService;
import com.mrmf.service.weuser.WeUserService;
import com.mrmf.service.wxOAuth2.WXOAuth2Service;
import com.mrmf.socket.WebSocket;
import com.osg.entity.Entity;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FileNameUtil;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.web.cache.CacheManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户端首页相关的类
 * @author yangshaodong
 */
@Controller
@RequestMapping("/app")
public class AppController {
	@Autowired 
	private WeUserService weUserService;
	@Autowired
	private StaffService staffService;
	@Autowired 
	private WeComonService weCommonService;
	@Autowired 
	private WxgetInfo wxgetInfo;
	@Autowired
	private UserMyService userMyService;
	@Autowired
	private AccountService accountService;
	@Autowired 
	private RedisService redisService;
	@Autowired 
	private WeOrganService weOrganService;
	@Autowired
	private StaffMyService staffMyService;
	@Autowired
	private CouponGrantService couponGrantService;
	@Autowired
	private WXOAuth2Service wxoAuth2Service;
	@Autowired
	private CacheManager cacheManager;

	private static Logger logger = Logger.getLogger(AppController.class);
	/**
	 *
	 * 通过手机号获得验证码	getCodeByPhone
	 * {"phone":"15620512895","type":"user"}
	 */
	@RequestMapping(value ="/getCodeByPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCodeByPhone(HttpServletRequest request, HttpServletResponse response) {
		//'phone':phone,'type':'user'
		String phone =String.valueOf(request.getParameter("phone"));
		String type = String.valueOf(request.getParameter("type"));
		Map<String, Object> map = new HashMap<String, Object>();
		if(weCommonService.verifycode(phone, type).isSuccess()) {
			map.put("code","0");
			map.put("message","获取验证码成功！");
			map.put("data","");
		}else {
			map.put("code","0");
			map.put("message","获取验证码成功！");
			map.put("data","");
		}
		return map;

	}

	/**
	 *
	 * APP  用户注册
	 *  {  "phone":"15620512895",  "code":"1052",  "mail":"523",  "password":"159221"  }
	 */
	@RequestMapping(value = "/userRegist", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> userRegist(String phone,String code,String mail,String password,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			HttpSession session=request.getSession(true);

			//	Map<String,Object> userInfo=(Map<String,Object>)session.getAttribute("userInfo");
			Map<String,Object> userInfo=new HashMap<String, Object>();;
			//		logger.info("已经存在userInfo:"+userInfo);
			userInfo.put("openid",phone);
			userInfo.put("unionid","asdf");
			userInfo.put("password",password);
            userInfo.put("phone",phone);




			// Map<String,Object> user_token=wxgetInfo.getAccess_token(code,"user");
			//   userInfo=wxgetInfo.getUserInfo(user_token);
			logger.info("userInfo:"+userInfo);
			ReturnStatus status=null;
			String oppenid="";
			String unionid="";






			if(userInfo.get("openid")!=null){
				oppenid=userInfo.get("openid").toString();
				session.setAttribute("openid", oppenid);
			}
			if(userInfo.get("unionid")!=null){
				unionid=userInfo.get("unionid").toString();
			}


			status=weCommonService.isExist(oppenid, unionid, "user");


		/*	if(accountService.verify(phone, code).isSuccess()) {

			} else {

				map.put("code","1");
				map.put("message","验证码有误");
				map.put("data","");
				return map;
			}*/


			if(status.isSuccess()||userMyService.isHaveUserPhone(phone)){
				//用户存在直接返回已经存在
				map.put("code","1");
				map.put("message","用户已经存在   ");
				map.put("data","");
				return map;
			}else{

				//用户不存在开始注册
				GpsPoint gpsPoint=new GpsPoint();
				double longitude;
				double latitude;
				session.setAttribute("city", "北京市");
				session.setAttribute("cityId", "1667920738524089172");
				Object lo=session.getAttribute("longitude");
				Object la=session.getAttribute("latitude");
				if(lo!=null){
					longitude=Double.parseDouble(lo.toString());
					gpsPoint.setLongitude(longitude);
				}
				if(la!=null){
					latitude=Double.parseDouble(la.toString());
					gpsPoint.setLongitude(latitude);
				}
				userInfo.put("gpsPoint", gpsPoint);
				if(userInfo.get("headimgurl") != null && !userInfo.get("headimgurl").equals("")) {
					InputStream in = weCommonService.returnBitMap((String)userInfo.get("headimgurl"));
					String imgName = weCommonService.downImg(in,request);
					String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down/"+imgName;
					File imgFile=new File(url);
					InputStream inFile=new FileInputStream(imgFile);
					String ossId = Entity.getLongUUID() + FileNameUtil.getSuffix(imgName);
					String etag = OSSFileUtil.upload(inFile, imgFile.length(), ossId, OSSFileUtil.pubBucketName);
					in.close();
					inFile.close();
					userInfo.put("headimgurl", ossId);
				}
				/*	if(!StringUtils.isEmpty(state) && !state.equals("123")) {
						String accountID = "";
						String accountType = "";
						String[] states = state.split("_");
						if (states != null && states.length > 1){
							accountID = states[1];
							accountType = states[0];
						}
						userInfo.put("accountType",accountType);
						userInfo.put("invitor", accountID);

					}*/
				userInfo.put("inviteDate", new Date());
				status=weCommonService.saveUser(userInfo);
				if(status.isSuccess()){
					User user=weUserService.queryUserByOpenId(oppenid);
					logger.info("最终的user:"+user);
					request.setAttribute("user", user);
					session.setAttribute("user", user);
					session.setAttribute("userId", user.get_id());
					couponGrantService.grantCouponByuserUuidAndType(user.get_id(),"关注",-1,"");
					userMyService.updateUser(user.get_id(),userInfo);

					map.put("code","0");
					map.put("message","用户注册成功");
					map.put("data","");
				}


			}

			logger.info("最终的userInfo:"+userInfo);
			oppenid=userInfo.get("openid").toString();
			User user = weUserService.queryUserByOpenId(oppenid);
			userMyService.updateUser(user.get_id(),userInfo);
			//	Map<String, Object> sign = redisService.getWechatPositioningMessage(Configure.DOMAIN_URL + request.getRequestURI() + "?" + request.getQueryString(),"user");

			//	List<WeCarousel> weCarousels = weUserService.findCarousels();
			session.setAttribute("userInfo", userInfo);
		} catch (Exception e) {
			map.put("code","1");
			map.put("message","该手机好异常");
			map.put("data","");
			e.printStackTrace();
		}

		return map;
	}


	@RequestMapping("/imgList")
	@ResponseBody
	public  Map<String, Object>  imgList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		Map<String, Object> map = new HashMap<String, Object>();

		FlipInfo<UserCollect> fpi = userMyService.mycollect("7840835260965106637","3","116.387194","39.927073","1","10");
		map.put("code","1");
		map.put("message","该手机好异常");
		map.put("data",fpi);



		return map;
	}


	@RequestMapping("/test")
	@ResponseBody
	public  Map<String, Object>  text(HttpServletRequest request, HttpServletResponse response) throws IOException {


		Map<String, Object> map = new HashMap<String, Object>();
//获取本机的InetAddress实例




//获取其他主机的InetAddress实例
	//	InetAddress address2 =InetAddress.getByName("其他主机名");
		//InetAddress address3 =InetAddress.getByName("IP地址");

   //  StringBuffer  test=new StringBuffer("其他主机名:"+address2+";");
		String phone =String.valueOf(request.getParameter("phone"));
        String type =String.valueOf(request.getParameter("type"));
	//	test.append("IP地址:"+address3);
		WebSocket asd = WebSocket.webSocketSet.get(phone);


            if( WebSocket.webSocketSet.get(phone)!=null){
                WebSocket.webSocketSet.get(phone).sendMessage(type);


            map.put("code","1");
            map.put("message","成功通知");
            map.put("data","geiliba");
        } else {
            map.put("code","1");
            map.put("message","用户不存在");
            map.put("data","geiliba");
        }
		return map;
	}

    /**
     *  App支付配置
     * @param phone
     * @param type（staff:技师;user:用户）
     * @param money
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/appPay")
    @ResponseBody
    public  Map<String, Object>  appPay(String  phone,String type,String money,HttpServletRequest request, HttpServletResponse response) throws IOException {


        Map<String, Object> map = new HashMap<String, Object>();




            map.put("code","0");
            map.put("message","支付成功");
            map.put("data","geiliba");




        return map;
    }
}














