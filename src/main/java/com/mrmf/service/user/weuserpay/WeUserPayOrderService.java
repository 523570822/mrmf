package com.mrmf.service.user.weuserpay;

import com.mrmf.entity.WeUserPayOrder;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;

public interface WeUserPayOrderService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询微信订单列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WeUserPayOrder> query(FlipInfo<WeUserPayOrder> fpi) throws BaseException;

}
