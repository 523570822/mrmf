package com.mrmf.service.staff.ticheng;

import java.util.List;

import com.mrmf.entity.staff.StaffFloatTicheng;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface StaffFloatTichengService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询浮动提成列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<StaffFloatTicheng> query(FlipInfo<StaffFloatTicheng> fpi) throws BaseException;

	/**
	 * 查询浮动提成信息
	 * 
	 * @param bigsortId
	 * @return
	 * @throws BaseException
	 */
	public StaffFloatTicheng queryById(String staffFloatTichengId) throws BaseException;

	/**
	 * 新增/修改浮动提成信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(StaffFloatTicheng staffFloatTicheng);

	/**
	 * 查询全部分段提成定义，按分段金额倒序排序后
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<StaffFloatTicheng> queryAllFenduan(String organId) throws BaseException;

	/**
	 * 查询全部最高提成定义，按分段金额倒序排序后
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<StaffFloatTicheng> queryAllZuigao(String organId) throws BaseException;

}
