package com.osg.framework.web.exception;

import com.osg.entity.error.MErrorCode;
import com.osg.framework.web.i18n.MessageUtil;


/**
 * 大家Mobile远程服务调用类异常（仅用于大家社区接口调用）
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 * 
 */
public class MRPCException extends MSystemException {

	private static final long serialVersionUID = -6812059289285120025L;

	/**
	 * 异常构造方法
	 * @param service 远程服务名
	 * @param method 远程方法名
	 * @param e 原异常
	 */
	public MRPCException(String service, String method, String params, Throwable e) {

		super(MErrorCode.e3001.code(), MessageUtil.getMessageWithArg(
				"error.system.rpc", new Object[] { service, method, params }), e);

	}
}
