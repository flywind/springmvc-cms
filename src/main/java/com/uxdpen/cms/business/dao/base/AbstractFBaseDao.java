package com.uxdpen.cms.business.dao.base;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;


/**
 * <p>
 * Operating database base class, the database to CRUD
 * 操作数据库的基类，实现对数据库的增、删、改、查
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public abstract class AbstractFBaseDao<T> implements FBaseDao<T> {
	
	/**
	 * Session Factory
	 * q.setCacheable(true);
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Get current session
	 * 
	 * @return {@link Session}
	 */
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Save an object
	 * @param o
	 * 			Object
	 * @return
	 * 			The unique identity of the object (primary key)
	 */
	@Override
	public Long save(T o) {
		if (null != o) {
			Serializable s = getSession().save(o);
			return null != s ? Long.parseLong(s.toString()) : null;
		} else {
			return null;
		}
	}

	/**
	 * Delete an object
	 * @param o
	 * 			Object
	 */
	@Override
	public void delete(T o) {
		if (null != o) {
			getSession().delete(o);
		}
	}
	
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
	@Override
	public boolean deleteById(Class<T> c, Long id) {
		if (null != c) {
			String className = c.getName();
			String hql = "DELETE FROM " + className + " o WHERE o.id = :id";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FBaseConstants.ID_STRING, id);
			int value = executeHql(hql, params);
			return value > 0 ? true : false;
		}
		return false;
	}

	/**
	 * Update an object
	 * 更新一个对象
	 * @param o
	 * 			Object
	 */
	@Override
	public void update(T o) {
		if (null != o) {
			getSession().update(o);
		}
	}

	/**
	 * Save or update an object
	 * 保存或更新一个对象
	 * @param o
	 * 			Object
	 */
	@Override
	public void saveOrUpdate(T o) {
		if (null != o) {
			getSession().saveOrUpdate(o);
		}
	}

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
	@Override
	public T getById(Class<T> c, Long id) {
		return (T) getSession().get(c, id);
	}

	/**
	 * Query an object through the HQL statement
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Object
	 */
	@Override
	public T getByHql(String hql) {
		Query q = getSession().createQuery(hql);
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}

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
	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}
	
	/**
	 * Query an object through the HQL statement
	 * 通过HQL语句查询出一个对象
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			Object
	 */
	@Override
	public T getBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}

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
	@Override
	public T getBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> list = q.list();
		if (null != list && !list.isEmpty()) {
			return list.get(FBaseConstants.FIRST_INDEX);
		}
		return null;
	}

	/**
	 * Through the HQL statement to query all the objects
	 * 通过HQL语句查询出所有的对象
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Objects collection
	 */
	@Override
	public List<T> query(String hql) {
		Query q = getSession().createQuery(hql);
		return q.list();
	}

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
	@Override
	public List<T> query(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

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
	@Override
	public List<T> query(String hql, int page, int rows) {
		Query q = getSession().createQuery(hql);
		if (page > 0) {
			q.setFirstResult((page - 1) * rows);
		}
		if (rows > 0) {
			q.setMaxResults(rows);
		}
		return q.list();
	}

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
	@Override
	public List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (page > 0) {
			q.setFirstResult((page - 1) * rows);
		}
		if (rows > 0) {
			q.setMaxResults(rows);
		}
		return q.list();
	}

	/**
	 * Through the HQL statement, the total number of records in the table (row)
	 * 通过HQL语句，统计表中总记录(行)数
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Total record (row) number
	 */
	@Override
	public Long count(String hql) {
		Query q = getSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

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
	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	/**
	 * Execute a HQL statement that can be: Insert, Update, Delete, Select
	 * 执行一条HQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param hql
	 * 			HQL statement
	 * @return
	 * 			Number of affected records (rows)
	 */
	@Override
	public int executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

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
	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	/**
	 * Through the SQL statement to query all the objects
	 * 通过SQL语句查询出所有的对象
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			ResultSet
	 */
	@Override
	public List<Map> queryBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

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
	@Override
	public List<Map> queryBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params & !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

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
	@Override
	public List<Map> queryBySql(String sql, int page, int rows) {
		SQLQuery q = getSession().createSQLQuery(sql);
		q.setFirstResult((page - 1) * rows);
		q.setMaxResults(rows);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

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
	@Override
	public List<Map> queryBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		q.setFirstResult((page - 1) * rows);
		q.setMaxResults(rows);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

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
	@Override
	public List<T> queryObjListBySql(String sql, Map<String, Object> params, Map<String, Type> resultValueTypeMap,
			Class<T> entityClass, FPage pagingEntity) {
		SQLQuery q = getSession().createSQLQuery(sql);
		
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		
		if (null != resultValueTypeMap && !resultValueTypeMap.isEmpty()) {
			for (String key : resultValueTypeMap.keySet()) {
				q.addScalar(key, resultValueTypeMap.get(key));
			}
		}
		
		if (null != pagingEntity) {
			q.setFirstResult((pagingEntity.getPageNumber() - 1) * pagingEntity.getPageSize());
			q.setMaxResults(pagingEntity.getPageSize());
		}
		
		AliasToBeanResultTransformer transformer = new AliasToBeanResultTransformer(entityClass);
		q.setResultTransformer(transformer);
		return q.list();
	}

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
	@Override
	public List<T> queryObjListBySql(String sql, String countSql, Map<String, Object> params,
			Map<String, Type> resultValueTypeMap, Class<T> entityClass, FPage pagingEntity) {
		SQLQuery q = getSession().createSQLQuery(sql);
		
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		
		if (null != resultValueTypeMap && !resultValueTypeMap.isEmpty()) {
			for (String key : resultValueTypeMap.keySet()) {
				q.addScalar(key, resultValueTypeMap.get(key));
			}
		}
		
		if (null != pagingEntity) {
			int rowCount = countBySql(countSql, params).intValue();
			pagingEntity.setRowCount(rowCount);
			pagingEntity.setDefaultValue();
			q.setFirstResult((pagingEntity.getPageNumber() - 1) * pagingEntity.getPageSize());
			q.setMaxResults(pagingEntity.getPageSize());
		}

		AliasToBeanResultTransformer transformer = new AliasToBeanResultTransformer(entityClass);
		q.setResultTransformer(transformer);
		return q.list();
	}

	/**
	 * Execute a SQL statement that can be: Insert, Update, Delete, Select
	 * 执行一条SQL语句，可以是：Insert, Update, Delete, Select
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			Number of affected records (rows)
	 */
	@Override
	public int executeSql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

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
	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	/**
	 * Through the SQL statement, the total number of records in the table (row)
	 * 通过SQL语句，统计表中总记录(行)数
	 * 
	 * @param sql
	 * 			SQL statement
	 * @return
	 * 			Total record (row) number
	 */
	@Override
	public BigInteger countBySql(String sql) {
		SQLQuery q = getSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

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
	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (null != params && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}
	
}
