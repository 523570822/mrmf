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

import com.mrmf.entity.Function;
import com.mrmf.entity.Role;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public List<Function> query() throws BaseException {
		return mongoTemplate.findAll(Function.class);
	}

	@Override
	public FlipInfo<Function> query(FlipInfo<Function> fpi) throws BaseException {
		fpi.setSortField("order");
		fpi.setSortOrder("ASC");
		mongoTemplate.findByPage(null, fpi, Function.class);
		return fpi;
	}

	@Override
	public Function queryById(String functionId) throws BaseException {
		Function function = mongoTemplate.findById(functionId, Function.class);
		if (function == null)
			throw new BaseException("指定id的菜单/资源信息不存在");
		else
			return function;
	}

	@Override
	public ReturnStatus upsert(Function function) {
		// String id = function.get_id();
		function.setIdIfNew();
		function.setCreateTimeIfNew();

		mongoTemplate.save(function);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(function);
		return status;
	}

	@Override
	public List<Function> getFunctionMenu(List<String> roleIds) throws BaseException {
		List<Function> result = new ArrayList<>();
		if (roleIds.size() > 0) {
			List<Role> roleList = mongoTemplate.find(Query.query(Criteria.where("_id").in(roleIds)), Role.class);
			List<String> functionIds = new ArrayList<>();
			for (Role role : roleList) {
				List<String> fids = role.getFunctionIds();
				for (String fid : fids) {
					if (!functionIds.contains(fid)) {
						functionIds.add(fid);
					}
				}
			}

			List<Function> allFunc = mongoTemplate.findAll(Function.class);
			Map<String, Function> allFuncMap = new HashMap<>();
			for (Function f : allFunc) {
				allFuncMap.put(f.get_id(), f);
			}

			for (String fid : functionIds) {
				Function func = allFuncMap.get(fid);
				if (func != null) {
					if ("0".equals(func.getParentId())) {
						if (!result.contains(func)) {
							result.add(func);
						}
					} else {
						Function parentFunc = allFuncMap.get(func.getParentId());
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
		Collections.sort(result, new Comparator<Function>() {
			@Override
			public int compare(Function o1, Function o2) {
				if (o1.getOrder() == o2.getOrder())
					return 0;
				else
					return o1.getOrder() < o2.getOrder() ? -1 : 1;
			}
		});

		return result;
	}

}
