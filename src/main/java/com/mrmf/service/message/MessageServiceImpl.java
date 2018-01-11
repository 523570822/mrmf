package com.mrmf.service.message;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mrmf.entity.Message;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.JsonUtils;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> template;

	@Override
	public ReturnStatus publishMessage(Message msg) {
		msg.setIdIfNew();
		msg.setCreateTimeIfNew();
		msg.setState(0); // 未读
		mongoTemplate.save(msg);

		template.convertAndSend("mrmf", JsonUtils.toJson(msg));

		return new ReturnStatus(true);
	}

	@Override
	public FlipInfo<Message> query(FlipInfo<Message> fpi) throws BaseException {
		fpi.setSortField("createTime");
		fpi.setSortOrder("DESC");
		fpi = mongoTemplate.findByPage(null, fpi, Message.class);
		return fpi;
	}
}
