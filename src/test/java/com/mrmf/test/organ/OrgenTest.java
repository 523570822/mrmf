package com.mrmf.test.organ;
import org.junit.Test;

import com.mrmf.service.sys.SysUser.SysUserService;
import com.osg.framework.test.TestCaseBase;

public class OrgenTest extends TestCaseBase {
	@Test
	public void tesQeryStaffCheck(){
		/*SysUserService su = getBean(SysUserService.class);
	    FlipInfo<Staff> staffFlip = new FlipInfo<Staff>();
	    staffFlip = su.queryStaffCheck(staffFlip);
	    for(Staff staff:staffFlip.getData()) {
	    	System.out.println(staff.getName());
	    }*/
	}
	
	/*3999358017948500710
	3204694250654025728
	1
	呵呵呵呵*/
	
	@Test
	public void testStaffCheck(){
		SysUserService su = getBean(SysUserService.class);
	   /* FlipInfo<Staff> staffFlip = new FlipInfo<Staff>();
	    for(Staff staff:staffFlip.getData()) {
	    	System.out.println(staff.getName());
	    }*/
		su.updateStaffVerify("3999358017948500710", "3204694250654025728", "1", "呵呵呵呵");
	}
}
