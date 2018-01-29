package com.uxdpen.cms.business.common.utils;

import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * <p>
 * MongoDB util
 * 操作MongoDB数据库的工具类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class MongoDBUtil {

	/**
	 * @log
	 */
	private static final Logger logger = Logger.getLogger(MongoDBUtil.class);

	/**
	 * User name
	 */
	private static final String USERNAME = "username";

	/**
	 * password
	 */
	private static final String PASSWORD = "password";

	/**
	 * Database host
	 */
	private static final String HOST = "host";

	/**
	 * Database port
	 */
	private static final String PORT = "port";

	/**
	 * Database name
	 */
	private static final String DBNAME = "dbname";

	/**
	 * MongoDB client
	 */
	private static MongoClient mongo;

	/**
	 * Stored MongoDB database information to connect the Properties, including (host, port, dbName)
	 */
	private static Properties prop;

	/**
	 * Get DBCollection through a table name
	 * 通过表名获取一个连接
	 * 
	 * @param tableName
	 *        Table name
	 * @return {@link DBCollection}
	 */
	@SuppressWarnings("deprecation")
	public static DBCollection getCollection(String tableName) {
		DBCollection dbCollection = null;
		try {
			String host = prop.getProperty(HOST);
			int port = Integer.parseInt(prop.getProperty(PORT));
			String username = prop.getProperty(USERNAME);
			String password = prop.getProperty(PASSWORD);
			String dbName = prop.getProperty(DBNAME);

			connectMongoDB(username, password, dbName, host, port);
			
			// Get MongoDatabase
			//MongoDatabase mdb = client.getDatabase(dbName);
			
			//Get DB
			DB db = mongo.getDB(dbName);

			// Get MongoCollection through a tabel name
			// MongoCollection<?> table = mdb.getCollection(tableName);

			dbCollection = db.getCollection(tableName);

		} catch (Exception e) {
			logger.error("Get MongoDB connection failed.", e);
		}
		return dbCollection;
	}

	
	/**
	 * Connect MongoDB
	 * 连接MongoDB
	 * 
	 * @param username 
	 * 		     User name
	 * @param password
	 * 			password
	 * @param dbName
	 * 			Database name
	 * @param host 
	 * 			Database host
	 * @param port 
	 * 			Database port
	 */
	public static void connectMongoDB(String username, String password, String dbName, String host, int port) {
		try {
			
			MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());

			ServerAddress addr = new ServerAddress(host, port);
			mongo = new MongoClient(addr, Arrays.asList(credential));
			logger.debug("MongoDB database connection success.");
		} catch (Exception e) {
			logger.error("MongoDB database connection failed.", e);
		}
	}
	
	/**
	 * Connect MongoDB
	 * 连接MongoDB
	 * 
	 * @param host 
	 * 			Database host
	 * @param port 
	 * 			Database port
	 */
	public static void connectMongoDB(String host, int port) {
		try {
			mongo = new MongoClient(host, port);
			logger.debug("MongoDB database connection success.");
		} catch (Exception e) {
			logger.error("MongoDB database connection failed.", e);
		}
	}

	/**
	 * Colsed to MongoDB connection
	 * 关闭MongoDB数据库连接
	 */
	public static void closeMongoDB() {
		try {
			if (null != mongo) {
				mongo.close();
			}
		} catch (Exception e) {
			logger.error("关闭MongoDB数据库连接失败。", e);
		}
	}

	/**
	 * Set MongoDB connection info
	 * 设置MongoDB数据库的连接信息
	 * 
	 * @param prop
	 *        The Properties object that stores the connection information of the MongoDB database, including (host, port, dbName)
	 */
	public static void setProp(Properties p) {
		prop = p;
	}

}
