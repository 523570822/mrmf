package com.mrmf.service.staff.staffpost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.staff.Staffpost;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("staffpostService")
public class StaffpostServiceImpl implements StaffpostService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Staffpost> query(FlipInfo<Staffpost> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, Staffpost.class);
		return fpi;
	}

	@Override
	public Staffpost queryById(String staffpostId) throws BaseException {
		Staffpost staffpost = mongoTemplate.findById(staffpostId, Staffpost.class);
		if (staffpost == null)
			throw new BaseException("指定id的岗位信息不存在");
		else {
			return staffpost;
		}
	}

	@Override
	public ReturnStatus upsert(Staffpost staffpost) {
		// String id = role.get_id();
		staffpost.setIdIfNew();
		staffpost.setCreateTimeIfNew();

		mongoTemplate.save(staffpost);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(staffpost);
		return status;
	}

	@Override
	public List<Staffpost> findAll(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), Staffpost.class);
	}
}
