package com.mrmf.service.sys.SysUser;

import com.mongodb.WriteResult;
import com.mrmf.entity.*;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.mrmf.entity.user.*;
import com.osg.entity.FlipInfo;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<User> queryUser(FlipInfo<User> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, User.class);

		return fpi;
	}

	@Override
	public User queryByUserId(String userId) throws BaseException {
		User user = mongoTemplate.findById(userId, User.class);
		if (user == null)
			throw new BaseException("指定id的用户信息不存在");
		else {
			return user;
		}
	}

	@Override
	public FlipInfo<WeOrganOrder> queryUserOrder(FlipInfo<WeOrganOrder> fpi, String userId) throws BaseException {
		fpi.getParams().put("userId", userId);
		mongoTemplate.findByPage(null, fpi, WeOrganOrder.class);
		List<WeOrganOrder> list = fpi.getData();

		for (WeOrganOrder weOrganOrder : list) {
			Organ organ = mongoTemplate.findById(weOrganOrder.getOrganId(), Organ.class);
			weOrganOrder.setOrganName(organ != null ? organ.getName() : "");
			Staff staff = mongoTemplate.findById(weOrganOrder.getStaffId(), Staff.class);
			weOrganOrder.setStaffName(staff != null ? staff.getName() : "");
			if (weOrganOrder.getType() == 2) {
				WeStaffCase weStaffCase = mongoTemplate.findById(weOrganOrder.getOrderService(), WeStaffCase.class);
				weOrganOrder.setServiveName(weStaffCase != null ? weStaffCase.getTitle() : "");
			} else if (weOrganOrder.getType() == 3) {
				WeUserInquiry weUserInquiry = mongoTemplate.findById(weOrganOrder.getOrderService(),
						WeUserInquiry.class);
				weOrganOrder.setServiveName(weUserInquiry != null ? weUserInquiry.getType() : "");
			}
		}

		return fpi;
	}

	@Override
	public FlipInfo<PositionOrder> queryPositionCheck(FlipInfo<PositionOrder> fpi, String organId) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		FlipInfo<PositionOrder> positionOrder = mongoTemplate.findByPage(Query.query(Criteria.where("organId").is(organId)), fpi, PositionOrder.class);

		return positionOrder;
	}


	@Override
	public void editUserStatus(String userIds, String state) {
		String[] userIdArray = userIds.split(",");
		for (String userId : userIdArray) {
			User oldUser = mongoTemplate.findById(userId, User.class);
			if (oldUser != null) {
				oldUser.setStatus(state);
				mongoTemplate.save(oldUser);
			}
		}

	}

	@Override
	public FlipInfo<Staff> queryStaff(FlipInfo<Staff> fpi) throws BaseException {

		String organName = fpi.getParams().get("regex:organ").toString();
		List<String> patterns = new ArrayList<String>();

		if (organName != null && !organName.equals("")) {
			// organName
			Query query = new Query();
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("name").regex(organName));
			query.addCriteria(criteria);
			List<Organ> result = mongoTemplate.find(query, Organ.class);
			for (Organ anchor : result) {
				patterns.add(anchor.get_id());
			}
			fpi.getParams().remove("regex:organ");
		}

		Query query = new Query();
		Criteria criteria = new Criteria();
		List<Criteria> conditionList = new ArrayList<Criteria>();

		if (fpi.getParams().get("regex:phone") != null && !fpi.getParams().get("regex:phone").equals("")) {
			conditionList.add(Criteria.where("phone").regex(fpi.getParams().get("regex:phone").toString()));
			fpi.getParams().remove("regex:phone");
		}
		if (fpi.getParams().get("regex:name") != null && !fpi.getParams().get("regex:name").equals("")) {
			conditionList.add(Criteria.where("name").regex(fpi.getParams().get("regex:name").toString()));
			fpi.getParams().remove("regex:name");
		}
		if (fpi.getParams().get("sex") != null && !fpi.getParams().get("sex").equals("")) {
			conditionList.add(Criteria.where("sex").is(fpi.getParams().get("sex").toString()));
			fpi.getParams().remove("sex");
		}
		if (fpi.getParams().get("gte:workYears|integer") != null
				&& !fpi.getParams().get("gte:workYears|integer").equals("")) {
			conditionList.add(Criteria.where("workYears").gte(Integer.parseInt(fpi.getParams().get("gte:workYears|integer").toString())));
			fpi.getParams().remove("gte:workYears|integer");
		}
		if (fpi.getParams().get("lte:workYears|integer") != null
				&& !fpi.getParams().get("lte:workYears|integer").equals("")) {
			conditionList.add(Criteria.where("workYears").lte(Integer.parseInt(fpi.getParams().get("lte:workYears|integer").toString())));
			fpi.getParams().remove("lte:workYears|integer");
		}

		if (patterns.size() > 0) {
			conditionList.add(Criteria.where("organId").in(patterns));
		}

		if (conditionList.size() > 0) {
			criteria.andOperator(conditionList.toArray(new Criteria[conditionList.size()]));
			query.addCriteria(criteria);
		}
		mongoTemplate.findByPage(query, fpi, Staff.class);
		List<Staff> data = fpi.getData();
		for (Staff staff : data) {
			if (!StringUtils.isEmpty(staff.getOrganId())) {
				Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staff.getOrganId())),
						Organ.class);
				if (organ != null) {
					staff.setOrganName(organ.getName());
				}
			}
		}

		return fpi;
	}

	@Override
	public Staff queryByStaffId(String staffId) throws BaseException {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (staff == null)
			throw new BaseException("指定id的用户信息不存在");
		else {
			return staff;
		}
	}


	@Override
	public void updatePositionOrder(String id,String check) throws BaseException {
		PositionOrder positionOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), PositionOrder.class);
		positionOrder.setState(Integer.parseInt(check));
		mongoTemplate.save(positionOrder);

	}

	@Override
	public WriteResult opd(String id, String check) throws BaseException {
		PositionOrder positonById = mongoTemplate.findById(id,PositionOrder.class);
		Organ organ = mongoTemplate.findById(positonById.getOrganId(),Organ.class);
		Staff staff = mongoTemplate.findById(positonById.getStaffId(),Staff.class);
		if(check.equals("1")){  //通过
			List<String> weOrganIds = staff.getWeOrganIds( );
			if(!weOrganIds.contains(organ.get_id())){
				weOrganIds.add(organ.get_id());
			}
			mongoTemplate.save(staff);

			//把分帐区间写入positionOrder表
			OrganPositionSetting ops = mongoTemplate.findOne(Query.query(Criteria.where("organId").is(positonById.getOrganId())),OrganPositionSetting.class);
			positonById.setFenZhangList(ops.getPositionSettingsList());
			mongoTemplate.save(positonById);

			//签约技师
			List<String> stringList = new ArrayList<>();
			List<WeOrganStaffVerify> staffList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staff.get_id())), WeOrganStaffVerify.class);
			for(WeOrganStaffVerify staffVerify:staffList){
				stringList.add(staffVerify.getOrganId());
			}
			if(!stringList.contains(organ.get_id())){
				WeOrganStaffVerify weOrganStaffVerify = new WeOrganStaffVerify();
				weOrganStaffVerify.setNewCreate();
				weOrganStaffVerify.setOrganId(organ.get_id());
				weOrganStaffVerify.setOrganName(organ.getName());
				weOrganStaffVerify.setStaffId(staff.get_id());
				weOrganStaffVerify.setStaffName(staff.getName());
				weOrganStaffVerify.setState(1);
				weOrganStaffVerify.setMemo("工位模式签约成功");
				mongoTemplate.save(weOrganStaffVerify);
			}

			List<OrganPositionDetails> positionOrderList = mongoTemplate.find(Query.query(Criteria.where("positionOrderId").is(id)), OrganPositionDetails.class);
			for(OrganPositionDetails positionDetaill:positionOrderList){
				Date time = positionDetaill.getTime( );
				int day = Integer.parseInt(DateUtil.format(time, "YYYYMMdd"));
				//插入之前查询有没有一个技师在同一天选择店铺
				mongoTemplate.remove(Query.query(Criteria.where("staffId").is(staff.get_id()).and("day").is(day)), WeStaffCalendar.class);
				WeStaffCalendar calendar = new WeStaffCalendar();
				calendar.setNewCreate();
				calendar.setOrganId(organ.get_id());
				calendar.setStaffId(staff.get_id());
				calendar.setDay(day);
				calendar.setTime0(false);
				calendar.setTime1(false);
				calendar.setTime2(false);
				calendar.setTime3(false);
				calendar.setTime4(false);
				calendar.setTime5(false);
				calendar.setTime6(false);
				calendar.setTime7(false);
				calendar.setTime8(false);
				calendar.setTime9(true);
				calendar.setTime10(true);
				calendar.setTime11(true);
				calendar.setTime12(true);
				calendar.setTime13(true);
				calendar.setTime14(true);
				calendar.setTime15(true);
				calendar.setTime16(true);
				calendar.setTime17(true);
				calendar.setTime18(true);
				calendar.setTime19(true);
				calendar.setTime20(true);
				calendar.setTime21(true);
				calendar.setTime22(true);
				calendar.setTime23(false);
				mongoTemplate.save(calendar);
			}

		}
		Update update = new Update( );
		update.set("state", Integer.parseInt(check));
		WriteResult positionOrderId=null;
		positionOrderId= mongoTemplate.updateMulti(Query.query(Criteria.where("positionOrderId").is(id)), update, OrganPositionDetails.class);
		return positionOrderId;


	}

	@Override
	public List<Organ> queryOrganListByStaffId(String staffId) throws BaseException {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (staff == null)
			throw new BaseException("指定id的用户信息不存在");
		else {
			List<Organ> data = new ArrayList<Organ>();
			for (String organId : staff.getWeOrganIds()) {
				Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)), Organ.class);
				data.add(organ);
			}
			return data;
		}

	}

	@Override
	public void editStaffStatus(String userIds, String status) {

	}

	@Override
	public void editStaffFaceScore(String staffId, Integer faceScore) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		if (staff != null) {
			staff.setFaceScore(faceScore);
			mongoTemplate.save(staff);
		}

	}

	@Override
	public FlipInfo<Staff> queryStaffCheck(FlipInfo<Staff> fpi, String organId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("state").is(0), Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		List<WeOrganStaffVerify> staffVerifys = mongoTemplate.find(query, WeOrganStaffVerify.class);
		List<String> staffIds = new ArrayList<>();
		for (WeOrganStaffVerify staffVerify : staffVerifys) {
			staffIds.add(staffVerify.getStaffId());
		}
		Query queryByIds = new Query();
		queryByIds.addCriteria(Criteria.where("_id").in(staffIds));
		fpi = mongoTemplate.findByPage(queryByIds, fpi, Staff.class);
		return fpi;
	}

	@Override
	public WriteResult updateStaffVerify(String organId, String staffId, String checkResult, String memo) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("staffId").is(staffId),Criteria.where("state").is(0));
		query.addCriteria(criteria);
		Update update = new Update();
		update.set("state", Integer.parseInt(checkResult));
		update.set("memo", memo);
		WriteResult weOrganStaffVerify = null;
		weOrganStaffVerify = mongoTemplate.updateFirst(query, update, WeOrganStaffVerify.class);
		return weOrganStaffVerify;
	}

	@Override
	public void findOneStaffAndAddOrganId(String staffId, String organId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(staffId));
		Update update = new Update();
		update.push("weOrganIds", organId);
		mongoTemplate.updateFirst(query, update, Staff.class);
	}

	@Override
	public List<Staff> exportStaff(String phone, String organ, String name, String sex, Integer startYear,
			Integer endYear) throws BaseException {
	     	List<Criteria> criterias = new ArrayList<Criteria>();
    		List<String> organIds = new ArrayList<String>();

    		//如果店铺名称不为空则查询符合店铺名称的店铺列表
    		if(!StringUtils.isEmpty(organ)){
	  		List<Organ> organList = mongoTemplate.find(Query.query(Criteria.where("name").regex(organ)), Organ.class);
			if(organList!=null && organList.size()>0){
				for(Organ o:organList){
					if(!organIds.contains(o.get_id())){
				 		organIds.add(o.get_id());
				    	}
			    	}
		    	}
	    	}
			if(organIds.size()>0){
				criterias.add(Criteria.where("organId").in(organIds));
			}
			if(!StringUtils.isEmpty(phone)){
				criterias.add(Criteria.where("phone").regex(phone));
			}
			if(!StringUtils.isEmpty(name)){
				criterias.add(Criteria.where("name").regex(name));
			}
			if(!StringUtils.isEmpty(sex)){
				criterias.add(Criteria.where("sex").is(sex));
			}
			if(startYear!=null){
				criterias.add(Criteria.where("workYears").gte(startYear));
			}
			if(endYear!=null){
				criterias.add(Criteria.where("workYears").lte(endYear));
			}
			Criteria criteria = new Criteria();
			if(criterias.size()>0&&criterias!=null){
				criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
			}
			List<Staff>list = mongoTemplate.find(Query.query(criteria), Staff.class);
			if(organIds.size()>0){
				getStaffDetail(list);
			}else{
				Organ organ1=null;
				for(Staff staff:list){				
					organ1=mongoTemplate.findOne(Query.query(Criteria.where("_id").in(staff.getOrganId())),Organ.class );
					if(organ1!=null){
						staff.setOrganName(organ1.getName());
					}
					
				}
			}
	    	return list;
	}
	public void getStaffDetail(List<Staff> data){
		List<String> organIds = new ArrayList<String>();
		for(Staff staff:data){
			if(!organIds.contains(staff.getOrganId())){
				organIds.add(staff.getOrganId());
			}
		}
		Map<String,Organ> organMap = new HashMap<String,Organ>();
		if(organIds.size()>0){
			List<Organ> list = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
			for(Organ organ:list){				
				organMap.put(organ.get_id(), organ);
			}
		}
		for(Staff staff:data){
			Organ organ = organMap.get(staff.getOrganId());
			if(organ!=null)
			staff.setOrganName(organ.getName());
		}
	}

	@Override
	public List<User> exportUserManagement(String phone, Date startTime, Date endTime) {
		List<Criteria> criterias = new ArrayList<Criteria>();
		if(!StringUtils.isEmpty(phone)){
			criterias.add(Criteria.where("phone").regex(phone));
		}
		if(startTime!=null){
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if(endTime!=null){
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		Criteria criteria = new Criteria();
		if(criterias.size()>0)
		{
			criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		}

		List<User> list = mongoTemplate.find(Query.query(criteria), User.class);
		return list;
	}

//	public ReturnStatus UpdateState(String userId, String internalStaff){
//		ReturnStatus result;
//		User user = mongoTemplate.findById(userId,User.class);
//		if (user == null) {
//			result = new ReturnStatus(false);
//			result.setMessage("指定id的公司信息不存在");
//		} else {
//			mongoTemplate.save(user);
//			result = new ReturnStatus(true);
//			result.setEntity(user); // 返回更新后的对象
//		}
//		return result;
//	}
@Override
public FlipInfo<Userpart> queryByFpi(FlipInfo<Userpart> fpi,String organName) throws BaseException {
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
	if(StringUtils.isEmpty(organName)||organName==null){
		fpi = mongoTemplate.findByPage(query, criteria, fpi, Userpart.class);
		if(criteria!=null){
			fpi = mongoTemplate.findByPage(query, criteria, fpi, Userpart.class);
		}else {
			fpi = mongoTemplate.findByPage(null, criteria, fpi, Userpart.class);

		}
	}else {
		List<Organ> organList = mongoTemplate.find(Query.query(Criteria.where("name").is(organName)), Organ.class);
		List<String> organIdList = new ArrayList<String>();
		for (Organ o : organList) {
			organIdList.add(o.get_id());
		}
		if(criteria!=null){
			fpi = mongoTemplate.findByPage(Query.query(Criteria.where("organId").in(organIdList).andOperator(criteria)), fpi, Userpart.class);
		}else {
			fpi = mongoTemplate.findByPage(Query.query(Criteria.where("organId").in(organIdList)), fpi, Userpart.class);

		}
	}
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
				/*userpart.setNowMoney4(incard.getMoney4() - incard.getMoney_qian()); // 最新卡余额(扣减欠费)*/
				userpart.setNowMoney4(incard.getMoney4());
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
					userpart.setNowMoney4(incard.getDanci_money() * incard.getShengcishu());
					// userpart.setShengcishu(userpart.getShengcishu() -
					// userpart.getCishu());
					// userpart.setMoney4(incard.getDanci_money() *
					// userpart.getShengcishu());
				}
			}

			if (!StringUtils.isEmpty(usersort) && usersortMap.containsKey(usersort)) {
				userpart.setUsersortName(usersortMap.get(usersort).getName1());
			}
			if (!noSmallsort && !StringUtils.isEmpty(smallsort) && smallsortMap.containsKey(smallsort)) {
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


}
