package com.mrmf.service.sys.SysUser;

import java.util.Date;
import java.util.List;

import com.mongodb.WriteResult;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface SysUserService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */
	/**
	 * 账号翻页查询接口
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<User> queryUser(FlipInfo<User> fpi) throws BaseException;


	void updatePositionOrder(String id,String check) throws BaseException;

	WriteResult opd(String id, String check) throws BaseException;

	/**
	 * 查询用户基本信息
	 *
	 * @param
	 * @return
	 * @throws BaseException
	 */
	public User queryByUserId(String userId) throws BaseException;
	
	/**
	 * 用户预约信息获取
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<WeOrganOrder> queryUserOrder(FlipInfo<WeOrganOrder> fpi, String userId) throws BaseException;


	/**
	 * 工位预约信息获取
	 *
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	FlipInfo<PositionOrder> queryPositionCheck(FlipInfo<PositionOrder> fpi, String organId) throws BaseException;


	/**
	 * 新增/修改账号信息
	 * 
	 * @param
	 * @return
	 */
	public void editUserStatus(String userIds, String status);
	/**
	 * 账号翻页查询接口
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Staff> queryStaff(FlipInfo<Staff> fpi) throws BaseException;
	
	/**
	 * 查询技师基本信息
	 * 
	 * @param
	 * @return
	 * @throws BaseException
	 */
	public Staff queryByStaffId(String staffId) throws BaseException;
	
	/**
	 * 查询技师加入店铺列表
	 * 
	 * @param
	 * @return
	 * @throws BaseException
	 */
	public List<Organ> queryOrganListByStaffId(String staffId) throws BaseException;
	
	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public void editStaffFaceScore(String staffId, Integer faceScore);
	
	/**
	 * 更改用户状态信息
	 * 
	 * @param
	 * @return
	 */
	public void editStaffStatus(String userIds, String status);
	/**
	 * 查询所有待审核技师
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Staff> queryStaffCheck(FlipInfo<Staff> fpi,String organId);
	/**
	 * 审核技师
	 * @param organId
	 * @param staffId
	 * @param checkResult
	 * @param memo
	 * @return
	 */
	public WriteResult updateStaffVerify(String organId, String staffId,
			String checkResult, String memo);
	/**
	 * 查询技师
	 * @param staffId
	 * @return
	 */
	public void findOneStaffAndAddOrganId(String staffId,String organId);
	/**
	 * 导出技师列表
	 * @param phone
	 * @param organ
	 * @param name
	 * @param sex
	 * @param startYear
	 * @param endYear
	 * @return
	 * @throws BaseException
	 */
	List<Staff> exportStaff(String phone,String organ,String name,String sex,Integer startYear,Integer endYear) throws BaseException;
	/**
	 * 导出注册用户
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return ModelAndView  
	 * @throws
	 * @author 
	 * @date 2016-12-28
	 */
	public List<User> exportUserManagement(String phone, Date startTime, Date endTime);

//	/**
//	 * 修改用户角色
//	 * @param userId
//	 * @param state
//	 * @return
//	 */
//	public ReturnStatus UpdateState(String userId, String internalStaff);

	/**
	 * 查询会员
	 * @param fpi
	 * @param organName
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Userpart> queryByFpi(FlipInfo<Userpart> fpi,String organName)throws BaseException;

	/**
	 *会员明细
	 * @param userparts
	 * @return
	 */
	public List<Userpart> getDetails(List<Userpart> userparts);

}
