package com.mrmf.service.weorgan;


import java.util.List;
import java.util.Map;

import com.mrmf.entity.*;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface WeOrganService {
	/**
	 * 根据oppid获取店铺信息

	 * @return
	 * @throws BaseException
	 */
public Organ queryOrgan(String oppenid) throws BaseException;
/**
 * 根据店铺Id获取店铺信息
 * @param organId
 * @return
 * @throws BaseException
 */
public Organ queryOrganById(String organId) throws BaseException;
/**
 * 修改店铺信息
 * @param organ
 * @return
 * @throws BaseException
 */
public ReturnStatus updateOrgan(Organ organ) throws BaseException;
/**
 * 获取店铺日程
 * @param organId
 * @return
 * @throws BaseException
 */
public WeOrganCalendar queryWeOrganCalendarByOrganId(String organId,int day) throws BaseException;
/**
 * 修改店铺日程
 * @param organId
 * @param index
 * @param selected
 * @return
 */
public ReturnStatus updateWeOrganCalendarByOrganId(String organId,int day,int index,boolean selected);
/**
 * 根据用户Id查询用户信息
 * @param userId
 * @return
 * @throws BaseException
 */
public User queryUserById(String userId) throws BaseException;
///**
// * 用户首页获取店铺列表
// * @param type 店铺类型
// * @param city  城市
// * @param district 区域
// * @param region  商圈
// * @param longitude 经度
// * @param latitude 纬度
// * @param maxDistance 搜索半径
// * @param followCount  关注（排序条件如果不为空就按关注的倒叙排序）
// * @param fpi
// * @return
// */
//public FlipInfo<Organ> queryOragnListByUser(String type,String city,String district,String  region,double longitude, double latitude, double maxDistance, String followCount,FlipInfo<Organ> fpi);
/**
 * 店铺列表按距离排序
 * @param longitude
 * @param latitude
 * @param maxDistance
 * @param fpi
 * @return
 */
public FlipInfo<Organ> queryOrganListByUser(double longitude, double latitude, double maxDistance,
		FlipInfo<Organ> fpi);
/**
 * 查询店铺列表根据距离排序
 * @param fpi
 * @return
 */
public FlipInfo<Organ> queryOrganListByfollowCount(double longitude, double latitude,FlipInfo<Organ> fpi);
/**
 * 店铺消息列表

 * @param fpi
 * @return
 */
public FlipInfo<WeMessage> queryOrganMessageList(FlipInfo<WeMessage> fpi);
/**
 * 获取店铺客户数量
 * @param organId
 * @return
 */
public Map<String,Integer> queryfavorOrganUserCount(String organId);
/**
 * 查询店铺客户列表
 * @param type 查询类型 0预约客户 1会员客户 2关注客户
 * @param organid 店铺Id
 * @param fpi
 * @return
 */
public FlipInfo<User> queryOrganUserList(int type,String organid,FlipInfo<User> fpi);
/**
 * 获取城市商圈地址
 * @param cityId
 * @return
 */
public Map<String,List> queryDistrictList(String cityId,String districtId);
/**
 * 根据城市名称获取城市ID
 * @param cityName
 * @return
 */
public String queryCityId(String cityName);
/**
 * 验证短信验证码
 * @param phone
 * @param code
 * @return
 */
public ReturnStatus verify(String phone, String code,String organId);

public ReturnStatus savePwd(String pwd,String organId);
/**
 * 根据店铺Id获取店铺服务项目
 * @param organId
 * @return
 */
public List<Bigsort> queryOrganType(String organId);

/**
 * 获取当前店铺日收益列表（分页显示）
 * @param organId
 * @param fpi
 * @return
 */
public FlipInfo<Userpart> queryOrganEarnList(String organId,FlipInfo<Userpart> fpi);

public double queryOrganEarnSumMoney(String organId);

/**
 * 根据id存储地理位置
 * @param userId
 * @param type
 * @param longitude 
 * @param latitude 
 */
public void saveLocation(String userId, String type, String latitude, String longitude);
/**
 * 店铺收益信息
 * @param organId
 * @return
 */
public Map<String,String> organEarnInfo(String organId);
/**
 * 查询店铺价目表
 * @param fpi
 * @return
 */
public FlipInfo<Smallsort> queryOrganSmallsort(FlipInfo<Smallsort> fpi);
/**
 * 读取未读信息列表
 * @param organId
 * @return
 */
public List<WeMessage> existMessageNoRead(String organId);
/**
 * 读取消息
 * @param organId
 * @return
 */
    ReturnStatus readMessage(String organId,String type);

	/** 修改支付密码   **/
	void updatePwd(String organId,String pwd);


	/** 修改电话   **/
    void updatePhone(String organId,String phone);

	/**
	 * 用户端绑定手机号
	 * @param phone
	 * @param code
	 * @param organId
	 * @return
	 */
	 ReturnStatus organVerify(String phone,String code,String organId);
	/**
	 * 根据店铺的organId去查询钱包的列表
	 */

	FlipInfo<WeUserWalletHis> queryWallet(FlipInfo<WeUserWalletHis> weUserWalletHis, String organId);

    /**
     * 是否存在未完成的支付的情况
     * @param organId 店铺id
     * @param senderType // 表示类型
     * @param status  //想提现
     * @return  返回值
     */
    boolean queryWePayLog(String organId, int senderType, int status,int type);

    /**
     * 得到openid
     * @param organId 店铺id
     * @return 返回openid
     */
    Account findOneAccount(String organId);

    /**
     * 保存支付日志信息
     * @param wePayLog 支付日志
     */
   void saveWePayLog(WePayLog wePayLog);

    /**
     * 查询支付log  通过Id
     * @param id 支付id
     * @return 支付日志
     */
   WePayLog queryWePayLogById(String id);

    /**
     * 保存消费记录
     * @param weUserWalletHis 消费记录
     */
    void saveWeUserWalletHis(WeUserWalletHis weUserWalletHis);

    /**
     * 更新用户的余额    通过id
     * @param organId 店铺id
     */
    void updateUserWalletAmount(String organId,double amount);

    /**
     * 根据手机号获得用户信息
     * @param phone 手机号
     * @return organ
     */
    Organ queryOrganByPhone(String phone);

	/**
	 * 更新店铺的余额    通过id
	 * @param organId
	 */
     void updateOrganWalletAmount(String organId,double amount);
	/**
	 * 更新店铺的余额    通过id
	 * @param organId
	 */
    OrganPositionSetting getOrganPositionSetting(String organId);

	/**
	 * 得到organPositionSetting里店铺信息
	 * @param organList
	 * @return
	 */
	public FlipInfo<Organ> queryOrganPosition(FlipInfo<Organ> organList);

	/**
	 * 根据主店铺查询子店铺列表
	 * @param organList
	 * @param organId
	 * @return
	 */
	public FlipInfo<Organ> queryOrganByParentId(FlipInfo<Organ> fpi,String organId);
}