package com.osg.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.osg.entity.ReturnStatus;
import com.osg.framework.Constants;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

/**
 * 短信发送接口（阿里大鱼）
 */
public class SMSUtil {

	public static ReturnStatus sendVoice(String phone, String templateId, Map<String, String> params) {
		String url = Constants.getProperty("sms.url");
		String appkey = Constants.getProperty("sms.appkey");
		String secret = Constants.getProperty("sms.secret");
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
		// req.setExtend("12345");
		if (params != null && params.size() > 0)
			req.setTtsParamString(JsonUtils.toJson(params));
		req.setCalledNum(phone);
		req.setCalledShowNum("01053912804");
		req.setTtsCode(templateId); // TTS_34455207

		AlibabaAliqinFcTtsNumSinglecallResponse rsp;
		ReturnStatus status;
		try {
			rsp = client.execute(req);
			if (rsp.getErrorCode() == null) {
				status = new ReturnStatus(true);
			} else {
				status = new ReturnStatus(false, rsp.getSubMsg());
			}
		} catch (ApiException e) {
			status = new ReturnStatus(false, e.getErrMsg());
		}
		return status;
	}

	public static ReturnStatus send(final String phone, String templateId, Map<String, String> params) {
		List<String> phones = new ArrayList<>();
		phones.add(phone);
		return send(phones, templateId, params);
	}

	public static ReturnStatus send(final String phone, String templateId, Map<String, String> params,
			String singName) {
		List<String> phones = new ArrayList<>();
		phones.add(phone);
		return send(phones, templateId, params, singName);
	}

	public static ReturnStatus send(List<String> phones, String templateId, Map<String, String> params) {
		String url = Constants.getProperty("sms.url");
		String appkey = Constants.getProperty("sms.appkey");
		String secret = Constants.getProperty("sms.secret");
		String sign = Constants.getProperty("sms.signName");
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		// req.setExtend("123456哈哈定制信息");
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		if (params != null && params.size() > 0)
			req.setSmsParamString(JsonUtils.toJson(params));
		StringBuilder sb = new StringBuilder();
		for (String phone : phones) {
			sb.append(phone).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		req.setRecNum(sb.toString());
		req.setSmsTemplateCode(templateId);
		AlibabaAliqinFcSmsNumSendResponse rsp;

		ReturnStatus status;
		try {
			rsp = client.execute(req);
			if (rsp.getErrorCode() == null) {
				status = new ReturnStatus(true);
			} else {
				status = new ReturnStatus(false, rsp.getSubMsg());
			}
		} catch (ApiException e) {
			status = new ReturnStatus(false, e.getErrMsg());
		}
		return status;
	}

	public static ReturnStatus send(List<String> phones, String templateId, Map<String, String> params,
			String singName) {
		String url = Constants.getProperty("sms.url");
		String appkey = Constants.getProperty("sms.appkey");
		String secret = Constants.getProperty("sms.secret");
		String sign = Constants.getProperty(singName);
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		// req.setExtend("123456哈哈定制信息");
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		if (params != null && params.size() > 0)
			req.setSmsParamString(JsonUtils.toJson(params));
		StringBuilder sb = new StringBuilder();
		for (String phone : phones) {
			sb.append(phone).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		req.setRecNum(sb.toString());
		req.setSmsTemplateCode(templateId);
		AlibabaAliqinFcSmsNumSendResponse rsp;

		ReturnStatus status;
		try {
			rsp = client.execute(req);
			if (rsp.getErrorCode() == null) {
				status = new ReturnStatus(true);
			} else {
				status = new ReturnStatus(false, rsp.getSubMsg());
			}
		} catch (ApiException e) {
			status = new ReturnStatus(false, e.getErrMsg());
		}
		return status;
	}
}
