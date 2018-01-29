package com.uxdpen.cms.business.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.uxdpen.cms.business.common.utils.MongoDBUtil;
import com.uxdpen.cms.business.common.utils.PropertiesUtils;

/**
 * <p>
 * Monitor class for controlling MongoDB connection switch
 * 控制MongoDB连接开关的监听类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class MongoDBListener implements ServletContextListener {
	
	/**
	 * @Log
	 */
	private static final Logger logger = Logger.getLogger(MongoDBListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		MongoDBUtil.closeMongoDB();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			String host = PropertiesUtils.getProperties().getProperty("mongodb.connection.host");
			String port = PropertiesUtils.getProperties().getProperty("mongodb.connection.port");
			MongoDBUtil.connectMongoDB(host, Integer.parseInt(port));
		} catch (Exception e) {
			logger.error("Failed to connect to MongoDB database.", e);
		}
	}

}
