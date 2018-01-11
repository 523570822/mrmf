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

import com.mrmf.entity.user.Userpart;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;

@Service("colligateService")
public class ColligateServiceImpl implements ColligateService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public List<Userpart> query(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);
		return resultList;
	}

	private List<Userpart> queryBy(List<Criteria> criterias, String organId, Date startTime, Date endTime)
			throws BaseException {
		if (criterias == null)
			criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
		criterias.add(Criteria.where("flag2").is(true)); // 已结账
		criterias.add(Criteria.where("delete_flag").is(false));
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
