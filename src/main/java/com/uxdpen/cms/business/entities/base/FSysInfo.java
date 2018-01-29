package com.uxdpen.cms.business.entities.base;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.uxdpen.cms.business.entities.sys.Organization;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>
 * Cache system base object
 * 缓存系统基础对象
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月28日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FSysInfo implements Serializable {

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 551452194707307789L;
	
	/**
	 * Login user's client IP address
	 */
	private String ip;
	
	/**
	 * Login user corresponding client code
	 */
	private String customerCode;
	
	/**
	 * Whether to log the operation log for viewing the log list, the default is no record
	 */
	private boolean writeFindLog = false;
	
	/**
	 * Login domain
	 */
	private String domainName;
	
	
	/**
	 * Login user information
	 */
	private User user;
	
	/**
	 * Subordinate units
	 */
	private Organization company;
	
	/**
	 * List of units
	 */
	private List<Organization> companys;
	
	/**
	 * List of unit ID
	 */
	private List<Long> companyIds;
	
	public FSysInfo() {
		
	}
	
	public FSysInfo(String ip, String customerCode, User user) {
		this.ip = ip;
		this.customerCode = customerCode;
		this.user = user;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Organization getCompany() {
		return company;
	}

	public void setCompany(Organization company) {
		this.company = company;
	}
	
	public boolean isWriteFindLog() {
		return writeFindLog;
	}

	public void setWriteFindLog(boolean writeFindLog) {
		this.writeFindLog = writeFindLog;
	}

	public List<Organization> getCompanys() {
		return companys;
	}

	public void setCompanys(List<Organization> companys) {
		this.companys = companys;
	}

	public List<Long> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Long> companyIds) {
		this.companyIds = companyIds;
	}
	
	/**
	 * Retrieve data from the Session cache
	 * 从Session缓存中取出数据
	 * 
	 * @param sessionKey
	 *        Session key
	 * @return
	 *        Object
	 */
	public Object getAttribute(Object sessionKey) {
		return SecurityUtils.getSubject().getSession().getAttribute(sessionKey);
	}
	
	/**
	 * Add data to the Session cache
	 * 将数据添加到Session缓存
	 * 
	 * @param sessionKey
	 *        Session key
	 * @param value
	 *        Value
	 */
	public void setAttribute(Object sessionKey, Object value) {
		SecurityUtils.getSubject().getSession().setAttribute(sessionKey, value);
	}
	
	/**
	 * Delete object data from the Session cache
	 * 从Session缓存中删除对象的数据
	 * 
	 * @param sessionKey
	 *        Session key
	 */
	public void removeAttribute(Object sessionKey) {
		SecurityUtils.getSubject().getSession().removeAttribute(sessionKey);
	}
	
}
