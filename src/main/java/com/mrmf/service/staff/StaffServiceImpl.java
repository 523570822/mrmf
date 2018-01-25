package com.mrmf.service.staff;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import com.mrmf.entity.*;
import com.mrmf.entity.kaoqin.KBancidingyi;
import com.mrmf.entity.kaoqin.KPaiban;
import com.mrmf.entity.relevance.UserRelevanceStaff;
import com.mrmf.entity.staff.Staffpost;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.service.VipMember.VipMemberService;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.PinYinUtil;
import com.mrmf.service.organ.OrganService;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service("staffService")
public class StaffServiceImpl implements StaffService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OrganService organService;
	@Autowired
	private VipMemberService vipMemberService;

	@Override
	public FlipInfo<Staff> query(FlipInfo<Staff> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi = mongoTemplate.findByPage(Query.query(Criteria.where("deleteFlag").ne(true)), fpi, Staff.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<Staff> datas) {
		List<String> bumenIds = new ArrayList<String>();
		List<String> staffpostIds = new ArrayList<String>();
		for (Staff staff : datas) {
			if (!bumenIds.contains(staff.getBumenId()))
				bumenIds.add(staff.getBumenId());
			if (!staffpostIds.contains(staff.getDutyId()))
				staffpostIds.add(staff.getDutyId());
		}

		Map<String, Bumen> bumenMap = new HashMap<>();
		List<Bumen> bumens = mongoTemplate.find(Query.query(Criteria.where("_id").in(bumenIds)), Bumen.class);
		for (Bumen bumen : bumens) {
			bumenMap.put(bumen.get_id(), bumen);
		}

		Map<String, Staffpost> staffpostMap = new HashMap<>();
		List<Staffpost> staffposts = mongoTemplate.find(Query.query(Criteria.where("_id").in(staffpostIds)),
				Staffpost.class);
		for (Staffpost staffpost : staffposts) {
			staffpostMap.put(staffpost.get_id(), staffpost);
		}

		for (Staff staff : datas) {
			Bumen bumen = bumenMap.get(staff.getBumenId());
			if (bumen != null)
				staff.setBumenName(bumen.getName());
			Staffpost staffpost = staffpostMap.get(staff.getDutyId());
			if (staffpost != null)
				staff.setDutyName(staffpost.getName());
		}

	}

	@Override
	public Staff queryById(String staffId) throws BaseException {
		Staff hospital = mongoTemplate.findById(staffId, Staff.class);
		if (hospital == null)
			throw new BaseException("指定id的员工信息不存在");
		else
			return hospital;
	}

	@Override
	public ReturnStatus upsert(Staff staff) {
		ReturnStatus status;
		Staff oldStaff = null;
		String oldPhone = "";
		if (StringUtils.isEmpty(staff.get_id())) { // 新建
			staff.setIdIfNew();
			staff.setCreateTimeIfNew();

			String organId = staff.getOrganId();
			if (StringUtils.isEmpty(organId)) {
				status = new ReturnStatus(false, "organId不能为空");
				status.setEntity(staff);
				return status;
			} else {
				// 设置员工parentId，即总公司id信息
				Organ organ = mongoTemplate.findById(organId, Organ.class);
				if (organ == null) {
					status = new ReturnStatus(false, "organId指定的公司信息不存在");
					status.setEntity(staff);
					return status;
				} else {
					staff.setParentId(organ.getParentId());
					GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(),
							organ.getGpsPoint().getLongitude());
					staff.setGpsPoint(gpsPoint);
				}
			}

		} else { // 修改
			oldStaff = mongoTemplate.findById(staff.get_id(), Staff.class);
			if (oldStaff != null) {
				oldPhone = oldStaff.getPhone();
				staff.setMoney(oldStaff.getMoney());
				staff.setMoneyAll(oldStaff.getMoneyAll());
				staff.setLiushui(oldStaff.getLiushui());
				staff.setTicheng(oldStaff.getTicheng());
				staff.setZuohuoNum(oldStaff.getZuohuoNum());

				// 微信相关字段数据保持原有
				// 改为页面控制 staff.setWeixin(oldStaff.getWeixin());
				staff.setState(oldStaff.getState());
				staff.setBusyTimeStart(oldStaff.getBusyTimeStart());
				staff.setBusyTimeEnd(oldStaff.getBusyTimeEnd());
				staff.setLogo(oldStaff.getLogo());
				staff.setNick(oldStaff.getNick());

				staff.setGpsPoint(oldStaff.getGpsPoint());
				staff.setFaceScore(oldStaff.getFaceScore());
				staff.setZanCount(oldStaff.getZanCount());
				staff.setQiuCount(oldStaff.getQiuCount());
				staff.setFollowCount(oldStaff.getFollowCount());
				staff.setTotalIncome(oldStaff.getTotalIncome());
				staff.setDesc(oldStaff.getDesc());
				staff.setDescImages(oldStaff.getDescImages());
				staff.setStartPrice(oldStaff.getStartPrice());
				staff.setWeOrganIds(oldStaff.getWeOrganIds());
				staff.setCreateTime(oldStaff.getCreateTime());

			} else {
				status = new ReturnStatus(false, "要修改的员工信息不存在");
				status.setEntity(staff);
				return status;
			}
		}

		staff.setZjfCode(PinYinUtil.getFirstSpell(staff.getName()));

		// 手机号重复校验
		String phone = null;
		if (oldStaff != null) { // 修改手机号
			if (!TextUtils.isEmpty(staff.getPhone()) && !staff.getPhone().equals(oldStaff.getPhone())) {
				phone = staff.getPhone();// flag=true
			}
		} else { // 新增
			phone = staff.getPhone();
		}

		Staff ss = null;
		if (!TextUtils.isEmpty(phone)) {
			Staff s = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Staff.class);
			if (s != null) {
				s.setOrganId(staff.getOrganId());
				ss = s;
//				if (oldStaff == null && StringUtils.isEmpty(s.getOrganId())) { // 新增时，被添加技师无主店铺，则归属到当前店铺
//					s.setOrganId(staff.getOrganId());
//					ss = s;
//				} else {
//					status = new ReturnStatus(false, "相同手机号的技师信息已经存在！");
//					status.setEntity(staff);
//					return status;
//				}
			}
		}

		if (oldStaff != null) {
			// 更改技师案例weixin状态
			List<WeStaffCase> caseList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staff.get_id())),
					WeStaffCase.class);
			for (WeStaffCase sc : caseList) {
				sc.setWeixin(staff.getWeixin());
				mongoTemplate.save(sc);
			}
		}

		if (ss == null) {
			//修改member手机号
			vipMemberService.updateMemberByPhone(oldPhone,staff.getPhone());
			mongoTemplate.save(staff);
			status = new ReturnStatus(true);
			status.setEntity(staff);
			return status;
		} else {
			mongoTemplate.save(ss);
			status = new ReturnStatus(false, "成功添加已存在技师信息，如有需要，请重新修改技师基本信息。");
			status.setEntity(ss);
			return status;
		}
	}

	@Override
	public List<Staff> findAll(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId).and("deleteFlag").ne(true)), Staff.class);
	}

	public ReturnStatus removeFromOrgan(String organId, String staffId) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (staff == null) {
			return new ReturnStatus(false, "指定技师信息不存在");
		}
		if(!staff.getWeOrganIds().remove(organId)) {
			return new ReturnStatus(false, "技师和店铺关系不正确");
		}
		WeOrganStaffVerify weOrganStaffVerify = mongoTemplate.findOne(
				Query.query(Criteria.where("staffId").is(staffId).and("organId").is(organId)), WeOrganStaffVerify.class);
		if(weOrganStaffVerify == null) {
			return new ReturnStatus(false, "技师和店铺不存在解约关系");
		}
		weOrganStaffVerify.setState(-2);
		mongoTemplate.save(weOrganStaffVerify);
		mongoTemplate.save(staff);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus verifycode(String phone) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Staff.class);
		if (staff == null) {
			return new ReturnStatus(false, "指定手机号未关联到员工信息！");
		}
		return accountService.verifycode(phone);
	}

	@Override
	public ReturnStatus verify(String openId, String unionId, String phone, String code) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Staff.class);
		if (staff == null) {
			return new ReturnStatus(false, "指定手机号未关联到员工信息！");
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("type").is("staff"), Criteria.where("accountName").is(openId));

		boolean exist = mongoTemplate.exists(Query.query(criteria), Account.class);
		if (exist) {
			return new ReturnStatus(false, "此微信号已经绑定过员工管理账号！");
		}

		ReturnStatus status = accountService.verify(phone, code);
		if (status.isSuccess()) {
			// 绑定新的微信账号
			Account account = new Account();
			account.setIdIfNew();
			account.setCreateTimeIfNew();
			account.setAccountName(openId);
			account.setAccountPwd(passwordEncoder.encodePassword(openId, null));
			account.setAccountType("staff"); // 员工类型
			account.setWeUnionId(unionId);
			account.setEntityID(staff.get_id());
			account.setStatus("1");
			mongoTemplate.save(account);

			staff.setWeixin(true); // 绑定成功后开通微信服务
			staff.setState(1);
			mongoTemplate.save(staff);
		}

		return status;
	}

	@Override
	public FlipInfo<Staff> queryOrderByHot(FlipInfo<Staff> fpi) throws BaseException {
		fpi.setSortField("followCount"); // 根据关注用户的数量降序
		fpi.setSortOrder("DESC");
		return mongoTemplate.findByPage(Query.query(Criteria.where("deleteFlag").ne(true)), fpi, Staff.class);
	}

	@Override
	public FlipInfo<Staff> queryOrderByPrice(FlipInfo<Staff> fpi) throws BaseException {
		fpi.setSortField("startPrice"); // 根据技师案例最低价格由低到高排序
		fpi.setSortOrder("ASC");
		return mongoTemplate.findByPage(Query.query(Criteria.where("deleteFlag").ne(true)), fpi, Staff.class);
	}

	@Override
	public FlipInfo<Staff> queryOrderByDistance(double longitude, double latitude, double maxDistance,
			FlipInfo<Staff> pp) throws BaseException {
		return mongoTemplate.findByPageGeo(longitude, latitude, maxDistance, Query.query(Criteria.where("deleteFlag").ne(true)), pp, Staff.class);
	}

	/**
	 * ------------------移动端--------------
	 */
	// 获取技师所属店铺
	@Override
	public List<Organ> getOrganList(String staffId) {
		List<Organ> oList = new ArrayList<Organ>();
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		for (String organId : staff.getWeOrganIds()) {
			Organ organ = mongoTemplate.findById(organId, Organ.class);
			oList.add(organ);
		}
		return oList;
	}

	// 获取技师信息
	@Override
	public Staff getStaff(String openId) {
		Account account = mongoTemplate.findOne(
				Query.query(Criteria.where("accountName").is(openId).and("accountType").is("staff")), Account.class);
		if (account != null) {
			Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(account.getEntityID())),
					Staff.class);
			Integer eval = staff.getEvaluateCount();
			if (eval != null && eval != 0) {
				int count = staff.getZanCount() / staff.getEvaluateCount();
				staff.setZanLevel(count);
			}
			if (!(staff.getLogo() != null && !staff.getLogo().equals(""))) {
				staff.setLogo("6604334300007122119.jpg");
				mongoTemplate.save(staff);
			}
			return staff;
		} else {
			return null;
		}
	}

	// 获取店铺列表
	@Override
	public FlipInfo<Organ> getOrgans(String cityId, String districtId, String regionId, HttpServletRequest request,
			String staffId) {
		FlipInfo<Organ> fpi = new FlipPageInfo<Organ>(request);
		List<String> list = new ArrayList<String>();
		WeBRegion weBRegion = mongoTemplate.findById(regionId, WeBRegion.class);
		WeBDistrict weBDistrict = mongoTemplate.findById(districtId, WeBDistrict.class);
		WeBCity weBCity = mongoTemplate.findById(cityId, WeBCity.class);
		List<WeOrganStaffVerify> organs = mongoTemplate
				.find(Query.query(Criteria.where("staffId").is(staffId).and("state").is(0)), WeOrganStaffVerify.class);
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		for (WeOrganStaffVerify organVerify : organs) {
			list.add(organVerify.getOrganId());
		}
		if (staff != null) {
			list.addAll(staff.getWeOrganIds());
		}
		String organName =(String) fpi.getParams().get("organName");
		fpi.getParams().remove("organName");
		fpi.getParams().remove("staffId");
		fpi.getParams().remove("cityId");
		fpi.getParams().remove("districtId");
		fpi.getParams().remove("regionId");
		fpi.getParams().remove("status");
		if (weBCity != null) {
			fpi.getParams().put("city", weBCity.getName());
			fpi.getParams().remove("cityId");
		}
		if (weBDistrict != null) {
			fpi.getParams().put("district", weBDistrict.getName());
			fpi.getParams().remove("districtId");
		}
		if (weBRegion != null) {
			fpi.getParams().put("region", weBRegion.getName());
			fpi.getParams().remove("regionId");
		}
		try {
			fpi = organService.query(fpi);
		} catch (BaseException e) {
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(organName)){
			return mongoTemplate.findByPage(Query.query(Criteria.where("_id").nin(list).and("name").regex(organName)), fpi, Organ.class);
		}
		return mongoTemplate.findByPage(Query.query(Criteria.where("_id").nin(list)), fpi, Organ.class);
	}

	// 获取区域
	@Override
	public List<WeBDistrict> getDistrict(String cityId) {
		return mongoTemplate.findAll(WeBDistrict.class);
	}

	// 获取商圈
	@Override
	public List<WeBRegion> getRegion(String districtId) {
		List<WeBRegion> weBRegions = mongoTemplate.find(Query.query(Criteria.where("districtId").is(districtId)),
				WeBRegion.class);
		return weBRegions;
	}

	// 获取店铺信息
	@Override
	public Organ getOrgan(String organId) {
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		return organ;
	}
	@Override
	public Staff queryStaffById(String staffId) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)), Staff.class);
		return staff;
	}


	// 根据id获取技师信息
	@Override
	public Staff getById(String staffId) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (staff != null) {
			Integer evaluate = staff.getEvaluateCount();
			Integer zan = staff.getZanCount();
			if (evaluate != null && evaluate != 0) {
				if (zan != null) {
					int count = staff.getZanCount() / staff.getEvaluateCount();
					staff.setZanLevel(count);
				}
			}
		}
		return staff;
	}

	// 获取技师列表
	@Override
	public List<Staff> getStaffList(String organId) {
		List<Staff> staffList = mongoTemplate.find(Query.query(Criteria.where("weOrganIds").all(organId)), Staff.class);
		return staffList;
	}

	// 修改技师信息
	@Override
	public void getAndSaveById(String staffId, String status, String val) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (status.equals("nick")) {
			staff.setNick(val);
		} else if (status.equals("name")) {
			staff.setName(val);
		} else if (status.equals("phone")) {
			staff.setPhone(val);
		} else if (status.equals("idcard")) {
			staff.setIdcard(val);
		} else if (status.equals("certNumber")) {
			staff.setCertNumber(val);
		} else if (status.equals("home")) {
			staff.setHome(val);
		} else if (status.equals("sex")) {
			if (val.equals("0")) {
				staff.setSex("男");
			} else {
				staff.setSex("女");
			}
		} else if (status.equals("jishiTechang")) {
			if (val.equals("0")) {
				staff.setJishiTechang("美发");
			} else if (val.equals("1")) {
				staff.setJishiTechang("美容");
			} else if (val.equals("2")) {
				staff.setJishiTechang("美甲");
			} else {
				staff.setJishiTechang("养生");
			}
		} else if (status.equals("workYears")) {
			int year = Integer.parseInt(val);
			staff.setWorkYears(year);
		}
		mongoTemplate.save(staff);
	}

	// 加入/解约店铺
	@Override
	public ReturnStatus isJoin(String staffId, String organId, String status) {
		ReturnStatus state = new ReturnStatus(true);
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		WeOrganStaffVerify verify = mongoTemplate.findOne(
				Query.query(Criteria.where("organId").is(organId).and("staffId").is(staffId).and("state").nin(0, 1)),
				WeOrganStaffVerify.class);
		List<String> weOrganIds = staff.getWeOrganIds();
		if (status.equals("terminate")) {// 解约
			if (weOrganIds.contains(organId)) {
				weOrganIds.remove(organId);
				if (weOrganIds.size() == 0) {
					staff.setWeOrganIds(null);
				} else {
					staff.setWeOrganIds(weOrganIds);
				}
				WeOrganStaffVerify staffInOrgan = mongoTemplate.findOne(
						Query.query(
								Criteria.where("organId").is(organId).and("staffId").is(staffId).and("state").is(1)),
						WeOrganStaffVerify.class);
				staffInOrgan.setState(-2);
				mongoTemplate.save(staffInOrgan);
				mongoTemplate.save(staff);
			} else {
				state.setSuccess(false);
				state.setMessage("您已解约该店铺");
			}
		} else if (status.equals("join")) {// 加入店铺
			if (verify == null) { // 未审核的等于空
				WeOrganStaffVerify wsv = new WeOrganStaffVerify();
				wsv.setNewId();
				wsv.setNewCreate();
				wsv.setOrganId(organId);
				wsv.setOrganName(organ.getName());
				wsv.setStaffId(staffId);
				wsv.setStaffName(staff.getName());
				wsv.setState(0);
				staff.setUpdateTimeIfNew();
				mongoTemplate.save(staff);
				mongoTemplate.save(wsv);
			} else if (verify.getState() != 0) {
				updateStateOrganStaffVerify(verify, staff);
			} else {
				state.setSuccess(false);
				state.setMessage("您已申请加入该店铺，请耐心等待");
			}
		}
		return state;
	}

	/**
	 * 更新签约状态
	 * 
	 * @param verify
	 * @param staff
	 */
	public void updateStateOrganStaffVerify(WeOrganStaffVerify verify, Staff staff) {
		verify.setState(0);
		staff.setUpdateTime(new Date());
		mongoTemplate.save(staff);
		mongoTemplate.save(verify);
	}

	// 获取典型案例
	@Override
	public FlipInfo<WeStaffCase> getStaffCases(String staffId, HttpServletRequest request) {
		FlipInfo<WeStaffCase> fpi = new FlipPageInfo<WeStaffCase>(request);
		fpi.getParams().remove("staffId");
		fpi.setSize(6);
		return mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)), fpi, WeStaffCase.class);
	}

	// 添加案例
	@Override
	public void addExample(String staffId, String type, String realType, String title, String desc, String price,
			String consumeTime, List<String> list) {
		double dprice = Double.parseDouble(price);
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		Organ organ = null;
		if (!staff.getParentId().equals("0")) { // 为0表示就是住公司
			organ = mongoTemplate.findById(staff.getParentId(), Organ.class);
		} else {
			organ = mongoTemplate.findById(staff.getOrganId(), Organ.class);
		}
		List<WeStaffCase> cases = mongoTemplate
				.find(Query.query(Criteria.where("staffId").is(staffId).and("price").lt(dprice)), WeStaffCase.class);
		WeStaffCase staffCase = new WeStaffCase();
		staffCase.setNewId();
		staffCase.setStaffId(staffId);
		staffCase.setType(type);
		staffCase.setRealType(realType);
		staffCase.setTitle(title);
		staffCase.setDesc(desc);
		staffCase.setPrice(dprice);
		staffCase.setConsumeTime(Integer.parseInt(consumeTime));
		staffCase.setFollowCount(0);
		staffCase.setNewCreate();
		staffCase.setLogo(list);
		staffCase.setGpsPoint(organ.getGpsPoint());
		staffCase.setGpsPoint(organ.getGpsPoint());
		staffCase.setCity(organ.getCity()); // 所在城市名字
		staffCase.setWeixin(staff.getWeixin());
		if (cases.size() == 0 && staff != null) {
			staff.setStartPrice(Integer.parseInt(price));
			mongoTemplate.save(staff);
		}
		mongoTemplate.insert(staffCase);
	}

	// 获取案例
	@Override
	public WeStaffCase getExample(String exampleId) {
		return mongoTemplate.findById(exampleId, WeStaffCase.class);
	}

	// 编辑保存案例
	@Override
	public ReturnStatus editAndSaveExam(String staffCaseId, String title, String desc, double price, int consumeTime) {
		ReturnStatus status = new ReturnStatus(true);
		WeStaffCase staffCase = mongoTemplate.findById(staffCaseId, WeStaffCase.class);
		FlipInfo<WeStaffCase> fpi = new FlipPageInfo<WeStaffCase>(1, 1);
		fpi.setSortField("price");
		fpi.setSortOrder("asc");
		if (staffCase != null) {
			staffCase.setTitle(title);
			staffCase.setDesc(desc);
			staffCase.setConsumeTime(consumeTime);
			staffCase.setGpsPoint(staffCase.getGpsPoint());
			staffCase.setCity(staffCase.getCity());

			Staff staff = mongoTemplate.findById(staffCase.getStaffId(), Staff.class);
			if (staff.getStartPrice() == staffCase.getPrice()) {// 修改最低价案例
				if (price <= staff.getStartPrice()) {// 比最低价更低
					staff.setStartPrice((int) price);
				} else {// 不再是最低价
					staffCase.setPrice(price);
					mongoTemplate.save(staffCase);
					FlipInfo<WeStaffCase> info = mongoTemplate.findByPage(
							Query.query(Criteria.where("staffId").is(staff.get_id())), fpi, WeStaffCase.class);
					staff.setStartPrice((int) info.getData().get(0).getPrice());
				}
			} else {// 修改非最低价案例
				if (price < staff.getStartPrice()) {// 新价变为最低价
					staff.setStartPrice((int) price);
				}
			}
			mongoTemplate.save(staff);
			staffCase.setPrice(price);
			mongoTemplate.save(staffCase);
		} else {
			status.setSuccess(false);
			status.setMessage("该案例不存在");
		}
		return status;
	}

	// 删除案例
	@Override
	public ReturnStatus deleteExample(String staffCaseId, String staffId) {
		ReturnStatus status = new ReturnStatus(true);
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		WeStaffCase staffCase = mongoTemplate.findById(staffCaseId, WeStaffCase.class);
		FlipInfo<WeStaffCase> fpi = new FlipPageInfo<WeStaffCase>(1, 1);
		fpi.setSortField("price");
		fpi.setSortOrder("asc");
		if (staffCase != null && staff != null) {
			mongoTemplate.remove(staffCase);
			if (staff.getStartPrice() == staffCase.getPrice()) {
				FlipInfo<WeStaffCase> cases = mongoTemplate
						.findByPage(Query.query(Criteria.where("staffId").is(staffId)), fpi, WeStaffCase.class);
				if (cases.getData().size() == 0) {
					staff.setStartPrice(0);
				} else {
					staff.setStartPrice((int) cases.getData().get(0).getPrice());
				}
				mongoTemplate.save(staff);
			}
		} else {
			status.setSuccess(false);
			status.setMessage("该案例不存在或已被删除");
		}
		return status;
	}

	// 询价列表
	@Override
	public FlipInfo<WeUserInquiry> askPrice(String staffId, FlipInfo<WeUserInquiry> fpi, double longitude,
			double latitude) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, day - 3);
		Date date = cal.getTime();
		// 先查询就是报价的
		List<WeUserInquiryQuote> weUserInquiryQuote = mongoTemplate
				.find(Query.query(Criteria.where("staffId").is(staffId)), WeUserInquiryQuote.class);
		List<String> list = new ArrayList<String>();

		for (WeUserInquiryQuote wiq : weUserInquiryQuote) {
			list.add(wiq.getInquiryId());
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("_id").nin(list), Criteria.where("createTime").gt(date));
		FlipInfo<WeUserInquiry> inquiry = mongoTemplate.findByPageGeo(longitude, latitude, 5, Query.query(criteria),
				fpi, WeUserInquiry.class);
		return inquiry;
	}

	// 询价详情
	@Override
	public WeUserInquiry askPriceById(String inquiryId) {
		return mongoTemplate.findById(inquiryId, WeUserInquiry.class);
	}

	// 保存报价
	@Override
	public ReturnStatus askPriceSave(String staffId, String inquiryId, Double myPrice, String mypriceDesc) {
		WeUserInquiryQuote quote = mongoTemplate.findOne(
				Query.query(Criteria.where("inquiryId").is(inquiryId).and("staffId").is(staffId)),
				WeUserInquiryQuote.class);
		ReturnStatus status = new ReturnStatus(true);
		if (quote != null) {
			status.setSuccess(false);
			status.setMessage("您已发送过询价");
			return status;
		}
		WeUserInquiry weUserInquiry = mongoTemplate.findById(inquiryId, WeUserInquiry.class);
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		double distance = GpsUtil.distance(weUserInquiry.getGpsPoint(), staff.getGpsPoint());
		String df = new DecimalFormat("#0.00").format(distance);
		double dis = Double.parseDouble(df);
		WeUserInquiryQuote wiq = new WeUserInquiryQuote();
		wiq.setNewId();
		wiq.setNewCreate();
		wiq.setInquiryId(inquiryId);
		wiq.setName(staff.getName());
		wiq.setFollowCount(staff.getFollowCount());
		wiq.setLevel(staff.getLevel());
		wiq.setLogo(staff.getLogo());
		wiq.setDistance(dis);
		wiq.setStaffId(staffId);
		wiq.setDesc(mypriceDesc);
		wiq.setPrice(myPrice);
		mongoTemplate.save(wiq);
		return status;
	}

	// 签到保存
	@Override
	public ReturnStatus saveSign(String staffId, String organId, String organName, Double longitude, Double latitude) {
		//ReturnStatus status = new ReturnStatus(true);
		Calendar c1 = new GregorianCalendar();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		Date date0 = c1.getTime();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (StringUtils.isEmpty(organId)) {
			organId = staff.getOrganId();
		}
		if (!StringUtils.isEmpty(staffId) && !StringUtils.isEmpty(organId)) {
			Organ organ = mongoTemplate.findById(organId, Organ.class);
			double dis = GpsUtil.distance(latitude, longitude, organ.getGpsPoint().getLatitude(),
					organ.getGpsPoint().getLongitude());
			if (dis > 1) {
				return new ReturnStatus(0,"对不起，你距离公司太远了，不能签到！");
			}
			List<WeStaffSign> signList = mongoTemplate.find(Query.query(
					Criteria.where("staffId").is(staffId).and("organId").is(organId).and("createTime").gt(date0)),
					WeStaffSign.class);
			if (signList.size() >= 2) {
				return new ReturnStatus(0,"每天签到次数不超过两次");
			} else {
				Date date = DateUtil.currentDate();
				try {
					ReturnStatus statusTemp = getStaffPaiBan(organId,staffId,date,signList.size());
					WeStaffSign ws = new WeStaffSign();
					GpsPoint gps = new GpsPoint();
					gps.setLatitude(latitude);
					gps.setLongitude(longitude);
					ws.setNewId();
					ws.setCreateTime(date);
					ws.setStaffId(staffId);
					ws.setOrganId(organId);
					ws.setOrganName(organName);
					ws.setGpsPoint(gps);
					ws.setCreateTimeFormat(time);
					mongoTemplate.insert(ws);
					if (staff != null && organ != null) {// 签到成功将店铺经纬度给技师
						GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(),
								organ.getGpsPoint().getLongitude());
						staff.setGpsPoint(gpsPoint);
						mongoTemplate.save(staff);
					}
					return statusTemp;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return new ReturnStatus(0,"程序内存错误,请即时反馈!");
	}

	private ReturnStatus getStaffPaiBan(String organId, String staffId, Date date,int signNum) throws ParseException{
		String yearMonthStr = DateUtil.getDateStr(date,"yyyyMM");
		Integer yearMonthInt = Integer.parseInt(yearMonthStr);
		String yearMonthDay = DateUtil.getDateStr(date,"yyyyMMdd");
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),
				Criteria.where("staffId").is(staffId),Criteria.where("yearmonth").is(yearMonthInt));
		query.addCriteria(criteria);
		KPaiban kPaiban = mongoTemplate.findOne(query, KPaiban.class);
		if(kPaiban == null) {
			return new ReturnStatus(0,"你好，你最近没有排班！可以不用签到");
		}
		int day = DateUtil.getDay(date);
		String dayPaiban = getDayPaiban(day,kPaiban);
		if(StringUtils.isEmpty(dayPaiban)) {
			return new ReturnStatus(0,"你好，你最近没有排班！可以不用签到");
		}
		KBancidingyi kBancidingyi = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(dayPaiban)), KBancidingyi.class);
		if(kBancidingyi != null && kBancidingyi.getKuatian() ==false) {
			Date startWork = null;
			Date endWork = null;
			long differMinutes = 0;
			if(signNum == 0 && kBancidingyi.getKaoqin_a1()) {
				startWork = DateUtil.parse(yearMonthDay + " " + kBancidingyi.getTime_a1(),"yyyyMMdd HH:mm");
				if (date.compareTo(startWork) > 0) { // 员工迟到了
					differMinutes = DateUtil.differMinutes(date,
							startWork);
					return new ReturnStatus(1, "你迟到了" + differMinutes + "分钟");
				}
			} else if(signNum == 1 && kBancidingyi.getKaoqin_a2() ) {
				endWork = DateUtil.parse(yearMonthDay + " " + kBancidingyi.getTime_a2(),"yyyyMMdd HH:mm");
				if (date.compareTo(endWork) < 0) {   // 员工早退了
					differMinutes = DateUtil.differMinutes(endWork,
							date);
					return new ReturnStatus(1, "你早退了" + differMinutes + "分钟");
				}
			}
		}
		return new ReturnStatus(1,"祝你上班愉快啦！");
	}

	private String getDayPaiban(int day,KPaiban kPaiban) {
		String dayPaiban = null;
		if(day <10) {
			dayPaiban = String.valueOf(GetSetUtil.getter(kPaiban, "Day0" + day));
		} else {
			dayPaiban = String.valueOf(GetSetUtil.getter(kPaiban, "Day" + day));
		}
		return dayPaiban;
	}

	// 获取签到明细
	@Override
	public FlipInfo<WeStaffSign> getSignStatistics(String staffId, String time, FlipInfo<WeStaffSign> fpi) {
		FlipInfo<WeStaffSign> weStaffSign = new FlipInfo<WeStaffSign>();
		if (time != null && !time.equals("") && !time.equals("选择日期")) {
			weStaffSign = mongoTemplate.findByPage(
					Query.query(Criteria.where("staffId").is(staffId).and("createTimeFormat").regex(time)), fpi,
					WeStaffSign.class);
		} else {
			weStaffSign = mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)), fpi,
					WeStaffSign.class);
		}
		return weStaffSign;
	}

	// 获取附近店铺
	@Override
	public FlipInfo<Organ> getNearOrgans(String staffId, FlipInfo<Organ> fpi, String organName) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		List<String> organs = new ArrayList<String>();
		organs.add(staff.getOrganId());
		if (organName != null && !organName.trim().equals("") && staff != null) {
			fpi = mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(organs).and("name").regex(organName)),
					fpi, Organ.class);
		} else if ((organName == null || organName.trim().equals("")) && staff != null) {
			fpi = mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(organs)), fpi, Organ.class);
		}
		if (fpi.getData() != null) {
			for (Organ organ : fpi.getData()) {
				GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(),
						organ.getGpsPoint().getLongitude());
				organ.setGpsPoint(gpsPoint);
			}
		}
		return fpi;
	}

	// 开启询价
	@Override
	public FlipInfo<WeUserInquiry> openInquiry(String staffId, FlipInfo<WeUserInquiry> fpi, Double longitude,
			Double latitude) {
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(staffId)), Update.update("openInquiry", true),
				Staff.class);
		return askPrice(staffId, fpi, longitude, latitude);
	}

	// 技师附近店铺
	@Override
	public FlipInfo<Organ> getNearOrgan(FlipInfo<Organ> fpi, Double longitude, Double latitude) {
		return mongoTemplate.findByPageGeo(longitude, latitude, 5, null, fpi, Organ.class);
	}

	// 技师日程
	@Override
	public WeStaffCalendar getWeStaffSchedule(String staffId, Integer day) {
		return mongoTemplate.findOne(Query.query(Criteria.where("staffId").is(staffId).and("day").is(day)),
				WeStaffCalendar.class);
	}

	// 查询技师技师一周日程
	@Override
	public List<WeStaffCalendar> findWeStaffSchedule(String staffId, Integer day, Integer day7) {
		FlipInfo<WeStaffCalendar> flipInfo = new FlipInfo<WeStaffCalendar>();
		flipInfo.setSortField("day");
		flipInfo.setSortOrder("asc");
		flipInfo = mongoTemplate.findByPage(
				Query.query(Criteria.where("staffId").is(staffId).and("day").gte(day).lte(day7)), flipInfo,
				WeStaffCalendar.class);
		return flipInfo.getData();
	}

	public WeStaffCalendar findScheduleTime(String day, String staffId, String organId) {
		return mongoTemplate.findOne(Query.query(
				Criteria.where("staffId").is(staffId).and("day").is(Integer.parseInt(day)).and("organId").is(organId)),
				WeStaffCalendar.class);
	}

	// 保存日程
	@Override
	public ReturnStatus scheduleSave(String staffId, String organId, Integer day, int index, boolean selected) {
		WeStaffCalendar wsc = new WeStaffCalendar();
		wsc = mongoTemplate.findOne(
				Query.query(Criteria.where("organId").is(organId).where("staffId").is(staffId).and("day").is(day)),
				WeStaffCalendar.class);
		if (wsc == null) {
			wsc = new WeStaffCalendar();
			wsc.setStaffId(staffId);
			wsc.setOrganId(organId);
			wsc.setDay(day);
		}
		if (organId != null) {
			wsc.setOrganId(organId);
		}
		wsc.setIdIfNew();
		switch (index) {
		case 0:
			wsc.setTime0(selected);
			break;
		case 1:
			wsc.setTime1(selected);
			break;
		case 2:
			wsc.setTime2(selected);
			break;
		case 3:
			wsc.setTime3(selected);
			break;
		case 4:
			wsc.setTime4(selected);
			break;
		case 5:
			wsc.setTime5(selected);
			break;
		case 6:
			wsc.setTime6(selected);
			break;
		case 7:
			wsc.setTime7(selected);
			break;
		case 8:
			wsc.setTime8(selected);
			break;
		case 9:
			wsc.setTime9(selected);
			break;
		case 10:
			wsc.setTime10(selected);
			break;
		case 11:
			wsc.setTime11(selected);
			break;
		case 12:
			wsc.setTime12(selected);
			break;
		case 13:
			wsc.setTime13(selected);
			break;
		case 14:
			wsc.setTime14(selected);
			break;
		case 15:
			wsc.setTime15(selected);
			break;
		case 16:
			wsc.setTime16(selected);
			break;
		case 17:
			wsc.setTime17(selected);
			break;
		case 18:
			wsc.setTime18(selected);
			break;
		case 19:
			wsc.setTime19(selected);
			break;
		case 20:
			wsc.setTime20(selected);
			break;
		case 21:
			wsc.setTime21(selected);
			break;
		case 22:
			wsc.setTime22(selected);
			break;
		case 23:
			wsc.setTime23(selected);
			break;
		}
		mongoTemplate.save(wsc);
		return new ReturnStatus(true);
	}

	// 选择店铺
	@Override
	public FlipInfo<Organ> getOrgans(String staffId, FlipInfo<Organ> fpi) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		List<String> organsTemp = new ArrayList<String>();
		organsTemp.addAll(staff.getWeOrganIds());
		organsTemp.add(staff.getOrganId());
		FlipInfo<Organ> organs = mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(organsTemp)), fpi,
				Organ.class);
		return organs;
	}

	// 设置繁忙时间
	@Override
	public ReturnStatus saveBustTime(String staffId, String busyTimeStart, String busyTimeEnd) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		staff.setBusyTimeStart(busyTimeStart);
		staff.setBusyTimeEnd(busyTimeEnd);
		mongoTemplate.save(staff);
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<Code> selectType(FlipInfo<Code> fpi) {
		FlipInfo<Code> code = mongoTemplate.findByPage(
				Query.query(Criteria.where("type").in("hairType", "meiJiaType", "meiRongType", "zuliaoType")), fpi,
				Code.class);
		return code;
	}

	@Override
	public List<Code> findTypes() {
		return mongoTemplate.find(
				Query.query(Criteria.where("type").in("hairType", "meiJiaType", "meiRongType", "zuLiaoType")),
				Code.class);
	}

	@Override
	public Code findCodeById(String codeId) {
		return mongoTemplate.findById(codeId, Code.class);
	}

	// 保存图片
	@Override
	public void getAndSavePhoto(String staffId, String logo) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		staff.setLogo(logo);
		mongoTemplate.save(staff);
	}

	// 技师加入店铺审核信息
	@Override
	public WeOrganStaffVerify getVerifyStatus(String staffId, String organId) {
		return mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId).and("staffId").is(staffId)),
				WeOrganStaffVerify.class);
	}

	// 获取自我推荐
	@Override
	public Staff getMyRecommendation(String staffId) {

		return mongoTemplate.findById(staffId, Staff.class);
	}

	// 保存自我推荐
	@Override
	public ReturnStatus editSave(String staffId, String textArea, String logo0, String logo1, String logo2) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		List<String> imgs = new ArrayList<String>();
		if (logo0 != null && !logo0.equals("")) {
			imgs.add(logo0);
		}
		if (logo1 != null && !logo1.equals("")) {
			imgs.add(logo1);
		}
		if (logo2 != null && !logo2.equals("")) {
			imgs.add(logo2);
		}
		staff.setDescImages(imgs);
		staff.setDesc(textArea);
		mongoTemplate.save(staff);
		return new ReturnStatus(true);
	}

	// 获取店铺技师列表
	@Override
	public FlipInfo<Staff> storeStaffList(String organId, FlipPageInfo<Staff> flp) {
		FlipInfo<Staff> staff = mongoTemplate.findByPage(
				Query.query(Criteria.where("weOrganIds").in(organId).and("deleteFlag").ne(true).and("logo").ne(null).ne("")), flp, Staff.class);
		List<Staff> data = staff.getData();
		for (Staff staff2 : data) {
			Integer zanCount = staff2.getZanCount();
			Integer evaluateCount = staff2.getEvaluateCount();
			if (zanCount != null && evaluateCount != null && evaluateCount != 0) {
				staff2.setZanLevel(zanCount / evaluateCount);
			}
		}
		return staff;
	}

	// 开启/关闭询价
	@Override
	public ReturnStatus openInquiry(String staffId, String type) {
		if (type.equals("open")) {// 开启
			mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(staffId)),
					Update.update("openInquiry", true), Staff.class);
		} else if (type.equals("close")) {// 关闭
			mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(staffId)),
					Update.update("openInquiry", false), Staff.class);
		}
		return new ReturnStatus(true);
	}

	// 获取类型列表
	@Override
	public List<Code> findtype() {
		List<Code> list = mongoTemplate.find(Query.query(Criteria.where("type").is("hairType")), Code.class);
		return list;
	}

	// 获取我的店铺
	@Override
	public FlipInfo<WeOrganStaffVerify> getMyStore(String staffId, FlipPageInfo<WeOrganStaffVerify> fpi) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		FlipInfo<WeOrganStaffVerify> organs = mongoTemplate
				.findByPage(Query.query(Criteria.where("staffId").is(staffId)), fpi, WeOrganStaffVerify.class);
		List<WeOrganStaffVerify> data = organs.getData();
		for (WeOrganStaffVerify weOrganStaffVerify : data) {
			Organ organ = mongoTemplate.findById(weOrganStaffVerify.getOrganId(), Organ.class);
			weOrganStaffVerify.setOrganName(organ.getName());
			weOrganStaffVerify.setLogo(organ.getLogo());
			weOrganStaffVerify.setStaffName(staff.getName());
			mongoTemplate.save(weOrganStaffVerify);
		}
		return organs;
	}

	// 技师收藏店铺
	@Override
	public Integer followOrgan(String staffId, String organId, String followType) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (staff.getFollowOrganIds() != null && staff.getFollowOrganIds().contains(organId)) {
			followType = "cancel";
		} else {
			followType = "follow";
		}
		if (followType.equals("follow")) {// 关注
			List<String> list = new ArrayList<String>();
			if (staff.getFollowOrganIds() != null) {
				list = staff.getFollowOrganIds();
			}
			list.add(organId);
			staff.setFollowOrganIds(list);
			organ.setFollowCount(organ.getFollowCount() + 1);
			mongoTemplate.save(staff);
			mongoTemplate.save(organ);
		} else if (followType.equals("cancel")) {// 取消关注
			List<String> list = new ArrayList<String>();
			if (staff.getFollowOrganIds() != null) {
				list = staff.getFollowOrganIds();
			}
			list.remove(organId);
			staff.setFollowOrganIds(list);
			organ.setFollowCount(organ.getFollowCount() - 1);
			mongoTemplate.save(staff);
			mongoTemplate.save(organ);
		}
		Organ organ2 = mongoTemplate.findById(organId, Organ.class);
		return organ2.getFollowCount();
	}

	// 新用户绑定店铺
	@Override
	public Staff bindOrgan(Staff staff) {
		List<String> list = new ArrayList<String>();
		list.add(staff.getOrganId());
		staff.setWeOrganIds(list);
		Organ organ = mongoTemplate.findById(staff.getOrganId(), Organ.class);
		WeOrganStaffVerify verify = new WeOrganStaffVerify();
		verify.setNewId();
		verify.setNewCreate();
		verify.setOrganId(staff.getOrganId());
		verify.setOrganName(organ.getName());
		verify.setStaffId(staff.get_id());
		verify.setStaffName(staff.getName());
		verify.setState(1);
		verify.setMemo("首次使用微信用户加入店铺");
		mongoTemplate.save(staff);
		mongoTemplate.insert(verify);
		return staff;
	}

	@Override
	public ReturnStatus queryWeOrganStaffVerifyNoVerify(String staffId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("staffId").is(staffId), Criteria.where("state").is(0));
		WeOrganStaffVerify verify = mongoTemplate.findOne(Query.query(criteria), WeOrganStaffVerify.class);
		if (verify != null) {
			return new ReturnStatus(true);
		} else {
			return new ReturnStatus(false);
		}
	}

	// 查询是否补全信息
	@Override
	public ReturnStatus isComplementMes(String staffId) {
		ReturnStatus status = new ReturnStatus(true);
		status.setStatus(0);
		List<WeStaffCase> caseList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId)),
				WeStaffCase.class);
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		String time = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		String[] times = time.split("/");
		int intTime = Integer.parseInt(times[0]) * 10000 + Integer.parseInt(times[1]) * 100
				+ Integer.parseInt(times[2]);
		List<WeStaffCalendar> calendarList = mongoTemplate.find(
				Query.query(Criteria.where("staffId").is(staffId).and("day").gte(intTime)), WeStaffCalendar.class);
		if (staff.getDesc() == null || staff.getDesc().trim().equals("") || staff.getDescImages() == null
				|| staff.getDescImages().size() == 0) {
			status.setStatus(1);
			status.setMessage("请补全自我推荐信息，方便客户查看");
		}
		if (caseList.size() == 0) {
			status.setStatus(2);
			status.setMessage("您还未添加案例，将会降低被客户搜到的机会");
		}
		if (calendarList.size() == 0) {
			status.setStatus(3);
			status.setMessage("您还未设置未来一周的日程，将无法被客户预约");
		}
		return status;
	}

	// 技师默认店铺价目表
	@Override
	public FlipInfo<Smallsort> getTariffList(String staffId, FlipPageInfo<Smallsort> flp) {
		FlipInfo<Smallsort> smallsort = new FlipInfo<Smallsort>();
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (staff != null) {
			Organ organ = mongoTemplate.findById(staff.getOrganId(), Organ.class);
			if (organ != null) {
				smallsort = mongoTemplate.findByPage(Query.query(Criteria.where("organId").is(organ.get_id()).and("deleteFlag").ne(true)), flp,
						Smallsort.class);
			}
		}
		return smallsort;
	}

	@Override
	public ReturnStatus removeStaff(String staffId) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)), Staff.class);
		if (staff != null) {
			staff.setOrganId(null);
			mongoTemplate.save(staff);
			return new ReturnStatus(true);
		} else {
			return new ReturnStatus(false, "没有该技师");
		}
	}

	@Override
	public long findMessageCount(String staffId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("toId").is(staffId), Criteria.where("toType").is("staff"),
				Criteria.where("readFalg").is(false));
		query.addCriteria(criteria);
		return mongoTemplate.count(query, WeMessage.class);
	}

	@Override
	public void updateMessageToRead(String staffId, int type) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (type == 1) { // 普通消息
			criteria.andOperator(Criteria.where("toId").is(staffId), Criteria.where("type").is("1"),
					Criteria.where("toType").is("staff"), Criteria.where("readFalg").is(false));
		} else if (type == 2) { // 系统消息
			criteria.andOperator(Criteria.where("toId").is(staffId), Criteria.where("type").is("2"),
					Criteria.where("toType").is("staff"), Criteria.where("readFalg").is(false));
		}
		query.addCriteria(criteria);
		Update update = new Update();
		update.set("readFalg", true);
		mongoTemplate.updateMulti(query, update, WeMessage.class);

	}
	public FlipInfo<Organ> queryOrganListByfollowCount(double longitude, double latitude, FlipInfo<Organ> fpi) {

		fpi=mongoTemplate.findByPage(Query.query(Criteria.where("organPositionState").is("0")), fpi, Organ.class);
		for(Organ organ:fpi.getData()){
//			System.out.println(organ.get_id());
			double dis= GpsUtil.distance(latitude, longitude,organ.getGpsPoint().getLatitude() , organ.getGpsPoint().getLongitude());
			if(dis<1){
				DecimalFormat df  = new DecimalFormat("###.000");
				organ.setDistance(Double.parseDouble(df.format(dis))*1000);
				organ.setUnit("m");
			}else{
				DecimalFormat df1  = new DecimalFormat("###.0");
				organ.setDistance(Double.parseDouble(df1.format(dis)));
				organ.setUnit("km");
			}
			Integer evaluateCount=organ.getEvaluateCount();
			int aver=0;
			if(evaluateCount!=null&&evaluateCount!=0){
				int zanCount=organ.getZanCount();
				aver=(int)Math.ceil((double)zanCount/evaluateCount);
				if(aver>5){
					aver=5;
				}
				organ.setZanCount(aver);
			}else{
				aver=organ.getZanCount();
				if(aver>5){
					aver=5;
					organ.setZanCount(aver);
				}
			}
			GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
			organ.setGpsPoint(gpsPoint);
		}
		return fpi;
	}
	@Override
	public FlipInfo<Organ> queryOrganListByUser(double longitude,double latitude, double maxDistance, FlipInfo<Organ> fpi) {
 		fpi=mongoTemplate.findByPageGeo(longitude, latitude, maxDistance, Query.query(Criteria.where("organPositionState").is("0")), fpi, Organ.class);
		for(Organ organ:fpi.getData()){
			//double dis=GpsUtil.distance(longitude, latitude, organ.getGpsPoint().getLongitude(), organ.getGpsPoint().getLatitude());
			if(organ.getDistance()<1){
				DecimalFormat df  = new DecimalFormat("###.000");
				organ.setDistance(Double.parseDouble(df.format(organ.getDistance()))*1000);
				organ.setUnit("m");
			}else{
				DecimalFormat df1  = new DecimalFormat("###.0");
				organ.setDistance(Double.parseDouble(df1.format(organ.getDistance())));
				organ.setUnit("km");
			}
			Integer evaluateCount=organ.getEvaluateCount();
			int aver=0;
			if(evaluateCount!=null&&evaluateCount!=0){
				int zanCount=organ.getZanCount();
				aver=(int)Math.ceil((double)zanCount/evaluateCount);
				if(aver>5){
					aver=5;
				}
				organ.setZanCount(aver);
			}else{
				aver=organ.getZanCount();
				if(aver>5){
					aver=5;
					organ.setZanCount(aver);
				}
			}
			GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
			organ.setGpsPoint(gpsPoint);
		}
		return fpi;
	}
	public FlipInfo<Organ> queryOrganPosition(FlipInfo<Organ> organList){
		List<String> list = new ArrayList<>();
		for (Organ organ:organList.getData()){
			list.add(organ.get_id());
		}
		List<OrganPositionSetting> settingList = mongoTemplate.find(Query.query(Criteria.where("organId").in(list)),OrganPositionSetting.class);
		for(Organ organ:organList.getData()){
			for (OrganPositionSetting setting:settingList){
				if(organ.get_id().equals(setting.getOrganId())){
					organ.setNum1(setting.getNum());
					organ.setLeaseMoney(setting.getLeaseMoney());
					organ.setLeaseType(setting.getLeaseType());
				}
			}
		}


		return organList;
	}

	/**
	 * 根据手机号查询技师
	 * @param phone
	 * @return
	 */
	public Staff queryStaffByPhone(String phone){
		return mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)),Staff.class);
	}
	public FlipInfo<Staff> queryUserOfStaff1(FlipInfo<Staff> fpi){
		TypedAggregation agg = Aggregation.newAggregation(
				UserRelevanceStaff.class
				,match(Criteria.where("organId").is(fpi.getParams().get("organId").toString()))
				,group("staffId").count().as("total")
				,sort(Sort.Direction.DESC, "total")
		);
		AggregationResults result = mongoTemplate.aggregate(agg, BasicDBObject.class);
		List<Map> resultList = result.getMappedResults();
		//赋值
		setNumToList(resultList,fpi.getData());
		return fpi;
	}
	/**
	 * 查询技师对应的关系用户
	 * @param fpi
	 * @return
	 */
	public List<Staff> queryUserOfstaff(FlipInfo<Staff> fpi){
		//利用fpi排序等功能
		String organId = fpi.getParams().get("organId").toString();
		Integer page = fpi.getPage();
		Integer size = fpi.getSize();
		FlipInfo<Staff> ff = fpi;
		//管道聚合查询
		TypedAggregation agg = Aggregation.newAggregation(
				UserRelevanceStaff.class
				,match(Criteria.where("organId").is(organId))
				,group("staffId").count().as("total")
				,sort(Sort.Direction.DESC, "total")
		);
		AggregationResults result = mongoTemplate.aggregate(agg, BasicDBObject.class);
		//堆栈问题
		ff.setSize(300);
		ff.setPage(1);
		ff = mongoTemplate.findByPage(null, ff, Staff.class);
		ff.setPage(page);
		ff.setSize(size);
		List<Map> resultList = result.getMappedResults();

		//赋值
		setNumToList(resultList,ff.getData());
		//排序
		Collections.sort(ff.getData(), new Comparator<Staff>() {
			@Override
			public int compare(Staff o1, Staff o2) {
				int stu1=o1.getUserNum();
				int stu2=o2.getUserNum();
				if(stu1<stu2){
					return 1;
				}else if(stu1==stu2){
					return 0;
				}else{
					return -1;
				}
			}
		});
		List cloneList = Arrays.asList(ArrayUtils.clone(ff.getData().toArray()));
		String sord = fpi.getSortOrder();
		//反转
		if("asc".equals(sord)){
			Collections.reverse(cloneList);
		}
		int totalNum = fpi.getTotal();
		int pageNum = (page-1)*size;
		int pageSize = page*size;
		if(totalNum/size==page-1){
			pageSize = totalNum;
		}
		return cloneList.subList(pageNum,pageSize);
	}
	/**
	 * 多条件查询店面下技师j
	 */
	public FlipInfo<UserRelevanceStaff> queryUserByFpi(FlipInfo<UserRelevanceStaff> fpi){
//		Object isLeave = fpi.getParams().get("isLeave");
//		fpi.getParams().remove("isLeave");
		fpi.setSortField("createTime");   //技师与用户关联时间
		fpi.setSortOrder("DESC");
		fpi = mongoTemplate.findByPage(null,fpi,UserRelevanceStaff.class);
		return fpi;
	}

	/**
	 * 改变门店技师对应用户状态
	 * @param request
	 * @return
	 */
	public ReturnStatus relieve(HttpServletRequest request){
		String staffId = request.getParameter("staffId");
		String organId = request.getParameter("organId");
		WriteResult result = mongoTemplate.updateMulti(Query.query(Criteria.where("staffId").is(staffId).and("organId").is(organId)), new Update().set("isActivate",0), UserRelevanceStaff.class);
//		result.isUpdateOfExisting()
		return new ReturnStatus(true);
	}
	public List setNumToList(List<Map> numList,List<Staff> staffList){
		for(Staff staff:staffList){
			for(Map resul:numList){
				if(resul.get("_id").equals(staff.get_id())){
					staff.setUserNum((Integer)resul.get("total"));
				}
			}
			if(staff.getUserNum()==null){
				staff.setUserNum(0);
			}
		}
		return staffList;
	}
}
