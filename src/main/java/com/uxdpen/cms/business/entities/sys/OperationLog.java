package com.uxdpen.cms.business.entities.sys;

import java.io.Serializable;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.utils.DateUtil;


/**
 * <p>操作日志模型类</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class OperationLog implements Serializable {

	private static final long serialVersionUID = 1260007918367649659L;
	
	/**
	 * Operation account
	 */
	private String username;
	
	/**
	 * User name
	 */
	private String name;
	
	/**
	 * Customer code
	 */
	private String customerCode;
	
	/**
	 * Log type (1 add, 2 delete, 3 modify, 4 query)
	 */
	private int type;
	
	/**
	 * Operating results (1: success, 2: failure)
	 */
	private int result;
	
	/**
	 * Log info
	 */
	private String info;
	
	/**
	 * Operating time
	 */
	private String opTime;
	
	/**
	 * Start time
	 */
	private String startTime;
	
	/**
	 * End time
	 */
	private String endTime;
	
	public OperationLog() {
		this.opTime = DateUtil.getCurrentDate(FBaseConstants.DATE_FORMAT_YMDHMS);
	}
	
	public OperationLog(String username, String name, int type, String info, int result) {
		this.username = username;
		this.name = name;
		this.type = type;
		this.info = info;
		this.result = result;
		this.opTime = DateUtil.getCurrentDate(FBaseConstants.DATE_FORMAT_YMDHMS);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
