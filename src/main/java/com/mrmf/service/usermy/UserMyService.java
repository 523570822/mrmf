package com.mrmf.service.usermy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrmf.entity.Account;
import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.UserCollect;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WePayLog;
import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeRedRecord;
import com.mrmf.entity.WeStaffIncome;
import com.mrmf.entity.WeUserCompensate;
import com.mrmf.entity.WeUserFeedback;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.coupon.MyCoupon;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;

public interface UserMyService {
	/**用户端-我的消费记录
	 * @param flp */
	FlipInfo<WeOrganOrder> expenseRecord(String userId,String page,String size, FlipPageInfo<WeOrganOrder> flp) throws BaseException;
	/**用户端-我的收藏*/
	FlipInfo<UserCollect> mycollect(String userId,String type,String longitude,String latitude,String page,String size) throws BaseException;
	/**用户端-评价订单*/
	public ReturnStatus evaluateOrder(String userId,String orderId,String zanCount,String qiuCount,String faceScore,String comment) throws BaseException;
	/** ysd write  **/
	public User queryUserById(String userId);
	/** 修改头像   **/
	public void updateImg(String userId,String imgSrc);
	/** 修改昵称   **/
	public void updateNick(String userId,String nick);
	/** 修改电话   **/
	public void updatePhone(String userId,String phone);
	/**
	 * 用户端绑定手机号
	 * @param phone
	 * @param code
	 * @param userId
	 * @return
	 */
	public ReturnStatus userVerify(String phone,String code,String userId);
	/**
	 * 修改用户手机号
	 */
	public void userVerifyNoCode(String phone,String userId);
	/**
	 * 根据手机号获得用户信息
	 * @param phone
	 * @return
	 */
	public User queryUserByPhone(String phone);
	
	/** 修改邮箱   **/
	public void updateEmail(String userId,String email);
	/** 修改生日 **/
	public void updateBirthDay(String id, String birthday);
	
	/** 修改支付密码   **/
	public void updatePwd(String id,String pwd);
	/**
	 * 根据用户的userId去查询钱包的列表
	 */
	public FlipInfo<WeUserWalletHis> queryWallet(FlipInfo<WeUserWalletHis> weUserWalletHis,String userId);	
	/**
	 * 插入一条记录WeUserWalletHis  没有什么用啊
	 * @param weUserWalletHis
	 */
	public void insert(WeUserWalletHis weUserWalletHis);
	
	/**
	 * 我的订单列表
	 * @param userId
	 * @param type
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeOrganOrder> getOrders(String userId, String type,
			FlipPageInfo<WeOrganOrder> flp);
	/**
	 * 订单详情
	 * @param orderId
	 * @return
	 */
	public WeOrganOrder getOrderDetail(String orderId);
	
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	public ReturnStatus cancelOrder(String orderId);
	
	/**
	 * 我的消息列表
	 * @param userId
	 * @param type
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeMessage> myMessageList(String userId, String type);
	
	/**
	 * 我的赔付列表
	 * @param userId
	 * @param type 
	 * @param flp
	 * @return
	 */
	public FlipInfo<WeUserCompensate> compensateList(String userId,
			String type, FlipPageInfo<WeUserCompensate> flp);
	
	/**
	 * 申诉处理结果
	 * @param compensateId
	 * @return
	 */
	public WeUserCompensate getCompensate(String compensateId);
	/**
	 * 获取类型列表
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Code> selectType(FlipInfo<Code> fpi);
	/**
	 * 选择赔付者
	 * @param userId
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeOrganOrder> selectProvider(String userId,
			FlipInfo<WeOrganOrder> fpi);
	/**
	 * 获取订单
	 * @param orderId
	 * @return
	 */
	public WeOrganOrder getOrderById(String orderId);
	/**
	 * 获取配符类型
	 * @param codeId
	 * @return
	 */
	public Code getCodeById(String codeId);
	/**
	 * 保存赔付
	 * @param userId
	 * @param orderId
	 * @param codeId
	 * @param target
	 * @param desc
	 * @param logo
	 * @return
	 */
	public ReturnStatus saveCompensate(String userId, String orderId, String codeId,
			String target, String desc, List<String> logos);
	/**
	 * 保存评论
	 */
	public void saveFeedBack(WeUserFeedback weUserFeedback);
	/**
	 * 更新用户的余额    通过id
	 * @param userId
	 */
	public void updateUserWalletAmount(String userId,double amount);
	/**
	 * 保存消费记录
	 * @param userId
	 */
	public void saveDonationRecord(WeUserWalletHis weDonationRecord);
	/**
	 * 保存user对象
	 * @param user对象
	 */
	public void saveUser(User user);
	

	
	
	/**
	 * 获取店铺
	 * @param organId
	 * @return
	 */
	public Organ getOrganById(String organId);
	/**
	 * 查找我的赔付
	 * @param userId
	 * @param orderId
	 * @return
	 */
	public WeUserCompensate getMyCompensate(String userId, String orderId);
	/**
	 * 保存消费记录
	 * @param weUserWalletHis
	 */
	public void saveWeUserWalletHis(WeUserWalletHis weUserWalletHis);
	/**
	 * 得到openid 
	 * @param userId
	 * @return
	 */
	public Account findOneAccount(String userId);
	
	/**
	 * 扫码支付生成订单
	 * @param payOrderId
	 * @return
	 */
	public ReturnStatus scanSaveOrder(String payOrderId);
	/**
	 * 更新订单价格
	 * @param orderId
	 * @param price
	 */
	public void updateOrderPrice(String orderId, double price);
	
	/**
	 * 红包的记录
	 */
	public WeRedRecord findWeRedRecord(String redPackId);
	/**
	 * 获取红包下的所有记录
	 * @param redId
	 * @return
	 */
	public List<WeRedRecord> findWeRedRecords(String redId);
	/**
	 * 获取红包
	 * @param redId 红包的id
	 * @return
	 */
	public WeRed findRedPack(String redId);
	
	/**
	 */
	public ReturnStatus getWeRed(List<WeRed> weReds,String userId) throws BaseException;
	/**
	 * 获取所有的红包
	 */
	public  List<WeRed> findWeReds(double longitude,double latitude,String userId);
	
	/**
	 * 查询用户是否是会员
	 */
	public Boolean isMember(String userId);
	/**
	 * 
	 * @param userId
	 * @param staffId
	 * @return
	 */
	public Boolean isStaffServiceUser(String userId,String staffId);
	/**
	 * 判断是否领取过红包
	 */
	public Boolean isGetRedPacket(String weRedId,String userId);
	
	
	/**
	 * 用户填写邀请码
	 */
	public void updateUserInvitor(String invitor,String userId);
	
	/**
	 * 给技师返现3%
	 */
	public void moneyToStaff(String staffId,double money,String userId);
	/**
	 * 判断是否两个时间是否大于一年
	 * @param nowTime  大的时间
	 * @param oldTime  小的时间
	 * @return  大于1年返回 true 小于1年返回 false
	 */
	public Boolean greaterThanYear(Date nowTime,Date oldTime);
	
	/**
	 * 获取已经完成订单的列表
	 * @param get_id
	 * @param flp
	 * @return 
	 */
	public FlipInfo<WeOrganOrder> getFinishedOrders(String userId,
			FlipInfo<WeOrganOrder> flp);
	/**
	 * 根据地铺的id去查询店铺
	 * @param organId
	 * @return
	 */
	public Organ findOrganById(String organId);
	/**
	 * 用户消费记录给用户返的钱 记录下来

	 */
	public void saveStaffWalletHis(String staffId, double price, String desc,
			String type);
	/**
	 * 保存技师收益记录
	 */
	public void saveStaffIncome(WeStaffIncome weStaffIncome);
	/**
	 * 用户打赏技师
	 * @param staffId
	 * @param money
	 */
	public void userAwardStaff(String staffId,double money);
	/**
	 * 查询技师通过staffId
	 * @param staffId
	 * @return
	 */
	public Staff findStaffById(String staffId);
	/**
	 * 更新用户钱包余额
	 */
	public void updateUserWallet(String userId,double money);
	/**
	 * 检查是否存在该手机号
	 * @param phone
	 */
	public boolean isHaveUserPhone(String phone);
	/**
	 * 查询用户未读短信多少条
	 * @param userId
	 * @return
	 */
	public long findUserMessageCount(String userId);
	/**
	 * 更新系统消息为已读
	 * @param userId
	 */
	public void updateSysMessageRead(String userId);
	/**
	 * 更新消息为已读
	 * @param userId
	 */
	public void updateMessageRead(String userId);
	
	/**
	 * 保存支付日志信息
	 * @param wePayLog
	 */
	public void saveWePayLog(WePayLog wePayLog);
	/**
	 * 是否存在未完成的支付的情况
	 * @param userId
	 * @param senderType
	 * @param status
	 * @return
	 */
	public boolean queryWePayLog(String userId, int senderType, int status,int type);
	/**
	 * 查询支付log  通过Id
	 * @param id 
	 * @return
	 */
	public WePayLog queryWePayLogById(String id);

	/**
	 *
	 * @param userId
	 * @param type
	 * @param flp
	 * @return
	 */
	FlipInfo<MyCoupon> getCoupon(String userId, String type, FlipPageInfo<MyCoupon> flp);


	public void updateUser(String userId, Map<String,Object> userInfo);

}
