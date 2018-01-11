package com.mrmf.service.user.userpart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganSetting;
import com.mrmf.entity.OrganXiaopiao;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WUsewupin;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Kaidan;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.Usersort;
import com.mrmf.service.common.Arith;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.usewupin.UsewupinService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.SecurityHelper;
import com.osg.framework.util.StringUtils;

@Service("userpartService")
public class UserpartServiceImpl implements UserpartService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private UsewupinService usewupinService;

	@Autowired
	private OrganService organService;

	@Override
	public List<Userpart> queryByCondition(final String organId, final String condition) throws BaseException {
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null) {
			throw new BaseException("指定id的公司信息不存在");
		}
		final List<String> organIds = new ArrayList<>();
		if (organ.getZongbu()) {
			organIds.add(organId);
			organIds.addAll(organ.getCardOrganIds());
		} else {
			Organ zongbuOrgan = mongoTemplate.findById(organ.getParentId(), Organ.class);
			if (zongbuOrgan == null) {
				throw new BaseException("数据错误，总部信息不存在");
			}
			if (zongbuOrgan.getCardOrganIds().contains(organ.get_id())) {
				organIds.add(zongbuOrgan.get_id());
				organIds.addAll(zongbuOrgan.getCardOrganIds());
			} else {
				organIds.add(organ.get_id());
			}
		}
		List<Criteria> cs = new ArrayList<Criteria>() {
			{
				add(Criteria.where("organId").in(organIds));
				add(Criteria.where("type").is(0));
				add(Criteria.where("delete_flag").is(false));
				Criteria c = new Criteria();
				c.orOperator(Criteria.where("phone").regex(condition), Criteria.where("name").regex(condition),
						Criteria.where("cardno").regex(condition), Criteria.where("id_2").regex(condition));
				add(c);
			}
		};
		Criteria criteria = new Criteria();
		criteria.andOperator(cs.toArray(new Criteria[cs.size()]));
		List<Userpart> userparts = mongoTemplate.find(Query.query(criteria), Userpart.class);
//		return getDetails(mongoTemplate.find(Query.query(criteria), Userpart.class));
		return userparts.size()>0?userparts:null;
	}

	public Userpart queryById(String id) throws BaseException {
		Userpart userpart = mongoTemplate.findById(id, Userpart.class);
		if (userpart == null) {
			throw new BaseException("指定id消费信息不存在");
		}
		List<Userpart> ups = new ArrayList<>();
		ups.add(userpart);
		return getDetails(ups).get(0);
	}

	@Override
	public FlipInfo<Userpart> queryByFpi(FlipInfo<Userpart> fpi) throws BaseException {
		String condition = (String) fpi.getParams().get("condition");
		Query query = null;
		Set set = fpi.getParams().keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if ("rxm".equals(key)){
				query = new Query(Criteria.where("money1").gte(0));
			}
		}
		fpi.getParams().remove("rxm");
		Criteria criteria = null;
		if (!StringUtils.isEmpty(condition)) {
			criteria = new Criteria();
			criteria.orOperator(Criteria.where("phone").regex(condition), Criteria.where("name").regex(condition),
					Criteria.where("cardno").regex(condition), Criteria.where("id_2").regex(condition));
			fpi.getParams().put("condition", null);
		}
		fpi = mongoTemplate.findByPage(query, criteria, fpi, Userpart.class);
		getDetails(fpi.getData());
		return fpi;
	}

	@Override
	public List<Userpart> getDetails(List<Userpart> userparts) {
		List<String> incardIds = new ArrayList<>();
		List<String> inincardIds = new ArrayList<>();
		List<String> usersortIds = new ArrayList<>();
		List<String> smallsortIds = new ArrayList<>();
		List<String> organIds = new ArrayList<>();
		List<String> userIds = new ArrayList<>();
		List<String> staff1s = new ArrayList<>();
		List<String> staff2s = new ArrayList<>();
		List<String> staff3s = new ArrayList<>();
		for (Userpart userpart : userparts) {
			if (userpart.getMiandan()) {
				userpart.setMiandanName("是");
			} else {
				userpart.setMiandanName("否");
			}
			if (userpart.getGuazhang_flag()) {
				userpart.setGuazhangName("是");
			} else {
				userpart.setGuazhangName("否");
			}
			if (userpart.getDelete_flag()) {
				userpart.setDeleteName("是");
			} else {
				userpart.setDeleteName("否");
			}
			String incardId = userpart.getIncardId();
			if (!StringUtils.isEmpty(incardId) && !incardIds.contains(incardId)) {
				incardIds.add(incardId);
			}
			String inincardId = userpart.getInincardId();
			if (!StringUtils.isEmpty(inincardId) && !inincardIds.contains(inincardId)) {
				inincardIds.add(inincardId);
			}
			String usersortId = userpart.getMembersort();
			if (!StringUtils.isEmpty(usersortId) && !usersortIds.contains(usersortId)) {
				usersortIds.add(usersortId);
			}
			String smallsortId = userpart.getSmallsort();
			if (!StringUtils.isEmpty(smallsortId) && !smallsortIds.contains(smallsortId)) {
				smallsortIds.add(smallsortId);
			}
			String organId = userpart.getOrganId();
			if (!StringUtils.isEmpty(organId) && !organIds.contains(organId)) {
				organIds.add(organId);
			}
			String userId = userpart.getUserId();
			if (!StringUtils.isEmpty(userId) && !organIds.contains(userId)) {
				userIds.add(userId);
			}
			String staff1 = userpart.getStaffId1();
			if (!StringUtils.isEmpty(staff1) && !staff1s.contains(staff1)) {
				staff1s.add(staff1);
			}
			String staff2 = userpart.getStaffId2();
			if (!StringUtils.isEmpty(staff2) && !staff1s.contains(staff2)) {
				staff1s.add(staff2);
			}
			String staff3 = userpart.getStaffId3();
			if (!StringUtils.isEmpty(staff3) && !staff1s.contains(staff3)) {
				staff1s.add(staff3);
			}
		}
		Map<String, Userincard> incardMap = new HashMap<>();
		Map<String, Userinincard> inincardMap = new HashMap<>();
		Map<String, Usersort> usersortMap = new HashMap<>();
		Map<String, Smallsort> smallsortMap = new HashMap<>();
		Map<String, Organ> organMap = new HashMap<>();
		Map<String, User> userMap = new HashMap<>();
		Map<String, Staff> staff1Map = new HashMap<>();
		Map<String, Staff> staff2Map = new HashMap<>();
		Map<String, Staff> staff3Map = new HashMap<>();
		if (staff1s.size() > 0) {
			List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("_id").in(staff1s)), Staff.class);
			for (Staff staff : staffs) {
				staff1Map.put(staff.get_id(), staff);
			}
		}
		if (staff2s.size() > 0) {
			List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("_id").in(staff2s)), Staff.class);
			for (Staff staff : staffs) {
				staff1Map.put(staff.get_id(), staff);
			}
		}
		if (staff3s.size() > 0) {
			List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("_id").in(staff3s)), Staff.class);
			for (Staff staff : staffs) {
				staff1Map.put(staff.get_id(), staff);
			}
		}
		if (incardIds.size() > 0) {
			List<Userincard> incards = mongoTemplate.find(Query.query(Criteria.where("_id").in(incardIds)),
					Userincard.class);
			for (Userincard incard : incards) {
				incardMap.put(incard.get_id(), incard);
			}
		}
		if (inincardIds.size() > 0) {
			List<Userinincard> inincards = mongoTemplate.find(Query.query(Criteria.where("_id").in(inincardIds)),
					Userinincard.class);
			for (Userinincard inincard : inincards) {
				inincardMap.put(inincard.get_id(), inincard);
			}
		}
		if (usersortIds.size() > 0) {
			List<Usersort> usersorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(usersortIds)),
					Usersort.class);
			for (Usersort usersort : usersorts) {
				usersortMap.put(usersort.get_id(), usersort);
			}
		}
		if (smallsortIds.size() > 0) {
			List<Smallsort> smallsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(smallsortIds)),
					Smallsort.class);
			for (Smallsort smallsort : smallsorts) {
				smallsortMap.put(smallsort.get_id(), smallsort);
			}
		}
		if (organIds.size() > 0) {
			List<Organ> organs = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
			for (Organ organ : organs) {
				organMap.put(organ.get_id(), organ);
			}
		}
		if (userIds.size() > 0) {
			List<User> users = mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)), User.class);
			for (User user : users) {
				userMap.put(user.get_id(), user);
			}
		}

		for (Userpart userpart : userparts) {
			String incardId = userpart.getIncardId();
			String inincardId = userpart.getInincardId();
			String usersort = userpart.getMembersort();
			String smallsort = userpart.getSmallsort();
			String organId = userpart.getOrganId();
			String userId = userpart.getUserId();
			boolean noSmallsort = false;
			// 子卡优先
			if (!StringUtils.isEmpty(inincardId) && inincardMap.containsKey(inincardId)) {
				Userinincard inincard = inincardMap.get(inincardId);
				userpart.setAllcishu(inincard.getAllcishu());
				userpart.setNowShengcishu(inincard.getShengcishu());
				userpart.setNowMoney4(inincard.getShengcishu() * inincard.getDanci_money()); // 卡余额
				userpart.setId_2(inincard.getId_2()); // 会员号（卡号）
				userpart.setXu_cishu(inincard.getXu_cishu()); // 续费次数
				userpart.setMoney_qian(inincard.getMoney_qian()); // 欠费
				userpart.setMembersort(inincard.getMembersort()); // 会员类别
				userpart.setDanci_money(inincard.getDanci_money()); // 单次款额
			} else if (!StringUtils.isEmpty(incardId) && incardMap.containsKey(incardId)) {
				Userincard incard = incardMap.get(incardId);
				userpart.setAllcishu(incard.getAllcishu());
				userpart.setNowShengcishu(incard.getShengcishu());
				//注原有的钱
				/*userpart.setNowMoney4(incard.getMoney4() - incard.getMoney_qian()); // 最新卡余额(扣减欠费)*/
				if(userpart.getType()==0 && (!userpart.getFlag2() || userpart.getGuazhang_flag())){
					userpart.setNowMoney4(0);
				}else {
					userpart.setNowMoney4(incard.getMoney4());
				}

				userpart.setId_2(incard.getId_2()); // 会员号（卡号）
				userpart.setXu_cishu(incard.getXu_cishu()); // 续费次数
				userpart.setMoney_qian(incard.getMoney_qian()); // 欠费
				// userpart.setMoney4(userpart.getMoney4() +
				// userpart.getMoney1());
				// userpart.setMoney_leiji(userpart.getMoney_leiji() +
				// userpart.getMoney1()); // 累计消费金额
				userpart.setMembersort(incard.getMembersort()); // 会员类别
				userpart.setNowSong_money(incard.getSong_money()); // 赠送金额余额
				userpart.setLaw_day(incard.getLaw_day()); // 会员卡有效期
				Double d1 = new Double(incard.getDanci_money());
				if(d1.isInfinite()){
					Usersort usersort1 = mongoTemplate.findById(incard.getMembersort(),Usersort.class);
					Userpart userpart1 = mongoTemplate.findOne(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class);
					if("1003".equals(userpart1.getUsersortType())){
						double danMoney = Double.valueOf(usersort1.getMoney() / userpart1.getAllcishu());
						BigDecimal l=new BigDecimal(Double.toString(danMoney));
						double res=l.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						incard.setDanci_money(res);
					}else {
						incard.setDanci_money(0);
					}
					mongoTemplate.save(incard);
				}
				userpart.setDanci_money(incard.getDanci_money()); // 单次款额

				if ("1001".equals(incard.getFlag1()) || "1002".equals(incard.getFlag1())) {// 单纯打折卡和存钱打折卡不关联服务项目
					noSmallsort = true;
				}
				if ("1001".equals(incard.getFlag1())) {// 1001:单纯打折卡
					userpart.setAllcishu(0);
					userpart.setShengcishu(0);
					userpart.setMoney4(0);
					userpart.setNowMoney4(0);
					userpart.setNowShengcishu(0);
				} else if ("1003".equals(incard.getFlag1())) {// 1003:次数卡
					BigDecimal de = new BigDecimal(incard.getDanci_money() * incard.getShengcishu());
					userpart.setNowMoney4(de.setScale(0,BigDecimal.ROUND_UP).doubleValue());
					// userpart.setShengcishu(userpart.getShengcishu() -
					// userpart.getCishu());
					// userpart.setMoney4(incard.getDanci_money() *
					// userpart.getShengcishu());
				}
			}

			if (!StringUtils.isEmpty(usersort) && usersortMap.containsKey(usersort)) {
				userpart.setUsersortName(usersortMap.get(usersort).getName1());
			}
			//错   !noSmallsort &&
			if (!StringUtils.isEmpty(smallsort) && smallsortMap.containsKey(smallsort)) {
				userpart.setSmallsortName(smallsortMap.get(smallsort).getName());
			}
			if (!StringUtils.isEmpty(organId) && organMap.containsKey(organId)) {
				userpart.setOrganName(organMap.get(organId).getName());
			}
			if (!StringUtils.isEmpty(userId) && userMap.containsKey(userId)) {
				User user = userMap.get(userId);
				// userpart.setName(user.getName());
				/*
				 * userpart.setPhone(user.getPhone());
				 * userpart.setBirthday(user.getBirthday());
				 * userpart.setPlace(user.getPlace());
				 * userpart.setSex(user.getSex());
				 * userpart.setLove(user.getLove());
				 */
			}
			String staffId1 = userpart.getStaffId1();
			String staffId2 = userpart.getStaffId2();
			String staffId3 = userpart.getStaffId3();
			if (!StringUtils.isEmpty(staffId1) && staff1Map.containsKey(staffId1)) {
				Staff staff = (Staff) staff1Map.get(staffId1);
				userpart.setStaff1Name(staff.getName());
			} else {
				userpart.setStaff1Name("无");
			}
			if (!StringUtils.isEmpty(staffId2) && staff1Map.containsKey(staffId2)) {
				Staff staff = (Staff) staff1Map.get(staffId2);
				userpart.setStaff2Name(staff.getName());
			} else {
				userpart.setStaff2Name("无");
			}
			if (!StringUtils.isEmpty(staffId3) && staff1Map.containsKey(staffId3)) {
				Staff staff = (Staff) staff1Map.get(staffId3);
				userpart.setStaff3Name(staff.getName());
			} else {
				userpart.setStaff3Name("无");
			}
		}
		return userparts;
	}

	@Override
	public List<Userpart> query(final String organId, final String kaidanId) throws BaseException {

		List<Criteria> cs = new ArrayList<Criteria>() {
			{
				add(Criteria.where("type").is(0));
				add(Criteria.where("organId").is(organId));
				add(Criteria.where("kaidanId").is(kaidanId));
				add(Criteria.where("delete_flag").is(false));
			}
		};

		Criteria criteria = new Criteria();
		criteria.andOperator(cs.toArray(new Criteria[cs.size()]));
		List<Userpart> userpartList = mongoTemplate.find(Query.query(criteria), Userpart.class);
		getDetails(userpartList);
		return userpartList;
	}

	@Override
	public ReturnStatus upsert(Userpart userpart) {
		boolean isNew = false; // 是否新建
		Userpart oldUserpart = null;
		if (StringUtils.isEmpty(userpart.get_id())) { // 新建
			if (!StringUtils.isEmpty(userpart.getUserpartId())) { // 界面有预置id则使用(用于产品选择场景)
				userpart.set_id(userpart.getUserpartId());
			} else {
				userpart.setIdIfNew();
			}
			/*userpart.setNowMoney4(userpart.getMoney2());*/
			userpart.setCreateTimeIfNew();
			isNew = true;
		} else {// 修改只是修改了赠送金额
			oldUserpart = mongoTemplate.findById(userpart.get_id(), Userpart.class);
			userpart.setId_2(userpart.getCardno());
			//验证修改卡号是否重复
			if (!userpart.getId_2().equals(oldUserpart.getId_2())) {
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(userpart.getOrganId()), Criteria.where("id_2").is(userpart.getId_2()),
						Criteria.where("delete_flag").is(false));
				if (mongoTemplate.exists(Query.query(criteria), Userpart.class)) {
					return new ReturnStatus(false, "相同卡号已经存在！");
				}
			}
			//修改赠送金额  会员名称
			oldUserpart.setNowSong_money(userpart.getSong_money());
			oldUserpart.setSong_money(userpart.getSong_money());
			oldUserpart.setName(userpart.getName());
			oldUserpart.setPhone(userpart.getPhone());
			oldUserpart.setPlace(userpart.getPlace());
			oldUserpart.setBirthday(userpart.getBirthday());
			oldUserpart.setLove(userpart.getLove());
			oldUserpart.setImages(userpart.getImages());
			oldUserpart.setStaff1Name(userpart.getStaff1Name());
			oldUserpart.setStaff2Name(userpart.getStaff2Name());
			oldUserpart.setStaff3Name(userpart.getStaff3Name());
			oldUserpart.setStaffId1(userpart.getStaffId1());
			oldUserpart.setStaffId2(userpart.getStaffId2());
			oldUserpart.setStaffId3(userpart.getStaffId3());
			oldUserpart.setYeji1(userpart.getYeji1());
			oldUserpart.setYeji2(userpart.getYeji2());
			oldUserpart.setYeji3(userpart.getYeji3());
			oldUserpart.setSomemoney1(userpart.getSomemoney1());
			oldUserpart.setSomemoney2(userpart.getSomemoney2());
			oldUserpart.setSomemoney3(userpart.getSomemoney3());
			oldUserpart.setHavemoney1(userpart.getHavemoney1());
			oldUserpart.setHavemoney2(userpart.getHavemoney2());
			oldUserpart.setHavemoney3(userpart.getHavemoney3());
			oldUserpart.setQuan1(userpart.getQuan1());
			oldUserpart.setQuan2(userpart.getQuan2());
			oldUserpart.setQuan3(userpart.getQuan3());
			oldUserpart.setDian1(userpart.getDian1());
			oldUserpart.setDian2(userpart.getDian2());
			oldUserpart.setDian3(userpart.getDian3());
			//修改卡表面
			oldUserpart.setCardno(userpart.getCardno());
			oldUserpart.setId_2(userpart.getCardno());

			mongoTemplate.save(oldUserpart);
			Userincard userincard = mongoTemplate.findById(oldUserpart.getIncardId(),Userincard.class);
			if(userincard!=null){
				userincard.setSong_money(userpart.getSong_money());
				userincard.setName(userpart.getName());
				userincard.setCardno(userpart.getCardno());
				userincard.setId_2(userpart.getId_2());
				mongoTemplate.save(userincard);
			}
		}

		Organ organ = mongoTemplate.findById(userpart.getOrganId(), Organ.class);
		if (organ == null) {
			return new ReturnStatus(false, "单位信息不存在!");
		}

		String id2 = StringUtils.getNumbers(userpart.getId_2()); // 会员卡号
		if (!StringUtils.isEmpty(id2) && userpart.getType() == 0 && userpart.getGuazhang_flag()) {
			if (isNew || (oldUserpart != null && !id2.equals(oldUserpart.getId_2()))) { // 卡号重复校验(新建或者修改了卡号时)
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(organ.get_id()), Criteria.where("id_2").is(id2),
						Criteria.where("delete_flag").is(false));
				if (mongoTemplate.exists(Query.query(criteria), Userpart.class)) {
					return new ReturnStatus(false, "相同卡号已经存在！");
				}
			}
			// 创建会员卡
			String phone = userpart.getPhone(); // 会员手机号，根据手机号查找是否已存在会员信息
			if (!StringUtils.isEmpty(phone)) {
				// 未填写卡表面号，则默认为卡号
				if (StringUtils.isEmpty(userpart.getCardno())) {
					userpart.setCardno(id2);
				}
				// 查询是否已经存在会员信息
				User user = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
				if (user == null) { // 不存在则创建
					user = new User();
					user.setIdIfNew();
					user.setCreateTimeIfNew();
					user.setDelete_flag(0); // 未删除
				}
				user.getImages().addAll(userpart.getImages()); // 设置会员照片
				user.setDoc(userpart.getDoc()); // 设置vip会员描述文档
				user.setPhone(phone);
				user.setName(userpart.getName());
				if (userpart.getBirthday() != null)
					user.setBirthday(userpart.getBirthday());
				if (!StringUtils.isEmpty(userpart.getPlace()))
					user.setPlace(userpart.getPlace());
				if (!StringUtils.isEmpty(userpart.getSex()))
					user.setSex(userpart.getSex());
				if (!StringUtils.isEmpty(userpart.getLove()))
					user.setLove(userpart.getLove());
				if (!user.getOrganIds().contains(organ.get_id()))
					user.getOrganIds().add(organ.get_id());
				if (!user.getParentIds().contains(organ.getParentId()))
					user.getParentIds().add(organ.getParentId());

				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("userId").is(user.get_id()),
						Criteria.where("organId").is(organ.get_id()));
				Usercard usercard = mongoTemplate.findOne(Query.query(criteria), Usercard.class);
				if (usercard == null) {
					usercard = new Usercard();
					usercard.setIdIfNew();
					usercard.setCreateTimeIfNew();
					usercard.setComeday(DateUtil.currentDate());
				}
				usercard.setUserId(user.get_id());
				usercard.setOrganId(organ.get_id());
				usercard.setCardno(userpart.getCardno());
				usercard.setPasswd(userpart.getPasswd());

				// 创建会员卡
				Userincard userincard;
				if (StringUtils.isEmpty(userpart.getIncardId())) {
					userincard = new Userincard();
					userincard.setIdIfNew();
					userincard.setCreateTimeIfNew();
				} else {
					userincard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
				}
				userincard.setUserId(user.get_id());
				userincard.setOrganId(organ.get_id());
				userincard.setCardId(usercard.get_id());
				userincard.setCardno(usercard.getCardno());
				userincard.setId_2(userpart.getId_2());
				userincard.setSong_money(userpart.getSong_money()); // 赠送金额
				String ms = userpart.getMembersort();
				Usersort usersort = mongoTemplate.findById(ms, Usersort.class);
				if (usersort == null) {
					return new ReturnStatus(false, "会员类型不能为空!");
				}
				userincard.setCoin(userpart.getCoin()); // 积分
				userincard.setMembersort(ms);
				//办卡自定义次数
//				userincard.setAllcishu(usersort.getCishu());
//				userincard.setShengcishu(usersort.getCishu());
//				userincard.setDanci_money(usersort.getDanci_money());
				userincard.setAllcishu(userpart.getAllcishu());
				userincard.setShengcishu(userpart.getAllcishu());
				double danMoney = 0;
				if("1003".equals(usersort.getFlag1())){
					DecimalFormat df = new DecimalFormat("#0.##");
					df.setRoundingMode(RoundingMode.FLOOR);
					danMoney = Double.valueOf(df.format(usersort.getMoney() / userpart.getAllcishu()));
				}
				userincard.setDanci_money(danMoney);
				userincard.setDelete_flag(false);
				userincard.setGuazhang_flag(true); // 挂账
				userincard.setSmallsort(userpart.getSmallsort());
				userincard.setBigsort(userpart.getBigsort());
				userincard.setCharge_big(userpart.getLaibin_flag());
				userincard.setCharge_flag(userpart.getCharge_fag());
				userincard.setZhekou(usersort.getZhekou());
				userincard.setCome_day(DateUtil.currentDate());
				userincard.setMoney4(userpart.getMoney2());
				//会员卡余额由usersort的money变为实缴金额

				/*userincard.setMoney4(usersort.getMoney()); // 卡余额*/
				userincard.setMoney_leiji(usersort.getMoney()); // 累计消费金额
				userincard.setLaw_day(usersort.getLaw_day());
				userincard.setName(userpart.getName()); // 会员姓名
				userincard.setFlag1(usersort.getFlag1()); // 会员类别代码
				userincard.setMoney_qian(userpart.getMoney_qian()); // 欠费金额

				// 单纯打折和存钱打折卡删除服务项目和次数相关字段
				if ("1001".equals(usersort.getFlag1()) || "1002".equals(usersort.getFlag1())) {
					userpart.setBigsort(null);
					userpart.setSmallsort(null);
					userpart.setCishu(0);
					userpart.setAllcishu(0);

					userincard.setAllcishu(0);
					userincard.setShengcishu(0);
				}

				// 单纯打折卡删除余额
				if ("1001".equals(usersort.getFlag1())) {
					userincard.setMoney4(0);
					userpart.setMoney4(0);
				}

				mongoTemplate.save(userincard);
				mongoTemplate.save(usercard);
				mongoTemplate.save(user);

				userpart.setIncardId(userincard.get_id());
				userpart.setCardId(usercard.get_id());
				userpart.setPasswd(null); // 清空卡口令设置
				userpart.setUsersortType(usersort.getFlag1());
				userpart.setUserId(user.get_id());
				userpart.setMoney6(userincard.getZhekou()); // 初始化折扣
			} else {
				return new ReturnStatus(false, "会员手机号不能为空!");
			}
		} else if (userpart.getType() == 1) { // 卡消费保存
			Userincard incard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
			List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class);
			Userpart oldPart = userpartList.get(0);
			if (incard == null) {
				return new ReturnStatus(false, "会员卡信息不存在!");
			}
			User user = mongoTemplate.findById(incard.getUserId(), User.class);
			if (user == null) {
				return new ReturnStatus(false, "会员用户信息不存在!");
			}
			/*
			 * 跨公司消费存在问题Usercard usercard =
			 * mongoTemplate.findById(incard.getCardId(), Usercard.class); if
			 * (usercard == null) { return new ReturnStatus(false, "用户卡信息不存在!");
			 * }
			 */
			Usersort usersort = mongoTemplate.findById(incard.getMembersort(), Usersort.class);
			if (usersort == null) {
				return new ReturnStatus(false, "会员类别不存在");
			}
			if (!incard.getOrganId().equals(userpart.getOrganId())) { // 跨店消费，消费记录保存办卡公司id
				userpart.setOwnerOrganId(incard.getOrganId());
			}
			String flag1 = usersort.getFlag1();
			if ("1001".equals(flag1)) { // 单纯打折卡
                if(userpart.getMiandan()){
                    userpart.setMiandanMoney(userpart.getMoney_cash());
                }
			} else if ("1002".equals(flag1)) { // 存钱打折卡
				// 优先消费赠送金额
				double song = incard.getSong_money(); // 赠送金额余额
				double x = 0;//消费金额
				//是否免单
				if(!userpart.getMiandan()){
					x = oldUserpart == null ? userpart.getMoney_xiaofei()
							: userpart.getMoney_xiaofei() - oldUserpart.getMoney_xiaofei();
				}else {
					userpart.setMiandanMoney(userpart.getMoney_xiaofei());
					userpart.setMoney5(0);
					userpart.setMoney_xiaofei(0);
				}
				//使用赠送金额更新type=0

				double y = x - song;//
				if (y > 0) {
					// 计算余额(扣减抹零)
					incard.setMoney4(incard.getMoney4() - y + userpart.getMoney5());
					incard.setSong_money(0);
					userpart.setNowSongMoney(song);//小票显示字段 本次消费赠送金额
					oldPart.setNowMoney4(oldPart.getNowMoney4()-y+userpart.getMoney5());
				} else {
					incard.setSong_money(-1 * y+userpart.getMoney5()); // 赠送金额 = y+抹零
					userpart.setNowSongMoney(x-userpart.getMoney5());//小票显示字段 本次消费赠送金额
				}
				oldPart.setNowSong_money(incard.getSong_money());
				userpart.setMoney4(incard.getMoney4());
				userpart.setSong_money(incard.getSong_money());
				mongoTemplate.save(oldPart);
				mongoTemplate.save(incard);
			} else if ("1003".equals(flag1)) { // 次数卡
				int shengcishu;
				if (oldUserpart != null) {
					shengcishu = incard.getShengcishu() - (userpart.getCishu() - oldUserpart.getCishu());
				} else {
					shengcishu = incard.getShengcishu() - userpart.getCishu();
				}
				if(userpart.getMiandan()){
					shengcishu = incard.getShengcishu();

				}
				oldPart.setNowMoney4(shengcishu * incard.getDanci_money());
				incard.setShengcishu(shengcishu);
				incard.setMoney4(incard.getShengcishu() * incard.getDanci_money());// 更新卡余额
				oldPart.setNowShengcishu(shengcishu);
				userpart.setBigsort(incard.getBigsort());
				userpart.setSmallsort(incard.getSmallsort());
				userpart.setMoney4(incard.getMoney4());
				userpart.setShengcishu(incard.getShengcishu());
				mongoTemplate.save(oldPart);
				mongoTemplate.save(incard);
			} else {
				return new ReturnStatus(false, "会员卡类型不能为空!");
			}

			// 查询办卡时的userpart记录，取办卡name姓名
			Criteria cr = new Criteria();
			cr.andOperator(Criteria.where("incardId").is(userpart.getIncardId()), Criteria.where("type").is(0));
			Userpart cardup = mongoTemplate.findOne(Query.query(cr), Userpart.class);
			if (cardup != null) {
				userpart.setName(cardup.getName());
			} else {
				userpart.setName(user.getName());
			}

			userpart.setSex(user.getSex());
			userpart.setBirthday(user.getBirthday());
			userpart.setId_2(incard.getId_2());
			userpart.setCardno(incard.getCardno());
			userpart.setUsersortType(flag1);
			userpart.setUserId(user.get_id());
		}
		if(userpart.getType()==0){
			if(userpart.getMiandan()){
				userpart.setMiandanMoney(userpart.getMoney1()-userpart.getMoney5());
			}
		}
		// 使用产品总价格计算
		try {
			// 是否更新库存
			boolean deductKucun = false;
			try {
				OrganSetting organSetting = organService.querySetting(organ.get_id());
				deductKucun = organSetting.getChanpinDeductKucun();
			} catch (BaseException e) {
				// ignore it
			}
			List<WUsewupin> usewupinList = usewupinService.query(userpart.get_id());
			double totalMoney1 = 0;
			for (WUsewupin usewupin : usewupinList) {
				totalMoney1 += usewupin.getMoney1();

				if (deductKucun) { // 更新库存
					double num = usewupin.getYongliang();
					Criteria criteria = new Criteria();
					criteria.andOperator(Criteria.where("organId").is(organ.get_id()),
							Criteria.where("wupinId").is(usewupin.getWupinId()));
					WStoreroom storeroom = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
					/*
					 * if (storeroom == null || storeroom.getNum() < amount) {
					 * return new ReturnStatus(false, "库存不足!"); }
					 */
					WOutstoreroom outstoreroom = mongoTemplate
							.findOne(Query.query(Criteria.where("danhao").is(usewupin.get_id())), WOutstoreroom.class);
					if (outstoreroom == null) { // 新增
						usewupin.setIdIfNew();

						storeroom.setNum(storeroom.getNum() - num);

						outstoreroom = new WOutstoreroom();
						outstoreroom.setIdIfNew();
						outstoreroom.setParentOrganId(storeroom.getParentOrganId());
						outstoreroom.setUserpartId(usewupin.getUserpartId()); // 关联消费记录id
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
						outstoreroom.setDanhao(usewupin.get_id());

					} else { // 修改
						storeroom.setNum(storeroom.getNum() + outstoreroom.getNum() - num);

						outstoreroom.setNum(num);
						outstoreroom.setPrice_all(Arith.mul(num, storeroom.getPrice()));
						outstoreroom.setWeight_all(Arith.mul(num, storeroom.getWeight()));
					}

					mongoTemplate.save(storeroom);
					if (outstoreroom != null)
						mongoTemplate.save(outstoreroom);
				}
			}
			userpart.setMoney_wupin(totalMoney1);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
		//mongoTemplate.save(userpart);
		if(isNew){
//			if(userpart.getMiandan()){
//				userpart.setMiandanMoney(userpart.getMoney1());
//			}
			mongoTemplate.save(userpart);
		}
		ReturnStatus status = new ReturnStatus(true);
		return status;
	}

	@Override
	public ReturnStatus remove(String organId, String id) {
		Userpart userpart = mongoTemplate.findById(id, Userpart.class);
		if (userpart == null) {
			return new ReturnStatus(false, "指定id的消费信息不存在!");
		} else if (!organId.equals(userpart.getOrganId())) {
			return new ReturnStatus(false, "指定消费信息不属于此公司!");
		} else if (!userpart.getGuazhang_flag()) {
			return new ReturnStatus(false, "已结账消费信息不能删除!");
		} else {
			if (!StringUtils.isEmpty(userpart.getIncardId()) && userpart.getType() == 0) { // 子卡删除处理
				Userincard userincard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
				if (userincard != null) {
					if (!userincard.getGuazhang_flag()) {
						return new ReturnStatus(false, "已结账卡信息不能删除");
					} else {
						userincard.setDelete_flag(true);
						mongoTemplate.save(userincard);
					}
				}
			} else if (userpart.getType() == 1) { // 卡消费删除
				Userincard incard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
				if (incard == null) {
					return new ReturnStatus(false, "会员卡信息不存在!");
				}
				Usercard usercard = mongoTemplate.findById(incard.getCardId(), Usercard.class);
				if (usercard == null) {
					return new ReturnStatus(false, "用户卡信息不存在!");
				}
				Usersort usersort = mongoTemplate.findById(incard.getMembersort(), Usersort.class);
				if (usersort == null) {
					return new ReturnStatus(false, "会员类别不存在");
				}
				String flag1 = usersort.getFlag1();
				List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class);
				Userpart oldUserpart = userpartList.get(0);
				if ("1001".equals(flag1)) { // 单纯打折卡

				} else if ("1002".equals(flag1)) { // 存钱打折卡
					//退卡钱，免单不退钱
					incard.setMoney4(incard.getMoney4() + userpart.getMoney_xiaofei() - userpart.getMoney5());
					//退type=0 的userpart钱
					oldUserpart.setNowMoney4(oldUserpart.getNowMoney4()+userpart.getMoney_xiaofei() - userpart.getMoney5());
					mongoTemplate.save(oldUserpart);
					mongoTemplate.save(incard);
				} else if ("1003".equals(flag1)) { // 次数卡
					//退卡钱和次数
					if(!userpart.getMiandan()) {
						incard.setShengcishu(incard.getShengcishu() + userpart.getCishu());
					}
					incard.setMoney4(incard.getShengcishu()*incard.getDanci_money());
					//退 type=0  次数
					oldUserpart.setNowMoney4(incard.getMoney4());
					oldUserpart.setNowShengcishu(incard.getShengcishu());
					mongoTemplate.save(oldUserpart);
					mongoTemplate.save(incard);
				} else {
					return new ReturnStatus(false, "会员卡类型不能为空!");
				}
			}
			userpart.setDelete_flag(true);
			mongoTemplate.save(userpart);

			// 使用产品信息删除标记
			List<WUsewupin> usewupinList = mongoTemplate
					.find(Query.query(Criteria.where("userpartId").is(userpart.get_id())), WUsewupin.class);
			for (WUsewupin usewupin : usewupinList) {
				usewupin.setDelete_flag(true);
				mongoTemplate.save(usewupin);
			}

			// 是否有关联出库信息恢复
			List<WOutstoreroom> outroomList = mongoTemplate
					.find(Query.query(Criteria.where("userpartId").is(userpart.get_id())), WOutstoreroom.class);
			for (WOutstoreroom outstoreroom : outroomList) {
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(outstoreroom.getOrganId()),
						Criteria.where("wupinId").is(outstoreroom.getWupinId()));
				WStoreroom storeroom = mongoTemplate.findOne(Query.query(criteria), WStoreroom.class);
				if (storeroom != null) {
					storeroom.setNum(storeroom.getNum() + outstoreroom.getNum()); // 恢复库存数量

					outstoreroom.setDelete_flag(true); // 设置删除标记

					mongoTemplate.save(storeroom);
				}
				mongoTemplate.save(outstoreroom);
			}

			return new ReturnStatus(true);
		}
	}

	@Override
	public ReturnStatus jiezhang(String organId, String[] userpartIds, String kaidanId, double money_yinhang_money,
			double money_li_money, String money_lijuan, double money_cash, double money3) {
		ReturnStatus status;
		Map<String, Kaidan> kaidanMap = new HashMap<>(); // 开单缓存，id -> Kaidan
		List<Userpart> userparts;
		if (!StringUtils.isEmpty(kaidanId)) {
			Kaidan kaidan = mongoTemplate.findById(kaidanId, Kaidan.class);
			if (kaidan == null) {
				return new ReturnStatus(false, "指定开单id不存在");
			} else if (!organId.equals(kaidan.getOrganId())) {
				return new ReturnStatus(false, "指定开单信息不属于此公司");
			} else if (!kaidan.getGuazhang_flag()) {
				return new ReturnStatus(false, "指定开单已结账");
			} else {
				kaidanMap.put(kaidan.get_id(), kaidan);
			}

			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("kaidanId").is(kaidanId), Criteria.where("delete_flag").is(false));
			userparts = mongoTemplate.find(Query.query(criteria), Userpart.class);
		} else {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("_id").in(userpartIds), Criteria.where("delete_flag").is(false));
			userparts = mongoTemplate.find(Query.query(criteria), Userpart.class);
		}

		List<String> uids = new ArrayList<>();
		for (Userpart userpart : userparts) {
			if (!userpart.getFlag2() || userpart.getGuazhang_flag()) { // 未交款或挂账的进行结算
				uids.add(userpart.get_id());
				userpart.setMoney2(userpart.getMoney1() - userpart.getMoney5() - userpart.getMoney_qian()); // 实缴=应交-抹零-欠款
				userpart.setFlag2(true); // 是否交款
				userpart.setGuazhang_flag(false); // 是否挂账
				userpart.setMoney3(money3);
				userpart.setMoney_li_money(money_li_money); // 代金券金额
				userpart.setMoney_lijuan(money_lijuan); // 代金券号
				userpart.setMoney_yinhang_money(money_yinhang_money); // 银行卡金额
				userpart.setMoney_cash(money_cash); // 现金

				if (!StringUtils.isEmpty(userpart.getCardId())) {// 主卡处理
					Usercard usercard = mongoTemplate.findById(userpart.getCardId(), Usercard.class);
					if (usercard != null) {
						usercard.setMoney_leiji(usercard.getMoney_leiji() + userpart.getMoney1()); // 累计消费金额
						usercard.setXu_cishu(usercard.getXu_cishu() + 1); // 续费次数
						usercard.setCome_num(usercard.getCome_num() + 1); // 来的次数
						usercard.setLastcomeday(DateUtil.currentDate());
						mongoTemplate.save(usercard);
					}
				}

				if (!StringUtils.isEmpty(userpart.getIncardId())) { // 子卡处理
					Userincard userincard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
					if (userincard != null) {
						userincard.setGuazhang_flag(false); // 是否挂账
						mongoTemplate.save(userincard);
					}
				}

				mongoTemplate.save(userpart);

				if (StringUtils.isEmpty(kaidanId)) { // 非开单结算，判断开单中消费信息是否都已结算
					String ki = userpart.getKaidanId();
					Criteria criteria = new Criteria();
					criteria.andOperator(Criteria.where("kaidanId").is(ki), Criteria.where("flag2").is(false),
							Criteria.where("guazhang_flag").is(true));
					if (!mongoTemplate.exists(Query.query(criteria), Userpart.class)) {
						// 如果不存在需要结算的消费记录，则设置开单为已结算
						Kaidan k = mongoTemplate.findById(ki, Kaidan.class);
						if (k != null) {
							k.setGuazhang_flag(false);
							mongoTemplate.save(k);
						}
					}

				}
			}
		}

		for (Kaidan kaidan : kaidanMap.values()) {
			kaidan.setGuazhang_flag(false);
			mongoTemplate.save(kaidan);
		}

		// 设置使用物品的产品出库结账状态
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("userpartId").in(uids), Criteria.where("delete_flag").is(false));
		List<WOutstoreroom> outroomList = mongoTemplate.find(Query.query(criteria), WOutstoreroom.class);
		for (WOutstoreroom outroom : outroomList) {
			outroom.setGuazhang_flag(false);
			mongoTemplate.save(outroom);
		}

		// 设置外卖和外卖出库结账状态
		criteria = new Criteria();
		criteria.andOperator(Criteria.where("kaidanId").is(kaidanId), Criteria.where("delete_flag").is(false));
		List<WWaimai> waimaiList = mongoTemplate.find(Query.query(criteria), WWaimai.class);
		for (WWaimai waimai : waimaiList) {
			waimai.setGuazhang_flag(false);
			mongoTemplate.save(waimai);
		}

		outroomList = mongoTemplate.find(Query.query(criteria), WOutstoreroom.class);
		for (WOutstoreroom outroom : outroomList) {
			outroom.setGuazhang_flag(false);
			mongoTemplate.save(outroom);
		}

		status = new ReturnStatus(true);
		return status;
	}

	@Override
	public List<Userpart> queryByIncardId(final String incardId, final int type, final boolean all, final boolean typeFlag)
			throws BaseException {
		List<Criteria> criterias = new ArrayList<Criteria>() {
			{
				add(Criteria.where("incardId").is(incardId));
				if(typeFlag){
                    add(Criteria.where("type").in(type,11));
                }else {
                    add(Criteria.where("type").is(type));
                }

				add(Criteria.where("delete_flag").is(false));
				if (!all) {
					add(Criteria.where("flag2").is(false));
					add(Criteria.where("guazhang_flag").is(true));
				}
			}
		};

		Criteria criteria = new Criteria();
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));

		return getDetails(mongoTemplate.find(Query.query(criteria), Userpart.class));
	}

	@Override
	public List<Userpart> queryByIds(List<String> ids) throws BaseException {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<>();
		}

		List<Userpart> userparts = getDetails(
				mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), Userpart.class));
		String organId = null;

		boolean xp = false;
		for (Userpart userpart : userparts) {
			if (organId == null) {
				organId = userpart.getOrganId();
			}
			if (StringUtils.isEmpty(userpart.getXiaopiao()))
				xp = true;
		}

		String xiaopiao = null;
		if (xp) {
			xiaopiao = getXiaopiaoCode(organId, ids);
		}

		if (xiaopiao != null) {
			for (Userpart userpart : userparts) {
				if (StringUtils.isEmpty(userpart.getXiaopiao())) {
					userpart.setXiaopiao(xiaopiao);
					mongoTemplate.save(userpart);
				}
			}
		}

		return userparts;
	}

	@Override
	public String getXiaopiaoCode(String organId, List<String> userpartIds) throws BaseException {
		Collections.sort(userpartIds);
		String checksum = SecurityHelper.digest(userpartIds.toArray(new String[userpartIds.size()]));

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("checksum").is(checksum), Criteria.where("organId").is(organId));
		OrganXiaopiao ox = mongoTemplate.findOne(Query.query(criteria), OrganXiaopiao.class);
		if (ox != null) { // 已经打印过则直接返回打印小票单号
			return getCodeStr(ox.getCode());
		}

		int date = Integer.parseInt(DateUtil.format(DateUtil.currentDate(), "yyyyMM"));
		criteria = new Criteria();
		criteria.andOperator(Criteria.where("date").is(date), Criteria.where("organId").is(organId));
		ox = mongoTemplate.findOne(Query.query(criteria).with(new Sort(Direction.DESC, "code")), OrganXiaopiao.class);
		int code = 1;
		if (ox != null) {
			code = ox.getCode() + 1;
		}

		ox = new OrganXiaopiao();
		ox.setIdIfNew();
		ox.setCreateTimeIfNew();
		ox.setOrganId(organId);
		ox.setDate(date);
		ox.setCode(code);
		ox.setUserparts(userpartIds);
		ox.setChecksum(checksum);
		mongoTemplate.save(ox);

		return getCodeStr(code);
	}

	private String getCodeStr(int code) {
		String codeStr = String.valueOf(code);
		String cs = codeStr;
		for (int i = 4; i > codeStr.length(); i--) {
			cs = "0" + cs;
		}
		return DateUtil.format(DateUtil.currentDate(), "yyyyMM") + cs;
	}

	@Override
	public List<Userpart> queryUserPartByFpi(FlipInfo<Userpart> fpi) throws BaseException {
		Query query = new Query();
		fpi.getParams().remove("rxm");
		Map<String, String> params = fpi.getParams();
		mongoTemplate.processParams(query, null, params);

		if (params != null) {
			String sortField = params.get("sidx");
			String sortOrder = params.get("sord");
			if (!StringUtils.isEmpty(sortField)) {
				fpi.setSortField(sortField);
				fpi.setSortOrder(sortOrder);
			}
		}
		if (!StringUtils.isEmpty(fpi.getSortField())) {
			Sort sort = null;
			List<String> sortFields = Arrays.asList(fpi.getSortField().split(","));
			if (!StringUtils.isEmpty(fpi.getSortOrder())) {
				if (fpi.getSortOrder().equalsIgnoreCase(Direction.ASC.toString())) {
					sort = new Sort(Direction.ASC, sortFields);
				} else {
					sort = new Sort(Direction.DESC, sortFields);
				}
			} else {
				sort = new Sort(Direction.ASC, sortFields);
			}
			if (sort != null) {
				query.with(sort);
			}
		}
		// System.out.println(query);
		List<Userpart> list = mongoTemplate.find(query, Userpart.class);
		getDetails(list);
		return list;
	}
	public Userpart queryByIncardId(String inCardId){
		List<Userpart> list = mongoTemplate.find(Query.query(Criteria.where("incardId").is(inCardId).and("type").is(0)),Userpart.class);
		if(list.size()==0){
			return new Userpart();
		}
		return list.get(0);
	}
}
