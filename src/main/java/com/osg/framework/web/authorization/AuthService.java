package com.osg.framework.web.authorization;

/**
 * 权限服务，适用于大家社区OAuth权限体系
 * 
 * @author xiangf
 * @date 2014-04-14
 * @Copyright(c) Dajia shequ
 */
public interface AuthService {

	/**
	 * 获取授权信息
	 * 
	 * @param accessToken
	 *            oauth token
	 * @return 用户信息
	 * @throws Exception
	 */
	public MAuthInfo getAuthInfo(String accessToken) throws Exception;
	

	public void removeAuthInfo(String accessToken) throws Exception;
}
