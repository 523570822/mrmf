package com.mrmf.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Staff;
import com.mrmf.entity.bean.StaffSalary;
import com.mrmf.entity.kaoqin.KKaoqin;
import com.mrmf.entity.kaoqin.KQingjiadengji;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.staff.StaffService;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

@Service("staffSalaryService")
public class StaffSalaryServiceImpl implements StaffSalaryService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private StaffService staffService;

	@Override
	public List<StaffSalary> query(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		List<Userpart> userpartList = queryBy(criterias, organId, startTime, endTime);

		// 查询考勤记录，汇总考勤扣款信息
		criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		List<KKaoqin> kaoqinList = mongoTemplate.find(Query.query(criteria), KKaoqin.class);
		List<KQingjiadengji> qingjiaList = mongoTemplate.find(Query.query(criteria), KQingjiadengji.class);

		List<StaffSalary> result = new ArrayList<>();
		List<Staff> staffList = staffService.findAll(organId);
		for (Staff staff : staffList) {
			StaffSalary ss = new StaffSalary();
			result.add(ss);
			ss.setStaffId(staff.get_id());
			ss.setStaffName(staff.getName());

			int dianCount = 0;
			double totalTicheng = 0, bankaYeji = 0, waimaiYeji = 0, laodongYeji = 0, weixinYeji = 0;

			for (Userpart up : userpartList) {
				boolean is = false;
				boolean isBanka = (up.getType() == 0 && !StringUtils.isEmpty(up.getIncardId())) || up.getType() == 10;
				if (staff.get_id().equals(up.getStaffId1())) {
					is = true;
					if (up.getDian1() != null && up.getDian1()) {
						dianCount++;
					}
					totalTicheng += up.getSomemoney1();
					if (isBanka)
						bankaYeji += up.getYeji1();
					if (up.getType() == 2)
						weixinYeji += up.getYeji1();
					else{
						if(!up.getMiandan())
							laodongYeji += up.getYeji1();
					}

				} else if (staff.get_id().equals(up.getStaffId2())) {
					is = true;
					if (up.getDian2() != null && up.getDian2()) {
						dianCount++;
					}
					totalTicheng += up.getSomemoney2();
					if (isBanka)
						bankaYeji += up.getYeji2();
					if (up.getType() == 2)
						weixinYeji += up.getYeji2();
					else{
						if(!up.getMiandan())
							laodongYeji += up.getYeji2();
					}
				} else if (staff.get_id().equals(up.getStaffId3())) {
					is = true;
					if (up.getDian3() != null && up.getDian3()) {
						dianCount++;
					}
					totalTicheng += up.getSomemoney3();
					if (isBanka)
						bankaYeji += up.getYeji3();
					if (up.getType() == 2)
						weixinYeji += up.getYeji3();
					else{
						if(!up.getMiandan())
							laodongYeji += up.getYeji3();
					}
				}

				if (is) {

				}
			}

			// 外卖业绩
			criterias.add(Criteria.where("guazhang_flag").is(false));
			List<WWaimai> waimaiList = mongoTemplate.find(
					Query.query(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
					WWaimai.class);
			for (WWaimai waimai : waimaiList) {
				if (staff.get_id().equals(waimai.getStaffId1())) {
					waimaiYeji += waimai.getYeji1();
					totalTicheng += waimai.getTicheng1();
				} else if (staff.get_id().equals(waimai.getStaffId2())) {
					waimaiYeji += waimai.getYeji2();
					totalTicheng += waimai.getTicheng2();
				}
			}

			ss.setDianCount(dianCount);
			ss.setTotalTicheng(totalTicheng);
			ss.setBankaYeji(bankaYeji);
			ss.setWaimaiYeji(waimaiYeji);
			ss.setWeixinYeji(weixinYeji);
			ss.setLaodongYeji(laodongYeji);

			double gongzi = !StringUtils.isEmpty(staff.getGongzi()) ? Double.parseDouble(staff.getGongzi()) : 0;
			ss.setBaseSalary(gongzi);

			double subsidy = 0; // 补助
			double reward = 0;// 奖金
			double deposit = 0; // 扣除押金

			// 计算考勤扣款
			for (KKaoqin kaoqin : kaoqinList) {
				if (staff.get_id().equals(kaoqin.getStaffId())) {
					deposit += kaoqin.getMoney_koufa();
				}
			}

			// 计算请假扣款
			for (KQingjiadengji qingjia : qingjiaList) {
				if (staff.get_id().equals(qingjia.getNames())) {
					deposit += qingjia.getMoney_koufa();
				}
			}

			double total = gongzi + totalTicheng + subsidy + reward - deposit;
			ss.setTotal(total);

			int kaoqinDays = 0;
			// 考勤天数，开始和结束时间
			if (startTime == null) {
				startTime = DateUtil.currentDate();
			}
			if (endTime == null) {
				endTime = DateUtil.currentDate();
			}
			kaoqinDays = DateUtil.differentDays(startTime, endTime) + 1;
			ss.setKaoqinDays(kaoqinDays);
		}
		return result;
	}

	private List<Userpart> queryBy(List<Criteria> criterias, String organId, Date startTime, Date endTime)
			throws BaseException {
		if (criterias == null)
			criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
		criterias.add(Criteria.where("flag2").is(true)); // 已结账
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		List<Userpart> resultList = mongoTemplate
				.find(Query.query(criteria.andOperator(criterias.toArray(new Criteria[criterias.size()])))
						.with(new Sort(Direction.DESC, "createTime")), Userpart.class);
		return resultList;
	}

}
