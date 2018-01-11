package com.mrmf.service.usercard;

import java.util.List;

import com.mrmf.entity.Organ;
import com.mrmf.entity.WeSysCardChargeHis;
import com.mrmf.entity.user.Usercard;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UsercardService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询公司信息（带平台卡信息）
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Usercard> sysQuerySysCard(FlipInfo<Usercard> fpi) throws BaseException;

	/**
	 * 查询平台店铺充值信息
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public Usercard sysQuery(String organId) throws BaseException;

	/**
	 * 新增/修改平台店铺充值信息
	 * 
	 * @param _id
	 * @param charge
	 * @return
	 */
	public ReturnStatus sysCharge(String _id, double charge);

	/**
	 * 查询店铺平台卡充值历史记录
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WeSysCardChargeHis> sysQueryChargeHis(FlipInfo<WeSysCardChargeHis> fpi) throws BaseException;
	/**
	 * 根据店铺城市、区域、商圈、名字查询店铺列表
	 * @param city
	 * @param district
	 * @param region
	 * @param name
	 * @return
	 */
	public List<Organ> queryOrganList(String city,String district,String region,String organName,String organAbname);

}
