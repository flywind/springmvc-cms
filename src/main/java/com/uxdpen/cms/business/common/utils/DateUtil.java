package com.uxdpen.cms.business.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.uxdpen.cms.business.common.constants.FBaseConstants;


/**
 * <p>
 * Tools for processing date and time
 * 处理日期时间的工具类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class DateUtil {
	
	/**
	 * @Log
	 */
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	
	/**
	 * Converts the date of the string type to the date of the Date type.
	 * 将字符串类型的日期，转换成Date类型的日期
	 * For example:
	 *     "yyyy-MM-dd" and  "yyyy-MM-dd HH:mm:ss"
	 * @param time
	 *        time
	 * @return Date
	 *         Date Object
	 */
	public static Date toDate(String time) {
		try {
			SimpleDateFormat sdf = null;
			if (StringUtils.isNotEmpty(time)) {
				if (time.length() == 10) {
					sdf = new SimpleDateFormat(FBaseConstants.DATE_FORMAT_YMD);
				} else {
					sdf = new SimpleDateFormat(FBaseConstants.DATE_FORMAT_YMDHMS);
				}
				return sdf.parse(time);
			}
		} catch (Exception e) {
			logger.error("Fails to convert a date in a string type to a Date type. time：" + time, e);
		}
		return null;
	}
	
	/**
	 * Converts the date of the string type to the date of the Date type.
	 * 将字符串类型的日期，转换成Date类型的日期。
	 * 
	 * @param time
	 *        Date string
	 * @param pattern
	 *        Transformation expression
	 * @return
	 *        Date object
	 */
	public static Date toDate(String time, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(time);
		} catch (Exception e) {
			logger.error("Fails to convert a date in a string type to a Date type. time：" + time, e);
		}
		return null;
	}
	
	/**
	 * Converts the date of the Date type to the date of the string type
	 * 将Date类型的日期转换成字符串类型的日期
	 * 
	 * @param date
	 *       Date object
	 * @param pattern
	 *       Transformation expression
	 * @return
	 *       Date string
	 */
	public static String toString(Date date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		} catch (Exception e) {
			logger.error("Fails to convert the date of the Date type into a date string type.", e);
		}
		return FBaseConstants.EMPTY_STRING;
	}
	
	/**
	 * Gets the current time string
	 * 获取当前时间字符串
	 * 
	 * @param pattern
	 *       Transformation expression
	 * @return
	 *       Date string
	 */
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	/**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
