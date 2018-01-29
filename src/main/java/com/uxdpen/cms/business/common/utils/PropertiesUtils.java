package com.uxdpen.cms.business.common.utils;

import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Properties util
 * properties文件加载类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class PropertiesUtils 
{
	private static  PropertiesUtils singl = null;
	private static Properties properties;
	
    public static PropertiesUtils getInstenc(){
    	return singl == null ? new PropertiesUtils() : singl;
    }
    
    public static void setProperties(Properties properties){
    	PropertiesUtils.properties = properties;
    }
    
    public static Properties getProperties(){
    	return PropertiesUtils.properties;
    }
	
    public static String getProperty(String key){
    	return properties.getProperty(key);
    }
    
    public String getVlaue(String key){
    	return properties.getProperty(key);
    }

    public String getVlaue(String key,String defaultValue){
    	if(StringUtils.isBlank(properties.getProperty(key))){
    		return defaultValue;
    	}
    	return properties.getProperty(key);
    }
}
