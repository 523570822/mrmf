package com.osg.framework.web.springmvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mrmf.service.message.AkkaUtil;
import com.osg.framework.web.AppContext;
import com.osg.framework.web.context.MAppContext;
import com.thoughtworks.xstream.core.BaseException;

public class MainServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		preInit(config);
		try {
			doInit(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		afterInit(config);
	}

	/**
	 * 留有接口
	 * 
	 * @param config
	 * @throws ServletException
	 */
	public void preInit(ServletConfig config) throws ServletException {
	}

	/**
	 * 留有接口
	 * 
	 * @param config
	 * @throws ServletException
	 */
	public void afterInit(ServletConfig config) throws ServletException {
	}

	/**
	 * servlet启动时 初始化系统
	 * 
	 * @param config
	 * @throws ServletException
	 * @throws BaseException
	 * @throws IOException
	 */
	public void doInit(ServletConfig config) throws ServletException {
		ServletContext sc = config.getServletContext();
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(sc);

		MAppContext.setAppContext(appContext);
		AppContext.init(sc, appContext, appContext);

		// 初始化Akka框架
		AkkaUtil.defaultActorSystem();
	}

	@Override
	public void destroy() {
		super.destroy();

		AkkaUtil.shutdown();
		// 强制停止进程
		System.exit(0);
	}
}
