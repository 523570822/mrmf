package com.mrmf.service.common;



import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mrmf.entity.wxpay.CommonUtil;


@Service("wxgetInfo")
public class WxgetInfo {
	private static Logger logger = Logger.getLogger(WxgetInfo.class);
	public static String token = null;
	public static String time = null;
	public static String jsapi_ticket = null;
	/**
	 *  获取用户授权token
	 * @param code
	 * @param type 店铺organ 技师 satff 用户user
	 * @return
	 */
	public static Map<String,Object> getAccess_token(String code,String type) {
		String appId="";
		String appSecret="";
		if("organ".equals(type)){
			appId=Configure.organAppID;
			appSecret=Configure.organAppSecret;
		}else if("staff".equals(type)){
			appId=Configure.staffAppID;
			appSecret=Configure.staffAppSecret;
		}else if("user".equals(type)){
			appId=Configure.userAppID;
			appSecret=Configure.userAppSecret;
		}
		logger.info("code:"+code);
		StringBuilder requestUrl = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
		.append(appId).append("&secret=").append(appSecret).append("&code=")
		.append(code).append("&grant_type=authorization_code");
		try {
			//String res = HttpService.doGet(requestUrl.toString());
			String results = CommonUtil.httpsRequest(requestUrl.toString(), "GET", null);
			logger.info("results:"+results);
			Map<String, Object> resultMap=JSON.parseObject(results, HashMap.class);
			if(resultMap!=null){
				return resultMap;
			}
		} catch (Exception e) {
			logger.info("getAccess_token failed");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取用户基本信息
	 * @param access_token 通过code获取的token
	 * @return
	 */
	public static Map<String,Object> getUserInfo(Map<String,Object> access_token){
		StringBuilder requestUrl = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?access_token=")
		.append(access_token.get("access_token").toString()).append("&openid=").append(access_token.get("openid").toString()).append("&lang=zh_CN");
		logger.info("access_token-obj:"+access_token);
		logger.info("access_token:"+access_token.get("access_token").toString());
		logger.info("openid:"+access_token.get("openid").toString());
		try {
			//String res = HttpService.doGet(requestUrl.toString());
			String results = CommonUtil.httpsRequest(requestUrl.toString(), "GET", null);
			System.out.println(results);
			Map<String, Object> resultMap=JSON.parseObject(results, HashMap.class);
			if(resultMap!=null){
				return resultMap;
			}
		} catch (Exception e) {
			logger.info("getUserInfo failed");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取用户基本信息
	 * @param access_token 这里的token是调用接口的token并不是通过code获取的token
	 * @return
	 */
	public static Map<String,Object> getUserInfoTwo(Map<String,Object> access_token){
		StringBuilder requestUrl = new StringBuilder("https://api.weixin.qq.com/cgi-bin/user/info?access_token=")
		.append(access_token.get("access_token").toString()).append("&openid=").append(access_token.get("openid").toString()).append("&lang=zh_CN");
		try {
			String res = HttpService.doGet(requestUrl.toString());
			Map<String, Object> resultMap=JSON.parseObject(res, HashMap.class);
			if(resultMap!=null){
				return resultMap;
			}
		} catch (Exception e) {
			logger.info("getUserInfo failed");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 刷新用户授权的token
	 * @param access_token
	 * @param type店铺organ 技师 satff 用户user
	 * @return
	 */
	public static Map<String,Object> refresh_token(Map<String,Object> access_token,String type){
		StringBuilder requestUrl = new StringBuilder("https://api.weixin.qq.com/sns/auth?access_token=")
		.append(access_token.get("access_token").toString()).append("&openid=").append(access_token.get("access_token").toString());
		try {
			String res = HttpService.doGet(requestUrl.toString());
			Map<String, Object> resultMap=JSON.parseObject(res, HashMap.class);
			if(resultMap!=null){
				String errmsg=resultMap.get("errmsg").toString();
				if(!"ok".equals(errmsg)){
					String appId="";
					String appSecret="";
					if("organ".equals(type)){
						appId=Configure.organAppID;
						appSecret=Configure.organAppSecret;
					}else if("staff".equals(type)){
						appId=Configure.staffAppID;
						appSecret=Configure.staffAppSecret;
					}else if("user".equals(type)){
						appId=Configure.userAppID;
						appSecret=Configure.userAppSecret;
					}

					StringBuilder refresh_token_Url = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid==")
					.append(appId).append("&grant_type=refresh_token&refresh_token=").append(access_token.get("refresh_token").toString());
					resultMap=JSON.parseObject(res, HashMap.class);
					if(resultMap!=null){
						return resultMap;
					}
				}else{
					return resultMap;
				}
			}
		} catch (Exception e) {
			logger.info("refresh_token failed");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取调用接口的token
	 * @param type店铺organ 技师 satff 用户user
	 * @return
	 */
	public static Map<String,Object> getAccess_token(String type) {
		String appId="";
		String appSecret="";
		if("organ".equals(type)){
			appId=Configure.organAppID;
			appSecret=Configure.organAppSecret;
		}else if("staff".equals(type)){
			appId=Configure.staffAppID;
			appSecret=Configure.staffAppSecret;
		}else if("user".equals(type)){
			appId=Configure.userAppID;
			appSecret=Configure.userAppSecret;
		}

		StringBuilder requestUrl = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?appid=")
		.append(appId).append("&secret=").append(appSecret)
		.append("&grant_type=client_credential");
		Map<String, Object> resultMap=null;
		try {
			String res = HttpService.doGet(requestUrl.toString());
			resultMap=JSON.parseObject(res, HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
