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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mrmf.entity.Function2;
import com.mrmf.service.function.Function2Service;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;

/**
 * 菜单/资源管理相关
 */
@Controller
@RequestMapping("/function2")
public class Function2Controller {

	@Autowired
	private Function2Service functionService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		List<Function2> functionList = functionService.query();

		// 添加根节点
		Function2 root = new Function2();
		root.set_id("0");
		root.setParentId("0");
		root.setName("菜单树");
		functionList.add(root);

		request.setAttribute("fffunction", functionList);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("function2/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Function2> query(HttpServletRequest request) throws Exception {
		FlipInfo<Function2> fpi = new FlipPageInfo<Function2>(request);
		fpi = functionService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String functionId, HttpServletRequest request)
			throws Exception {
		Function2 function;
		if (!StringUtils.isEmpty(functionId)) {
			function = functionService.queryById(functionId);
		} else {
			function = new Function2();
		}

		request.setAttribute("fffunction", function);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("function2/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Function2 function, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = function.get_id();
		ReturnStatus status = functionService.upsert(function);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			function.set_id(originId);// 恢复之前的id
			request.setAttribute("fffunction", function);
			request.setAttribute("returnStatus", status);
			mv.setViewName("function2/upsert");
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
