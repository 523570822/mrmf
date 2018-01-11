package com.osg.framework.util;

import java.util.UUID;

/**
 * UUID生成工具
 * 
 * @version 1.0
 * @since JDK5.0
 * @author xiangf
 *
 */
public class UUIDUtil {

	/**
	 * 获取随机UUID（使用JDK生成）
	 * @return UUID
	 */
	public static String getUUID() {
		while (true) {
			long uuid = UUID.randomUUID().getMostSignificantBits();
			if (uuid > 0) {
				return uuid + "";
			}
		}
	}

	/**
	 * 生成一个纯数字型的UUID。
	 */
	public static String absNumUUID() {
		return String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits()));
	}
}
