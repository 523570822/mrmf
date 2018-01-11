package com.mrmf.service.report;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.bean.StaffTichengReport;
import com.osg.framework.BaseException;

public interface StaffTichengReportService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 员工提成时间段报查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<StaffTichengReport> query(String organId, Date startTime, Date endTime) throws BaseException;

}
