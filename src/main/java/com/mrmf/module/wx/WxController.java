package com.mrmf.module.wx;

import com.alibaba.fastjson.JSONArray;
import com.mrmf.entity.Account;
import com.mrmf.entity.Article.Article;
import com.mrmf.entity.Organ;
import com.mrmf.entity.Staff;
import com.mrmf.entity.User;
import com.mrmf.module.wx.utils.WXUtil;
import com.mrmf.service.coupon.CouponGrantService;
import com.mrmf.service.wecommon.WeComonService;
import com.mrmf.service.weuser.WeUserService;
import com.mrmf.service.wxSendNewsMessage.WxSendNewsMessageService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.MessageUtil;
import com.osg.framework.web.cache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wx")
public class WxController {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private WeComonService weCommonService;
	@Autowired
	private WeUserService weUserService;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CouponGrantService couponGrantService;
	@Autowired
	private WxSendNewsMessageService wxSendNewsMessageService;

	ReturnStatus status=null;

	Logger _logger = Logger.getLogger(this.getClass());

	@RequestMapping(value = "/sign",produces = "application/json; charset=utf-8",method = RequestMethod.GET)
	public void sign(HttpServletRequest request,HttpServletResponse reponse) throws UnsupportedEncodingException, IOException{
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if (WXUtil.checkSignature(signature, timestamp, nonce)) {
			reponse.getOutputStream().write(echostr.getBytes("utf-8"));
			reponse.getOutputStream().close();
		}
		_logger.info("sign success");
	}
	
	@RequestMapping(value = "/sign",produces = "application/xml; charset=utf-8",method = RequestMethod.POST)
	public void handle(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		boolean isShareCode = false;
        String accountType = "";
		Map<String, String> map = null;
		try {
			map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String eventKey = map.get("EventKey");

			String text = "success";
		    if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
				String eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					Article article = wxSendNewsMessageService.getCurrentArticleMessage("1");
					System.out.println(article);
					if (article != null){
						text = MessageUtil.initNewsTextWithData(toUserName, fromUserName, article);
						System.out.println(text);
					}
				    //取到inviatorId
					//获取当前关注用户的openid
					String scene_id="";
					String inviatorId = "";
                    if(StringUtils.isNotEmpty(eventKey)){
                        String sceneValues[] = eventKey.split("_",0);
                        if (sceneValues != null && sceneValues.length>1){
                            isShareCode = true;
                            scene_id = sceneValues[1];
                            if (scene_id.startsWith("17")){
                                //用户
                                accountType = "user";
								User user = (User) cacheManager.get(scene_id+"");
								inviatorId = user.get_id();
                            }else if (scene_id.startsWith("18")){
                                //技师
								accountType = "staff";
								Staff staff = (Staff) cacheManager.get(scene_id+"");
								inviatorId = staff.get_id();
                            }else if (scene_id.startsWith("19")){
                                // 店铺
								accountType = "organ";
								Organ organ = (Organ) cacheManager.get(scene_id+"");
								inviatorId = organ.get_id();
                            }
                        }
                    }
					_logger.info("关注人的~~~~~~openid"+fromUserName);
                    _logger.info("推荐人scene_id~~~~~~~"+scene_id);
					if (isShareCode){
						status=weCommonService.isExist(fromUserName,"", "user");//判断用户是否是第一次关注
						if (status.isSuccess()){
							System.out.println("不是第一次关注");
						}else {
							User isExistuser = new User();
							_logger.info("推荐人user"+inviatorId);
							if (StringUtils.isNotEmpty(inviatorId)){
								isExistuser.setInvitor(inviatorId);
								isExistuser.setInviteDate(new Date());
								isExistuser.setCreateTime(new Date());
								isExistuser.setAccountType(accountType);
							}
							mongoTemplate.save(isExistuser);
							_logger.info("isExistuser.id"+isExistuser.get_id());
							couponGrantService.grantCouponByuserUuidAndType(isExistuser.get_id(),"关注",-1,"");
							Account account = new Account();
							account.setAccountType("user");
							account.setAccountName(fromUserName);
							account.setStatus("1");
							account.setWeUnionId("asdf");
							account.setCreateTime(new Date());
							account.setEntityID(isExistuser.get_id());
							mongoTemplate.save(account);
						}
					}else {
						System.out.println("我不是分享扫描的。。。");
					}
				}
			}
			System.out.println("System.out.println(text)"+text);
			response.getOutputStream().write(text.getBytes("utf-8"));
			response.getOutputStream().close();
		} catch (Exception e) {
			System.out.println("用户交互产生了异常！");
			e.printStackTrace();
		}
	}


}
