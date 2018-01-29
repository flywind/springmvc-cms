package com.uxdpen.cms.business.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.utils.MongoDBUtil;


/**
 * <p>
 * MongoDB DAO impl
 * 操作MongoDB数据库的DAO实现类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class MongoBaseDaoImpl<T> implements MongoBaseDao<T> {
	
	/**
	 * Sort the logo, ASC ascending
	 */
	private static final String ASC = "ASC";
	
	/**
	 * MongoDB data sort (ascending)
	 */
	private static final int MONGO_ASC = 1;
	
	/**
	 * MongoDB data sorting (descending)
	 */
	private static final int MONGO_DESC = -1;

	@Override
	public void save(T o, String tableName) {
		DBCollection dbCollection = MongoDBUtil.getCollection(tableName);
		DBObject dbObject = toDBObject(o);
		if (null != dbObject) {
			dbCollection.save(dbObject);
		}
	}
	
	@Override
	public void remove(Map<String, Object> params, String tableName){
		DBCollection dbCollection = MongoDBUtil.getCollection(tableName);
		
		if (null != params && !params.isEmpty()) {
			
			BasicDBObject searchParams = new BasicDBObject();
			for (String key : params.keySet()) {
				searchParams.put(key, params.get(key));
			}
			dbCollection.findAndRemove(searchParams);
		}
		
	}

	@Override
	public List<T> findAll(Class<T> c, Map<String, Object> params, String tableName, FPage paging) {
		DBCollection dbCollection = MongoDBUtil.getCollection(tableName);
		
		List<T> result = new ArrayList<T>();
		DBCursor dbCursor = null;
		
		if (null != params && !params.isEmpty()) {
			
			BasicDBObject searchParams = new BasicDBObject();
			for (String key : params.keySet()) {
				searchParams.put(key, params.get(key));
			}
			dbCursor = dbCollection.find(searchParams);
		} else {
			dbCursor = dbCollection.find();
		}
		
		//Perform paging query, first sort (1.ASC, -1.DESC), after the page
		int beginPage = (paging.getPageNumber() - 1) * paging.getPageSize();
		String sortName = paging.getSortName();
		String sortOrder = paging.getSortOrder();
		if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
			if (ASC.equalsIgnoreCase(sortOrder)) {
				dbCursor.sort(new BasicDBObject(sortName, MONGO_ASC));
			} else {
				dbCursor.sort(new BasicDBObject(sortName, MONGO_DESC));
			}
		}
		dbCursor.skip(beginPage).limit(paging.getPageSize());
		
		while (dbCursor.hasNext()) {
			DBObject dbObject = dbCursor.next();
			if (null == dbObject) {
				break;
			}
			T o = toTargetObject(dbObject, c);
			result.add(o);
		}
		
		return result;
	}
	
	@Override
	public Long count(Map<String, Object> params, String tableName) {
		DBCollection dbCollection = MongoDBUtil.getCollection(tableName);
		Long count = 0L;
		
		if (null != params && !params.isEmpty()) {
			
			BasicDBObject searchParams = new BasicDBObject();
			for (String key : params.keySet()) {
				searchParams.put(key, params.get(key));
			}
			count = dbCollection.count(searchParams);
		} else {
			count = dbCollection.count();
		}
		
		return count;
	}
	
	/**
	 * Converts a target object to a MongoDB object
	 * 将目标对象转换成MongoDB对象
	 * 
	 * @param o
	 *        Target object
	 * @return
	 *        MongoDB object
	 */
	private DBObject toDBObject(Object o) {
		DBObject dbObject = null;
		String value = JSON.toJSONStringWithDateFormat(o, FBaseConstants.DATE_FORMAT_YMDHMS, 
				SerializerFeature.WriteDateUseDateFormat);
		dbObject = (DBObject) com.mongodb.util.JSON.parse(value);
		return dbObject;		
	}
	
	/**
	 * Converts a MongoDB object into a target object
	 * 将MongoDB对象转换成目标对象
	 * 
	 * @param dbObject
	 *        MongoDB object
	 * @param c
	 *        Class type of target object
	 * @return
	 *        Target object
	 */
	private T toTargetObject(DBObject dbObject,Class<T> c) {
		T o = null;
		String value = JSON.toJSONString(dbObject);
		o = JSON.parseObject(value, c);
		return o;
	}

}
