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

import com.mrmf.entity.Function;
import com.mrmf.service.function.FunctionService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;

/**
 * 菜单/资源管理相关
 */
@Controller
@RequestMapping("/function")
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		List<Function> functionList = functionService.query();

		// 添加根节点
		Function root = new Function();
		root.set_id("0");
		root.setParentId("0");
		root.setName("菜单树");
		functionList.add(root);

		request.setAttribute("fffunction", functionList);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("function/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Function> query(HttpServletRequest request) throws Exception {
		FlipInfo<Function> fpi = new FlipPageInfo<Function>(request);
		fpi = functionService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String functionId, HttpServletRequest request)
			throws Exception {
		Function function;
		if (!StringUtils.isEmpty(functionId)) {
			function = functionService.queryById(functionId);
		} else {
			function = new Function();
		}

		request.setAttribute("fffunction", function);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("function/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Function function, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = function.get_id();
		ReturnStatus status = functionService.upsert(function);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			function.set_id(originId);// 恢复之前的id
			request.setAttribute("fffunction", function);
			request.setAttribute("returnStatus", status);
			mv.setViewName("function/upsert");
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
