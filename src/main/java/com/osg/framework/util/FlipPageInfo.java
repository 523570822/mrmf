/**
 * $Author: wuym $
 * $Rev: 155 $
 * $Date:: 2012-07-09 15:08:37#$:
 *
 * Copyright (C) 2012 Seeyon, Inc. All rights reserved.
 *
 * This software is the proprietary information of Seeyon, Inc.
 * Use is subject to license terms.
 */
package com.osg.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.osg.entity.FlipInfo;

/**
 * <p>
 * Title: T1开发框架
 * </p>
 * <p>
 * Description: 翻页信息类，带界面交互信息。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: seeyon.com
 * </p>
 * 
 * @since CTP2.0
 */
public class FlipPageInfo<T> extends FlipInfo {

	private transient HttpServletRequest request;

	private String paramStr;

	private String firstPageLink;
	private String lastPageLink;
	private String previousPageLink;
	private String nextPageLink;
	private String paraPageLink;

	/**
	 * 根据HttpServletRequest请求初始化翻页信息类
	 * 
	 * @param request
	 *            界面请求对象
	 */
	public FlipPageInfo(HttpServletRequest request) {
		this(request, request == null ? null : convertMap(request.getParameterMap()));
	}

	/**
	 * 根据HttpServletRequest请求和自定义参数初始化翻页信息类
	 * 
	 * @param request
	 *            界面请求对象
	 * @param params
	 *            自定义参数
	 */
	public FlipPageInfo(HttpServletRequest request, Map params) {
		this.request = request;
		Map ps = new HashMap();
		setParams(ps);

		if (params != null) {
			ps.putAll(params);
			Iterator ite = params.keySet().iterator();
			String key, str;
			Object val;
			while (ite.hasNext()) {
				key = (String) ite.next();
				val = params.get(key);
				if (val != null) {
					if (val instanceof String) {
						str = (String) val;
						str = StringUtils.replace((String) str, "\\", "\\\\");
						str = StringUtils.replace((String) str, "\n", "\\n");
						str = StringUtils.replace((String) str, "\r", "\\r");
						str = StringUtils.replace((String) str, "</script>", "<//script>");
						val = str;
					}

					ps.put(key, val);
				}

			}
		}
		firstPageLink = "#";
		lastPageLink = "#";
		previousPageLink = "#";
		nextPageLink = "#";
		if (request != null)
			init(ps);
	}

	/**
	 * 根据当前要显示页和每页记录数初始化翻页信息类
	 * 
	 * @param page
	 *            当前页
	 * @param size
	 *            每页记录数
	 */
	public FlipPageInfo(int page, int size) {
		super(page, size);
	}

	public static Map convertMap(final Map params) {
		Map result = new HashMap();
		Iterator paramIte = params.keySet().iterator();
		while (paramIte.hasNext()) {
			String pName = (String) paramIte.next();
			Object valueObj = params.get(pName);
			if (valueObj == null)
				continue;
			String pValue = "";
			if (valueObj.getClass().isArray()) {
				String[] varray = (String[]) valueObj;
				if (varray.length > 0)
					pValue = varray[0];
				else
					continue;
			} else
				pValue = valueObj.toString();
			result.put(pName, pValue);
		}
		return result;
	}

	private void init(Map params) {
		String sz = request.getParameter("size");
		if (sz == null)
			sz = request.getParameter("rows");
		try {
			super.setSize(Integer.valueOf(sz));
		} catch (Exception e) {
		}
		String pagePara = request.getParameter("page");

		if (pagePara != null) {
			try {
				super.setPage(Integer.valueOf(pagePara));
			} catch (NumberFormatException e) {
			}
		}

		setSortField(request.getParameter("sortField"));
		setSortOrder(request.getParameter("sortOrder"));

		if (!params.containsKey("_json_params")) {
			String jsonStr = request.getParameter("_json_params");
			if (jsonStr != null && !"".equals(jsonStr))
				params.put("_json_params", jsonStr);
		}
		StringBuffer paramsb = new StringBuffer();
		Iterator paramIte = params.keySet().iterator();
		while (paramIte.hasNext()) {
			String pName = (String) paramIte.next();
			Object valueObj = params.get(pName);
			if (valueObj == null)
				continue;
			String pValue = "";
			if (valueObj.getClass().isArray()) {
				String[] varray = (String[]) valueObj;
				if (varray.length > 0) {
					// pValue = varray[0];
					for (int i = 0; i < varray.length; i++) {
						pValue = varray[i];
						if (!"page".equals(pName)) {
							paramsb.append(pName);
							paramsb.append("=");
							try {
								paramsb.append(URLEncoder.encode(pValue, "GBK"));
							} catch (UnsupportedEncodingException e) {
								paramsb.append(pValue);
							}
							paramsb.append("&");
						}
					}
					pName = "page";
				} else
					continue;
			} else
				pValue = valueObj.toString();

			if (!"page".equals(pName)) {
				paramsb.append(pName);
				paramsb.append("=");
				try {
					paramsb.append(URLEncoder.encode(pValue, "GBK"));
				} catch (UnsupportedEncodingException e) {
					paramsb.append(pValue);
				}
				paramsb.append("&");
			}
		}
		this.paramStr = paramsb.toString();
		paraPageLink = request.getRequestURI() + "?" + paramStr;
	}

	private void initLinks() {
		String uri = request.getRequestURI();
		int pagei = getPage();
		int pages = getPages().intValue();
		if (pagei > 1) {
			firstPageLink = uri + "?" + paramStr + "page=1";
			previousPageLink = uri + "?" + paramStr.toString() + "page=" + (pagei - 1);
		}
		if (pagei < pages) {
			nextPageLink = uri + "?" + paramStr.toString() + "page=" + (pagei + 1);
			lastPageLink = uri + "?" + paramStr.toString() + "page=" + pages;
		}
	}

	/**
	 * 当前页数据对象列表
	 * 
	 * @param data
	 *            当前页数据对象列表
	 */
	public void setData(List data) {
		super.setData(data);
		if (request != null)
			initLinks();
	}

	/**
	 * 获取第一页链接
	 * 
	 * @return 第一页链接
	 */
	public String getFirstPageLink() {
		return firstPageLink;
	}

	/**
	 * 获取最后页链接
	 * 
	 * @return 最后页链接
	 */
	public String getLastPageLink() {
		return lastPageLink;
	}

	/**
	 * 获取上一页链接
	 * 
	 * @return 上一页链接
	 */
	public String getPreviousPageLink() {
		return previousPageLink;
	}

	/**
	 * 获取下一页链接
	 * 
	 * @return 下一页链接
	 */
	public String getNextPageLink() {
		return nextPageLink;
	}

	/**
	 * 获取带参数链接
	 * 
	 * @return 带参数链接
	 */
	public String getParaPageLink() {
		return paraPageLink;
	}

	/**
	 * 根据另一翻页信息对象初始化当前对象
	 * 
	 * @param fi
	 *            翻页信息类
	 */
	public void initByFlipInfo(FlipInfo fi) {
		super.setPage(fi.getPage());
		super.setSize(fi.getSize());
		super.setTotal(fi.getTotal());
		super.setData(fi.getData());
		super.setNeedTotal(fi.isNeedTotal());
	}

}
