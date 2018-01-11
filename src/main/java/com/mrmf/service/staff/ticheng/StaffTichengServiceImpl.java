package com.mrmf.service.staff.ticheng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.staff.StaffTicheng;
import com.mrmf.entity.staff.Staffpost;
import com.mrmf.entity.user.Smallsort;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;

@Service("staffTichengService")
public class StaffTichengServiceImpl implements StaffTichengService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<StaffTicheng> query(FlipInfo<StaffTicheng> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, StaffTicheng.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<StaffTicheng> datas) {
		List<String> staffpostIds = new ArrayList<String>();
		List<String> smallsortIds = new ArrayList<String>();
		for (StaffTicheng staffTicheng : datas) {
			if (!smallsortIds.contains(staffTicheng.getSmallsort()))
				smallsortIds.add(staffTicheng.getSmallsort());
			if (!staffpostIds.contains(staffTicheng.getStaffpost()))
				staffpostIds.add(staffTicheng.getStaffpost());

		}

		Map<String, Smallsort> smallsortMap = new HashMap<>();
		List<Smallsort> smallsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(smallsortIds)),
				Smallsort.class);
		for (Smallsort smallsort : smallsorts) {
			smallsortMap.put(smallsort.get_id(), smallsort);
		}

		Map<String, Staffpost> staffpostMap = new HashMap<>();
		List<Staffpost> staffposts = mongoTemplate.find(Query.query(Criteria.where("_id").in(staffpostIds)),
				Staffpost.class);
		for (Staffpost staffpost : staffposts) {
			staffpostMap.put(staffpost.get_id(), staffpost);
		}

		for (StaffTicheng staffTicheng : datas) {
			Smallsort smallsort = smallsortMap.get(staffTicheng.getSmallsort());
			if (smallsort != null)
				staffTicheng.setSmallsortName(smallsort.getName());
			Staffpost staffpost = staffpostMap.get(staffTicheng.getStaffpost());
			if (staffpost != null)
				staffTicheng.setStaffpostName(staffpost.getName());
		}
	}

	@Override
	public StaffTicheng queryById(String staffTichengId) throws BaseException {
		StaffTicheng staffTicheng = mongoTemplate.findById(staffTichengId, StaffTicheng.class);
		if (staffTicheng == null)
			throw new BaseException("指定id的岗位信息不存在");
		else {
			return staffTicheng;
		}
	}

	@Override
	public ReturnStatus upsert(StaffTicheng staffTicheng) {
		preHandleStaffTicheng(staffTicheng);

		mongoTemplate.save(staffTicheng);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(staffTicheng);
		return status;
	}

	@Override
	public List<StaffTicheng> findAll(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), StaffTicheng.class);
	}

	@Override
	public ReturnStatus upsertBatch(String[] smallsortIds, StaffTicheng staffTicheng) {
		if (smallsortIds != null && smallsortIds.length > 0) {
			// 查询服务项目信息，批量设置默认业绩为服务项目价格
			List<String> ssIds = Arrays.asList(smallsortIds);
			List<Smallsort> smallsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(ssIds)),
					Smallsort.class);
			Map<String, Smallsort> smallsortMap = new HashMap<>();
			for (Smallsort ss : smallsorts) {
				smallsortMap.put(ss.get_id(), ss);
			}
			for (String smallsortId : smallsortIds) {
				staffTicheng.set_id(null);
				staffTicheng.setSmallsort(smallsortId);
				Smallsort ss = smallsortMap.get(smallsortId);
				if (ss != null)
					staffTicheng.setYeji(ss.getPrice());

				preHandleStaffTicheng(staffTicheng);
				mongoTemplate.save(staffTicheng);
			}
		}
		return new ReturnStatus(true);
	}

	private void preHandleStaffTicheng(StaffTicheng staffTicheng) {
		// 先查询是否已经存在定义，存在则更新，否则新增插入
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("staffpost").is(staffTicheng.getStaffpost()),
				Criteria.where("smallsort").is(staffTicheng.getSmallsort()));
		StaffTicheng st = mongoTemplate.findOne(Query.query(criteria), StaffTicheng.class);
		if (st == null) { // 新增插入
			staffTicheng.setIdIfNew();
			staffTicheng.setCreateTimeIfNew();
		} else { // 更新
			staffTicheng.set_id(st.get_id());
			staffTicheng.setUpdateTime(DateUtil.currentDate());
		}
	}
}
