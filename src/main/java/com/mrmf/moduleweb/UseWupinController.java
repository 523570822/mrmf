package com.mrmf.moduleweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.kucun.WUsewupin;
import com.mrmf.service.kucun.KucunService;
import com.mrmf.service.usewupin.UsewupinService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 选择产品相关
 */
@Controller
@RequestMapping("/usewupin")
public class UseWupinController {

	@Autowired
	private KucunService kucunService;
	@Autowired
	private UsewupinService usewupinService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		request.setAttribute("ffleibies", kucunService.queryWWupinList(parentId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usewupin/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public List<WUsewupin> query(String userpartId, HttpServletRequest request) throws Exception {
		return usewupinService.query(userpartId);
	}

	@RequestMapping("/upsert")
	@ResponseBody
	public ReturnStatus upsert(WUsewupin usewupin, BindingResult results, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		usewupin.setOrganId(organId);
		return usewupinService.upsert(usewupin);
	}

	@RequestMapping("/remove/{id}")
	@ResponseBody
	public ReturnStatus remove(@PathVariable String id) throws Exception {
		return usewupinService.remove(id);
	}

	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		// 解决_id字段注入问题，去除“_”前缀处理
		binder.setFieldMarkerPrefix(null);
	}

}
