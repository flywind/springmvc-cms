package com.uxdpen.cms.business.common.utils;

/**
<p>
Thread class for sending text messages
用于发送短信的线程类
</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FMessageThread extends Thread {
	
	/**
	 * SMS content
	 */
	private String content;
	
	/**
	 * System type
	 */
	private int systemType;
	
	/**
	 * Phone number
	 */
	private String phoneNumber;
	
	/**
	 * Customer Code
	 */
	private String customerCode;
	
	public FMessageThread(String content, int systemType, String phoneNumber, String customerCode) {
		this.content = content;
		this.systemType = systemType;
		this.phoneNumber = phoneNumber;
		this.customerCode = customerCode;
	}
	
	@Override
	public void run() {
		FMessageUtil.send(content, systemType, phoneNumber, customerCode);
	}

}
