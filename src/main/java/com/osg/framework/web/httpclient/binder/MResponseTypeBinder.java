package com.osg.framework.web.httpclient.binder;

import java.io.InputStream;

/**
 * HttpClient响应内容绑定工具
 * 
 * @author xiangf
 * @since JDK5.0
 * 
 */
public interface MResponseTypeBinder {

	/**
	 * 将响应内容转换为指定对象
	 * @param content 响应内容
	 * @param clazz 类
	 * @return 绑定后的对象
	 */
	public <T> T toObject(String content, Class<T> clazz) throws Exception;
	
	/**
	 * 将响应内容转换为指定对象
	 * @param content 响应内容
	 * @param clazz 类
	 * @return 绑定后的对象
	 */
	public <T> T toObject(InputStream content, Class<T> clazz) throws Exception;
	
	/**
	 * 将响应内容转换为指定对象
	 * @param content 响应内容
	 * @param clazz 类
	 * @return 绑定后的对象
	 */
	public <T> T toObject(byte[] content, Class<T> clazz) throws Exception;

}
