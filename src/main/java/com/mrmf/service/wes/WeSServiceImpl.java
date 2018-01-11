package com.mrmf.service.wes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.WeBCity;
import com.mrmf.entity.WeBDistrict;
import com.mrmf.entity.WeBRegion;
import com.osg.entity.DataEntity;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("weSService")
public class WeSServiceImpl implements WeSService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public List<WeBCity> queryCity() throws BaseException {
		return mongoTemplate.find(Query.query(new Criteria()).with(new Sort(Direction.ASC, "order")), WeBCity.class);
	}

	@Override
	public List<WeBDistrict> queryDistrict(String cityId) throws BaseException {
		return mongoTemplate.find(
				Query.query(Criteria.where("cityId").is(cityId)).with(new Sort(Direction.ASC, "order")),
				WeBDistrict.class);
	}

	@Override
	public List<WeBDistrict> queryDistrictAll() throws BaseException {
		return mongoTemplate.find(new Query().with(new Sort(Direction.ASC, "order")), WeBDistrict.class);
	}

	@Override
	public List<WeBRegion> queryRegion(String districtId) throws BaseException {
		return mongoTemplate.find(
				Query.query(Criteria.where("districtId").is(districtId)).with(new Sort(Direction.ASC, "order")),
				WeBRegion.class);
	}

	@Override
	public List<DataEntity> findAll() throws BaseException {
		List<DataEntity> results = new ArrayList<>();
		results.addAll(
				mongoTemplate.find(Query.query(new Criteria()).with(new Sort(Direction.ASC, "order")), WeBCity.class));
		results.addAll(mongoTemplate.find(Query.query(new Criteria()).with(new Sort(Direction.ASC, "order")),
				WeBDistrict.class));
		results.addAll(mongoTemplate.find(Query.query(new Criteria()).with(new Sort(Direction.ASC, "order")),
				WeBRegion.class));
		return results;
	}

	@Override
	public WeBCity queryCityById(String cityId) throws BaseException {
		return mongoTemplate.findById(cityId, WeBCity.class);
	}

	@Override
	public WeBDistrict queryDistrictById(String districtId) throws BaseException {
		return mongoTemplate.findById(districtId, WeBDistrict.class);
	}

	@Override
	public WeBRegion queryRegionById(String regionId) throws BaseException {
		return mongoTemplate.findById(regionId, WeBRegion.class);
	}

	@Override
	public void upsertCity(WeBCity city) throws BaseException {
		if (StringUtils.isEmpty(city.get_id())) { // 新增
			city.setIdIfNew();
			city.setCreateTimeIfNew();
		}
		mongoTemplate.save(city);
	}

	@Override
	public void upsertDistrict(WeBDistrict district) throws BaseException {
		if (StringUtils.isEmpty(district.get_id())) { // 新增
			district.setIdIfNew();
			district.setCreateTimeIfNew();
		}
		mongoTemplate.save(district);
	}

	@Override
	public void upsertRegion(WeBRegion region) throws BaseException {
		if (StringUtils.isEmpty(region.get_id())) { // 新增
			region.setIdIfNew();
			region.setCreateTimeIfNew();
		}
		mongoTemplate.save(region);
	}

}
