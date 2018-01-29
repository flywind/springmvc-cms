package com.uxdpen.cms.business.common.utils;

import java.util.Date;

import org.apache.log4j.Logger;

public class RelativeDateUtile {

	private static final Logger logger = Logger.getLogger(RelativeDateUtile.class);
	
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static String ONE_SECOND_AGO;  
    private static String ONE_MINUTE_AGO;  
    private static String ONE_HOUR_AGO;  
    private static String ONE_DAY_AGO;  
    private static String ONE_MONTH_AGO;  
    private static String ONE_YEAR_AGO;
    private static String ONE_YESTERDAY_AGO;
  
    public static String format(Date date, String lang) {  
        long delta = new Date().getTime() - date.getTime();  
        if("zh-cn".equalsIgnoreCase(lang)){
        	ONE_SECOND_AGO = "秒前";
        	ONE_MINUTE_AGO = "分钟前";
        	ONE_HOUR_AGO = "小时前";
        	ONE_DAY_AGO = "天前";
        	ONE_MONTH_AGO = "月前";
        	ONE_YEAR_AGO = "年前";
        	ONE_YESTERDAY_AGO = "昨天";
        }else{
        	ONE_SECOND_AGO = " Second ago";
        	ONE_MINUTE_AGO = " Minute ago";
        	ONE_HOUR_AGO = " Hour ago";
        	ONE_DAY_AGO = " Day ago";
        	ONE_MONTH_AGO = " Month ago";
        	ONE_YEAR_AGO = " Year ago";
        	ONE_YESTERDAY_AGO = " Yesterday";
        }
        if (delta < 1L * ONE_MINUTE) {  
            long seconds = toSeconds(delta);  
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
        }  
        if (delta < 60L * ONE_MINUTE) {  
            long minutes = toMinutes(delta);  
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
        }  
        if (delta < 24L * ONE_HOUR) {  
            long hours = toHours(delta);  
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
        }  
        if (delta < 48L * ONE_HOUR) {  
            return ONE_YESTERDAY_AGO;  
        }  
        if (delta < 30L * ONE_DAY) {  
            long days = toDays(delta);  
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
        }  
        if (delta < 12L * 4L * ONE_WEEK) {  
            long months = toMonths(delta);  
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
        } else {  
            long years = toYears(delta);  
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
        }  
    }  
  
    private static long toSeconds(long date) {  
        return date / 1000L;  
    }  
  
    private static long toMinutes(long date) {  
        return toSeconds(date) / 60L;  
    }  
  
    private static long toHours(long date) {  
        return toMinutes(date) / 60L;  
    }  
  
    private static long toDays(long date) {  
        return toHours(date) / 24L;  
    }  
  
    private static long toMonths(long date) {  
        return toDays(date) / 30L;  
    }  
  
    private static long toYears(long date) {  
        return toMonths(date) / 365L;  
    }  
}
