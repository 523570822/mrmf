package com.mrmf.service.user.weuserpay;

import com.mrmf.entity.*;
import com.mrmf.entity.bean.UserPayFenzhangSum;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("weUserPayFenzhangService")
public class WeUserPayFenzhangServiceImpl implements WeUserPayFenzhangService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public WeUserPayFenzhang queryById(String fenzhangId) throws BaseException {
		return mongoTemplate.findById(fenzhangId, WeUserPayFenzhang.class);
	}

	@Override
	public FlipInfo<WeUserPayFenzhang> query(FlipInfo<WeUserPayFenzhang> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		mongoTemplate.findByPage(null, fpi, WeUserPayFenzhang.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<WeUserPayFenzhang> datas) {
		List<String> usersortIds = new ArrayList<String>();
		for (WeUserPayFenzhang usersort : datas) {
			if (!usersortIds.contains(usersort.getUserId()))
				usersortIds.add(usersort.getUserId());
		}

		Map<String, User> usersortTypeMap = new HashMap<>();
		List<User> bigsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(usersortIds)), User.class);
		for (User bigsort : bigsorts) {
			usersortTypeMap.put(bigsort.get_id(), bigsort);
		}

		for (WeUserPayFenzhang usersort : datas) {
			User code = usersortTypeMap.get(usersort.getUserId());
			if (code != null) {
				if (!TextUtils.isEmpty(code.getName()))
					usersort.setUserName(code.getName());
				else
					usersort.setUserName(code.getNick());
			}

		}
	}

	@Override
	public Userpart handleFenzhangEnter(String fenzhangId) throws BaseException {
		WeUserPayFenzhang fenzhang = mongoTemplate.findById(fenzhangId, WeUserPayFenzhang.class);
		if (fenzhang == null) {
			throw new BaseException("分账记录不存在！");
		}
		WeUserPayOrder payOrder = mongoTemplate.findById(fenzhang.getOrderId(), WeUserPayOrder.class);
		if (payOrder == null) {
			throw new BaseException("微信支付订单记录不存在！");
		}

		User user = mongoTemplate.findById(fenzhang.getUserId(), User.class);
		if (user == null) {
			throw new BaseException("用户信息不存在！");
		}

		WeOrganOrder organOrder = null;
		if (!StringUtils.isEmpty(payOrder.getOrganOrderId())) {
			organOrder = mongoTemplate.findById(payOrder.getOrganOrderId(), WeOrganOrder.class);
			if (organOrder == null) {
				throw new BaseException("微信预约信息不存在！");
			}
		}
		Usercard card = mongoTemplate.findById(fenzhang.getSysCardId(), Usercard.class);
		if (card == null) {
			Userpart userpart = new Userpart();
			userpart.setUserId(user.get_id());
			userpart.setMoney1(fenzhang.getOrganAmount()); // 应交
			userpart.setMoney2(fenzhang.getOrganAmount()); // 实际缴费
			userpart.setOrganId(fenzhang.getOrganId());
			userpart.setFenzhangId(fenzhang.get_id());
			userpart.setMoney_xiaofei(fenzhang.getOrganAmount());
			if (organOrder != null) {
				userpart.setStaffId1(organOrder.getStaffId()); // 预约的技师自动带出来
			}
			return userpart;
		}
		// 初始化Userpart消费记录
		Userpart userpart = new Userpart();
		userpart.setUserId(user.get_id());
		userpart.setMoney1(fenzhang.getOrganAmount()); // 应交
		userpart.setMoney2(fenzhang.getOrganAmount()); // 实际缴费
		userpart.setOrganId(fenzhang.getOrganId());
		userpart.setFenzhangId(fenzhang.get_id());
		userpart.setCardId(card.get_id());
		userpart.setMoney_xiaofei(fenzhang.getOrganAmount());
		if (organOrder != null) {
			userpart.setStaffId1(organOrder.getStaffId()); // 预约的技师自动带出来
		}

		return userpart;
	}

	@Override
	public ReturnStatus handleFenzhang(Userpart userpart) {
		WeUserPayFenzhang fenzhang = mongoTemplate.findById(userpart.getFenzhangId(), WeUserPayFenzhang.class);
		if (fenzhang == null) {
			return new ReturnStatus(false, "微信分账信息不存在！");
		}

		String staffId = userpart.getStaffId1();
		if (!StringUtils.isEmpty(staffId)) { // 生成技师做活信息记录
			Staff staff = mongoTemplate.findById(staffId, Staff.class);
			if (staff == null) {
				return new ReturnStatus(false, "技师信息不存在！");
			}
			WeUserPayOrder weUserPayOrder = mongoTemplate.findById(fenzhang.getOrderId(), WeUserPayOrder.class);
			if (weUserPayOrder == null) {
				return new ReturnStatus(false, "微信订单信息不存在！");
			}
			String smallsortId = userpart.getSmallsort();
			String smallsortName = "微信支付消费";
			if (!StringUtils.isEmpty(smallsortId)) {
				Smallsort ss = mongoTemplate.findById(smallsortId, Smallsort.class);
				if (ss != null) {
					smallsortName = ss.getName();
				}
			}
			Organ organ = mongoTemplate.findById(weUserPayOrder.getOrganId(), Organ.class);
			String organOrderId = weUserPayOrder.getOrganOrderId();
			WeOrganOrder organOrder;
			if (StringUtils.isEmpty(organOrderId)) { // 非微信预约订单，则自动生成微信预约订单信息
				organOrder = new WeOrganOrder();
				organOrder.setNewId();
				organOrder.setNewCreate();
				organOrder.setUserId(weUserPayOrder.getUserId());
				organOrder.setOrganId(weUserPayOrder.getOrganId());
				organOrder.setStaffId(staffId);
				// organOrder.setOrderTime();
				organOrder.setOrderPrice(weUserPayOrder.getPrice());
				organOrder.setType(2); // 预约技师类型
				organOrder.setOrderService(smallsortId);
				organOrder.setState(3); // 已支付状态
				organOrder.setTitle(smallsortName);
				organOrder.setStaffName(staff.getName());
				if (organ != null) {
					organOrder.setOrganName(organ.getName());
				}
			} else {
				organOrder = mongoTemplate.findById(organOrderId, WeOrganOrder.class);
				if (organOrder == null) {
					return new ReturnStatus(false, "订单信息不存在！");
				}
				organOrder.setStaffId(staffId);
			}
			mongoTemplate.save(organOrder);
		}
		userpart.setIdIfNew();
		userpart.setCreateTimeIfNew();
		userpart.setFlag2(true); // 是否交款
		userpart.setGuazhang_flag(false); // 是否挂账
		userpart.setDelete_flag(false);

		userpart.setType(2); // 微信分账消费

		fenzhang.setState(1); // 已处理状态

		mongoTemplate.save(userpart);
		mongoTemplate.save(fenzhang);
		return new ReturnStatus(true);
	}

	@Override
	public UserPayFenzhangSum totalOrgan(String organId, Integer state, Date startTime, Date endTime)
			throws BaseException {
		Criteria criteria = new Criteria();
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("organId").is(organId));
		if (state != null) {
			criterias.add(Criteria.where("state").is(state));
		}
		if (startTime != null) {
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if (endTime != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]))),
				Aggregation.group("organId").sum("userAmount").as("totalUser").sum("sysAmount").as("totalSys")
						.sum("organAmount").as("totalOrgan").sum("price").as("total"));

		AggregationResults<UserPayFenzhangSum> ar = mongoTemplate.aggregate(aggregation, WeUserPayFenzhang.class,
				UserPayFenzhangSum.class);

		criteria = new Criteria();
		criteria.andOperator(Criteria.where("userId").is("0"), Criteria.where("organId").is(organId));
		Usercard usercard = mongoTemplate.findOne(Query.query(criteria), Usercard.class);

		UserPayFenzhangSum fs;
		List<UserPayFenzhangSum> list = ar.getMappedResults();
		if (list.size() > 0) {
			fs = list.get(0);
		} else {
			fs = new UserPayFenzhangSum();
		}
		if (usercard != null) {
			fs.setCardRest(usercard.getMoney4());
		}
		return fs;
	}

}
