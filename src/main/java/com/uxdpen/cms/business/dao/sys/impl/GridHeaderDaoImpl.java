package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.cache.I18NCache;
import com.uxdpen.cms.business.common.cache.util.CacheUtil;
import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.sys.GridHeaderDao;
import com.uxdpen.cms.business.entities.base.FProp;
import com.uxdpen.cms.business.entities.sys.GridHeader;

/**
 * <p>自定义表头dao实现</p>
 * 
 * @author flywind 2158
 * @date 2015年12月16日
 * @since 1.0
 */
@Repository
public class GridHeaderDaoImpl extends AbstractFBaseDao<GridHeader> implements GridHeaderDao {
	
	public List<GridHeader> getGridHeader(String tableName, String customerCode){
		StringBuffer hql = new StringBuffer("from GridHeader");
		hql.append(" where tableName =:tableName");
		hql.append(" and customerCode =:customerCode");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		params.put("customerCode", customerCode);
		
		return this.query(hql.toString(), params);
	}
	
	public GridHeader getGridHeader(String tableName, String field, String customerCode){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer hql = new StringBuffer("from GridHeader");

		hql.append(" where tableName =:tableName");
		hql.append(" and customerCode =:customerCode");
		hql.append(" and field =:field");

		params.put("tableName", tableName);
		params.put("customerCode", customerCode);
		params.put("field", field);
		
		List<GridHeader> res = this.query(hql.toString(), params);
		if(res.size() > 0){
			GridHeader gh = res.get(0);
			return gh;
		}
		
		return null;
	}

	@Override
	public List<GridHeader> getGridHeader(String customerCode) {
		StringBuffer hql = new StringBuffer("from GridHeader");	
		hql.append(" where  customerCode =:customerCode");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerCode", customerCode);
		return this.query(hql.toString(), params);
	}
	
	@Override
	public List<GridHeader> getGridHeaderByLanguage(String customerCode, String language) {
		String languageField = "";
		if("zh-cn".equalsIgnoreCase(language)){
			languageField = "titleZhCn";
		}else if("en".equalsIgnoreCase(language)){
			languageField = "titleEn";
		}
		System.out.println(languageField);
		//String languageField = "title" + language.substring(0, 1).toLowerCase() + language.substring(1);
		StringBuffer hql = new StringBuffer("SELECT new org.flywind.business.entities.sys.GridHeader(tableName, field, ");
		hql.append(languageField).append(") FROM GridHeader WHERE customerCode = :customerCode");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		return this.query(hql.toString(), params);
	}
	

	@Override
	public long saveGridHeader(GridHeader gridHeader) {
		
		return  super.save(gridHeader);
	}
	
	@Override
	public void update(FProp prop, String customerCode, String language) throws Exception {
		List<String> fields = prop.getFieldNames();
		String tableName = prop.getTableName();
		String hql = "UPDATE GridHeader SET titleCn = :titleCn WHERE customerCode = :customerCode AND tableName = :tableName AND field = :field";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		params.put("tableName", tableName);
		
		Map<String, Map<String, String>> propMap = I18NCache.getResource(customerCode, language);
		for (int i = 0; i < fields.size(); i++) {
			String fname = fields.get(i);
			params.put("field", fname);
			String val = prop.getFieldValue(fname);
			params.put("titleCn", val);
			propMap.get(tableName).put(fname, val);
			this.executeHql(hql, params);
		}
		
		//更新REDIS缓存
		String key = I18NCache.getCacheKey(customerCode, language);
		CacheUtil.setAttribute(key, propMap);
	}

}
