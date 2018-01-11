package com.mrmf.service.usewupin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganSetting;
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WUsewupin;
import com.mrmf.entity.kucun.WWupin;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.Entity;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("usewupinService")
public class UsewupinServiceImpl implements UsewupinService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private OrganService organService;

	@Override
	public List<WUsewupin> query(String userpartId) throws BaseException {
		if (!StringUtils.isEmpty(userpartId)) {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("userpartId").is(userpartId), Criteria.where("delete_flag").is(false)); // 非删除记录
			List<WUsewupin> dataList = mongoTemplate.find(Query.query(criteria), WUsewupin.class);
			getDetails(dataList);
			return dataList;
		} else {
			return new ArrayList<WUsewupin>();
		}
	}

	private void getDetails(List<WUsewupin> dataList) {
		List<String> wupinIds = new ArrayList<String>();
		for (WUsewupin usewupin : dataList) {
			if (!wupinIds.contains(usewupin.getWupinId())) {
				wupinIds.add(usewupin.getWupinId());
			}
		}

		if (wupinIds.size() > 0) {
			Map<String, WWupin> wupinMap = new HashMap<String, WWupin>();
			List<WWupin> wupins = mongoTemplate.find(Query.query(Criteria.where("_id").in(wupinIds)), WWupin.class);
			for (WWupin wupin : wupins) {
				wupinMap.put(wupin.get_id(), wupin);
			}

			for (WUsewupin usewupin : dataList) {
				WWupin wupin = wupinMap.get(usewupin.getWupinId());
				if (wupin != null) {
					usewupin.setWupinName(wupin.getMingcheng());
				}
			}
		}

	}

	@Override
	public ReturnStatus upsert(WUsewupin usewupin) {
		String organId = usewupin.getOrganId();
		if (StringUtils.isEmpty(organId)) {
			return new ReturnStatus(false, "公司id不能为空");
		}
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null) {
			return new ReturnStatus(false, "指定id的公司信息不存在");
		}
		String wupinId = usewupin.getWupinId();
		if (StringUtils.isEmpty(wupinId)) {
			return new ReturnStatus(false, "物品id不能为空");
		}
		WWupin wupin = mongoTemplate.findById(wupinId, WWupin.class);
		if (wupin == null) {
			return new ReturnStatus(false, "指定id的物品信息不存在");
		}

		// 计算使用量的价格，按出货价格计算
		double p = wupin.getPrice_ch();
		double amount = p * usewupin.getYongliang();
		usewupin.setMoney1(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

		if (StringUtils.isEmpty(usewupin.getUserpartId())) { // 如不存在消费id，则生成一个
			usewupin.setUserpartId(Entity.getLongUUID());
		}

		// 是否更新库存
		boolean deductKucun = false;
		try {
			OrganSetting organSetting = organService.querySetting(organId);
			deductKucun = organSetting.getChanpinDeductKucun();
		} catch (BaseException e) {
			// ignore it
		}

		if (deductKucun) { // 更新库存
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("wupinId").is(wupinId));
			WStoreroom storeroom = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
			if (storeroom == null || storeroom.getNum() < amount) {
				return new ReturnStatus(false, "库存不足!");
			}
		}

		usewupin.setIdIfNew();
		usewupin.setCreateTimeIfNew();

		mongoTemplate.save(usewupin);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(usewupin);
		return status;
	}

	@Override
	public ReturnStatus remove(String id) {
		WUsewupin usewupin = mongoTemplate.findById(id, WUsewupin.class);
		if (usewupin != null) {
			usewupin.setDelete_flag(true);

			// 是否有关联出库信息
			WOutstoreroom outstoreroom = mongoTemplate
					.findOne(Query.query(Criteria.where("danhao").is(usewupin.get_id())), WOutstoreroom.class);
			if (outstoreroom != null) { // 有则恢复库存
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(usewupin.getOrganId()),
						Criteria.where("wupinId").is(usewupin.getWupinId()));
				WStoreroom storeroom = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
				if (storeroom == null) {
					return new ReturnStatus(false, "库存信息缺失");
				}
				storeroom.setNum(storeroom.getNum() + outstoreroom.getNum()); // 恢复库存数量

				outstoreroom.setDelete_flag(true); // 设置删除标记

				mongoTemplate.save(storeroom);
				mongoTemplate.save(outstoreroom);
			}
			mongoTemplate.save(usewupin);
		}
		return new ReturnStatus(true);
	}
}
