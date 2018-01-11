package com.mrmf.service.weuser;

import com.mrmf.entity.user.Bigsort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Account;
import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeCarousel;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeStaffCalendar;
import com.mrmf.entity.WeStaffCase;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.GpsUtil;
import com.osg.framework.util.PositionUtil;
import com.thoughtworks.xstream.security.RegExpTypePermission;
@Service("weUserService")
public class WeUserServiceImpl implements WeUserService {
	@Autowired
	private EMongoTemplate mongoTemplate;
	
	@Override
	public FlipInfo<WeStaffCase> queryBeautyALL(FlipInfo<WeStaffCase> fpiWeStaffCases,String type,
			String hotOrder,String priceOrder,double longitude,double latitude, String city,int priceCount,List<String> typeList) {
//		fpiWeStaffCases.getParams().put("realType", "hairType");
		fpiWeStaffCases.getParams().put("weixin|boolean", "true");
		Criteria criteria=new Criteria();
		if(type.equals("所有类型")) {
			fpiWeStaffCases.getParams().remove("type");
			//criteria.where("type").in(typeList);
		}else {
			fpiWeStaffCases.getParams().put("type", type);
		}
		if(hotOrder!=null &&!hotOrder.equals("")) {
			fpiWeStaffCases.setSortField(hotOrder);   //热度排序
			fpiWeStaffCases.setSortOrder("DESC");
			if(type.equals("所有类型")) {
				fpiWeStaffCases = mongoTemplate.findByPage(Query.query(Criteria.where("type").in(typeList)), fpiWeStaffCases, WeStaffCase.class);
				//criteria.where("type").in(typeList);
			}else {
				fpiWeStaffCases = mongoTemplate.findByPage(null, fpiWeStaffCases, WeStaffCase.class);
			}
		}else if(priceOrder!=null && !priceOrder.equals("")) {
			if(type.equals("所有类型")) {
				fpiWeStaffCases = mongoTemplate.findByPageGeo(longitude, latitude, 20, Query.query(Criteria.where("type").in(typeList)), fpiWeStaffCases, WeStaffCase.class);
				//criteria.where("type").in(typeList);
			}else {
				fpiWeStaffCases = mongoTemplate.findByPageGeo(longitude, latitude, 20, null, fpiWeStaffCases, WeStaffCase.class);
			}
			sortByStartPrice(fpiWeStaffCases,priceCount);
		} else {
			if(type.equals("所有类型")) {
				fpiWeStaffCases = mongoTemplate.findByPageGeo(longitude, latitude, 20, Query.query(Criteria.where("type").in(typeList)), fpiWeStaffCases, WeStaffCase.class);				//criteria.where("type").in(typeList);
			}else {
				fpiWeStaffCases = mongoTemplate.findByPageGeo(longitude, latitude, 20, null, fpiWeStaffCases, WeStaffCase.class);			}

			sortByStartPrice(fpiWeStaffCases,priceCount);
		}
		return fpiWeStaffCases;
	}
	
	/**
	 * 排序
	 * @param fpiWeStaffCases
	 */
	private void sortByStartPrice(FlipInfo<WeStaffCase> fpiWeStaffCases, int priceCount) {
		if (priceCount == 1) {
			Collections.sort(fpiWeStaffCases.getData(), new Comparator<WeStaffCase>() {
				public int compare(WeStaffCase weStaffCaseSmall, WeStaffCase weStaffCaseBig) {
					if (weStaffCaseSmall.getPrice() > weStaffCaseBig.getPrice()) {
						return 1;
					} else if (weStaffCaseSmall.getPrice() == weStaffCaseBig.getPrice()) {
						return 0;
					} else {
						return -1;
					}
				}
			});
		} else {
			Collections.sort(fpiWeStaffCases.getData(), new Comparator<WeStaffCase>() {
				public int compare(WeStaffCase weStaffCaseSmall, WeStaffCase weStaffCaseBig) {
					if (weStaffCaseSmall.getPrice() > weStaffCaseBig.getPrice()) {
						return -1;
					} else if (weStaffCaseSmall.getPrice() == weStaffCaseBig.getPrice()) {
						return 0;
					} else {
						return 1;
					}
				}
			});
		}
	}


	@Override
	public FlipInfo<WeStaffCase> queryTypeProgramALL(FlipInfo<WeStaffCase> fpiWeStaffCases,String type,
			String hotOrder,String priceOrder,double longitude,double latitude, String city) {
		Query query = new Query();
		query.addCriteria(Criteria.where("weixin").is(true));
		query.addCriteria(Criteria.where("city").is(city));
		if(type!=null && !type.equals("")) {
			if(type.equals("养生")) {
				type="zuLiaoType";
			} else if(type.equals("美甲")) {
			    type="meiJiaType";
			} else if(type.equals("美容")) {
			    type="meiRongType";
			}
			List<Code> codeList=new ArrayList<Code>();
			codeList=mongoTemplate.find(Query.query(Criteria.where("type").is(type)),Code.class);
			List<String> nameList=new ArrayList<String>();
			for (Code c:codeList){
				nameList.add(c.getName());
			}
			query.addCriteria(Criteria.where("type").in(nameList));
		}
		if(hotOrder!=null &&!hotOrder.equals("")) {
			fpiWeStaffCases.setSortField(hotOrder);   //热度排序
			fpiWeStaffCases.setSortOrder("DESC");
		}
		if(priceOrder!=null && !priceOrder.equals("")) {
			fpiWeStaffCases.setSortField("distance");   //安装距离升序排序
			fpiWeStaffCases.setSortOrder("ASC");
			fpiWeStaffCases = mongoTemplate.findByPageGeo(longitude, latitude, -1, query, fpiWeStaffCases, WeStaffCase.class);
			sortByStartPrice(fpiWeStaffCases,1);
		}else {
			fpiWeStaffCases = mongoTemplate.findByPageGeo(longitude, latitude, -1, query, fpiWeStaffCases, WeStaffCase.class);
		}
		return fpiWeStaffCases;
	}
	
	@Override
	public FlipInfo<Staff> queryStaffALL(Query query,FlipInfo<Staff> fpi,double longitude,double latitude) {
		query.addCriteria(Criteria.where("logo").ne(""));
		query.addCriteria(Criteria.where("weixin").is(true));
		query.addCriteria(Criteria.where("weOrganIds").ne(null));
		fpi=mongoTemplate.findByPage(query, fpi, Staff.class);
		for(Staff staff:fpi.getData()){
			double dis=GpsUtil.distance(latitude, longitude,staff.getGpsPoint().getLatitude(),staff.getGpsPoint().getLongitude());
			if(dis<1){
				DecimalFormat df  = new DecimalFormat("###.000");
				staff.setDistance(Double.parseDouble(df.format(dis))*1000);	
				staff.setUnit("m");
			}else{
				DecimalFormat df1  = new DecimalFormat("###.0");
				staff.setDistance(Double.parseDouble(df1.format(dis)));
				staff.setUnit("km");
			}
		}
		return fpi;
	}




	@Override
	public FlipInfo<Staff> queryStaffByUser(Query query,double longitude, double latitude,
			double maxDistance,FlipInfo<Staff> fpi){
		fpi.setSortField("distance");
		fpi.setSortOrder("ASC");
		query.addCriteria(Criteria.where("logo").ne(""));
		query.addCriteria(Criteria.where("weixin").is(true));
		query.addCriteria(Criteria.where("weOrganIds").ne(null));
		fpi=mongoTemplate.findByPageGeo(longitude, latitude, maxDistance, query, fpi, Staff.class);
		for(Staff staff:fpi.getData()){
			double dis=GpsUtil.distance(latitude,longitude,staff.getGpsPoint().getLatitude(),staff.getGpsPoint().getLongitude());
			if(dis<1){
				DecimalFormat df  = new DecimalFormat("###.000");
				staff.setDistance(Double.parseDouble(df.format(dis))*1000);
				staff.setUnit("m");
			}else{
				DecimalFormat df1  = new DecimalFormat("###.0");
				staff.setDistance(Double.parseDouble(df1.format(dis)));
				staff.setUnit("km");
			}
		}
		return fpi;
	}

	@Override
	public FlipInfo<Staff> queryStaffByName(Query query,double longitude, double latitude,
											double maxDistance,FlipInfo<Staff> fpi,String name){
		fpi.setSortField("distance");
		fpi.setSortOrder("ASC");
		query.addCriteria(Criteria.where("logo").ne(""));
		query.addCriteria(Criteria.where("weixin").is(true));
		query.addCriteria(Criteria.where("weOrganIds").ne(null));
		query.addCriteria(Criteria.where("name").regex(name));
		fpi=mongoTemplate.findByPageGeo(longitude,latitude,maxDistance, query, fpi, Staff.class);
		for(Staff staff:fpi.getData()){
			double dis=GpsUtil.distance(latitude,longitude,staff.getGpsPoint().getLatitude(),staff.getGpsPoint().getLongitude());
			if(dis<1){
				DecimalFormat df  = new DecimalFormat("###.000");
				staff.setDistance(Double.parseDouble(df.format(dis))*1000);
				staff.setUnit("m");
			}else{
				DecimalFormat df1  = new DecimalFormat("###.0");
				staff.setDistance(Double.parseDouble(df1.format(dis)));
				staff.setUnit("km");
			}
		}
		return fpi;
	}


	@Override
	public List<Code> findBeautyType(String hairType) {
		List<Code> types = mongoTemplate.find(Query.query(Criteria.where("type").is(hairType)), Code.class);
		return types;
	}

	@Override
	public FlipInfo<WeStaffCase> queryBeautyCase(FlipInfo<WeStaffCase> fpiWeStaffCases,String title,String homeType) {
		Query query = new Query();
		query.addCriteria(Criteria.where("title").regex(title));
		query.addCriteria(Criteria.where("weixin").is(true));
		if(!StringUtils.isEmpty(homeType)) {
			if(homeType.equals("精美发型")) {
				List<Code> codes=new ArrayList<Code>();
				codes=mongoTemplate.find(Query.query(Criteria.where("type").is("hairType")),Code.class);
				List<String> names=new ArrayList<String>();
				for (Code c:codes){
					names.add(c.getName());
				}
				query.addCriteria(Criteria.where("type").in(names));
			} else if(homeType.equals("养生")) {
				List<Code> codes=new ArrayList<Code>();
				codes=mongoTemplate.find(Query.query(Criteria.where("type").is("zuLiaoType")),Code.class);
				List<String> names=new ArrayList<String>();
				for (Code c:codes){
					names.add(c.getName());
				}
				query.addCriteria(Criteria.where("type").in(names));
			} else if(homeType.equals("美甲")) {
				List<Code> codes=new ArrayList<Code>();
				codes=mongoTemplate.find(Query.query(Criteria.where("type").is("meiJiaType")),Code.class);
				List<String> names=new ArrayList<String>();
				for (Code c:codes){
					names.add(c.getName());
				}
				query.addCriteria(Criteria.where("type").in(names));
			} else if(homeType.equals("美容")) {
				List<Code> codes=new ArrayList<Code>();
				codes=mongoTemplate.find(Query.query(Criteria.where("type").is("meiRongType")),Code.class);
				List<String> names=new ArrayList<String>();
				for (Code c:codes){
					names.add(c.getName());
				}
				query.addCriteria(Criteria.where("type").in(names));
			}
		}
		FlipInfo<WeStaffCase> weStaffCases = mongoTemplate.findByPage(query,fpiWeStaffCases,WeStaffCase.class);
		return weStaffCases;
	}

	@Override
	public FlipInfo<Staff> queryStaff(FlipInfo<Staff> fpiWeStaffCases, String name) {

		Query query = new Query();
		query.addCriteria(Criteria.where("title").regex(name));
		query.addCriteria(Criteria.where("weixin").is(true));
		FlipInfo<Staff> weStaffCases = mongoTemplate.findByPage(query,fpiWeStaffCases,Staff.class);
		return weStaffCases;
	}


	@Override
	public Staff queryStaffById(String staffId) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)), Staff.class);
		return staff;
	}

	@Override
	public User queryUserById(String userId) {
		User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
		return user;
	}

	@Override
	public WeStaffCase queryCaseById(String caseId) {
		WeStaffCase weStaffCase = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(caseId)), WeStaffCase.class);
		return weStaffCase;
	}
	
	@Override
	public Staff queryStaffByIdAndDistance(String staffId,double longitude, double latitude) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)), Staff.class);
		double dis=GpsUtil.distance(latitude, longitude,staff.getGpsPoint().getLatitude() , staff.getGpsPoint().getLongitude());
		if(dis<1){
			DecimalFormat df  = new DecimalFormat("###.000");
			staff.setDistance(Double.parseDouble(df.format(dis))*1000);
			staff.setUnit("m");
		}else{
			DecimalFormat df1  = new DecimalFormat("###.0");
			staff.setDistance(Double.parseDouble(df1.format(dis)));
			staff.setUnit("km");
		}
		return staff;
	}
	@Override
	public FlipInfo<WeStaffCase> queryCaseByStaffId(String staffId,
			FlipInfo<WeStaffCase> weStaffCases) {
		Query query = new Query();
		query.addCriteria(Criteria.where("staffId").is(staffId));
		return mongoTemplate.findByPage(query, weStaffCases, WeStaffCase.class);
	}
	
	@Override
	public void addAppoint(WeOrganOrder weOrganOrder) {
		mongoTemplate.save(weOrganOrder);
	}
	@Override
	public User queryUserByOpenId(String openid) {
		User user=null;
		Account account=mongoTemplate.findOne(Query.query(Criteria.where("accountName").is(openid)), Account.class);
		if(account!=null){
			user=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(account.getEntityID())), User.class);
		}
		return user;
	}
	@Override
	public void updateFollowCount(int followCount,String caseId) {
		Update update = new Update();
		update.set("followCount", followCount);
		mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(caseId)), update, WeStaffCase.class);
	}
	@Override
	public Organ findOrganById(String organId) {
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		if (organ !=null) {
			GpsPoint gpsPoint = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
			organ.setGpsPoint(gpsPoint);
		}
		return organ;
	}
	@Override
	public User findUserByPhone(String phone) {
		Query query = new Query();
		query.addCriteria(Criteria.where("phone").is(phone));
		User user = mongoTemplate.findOne(query, User.class);
		if(user != null) {
			return user;
		}
 		return null;
	}
	
	
	@Override
	public boolean findStaffServiceTime(String staffId) {
		Query query = new Query();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = simpleDateFormat.format(date);
		int intDate = Integer.parseInt(currentDate);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("day").gte(intDate).lte(intDate+6),Criteria.where("staffId").is(staffId));
		query.addCriteria(criteria);
		List<WeStaffCalendar> weStaffCalendars = mongoTemplate.find(query, WeStaffCalendar.class);
		boolean flag = false;
		for (WeStaffCalendar weStaffCalendar : weStaffCalendars) {
			flag = weStaffCalendar.isTime0()?true:flag;
			flag = weStaffCalendar.isTime1()?true:flag;
			flag = weStaffCalendar.isTime2()?true:flag;
			flag = weStaffCalendar.isTime3()?true:flag;
			flag = weStaffCalendar.isTime4()?true:flag;
			flag = weStaffCalendar.isTime5()?true:flag;
			flag = weStaffCalendar.isTime6()?true:flag;
			flag = weStaffCalendar.isTime7()?true:flag;
			flag = weStaffCalendar.isTime8()?true:flag;
			flag = weStaffCalendar.isTime9()?true:flag;
			flag = weStaffCalendar.isTime10()?true:flag;
			flag = weStaffCalendar.isTime11()?true:flag;
			flag = weStaffCalendar.isTime12()?true:flag;
			flag = weStaffCalendar.isTime13()?true:flag;
			flag = weStaffCalendar.isTime14()?true:flag;
			flag = weStaffCalendar.isTime15()?true:flag;
			flag = weStaffCalendar.isTime16()?true:flag;
			flag = weStaffCalendar.isTime17()?true:flag;
			flag = weStaffCalendar.isTime18()?true:flag;
			flag = weStaffCalendar.isTime19()?true:flag;
			flag = weStaffCalendar.isTime20()?true:flag;
			flag = weStaffCalendar.isTime21()?true:flag;
			flag = weStaffCalendar.isTime22()?true:flag;
			flag = weStaffCalendar.isTime23()?true:flag;
		}
		return flag;
	}
	
	//验证订单是否存在
	@Override
	public ReturnStatus verifyOrder(WeOrganOrder weOrder) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.where("staffId").is(weOrder.getStaffId());
		criteria.and("userId").is(weOrder.getUserId());
		criteria.and("organId").is(weOrder.getOrganId());
		criteria.and("orderTime").is(weOrder.getOrderTime());
		criteria.and("orderPrice").is(weOrder.getOrderPrice());
		criteria.and("title").is(weOrder.getTitle());
		query.addCriteria(criteria);
		WeOrganOrder order = mongoTemplate.findOne(query, WeOrganOrder.class);
		if (order !=null) {
			return new ReturnStatus(false);
		}
		return new ReturnStatus(true);
	}
	@Override
	public List<WeBCity> cityList() {
		return mongoTemplate.find(null, WeBCity.class);
	}
	
	@Override
	public List<WeStaffCase> findWeStaffCases(String staffId) {
		return mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId)), WeStaffCase.class);
	}

	@Override
	public boolean isHaveWeStaffCase(String staffId) {
		return mongoTemplate.exists(Query.query(Criteria.where("staffId").is(staffId)), WeStaffCase.class);
	}

	@Override
	public List<WeCarousel> findCarousels() {
		Query query = new Query();
		query.addCriteria(Criteria.where("flag").is(false));
		return mongoTemplate.find(query, WeCarousel.class);
	}

	@Override
	public List<Code> findCodeByType(String type) {
		return mongoTemplate.find(Query.query(Criteria.where("type").is(type)), Code.class);
	}

	@Override
	public List<Bigsort> findBigSortByOrganId(String organId) {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), Bigsort.class);
	}

	
	
	/*@Override
	public void addCaseByLocation() {
		List<WeStaffCase> weStaffCases = mongoTemplate.find(null, WeStaffCase.class);
		for (WeStaffCase weStaffCase : weStaffCases) {
			if(weStaffCase != null) {
				String staffId = weStaffCase.getStaffId();
				if(!StringUtils.isEmpty(staffId)) {
					Staff staff = mongoTemplate.findById(staffId, Staff.class);
					if(staff != null) {
						if(!staff.getParentId().equals("0")) {
							Organ organ = mongoTemplate.findById(staff.getParentId(), Organ.class);
							if(organ != null ) {
								weStaffCase.setGpsPoint(organ.getGpsPoint());
								weStaffCase.setCity(organ.getCity());
								mongoTemplate.save(weStaffCase);
							}
						} else {
							Organ organ = mongoTemplate.findById(staff.getOrganId(), Organ.class);
							if(organ != null ) {
								weStaffCase.setGpsPoint(organ.getGpsPoint());
								weStaffCase.setCity(organ.getCity());
								mongoTemplate.save(weStaffCase);
							}
						}
					}
				}
				
			}
		}
	}*/
	
}
