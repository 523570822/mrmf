package com.mrmf.service.message;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.osg.framework.util.JsonUtils;
import com.osg.framework.util.StringUtils;

public class RedisMessageListener {

	private static final Logger logger = Logger.getLogger(RedisMessageListener.class);

	public void handleMessage(Serializable message) {
		if (message instanceof String) {
			try {
				Map<String, String> m = JsonUtils.fromJson((String) message, Map.class);
				String key = m.get("key");
				if (!StringUtils.isEmpty(key)) {
					AkkaUtil.defaultActorSystem().actorSelection("/user/" + key).tell(m, null);
				}else {
					logger.error("redis消息格式错误(无key):" + message);
				}
			} catch (Exception e) {
				logger.error("redis消息json解析错误:" + message, e);
			}
		} else {
			logger.error("未知的redis消息：" + message);
		}
	}
}
