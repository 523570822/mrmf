package com.mrmf.moduleweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.User;
import com.mrmf.service.vipuser.VipUserService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

@Controller
@RequestMapping("vipuser")
public class VipBirthdayController {
	@Autowired 
	VipUserService vipUserService;
	
	@RequestMapping("/toQuery")
	public ModelAndView toPinpaiQuery(HttpServletRequest request)
			throws Exception {

		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				// request.setAttribute("smallsorts",
				// smallsortService.findAll(organId));
			}
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("vipbirthday/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public List<User> vipUserQuery(Integer day,HttpServletRequest request)
			throws Exception {
		//FlipInfo<User> fpi = new FlipPageInfo<User>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext
				.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {

			}

		}
		List<User> users=vipUserService.queryVipUser(day, organId);
		return users;
	}
	@RequestMapping("/sendMessage")
	@ResponseBody
	public ReturnStatus sendMessage(Integer day)
			throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		List<User> users=vipUserService.queryVipUser(day, organId);
		
		return vipUserService.sendMessage(users, organId);
	}
}
