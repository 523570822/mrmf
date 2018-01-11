package com.mrmf.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekDay {
private String week;
private String day;
private int daytime;  // 设置天，格式yyyyMMdd
public String getWeek() {
	return week;
}
public void setWeek(String week) {
	this.week = week;
}
public String getDay() {
	return day;
}
public void setDay(String day) {
	this.day = day;
}

public int getDaytime() {
	return daytime;
}
public void setDaytime(int daytime) {
	this.daytime = daytime;
}
@Override
	public String toString() {
		return  "day:"+day+"==week:"+week+"=="+daytime;
	}
	/**
	 * 将字符串类型的日期转换成int类型
	 * @param date  yyyyMMdd
	 * @return
	 */
	public int DateparseInt(String date){
		int intdate=0;
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
			Date ttt=sdf1.parse(date);
			String tstring=sdf1.format(ttt);
			intdate=Integer.parseInt(tstring);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return intdate;
	}
	public Date intParseDate(int day){
		String d=day+"";
		Date time=null;
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
		try {
			time=sdf1.parse(d);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	public static String getWeekByDate(int date,int day){
		if(date == day) {
			return "今天";
		} else if(date==day+1) {
			return "明天";
		} else {
			String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六",};  
	        Calendar cal = Calendar.getInstance();  
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	        Date date1 = null;
			try {
				date1 = df.parse(String.valueOf(date));
			} catch (ParseException e) {
				throw new RuntimeException("转换日期异常!!!");
			}
	        cal.setTime(date1);  
	        int week_index = cal.get(Calendar.DAY_OF_WEEK) -1;  
	        if(week_index<0){  
	            week_index = 0;  
	        }   
	        return weeks[week_index]; 
		}
         
    }  

}
