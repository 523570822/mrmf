package com.mrmf.test.staff;

import org.junit.Test;

import com.mrmf.service.staff.StaffService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.test.TestCaseBase;

public class VerifyTest extends TestCaseBase {

	@Test
	public void test() {
		StaffService as = getBean(StaffService.class);
		ReturnStatus status = as.verify("myopenid", "myunionid", "18518487105", "1116");
		printStatus(status);
	}

}
