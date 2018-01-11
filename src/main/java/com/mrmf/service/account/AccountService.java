package com.mrmf.service.account;

import java.util.List;

import com.mrmf.entity.Account;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface AccountService {

	/**
	 * 获取账号
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws BaseException
	 */
	public Account getAccount(String username, String password);

	/**
	 * 新增插入账号
	 * 
	 * @param account
	 * @return
	 */
	public String insertAccount(Account account);

	/**
	 * 根据账号修改口令
	 * 
	 * @param accountName
	 * @param password
	 * @return
	 */
	public ReturnStatus changePasswordByAccountName(String accountName, String password);

	/**
	 * 获取短信验证码
	 * 
	 * @param phone
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus verifycode(String phone);
	
	/**
	 * 获取语音验证码
	 * 
	 * @param phone
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus verifycodeVoice(String phone);

	/**
	 * 短信验证码验证
	 * 
	 * @param phone
	 * @param code
	 * @return
	 * @throws BaseException
	 */
	public ReturnStatus verify(String phone, String code);
	
	/**
	 * 根据对应实体id和实体类型获取账号信息
	 * @param entityID
	 * @param accountType
	 * @return
	 */
	public Account getAccountByEntityID(String entityID, String accountType);
	
	/**
	 * 根据对应实体id和实体类型获取账号信息
	 * @param entityID
	 * @param accountType
	 * @return
	 */
	public List<Account> getAccountsByEntityID(String entityID, String accountType);

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
	public FlipInfo<Account> query(FlipInfo<Account> fpi) throws BaseException;

	/**
	 * 查询账号基本信息
	 * 
	 * @param accountId
	 * @return
	 * @throws BaseException
	 */
	public Account queryById(String accountId) throws BaseException;

	/**
	 * 新增/修改账号信息
	 * 
	 * @param account
	 * @return
	 */
	public ReturnStatus upsert(Account account);
	
	/**
	 * 修改账号登录口令
	 * 
	 * @param accountId
	 * @param oldPasswd
	 * @param newPasswd
	 * @return
	 */
	public ReturnStatus changePasswd(String accountId, String oldPasswd, String newPasswd);

}
