package com.mrmf.service.waimai;

import java.util.List;

import com.mrmf.entity.kucun.WWaimai;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface WaimaiService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 翻页查询外卖
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WWaimai> query(FlipInfo<WWaimai> fpi) throws BaseException;

	/**
	 * 根据开单id(会员卡外卖消费时，为会员卡号)查询未结账外卖列表
	 * 
	 * @param kaidanId
	 * @return
	 * @throws BaseException
	 */
	public List<WWaimai> query(String kaidanId) throws BaseException;

	/**
	 * 根据开单id(会员卡外卖消费时，为会员卡号)查询全部外卖列表，不管是否结账
	 * 
	 * @param kaidanId
	 * @return
	 * @throws BaseException
	 */
	public List<WWaimai> queryAll(String kaidanId) throws BaseException;

	/**
	 * 获取外卖物品详情信息
	 * 
	 * @param dataList
	 * @return
	 */
	public List<WWaimai> getDetails(List<WWaimai> dataList);

	/**
	 * 新增/修改外卖信息
	 * 
	 * @param waimai
	 * @return
	 */
	public ReturnStatus upsert(WWaimai waimai);

	/**
	 * 删除外卖信息
	 * 
	 * @param id
	 * @return
	 */
	public ReturnStatus remove(String id);

	/**
	 * 查询未处理订购列表
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<WWaimai> queryDinggou(String organId) throws BaseException;

	/**
	 * 处理订购信息
	 * 
	 * @param waimaiId
	 * @return
	 */
	public ReturnStatus handleDingou(String waimaiId);

	/**
	 * 根据id列表查询外卖信息
	 * 
	 * @param xiaopiao
	 * @param ids
	 * @return
	 * @throws BaseException
	 */
	public List<WWaimai> queryByIds(String xiaopiao, List<String> ids) throws BaseException;
}
