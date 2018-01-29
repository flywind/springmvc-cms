package com.uxdpen.cms.business.dao.base;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * MongoDB DAO
 * 操作MongoDB数据库的DAO
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface MongoBaseDao<T> {
	
	/**
	 * Insert data into the database
	 * 向数据库中插入数据
	 * 
	 * @param o
	 *        Data object
	 * @param tableName
	 *        Table name
	 */
	public void save(T o, String tableName);
	
	/**
	 * Delete data according to the query condition
	 * 根据查询条件删除数据
	 * @param params
	 * 		  Search parameters
	 * @param tableName
	 * 		  Table name
	 */
	public void remove(Map<String, Object> params, String tableName);
	
	/**
	 * Query data, support advanced search, paging query
	 * 查询数据，支持高级搜索，分页查询
	 * 
	 * @param c
	 *        The class type of the result object
	 * @param params
	 *        Advanced search parameters
	 * @param tableName
	 *        Table name
	 * @param paging
	 *        Paging object
	 * @return
	 *        Data list
	 */
	public List<T> findAll(Class<T> c, Map<String, Object> params, String tableName, FPage paging);
	
	/**
	 * Number of records (rows) in the corresponding table
	 * 统计对应表中的总记录(行)数
	 * 
	 * @param params
	 *        Advanced search parameters
	 * @param tableName
	 *        Table name
	 * @return
	 *        Total record (row) number
	 */
	public Long count(Map<String, Object> params, String tableName);

}
