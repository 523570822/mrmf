package com.mrmf.service.role;

import java.util.List;

import com.mrmf.entity.Role;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface RoleService {

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
	public FlipInfo<Role> query(FlipInfo<Role> fpi) throws BaseException;

	/**
	 * 查询角色基本信息
	 * 
	 * @param roleId
	 * @return
	 * @throws BaseException
	 */
	public Role queryById(String roleId) throws BaseException;

	/**
	 * 新增/修改角色信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Role role);

	/**
	 * 根据公司id查询角色列表
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Role> queryByOrganId(String organId) throws BaseException;

}
