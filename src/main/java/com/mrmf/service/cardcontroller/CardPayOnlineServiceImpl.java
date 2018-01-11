package com.mrmf.service.cardcontroller;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBDistrict;
import com.mrmf.entity.WeBRegion;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeUserCardCharge;
import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.bean.CardPayOnlineSum;
import com.mrmf.entity.bean.OrganCardNum;
import com.mrmf.entity.bean.StaffRank;
import com.mrmf.entity.bean.UserPayFenzhangSum;
import com.mrmf.entity.user.Userincard;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;

@Service("cardPayOnlineService")
public class CardPayOnlineServiceImpl implements CardPayOnlineService {
	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<WeUserCardCharge> queryUserCardCharge(FlipInfo<WeUserCardCharge> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WeUserCardCharge.class);
		for (WeUserCardCharge cc : fpi.getData()) {
			User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cc.getUserId())), User.class);
			if(user != null)
				cc.setUserName(user.getNick());
			Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cc.getOrganId())), Organ.class);
			if(organ != null) {
				cc.setOrganName(organ.getName());
				cc.setBankAccount(organ.getBankAccount());
				cc.setBankAccountName(organ.getBankAccountName());
				cc.setBankKaihu(organ.getBankKaihu());
			}
		}
		return fpi;
	}

	@Override
	public List<Organ> queryOrganList(String organName) {
		List<Organ> organList = mongoTemplate.find(Query.query(Criteria.where("name").regex(organName)), Organ.class);
		return organList;
	}

	@Override
	public ReturnStatus dealWithOnlyOne(String cardId, String state) {
		WeUserCardCharge charge = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cardId)),
				WeUserCardCharge.class);
		if ("state".equals(state)) {
			charge.setState(1);
		} else if ("organState".equals(state)) {
			charge.setOrganState(1);
		} else {
			return new ReturnStatus(false, "处理失败");
		}
		mongoTemplate.save(charge);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus dealWithAll(String organId, String stateType, Integer state, String organName, Date startTime,
			Date endTime) {
		Criteria criteria = new Criteria();
		List<Criteria> criterias = new ArrayList<>();
		if (!StringUtils.isEmpty(organId)) {
			criterias.add(Criteria.where("organId").is(organId));
		}
		if (state != null) {
			if ("state".equals(stateType)) {
				criterias.add(Criteria.where("state").is(state));
			} else if ("organState".equals(stateType)) {
				criterias.add(Criteria.where("organState").is(state));
			} else {
				return new ReturnStatus(false, "处理失败");
			}
		} else {
			if ("state".equals(stateType)) {
				criterias.add(Criteria.where("state").is(0));
			} else if ("organState".equals(stateType)) {
				criterias.add(Criteria.where("organState").is(0));
			} else {
				return new ReturnStatus(false, "处理失败");
			}
		}
		if (!StringUtils.isEmpty(organName)) {
			List<Organ> organs = queryOrganList(organName);
			String organIds = "";
			for (Organ o : organs) {
				organIds = organIds + o.get_id() + ",";
			}
			if (!"".equals(organIds)) {
				organIds = organIds.substring(0, organIds.length() - 1);
				criterias.add(Criteria.where("organId").in(organIds));
			}
		}
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		List<WeUserCardCharge> card = mongoTemplate.find(Query.query(criteria), WeUserCardCharge.class);
		for (WeUserCardCharge cc : card) {
			if ("state".equals(stateType)) {
				cc.setState(1);
			} else if ("organState".equals(stateType)) {
				cc.setOrganState(1);
			}
			mongoTemplate.save(cc);
		}
		return new ReturnStatus(true);
	}

	@Override
	public CardPayOnlineSum totalCardPayOnline(String organ, Integer state,
			Date startTime, Date endTime, String type) {
		List<Criteria> criterias = new ArrayList<Criteria>();
		Criteria criteria = new Criteria();
		if("1".equals(type)){//店铺端
			if(!StringUtils.isEmpty(organ)){
				criterias.add(Criteria.where("organId").is(organ));
			}
			if(state!=null){
				criterias.add(Criteria.where("organState").is(state));
			}else{
				criterias.add(Criteria.where("organState").in(0,1));
			}
			
		}else if("2".equals(type)){//平台端
			if(!StringUtils.isEmpty(organ)){
				criterias.add(Criteria.where("organId").in(Arrays.asList(organ.split(","))));
			}
			if(state!=null){
				criterias.add(Criteria.where("state").is(state));
			}else{
				criterias.add(Criteria.where("state").in(0,1));
			}
		}
		if(startTime!=null){
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if(endTime!=null){
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Aggregation aggregation=Aggregation.newAggregation(Aggregation.match(criteria),Aggregation.group("organId").sum("money1").as("total"));
		AggregationResults<CardPayOnlineSum> ar = mongoTemplate.aggregate(aggregation, WeUserCardCharge.class,
				CardPayOnlineSum.class);
		CardPayOnlineSum cos = new CardPayOnlineSum();
		List<CardPayOnlineSum> list = ar.getMappedResults();//得到查到的集合
		for(CardPayOnlineSum s:list){//循环加钱
			cos.setTotal(cos.getTotal()+s.getTotal());
		}
		return cos;
	}

	@Override
	public FlipInfo<Organ> queryOrgan(String city, String distirct, String region,
			String name,FlipInfo<Organ> fpi) {
		List<Criteria> criterias =new  ArrayList<Criteria>();
		if(!StringUtils.isEmpty(city)){
			WeBCity c= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(city)), WeBCity.class);
			criterias.add(Criteria.where("city").is(c.getName()));
		}
		if(!StringUtils.isEmpty(distirct)){
			WeBDistrict d = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(distirct)), WeBDistrict.class);
			criterias.add(Criteria.where("district").is(d.getName()));
		}
		if(!StringUtils.isEmpty(region)){
			WeBRegion r = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(region)), WeBRegion.class);
			criterias.add(Criteria.where("region").is(r.getName()));
		}
		if(!StringUtils.isEmpty(name)){
			criterias.add(Criteria.where("name").regex(name));
		}
		if(criterias.size()>0){
			Criteria criteria =new Criteria();
			criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
			fpi = mongoTemplate.findByPage(Query.query(criteria), fpi, Organ.class);
		}else{
			fpi = mongoTemplate.findByPage(null, fpi, Organ.class);
		}
		
		return fpi;
	}

	@Override
	public FlipInfo<Organ> queryOrganCardNum(FlipInfo<Organ> fpi) {
		String organIds="";
		for(Organ organ:fpi.getData()){
			organIds+=organ.get_id()+",";
		}
		if(organIds.length()>0){
			organIds=organIds.substring(0,organIds.length()-1);
			Criteria c1 = new Criteria();
			//查询没有被删除的
			c1.andOperator(Criteria.where("organId").in(Arrays.asList(organIds.split(","))),Criteria.where("delete_flag").is(false));
			Criteria c2 = new Criteria();
			//查询被删除的
			c2.andOperator(Criteria.where("organId").in(Arrays.asList(organIds.split(","))),Criteria.where("delete_flag").is(true));
			Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(c1) ,Aggregation.group("organId").count().as("num"));
			AggregationResults<OrganCardNum> rs=mongoTemplate.aggregate(aggregation, Userincard.class, OrganCardNum.class);
			List<OrganCardNum> srList=rs.getMappedResults();
			for(Organ organ:fpi.getData()){
				for(OrganCardNum cardNum:srList){
					if(organ.get_id().equals(cardNum.get_id())){
						organ.setNum(cardNum.getNum());
						break;
					}
				}
			}
			
			aggregation = Aggregation.newAggregation(Aggregation.match(c2) ,Aggregation.group("organId").count().as("delNum"));
		    rs=mongoTemplate.aggregate(aggregation, Userincard.class, OrganCardNum.class);
			srList=rs.getMappedResults();
			for(Organ organ:fpi.getData()){
				for(OrganCardNum cardNum:srList){
					if(organ.get_id().equals(cardNum.get_id())){
						organ.setDelNum(cardNum.getDelNum());
						break;
					}
				}
			}
		}
		return fpi;
	}

}
