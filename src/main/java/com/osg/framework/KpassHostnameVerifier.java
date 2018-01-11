package com.osg.framework;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class KpassHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}
