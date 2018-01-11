package com.mrmf.service.user;

import com.mrmf.entity.User;
import com.mrmf.entity.user.Bigsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface UserService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据id查询用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws BaseException
	 */
	public User queryById(String userId) throws BaseException;

	/**
	 * 查询服务大类列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Bigsort> query(FlipInfo<Bigsort> fpi) throws BaseException;

	/**
	 * 导入Excel会员数据
	 * 
	 * @param hospitalId
	 * @param fileId
	 * @return
	 */
	public ReturnStatus importUser(String organId, String fileId);

}
