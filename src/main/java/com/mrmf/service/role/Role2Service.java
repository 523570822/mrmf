package com.mrmf.service.role;

import java.util.List;

import com.mrmf.entity.Role2;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface Role2Service {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询角色列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Role2> query(FlipInfo<Role2> fpi) throws BaseException;

	/**
	 * 查询角色基本信息
	 * 
	 * @param roleId
	 * @return
	 * @throws BaseException
	 */
	public Role2 queryById(String roleId) throws BaseException;

	/**
	 * 新增/修改角色信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Role2 role);

	/**
	 * 查询全部平台角色列表
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<Role2> queryAll() throws BaseException;

}
