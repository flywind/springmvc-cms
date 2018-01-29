package com.uxdpen.cms.business.common.utils;

import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.uxdpen.cms.business.common.constants.FBaseConstants;

/**
 * <p>
 * Send mail util
 * 发送邮件工具类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FEmailUtil {
	
	/**
	 * @log
	 */
	private static final Logger logger = Logger.getLogger(FEmailUtil.class);
	
	/**
	 * Target email server address
	 */
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	
	/**
	 * Whether the need for authentication
	 */
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	
	/**
	 * Sender's email address
	 */
	private static final String FROM_ADDRESS = "zou_ruixian1@163.com";
	
	/**
	 * Sender user name
	 */
	private static final String USER_NAME = "zou_ruixian1";
	
	/**
	 * Sender password
	 */
	private static final String PWD = "tapestrycms";
	
	/**
	 * Send email
	 * 发送邮件
	 * 
	 * @param address
	 *        Target email server address
	 * @param title
	 *        Email title
	 * @param content
	 *        Email content
	 */
	public static void send(String address, String title, String content) {
		logger.debug("Send mail to:" + address);
		
		Properties props = new Properties();
		//Set target mail server address
		String host = getHostUrl(address);
		props.put(MAIL_SMTP_HOST, host);
		//Need to be authorized
		props.put(MAIL_SMTP_AUTH, true);
		Session session = Session.getDefaultInstance(props);
		
		//Can see the process of sending messages to the console, only open under the debugger
		session.setDebug(true);
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			//Set sender address
			message.setFrom(FROM_ADDRESS);
			
			//Set recipient address
			InternetAddress targetAddress = new InternetAddress(address);
			message.addRecipient(RecipientType.TO, targetAddress);
			
			//Set message title
			message.setSubject(title);
			
			//Set message content
			message.setText(content);
			
			/*
			 * Send eamil message
			 */
			Transport.send(message, USER_NAME, PWD);
			
		} catch (Exception e) {
			logger.error("Send mail failed.", e);
		}
		
	}
	
	/**
	 * Get target mail server host address
	 * 获取目标邮件服务器主机地址
	 * 
	 * @param address
	 *        Target email address
	 * @return
	 *        Mail server host address
	 */
	public static String getHostUrl(String address) {
		String host = FBaseConstants.EMPTY_STRING;
		if (StringUtils.isNotBlank(address) && address.contains("@")) {
			host = "mail." + address.substring(address.indexOf("@") + 1);
		}
		logger.debug("Target mail server host:" + host);
		return host;
	}

}
