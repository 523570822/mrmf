package com.osg.framework.web.security.verify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mrmf.entity.Account;
import com.mrmf.service.account.AccountService;
import com.osg.framework.web.security.VerifyObject;

public class VerifyAccountRepositoryImpl implements VerifyAccountRepository {
	Logger logger = LoggerFactory.getLogger(VerifyAccountRepositoryImpl.class);

	@Autowired
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public VerifyObject getAccountByName(String accountName) {
		Account account = accountService.getAccount(accountName, null);
		logger.debug("begin load account by name:{}", accountName);
		if (account == null)
			return null;
		logger.debug("end load account by name:{}", accountName);
		VerifyObject vo = new VerifyObject();
		vo.setUserName(account.getAccountName());
		vo.setFullName(account.getAccountName());
		vo.setPassword(account.getAccountPwd());
		vo.setEntity(account);
		logger.debug("load verifyobject :{}", vo);
		return vo;
	}
}
