package com.mrmf.service.user.inincard;

import java.util.List;

import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface InincardService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据id查询子卡信息
	 * 
	 * @param inicardId
	 * @return
	 * @throws BaseException
	 */
	public Userinincard queryById(String inicardId) throws BaseException;

	/**
	 * 新建子卡信息
	 * 
	 * @param userpart
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus insert(Userpart userpart);

	/**
	 * 删除子卡信息
	 * 
	 * @param id
	 * @param danciTui
	 * @return
	 */
	public ReturnStatus remove(String id, double danciTui);

	/**
	 * 根据会员卡id查询关联的子卡信息列表
	 * 
	 * @param incardId
	 * @return
	 * @throws BaseException
	 */
	public List<Userinincard> query(String incardId) throws BaseException;
	
	/**
	 * 根据fpi查询子卡信息列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Userinincard> queryByFpi(FlipInfo<Userinincard> fpi) throws BaseException;

	/**
	 * 新增子卡消费信息
	 * 
	 * @param userpart
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus insertXiaofei(Userpart userpart);

	/**
	 * 删除子卡消费信息
	 * 
	 * @param id
	 * @return
	 */
	public ReturnStatus removeXiaofei(String id);

	/**
	 * 会员卡消费结账
	 * 
	 * @param userpartId
	 * @param xiaocishu
	 * @param xianjin
	 * @param money_cash
	 * @param money3
	 * @param passwd
	 * @return
	 */
	public ReturnStatus jiezhang(String inincardId, int xiaocishu, double xianjin, double money_cash, double money3,
			String passwd);

	/**
	 * 查询未结账的子卡消费信息列表
	 * 
	 * @param inincardId
	 * @return
	 * @throws BaseException
	 */
	public List<Userpart> queryUserpart(String inincardId) throws BaseException;
	
	public List<Userinincard> queryInCard(FlipInfo<Userinincard> fpi) throws BaseException;
}
