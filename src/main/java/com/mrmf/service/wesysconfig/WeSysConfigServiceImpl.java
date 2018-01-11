package com.mrmf.service.wesysconfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.osg.framework.util.DateUtil;



import com.mrmf.entity.WeCarousel;
import com.mrmf.entity.WeSysConfig;
import com.mrmf.entity.WeUserCompensate;
import com.mrmf.entity.WeUserFeedback;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("weSysConfigService")
public class WeSysConfigServiceImpl implements WeSysConfigService {
	@Autowired
	private EMongoTemplate mongoTemplate;
	@Override
	public WeSysConfig query() throws BaseException {
		WeSysConfig config = mongoTemplate.findOne(new Query(), WeSysConfig.class);
		if (config == null) {
			config = new WeSysConfig();
			config.setIdIfNew();
			config.setAllzhekou(50);
			config.setMan1(30);
			config.setFan1(10);
			config.setMan2(50);
			config.setFan2(20);
			config.setMan3(100);
			config.setFan3(35);
			//config.setAllfandian(0.35);
			mongoTemplate.save(config);
		}
		return config;
	}
	@Override
	public ReturnStatus upsert(WeSysConfig config) {
		ReturnStatus status;
		if (config == null || StringUtils.isEmpty(config.get_id())) {
			status = new ReturnStatus(false, "参数错误！");
		} else {
			if (mongoTemplate.exists(Query.query(Criteria.where("_id").is(config.get_id())), WeSysConfig.class)) {
				mongoTemplate.save(config);
				status = new ReturnStatus(true);
			} else {
				status = new ReturnStatus(false, "数据错误！");
			}
		}
		return status;
	}
	
	@Override
	public Boolean addCarouselImg(String imgId) {
		if(!StringUtils.isEmpty(imgId)) {
			WeCarousel weCarousel= new WeCarousel();
			weCarousel.setNewId();
			weCarousel.setImg(imgId);
			weCarousel.setFlag(false);
			weCarousel.setCreateTimeIfNew();
			mongoTemplate.save(weCarousel);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public FlipInfo<WeCarousel> queryCarouselImg(FlipInfo<WeCarousel> fpi) {
		Query query = new Query();
		query.addCriteria(Criteria.where("flag").is(false));
		fpi.setSortField("createTime");
		fpi.setSortField("DESC");
		return mongoTemplate.findByPage(query, fpi, WeCarousel.class);
	}

	@Override
	public void deleteCarouselImg(String carouselId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(carouselId));
		Update update = new Update();
		update.set("flag", true);
		mongoTemplate.updateFirst(query, update, WeCarousel.class);
	}

	@Override
	public FlipInfo<WeUserFeedback> findFeedBacks(
			FlipInfo<WeUserFeedback> feedBacks) {
		feedBacks.setSortField("createTime");
		feedBacks.setSortOrder("DESC");
		return mongoTemplate.findByPage(null, feedBacks, WeUserFeedback.class);
	}
	@Override
	public FlipInfo<WeUserCompensate> findCompensates(
			FlipInfo<WeUserCompensate> compensateFiFlipInfo) {
		compensateFiFlipInfo.setSortField("createTime");
		compensateFiFlipInfo.setSortOrder("DESC");
		return mongoTemplate.findByPage(null, compensateFiFlipInfo, WeUserCompensate.class);
	}
	@Override
	public WeUserCompensate findCompensateById(String compensateId) {
		return mongoTemplate.findById(compensateId, WeUserCompensate.class);
	}
	@Override
	public void saveCompensate(String compensateId, String result,
			String resultDesc) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(compensateId));
		Update update = new Update();
		update.set("result", result);
		update.set("resultDesc", resultDesc);
		update.set("state", 1);
		mongoTemplate.updateFirst(query, update, WeUserCompensate.class);
	}
	@Override
	public List<WeUserFeedback> exportUserFeed(String userName, Date startTime, Date endTime,String type) {
		List<Criteria> criterias=new ArrayList<Criteria>();
		criterias.add(Criteria.where("type").is(type));
		if(!StringUtils.isEmpty(userName)){
				criterias.add(Criteria.where("userName").regex(userName));
		}
		if(startTime!=null){
			criterias.add(Criteria.where("createTime").gte(startTime));
		}
		if(endTime!=null){
			criterias.add(Criteria.where("createTime").lte(DateUtil.addDate(endTime, 1)));
		}
		
		Criteria criteria=new Criteria();
		if(criterias.size()>0){
			criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		}
		
		return mongoTemplate.find(Query.query(criteria), WeUserFeedback.class);
	}

	
}
