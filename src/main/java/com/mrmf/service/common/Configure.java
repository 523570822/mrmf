package com.mrmf.service.common;

import org.springframework.stereotype.Component;

import com.osg.framework.Constants;

/**
 * 存储微信公众号的配置数据
 */
@Component
public class Configure {
	public static String organAppID=Constants.getProperty("organAppID"); 
	public static String organAppSecret=Constants.getProperty("organAppSecret"); 
	public static String staffAppID=Constants.getProperty("staffAppID"); 
	public static String staffAppSecret=Constants.getProperty("staffAppSecret"); 
	public static String userAppID=Constants.getProperty("userAppID"); 
	public static String userAppSecret=Constants.getProperty("userAppSecret"); 
	
	//商户信息
	public static String MCH_ID=Constants.getProperty("MCH_ID");  
	public static String MCH_ID_STAFF=Constants.getProperty("MCH_ID_STAFF");
	public static String MCH_ID_ORGAN=Constants.getProperty("MCH_ID_ORGAN");
	public static String API_KEY=Constants.getProperty("API_KEY");    //商户号API安全的API密钥中
	public static String CASH_API_KEY=Constants.getProperty("CASH_API_KEY"); 
	
	//异步回调地址
	public static String NOTIFY_URL=Constants.getProperty("NOTIFY_URL"); 
	public static String UNIFIED_ORDER_URL=Constants.getProperty("UNIFIED_ORDER_URL"); 
    public static String DOMAIN_URL=Constants.getProperty("DOMAIN_URL"); 
	//消息模版
	public static String ORDERED_ORGAN_SUCCESS=Constants.getProperty("ORDERED_ORGAN_SUCCESS");   //店铺点单支付成功
	public static String USER_APPOINT_SUCCESS=Constants.getProperty("USER_APPOINT_SUCCESS");  //用户预约待确认通知
	public static String PAY_SUCCESS=Constants.getProperty("PAY_SUCCESS");  //用户预约店铺成功后给用户通知
	public static String ORDERED_ORGAN_REMIND=Constants.getProperty("ORDERED_ORGAN_REMIND");  //用户预约店铺成功后给店铺提醒
	public static String APPOINT_STAFF_REMIND=Constants.getProperty("APPOINT_STAFF_REMIND"); 
	public static String STAFF_REFUSE_ORDER=Constants.getProperty("STAFF_REFUSE_ORDER"); //技师拒绝订单
	public static String ORDER_CANCEL_USER=Constants.getProperty("ORDER_CANCEL_USER"); //客户取消订单-通知客户
	public static String ORDER_CANCEL_STAFF=Constants.getProperty("ORDER_CANCEL_STAFF"); //客户取消订单-通知技师
	public static String ORDER_CANCEL_ORGAN=Constants.getProperty("ORDER_CANCEL_ORGAN"); //客户取消订单-通知店铺
	public static String WITHDRAW_USER=Constants.getProperty("WITHDRAW_USER"); //提现成功通知客户
	public static String WITHDRAW_STAFF=Constants.getProperty("WITHDRAW_STAFF"); //提现成功通知技师
	//技师端消息模板
	public static String ORDER_ORGAN_SUCCESS=Constants.getProperty("ORDER_ORGAN_SUCCESS"); //支付成功发送给店铺
	public static String ORDER_STAFF_SUCCESS=Constants.getProperty("ORDER_STAFF_SUCCESS"); //支付成功之后发送给技师
	public static String PAY_USER_REMIND=Constants.getProperty("PAY_USER_REMIND"); //支付成功后给用户
	public static String DONATION_INFO=Constants.getProperty("DONATION_INFO"); //转账成功之后给转账方的消息
	public static String PAYONLINE_USER_INFO=Constants.getProperty("PAYONLINE_USER_INFO"); 	//充值成功之后给用户发送消息
	public static String PAYONLINE_ORGAN_INFO=Constants.getProperty("PAYONLINE_ORGAN_INFO");   //充值成功之后给店铺发送消息
	public static String PRIZES_INFO=Constants.getProperty("PRIZES_INFO");  //用户排行中奖后推送消息
	public static String STAFF_PRIZES_INFO=Constants.getProperty("STAFF_PRIZES_INFO"); //技师排行中奖之后推送消息
	public final static String SIGN_TYPE = "MD5";//签名加密方式
}
