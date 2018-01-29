package com.uxdpen.cms.business.common.utils;

import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * <p>
 * Send SMS util
 * 发送短信的工具类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FMessageUtil {
	
	/**
	 * system
	 */
	public static final int F_SYSTEM = 4;
	
	/**
	 * @log
	 */
	private static final Logger logger = Logger.getLogger(FMessageUtil.class);
	
	/**
	 * Send text message address
	 */
	private static final String smsAddress = PropertiesUtils.getProperties().getProperty("message_address");
	
	/**
	 * Send SMS
	 * 发送短信
	 * 
	 * @param content
	 *        SMS content
	 * @param systemType
	 *        System type
	 * @param phoneNumber
	 *        Phone number
	 * @param customerCode
	 *        Customer code
	 */
	public static void send(String content, int systemType, String phoneNumber, String customerCode) {
		logger.info("Send SMS to"  + phoneNumber);
		
		GetMethod method = null;
		HttpClient client = null;
		
		try {
			String smsContent = URLEncoder.encode(content, "UTF-8");
			//Send SMS addr
			String url = smsAddress + "?companyId=" + customerCode + "&content=" + smsContent 
					+ "&phones=" + phoneNumber + "&appSystem=" + systemType;
			logger.info("Short message sending address:" + url);
			
			method = new GetMethod(url);
			client = new HttpClient();
			client.executeMethod(method);
			
		} catch (Exception e) {
			logger.error("Failed to send text messages.", e);
		} finally {
			try {
				if (null != method) {
					method.releaseConnection();
				}
				if (null != client) {
					((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();;
				}
			} catch (Exception e) {
				logger.error("Close SMS connection failed.", e);
			}
		}
	}

}
