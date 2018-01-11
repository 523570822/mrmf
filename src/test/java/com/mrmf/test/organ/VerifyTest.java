package com.mrmf.test.organ;

import org.junit.Test;

import com.mrmf.service.organ.OrganService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.test.TestCaseBase;

public class VerifyTest extends TestCaseBase {

	@Test
	public void test() {
		OrganService as = getBean(OrganService.class);
		ReturnStatus status = as.verify("myopenid", "myunionid", "18612980491", "1829");
		printStatus(status);
	}

}
