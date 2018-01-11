package com.mrmf.service.report;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userpart;
import com.osg.framework.BaseException;

public interface DailyIncomeService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 普通用户消费记录查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryUser(String organId, Date startTime, Date endTime) throws BaseException;

	/**
	 * 会员详细消费记录查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryUserCard(String organId, Date startTime, Date endTime) throws BaseException;
	
	/**
	 * 会员办卡记录查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryUserCardNew(String organId, Date startTime, Date endTime) throws BaseException;
	
	/**
	 * 会员续费记录查询
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryUserCardXufei(String organId, Date startTime, Date endTime) throws BaseException;
	public List<Userpart> queryUserCardZK(String organId, Date startTime, Date endTime) throws BaseException;
	/**
	 * 查询赠送记录
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryZengsong(String organId, Date startTime, Date endTime) throws BaseException;
	
	/**
	 * 查询外卖记录
	 * 
	 * @param organId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws BaseException
	 */
	public List<WWaimai> queryWaimai(String organId, Date startTime, Date endTime) throws BaseException;
}
