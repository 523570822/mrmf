package com.mrmf.service.organ;

import com.mrmf.entity.*;
import com.mrmf.entity.sqlEntity.MrmfShop;
import com.mrmf.entity.sqlEntity.Prestore;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.PinYinUtil;
import com.mrmf.service.kaoqin.KaoqinService;
import com.mrmf.service.usercard.UsercardService;
import com.mrmf.sqlDao.MemberDao;
import com.mrmf.sqlDao.MrmfShopDao;
import com.mrmf.sqlDao.PrestoreDao;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.Constants;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.SMSUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("organService")
public class OrganServiceImpl implements OrganService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsercardService usercardService;

	@Autowired
	private KaoqinService kaoqinService;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PrestoreDao prestoreDao;
	@Autowired
	private MrmfShopDao mrmfShopDao;


	@Override
	public FlipInfo<Organ> query(FlipInfo<Organ> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi = mongoTemplate.findByPage(null, fpi, Organ.class);
		return fpi;
	}@Override
	public FlipInfo<Organ> queryByShopPhone(FlipInfo<Organ> fpi) throws BaseException {
		String sidx = fpi.getParams().get("sidx")==null?"":fpi.getParams().get("sidx").toString();
		fpi.setSortField("shopPrice");
		fpi.setSortOrder("DESC");
		if("shopPrice".equals(sidx)||"".equals(sidx)){
			addPrice();//把mysql数据给到 mongo
		}

		fpi = mongoTemplate.findByPage(null, fpi, Organ.class);
		return fpi;
	}
	public void addPrice(){
		List<MrmfShop> list = mrmfShopDao.findAll();
		for(MrmfShop mrmfShop : list){
			Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(mrmfShop.getOrganId())),Organ.class);
			organ.setShopPrice(mrmfShop.getPrestore());
			mongoTemplate.save(organ);
		}
	}
	public void findPrice(FlipInfo<Organ> fpi,String sidx){
		List<String> phones = new ArrayList<>();
		List<Organ> list = fpi.getData();
		//查到本页电话，in查询list
		for(Organ organ:list){
			phones.add(organ.get_id());
		}
		Map<String,Double> map = memberDao.findByIds(phones);
		for(Organ organ:list){
			organ.setShopPrice(map.get(organ.get_id()));
		}
		//根据sidx排序或者反转
		if("".equals(sidx)||"shopPrice".equals(sidx)){
			Collections.sort(list, new Comparator<Organ>() {
				@Override
				public int compare(Organ o1, Organ o2) {
					Double stu1=o1.getShopPrice();
					Double stu2=o2.getShopPrice();
					if(stu1<stu2){
						return 1;
					}else if(stu1==stu2){
						return 0;
					}else{
						return -1;
					}
				}
			});
			if("desc".equals(fpi.getParams().get("sord"))){
				Collections.reverse(list);
			}
		}

	}
	@Transactional
	@Override
	public ReturnStatus sysCharge(String _id, double charge) throws Exception{
		ReturnStatus status;
		if (StringUtils.isEmpty(_id)) {
			status = new ReturnStatus(false, "参数错误！");
		} else {
			Organ organ = queryById(_id);
			MrmfShop mrmfShop = mrmfShopDao.findByOrganId(organ.get_id());
			if (mrmfShop != null) {
				double yue = mrmfShop.getPrestore() + charge;
				if (yue < 0) {
					status = new ReturnStatus(false, "冲减后余额为负数！");
				} else {
					// 插入充值记录
					memberDao.updatePrestore(mrmfShop.getId(),new BigDecimal(yue));
					prestoreDao.save(charge,mrmfShop.getOrganId());
					status = new ReturnStatus(true);
				}
			} else {
				status = new ReturnStatus(false, "请先指定商城管理员手机号！");
			}
		}
		return status;
	}
	@Override
	public FlipInfo<Prestore> sysQueryChargeHis(FlipInfo<Prestore> fpi) throws BaseException{
		return prestoreDao.findByPage(fpi);
	}
	@Override
	public Organ queryById(String organId) throws BaseException {
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null)
			throw new BaseException("指定id的公司信息不存在");
		else
			return organ;
	}

	@Override
	public List<Organ> queryByIds(List<String> organIds) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
	}

	@Override
	public List<Organ> queryByParentId(String parentId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("parentId").in(parentId)), Organ.class);
	}

	@Override
	public Organ queryByAdminId(String adminId) throws BaseException {
		Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("adminId").is(adminId)), Organ.class);
		return organ;
	}

	@Override
	public ReturnStatus addIp(String organId, String ip) {
		ReturnStatus status;
		try {
			Organ organ = queryById(organId);
			if (!organ.getIps().contains(ip)) {
				organ.getIps().add(ip);
				mongoTemplate.save(organ);
			}

			status = new ReturnStatus(true);
		} catch (BaseException e) {
			status = new ReturnStatus(false, e.getMessage());
		}

		return status;
	}

	@Override
	public ReturnStatus upsert(Organ organ) {
		ReturnStatus status;
		// 获取管理员口令
		String accountName = organ.getAdminId();
		String passwd = organ.getPasswd();
		organ.setPasswd(null);
		Boolean flag = false;
		if (StringUtils.isEmpty(organ.get_id())) { // 新建
			flag = true;
			organ.setIdIfNew();
			organ.setCreateTimeIfNew();
			kaoqinService.initKaoqinByOrgan(organ);
			long count = mongoTemplate.count(Query.query(Criteria.where("accountName").is(accountName)), Account.class);
			if (count > 0) {
				status = new ReturnStatus(false);
				status.setMessage("已存在同名的账号");
				return status;
			} else {
				// 创建公司管理员账号
				Account account = new Account();
				account.set_id(organ.get_id());
				account.setAccountName(accountName);
				account.setAccountPwd(passwd);
				account.setAccountType(Account.TYPE_ORGAN_ADMIN);
				account.getRoleIds().add(Constants.getProperty("system.organ.admin.roleId")); // 默认企业管理员角色，全企业管理功能
				account.setCreateTimeIfNew();

				account.setEntityID(organ.get_id());
				account.setStatus(Account.STATUS_ACTIVE);

				accountService.insertAccount(account);
			}

		} else { // 修改
			Organ oldOrgan = mongoTemplate.findById(organ.get_id(), Organ.class);
			if (oldOrgan != null) {
				// 修改公司管理员账号口令
				if (!StringUtils.isEmpty(passwd)) {
					accountService.changePasswordByAccountName(accountName, passwd);
				}

				organ.setCardOrganIds(oldOrgan.getCardOrganIds());

				// 微信相关字段数据保持原有
				organ.setWalletAmount(oldOrgan.getWalletAmount());
				organ.setPayPassword(oldOrgan.getPayPassword());
				organ.setState(oldOrgan.getState());
				organ.setBusyTimeStart(oldOrgan.getBusyTimeStart());
				organ.setBusyTimeEnd(oldOrgan.getBusyTimeEnd());
				// organ.setGpsPoint(oldOrgan.getGpsPoint());

				organ.setZanCount(oldOrgan.getZanCount());
				organ.setQiuCount(oldOrgan.getQiuCount());
				organ.setFollowCount(oldOrgan.getFollowCount());

				organ.setIps(oldOrgan.getIps());

				// 手机号修改则发送通知短信给老手机号
				if (!StringUtils.isEmpty(oldOrgan.getPhone()) && !StringUtils.isEmpty(organ.getPhone())
						&& !oldOrgan.getPhone().equals(organ.getPhone())) {
					Map<String, String> params = new HashMap<>();
					params.put("name", organ.getName());
					params.put("phone", organ.getPhone());
				//需要删除当前店铺所有店长 放到bak表  以后在这里加消息推送
					delAccount(organ.get_id(),"organ");
					ReturnStatus s = SMSUtil.send(oldOrgan.getPhone(),
							Constants.getProperty("sms.modifyOrganPhoneTemplate"), params);
					if (s.isSuccess()) {
						// ignore it
					}
				}

			} else {
				status = new ReturnStatus(false);
				status.setMessage("要修改的公司信息不存在");
				return status;
			}
		}
		if (!StringUtils.isEmpty(organ.getAbname())) {
			String zjfCode = PinYinUtil.getFirstSpell(organ.getAbname());
			organ.setAb(zjfCode);
		} else {
			String zjfCode = PinYinUtil.getFirstSpell(organ.getName());
			organ.setAb(zjfCode);
		}
		//清空当前店面微信绑定手机号其他店面绑定信息
		List<Organ> organList = mongoTemplate.find(Query.query(Criteria.where("phone").is(organ.getPhone())),Organ.class);
		for (Organ organ1:organList){
			delAccount(organ1.get_id(),"organ");
		}
		mongoTemplate.save(organ);
		if (flag) {
			// 保存成功后给店面增加默认大类
			System.err.println("当前店铺id是:" + organ.get_id());
			List<String> typeList = organ.getType();// 得到店面类型
			for (String typeName : typeList) {
				String type = new String();// 遍历店面服务类型转换成平台搜索条件
				if (typeName.equals("美容")) {
					type = "meiRongType";
				} else if (typeName.equals("美发")) {
					type = "hairType";
				} else if (typeName.equals("美甲")) {
					type = "meijiaType";
				} else {
					type = "zuLiaoType";
				}
				List<Code> codes = mongoTemplate.find(Query.query(Criteria.where("type").is(type)), Code.class);
				for (Code code : codes) {
					Bigsort big = new Bigsort();
					big.setOrganId(organ.get_id());// 赋值店面id
					big.setName(code.getName());// 设置大类名称
					big.setTypeName(typeName);// 设置店铺类型
					big.setCodeName(code.getName());// 设置平台名称
					big.setCodeId(code.get_id());// 设置平台类型id
					mongoTemplate.save(big);
				}
			}
		}
		try {
			Usercard card = usercardService.sysQuery(organ.get_id());
			card.setOrganName(organ.getName());
			card.setOrganAbname(organ.getAbname());
			mongoTemplate.save(card);
		} catch (BaseException e) {
			// ignore it，不会发生此情况
		}
		status = new ReturnStatus(true);
		status.setEntity(organ);
		return status;
	}
	public void delAccount(String organId,String type){
		List<Account> list = accountService.getAccountsByEntityID(organId,type);
		Bak bak = new Bak();
		if(list !=null){
			for(Account acc:list){
				bak.setEntityID(acc.getEntityID());
				bak.setName("Account");
				bak.set_id(acc.get_id());
				bak.setAccountType("organ");
				bak.setWeUnionId(acc.getWeUnionId());
				bak.setAccountName(acc.getAccountName());
				bak.setAccountPwd(acc.getAccountPwd());
				bak.setStatus(acc.getStatus());
				mongoTemplate.save(bak);
				mongoTemplate.remove(acc);
			}
		}
	}
	@Override
	public ReturnStatus changeState(String organId, int state) {
		try {
			Organ organ = queryById(organId);
			organ.setState(state);
			mongoTemplate.save(organ);
			return new ReturnStatus(true);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	@Override
	public ReturnStatus changeCardOrganIds(String organId, List<String> cardOrganIds) {
		try {
			Organ organ = queryById(organId);
			organ.setCardOrganIds(cardOrganIds);
			mongoTemplate.save(organ);
			return new ReturnStatus(true);
		} catch (BaseException e) {
			return new ReturnStatus(false, e.getMessage());
		}
	}

	public List<Organ> queryAllPartner(String organId) throws BaseException {
		Organ organ = queryById(organId);
		String zongbuId;
		if (organ.getZongbu()) { // 总部
			zongbuId = organId;
		} else {
			zongbuId = organ.getParentId();
			organ = queryById(zongbuId); // 查询总部
		}

		List<Organ> organList = new ArrayList<>();
		if (!organId.equals(organ.get_id()))
			organList.add(organ);
		List<Organ> subOrganList = mongoTemplate.find(Query.query(Criteria.where("parentId").is(zongbuId)),
				Organ.class);
		for (Organ og : subOrganList) {
			if (!organId.equals(og.get_id()))
				organList.add(og);
		}

		return organList;
	}

	@Override
	public ReturnStatus enable(String organId) {
		ReturnStatus result;
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null) {
			result = new ReturnStatus(false);
			result.setMessage("指定id的公司信息不存在");
		} else {
			organ.setValid(1);
			mongoTemplate.save(organ);
			result = new ReturnStatus(true);
			result.setEntity(organ); // 返回更新后的对象
		}

		return result;
	}

	@Override
	public ReturnStatus disable(String organId) {
		ReturnStatus result;
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ == null) {
			result = new ReturnStatus(false);
			result.setMessage("指定id的公司信息不存在");
		} else {
			organ.setValid(0);
			mongoTemplate.save(organ);
			result = new ReturnStatus(true);
			result.setEntity(organ); // 返回更新后的对象
		}

		return result;
	}

	@Override
	public ReturnStatus verifycode(String phone) {
		Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Organ.class);
		if (organ == null) {
			return new ReturnStatus(false, "指定手机号未关联到公司信息！");
		}
		return accountService.verifycode(phone);
	}

	@Override
	public ReturnStatus verify(String openId, String unionId, String phone, String code) {
		Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Organ.class);
		if (organ == null) {
			return new ReturnStatus(false, "指定手机号未关联到公司信息！");
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("type").is("organ"), Criteria.where("accountName").is(openId));

		boolean exist = mongoTemplate.exists(Query.query(criteria), Account.class);
		if (exist) {
			return new ReturnStatus(false, "此微信号已经绑定过公司管理账号！");
		}

		ReturnStatus status = accountService.verify(phone, code);
		if (status.isSuccess()) {
			// 绑定新的微信管理账号
			Account account = new Account();
			account.setIdIfNew();
			account.setCreateTimeIfNew();
			account.setAccountName(openId);
			account.setAccountPwd(passwordEncoder.encodePassword(openId, null));
			account.setAccountType("organ"); // 公司管理员类型
			account.setWeUnionId(unionId);
			account.setEntityID(organ.get_id());
			account.setStatus("1");
			mongoTemplate.save(account);
		}

		return status;
	}

	@Override
	public OrganSetting querySetting(String organId) throws BaseException {
		if (StringUtils.isEmpty(organId)) {
			throw new BaseException("公司id不能为空");
		}

		// 验证公司是否存在
		queryById(organId);

		OrganSetting setting = mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId)),
				OrganSetting.class);
		if (setting == null) {
			setting = new OrganSetting();
			setting.setIdIfNew();
			setting.setCreateTimeIfNew();
			setting.setOrganId(organId);
			setting.setCanTixian(true); // 默认允许提现
			setting.setDisplayTicheng(true); // 默认显示员工提成

			// 默认提成计算包含流水（散客、外卖、会员卡、办卡、续费、会员卡外卖、免单流水）
			List<String> tichengLiushuiList = setting.getTichengLiushui();
			if (tichengLiushuiList == null) {
				tichengLiushuiList = new ArrayList<String>();
				tichengLiushuiList.add("sanke");
				tichengLiushuiList.add("waimai");
				tichengLiushuiList.add("huiyuanka");
				tichengLiushuiList.add("banka");
				tichengLiushuiList.add("xufei");
				tichengLiushuiList.add("huiyuankawaimai");
				// tichengLiushuiList.add("miandan");
				setting.setTichengLiushui(tichengLiushuiList);
			}
			mongoTemplate.save(setting);
		} else {
			// 默认提成计算包含流水（散客、外卖、会员卡、办卡、续费、会员卡外卖、免单流水）
			List<String> tichengLiushuiList = setting.getTichengLiushui();
			if (tichengLiushuiList == null) {
				tichengLiushuiList = new ArrayList<String>();
				tichengLiushuiList.add("sanke");
				tichengLiushuiList.add("waimai");
				tichengLiushuiList.add("huiyuanka");
				tichengLiushuiList.add("banka");
				tichengLiushuiList.add("xufei");
				tichengLiushuiList.add("huiyuankawaimai");
				// tichengLiushuiList.add("miandan");
				setting.setTichengLiushui(tichengLiushuiList);
			}
			mongoTemplate.save(setting);
		}

		return setting;
	}

	@Override
	public ReturnStatus upsertSetting(OrganSetting setting) {
		OrganSetting oldSetting = mongoTemplate.findById(setting.get_id(), OrganSetting.class);
		if (oldSetting != null) { // 续传提成流水设置，不在此处设置，在浮动提成设置中
			setting.setTichengLiushui(oldSetting.getTichengLiushui());
		}
		setting.setUpdateTimeIfNew();
		mongoTemplate.save(setting);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus upsertSettingTichengLiushui(String settingId, List<String> tichengLiushui) {
		OrganSetting setting = mongoTemplate.findById(settingId, OrganSetting.class);
		if (setting == null) {
			return new ReturnStatus(false, "指定公司设置信息不存在");
		} else {
			setting.setTichengLiushui(tichengLiushui);
			mongoTemplate.save(setting);
			return new ReturnStatus(true);
		}
	}

	@Override
	public List<String> queryAdminOrganIds(Account account) throws BaseException {
		List<String> ids = new ArrayList<String>();
		if ("admin".equals(account.getAccountType())) {
			List<String> cityList = account.getCityList();
			List<String> districtList = account.getDistrictList();
			List<Criteria> cs = new ArrayList<Criteria>();
			if (cityList.size() > 0) {
				cs.add(Criteria.where("city").in(cityList));
			}
			if (districtList.size() > 0) {
				cs.add(Criteria.where("district").in(districtList));
			}

			if (cs.size() > 0) {
				Criteria c = new Criteria();
				c.andOperator(cs.toArray(new Criteria[cs.size()]));
				List<Organ> organs = mongoTemplate.find(Query.query(c), Organ.class);
				for (Organ organ : organs) {
					ids.add(organ.get_id());
				}
				if (ids.size() == 0) // 相当于有区域限制，但是还没有店铺，加个不存在的店铺id
					ids.add("-1");
			}
		}
		return ids;
	}

	@Override
	public List<Organ> queryOrganListByParentId(String parentId) {
		List<Organ> organList = mongoTemplate.find(Query.query(Criteria.where("parentId").is(parentId)), Organ.class);
		return organList;
	}
	@Override
	public List<Organ> queryOrganList() {
		List<Organ> organList = mongoTemplate.findAll( Organ.class);
		return organList;
	}
}
