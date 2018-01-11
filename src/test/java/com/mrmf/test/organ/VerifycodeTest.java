package com.mrmf.test.organ;

import org.junit.Test;

import com.mrmf.service.organ.OrganService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.test.TestCaseBase;

public class VerifycodeTest extends TestCaseBase {

	@Test
	public void test() {
		OrganService as = getBean(OrganService.class);
		ReturnStatus status = as.verifycode("18612980491");
		printStatus(status);
	}

}
