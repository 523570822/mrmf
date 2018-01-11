package com.osg.framework.web.exception;

import com.osg.entity.error.MErrorCode;


/**
 * 系统类异常，此类异常不暴露给服务调用者
 * 
 * @author xiangf
 * 
 */
public class MSystemException extends MBaseException {

	private static final long serialVersionUID = 3715451616335837343L;

	public MSystemException() {
	}

	public MSystemException(String arg0) {
		super(arg0);
	}

	public MSystemException(Throwable arg0) {
		super(arg0);
	}

	public MSystemException(String arg0, Throwable arg1) {
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
	public MSystemException(Integer errorCode, String message, Throwable ex) {
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
	public MSystemException(Integer errorCode, String message) {
		super(errorCode, message);
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * @see MErrorCode
	 */
	public MSystemException(Integer errorCode) {
		super(errorCode);
	}
}
