package com.mrmf.service.bumen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Bumen;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("bumenService")
public class BumenServiceImpl implements BumenService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Bumen> query(FlipInfo<Bumen> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, Bumen.class);
		return fpi;
	}

	@Override
	public Bumen queryById(String bumenId) throws BaseException {
		Bumen bumen = mongoTemplate.findById(bumenId, Bumen.class);
		if (bumen == null)
			throw new BaseException("指定id的部门信息不存在");
		else
			return bumen;
	}

	@Override
	public ReturnStatus upsert(Bumen bumen) {
		// String id = bumen.get_id();
		bumen.setIdIfNew();
		bumen.setCreateTimeIfNew();
		mongoTemplate.save(bumen);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(bumen);
		return status;
	}

	@Override
	public List<Bumen> findAll(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), Bumen.class);
	}

	@Override
	public ReturnStatus remove(String bumenId) {
		mongoTemplate.remove(Query.query(Criteria.where("_id").is(bumenId)), Bumen.class);
		return new ReturnStatus(true);
	}
}
