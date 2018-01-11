package com.mrmf.service.staff.ticheng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.staff.StaffFloatTicheng;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("staffFloatTichengService")
public class StaffFloatTichengServiceImpl implements StaffFloatTichengService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<StaffFloatTicheng> query(FlipInfo<StaffFloatTicheng> fpi) throws BaseException {
		fpi.setSortField("floatType,yeji1");
		fpi.setSortOrder("ASC");
		mongoTemplate.findByPage(null, fpi, StaffFloatTicheng.class);
		return fpi;
	}

	@Override
	public StaffFloatTicheng queryById(String staffFloatTichengId) throws BaseException {
		StaffFloatTicheng bigsort = mongoTemplate.findById(staffFloatTichengId, StaffFloatTicheng.class);
		if (bigsort == null)
			throw new BaseException("指定id的浮动提成信息不存在");
		else {
			return bigsort;
		}
	}

	@Override
	public ReturnStatus upsert(StaffFloatTicheng staffFloatTicheng) {
		// String id = role.get_id();
		staffFloatTicheng.setIdIfNew();
		staffFloatTicheng.setCreateTimeIfNew();

		mongoTemplate.save(staffFloatTicheng);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(staffFloatTicheng);
		return status;
	}

	@Override
	public List<StaffFloatTicheng> queryAllFenduan(String organId) throws BaseException {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("floatType").is(0), Criteria.where("organId").is(organId));
		return mongoTemplate.find(Query.query(criteria).with(new Sort(Direction.ASC, "yeji1")),
				StaffFloatTicheng.class);
	}

	@Override
	public List<StaffFloatTicheng> queryAllZuigao(String organId) throws BaseException {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("floatType").is(1), Criteria.where("organId").is(organId));
		return mongoTemplate.find(Query.query(criteria).with(new Sort(Direction.DESC, "yeji1")),
				StaffFloatTicheng.class);
	}

}
