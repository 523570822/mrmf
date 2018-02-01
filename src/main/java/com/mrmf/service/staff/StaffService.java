package com.mrmf.service.staff;

import com.mrmf.entity.*;
import com.mrmf.entity.relevance.UserRelevanceStaff;
import com.mrmf.entity.user.Smallsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StaffService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询员工列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Staff> query(FlipInfo<Staff> fpi) throws BaseException;

	/**
	 * 查询员工基本信息
	 * 
	 * @param staffId
	 * @return
	 * @throws BaseException
	 */
	public Staff queryById(String staffId) throws BaseException;

	/**
	 * 新增/修改员工信息
	 * 
	 * @param staff
	 * @return
	 */
	public ReturnStatus upsert(Staff staff);

	/**
	 * 根据公司id获取全部员工信息
	 * 
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Staff> findAll(String organId) throws BaseException;

	/**
	 * 技师签约店铺删除，解除技师与店铺的关联
	 * 
	 * @param organId
	 * @param staffId
	 * @return
	 */
	public ReturnStatus removeFromOrgan(String organId, String staffId);

	/**
	 * ------------------移动端调用相关接口--------------
	 */

	/**
	 * 获取技师手机号短信验证码
	 * 
	 * @param phone
	 * @return
	 */
	public ReturnStatus verifycode(String phone);

	/**
	 * 短信验证码验证，验证通过后绑定微信号到指定手机号技师
	 * 
	 * @param openId
	 * @param unionId
	 * @param phone
	 * @param code
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus verify(String openId, String unionId, String phone, String code);

	/**
	 * 技师搜素热度排序
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Staff> queryOrderByHot(FlipInfo<Staff> fpi) throws BaseException;

	/**
	 * 技师搜素价格排序
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Staff> queryOrderByPrice(FlipInfo<Staff> fpi) throws BaseException;

	/**
	 * 技师搜素距离排序
	 * 
	 * @param longitude
	 * @param latitude
	 * @param maxDistance
	 *            距离限制，单位公里，传入值小于等于0为不限制距离
	 * @param pp
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Staff> queryOrderByDistance(double longitude, double latitude, double maxDistance,
			FlipInfo<Staff> pp) throws BaseException;

	/**
	 * 通过技师id 查找技师所属店铺
	 * 
	 * @param staffId
	 * @return
	 */
	public List<Organ> getOrganList(String staffId);

	/**
	 * 通过openid 查询技师信息
	 * 
	 * @param openId
	 * @return
	 */
	public Staff getStaff(String openId);

	/**
	 * 根据商圈id获取店铺列表
	 * 
	 * @param cityId
	 * @param regionId
	 * @param districtId
	 * @param request
	 * @param staffId
	 * @return
	 */
	public FlipInfo<Organ> getOrgans(String cityId, String districtId, String regionId, HttpServletRequest request,
			String staffId);

	/**
	 * 根据店铺id获取店铺信息
	 * 
	 * @param organId
	 * @return
	 */
	public Organ getOrgan(String organId);

	/**
	 * 通过城市id 获取区域
	 * 
	 * @param cityId
	 * @return
	 */
	public List<WeBDistrict> getDistrict(String cityId);

	/**
	 * 通过区域id 获取商圈
	 * 
	 * @param districtId
	 * @return
	 */
	public List<WeBRegion> getRegion(String districtId);

	/**
	 * 根据id获取技师信息
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getById(String staffId);

	/**
	 * 根据店铺id获取加入技师列表
	 * 
	 * @param organId
	 * @return
	 */
	public List<Staff> getStaffList(String organId);

	/**
	 * 根据id，修改技师信息
	 * 
	 * @param staffId
	 * @param status(需修改字段名)
	 * @param val
	 * @return
	 */
	public void getAndSaveById(String staffId, String status, String val);

	/**
	 * 根据店铺，技师id修改技师加入与解约
	 * 
	 * @param staffId
	 * @param organId
	 * @param status
	 */
	public ReturnStatus isJoin(String staffId, String organId, String status);

	/**
	 * 根据技师案例查找典型案例
	 * 
	 * @param staffId
	 * @param request
	 * @return
	 */
	public FlipInfo<WeStaffCase> getStaffCases(String staffId, HttpServletRequest request);

	/**
	 * 添加案例
	 * 
	 * @param staffId
	 * @param type
	 * @param title
	 * @param desc
	 * @param price
	 * @param consumeTime
	 * @param list
	 *
	 */
	public void addExample(String staffId, String type, String realType, String title, String desc, String price, String consumeTime,
			List<String> list);

	/**
	 * 根据id 查询案例
	 * 
	 * @param exampleId
	 * @return
	 */
	public WeStaffCase getExample(String exampleId);

	/**
	 * 编辑保存案例
	 * @param staffCaseId
	 * @param title
	 * @param desc
	 * @param price
	 * @param consumeTime
	 * @return
	 */
	public ReturnStatus editAndSaveExam(String staffCaseId, String title,
			String desc, double price, int consumeTime);
	/**
	 * 删除案例
	 * 
	 * @param staffCaseId
	 * @param staffId
	 *
	 */
	public ReturnStatus deleteExample(String staffCaseId, String staffId);

	/**
	 * 查看询价列表
	 * 
	 * @param staffId
	 * @param fpi
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public FlipInfo<WeUserInquiry> askPrice(String staffId, FlipInfo<WeUserInquiry> fpi, double longitude,
			double latitude);

	/**
	 * 获取询价详情
	 * 
	 * @param inquiryId
	 * @return
	 */
	public WeUserInquiry askPriceById(String inquiryId);

	/**
	 * 保存报价
	 * 
	 * @param staffId
	 * @param inquiryId
	 * @param myPrice
	 * @param mypriceDesc
	 * @return 
	 */
	public ReturnStatus askPriceSave(String staffId, String inquiryId, Double myPrice, String mypriceDesc);

	/**
	 * 获取签到明细
	 * 
	 * @param staffId
	 * @param time
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeStaffSign> getSignStatistics(String staffId, String time, FlipInfo<WeStaffSign> fpi);

	/**
	 * 签到保存
	 * 
	 * @param staffId
	 * @param organId
	 * @param organName
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public ReturnStatus saveSign(String staffId, String organId, String organName, Double longitude, Double latitude);

	/**
	 * 根据经纬度获取附近店铺
	 * @param staffId
	 * @param organName
	 * @return
	 */
	public FlipInfo<Organ> getNearOrgans(String staffId, FlipInfo<Organ> fpi,
			String organName);

	/**
	 * 开启询价
	 * 
	 * @param staffId
	 * @param fpi
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public FlipInfo<WeUserInquiry> openInquiry(String staffId, FlipInfo<WeUserInquiry> fpi, Double longitude,
			Double latitude);

	/**
	 * 获取技师附近店铺
	 * 
	 * @param fpi
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public FlipInfo<Organ> getNearOrgan(FlipInfo<Organ> fpi, Double longitude, Double latitude);

	/**
	 * 查询技师日程
	 * 
	 * @param staffId
	 * @param day
	 * @return
	 */
	public WeStaffCalendar getWeStaffSchedule(String staffId, Integer day);

	/**
	 * 查询技师一周日程
	 * 
	 * @param staffId
	 * @param day
	 * @param day7
	 * @return
	 */
	public List<WeStaffCalendar> findWeStaffSchedule(String staffId, Integer day, Integer day7);

	/**
	 * 查询技师一天可服务时间;
	 * 
	 * @param staffId
	 * @param day
	 *            day':day,'staffId':staffId,'organId':organId
	 */
	public WeStaffCalendar findScheduleTime(String day, String staffId, String organId);

	/**
	 * 保存技师日程
	 * 
	 * @param staffId
	 * @param organId
	 * @param day
	 * @param index
	 * @param selected
	 * @return
	 */
	public ReturnStatus scheduleSave(String staffId, String organId, Integer day, int index, boolean selected);

	/**
	 * 选择店铺
	 * 
	 * @param staffId
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Organ> getOrgans(String staffId, FlipInfo<Organ> fpi);

	/**
	 * 设置繁忙时间
	 * 
	 * @param staffId
	 * @param busyTimeStart
	 * @param busyTimeEnd
	 * @return
	 */
	public ReturnStatus saveBustTime(String staffId, String busyTimeStart, String busyTimeEnd);

	/**
	 * 选择类型
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Code> selectType(FlipInfo<Code> fpi);
	public List<Code> findTypes();

	// 保存图片
	public void getAndSavePhoto(String staffId, String logo);

	/**
	 * 获取技师加入店铺审核信息
	 * 
	 * @param staffId
	 * @param organId
	 * @return
	 */
	public WeOrganStaffVerify getVerifyStatus(String staffId, String organId);

	/**
	 * 获取自我推荐
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getMyRecommendation(String staffId);

	/**
	 * 保存自我推荐
	 * 
	 * @param staffId
	 * @param textArea
	 * @param logo0
	 * @param logo2 
	 * @param logo1 
	 * @return
	 */
	public ReturnStatus editSave(String staffId, String textArea, String logo0, String logo1, String logo2);

	/**
	 * 获取店铺技师列表
	 * 
	 * @param organId
	 * @param flp
	 * @return
	 */
	public FlipInfo<Staff> storeStaffList(String organId, FlipPageInfo<Staff> flp);

	/**
	 * 开启/关闭询价
	 * 
	 * @param staffId
	 * @param type
	 * @return
	 */
	public ReturnStatus openInquiry(String staffId, String type);

	/**
	 * 获取类型列表
	 * 
	 * @return
	 */
	public List<Code> findtype();

	/**
	 * 获取我的店铺
	 * 
	 * @param staffId
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeOrganStaffVerify> getMyStore(String staffId, FlipPageInfo<WeOrganStaffVerify> fpi);

	/**
	 * 技师收藏店铺
	 * 
	 * @param staffId
	 * @param organId
	 * @param followType
	 * @return
	 */
	public Integer followOrgan(String staffId, String organId, String followType);

	/**
	 * 新用户绑定店铺
	 * 
	 * @param staff
	 * @return
	 */
	public Staff bindOrgan(Staff staff);
	/**
	 * 查询技师是否存在待审核的记录信息
	 * @param staffId
	 * @return
	 */
	public ReturnStatus queryWeOrganStaffVerifyNoVerify(String staffId);

	/**
	 * 查询是否补全信息
	 * @param staffId
	 * @return
	 */
	public ReturnStatus isComplementMes(String staffId);

	/**
	 * 技师默认店铺价目表
	 * @param staffId
	 * @param flp 
	 * @return
	 */
	public FlipInfo<Smallsort> getTariffList(String staffId, FlipPageInfo<Smallsort> flp);
	/**
	 * 店铺删除技师
	 * @param staffId
	 * @return
	 */
	public ReturnStatus removeStaff(String staffId);
	/**
	 * 发现一个Code by id
	 * @param codeId
	 * @return
	 */
	public Code findCodeById(String codeId);
	/**
	 * 读取短信
	 *
	 * @return
	 */
	public long findMessageCount(String staffId);
	/**
	 * 读取短信
	 *
	 * @return
	 */
	public void updateMessageToRead(String staffId,int type);

	/**
	 * 查找技师
	 * @param staffId
	 * @return
	 */
	public Staff queryStaffById(String staffId);

	/**
	 *
	 * @param longitude
	 * @param latitude
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Organ> queryOrganListByfollowCount(double longitude, double latitude,FlipInfo<Organ> fpi);

	/**
	 *
	 * @param longitude
	 * @param latitude
	 * @param maxDistance
	 * @param fpi
	 * @return
	 */
	public FlipInfo<Organ> queryOrganListByUser(double longitude,double latitude, double maxDistance, FlipInfo<Organ> fpi);

	/**
	 *
	 * @param organList
	 * @return
	 */
	public FlipInfo<Organ> queryOrganPosition(FlipInfo<Organ> organList);
	public Staff queryStaffByPhone(String phone);
	public List<Staff> queryUserOfstaff(FlipInfo<Staff> fpi);
	public FlipInfo<UserRelevanceStaff> queryUserByFpi(FlipInfo<UserRelevanceStaff> fpi);
	public ReturnStatus relieve(HttpServletRequest request);
	public FlipInfo<Staff> queryUserOfStaff1(FlipInfo<Staff> fpi);
}
