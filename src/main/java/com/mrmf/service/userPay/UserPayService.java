package com.mrmf.service.userPay;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import com.mrmf.entity.WeSysConfig;
import com.mrmf.entity.User;
import com.mrmf.entity.Organ;
import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.bean.UserTixianSum;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Usersort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UserPayService {

    public ReturnStatus saveUserPayOrder(String userId, String organId, double price, HttpServletRequest request) throws BaseException;

    public ReturnStatus cancelUserPayOrder(String orderNo) throws BaseException;

    ReturnStatus cancelPositionOrder(String orderNo) throws BaseException;

    public ReturnStatus saveUserPayOrder(String userId, String organId, double price, String orderId, HttpServletRequest request) throws BaseException;

    /**
     * 用户会员卡在线充值
     *
     * @param userId
     * @param cardId
     * @param price
     * @param request
     * @return
     * @throws BaseException
     */
    public ReturnStatus saveUserpayOnline(String userId, String cardId, double price, HttpServletRequest request) throws BaseException;

    public Usercard sysQuery(String organId) throws BaseException;

    ReturnStatus payForStaffSuccess(Map<Object, Object> map) throws BaseException, ParseException;

    public ReturnStatus paySuccess(Map<Object, Object> map) throws BaseException;

    public ReturnStatus payOnlineSuccess(Map<Object, Object> map) throws BaseException;

    public SortedMap<Object, Object> getPrepay_id(HttpServletRequest request, WeUserPayOrder userPayOrder);

    public SortedMap<Object, Object> getPrepay_id(HttpServletRequest request, PositionOrder positionOrder);

    public WeUserPayOrder getWeUserPayOrder(String orderId);

    public Usersort getUsersortById(String usersortId);

    ReturnStatus saveUserBalancePayOrderToStaff(String userId, String organId, double price, String orderId, String staffId,String couponId, HttpServletRequest request) throws Exception;


    ReturnStatus staffPaySuccess(Map<Object, Object> map) throws BaseException;


    /**
     * 用户钱包支付
     *
     * @param userId
     * @param organId
     * @param price
     * @param orderId
     * @param request
     * @return
     * @throws BaseException
     */
    public ReturnStatus saveUserBalancePayOrder(String userId, String organId, double price, String orderId, String couponId, HttpServletRequest request) throws BaseException;


    /**
     * id 查订单数量
     *
     * @param userId userid
     * @return Integer
     * @throws BaseException exception
     */
    Long queryUserPayOrder(String userId) throws BaseException;

    /**
     * 获取返现金额
     *
     * @return Integer
     * @throws BaseException exception
     */
    WeSysConfig querySaoMAFan( ) throws BaseException;

    /**
     * 通过id查店铺信息
     *
     * @param organId
     * @return
     * @throws BaseException
     */
    Organ queryOrgan(String organId) throws BaseException;

    /**
     * 通过id查店铺信息
     *
     * @param userId
     * @return
     * @throws BaseException
     */
    User queryUser(String userId) throws BaseException;

    /**
     * 平台级别查询提现列表
     *
     * @param pp
     * @return
     * @throws BaseException
     */
    public FlipInfo<WeUserWalletHis> findTixian(FlipInfo<WeUserWalletHis> pp) throws BaseException;

    /**
     * 提现金额汇总
     *
     * @param type      提现类型，1:用户提现，2：技师提现
     * @param startTime
     * @param endTime
     * @return
     * @throws BaseException
     */
    public UserTixianSum tixianSum(String type, Date startTime, Date endTime) throws BaseException;

    public Usercard queryUserCardById(String cardId);

    /**
     * 红包微信支付
     *
     * @param staffId
     * @param hongbaoId
     * @param request
     * @return
     */
    public ReturnStatus saveHongBaoOrder(String staffId, String hongbaoId, HttpServletRequest request);

    /**
     * 红包微信支付成功回调
     *
     * @param map
     * @return
     */
    public ReturnStatus payHongbaoSuccess(Map<Object, Object> map);

}
