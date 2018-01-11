package com.mrmf.service.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;

import com.osg.framework.util.JsonUtils;

import akka.actor.UntypedActor;

/**
 * 系统web消息跨线程输出，包括平台消息和公司消息
 * 
 * @author wuyumin
 *
 */
public class MessageActor extends UntypedActor {

	private Map<String, AsyncContext> ctxMap = new HashMap<String, AsyncContext>();

	@Override
	public void onReceive(Object msg) {
		if (msg instanceof Map) {
			Map<String, String> msgMap = (Map<String, String>) msg;
			String m = JsonUtils.toJson(msgMap);
			for (String key : ctxMap.keySet()) {
				try {
					AsyncContext asyncContext = ctxMap.get(key);
					PrintWriter writer = asyncContext.getResponse().getWriter();
					writer.write(m);
					writer.flush();
					asyncContext.complete();
				} catch (IOException e) {
					// 可能产生客户端连接中断
					e.printStackTrace();
				}
			}

			getContext().stop(getSelf());
		} else if (msg instanceof AkkaInitBean) {
			AkkaInitBean initBean = (AkkaInitBean) msg;
			ctxMap.put(initBean.getIp(), initBean.getContext());
		} else {
			unhandled(msg);
		}
	}
}
