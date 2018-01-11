package com.mrmf.moduleweb.user;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrmf.entity.user.Kaidan;
import com.mrmf.service.user.kaidan.KaidanService;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 开单相关
 */
@Controller
@RequestMapping("/user/kaidan")
public class KaidanController {

	@Autowired
	private KaidanService kaidanService;

	@RequestMapping("/query")
	@ResponseBody
	public List<Kaidan> query(@RequestParam(required = false) String kaidan_id, HttpServletRequest request)
			throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		return kaidanService.query(organId, kaidan_id);
	}

	@RequestMapping("/upsert")
	@ResponseBody
	public ReturnStatus upsert(Kaidan kaidan, BindingResult results, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}
		String kdId = request.getParameter("kdId");
		kaidan.setKaidan_id(kdId);
		kaidan.setOrganId(organId);

		return kaidanService.upsert(kaidan);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public ReturnStatus remove(String id, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		return kaidanService.remove(organId, id);
	}

	@RequestMapping("/union")
	@ResponseBody
	public ReturnStatus union(String[] kaidanList, HttpServletRequest request) throws Exception {
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			}
		} else {
			throw new BaseException("当前登录企业信息缺失！");
		}

		return kaidanService.union(organId, kaidanList);
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
