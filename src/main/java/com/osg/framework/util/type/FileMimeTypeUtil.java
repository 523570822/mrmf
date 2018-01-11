package com.osg.framework.util.type;

import java.util.HashMap;
import java.util.Map;

public class FileMimeTypeUtil {

	private static final Map<String, String> mime = new HashMap<String, String>();

	static {
		// 文档类型
		mime.put(".doc", "application/msword");
		mime.put(".docx", "application/msword");
		mime.put(".xls", "application/vnd.ms-excel");
		mime.put(".xlsx", "application/vnd.ms-excel");
		mime.put(".ppt", "application/vnd.ms-powerpoint");
		mime.put(".pptx", "application/vnd.ms-powerpoint");
		mime.put(".pdf", "application/pdf");
		mime.put(".key", "application/x-iwork-keynote-sffkey");
		mime.put(".pages", "application/x-iwork-pages-sffpages");
		mime.put(".numbers", "application/x-iwork-numbers-sffnumbers");
		mime.put(".rtf", "application/rtf");

		// 图片类型
		mime.put(".jpe", "image/jpeg");
		mime.put(".jpeg", "image/jpeg");
		mime.put(".jpg", "image/jpeg");
		mime.put(".gif", "image/gif");
		mime.put(".png", "image/png");
		mime.put(".bmp", "image/bmp ");
		mime.put(".tif", "image/tiff");

		// 声音类型
		mime.put(".mp3", "audio/x-mpeg");
		mime.put(".amr", "audio/amr");
		mime.put(".aac", "audio/x-aac");
		mime.put(".caf", "audio/x-caf");
		mime.put(".wav", "audio/wav");
		mime.put(".wma", "audio/x-ms-wma");
		mime.put(".awb", "audio/amr-wb");
		mime.put(".mid", "audio/midi");
		mime.put(".midi", "audio/midi");
		mime.put(".ra", "audio/x-pn-realaudio");
		mime.put(".ram", "audio/x-pn-realaudio");

		// 文本、超文本类型
		mime.put(".html", "text/html");
		mime.put(".htx", "text/html");
		mime.put(".htm", "text/html");
		mime.put(".xml", "text/xml");
		mime.put(".xsl", "text/xml");
		mime.put(".xhtml", "application/xhtml+xml");
		mime.put(".txt", "text/plain");

		// 视频类型
		mime.put(".mp4", "video/mp4");
		mime.put(".mpeg", "video/mpeg");
		mime.put(".mpg", "video/mpeg");
		mime.put(".avi", "video/avi");
		mime.put(".wmv", "video/x-ms-wmv");
		mime.put(".flv", "video/x-flv");
		mime.put(".mov", "video/quicktime");
		mime.put(".m4v", "video/x-m4v");
		mime.put(".3gp", "video/3gpp");

		// 其他类型
		mime.put(".exe", "application/x-msdownload");
		mime.put(".apk", "application/vnd.android.package-archive");
		mime.put(".swf", "application/x-shockwave-flash");
		mime.put(".zip", "application/zip");
		mime.put(".rar", "application/x-rar-compressed");
	}

	public static String transMimeType(String suffix) {

		String suf = suffix.toLowerCase();
		String type = mime.get(suf);
		if (type == null) {
			type = "application/octet-stream";
		}
		return type;
	}
}
