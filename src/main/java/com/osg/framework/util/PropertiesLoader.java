/**
 * $Author: wuym $
 * $Rev: 6316 $
 * $Date:: 2013-04-22 13:33:40#$:
 *
 * Copyright (C) 2012 Seeyon, Inc. All rights reserved.
 *
 * This software is the proprietary information of Seeyon, Inc.
 * Use is subject to license terms.
 */
package com.osg.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * Title: T1开发框架
 * </p>
 * <p>
 * Description: Properties加载工具类，支持properties或xml格式配置文件加载，xml配置描述如下： Provides the
 * the ability to use simple XML property files. Each property is in the form
 * X.Y.Z, which would map to an XML snippet of:
 * 
 * <pre>
 * &lt;X&gt;
 *     &lt;Y&gt;
 *         &lt;Z&gt;someValue&lt;/Z&gt;
 *     &lt;/Y&gt;
 * &lt;/X&gt;
 * </pre>
 *
 * The XML file is passed in to the constructor and must be readable.
 * <p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: seeyon.com
 * </p>
 * 
 * @since CTP2.0
 */
public class PropertiesLoader {

	private static final Logger LOGGER = Logger
			.getLogger(PropertiesLoader.class);

	public static Properties loadXml(InputStream is) {
		Properties props = new Properties();
		try {
			SAXReader builder = new SAXReader();
			Document doc = builder.read(is);
			Element root = doc.getRootElement();
			List<Element> childrenList = root.elements();
			for (Element child : childrenList) {
				parseElement(child, "", props);
			}
		} catch (Exception e) {
			LOGGER.error("Error parsing XML stream");
		}
		return props;
	}

	/**
	 * 加载properties或xml属性配置文件
	 * 
	 * @param file
	 *            要加载的属性配置文件
	 * @return 加载后的Properties对象
	 */
	public static Properties load(File file) {
		if (file == null)
			throw new IllegalArgumentException(
					"Parameter 'file' can't be null.");
		if (!file.exists() || !file.isFile())
			throw new IllegalArgumentException("Properties file not exists:"
					+ file.getAbsolutePath());
		String fm = file.getName();
		Properties props = new Properties();
		if (fm.endsWith(".xml")) {
			try {
				props = loadXml(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				LOGGER.error(
						"Error loading xml file:"
								+ file.getAbsolutePath(), e);
			}
		} else if (fm.endsWith(".properties")) {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				props.load(is);
			} catch (Exception e) {
				LOGGER.error(
						"Error loading properties file:"
								+ file.getAbsolutePath(), e);
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					// ignore it
				}
			}
		} else
			throw new IllegalArgumentException(
					"Unsupported properties file type:"
							+ file.getAbsolutePath());
		return props;
	}

	private static void parseElement(final Element ele, final String fkey,
			final Properties props) {
		String name = ele.getName(), text = ele.getText(), key = fkey;
		List<Element> childrenList = ele.elements();
		if (key.length() > 0)
			key += ".";
		key += name;

		// 配置元素内容非空，或者配置元素没有子节点（空配置项内容）均生成property配置
		if ((text != null && !"".equals(text.trim()))
				|| childrenList.size() == 0) {
			putProperty(key, text, props);
		}

		List<Attribute> attrList = ele.attributes();
		String attKey, attText;
		for (Attribute attr : attrList) {
			attKey = key + "." + attr.getName();
			attText = attr.getValue();
			putProperty(attKey, attText, props);
		}

		for (Element child : childrenList) {
			parseElement(child, key, props);
		}
	}

	private static void putProperty(final String key, final String text,
			final Properties props) {
		String tmp;
		if (props.containsKey(key)) {
			// 存在重复key则用"|"分隔
			tmp = props.getProperty(key);
			props.put(key, tmp + "|" + text);
		} else {
			props.put(key, text);
		}
	}

}