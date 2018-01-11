package com.mrmf.service.usercard;

import java.util.ArrayList;
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
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBDistrict;
import com.mrmf.entity.WeBRegion;
import com.mrmf.entity.WeSysCardChargeHis;
import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.bean.CardPayOnlineSum;
import com.mrmf.entity.user.Usercard;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

@Service("usercardService")
public class UsercardServiceImpl implements UsercardService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Usercard> sysQuerySysCard(FlipInfo<Usercard> fpi) throws BaseException {
		fpi.setSortField("updateTime");
		fpi.setSortOrder("DESC");

		fpi = mongoTemplate.findByPage(Query.query(Criteria.where("userId").is("0")), fpi, Usercard.class);
		getDetail(fpi.getData());
		return fpi;
	}

	private void getDetail(List<Usercard> cardList) {
		List<String> organIds = new ArrayList<>();
		for (Usercard card : cardList) {
			if (!organIds.contains(card.getOrganId()))
				organIds.add(card.getOrganId());
		}

		Map<String, Organ> organMap = new HashMap<>();
		List<Organ> organs = mongoTemplate.find(new Query(Criteria.where("_id").in(organIds)), Organ.class);
		for (Organ organ : organs) {
			organMap.put(organ.get_id(), organ);
		}

		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("organId").in(organIds)),
				Aggregation.group("organId").sum("price").as("total"));
		AggregationResults<CardPayOnlineSum> ar = mongoTemplate.aggregate(aggregation, WeUserPayFenzhang.class,
				CardPayOnlineSum.class);
		Map<String, Double> totalPriceMap = new HashMap<String, Double>();
		List<CardPayOnlineSum> list = ar.getMappedResults();
		for (CardPayOnlineSum s : list) {
			totalPriceMap.put(s.get_id(), s.getTotal());
		}

		for (Usercard card : cardList) {
			Organ organ = organMap.get(card.getOrganId());
			if (organ != null) {
				card.setParentId(organ.getParentId());
				card.setContactMan(organ.getContactMan());
				card.setPhone(organ.getPhone());
				card.setMaster(organ.getMaster());
				card.setOrganValid(organ.getValid());
				card.setOrganIsNotPrepay(organ.getIsNotPrepay());
			}

			Double d = totalPriceMap.get(card.getOrganId());
			if (d != null)
				card.setUserPayTotal(d);
		}
	}

	@Override
	public Usercard sysQuery(String organId) throws BaseException {
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("公司id不能为空！");
		}
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null) {
			throw new BaseException("指定公司id信息不存在！");
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("userId").is("0"));
		Usercard syscard = mongoTemplate.findOne(new Query(criteria), Usercard.class);
		if (syscard == null) {
			syscard = new Usercard();
			syscard.setIdIfNew();
			syscard.setCreateTimeIfNew();
			syscard.setUpdateTimeIfNew();
			syscard.setUserId("0");
			syscard.setOrganId(organId);
			syscard.setMoney_leiji(0); // 累计金额
			syscard.setMoney4(0); // 卡余额
			syscard.setOrganName(organ.getName());
			syscard.setOrganAbname(organ.getAbname());
			mongoTemplate.save(syscard);
		}
		syscard.setOrganName(organ.getName());
		return syscard;
	}

	@Override
	public ReturnStatus sysCharge(String _id, double charge) {
		ReturnStatus status;
		if (StringUtils.isEmpty(_id)) {
			status = new ReturnStatus(false, "参数错误！");
		} else {
			Usercard syscard = mongoTemplate.findById(_id, Usercard.class);
			if (syscard != null) {
				double yue = syscard.getMoney4() + charge;
				if (yue < 0) {
					status = new ReturnStatus(false, "冲减后余额为负数！");
				} else {
					syscard.setMoney_leiji(syscard.getMoney_leiji() + charge);
					syscard.setMoney4(yue);

					syscard.setUpdateTime(DateUtil.currentDate());

					// 插入充值记录
					WeSysCardChargeHis chargeHis = new WeSysCardChargeHis();
					chargeHis.setIdIfNew();
					chargeHis.setCreateTimeIfNew();
					chargeHis.setOrganId(syscard.getOrganId());
					chargeHis.setAmount(charge);

					mongoTemplate.save(chargeHis);
					mongoTemplate.save(syscard);
					status = new ReturnStatus(true);
				}
			} else {
				status = new ReturnStatus(false, "数据错误！");
			}
		}
		return status;
	}

	@Override
	public FlipInfo<WeSysCardChargeHis> sysQueryChargeHis(FlipInfo<WeSysCardChargeHis> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");

		fpi = mongoTemplate.findByPage(null, fpi, WeSysCardChargeHis.class);
		return fpi;
	}

	@Override
	public List<Organ> queryOrganList(String city, String district, String region, String organName,
			String organAbname) {
		List<Criteria> criterias = new ArrayList<Criteria>();
		List<Organ> organs = null;
		if (!StringUtils.isEmpty(city)) {
			WeBCity c = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(city)), WeBCity.class);
			criterias.add(Criteria.where("city").is(c.getName()));
		}
		if (!StringUtils.isEmpty(district)) {
			WeBDistrict d = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(district)), WeBDistrict.class);
			criterias.add(Criteria.where("district").is(d.getName()));
		}
		if (!StringUtils.isEmpty(region)) {
			WeBRegion r = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(region)), WeBRegion.class);
			criterias.add(Criteria.where("region").is(r.getName()));
		}
		if (!StringUtils.isEmpty(organName)) {
			criterias.add(Criteria.where("name").regex(organName));
		}
		if (!StringUtils.isEmpty(organAbname)) {
			criterias.add(Criteria.where("abname").regex(organAbname));
		}
		if (criterias.size() > 0) {
			Criteria criteria = new Criteria();
			criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
			organs = mongoTemplate.find(Query.query(criteria), Organ.class);
		} else {
			organs = mongoTemplate.find(null, Organ.class);
		}

		return organs;
	}

}
