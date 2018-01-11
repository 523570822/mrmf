package com.mrmf.service.weorgan;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrmf.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrmf.entity.bean.WeCommonCalendar;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.userPay.UserPayService;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.GpsUtil;
import com.osg.framework.util.PositionUtil;
@Service("weOrganService")
public class WeOrganServiceImpl implements WeOrganService{
	@Autowired
	private EMongoTemplate mongoTemplate;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserPayService userPayService;
    private String phone;
    private String code;
    private String organId;

    @Override
	public Organ queryOrgan(String oppenid) throws BaseException {
		Organ organ=null;
		Account account=mongoTemplate.findOne(Query.query(Criteria.where("accountName").is(oppenid)), Account.class);
		if(account!=null){
			organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(account.getEntityID())), Organ.class);
		}
		return organ;
	}
	@Override
	public Organ queryOrganById(String organId) throws BaseException {
		Organ organ;
		organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)), Organ.class);
		return organ;
	}
	@Override
	public ReturnStatus updateOrgan(Organ organ) throws BaseException {
		mongoTemplate.save(organ);
		return new ReturnStatus(true);
	}
	@Override
	public WeOrganCalendar queryWeOrganCalendarByOrganId(String organId,int day)
			throws BaseException {
		WeOrganCalendar weOrganCalendar;
		Criteria criteria=new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("day").is(day));
		weOrganCalendar = mongoTemplate.findOne(Query.query(criteria), WeOrganCalendar.class);
		if(weOrganCalendar==null){
			WeekDay week = new WeekDay();
			Date date = week.intParseDate(day);
			weOrganCalendar = dayOfWeek(date);
			weOrganCalendar.setIdIfNew();
			weOrganCalendar.setOrganId(organId);
			weOrganCalendar.setDay(day);
			mongoTemplate.save(weOrganCalendar);
		}
		return weOrganCalendar;
	}
	@Override
	public ReturnStatus updateWeOrganCalendarByOrganId(String organId,int day,
			int index, boolean selected) {
		WeOrganCalendar weOrganCalendar=null;
		Criteria criteria=new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("day").is(day));
		weOrganCalendar = mongoTemplate.findOne(Query.query(criteria), WeOrganCalendar.class);
		if(weOrganCalendar==null){
			weOrganCalendar=new WeOrganCalendar();
			weOrganCalendar.setOrganId(organId);
			weOrganCalendar.setDay(day);
		}
		weOrganCalendar.setIdIfNew();
		switch(index){
		case 0:weOrganCalendar.setTime0(selected);break;
		case 1:weOrganCalendar.setTime1(selected);break;
		case 2:weOrganCalendar.setTime2(selected);break;
		case 3:weOrganCalendar.setTime3(selected);break;
		case 4:weOrganCalendar.setTime4(selected);break;
		case 5:weOrganCalendar.setTime5(selected);break;
		case 6:weOrganCalendar.setTime6(selected);break;
		case 7:weOrganCalendar.setTime7(selected);break;
		case 8:weOrganCalendar.setTime8(selected);break;
		case 9:weOrganCalendar.setTime9(selected);break;
		case 10:weOrganCalendar.setTime10(selected);break;
		case 11:weOrganCalendar.setTime11(selected);break;
		case 12:weOrganCalendar.setTime12(selected);break;
		case 13:weOrganCalendar.setTime13(selected);break;
		case 14:weOrganCalendar.setTime14(selected);break;
		case 15:weOrganCalendar.setTime15(selected);break;
		case 16:weOrganCalendar.setTime16(selected);break;
		case 17:weOrganCalendar.setTime17(selected);break;
		case 18:weOrganCalendar.setTime18(selected);break;
		case 19:weOrganCalendar.setTime19(selected);break;
		case 20:weOrganCalendar.setTime20(selected);break;
		case 21:weOrganCalendar.setTime21(selected);break;
		case 22:weOrganCalendar.setTime22(selected);break;
		case 23:weOrganCalendar.setTime23(selected);break;
		}
		
		mongoTemplate.save(weOrganCalendar);
		return new ReturnStatus(true);
	}
	@Override
	public User queryUserById(String userId) throws BaseException {
		User user=null;
		user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
		return null;
	}

	@Override
	public FlipInfo<Organ> queryOrganListByUser(double longitude,
			double latitude, double maxDistance, FlipInfo<Organ> fpi) {
		fpi=mongoTemplate.findByPageGeo(longitude, latitude, maxDistance, null, fpi, Organ.class);
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
	@Override
	public FlipInfo<Organ> queryOrganListByfollowCount(double longitude, double latitude,FlipInfo<Organ> fpi) {
		fpi=mongoTemplate.findByPage(null, fpi, Organ.class);
		for(Organ organ:fpi.getData()){
//			System.out.println(organ.get_id());
			double dis=GpsUtil.distance(latitude, longitude,organ.getGpsPoint().getLatitude() , organ.getGpsPoint().getLongitude());
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
	public FlipInfo<WeMessage> queryOrganMessageList(FlipInfo<WeMessage> fpi) {
		fpi=mongoTemplate.findByPage(null, fpi, WeMessage.class);
		return fpi;
	}
	@Override
	public Map<String, Integer> queryfavorOrganUserCount(String organId) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		
		int count=0;
		//关注店铺数量
		count=(int) mongoTemplate.count(Query.query(Criteria.where("favorOrganIds").all(organId)), User.class);
		map.put("favorOrgan", count);
		//预约店铺数量
		count=(int)mongoTemplate.count(Query.query(Criteria.where("orderHisIds").all(organId)), User.class);
		map.put("orderHisIds", count);
		//会员客户数量
		count=(int)mongoTemplate.count(Query.query(Criteria.where("organId").is(organId).and("userId").nin("0")), Usercard.class);
		map.put("vip", count);
		return map;
	}
	@Override
	public FlipInfo<User> queryOrganUserList(int type, String organid,
			FlipInfo<User> fpi) {
		List<String> list=new ArrayList<String>();
		if(type==0){//查询预约客户列表
			fpi=mongoTemplate.findByPage(Query.query(Criteria.where("orderHisIds").all(organid)), fpi, User.class);
		}else if(type==1){ //查询会员客户
			List<Usercard> usercards = mongoTemplate.find(Query.query(Criteria.where("organId").is(organid)), Usercard.class);
			for (Usercard usercard : usercards) {
				list.add(usercard.getUserId());
			}
			fpi=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(list)), fpi, User.class);
		}else if(type==2){ //查询关注客户
			fpi=mongoTemplate.findByPage(Query.query(Criteria.where("favorOrganIds").all(organid)), fpi, User.class);
		}
		for(User user:fpi.getData()){
			Criteria criteria=new Criteria();
			criteria.andOperator(Criteria.where("organId").is(organid),Criteria.where("userId").is(user.get_id()));
			int count=(int)mongoTemplate.count(Query.query(criteria), WeOrganOrder.class);
			user.setOrderNum(count);
			//判断是否是会员用户
			List<Usercard> list2 = mongoTemplate.find(Query.query(Criteria.where("organId").is(organid).and("userId").is(user.get_id())), Usercard.class);
			if (list2.size()>0) {
				user.setUserType("会员");
			}
			if(type==0){
				Criteria c=new Criteria();
				c.andOperator();
				Query query=new Query();
				query.addCriteria(Criteria.where("userId").is(user.get_id()));
				query.with(new Sort(Direction.DESC,"orderTime"));
				//Sort sort=new Sort();
				WeOrganOrder order=mongoTemplate.findOne(query, WeOrganOrder.class);
				user.setOrderTime(order.getOrderTime());
			}
		}
		
		return fpi;
	}
	@Override
	public Map<String, List> queryDistrictList(String cityId,String districtId) {
		List<WeBDistrict>  weBDistrictList=new ArrayList<WeBDistrict>();
		List<WeBRegion> weBRegionList=new ArrayList<WeBRegion>();
		weBDistrictList=mongoTemplate.find(Query.query(Criteria.where("cityId").is(cityId)), WeBDistrict.class);
		if(StringUtils.isEmpty(districtId)){
			weBRegionList=mongoTemplate.find(Query.query(Criteria.where("cityId").is(cityId)), WeBRegion.class);
		}else{
			Criteria criteria=new Criteria();
			criteria.andOperator(Criteria.where("cityId").is(cityId),Criteria.where("districtId").is(districtId));
			weBRegionList=mongoTemplate.find(Query.query(criteria), WeBRegion.class);
		}
		Map<String,List> map=new HashMap<String,List>();
		map.put("district", weBDistrictList);
		map.put("region", weBRegionList);
		return map;
	}
	@Override
	public ReturnStatus verify(String phone, String code,String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("phone").is(phone), Criteria.where("_id").is(organId));
		Organ organ = mongoTemplate.findOne(Query.query(criteria), Organ.class);
		if (organ == null) {
			return new ReturnStatus(false, "指定手机号未关联到公司信息！");
		}
		ReturnStatus status = accountService.verify(phone, code);
		return status;
	}
	@Override
	public ReturnStatus savePwd(String pwd, String organId) {
		Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)), Organ.class);
		if(organ!=null){
			organ.setPayPassword(pwd);
			mongoTemplate.save(organ);
		}
		return new ReturnStatus(true);
	}
	@Override
	public List<Bigsort> queryOrganType(String organId) {
		Criteria criteria=new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId));
		List<Bigsort> list=mongoTemplate.find(Query.query(criteria), Bigsort.class);
		return list;
	}
	@Override
	public FlipInfo<Userpart> queryOrganEarnList(String organId,FlipInfo<Userpart> fpi) {
		Map<String,Date> todayTime=getTodayStartAndEnd();
		Date st=(Date)todayTime.get("st");
		Date et=(Date)todayTime.get("et");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Criteria criteria=new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("flag2").is(true),Criteria.where("delete_flag").is(false),Criteria.where("createTime").gt(st).lt(et));
		fpi=mongoTemplate.findByPage(Query.query(criteria), fpi, Userpart.class);
		for(Userpart up:fpi.getData()){
			Date ct=up.getCreateTime();
			String ctstr=sdf.format(ct);
			up.setCreateTimeFormat(ctstr);
			Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(up.getOrganId())), Organ.class);
			up.setOrganName(organ.getName());
			Smallsort smt=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(up.getSmallsort())), Smallsort.class);
			up.setSmallsortName(smt.getName());
		}
		
		return fpi;
	}
	//根据id存储地理位置
	@Override
	public void saveLocation(String userId, String type, String latitude,
			String longitude) {
		System.out.println("staff save userId and type -------"+userId+"-----------"+type);
		double lat=40.042274958016,lon=116.31362100748;
		if (latitude !=null && !latitude.equals("")) {
			lat=Double.parseDouble(latitude);
		}
		if (longitude !=null && !longitude.equals("")) {
			lon=Double.parseDouble(longitude);
		}
		GpsPoint gpsPoint =new GpsPoint();
		gpsPoint.setLatitude(lat);
		gpsPoint.setLongitude(lon);
		if (type.equals("organ")) {
			Organ organ = mongoTemplate.findById(userId, Organ.class);
			if (organ !=null) {
				organ.setGpsPoint(gpsPoint);
				mongoTemplate.save(organ);
				}
			}else if (type.equals("user")) {
				User user = mongoTemplate.findById(userId, User.class);
				if (user !=null) {
					user.setGpsPoint(gpsPoint);
					mongoTemplate.save(user);
				}
			}else if (type.equals("staff")) {
				Staff staff = mongoTemplate.findById(userId, Staff.class);
				if (staff !=null) {
					staff.setGpsPoint(gpsPoint);
					System.out.println("staff save -------"+gpsPoint);
					mongoTemplate.save(staff);
					System.out.println("staff saveSuccess -------"+staff.getGpsPoint());
				}
			}
		}

	
	@Override
	public double queryOrganEarnSumMoney(String organId) {
		Map<String,Date> todayTime=getTodayStartAndEnd();
		Date st=(Date)todayTime.get("st");
		Date et=(Date)todayTime.get("et");
		List<Userpart> userpartList=null;
		double sumMoney=0;
		Criteria criteria=new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("flag2").is(true),Criteria.where("delete_flag").is(false),Criteria.where("createTime").gt(st).lt(et));
		userpartList=mongoTemplate.find(Query.query(criteria), Userpart.class);
		for(Userpart up:userpartList){
			sumMoney=sumMoney+up.getMoney2();
		}
		return sumMoney;
	}
	
//	@Override
//	public FlipInfo<Organ> queryOragnListByUser(String type, String city,
//			String district, String region, double longitude, double latitude,
//			double maxDistance, String followCount, FlipInfo<Organ> fpi) {
//		return mongoTemplate.findByPageGeo(longitude, latitude, maxDistance, null, fpi, Organ.class);
//	}
	public Map<String,Date> getTodayStartAndEnd(){
		Map<String,Date> map=new HashMap<String,Date>();
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		String m="";
		String d="";
		Date st=null;
		Date et=null;
		if(month<10){
			m="0"+month;
		}else{
			m=month+"";
		}
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		if(day<10){
			d="0"+day;	
		}else{
			d=day+"";
		}
		String startTime=year+"-"+m+"-"+d+" 00:00:00";
		String endTime=year+"-"+m+"-"+d+" 23:59:59";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			st=sdf.parse(startTime);
			 et=sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("st", st);
		map.put("et", et);
		return map;
	}
	@Override
	public Map<String, String> organEarnInfo(String organId) {
		Map<String,Date> todayTime=getTodayStartAndEnd();
		Map<String,String> map=new HashMap<String,String>();
		List<Userpart> earnList=null;
		Date st=(Date)todayTime.get("st");
		Date et=(Date)todayTime.get("et");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Criteria criteria=new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("flag2").is(true),Criteria.where("delete_flag").is(false),Criteria.where("createTime").gte(st),Criteria.where("createTime").lte(DateUtil.addDate(et, 1)));
		earnList=mongoTemplate.find(Query.query(criteria), Userpart.class);
		double nonMember=0;//非会员消费
		double doMember=0;//办会员卡
		double member=0;//会员卡消费
		double weChat=0;//微信分账消费
		double memberRecharge=0;//会员卡充值
		double payArrearage=0;//补缴欠费
		double rxmCard=0;//任性猫卡余额
		double cashConsumption=0;//现金消费
		int cardNum=0;
		for(Userpart up:earnList){
			//Date ct=up.getCreateTime();
			//String ctstr=sdf.format(ct);
			//up.setCreateTimeFormat(ctstr);
			//Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(up.getOrganId())), Organ.class);
			//up.setOrganName(organ.getName());
			//Smallsort smt=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(up.getSmallsort())), Smallsort.class);
			//up.setSmallsortName(smt.getName());
			//判断消费类别并且将同类别想加
			if(up.getType()==0){
				String incardId=up.getIncardId();
				if(StringUtils.isEmpty(incardId)){
					nonMember=nonMember+up.getMoney2();
				}else{
					doMember=doMember+up.getMoney2();
					cardNum++;
				}
			}else if(up.getType()==1){
				member=member+up.getMoney2();
			}else if(up.getType()==2){
				weChat=weChat+up.getMoney2();
			}else if(up.getType()==3){
				memberRecharge=memberRecharge+up.getMoney2();
			}else if(up.getType()==4){
				payArrearage=payArrearage+up.getMoney2();
			}
		}
		cashConsumption=nonMember+doMember+memberRecharge;
		try {
			Usercard sysQuery = userPayService.sysQuery(organId);
			if(sysQuery != null){
				rxmCard=sysQuery.getMoney4();
			}else {
				rxmCard = 0;
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("###0.00");
		map.put("nonMember",df.format(nonMember) );
		map.put("cardNum",cardNum+"" );
		map.put("member",df.format(member ));
		map.put("weChat",df.format(weChat ));
		map.put("memberRecharge",df.format(memberRecharge ));
		map.put("payArrearage",df.format(payArrearage ));
		map.put("rxmCard",df.format(rxmCard));
		map.put("cashConsumption", df.format(cashConsumption));
		return map;
	}
	@Override
	public String queryCityId(String cityName) {
		WeBCity city=mongoTemplate.findOne(Query.query(Criteria.where("name").regex(cityName)), WeBCity.class);
		if(city!=null){
			return city.get_id();
		}
		return null;
	}
	@Override
	public FlipInfo<Smallsort> queryOrganSmallsort(FlipInfo<Smallsort> fpi) {
		fpi=  mongoTemplate.findByPage(null, fpi, Smallsort.class);
		return fpi;
	}
	@Override
	public List<WeMessage> existMessageNoRead(String organId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("toId").is(organId),Criteria.where("readFalg").is(false));
		List<WeMessage> messages = mongoTemplate.find(Query.query(criteria), WeMessage.class);
		return messages;
	}
	@Override
	public ReturnStatus readMessage(String organId,String type) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("toId").is(organId),Criteria.where("readFalg").is(false),Criteria.where("type").is(type));
		mongoTemplate.updateMulti(Query.query(criteria), Update.update("readFalg", true), WeMessage.class);
		return new ReturnStatus(true);
	}
	
	public  WeOrganCalendar dayOfWeek(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int weekday=calendar.get(Calendar.DAY_OF_WEEK);
		WeOrganCalendar cal=null;
		WeCommonCalendar cc= new WeCommonCalendar();
		switch(weekday){
		case Calendar.MONDAY:cal=(WeOrganCalendar)cc.weekday;break;
		case Calendar.TUESDAY:cal=(WeOrganCalendar)cc.weekday;break;
		case Calendar.WEDNESDAY:cal=(WeOrganCalendar)cc.weekday;break;
		case Calendar.THURSDAY:cal=(WeOrganCalendar)cc.weekday;break;
		case Calendar.FRIDAY:cal=(WeOrganCalendar)cc.weekday;break;
		case Calendar.SATURDAY:cal=(WeOrganCalendar)cc.weekend;break;
		case Calendar.SUNDAY:cal=(WeOrganCalendar)cc.weekend;break;
		}
		return cal;
	}

	@Override
	public void updatePwd(String organId, String pwd) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(organId));
		Update update = new Update();
		update.set("payPassword", pwd);
		mongoTemplate.updateFirst(query, update, Organ.class);
	}

	@Override
	public Organ queryOrganByPhone(String phone) {
		return mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Organ.class);

	}

	@Override
	public void updatePhone(String organId, String phone) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(organId));
		Update update = new Update();
		update.set("phone", phone);
		mongoTemplate.updateFirst(query, update, Organ.class);
	}


	@Override
	public FlipInfo<WeUserWalletHis> queryWallet(
			FlipInfo<WeUserWalletHis> weUserWalletHis,String organId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("amount").ne(0),Criteria.where("organId").is(organId));
		query.addCriteria(criteria);
		return mongoTemplate.findByPage(query, weUserWalletHis, WeUserWalletHis.class);
	}

	@Override
	public boolean queryWePayLog(String organId, int senderType, int status,int type) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId),
				Criteria.where("senderType").is(senderType),
				Criteria.where("status").is(status),Criteria.where("type").is(type)
		);
		query.addCriteria(criteria);
		return mongoTemplate.exists(query, WePayLog.class);
	}
	@Override
	public Account findOneAccount(String organId) {
        Query query  =  new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("entityID").is(organId),Criteria.where("accountType").is(Account.TYPE_ORGAN_ADMIN));
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, Account.class);
	}

	@Override
	public void saveWePayLog(WePayLog wePayLog) {
        mongoTemplate.save(wePayLog);
	}

    @Override
    public WePayLog queryWePayLogById(String id) {
        return mongoTemplate.findById(id, WePayLog.class);
    }

    @Override
    public void saveWeUserWalletHis(WeUserWalletHis weUserWalletHis) {
        mongoTemplate.save(weUserWalletHis);
    }

    @Override
    public void updateUserWalletAmount(String organId,double amount) {
        Update update = new Update();
        update.set("walletAmount", amount);
        Query query  = new Query();
        query.addCriteria(Criteria.where("_id").is(organId));
        mongoTemplate.updateFirst(query, update, Organ.class);
    }

    @Override
    public ReturnStatus organVerify(String phone, String code, String organId) {
        ReturnStatus status = accountService.verify(phone, code);
        if(status.isSuccess()){
            Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Organ.class);
            Organ u=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)), Organ.class);
            if(organ!=null){
                organ.setWeixin(u.getWeixin());
                organ.setState(u.getState());
                organ.setBusyTimeStart(u.getBusyTimeStart());
                organ.setBusyTimeEnd(u.getBusyTimeEnd());
                organ.setType(u.getType());
                organ.setLogo(u.getLogo());
                organ.setZanCount(u.getZanCount());
                organ.setQiuCount(u.getQiuCount());
                organ.setFollowCount(u.getFollowCount());
                organ.setDiscountInfo(u.getDiscountInfo());
                organ.setEvaluateCount(u.getEvaluateCount());
                organ.setImages(u.getImages());
                organ.setWalletAmount(u.getWalletAmount());
                organ.setPayPassword(u.getPayPassword());
                List<WeUserPayOrder> weUserPayOrders=mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), WeUserPayOrder.class);
                for(WeUserPayOrder order:weUserPayOrders){
                    order.setUserId(organ.get_id());
                    mongoTemplate.save(order);
                }
                List<WeUserPayFenzhang> weUserPayFenzhangs=mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), WeUserPayFenzhang.class);
                for(WeUserPayFenzhang fz:weUserPayFenzhangs){
                    fz.setUserId(organ.get_id());
                    mongoTemplate.save(fz);
                }
                mongoTemplate.remove(u);
                mongoTemplate.save(organ);
                Criteria c=new Criteria();
                c.andOperator(Criteria.where("entityID").is(organId),Criteria.where("accountType").is("organ"));
                Account account=mongoTemplate.findOne(Query.query(c), Account.class);
                account.setEntityID(organ.get_id());
                mongoTemplate.save(account);
            }else{
                u.setPhone(phone);
                mongoTemplate.save(u);
            }
        } else{
            return new ReturnStatus(false);
        }
        return new ReturnStatus(true);
    }

    @Override
    public void updateOrganWalletAmount(String organId,double amount) {
        Update update = new Update();
        update.set("walletAmount", amount);
        Query query  = new Query();
        query.addCriteria(Criteria.where("_id").is(organId));
        mongoTemplate.updateFirst(query, update, Query.class);
    }

	@Override
	public OrganPositionSetting getOrganPositionSetting(String organId) {
		return mongoTemplate.findOne(Query.query(Criteria.where("organId").is(organId)),OrganPositionSetting.class);

	}


	public FlipInfo<Organ> queryOrganPosition(FlipInfo<Organ> organList){
		List<String> list = new ArrayList<String>();
		for(Organ org:organList.getData()){
			list.add(org.get_id());
		}
		List<OrganPositionSetting> list1=mongoTemplate.find(Query.query(Criteria.where("organId").in(list)),OrganPositionSetting.class);
        for (OrganPositionSetting o:list1){
        	for (Organ oo:organList.getData()){
        		if(oo.get_id().equals(o.getOrganId())){
        			oo.setLeaseMoney(o.getLeaseMoney());
        			oo.setLeaseType(o.getLeaseType());
        			oo.setState1(o.getState());
        			oo.setNum1(o.getNum());
				}
			}
		}
		return organList;
	}
	public FlipInfo<Organ> queryOrganByParentId(FlipInfo<Organ> fpi,String organId){
		return mongoTemplate.findByPage(Query.query(Criteria.where("parentId").is(organId)),fpi,Organ.class);
	}
}

