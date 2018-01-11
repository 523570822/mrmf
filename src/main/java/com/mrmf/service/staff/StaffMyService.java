package com.mrmf.service.staff;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.mrmf.entity.*;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;

public interface StaffMyService {
	/**订单评价列表*/
	public FlipInfo<WeOrganComment> getCommentList(String staffId,String page,String size);
	/**技师信息*/
	public Staff getStaff(String staffId);
	/**消息列表*/
	public FlipInfo<WeMessage> getMessageList(String staffId,String type,String page,String size);
	/**确认订单*/
	public void conformOrder(String orderId);
	/**拒绝订单*/
	public void refuseOrder(String orderId,String refusecomment);
	/**技师全部客户*/
	public Map getCustomerData(String staffId);
	/**技师客户列表*/
	public Map getCustomerList(String staffId,String type,String page,String size);
	/**客户详情列表*/
	public Map getCustomerDetail(String userId,String type,String page,String size);
	/**我的收益列表*/
	public Map getMyEarn(String staffId,String page,String size);




	Organ findOneOrgan(String organId);
	/**
	 * 获取关注客户列表
	 * @param staffId
	 * @param type
	 * @param fpi
	 * @return
	 */
	public FlipInfo<User> getCustomers(String staffId, String type,
			FlipInfo<User> fpi);
	/**
	 * 获取预约客户列表
	 * @param staffId
	 * @param type
	 * @param fpi
	 * @return
	 */
	public FlipInfo<User> getCustomersAppoint(String staffId, String type,
			FlipInfo<User> fpi);
	//客户统计
	public Map<String, Integer> getCustomerCount(String staffId);
	/**
	 * 会员客户信息
	 * @param userId
	 * @return
	 */
	public User getMemberDetail(String userId);
	
	/**
	 * 获取会员消费列表
	 * @param userId
	 * @param fip
	 * @return
	 */
	public FlipInfo<WeOrganOrder> customList(String userId,
			FlipPageInfo<WeOrganOrder> fip);
	/**
	 * 我的订单
	 * @param staffId
	 * @param type
	 * @param flp 
	 * @return
	 */
	public FlipInfo<WeOrganOrder> getOrders(String staffId, String type, FlipPageInfo<WeOrganOrder> flp);
	/**
	 * 获取订单详情
	 * @param orderId
	 * @return
	 */
	public WeOrganOrder getOrderDetail(String orderId);
	/**
	 * 拒绝订单保存
	 * @param orderId
	 * @param refuseComment
	 * @return
	 */
	public ReturnStatus refuseOrderSave(String orderId, String refuseComment);
	/**
	 * 获取我的评价
	 * @param staffId
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeOrganComment> getMyComment(String staffId,
			FlipPageInfo<WeOrganComment> flp);
	/**
	 * 获取我的信息
	 * @param staffId
	 * @param type
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeMessage> myMessageList(String staffId, String type,
			FlipPageInfo<WeMessage> flp);
	
	/**
	 * 获取收益列表
	 * @param staffId

	 * @return
	 */
	public FlipInfo<WeStaffIncome> getIncomeList(String staffId);
	/**
	 * 确认订单
	 * @param orderId
	 * @return
	 */
	public ReturnStatus confirmOrder(String orderId);
	
	/**
	 * 案例收藏
	 * @param userId
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeStaffCase> getCaseList(String userId,
			FlipPageInfo<WeStaffCase> flp);
	
	/**
	 * 技师收藏
	 * @param userId
	 * @param flp
	 * @return
	 */
	public FlipInfo<Staff> getStaffList(String userId, FlipPageInfo<Staff> flp);
	
	/**
	 * 店铺收藏
	 * @param userId
	 * @param flp
	 * @return
	 */
	public FlipInfo<Organ> getOrganList(String userId, FlipPageInfo<Organ> flp);
	/**
	 * 店铺收藏并且获取距离
	 * @param longitude
	 * @param latitude
	 * @param userId
	 * @param flp
	 * @return
	 */
	public FlipInfo<Organ> getOrganList(double longitude,double latitude,String userId, FlipPageInfo<Organ> flp);
	
	/**
	 * 保存反馈意见
	 * @param staffId
	 * @param fbcontent
	 *
	 * @return
	 */
	public ReturnStatus saveFeedBack(String staffId, String fbcontent,
			String contact);
	
	/**
	 * 获取红包发送次数
	 * @param staffId
	 * @return
	 */
	public Map<String,Object> getRedCount(String staffId);
	
	/**
	 * 发送红包
	 * @param staffId
	 *
	 * @param scope
	 *
	 * @param count
	 * @param money
	 * @param desc
	 * @param request 
	 * @return
	 */
	public ReturnStatus saveRedPacket(String staffId, String scope,
			/*String sendTime,*/ String count, String money, String desc, HttpServletRequest request);
	
	/**
	 * 获取已发红包列表
	 * @param staffId
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeRed> getRedPacketList(String staffId,
			FlipPageInfo<WeRed> flp);
	/**
	 * 获取红包详情
	 * @param redId
	 * @return
	 */
	public WeRed getRedPacketById(String redId);
	/**
	 * 获取已抢红包用户
	 * @param redId
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeRedRecord> getRedUserList(String redId,
			FlipPageInfo<WeRedRecord> flp);
	/**
	 * 获取我的店铺
	 * @param staffId
	 * @return
	 */
	public List<Organ> getMyOrgans(String staffId);
	/**
	 * 获取会员客户
	 *
	 * @param type
	 * @param fpi
	 * @return
	 */
	public FlipInfo<User> getCustomersMember(String staffId, String type,
			FlipInfo<User> fpi);
	
	public Account getAccountById(String userId, String type);
	/**
	 * 修改支付密码
	 * @param staffId
	 *
	 */
	public void updatePassword(String staffId, String pwd);
	/**
	 * 查找技师对应的Account 
	 * @param staffId
	 * @return
	 */
	public Account findOneAccount(String staffId);
	/**
	 * 根据技师ID和技师openid获取技师的Account
	 * @param staffId
	 * @param openid
	 * @return
	 */
	public Account findOneAccount(String staffId,String openid);
	/**
	 * 更新技师收入
	 * @param staffId
	 * @param totalIncome
	 */
	public void updateStaffIncome(String staffId, double totalIncome);
	/**
	 * 获取技师提成
	 *
	 * @return
	 */
	public FlipInfo<Userpart> getPercentageList(String staffId);
	/**
	 * 保存技师钱包记录
	 */
	public void saveStaffIncome(WeStaffIncome weStaffIncome);
	/**
	 * 判断有没有技师使用该手机号
	 * @param phone
	 * @return
	 */
	public boolean isHaveStaffPhone(String phone);

    /**
     * 初始化技师可服务时间
     */
    public void setWeStaffCalendar(String staffId);

	/**
	 *得到工位租赁表
	 * @param staffId
	 * @param fpi
	 * @return
	 */
    public  FlipInfo<PositionOrder> getStationList(String staffId, FlipInfo<PositionOrder> fpi);

	/**
	 * 工位租赁详情相关数值

	 * @param fpi
	 * @return
	 */
    public TreeMap<String, Integer> queryStationList(String positionOrderId , FlipInfo<OrganPositionDetails> fpi);

	/**
	 * 获取租赁店面的信息

	 * @return
	 */
	public PositionOrder getStationDetails(String positionOrderId);
	/**
	 * 查询技师有关的订单
	 */
	public FlipInfo<WeOrganOrder> queryStaffProfit(FlipInfo fpi,String staffId,String appDate);
	/**
	 * 根据订单List 查询支付表
	 */
	public List<WeUserPayOrder> queryPayOrder(List<String> organOrderIdList);
	/**
	 * 根据支付表List 查询分帐表
	 */
	public List<WeUserPayFenzhang> queryFenzhang(List<String> payOrderList);

	/**
	 * 查询技师 指定开始和结束时间的店铺订单(已完成)
	 */
	public List<WeOrganOrder> queryWeOrganOrderOfOneDay(String staffId,String  time);
}
