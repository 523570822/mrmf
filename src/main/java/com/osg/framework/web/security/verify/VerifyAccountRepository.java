package com.osg.framework.web.security.verify;

import com.osg.framework.web.security.VerifyObject;

public interface VerifyAccountRepository {

    VerifyObject getAccountByName(String userName);
}
