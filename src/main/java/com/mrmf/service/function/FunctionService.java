package com.mrmf.service.function;

import java.util.List;

import com.mrmf.entity.Function;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface FunctionService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询全部菜单/资源列表，用于生成树形结构
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<Function> query() throws BaseException;

	/**
	 * 查询菜单/资源列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Function> query(FlipInfo<Function> fpi) throws BaseException;

	/**
	 * 查询菜单/资源基本信息
	 * 
	 * @param functionId
	 * @return
	 * @throws BaseException
	 */
	public Function queryById(String functionId) throws BaseException;

	/**
	 * 新增/修改菜单/资源信息
	 * 
	 * @param function
	 * @return
	 */
	public ReturnStatus upsert(Function function);

	/**
	 * 根据角色id列表获取菜单结构Funciton集合
	 * 
	 * @param roleIds
	 * @return
	 * @throws BaseException
	 */
	public List<Function> getFunctionMenu(List<String> roleIds) throws BaseException;

}
