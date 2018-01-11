package com.mrmf.service.userService;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeUserInquiry;
import com.mrmf.entity.WeUserInquiryQuote;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.Usersort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;

public interface UserService {

	/**
	 * 获取报价列表
	 * @param userId
	 * @param inquiryId 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeUserInquiryQuote> getEnquiryList(String userId,
			String inquiryId, FlipInfo<WeUserInquiryQuote> fpi);
	
	/**
	 * 保存询价
	 * @param userId
	 * @param latitude 
	 * @param longitude 
	 * @param status
	 * @param logo0
	 * @param logo1
	 * @param logo2
	 * @param desc 
	 * @param type 
	 * @return
	 */
	public ReturnStatus saveEnquiry(String userId, Double longitude, Double latitude,String logo0, String logo1, String logo2, String type, String desc);
	/**
	 * 删除询价
	 * @param userId
	 * @return
	 */
	public ReturnStatus deleteEnquiry(String userId);
	
	/**
	 * 获取询价详细信息
	 * @param quoteId
	 * @return
	 */
	public WeUserInquiryQuote getQuoteById(String quoteId);
	
	/**
	 * 获取类型
	 * @param codeId
	 * @return
	 */
	public Code getCodeById(String codeId);
	//询价预约包保存
	public ReturnStatus appointSave(String userId, String organId,
			String staffId, String quoteId, String orderTime, double price, int timeNum, int day, String replyId);

	//获取技师信息
	public Staff getStaffById(String staffId);

	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	public User getUserById(String userId);

	/**
	 * 获取会员卡列表
	 * @param userId
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Userincard> getVIPCard(String userId, FlipInfo<Userincard> fpi);

	/**
	 * 获取会员卡
	 * @param cardId
	 * @return
	 */
	public Userincard getCard(String cardId);

	/**
	 * 会员卡记录详情
	 * @param userId
	 * @param cardId 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Userincard> getcardInfo(String userId,
			String cardId, FlipPageInfo<Userincard> fpi);

	/**
	 * 会员消费记录
	 * @param userId
	 * @param cardId
	 * @param cardType 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Userpart> getCustomList(String userId, String cardId,
			String cardType, FlipPageInfo<Userpart> fpi);

	/**
	 * 会员消费记录
	 * @param userId
	 * @param cardId
	 * @param cardType
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Userpart> getRechargeList(String userId,
			String cardId,String cardType, FlipPageInfo<Userpart> fpi);

	/**
	 * 会员卡门店列表
	 * @param userId
	 * @param cardId
	 * @param latitude 
	 * @param longitude 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Organ> getCardStoreList(String cardId,String card,
			double longitude, double latitude, FlipPageInfo<Organ> fpi);

	/**
	 * 会员卡子卡列表
	 * @param userId
	 * @param cardId 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Userinincard> getVIPInCard(String userId,
			String cardId, FlipInfo<Userinincard> fpi);

	/**
	 * 获取会员卡主卡
	 * @param incardId
	 * @return
	 */
	public Userincard getInCard(String incardId);

	/**
	 * 获取会员卡子卡
	 * @param incardId
	 * @return
	 */
	public Userinincard getIninCard(String incardId);
	/**
	 * 子卡详情记录
	 * @param userId
	 * @param incardId
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Userinincard> getIncardInfo(String incardId);

	/**
	 * 获取技师日程状态
	 * @param staffId
	 * @return
	 */
	public ReturnStatus getStaffCalendar(String staffId);

	/**
	 * 获得我的询价
	 * @param quoteId
	 * @return
	 */
	public WeUserInquiry getMyInquiry(String quoteId);

	/**
	 * 根据用户获得询价
	 * @param userId
	 * @return
	 */
	public WeUserInquiry getInquiry(String userId); 
	/**
	 * 根据会员卡类型ID获取信息
	 * @param usersortId
	 * @return
	 */
	public Usersort getUsersort(String usersortId);
	/**
	 * 查询
	 * @param id_2
	 * @return
	 */
	public Userinincard findInInCard(String id_2,String userId);


}
