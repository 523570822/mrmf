package com.mrmf.service.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Function2;
import com.mrmf.entity.Role2;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("function2Service")
public class Function2ServiceImpl implements Function2Service {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public List<Function2> query() throws BaseException {
		return mongoTemplate.findAll(Function2.class);
	}

	@Override
	public FlipInfo<Function2> query(FlipInfo<Function2> fpi) throws BaseException {
		fpi.setSortField("order");
		fpi.setSortOrder("ASC");
		mongoTemplate.findByPage(null, fpi, Function2.class);
		return fpi;
	}

	@Override
	public Function2 queryById(String functionId) throws BaseException {
		Function2 function = mongoTemplate.findById(functionId, Function2.class);
		if (function == null)
			throw new BaseException("指定id的菜单/资源信息不存在");
		else
			return function;
	}

	@Override
	public ReturnStatus upsert(Function2 function) {
		// String id = function.get_id();
		function.setIdIfNew();
		function.setCreateTimeIfNew();

		mongoTemplate.save(function);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(function);
		return status;
	}

	@Override
	public List<Function2> getFunction2Menu(List<String> roleIds) throws BaseException {
		List<Function2> result = new ArrayList<>();
		if (roleIds.size() > 0) {
			List<Role2> roleList = mongoTemplate.find(Query.query(Criteria.where("_id").in(roleIds)), Role2.class);
			List<String> functionIds = new ArrayList<>();
			for (Role2 role : roleList) {
				List<String> fids = role.getFunctionIds();
				for (String fid : fids) {
					if (!functionIds.contains(fid)) {
						functionIds.add(fid);
					}
				}
			}

			List<Function2> allFunc = mongoTemplate.findAll(Function2.class);
			Map<String, Function2> allFuncMap = new HashMap<>();
			for (Function2 f : allFunc) {
				allFuncMap.put(f.get_id(), f);
			}

			for (String fid : functionIds) {
				Function2 func = allFuncMap.get(fid);
				if (func != null) {
					if ("0".equals(func.getParentId())) {
						if (!result.contains(func)) {
							result.add(func);
						}
					} else {
						Function2 parentFunc = allFuncMap.get(func.getParentId());
						if (!result.contains(parentFunc)) {
							result.add(parentFunc);
						}
						if (!parentFunc.getFunctionList().contains(func)) {
							parentFunc.getFunctionList().add(func);
						}

					}
				}
			}
		}

		// 菜单排序
		Collections.sort(result, new Comparator<Function2>() {
			@Override
			public int compare(Function2 o1, Function2 o2) {
				if (o1.getOrder() == o2.getOrder())
					return 0;
				else
					return o1.getOrder() < o2.getOrder() ? -1 : 1;
			}
		});

		return result;
	}

}
