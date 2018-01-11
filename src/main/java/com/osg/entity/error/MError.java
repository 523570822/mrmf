package com.osg.entity.error;

import java.io.Serializable;

/**
 * 错误信息
 */
public class MError implements Serializable{
	
	private static final long serialVersionUID = -8020756161884488099L;

	/**
	 * 错误代码
	 */
	private Integer errorCode;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;
	
	/**
	 * 内部异常ID（仅供debug使用�?	 */
	private String exceptionId;
	
	/**
	 * 错误堆栈信息（仅供debug使用�?	 */
	private String errorStack;

	public MError(){}
	public MError(MErrorCode meErrorCode){
		this.errorCode = meErrorCode.code();
		this.errorMessage = meErrorCode.desc();
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorStack() {
		return errorStack;
	}

	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
}
