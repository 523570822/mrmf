package com.mrmf.service.usermy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.mrmf.entity.coupon.MyCoupon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Account;
import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.UserCollect;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.WeOrganComment;
import com.mrmf.entity.WeOrganOrder;
import com.mrmf.entity.WePayLog;
import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeRedRecord;
import com.mrmf.entity.WeStaffCase;
import com.mrmf.entity.WeStaffIncome;
import com.mrmf.entity.WeUserCompensate;
import com.mrmf.entity.WeUserFeedback;
import com.mrmf.entity.WeUserPayFenzhang;
import com.mrmf.entity.WeUserPayOrder;
import com.mrmf.entity.WeUserWalletHis;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.common.Arith;
import com.mrmf.service.wecommon.WeCommonServiceImpl;
import com.mrmf.service.wecommon.WeComonService;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DistanceUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.GpsUtil;

@Service("userMyService")
public class UserMyServiceImpl implements UserMyService{
	@Autowired
	private EMongoTemplate mongoTemplate;
	@Autowired
	private AccountService accountService;
	@Autowired
	private WeComonService weCommonService;

	@Override
	public FlipInfo<WeOrganOrder> expenseRecord(String userId, String page, String size,FlipPageInfo<WeOrganOrder> flp) throws BaseException{
		List<Integer> list=new ArrayList<Integer>();
		list.add(-1);
		list.add(0);
		list.add(1);
		list.add(2);
		FlipInfo<WeOrganOrder> fpi=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").nin(list)), flp, WeOrganOrder.class);
		for(WeOrganOrder order : fpi.getData()){
			String orderTime = order.getOrderTime();
			if(orderTime != null && orderTime.length() > 10){
				String orderTimeFormat = orderTime.substring(0, 4)+"/"+orderTime.substring(5, 7)+"/"+orderTime.substring(8, 10);
				order.setOrderTimeFormat(orderTimeFormat);
			}
			Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(order.getOrganId())), Organ.class);
			if(organ == null){
				continue;
			}
			order.setOrganName(organ.getName());
			order.setOrganLogo(organ.getLogo());
		}
		return fpi;
	}

	@Override
	public FlipInfo<UserCollect> mycollect(String userId, String type,String longitude,String latitude, String page, 
			String size) throws BaseException {
		userId = "7840835260965106637";
		page="1";
		size="10";
		longitude = "116.387194";
		latitude = "39.927073";
		FlipInfo<UserCollect> fpiuc = new FlipInfo<UserCollect>();
		List<UserCollect> userCollectList = new ArrayList<UserCollect>();
		UserCollect userCollect = new UserCollect();
		User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
		List<String> collectids = new ArrayList<String>();
		int start = Integer.parseInt(size)*(Integer.parseInt(page)-1);
		int end = Integer.parseInt(size)*Integer.parseInt(page)-1;
		String collectid = "";
		if("1".equals(type)){//案例
			collectids = user.getFavorCaseIds();
			for(;collectids.size() >= start+1 && start <= end;start++){
				userCollect = new UserCollect();
				collectid = collectids.get(start);
				WeStaffCase staffcase = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(collectid)), WeStaffCase.class);
				userCollect.setPicture(staffcase.getLogo().get(0));
				userCollect.setTitle(staffcase.getTitle());
				userCollect.setPrice(staffcase.getPrice());
				userCollect.setFollowCount(staffcase.getFollowCount());
				userCollectList.add(userCollect);
			}
		}else if("2".equals(type)){//技师
			collectids = user.getFavorStaffIds();
			for(;collectids.size() >= start+1 && start <= end;start++){
				userCollect = new UserCollect();
				collectid = collectids.get(start);
				Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(collectid)), Staff.class);
				userCollect.setPicture(staff.getLogo());
				userCollect.setTitle(staff.getName());
				userCollect.setPrice(staff.getStartPrice());
				userCollect.setFollowCount(staff.getFollowCount());
				userCollect.setZanCount(staff.getZanCount());
				userCollectList.add(userCollect);
			}
		}else if("3".equals(type)){//店铺
			collectids = user.getFavorOrganIds();
			GpsPoint userGpsPoint = new GpsPoint(Double.parseDouble(longitude),Double.parseDouble(latitude));
			for(;collectids.size() >= start+1 && start <= end;start++){
				userCollect = new UserCollect();
				collectid = collectids.get(start);
				Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(collectid)), Organ.class);
				userCollect.setPicture(organ.getLogo());
				userCollect.setTitle(organ.getName());
				userCollect.setFollowCount(organ.getFollowCount());
				userCollect.setZanCount(organ.getZanCount());
				userCollect.setState(organ.getState());
				double distance = GpsUtil.distance(userGpsPoint, organ.getGpsPoint());
				userCollect.setDistance(DistanceUtil.getDistanceChange(distance));
				userCollectList.add(userCollect);
			}
		}
		fpiuc.setData(userCollectList);
		fpiuc.setPage(Integer.parseInt(page));
		fpiuc.setSize(Integer.parseInt(size));
		return fpiuc;
	}
	//评价订单
	@Override
	public ReturnStatus evaluateOrder(String userId, String orderId, String zanCount,String qiuCount, String faceScore, 
			String comment) throws BaseException {
		ReturnStatus status = new ReturnStatus(true);
		WeOrganOrder order = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeOrganOrder.class);
		if (order ==null) {
			status.setSuccess(false);
			status.setMessage("订单不存在");
			return status;
		}
		WeOrganComment organComment = mongoTemplate.findOne(Query.query(Criteria.where("orderId").is(order.get_id()).and("starFaceScore").is(0)), WeOrganComment.class);
		WeOrganComment staffComment = mongoTemplate.findOne(Query.query(Criteria.where("orderId").is(order.get_id()).and("starFaceScore").gt(0)), WeOrganComment.class);
		if (organComment !=null) {
			status.setSuccess(false);
			status.setMessage("您已评价过该店铺");
			return status;
		}
		if (faceScore !=null && !"".equals(faceScore)) {
			if (staffComment !=null) {
				status.setSuccess(false);
				status.setMessage("您已评价过该技师");
				return status;
			}
		}
		WeOrganComment woc = new WeOrganComment();
		woc.setNewId();
		woc.setNewCreate();
		woc.setOrderId(orderId);
		woc.setUserId(userId);
		woc.setContent(comment);
		woc.setStarZan(Integer.parseInt(zanCount));
		woc.setStarQiu(Integer.parseInt(qiuCount));
		if (order !=null) {
			order.setState(10);
			if (faceScore !=null && !faceScore.equals("")) {//评价技师
				woc.setStarFaceScore(Integer.parseInt(faceScore));
				woc.setStaffId(order.getStaffId());
				Staff staff = mongoTemplate.findById(order.getStaffId(), Staff.class);
				if (staff !=null) {
					if (staff.getEvaluateCount() == null) {
						staff.setEvaluateCount(1);
					}else {
						staff.setEvaluateCount(staff.getEvaluateCount()+1);
					}
					staff.setZanCount(staff.getZanCount()+Integer.parseInt(zanCount));
					staff.setQiuCount(staff.getQiuCount()+Integer.parseInt(qiuCount));
					staff.setFaceScore(staff.getFaceScore()+Integer.parseInt(faceScore));
					//mongoTemplate.save(objectToSave, collectionName)
					mongoTemplate.save(staff);
				}
			}else {//评价店铺
				woc.setOrganId(order.getOrganId());
				Organ organ = mongoTemplate.findById(order.getOrganId(), Organ.class);
				if (organ !=null ) {
					Integer evaluateCount=organ.getEvaluateCount();
					if (evaluateCount == null) {
						organ.setEvaluateCount(1);
					}else {
						organ.setEvaluateCount(organ.getEvaluateCount()+1);
					}
					organ.setZanCount(organ.getZanCount()+Integer.parseInt(zanCount));
					organ.setQiuCount(organ.getQiuCount()+Integer.parseInt(qiuCount));
					mongoTemplate.save(organ);
				}
			}
		}
		//在这里进行云净网验证不通过就存到另一张表里
		if(WeCommonServiceImpl.sensitiveWords.size()==0){
			weCommonService.querySensitiveWords();
		}
		ReturnStatus checkstatus= weCommonService.checkComment(woc.getContent());
		if (!checkstatus.isSuccess()) {
			mongoTemplate.insert(woc,"weOrganCommentSecret");
		}else{
			mongoTemplate.insert(woc);
		}
		mongoTemplate.save(order);
		return status;
	}

	@Override
	public User queryUserById(String userId) {
		return mongoTemplate.findById(userId, User.class);
	}

	@Override
	public void updateImg(String userId,String imgSrc) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.set("avatar", imgSrc);
		mongoTemplate.updateFirst(query, update, User.class);
	}
	@Override
	public void updateNick(String userId, String nick) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.set("nick", nick);
		mongoTemplate.updateFirst(query, update, User.class);
	}
	
	
	@Override
	public void updatePhone(String userId, String phone) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.set("phone", phone);
		mongoTemplate.updateFirst(query, update, User.class);
	}
	@Override
	public void updateEmail(String userId, String email) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.set("email", email);
		mongoTemplate.updateFirst(query, update, User.class);
	}
	@Override
	public void updateBirthDay(String id, String birthday) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");         
		Date date = null;
		try {
		     date = sdf.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}  	
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("birthday", date);
	    mongoTemplate.updateFirst(query, update, User.class);
	}

	@Override
	public void updatePwd(String id, String pwd) {
		Query  query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("payPassword", pwd);
	    mongoTemplate.updateFirst(query, update, User.class);
	}
	
	@Override
	public FlipInfo<WeUserWalletHis> queryWallet(
			FlipInfo<WeUserWalletHis> weUserWalletHis,String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("amount").ne(0),Criteria.where("userId").is(userId));
		query.addCriteria(criteria);
		return mongoTemplate.findByPage(query, weUserWalletHis, WeUserWalletHis.class);
	}
	@Override
	public void insert(WeUserWalletHis weUserWalletHis) {
		mongoTemplate.save(weUserWalletHis);
	}

	//我的订单列表
	@Override
	public FlipInfo<WeOrganOrder> getOrders(String userId, String type,
			FlipPageInfo<WeOrganOrder> flp) {
		//两天以前未支付的订单取消
		Calendar cal=Calendar.getInstance();
		int day=cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, day-2);
		Date date=cal.getTime();//两天前的时间
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("state").is(2),Criteria.where("createTime").lt(date));
		List<WeOrganOrder> weorders=mongoTemplate.find(Query.query(c), WeOrganOrder.class);
		for(WeOrganOrder order:weorders){
			order.setState(-1);
			mongoTemplate.save(order);
		}
		FlipInfo<WeOrganOrder> orders=new FlipInfo<WeOrganOrder>();
		if (type.equals("1")) {//全部
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").nin(-1,0)), flp, WeOrganOrder.class);
		}else if (type.equals("2")) {//待确认
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").is(1)), flp, WeOrganOrder.class);
		}else if (type.equals("3")) {//待支付
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").is(2)), flp, WeOrganOrder.class);
		}else if (type.equals("4")) {//待评价
			orders=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").is(3)), flp, WeOrganOrder.class);
		}
		List<WeOrganOrder> data = orders.getData();
		for (WeOrganOrder weOrganOrder : data) {
			if (weOrganOrder.getServiveName()==null) {
				weOrganOrder.setServiveName(weOrganOrder.getTitle());
			}
			Organ organ = mongoTemplate.findById(weOrganOrder.getOrganId(), Organ.class);
			weOrganOrder.setOrganAddress(organ.getAddress());
			weOrganOrder.setOrganLogo(organ.getLogo());
			weOrganOrder.setOrganName(organ.getName());
			if (weOrganOrder.getType()==1) {
				Bigsort bigsort = mongoTemplate.findById(weOrganOrder.getOrderService(), Bigsort.class);
				if (bigsort !=null) {
					weOrganOrder.setTitle(bigsort.getName());
				}
			}
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
		Staff staff = mongoTemplate.findById(order.getStaffId(), Staff.class);
		Organ organ = mongoTemplate.findById(order.getOrganId(), Organ.class);
		//通过订单id查到订单对象，通过订单里的技师和店面id得到对象
		if (staff !=null ) {//如果技师不为空，则赋值
			order.setStaffLogo(staff.getLogo());
			order.setStaffName(staff.getName());
			order.setUserPhone(staff.getPhone());
		}
		if (organ !=null ) {//如果店面不为空，则赋值
			order.setOrganName(organ.getName());
			order.setOrganAddress(organ.getAddress());
			order.setOrganLogo(organ.getLogo());
		}
		if (order.getState()==10) {//状态是已评价才进去
			WeOrganComment comment = mongoTemplate.findOne(Query.query(Criteria.where("orderId").is(order.get_id()).and("starFaceScore").is(0)), WeOrganComment.class);
			if (comment !=null) {
				order.setStarZan(comment.getStarZan());
				order.setContent(comment.getContent());
			}
		}
		if (order.getTitle() != null) {//如果订单服务类型不为空
			if (order.getTitle().indexOf("发") != -1) {//返回索引位置，-1为未找到
				order.setServerType("1");//给服务类型赋值
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
		return order;
	}

	//取消订单
	@Override
	public ReturnStatus cancelOrder(String orderId) {
		ReturnStatus status = new ReturnStatus(true);
		WeOrganOrder order = mongoTemplate.findById(orderId, WeOrganOrder.class);
		if (order !=null) {
			if (order.getState()==1 || order.getState()==2) {
				order.setState(0);
				mongoTemplate.save(order);
				return status;
			}else {
				status.setSuccess(false);
				status.setMessage("订单已失效");
				return status;
			}
		}else {
			status.setSuccess(false);
			status.setMessage("订单不存在");
			return status;
		}
	}

	//我的消息列表
	@Override
	public FlipInfo<WeMessage> myMessageList(String userId, String type) {
		FlipInfo<WeMessage> message=new FlipInfo<WeMessage>();
		message.setSortField("createTime");
		message.setSortOrder("DESC");
		if (type.equals("1")) {//个人消息
			message=mongoTemplate.findByPage(Query.query(Criteria.where("toId").is(userId).and("type").is("1").and("toType").is("user")), message, WeMessage.class);
		}else if (type.equals("2")) {//系统消息
			message=mongoTemplate.findByPage(Query.query(Criteria.where("toId").is(userId).and("type").is("2").and("toType").is("user")), message, WeMessage.class);
		}
		return message;
	}

	//我的赔付列表
	@Override
	public FlipInfo<WeUserCompensate> compensateList(String userId,String type,
			FlipPageInfo<WeUserCompensate> flp) {
		FlipInfo<WeUserCompensate> compensate=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId)), flp, WeUserCompensate.class);
		for (WeUserCompensate weUserCompensate : compensate.getData()) {
			if (weUserCompensate.getTarget().equals("1")) {//店铺赔付
				Organ organ = mongoTemplate.findById(weUserCompensate.getProviderId(),Organ.class);
				weUserCompensate.setProviderName(organ.getName());
				weUserCompensate.setLogo(organ.getLogo());
			}else if (weUserCompensate.getTarget().equals("2")) {//技师赔付
				Staff staff = mongoTemplate.findById(weUserCompensate.getProviderId(), Staff.class);
				weUserCompensate.setProviderName(staff.getName());
				weUserCompensate.setLogo(staff.getLogo());
			}
			String time=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(weUserCompensate.getCreateTime());
			weUserCompensate.setTimeFormat(time);
		}
		return compensate;
	}

	//申诉处理结果
	@Override
	public WeUserCompensate getCompensate(String compensateId) {
		WeUserCompensate compensate = mongoTemplate.findById(compensateId, WeUserCompensate.class);
		String time=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(compensate.getCreateTime());
		compensate.setTimeFormat(time);
		return compensate;
	}

	//获取项目列表
	@Override
	public FlipInfo<Code> selectType(FlipInfo<Code> fpi) {
		FlipInfo<Code> code = mongoTemplate.findByPage(Query.query(Criteria.where("type").is("compensateType")), fpi,
				Code.class);
		return code;
	}

	@Override
	public FlipInfo<WeOrganOrder> selectProvider(String userId,
			FlipInfo<WeOrganOrder> fpi) {
		FlipInfo<WeOrganOrder> order=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("state").is(3)), fpi, WeOrganOrder.class);
		List<WeOrganOrder> data = order.getData();
		for (WeOrganOrder weOrganOrder : data) {
			Staff staff = mongoTemplate.findById(weOrganOrder.getStaffId(),Staff.class );
			Organ organ = mongoTemplate.findById(weOrganOrder.getOrganId(),Organ.class );
			weOrganOrder.setOrganName(organ.getName());
			weOrganOrder.setStaffName(staff.getName());
		}
		return order;
	}

	//获取订单
	@Override
	public WeOrganOrder getOrderById(String orderId) {
		WeOrganOrder order = mongoTemplate.findById(orderId, WeOrganOrder.class);
		if (order !=null) {
			Organ organ = mongoTemplate.findById(order.getOrganId(), Organ.class);
			Staff staff = mongoTemplate.findById(order.getStaffId(), Staff.class);
			if (organ !=null) {
				order.setOrganName(organ.getName());
			}
			if (staff !=null) {
				order.setStaffName(staff.getName());
			}
		}
		return order;
	}

	//获取赔付类型
	@Override
	public Code getCodeById(String codeId) {
		Code code = mongoTemplate.findById(codeId, Code.class);
		return code;
	}

	//保存赔付
	@Override
	public ReturnStatus saveCompensate(String userId, String orderId,
			String codeId, String target, String desc, List<String> logos) {
		List<String> list=new ArrayList<String>();
		list.addAll(logos);
		WeUserCompensate compensate = new WeUserCompensate();
		WeOrganOrder order = mongoTemplate.findById(orderId, WeOrganOrder.class);
		Code code = mongoTemplate.findById(codeId, Code.class);
		User user = queryUserById(userId);
		compensate.setNewId();
		compensate.setNewCreate();
		if (target.equals("1")) {//店铺
			compensate.setProviderId(order.getOrganId());
			Organ organ = mongoTemplate.findById(order.getOrganId(), Organ.class);
			compensate.setProviderName(organ.getName());
		}else {
			compensate.setProviderId(order.getStaffId());
			Staff staff = mongoTemplate.findById(order.getStaffId(), Staff.class);
			compensate.setProviderName(staff.getName());
		}
		compensate.setUserId(userId);
		compensate.setOrderId(orderId);
		compensate.setService(order.getTitle());
		compensate.setType(code.getName());
		compensate.setDesc(desc);
		compensate.setImages(list);
		compensate.setState(0);
		compensate.setTarget(target);
		compensate.setPhone(user.getPhone());
		mongoTemplate.insert(compensate);
		return new ReturnStatus(true);
	}

	@Override
	public void saveFeedBack(WeUserFeedback weUserFeedback) {
		mongoTemplate.save(weUserFeedback);
	}
	
	@Override
	public void updateUserWalletAmount(String userId,double amount) {
		Update update = new Update();
		update.set("walletAmount", amount);
		Query query  = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		mongoTemplate.updateFirst(query, update, User.class);
	}

	@Override
	public void saveDonationRecord(WeUserWalletHis weDonationRecord) {
		mongoTemplate.save(weDonationRecord);
	}

	@Override
	public void saveUser(User user) {
		mongoTemplate.save(user);
	}
	/**
	 * 查询所有的红包
	 */
	@Override
	public List<WeRed> findWeReds(double longitude,double latitude,String userId) {
		List<WeRed> weReds = new ArrayList<WeRed>();
		FlipInfo<WeRed> flip = new FlipInfo<WeRed>();
		Query queryStaffRed = new Query();
		Criteria criteriaStaffRed = new Criteria();
		criteriaStaffRed.andOperator(Criteria.where("type").is(2),Criteria.where("restCount").gt(0),Criteria.where("state").is(1));
		queryStaffRed.addCriteria(criteriaStaffRed);
		flip = mongoTemplate.findByPageGeo(longitude, latitude, 10, queryStaffRed, flip, WeRed.class);
		
		if(flip.getData() != null && flip.getData().size() >0) {
			weReds.addAll(flip.getData());
		}
		
		Query querySysRed = new Query();
		Criteria criteriaSysRed = new Criteria();
		criteriaSysRed.andOperator(Criteria.where("type").is(1),Criteria.where("restCount").gt(0),Criteria.where("state").is(1));
		querySysRed.addCriteria(criteriaSysRed);
		List<WeRed> weRedTem = mongoTemplate.find(querySysRed, WeRed.class);
		if(weRedTem != null && weRedTem.size() >0) {
			weReds.addAll(weRedTem);
		}
		return weReds;
	}
	/**
	 * 0 表示程序出错了
	 * 1  表示成功领取
	 * 2  红包已经被人领取完了
	 * 3  技师发送的红包有错误！
	 * 4  你还有不能领取红包,赶紧去消费,关注技师，或者办理会员去吧！就有机会领取红包
	 * 5 你已经领取过红包！
	 */
	@Override
	public ReturnStatus getWeRed(List<WeRed> weReds,String userId) throws BaseException {
		if(weReds.size() < 1) {
			return new ReturnStatus(2,"红包已经被人领取完了");  
		}
		
		for (int i = 0; i < weReds.size(); i++) {  
			if(isGetRedPacket(weReds.get(i).get_id(), userId)) { //判断是否领取过红包 就去移除
				weReds.remove(i); 
				i--; 
            } 
		}
		System.out.println(weReds);
		if(weReds.size() < 1) {
			return new ReturnStatus(5,"你已经领取过红包！");  
		}
		//这里验证剩下的红包是否合法
		User user = this.queryUserById(userId);
		Random random = new Random();
		WeRed weRed = weReds.get(random.nextInt(weReds.size()));  //从所有的红包中获取其中的一个
		if(weRed.getType() == 1) { //平台红包
			 if(isConsumeUser(userId)) {
				 return updateRedPacket(weRed, user);
			 } else {
				 return new ReturnStatus(4,"您还不能领取红包,赶紧去消费,关注技师，或者办理会员去吧！就有机会领取红包");
			 }
		} else if(weRed.getType() == 2) { //技师红包
			Staff staff = mongoTemplate.findById(weRed.getSenderId(), Staff.class);
			if(weRed.getScope() == 1) {    //服务过的会员客户
				if(isMember(userId)) {     //是会员
					if(isStaffServiceUser(userId, staff.get_id())) { //被服务过
						return updateRedPacket(weRed, user);
					} 
				}
				return new ReturnStatus(4,"您还不能领取红包,赶紧去消费,关注技师，或者办理会员去吧！就有机会领取红包");
			} else if(weRed.getScope() == 2) { //关注过的客户
				if(user.getFavorStaffIds().contains(staff.get_id())) {
					return updateRedPacket(weRed, user);
				} else {
					return new ReturnStatus(4,"您还不能领取红包,赶紧去消费,关注技师，或者办理会员去吧！就有机会领取红包");
				}
			} 
		}
		return new ReturnStatus(3,"亲，继续抢抢吧！可能有红包哦！");  
	}
    /**
     * 领取红包
     */
	public ReturnStatus updateRedPacket(WeRed weRed,User user) {
	   Random random = new Random();
	   if(weRed.getRestCount() >1) {
			 List<Double> smallReds =  weRed.getSmallReds();
			 double smallRed = smallReds.remove(random.nextInt(smallReds.size()));
			 weRed.setSmallReds(smallReds);
			 weRed.setRestAmount(Arith.sub(weRed.getRestAmount(),smallRed));
			 weRed.setRestCount(weRed.getRestCount()-1);
			 user.setWalletAmount(Arith.add(user.getWalletAmount(), smallRed));
			 mongoTemplate.save(user);
			 mongoTemplate.save(weRed); 
			 //保存红包记录
			 WeRedRecord weRedRecord = new WeRedRecord();
			 weRedRecord.setNewId();
			 weRedRecord.setRedId(weRed.get_id());
			 weRedRecord.setUserId(user.get_id());
			 weRedRecord.setUserAvatar(user.getAvatar());
			 weRedRecord.setUserNick(user.getNick());
			 weRedRecord.setAmount(smallRed);
			 weRedRecord.setCreateTime(new Date());
			 SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			 String day = format.format(new Date());
			 weRedRecord.setDay(Integer.parseInt(day));
			 mongoTemplate.save(weRedRecord);
			 /* 用户余额变动记录
			  */
			 WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
			 weUserWalletHis.setIdIfNew();
			 weUserWalletHis.setUserId(user.get_id());
			 weUserWalletHis.setAmount(smallRed);
			 weUserWalletHis.setDesc("红包领取");
			 weUserWalletHis.setNewCreate();
			 mongoTemplate.insert(weUserWalletHis);
			 return new ReturnStatus(weRedRecord,1);  //领取红包成功！
	   } else if(weRed.getRestCount() == 1) {
			WeRedRecord weRedRecord = new WeRedRecord();
			weRedRecord.setNewId();
			weRedRecord.setRedId(weRed.get_id());
			weRedRecord.setUserId(user.get_id());
			weRedRecord.setUserAvatar(user.getAvatar());
			weRedRecord.setUserNick(user.getNick());
			weRedRecord.setAmount(weRed.getRestAmount());
			weRedRecord.setCreateTime(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String day = format.format(new Date());
			weRedRecord.setDay(Integer.parseInt(day));
			weRed.setSmallReds(null);
			weRed.setRestAmount(0);
			weRed.setRestCount(0);
			mongoTemplate.save(weRed);
			mongoTemplate.save(weRedRecord);
			/* 
			 * 用户余额变动记录
			*/
			WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
			weUserWalletHis.setIdIfNew();
			weUserWalletHis.setUserId(user.get_id());
			weUserWalletHis.setAmount(weRed.getRestAmount());
			weUserWalletHis.setDesc("红包领取");
			weUserWalletHis.setNewCreate();
			mongoTemplate.insert(weUserWalletHis);
			return new ReturnStatus(weRedRecord,1);
	   }else {
		    return new ReturnStatus(2,"红包已经被人领取完了");  //表示红包领取完了
	   }
	}
	
	//获取店铺
	@Override
	public Organ getOrganById(String organId) {
		return mongoTemplate.findById(organId, Organ.class);
	}

	//查找我的赔付
	@Override
	public WeUserCompensate getMyCompensate(String userId, String orderId) {
		WeUserCompensate compensate = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId).and("orderId").is(orderId)), WeUserCompensate.class);
		return compensate;
	}


	@Override
	public void saveWeUserWalletHis(WeUserWalletHis weUserWalletHis) {
		mongoTemplate.save(weUserWalletHis);
	}

	@Override
	public Account findOneAccount(String userId) {
		Query query  =  new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("entityID").is(userId),Criteria.where("accountType").is(Account.TYPE_USER));
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Account.class);
	}


	@Override
	public ReturnStatus userVerify(String phone, String code, String userId) {
		ReturnStatus status = accountService.verify(phone, code);
		if(status.isSuccess()){
			User user=mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
			User u=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
			if(user!=null){
				user.setNick(u.getNick());
				user.setAvatar(u.getAvatar());
				user.setGpsPoint(u.getGpsPoint());
				user.setCity(u.getCity());
				user.setFavorOrganIds(u.getFavorOrganIds());
				user.setFavorStaffIds(u.getFavorStaffIds());
				user.setFavorCaseIds(u.getFavorCaseIds());
				user.setOrderHisIds(u.getOrderHisIds());
				user.setWalletAmount(u.getWalletAmount());
				user.setPayPassword(u.getPayPassword());
				List<WeUserPayOrder> weUserPayOrders=mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), WeUserPayOrder.class);
				for(WeUserPayOrder order:weUserPayOrders){
					order.setUserId(user.get_id());
					mongoTemplate.save(order);
				}
				List<WeUserPayFenzhang> weUserPayFenzhangs=mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), WeUserPayFenzhang.class);
				for(WeUserPayFenzhang fz:weUserPayFenzhangs){
					fz.setUserId(user.get_id());
					mongoTemplate.save(fz);
				}
				mongoTemplate.remove(u);
				mongoTemplate.save(user);
				Criteria c=new Criteria();
				c.andOperator(Criteria.where("entityID").is(userId),Criteria.where("accountType").is("user"));
				Account account=mongoTemplate.findOne(Query.query(c), Account.class);
				account.setEntityID(user.get_id());
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
	public void userVerifyNoCode(String phone, String userId) {
		User user=mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
		User u=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
		if(user!=null){
			user.setNick(u.getNick());
			user.setAvatar(u.getAvatar());
			user.setGpsPoint(u.getGpsPoint());
			user.setCity(u.getCity());
			user.setFavorOrganIds(u.getFavorOrganIds());
			user.setFavorStaffIds(u.getFavorStaffIds());
			user.setFavorCaseIds(u.getFavorCaseIds());
			user.setOrderHisIds(u.getOrderHisIds());
			user.setWalletAmount(u.getWalletAmount());
			user.setPayPassword(u.getPayPassword());
			List<WeUserPayOrder> weUserPayOrders=mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), WeUserPayOrder.class);
			for(WeUserPayOrder order:weUserPayOrders){
				order.setUserId(user.get_id());
				mongoTemplate.save(order);
			}
			List<WeUserPayFenzhang> weUserPayFenzhangs=mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), WeUserPayFenzhang.class);
			for(WeUserPayFenzhang fz:weUserPayFenzhangs){
				fz.setUserId(user.get_id());
				mongoTemplate.save(fz);
			}
			mongoTemplate.remove(u);
			mongoTemplate.save(user);
			Criteria c=new Criteria();
			c.andOperator(Criteria.where("entityID").is(userId),Criteria.where("accountType").is("user"));
			Account account=mongoTemplate.findOne(Query.query(c), Account.class);
			account.setEntityID(user.get_id());
			mongoTemplate.save(account);
		}else{
			u.setPhone(phone);
			mongoTemplate.save(u);
		}
	}

	@Override
	public User queryUserByPhone(String phone) {
		return mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
	}

	//扫码支付生成订单
	@Override
	public ReturnStatus scanSaveOrder(String payOrderId) {
		WeUserPayOrder userPayOrder = mongoTemplate.findById(payOrderId, WeUserPayOrder.class);
		if (userPayOrder !=null&&StringUtils.isEmpty(userPayOrder.getOrganOrderId())) {
			WeOrganOrder order = new WeOrganOrder();
			order.setNewId();
			order.setNewCreate();
			order.setUserId(userPayOrder.getUserId());
			order.setOrganId(userPayOrder.getOrganId());
			order.setOrderPrice(userPayOrder.getPrice());
			order.setStaffId(userPayOrder.getStaffId());
			order.setState(3);
			order.setType(1);
			order.setTitle("微信扫码支付");
			order.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			order.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			mongoTemplate.insert(order);
			userPayOrder.setOrganOrderId(order.get_id());
			mongoTemplate.save(userPayOrder);//将生成的订单ID存入微信支付订单中
			ReturnStatus status = new ReturnStatus(true);
			status.setMessage(order.get_id());
			return status;
		}else {
			return new ReturnStatus(false);
		}
	}

	@Override
	public WeRedRecord findWeRedRecord(String redPackId) {
		Query query =  new Query();
		query.addCriteria(Criteria.where("_id").is(redPackId));
		return mongoTemplate.findOne(query, WeRedRecord.class);
	}

	@Override
	public List<WeRedRecord> findWeRedRecords(String redId) {
		Query query =  new Query();
		query.addCriteria(Criteria.where("redId").is(redId));
		return mongoTemplate.find(query, WeRedRecord.class);
	}

	@Override
	public WeRed findRedPack(String redId) {
		Query query =  new Query();
		query.addCriteria(Criteria.where("_id").is(redId));
		return mongoTemplate.findOne(query, WeRed.class);
	}
	//更新订单价格
	@Override
	public void updateOrderPrice(String orderId, double price) {
		Query query  = new Query();
		query.addCriteria(Criteria.where("_id").is(orderId));
		Update  update = new Update();
		update.set("orderPrice", price);
		mongoTemplate.updateFirst(query, update, WeOrganOrder.class);
	}
	/*
	 * 用户是否消费过
	 */
	public Boolean isConsumeUser(String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("userId").is(userId),
				Criteria.where("type").is(0),Criteria.where("state").is(1));
		query.addCriteria(criteria);
		return mongoTemplate.exists(query, WeUserPayOrder.class);
	}
	/* 是否是会员 
	 */
	@Override
	public Boolean isMember(String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("userId").is(userId),Criteria.where("delete_flag").is(false));
		query.addCriteria(criteria);
		return mongoTemplate.exists(query, Userincard.class);
	}
	/*
	 * 是否被该几服务过
	 */
	@Override
	public Boolean isStaffServiceUser(String userId,String staffId) {
		Query query = new Query();
		Criteria criteriaOr = new Criteria();
		criteriaOr.orOperator(Criteria.where("staffId1").is(staffId),
				Criteria.where("staffId2").is(staffId),
				Criteria.where("staffId3").is(staffId));
		Criteria criteriaAnd = new Criteria();
		criteriaAnd.andOperator(Criteria.where("userId").is(userId),criteriaOr);
		query.addCriteria(criteriaAnd);
		return mongoTemplate.exists(query, Userpart.class);
	}
	/*
	 * 是否领过去红包
	 */
	@Override
	public Boolean isGetRedPacket(String weRedId, String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("redId").is(weRedId),Criteria.where("userId").is(userId));
		query.addCriteria(criteria);
		return mongoTemplate.exists(query, WeRedRecord.class);
	}

	@Override
	public void updateUserInvitor(String invitor,String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		Update update = new Update();
		update.set("invitor", invitor);
		update.set("inviteDate", new Date());
		mongoTemplate.updateFirst(query, update, User.class);
	}

	@Override
	public void moneyToStaff(String staffId,double money,String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(staffId));
		money =Arith.mul(Arith.div(money, 100), 3);
		Staff staff = mongoTemplate.findOne(query, Staff.class);
		staff.setTotalIncome(staff.getTotalIncome() + money);
		mongoTemplate.save(staff);
		saveStaffIncome(staffId,money,"用户消费返现",userId);
	}
	/*
	 * 保存技师收益记录
	 */
	private void saveStaffIncome(String staffId, double money, String title, String userId) {
		WeStaffIncome weStaffIncome = new WeStaffIncome();
		weStaffIncome.setUserId(userId);
		weStaffIncome.setAmount(money);
		weStaffIncome.setCreateTime(new Date());
		weStaffIncome.setStaffId(staffId);
		weStaffIncome.setTitle(title);
		mongoTemplate.save(weStaffIncome);
	}

	@Override
	public Boolean greaterThanYear(Date nowTime, Date oldTime) {
		long year = (nowTime.getTime() - oldTime.getTime())/(1000*60*60*24*365L);
		if(year>=1) {
			return true;
		} else {
			return false;
		}
	}
	/*获取已经完成的订单列表*/
	@Override
	public FlipInfo<WeOrganOrder> getFinishedOrders(String userId,
			FlipInfo<WeOrganOrder> flp) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("userId").is(userId),Criteria.where("state").is(10));
		query.addCriteria(criteria);
		return mongoTemplate.findByPage(query, flp, WeOrganOrder.class);
	}

	@Override
	public Organ findOrganById(String organId) {
		return mongoTemplate.findById(organId, Organ.class);
	}

	@Override
	public void saveStaffWalletHis(String staffId, double price, String desc,
			String type) {
		WeUserWalletHis weUserWalletHis = new WeUserWalletHis();
		weUserWalletHis.setIdIfNew();
		weUserWalletHis.setUserId(staffId);
		weUserWalletHis.setDesc(desc);
		weUserWalletHis.setOrderId(type);
		weUserWalletHis.setAmount(price);
		weUserWalletHis.setCreateTimeIfNew();
		mongoTemplate.save(weUserWalletHis);
	}
	
	@Override
	public void saveStaffIncome(WeStaffIncome weStaffIncome) {
		mongoTemplate.save(weStaffIncome);
	}

	@Override
	public void userAwardStaff(String staffId, double money) {
		Staff staff  = mongoTemplate.findById(staffId, Staff.class);
		staff.setTotalIncome(staff.getTotalIncome() + money);
		mongoTemplate.save(staff);
	}

	@Override
	public Staff findStaffById(String staffId) {
		return mongoTemplate.findById(staffId, Staff.class);
	}

	@Override
	public void updateUserWallet(String userId, double money) {
		User user = queryUserById(userId);
		user.setWalletAmount(user.getWalletAmount() - money);
		mongoTemplate.save(user);
	}

	@Override
	public boolean isHaveUserPhone(String phone) {
		Query query = new Query();
		query.addCriteria(Criteria.where("phone").is(phone));
		return mongoTemplate.exists(query, User.class);
	}

	@Override
	public long findUserMessageCount(String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("toId").is(userId),Criteria.where("toType").is("user"),Criteria.where("readFalg").is(false));
		query.addCriteria(criteria);
		return mongoTemplate.count(query, WeMessage.class);
	}

	@Override
	public void updateSysMessageRead(String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("toId").is(userId),Criteria.where("type").is("2"),Criteria.where("toType").is("user"),Criteria.where("readFalg").is(false));
		query.addCriteria(criteria);
		Update update =new Update();
		update.set("readFalg", true);
		mongoTemplate.updateMulti(query, update, WeMessage.class);
	}

	@Override
	public void updateMessageRead(String userId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("toId").is(userId),Criteria.where("type").is("1"),Criteria.where("toType").is("user"),Criteria.where("readFalg").is(false));
		query.addCriteria(criteria);
		Update update =new Update();
		update.set("readFalg", true);
		mongoTemplate.updateMulti(query, update, WeMessage.class);
	}

	@Override
	public void saveWePayLog(WePayLog wePayLog) {
		mongoTemplate.save(wePayLog);
	}
	
	@Override
	public boolean queryWePayLog(String userId, int senderType, int status,int type) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("userId").is(userId),
				Criteria.where("senderType").is(senderType),
				Criteria.where("status").is(status),Criteria.where("type").is(type)
				);
		query.addCriteria(criteria);
		return mongoTemplate.exists(query, WePayLog.class);
	}

	@Override
	public WePayLog queryWePayLogById(String id) {
		return mongoTemplate.findById(id, WePayLog.class);
	}
	@Override
	public FlipInfo<MyCoupon> getCoupon(String userId, String type, FlipPageInfo<MyCoupon> flp) {
		FlipInfo<MyCoupon> coupon=new FlipInfo<MyCoupon>();
		if (type.equals("1")) {//全部
			coupon=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId)), flp, MyCoupon.class);
		}else if (type.equals("2")) {//未使user用
			Criteria criteria=new Criteria();
			criteria.orOperator(Criteria.where("userId").is(userId).and("isUsed").is("0").and("endTime").gte(new Date()),Criteria.where("userId").is(userId).and("isUsed").is("0").and("endTime").is(null));
			coupon = mongoTemplate.findByPage(Query.query(criteria), flp, MyCoupon.class);
			//and("endTime").is(null).and("endTime").gte(new Date())
		}else if (type.equals("3")) {//已使用
			coupon=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("isUsed").is("1")), flp, MyCoupon.class);
		}else if (type.equals("4")) {//已过期
			coupon=mongoTemplate.findByPage(Query.query(Criteria.where("userId").is(userId).and("isUsed").is("0").and("endTime").lt(new Date())), flp, MyCoupon.class);
		}
		return coupon;
	}

	@Override
	public void updateUser(String userId, Map<String, Object> userInfo) {
		User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)),User.class);
		Update update = new Update();
		if (user!=null){
			user.setIdIfNew();
			String nick=(String)userInfo.get("nickname");
			if(!com.osg.framework.util.StringUtils.isEmpty(nick)){
				update.set("nick",nick);
			}
			String avatar=(String)userInfo.get("headimgurl");
			if(!com.osg.framework.util.StringUtils.isEmpty(avatar) && com.osg.framework.util.StringUtils.isEmpty(user.getAvatar())){
				update.set("avatar",avatar);
			}
			GpsPoint gpsPoint=(GpsPoint)userInfo.get("gpsPoint");
			if(gpsPoint!=null){
				update.set("gpsPoint",gpsPoint);
			}
			String isInternalStaff=(String)userInfo.get("isInternalStaff");
			if(!com.osg.framework.util.StringUtils.isEmpty(isInternalStaff)){
				update.set("isInternalStaff",isInternalStaff);
			}
			mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(user.get_id())), update, User.class);
		}

	}
}