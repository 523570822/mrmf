package com.osg.framework.web.http.util;

import java.lang.reflect.Type;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import com.osg.framework.util.JsonUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 请求参数获取器，设计用于从任何类型content-type的Request中获取指定请求参数
 * 
 * @author xiangf
 * @date 2014-04-14
 * @Copyright(c) Dajia shequ
 */
public class MHttpRequestParameterUtil {

	/**
	 * 获取字符串类型请求参数
	 * 
	 * @param name
	 *            参数名称
	 * @return 参数值
	 */
	@SuppressWarnings("unchecked")
	public static String getReqParamAsString(String name) {
		Map<String, Object> params = (Map<String, Object>) MAppContext
				.getReqParamMap();
		if (params != null) {
			Object v = params.get(name);
			if (v != null) {
				if (v instanceof JsonNode) {
					JsonNode jn = (JsonNode) v;
					return jn.asText();
				} else {
					return String.valueOf(v);
				}
			}
		}
		return null;
	}

	/**
	 * 获取请求参数
	 * 
	 * @param name
	 *            参数名
	 * @param type
	 *            参数类型
	 * @return 参数值
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object getJsonReqParam(String name, Type type)
			throws Exception {
		Map<String, Object> params = (Map<String, Object>) MAppContext
				.getReqParamMap();
		if (params != null) {
			Object rootNode = params.get(name);
			if (rootNode != null && rootNode instanceof JsonNode) {
				JsonNode rn = (JsonNode) rootNode;
				if (!rn.isMissingNode() && !rn.isNull()) {
					return JsonUtils.getObjectMapper().readValue(rn,
							JsonUtils.getObjectMapper().constructType(type));
				}
			} else {
				return rootNode;
			}
		}
		return null;
	}

}
