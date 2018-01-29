package com.uxdpen.cms.business.common.cache.util;

/**
 * <p>
 * Cache Util
 * 缓存工具类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class CacheUtil {
	
	/**
	 * Store data to the cache
	 * 将数据存放到缓存
	 * 
	 * @param key
	 *        Cache key
	 * @param value
	 *        Cache value
	 * @param timeoutSecends
	 *        Timeout period (SEC)
	 */
	public static void setAttribute(String key, Object value, long timeoutSecends) {
		CacheService.getInstance().save(key, value, timeoutSecends);
	}
	
	/**
	 * Store data to the cache
	 * 将数据存放到缓存
	 * 
	 * @param key
	 *        Cache key
	 * @param value
	 *        Cache value
	 */
	public static void setAttribute(String key, Object value) {
		CacheService.getInstance().save(key, value);
	}
	
	/**
	 * Get data from the cache
	 * 
	 * @param key
	 *        Cache key
	 * @return
	 *        Cache value
	 */
	public static Object getAttribute(String key) {
		return CacheService.getInstance().get(key);
	}
	
	/**
	 * Clear cache
	 * 清除缓存
	 * 
	 * @param key
	 *        Cache key
	 */
	public static void clearCache(String key) {
		CacheService.getInstance().delete(key);
	}

}
