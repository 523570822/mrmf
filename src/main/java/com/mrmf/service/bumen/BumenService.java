package com.mrmf.service.bumen;

import java.util.List;

import com.mrmf.entity.Bumen;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface BumenService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询部门列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Bumen> query(FlipInfo<Bumen> fpi) throws BaseException;

	/**
	 * 查询部门基本信息
	 * 
	 * @param bumenId
	 * @return
	 * @throws BaseException
	 */
	public Bumen queryById(String bumenId) throws BaseException;

	/**
	 * 新增/修改部门信息
	 * 
	 * @param bumen
	 * @return
	 */
	public ReturnStatus upsert(Bumen bumen);

	/**
	 * 根据公司id获取所有部门
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Bumen> findAll(String organId) throws BaseException;

	/**
	 * 删除部门信息
	 * 
	 * @param bumenId
	 * @return
	 */
	public ReturnStatus remove(String bumenId);
}
