package com.mrmf.service.staff.staffpost;

import java.util.List;

import com.mrmf.entity.staff.Staffpost;
import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface StaffpostService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询岗位列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Staffpost> query(FlipInfo<Staffpost> fpi) throws BaseException;

	/**
	 * 查询岗位信息
	 * 
	 * @param smallsortId
	 * @return
	 * @throws BaseException
	 */
	public Staffpost queryById(String staffpostId) throws BaseException;

	/**
	 * 新增/修改岗位信息
	 * 
	 * @param staffpost
	 * @return
	 */
	public ReturnStatus upsert(Staffpost staffpost);
	
	/**
	 * 根据公司id查询所有岗位
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Staffpost> findAll(String organId) throws BaseException;

}
