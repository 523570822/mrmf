package com.mrmf.service.common;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.mrmf.entity.wxpay.MD5Util;
import com.mrmf.service.common.Configure;

public class WXPayReqData {

	private String mch_appid;
	private String mchid;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String partner_trade_no;
	private String openid;
	private String check_name;
	private String re_user_name;
	private String amount;
	private String desc;
	private String spbill_create_ip;

	public WXPayReqData() {
		this.mch_appid = Configure.userAppID;
		this.mchid = Configure.MCH_ID;
		this.check_name = "NO_CHECK";
	}
	public WXPayReqData(int n) {
		this.mch_appid = Configure.staffAppID;
		this.mchid = Configure.MCH_ID_STAFF;
		this.check_name = "NO_CHECK";
	}
	public WXPayReqData(String s){
		this.mch_appid = Configure.organAppID;
		this.mchid = Configure.MCH_ID_ORGAN;
		this.check_name = "NO_CHECK";
	}
	public String getMch_appid() {
		return mch_appid;
	}

	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCheck_name() {
		return check_name;
	}

	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}

	public String getRe_user_name() {
		return re_user_name;
	}

	public void setRe_user_name(String re_user_name) {
		this.re_user_name = re_user_name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	
	/**
	 * 生成企业付款请求
	 * @param partner_trade_no	订单号
	 * @param openid	收款用户openid
	 * @param amount	付款金额，(分)
	 * @param desc	付款描述
	 * @param spbill_create_ip	调用接口的机器Ip地址
	 * @return
	 */
	public static WXPayReqData buildWXPayReqDataForMchPay(String partner_trade_no,String openid,int amount,String desc,String spbill_create_ip){
		WXPayReqData reqData = new WXPayReqData();
		reqData.setPartner_trade_no(partner_trade_no);
		reqData.setOpenid(openid);
		reqData.setAmount(String.valueOf(amount));
		reqData.setDesc(desc);
		reqData.setSpbill_create_ip(spbill_create_ip);
		char[] RANDOM_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String nonce_str = RandomStringUtils.random(32,RANDOM_CHARS).toUpperCase();
		reqData.setNonce_str(nonce_str);
		String sign = reqData.signReqData(reqData.toMap());
		reqData.setSign(sign);
		return reqData;
	}
	public static WXPayReqData buildWXPayReqDataForMchPayStaff(String partner_trade_no,String openid,int amount,String desc,String spbill_create_ip){
		WXPayReqData reqData = new WXPayReqData(0);
		reqData.setPartner_trade_no(partner_trade_no);
		reqData.setOpenid(openid);
		reqData.setAmount(String.valueOf(amount));
		reqData.setDesc(desc);
		reqData.setSpbill_create_ip(spbill_create_ip);
		char[] RANDOM_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String nonce_str = RandomStringUtils.random(32,RANDOM_CHARS).toUpperCase();
		reqData.setNonce_str(nonce_str);
		String sign = reqData.signReqData(reqData.toMap());
		reqData.setSign(sign);
		return reqData;
	}
	public static WXPayReqData buildWXPayReqDataForMchPayOrgan(String partner_trade_no,String openid,int amount,String desc,String spbill_create_ip){
		WXPayReqData reqData = new WXPayReqData("s");
		reqData.setPartner_trade_no(partner_trade_no);
		reqData.setOpenid(openid);
		reqData.setAmount(String.valueOf(amount));
		reqData.setDesc(desc);
		reqData.setSpbill_create_ip(spbill_create_ip);
		char[] RANDOM_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String nonce_str = RandomStringUtils.random(32,RANDOM_CHARS).toUpperCase();
		reqData.setNonce_str(nonce_str);
		String sign = reqData.signReqData(reqData.toMap());
		reqData.setSign(sign);
		return reqData;
	}
	private Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	private String signReqData(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + Configure.API_KEY;
        System.out.println("result : " + result);
        result = MD5Util.MD5Encode(result, "utf-8").toUpperCase();
        return result;
    }
	

}
