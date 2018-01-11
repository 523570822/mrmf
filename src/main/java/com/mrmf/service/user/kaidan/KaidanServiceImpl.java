package com.mrmf.service.user.kaidan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.entity.user.Kaidan;
import com.mrmf.entity.user.Userpart;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.StringUtils;

@Service("kaidanService")
public class KaidanServiceImpl implements KaidanService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public List<Kaidan> query(final String organId, final String kaidan_id) throws BaseException {

		List<Criteria> cs = new ArrayList<Criteria>() {
			{
				add(Criteria.where("organId").is(organId));
				add(Criteria.where("guazhang_flag").is(true));
				if (!StringUtils.isEmpty(kaidan_id)) {
					add(Criteria.where("kaidan_id").regex(kaidan_id));
				}
			}
		};

		Criteria criteria = new Criteria();
		criteria.andOperator(cs.toArray(new Criteria[cs.size()]));
		List<Kaidan> result = new ArrayList<Kaidan>();
		List<Kaidan> klist = mongoTemplate.find(Query.query(criteria), Kaidan.class);
		for (Kaidan k : klist) {
			if (!DateUtil.format(DateUtil.currentDate()).equals(DateUtil.format(k.getCreateTime()))
					&& !mongoTemplate.exists(Query.query(Criteria.where("kaidanId").is(k.get_id())), Userpart.class)) {
				// 非当天单号并且没有消费记录的，以及没有外卖记录的，则删除单号
				criteria = new Criteria();
				criteria.andOperator(Criteria.where("kaidanId").is(k.get_id()),
						Criteria.where("delete_flag").is(false));
				// 没有外卖记录的删除
				if (!mongoTemplate.exists(Query.query(criteria), WWaimai.class)) {
					mongoTemplate.remove(k);
				}
			} else {
				result.add(k);
			}
		}
		return result;
	}

	@Override
	public ReturnStatus upsert(Kaidan kaidan) {
		ReturnStatus status = new ReturnStatus(true);
		if (StringUtils.isEmpty(kaidan.get_id())) { // 新建
			kaidan.setIdIfNew();
			kaidan.setCreateTimeIfNew();
			kaidan.setGuazhang_flag(true);
			if(!StringUtils.isEmpty(kaidan.getKaidan_id())){
				Kaidan kd = mongoTemplate.findOne(Query.query(Criteria.where("kaidan_id").is(kaidan.getKaidan_id())
						.and("organId").is(kaidan.getOrganId())),Kaidan.class);
				if(kd!=null){
					status.setSuccess(false);
					status.setMessage("已存在该单号");
					return status;
				}
			}else {
				// 根据规则生成开单id
				Criteria criteria = new Criteria();
				criteria.andOperator(Criteria.where("organId").is(kaidan.getOrganId()),
						Criteria.where("createTime").gte(DateUtil.getZhengdianDate(DateUtil.currentDate())));
				List<Kaidan> kaidanList = mongoTemplate.find(Query.query(criteria), Kaidan.class);
				int num = 0;
				for (Kaidan k : kaidanList) {
					int n = Integer.parseInt(k.getKaidan_id());
					if (n > num)
						num = n;
				}
				num++;
				String ns = "" + num;
				while (ns.length() < 3) {
					ns = "0" + ns;
				}
				kaidan.setKaidan_id(ns);
			}

		}
		mongoTemplate.save(kaidan);
		status.setData(kaidan);
		return status;
	}

	@Override
	public ReturnStatus remove(String organId, String id) {
		Kaidan kaidan = mongoTemplate.findById(id, Kaidan.class);
		if (kaidan == null) {
			return new ReturnStatus(false, "指定id的开单信息不存在!");
		} else if (!organId.equals(kaidan.getOrganId())) {
			return new ReturnStatus(false, "指定开单信息不属于此公司!");
		} else {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("kaidanId").is(id), Criteria.where("delete_flag").is(false));
			if (mongoTemplate.count(Query.query(criteria), Userpart.class) > 0) {
				return new ReturnStatus(false, "改单号下面还有未结账的消费信息!");
			} else {
				mongoTemplate.remove(kaidan);
				return new ReturnStatus(true);
			}
		}
	}

	@Override
	public ReturnStatus union(String organId, String[] ids) {
		if (ids == null || ids.length < 2) {
			return new ReturnStatus(false, "请选择多个要合并的单号!");
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("_id").in(ids));
		List<Kaidan> kaidanList = mongoTemplate.find(Query.query(criteria), Kaidan.class);
		Collections.sort(kaidanList, new Comparator<Kaidan>() {

			@Override
			public int compare(Kaidan o1, Kaidan o2) {
				String k1 = o1.getKaidan_id();
				String k2 = o2.getKaidan_id();
				return Integer.parseInt(k1) - Integer.parseInt(k2);
			}
		});
		Kaidan kaidan = kaidanList.get(0);

		criteria = new Criteria();
		criteria.andOperator(Criteria.where("organId").is(organId), Criteria.where("kaidanId").in(ids));
		List<Userpart> userpartList = mongoTemplate.find(Query.query(criteria), Userpart.class);
		for (Userpart userpart : userpartList) {
			if (!kaidan.get_id().equals(userpart.getKaidanId())) {
				userpart.setKaidanId(kaidan.get_id());
				mongoTemplate.save(userpart);
			}
		}

		return new ReturnStatus(true);
	}

	@Override
	public Kaidan queryById(String kaidanId) throws BaseException {
		return mongoTemplate.findById(kaidanId, Kaidan.class);
	}

}
