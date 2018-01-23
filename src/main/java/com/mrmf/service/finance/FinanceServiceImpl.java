package com.mrmf.service.finance;

import com.mrmf.entity.*;
import com.mrmf.entity.user.Usercard;
import com.osg.entity.FlipInfo;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
@Service("financeService")
public class FinanceServiceImpl implements FinanceService {
	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<WeUserWalletHis> queryUserVallet(FlipInfo<WeUserWalletHis> fpi) {
		mongoTemplate.findByPage(Query.query(Criteria.where("orderId").ne("2")), fpi, WeUserWalletHis.class);
		return fpi;
	}

	@Override
	public List<WeUserWalletHis> queryUserWalletHisAsExport(String userName, String organId, String startTimeStr,
			String endTimeStr) {
		Criteria criteria = new Criteria();
		Query query = new Query();
		List<Criteria> conditionList = new ArrayList<Criteria>();
		if (!StringUtils.isEmpty(userName)) {
			List<String> userIds = queryUserIds(userName);
			conditionList.add(Criteria.where("userId").in(userIds));
		}
		if (!StringUtils.isEmpty(startTimeStr)) {
			Date startTime = null;
			try {
				startTime = DateUtil.parse(startTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").gte(startTime));
		}
		if (!StringUtils.isEmpty(endTimeStr)) {
			Date endTime = null;
			try {
				endTime = DateUtil.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").lte(endTime));
		}
		conditionList.add(Criteria.where("orderId").ne("2"));
		criteria.andOperator(conditionList.toArray(new Criteria[conditionList.size()]));
		query.addCriteria(criteria);
		// 排序
		List<String> sortFields = new ArrayList<String>();
		sortFields.add("userId");
		Sort sort = new Sort(Direction.ASC, sortFields);
		if (sort != null) {
			query.with(sort);
		}
		List<WeUserWalletHis> weUserWalletHis = mongoTemplate.find(query, WeUserWalletHis.class);
		for (WeUserWalletHis weUserWalletHisTemp : weUserWalletHis) {
			User user = mongoTemplate.findById(weUserWalletHisTemp.getUserId(), User.class);
			if (user != null) {
				weUserWalletHisTemp.setUserName(user.getNick());
				BigDecimal bg = new BigDecimal(weUserWalletHisTemp.getAmount());
				double roundAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				weUserWalletHisTemp.setAmount(roundAmount);
			}
		}
		return weUserWalletHis;
	}

	@Override
	public List<String> queryUserIds(String userName) {
		List<String> userIds = new ArrayList<>();
		List<User> users = mongoTemplate.find(Query.query(Criteria.where("nick").regex(userName)), User.class);
		for (User user : users) {
			userIds.add(user.get_id());
		}
		return userIds;
	}

	@Override
	public List<String> queryStaffIds(String staffName) {
		List<String> staffIds = new ArrayList<>();
		List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("name").regex(staffName)), Staff.class);
		for (Staff staff : staffs) {
			staffIds.add(staff.get_id());
		}
		return staffIds;
	}

	@Override
	public FlipInfo<WeRed> queryStaffWeRed(FlipInfo<WeRed> fpi) {
		mongoTemplate.findByPage(null, fpi, WeRed.class);
		return fpi;
	}

	@Override
	public List<WeRed> queryWeRedsAsExport(String staffName, String startTimeStr, String endTimeStr) {
		Criteria criteria = new Criteria();
		Query query = new Query();
		List<Criteria> conditionList = new ArrayList<Criteria>();
		if (!StringUtils.isEmpty(staffName)) {
			List<String> staffIds = queryStaffIds(staffName);
			conditionList.add(Criteria.where("senderId").in(staffIds));
		}
		if (!StringUtils.isEmpty(startTimeStr)) {
			Date startTime = null;
			try {
				startTime = DateUtil.parse(startTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").gte(startTime));
		}
		if (!StringUtils.isEmpty(endTimeStr)) {
			Date endTime = null;
			try {
				endTime = DateUtil.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").lte(endTime));
		}
		conditionList.add(Criteria.where("senderId").ne("0"));
		criteria.andOperator(conditionList.toArray(new Criteria[conditionList.size()]));
		query.addCriteria(criteria);
		// 排序
		List<String> sortFields = new ArrayList<String>();
		sortFields.add("senderId");
		Sort sort = new Sort(Direction.ASC, sortFields);
		if (sort != null) {
			query.with(sort);
		}
		List<WeRed> weReds = mongoTemplate.find(query, WeRed.class);
		for (WeRed weRed : weReds) {
			Staff staff = mongoTemplate.findById(weRed.getSenderId(), Staff.class);
			Organ organ = mongoTemplate.findById(weRed.getOrganId(), Organ.class);
			if (staff != null && organ != null) {
				weRed.setStaffName(staff.getName());
				weRed.setOrganName(organ.getName());
				BigDecimal bg = new BigDecimal(weRed.getAmount());
				double roundAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				weRed.setAmount(roundAmount);
			}
		}
		return weReds;
	}

	@Override
	public List<String> queryOrganIds(String organName) {
		List<String> organIds = new ArrayList<>();
		List<Organ> organs = mongoTemplate.find(Query.query(Criteria.where("name").regex(organName)), Organ.class);
		for (Organ organ : organs) {
			organIds.add(organ.get_id());
		}
		return organIds;
	}

	@Override
	public FlipInfo<WeSysCardChargeHis> querySysCard(FlipInfo<WeSysCardChargeHis> fpi) {
		mongoTemplate.findByPage(null, fpi, WeSysCardChargeHis.class);
		return fpi;
	}

	@Override
	public List<WeSysCardChargeHis> querySyscardAsExport(String organName, String startTimeStr, String endTimeStr) {
		Criteria criteria = new Criteria();
		Query query = new Query();
		List<Criteria> conditionList = new ArrayList<Criteria>();
		if (!StringUtils.isEmpty(organName)) {
			List<String> organIds = queryOrganIds(organName);
			conditionList.add(Criteria.where("organId").in(organIds));
		}
		if (!StringUtils.isEmpty(startTimeStr)) {
			Date startTime = null;
			try {
				startTime = DateUtil.parse(startTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").gte(startTime));
		}
		if (!StringUtils.isEmpty(endTimeStr)) {
			Date endTime = null;
			try {
				endTime = DateUtil.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").lte(endTime));
		}
		if (conditionList.size() > 0) {
			criteria.andOperator(conditionList.toArray(new Criteria[conditionList.size()]));
			query.addCriteria(criteria);
		}
		// 排序
		List<String> sortFields = new ArrayList<String>();
		sortFields.add("organId");
		Sort sort = new Sort(Direction.ASC, sortFields);
		if (sort != null) {
			query.with(sort);
		}
		List<WeSysCardChargeHis> sysCards = mongoTemplate.find(query, WeSysCardChargeHis.class);
		for (WeSysCardChargeHis sysCard : sysCards) {
			Organ organ = mongoTemplate.findById(sysCard.getOrganId(), Organ.class);
			if (organ != null) {
				sysCard.setOrganName(organ.getName());
				BigDecimal bg = new BigDecimal(sysCard.getAmount());
				double roundAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				sysCard.setAmount(roundAmount);
			}
		}
		return sysCards;
	}

	@Override
	public FlipInfo<WeUserPayFenzhang> queryFenZhang(FlipInfo<WeUserPayFenzhang> fpi) {
		mongoTemplate.findByPage(null, fpi, WeUserPayFenzhang.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<WeUserPayFenzhang> fenzhangs) {
		List<String> userIds = new ArrayList<String>();
		List<String> organIds = new ArrayList<String>();
		List<String> cardIds = new ArrayList<String>();
		for (WeUserPayFenzhang fenzhang : fenzhangs) {
			String userId = fenzhang.getUserId();
			if (!StringUtils.isEmpty(userId) && !userIds.contains(userId))
				userIds.add(userId);
			String organId = fenzhang.getOrganId();
			if (!StringUtils.isEmpty(organId) && !organIds.contains(organId))
				organIds.add(organId);
			String cardId = fenzhang.getSysCardId();
			if (!StringUtils.isEmpty(cardId) && !cardIds.contains(cardId))
				cardIds.add(cardId);
		}

		Map<String, User> userMap = new HashMap<String, User>();
		if (userIds.size() > 0) {
			List<User> users = mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)), User.class);
			for (User user : users) {
				userMap.put(user.get_id(), user);
			}
		}

		Map<String, Organ> organMap = new HashMap<String, Organ>();
		if (organIds.size() > 0) {
			List<Organ> organs = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
			for (Organ organ : organs) {
				organMap.put(organ.get_id(), organ);
			}
		}

		Map<String, Usercard> cardMap = new HashMap<String, Usercard>();
		if (cardIds.size() > 0) {
			List<Usercard> organs = mongoTemplate.find(Query.query(Criteria.where("_id").in(cardIds)), Usercard.class);
			for (Usercard card : organs) {
				cardMap.put(card.get_id(), card);
			}
		}

		for (WeUserPayFenzhang fenzhang : fenzhangs) {
			User user = userMap.get(fenzhang.getUserId());
			if (user != null)
				fenzhang.setUserName(StringUtils.isEmpty(user.getName()) ? user.getNick() : user.getName());
			Organ organ = organMap.get(fenzhang.getOrganId());
			if (organ != null)
				fenzhang.setOrganName(organ.getName());
			Usercard card = cardMap.get(fenzhang.getSysCardId());
			boolean updated = false;
			// 兼容老数据，获取店铺平台卡余额
			if (card != null && fenzhang.getCardMoney4() == 0) {
				fenzhang.setCardMoney4(card.getMoney4());
				updated = true;
			}
			// 兼容老数据，获取店铺平台卡消费次数
			if (fenzhang.getCardCount() == 0) {
				long count = mongoTemplate.count(Query.query(Criteria.where("userId").is(fenzhang.getUserId()).and("cardCount").ne(0)),
						WeUserPayFenzhang.class) + 1;
				fenzhang.setCardCount((int) count);
				updated = true;
			}
			if (updated) {
				mongoTemplate.save(fenzhang);
			}
		}
	}

	@Override
	public List<WeUserPayFenzhang> queryFenZhangAsExport(String userName, String startTimeStr, String endTimeStr) {
		Criteria criteria = new Criteria();
		Query query = new Query();
		List<Criteria> conditionList = new ArrayList<Criteria>();
		if (!StringUtils.isEmpty(userName)) {
			List<String> userIds = queryUserIds(userName);
			conditionList.add(Criteria.where("userId").in(userIds));
		}
		if (!StringUtils.isEmpty(startTimeStr)) {
			Date startTime = null;
			try {
				startTime = DateUtil.parse(startTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").gte(startTime));
		}
		if (!StringUtils.isEmpty(endTimeStr)) {
			Date endTime = null;
			try {
				endTime = DateUtil.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conditionList.add(Criteria.where("createTime").lte(endTime));
		}
		if (conditionList.size() > 0) {
			criteria.andOperator(conditionList.toArray(new Criteria[conditionList.size()]));
			query.addCriteria(criteria);
		}
		// 排序
		List<String> sortFields = new ArrayList<String>();
		sortFields.add("userId");
		Sort sort = new Sort(Direction.ASC, sortFields);
		if (sort != null) {
			query.with(sort);
		}
		List<WeUserPayFenzhang> fenzhangs = mongoTemplate.find(query, WeUserPayFenzhang.class);
		for (WeUserPayFenzhang fenzhang : fenzhangs) {
			Organ organ = mongoTemplate.findById(fenzhang.getOrganId(), Organ.class);
			User user = mongoTemplate.findById(fenzhang.getUserId(), User.class);
			if (organ != null) {
				fenzhang.setOrganName(organ.getName());
			}
			if (user != null) {
				fenzhang.setUserName(user.getNick());
			}
			fenzhang.setPrice(roundHalfUp(fenzhang.getPrice()));
			fenzhang.setPayWeixin(roundHalfUp(fenzhang.getPayWeixin()));
			fenzhang.setPayWallet(roundHalfUp(fenzhang.getPayWallet()));
			fenzhang.setOrganAmount(roundHalfUp(fenzhang.getOrganAmount()));
			fenzhang.setSysAmount(roundHalfUp(fenzhang.getSysAmount()));
			fenzhang.setUserAmount(roundHalfUp(fenzhang.getUserAmount()));
			if (fenzhang != null) {
				if (fenzhang.getState() == 0) {
					fenzhang.setStateName("未处理");
				} else if (fenzhang.getState() == 1) {
					fenzhang.setStateName("已处理");
				}
			}
		}
		return fenzhangs;
	}

	private double roundHalfUp(double res) {
		BigDecimal bg = new BigDecimal(res);
		double roundAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return roundAmount;
	}
}
