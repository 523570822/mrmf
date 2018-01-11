package com.mrmf.service.user.usersort;

import java.util.List;

import com.mrmf.entity.user.Usersort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UsersortService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询会员类型列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Usersort> query(FlipInfo<Usersort> fpi) throws BaseException;

	/**
	 * 查询会员类型信息
	 * 
	 * @param usersortId
	 * @return
	 * @throws BaseException
	 */
	public Usersort queryById(String usersortId) throws BaseException;

	/**
	 * 新增/修改会员类型信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Usersort usersort);

	/**
	 * 根据公司id获取全部会员类型
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Usersort> findAll(String organId) throws BaseException;

}
