package com.mrmf.service.user.charge;

import java.util.List;

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface ChargeBackService {
	/**
	 * 非会员查询
	 * 
	 * @param xiaopiao
	 * @return
	 */
	public List<Userpart> queryUserPartUser(String xiaopiao,String organId);

	/**
	 * 会员消费
	 * 
	 * @param xiaopiao
	 * @return
	 */
	public List<Userpart> queryUserPartVipUser(String xiaopiao,String organId);

	/**
	 * 外卖
	 * 
	 * @param xiaopiao
	 * @return
	 */
	public List<WWaimai> queryWaimai(String xiaopiao,String organId);

	/**
	 * 子卡查询
	 * 
	 * @param xiaopiao
	 * @return
	 */
	public List<Userpart> queryInCard(String xiaopiao,String organId);

	/**
	 * 折扣卡
	 * 
	 * @param xiaopiao
	 * @return
	 */
	public List<Userpart> querySaleCard(String xiaopiao,String organId);

	/**
	 * 外卖退单
	 * 
	 * @param ids
	 * @return
	 */
	public ReturnStatus chargeWaiMai(String[] ids);

	/**
	 * 会员消费退单
	 * 
	 * @param ids
	 * @return
	 */
	public ReturnStatus chargeUserPart(String[] ids);

	/**
	 * 根据fpi分页查询退单消费信息
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Userpart> queryUserpart(FlipInfo<Userpart> fpi) throws BaseException;

	/**
	 * 根据fpi分页查询外卖消费信息
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WWaimai> queryWaimai(FlipInfo<WWaimai> fpi) throws BaseException;
}
