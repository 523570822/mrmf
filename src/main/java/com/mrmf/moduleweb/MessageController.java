package com.mrmf.moduleweb;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Message;
import com.mrmf.service.message.AkkaInitBean;
import com.mrmf.service.message.AkkaUtil;
import com.mrmf.service.message.MessageActor;
import com.mrmf.service.message.MessageService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

import akka.actor.InvalidActorNameException;
import akka.actor.Props;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@RequestMapping("/test")
	@ResponseBody
	public ReturnStatus test(@RequestParam(required = false) String key, @RequestParam(required = false) String content,
			@RequestParam(required = false) String url, HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(key)) {
			Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
			String organId = (String) MAppContext.getSessionVariable("organId");
			if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
				if (StringUtils.isEmpty(organId)) {
					throw new BaseException("当前登录企业信息缺失！");
				}
			} else { // 平台消息
				organId = "0";
			}
			key = organId;
		}
		if (StringUtils.isEmpty(content)) {
			content = "测试消息内容";
		}

		Message message = new Message();
		message.setKey(key);
		message.setContent(content);
		message.setUrl(url);
		ReturnStatus status = messageService.publishMessage(message);

		return status;
	}

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("message/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Message> query(HttpServletRequest request) throws Exception {
		FlipInfo<Message> fpi = new FlipPageInfo<Message>(request);

		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else { // 平台消息
			organId = "0";
		}
		fpi.getParams().put("key", organId);
		fpi = messageService.query(fpi);
		return fpi;
	}

	@RequestMapping("/m")
	@ResponseBody
	public ReturnStatus m(HttpServletRequest req) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			organId = "0"; // 平台管理
		}

		AsyncContext asyncContext = req.startAsync();
		asyncContext.setTimeout(1000*60*5); // 永不超时
		asyncContext.getResponse().setContentType("text/html;charset=UTF-8");

		String ip = getClientIp(req);
		AkkaInitBean initBean = new AkkaInitBean(asyncContext, ip);

		String key = organId; // akka actor key

		try {
			AkkaUtil.defaultActorSystem().actorOf(Props.create(MessageActor.class), key);
		} catch (InvalidActorNameException e) {
			// 已经存在命名Actor
		}

		AkkaUtil.defaultActorSystem().actorSelection("/user/" + key).tell(initBean, null);

		return null;
	}

	/**
	 * 从request对象中获取客户端真实的ip地址
	 * 
	 * @param request
	 *            request对象
	 * @return 客户端的IP地址
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
