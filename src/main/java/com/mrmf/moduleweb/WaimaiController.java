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

import com.mrmf.entity.Organ;
import com.mrmf.entity.kucun.WWaimai;
import com.mrmf.service.kucun.KucunService;
import com.mrmf.service.organ.OrganService;
import com.mrmf.service.staff.StaffService;
import com.mrmf.service.waimai.WaimaiService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 外卖相关
 */
@Controller
@RequestMapping("/waimai")
public class WaimaiController {

	@Autowired
	private KucunService kucunService;
	@Autowired
	private WaimaiService waimaiService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private OrganService organService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		request.setAttribute("ffleibies", kucunService.queryWWupinList(parentId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		request.setAttribute("organSetting", organService.querySetting(organId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("waimai/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public List<WWaimai> query(String kaidanId, HttpServletRequest request) throws Exception {
		return waimaiService.query(kaidanId);
	}

	@RequestMapping("/toQueryByFpi")
	public ModelAndView toQueryByFpi(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		request.setAttribute("ffleibies", kucunService.queryWWupinList(parentId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		request.setAttribute("organSetting", organService.querySetting(organId));
		List<Organ> organList = organService.queryOrganListByParentId(organId);
		request.setAttribute("organList", organList);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("waimai/queryByFpi");
		return mv;
	}

	@RequestMapping("/queryByFpi")
	@ResponseBody
	public FlipInfo<WWaimai> queryByFpi(HttpServletRequest request) throws Exception {
		FlipInfo<WWaimai> fpi = new FlipPageInfo<WWaimai>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				if (StringUtils.isEmpty((String) fpi.getParams().get("organId")))
					fpi.getParams().put("organId", organId);
			}
		}
		return waimaiService.query(fpi);
	}

	@RequestMapping("/queryAll")
	@ResponseBody
	public List<WWaimai> queryAll(String kaidanId, HttpServletRequest request) throws Exception {
		return waimaiService.queryAll(kaidanId);
	}

	@RequestMapping("/upsert")
	@ResponseBody
	public ReturnStatus upsert(WWaimai waimai, BindingResult results, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}

		waimai.setOrganId(organId);
		return waimaiService.upsert(waimai);
	}

	@RequestMapping("/remove/{id}")
	@ResponseBody
	public ReturnStatus remove(@PathVariable String id) throws Exception {
		return waimaiService.remove(id);
	}

	@RequestMapping("/toQueryDinggou")
	public ModelAndView toQueryDinggou(HttpServletRequest request) throws Exception {
		String organId = (String) MAppContext.getSessionVariable("organId");
		String parentId = kucunService.queryParentOrganId(organId);
		// request.setAttribute("ffleibies",
		// kucunService.queryWWupinList(parentId));
		request.setAttribute("ffstaffs", staffService.findAll(organId));
		// request.setAttribute("organSetting",
		// organService.querySetting(organId));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("waimai/queryDinggou");
		return mv;
	}

	@RequestMapping("/queryDinggou")
	@ResponseBody
	public List<WWaimai> queryDinggou(String kaidanId, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		}
		return waimaiService.queryDinggou(organId);
	}

	@RequestMapping("/handleDinggou/{id}")
	@ResponseBody
	public ReturnStatus handleDingou(@PathVariable String id) throws Exception {
		return waimaiService.handleDingou(id);
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
