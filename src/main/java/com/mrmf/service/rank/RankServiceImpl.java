package com.mrmf.service.rank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.mrmf.entity.Account;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBDistrict;
import com.mrmf.entity.WeBRegion;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WeStaffIncome;
import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.bean.StaffRank;
import com.mrmf.entity.wxpay.WxTemplate;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Arith;
import com.mrmf.service.common.Configure;
import com.mrmf.service.redis.RedisService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
@Service("rankService")
public class RankServiceImpl implements RankService{
	@Autowired
	EMongoTemplate mongoTemplate;
	@Autowired
	private AccountService accountService;
	@Autowired
	private RedisService redisService;
	@Override
	public FlipInfo<WeUserPayOrder> queryUserRank(FlipInfo<WeUserPayOrder> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WeUserPayOrder.class);
			for(WeUserPayOrder order:fpi.getData()){
			Organ organ=null;
			User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getUserId())), User.class);
			if(!StringUtils.isEmpty(order.getOrganId())){
				 organ= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getOrganId())), Organ.class);
			}
			if(user!=null){
			if(!StringUtils.isEmpty(user.getName())){
				order.setUserName(user.getName());
			}else{
				order.setUserName(user.getNick());
			}
			order.setPhone(user.getPhone());
			}
			if(organ!=null)
			order.setOrganName(organ.getName());
		}
		return fpi;
	}
	@Override
	public ReturnStatus sendMessageToUser(String orderId,String activity,String prize) {
		WeUserPayOrder order= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeUserPayOrder.class);
		User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getUserId())), User.class);
		Account account = accountService.getAccountByEntityID(user.get_id(), "user");
		WxTemplate tempToUser = redisService.getWxTemplate("恭喜你中奖啦！世上美女千千万，轻奢的路上有你相伴！", activity,
				prize,null, null, null, "奖品已经包装完毕，请耐心等待！");
		try {
			redisService.send_template_message(account.getAccountName(), "user", Configure.PRIZES_INFO, tempToUser);
		} catch (Exception e) {
			System.out.println("消息通知失败！" + e.getMessage());
		}
		WeMessage messasge = new WeMessage();
		messasge.setIdIfNew();
		messasge.setFromType("rxm");
		messasge.setFromId("0");
		messasge.setFromName("任性猫平台");
		messasge.setToType("user");
		messasge.setToId(user.get_id());
		messasge.setToName(user.getNick());
		messasge.setType("1");
		messasge.setContent("任性猫用户消费排行奖励：任性猫举行"+activity+"活动，奖励了您"+prize+",奖品已经包装完毕，请耐心等待！");
		messasge.setCreateTimeIfNew();
		messasge.setReadFalg(false);
		messasge.setCreateTimeFormat(DateUtil.getDateStr(new Date(), DateUtil.YMDHM_PATTERN));
		mongoTemplate.save(messasge);
		return new ReturnStatus(true);
	}
	@Override
	public FlipInfo<StaffRank> queryStaffRank(FlipInfo<StaffRank> fpi,Date start,Date end,String staffids,boolean flag) {
		//Aggregation.project("staffId"),
		Criteria criteria = new Criteria();
		List<Criteria> criterias = new ArrayList<>();
		if(flag)
			criterias.add(Criteria.where("staffId").in(Arrays.asList(staffids.split(","))));
		if (start != null) {
			criterias.add(Criteria.where("createTime").gte(start));
		}
		if (end != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(end, 1)));
		}
		criterias.add(Criteria.where("state").in(3,10));
		
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		
		int skip=(fpi.getPage()-1)*fpi.getSize();
		int limit=fpi.getSize();
		
		
		
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),Aggregation.group("staffId").sum("orderPrice").as("totalPrice"),Aggregation.sort(Direction.DESC, "totalPrice"),Aggregation.limit(limit));
		AggregationResults<StaffRank> rs=mongoTemplate.aggregate(aggregation, WeOrganOrder.class, StaffRank.class);
		List<StaffRank> srList=rs.getMappedResults();
		List<StaffRank> moder = new ArrayList<StaffRank>();
		for(int i=0;i<srList.size();i++){
			if(!StringUtils.isEmpty(srList.get(i).get_id())){
				Staff staff=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(srList.get(i).get_id())), Staff.class);
				if(staff!=null){
					srList.get(i).setStaffName(staff.getName());
					Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staff.getOrganId())), Organ.class);
					if(organ!=null){
						srList.get(i).setOrganName(organ.getName());
					}else{
						List<String> organIds=staff.getWeOrganIds();
						if(organIds!=null&&organIds.size()>0){
						List<Organ>	organs = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
						String organNames="";
							for(Organ o:organs){
								organNames=organNames+o.getName()+",";
							}
							if(!StringUtils.isEmpty(organNames)){
								organNames=organNames.substring(0,organNames.length()-1);
								srList.get(i).setOrganName(organNames);
							}
						}
						
						
					}
				}
				
				moder.add(srList.get(i));
				
			}
		}
		fpi.setData(moder);
		return fpi;
	}
	@Override
	public ReturnStatus rechargeToStaff(String staffId, double price) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)), Staff.class);
		if(staff!=null){
			staff.setTotalIncome(Arith.add(staff.getTotalIncome(), price));
			WeStaffIncome wsc= new WeStaffIncome();
			wsc.setIdIfNew();
			if(!StringUtils.isEmpty(staff.getOrganId())){
			wsc.setOrganId(staff.getOrganId());
			}else{
				List<String> organs=staff.getWeOrganIds();
				if(organs!=null&&organs.size()>0){
					//Organ organ= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organs.get(0))), Organ.class);
					wsc.setOrganId(organs.get(0));
				}
				
			}
			wsc.setStaffId(staffId);
			wsc.setOrderId("0");
			wsc.setAmount(price);
			wsc.setCreateTimeIfNew();
			wsc.setTitle("任性猫平台赠送奖励");
			WeMessage message = new WeMessage();
			message.setIdIfNew();
			message.setFromType("rxm");
			message.setFromId("0");
			message.setFromName("任性猫平台");
			message.setToType("staff");
			message.setToId(staff.get_id());
			message.setToName(staff.getName());
			message.setType("1");
			message.setContent("任性猫平台技师排行奖励了您"+price+"元，已经存入您的钱包请查收");
			message.setCreateTimeIfNew();
			message.setReadFalg(false);
			message.setCreateTimeFormat(DateUtil.getDateStr(new Date(), DateUtil.YMDHM_PATTERN));
			mongoTemplate.save(message);
			mongoTemplate.save(wsc);
			mongoTemplate.save(staff);
			Account account = accountService.getAccountByEntityID(staff.get_id(), "staff");
			WxTemplate tempToUser = redisService.getWxTemplateTwo("恭喜你中奖啦！世上美女千千万，轻奢的路上有你相伴！", price+"元",
					DateUtil.getDateStr(new Date(), DateUtil.YMDHMS_PATTERN),"任性猫钱包", DateUtil.getDateStr(new Date(), DateUtil.YMDHMS_PATTERN), null, "奖金已经存入您的钱包，请注意查收！");
			try {
				redisService.send_template_message(account.getAccountName(), "staff", Configure.STAFF_PRIZES_INFO, tempToUser);
			} catch (Exception e) {
				System.out.println("消息通知失败！" + e.getMessage());
			}
		}
		return new ReturnStatus(true);
	}
	@Override
	public List<User> queryUser(String condition) {
		Criteria criteria=new Criteria();
		criteria.orOperator(Criteria.where("name").regex(condition),Criteria.where("nick").regex(condition),Criteria.where("phone").regex(condition));
		List<User> users = mongoTemplate.find(Query.query(criteria), User.class);
		return users;
	}


	@Override
	public ReturnStatus rechargeToUser(String rorderId, double price) {
		WeUserPayOrder order= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(rorderId)), WeUserPayOrder.class);
		User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getUserId())), User.class);
		WeUserWalletHis wuwh=new WeUserWalletHis();
		wuwh.setIdIfNew();
		wuwh.setUserId(user.get_id());
		wuwh.setAmount(price);
		wuwh.setDesc("平台奖励");
		wuwh.setCreateTimeIfNew();
		mongoTemplate.save(wuwh);
		user.setWalletAmount(Arith.add(user.getWalletAmount(),price));
		mongoTemplate.save(user);
		WeMessage message = new WeMessage();
		message.setIdIfNew();
		message.setFromType("rxm");
		message.setFromId("0");
		message.setFromName("任性猫平台");
		message.setToType("user");
		message.setToId(user.get_id());
		message.setToName(user.getNick());
		message.setType("1");
		message.setContent("任性猫平台用户消费排行奖励了您"+price+"元，已经存入您的钱包请查收");
		message.setCreateTimeIfNew();
		message.setReadFalg(false);
		message.setCreateTimeFormat(DateUtil.getDateStr(new Date(), DateUtil.YMDHM_PATTERN));
		mongoTemplate.save(message);
		return new ReturnStatus(true);
	}
	@Override
	public List<Staff> queryStaff(String condition,String organIds,boolean organFlag) {
		Criteria criteria=new Criteria();
		if(!StringUtils.isEmpty(condition))
		criteria.orOperator(Criteria.where("name").regex(condition),Criteria.where("nick").regex(condition),Criteria.where("phone").regex(condition));
		Criteria c= new Criteria();
		if(organFlag){
			c.orOperator(Criteria.where("organId").in(Arrays.asList(organIds.split(","))),Criteria.where("weOrganIds").all(Arrays.asList(organIds.split(","))));
		}
		criteria.andOperator(c);
		List<Staff> staffs = mongoTemplate.find(Query.query(criteria), Staff.class);
		return staffs;
	}
	@Override
	public List<WeBCity> cityList() {
		List<WeBCity> cityList = mongoTemplate.find(null, WeBCity.class);
		return cityList;
	}
	@Override
	public List<Organ> queryOrgan(String city, String distirct, String region) {
		List<Criteria> criterias =new  ArrayList<Criteria>();
		if(!StringUtils.isEmpty(city)){
			WeBCity c= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(city)), WeBCity.class);
			criterias.add(Criteria.where("city").is(c.getName()));
		}
		if(!StringUtils.isEmpty(distirct)){
			WeBDistrict d = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(distirct)), WeBDistrict.class);
			criterias.add(Criteria.where("district").is(d.getName()));
		}
		if(!StringUtils.isEmpty(region)){
			WeBRegion r = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(region)), WeBRegion.class);
			criterias.add(Criteria.where("region").is(r.getName()));
		}
		Criteria criteria =new Criteria();
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		List<Organ> organs = mongoTemplate.find(Query.query(criteria), Organ.class);
		return organs;
	}
	@Override
	public List<StaffRank> queryStaffRankList(Date start,Date end,String staffids,boolean flag,int limit) {
		//Aggregation.project("staffId"),
		Criteria criteria = new Criteria();
		List<Criteria> criterias = new ArrayList<>();
		if(flag){
			criterias.add(Criteria.where("staffId").in(Arrays.asList(staffids.split(","))));
		}
		else{
			criterias.add(Criteria.where("staffId").ne(null));
		}
		if (start != null) {
			criterias.add(Criteria.where("createTime").gte(start));
		}
		if (end != null) {
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(end, 1)));
		}
		
		criterias.add(Criteria.where("state").in(3,10));
		
		criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),Aggregation.group("staffId").sum("orderPrice").as("totalPrice"),Aggregation.sort(Direction.DESC, "totalPrice"),Aggregation.limit(limit));
		AggregationResults<StaffRank> rs=mongoTemplate.aggregate(aggregation, WeOrganOrder.class, StaffRank.class);
		List<StaffRank> srList=rs.getMappedResults();
		List<StaffRank> moder = new ArrayList<StaffRank>();
		for(int i=0;i<srList.size();i++){
			if(!StringUtils.isEmpty(srList.get(i).get_id())){
				Staff staff=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(srList.get(i).get_id())), Staff.class);
				if(staff!=null){
					srList.get(i).setStaffName(staff.getName());
					Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staff.getOrganId())), Organ.class);
					if(organ!=null){
						srList.get(i).setOrganName(organ.getName());
					}else{
						List<String> organIds=staff.getWeOrganIds();
						if(organIds!=null&&organIds.size()>0){
						List<Organ>	organs = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
						String organNames="";
							for(Organ o:organs){
								organNames=organNames+o.getName()+",";
							}
							if(!StringUtils.isEmpty(organNames)){
								organNames=organNames.substring(0,organNames.length()-1);
								srList.get(i).setOrganName(organNames);
							}
						}else{
							srList.get(i).setOrganName("任性猫平台");
						}
					}
					//查询当前技师有多少订单超过了30元
				}
				
				moder.add(srList.get(i));
				
			}
		}
		getDetail(moder);
		return moder;
	}
	public List<StaffRank> getDetail(List<StaffRank> srs){
		List<String> staffIds = new ArrayList<String>();
		for(StaffRank sr:srs){
			if(!staffIds.contains(sr.get_id())){
				staffIds.add(sr.get_id());
			}
		}
		if(staffIds.size()>0){
			Criteria criteria = new Criteria();
			Map<String, StaffRank> staffMap = new HashMap<String, StaffRank>();
			criteria.andOperator(Criteria.where("staffId").in(staffIds),Criteria.where("state").in(3,10),Criteria.where("orderPrice").gt(30));
			Aggregation aggregation =Aggregation.newAggregation(Aggregation.match(criteria),Aggregation.group("staffId").count().as("orderNum"));
			AggregationResults<StaffRank> rs=mongoTemplate.aggregate(aggregation, WeOrganOrder.class, StaffRank.class);
			List<StaffRank> srList=rs.getMappedResults();
			for(StaffRank sr:srList){
				staffMap.put(sr.get_id(), sr);
			}
			for(StaffRank sr:srs){
				StaffRank mapsr = staffMap.get(sr.get_id());
				if(mapsr!=null){
					sr.setOrderNum(mapsr.getOrderNum());
				}
			}
		}
		return srs;
	}
public List<WeUserPayOrder> exportUserRank(String condition, Date startTime, Date endTime) {
			List<String> userIds=new ArrayList<String>();
			List<WeUserPayOrder> payOrder=new ArrayList<WeUserPayOrder>();
			if(!StringUtils.isEmpty(condition)){
				Criteria criteria1=new Criteria();
				criteria1.orOperator(Criteria.where("nick").regex(condition),Criteria.where("name").regex(condition),Criteria.where("phone").regex(condition));
				List<User> userList = mongoTemplate.find(Query.query(criteria1), User.class);
				if(userList.size()>0&&userList!=null){
					for(User u:userList){
						if(!userIds.contains(userList)){
							userIds.add(u.get_id());
						}
					}
				}
			
			if(userIds.size()>0&&userIds!=null){
				for(String s:userIds){
					
					Criteria criteria=new Criteria();
					criteria.andOperator(Criteria.where("userId").regex(s));
					payOrder = mongoTemplate.find(Query.query(criteria), WeUserPayOrder.class);
				}
			}
			for(WeUserPayOrder order:payOrder){
				Organ organ=null;
				User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getUserId())), User.class);
				if(!StringUtils.isEmpty(order.getOrganId())){
					organ= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getOrganId())), Organ.class);
				}
				if(user!=null){
					if(!StringUtils.isEmpty(user.getName())){
						order.setUserName(user.getName());
					}else{
						order.setUserName(user.getNick());
					}
					order.setPhone(user.getPhone());
				}
				if(organ!=null)
					order.setOrganName(organ.getName());
			}
			}else{
				List<Criteria> criterias = new ArrayList<Criteria>();
				Criteria criteria = new Criteria();
				if(startTime!=null){

					criterias.add(Criteria.where("createTime").gte(startTime));
				}
				if(endTime!=null){
					criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
				}

				if(criterias.size()>0)
				{
					criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
				}
				payOrder= mongoTemplate.find(Query.query(criteria), WeUserPayOrder.class);
				if(payOrder.size()>0&&payOrder!=null){
					for(WeUserPayOrder order:payOrder){
						Organ organ=null;
						User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getUserId())), User.class);
						if(!StringUtils.isEmpty(order.getOrganId())){
							organ= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getOrganId())), Organ.class);
						}
						if(user!=null){
							if(!StringUtils.isEmpty(user.getName())){
								order.setUserName(user.getName());
							}else{
								order.setUserName(user.getNick());
							}
							order.setPhone(user.getPhone());
						}
						if(organ!=null)
							order.setOrganName(organ.getName());
					}
					
				}
				
			}
		return payOrder;
	}




}
