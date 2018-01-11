package com.mrmf.service.filtercomment;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.entity.WeOrganComment;
import com.osg.entity.FlipInfo;
import com.osg.framework.mongodb.EMongoTemplate;
@Service("filterCommentService")
public class FilterCommentServiceImpl implements FilterCommentService{
	@Autowired
	private EMongoTemplate mongoTemplate;
	@Override
	public FlipInfo<WeOrganComment> queryFilterComment(
			FlipInfo<WeOrganComment> fpi) {
		fpi=mongoTemplate.findByPage(null, fpi, WeOrganComment.class,"weOrganCommentSecret");
		for(WeOrganComment fwoc:fpi.getData()){
			if(!StringUtils.isEmpty(fwoc.getUserId())){
				User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(fwoc.getUserId())), User.class);
				if(!StringUtils.isEmpty(user.getName()))
					fwoc.setUserName(user.getName());
				else
					fwoc.setUserName(user.getNick());
			}
			if(!StringUtils.isEmpty(fwoc.getOrganId())){
				Organ organ = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(fwoc.getOrganId())), Organ.class);
				fwoc.setOrganName(organ.getName());
			}
			if(!StringUtils.isEmpty(fwoc.getStaffId())){
				Staff staff = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(fwoc.getStaffId())), Staff.class);
				fwoc.setStaffName(staff.getName());
			}
		}
		return fpi;
	}

}
