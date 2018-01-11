package com.osg.framework.web.exception;

import com.osg.entity.error.MErrorCode;
import com.osg.framework.web.i18n.MessageUtil;

/**
 * 大家Mobile非法请求参数类异常（用于服务接口参数校验）
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 * 
 */
public class MIllegalParamException extends MBusinessException {

	private static final long serialVersionUID = -3163766069434267312L;

	public MIllegalParamException() {
		super(MErrorCode.e2001.code());
	}

	public MIllegalParamException(String arg0) {
		super(MErrorCode.e2001.code(), arg0);
	}

	public MIllegalParamException(Throwable arg0) {
		super(MErrorCode.e2001.code(), arg0);
	}

	public MIllegalParamException(String arg0, Throwable arg1) {
		super(MErrorCode.e2001.code(), arg0, arg1);
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是某个参数不被支持
	 * 
	 * @param paramName
	 *            非法参数名称
	 */
	public static MIllegalParamException getIllegalParamException(
			String paramName) {
		return new MIllegalParamException(MessageUtil.getMessageWithArg(
				"error.business.illegalParam", new Object[] { paramName }));
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是某个参数不被支持，附带导致的系统异常
	 * 
	 * @param paramName
	 *            非法参数名称
	 * 
	 * @param e
	 *            源系统异常
	 */
	public static MIllegalParamException getIllegalParamException(
			String paramName, Throwable e) {
		return new MIllegalParamException(MessageUtil.getMessageWithArg(
				"error.business.illegalParam", new Object[] { paramName }), e);
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是某个参数不被支持，附带具体描述信息
	 * 
	 * @param paramName
	 *            非法参数名称
	 * @param description
	 *            描述
	 */
	public static MIllegalParamException getIllegalParamException(
			String paramName, String description) {
		return new MIllegalParamException(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.description", new Object[] {
						paramName, description }));
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是某个参数不被支持，附带具体描述信息及导致的系统异常
	 * 
	 * @param paramName
	 *            非法参数名称
	 * @param description
	 *            描述
	 * 
	 * @param e
	 *            源系统异常
	 */
	public static MIllegalParamException getIllegalParamException(
			String paramName, String description, Throwable e) {
		return new MIllegalParamException(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.description", new Object[] {
						paramName, description }), e);
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是必要请求参数缺失
	 * 
	 * @param paramName
	 *            非法参数名称
	 * @return 非法请求参数类异常
	 */
	public static MIllegalParamException getParamMissedException(
			String paramName) {
		return MIllegalParamException.getIllegalParamException(paramName,
				MessageUtil.getMessage("error.business.illegalParam.missed"));
	}
	
	/**
	 * 构造一个MIllegalParamException异常，描述时间格式错误
	 * 
	 * @param paramName
	 *            非法参数名称
	 * @return 非法请求参数类异常
	 */
	public static MIllegalParamException getTimePatternException(
			String paramName) {
		return MIllegalParamException.getIllegalParamException(paramName,
				MessageUtil
						.getMessage("error.business.illegalParam.timePattern"));
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是不被支持的ContentType
	 * 
	 * @param contentType
	 *            错误的contentType
	 * @return 非法请求参数类异常
	 */
	public static MIllegalParamException getContentTypeException(
			String contentType) {
		return new MIllegalParamException(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.contentType",
				new Object[] { contentType }));
	}
	
	/**
	 * 构造一个MIllegalParamException异常，原因是不被支持的请求方法
	 * 
	 * @param method
	 *            错误的method
	 * @return 非法请求参数类异常
	 */
	public static MIllegalParamException getInvalidMethodException(
			String method) {
		return new MIllegalParamException(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.invalidMethod",
				new Object[] { method }));
	}

	/**
	 * 构造一个MIllegalParamException异常，原因是请求内容格式异常
	 * 
	 * @param e
	 *            格式解析导致的系统异常
	 * @return 非法请求参数类异常
	 */
	public static MIllegalParamException getIllegalFormatException(Throwable e) {
		return new MIllegalParamException(
				MessageUtil.getMessage("error.business.illegalParam.format"), e);
	}

}
