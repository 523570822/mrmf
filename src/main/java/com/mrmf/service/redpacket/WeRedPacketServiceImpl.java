package com.mrmf.service.redpacket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.WeRed;
import com.mrmf.entity.WeRedRecord;
import com.osg.entity.FlipInfo;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("WeRedPacketService")
public class WeRedPacketServiceImpl implements WeRedPacketService {
	@Override
	public FlipInfo<WeRedRecord> findWeRedRecords(FlipInfo<WeRedRecord> fpi) {
		//Query query = new Query();
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		/*query.addCriteria(Criteria.where("type").is(1));*/
		mongoTemplate.findByPage(null, fpi, WeRedRecord.class);
		return fpi;
	}
	@Autowired
	private EMongoTemplate mongoTemplate;
	/**
	 * 查询所有红包
	 */
	@Override
	public FlipInfo<WeRed> findWeReds(FlipInfo<WeRed> fpi) {
		Query query = new Query();
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		query.addCriteria(Criteria.where("type").is(1));
		mongoTemplate.findByPage(query, fpi, WeRed.class);
		return fpi;
	}
	/**
	 * 保存红包
	 */
	@Override
	public void saveRedPacket(WeRed weRed) {
		mongoTemplate.save(weRed);
	}
}
