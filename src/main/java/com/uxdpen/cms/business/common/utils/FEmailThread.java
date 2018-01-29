package com.uxdpen.cms.business.common.utils;

/**
 * <p>
 * Thread class for sending email
 * 用于发送邮件的线程类
 * </p>
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FEmailThread extends Thread {
	
	/**
	 * Target email address
	 */
	private String address;
	
	/**
	 * Email title
	 */
	private String title;
	
	/**
	 * Email content
	 */
	private String content;
	
	/**
	 * Construction method
	 * 构造方法
	 * 
	 * @param address
	 *        Target email address
	 * @param title
	 *        Email title
	 * @param content
	 *        Email content
	 */
	public FEmailThread(String address, String title, String content) {
		this.address = address;
		this.title = title;
		this.content = content;
	}
	
	@Override
	public void run() {
		FEmailUtil.send(address, title, content);
	}

}
