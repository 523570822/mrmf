package com.mrmf.service.waimai;

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
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.kucun.WWupin;
import com.mrmf.service.common.Arith;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.user.userpart.UserpartService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("waimaiService")
public class WaimaiServiceImpl implements WaimaiService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private OrganService organService;

	@Autowired
	private UserpartService userpartService;

	@Override
	public FlipInfo<WWaimai> query(FlipInfo<WWaimai> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, WWaimai.class);
		getDetails(fpi.getData());
		return fpi;
	}

	@Override
	public List<WWaimai> query(String kaidanId) throws BaseException {
		if (!StringUtils.isEmpty(kaidanId)) {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("kaidanId").is(kaidanId), Criteria.where("delete_flag").is(false),
					Criteria.where("guazhang_flag").is(true)); // 非删除，挂账未结账记录
			List<WWaimai> dataList = mongoTemplate.find(Query.query(criteria), WWaimai.class);
			getDetails(dataList);
			return dataList;
		} else {
			return new ArrayList<WWaimai>();
		}
	}

	@Override
	public List<WWaimai> queryAll(String kaidanId) throws BaseException {
		if (!StringUtils.isEmpty(kaidanId)) {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("kaidanId").is(kaidanId), Criteria.where("delete_flag").is(false)); // 非删除
			List<WWaimai> dataList = mongoTemplate.find(Query.query(criteria), WWaimai.class);
			getDetails(dataList);
			return dataList;
		} else {
			return new ArrayList<WWaimai>();
		}
	}

	@Override
	public List<WWaimai> getDetails(List<WWaimai> dataList) {
		List<String> waimaiIds = new ArrayList<String>();
		for (WWaimai waimai : dataList) {
			if (!waimaiIds.contains(waimai.getWupinId())) {
				waimaiIds.add(waimai.getWupinId());
			}
		}

		if (waimaiIds.size() > 0) {
			Map<String, WWupin> wupinMap = new HashMap<String, WWupin>();
			List<WWupin> wupins = mongoTemplate.find(Query.query(Criteria.where("_id").in(waimaiIds)), WWupin.class);
			for (WWupin wupin : wupins) {
				wupinMap.put(wupin.get_id(), wupin);
			}

			for (WWaimai waimai : dataList) {
				WWupin wupin = wupinMap.get(waimai.getWupinId());
				if (wupin != null) {
					waimai.setWupinName(wupin.getMingcheng());
					waimai.setGuige(wupin.getGuige());
					waimai.setWupinCode(wupin.getCode());
					if(waimai.getIsCard()){
						waimai.setIsCardName("是");
					}else {
						waimai.setIsCardName("否");
					}
				}
			}
		}
		return dataList;
	}

	@Override
	public ReturnStatus upsert(WWaimai waimai) {
		String organId = waimai.getOrganId();
		if (StringUtils.isEmpty(organId)) {
			return new ReturnStatus(false, "公司id不能为空");
		}
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null) {
			return new ReturnStatus(false, "指定id的公司信息不存在");
		}
		String wupinId = waimai.getWupinId();
		if (StringUtils.isEmpty(wupinId)) {
			return new ReturnStatus(false, "物品id不能为空");
		}
		WWupin wupin = mongoTemplate.findById(wupinId, WWupin.class);
		if (wupin == null) {
			return new ReturnStatus(false, "指定id的物品信息不存在");
		}

		// 计算使用量的价格，按出货价格计算
		double p = waimai.getMoney2();
		double num = waimai.getNum();
		double amount = p * waimai.getNum() * waimai.getZhekou() / 100;
		waimai.setMoney1(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

		// 更新库存
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("wupinId").is(wupinId));
		WStoreroom storeroom = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
		if (storeroom == null || storeroom.getNum() < num) {
			return new ReturnStatus(false, "库存不足!");
		}

		// 生成出货信息
		WOutstoreroom outstoreroom = null;
		if (StringUtils.isEmpty(waimai.get_id())) { // 新增
			waimai.setIdIfNew();

			storeroom.setNum(storeroom.getNum() - num);

			outstoreroom = new WOutstoreroom();
			outstoreroom.setIdIfNew();
			outstoreroom.setParentOrganId(storeroom.getParentOrganId());
			outstoreroom.setOrganId(storeroom.getOrganId());
			// outstoreroom.setCode(wupin.getCode());
			outstoreroom.setWupinId(storeroom.getWupinId());
			outstoreroom.setMingcheng(storeroom.getMingcheng());
			outstoreroom.setPrice(storeroom.getPrice());
			outstoreroom.setNum(num);
			outstoreroom.setPrice_all(Arith.mul(num, storeroom.getPrice()));
			outstoreroom.setWeight(storeroom.getWeight());
			outstoreroom.setWeight_all(Arith.mul(num, storeroom.getWeight()));
			outstoreroom.setBumen(storeroom.getBumen());
			outstoreroom.setCreateTimeIfNew();
			outstoreroom.setFlag(true); // 外卖出库
			outstoreroom.setDelete_flag(false);
			outstoreroom.setGuazhang_flag(true);
			outstoreroom.setShenhe(true); // 默认审核通过
			outstoreroom.setShenhe_fen(false);
			outstoreroom.setDanhao(waimai.get_id());
			outstoreroom.setKaidanId(waimai.getKaidanId()); // 关联开单id
		} else { // 修改
			WWaimai oldWaimai = mongoTemplate.findById(waimai.get_id(), WWaimai.class);
			if (oldWaimai == null)
				return new ReturnStatus(false, "要修改的外卖信息不存在!");
			outstoreroom = mongoTemplate.findOne(Query.query(Criteria.where("danhao").is(waimai.get_id())),
					WOutstoreroom.class);
			if (outstoreroom != null) {
				storeroom.setNum(storeroom.getNum() + outstoreroom.getNum() - num);

				outstoreroom.setNum(num);
				outstoreroom.setPrice_all(Arith.mul(num, storeroom.getPrice()));
				outstoreroom.setWeight_all(Arith.mul(num, storeroom.getWeight()));
			} else {
				return new ReturnStatus(false, "关联的出库信息不存在!");
			}
		}

		mongoTemplate.save(storeroom);
		if (outstoreroom != null)
			mongoTemplate.save(outstoreroom);

		waimai.setCreateTimeIfNew();

		mongoTemplate.save(waimai);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(waimai);
		return status;
	}

	@Override
	public ReturnStatus remove(String id) {
		WWaimai waimai = mongoTemplate.findById(id, WWaimai.class);
		if (waimai != null) {
			waimai.setDelete_flag(true);

			// 是否有关联出库信息
			WOutstoreroom outstoreroom = mongoTemplate
					.findOne(Query.query(Criteria.where("danhao").is(waimai.get_id())), WOutstoreroom.class);
			if (outstoreroom != null) { // 有则恢复库存
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(waimai.getOrganId()),
						Criteria.where("wupinId").is(waimai.getWupinId()));
				WStoreroom storeroom = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
				if (storeroom == null) {
					return new ReturnStatus(false, "库存信息缺失");
				}
				storeroom.setNum(storeroom.getNum() + outstoreroom.getNum()); // 恢复库存数量

				outstoreroom.setDelete_flag(true); // 设置删除标记

				mongoTemplate.save(storeroom);
				mongoTemplate.save(outstoreroom);
			}
			mongoTemplate.save(waimai);
		}
		return new ReturnStatus(true);
	}

	@Override
	public List<WWaimai> queryDinggou(String organId) throws BaseException {
		if (!StringUtils.isEmpty(organId)) {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("delete_flag").is(false),
					Criteria.where("guazhang_flag").is(false), Criteria.where("flag").is(false),
					Criteria.where("money_qian").gt(0));
			List<WWaimai> dataList = mongoTemplate.find(Query.query(criteria), WWaimai.class);
			getDetails(dataList);
			return dataList;
		} else {
			return new ArrayList<WWaimai>();
		}
	}

	@Override
	public ReturnStatus handleDingou(String waimaiId) {
		if (!StringUtils.isEmpty(waimaiId)) {
			WWaimai waimai = mongoTemplate.findById(waimaiId, WWaimai.class);
			if (waimai == null) {
				return new ReturnStatus(false, "指定id的外卖信息不存在");
			} else {
				waimai.setFlag(true);
				mongoTemplate.save(waimai);
				return new ReturnStatus(true);
			}
		} else {
			return new ReturnStatus(false, "外卖id不能为空");
		}
	}

	@Override
	public List<WWaimai> queryByIds(String xiaopiao, List<String> ids) throws BaseException {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<>();
		}

		List<WWaimai> waimais = getDetails(
				mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), WWaimai.class));
		String organId = null;

		boolean xp = false;
		for (WWaimai waimai : waimais) {
			if (organId == null) {
				organId = waimai.getOrganId();
			}
			if (StringUtils.isEmpty(waimai.getXiaopiao()))
				xp = true;
		}

		if (xp && xiaopiao == null) {
			xiaopiao = userpartService.getXiaopiaoCode(organId, ids);
		}

		if (xiaopiao != null) {
			for (WWaimai waimai : waimais) {
				if (StringUtils.isEmpty(waimai.getXiaopiao())) {
					waimai.setXiaopiao(xiaopiao);
					mongoTemplate.save(waimai);
				}
			}
		}

		return waimais;
	}
}
