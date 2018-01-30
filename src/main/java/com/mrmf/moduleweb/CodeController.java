package com.mrmf.moduleweb;

import com.mrmf.entity.Code;
import com.mrmf.service.code.CodeService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 代码管理相关
 */
@Controller
@RequestMapping("/code")
public class CodeController {

	@Autowired
	private CodeService codeService;

	@RequestMapping("/toQuery")
	public ModelAndView toQuery(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("code/query");
		return mv;
	}

	@RequestMapping("/query")
	@ResponseBody
	public FlipInfo<Code> query(HttpServletRequest request) throws Exception {
		FlipInfo<Code> fpi = new FlipPageInfo<Code>(request);
		fpi = codeService.query(fpi);
		return fpi;
	}

	@RequestMapping("/toUpsert")
	public ModelAndView toUpsert(@RequestParam(required = false) String codeId, HttpServletRequest request)
			throws Exception {
		Code code;
		if (!StringUtils.isEmpty(codeId)) {
			code = codeService.queryById(codeId);
		} else {
			code = new Code();
		}

		request.setAttribute("ffcode", code);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("code/upsert");
		return mv;
	}

	@RequestMapping("/upsert")
	public ModelAndView upsert(Code code, BindingResult results, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String originId = code.get_id();
		ReturnStatus status = codeService.upsert(code);
		if (status.isSuccess()) {
			mv = toQuery(request);
		} else {
			code.set_id(originId);// 恢复之前的id
			request.setAttribute("ffcode", code);
			request.setAttribute("returnStatus", status);
			mv.setViewName("code/upsert");
		}

		return mv;
	}

	@RequestMapping("/remove/{codeId}")
	@ResponseBody
	public ReturnStatus remove(@PathVariable String codeId) throws Exception {
		return codeService.remove(codeId);
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
