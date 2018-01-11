package com.mrmf.service.userService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeStaffCalendar;
import com.mrmf.entity.WeUserInquiry;
import com.mrmf.entity.WeUserInquiryQuote;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userinincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.Usersort;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.GpsUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private EMongoTemplate mongoTemplate;
	//报价列表
	@Override
	public FlipInfo<WeUserInquiryQuote> getEnquiryList(String userId,String inquiryId,
			FlipInfo<WeUserInquiryQuote> fpi) {
		WeUserInquiry weUserInquiry = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), WeUserInquiry.class);
		if (weUserInquiry == null) {
			return null;
		}
		FlipInfo<WeUserInquiryQuote> weUserInquiryQuote = mongoTemplate.findByPage(Query.query(Criteria.where("inquiryId").is(inquiryId)), fpi, WeUserInquiryQuote.class);
		List<WeUserInquiryQuote> data = weUserInquiryQuote.getData();
		for (WeUserInquiryQuote wiq : data) {
			Staff staff = mongoTemplate.findById(wiq.getStaffId(), Staff.class);
			if (staff !=null) {
				Integer zan=staff.getZanCount();
				Integer count=staff.getEvaluateCount();
				if (zan !=null && count !=null && count !=0) {
					wiq.setLevel(zan/count);
				}
			}
		}
		return weUserInquiryQuote;
	}
	//保存询价
	@Override
	public ReturnStatus saveEnquiry(String userId,Double longitude, Double latitude,String logo0,String logo1,String logo2, String type, String desc) {
		WeUserInquiry exitInquiry = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), WeUserInquiry.class);
		if (exitInquiry !=null) {
			return new ReturnStatus(false);
		}
		String time = new SimpleDateFormat(" HH:mm").format(new Date());
		List<String> imgs = new ArrayList<String>();
		GpsPoint gpsPoint = new GpsPoint();
		gpsPoint.setLongitude(longitude);
		gpsPoint.setLatitude(latitude);
		WeUserInquiry weUserInquiry=new WeUserInquiry();
		weUserInquiry.setNewId();
		weUserInquiry.setNewCreate();
		weUserInquiry.setUserId(userId);
		weUserInquiry.setType(type);
		weUserInquiry.setDesc(desc);
		weUserInquiry.setCreateTimeFormat(time);
		weUserInquiry.setGpsPoint(gpsPoint);
		if (logo0 !=null && !logo0.equals("")) {
			imgs.add(logo0);
		}
		if (logo1 !=null && !logo1.equals("")) {
			imgs.add(logo1);
		}
		if (logo2 !=null && !logo2.equals("")) {
			imgs.add(logo2);
		}
		weUserInquiry.setImages(imgs);
		mongoTemplate.insert(weUserInquiry);
		return new ReturnStatus(true);
	}
	
	//删除询价
	@Override
	public ReturnStatus deleteEnquiry(String userId) {
		WeUserInquiry weUserInquiry = mongoTemplate.findAndRemove(Query.query(Criteria.where("userId").is(userId)), WeUserInquiry.class);
		mongoTemplate.findAndRemove(Query.query(Criteria.where("inquiryId").is(weUserInquiry.get_id())), WeUserInquiryQuote.class);
		return new ReturnStatus(true);
	}
	
	//获取询价详细信息
	@Override
	public WeUserInquiryQuote getQuoteById(String quoteId) {
		return mongoTemplate.findById(quoteId, WeUserInquiryQuote.class);
	}
	//获取类型
	@Override
	public Code getCodeById(String codeId) {
		return  mongoTemplate.findById(codeId, Code.class);
	}
	
	//询价预约保存
	@Override
	public ReturnStatus appointSave(String userId, String organId,
			String staffId,String quoteId, String orderTime, double price, int timeNum, int day, String replyId) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		User user=mongoTemplate.findById(userId, User.class);
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		WeUserInquiry weInquiry = mongoTemplate.findById(quoteId, WeUserInquiry.class);
		//WeUserInquiryQuote inquiryQuote = mongoTemplate.findById(replyId, WeUserInquiryQuote.class);
		//WeStaffCalendar calendar = mongoTemplate.findOne(Query.query(Criteria.where("staffId").is(staffId).and("organId").is(organId).and("day").is(day)), WeStaffCalendar.class);
		WeOrganOrder order = new WeOrganOrder();
		order.setNewId();
		order.setNewCreate();
		order.setUserId(userId);
		order.setOrganId(organId);
		order.setStaffId(staffId);
		order.setOrderTime(orderTime);
		order.setOrderPrice(price);
		order.setType(3);
		order.setOrderService(replyId);
		order.setState(1);
		order.setTitle(weInquiry.getType());
		if (staff !=null) {
			order.setStaffName(staff.getName());
		}
		if (organ !=null) {
			order.setOrganName(organ.getName());
		}
		ReturnStatus status=new ReturnStatus(true);
		status.setMessage(order.get_id());
		mongoTemplate.insert(order);
		/*if (calendar !=null) {
			switch (timeNum) {
			case 8:calendar.setTime8(false);break;
			case 9:calendar.setTime9(false);break;
			case 10:calendar.setTime10(false);break;
			case 11:calendar.setTime11(false);break;
			case 12:calendar.setTime12(false);break;
			case 13:calendar.setTime13(false);break;
			case 14:calendar.setTime14(false);break;
			case 15:calendar.setTime15(false);break;
			case 16:calendar.setTime16(false);break;
			case 17:calendar.setTime17(false);break;
			case 18:calendar.setTime18(false);break;
			case 19:calendar.setTime19(false);break;
			case 20:calendar.setTime20(false);break;
			case 21:calendar.setTime21(false);break;
			case 22:calendar.setTime22(false);break;
			case 23:calendar.setTime23(false);break;
			}
			mongoTemplate.save(calendar);
		}*/
		String time=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
		WeMessage wem = new WeMessage();
		wem.setNewId();
		wem.setNewCreate();
		wem.setFromType("user");
		wem.setFromId(userId);
		wem.setFromName(user.getNick());
		wem.setToType("staff");
		wem.setToId(staffId);
		wem.setToName(staff.getName());
		wem.setType("1");
		wem.setContent("客户 "+user.getNick()+" 已向您发出预约，请及时处理");
		wem.setCreateTimeFormat(time);
		mongoTemplate.save(wem);
		WeUserInquiry weUserInquiry = mongoTemplate.findAndRemove(Query.query(Criteria.where("userId").is(userId)), WeUserInquiry.class);
		mongoTemplate.findAndRemove(Query.query(Criteria.where("inquiryId").is(weUserInquiry.get_id()).and("_id").nin(replyId)), WeUserInquiryQuote.class);
		return status;
	}
	
	//获取技师信息
	@Override
	public Staff getStaffById(String staffId) {

		return mongoTemplate.findById(staffId, Staff.class);
	}
	
	//获取用户
	@Override
	public User getUserById(String userId) {
		return mongoTemplate.findById(userId, User.class);
	}
	
	//获取会员卡列表
	@Override
	public FlipInfo<Userincard> getVIPCard(String userId, FlipInfo<Userincard> fpi) {
		FlipInfo<Userincard> userCards = mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId)), fpi, Userincard.class);
		List<Userincard> data = userCards.getData();
		if (data !=null) {
			for (Userincard usercard : data) {
				Organ organ = mongoTemplate.findById(usercard.getOrganId(), Organ.class);
				usercard.setOrganName(organ.getName());
				Usersort usersort = mongoTemplate.findById(usercard.getMembersort(), Usersort.class);
				if (usersort !=null) {
					usercard.setFlag1(usersort.getFlag1());
				}
			}
		}
		return userCards;
	}
	
	//获取会员卡
	@Override
	public Userincard getCard(String cardId) {
		Userincard usercard = mongoTemplate.findById(cardId, Userincard.class);
		Organ organ = mongoTemplate.findById(usercard.getOrganId(),Organ.class);
		if (organ !=null) {
			usercard.setOrganName(organ.getName());
		}
		return usercard;
	}
	
	//会员卡详情
	@Override
	public FlipInfo<Userincard> getcardInfo(String userId,String cardId,
			FlipPageInfo<Userincard> fpi) {
		FlipInfo<Userincard> userCard=mongoTemplate.findByPage(Query.query(Criteria.where("_id").is(cardId)), fpi, Userincard.class);
		if (userCard !=null && userCard.getData().size()>0) {
			Userincard card = userCard.getData().get(0);
			Organ organ = mongoTemplate.findById(card.getOrganId(), Organ.class);
			Usersort usersort = mongoTemplate.findById(card.getMembersort(), Usersort.class);
			if (usersort !=null) {
				card.setCardType(usersort.getName1());
				card.setFlag1(usersort.getFlag1());
			}
			card.setOrganName(organ.getName());
			String createTime=new SimpleDateFormat("yyyy/MM/dd").format(card.getCreateTime());
			if (card.getLaw_day() !=null) {
				String lawTime=new SimpleDateFormat("yyyy/MM/dd").format(card.getLaw_day());
				card.setLawDayFormat(lawTime);
			}
			card.setCreateTimeFormat(createTime);
		}
		return userCard;
	}
	
	//会员消费记录
	@Override
	public FlipInfo<Userpart> getCustomList(String userId, String cardId,String cardType,
			FlipPageInfo<Userpart> fpi) {
		FlipInfo<Userpart> userparts =new FlipInfo<Userpart>();
		if (cardType.equals("parent")) {//主卡消费
			userparts=mongoTemplate.findByPage(Query.query(Criteria.where("incardId").is(cardId).
					and("type").is(1).and("flag2").is(true)), fpi, Userpart.class);
		}else if (cardType.equals("children")) {//子卡消费
			userparts=mongoTemplate.findByPage(Query.query(Criteria.where("inincardId").is(cardId).and("type").is(11).and("flag2").is(true)), fpi, Userpart.class);
		}
		for (Userpart userpart : userparts.getData()) {
			Organ organ = mongoTemplate.findById(userpart.getOrganId(), Organ.class);
			if (organ !=null) {
				userpart.setOrganName(organ.getName());
				String time=new SimpleDateFormat("MM/dd").format(userpart.getCreateTime());
				userpart.setCreateTimeFormat(time);
				userpart.setOrganLogo(organ.getLogo());
			}
		}
		return userparts;
	}
	
	//会员充值记录
	@Override
	public FlipInfo<Userpart> getRechargeList(String userId,
			String cardId,String cardType, FlipPageInfo<Userpart> fpi) {
		FlipInfo<Userpart> userpart=new FlipInfo<Userpart>();
		if (cardType.equals("parent")) {//主卡充值
			userpart= mongoTemplate.findByPage(Query.query(Criteria.where("incardId").is(cardId).and("type").is(3)), fpi, Userpart.class);
		}else if (cardType.equals("child")) {//子卡充值
			userpart= mongoTemplate.findByPage(Query.query(Criteria.where("inincardId").is(cardId).and("type").is(3)), fpi, Userpart.class);
		}
		if (userpart !=null) {
			for (Userpart record : userpart.getData()) {
				record.setCreateTimeFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(record.getCreateTime()));
			}
		}
		return userpart;
	}
	
	//会员卡门店列表
	@Override
	public FlipInfo<Organ> getCardStoreList(String cardId,String card,double longitude, double latitude,
			FlipPageInfo<Organ> fpi) {
		String distance;
		String unit;
		FlipInfo<Organ> organs = new FlipInfo<Organ>();
		List<String> cardOrganIds = new ArrayList<String>();
		if (card !=null && card.equals("parent")) {//主卡
			Userincard usercard = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cardId)), Userincard.class);
			Organ organ = mongoTemplate.findById(usercard.getOrganId(), Organ.class);
			if(!organ.getParentId().equals("0")) {
				Organ organParent = mongoTemplate.findById(organ.getParentId(), Organ.class);
				if(organParent.getCardOrganIds().size() > 0) { //是通用的
					cardOrganIds.add(organParent.get_id());
					cardOrganIds.addAll(organParent.getCardOrganIds());
				} else {
					cardOrganIds.add(organ.get_id());
				}
			} else {
				  if(organ.getCardOrganIds().size() > 0) { //是通用的
					cardOrganIds.add(organ.get_id());
					cardOrganIds.addAll(organ.getCardOrganIds());
				  } else {
					cardOrganIds.add(organ.get_id());
				}
			}
			organs=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(cardOrganIds)), fpi, Organ.class);
		}
		/*else if (card !=null && card.equals("children")) {//子卡
			Userinincard usercard = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cardId)), Userinincard.class);
			organs=mongoTemplate.findByPage(Query.query(Criteria.where("_id").is(usercard.getOrganId())), fpi, Organ.class);
		}*/
		List<Organ> data = organs.getData();
		for (Organ organ : data) {
			Integer zanCount = organ.getZanCount();
			Integer evaluateCount = organ.getEvaluateCount();
			if (zanCount !=null && evaluateCount !=null && evaluateCount !=0) {
				int level=(int) Math.ceil(zanCount*1.0/evaluateCount);
				organ.setLevel(level);
			}
			double dis = GpsUtil.distance(latitude, longitude, organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
			if (dis<1) {
				distance=new DecimalFormat("###.000").format(dis);
				double parseDouble = Double.parseDouble(distance)*1000;
				organ.setDistance(parseDouble);
				unit ="m";
			}else {
				distance=new DecimalFormat("###.0").format(dis);
				double parseDouble = Double.parseDouble(distance);
				organ.setDistance(parseDouble);
				unit ="km";
			}
			organ.setUnit(unit);
		}
		return organs;
	}
	
	//会员卡子卡列表
	@Override
	public FlipInfo<Userinincard> getVIPInCard(String userId,String cardId,
			FlipInfo<Userinincard> fpi) {
		FlipInfo<Userinincard> userInCards = mongoTemplate.findByPage(Query.query(Criteria.where("incardId").is(cardId).and("userId").is(userId)), fpi, Userinincard.class);
		for (Userinincard userincard : userInCards.getData()) {
			Organ organ = mongoTemplate.findById(userincard.getOrganId(), Organ.class);
			if (organ !=null) {
				userincard.setOrganName(organ.getName());
			}
			Usersort usersort = mongoTemplate.findById(userincard.getMembersort(), Usersort.class);
			if (usersort !=null) {
				//mongoTemplate.findById(usersort.get, )
				userincard.setFlag1(usersort.getFlag1());
				userincard.setUsersortName(usersort.getName1());
			}
		}
		return userInCards;
	}
	
	//获取会员卡主卡
	@Override
	public Userincard getInCard(String incardId) {
		Userincard userincard = mongoTemplate.findById(incardId, Userincard.class);
		Organ organ = mongoTemplate.findById(userincard.getOrganId(), Organ.class);
		if (organ !=null) {
			userincard.setOrganName(organ.getName());
		}
		return userincard;
	}
	
	//获取会员卡子卡
	@Override
	public Userinincard getIninCard(String incardId) {
		Userinincard userincard = mongoTemplate.findById(incardId, Userinincard.class);
		Organ organ = mongoTemplate.findById(userincard.getOrganId(), Organ.class);
		if (organ !=null) {
			userincard.setOrganName(organ.getName());
		}
		return userincard;
	}
	
	//子卡详情记录
	@Override
	public FlipInfo<Userinincard> getIncardInfo(String incardId) {
		FlipInfo<Userinincard> userinCards=new FlipInfo<Userinincard>();
		userinCards=mongoTemplate.findByPage(Query.query(Criteria.where("_id").is(incardId)), userinCards, Userinincard.class);
		if (userinCards !=null && userinCards.getData().size()>0) {
			Userinincard card = userinCards.getData().get(0);
			Organ organ = mongoTemplate.findById(card.getOrganId(), Organ.class);
			Usersort usersort = mongoTemplate.findById(card.getMembersort(), Usersort.class);
			if (usersort !=null) {
				card.setCardType(usersort.getName1());
				card.setFlag1(usersort.getFlag1());
			}
			card.setOrganName(organ.getName());
			String createTime=new SimpleDateFormat("yyyy/MM/dd").format(card.getCreateTime());
			if (card.getLaw_day() !=null) {
				String lawTime=new SimpleDateFormat("yyyy/MM/dd").format(card.getLaw_day());
				card.setLawDayFormat(lawTime);
			}
			card.setCreateTimeFormat(createTime);
		}
		return userinCards;
	}
	
	//获取技师日程状态
	@Override
	public ReturnStatus getStaffCalendar(String staffId) {
		String time=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		String[] split = time.split("/");
		int day=Integer.parseInt(split[0])*10000+Integer.parseInt(split[1])*100+Integer.parseInt(split[2]);
		List<WeStaffCalendar> list = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("day").gte(day)), WeStaffCalendar.class);
		if (list.size()>0) {
			return new ReturnStatus(true);
		}
		return new ReturnStatus(false);
	}
	
	//我的询价
	@Override
	public WeUserInquiry getMyInquiry(String quoteId) {
		WeUserInquiry inquiry = mongoTemplate.findById(quoteId, WeUserInquiry.class);
		return inquiry;
	}
	
	//获取询价
	@Override
	public WeUserInquiry getInquiry(String userId) {
		long days=0;
		WeUserInquiry weUserInquiry = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), WeUserInquiry.class);
		if (weUserInquiry !=null) {
			try{
				Date d1 = new Date();
				Date d2 = weUserInquiry.getCreateTime();
				long diff = d1.getTime() - d2.getTime();
				days = diff / (1000 * 60 * 60 * 24);
			}catch (Exception e){
			}
			if (days>3) {//询价大于三天，清除
				mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(weUserInquiry.get_id())), WeUserInquiry.class);
				mongoTemplate.findAndRemove(Query.query(Criteria.where("inquiryId").is(weUserInquiry.get_id())), WeUserInquiryQuote.class);
				return null;
			}
		}
		return weUserInquiry;
	}
	@Override
	public Usersort getUsersort(String usersortId) {
		Usersort usersort=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(usersortId)), Usersort.class);
		return usersort;
	}
	
	@Override
	public Userinincard findInInCard(String id_2,String userId) {
		return mongoTemplate.findOne(Query.query(Criteria.where("incardId").is(id_2).and("userId").is(userId)), Userinincard.class);
	}
}
