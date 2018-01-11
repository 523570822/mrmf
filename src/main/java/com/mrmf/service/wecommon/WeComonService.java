package com.mrmf.service.wecommon;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.SensitiveWords;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.WeOrganComment;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeStaffCase;
import com.mrmf.entity.WeUserFeedback;
import com.mrmf.entity.WeekDay;
import com.mrmf.entity.organPisition.TimeCreate;
import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

/**
 * 微信共用接口
 * @author win7
 *
 */
public interface WeComonService {
	/**
	 * 判断微信账号是否已经关注过指定公众号
	 * @param oppenid
	 * @param unionid
	 * @param appid
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus isExist(String oppenid,String unionid,String appid) throws BaseException;
	/**
	 * 获取手机号短信验证码
	 * @param phone
	 * @param type 公众账号类型（店铺organ、技师staff、用户user）
	 * @return
	 */
	public ReturnStatus verifycode(String phone,String type);
	/**
	 * 短信验证码验证，验证通过后绑定微信号
	 * @param openId
	 * @param unionId
	 * @param phone
	 * @param code
	 * @param type 公众账号类型（店铺organ、技师staff、用户user）
	 * @param logo 微信图片
	 * @param nick 微信昵称
	 * @param gpsPoint  绑定用户的经纬度
	 * @return
	 */
	public ReturnStatus verify(String openId, String unionId, String phone, String code,String type,String logo,String	nick,GpsPoint gpsPoint);
	/**
	 * 获取日程设置表头
	 * @return
	 */
	public List<WeekDay> queryScheduleTitle();
	/**
	 * 获取店铺或者技师的评价列表
	 * @param type 1店铺2技师
	 * @param id
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WeOrganComment> queryWeCommentById(int type,String id,FlipInfo<WeOrganComment> fpi) throws BaseException;
	/**
	 * 获取店铺的技师列表信息
	 * @param organId
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Staff> queryStaffListByOrganId(String organId,FlipInfo<Staff> fpi);
	/**
	 * 查询订单列表
	 * @param type（1店铺2技师3用户）
	 * @param id
	 * @param status  如果查询全部就是一个空字符串如果完成状态就传递 "10"
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WeOrganOrder> queryWeOrganOrderListById(int type,String id,String status,FlipInfo<WeOrganOrder> fpi) throws BaseException;
	/**
	 * 查询订单详情
	 * @param weOrganOrderId
	 * @return
	 */
	public WeOrganOrder queryWeOrganOrderById(String weOrganOrderId);
	/**
	 * 根据Id获取案例详情
	 * @param weStaffCaseId
	 * @return
	 */
	public WeStaffCase queryWeStaffCaseById(String weStaffCaseId);
	/**
	 * 根据技师Id获取典型案例列表
	 * @param staffId
	 * @return
	 */
	public FlipInfo<WeStaffCase> queryWeStaffCaseListByStaffId(String staffId,FlipInfo<WeStaffCase> fpi);
	/**
	 * 保存用户反馈信息
	 * @param weUserFeedback
	 * @return
	 */
	public ReturnStatus saveWeUserFeedback(WeUserFeedback weUserFeedback);
	/**
	 * 用户收藏（店铺/技师/案例）
	 * @param id（案例/技师/店铺）等ID
	 * @param type 案例1 技师2 店铺3
	 * @param userId 
	 * @param state 收藏1 取消2
	 * @return
	 */
	public ReturnStatus saveUserFavor(String id,String type,String userId,String state);
	/**
	 * 根据类型获取类别列表
	 * @param type
	 * @return
	 */
	public List<Code> queryTypeList(String type);
	/**
	 * 保存店铺的预约订单信息
	 */
	public ReturnStatus saveWeOrganOrder(WeOrganOrder weOrganOrder);
	/**
	 *添加消息信息
	 * @param weMessage
	 * @return
	 */
	public ReturnStatus saveMessage(WeMessage weMessage);
	
	
	public ReturnStatus saveUser(Map<String,Object> userInfo);
	
	
	
	public ReturnStatus saveWeStaffCase(WeStaffCase weStaffCase);
	public ReturnStatus saveCode(Code code);
	public ReturnStatus saveCommmnet(WeOrganComment weOrganComment);
	
	public ReturnStatus saveUser(User user);
	
	/**
	 * 根据服务ID获取服务对象信息
	 * @param orderServiceId
	 * @return
	 */
	public Bigsort getBigsortById(String orderServiceId);
	/**
	 * 技师端申请加入店铺获取验证码
	 * @param phone
	 * @return
	 */
	public ReturnStatus verifycode(String phone);
	/**
	 * 技师申请加入店铺校验验证码
	 * @param openId
	 * @param unionId
	 * @param phone
	 * @param code
	 * @param logo
	 * @param nick
	 * @param sex
	 * @param gpsPoint
	 * @param organId
	 * @return
	 */
	public ReturnStatus verify(String openId, String unionId, String phone, String code,String logo,String	nick,String sex,GpsPoint gpsPoint,String organId,String organName);
	/**
	 * 搜索店铺列表
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Organ> searchOrgan(FlipInfo<Organ> fpi);
	
	
	/** 
	  * 通过图片url返回图片Bitmap 
	  * @param url 
	  * @return 
	  */  
	public InputStream returnBitMap(String path);
	
	/**
	 * 下载图片
	 * @param in
	 * @param request
	 * @return
	 */
	public String downImg(InputStream in,HttpServletRequest request);
	
	/**
	 * wgs84坐标转换成百度坐标
	 * @param x
	 * @param y
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> wgs84ToBaiDu(String x, String y) throws IOException;
	
	public WeOrganOrder queryWeOrganOrder(String orderId);
	/**
	 * 查询敏感词列表
	 * @return
	 */
	public List<SensitiveWords> querySensitiveWords();
	/**
	 * 判断是否包含敏感词
	 * @param comment
	 * @param words
	 * @return
	 */
	public ReturnStatus checkComment(String comment);
	
	public ReturnStatus save(SensitiveWords word);
	/**
	 * 根据城市获取城市的id
	 * @param city
	 */
	public WeBCity findCityIdByCity(String city);

	int getTime();
}
