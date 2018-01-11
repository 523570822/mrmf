package com.mrmf.service.vipuser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.User;
import com.mrmf.entity.WeMessage;
import com.mrmf.entity.user.Usercard;
import com.osg.entity.ReturnStatus;
import com.osg.framework.Constants;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.SMSUtil;
@Service("vipUserService")
public class VipUserServiceImpl implements VipUserService{
	@Autowired EMongoTemplate mongoTemplate;
	
	@Override
	public List<User> queryVipUser(Integer day,String organId) {
		if(day==null){
			day=0;
		}
		List<Usercard> usercards = mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), Usercard.class);
		List<String> cardUserids=new ArrayList<String>();
		List<User> userbirthday=new ArrayList<User>();
		for(Usercard usercard:usercards){
			cardUserids.add(usercard.getUserId());
		}
		List<User> users= mongoTemplate.find(Query.query(Criteria.where("_id").in(cardUserids)), User.class);
		for(User user:users){
			if(user.getBirthday()!=null){
			if(isBrithday(day,user.getBirthday())){
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(organId),Criteria.where("userId").is(user.get_id()));
				Usercard card = mongoTemplate.findOne(Query.query(criteria), Usercard.class);
				/*List<Userincard> incards = mongoTemplate.find(Query.query(Criteria.where("cardId").is(card.get_id())), Userincard.class);
				String vipType="";
				for(Userincard incard:incards){
					Usersort sort = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(incard.getMembersort())), Usersort.class);
					vipType+= sort.getName1()+",";
				}
				if(!StringUtils.isEmpty(vipType)){
					vipType=vipType.substring(0,vipType.length()-1);
				}
				user.setVipType(vipType);
				user.setVipNo(card.getCardno());
				userbirthday.add(user);*/
				user.setCreateTime(card.getCreateTime());
				userbirthday.add(user);
			}
			}
		}
		return userbirthday;
	}
	public boolean isBrithday(int day,Date birthday){
		Calendar cal=Calendar.getInstance();
		int nowday=cal.get(Calendar.DAY_OF_YEAR);//当天天数
		for(int i=0;i<=day;i++){
			cal.set(Calendar.DAY_OF_YEAR, nowday+i);
			SimpleDateFormat format=new SimpleDateFormat("MM-dd");
			String searchBrithday=format.format(cal.getTime());
			String userBrithday=format.format(birthday);
			if(searchBrithday.equals(userBrithday)){
				return true;
			}
		}
		/*cal.set(Calendar.DAY_OF_YEAR, nowday+day);
		SimpleDateFormat format=new SimpleDateFormat("MM-dd");
		String searchBrithday=format.format(cal.getTime());
		String userBrithday=format.format(birthday);
		if(searchBrithday.equals(userBrithday)){
			return true;
		}*/
		return false;
	}
	
	@Override
	public ReturnStatus sendMessage(List<User> users,String organId) {
		Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(organId)), Organ.class);
		for(User user:users){
			if(!StringUtils.isEmpty(user.getPhone())){
				/*Map<String, String> params = new HashMap<>();
				params.put("organName", organ.getName());
				ReturnStatus s = SMSUtil.send(user.getPhone(), Constants.getProperty("sms.vipUserBrithdayTemplate"),
					params,"sms.signNameBrithday");
				System.out.println(s);*/
				WeMessage message = new WeMessage();
				message.setIdIfNew();
				message.setFromType("rxm");
				message.setFromId("0");
				message.setFromName("任性猫平台");
				message.setToType("user");
				message.setToId(user.get_id());
				message.setToName(user.getNick());
				message.setType("1");
				message.setContent("任性猫恭祝：感谢您对“"+organ.getName()+"”的支持，祝您生日快乐，幸福一生。");
				message.setCreateTimeIfNew();
				message.setReadFalg(false);
				message.setCreateTimeFormat(DateUtil.getDateStr(new Date(), DateUtil.YMDHM_PATTERN));
				mongoTemplate.save(message);
			}
			
		}
		return new ReturnStatus(true);
	}
}
