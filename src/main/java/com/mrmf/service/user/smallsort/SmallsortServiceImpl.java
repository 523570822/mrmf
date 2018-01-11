package com.mrmf.service.user.smallsort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.service.common.PinYinUtil;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("smallsortService")
public class SmallsortServiceImpl implements SmallsortService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Smallsort> query(FlipInfo<Smallsort> fpi) throws BaseException {
		mongoTemplate.findByPage(Query.query(Criteria.where("delete_flag").ne(true)), fpi, Smallsort.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<Smallsort> datas) {
		List<String> bigsortIds = new ArrayList<String>();
		for (Smallsort smallsort : datas) {
			if (!bigsortIds.contains(smallsort.getBigcode()))
				bigsortIds.add(smallsort.getBigcode());
		}

		Map<String, Bigsort> bigsortMap = new HashMap<>();
		List<Bigsort> bigsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(bigsortIds)), Bigsort.class);
		for (Bigsort bigsort : bigsorts) {
			bigsortMap.put(bigsort.get_id(), bigsort);
		}

		for (Smallsort smallsort : datas) {
			Bigsort bigsort = bigsortMap.get(smallsort.getBigcode());
			if (bigsort != null)
				smallsort.setBigsortName(bigsort.getName());
		}
	}

	@Override
	public Smallsort queryById(String smallsortId) throws BaseException {
		Smallsort bigsort = mongoTemplate.findById(smallsortId, Smallsort.class);
		if (bigsort == null)
			throw new BaseException("指定id的服务服务价目信息不存在");
		else {
			return bigsort;
		}
	}

	@Override
	public ReturnStatus upsert(Smallsort smallsort) {
		smallsort.setIdIfNew();
		smallsort.setCreateTimeIfNew();

		smallsort.setZjfCode(PinYinUtil.getFirstSpell(smallsort.getName()));
		mongoTemplate.save(smallsort);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(smallsort);
		return status;
	}

	@Override
	public List<Smallsort> findAll(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId).and("delete_flag").ne(true)), Smallsort.class);
	}

	@Override
	public List<Smallsort> findAllValid(String organId) throws BaseException {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("valid").is(true), Criteria.where("organId").is(organId),Criteria.where("delete_flag").ne(true));
		return mongoTemplate.find(Query.query(criteria), Smallsort.class);
	}

	@Override
	public ReturnStatus enable(String organId, String id) {
		try {
			Smallsort ss = queryById(id);
			if (!organId.equals(ss.getOrganId())) {
				return new ReturnStatus(false, "服务项目所在机构id与当前机构id不符");
			} else {
				ss.setValid(true);
				mongoTemplate.save(ss);
			}
			return new ReturnStatus(true);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	@Override
	public ReturnStatus disable(String organId, String id) {
		try {
			Smallsort ss = queryById(id);
			if (!organId.equals(ss.getOrganId())) {
				return new ReturnStatus(false, "服务项目所在机构id与当前机构id不符");
			} else {
				ss.setValid(false);
				mongoTemplate.save(ss);
			}
			return new ReturnStatus(true);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

}
