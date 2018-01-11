package com.osg.framework.web.i18n;

import java.util.Locale;

import com.osg.framework.web.context.MAppContext;
import com.osg.framework.web.exception.MSystemException;

/**
 * 提供国际化消息获取接口<br/>
 * 1. 需要预先在message_xx_XX.properties文件中配置消息内容<br/>
 * 2. 依赖Spring Appcontext，需要先初始化MAppContext
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangfei
 * 
 */
public class MessageUtil {

	/**
	 * 获取国际化消息
	 * 
	 * @param msg
	 *            消息key
	 * 
	 * @return 消息内容
	 */
	public static String getMessage(String msg) {
		return MessageUtil.getMessageWithArg(msg, null);
	}

	/**
	 * 获取带参数的国际化消息
	 * 
	 * @param msg
	 *            消息key
	 * @param arg
	 *            消息内容中的参数
	 * 
	 * @return 消息内容
	 */
	public static String getMessageWithArg(String msg, Object[] arg) {
		return MessageUtil.getMessage(msg, arg, Locale.CHINA);
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param msg
	 *            消息key
	 * @param arg
	 *            消息内容中的参数
	 * @param locale
	 *            地区
	 * 
	 * @return 消息内容
	 */
	public static String getMessage(String msg, Object[] arg, Locale locale) {
		if (MAppContext.getAppContext() != null) {
			return MAppContext.getAppContext().getMessage(msg, arg, locale);
		} else {
			throw new MSystemException("Get i18n message failed cause spring applicationCntext is missing!");
		}
	}

}
