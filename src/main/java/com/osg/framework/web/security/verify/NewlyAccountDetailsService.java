package com.osg.framework.web.security.verify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.osg.framework.web.security.VerifyContext;
import com.osg.framework.web.security.VerifyObject;

public class NewlyAccountDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory
			.getLogger(NewlyAccountDetailsService.class);

	VerifyAccountRepository verifyAccountRepository;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		logger.debug("Load User By Username:{}", userName);
		VerifyObject verifyObject = verifyAccountRepository
				.getAccountByName(userName);
		logger.debug("Load Verify Object --->>{}", verifyObject);


		if (verifyObject == null) {
			logger.error("User :{} don't found!");
			throw new UsernameNotFoundException("User " + userName
					+ " not found");
		}
		return new VerifyContext(verifyObject);
	}

	public VerifyAccountRepository getVerifyAccountRepository() {
		return verifyAccountRepository;
	}

	public void setVerifyAccountRepository(
			VerifyAccountRepository verifyAccountRepository) {
		this.verifyAccountRepository = verifyAccountRepository;
	}
	
}
