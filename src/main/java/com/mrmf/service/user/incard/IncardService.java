package com.mrmf.service.user.incard;

import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

import javax.servlet.http.HttpServletRequest;

public interface IncardService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据id查询卡信息
	 * 
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public Userincard queryById(String id) throws BaseException;

	/**
	 * 会员卡消费结账
	 * 
	 * @param incardId
	 * @param xiaocishu
	 * @param xianjin
	 * @param huaka
	 * @param money_cash
	 * @param money_yinhang_money
	 * @param money3
	 * @return
	 */
	public ReturnStatus jiezhang(String incardId, int xiaocishu, double xianjin, double huaka, double money_cash,
			double money_yinhang_money, double money3, String passwd);

	/**
	 * 会员卡续费
	 * 
	 * @param userpart
	 * @return
	 */
	public ReturnStatus xufei(Userpart userpart);

	/**
	 * 删除会员卡续费
	 * 
	 * @param userpartId
	 *            续费消费id
	 * @param code
	 *            短信验证码
	 * @return
	 */
	public ReturnStatus xufeiRemove(String userpartId, String code);

	/**
	 * 发送卡相关敏感操作短信验证码给用户
	 * 
	 * @param incardId
	 * @return
	 */
	public ReturnStatus verifycode(String incardId);

	/**
	 * 发送卡相关敏感操作短信验证码给店铺
	 * 
	 * @param incardId
	 * @return
	 */
	public ReturnStatus verifycodeOrgan(String incardId);

	/**
	 * 修改会员卡卡号
	 * 
	 * @param incardId
	 * @param id_2
	 * @return
	 */
	public ReturnStatus changeId_2(String incardId, String id_2);

	/**
	 * 修改会员卡口令
	 * 
	 * @param incardId
	 * @param passwd
	 * @param code
	 *            短信验证码
	 * @return
	 */
	public ReturnStatus changePasswd(String incardId, String passwd, String code);

	/**
	 * 会员卡补缴欠费
	 * 
	 * @param userpart
	 * @return
	 */
	public ReturnStatus qianfei(Userpart userpart, String partId);

	/**
	 * 更改卡类型
	 * 
	 * @param incardId
	 * @param usersortId
	 * @return
	 */
	public ReturnStatus changeCardType(String incardId, String usersortId);

	/**
	 * 会员卡提现
	 * 
	 * @param incardId
	 * @param tixian
	 * @return
	 */
	public ReturnStatus tixian(String incardId, double tixian);

	/**
	 * 会员卡删除
	 * 
	 * @param incardId
	 * @param code
	 * @return
	 */
	public ReturnStatus remove(String incardId, String code);

	/**
	 * 根据公司id获取电子卡卡号
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public String getElecCardNum(String organId) throws BaseException;
}
