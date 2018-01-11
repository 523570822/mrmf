package com.osg.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class SecurityHelper {

	private static final Logger LOGGER = Logger.getLogger(SecurityHelper.class);

	private static MessageDigest digester;

	private static final String ALGORITHM = "MD5";

	/**
	 * 利用md5算法生成信息摘要
	 * 
	 * @param digestPara
	 *            要生成信息摘要的参数对象
	 * @return 摘要
	 */
	public static String digest(Object... digestPara) {
		StringBuffer digestStr = new StringBuffer();
		for (Object o : digestPara) {
			if (o instanceof String[]) {
				for (Object o2 : (Object[]) o) {
					digestStr.append(o2).append("+");
				}
			} else {
				digestStr.append(o).append("+");
			}
		}

		if (digestStr.length() > 0) {
			digestStr.deleteCharAt(digestStr.length() - 1);
		}

		MessageDigest d = getSharedDigester();
		if (d != null) {
			try {
				return bytes2Hex(((MessageDigest) d.clone()).digest(digestStr.toString().getBytes()));
			} catch (CloneNotSupportedException e) {
				// 一般情况不会出现此异常
				LOGGER.error("", e);
			}
		}
		return String.valueOf(digestStr);
	}

	private static MessageDigest getSharedDigester() {
		if (digester == null) {
			try {
				digester = MessageDigest.getInstance(ALGORITHM);
			} catch (NoSuchAlgorithmException e) {
				LOGGER.error("", e);
			}
		}
		return digester;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}