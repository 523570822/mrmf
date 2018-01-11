package com.mrmf.service.wecommon;

import com.mrmf.entity.*;
import com.mrmf.entity.organPisition.TimeCreate;
import com.mrmf.service.redis.RedisService;
import it.sauronsoftware.base64.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.service.account.AccountService;
import com.mrmf.service.staff.StaffService;
import com.osg.entity.Entity;
import com.osg.entity.FlipInfo;
import com.osg.entity.GpsPoint;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.PositionUtil;
import com.osg.framework.util.StringUtils;
@Service("weCommonService")
public class WeCommonServiceImpl implements WeComonService{
	public static final String KEY = "";
	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private StaffService staffService;
	@Autowired
	private RedisService redisService;


	@Override
	public ReturnStatus verifycode(String phone, String type) {
		if(Account.TYPE_ORGAN_ADMIN.equals(type)){
			Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Organ.class);
			if (organ == null) {
					return new ReturnStatus(false, "指定手机号未关联到公司信息！");
			}
			return accountService.verifycode(phone);

		}else if(Account.TYPE_STAFF.equals(type)){
			Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Staff.class);
			if (staff == null) {
				return new ReturnStatus(false, "指定手机号未关联到员工信息！");
			} else if(staff.getOrganId()==null&&(staff.getWeOrganIds()==null||staff.getWeOrganIds().size()==0)){
				WeOrganStaffVerify weOrganStaffVerify = mongoTemplate.findOne(Query.query(Criteria.where("staffId").is(staff.get_id())), WeOrganStaffVerify.class);
				if(weOrganStaffVerify != null) {
					if(weOrganStaffVerify.getState() == 0) {
						return new ReturnStatus(false, "您申请的店铺还在审核中请耐心等待！");
					} 
				}
			}
			return accountService.verifycode(phone);
		}else if(Account.TYPE_USER.equals(type)){
			User user=mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
			if(user!=null){
				Account a=mongoTemplate.findOne(Query.query(Criteria.where("entityID").is(user.get_id())), Account.class);
				if(a!=null){
					return new ReturnStatus(false, "指定手机号已关联用户信息！");
				}
			}
			return accountService.verifycode(phone);
		}
		return new ReturnStatus(false,"获取验证码失败");
	}

	@Override
	public ReturnStatus verify(String openId, String unionId, String phone,
			String code, String type,String avatar,String nick,GpsPoint gpsPoint) {

		System.out.println("店铺useropenId======="+openId);
		if(StringUtils.isEmpty(unionId)){
			unionId=openId;
		}
		if(Account.TYPE_ORGAN_ADMIN.equals(type)){//绑定为店铺微信号
			Organ organ =mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone).and("parentId").is("0")), Organ.class);
			if(organ == null){//优先绑定主公司
				organ = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Organ.class);
				if (organ == null) {
					return new ReturnStatus(false, "指定手机号未关联到公司信息！");
				}else{
					if(1 == organ.getValid()){
						if(organ.getWxBanding()==null||organ.getWxBanding()==false){
							return new ReturnStatus(false, "该公司不允许微信绑定，请联系后台管理员");
						}
					}
				}
			}
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("accountType").is("organ"), Criteria.where("accountName").is(openId));

			boolean exist = mongoTemplate.exists(Query.query(criteria), Account.class);
			if (exist) {
				return new ReturnStatus(false, "此微信号已经绑定过公司管理账号！");
			}

			ReturnStatus status = accountService.verify(phone, code);//验证码验证
			if (status.isSuccess()) {
				// 绑定新的微信管理账号  删除旧的微信管理号
				Bak bak = new Bak();
				//Account existOrganAccount = accountService.getAccountByEntityID(organ.get_id(),"organ");
				List<Account> existOrganAccount = accountService.getAccountsByEntityID(organ.get_id(),"organ");
				if (existOrganAccount !=null){
					for(Account exisAccount:existOrganAccount){
						bak.setEntityID(exisAccount.getEntityID());
						bak.setName("Account");
						bak.set_id(exisAccount.get_id());
						bak.setAccountType("organ");
						bak.setWeUnionId(exisAccount.getWeUnionId());
						bak.setAccountName(exisAccount.getAccountName());
						bak.setAccountPwd(exisAccount.getAccountPwd());
						bak.setStatus(exisAccount.getStatus());
						mongoTemplate.save(bak);
						mongoTemplate.remove(new Query(Criteria.where("_id").is(exisAccount.get_id())),Account.class);
						//给原店铺联系人消息通知
//                    try {
//                        WxTemplate tempToOrgan = redisService.getWxTemplate("绑定解除",null,null,null,
//								null,null,"非本人操作请修改密码再次绑定");
//                        redisService.send_template_message(exisAccount.getAccountName(), "organ", Configure.RELIEVE_BINDING_ORGAN, tempToOrgan);
//                    } catch (Exception e) {
//                        System.out.println("原店铺联系人消息通知失败！" + e.getMessage());
//                    }
					}
				}
				Account account = new Account();
				account.setIdIfNew();
				account.setCreateTimeIfNew();
				account.setAccountName(openId);
				account.setAccountPwd(passwordEncoder.encodePassword(openId, null));
				account.setAccountType("organ"); // 公司管理员类型
				account.setWeUnionId(unionId);
				account.setEntityID(organ.get_id());
				account.setStatus("1");
				account.setCreateTime(new Date());
				mongoTemplate.save(account);
			}

			return status;
		}else if(Account.TYPE_STAFF.equals(type)){//绑定为技师微信号
			Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Staff.class);
			if (staff == null) {
				return new ReturnStatus(false, "指定手机号未关联到员工信息！");
			}

			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("accountType").is("staff"), Criteria.where("accountName").is(openId));

			boolean exist = mongoTemplate.exists(Query.query(criteria), Account.class);
			if (exist) {
				return new ReturnStatus(false, "此微信号已经绑定过员工管理账号！");
			}

			ReturnStatus status = accountService.verify(phone, code);
			if (status.isSuccess()) {
				Bak bak = new Bak();
				Account existOrganAccount = accountService.getAccountByEntityID(staff.get_id(),"staff");
				if (existOrganAccount != null){
					bak.setEntityID(existOrganAccount.getEntityID());
					bak.setName("Account");
					bak.set_id(existOrganAccount.get_id());
					bak.setAccountType("staff");
					bak.setWeUnionId(existOrganAccount.getWeUnionId());
					bak.setAccountName(existOrganAccount.getAccountName());
					bak.setAccountPwd(existOrganAccount.getAccountPwd());
					bak.setStatus(existOrganAccount.getStatus());
					mongoTemplate.save(bak);
					mongoTemplate.remove(new Query(Criteria.where("_id").is(existOrganAccount.get_id())),Account.class);
                    //给原技师联系人消息通知
//                    try {
//						WxTemplate tempToStaff = redisService.getWxTemplate("绑定解除",null,null,null,
//								null,null,"非本人操作请修改密码再次绑定");
//                        redisService.send_template_message(existOrganAccount.getAccountName(), "staff", Configure.RELIEVE_BINDING_STAFF, tempToStaff);
//                    } catch (Exception e) {
//                        System.out.println("原技师联系人消息通知失败！" + e.getMessage());
//                    }
				}
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
				account.setCreateTime(new Date());
				mongoTemplate.save(account);

				staff.setWeixin(true); // 绑定成功后开通微信服务
				staff.setState(1);
				staff.setNick(nick);
				mongoTemplate.save(staff);
			}

			return status;
		}else if(Account.TYPE_USER.equals(type)){//绑定为用户微信号
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("accountType").is("user"), Criteria.where("accountName").is(openId));

			boolean exist = mongoTemplate.exists(Query.query(criteria), Account.class);
			if (exist) {
				return new ReturnStatus(false, "此微信号已经绑定过用户管理账号！");
			}
			ReturnStatus status = accountService.verify(phone, code);
			if (status.isSuccess()) {
				//添加用户信息
				Bak bak = new Bak();
				User user = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
				Account existOrganAccount = accountService.getAccountByEntityID(user.get_id(),"user");
				if (existOrganAccount != null){
					bak.setEntityID(existOrganAccount.getEntityID());
					bak.setName("Account");
					bak.set_id(existOrganAccount.get_id());
					bak.setAccountType("user");
					bak.setWeUnionId(existOrganAccount.getWeUnionId());
					bak.setAccountName(existOrganAccount.getAccountName());
					bak.setAccountPwd(existOrganAccount.getAccountPwd());
					bak.setStatus(existOrganAccount.getStatus());
					mongoTemplate.save(bak);
					mongoTemplate.remove(new Query(Criteria.where("_id").is(existOrganAccount.get_id())),Account.class);
                    //给原用户联系人消息通知
                   /* try {
                        WxTemplate tempToUser = redisService.getWxTemplate("绑定解除",null,null,null,
								null,null,"非本人操作请修改密码再次绑定");
                        redisService.send_template_message(existOrganAccount.getAccountName(), "user", Configure.RELIEVE_BINDING_USER, tempToUser);
                    } catch (Exception e) {
                        System.out.println("原用户联系人消息通知失败！" + e.getMessage());
                    }*/
				}
				User user1=new User();
				user.setIdIfNew();
				user.setPhone(phone);
				user.setNick(nick);    
				user.setAvatar(avatar);
				user.setGpsPoint(gpsPoint);
				mongoTemplate.save(user1);//这里应该有问题
				// 绑定新的微信账号
				Account account = new Account();
				account.setIdIfNew();
				account.setCreateTimeIfNew();
				account.setAccountName(openId);
				account.setAccountPwd(passwordEncoder.encodePassword(openId, null));
				account.setAccountType("user"); // 员工类型
				account.setWeUnionId(unionId);
				account.setEntityID(user.get_id());
				account.setStatus("1");
				account.setCreateTime(new Date());
				mongoTemplate.save(account);
			}

			return status;
		}
		return new ReturnStatus(false,"绑定失败！");
	}

	@Override
	public ReturnStatus isExist(String oppenid, String unionid, String appid)
			throws BaseException {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("accountName").is(oppenid),Criteria.where("accountType").is(appid));
		long count = mongoTemplate.count(Query.query(criteria), Account.class);
		if(count>0){
			return new ReturnStatus(true);
		}
		return new ReturnStatus(false,"该微信号第一次关注");
	}

	@Override
	public List<WeekDay> queryScheduleTitle() {
		//设置时间
		Calendar cal = Calendar.getInstance();
		int week=cal.get(Calendar.DAY_OF_WEEK)-1;
		int day=0;
		int month=0;
		int year=0;
		
		List<WeekDay> weekDays=new ArrayList<WeekDay>();
		for(int i=week;i<week+7;i++){
			WeekDay weekDay=new WeekDay();
			day=cal.get(Calendar.DAY_OF_MONTH);
			month= cal.get(Calendar.MONTH) + 1;
			weekDay.setDay(month+"/"+day);
			String monthstr="";
			String daystr="";
			if(month<10){
				monthstr="0"+month;
			}else{
				monthstr=month+"";
			}
			if(day<10){
				daystr="0"+day;
			}else{
				daystr=day+"";
			}
			weekDay.setDay(monthstr+"/"+daystr);
			year=cal.get(Calendar.YEAR);
			int daytime=0;
			daytime=weekDay.DateparseInt(year+monthstr+daystr);
			weekDay.setDaytime(daytime);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			switch(i%7){
			case 0:weekDay.setWeek("星期日");break;
			case 1:weekDay.setWeek("星期一");break;
			case 2:weekDay.setWeek("星期二");break;
			case 3:weekDay.setWeek("星期三");break;
			case 4:weekDay.setWeek("星期四");break;
			case 5:weekDay.setWeek("星期五");break;
			case 6:weekDay.setWeek("星期六");break;
			}
			if(i==week){
				weekDay.setWeek("今天");
			}else if(i==week+1){
				weekDay.setWeek("明天");
			}
			weekDays.add(weekDay);
		}
		return weekDays;
	}

	@Override
	public FlipInfo<WeOrganComment> queryWeCommentById(int type, String id,
			FlipInfo<WeOrganComment> fpi) throws BaseException {
		if(type==1){
			mongoTemplate.findByPage(Query.query(Criteria.where("organId").is(id)), fpi, WeOrganComment.class);
		}else if(type==2){
			mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(id)), fpi, WeOrganComment.class);
		}
		for(WeOrganComment weOrganComment:fpi.getData()){
			User user=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(weOrganComment.getUserId())), User.class);
			if(user!=null){
				weOrganComment.setUserName(user.getNick());
				weOrganComment.setUserImg(user.getAvatar());
			}
		}
		return fpi;
	}

	@Override
	public ReturnStatus saveCommmnet(WeOrganComment weOrganComment) {
		mongoTemplate.save(weOrganComment);
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<Staff> queryStaffListByOrganId(String organId,
			FlipInfo<Staff> fpi) {
			Criteria criteria =new Criteria();
			//,Criteria.where("organId").is(organId)  注释掉了
			criteria.orOperator(Criteria.where("weOrganIds").all(organId));
			Criteria c= new Criteria();
			c.andOperator(Criteria.where("weixin").is(true),Criteria.where("logo").ne(null).ne(""),criteria);
			
		
		//fpi=mongoTemplate.findByPage(Query.query(Criteria.where("organId").is(organId).and("logo").ne(null).ne("")), fpi, Staff.class);
		fpi=mongoTemplate.findByPage(Query.query(c), fpi, Staff.class);
		//mongoTemplate.findByPage(null, fpi, Staff.class);
		for(Staff staff:fpi.getData()){
			Integer evaluateCount=staff.getEvaluateCount();
			if(evaluateCount!=null&&evaluateCount!=0){
				int zanCount=staff.getZanCount();
				int aver=(int)Math.ceil((double)zanCount/evaluateCount);
				if(aver>5){
					aver=5;
				}
				staff.setZanCount(aver);
			}
		}
		return fpi;
	}

	@Override
	public FlipInfo<WeOrganOrder> queryWeOrganOrderListById(int type,
			String id, String status,FlipInfo<WeOrganOrder> fpi) throws BaseException {
		Criteria criteria=new Criteria();
		if(type==1){
			if(!"".equals(status)){
				String[] str=status.split(",");
				List<Integer> ss=new ArrayList<Integer>();
				for(String s:str){
					ss.add(Integer.parseInt(s));
				}
				//int state=Integer.parseInt(status);
				criteria.andOperator(Criteria.where("organId").is(id),Criteria.where("state").in(ss));
			}else{
				criteria.andOperator(Criteria.where("organId").is(id));
			}
			Query query=new Query();
			query.addCriteria(criteria);
			query.with(new Sort(Direction.DESC,"createTime"));
			mongoTemplate.findByPage(query, fpi, WeOrganOrder.class);
		}else if(type==2){
			if(!"".equals(status)){
				String[] str=status.split(",");
				List<Integer> ss=new ArrayList<Integer>();
				for(String s:str){
					ss.add(Integer.parseInt(s));
				}
				//int state=Integer.parseInt(status);
				criteria.andOperator(Criteria.where("staffId").is(id),Criteria.where("state").in(ss));
			}else{
			criteria.andOperator(Criteria.where("staffId").is(id));
			}
			Query query=new Query();
			query.addCriteria(criteria);
			query.with(new Sort(Direction.DESC,"createTime"));
			mongoTemplate.findByPage(query, fpi, WeOrganOrder.class);
		}else if(type==3){
			if(!"".equals(status)){
				String[] str=status.split(",");
				List<Integer> ss=new ArrayList<Integer>();
				for(String s:str){
					ss.add(Integer.parseInt(s));
				}
				//int state=Integer.parseInt(status);
				criteria.andOperator(Criteria.where("userId").is(id),Criteria.where("state").in(ss));
			}else{
			criteria.andOperator(Criteria.where("userId").is(id));
			}
			Query query=new Query();
			query.addCriteria(criteria);
			query.with(new Sort(Direction.DESC,"createTime"));
			mongoTemplate.findByPage(query, fpi, WeOrganOrder.class);
		}
		for(WeOrganOrder weOrganOrder:fpi.getData()){
			Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(weOrganOrder.getOrganId())), Organ.class);
			weOrganOrder.setOrganAddress(organ.getAddress());
			weOrganOrder.setOrganLogo(organ.getLogo());
			weOrganOrder.setOrganName(organ.getName());
		}
		return fpi;
	}

	@Override
	public ReturnStatus saveWeOrganOrder(WeOrganOrder weOrganOrder) {
		mongoTemplate.save(weOrganOrder);
		return new ReturnStatus(true);
	}

	@Override
	public WeOrganOrder queryWeOrganOrderById(String weOrganOrderId) {
		WeOrganOrder weOrganOrder=null;
		weOrganOrder=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(weOrganOrderId)), WeOrganOrder.class);
		if(weOrganOrder!=null){
			Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(weOrganOrder.getOrganId())), Organ.class);
			weOrganOrder.setOrganName(organ.getName());
			weOrganOrder.setOrganLogo(organ.getLogo());
			weOrganOrder.setOrganAddress(organ.getAddress());
			
			User user=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(weOrganOrder.getUserId())), User.class);
			if(user!=null){
				weOrganOrder.setUserNick(user.getNick());
				weOrganOrder.setUserImg(user.getAvatar());
				weOrganOrder.setUserPhone(user.getPhone());
			}
			Criteria criteria=new Criteria();
			//criteria.andOperator(Criteria.where("userId").is(weOrganOrder.getUserId()),Criteria.where("organId").is(weOrganOrder.getOrganId()),Criteria.where("staffId").is(weOrganOrder.getStaffId()));
			criteria.andOperator(Criteria.where("orderId").is(weOrganOrderId));
			WeOrganComment weOrganComment=mongoTemplate.findOne(Query.query(criteria), WeOrganComment.class);
			if(weOrganComment!=null){
				weOrganOrder.setContent(weOrganComment.getContent());
				weOrganOrder.setStarZan(weOrganComment.getStarZan());
			}
			List<Usercard> list = mongoTemplate.find(Query.query(Criteria.where("organId").is(weOrganOrder.getOrganId()).and("userId").is(weOrganOrder.getUserId())),Usercard.class );
			if (list.size()>0) {
				weOrganOrder.setIsMember(true);
			}
			
		}
		return weOrganOrder;
	}

	@Override
	public WeStaffCase queryWeStaffCaseById(String weStaffCaseId) {
		WeStaffCase weStaffCase=null;
		weStaffCase=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(weStaffCaseId)), WeStaffCase.class);
		return weStaffCase;
	}

	@Override
	public ReturnStatus saveWeStaffCase(WeStaffCase weStaffCase) {
		mongoTemplate.save(weStaffCase);
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<WeStaffCase> queryWeStaffCaseListByStaffId(String staffId,FlipInfo<WeStaffCase> fpi) {
		mongoTemplate.findByPage(Query.query(Criteria.where("staffId").is(staffId)), fpi, WeStaffCase.class);
		return fpi;
	}

	@Override
	public ReturnStatus saveWeUserFeedback(WeUserFeedback weUserFeedback) {
		mongoTemplate.save(weUserFeedback);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus saveUserFavor(String id, String type, String userId,
			String state) {
		User user=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
		List<String> list=null;
		if("1".equals(type)){//案例
			 list=user.getFavorCaseIds();
			 WeStaffCase wsc=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), WeStaffCase.class);
			 if("1".equals(state)){
					list.add(id); 
					wsc.setFollowCount(wsc.getFollowCount()+1);
				 }else{
					 for(int i=list.size()-1;i>=0;i--){
						 if(id.equals(list.get(i))){
							 list.remove(i);
							 user.setFavorCaseIds(list);
						 }
					 }
					 wsc.setFollowCount(wsc.getFollowCount()-1);
				 }
			 mongoTemplate.save(wsc);
		}else if("2".equals(type)){//技师
			list=user.getFavorStaffIds();
			Staff s=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Staff.class);
			if("1".equals(state)){
				list.add(id); 
				s.setFollowCount(s.getFollowCount()+1);
			 }else{
				 for(int i=list.size()-1;i>=0;i--){
					 if(id.equals(list.get(i))){
						 list.remove(i);
						 user.setFavorStaffIds(list);
					 }
				 }
				 s.setFollowCount(s.getFollowCount()-1);
			 }
			mongoTemplate.save(s);
			
		}else if("3".equals(type)){//店铺
			list=user.getFavorOrganIds();
			Organ organ=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Organ.class);
			if("1".equals(state)){
				list.add(id);
				organ.setFollowCount(organ.getFollowCount()+1);
			 }else{
				 for(int i=list.size()-1;i>=0;i--){
					 if(id.equals(list.get(i))){
						 list.remove(i);
						 user.setFavorOrganIds(list);
					 }
				 }
				 organ.setFollowCount(organ.getFollowCount()-1);
			 }
			mongoTemplate.save(organ);
		}
		
		mongoTemplate.save(user);
		return new ReturnStatus(true);
	}

	@Override
	public List<Code> queryTypeList(String type) {
		List<Code> codeList=mongoTemplate.find(Query.query(Criteria.where("type").is(type)), Code.class);
		return codeList;
	}

	@Override
	public ReturnStatus saveCode(Code code) {
		mongoTemplate.save(code);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus saveMessage(WeMessage weMessage) {
		mongoTemplate.save(weMessage);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus saveUser(User user) {
		mongoTemplate.save(user);
		return new ReturnStatus(true);
	}

	@Override
	public ReturnStatus saveUser(Map<String, Object> userInfo) {
		//添加用户信息
		User user=new User();
		user.setIdIfNew();
		String nick=(String)userInfo.get("nickname");
		if(!StringUtils.isEmpty(nick)){
			user.setNick(nick);
		}
		String avatar=(String)userInfo.get("headimgurl");
		if(!StringUtils.isEmpty(avatar)){
			user.setAvatar(avatar);
		}
		GpsPoint gpsPoint=(GpsPoint)userInfo.get("gpsPoint");
		if(gpsPoint!=null){
			user.setGpsPoint(gpsPoint);
		}
		user.setCreateTime(new Date());
		String invitor = String.valueOf(userInfo.get("invitor"));
		if(!StringUtils.isEmpty(invitor)) {
			user.setInvitor(invitor);
		}
		Date inviteDate =(Date)userInfo.get("inviteDate");
		if(inviteDate != null) {
			user.setInviteDate(inviteDate);
		}
		String accountTypeWithInviate = (String) userInfo.get("accountType");
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(accountTypeWithInviate)){
			user.setAccountType(accountTypeWithInviate);
		}

		mongoTemplate.save(user);
		// 绑定新的微信账号
		Account account = new Account();
		account.setIdIfNew();
		account.setCreateTimeIfNew();
		String openId=(String)userInfo.get("openid");
		if(!StringUtils.isEmpty(openId)){
			account.setAccountName(openId);
		}
		account.setAccountPwd(passwordEncoder.encodePassword(openId, null));
		account.setAccountType("user"); // 员工类型
		String unionId=(String)userInfo.get("unionid");
		if(!StringUtils.isEmpty(unionId)){
			account.setWeUnionId(unionId);
		}
		String unionid=(String)userInfo.get("unionid");
		if(StringUtils.isEmpty(unionid)){
			unionid="asdf";
		}
		account.setWeUnionId(unionid);
		account.setEntityID(user.get_id());
		account.setStatus("1");
		mongoTemplate.save(account);
		return new ReturnStatus(true);
	}
	
	@Override
	public Bigsort getBigsortById(String orderServiceId) {
		Bigsort bigsort = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderServiceId)), Bigsort.class);
		return bigsort;
	}


	@Override
	public ReturnStatus verifycode(String phone) {
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), Staff.class);
		if (staff != null) {
			if(staff.getOrganId()==null&&(staff.getWeOrganIds()==null||staff.getWeOrganIds().size()==0)){
				WeOrganStaffVerify weOrganStaffVerify = mongoTemplate.findOne(Query.query(Criteria.where("staffId").is(staff.get_id())), WeOrganStaffVerify.class);
				if(weOrganStaffVerify != null) {
					if(weOrganStaffVerify.getState() == 0) {
						return new ReturnStatus(false, "您申请的店铺还在审核中请耐心等待！");
					} 
				}
			}else if(staff.getOrganId()==null&&!(staff.getWeOrganIds()==null||staff.getWeOrganIds().size()==0)){
				return new ReturnStatus(false, "您已经申请店铺请直接进入任性猫！");
			}else{
				return new ReturnStatus(false, "您的手机号已经存在，请直接进入任性猫验证");
			}
		}
		return accountService.verifycode(phone);
	}

	@Override
	public ReturnStatus verify(String openId, String unionId, String phone,
			String code, String logo, String nick,String sex, GpsPoint gpsPoint,
			String organId,String organName) {
		
		
		ReturnStatus status=null;
		Staff staff=null;
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("accountType").is("staff"), Criteria.where("accountName").is(openId));
		Organ organ = mongoTemplate.findById(organId, Organ.class);
		boolean exist = mongoTemplate.exists(Query.query(criteria), Account.class);
		if (exist) {
			status = accountService.verify(phone, code);
			staff=staffService.getStaff(openId);
			if (sex.equals("1")) {
				staff.setSex("男");
			}else if (sex.equals("2")) {
				staff.setSex("女");
			}
			staff.setName(nick);
			staff.setCreateTimeIfNew();
			staff.setPhone(phone);
			//staff.setState(1);
			staff.setNick(nick);
			if (organ !=null) {
				GpsPoint organGps = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
				staff.setGpsPoint(organGps);
			}else {
				staff.setGpsPoint(gpsPoint);
			}
			mongoTemplate.save(staff);
		}else{
			 status = accountService.verify(phone, code);
			 if (status.isSuccess()) {
				//添加技师信息
				staff=new Staff();
				staff.setIdIfNew();
				if (sex.equals("1")) {
					staff.setSex("男");
				}else if (sex.equals("2")) {
					staff.setSex("女");
				}
				staff.setName(nick);
				staff.setPhone(phone);
				staff.setCreateTimeIfNew();
				//staff.setState(1);
				staff.setNick(nick);
				if (organ !=null) {
					GpsPoint organGps = PositionUtil.gcj02_To_Bd09(organ.getGpsPoint().getLatitude(), organ.getGpsPoint().getLongitude());
					staff.setGpsPoint(organGps);
				}else {
					staff.setGpsPoint(gpsPoint);
				}
				mongoTemplate.save(staff);
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

		}
		//将技师申请的店铺信息添加到审核表
		WeOrganStaffVerify verify=new WeOrganStaffVerify();
		verify.setIdIfNew();
		verify.setOrganId(organId);
		verify.setOrganName(organName);
		verify.setStaffId(staff.get_id());
		verify.setStaffName(staff.getNick());
		verify.setState(0);
		verify.setCreateTimeIfNew();
		mongoTemplate.save(verify);
		
		return status;
	}

	@Override
	public FlipInfo<Organ> searchOrgan(FlipInfo<Organ> fpi) {
		fpi=mongoTemplate.findByPage(null, fpi, Organ.class);
		return fpi;
	}

	
	@Override
	public InputStream returnBitMap(String path) {
		URL url = null;  
        InputStream is =null;  
        try {  
            url = new URL(path);  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        }  
        try {  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.   
            conn.setDoInput(true);  
            conn.connect();  
            is = conn.getInputStream(); //得到网络返回的输入流    
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return is;  
	}
	
	@Override
	public String downImg(InputStream in, HttpServletRequest request) {
		String ossId = Entity.getLongUUID()+".png";
		String url=request.getSession().getServletContext().getRealPath("")+"/module/resources/down";
		File file = new File(url);
		if(!file.exists()){
			file.mkdir();
		}
		try {
			File imgFile = new File(url,ossId);;
			FileOutputStream  out=new FileOutputStream (imgFile);
			byte[] temp=new byte[1024];
			int size=0;
			while((size =in.read(temp))!=-1){
				out.write(temp,  0, size);;
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ossId;
	}
	
	@Override
	public Map<String, String> wgs84ToBaiDu(String x, String y)
			throws IOException {
		URL url = new URL("http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=" + x + "&y=" + y);
		URLConnection connection = url.openConnection();

		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "utf-8");

		out.flush();
		out.close();
		// 服务器的回应的字串，并解析
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			if (!sCurrentLine.equals(""))
				sTotalString += sCurrentLine;
		}
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(sTotalString);
		sTotalString = sTotalString.substring(1, sTotalString.length() - 1);
		System.out.println(sTotalString);
		String[] results = sTotalString.split("\\,");
		if (results.length == 3) {
			if (results[0].split("\\:")[1].equals("0")) {
				String mapX = results[1].split("\\:")[1];
				String mapY = results[2].split("\\:")[1];
				mapX = mapX.substring(1, mapX.length() - 1);
				mapY = mapY.substring(1, mapY.length() - 1);
				mapX = new String(Base64.decode(mapX));
				mapY = new String(Base64.decode(mapY));
				map.put("longitude", mapX);
				map.put("latitude", mapY);
				System.out.println(mapX);
				System.out.println(mapY);
			} else {
				System.out.println("error != 0");
			}
		} else {
			System.out.println("String invalid!");
		}
		return map;
	}

	@Override
	public WeOrganOrder queryWeOrganOrder(String orderId) {
		WeOrganOrder order=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(orderId)), WeOrganOrder.class);
		return order;
	}
	

	@Override
	public ReturnStatus save(SensitiveWords word) {
		mongoTemplate.save(word);
		return new ReturnStatus(true);
	}


	@Override
	public ReturnStatus checkComment(String comment) {
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(comment);
		comment= m.replaceAll("").trim();
		for(SensitiveWords word:sensitiveWords){
			if(comment.indexOf(word.getWord())!=-1){
				return new ReturnStatus(false,"包含特殊字符");
			}
		}
		return new ReturnStatus(true);
	}
	public static List<SensitiveWords> sensitiveWords =new ArrayList<SensitiveWords>();
	@Override
	public List<SensitiveWords> querySensitiveWords() {
		List<SensitiveWords> list = mongoTemplate.find(Query.query(Criteria.where("del_flag").is(false)), SensitiveWords.class);
		if(list==null||list.size()==0){
			try {
				Set<String> keyWordSet = readSensitiveWordFile();
				Iterator i = keyWordSet.iterator();//先迭代出来
				while(i.hasNext()){//遍历  
		            //System.out.println(i.next());  
					SensitiveWords word = new SensitiveWords();
					word.setIdIfNew();
					word.setWord((String)i.next());
					word.setDel_flag(false);
					mongoTemplate.save(word);
		        } 
			} catch (Exception e) {
				e.printStackTrace();
			}
			list = mongoTemplate.find(Query.query(Criteria.where("del_flag").is(false)), SensitiveWords.class);
		}
		for(SensitiveWords word:list){
			sensitiveWords.add(word);
		}
		return list;
	}
	
	
	@Override
	public WeBCity findCityIdByCity(String city) {
		WeBCity weBCity  = mongoTemplate.findOne(Query.query(Criteria.where("name").is(city)), WeBCity.class);
		return weBCity;
	}

	@Override
	public int getTime() {
		List<TimeCreate> timeCreates = mongoTemplate.find(null, TimeCreate.class);
		if(timeCreates.size() == 0){
			TimeCreate timeCreate = new TimeCreate();
			timeCreate.setTime(0);
			timeCreate.setCreateTime(new Date());
			mongoTemplate.save(timeCreate);
			return  0;
		}else{
			TimeCreate timeCreate1 = timeCreates.get(0);
			timeCreate1.setTime(timeCreate1.getTime()+1);
			timeCreate1.setCreateTime(new Date());
			mongoTemplate.save(timeCreate1);
			return timeCreate1.getTime();
		}

	}

	private Set<String> readSensitiveWordFile() throws Exception{
		Set<String> set = null;
		String URL=WeCommonServiceImpl.class.getClassLoader().getResource("CensorWords.txt").getPath();
		File file = new File(URL);    //读取文件
		InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
		try {
			if(file.isFile() && file.exists()){      //文件流是否存在
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
					set.add(txt);
			    }
			}
			else{         //不存在抛出异常信息
				throw new Exception("敏感词库文件不存在");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();     //关闭文件流
		}
		return set;
	}
}
