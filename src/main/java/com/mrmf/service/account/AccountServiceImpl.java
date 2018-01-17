package com.mrmf.service.account;

import com.mrmf.entity.Account;
import com.mrmf.entity.Role;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.entity.VerifyCode;
import com.osg.framework.BaseException;
import com.osg.framework.Constants;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.SMSUtil;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EMongoTemplate mongoTemplate;

	public Account getAccount(String username, String password) {
		if (password != null)
			password = passwordEncoder.encodePassword(password, null);
		Query query = new Query();
		query.addCriteria(Criteria.where("accountName").is(username));
		if (null != password)
			query.addCriteria(Criteria.where("accountPwd").is(password));
		Account a = this.mongoTemplate.findOne(query, Account.class);
		if (a != null) {
			Update update = new Update();
			update.inc("loginTimes", 1);
			this.mongoTemplate.updateFirst(query, update, Account.class);
		}
		return a;
	}

	public String insertAccount(Account account) {
		String pwd = account.getAccountPwd();
		pwd = passwordEncoder.encodePassword(pwd, null);
		account.setAccountPwd(pwd);

		mongoTemplate.save(account);
		return account.get_id();
	}

	public ReturnStatus changePasswordByAccountName(String accountName, String password) {
		Account account = mongoTemplate.findOne(Query.query(Criteria.where("accountName").is(accountName)),
				Account.class);
		if (account == null) {
			ReturnStatus result = new ReturnStatus(ReturnStatus.FAILED);
			result.setMessage("指定账号不存在");
			return result;
		} else {
			String passwd = passwordEncoder.encodePassword(password, null);
			account.setAccountPwd(passwd);
			mongoTemplate.save(account);
			return new ReturnStatus(true);
		}
	}

	private ReturnStatus verifyResult(ReturnStatus status, String phone, String randomCode) {
		if (status.isSuccess()) {
			// 将之前待验证的验证码都置为无效
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("phone").is(phone), Criteria.where("status").is(0));
			List<VerifyCode> codes = mongoTemplate.find(Query.query(criteria), VerifyCode.class);
			for (VerifyCode code : codes) {
				code.setStatus(-1);
				mongoTemplate.save(code);
			}

			// 存储新的验证码信息
			VerifyCode vc = new VerifyCode();
			vc.setPhone(phone);
			vc.setCode(randomCode);
			vc.setStatus(0);
			vc.setCreateTimeIfNew();
			mongoTemplate.insert(vc);
		}

		return status;
	}

	@Override
	public ReturnStatus verifycodeVoice(String phone) {
		Random random = new Random();
		String randomCode = String.valueOf(Math.abs(random.nextInt())).substring(0, 4);
		Map<String, String> params = new HashMap<>();
		params.put("code", randomCode);
		ReturnStatus status = SMSUtil.sendVoice(phone, Constants.getProperty("sms.verifyCodeVoiceTemplate"), params);

		return verifyResult(status, phone, randomCode);
	}

	@Override
	public ReturnStatus verifycode(String phone) {
		Random random = new Random();
		String randomCode = String.valueOf(Math.abs(random.nextInt())).substring(0, 4);
		Map<String, String> params = new HashMap<>();
		params.put("code", randomCode);
		params.put("product", "任性猫");
		ReturnStatus status = SMSUtil.send(phone, Constants.getProperty("sms.verifyCodeTemplate"), params);



		return verifyResult(status, phone, randomCode);
	}

	@Override
	public ReturnStatus verify(String phone, String code) {
		// 验证码验证，status为0未验证的验证码
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("phone").is(phone), Criteria.where("status").is(0));

		boolean validCode = false;
		long expiredIn = Long.parseLong(Constants.getProperty("sms.expiredin"));
		Date current = DateUtil.currentDate();
		List<VerifyCode> codes = mongoTemplate.find(Query.query(criteria), VerifyCode.class);
		for (VerifyCode vc : codes) {
			Date createTime = vc.getCreateTime();
			if (vc.getCode().equals(code)) {
				if (current.getTime() - createTime.getTime() <= expiredIn && !validCode) {
					vc.setStatus(1);
					validCode = true;
				} else {
					vc.setStatus(-1);
				}
			} else {
				vc.setStatus(-1);
			}
		}

		ReturnStatus status;
		if (!validCode) {
			status = new ReturnStatus(false, "验证码错误！");
		} else {
			// 存储验证码状态
			for (VerifyCode vc : codes) {
				mongoTemplate.save(vc);
			}
			status = new ReturnStatus(true);
		}
		return status;
	}

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
			List<Role> roleList = mongoTemplate.find(Query.query(Criteria.where("_id").in(roleIds)), Role.class);
			List<String> roleNames = new ArrayList<>();
			for (Role role : roleList) {
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

				String organAdminRoleId = Constants.getProperty("system.organ.admin.roleId");
				if (oldAccount.getRoleIds().contains(organAdminRoleId)
						&& !account.getRoleIds().contains(organAdminRoleId)) {
					account.getRoleIds().add(organAdminRoleId);
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

	@Override
	public Account getAccountByEntityID(String entityID, String accountType) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("entityID").is(entityID), Criteria.where("accountType").is(accountType),
				Criteria.where("weUnionId").ne("").ne(null));
		Account Account = mongoTemplate.findOne(Query.query(criteria), Account.class);
		return Account;
	}

	@Override
	public List<Account> getAccountsByEntityID(String entityID, String accountType) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("entityID").is(entityID), Criteria.where("accountType").is(accountType),
				Criteria.where("weUnionId").ne("").ne(null));
		List<Account> accounts = mongoTemplate.find(Query.query(criteria), Account.class);
		return accounts;
	}

	@Override
	public ReturnStatus changePasswd(String accountId, String oldPasswd, String newPasswd) {
		Account account = mongoTemplate.findById(accountId, Account.class);
		if (account == null) {
			return new ReturnStatus(false, "指定id的账号信息不存在!");
		}
		String op = passwordEncoder.encodePassword(oldPasswd, null);
		if (!account.getAccountPwd().equals(op)) {
			return new ReturnStatus(false, "原口令错误!");
		} else {
			account.setAccountPwd(passwordEncoder.encodePassword(newPasswd, null));
			mongoTemplate.save(account);
			return new ReturnStatus(true);
		}
	}
}
