package com.osg.framework.web.exception;

import com.osg.entity.error.MErrorCode;

/**
 * 大家Mobile业务类异常，此类异常信息需暴露给用户或服务调用者
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 */
public class MBusinessException extends MBaseException {

	private static final long serialVersionUID = 1580931762782419015L;

	public MBusinessException() {
		super();
	}

	public MBusinessException(String arg0) {
		super(arg0);
	}

	public MBusinessException(Throwable arg0) {
		super(arg0);
	}

	public MBusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * @param message
	 *            异常信息
	 * @param ex
	 *            原异常
	 * @see MErrorCode
	 */
	public MBusinessException(Integer errorCode, String message, Throwable ex) {
		super(errorCode, message, ex);
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            错误代码，值域参考MErrorCode
	 * @param message
	 *            异常信息
	 * @see MErrorCode
	 */
	public MBusinessException(Integer errorCode, String message) {
		super(errorCode, message);
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * @see MErrorCode
	 */
	public MBusinessException(Integer errorCode) {
		super(errorCode);
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * 
	 * @param ex
	 *            源异常
	 * @see MErrorCode
	 */
	public MBusinessException(Integer errorCode, Throwable ex) {
		super(errorCode, ex);
	}
}
