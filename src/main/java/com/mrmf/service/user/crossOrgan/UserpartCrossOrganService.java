package com.mrmf.service.user.crossOrgan;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.bean.UserpartCrossOrganSum;
import com.mrmf.entity.user.UserpartCrossOrgan;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UserpartCrossOrganService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询跨公司消费金额情况列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<UserpartCrossOrgan> query(FlipInfo<UserpartCrossOrgan> fpi) throws BaseException;

	/**
	 * 导出跨公司消费金额情况列表
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public List<UserpartCrossOrgan> queryList(FlipInfo<UserpartCrossOrgan> fpi) throws BaseException;
	/**
	 * 获取跨公司消费汇总金额
	 * 
	 * @param status
	 * @param ownerStatus
	 * @param organId
	 * @param ownerOrganId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public UserpartCrossOrganSum totalHandle(Integer status, Integer ownerStatus, String organId, String ownerOrganId,
			Date startTime, Date endTime) throws BaseException;

	/**
	 * 跨公司消费处理
	 * 
	 * @param status
	 * @param ownerStatus
	 * @param organId
	 * @param ownerOrganId
	 * @param startTime
	 * @param endTime
	 * @param isOwner
	 * @return
	 */
	public ReturnStatus handle(Integer status, Integer ownerStatus, String organId, String ownerOrganId, Date startTime,
			Date endTime, boolean isOwner);

}
