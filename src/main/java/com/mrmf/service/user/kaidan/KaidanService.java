package com.mrmf.service.user.kaidan;

import java.util.List;

import com.mrmf.entity.user.Kaidan;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface KaidanService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据公司id查询开单列表
	 * 
	 * @param organId
	 * @param kaidan_id
	 * @return
	 * @throws BaseException
	 */
	public List<Kaidan> query(String organId, String kaidan_id) throws BaseException;

	/**
	 * 新增/修改开单信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Kaidan kaidan);

	/**
	 * 删除开单信息
	 * 
	 * @param organId
	 * @param kaidanId
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus remove(String organId, String id);

	/**
	 * 开单合并
	 * 
	 * @param organId
	 * @param ids
	 * @return
	 */
	public ReturnStatus union(String organId, String[] ids);

	/**
	 * 根据id查询开单信息
	 * 
	 * @param kaidanId
	 * @return
	 * @throws BaseException
	 */
	public Kaidan queryById(String kaidanId) throws BaseException;

}
