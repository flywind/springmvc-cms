package com.uxdpen.cms.business.common.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.uxdpen.cms.business.common.cache.util.CacheUtil;
/**
 * <p>
 * International resource information cache
 * 国际化资源信息的缓存
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class I18NCache {
	
	/**
	 * @Log
	 */
	private static Logger logger = Logger.getLogger(I18NCache.class);
	
	/**
	 * According to the customerCode, access to international information from the cache
	 * 根据客户代码，从缓存中获取国际化信息
	 * 
	 * @param customerCode
	 *        客户代码
	 * @param language
	 *        语言
	 * @return map
	 *        缓存信息的Map。外层Map的key对应数据库中的tableName，里层Map的key对应数据库中的field
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getResource(String customerCode, String language) {
		Map<String, Map<String, String>> i18nMap = new HashMap<String, Map<String,String>>();
		String key = getCacheKey(customerCode, language);
		Map<String,Map<String, String>> i18nObj = (Map<String,Map<String, String>>)CacheUtil.getAttribute(key);
		boolean isNull = false;
		if(i18nObj != null){
			for(String mapkey : i18nObj.keySet()){
				if(i18nObj.get(mapkey) != null){
					for(String childKey : i18nObj.get(mapkey).keySet()){
						if(i18nObj.get(mapkey).get(childKey) == null){
							isNull = true;
							break;
						}
					}
					
				}
			}
		}
		if (isNull || i18nObj == null) {
			/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			GridHeaderService heagerService = wac.getBean("headerService", GridHeaderService.class);
			List<GridHeader> ghList = heagerService.getGridHeaderByLanguage(customerCode, language);
			if (null != ghList && !ghList.isEmpty()) {
				for (int i = 0; i < ghList.size(); i++) {
					GridHeader gh = ghList.get(i);
					Map<String, String> fieldMap = i18nMap.get(gh.getTableName());
					if (null == fieldMap) {
						fieldMap = new HashMap<String, String>();
						i18nMap.put(gh.getTableName(), fieldMap);
					}
					fieldMap.put(gh.getField(), gh.getValue());
				}
				
			}
			CacheUtil.setAttribute(key, i18nMap);
			i18nObj = (Map<String,Map<String, String>>)CacheUtil.getAttribute(key);*/
		}
		
		try {
			i18nMap = (Map<String, Map<String, String>>) i18nObj;
		} catch (Exception e) {
			logger.error("Convert international information in the cache into Map failed.", e);
		}
		
		return i18nMap;
	}

	/**
	 * Get the cache key
	 * 获取缓存key
	 * 
	 * @param customerCode
	 *        客户代码
	 * @param language
	 *        语言
	 * @return String
	 *        缓存key
	 */
	public static String getCacheKey(String customerCode, String language) {
		return "F_i18n_" + customerCode + "_" + language;
	}
	
}
