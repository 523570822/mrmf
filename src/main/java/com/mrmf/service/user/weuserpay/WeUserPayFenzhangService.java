package com.mrmf.service.user.weuserpay;

import java.util.Date;

import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.bean.UserPayFenzhangSum;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface WeUserPayFenzhangService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据id查询分账信息
	 * 
	 * @param fenzhangId
	 * @return
	 * @throws BaseException
	 */
	public WeUserPayFenzhang queryById(String fenzhangId) throws BaseException;

	/**
	 * 查询微信支付分账列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WeUserPayFenzhang> query(FlipInfo<WeUserPayFenzhang> fpi) throws BaseException;

	/**
	 * 进入分账处理，生成Userpart消费记录信息
	 * 
	 * @param fenzhangId
	 *            分账id
	 * @return
	 * @throws BaseException
	 */
	public Userpart handleFenzhangEnter(String fenzhangId) throws BaseException;

	/**
	 * 分账处理提交
	 * 
	 * @param userpartId
	 * @return
	 */
	public ReturnStatus handleFenzhang(Userpart userpart);

	/**
	 * 获取分账店铺汇总金额
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public UserPayFenzhangSum totalOrgan(String organId, Integer state, Date startTime, Date endTime)
			throws BaseException;
}
