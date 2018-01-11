package com.mrmf.service.account;

import com.mrmf.entity.Account;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface Account2Service {

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

}
