package com.osg.framework.web.springmvc.error.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.osg.framework.BaseException;

/**
 * 
 * @author 张一杰
 */
public class WebExceptionHandler implements HandlerExceptionResolver {

	public static Logger logger = Logger.getLogger(WebExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error(ex);
		if (ex instanceof BaseException) {
			logger.error("baseException来了");
		} else {
			return null;
			/*
			 * ModelAndView mv = new ModelAndView();
			 * mv.setViewName("/template/default/error"); return mv;
			 */
		}
		return null;
	}

}
