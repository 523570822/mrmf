package com.osg.framework.web.springmvc.interceptor;

import com.osg.framework.web.authorization.MAuthInfo;

/**
 * 权限拦截器扩展
 * 
 * @author xiangf
 * @date 2014-04-14
 * @Copyright(c) Dajia shequ
 */
public interface MAuthInterceptorExtention {

	/**
	 * 当新用户（session）出现并验证权限成功后调用此方法
	 * @param authInfo 授权信息
	 */
	public void postHandle(MAuthInfo authInfo) throws Exception;
}
