package com.mrmf.service.loginLog;

import com.mrmf.entity.LoginLog;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface LoginLogService {

	/**
	 * 登陆日志查询
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<LoginLog> query(FlipInfo<LoginLog> fpi) throws BaseException;

	/**
	 * 插入登陆日志
	 * 
	 * @param loginLog
	 * @return
	 */
	public ReturnStatus upsert(LoginLog loginLog);

}
