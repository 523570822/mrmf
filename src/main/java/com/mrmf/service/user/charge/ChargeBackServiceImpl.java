package com.mrmf.service.user.charge;

import java.util.Arrays;
import java.util.List;

import com.mrmf.entity.user.Kaidan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.user.userpart.UserpartService;
import com.mrmf.service.waimai.WaimaiService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

@Service("chargeBackService")
public class ChargeBackServiceImpl implements ChargeBackService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private UserpartService userpartService;

	@Autowired
	private WaimaiService waimaiService;

	@Override
	public List<Userpart> queryUserPartUser(String xiaopiao,String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("type").is(0), Criteria.where("incardId").is(""),
				Criteria.where("xiaopiao").is(xiaopiao),Criteria.where("organId").is(organId));
		List<Userpart> list = mongoTemplate.find(Query.query(criteria), Userpart.class);
		userpartService.getDetails(list);
		return list;
	}

	@Override
	public List<Userpart> queryUserPartVipUser(String xiaopiao,String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("type").is(1), Criteria.where("usersortType").in("1002", "1003"),
				Criteria.where("xiaopiao").is(xiaopiao),Criteria.where("organId").is(organId));
		List<Userpart> list = mongoTemplate.find(Query.query(criteria), Userpart.class);
		userpartService.getDetails(list);
		return list;
	}

	@Override
	public List<WWaimai> queryWaimai(String xiaopiao,String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("xiaopiao").is(xiaopiao),Criteria.where("organId").is(organId));
		return mongoTemplate.find(Query.query(criteria), WWaimai.class);
	}

	@Override
	public List<Userpart> queryInCard(String xiaopiao,String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("xiaopiao").is(xiaopiao), Criteria.where("type").is(11),Criteria.where("organId").is(organId));
		List<Userpart> list = mongoTemplate.find(Query.query(criteria), Userpart.class);
		userpartService.getDetails(list);
		return list;
	}

	@Override
	public List<Userpart> querySaleCard(String xiaopiao,String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("xiaopiao").is(xiaopiao), Criteria.where("usersortType").is("1001"),Criteria.where("organId").is(organId));
		List<Userpart> list = mongoTemplate.find(Query.query(criteria), Userpart.class);
		userpartService.getDetails(list);
		return list;
	}

	@Override
	public ReturnStatus chargeWaiMai(String[] ids) {
		List<WWaimai> waimais = mongoTemplate.find(Query.query(Criteria.where("_id").in(Arrays.asList(ids))),
				WWaimai.class);
		if (waimais != null && waimais.size() > 0) {
			for (WWaimai waimai : waimais) {
				// 恢复库存数量
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(waimai.getOrganId()),
						Criteria.where("wupinId").is(waimai.getWupinId()));
				WStoreroom store = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
				if (store != null) {
					store.setNum(store.getNum() + waimai.getNum());
					mongoTemplate.save(store);
				}
				waimai.setUpdateTime(DateUtil.currentDate());
				mongoTemplate.save(waimai, "wwaimai2");
				mongoTemplate.remove(waimai);
				Userincard userincard = mongoTemplate.findById(waimai.getKaidanId(),Userincard.class);
				userincard.setMoney4(userincard.getMoney4()+waimai.getMoney1());
				mongoTemplate.save(userincard);
				mongoTemplate.updateFirst(Query.query(Criteria.where("incardId").is(userincard.get_id()).and("type").is(0)), Update.update("nowMoney4",userincard.getMoney4()),Userpart.class);
			}
			return new ReturnStatus(true);
		} else {
			return new ReturnStatus(false, "该记录不存在");
		}
	}

	@Override
	public ReturnStatus chargeUserPart(String[] ids) {
		List<Userpart> userParts = mongoTemplate.find(Query.query(Criteria.where("_id").in(Arrays.asList(ids))),
				Userpart.class);
		if (userParts != null && userParts.size() > 0) {
			for (Userpart userpart : userParts) {
				List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(userpart.getIncardId())),Userpart.class);
				Userpart oldUserPart = userpartList.get(0);
				// 恢复会员卡
				if (userpart.getType() == 1) { // 主卡消费退单
					Userincard incard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
					if (incard != null) {
						if ("1002".equals(userpart.getUsersortType())) {// 存钱打折卡
							incard.setMoney4(incard.getMoney4() + userpart.getMoney_xiaofei() - userpart.getMoney5());
							//更新type=0 nowMoney4
							oldUserPart.setNowMoney4(oldUserPart.getNowMoney4()+userpart.getMoney_xiaofei() - userpart.getMoney5());
							mongoTemplate.save(oldUserPart);
							mongoTemplate.save(incard);
						} else if ("1003".equals(userpart.getUsersortType())) {// 次数卡
							incard.setShengcishu(incard.getShengcishu() + userpart.getCishu());
							incard.setMoney4(incard.getShengcishu() * incard.getDanci_money());
							//更新type=0 次数
							oldUserPart.setShengcishu(oldUserPart.getShengcishu()+userpart.getCishu());
							oldUserPart.setNowMoney4(oldUserPart.getShengcishu()*oldUserPart.getDanci_money());
							mongoTemplate.save(oldUserPart);
							mongoTemplate.save(incard);
						}
					}
				} else if (userpart.getType() == 11) { // 子卡消费退单
					Userinincard inincard = mongoTemplate.findById(userpart.getInincardId(), Userinincard.class);
					if (inincard != null) {
						inincard.setShengcishu(inincard.getShengcishu() + userpart.getCishu());
						mongoTemplate.save(inincard);
					}
				}

				userpart.setUpdateTime(DateUtil.currentDate());
				mongoTemplate.save(userpart, "userpart2");
				String kdId = userpart.getKaidanId();
				mongoTemplate.remove(userpart);
				//删除手动开单
				if(!StringUtils.isEmpty(kdId)){
					Long num = mongoTemplate.count(Query.query(Criteria.where("kaidanId").is(kdId)),Userpart.class);
					if(num==0){
						Kaidan kd = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(kdId)), Kaidan.class);
						mongoTemplate.save(kd, "kaidan2");
						mongoTemplate.remove(kd);
					}
				}

			}
			return new ReturnStatus(true);
		} else {
			return new ReturnStatus(false, "该记录不存在");
		}
	}

	@Override
	public FlipInfo<Userpart> queryUserpart(FlipInfo<Userpart> fpi) throws BaseException {
		String condition = (String) fpi.getParams().get("condition");
		Criteria criteria = null;
		if (!StringUtils.isEmpty(condition)) {
			criteria = new Criteria();
			criteria.orOperator(Criteria.where("phone").regex(condition), Criteria.where("name").regex(condition),
					Criteria.where("cardno").regex(condition), Criteria.where("id_2").regex(condition));
			fpi.getParams().put("condition", null);
		}
		fpi = mongoTemplate.findByPage(null, criteria, fpi, Userpart.class, "userpart2");
		userpartService.getDetails(fpi.getData());
		return fpi;
	}

	@Override
	public FlipInfo<WWaimai> queryWaimai(FlipInfo<WWaimai> fpi) throws BaseException {
		fpi = mongoTemplate.findByPage(null, fpi, WWaimai.class, "wwaimai2");
		waimaiService.getDetails(fpi.getData());
		return fpi;
	}
}
