package com.mrmf.test.account;

import org.junit.Test;

import com.mrmf.service.account.AccountService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.test.TestCaseBase;

public class VerifycodeVoiceTest extends TestCaseBase {

	@Test
	public void test() {
		AccountService as = getBean(AccountService.class);
		ReturnStatus status = as.verifycodeVoice("18612980491");
		printStatus(status);
	}

}
