package com.mrmf.service.loginLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrmf.entity.LoginLog;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<LoginLog> query(FlipInfo<LoginLog> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, LoginLog.class);
		return fpi;
	}

	@Override
	public ReturnStatus upsert(LoginLog loginLog) {
		loginLog.setIdIfNew();
		loginLog.setCreateTimeIfNew();
		mongoTemplate.save(loginLog);
		return new ReturnStatus(true);
	}
}
