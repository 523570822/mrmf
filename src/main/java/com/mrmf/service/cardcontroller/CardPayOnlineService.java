package com.mrmf.service.cardcontroller;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.Organ;
import com.mrmf.entity.WeUserCardCharge;
import com.mrmf.entity.bean.CardPayOnlineSum;
import com.mrmf.entity.bean.OrganCardNum;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

public interface CardPayOnlineService {
	/**
	 * 获取会员卡充值记录表
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeUserCardCharge> queryUserCardCharge(FlipInfo<WeUserCardCharge> fpi);
	/**
	 * 根据名字模糊匹配店铺列表
	 * @param organName
	 * @return
	 */
	public List<Organ> queryOrganList(String organName);
	/**
	 * 对会员卡信息记录进行单条处理
	 * @param cardId
	 * @param state
	 * @return
	 */
	public ReturnStatus dealWithOnlyOne(String cardId,String state);
	/**
	 * 批量处理
	 * @param stateType
	 * @param state
	 * @param organName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ReturnStatus dealWithAll(String organId,String stateType,Integer state,String organName,Date startTime,Date endTime);
	/**
	 * 会员卡在线充值汇总
	 * @param organ 店铺端就是店铺ID平台端就是搜索条件店铺名称
	 * @param state 状态
	 * @param startTime
	 * @param endTime
	 * @param type 类型 店铺端1 平台端2
	 * @return
	 */
	public CardPayOnlineSum totalCardPayOnline(String organ,Integer state,Date startTime,Date endTime,String type);
	/**
	 * 根据城市 区域 商圈 名字 查询店铺
	 * @param city
	 * @param distirct
	 * @param region
	 * @param name
	 * @return
	 */
	public FlipInfo<Organ> queryOrgan(String city,String distirct,String region,String name,FlipInfo<Organ> fpi);
	/**
	 * 查询店铺会员卡情况
	 * @param organIds
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Organ> queryOrganCardNum(FlipInfo<Organ> fpi);
}
