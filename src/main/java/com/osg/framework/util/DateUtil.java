package com.osg.framework.util;

import com.mrmf.entity.organPisition.DayBean;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期、时间处理类
 * 
 * @author fengdzh
 * 
 */
public class DateUtil {
	/** 年月日模式字符串 */
	public static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";

	/** 时分秒模式字符串 */
	public static final String HOUR_MINUTE_SECOND_PATTERN = "HH:mm:ss";

	/** 年月日时分秒模式字符串 */
	public static final String YMDHMS_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 年月日时分 */
	public static final String YMDHM_PATTERN = "yyyy-MM-dd HH:mm";

	public static final String YEAR_MONTH = "yyyy-MM";

	public static final String MONTH_DAY = "MM-dd";

	public static final String HOUR_MIN = "HH:mm";

	public static final String YMDHMS_PATTERNS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final long PARTY_ONE_DAY = 24 * 60 * 60 * 1000;

	/** 年月日时分秒没有空格和横杠 */
	public static final String DATE_TIME_NO_SLASH = "yyyyMMddHHmmss";

	/**
	 * 得到时间差;
	 * 
	 * @param parseTime
	 * @return
	 */
	public static String getTimeGaps(Timestamp parseTime) {
		String timeDistance = "";
		String minUnit = "";
		String secondUnit = "";
		String beforeUnit = "";
		String todayUnit = "";
		try {
			// 由于耦合度太高 去掉了资源
			minUnit = "分钟";
			secondUnit = "秒";
			beforeUnit = "前";
			todayUnit = "今天";
		} catch (Exception e) {
			return "";
		}
		if (parseTime != null) {
			long distance = (new Date().getTime() - parseTime.getTime());
			if (distance < 1) {
				timeDistance = 1 + secondUnit + beforeUnit;
				return timeDistance;
			}

			int t1 = getDay(new Date());
			int t2 = getDay(new Date(parseTime.getTime()));
			int t = t1 - t2; // 是否是今天发的消息
			if (distance < 3600000 * 24 && (t == 0)) {
				int hours = (int) ((distance) / 3600000);
				if (hours > 0) {
					timeDistance = todayUnit + " " + format(new Date(parseTime.getTime()), HOUR_MIN);
					return timeDistance;
				}
				int minutes = (int) (((distance) / 1000 - hours * 3600) / 60);
				if (minutes > 0) {
					timeDistance = minutes + minUnit + beforeUnit;
					return timeDistance;
				}
				int second = (int) ((distance) / 1000 - hours * 3600 - minutes * 60) + 1;
				if (second > 0) {
					timeDistance = second + secondUnit + beforeUnit;
					return timeDistance;
				}
			} else {
				timeDistance = DateUtil.format(new Date(parseTime.getTime()), YMDHM_PATTERN);
			}
		}
		return timeDistance;
	}

	/**
	 * 得到时间差;
	 * 
	 * @param parseTime
	 * @return
	 */
	public static String timeStampToString(Timestamp parseTime) {
		String timeDistance = DateUtil.format(new Date(parseTime.getTime()), DateUtil.YMDHMS_PATTERN);
		return timeDistance;
	}

	/**
	 * 得到时间差;
	 * 
	 * @param parseTime
	 * @return
	 */
	public static String timeStampToString(Timestamp parseTime, String YMDHM_PATTERN) {
		String timeDistance = DateUtil.format(new Date(parseTime.getTime()), YMDHM_PATTERN);
		return timeDistance;
	}

	/**
	 * 判断间隔时间是否超出预定
	 * 
	 * @param //startTime开始时间
	 * @param //endTime结束时间
	 * @param //预定时间
	 *            (ms毫秒)
	 * @return
	 */
	public static Boolean getTimeGaps(Timestamp startTime, Timestamp endTime, long gap) {
		long distance = (endTime.getTime() - startTime.getTime());
		if (distance < gap) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取 当前时间   yyyy-MM-dd
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date currentDate(Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(date);
		Date d = df.parse(s);
		return d;
	}

	/**
	 * 获得当前时间
	 * @return 返回当前时间
	 */
	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 从当前时间 并返回Timestamp类型时间。
	 * 
	 * @return 返回当前时间
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(currentDate().getTime());
	}

	/**
	 * 当前年
	 * @return
	 */
	public static int getCurrentYear() {
		return getYear(currentDate());
	}

	/**
	 * 得到时间对应字符串
	 * @return
	 */
	public static String currentTimestampToString() {
		return currentTimestamp().toString();
	}

	/**
	 * 获取给定日期对象的年
	 * @param date
	 * 日期对象
	 * @return 年
	 */
	public static int getYear(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取给定日期对象的月
	 * @param date 日期对象
	 * @return 月
	 */
	public static int getMonth(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取给定日期对象的天
	 * 
	 * @param date
	 *            日期对象
	 * @return 天
	 */
	public static int getDay(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}

	/**
	 * 获取给定日期对象的时
	 * @param date
	 *            日期对象
	 * @return 时
	 */
	public static int getHour(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取给定日期对象的分
	 * 
	 * @param date
	 *            日期对象
	 * @return 分
	 */
	public static int getMinute(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取给定日期对象的秒
	 * 
	 * @param date
	 *            日期对象
	 * @return 秒
	 */
	public static int getSecond(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取传入日期的年和月的Integer形式（yyyyMM）。
	 * 
	 * @param date
	 *            要转换的日期对象
	 * @return 转换后的Integer对象
	 */
	public static Integer getYearMonth(final Date date) {
		return new Integer(format(date, "yyyyMM"));
	}
	
	
	
	/**
	 * 将年月的整数形式（yyyyMM）转换为日期对象返回。
	 * 
	 * @param yearMonth
	 *            年月的整数形式（yyyyMM）
	 * @return 日期类型
	 * @throws ParseException
	 */
	public static Date parseYearMonth(final Integer yearMonth) throws ParseException {
		return parse(String.valueOf(yearMonth), "yyyyMM");
	}

	/**
	 * 将年月的整数形式（yyyyMM）转换为日期对象返回。
	 * @param //yearMonth
	 *            年月的整数形式（yyyyMMdd）
	 * @return 日期类型
	 * @throws ParseException
	 */
	public static Date parseYearMonthDay(final Integer yearMonthDay) throws ParseException {
		return parse(String.valueOf(yearMonthDay), "yyyyMMdd");
	}

	/**
	 * 将某个日期增加指定年数，并返回结果。如果传入负数，则为减。
	 * 
	 * @param date
	 *            要操作的日期对象
	 * @param ammount
	 *            要增加年的数目
	 * @return 结果日期对象
	 */
	public static Date addYear(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, ammount);
		return c.getTime();
	}
	/**
	 * 将某个日期增加指定月数，并返回结果。如果传入负数，则为减。
	 *
	 * @param date
	 *            要操作的日期对象
	 * @param ammount
	 *            要增加月的数目
	 * @return 结果日期对象
	 */
	public static Date addMonth(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, ammount);
		return c.getTime();
	}

	/**
	 * 将某个日期增加指定天数，并返回结果。如果传入负数，则为减。
	 * 
	 * @param date
	 *            要操作的日期对象
	 * @param ammount
	 *            要增加天数
	 * @return 结果日期对象
	 */
	public static Date addDate(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, ammount);
		return c.getTime();
	}

	/**
	 * 获取昨天日期
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Date today = currentDate();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	
	
	/**
	 * 改变日期的小时
	 * @param date
	 * @param hour 在一天时间的小时  负数表示减去几个小时   整数表示增加几个小时
	 * @return 
	 */
	public static Date setDayHour(Date date,int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}

	/**
	 * 根据传入的日期格式化pattern将传入的日期格式化成字符串。
	 * 
	 * @param date
	 *            要格式化的日期对象
	 * @param pattern
	 *            日期格式化pattern
	 * @return 格式化后的日期字符串
	 */
	public static String format(final Date date, final String pattern) {
		if (null == date) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 将传入的日期按照默认形势转换成字符串（yyyy-MM-dd）
	 * 
	 * @param date
	 *            要格式化的日期对象
	 * @return 格式化后的日期字符串
	 */
	public static String format(final Date date) {
		return format(date, YEAR_MONTH_DAY_PATTERN);
	}
	public static String formatTime(Date date,int type) {
		if(type==2){
			return String.valueOf(getYear(date));
		}else if(type==1){
			return format(date, YEAR_MONTH);
		}else {
			return format(date, YEAR_MONTH_DAY_PATTERN);
		}
	}

	/**
	 * 根据传入的日期格式化patter将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @param pattern
	 *            日期格式化pattern
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不合法
	 */
	public static Date parse(final String dateStr, final String pattern) throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(dateStr);
	}

	/**
	 * 将传入的字符串按照默认格式转换为日期对象（yyyy-MM-dd）
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不符合默认格式（如果传入的字符串格式不合法）
	 */
	public static Date parse(final String dateStr) throws ParseException {
		// modify by liaoxl 支持两种格式的时间yyyy-MM-dd和yyyy-MM-dd HH:mm:ss
		if (dateStr.length() == YEAR_MONTH_DAY_PATTERN.length()) {
			return parse(dateStr, YEAR_MONTH_DAY_PATTERN);
		} else if (dateStr.length() == YMDHMS_PATTERN.length()) {
			return parse(dateStr, YMDHMS_PATTERN);
		} else {
			return parse(dateStr, YEAR_MONTH_DAY_PATTERN);
		}
	}

	private static Date parse2(final String dateStr) throws ParseException {
		// modify by liaoxl 支持两种格式的时间yyyy-MM-dd和yyyy-MM-dd HH:mm:ss
		if (dateStr.length() == YEAR_MONTH_DAY_PATTERN.length()) {
			return parse(dateStr, YEAR_MONTH_DAY_PATTERN);
		} else if (dateStr.length() == YMDHMS_PATTERN.length()) {
			return parse(dateStr, YMDHMS_PATTERN);
		} else if (dateStr.length() == YMDHMS_PATTERNS.length()) {
			return parse(dateStr, YMDHMS_PATTERNS);
		} else if (dateStr.length() > YMDHMS_PATTERN.length() && dateStr.length() < YMDHMS_PATTERNS.length()) {
			return parse(dateStr, YMDHMS_PATTERNS);
		} else {
			return parse(dateStr, YEAR_MONTH_DAY_PATTERN);
		}
	}

	/**
	 * 要进行合法性验证的年月数值
	 * 
	 * @param yearMonth
	 *            验证年月数值
	 * @return 年月是否合法
	 */
	public static boolean isYearMonth(final Integer yearMonth) {
		String yearMonthStr = yearMonth.toString();
		return isYearMonth(yearMonthStr);
	}

	/**
	 * 要进行合法性验证的年月字符串
	 * 
	 * @param yearMonthStr
	 *            验证年月字符串
	 * @return 年月是否合法
	 */
	public static boolean isYearMonth(final String yearMonthStr) {
		if (yearMonthStr.length() != 6)
			return false;
		else {
			String yearStr = yearMonthStr.substring(0, 4);
			String monthStr = yearMonthStr.substring(4, 6);
			try {
				int year = Integer.parseInt(yearStr);
				int month = Integer.parseInt(monthStr);
				if (year < 1800 || year > 3000) {
					return false;
				}
				if (month < 1 || month > 12) {
					return false;
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * 根据传入的日期格式化patter将传入的字符串转换成Timstamp对象
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @param pattern
	 *            日期格式化pattern
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不合法
	 */
	public static Timestamp parseTimestamp(final String dateStr, final String pattern) throws ParseException {
		return new Timestamp(parse(dateStr, pattern).getTime());
	}

	/**
	 * 将传入的字符串按照默认格式转换为Timestamp对象（yyyy-MM-dd）
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不符合默认格式（如果传入的字符串格式不合法）
	 */
	public static Timestamp parseTimestamp(final String dateStr) throws ParseException {
		return new Timestamp(parse2(dateStr).getTime());
	}

	public static int getCurrentMonth() {
		return getMonth(currentDate());
	}

	/**
	 * @param currentTimestamp
	 * @param dayGap
	 * @return
	 */
	public static Timestamp getBeforeTimeStamp(Timestamp currentTimestamp, long dayGap) {
		Timestamp timestamp = null;
		long d = currentTimestamp.getTime() - DateUtil.PARTY_ONE_DAY * dayGap;
		timestamp = new Timestamp(d);
		return timestamp;
	}

	public static Date getDate(Long timeline) {
		return new Date(timeline);
	}

	public static String getDate(String month, String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		java.util.Date d = new java.util.Date();
		;
		String str = sdf.format(d);
		String nowmonth = str.substring(5, 7);
		String nowday = str.substring(8, 10);
		String result = null;

		int temp = Integer.parseInt(nowday) - Integer.parseInt(day);
		switch (temp) {
		case 0:
			result = "今天";
			break;
		case 1:
			result = "昨天";
			break;
		case 2:
			result = "前天";
			break;
		default:
			StringBuilder sb = new StringBuilder();
			sb.append(Integer.parseInt(month) + "月");
			sb.append(Integer.parseInt(day) + "日");
			result = sb.toString();
			break;
		}
		return result;
	}

	public static String getTime(int timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = null;
		try {
			java.util.Date currentdate = new java.util.Date();// 当前时间

			long i = (currentdate.getTime() / 1000 - timestamp) / (60);
			System.out.println(currentdate.getTime());
			System.out.println(i);
			Timestamp now = new Timestamp(System.currentTimeMillis());// 获取系统当前时间
			System.out.println("now-->" + now);// 返回结果精确到毫秒。

			String str = sdf.format(new Timestamp(IntToLong(timestamp)));
			time = str.substring(11, 16);

			String month = str.substring(5, 7);
			String day = str.substring(8, 10);
			System.out.println(str);
			System.out.println(time);
			System.out.println(getDate(month, day));
			time = getDate(month, day) + time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	// java Timestamp构造函数需传入Long型
	public static long IntToLong(int i) {
		long result = (long) i;
		result *= 1000;
		return result;
	}

	// java Timestamp构造函数需传入Long型
	public static String getNextMonth() {
		Date date = new Date();
		String hms = format(date, "HH:mm:ss");
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));// 取到年份值
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date)) + 1;// 取到月份值
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));// 取到天值
		if (month == 0) {
			year -= 1;
			month = 12;
		} else if (day > 28) {
			if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29;
				} else
					day = 28;
			} else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
				day = 30;
			}
		}
		String y = year + "";
		String m = "";
		String d = "";
		if (month < 10)
			m = "0" + month;
		else
			m = month + "";
		if (day < 10)
			d = "0" + day;
		else
			d = day + "";
		// System.out.println(y + "-" + m + "-" + d);
		return y + "-" + m + "-" + d + " " + hms;
	}

	/**
	 * 判断给定时间是否属于今天
	 * 
	 * @param parseTime
	 * @return
	 */
	public static boolean checkToDay(Timestamp parseTime) {
		if (parseTime != null) {
			long distance = (new Date().getTime() - parseTime.getTime());
			int t1 = getDay(new Date());
			int t2 = getDay(new Date(parseTime.getTime()));
			int t = t1 - t2; // 是否是今天发的消息
			if (distance < 3600000 * 24 && (t == 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断今天属于本周几
	 * 
	 * @param// parseTime
	 * @return 1.星期天 2.星期一 3.星期二 4.星期三 5.星期四 6.星期五 7.星期六
	 */
	public static int checkDayToWeek() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获今天所属 某一周的,星期一的时间
	 * 
	 * @param
	 * @return
	 */
	public static Date getMonday() throws ParseException {
		int toDay = DateUtil.checkDayToWeek();
		Date date = new Date();
		if (toDay == 3) {
			date = DateUtil.addDate(date, -1);
		} else if (toDay == 4) {
			date = DateUtil.addDate(date, -2);
		} else if (toDay == 5) {
			date = DateUtil.addDate(date, -3);
		} else if (toDay == 6) {
			date = DateUtil.addDate(date, -4);
		} else if (toDay == 7) {
			date = DateUtil.addDate(date, -5);
		} else if (toDay == 1) {
			date = DateUtil.addDate(date, -6);
		}
		return date;
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		return DateUtil.format(calendar.getTime(), YEAR_MONTH_DAY_PATTERN);
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateUtil.format(calendar.getTime(), YEAR_MONTH_DAY_PATTERN);
	}

	/**
	 * 获取零时整点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getZhengdianDate(Date date) {

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		/*
		 * if ((gc.get(gc.HOUR_OF_DAY) == 0) && (gc.get(gc.MINUTE) == 0) &&
		 * (gc.get(gc.SECOND) == 0)) { return new Date(date.getTime() - (24 * 60
		 * * 60 * 1000)); } else { Date date2 = new Date(date.getTime() -
		 * gc.get(gc.HOUR_OF_DAY) * 60 * 60 1000 - gc.get(gc.MINUTE) * 60 * 1000
		 * - gc.get(gc.SECOND) 1000 - 24 * 60 * 60 * 1000); return date2; }
		 */
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		return gc.getTime();
	}

	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}
	
	/**
	 * 计算出相差的分钟数
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long differMinutes(Date time1,Date time2) {
		return (time1.getTime()-time2.getTime())/(1000*60L);
	}
	/**
	 * 判断两天是否是同一天
	 */
	public static boolean isSameDate(Date date1, Date date2) {
	       Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(date1);
	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date2);
	       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth = isSameYear
	               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	       boolean isSameDate = isSameMonth
	               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                       .get(Calendar.DAY_OF_MONTH);
	       return isSameDate;
	}
	public static void main(String[] args) throws Exception {
		/*
		 * Timestamp time = new Timestamp(new Date().getTime()); String time2 =
		 * "2014-03-21 21:22:33"; String time3=
		 * DateUtil.format(DateUtil.parseTimestamp(time2),DateUtil.
		 * YMDHMS_PATTERNS); System.out.println(time3);
		 */
	    //	System.out.println(DateUtil.format(DateUtil.setDayHour(new Date(), 2), "yyyyMMdd HH:mm:ss"));
		//System.out.println(getZhengdianDate(currentDate()));
		//differMinutes();
		//System.out.println((1470653511298L-1470618000000L)/(1000*60L));
		//System.out.println(DateUtil.getDay(new Date()));
	}
	public static String getDateStr(Date date,String datesimple){
		SimpleDateFormat format = new SimpleDateFormat(datesimple);
		return format.format(date);
	}
	/**
	 * 根据日期获取这一天的开始时间和结束时间
	 * @param //date  yyyy-MM-dd
	 * @return
	 */
	public static String[] getDateStartEnd(String dateStr){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			 date = sdf1.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date start = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		Date end = calendar.getTime();
		
		String startstr = sdf.format(start);
		String endstr = sdf.format(end);
		String[] str = new String[2];
		str[0] = startstr;
		str[1] = endstr;
		return str;
	}

	static final String DAY_NULL   = "0";    //  占位日
	static final String DAY_PAST   = "1";    //  已经过去的日
	static final String DAY_BLANK  = "2";    //  未出租日
	static final String DAY_RENTED = "3";    //  已出租日

	/**
	 * 格式化日历，
	 * @param currentYear 年
	 * @param currentMonth 月
	 * @param limitDate 当前时间
	 * @param rentedDayList 日集合
	 * @return
	 */
	public static List<DayBean> build(int currentYear, int currentMonth, List<String> rentedDayList, Date limitDate){
		int calendar_month = currentMonth - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(currentYear, calendar_month, 1);
		int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<DayBean> daybeans = new ArrayList<>();
		for (int i = 1 ; i < firstDayOfWeek ; i ++){
			DayBean bean = new DayBean();
			bean.setState(DAY_NULL);
			daybeans.add(bean);
		}
		Long limitDateLong = null;
		if (limitDate != null){
			limitDateLong = NumberUtils.toLong(DateFormatUtils.format(limitDate, "yyyyMMdd"));
		}
		for (int i = 0 ; i < maxDay ; i ++){
			String tempMonth = StringUtils.leftPad(String.valueOf((currentMonth)), 2, "0");
			String tempDay = StringUtils.leftPad(String.valueOf((i + 1)), 2, "0");
			String tempDate = currentYear + "-" + tempMonth + "-" + tempDay;

			long tempDateLong = NumberUtils.toLong(currentYear + tempMonth + tempDay);
			DayBean bean = new DayBean();
			bean.setTitle(i+1);
			bean.setTime(tempDate);
			bean.setMonth(currentMonth);
			if (rentedDayList.contains(tempDate))//对比时间字符串，有说明租出去了 =3
				bean.setState(DAY_RENTED);
			else if (limitDateLong != null && tempDateLong < limitDateLong) { //过去的日子=1
				bean.setState(DAY_PAST);
			}
			else {
				bean.setState(DAY_BLANK); //未出租 =2
			}
			daybeans.add(bean);
		}
		for (int i = 0 ; i < daybeans.size() ; i ++){
			if (i % 7 == 0)
				System.out.println("");
			System.out.print(daybeans.get(i).getTitle() + "\t");
		}
		return daybeans;
	}
	public static List<DayBean> sumDate(int currentYear, int currentMonth, List<String> rentedDayList, Date limitDate, List<String> detailsTimeList){
		int calendar_month = currentMonth - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(currentYear, calendar_month, 1);
		int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<DayBean> daybeans = new ArrayList<>();
		for (int i = 1 ; i < firstDayOfWeek ; i ++){
			DayBean bean = new DayBean();
			bean.setState(DAY_NULL);
			daybeans.add(bean);
		}
		Long limitDateLong = null;
		if (limitDate != null){
			limitDateLong = NumberUtils.toLong(DateFormatUtils.format(limitDate, "yyyyMMdd"));
		}
		for (int i = 0 ; i < maxDay ; i ++){
			String tempMonth = StringUtils.leftPad(String.valueOf((currentMonth)), 2, "0");
			String tempDay = StringUtils.leftPad(String.valueOf((i + 1)), 2, "0");
			String tempDate = currentYear + "-" + tempMonth + "-" + tempDay;

			long tempDateLong = NumberUtils.toLong(currentYear + tempMonth + tempDay);
			DayBean bean = new DayBean();
			bean.setTitle(i+1);
			bean.setTime(tempDate);
			bean.setMonth(currentMonth);
			int dateFlag = 0;
			if (limitDateLong != null && tempDateLong < limitDateLong||detailsTimeList.contains(tempDate)) { //过去的日子=1
				bean.setState(DAY_PAST);
			} else if (rentedDayList.contains(tempDate)) {//对比时间字符串，有说明租出去了 =3
				bean.setState(DAY_BLANK); //未出租 =2
				for(String str : rentedDayList){
					if(str.contains(tempDate)){
						dateFlag+=1;
					}
				}
			}
			else {
				bean.setState(DAY_BLANK); //未出租 =2
			}
			bean.setFlag(dateFlag);
			daybeans.add(bean);
		}
		Calendar calenda = Calendar.getInstance();
		int lastDay = getDaysByYearMonth(currentYear,currentMonth);
		calenda.set(currentYear, calendar_month, lastDay);
		int lastDayOfWeek = calenda.get(Calendar.DAY_OF_WEEK);
		for (int i = 1 ; i < 8-lastDayOfWeek ; i ++){
			DayBean bean = new DayBean();
			bean.setState(DAY_NULL);
			daybeans.add(bean);
		}
		return daybeans;
	}

	/**
	 * 获得指定月份的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据年，月，日转化时间格式
	 * @param flag  0 返回当天起   1 当天末
	 * @return
	 */
	public static Date getDateToStr(int year,int month,int day,int flag){
		String str = year+"-"+month+"-"+day;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dd = sdf.parse(str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);
			//时分秒（毫秒数）
			long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
			//凌晨00:00:00
			cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

			if (flag == 0) {
				return cal.getTime();
			} else if (flag == 1) {
				//凌晨23:59:59
				cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
			}
			return cal.getTime();
		}catch (Exception e){

		}
		return null;
	}

	/**
	 * 返回指定年月的第一天和最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Map getFristAndLast(int year,int month){
		String str = year+"-"+month;
		Calendar calendar=new GregorianCalendar();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM");
		Map map = new HashMap();
		try {
			Date date=sf.parse(str);
			calendar.setTime(date);
			calendar.add(calendar.DATE, 0);//因为格式化时默认了DATE为本月第一天所以此处为0
			map.put("frist",sdf.format(calendar.getTime()));
			System.out.println("获取指定月的第一天："+sdf.format(calendar.getTime()));
			calendar.roll(calendar.DATE, -1);//api解释roll()：向指定日历字段添加指定（有符号的）时间量，不更改更大的字段
			System.out.println("获取指定月的最后一天："+sdf.format(calendar.getTime()));
			map.put("last",sdf.format(calendar.getTime()));
		}catch (Exception e){
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 判断nextTime是否是time的下一天
	 */
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
	public static Boolean getNextTime(String time,String nextTime){

		Calendar calendar=new GregorianCalendar();
		try {
			calendar.setTime(sdf1.parse(time));
			calendar.add(Calendar.DATE,1);
			String fristTime = sdf1.format(calendar.getTime());
			if(fristTime.equals(nextTime)){
				return true;
			}else {
				return  false;
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public static Boolean getNextTime2(String time,String nextTime,Integer numf,Integer nums){

		Calendar calendar=new GregorianCalendar();
		try {
			calendar.setTime(sdf1.parse(time));
			calendar.add(Calendar.DATE,1);
			String fristTime = sdf1.format(calendar.getTime());
			if(fristTime.equals(nextTime)&&numf==nums){
				return true;
			}else {
				return  false;
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * string转date
	 */
	public  static Date formatDate(String time){
		try {
			return sdf1.parse(time);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * string格式化string
	 */
	public  static String formatString(String time){
		try {
			Date dateTime = sdf1.parse(time);
			return sdf1.format(dateTime);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 判断两天是否同一天
	 */
	public static Boolean timeIsEqual(String time,String nextTime){
		if(time.equals(nextTime)){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 获得后一天0.00分
	 * @param cl
	 * @return
	 */
	public static String getAfterDay(Date cl){
		Calendar cal = Calendar.getInstance();
		cal.setTime(cl);
		//使用set方法直接设置时间值
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day+1);
		return format(cal.getTime(),YMDHMS_PATTERN);
	}
	/**
	 * 截取时间字符串
	 * @param timeStr  时间类字符串
	 * @param type  0-时分  1-月日
	 * @return
	 */
	public static String getMonthDay(String timeStr,int type){
		String str = "";
		if(type==0){
			int index = timeStr.indexOf(":");
			String hh = "";
			String mm = "";
			if(index==-1){
				hh = "00";
				mm = "00";
			}else {
				hh = timeStr.substring(index-2,index);
				mm = timeStr.substring(index+1,index+3);
			}
			str = hh+":"+mm;
		}else if(type==1){
			String month = timeStr.substring(5,7);
			String day = timeStr.substring(8,10);
			str = month+"月"+day+"日";
		}
		return str;
	}
	public static Date monthLastDay(Date date){
		Calendar a=Calendar.getInstance();
		a.setTime(date);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int MaxDate=a.get(Calendar.DATE);
		return dayEnd(a.getTime(),0);
	}
	/**
	 * 得到指定日期的23：59：59.999
	 * @param date  0-23:59：59   1-00:00:00
	 * @return
	 */
	public static Date dayEnd(final Date date,int flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(flag==0){
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			return cal.getTime();
		}else if(flag==1){
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);
			//时分秒（毫秒数）
			long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
			//凌晨00:00:00
			cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);
			return cal.getTime();
		}
		return null;
	}
	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}
	public static int getMonthSpace(String date1, String date2)
			throws ParseException {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(formatDate(date1));
		c2.setTime(formatDate(date2));
		if(c1.getTimeInMillis() > c2.getTimeInMillis()) return 0;
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);

		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int yearNum = year2-year1;
		if(year2==year1){
			return month2-month1;
		}else {
			return yearNum*12+month2-month1;
		}


	}
	public static int yearDateDiff(String startDate,String endDate){
		Calendar calBegin = Calendar.getInstance(); //获取日历实例
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(formatDate(startDate)); //字符串按照指定格式转化为日期
		calEnd.setTime(formatDate(endDate));
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
	}
}
