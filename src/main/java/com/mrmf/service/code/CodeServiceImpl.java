package com.mrmf.service.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Code;
import com.mrmf.service.common.PinYinUtil;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;

@Service("codeService")
public class CodeServiceImpl implements CodeService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public List<Code> queryByType(String type) throws BaseException {

		return mongoTemplate.find(
				Query.query(Criteria.where("type").is(type)).with(new Sort(Direction.ASC, "orderCode")), Code.class);
	}

	@Override
	public FlipInfo<Code> query(FlipInfo<Code> fpi) throws BaseException {
		fpi.setSortField("orderCode");
		fpi.setSortOrder("DESC");
		mongoTemplate.findByPage(null, fpi, Code.class);
		return fpi;
	}

	@Override
	public Code queryById(String codeId) throws BaseException {
		Code code = mongoTemplate.findById(codeId, Code.class);
		if (code == null)
			throw new BaseException("指定id的代码信息不存在");
		else
			return code;
	}

	@Override
	public ReturnStatus upsert(Code code) {
		code.setIdIfNew();
		code.setCreateTimeIfNew();

		code.setZjfCode(PinYinUtil.getFirstSpell(code.getName()));
		mongoTemplate.save(code);
		ReturnStatus status = new ReturnStatus(true);
		status.setData(code);
		return status;
	}

	@Override
	public ReturnStatus remove(String codeId) {
		mongoTemplate.remove(Query.query(Criteria.where("_id").is(codeId)), Code.class);
		return new ReturnStatus(true);
	}

}
