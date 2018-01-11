package com.mrmf.service.user.bigsort;

import java.util.List;

import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface BigsortService {

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
	public FlipInfo<Bigsort> query(FlipInfo<Bigsort> fpi) throws BaseException;

	/**
	 * 查询服务大类基本信息
	 * 
	 * @param bigsortId
	 * @return
	 * @throws BaseException
	 */
	public Bigsort queryById(String bigsortId) throws BaseException;

	/**
	 * 新增/修改服务大类信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Bigsort bigsort);
	
	/**
	 * 查询公司所有服务大类列表
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Bigsort> findAll(String organId) throws BaseException;

	/**
	 * 根据店面id和服务名称得到大类对象
	 * @param organId
	 * @param title
	 * @return
	 * @throws BaseException
	 */
	public Bigsort findOne(String organId,String title) throws  BaseException;

}
