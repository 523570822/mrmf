package com.mrmf.service.report;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.user.Userpart;
import com.osg.framework.BaseException;

public interface ColligateService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 店面消费记录查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> query(String organId, Date startTime, Date endTime) throws BaseException;

}
