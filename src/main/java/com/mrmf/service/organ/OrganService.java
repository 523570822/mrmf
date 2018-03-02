package com.mrmf.service.organ;

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganSetting;
import com.mrmf.entity.sqlEntity.Prestore;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

import java.util.List;

public interface OrganService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 查询公司列表
	 *
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Organ> query(FlipInfo<Organ> fpi) throws BaseException;

	/**
	 * 验证预存款项
	 *
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus sysCharge(String _id, double charge) throws Exception;

	/**
	 * 查询店铺平台卡充值历史记录
	 *
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Prestore> sysQueryChargeHis(FlipInfo<Prestore> fpi) throws BaseException;

	/**
	 * 查询公司商城集合
	 *
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Organ> queryByShopPhone(FlipInfo<Organ> fpi) throws BaseException;

	/**
	 * 查询公司基本信息
	 *
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public Organ queryById(String organId) throws BaseException;

	/**
	 * 根据id查询公司基本信息
	 *
	 * @param organIds
	 * @return
	 * @throws BaseException
	 */
	public List<Organ> queryByIds(List<String> organIds) throws BaseException;

	/**
	 * 根据父机构id查询子机构列表
	 *
	 * @param parentId
	 * @return
	 * @throws BaseException
	 */
	public List<Organ> queryByParentId(String parentId) throws BaseException;

	/**
	 * 根据管理员账号查询公司基本信息
	 *
	 * @param adminId
	 * @return
	 * @throws BaseException
	 */
	public Organ queryByAdminId(String adminId) throws BaseException;

	/**
	 * 指定公司增加信任ip列表
	 *
	 * @param organId
	 * @param ip
	 * @return
	 */
	public ReturnStatus addIp(String organId, String ip);

	/**
	 * 新增/修改公司信息
	 *
	 * @param organ
	 * @return
	 */
	public ReturnStatus upsert(Organ organ);

	/**
	 * 启用公司
	 *
	 * @param organId
	 * @return
	 */
	public ReturnStatus enable(String organId);

	/**
	 * 禁用公司
	 *
	 * @param organId
	 * @return
	 */
	public ReturnStatus disable(String organId);

	/**
	 * 更改公司繁忙状态
	 *
	 * @param state
	 * @return
	 */
	public ReturnStatus changeState(String organId, int state);

	/**
	 * 更改会员卡通用公司列表
	 *
	 * @param organId
	 * @param cardOrganIds
	 * @return
	 */
	public ReturnStatus changeCardOrganIds(String organId, List<String> cardOrganIds);

	/**
	 * 查询指定机构的伙伴机构列表，如其他子公司、总部等（不含本机构）
	 *
	 * @param organId
	 * @return
	 * @throws BaseException
	 */
	public List<Organ> queryAllPartner(String organId) throws BaseException;

	/**
	 * 获取公司参数设置对象
	 *
	 * @param organId
	 * @return
	 */
	public OrganSetting querySetting(String organId) throws BaseException;

	/**
	 * 更新公司参数设置
	 *
	 * @param setting
	 * @return
	 */
	public ReturnStatus upsertSetting(OrganSetting setting);

	/**
	 * 更新提成流水设置
	 *
	 * @param settingId
	 * @param tichengLiushui
	 * @return
	 */
	public ReturnStatus upsertSettingTichengLiushui(String settingId, List<String> tichengLiushui);

	/**
	 * 查询当前账号可管理的店铺id列表
	 *
	 * @param account
	 * @return
	 * @throws BaseException
	 */
	public List<String> queryAdminOrganIds(Account account) throws BaseException;

	/**
	 * ------------------移动端调用相关接口--------------
	 */

	/**
	 * 获取公司管理员手机号短信验证码
	 *
	 * @param phone
	 * @return
	 */
	public ReturnStatus verifycode(String phone);

	/**
	 * 短信验证码验证，验证通过后绑定微信号为公司管理员
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
	 * 根据总公司ID获取子公司列表
	 *
	 * @param parentId
	 * @return
	 */
	public List<Organ> queryOrganListByParentId(String parentId);

	/**
	 * 查询所有店铺
	 */
	public List<Organ> queryOrganList();

	/**
	 * 测试查询符合条件的地址
	 */
	public List<Organ> queryCity(String city);
}