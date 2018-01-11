package com.mrmf.service.role;

import java.util.ArrayList;
import java.util.List;

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

@Service("role2Service")
public class Role2ServiceImpl implements Role2Service {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Role2> query(FlipInfo<Role2> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, Role2.class);
		return fpi;
	}

	@Override
	public Role2 queryById(String roleId) throws BaseException {
		Role2 role = mongoTemplate.findById(roleId, Role2.class);
		if (role == null)
			throw new BaseException("指定id的角色信息不存在");
		else {
			getDetailInfo(role);
			return role;
		}
	}

	private void getDetailInfo(Role2 role) {
		List<String> functionIds = role.getFunctionIds();
		if (functionIds.size() > 0) {
			List<Function2> functionList = mongoTemplate.find(Query.query(Criteria.where("_id").in(functionIds)),
					Function2.class);
			List<String> functionNames = new ArrayList<>();
			for (Function2 function : functionList) {
				functionNames.add(function.getName());
			}
			role.setFunctionNames(functionNames);
		}
	}

	@Override
	public ReturnStatus upsert(Role2 role) {
		// String id = role.get_id();
		role.setIdIfNew();
		role.setCreateTimeIfNew();

		// 清空非存储字段
		role.setFunctionNames(null);

		mongoTemplate.save(role);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(role);
		return status;
	}

	@Override
	public List<Role2> queryAll() throws BaseException {
		return mongoTemplate.findAll(Role2.class);
	}

}
