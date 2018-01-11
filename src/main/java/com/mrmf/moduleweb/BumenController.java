package com.mrmf.moduleweb;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Bumen;
import com.mrmf.service.bumen.BumenService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 部门管理相关
 */
@Controller
@RequestMapping("/bumen")
public class BumenController {

	@Autowired
	private BumenService bumenService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bumen/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Bumen> query(HttpServletRequest request) throws Exception {
		FlipInfo<Bumen> fpi = new FlipPageInfo<Bumen>(request);
		Boolean isOrganAdmin = (Boolean) MAppContext.getSessionVariable("isOrganAdmin");
		String organId = (String) MAppContext.getSessionVariable("organId");
		if (isOrganAdmin != null && isOrganAdmin) { // 企业管理员
			if (StringUtils.isEmpty(organId)) {
				throw new BaseException("当前登录企业信息缺失！");
			} else {
				fpi.getParams().put("organId", organId);
			}
		}
		fpi = bumenService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String bumenId, HttpServletRequest request)
			throws Exception {
		Bumen bumen;
		if (!StringUtils.isEmpty(bumenId)) {
			bumen = bumenService.queryById(bumenId);
		} else {
			bumen = new Bumen();
		}

		request.setAttribute("ffbumen", bumen);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bumen/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Bumen bumen, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = bumen.get_id();
		ReturnStatus status = bumenService.upsert(bumen);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			bumen.set_id(originId);// 恢复之前的id
			request.setAttribute("ffbumen", bumen);
			request.setAttribute("returnStatus", status);
			mv.setViewName("bumen/upsert");
		}

		return mv;
	}

	@RequestMapping("/remove/{bumenId}")
	@ResponseBody
	public ReturnStatus remove(@PathVariable String bumenId) throws Exception {
		return bumenService.remove(bumenId);
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
