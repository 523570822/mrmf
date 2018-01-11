package com.mrmf.service.role;

import java.util.ArrayList;
import java.util.List;

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

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Role> query(FlipInfo<Role> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, Role.class);
		return fpi;
	}

	@Override
	public Role queryById(String roleId) throws BaseException {
		Role role = mongoTemplate.findById(roleId, Role.class);
		if (role == null)
			throw new BaseException("指定id的角色信息不存在");
		else {
			getDetailInfo(role);
			return role;
		}
	}

	private void getDetailInfo(Role role) {
		List<String> functionIds = role.getFunctionIds();
		if (functionIds.size() > 0) {
			List<Function> functionList = mongoTemplate.find(Query.query(Criteria.where("_id").in(functionIds)),
					Function.class);
			List<String> functionNames = new ArrayList<>();
			for (Function function : functionList) {
				functionNames.add(function.getName());
			}
			role.setFunctionNames(functionNames);
		}
	}

	@Override
	public ReturnStatus upsert(Role role) {
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
	public List<Role> queryByOrganId(String organId) throws BaseException {
		return mongoTemplate.find(Query.query(Criteria.where("organId").is(organId)), Role.class);
	}

}
