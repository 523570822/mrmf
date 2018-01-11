package com.mrmf.service.message;

import javax.servlet.AsyncContext;

public class AkkaInitBean {

	private AsyncContext context;
	private String ip;

	public AkkaInitBean(AsyncContext context, String ip) {
		this.context = context;
		this.ip = ip;
	}

	public AsyncContext getContext() {
		return context;
	}

	public String getIp() {
		return ip;
	}

}
