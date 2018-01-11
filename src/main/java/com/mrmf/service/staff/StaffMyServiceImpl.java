package com.mrmf.service.staff;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.mrmf.entity.*;
import com.mrmf.entity.coupon.MyCoupon;
import com.mrmf.entity.organPisition.OrganPositionDetails;
import com.mrmf.entity.organPisition.PositionOrder;
import com.osg.framework.BaseException;
import com.osg.framework.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.derby.tools.sysinfo;
import org.apache.derby.vti.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrmf.entity.bean.WeStaffCalendarTemplate;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.module.wx.utils.WeixinRedPacket;
import com.mrmf.service.common.Arith;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.GpsUtil;

@Service("staffMyService")
public class StaffMyServiceImpl implements StaffMyService{
	@Autowired
	private EMongoTemplate mongoTemplate;


	@Override
	public  Organ findOneOrgan(String organId){
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)),Organ.class);
	}

	@Override
	public FlipInfo<WeOrganComment> getCommentList(String staffId, String page,String size) {
		staffId = "7840835260965106637";
		page="1";
		size="10";
		FlipInfo<WeOrganComment> fpi = new FlipInfo<WeOrganComment>(Integer.parseInt(page),Integer.parseInt(size));
		mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)), fpi, WeOrganComment.class);
		for(WeOrganComment comment : fpi.getData()){
			User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(comment.getUserId())), User.class);
			if(user!=null){
				comment.setUserName(user.getName());
				comment.setUserImg(user.getAvatar());
			}
			comment.setCommentTime(comment.getCreateTime().toString().substring(0, 19));
		}
		return fpi;
	}
	@Override
	public Staff getStaff(String staffId) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffId)), Staff.class);
		return staff;
	}
	@Override
	public FlipInfo<WeMessage> getMessageList(String staffId, String type,
											  String page, String size) {
		staffId = "7840835260965106637";
		page="1";
		size="10";
		FlipInfo<WeMessage> fpi = new FlipInfo<WeMessage>(Integer.parseInt(page),Integer.parseInt(size));
		if("1".equals(type)){
			mongoTemplate.findByPage(Query.query(Criteria.where("toId").is("staff").and("type").is("1")), fpi, WeMessage.class);
		}else if("2".equals(type)){
			mongoTemplate.findByPage(Query.query(Criteria.where("type").is("2")), fpi, WeMessage.class);
		}
		for(WeMessage message : fpi.getData()){
			message.setCreateTimeFormat(message.getCreateTime().toString().substring(0, 16));
		}
		return fpi;
	}
	@Override
	public void conformOrder(String orderId) {
		orderId="5502204266264412359";
//		WeOrganOrder order = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeOrganOrder.class);
//		order.setState(2);
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(orderId)), Update.update("state", 2), WeOrganOrder.class);
	}
	@Override
	public void refuseOrder(String orderId,String refusecomment) {
		orderId="5502204266264412359";
		mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(orderId)), Update.update("state", -1).update("rejectReason", refusecomment), WeOrganOrder.class);
	}
	@Override
	public Map getCustomerList(String staffId, String type,String page, String size) {
		Map map = new HashMap();
		FlipInfo<StaffCustomer> fpi = new FlipInfo<StaffCustomer>(Integer.parseInt(page),Integer.parseInt(size));
		List<StaffCustomer> list = new ArrayList<StaffCustomer>();
		if("1".equals(type)){//预约客户
			Query query = Query.query(Criteria.where("orderHisIds").regex(staffId)).with(new Sort(new Sort.Order(Sort.Direction.ASC,"_id")));
			List<User> result = mongoTemplate.find(query, User.class);
			int total = result.size();
			int start = (Integer.parseInt(page)-1)*Integer.parseInt(size);
			int end = (Integer.parseInt(page)*Integer.parseInt(size))-1;
			for (;start <= end && start < total;start++) {
				User user = result.get(start);
				StaffCustomer customer = new StaffCustomer();
				customer.setName(user.getName());
				customer.setPicture(user.getAvatar());
				customer.setPhone(user.getPhone());
				List<WeOrganOrder> orderlist = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
				WeOrganOrder order = new WeOrganOrder();
				if(orderlist.size() == 1){
					customer.setIsFirst("1");
					order = orderlist.get(0);
				}else{
					order = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
				}
				customer.setOrdertime(order.getCreateTime().toString().substring(0, 16));
				list.add(customer);
			}
			fpi.setData(list);
			map.put("fpi", fpi);
			map.put("total", total);
		}else if("2".equals(type)){//会员客户

		}else if("3".equals(type)){//关注客户
			Query query = Query.query(Criteria.where("orderHisIds").regex(staffId).and("favorStaffIds").regex(staffId)).with(new Sort(new Sort.Order(Sort.Direction.ASC,"_id")));
			List<User> result = mongoTemplate.find(query, User.class);
			int total = result.size();
			int start = (Integer.parseInt(page)-1)*Integer.parseInt(size);
			int end = (Integer.parseInt(page)*Integer.parseInt(size))-1;
			for (;start <= end && start < total;start++) {
				User user = result.get(start);
				StaffCustomer customer = new StaffCustomer();
				customer.setName(user.getName());
				customer.setPicture(user.getAvatar());
				customer.setPhone(user.getPhone());
				List<WeOrganOrder> orderlist = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
				WeOrganOrder order = new WeOrganOrder();
				if(orderlist.size() == 1){
					customer.setIsFirst("1");
					order = orderlist.get(0);
				}else{
					order = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
				}
				customer.setOrdertime(order.getCreateTime().toString().substring(0, 16));
				list.add(customer);
			}
			fpi.setData(list);
			map.put("fpi", fpi);
			map.put("total", total);
		}
		return map;
	}
	@Override
	public Map getCustomerData(String staffId) {
		Map map = new HashMap();
		List<StaffCustomer> staffUserlist = new ArrayList<StaffCustomer>();
		int start = 0;
		int end = 19;
		Query query = Query.query(Criteria.where("orderHisIds").regex(staffId)).with(new Sort(new Sort.Order(Sort.Direction.ASC,"_id")));
		List<User> userList = mongoTemplate.find(query, User.class);
		int ordertotal = userList.size();
		for (;start <= end && start < ordertotal;start++) {
			User user = userList.get(start);
			StaffCustomer customer = new StaffCustomer();
			customer.setName(user.getName());
			customer.setPicture(user.getAvatar());
			customer.setPhone(user.getPhone());
			List<WeOrganOrder> orderlist = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
			WeOrganOrder order = new WeOrganOrder();
			if(orderlist.size() == 1){
				customer.setIsFirst("1");
				order = orderlist.get(0);
			}else{
				order = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
			}
			customer.setOrdertime(order.getCreateTime().toString().substring(0, 16));
			staffUserlist.add(customer);
		}

		query = Query.query(Criteria.where("orderHisIds").regex(staffId).and("favorStaffIds").regex(staffId)).with(new Sort(new Sort.Order(Sort.Direction.ASC,"_id")));
		start = 0;
		end = 19;
		List<StaffCustomer> staffFocuslist = new ArrayList<StaffCustomer>();
		List<User> focusList = mongoTemplate.find(query, User.class);
		int focustotal = focusList.size();
		query = Query.query(Criteria.where("orderHisIds").regex(staffId).and("favorStaffIds").regex(staffId)).with(new Sort(new Sort.Order(Sort.Direction.ASC,"_id")));
		List<User> result = mongoTemplate.find(query, User.class);
		for (;start <= end && start < focustotal;start++) {
			User user = result.get(start);
			StaffCustomer customer = new StaffCustomer();
			customer.setName(user.getName());
			customer.setPicture(user.getAvatar());
			customer.setPhone(user.getPhone());
			List<WeOrganOrder> orderlist = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
			WeOrganOrder order = new WeOrganOrder();
			if(orderlist.size() == 1){
				customer.setIsFirst("1");
				order = orderlist.get(0);
			}else{
				order = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(user.get_id())).with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"))), WeOrganOrder.class);
			}
			customer.setOrdertime(order.getCreateTime().toString().substring(0, 16));
			staffFocuslist.add(customer);
		}
		map.put("staffUserlist", staffUserlist);
		map.put("staffFocuslist", staffFocuslist);
		map.put("ordertotal", ordertotal);
		map.put("focustotal", focustotal);
		return map;
	}
	@Override
	public Map getCustomerDetail(String userId, String type, String page,
								 String size) {
		User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
		return null;
	}
	@Override
	public Map getMyEarn(String staffId, String page, String size) {
		Map map = new HashMap();
		staffId = "";
		WeStaffIncome income = new WeStaffIncome();
		page = "1";
		size="10";
		FlipInfo<WeStaffIncome> fpi = new FlipInfo<WeStaffIncome>(Integer.parseInt(page),Integer.parseInt(size));
		mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is("staffId")), fpi, WeStaffIncome.class);
		for(WeStaffIncome incomedata : fpi.getData()){
			Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(incomedata.getOrderId())), Organ.class);
			incomedata.setOrganName(organ.getName());
			incomedata.setDateTimeFormat(incomedata.getCreateTime().toString().substring(0, 16));
		}

		Long total = 0l;
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("staffId").is(staffId)),
				Aggregation.group("staffId").sum("amount").as("sumprice") );

		AggregationResults<WeStaffIncome> ar = mongoTemplate.aggregate(aggregation, "sumprice", WeStaffIncome.class);
		List<WeStaffIncome> list = ar.getMappedResults();
		if(list != null && list.size() > 0){
			//total = list.get(0);
		}
		map.put("fpi", fpi);
		map.put("sumPrice", 0.0d);
		return map;
	}
	//获取客户列表(关注客户)
	@Override
	public FlipInfo<User> getCustomers(String staffId, String type,FlipInfo<User> fpi) {
		FlipInfo<User> users = new FlipInfo<User>();
		if (type.equals("3")) {//关注客户
			users = mongoTemplate.findByPage(Query.query(Criteria.where("favorStaffIds").in(staffId)), fpi, User.class);
		}
		return users;
	}
	//获取客户列表(预约客户)
	@Override
	public FlipInfo<User> getCustomersAppoint(String staffId, String type,
											  FlipInfo<User> fpi) {
		List<String> userList=new ArrayList<String>();
		List<WeOrganOrder> orderList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("state").is(1)), WeOrganOrder.class);
		for (WeOrganOrder weOrganOrder : orderList) {
			if (!userList.contains(weOrganOrder.getUserId())) {
				userList.add(weOrganOrder.getUserId());
			}
		}
		FlipInfo<User> users = mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(userList)), fpi, User.class);
		List<User> data = users.getData();
		for (User user : data) {
			List<WeOrganOrder> appointList = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id()).and("state").is(1)), WeOrganOrder.class);
			List<Usercard> cardList = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id())), Usercard.class);
			if (cardList.size()>0) {
				user.setIsMember(true);
			}
			if (appointList.size()>0) {
				user.setOrderTime(appointList.get(0).getOrderTime());
			}
			int count=(int)mongoTemplate.count(Query.query(Criteria.where("userId").is(user.get_id())), WeOrganOrder.class);
			user.setOrderNum(count);
		}
		return users;
	}
	//客户统计
	@Override
	public Map<String, Integer> getCustomerCount(String staffId) {
		List<String> userList=new ArrayList<String>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(-1);list.add(0);
		Map<String, Integer> map=new HashMap<String, Integer>();
		List<WeOrganOrder> orderList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("state").nin(list)), WeOrganOrder.class);
		List<WeOrganOrder> appointList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("state").is(1)), WeOrganOrder.class);
		for (WeOrganOrder weOrganOrder : orderList) {
			if (!userList.contains(weOrganOrder.getUserId())) {
				userList.add(weOrganOrder.getUserId());
			}
		}
		int member = (int)mongoTemplate.count(Query.query(Criteria.where("userId").in(userList)), Usercard.class);
		userList.clear();
		for (WeOrganOrder weOrganOrder : appointList) {
			if (!userList.contains(weOrganOrder.getUserId())) {
				userList.add(weOrganOrder.getUserId());
			}
		}
		int appoint = userList.size();
		int follow = (int)mongoTemplate.count(Query.query(Criteria.where("favorStaffIds").in(staffId)), User.class);
		map.put("follow", follow);
		map.put("appoint", appoint);
		map.put("member", member);
		return map;
	}

	//会员客户信息
	@Override
	public User getMemberDetail(String userId) {

		return mongoTemplate.findById(userId.trim(), User.class);
	}
	//会员消费列表
	@Override
	public FlipInfo<WeOrganOrder> customList(String userId,
											 FlipPageInfo<WeOrganOrder> fip) {
		List<Object> list = new ArrayList<>();
		list.add(-1);
		list.add(0);
		list.add(1);
		list.add(2);
		FlipInfo<WeOrganOrder> orders=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").nin(list)), fip, WeOrganOrder.class);
		List<WeOrganOrder> data = orders.getData();
		for (WeOrganOrder weOrganOrder : data) {
			Organ organ = mongoTemplate.findById(weOrganOrder.getOrganId(), Organ.class);
			weOrganOrder.setOrganName(organ.getName());
			String date=weOrganOrder.getOrderTime().substring(5, 10);
			weOrganOrder.setOrderTime(date.replace("-", "/"));
		}
		return orders;
	}

	//我的订单
	@Override
	public FlipInfo<WeOrganOrder> getOrders(String staffId, String type, FlipPageInfo<WeOrganOrder> flp) {
		List<Object> list = new ArrayList<>();
		list.add(-1);
		list.add(0);
		FlipInfo<WeOrganOrder> orders=new FlipInfo<WeOrganOrder>();
		if (type.equals("1")) {//全部
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId).and("state").nin(list)), flp, WeOrganOrder.class);
		}else if (type.equals("2")) {//待确认
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId).and("state").is(1)), flp, WeOrganOrder.class);
		}else if (type.equals("3")) {//待支付
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId).and("state").is(2)), flp, WeOrganOrder.class);
		}else if (type.equals("4")) {//待评价
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId).and("state").is(3)), flp, WeOrganOrder.class);
		}
		List<WeOrganOrder> data = orders.getData();
		for (WeOrganOrder weOrganOrder : data) {
			Organ organ = mongoTemplate.findById(weOrganOrder.getOrganId(), Organ.class);
			weOrganOrder.setOrganAddress(organ.getAddress());
			weOrganOrder.setOrganLogo(organ.getLogo());
			weOrganOrder.setOrganName(organ.getName());
			if (weOrganOrder.getTitle() != null) {
				if (weOrganOrder.getTitle().indexOf("发") != -1) {
					weOrganOrder.setServerType("1");
				}else if (weOrganOrder.getTitle().indexOf("容") != -1) {
					weOrganOrder.setServerType("2");
				}else if (weOrganOrder.getTitle().indexOf("甲") !=-1) {
					weOrganOrder.setServerType("3");
				}else if (weOrganOrder.getTitle().indexOf("养") != -1) {
					weOrganOrder.setServerType("4");
				}else {
					weOrganOrder.setServerType("1");
				}
			}
		}
		return orders;
	}
	//订单详情
	@Override
	public WeOrganOrder getOrderDetail(String orderId) {
		WeOrganOrder order = mongoTemplate.findById(orderId, WeOrganOrder.class);
		User user = mongoTemplate.findById(order.getUserId(), User.class);
		Organ organ = mongoTemplate.findById(order.getOrganId(), Organ.class);
		List<Usercard> list = mongoTemplate.find(Query.query(Criteria.where("userId").is(order.getUserId())), Usercard.class);
		order.setUserImg(user.getAvatar());
		order.setUserNick(user.getNick());
		order.setUserName(user.getName());
		order.setUserPhone(user.getPhone());
		order.setOrganName(organ.getName());
		order.setOrganAddress(organ.getAddress());
		if (order.getState()==10) {
			WeOrganComment comment = mongoTemplate.findOne(Query.query(Criteria.where("orderId").is(order.get_id()).and("staffId").is(order.getStaffId())), WeOrganComment.class);
			order.setStarZan(comment.getStarZan());
			order.setContent(comment.getContent());
		}
		if (order.getTitle() != null) {
			if (order.getTitle().indexOf("发") != -1) {
				order.setServerType("1");
			}else if (order.getTitle().indexOf("容") != -1) {
				order.setServerType("2");
			}else if (order.getTitle().indexOf("甲") !=-1) {
				order.setServerType("3");
			}else if (order.getTitle().indexOf("养") != -1) {
				order.setServerType("4");
			}else {
				order.setServerType("1");
			}
		}
		if (list.size()>0) {
			order.setIsMember(true);
		}else {
			order.setIsMember(false);
		}
		return order;
	}

	//拒绝订单保存
	@Override
	public ReturnStatus refuseOrderSave(String orderId, String refuseComment) {
		ReturnStatus status = new ReturnStatus(true);
		WeOrganOrder order = mongoTemplate.findById(orderId, WeOrganOrder.class);
		if (order !=null) {
			if (order.getState()!=-1) {
				order.setState(-1);
				order.setRejectReason(refuseComment);

				mongoTemplate.save(order);
				Staff staff = mongoTemplate.findById(order.getStaffId(), Staff.class);
				User user = mongoTemplate.findById(order.getUserId(), User.class);
				String createTime=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
				WeMessage mes = new WeMessage();
				mes.setNewId();
				mes.setNewCreate();
				mes.setFromType("staff");
				mes.setFromId(order.getStaffId());
				mes.setFromName(staff.getName());
				mes.setToType("user");
				mes.setToId(order.getUserId());
				mes.setToName(user.getNick());
				mes.setType("1");
				mes.setContent("技师 "+staff.getName()+" 已拒绝您的预约");
				mes.setCreateTimeFormat(createTime);
				mongoTemplate.insert(mes);
				Organ organ = mongoTemplate.findById(order.getOrganId(), Organ.class);
				status.setMessage(organ.getName()+"("+staff.getName()+")");
				return status;
			}else {
				status.setSuccess(false);
				status.setMessage("您已经拒接过该订单");
				return status;
			}
		}else {
			status.setSuccess(false);
			status.setMessage("该订单不存在");
			return status;
		}
	}
	//我的评价
	@Override
	public FlipInfo<WeOrganComment> getMyComment(String staffId,
												 FlipPageInfo<WeOrganComment> flp) {
		FlipInfo<WeOrganComment> comment=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)), flp, WeOrganComment.class);
		List<WeOrganComment> data = comment.getData();
		for (WeOrganComment weOrganComment : data) {
			User user = mongoTemplate.findById(weOrganComment.getUserId(), User.class);
			weOrganComment.setUserName(user.getNick());
			weOrganComment.setUserImg(user.getAvatar());
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(weOrganComment.getCreateTime());
			weOrganComment.setCommentTime(time);
		}
		return comment;
	}

	//我的信息
	@Override
	public FlipInfo<WeMessage> myMessageList(String staffId, String type,
											 FlipPageInfo<WeMessage> flp) {
		FlipInfo<WeMessage> message=new FlipInfo<WeMessage>();
		if (type.equals("1")) {//个人消息
			message=mongoTemplate.findByPage(Query.query(Criteria.where("toId").is(staffId).and("type").is("1").and("toType").is("staff")), flp, WeMessage.class);
		}else if (type.equals("2")) {//系统消息
			message=mongoTemplate.findByPage(Query.query(Criteria.where("toId").is(staffId).and("type").is("2").and("toType").is("staff")), flp, WeMessage.class);
		}
		return message;
	}

	//收益列表
	@Override
	public FlipInfo<WeStaffIncome> getIncomeList(String staffId) {
		FlipInfo<WeStaffIncome> income=new FlipInfo<WeStaffIncome>();
		income.setSortField("createTime");
		income.setSortOrder("DESC");
		income=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)),income, WeStaffIncome.class);
		if(income.getData().size()>0) {
			for(WeStaffIncome wsfi:income.getData()){
				if(!StringUtils.isEmpty(wsfi.getOrganId())){
					Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(wsfi.getOrganId())), Organ.class);
					wsfi.setOrganName(organ.getName());
				} else if(!StringUtils.isEmpty(wsfi.getUserId())){
					User user = mongoTemplate.findById(wsfi.getUserId(), User.class);
					wsfi.setOrganName(user.getNick());
				} else if(!StringUtils.isEmpty(wsfi.getStaffId())){
					Staff staff = mongoTemplate.findById(wsfi.getStaffId(), Staff.class);
					wsfi.setOrganName(staff.getNick());
				}
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date=format.format(wsfi.getCreateTime());
				wsfi.setDateTimeFormat(date);
			}
		}
		return income;
	}

	@Override
	public FlipInfo<Userpart> getPercentageList(String staffId) {
		FlipInfo<Userpart> percentages=new FlipInfo<Userpart>();
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("staffId1").is(staffId),Criteria.where("staffId2").is(staffId),Criteria.where("staffId3").is(staffId));
		query.addCriteria(criteria);
		percentages=mongoTemplate.findByPage(query,percentages, Userpart.class);
		for (Userpart userpart : percentages.getData()) {
			if(userpart.getStaffId1().equals(staffId)) {
				userpart.setPercentage(Arith.add(userpart.getSomemoney1(), userpart.getPercentage()));
			}
			if(userpart.getStaffId2().equals(staffId)) {
				userpart.setPercentage(Arith.add(userpart.getSomemoney2(), userpart.getPercentage()));
			}
			if(userpart.getStaffId3().equals(staffId)) {
				userpart.setPercentage(Arith.add(userpart.getSomemoney2(), userpart.getPercentage()));
			}
			if(!StringUtils.isEmpty(userpart.getOrganId())){
				Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userpart.getOrganId())), Organ.class);
				userpart.setOrganName(organ.getName());
			}
			if(!StringUtils.isEmpty(userpart.getSmallsort())){
				Smallsort smallsort = mongoTemplate.findById(userpart.getSmallsort(), Smallsort.class);
				userpart.setSmallsortName(smallsort.getName());
			}
		}
		return percentages;
	}
	//确认订单
	@Override
	public ReturnStatus confirmOrder(String orderId) {
		ReturnStatus status = new ReturnStatus(true);
		WeOrganOrder order = mongoTemplate.findById(orderId, WeOrganOrder.class);
		if (order !=null) {
			if (order.getState()==1) {
				Staff staff = mongoTemplate.findById(order.getStaffId(), Staff.class);
				User user = mongoTemplate.findById(order.getUserId(), User.class);
				String time=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
				order.setState(2);
				WeMessage mes = new WeMessage();
				mes.setNewId();
				mes.setNewCreate();
				mes.setFromType("staff");
				mes.setFromId(order.getStaffId());
				mes.setFromName(staff.getName());
				mes.setToType("user");
				mes.setToId(order.getUserId());
				mes.setToName(user.getNick());
				mes.setType("1");
				mes.setContent("技师 "+staff.getName()+" 已接受您的预约，请及时付款");
				mes.setCreateTimeFormat(time);
				mongoTemplate.insert(mes);
				mongoTemplate.save(order);
				return status;
			}else {
				status.setSuccess(false);
				status.setMessage("订单已失效");
				return status;
			}
		}else {
			status.setSuccess(false);
			status.setMessage("该订单不存在");
			return status;
		}
	}

	//案例收藏
	@Override
	public FlipInfo<WeStaffCase> getCaseList(String userId,
											 FlipPageInfo<WeStaffCase> flp) {
		User user = mongoTemplate.findById(userId, User.class);
		List<String> list=new ArrayList<String>();
		if (user !=null ) {
			list=user.getFavorCaseIds();
		}
		FlipInfo<WeStaffCase> weCase=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(list)), flp, WeStaffCase.class);
		return weCase;
	}

	//技师收藏
	@Override
	public FlipInfo<Staff> getStaffList(String userId, FlipPageInfo<Staff> flp) {
		User user = mongoTemplate.findById(userId, User.class);
		List<String> list=new ArrayList<String>();
		if (user !=null) {
			list=user.getFavorStaffIds();
		}
		FlipInfo<Staff> staff=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(list)), flp, Staff.class);
		List<Staff> data = staff.getData();
		for (Staff staff2 : data) {
			Integer evaluateCount=staff2.getEvaluateCount();
			int aver=0;
			if(evaluateCount!=null&&evaluateCount!=0){
				int zanCount=staff2.getZanCount();
				aver=(int)Math.ceil((double)zanCount/evaluateCount);
				if(aver>5){
					aver=5;
				}
				staff2.setZanCount(aver);
			}else{
				aver=staff2.getZanCount();
				if(aver>5){
					aver=5;
					staff2.setZanCount(aver);
				}
			}
		}
		return staff;
	}

	//店铺收藏
	@Override
	public FlipInfo<Organ> getOrganList(String userId, FlipPageInfo<Organ> flp) {
		User user = mongoTemplate.findById(userId, User.class);
		List<String> list=new ArrayList<String>();
		if (user !=null ) {
			list=user.getFavorOrganIds();
		}
		FlipInfo<Organ> organ=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(list)), flp, Organ.class);
		List<Organ> data = organ.getData();
		for (Organ organ2 : data) {
			Integer zan=organ2.getZanCount(),count=organ2.getEvaluateCount();
			if (zan !=null && count !=null && count !=0) {
				organ2.setLevel(zan/count);
			}
		}
		return organ;
	}

	//店铺收藏
	@Override
	public FlipInfo<Organ> getOrganList(double longitude,double latitude,String userId, FlipPageInfo<Organ> flp) {
		User user = mongoTemplate.findById(userId, User.class);
		List<String> list=new ArrayList<String>();
		if (user !=null ) {
			list=user.getFavorOrganIds();
		}
		FlipInfo<Organ> organ=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(list)), flp, Organ.class);
		for (Organ organ2 : organ.getData()) {
			Integer evaluateCount=organ2.getEvaluateCount();
			int aver=0;
			if(evaluateCount!=null&&evaluateCount!=0){
				int zanCount=organ2.getZanCount();
				aver=(int)Math.ceil((double)zanCount/evaluateCount);
				if(aver>5){
					aver=5;
				}
				organ2.setZanCount(aver);
			}else{
				aver=organ2.getZanCount();
				if(aver>5){
					aver=5;
					organ2.setZanCount(aver);
				}
			}
			double dis=GpsUtil.distance(latitude, longitude,organ2.getGpsPoint().getLatitude() , organ2.getGpsPoint().getLongitude());
			if(dis<1){
				DecimalFormat df  = new DecimalFormat("###.000");
				organ2.setDistance(Double.parseDouble(df.format(dis))*1000);
				organ2.setUnit("m");
			}else{
				DecimalFormat df1  = new DecimalFormat("###.0");
				organ2.setDistance(Double.parseDouble(df1.format(dis)));
				organ2.setUnit("km");
			}
		}
		return organ;
	}

	//保存反馈意见
	@Override
	public ReturnStatus saveFeedBack(String staffId, String fbcontent,
									 String contact) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		WeUserFeedback feedback = new WeUserFeedback();
		feedback.setNewId();
		feedback.setNewCreate();
		feedback.setUserId(staffId);
		feedback.setDesc(fbcontent);
		feedback.setContact(contact);
		if (staff !=null) {
			feedback.setUserName(staff.getName());
		}
		feedback.setType("staff");
		mongoTemplate.insert(feedback);
		return new ReturnStatus(true);
	}

	//获取红包发送次数
	@Override
	public Map<String,Object> getRedCount(String staffId) {
		/*Calendar cal=Calendar.getInstance();
		int day=cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, day-3);
		Date date0=cal.getTime();//三天前的时间
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("state").is(1),Criteria.where("createTime").lt(date0));
		List<WeRed> weReds=mongoTemplate.find(Query.query(c), WeRed.class);
		for(WeRed weRed:weReds){
			weRed.setState(2);
			Staff staff = mongoTemplate.findById(weRed.getSenderId(), Staff.class);
			staff.setTotalIncome(staff.getTotalIncome()+weRed.getRestAmount());
			mongoTemplate.save(staff);
			mongoTemplate.save(weRed);
		}*/

		Map<String,Object> map=new HashMap<String,Object>();
		/*Date date=new Date();
		String time2=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		try {
			date=new SimpleDateFormat("yyyy-MM-dd").parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		double totalIncome = staff.getTotalIncome();
		//int count = (int)mongoTemplate.count(Query.query(Criteria.where("senderId").is(staffId).and("type").is(2).and("createTime").gte(date)), WeRed.class);
		map.put("totalIncome", totalIncome);
		//map.put("count", count);
		return map;
	}

	//保存红包
	@Override
	public ReturnStatus saveRedPacket(String staffId, String scope,
									  String count, String money, String desc, HttpServletRequest request) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		ReturnStatus status = new ReturnStatus(true);
		WeRed weRed = new WeRed();
		weRed.setNewId();
		weRed.setNewCreate();
		weRed.setType(2);
		weRed.setSenderId(staffId);
		weRed.setScope(Integer.parseInt(scope));
		weRed.setGpsPoint(staff.getGpsPoint());
		weRed.setRange(10);
		weRed.setCount(Integer.parseInt(count));
		weRed.setRestCount(Integer.parseInt(count));
		weRed.setAmount(Double.parseDouble(money));
		weRed.setOrganId(staff.getOrganId());
		weRed.setRestAmount(Double.parseDouble(money));
		weRed.setDesc(desc);
		if (staff.getTotalIncome()>=Double.parseDouble(money)) {//余额支付
			weRed.setState(1);
			staff.setTotalIncome(Arith.sub(staff.getTotalIncome(), Double.parseDouble(money)));
			mongoTemplate.save(staff);
		}else {//微信支付
			weRed.setState(0);    //表示存在这个记录但是没有支付
			status.setMessage(weRed.get_id());
		}
		BigDecimal money1 = new BigDecimal(weRed.getAmount()+"");
		int numberOfPeople = weRed.getRestCount();
		BigDecimal minMoney = new BigDecimal("0.01");
		List<BigDecimal> result = WeixinRedPacket.initMoney(money1, minMoney, numberOfPeople);
		List<Double> moneyList=new ArrayList<Double>();
		for (BigDecimal bigDecimal : result) {
			moneyList.add(bigDecimal.doubleValue());
		}
		weRed.setSmallReds(moneyList);
		mongoTemplate.insert(weRed);
		return status;
	}

	//已发红包列表
	@Override
	public FlipInfo<WeRed> getRedPacketList(String staffId,
											FlipPageInfo<WeRed> flp) {
		FlipInfo<WeRed> weReds= new FlipInfo<WeRed>();
		weReds=mongoTemplate.findByPage(Query.query(Criteria.where("senderId").is(staffId).and("type").is(2).and("state").ne(0)), flp, WeRed.class);
		return weReds;
	}

	//获取红包详情
	@Override
	public WeRed getRedPacketById(String redId) {
		WeRed weRed = mongoTemplate.findById(redId, WeRed.class);
		return weRed;
	}

	//获取已抢红包用户
	@Override
	public FlipInfo<WeRedRecord> getRedUserList(String redId,
												FlipPageInfo<WeRedRecord> flp) {
		FlipInfo<WeRedRecord> redRecord=new FlipInfo<WeRedRecord>();
		redRecord=mongoTemplate.findByPage(Query.query(Criteria.where("redId").is(redId).and("")), flp, WeRedRecord.class);
		List<WeRedRecord> data = redRecord.getData();
	/*	for (WeRedRecord weRedRecord : data) {
			String date=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(weRedRecord.getCreateTime());
			weRedRecord.setCreateTimeFormat(date);
		}*/
		return redRecord;
	}

	//获取我的店铺
	@Override
	public List<Organ> getMyOrgans(String staffId) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		List<Organ> list = mongoTemplate.find(Query.query(Criteria.where("_id").in(staff.getWeOrganIds()).andOperator(Criteria.where("organPositionState").is("0"))), Organ.class);
		return list;
	}
	//获取会员客户
	@Override
	public FlipInfo<User> getCustomersMember(String staffId, String type,
											 FlipInfo<User> fpi) {
		FlipInfo<User> users=new FlipInfo<User>();
		List<Integer> list=new ArrayList<Integer>();
		list.add(-1);list.add(0);
		List<String> userList=new ArrayList<String>();
		List<WeOrganOrder> orderList = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("state").nin(list)), WeOrganOrder.class);
		for (WeOrganOrder weOrganOrder : orderList) {
			if (!userList.contains(weOrganOrder.getUserId())) {
				userList.add(weOrganOrder.getUserId());
			}
		}
		List<Usercard> cardList = mongoTemplate.find(Query.query(Criteria.where("userId").in(userList)), Usercard.class);
		userList.clear();
		for (Usercard usercard : cardList) {
			userList.add(usercard.getUserId());
		}
		users=mongoTemplate.findByPage(Query.query(Criteria.where("_id").in(userList)), fpi, User.class);
		List<User> data = users.getData();
		for (User user : data) {
			List<WeOrganOrder> appointList = mongoTemplate.find(Query.query(Criteria.where("userId").is(user.get_id()).and("state").is(1)), WeOrganOrder.class);
			if (appointList.size()>0) {
				user.setOrderTime(appointList.get(0).getOrderTime());
			}else {
				user.setOrderTime(orderList.get(0).getOrderTime());
			}
			user.setIsMember(true);
		}
		return users;
	}
	@Override
	public Account getAccountById(String userId, String type) {
		Account account = mongoTemplate.findOne(Query.query(Criteria.where("entityID").is(userId).and("accountType").is(type).and("weUnionId").ne(null).ne("")), Account.class);
		return account;
	}
	//修改支付密码
	@Override
	public void updatePassword(String staffId, String pwd) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(staffId));
		Update update = new Update();
		update.set("payPassword", pwd);
		mongoTemplate.updateFirst(query, update, Staff.class);
	}
	@Override
	public Account findOneAccount(String staffId) {
		Query query  =  new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("entityID").is(staffId),Criteria.where("accountType").is(Account.TYPE_STAFF));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Account.class);
	}
	@Override
	public Account findOneAccount(String staffId,String openid) {
		Query query  =  new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("entityID").is(staffId),Criteria.where("accountType").is(Account.TYPE_STAFF),Criteria.where("accountName").is(openid));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Account.class);
	}
	@Override
	public void updateStaffIncome(String staffId, double totalIncome) {
		Query query =  new Query();
		Update update = new Update();
		query.addCriteria(Criteria.where("_id").is(staffId));
		update.set("totalIncome", totalIncome);
		mongoTemplate.updateFirst(query, update, Staff.class);
	}

	@Override
	public void saveStaffIncome(WeStaffIncome weStaffIncome) {
		mongoTemplate.save(weStaffIncome);
	}
	@Override
	public boolean isHaveStaffPhone(String phone) {
		Query query =  new Query();
		query.addCriteria(Criteria.where("phone").is(phone));
		return mongoTemplate.exists(query, Staff.class);
	}
	@Override
	public void setWeStaffCalendar(String staffId) {
		Query query = new Query();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = simpleDateFormat.format(date);
		int intDate = Integer.parseInt(currentDate);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("day").gte(intDate).lte(intDate+6),Criteria.where("staffId").is(staffId));
		query.addCriteria(criteria);
		List<WeStaffCalendar> weStaffCalendars = mongoTemplate.find(query, WeStaffCalendar.class);
		if(weStaffCalendars.size() != 7) { //7表示都设置了
			LinkedList<Integer> days = new LinkedList<Integer>();
			for(Integer i = intDate;i<= intDate+6;i++ ) {
				days.add(i);
			}
			if(weStaffCalendars.size() > 0) {
				for(WeStaffCalendar weStaffCalendar : weStaffCalendars) {
					days.remove(new Integer(weStaffCalendar.getDay()));
				}
			}
			for (Integer day : days) {
				setStaffDayCalendar(staffId,day);
			}
		}
	}
	private void setStaffDayCalendar(String staffId, Integer day) {
		Staff staff = mongoTemplate.findById(staffId, Staff.class);
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(day.toString()));
			int weekNum= calendar.get(Calendar.DAY_OF_WEEK) - 1;
			WeStaffCalendar weStaffCalendar = null;
			switch (weekNum) {
				case 0:  //周日
					weStaffCalendar = setCalendar(2,day);
					break;
				case 1:
					weStaffCalendar = setCalendar(1,day);
					break;
				case 2:
					weStaffCalendar = setCalendar(1,day);
					break;
				case 3:
					weStaffCalendar = setCalendar(1,day);
					break;
				case 4:
					weStaffCalendar = setCalendar(1,day);
					break;
				case 5:
					weStaffCalendar = setCalendar(1,day);
					break;
				case 6:
					weStaffCalendar = setCalendar(2,day);
					break;
			}
			if(weStaffCalendar != null) {
				weStaffCalendar.setStaffId(staffId);
				weStaffCalendar.setOrganId(staff.getOrganId());
				mongoTemplate.save(weStaffCalendar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * @param type 1 表示1到周五 2 表示周末
	 */
	private WeStaffCalendar setCalendar(int type,Integer day) {
		try {
			WeStaffCalendar weStaffCalendar = null;
			if(type == 1) {
				weStaffCalendar = (WeStaffCalendar) new WeStaffCalendarTemplate().weekday.clone();
			} else {
				weStaffCalendar = (WeStaffCalendar) new WeStaffCalendarTemplate().weekend.clone();
			}
			weStaffCalendar.setDay(day);
			weStaffCalendar.setIdIfNew();
			weStaffCalendar.setCreateTimeIfNew();
			return weStaffCalendar;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public  FlipInfo<PositionOrder> getStationList(String staffId, FlipInfo<PositionOrder> fpi){
		FlipInfo<PositionOrder> stationList=mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)),fpi,PositionOrder.class);
		for(PositionOrder po:stationList.getData()){
			Date endTime = DateUtil.dayEnd(po.getEndTime(),0);
			Date beginTime = DateUtil.dayEnd(po.getBeginTime(),1);
			int timeState = dateTimeState(beginTime,endTime);
			po.setTimeState(timeState);
		}
		return stationList;
	}
	public int dateTimeState(Date beginTime,Date endTime){
		if(beginTime.getTime()>new Date().getTime()){
			return 0;
		}else if(beginTime.getTime()<=new Date().getTime()&&new Date().getTime()<=endTime.getTime()){
			return 1;
		}else if(new Date().getTime()>endTime.getTime()){
			return 2;
		}
		return 3;
	}
	@Override
	public TreeMap<String, Integer> queryStationList(String positionOrderId, FlipInfo<OrganPositionDetails> fpi) {
		HashMap<String, Integer> mapOne = new HashMap<String, Integer>();
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		FlipInfo<OrganPositionDetails> staList=mongoTemplate.findByPage(Query.query(Criteria.where("positionOrderId").is(positionOrderId)),fpi,OrganPositionDetails.class);
		TreeMap<String, Integer> staList1=new TreeMap<String, Integer>();
		for(OrganPositionDetails opd:staList.getData()){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String s=sdf.format(opd.getTime());
			mapOne.put(s, opd.getNum());
		}
		Object[] key =  mapOne.keySet().toArray();
		Arrays.sort(key);
		for(int i=0;i<key.length;i++){
			staList1.put((String) key[i],mapOne.get(key[i]));
			System.out.println((String) key[i]+mapOne.get(key[i]));
		}
		map=com.osg.framework.util.StringUtils.timeListToStringList(staList1);
		return map;
		}
	@Override
	public PositionOrder getStationDetails(String positionOrderId) {
		PositionOrder po=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(positionOrderId)),PositionOrder.class);
		Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(po.getOrganId())),Organ.class);
		Date endTime = DateUtil.dayEnd(po.getEndTime(),0);
		Date beginTime = DateUtil.dayEnd(po.getBeginTime(),1);
		int timeState = dateTimeState(beginTime,endTime);
		po.setTimeState(timeState);
		po.setCity(organ.getCity());
		po.setDistrict(organ.getDistrict());
		po.setLogo(organ.getLogo());
		po.setAddress(organ.getAddress());
		return po;
	}

	/**
	 * 查询我的收入
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeOrganOrder> queryStaffProfit(FlipInfo fpi,String staffId,String appDate){
		if(StringUtils.isNotEmpty(appDate)){//选择时间显示，对历史数据处理
			Date date = DateUtil.dayEnd(DateUtil.formatDate(appDate),0);
			String dateString = DateUtil.format(date,"yyyy-MM-dd HH:mm:ss");
			Criteria orCriteria = new Criteria();
			orCriteria.orOperator(Criteria.where("payTime").lte(dateString),Criteria.where("payTime").is(null));
			Criteria criteria = new Criteria();
			criteria.and("staffId").is(staffId).and("state").in(3,10);
			criteria.andOperator(orCriteria);
			fpi = mongoTemplate.findByPage(Query.query(criteria)
					.with(new Sort(Sort.Direction.DESC,"payTime")),fpi,WeOrganOrder.class);
			return fpi;
		}
		fpi = mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId).and("state").in(3,10))
				.with(new Sort(Sort.Direction.DESC,"payTime")),fpi,WeOrganOrder.class);
		return fpi;
	}
	/**
	 *  organOrderIdList  店铺预约订单id 集合
	 * 根据订单查询分账
	 */
	public List<WeUserPayOrder> queryPayOrder(List<String> organOrderIdList){
		return mongoTemplate.find(Query.query(Criteria.where("organOrderId").in(organOrderIdList).and("state").is(1)),WeUserPayOrder.class);
	}
	/**
	 * payOrderList  支付订单的id 集合
	 *根据支付订单表的id查询分账信息
	 */
	public List<WeUserPayFenzhang> queryFenzhang(List<String> payOrderList){
		return mongoTemplate.find(Query.query(Criteria.where("orderId").in(payOrderList)),WeUserPayFenzhang.class);
	}
	/**
	 * 查询当前技师指定时间的所有订单(已经给过钱的单子)
	 */
	public List<WeOrganOrder> queryWeOrganOrderOfOneDay(String staffId,String time){
		if("1900-01-01".equals(time)){//对历史数据处理
			return mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("state").in(3,10).and("payTime").is(null)),WeOrganOrder.class);
		}
		return mongoTemplate.find(Query.query(Criteria.where("staffId").is(staffId).and("state").in(3,10).and("payTime").regex(time)),WeOrganOrder.class);
	}
}
