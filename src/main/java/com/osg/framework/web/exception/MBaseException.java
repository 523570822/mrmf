package com.osg.framework.web.exception;

import com.osg.entity.error.MErrorCode;
import com.osg.framework.util.UUIDUtil;

/**
 * 大家Mobile异常基类
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 * 
 */
public class MBaseException extends RuntimeException {

	private static final long serialVersionUID = 2454404292180333772L;

	/**
	 * 异常ID（自动生成），用于追踪错误信息
	 */
	protected String exceptionId;

	/**
	 * 错误代码
	 * 
	 * @see MErrorCode
	 */
	protected Integer errorCode;

	public MBaseException() {
		super();
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(String arg0) {
		super(arg0);
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(Throwable arg0) {
		super(arg0);
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		exceptionId = UUIDUtil.getUUID();
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
	public MBaseException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(MErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode.code();
		exceptionId = UUIDUtil.getUUID();
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
	public MBaseException(Integer errorCode, String message, Throwable ex) {
		super(message, ex);
		this.errorCode = errorCode;
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(MErrorCode errorCode, String message, Throwable ex) {
		super(message, ex);
		this.errorCode = errorCode.code();
		exceptionId = UUIDUtil.getUUID();
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * @see MErrorCode
	 */
	public MBaseException(Integer errorCode) {
		super();
		this.errorCode = errorCode;
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(MErrorCode errorCode) {
		super(errorCode.desc());
		this.errorCode = errorCode.code();
		exceptionId = UUIDUtil.getUUID();
	}

	/**
	 * 异常构造方法
	 * 
	 * @param errorCode
	 *            异常类型，值域参考MErrorCode
	 * @see MErrorCode
	 */
	public MBaseException(Integer errorCode, Throwable ex) {
		super(ex);
		this.errorCode = errorCode;
		exceptionId = UUIDUtil.getUUID();
	}

	public MBaseException(MErrorCode errorCode, Throwable ex) {
		super(errorCode.desc(), ex);
		this.errorCode = errorCode.code();
		exceptionId = UUIDUtil.getUUID();
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
