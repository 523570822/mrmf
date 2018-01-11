package com.osg.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Constants {

	private static Map<String, Properties> propmaps = new HashMap<String, Properties>();

	// 阿里云存储
	public static String ALI_OSS_ACCESSKEY = getProperty("ali.oss.accessKeyId");
	public static String ALI_OSS_ACCESSSECRET = getProperty("ali.oss.accessKeySecret");
	public static String ALI_OSS_PUBBUCKET = getProperty("ali.oss.pubBucketName");
	public static String ALI_OSS_PRIVBUCKET = getProperty("ali.oss.privBucketName");
	public static String ALI_OSS_ENDPOINT = getProperty("ali.oss.endpoint");
	public static String ALI_OSS_IMAGE_HOST = getProperty("ali.oss.image.host");

	// 微信服务号
	public static String WECHAT_APPID = getProperty("wechat.AppID");;
	public static String WECHAT_APPSSCRET = getProperty("wechat.AppSecret");;

	public static String WECHAT_SIGNURL = getProperty("wechat.SignUrl");

	public static String getProperty(String key) {
		return getProperties("config.properties").getProperty(key);
	}

	private static Properties getProperties(String propsName) {
		Properties properties = propmaps.get(propsName);
		if (properties == null) {
			properties = new Properties();
			try {
				InputStream inputStream = Constants.class.getClassLoader().getResourceAsStream(propsName);

				properties.load(inputStream);
				propmaps.put(propsName, properties);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return properties;
	}
}
