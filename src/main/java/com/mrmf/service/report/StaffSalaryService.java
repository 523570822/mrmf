package com.mrmf.service.report;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.bean.StaffSalary;
import com.mrmf.entity.user.Userpart;
import com.osg.framework.BaseException;

public interface StaffSalaryService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 员工工资查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<StaffSalary> query(String organId, Date startTime, Date endTime) throws BaseException;

}
