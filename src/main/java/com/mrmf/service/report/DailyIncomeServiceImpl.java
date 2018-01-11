package com.mrmf.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.waimai.WaimaiService;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;

@Service("dailyIncomeService")
public class DailyIncomeServiceImpl implements DailyIncomeService {

	@Autowired
	private EMongoTemplate mongoTemplate;
	@Autowired
	private WaimaiService waimaiService;
	private static Logger logger = Logger.getLogger(DailyIncomeServiceImpl.class);
	@Override
	public List<Userpart> queryUser(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("type").is(0));
		criterias.add(Criteria.where("incardId").is(""));

		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);
		return resultList;
	}

	@Override
	public List<Userpart> queryUserCard(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		List<Integer> types = new ArrayList<>();
		types.add(1); // 会员卡消费记录
		types.add(11); // 子卡消费记录
		criterias.add(Criteria.where("type").in(types));

		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);



//			for (int i = 0; i <resultList.size() ; i++) {
//                try {
//                    System.out.println("resultList第"+i+"次");
//                    String jsonString = JSON.toJSONString(resultList.get(i));
//                    System.out.println(jsonString);
//                } catch (Exception e) {
//                    System.out.println("resultList第"+i+"次报错了啊啊啊啊啊");
//                    Userpart userpart = resultList.get(i);
//                    System.out.println("resultList的单个ID"+userpart.get_id()+"当前卡类型"+userpart.getType()+"服务项目"+userpart.getSmallsort()+"somemoney1是"+userpart.getSomemoney1()+
//                    "id_2是"+userpart.getId_2()+"IncardId是"+userpart.getIncardId()+"InincatdId是"+userpart.getInincardId());
//                    e.printStackTrace();
//                }
//            }


		return resultList;
	}
	@Override
	public List<Userpart> queryUserCardZK(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		List<Integer> types = new ArrayList<>();
		types.add(1); // 会员卡消费记录
		types.add(11); // 子卡消费记录
		criterias.add(Criteria.where("type").in(types));
		criterias.add(Criteria.where("usersortType").is("1001"));

		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);
		return resultList;
	}
	@Override
	public List<Userpart> queryUserCardNew(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("type").is(0));
		criterias.add(Criteria.where("incardId").ne(""));

		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);
		return resultList;
	}

	@Override
	public List<Userpart> queryUserCardXufei(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("type").is(3));

		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);
		return resultList;
	}

	private List<Userpart> queryBy(List<Criteria> criterias, String organId, Date startTime, Date endTime)
			throws BaseException {
		if (criterias == null)
			criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
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

	@Override
	public List<Userpart> queryZengsong(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("song_money").gt(0));
		criterias.add(Criteria.where("type").in(0,3));
		List<Userpart> resultList = queryBy(criterias, organId, startTime, endTime);
		return resultList;
	}

	@Override
	public List<WWaimai> queryWaimai(String organId, Date startTime, Date endTime) throws BaseException {
		List<Criteria> criterias = new ArrayList<>();
		Criteria criteria = new Criteria();
		criterias.add(Criteria.where("organId").is(organId));
		criterias.add(Criteria.where("delete_flag").is(false));
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		List<WWaimai> resultList = mongoTemplate
				.find(Query.query(criteria.andOperator(criterias.toArray(new Criteria[criterias.size()])))
						.with(new Sort(Direction.DESC, "createTime")), WWaimai.class);
		return waimaiService.getDetails(resultList);
	}

}
