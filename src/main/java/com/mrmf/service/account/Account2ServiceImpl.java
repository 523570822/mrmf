package com.mrmf.service.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Account;
import com.mrmf.entity.Role2;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.StringUtils;

@Service("account2Service")
public class Account2ServiceImpl implements Account2Service {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Override
	public FlipInfo<Account> query(FlipInfo<Account> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, Account.class);
		return fpi;
	}

	@Override
	public Account queryById(String accountId) throws BaseException {
		Account account = mongoTemplate.findById(accountId, Account.class);
		if (account == null)
			throw new BaseException("指定id的角色信息不存在");
		else {
			getDetailInfo(account);
			return account;
		}
	}

	private void getDetailInfo(Account account) {
		List<String> roleIds = account.getRoleIds();
		if (roleIds.size() > 0) {
			List<Role2> roleList = mongoTemplate.find(Query.query(Criteria.where("_id").in(roleIds)), Role2.class);
			List<String> roleNames = new ArrayList<>();
			for (Role2 role : roleList) {
				roleNames.add(role.getName());
			}
			account.setRoleNames(roleNames);
		}
	}

	@Override
	public ReturnStatus upsert(Account account) {
		ReturnStatus status;
		// 获取管理员口令
		String accountName = account.getAccountName();
		String passwd = account.getAccountPwd();
		account.setAccountPwd(null);
		if (StringUtils.isEmpty(account.get_id())) { // 新建
			account.setIdIfNew();
			account.setCreateTimeIfNew();

			long count = mongoTemplate.count(Query.query(Criteria.where("accountName").is(accountName)), Account.class);
			if (count > 0) {
				status = new ReturnStatus(false);
				status.setMessage("已存在同名的账号");
				return status;
			} else {
				account.setAccountPwd(passwordEncoder.encodePassword(passwd, null));
			}
		} else { // 修改
			Account oldAccount = mongoTemplate.findById(account.get_id(), Account.class);
			if (oldAccount != null) {
				// 修改公司管理员账号口令
				if (!StringUtils.isEmpty(passwd)) {
					account.setAccountPwd(passwordEncoder.encodePassword(passwd, null));
				} else {
					account.setAccountPwd(oldAccount.getAccountPwd());
				}
			} else {
				status = new ReturnStatus(false);
				status.setMessage("要修改的账号信息不存在");
				return status;
			}

		}
		// String id = role.get_id();

		// 清空非存储字段
		account.setRoleNames(null);

		mongoTemplate.save(account);
		status = new ReturnStatus(true);
		status.setData(account);
		return status;
	}

}
