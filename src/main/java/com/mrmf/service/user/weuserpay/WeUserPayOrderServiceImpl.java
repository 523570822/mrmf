package com.mrmf.service.user.weuserpay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.User;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeUserPayOrder;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("weUserPayOrderService")
public class WeUserPayOrderServiceImpl implements WeUserPayOrderService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<WeUserPayOrder> query(FlipInfo<WeUserPayOrder> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		mongoTemplate.findByPage(Query.query(Criteria.where("state").is(1)), fpi, WeUserPayOrder.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<WeUserPayOrder> datas) {
		List<String> orderIds = new ArrayList<String>();
		List<String> usersortIds = new ArrayList<String>();
		for (WeUserPayOrder usersort : datas) {
			if (!usersortIds.contains(usersort.getUserId()))
				usersortIds.add(usersort.getUserId());
			if (!StringUtils.isEmpty(usersort.getOrganOrderId()) && !orderIds.contains(usersort.getOrganOrderId())) {
				orderIds.add(usersort.getOrganOrderId());
			}
		}

		Map<String, User> userMap = new HashMap<>();
		List<User> bigsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(usersortIds)), User.class);
		for (User bigsort : bigsorts) {
			userMap.put(bigsort.get_id(), bigsort);
		}

		Map<String, WeOrganOrder> orderMap = new HashMap<>();
		if (orderIds.size() > 0) {
			List<WeOrganOrder> smallsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(orderIds)),
					WeOrganOrder.class);
			for (WeOrganOrder smallsort : smallsorts) {
				orderMap.put(smallsort.get_id(), smallsort);
			}
		}

		for (WeUserPayOrder payOrder : datas) {
			User user = userMap.get(payOrder.getUserId());
			if (user != null)
				payOrder.setUserName(StringUtils.isEmpty(user.getName()) ? user.getNick() : user.getName());

			if (!StringUtils.isEmpty(payOrder.getOrganOrderId())) {
				WeOrganOrder smallsort = orderMap.get(payOrder.getOrganOrderId());
				if (smallsort != null) {
					payOrder.setOrganOrderTitle(smallsort.getTitle());
				}
			}
		}
	}

}
