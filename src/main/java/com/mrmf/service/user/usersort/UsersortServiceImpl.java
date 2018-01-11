package com.mrmf.service.user.usersort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Code;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usersort;
import com.mrmf.service.common.PinYinUtil;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("usersortService")
public class UsersortServiceImpl implements UsersortService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Usersort> query(FlipInfo<Usersort> fpi) throws BaseException {
		mongoTemplate.findByPage(Query.query(Criteria.where("delete_flag").ne(true)), fpi, Usersort.class);
		getDetails(fpi.getData());
		return fpi;
	}

	private void getDetails(List<Usersort> datas) {
		List<String> smallsortIds = new ArrayList<String>();
		List<String> usersortIds = new ArrayList<String>();
		for (Usersort usersort : datas) {
			if (!usersortIds.contains(usersort.getFlag1()))
				usersortIds.add(usersort.getFlag1());
			if (!StringUtils.isEmpty(usersort.getName2()) && !smallsortIds.contains(usersort.getName2())) {
				smallsortIds.add(usersort.getName2());
			}
		}

		Map<String, Code> usersortTypeMap = new HashMap<>();
		List<Code> bigsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(usersortIds)), Code.class);
		for (Code bigsort : bigsorts) {
			usersortTypeMap.put(bigsort.get_id(), bigsort);
		}

		Map<String, Smallsort> smallsortMap = new HashMap<>();
		if (smallsortIds.size() > 0) {
			List<Smallsort> smallsorts = mongoTemplate.find(Query.query(Criteria.where("_id").in(smallsortIds)),
					Smallsort.class);
			for (Smallsort smallsort : smallsorts) {
				smallsortMap.put(smallsort.get_id(), smallsort);
			}
		}

		for (Usersort usersort : datas) {
			Code code = usersortTypeMap.get(usersort.getFlag1());
			if (code != null)
				usersort.setFlag1Name(code.getName());

			if (!StringUtils.isEmpty(usersort.getName2())) {
				Smallsort smallsort = smallsortMap.get(usersort.getName2());
				if (smallsort != null) {
					usersort.setName2Name(smallsort.getName());
				}
			}
		}
	}

	@Override
	public Usersort queryById(String usersortId) throws BaseException {
		Usersort usersort = mongoTemplate.findById(usersortId, Usersort.class);
		if (usersort == null)
			throw new BaseException("指定id的会员类型信息不存在");
		else {
			return usersort;
		}
	}

	@Override
	public ReturnStatus upsert(Usersort usersort) {
		usersort.setIdIfNew();
		usersort.setCreateTimeIfNew();

		if (usersort.getCishu() > 0) {
			usersort.setDanci_money(usersort.getMoney() / usersort.getCishu());// 单次款额
		}

		if (usersort.getFlag_putong() != null && usersort.getFlag_putong()) {
			// 将其他会员类型设置为非默认
			List<Usersort> usersorts = mongoTemplate
					.find(Query.query(Criteria.where("organId").is(usersort.getOrganId())), Usersort.class);
			for (Usersort us : usersorts) {
				if (us.getFlag_putong() != null && us.getFlag_putong()) {
					us.setFlag_putong(false);
					mongoTemplate.save(us);
				}
			}

		}
		usersort.setZjfCode(PinYinUtil.getFirstSpell(usersort.getName1()));
		mongoTemplate.save(usersort);

		ReturnStatus status = new ReturnStatus(true);
		status.setData(usersort);
		return status;
	}

	@Override
	public List<Usersort> findAll(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId).and("delete_flag").ne(true)), Usersort.class);
	}

}
