package com.mrmf.service.user.incard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.OrganSetting;
import com.mrmf.entity.User;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.ElecCardNum;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.UserpartCrossOrgan;
import com.mrmf.entity.user.Usersort;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.user.userpart.UserpartService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service("incardService")
public class IncardServiceImpl implements IncardService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserpartService userpartService;

	@Autowired
	private OrganService organService;

	@Override
	public Userincard queryById(String id) throws BaseException {
		Userincard userincard = mongoTemplate.findById(id, Userincard.class);
		if (userincard == null) {
			throw new BaseException("指定id的会员卡不存在!");
		}

		User user = mongoTemplate.findById(userincard.getUserId(), User.class);
		if (user == null) {
			throw new BaseException("用户信息不存在");
		}

		Usersort usersort = mongoTemplate.findById(userincard.getMembersort(), Usersort.class);
		if (usersort == null) {
			throw new BaseException("会员类别不存在");
		}

		if (StringUtils.isEmpty(userincard.getName())) {
			List<Userpart> userparts = userpartService.queryByIncardId(userincard.get_id(), 0, true,false);

			if (userparts.size() > 0) {
				userincard.setName(userparts.get(0).getName());
				// userincard.setName(StringUtils.isEmpty(user.getName()) ?
				// user.getNick() : user.getName());
			}
		}
		userincard.setFlag1(usersort.getFlag1());

		return userincard;
	}

	@Override
	public ReturnStatus jiezhang(String incardId, int xiaocishu, double xianjin, double huaka, double money_cash,
			double money_yinhang_money, double money3, String passwd) {
		if (StringUtils.isEmpty(incardId)) {
			return new ReturnStatus(false, "消卡id不能为空!");
		}
		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "会员卡信息不存在!");
		}
		Usercard usercard = mongoTemplate.findById(incard.getCardId(), Usercard.class);
		if (usercard == null) {
			return new ReturnStatus(false, "用户卡信息不存在!");
		}
		if (!usercard.getPasswd().equals(passwd)) {
			return new ReturnStatus(false, "卡密码错误!");
		}

		Usersort usersort = mongoTemplate.findById(incard.getMembersort(), Usersort.class);
		if (usersort == null) {
			return new ReturnStatus(false, "会员类别不存在");
		}

		List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("incardId").is(incardId).and("type").is(0)),Userpart.class);
		Userpart oldUserpart = userpartList.get(0);

		String flag1 = usersort.getFlag1();
		if ("1001".equals(flag1)) { // 单纯打折卡

		} else if ("1002".equals(flag1)) { // 存钱打折卡 在保存动作做了
//			oldUserpart.setNowMoney4(incard.getMoney4());
//			mongoTemplate.save(oldUserpart);
			// incard.setMoney4(incard.getMoney4() - huaka);

		} else if ("1003".equals(flag1)) { // 次数卡    在保存动作做了
			// incard.setShengcishu(incard.getShengcishu() - xiaocishu);
//			oldUserpart.setNowShengcishu(incard.getShengcishu());
//			oldUserpart.setNowMoney4(incard.getMoney4());
//			mongoTemplate.save(oldUserpart);
		} else {
			return new ReturnStatus(false, "会员卡类型不能为空!");
		}
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("flag2").is(false), Criteria.where("guazhang_flag").is(true),
				Criteria.where("incardId").is(incardId));
		List<Userpart> userparts = mongoTemplate.find(Query.query(criteria), Userpart.class);
		//根据主卡id查询消费记录
		List<String> uids = new ArrayList<>();
		for (Userpart userpart : userparts) {
			uids.add(userpart.get_id());
			userpart.setFlag2(true); // 是否交款
			userpart.setGuazhang_flag(false); // 是否挂账
			userpart.setMoney3(money3);
			userpart.setMoney_yinhang_money(money_yinhang_money); // 银行卡金额
			userpart.setMoney_cash(money_cash); // 现金
			userpart.setMoney2(userpart.getMoney1() - userpart.getMoney5()); // 实缴=消费金额-抹零

			mongoTemplate.save(userpart);

			// 生成消费消息到用户个人消息中
			WeMessage msg = new WeMessage();
			msg.setIdIfNew();
			msg.setCreateTimeIfNew();
			msg.setFromType("rxm");
			msg.setFromId("0");
			msg.setFromName("系统");
			msg.setToType("user");
			msg.setToName(userpart.getName());
			msg.setToId(userpart.getUserId());
			msg.setType("1");
			if ("1001".equals(flag1)) { // 单纯打折卡
				msg.setContent("单打折卡消费金额:" + userpart.getMoney1());
			} else if ("1002".equals(flag1)) { // 存钱打折卡
				msg.setContent("存钱打折卡消费金额:" + userpart.getMoney1());
			} else if ("1003".equals(flag1)) { // 次数卡
				msg.setContent("次数卡消费次数:" + userpart.getCishu());
			}

			mongoTemplate.save(msg);

			if (!StringUtils.isEmpty(userpart.getOwnerOrganId())) {
				// 跨店消费，生成公司间对账信息
				double amount = 0; // 折算金额
				if ("1001".equals(flag1)) { // 单纯打折卡
					// 单打折不需要跨店结算 amount += userpart.getMoney_xiaofei();
				} else if ("1002".equals(flag1)) { // 存钱打折卡
					amount += userpart.getMoney_xiaofei();
				} else if ("1003".equals(flag1)) { // 次数卡
					amount += userpart.getCishu() * incard.getDanci_money(); // 次数卡折算金额
				}

				if (amount > 0) {
					UserpartCrossOrgan uco = new UserpartCrossOrgan();
					uco.setIdIfNew();
					uco.setCreateTimeIfNew();
					uco.setIncardId(incardId);
					uco.setUserpartId(userpart.get_id());
					uco.setOrganId(userpart.getOrganId());
					uco.setOwnerOrganId(userpart.getOwnerOrganId());
					uco.setStatus(0);
					uco.setOwnerStatus(0);
					uco.setAmount(amount);
					uco.setMemo("会员卡消费");

					mongoTemplate.save(uco);
				}
			}
		}

		// 设置使用物品的产品出库结账状态
		criteria = new Criteria();
		criteria.andOperator(Criteria.where("userpartId").in(uids), Criteria.where("delete_flag").is(false));
		List<WOutstoreroom> outroomList = mongoTemplate.find(Query.query(criteria), WOutstoreroom.class);
		for (WOutstoreroom outroom : outroomList) {
			outroom.setGuazhang_flag(false);
			mongoTemplate.save(outroom);
		}

		// 设置外卖和外卖出库结账状态
		String kaidanId = incardId;
		criteria = new Criteria();
		criteria.andOperator(Criteria.where("kaidanId").is(kaidanId), Criteria.where("guazhang_flag").is(true),
				Criteria.where("delete_flag").is(false));
		List<WWaimai> waimaiList = mongoTemplate.find(Query.query(criteria), WWaimai.class);
		double wm = 0;
		for (WWaimai waimai : waimaiList) {
			waimai.setGuazhang_flag(false);
			waimai.setIsCard(true); // 会员卡外卖标记
			mongoTemplate.save(waimai);
			if (!waimai.getMiandan()) { // 非免单计费
				wm += waimai.getMoney1() - waimai.getMoney_qian();
			}
		}

		if ("1002".equals(flag1)) { // 存钱打折卡，则外卖用卡里余额支付
			double m4 = incard.getMoney4();
			if (m4 >= wm) {
				incard.setMoney4(m4 - wm);
			} else {
				incard.setMoney4(0);
			}
			mongoTemplate.save(incard);
			Userpart userpart = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class).get(0);
			userpart.setNowMoney4(incard.getMoney4());
			mongoTemplate.save(userpart);
		}

		outroomList = mongoTemplate.find(Query.query(criteria), WOutstoreroom.class);
		for (WOutstoreroom outroom : outroomList) {
			outroom.setGuazhang_flag(false);
			mongoTemplate.save(outroom);
		}

		// mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus xufei(Userpart userpart) {
		String incardId = userpart.getIncardId();
		if (StringUtils.isEmpty(incardId)) {
			return new ReturnStatus(false, "卡id不能为空!");
		}

		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "会员卡信息不存在!");
		}

		userpart.setIdIfNew();
		userpart.setCreateTimeIfNew();
		userpart.setFlag2(true);
		userpart.setGuazhang_flag(false);
		userpart.setDelete_flag(false);
		userpart.setType(3);
		userpart.setSong_money(userpart.getSongMoney());

		int cishu = userpart.getCishu();
		double money = userpart.getMoney_cash() + userpart.getMoney_yinhang_money();
		userpart.setMoney1(money);
		userpart.setMoney2(money);

		incard.setAllcishu(incard.getAllcishu() + cishu);
		incard.setShengcishu(incard.getShengcishu() + cishu);
		incard.setMoney4(incard.getMoney4() + money);
		incard.setXu_cishu(incard.getXu_cishu() + 1); // 续费次数
		incard.setMoney_leiji(incard.getMoney_leiji() + money); // 累计消费金额
		incard.setMoney_qian(incard.getMoney_qian() + userpart.getMoney_qian()); // 欠费
		incard.setSong_money(incard.getSong_money()+userpart.getSongMoney());
		if (userpart.getLaw_date() > 0)
			incard.setLaw_day(DateUtil.addDate(DateUtil.currentDate(), userpart.getLaw_date()));
		else
			incard.setLaw_day(null);
		
		userpart.setMoney4(incard.getMoney4());
		userpart.setAllcishu(incard.getAllcishu());
		userpart.setShengcishu(incard.getShengcishu());
		userpart.setXu_cishu(incard.getXu_cishu());

		if (!incard.getOrganId().equals(userpart.getOrganId())) { // 跨店消费，消费记录保存办卡公司id
			userpart.setOwnerOrganId(incard.getOrganId());

			// 生成公司间对账信息
			UserpartCrossOrgan uco = new UserpartCrossOrgan();
			uco.setIdIfNew();
			uco.setCreateTimeIfNew();
			uco.setIncardId(incardId);
			uco.setUserpartId(userpart.get_id());
			uco.setOrganId(userpart.getOrganId());
			uco.setOwnerOrganId(userpart.getOwnerOrganId());
			uco.setStatus(0);
			uco.setOwnerStatus(0);
			uco.setAmount(money * -1); // 消费公司向办卡公司付会员卡充值的钱
			uco.setMemo("会员卡续费");

			mongoTemplate.save(uco);
		}
		//续费更新type=0 的nowMoney4 字段
		List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("type").is(0).and("incardId").is(incard.get_id())),Userpart.class);
		Userpart oldUserPart = userpartList.get(0);
		oldUserPart.setNowShengcishu(oldUserPart.getNowShengcishu()+cishu);
		oldUserPart.setNowMoney4(money+oldUserPart.getNowMoney4());
		oldUserPart.setAllcishu(oldUserPart.getAllcishu()+cishu);
		oldUserPart.setNowSong_money(incard.getSong_money());
		mongoTemplate.save(oldUserPart);

		mongoTemplate.save(userpart);
		mongoTemplate.save(incard);

		ReturnStatus status = new ReturnStatus(true);
		status.setData(userpart.get_id());
		return status;
	}

	@Override
	public ReturnStatus xufeiRemove(String userpartId, String code) {
		Userpart userpart = mongoTemplate.findById(userpartId, Userpart.class);
		if (userpart == null) {
			return new ReturnStatus(false, "会员卡续费信息不存在!");
		}
		if (userpart.getType() != 3) {
			return new ReturnStatus(false, "消息类型不是会员卡充值!");
		}

		Userincard incard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "会员卡信息不存在!");
		}

		ReturnStatus status = verify(incard.get_id(), code);
		if (!status.isSuccess()) {
			return status;
		}

		incard.setAllcishu(incard.getAllcishu() - userpart.getCishu());
		incard.setShengcishu(incard.getShengcishu() - userpart.getCishu());
		incard.setMoney4(incard.getMoney4() - (userpart.getMoney_cash() + userpart.getMoney_yinhang_money()));
		incard.setXu_cishu(incard.getXu_cishu() - 1);
		incard.setMoney_qian(incard.getMoney_qian() - userpart.getMoney_qian());

		userpart.setDelete_flag(true);
		mongoTemplate.save(userpart);
		mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus verifycode(String incardId) {
		try {
			String phone = getPhone(incardId, 0);
			ReturnStatus status = accountService.verifycode(phone);
			status.setData(phone);
			return status;
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	@Override
	public ReturnStatus verifycodeOrgan(String incardId) {
		try {
			String phone = getPhone(incardId, 1);
			ReturnStatus status = accountService.verifycode(phone);
			status.setData(phone);
			return status;
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	private ReturnStatus verify(String incardId, String code) {
		try {
			return accountService.verify(getPhone(incardId, 0), code);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	private ReturnStatus verifyOrgan(String incardId, String code) {
		try {
			return accountService.verify(getPhone(incardId, 1), code);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	private String getPhone(String incardId, int type) throws BaseException {
		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			throw new BaseException("会员卡信息不存在!");
		}

		String phone;
		if (type == 0) {// 用户手机号验证
			User user = mongoTemplate.findById(incard.getUserId(), User.class);
			if (user == null) {
				throw new BaseException("用户信息不存在!");
			}
			phone = user.getPhone();
			if (StringUtils.isEmpty(phone)) {
				throw new BaseException("用户手机号不能为空!");
			}
		} else if (type == 1) {// 店铺手机号验证
			Organ organ = organService.queryById(incard.getOrganId());
			phone = organ.getPhone();
			if (StringUtils.isEmpty(phone)) {
				throw new BaseException("店铺手机号不能为空!");
			}
		} else {
			throw new BaseException("手机号验证规则不支持!");
		}
		return phone;
	}

	@Override
	public ReturnStatus changeId_2(String incardId, String id_2) {
		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "指定卡号的会员卡不存在!");
		}

		// 修改消费记录的卡号，以支持会员管理查询
		List<Userpart> userparts = mongoTemplate.find(Query.query(Criteria.where("incardId").is(incardId)),
				Userpart.class);
		for (Userpart userpart : userparts) {
			userpart.setId_2(id_2);
			mongoTemplate.save(userpart);
		}

		incard.setId_2(id_2);
		mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus changePasswd(String incardId, String passwd, String code) {
		ReturnStatus status = verify(incardId, code);
		if (!status.isSuccess()) {
			return status;
		}

		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "指定卡号的会员卡不存在!");
		}

		Usercard usercard = mongoTemplate.findById(incard.getCardId(), Usercard.class);
		if (usercard == null) {
			return new ReturnStatus(false, "指定会员卡关联信息不存在!");
		}

		usercard.setPasswd(passwd);
		mongoTemplate.save(usercard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus qianfei(Userpart userpart,String partId) {

		Userincard incard = mongoTemplate.findById(userpart.getIncardId(), Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "会员卡信息不存在!");
		}

		// 补缴欠费金额
		double m = userpart.getMoney_cash() + userpart.getMoney_yinhang_money();

		incard.setMoney_qian(incard.getMoney_qian() - m); // 偿还欠费
		incard.setMoney4(incard.getMoney4() + m); // 增加余额

		userpart.set_id(Userpart.getLongUUID());
		userpart.setCreateTimeIfNew();
		userpart.setType(4); // 补缴欠费类型
		userpart.setMoney1(m);
		userpart.setMoney2(m); // 增加店铺收入
		userpart.setFlag2(true);
		userpart.setGuazhang_flag(false);
		userpart.setDelete_flag(false);

		//更新办卡的钱
		Userpart oldUserpart = mongoTemplate.findById(partId,Userpart.class);
		oldUserpart.setNowMoney4(oldUserpart.getNowMoney4()+m);//卡余额
		oldUserpart.setMoney_qian(oldUserpart.getMoney_qian()-m);//卡欠费
		oldUserpart.setMoney2(oldUserpart.getMoney2()+m);//实缴金额

		mongoTemplate.save(oldUserpart);
		mongoTemplate.save(userpart);
		mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus changeCardType(String incardId, String usersortId) {
		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		Usersort usersort = mongoTemplate.findById(usersortId,Usersort.class);
		Query query = new Query(Criteria.where("incardId").is(incardId).and("type").is(0));
//		Userpart userpart = mongoTemplate.findOne(query,Userpart.class);
		if (incard == null) {
			return new ReturnStatus(false, "会员卡信息不存在!");
		}
		incard.setMembersort(usersortId);
		incard.setZhekou(usersort.getZhekou());
		Update update = new Update();
		update.set("usersortType",incard.getFlag1());
		update.set("usersortName",usersort.getName1());
		update.set("membersort",usersort.get_id());
		update.set("incardId",incardId);
		mongoTemplate.updateFirst(query,update,Userpart.class);
		mongoTemplate.save(incard);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus tixian(String incardId, double tixian) {
		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "会员卡信息不存在!");
		}

		double ti = tixian * -1;

		incard.setMoney4(incard.getMoney4() + ti);

		// 创建负值冲减提现金额
		Userpart userpart = new Userpart();
		userpart.setIdIfNew();
		userpart.setCreateTimeIfNew();
		userpart.setOrganId(incard.getOrganId());
		userpart.setUserId(incard.getUserId());
		userpart.setCardId(incard.getCardId());
		userpart.setCardno(incard.getCardno());
		userpart.setId_2(incard.getId_2());
		userpart.setMoney4(incard.getMoney4());
		userpart.setMembersort(incard.getMembersort());
		userpart.setFlag2(true);
		userpart.setGuazhang_flag(false);
		userpart.setDelete_flag(false);
		userpart.setMoney1(ti);
		userpart.setMoney2(ti);
		userpart.setType(5); // 提现类型
		//提现更新userpart
		List<Userpart> userpartList = mongoTemplate.find(Query.query(Criteria.where("incardId").is(incard.get_id()).and("type").is(0)),Userpart.class);
		Userpart oldUserpart = userpartList.get(0);
		oldUserpart.setNowMoney4(oldUserpart.getNowMoney4()+ti);
		mongoTemplate.save(oldUserpart);

		mongoTemplate.save(userpart);
		mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus remove(String incardId, String code) {
		ReturnStatus status = verifyOrgan(incardId, code);
		if (!status.isSuccess()) {
			return status;
		}

		Userincard incard = mongoTemplate.findById(incardId, Userincard.class);
		if (incard == null) {
			return new ReturnStatus(false, "指定卡号的会员卡不存在!");
		}

		// 设置消费信息为已删除状态
		List<Userpart> userparts = mongoTemplate.find(Query.query(Criteria.where("incardId").is(incardId)),
				Userpart.class);
		for (Userpart userpart : userparts) {
			userpart.setDelete_flag(true);
			mongoTemplate.save(userpart);
		}

		incard.setDelete_flag(true);
		mongoTemplate.save(incard);

		return new ReturnStatus(true);
	}

	@Override
	public String getElecCardNum(String organId) throws BaseException {
		ElecCardNum cn = mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId)), ElecCardNum.class);
		if (cn == null) {
			cn = new ElecCardNum();
			cn.setOrganId(organId);
		}

		int seq = cn.getSeq() + 1;
		String seqStr = "" + seq;
		// 过滤掉带4和7的卡号
		while (seqStr.indexOf("4") != -1 || seqStr.indexOf("7") != -1) {
			seq++;
			seqStr = "" + seq;
		}
		cn.setSeq(seq);
		mongoTemplate.save(cn);

		OrganSetting os = organService.querySetting(organId);
		int l = os.getElecCardLength();
		String s = String.valueOf(seq);

		while (s.length() < l) {
			s = "0" + s;
		}

		return s;
	}

}
