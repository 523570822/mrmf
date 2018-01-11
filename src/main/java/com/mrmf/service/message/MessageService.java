package com.mrmf.service.message;

import com.mrmf.entity.Message;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface MessageService {

	/**
	 * 发布消息
	 * 
	 * @param msg
	 */
	public ReturnStatus publishMessage(Message msg);

	/**
	 * 查询消息列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Message> query(FlipInfo<Message> fpi) throws BaseException;
}
