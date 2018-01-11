package com.mrmf.service.user.inincard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.User;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.UserpartCrossOrgan;
import com.mrmf.entity.user.Usersort;
import com.mrmf.service.user.userpart.UserpartService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("inincardService")
public class InincardServiceImpl implements InincardService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private UserpartService userpartService;

	@Override
	public Userinincard queryById(String inicardId) throws BaseException {
		if (!StringUtils.isEmpty(inicardId))
			return mongoTemplate.findById(inicardId, Userinincard.class);
		else
			return null;
	}

	@Override
	public ReturnStatus insert(Userpart userpart) {
		String incardId = userpart.getIncardId();
		
		if (StringUtils.isEmpty(incardId)) {
			return new ReturnStatus(false, "母卡id不能为空");
		}
		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "指定id的母卡信息不存在");
		}
		Usersort usersort = mongoTemplate.findById(userpart.getMembersort(), Usersort.class);
		if (usersort == null) {
			return new ReturnStatus(false, "会员类别不存在");
		}

		double zhekou = userpart.getMoney6(); // 折扣
		double money4 = incard.getMoney4(); // 卡余额

		double money1 = userpart.getMoney1(); // 成交金额（办卡金额）
		double money2 = BigDecimal.valueOf(money1).multiply(BigDecimal.valueOf(zhekou)).divide(BigDecimal.valueOf(100))
				.doubleValue(); // 计算实缴金额(新办卡金额
		//判断当前会员卡欠费金额是否大于等于开卡时的金额
		if (!(userpart.getMiandan() != null && userpart.getMiandan())){
			if((money4-money2)+usersort.getMoney()<0){
				return new ReturnStatus(false,"欠费金额超过开卡金额");
			}
		}
		
		
		userpart.setIdIfNew();
		userpart.setCreateTimeIfNew();
		userpart.setType(10); // 设置类型为办子卡
		userpart.setMoney1(money2); // 设置新办卡金额（折后）
		// userpart.setMoney2(money2); 统计实际收入不包含此金额，因为是从存钱打折卡中划转的
		userpart.setMoney6(zhekou); // 设置折扣

		Userinincard inincard = new Userinincard();
		inincard.setIdIfNew();

		List<String> userpartIds = new ArrayList<String>();
		userpartIds.add(inincard.get_id());
		String xiaopiao = "";
		try {
			xiaopiao = userpartService.getXiaopiaoCode(userpart.getOrganId(), userpartIds);
		} catch (BaseException e) {
			// ignore it
		}
		userpart.setXiaopiao(xiaopiao);

		inincard.setCreateTimeIfNew();
		inincard.setUserId(userpart.getUserId());
		inincard.setOrganId(userpart.getOrganId());
		inincard.setIncardId(incardId);
		inincard.setCardId(incard.getCardId());
		inincard.setCardno(userpart.getCardno());
		inincard.setId_2(userpart.getId_2());
		inincard.setMembersort(userpart.getMembersort());
		inincard.setAllcishu(userpart.getAllcishu());
		inincard.setMiandan(userpart.getMiandan());
		inincard.setMoney_leiji(money2);
		inincard.setId_2(incard.getId_2()); // 会员号续传
		inincard.setName(incard.getName()); // 复制会员卡姓名
		inincard.setXiaopiao(xiaopiao);

		// 设置剩余次数
		if (userpart.getShengcishu() == 0) { // 剩余次数未填写，则为总次数
			inincard.setShengcishu(inincard.getAllcishu());
		} else {
			inincard.setShengcishu(userpart.getShengcishu());
		}
		// 设置单次款额（折后办卡金额 / 总次数）
		inincard.setDanci_money(
				BigDecimal.valueOf(money2).divide(BigDecimal.valueOf(inincard.getAllcishu())).doubleValue());
		inincard.setDelete_flag(false);
		inincard.setGuazhang_flag(false);
		inincard.setBigsort(userpart.getBigsort());
		inincard.setSmallsort(userpart.getSmallsort());

		// 免单则不扣除主卡余额
		if (!(userpart.getMiandan() != null && userpart.getMiandan())) {
			// 扣除会员主卡余额
			double balance = money4 - money2;
			if(balance<0){
				incard.setMoney_qian(incard.getMoney_qian()+balance);
			}
			incard.setMoney4(balance);
			mongoTemplate.save(incard);
		}
		//办理子卡 更新userpart=0 的钱
		List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class);
		Userpart oldUserPart = userpartList.get(0);
		oldUserPart.setNowMoney4(incard.getMoney4());

		userpart.setShengcishu(inincard.getShengcishu());
		userpart.setAllcishu(inincard.getAllcishu());
		userpart.setUsersortType(usersort.getFlag1());

		mongoTemplate.save(oldUserPart);
		mongoTemplate.save(userpart);
		mongoTemplate.save(inincard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus remove(String id, double danciTui) {
		if (StringUtils.isEmpty(id)) {
			return new ReturnStatus(false, "要删除的子卡id不能为空");
		}

		Userinincard inincard = mongoTemplate.findById(id, Userinincard.class);
		if (inincard == null) {
			return new ReturnStatus(false, "指定id的子卡信息为空");
		}

		Userincard incard = mongoTemplate.findById(inincard.getIncardId(), Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "主卡信息不存在");
		}

		// 免单,子卡不返钱
		if (!(inincard.getMiandan() != null && inincard.getMiandan())) {

			// 计算卡内剩余金额（剩余次数*单次款额）
			double shengMoney;
			if (danciTui == -1) { // 删除
				shengMoney = BigDecimal.valueOf(inincard.getDanci_money())
						.multiply(BigDecimal.valueOf(inincard.getShengcishu())).doubleValue();
			} else {
				shengMoney = BigDecimal.valueOf(danciTui).multiply(BigDecimal.valueOf(inincard.getShengcishu()))
						.doubleValue();
			}
			incard.setMoney4(incard.getMoney4() + shengMoney);

			//删除子卡 更新type=0 userPart
			List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class);
			Userpart oldUserPart = userpartList.get(0);
			oldUserPart.setNowMoney4(oldUserPart.getNowMoney4()+shengMoney);
			mongoTemplate.save(oldUserPart);
			mongoTemplate.save(incard);
		}

		// 删除全部未结账消费记录
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag2").is(false), Criteria.where("guazhang_flag").is(true),
				Criteria.where("inincardId").is(id));
		mongoTemplate.remove(Query.query(criteria), Userpart.class);

		inincard.setDelete_flag(true);
		mongoTemplate.save(inincard);

		return new ReturnStatus(true);
	}

	@Override
	public List<Userinincard> query(String incardId) throws BaseException {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("delete_flag").is(false), Criteria.where("incardId").is(incardId));
		List<Userinincard> inincardList = mongoTemplate
				.find(Query.query(criteria).with(new Sort(Direction.ASC, "createTime")), Userinincard.class);
		return getDetails(inincardList);
	}

	@Override
	public FlipInfo<Userinincard> queryByFpi(FlipInfo<Userinincard> fpi) throws BaseException {
		fpi = mongoTemplate.findByPage(null, fpi, Userinincard.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private List<Userinincard> getDetails(List<Userinincard> inincardList) {
		List<String> ids = new ArrayList<>();
		List<String> usersortIds = new ArrayList<>();
		List<String> smallsortIds = new ArrayList<>();
		for (Userinincard inincard : inincardList) {
			if (inincard.getDelete_flag()) {
				inincard.setDeleteName("删除");
			} else {
				inincard.setDeleteName("正常");
			}
			if (inincard.getMiandan()) {
				inincard.setMiandanName("是");
			} else {
				inincard.setMiandanName("否");
			}
			if (!ids.contains(inincard.getUserId()))
				ids.add(inincard.getUserId());
			String usersortId = inincard.getMembersort();
			if (!StringUtils.isEmpty(usersortId) && !usersortIds.contains(usersortId)) {
				usersortIds.add(usersortId);
			}
			if (!smallsortIds.contains(inincard.getSmallsort()))
				smallsortIds.add(inincard.getSmallsort());
		}

		Map<String, User> userMap = new HashMap<>();
		if (ids.size() > 0) {
			List<User> userList = mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), User.class);
			for (User user : userList) {
				userMap.put(user.get_id(), user);
			}
		}

		Map<String, Usersort> usersortMap = new HashMap<>();
		if (usersortIds.size() > 0) {
			List<Usersort> usersorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(usersortIds)),
					Usersort.class);
			for (Usersort usersort : usersorts) {
				usersortMap.put(usersort.get_id(), usersort);
			}
		}

		Map<String, Smallsort> smallsortMap = new HashMap<>();
		if (smallsortIds.size() > 0) {
			List<Smallsort> smallsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(smallsortIds)),
					Smallsort.class);
			for (Smallsort smallsort : smallsorts) {
				smallsortMap.put(smallsort.get_id(), smallsort);
			}
		}

		for (Userinincard inincard : inincardList) {
			User user = userMap.get(inincard.getUserId());
			String usersort = inincard.getMembersort();
			if (StringUtils.isEmpty(inincard.getName()) && user != null) {
				inincard.setName(user.getName() == null ? user.getNick() : user.getName());
			}
			if (!StringUtils.isEmpty(usersort) && usersortMap.containsKey(usersort)) {
				inincard.setUsersortName(usersortMap.get(usersort).getName1());
			}

			Smallsort smallsort = smallsortMap.get(inincard.getSmallsort());
			if (smallsort != null) {
				inincard.setSmallsortName(smallsort.getName());
			}
		}

		return inincardList;
	}

	@Override
	public ReturnStatus insertXiaofei(Userpart userpart) {
		String inincardId = userpart.getInincardId();
		if (StringUtils.isEmpty(inincardId)) {
			return new ReturnStatus(false, "子卡id不能为空");
		}
		Userinincard inincard = mongoTemplate.findById(inincardId, Userinincard.class);
		if (inincard == null) {
			return new ReturnStatus(false, "指定id的子卡信息不存在");
		}

		if (userpart.getCishu() > inincard.getShengcishu()) {
			return new ReturnStatus(false, "剩余次数不足");
		}
		
		Usersort usersort = mongoTemplate.findById(inincard.getMembersort(), Usersort.class);
		if (usersort == null) {
			return new ReturnStatus(false, "会员类别不存在");
		}
		inincard.setShengcishu(inincard.getShengcishu() - userpart.getCishu());
		if(null==userpart.getSomemoney1()){
			userpart.setSomemoney1(0.0);
		}
		userpart.setIdIfNew();
		userpart.setCreateTimeIfNew();
		userpart.setGuazhang_flag(true);
		userpart.setFlag2(false);
		userpart.setDelete_flag(false);
		userpart.setType(11); // 子卡充值类型
		userpart.setMembersort(inincard.getMembersort());
		userpart.setSmallsort(inincard.getSmallsort());
		userpart.setShengcishu(inincard.getShengcishu());
		userpart.setAllcishu(inincard.getAllcishu());
		userpart.setMoney4(inincard.getDanci_money() * inincard.getShengcishu());
		userpart.setUsersortType(usersort.getFlag1());

		if (!inincard.getOrganId().equals(userpart.getOrganId())) { // 跨店消费，消费记录保存办卡公司id
			userpart.setOwnerOrganId(inincard.getOrganId());
		}

		mongoTemplate.save(userpart);
		mongoTemplate.save(inincard);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus removeXiaofei(String id) {
		if (StringUtils.isEmpty(id)) {
			return new ReturnStatus(false, "子卡消费记录id不能为空");
		}
		Userpart userpart = mongoTemplate.findById(id, Userpart.class);
		if (userpart == null) {
			return new ReturnStatus(false, "指定id的子卡消费记录不存在");
		}

		Userinincard inincard = mongoTemplate.findById(userpart.getInincardId(), Userinincard.class);
		if (inincard == null) {
			return new ReturnStatus(false, "子卡信息不存在");
		}

		inincard.setShengcishu(inincard.getShengcishu() + userpart.getCishu());

		userpart.setDelete_flag(true);
		mongoTemplate.save(userpart);
		mongoTemplate.save(inincard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus jiezhang(String inincardId, int xiaocishu, double xianjin, double money_cash, double money3,
			String passwd) {
		if (StringUtils.isEmpty(inincardId)) {
			return new ReturnStatus(false, "子卡id不能为空!");
		}
		Userinincard inincard = mongoTemplate.findById(inincardId, Userinincard.class);
		if (inincard == null) {
			return new ReturnStatus(false, "子卡信息不存在!");
		}
		Usercard usercard = mongoTemplate.findById(inincard.getCardId(), Usercard.class);
		if (usercard == null) {
			return new ReturnStatus(false, "用户卡信息不存在!");
		}
		if (!usercard.getPasswd().equals(passwd)) {
			return new ReturnStatus(false, "卡密码错误!");
		}

		Usersort usersort = mongoTemplate.findById(inincard.getMembersort(), Usersort.class);
		if (usersort == null) {
			return new ReturnStatus(false, "会员类别不存在");
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag2").is(false), Criteria.where("guazhang_flag").is(true),
				Criteria.where("inincardId").is(inincardId));
		List<Userpart> userparts = mongoTemplate.find(Query.query(criteria), Userpart.class);
		for (Userpart userpart : userparts) {
			userpart.setFlag2(true); // 是否交款
			userpart.setGuazhang_flag(false); // 是否挂账
			userpart.setMoney3(money3);
			userpart.setMoney_cash(money_cash); // 现金

			mongoTemplate.save(userpart);

			// 生成跨店消费结算数据

			if (!StringUtils.isEmpty(userpart.getOwnerOrganId())) {
				// 跨店消费，生成公司间对账信息
				String flag1 = usersort.getFlag1();
				double amount = 0; // 折算金额
				if ("1001".equals(flag1)) { // 单纯打折卡
					userpart.setMoney2(userpart.getMoney_xiaofei()); // 实缴=消费金额
					// 单打折卡不需要跨店结算 amount += userpart.getMoney_xiaofei();
				} else if ("1002".equals(flag1)) { // 存钱打折卡
					amount += userpart.getMoney_xiaofei();
				} else if ("1003".equals(flag1)) { // 次数卡
					amount += userpart.getCishu() * inincard.getDanci_money(); // 次数卡折算金额
				}

				if (amount > 0) {
					UserpartCrossOrgan uco = new UserpartCrossOrgan();
					uco.setIdIfNew();
					uco.setCreateTimeIfNew();
					uco.setIncardId(inincard.getIncardId());
					uco.setInincardId(inincardId);
					uco.setUserpartId(userpart.get_id());
					uco.setOrganId(userpart.getOrganId());
					uco.setOwnerOrganId(userpart.getOwnerOrganId());
					uco.setStatus(0);
					uco.setOwnerStatus(0);
					uco.setAmount(amount);
					uco.setMemo("子卡消费");

					mongoTemplate.save(uco);
				}
			}
		}

		// mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public List<Userpart> queryUserpart(String inincardId) throws BaseException {
		if (StringUtils.isEmpty(inincardId)) {
			throw new BaseException("子卡id不能为空!");
		}
		Userinincard inincard = mongoTemplate.findById(inincardId, Userinincard.class);
		if (inincard == null) {
			throw new BaseException("子卡信息不存在!");
		}
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag2").is(false), Criteria.where("guazhang_flag").is(true),
				Criteria.where("inincardId").is(inincardId), Criteria.where("delete_flag").is(false));
		List<Userpart> userparts = mongoTemplate.find(Query.query(criteria), Userpart.class);
//		for (int i = 0; i <userparts.size() ; i++) {
//                try {
//                    System.out.println("resultList第"+i+"次");
//                    String jsonString = JSON.toJSONString(userparts.get(i));
//                    System.out.println(jsonString);
//                } catch (Exception e) {
//                    System.out.println("resultList第"+i+"次报错了啊啊啊啊啊");
//                    Userpart userpart = userparts.get(i);
//                    System.out.println("resultList的单个ID"+userpart.get_id()+"当前卡类型"+userpart.getType()+"服务项目"+userpart.getSmallsort()+"somemoney2是"+userpart.getSomemoney2()+
//                    "id_2是"+userpart.getId_2()+"IncardId是"+userpart.getIncardId()+"InincatdId是"+userpart.getInincardId()+"somemoney3是"+userpart.getSomemoney3());
//                    e.printStackTrace();
//                }
//            }
		return getUserpartDetails(userparts);
	}

	private List<Userpart> getUserpartDetails(List<Userpart> userparts) {
		List<String> usersortIds = new ArrayList<>();
		List<String> smallsortIds = new ArrayList<>();
		List<String> organIds = new ArrayList<>();
		for (Userpart userpart : userparts) {
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
		}

		Map<String, Usersort> usersortMap = new HashMap<>();
		List<Usersort> usersorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(usersortIds)),
				Usersort.class);
		for (Usersort usersort : usersorts) {
			usersortMap.put(usersort.get_id(), usersort);
		}

		for (Userpart userpart : userparts) {
			String usersort = userpart.getMembersort();
			String smallsort = userpart.getSmallsort();
			String organId = userpart.getOrganId();

			if (!StringUtils.isEmpty(usersort) && usersortMap.containsKey(usersort)) {
				userpart.setUsersortName(usersortMap.get(usersort).getName1());
			}
		}
		return userparts;
	}

	@Override
	public List<Userinincard> queryInCard(FlipInfo<Userinincard> fpi) throws BaseException {
		Query query = new Query();
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
		List<Userinincard> list = mongoTemplate.find(query, Userinincard.class);
		getDetails(list);
		return list;
	}

}
