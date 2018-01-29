package com.uxdpen.cms.business.dao.base;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * <p>
 * Operating database base class, the database to CRUD
 * 操作数据库的基础接口，实现对数据库的增、删、改、查
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public interface FBaseDao<T> {
	
	/**
	 * Get current session
	 * 
	 * @return {@link Session}
	 */
	public Session getSession();
	
	/**
	 * Save an object
	 * @param o
	 * 			Object
	 * @return
	 * 			The unique identity of the object (primary key)
	 */
	public Long save(T o);
	
	/**
	 * Delete an object
	 * @param o
	 * 			Object
	 */
	public void delete(T o);
	
	/**
	 * Delete object based on ID
	 * 根据id删除对象
	 * 
	 * @param c
	 * 			Class type of object
	 * @param id
	 * 			Object id
	 * @return
	 * 			True:deleted successfully, false:delete failed
	 */
	public boolean deleteById(Class<T> c, Long id);
	
	/**
	 * Update an object
	 * 更新一个对象
	 * @param o
	 * 			Object
	 */
	public void update(T o);
	
	/**
	 * Save or update an object
	 * 保存或更新一个对象
	 * @param o
	 * 			Object
	 */
	public void saveOrUpdate(T o);

	/**
	 * Check out the corresponding object by unique identifier (primary key).
	 * 通过唯一标识(主键)查询出对应的对象
	 * 
	 * @param c
	 * 			Class types of the corresponding classes of objects
	 * @param id
	 * 			Unique identifier (primary key)
	 * @return
	 */
	public T getById(Class<T> c, Long id);
	
	/**
	 * Query an object through the HQL statement
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Object
	 */
	public T getByHql(String hql);

	/**
	 * Query an object through the HQL statement
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param hql
	 * 			HQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @return
	 * 			Object
	 */
	public T getByHql(String hql, Map<String, Object> params);
	
	/**
	 * Query an object through the HQL statement
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			Object
	 */
	public T getBySql(String sql);
	
	/**
	 * Query an object through the SQL statement
	 * 通过SQL语句查询出一个对象
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @return
	 * 			Object
	 */
	public T getBySql(String sql, Map<String, Object> params);
	
	/**
	 * Through the HQL statement to query all the objects
	 * 通过HQL语句查询出所有的对象
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Objects collection
	 */
	public List<T> query(String hql);
	
	/**
	 * Through the HQL statement to query all the objects
	 * 通过HQL语句查询出所有的对象
	 * 
	 * @param hql
	 * 			HQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @return
	 * 			Object
	 */
	public List<T> query(String hql, Map<String, Object> params);
	
	/**
	 * Through the HQL statement, query the list of objects in a page
	 * 通过HQL语句，以分页的方式查询出对象列表
	 * 
	 * @param hql
	 * 			HQL statement
	 * @param page
	 * 			Display the first few pages
	 * @param rows
	 * 			Number of records (rows) displayed on a page
	 * @return
	 * 			Objects collection
	 */
	public List<T> query(String hql, int page, int rows);
	
	/**
	 * Through the HQL statement, query the list of objects in a page
	 * 通过HQL语句，以分页的方式查询出对象列表
	 * 
	 * @param hql
	 * 			HQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @param page
	 * 			Display the first few pages
	 * @param rows
	 * 			Number of records (rows) displayed on a page
	 * @return
	 * 			Objects collection
	 */
	public List<T> query(String hql, Map<String, Object> params, int page, int rows);
	
	/**
	 * Through the HQL statement, the total number of records in the table (row)
	 * 通过HQL语句，统计表中总记录(行)数
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Total record (row) number
	 */
	public Long count(String hql);
	
	/**
	 * Through the HQL statement, the total number of records in the table (row)
	 * 通过HQL语句，统计表中总记录(行)数
	 * 
	 * @param hql
	 * 			HQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @return
	 * 			Total record (row) number
	 */
	public Long count(String hql, Map<String, Object> params);
	
	/**
	 * Execute a HQL statement that can be: Insert, Update, Delete, Select
	 * 执行一条HQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Number of affected records (rows)
	 */
	public int executeHql(String hql);
	
	/**
	 * Execute a HQL statement that can be: Insert, Update, Delete, Select
	 * 执行一条HQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param hql
	 * 			HQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @return
	 * 			Number of affected records (rows)
	 */
	public int executeHql(String hql, Map<String, Object> params);
	
	/**
	 * Through the SQL statement to query all the objects
	 * 通过SQL语句查询出所有的对象
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			ResultSet
	 */
	public List<Map> queryBySql(String sql);
	
	/**
	 * Through the SQL statement to query all the objects
	 * 通过SQL语句查询出所有的对象
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @return
	 * 			ResultSet
	 */
	public List<Map> queryBySql(String sql, Map<String, Object> params);
	
	/**
	 * Through the SQL statement, query the list of objects in a page
	 * 通过SQL语句，以分页的方式查询出对象列表
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param page
	 * 			Display the first few pages
	 * @param rows
	 * 			Number of records (rows) displayed on a page
	 * @return
	 * 			Object Collection
	 */
	public List<Map> queryBySql(String sql, int page, int rows);
	
	/**
	 * Through the SQL statement, query the list of objects in a page
	 * 通过SQL语句，以分页的方式查询出对象列表
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @param page
	 * 			Display the first few pages
	 * @param rows
	 * 			Number of records (rows) displayed on a page
	 * @return
	 * 			Object Collection
	 */
	public List<Map> queryBySql(String sql, Map<String, Object> params, int page, int rows);
	
	/**
	 * Query a list of objects
	 * 查询出对象列表
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @param resultValueTypeMap
	 * 			Results the properties of the object and the set of attribute types
	 * @param entityClass
	 * 			The Class type of the result object
	 * @param pagingEntity
	 * 			Paging object
	 * @return
	 * 			Object Collection
	 */
	public List<T> queryObjListBySql(String sql, Map<String, Object> params, 
			Map<String, Type> resultValueTypeMap, Class<T> entityClass, FPage pagingEntity);
	
	/**
	 * Query a list of objects
	 * 查询出对象列表
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in the query statement
	 * @param resultValueTypeMap
	 * 			Results the properties of the object and the set of attribute types
	 * @param entityClass
	 * 			The Class type of the result object
	 * @param pagingEntity
	 * 			Paging object
	 * @return
	 * 			Object Collection
	 */
	public List<T> queryObjListBySql(String sql, String countSql, Map<String, Object> params, 
			Map<String, Type> resultValueTypeMap, Class<T> entityClass, FPage pagingEntity);
	
	/**
	 * Execute a SQL statement that can be: Insert, Update, Delete, Select
	 * 执行一条SQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			Number of affected records (rows)
	 */
	public int executeSql(String sql);
	
	/**
	 * Execute a SQL statement that can be: Insert, Update, Delete, Select
	 * 执行一条SQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in a statistical statement
	 * @return
	 * 			Number of affected records (rows)
	 */
	public int executeSql(String sql, Map<String, Object> params);
	
	/**
	 * Through the SQL statement, the total number of records in the table (row)
	 * 通过SQL语句，统计表中总记录(行)数
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			Total record (row) number
	 */
	public BigInteger countBySql(String sql);
	
	/**
	 * Through the SQL statement, the total number of records in the table (row)
	 * 通过SQL语句，统计表中总记录(行)数
	 * 
	 * @param sql
	 * 			SQL statement
	 * @param params
	 * 			A set of parameters that are needed in a statistical statement
	 * @return
	 * 			Total record (row) number
	 */
	public BigInteger countBySql(String sql, Map<String, Object> params);
	
}
