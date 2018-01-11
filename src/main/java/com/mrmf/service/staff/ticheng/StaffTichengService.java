package com.mrmf.service.staff.ticheng;

import java.util.List;

import com.mrmf.entity.staff.StaffTicheng;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface StaffTichengService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询提成列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<StaffTicheng> query(FlipInfo<StaffTicheng> fpi) throws BaseException;

	/**
	 * 查询提成信息
	 * 
	 * @param smallsortId
	 * @return
	 * @throws BaseException
	 */
	public StaffTicheng queryById(String staffTichengId) throws BaseException;

	/**
	 * 新增/修改提成信息
	 * 
	 * @param staffTicheng
	 * @return
	 */
	public ReturnStatus upsert(StaffTicheng staffTicheng);
	
	/**
	 * 批量新增/修改提成信息
	 * 
	 * @param smallsortIds
	 * @param staffTicheng
	 * @return
	 */
	public ReturnStatus upsertBatch(String[] smallsortIds,StaffTicheng staffTicheng);

	/**
	 * 根据公司id查询所有提成
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<StaffTicheng> findAll(String organId) throws BaseException;

}
