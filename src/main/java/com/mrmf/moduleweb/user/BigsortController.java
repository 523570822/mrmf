package com.mrmf.moduleweb.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.service.coupon.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.user.Bigsort;
import com.mrmf.service.user.bigsort.BigsortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import com.osg.framework.web.context.MAppContext;

/**
 * 服务大类
 */
@Controller
@RequestMapping("/user/bigsort")
public class BigsortController {
	@Autowired
	OperationService operationService;
	@Autowired
	private BigsortService bigsortService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/bigsort/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Bigsort> query(HttpServletRequest request) throws Exception {
		FlipInfo<Bigsort> fpi = new FlipPageInfo<Bigsort>(request);
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
		fpi = bigsortService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String bigsortId, HttpServletRequest request)
			throws Exception {
		//查到当前店面的店面类型
		String organId = request.getParameter("organId");
		Organ organ = operationService.queryShopType(organId);
		Bigsort bigsort;
		if (!StringUtils.isEmpty(bigsortId)) {
			bigsort = bigsortService.queryById(bigsortId);
		} else {
			bigsort = new Bigsort();
		}

		request.setAttribute("ffbigsort", bigsort);
		ModelAndView mv = new ModelAndView();
		mv.addObject("typeName",organ.getType());
		mv.setViewName("user/bigsort/upsert");
		return mv;
	}
	public List<Code> queryCode(HttpServletRequest request){
		String typeName = request.getParameter("type");
		String type = new String();
		if(typeName.equals("美发")){
			type="hairType";
		}else if(typeName.equals("美容")){
			type="meiRongType";
		}else if(typeName.equals("养生")){
			type="zuLiaoType";
		}else if(typeName.equals("美甲")){
			type="meiJiaType";
		}
		//查到平台服务大类
		List<Code> code = operationService.queryCode(type);
		return code;
	}
	@RequestMapping("/upsert")
	public ModelAndView upsert(Bigsort bigsort, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = bigsort.get_id();
		ReturnStatus status = bigsortService.upsert(bigsort);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			bigsort.set_id(originId);// 恢复之前的id
			request.setAttribute("ffbigsort", bigsort);
			request.setAttribute("returnStatus", status);
			mv.setViewName("user/bigsort/upsert");
		}

		return mv;
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
