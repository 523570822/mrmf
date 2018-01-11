/**
 * $Author: wuym $
 * $Rev: 2818 $
 * $Date:: 2012-12-13 16:07:36#$:
 *
 * Copyright (C) 2012 Seeyon, Inc. All rights reserved.
 *
 * This software is the proprietary information of Seeyon, Inc.
 * Use is subject to license terms.
 */
package com.osg.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.log4j.Logger;
import scala.Char;

/**
 * <p>
 * Title: T1开发框架
 * </p>
 * <p>
 * Description: 字符串处理工具类。
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
public class StringUtils {

	private static final Logger LOGGER = Logger.getLogger(StringUtils.class);

	/**
	 * 如果是null字符串, 则返回""字符串
	 *
	 * @param str
	 *            String
	 * @return String
	 */
	public static String filterNull(String str) {
		if (str == null) {
			return new String();
		} else {
			return str;
		}
	}

	/**
	 * 如果是null对象, 则返回""字符串
	 *
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static String filterNullObject(Object obj) {
		if (obj == null) {
			return new String();
		} else {
			return obj.toString();
		}
	}

	/**
	 * 传入的字符串如果为null，则返回""字符串，否则原样返回。
	 *
	 * @param str
	 *            要处理的字符串
	 * @return 结果字符串
	 */
	public static String stringToString(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 转换XML特殊字符。
	 *
	 * @param val
	 *            要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String deal(String val) {

		val = replace(val, "\"", "’");
		val = replace(val, ">", "’");
		val = replace(val, "<", "’");
		return val;
	}

	/**
	 * Replace substrings of one string with another string and return altered
	 * string.
	 *
	 * @param original
	 *            input string
	 * @param oldString
	 *            the substring section to replace
	 * @param newString
	 *            the new substring replacing old substring section
	 * @return converted string
	 */
	public static String replace(final String original, final String oldString, final String newString) {
		return replace(original, oldString, newString, 0);
	}

	/**
	 * Replace substrings of one string with another string and return altered
	 * string.
	 *
	 * @param original
	 *            input string
	 * @param oldString
	 *            the substring section to replace
	 * @param newString
	 *            the new substring replacing old substring section
	 * @param counts
	 *            how many times the replace happen, 0 for all.
	 * @return converted string
	 */
	public static String replace(final String original, final String oldString, final String newString,
								 final int counts) {
		if (original == null || oldString == null || newString == null) {
			return "";
		}
		if (counts < 0)
			throw new IllegalArgumentException("parameter counts can not be negative");

		final StringBuffer sb = new StringBuffer();

		int end = original.indexOf(oldString);
		int start = 0;
		final int stringSize = oldString.length();

		int currentCount = 0;
		while (end != -1) {
			if (counts == 0 || currentCount < counts) {
				sb.append(original.substring(start, end));
				sb.append(newString);
				start = end + stringSize;
				end = original.indexOf(oldString, start);
				currentCount++;
			} else
				break;
		}

		end = original.length();
		sb.append(original.substring(start, end));

		return sb.toString();
	}

	/**
	 * 把由delim分割的字符串分裂并形成字符串数组。 例如： String sourceString =
	 * "string1;string2;string3"; String[] result =
	 * StringUtility.split(sourceString, ";");
	 * 则result是由字符串"string1","string2"和"string3"组成的数组。
	 *
	 * @param sourceString
	 *            要分裂的字符串
	 * @param delim
	 *            分隔符
	 * @return 分裂并组合后的字符串数组
	 */
	public static String[] split(String sourceString, String delim) {
		if (sourceString == null || delim == null)
			return new String[0];
		StringTokenizer st = new StringTokenizer(sourceString, delim);
		List stringList = new ArrayList();
		for (; st.hasMoreTokens(); stringList.add(st.nextToken()))
			;
		return (String[]) (stringList.toArray(new String[stringList.size()]));
		// 故提供split(String str, String delimiters, boolean skipDelim)
	}

	/**
	 * added by chenjie 把由delim分割的字符串分裂并形成字符串数组，并将分割的字符串两边的空格去除。
	 *
	 * @param src
	 * @param delim
	 * @return
	 */
	public static String[] splitAndTrim(String src, String delim) {
		if (src == null || delim == null)
			return new String[0];
		StringTokenizer st = new StringTokenizer(src, delim);
		List stringList = new ArrayList();
		for (; st.hasMoreTokens(); stringList.add(st.nextToken().trim()))
			;
		return (String[]) (stringList.toArray(new String[stringList.size()]));
	}

	/**
	 * 把以delimiters分隔的字符串分裂成字符串数组。
	 *
	 * @param str
	 *            要分裂的字符串
	 * @param delimiters
	 *            分隔字符串
	 * @param skipDelim
	 *            当分隔字符串连续出现时或者以分隔字符串开始、结束时，true跳过空字符串，false输出空字符串
	 * @return 分裂后的字符串数组
	 */
	public static String[] split(String str, String delimiters, boolean skipDelim) {
		// 1、当分裂字符串里有""出现，可能""之间内容当作一个整体，不可分隔。
		// 2、当出现第一种情况时，若分隔字符串里本身也存在""，
		// 象上述复杂情况可以考虑一个PowerfulTokenizer类（扩展java.util.StringTokenizer）来解析
		if (str == null || delimiters == null) {
			return new String[0];
		}
		List strList = new ArrayList();
		int start = 0;
		int end = str.length();

		while (start <= end) {
			int delimIdx = str.indexOf(delimiters, start);
			if (delimIdx < 0) {
				String tok = str.substring(start);
				if (!skipDelim || !tok.equals("")) {
					strList.add(tok);
				}
				start = end + delimiters.length();
			} else {
				String tok = str.substring(start, delimIdx);
				if (!skipDelim || !tok.equals("")) {
					strList.add(tok);
				}
				start = delimIdx + delimiters.length();
			}
		}
		return (String[]) strList.toArray(new String[strList.size()]);
	}

	/**
	 * 首字母大写
	 *
	 * @param str
	 * @return
	 */
	public static String firstCharUp(String str) {
		return str.replaceFirst("" + str.charAt(0), ("" + str.charAt(0)).toUpperCase());
	}

	/**
	 * 首字母小写
	 */
	public static String firstCharLower(String str) {
		return str.replaceFirst("" + str.charAt(0), ("" + str.charAt(0)).toLowerCase());
	}

	/**
	 * @param obj
	 *            要判断的是否为空的对象
	 * @param info
	 *            如果该对象为空抛出的提示信息
	 */
	public static void judgeNull(final Object obj, final String info) {
		if (obj == null || "".equals(obj.toString().trim())) {
			throw new RuntimeException(info);
		}
	}

	public static byte[] compress(String s) {
		byte[] input = null;

		try {
			input = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			input = s.getBytes();
		}

		// Create the compressor with highest level of compression
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);

		// Give the compressor the data to compress
		compressor.setInput(input);
		compressor.finish();

		// Create an expandable byte array to hold the compressed data.
		// You cannot use an array that's the same size as the orginal because
		// there is no guarantee that the compressed data will be smaller than
		// the uncompressed data.
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		// Compress the data
		byte[] buf = new byte[1024];
		boolean done = false;
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			if (count < buf.length) {
				done = true;
			}
			if (count > 0)
				bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
		}

		// Get the compressed data
		byte[] compressedData = bos.toByteArray();
		return compressedData;
	}

	public static String decompress(byte[] compressedData) {
		// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);

		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		// Decompress the data
		byte[] buf = new byte[1024];
		boolean done = false;
		// SysFunc.Debug(2, "DeCompress....1");
		while (!done) {
			try {
				int count = decompressor.inflate(buf);
				if (count < buf.length) {
					done = true;
				}
				if (count > 0) {
					bos.write(buf, 0, count);
				}
			} catch (DataFormatException e) {
				e.printStackTrace();
				return null;
			}
			Thread.currentThread().yield();
		}
		// SysFunc.Debug(2, "DeCompress....2");

		try {
			bos.close();
		} catch (IOException e) {
		}

		// Get the decompresse data
		try {
			return bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 检查字符串是否为空或是特定空值
	 *
	 * @param data
	 * @return
	 */
	public static boolean checkNull(String data) {
		if ((data == null) || (("".equals(data))) || (("null".equalsIgnoreCase(data))))
			return true;
		return false;
	}

	public static String toTimeString(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day > 0 ? day + "天" : "";
		String strHour = hour > 0 ? hour + "时" : "";
		String strMinute = minute > 0 ? minute + "分" : "";
		String strSecond = second > 0 ? second + "秒" : "";
		String strMilliSecond = milliSecond > 0 ? milliSecond + "毫秒" : "";

		return strDay + strHour + strMinute + strSecond + strMilliSecond;

	}

	public static String formatFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 验收手机号合法性
	 *
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {

		/*
		 * 06. 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 07.
		 * 联通：130、131、132、152、155、156、185、186 08. 电信：133、153、180、189、（1349卫通）
		 * 09. 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 10.
		 */
		String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (StringUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);

	}

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str) || "undefined".equals(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(List arr) {
		if (arr == null || arr.size() <= 0) {
			return true;
		}
		int i = 0;
		for (Object object : arr) {
			if (object != null && !StringUtils.isEmpty(object.toString())) {
				i++;
			}
		}
		if (i > 0) {
			return false;
		} else {
			return true;
		}
	}

	// 截取数字
	public static String getNumbers(String content) {
		if (isEmpty(content))
			return content;
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 去除字符串首尾出现的某个字符.
	 * @param source 源字符串.
	 * @param element 需要去除的字符.
	 * @return String.
	 */
	public static String trimFirstAndLastChar(String source,char element){
		boolean beginIndexFlag = true;
		boolean endIndexFlag = true;
		do{
			int beginIndex = source.indexOf(element) == 0 ? 1 : 0;
			int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();
			source = source.substring(beginIndex, endIndex);
			beginIndexFlag = (source.indexOf(element) == 0);
			endIndexFlag = (source.lastIndexOf(element) + 1 == source.length());
		} while (beginIndexFlag || endIndexFlag);
		return source;
	}

	/**
	 * 判断添加0
	 * @param month
	 * @param num
	 * @param sun
	 * @return
	 */
	public  static String leftPad(String month, int num, String sun){
		if(month.length()>=num){
			return month;
		}else {
			sun+=month;
			return sun;
		}
	}
	public static String timeListToString(List<String> timeList){
		int flag=1;
		boolean pd=false;
		StringBuffer stringBuffer = new StringBuffer();
		String fristDate = new String();
		for(int i = 0;i<timeList.size();i++){
			String fristTime = timeList.get(i);
			if(i+1<timeList.size()){
				String secondTime = timeList.get(i+1);
				if(DateUtil.getNextTime(fristTime,secondTime)){
					pd=true;
					if(flag==1){
						fristDate=getString(getNumberString(fristTime,5,10),"-","/");
						flag=2;
					}
				}else {
					fristTime = getString(getNumberString(fristTime,5,10),"-","/");
					pd=false;
					if(flag==1){
						stringBuffer.append(fristTime+",");
					}else  if (flag==2){
						stringBuffer.append(fristDate+"-"+fristTime+",");
						flag=1;
					}
				}
			}
			if(i+1==timeList.size()){
				fristTime = getString(getNumberString(fristTime,5,10),"-","/");
				if(pd){
					if(flag==1){
						stringBuffer.append(fristDate+"-"+fristTime);
					}else if(flag==2){
						stringBuffer.append(fristDate+"-"+fristTime);
					}
				}else {
					stringBuffer.append(fristTime);
				}
			}
		}
		return stringBuffer.toString();
	}
	public static TreeMap<String,Integer> timeListToStringList(TreeMap<String,Integer> map){
		int flag=1;
		TreeMap<String,Integer> mapOne=new TreeMap<String,Integer>();

		String fristDate = new String();
		String fristTime="";
		String secondTime="";
		Integer numf=0;
		Integer nums=0;
		Set<String> mapSet = map.keySet();
		Iterator<String> i = mapSet.iterator();
		while (i.hasNext()) {
			if (StringUtils.isEmpty(fristTime)) {
				fristTime = i.next();
				numf = map.get(fristTime);
			} else {
				secondTime =i.next();
				nums = map.get(secondTime);
				numf=map.get(fristTime);
				if (DateUtil.getNextTime2(fristTime, secondTime,numf,nums)) {
					if (flag == 1) {
						fristDate = getString(getNumberString(fristTime, 5, 10), "-", "/");
						flag = 2;
					}
				} else {
					String frist = getString(getNumberString(fristTime, 5, 10), "-", "/");
					if (flag == 1) {
						StringBuffer stringBuffer = new StringBuffer();
						String s=stringBuffer.append(frist + "-"+frist).toString();
						mapOne.put(s,numf);

					} else if (flag == 2) {
						StringBuffer stringBuffer = new StringBuffer();
						String s=stringBuffer.append(fristDate + "-" + frist).toString();
						mapOne.put(s,numf);
						flag = 1;
					}
				}
			}
			if(!StringUtils.isEmpty(secondTime)){
				fristTime=secondTime;
				if(secondTime.equals(map.lastKey())){
					if (DateUtil.getNextTime2(fristTime, secondTime,numf,nums)) {
						if (flag == 1) {
							fristDate = getString(getNumberString(fristTime, 5, 10), "-", "/");
							flag = 2;
						}
					} else {
						String frist = getString(getNumberString(fristTime, 5, 10), "-", "/");
						if (flag == 1) {
							StringBuffer stringBuffer = new StringBuffer();
							String s=stringBuffer.append(frist + "-"+frist).toString();
							mapOne.put(s,nums);



						} else if (flag == 2) {
							StringBuffer stringBuffer = new StringBuffer();
							String s=stringBuffer.append(fristDate + "-" + frist).toString();
							mapOne.put(s,numf);
							flag = 1;
						}
					}
				}
			}else if(map.size()==1){
				String frist = getString(getNumberString(fristTime, 5, 10), "-", "/");
				StringBuffer stringBuffer = new StringBuffer();
				String s=stringBuffer.append(frist + "-"+frist).toString();
				mapOne.put(s,numf);

			}
		}

		return mapOne;
	}

	/**
	 * 指定替换字符
	 * Str指定字符串
	 * str 指定字符
	 * @return
	 */
	public static String getString(String string,String oldStr,String newStr){
		return string.replace(oldStr,newStr);
	}

	/**
	 * 截取一定长度字符串
	 * @param string
	 * @param beginIndex 开始索引
	 * @param endIndex 结束索引
	 * @return
	 */
	public static String getNumberString(String string,int beginIndex,int endIndex){
		return string.substring(beginIndex,endIndex);
	}
}