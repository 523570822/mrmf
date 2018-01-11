package com.mrmf.service.user.smallsort;

import java.util.List;

import com.mrmf.entity.user.Smallsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface SmallsortService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询服务大类列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Smallsort> query(FlipInfo<Smallsort> fpi) throws BaseException;

	/**
	 * 查询服务大类基本信息
	 * 
	 * @param smallsortId
	 * @return
	 * @throws BaseException
	 */
	public Smallsort queryById(String smallsortId) throws BaseException;

	/**
	 * 新增/修改服务大类信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Smallsort smallsort);

	/**
	 * 根据公司id获取全部服务项目列表
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Smallsort> findAll(String organId) throws BaseException;

	/**
	 * 根据公司id获取全部启用的服务项目列表
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Smallsort> findAllValid(String organId) throws BaseException;

	/**
	 * 根据公司和服务项目id启用项目
	 * 
	 * @param organId
	 * @param id
	 * @return
	 */
	public ReturnStatus enable(String organId, String id);

	/**
	 * 根据公司和服务项目id禁用项目
	 * 
	 * @param organId
	 * @param id
	 * @return
	 */
	public ReturnStatus disable(String organId, String id);

}
