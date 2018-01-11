package com.mrmf.service.rank;

import java.util.Date;
import java.util.List;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBRegion;
import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.bean.StaffRank;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

public interface RankService {
	/**
	 * 查询用户排行
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeUserPayOrder> queryUserRank(FlipInfo<WeUserPayOrder> fpi);
	/**
	 * 根据微信订单号给用户发送消息
	 * @param orderId
	 * @return
	 */
	public ReturnStatus sendMessageToUser(String orderId,String activity,String prize);
	/**
	 * 查询技师排行
	 * @param fpi
	 * @return
	 */
	public FlipInfo<StaffRank> queryStaffRank(FlipInfo<StaffRank> fpi,Date start,Date end,String staffids,boolean flag);
	
	public List<StaffRank> queryStaffRankList(Date start,Date end,String staffids,boolean flag,int limit);
	/**
	 * 给技师充值
	 * @param staffId
	 * @param price
	 * @return
	 */
	public ReturnStatus rechargeToStaff(String staffId,double price);
	/**
	 * 根据用户名称和电话模糊查询用户信息列表
	 * @param condition
	 * @return
	 */
	public List<User> queryUser(String condition);
	/**
	 * 根据用户名称和电话模糊查询技师信息列表
	 * @param condition
	 * @return
	 */
	public List<Staff> queryStaff(String condition,String organIds,boolean flag);
	/**
	 * 给用户充值
	 * @param rorderId
	 * @param price
	 * @return
	 */
	public ReturnStatus rechargeToUser(String rorderId,double price);
	/**
	 * 获取城市列表
	 * @return
	 */
	public List<WeBCity> cityList();
	/**
	 * 根据城市 区域 商圈 获取店铺列表
	 * @param city
	 * @param distirct
	 * @param region
	 * @return
	 */
	public List<Organ> queryOrgan(String city,String distirct,String region);
	/**
	 * 
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @return
	 */

	public List<WeUserPayOrder> exportUserRank(String condition, Date startTime, Date endTime);

}
