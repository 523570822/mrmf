package com.mrmf.test.staff;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mrmf.entity.Staff;
import com.mrmf.service.staff.StaffService;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.test.TestCaseBase;

public class QueryOrderByHotTest extends TestCaseBase {

	@Test
	public void test() {
		StaffService as = getBean(StaffService.class);

		FlipInfo<Staff> fpi = new FlipInfo<>();
		Map params = new HashMap<>();
		params.put("sex", "女");
		fpi.setParams(params);
		try {
			fpi = as.queryOrderByHot(fpi);
			for (Staff staff : fpi.getData()) {
				System.out.println(staff.getName() + ":" + staff.getFollowCount());
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
}
