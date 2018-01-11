package com.mrmf.service.wes;

import java.util.List;

import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBDistrict;
import com.mrmf.entity.WeBRegion;
import com.osg.entity.DataEntity;
import com.osg.framework.BaseException;

/**
 * 城市、区域、商圈相关代码表
 */
public interface WeSService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询城市信息
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<WeBCity> queryCity() throws BaseException;

	/**
	 * 查询区域信息
	 * 
	 * @return
	 */
	public List<WeBDistrict> queryDistrict(String cityId) throws BaseException;

	/**
	 * 查询全部区域
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<WeBDistrict> queryDistrictAll() throws BaseException;

	/**
	 * 查询商圈信息
	 * 
	 * @return
	 */
	public List<WeBRegion> queryRegion(String districtId) throws BaseException;

	/**
	 * 查询全部城市、区域、商圈，用于生成树结构
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<DataEntity> findAll() throws BaseException;

	/**
	 * 根据id查询城市
	 * 
	 * @param cityId
	 * @return
	 * @throws BaseException
	 */
	public WeBCity queryCityById(String cityId) throws BaseException;

	/**
	 * 根据id查询区域
	 * 
	 * @param districtId
	 * @return
	 * @throws BaseException
	 */
	public WeBDistrict queryDistrictById(String districtId) throws BaseException;

	/**
	 * 根据id查询商圈
	 * 
	 * @param regionId
	 * @return
	 * @throws BaseException
	 */
	public WeBRegion queryRegionById(String regionId) throws BaseException;

	/**
	 * 新增/修改城市信息
	 * 
	 * @param city
	 * @throws BaseException
	 */
	public void upsertCity(WeBCity city) throws BaseException;

	/**
	 * 新增/修改区域信息
	 * 
	 * @param district
	 * @throws BaseException
	 */
	public void upsertDistrict(WeBDistrict district) throws BaseException;

	/**
	 * 新增/修改商圈信息
	 * 
	 * @param region
	 * @throws BaseException
	 */
	public void upsertRegion(WeBRegion region) throws BaseException;
}
