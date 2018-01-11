package com.osg.framework.web.authorization;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.osg.entity.error.MErrorCode;
import com.osg.framework.web.exception.MAuthorizedException;
import com.osg.framework.web.exception.MSystemException;
import com.osg.framework.web.httpclient.MHttpClient;
import com.osg.framework.web.httpclient.exception.MHttpClientException;

/**
 * 权限服务实现类，适用于大家社区OAuth权限体系
 * 
 * @author xiangf
 * @date 2014-04-14 @Copyright(c) Dajia shequ
 */
public class AuthServiceImpl implements AuthService {

	@Autowired
	private MHttpClient authClient;
	@Autowired
	private MHttpClient authRemove;

	@Override
	public MAuthInfo getAuthInfo(String accessToken) throws Exception {
		MAuthInfo ma = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", accessToken);
		Map<?, ?> authInfo = null;
		try {
			authInfo = authClient.get(param, Map.class);
			Map<?, ?> person = null;
			person = (Map<?, ?>) authInfo.get("person");
			ma = new MAuthInfo();
			ma.setClientID((String) authInfo.get("clientID"));
			ma.setPersonID((String) person.get("_id"));
			ma.setEntityID((String) person.get("entityID"));
			// ma.setPersonName((String) person.get("name"));
			ma.setUserName((String) person.get("accountName"));
			ma.setToken(accessToken);
			ma.setAuthInfo(authInfo);
		} catch (MHttpClientException e) {
			// 授权失败
			if (e.getHttpStatus() == 401) {
				throw new MAuthorizedException(MErrorCode.e1003);
			} else {
				throw new MSystemException(e);
			}
		} catch (Exception e) {
			throw new MSystemException(e);
		}
		return ma;
	}

	@Override
	public void removeAuthInfo(String accessToken) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", accessToken);
		authRemove.get(param);
	}

	public MHttpClient getAuthClient() {
		return authClient;
	}

	public void setAuthClient(MHttpClient authClient) {
		this.authClient = authClient;
	}

	public MHttpClient getAuthRemove() {
		return authRemove;
	}

	public void setAuthRemove(MHttpClient authRemove) {
		this.authRemove = authRemove;
	}

}
