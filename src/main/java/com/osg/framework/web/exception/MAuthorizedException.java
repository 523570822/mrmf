package com.osg.framework.web.exception;

import com.osg.entity.error.MErrorCode;

/**
 * 大家Mobile授权类异常
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 */
public class MAuthorizedException extends MBaseException {

	private static final long serialVersionUID = -53608634906084515L;

	public MAuthorizedException() {
		super();
	}

	public MAuthorizedException(String arg0) {
		super(arg0);
	}

	public MAuthorizedException(Throwable arg0) {
		super(arg0);
	}

	public MAuthorizedException(String arg0, Throwable arg1) {
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
	 */
	public MAuthorizedException(Integer errorCode, String message, Throwable ex) {
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
	public MAuthorizedException(Integer errorCode, String message) {
		super(errorCode, message);
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * @see MErrorCode
	 */
	public MAuthorizedException(Integer errorCode) {
		super(errorCode);
	}
	
	public MAuthorizedException(MErrorCode arg0) {
		super(arg0);
	}

}
