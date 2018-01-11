package com.mrmf.service.user.crossOrgan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.bean.UserpartCrossOrganSum;
import com.mrmf.entity.user.UserpartCrossOrgan;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

@Service("userpartCrossOrganService")
public class UserPartCrossOrganServiceImpl implements UserpartCrossOrganService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<UserpartCrossOrgan> query(FlipInfo<UserpartCrossOrgan> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		mongoTemplate.findByPage(null, fpi, UserpartCrossOrgan.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<UserpartCrossOrgan> datas) {
		List<String> organIds = new ArrayList<String>();
		for (UserpartCrossOrgan co : datas) {
			if (!organIds.contains(co.getOrganId()))
				organIds.add(co.getOrganId());
			if (!organIds.contains(co.getOwnerOrganId()))
				organIds.add(co.getOwnerOrganId());
		}

		Map<String, Organ> organMap = new HashMap<>();
		List<Organ> organs = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
		for (Organ organ : organs) {
			organMap.put(organ.get_id(), organ);
		}

		for (UserpartCrossOrgan co : datas) {
			Organ organ = organMap.get(co.getOrganId());
			if (organ != null)
				co.setOrganName(organ.getName());

			organ = organMap.get(co.getOwnerOrganId());
			if (organ != null)
				co.setOwnerOrganName(organ.getName());
			if(co.getStatus()==0){
				co.setStatusName("未处理");
			}else{
				co.setStatusName("已处理");
			}
			if(co.getOwnerStatus()==0){
				co.setOwnerStatusName("未处理");
			}else{
				co.setOwnerStatusName("已处理");
			}

		}
	}

	@Override
	public UserpartCrossOrganSum totalHandle(Integer status, Integer ownerStatus, String organId, String ownerOrganId,
			Date startTime, Date endTime) throws BaseException {
		Criteria criteria = new Criteria();
		List<Criteria> criterias = new ArrayList<>();
		if (!StringUtils.isEmpty(organId)) {
			criterias.add(Criteria.where("organId").is(organId));
		}
		if (!StringUtils.isEmpty(ownerOrganId)) {
			criterias.add(Criteria.where("ownerOrganId").is(ownerOrganId));
		}
		if (status != null) {
			criterias.add(Criteria.where("status").is(status));
		}
		if (ownerStatus != null) {
			criterias.add(Criteria.where("ownerStatus").is(ownerStatus));
		}
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]))),
				Aggregation.group("organId").sum("amount").as("totalAmount"));

		AggregationResults<UserpartCrossOrganSum> ar = mongoTemplate.aggregate(aggregation, UserpartCrossOrgan.class,
				UserpartCrossOrganSum.class);

		UserpartCrossOrganSum fs = new UserpartCrossOrganSum();
		List<UserpartCrossOrganSum> list = ar.getMappedResults();
		for (UserpartCrossOrganSum os : list) {
			fs.setTotalAmount(fs.getTotalAmount() + os.getTotalAmount());
		}

		return fs;
	}

	@Override
	public ReturnStatus handle(Integer status, Integer ownerStatus, String organId, String ownerOrganId, Date startTime,
			Date endTime, boolean isOwner) {
		Criteria criteria = new Criteria();
		List<Criteria> criterias = new ArrayList<>();
		if (!StringUtils.isEmpty(organId)) {
			criterias.add(Criteria.where("organId").is(organId));
		}
		if (!StringUtils.isEmpty(ownerOrganId)) {
			criterias.add(Criteria.where("ownerOrganId").is(ownerOrganId));
		}
		if (status != null) {
			criterias.add(Criteria.where("status").is(status));
		}
		if (ownerStatus != null) {
			criterias.add(Criteria.where("ownerStatus").is(ownerStatus));
		}
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));

		List<UserpartCrossOrgan> coList = mongoTemplate.find(Query.query(criteria), UserpartCrossOrgan.class);
		for (UserpartCrossOrgan co : coList) {
			if (isOwner && co.getOwnerStatus() == 0) {
				co.setOwnerStatus(1);
				mongoTemplate.save(co);
			}
			if (!isOwner && co.getStatus() == 0) {
				co.setStatus(1);
				mongoTemplate.save(co);
			}
		}

		return new ReturnStatus(true);
	}

	@Override
	public List<UserpartCrossOrgan> queryList(FlipInfo<UserpartCrossOrgan> fpi)
			throws BaseException {
		List<Criteria> criterias = new ArrayList<Criteria>();
		String ownerStatus =(String) fpi.getParams().get("ownerStatus");
		if(!StringUtils.isEmpty(ownerStatus)){
			criterias.add(Criteria.where("ownerStatus").is(Integer.parseInt(ownerStatus)));
		}
		String organId = (String) fpi.getParams().get("organId");
		if(!StringUtils.isEmpty(organId)){
			criterias.add(Criteria.where("organId").is(organId));
		}
		String ownerOrganId = (String)fpi.getParams().get("ownerOrganId");
		if(!StringUtils.isEmpty(ownerOrganId)){
			criterias.add(Criteria.where("ownerOrganId").is(ownerOrganId));
		}
		try {
			String date = (String) fpi.getParams().get("startTime");
			if(!StringUtils.isEmpty(date)){
				criterias.add(Criteria.where("createTime").gte(DateUtil.parse(date, DateUtil.YEAR_MONTH_DAY_PATTERN)));
			}
			String date1 = (String) fpi.getParams().get("endTime");
			if(!StringUtils.isEmpty(date1)){
				criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(DateUtil.parse(date1, DateUtil.YEAR_MONTH_DAY_PATTERN), 1)));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Criteria criteria = new Criteria();
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		List<UserpartCrossOrgan> crossOrgan = mongoTemplate.find(Query.query(criteria), UserpartCrossOrgan.class);
		getDetails(crossOrgan);
		return crossOrgan;
	}

}
