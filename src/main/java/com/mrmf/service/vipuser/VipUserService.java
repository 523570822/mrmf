package com.mrmf.service.vipuser;

import java.util.List;

import com.mrmf.entity.User;
import com.osg.entity.ReturnStatus;

/**
 * 对会员用户进行生日提醒的service
 * @author win7
 *
 */
public interface VipUserService {
	/**
	 * 查询将要过生日的会员用户
	 * @param day
	 * @param organId
	 * @return
	 */
	
public List<User> queryVipUser(Integer day,String organId);
/**
 * 给会员用户发送消息
 * @param users
 * @return
 */
public ReturnStatus sendMessage(List<User> users,String organId);
}
