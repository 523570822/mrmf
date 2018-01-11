package com.mrmf.test.staff;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mrmf.entity.WeBRegion;
import com.mrmf.entity.WeStaffSign;
import com.mrmf.service.staff.StaffService;
import com.osg.framework.test.TestCaseBase;

public class StaffTest extends TestCaseBase{
	@Test
	public void test() {
		/*StaffService staffService=getBean(StaffService.class);
		List<WeStaffSign> signStatistics = staffService.getSignStatistics("1342494374921681777");
		System.out.println("......");
		for (WeStaffSign weStaffSign : signStatistics) {
			Date date = weStaffSign.getCreateTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String day=new SimpleDateFormat("yyyy-MM-dd").format(date);
			String time=new SimpleDateFormat("HH:mm").format(date);
			int week=cal.get(Calendar.DAY_OF_WEEK);
			System.out.println(day+"/"+time+"/"+week);
			System.out.println(date);
		}
		*/
	}
}
