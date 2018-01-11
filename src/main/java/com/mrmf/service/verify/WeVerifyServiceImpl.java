package com.mrmf.service.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeOrganComment;
import com.mrmf.entity.WeStaffCase;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.mongodb.EMongoTemplate;
@Service("weVerifyService")
public class WeVerifyServiceImpl implements WeVerifyService{
	@Autowired
	EMongoTemplate mongoTemplate;
	@Override
	public FlipInfo<WeStaffCase> queryWeStaffCase(FlipInfo<WeStaffCase> fpi) {
		fpi= mongoTemplate.findByPage(null, fpi, WeStaffCase.class);
		getDetails(fpi.getData());
		return fpi;
	}
	
	public List<WeStaffCase> getDetails(List<WeStaffCase> caseList) {
		List<String> staffIds = new ArrayList<String>();
		for (WeStaffCase weStaffCase : caseList) {
			if (!staffIds.contains(weStaffCase.getStaffId())) {
				staffIds.add(weStaffCase.getStaffId());
			}
		}

		if (staffIds.size() > 0) {
			Map<String, Staff> staffMap = new HashMap<String, Staff>();
			List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("_id").in(staffIds)), Staff.class);
			for (Staff staff : staffs) {
				staffMap.put(staff.get_id(), staff);
			}

			for (WeStaffCase staffCase : caseList) {
				Staff staff = staffMap.get(staffCase.getStaffId());
				if (staff != null) {
					staffCase.setStaffName(staff.getName());
				}
			}
		}
		return caseList;
	}

	@Override
	public ReturnStatus delStaffCase(String caseId) {
		WeStaffCase sc= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(caseId)), WeStaffCase.class);
		Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(sc.getStaffId())), Staff.class);
		mongoTemplate.remove(sc);
		List<WeStaffCase> cases = mongoTemplate.find(Query.query(Criteria.where("staffId").is(staff.get_id())), WeStaffCase.class);
		double minprice=999999999;
		if(cases!=null&&cases.size()>0){
			for(WeStaffCase c:cases){
				if(c.getPrice()<minprice){
					minprice=c.getPrice();
				}
			}
			staff.setStartPrice((int)minprice);
		}else{
			staff.setStartPrice(0);
		}
		mongoTemplate.save(staff);
		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<WeOrganComment> queryUserComment(
			FlipInfo<WeOrganComment> fpi) {
		fpi = mongoTemplate.findByPage(null, fpi, WeOrganComment.class);
		getCommentDetail(fpi.getData());
		return fpi;
	}
	public List<WeOrganComment> getCommentDetail(List<WeOrganComment> comments){
		//转换用户名称
		List<String> userIds = new ArrayList<String>();
		for(WeOrganComment c:comments){
			if(c.getUserId()!=null){
				if(!userIds.contains(c.getUserId())){
					userIds.add(c.getUserId());
				}
			}
		}
		if(userIds.size()>0){
			Map<String,User> users = new HashMap<String,User>();
			List<User> us = mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)), User.class);
			for(User u:us){
				users.put(u.get_id(), u);
			}
			for(WeOrganComment c:comments){
				User user = users.get(c.getUserId());
				c.setUserName(user.getNick());
			}
		}
		//转换店铺名称
		List<String> organIds = new ArrayList<String>();
		for(WeOrganComment c:comments){
			if(!StringUtils.isEmpty(c.getOrganId())){
				if(!organIds.contains(c.getOrganId())){
					organIds.add(c.getOrganId());
				}
			}
		}
		if(organIds.size()>0){
			Map<String,Organ> organs = new HashMap<String,Organ>();
			List<Organ> os = mongoTemplate.find(Query.query(Criteria.where("_id").in(organIds)), Organ.class);
			for(Organ o:os){
				organs.put(o.get_id(), o);
			}
			for(WeOrganComment wc:comments){
				if(!StringUtils.isEmpty(wc.getOrganId())){
					Organ organ = organs.get(wc.getOrganId());
					if(organ!=null){
						wc.setOrganName(organ.getName());
					}
				}
			}
		}
		//转换技师的名字
		List<String> staffIds = new ArrayList<String>();
		for(WeOrganComment wc:comments){
			if(!StringUtils.isEmpty(wc.getStaffId())){
				if(!staffIds.contains(wc.getStaffId())){
					staffIds.add(wc.getStaffId());
				}
			}
		}
		if(staffIds.size()>0){
			Map<String,Staff> staffMap = new HashMap<String,Staff>();
			List<Staff> staffs = mongoTemplate.find(Query.query(Criteria.where("_id").in(staffIds)), Staff.class);
			for(Staff staff:staffs){
				staffMap.put(staff.get_id(), staff);
			}
			for(WeOrganComment wc:comments){
				if(!StringUtils.isEmpty(wc.getStaffId())){
					Staff staff = staffMap.get(wc.getStaffId());
					if(staff!=null){
						wc.setStaffName(staff.getName());
					}
				}
			}
		}
		return comments;
	}

	@Override
	public ReturnStatus delComment(String commentId) {
		//mongoTemplate.remove(Query.query(Criteria.where("_id").is(commentId)), WeOrganComment.class);
		WeOrganComment wc = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(commentId)), WeOrganComment.class);
		mongoTemplate.save(wc,"weOrganCommentSecret");
		mongoTemplate.remove(wc);
		return new ReturnStatus(true);
	}

	@Override
	public WeStaffCase queryCaseById(String caseId) {
		WeStaffCase staffCase = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(caseId)), WeStaffCase.class);
		if(staffCase!=null){
			Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(staffCase.getStaffId())), Staff.class);
			if(staff!=null){
				staffCase.setStaffName(staff.getName());
			}
		}
		return staffCase;
	}

	@Override
	public ReturnStatus delcaseImg(String _id, String img) {
		ReturnStatus returnStatus = new ReturnStatus(true);
		WeStaffCase weStaffCase = mongoTemplate.findById(_id, WeStaffCase.class);
		List<String> logos = weStaffCase.getLogo();
		List<String> logosTemp = new ArrayList<String>();
		for (String logo : logos) {
			if(logo.equals(img)) {
				continue;
			}
			logosTemp.add(logo);
		}
		weStaffCase.setLogo(logosTemp);
		mongoTemplate.save(weStaffCase);
		returnStatus.setData(logosTemp);
		return returnStatus;
	}

	@Override
	public ReturnStatus setCaseTopImg(String _id, String img) {
		ReturnStatus returnStatus = new ReturnStatus(true);
		WeStaffCase weStaffCase = mongoTemplate.findById(_id, WeStaffCase.class);
		List<String> logos = weStaffCase.getLogo();
		List<String> logosTemp = new ArrayList<String>();
		logosTemp.add(img);
		for (String logo : logos) {
			if(logo.equals(img)) {
				continue;
			}
			logosTemp.add(logo);
		}
		weStaffCase.setLogo(logosTemp);
		mongoTemplate.save(weStaffCase);
		returnStatus.setData(logosTemp);
		return returnStatus;
	}
}
