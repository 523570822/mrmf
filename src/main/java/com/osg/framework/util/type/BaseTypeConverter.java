package com.osg.framework.util.type;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 基础类型转换工具（带保护）
 * 
 * @author xiangf
 * @since JDK5.0
 * 
 */
public class BaseTypeConverter {

	private static Log log = LogFactory.getLog(BaseTypeConverter.class);

	/**
	 * Integer转换为int，为空返回0
	 * 
	 * @param arg
	 *            Integer
	 * @return int
	 */
	public static int transInteger(Integer arg) {
		return (arg == null) ? 0 : arg.intValue();
	}

	/**
	 * Long转换为long，为空返回0
	 * 
	 * @param arg
	 *            Long
	 * @return long
	 */
	public static long transLong(Long arg) {
		return (arg == null) ? 0 : arg.longValue();
	}

	/**
	 * Double转换为double，为空返回0
	 * 
	 * @param arg
	 *            Double
	 * @return double
	 */
	public static double transDouble(Double arg) {
		return (arg == null) ? 0 : arg.doubleValue();
	}

	/**
	 * Float转换为float，为空返回0
	 * 
	 * @param arg
	 *            Float
	 * @return float
	 */
	public static float transFloat(Float arg) {
		return (arg == null) ? 0 : arg.floatValue();
	}
	
	/**
	 * Date类型转换为String，为空返回null，格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String transDate(java.util.Date date) {
		String result = null;
		if (date != null) {
			DateFormat format = new SimpleDateFormat(YMDHMS_PATTERN);
			result = format.format(date);
		}
		return result;
	}
	/**
	 * String转换为Date，为空或非法格式返回null
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date transDateStr(String dateStr) {
		DateFormat format = DateFormat.getDateTimeInstance();
		Date result = null;
		if (dateStr != null && !"".equals(dateStr)) {
			try {
				result = format.parse(dateStr);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return result;
	}

	/**
	 * Timestamp类型转换为String，为空返回null，格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param arg
	 *            Timestamp
	 * @return String
	 */
	public static String transTime(Timestamp arg) {
		String result = null;
		if (arg != null) {
			Date d = new Date(arg.getTime());
			result = transDate(d);
		}
		return result;
	}

	/**
	 * String转换为Timestamp，为空或非法格式返回null
	 * 
	 * @param timeStr
	 *            String
	 * @return Timestamp
	 */
	public static Timestamp transTimeStr(String timeStr) {
		Timestamp result = null;
		Date d = transDateStr(timeStr);
		if (d != null) {
			result = new Timestamp(d.getTime());
		}
		return result;
	}

	private static final java.lang.String YEAR_PATTERN 	= "yyyy";	
	private static final java.lang.String YM_PATTERN 	= "yyyy-MM";	
	private static final java.lang.String YMD_PATTERN 	= "yyyy-MM-dd";
	private static final java.lang.String YMDHM_PATTERN = "yyyy-MM-dd HH:mm";
	private static final java.lang.String YMDHMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final java.lang.String YMDHMSS_PATTERN= "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * String转换为Timestamp，为空或非法格式返回null
	 * 由String的长度来判定Timestamp的精度
	 * @param timeStr
	 *            String
	 * @return Timestamp
	 */
	public static Timestamp parseTimestamp(String timeStr) {
		Timestamp result = null;
		Date d = parse(timeStr);
		if (d != null) {
			result = new Timestamp(d.getTime());
		}
		return result;
	}
	/**
	 * String转换为Date，为空或非法格式返回null
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date parse(String dateStr) {
		Date result = null;
		if (!BaseTypeUtil.isEmptyString(dateStr)) {
			try {
				String pattern = YMDHMS_PATTERN;
				if(dateStr.length()<=4){
					pattern = YEAR_PATTERN;
				}else if(dateStr.length()<=7){
					pattern = YM_PATTERN;
				}else if(dateStr.length()<=10){
					pattern = YMD_PATTERN;
				}else if(dateStr.length()<=16){
					pattern = YMDHM_PATTERN;
				}else if(dateStr.length()>19){
					pattern = YMDHMSS_PATTERN;
				}
				result = new SimpleDateFormat(pattern).parse(dateStr);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return result;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(currentDate().getTime());
	}

	/**
	 * 获取当前时间戳的字符串
	 * @return
	 */
	public static String currentTimestampToString() {
		return currentTimestamp().toString();
	}
}
