package com.osg.framework.web.httpclient.exception;

/**
 * Http client工具类异常
 * 
 * @author xiangf
 * 
 */
public class MHttpClientException extends Exception {

	private static final long serialVersionUID = 2572713290542517998L;
	private int httpStatus;

	public MHttpClientException() {
	}

	public MHttpClientException(String arg0) {
		super(arg0);
	}

	public MHttpClientException(Throwable arg0) {
		super(arg0);
	}

	public MHttpClientException(String msg, int httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}

	public MHttpClientException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
}
