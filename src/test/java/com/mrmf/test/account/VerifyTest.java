package com.mrmf.test.account;

import org.junit.Test;

import com.mrmf.service.account.AccountService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.test.TestCaseBase;

public class VerifyTest extends TestCaseBase {

	@Test
	public void test() {
		AccountService as = getBean(AccountService.class);
		ReturnStatus status = as.verify("18612980491","6201");
		printStatus(status);
	}

}
