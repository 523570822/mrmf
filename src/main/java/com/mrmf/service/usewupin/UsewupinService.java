package com.mrmf.service.usewupin;

import java.util.List;

import com.mrmf.entity.kucun.WUsewupin;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UsewupinService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据消费id查询物品列表
	 * 
	 * @param userpartId
	 * @return
	 * @throws BaseException
	 */
	public List<WUsewupin> query(String userpartId) throws BaseException;

	/**
	 * 新增/修改使用物品信息
	 * 
	 * @param usewupin
	 * @return
	 */
	public ReturnStatus upsert(WUsewupin usewupin);

	/**
	 * 删除使用物品信息
	 * 
	 * @param id
	 * @return
	 */
	public ReturnStatus remove(String id);
}
