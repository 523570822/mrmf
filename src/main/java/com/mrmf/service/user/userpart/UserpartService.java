package com.mrmf.service.user.userpart;

import java.util.List;

import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UserpartService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 模糊查询，匹配手机号、姓名、cardno卡表面号和id_2卡号
	 * 
	 * @param organId
	 * @param condition
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryByCondition(String organId, String condition) throws BaseException;

	/**
	 * 根据id查询消费信息
	 * 
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public Userpart queryById(String id) throws BaseException;

	/**
	 * 根据fpi分页查询消费信息
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Userpart> queryByFpi(FlipInfo<Userpart> fpi) throws BaseException;

	/**
	 * 根据公司id查询消费记录
	 * 
	 * @param organId
	 * @param kaidanId
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> query(String organId, String kaidanId) throws BaseException;

	/**
	 * 新增/修改消费信息
	 * 
	 * @param role
	 * @return
	 */
	public ReturnStatus upsert(Userpart userpart);

	/**
	 * 删除消费信息
	 * 
	 * @param organId
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus remove(String organId, String id);

	/**
	 * 消费结账
	 * 
	 * @param organId
	 *            公司id
	 * @param kaidanId
	 *            开单id
	 * @param userpartIds
	 *            用户消费id数组
	 * @param money_yinhang_money
	 *            银行卡
	 * @param money_li_money
	 *            代金券
	 * @param money_lijuan
	 *            代金券号
	 * @param money_cash
	 *            现金
	 * @param money3
	 *            找零
	 * @return
	 */
	public ReturnStatus jiezhang(String organId, String[] userpartIds, String kaidanId, double money_yinhang_money,
			double money_li_money, String money_lijuan, double money_cash, double money3);

	/**
	 * 根据会员卡id和类型查询消费信息
	 * 
	 * @param incardId
	 * @param type
	 * @param all
	 *            是否全部记录（结账、未结账）
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryByIncardId(String incardId, int type, boolean all,boolean typeFlag) throws BaseException;

	/**
	 * 根据ids查询消费信息列表
	 * 
	 * @param ids
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryByIds(List<String> ids) throws BaseException;

	/**
	 * 获取指定公司的小票打印码（每日从1开始自增）
	 * 
	 * @param organId
	 * @param userpartIds
	 * @return
	 * @throws BaseException
	 */
	public String getXiaopiaoCode(String organId, List<String> userpartIds) throws BaseException;
	
	public List<Userpart> queryUserPartByFpi(FlipInfo<Userpart> fpi) throws BaseException;
	
	public List<Userpart> getDetails(List<Userpart> userparts);

	/**
	 * 通过会员卡id找到卡开卡记录
	 * @param inCardId
	 * @return
	 */
	public Userpart queryByIncardId(String inCardId);
}
