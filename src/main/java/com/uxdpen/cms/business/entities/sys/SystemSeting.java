package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name = "td_s_seting")
public class SystemSeting extends FBase {

	private static final long serialVersionUID = 1334334345L;
	
	/**
	 * System main title
	 */
	private String systemMainTitle;
	
	/**
	 * Keyword
	 */
	private String keyword;

	/**
	 * System Logo path
	 */
	private String systemLogoPath;

	/**
	 * Page style
	 */
	private String style;

	/**
	 * Enterprise name
	 */
	private String companyName;
	
	/** 
	 * Enterprise web site
	 */
	private String webUrl;

	/** 
	 * Email
	 **/
	private String email;

	/** 
	 * Contact address
	 * */
	private String address;

	/** 
	 * Contact phone
	 */
	private String phone;

	/**
	 * Unit fax
	 */
	private String customerFax;
	
	/**
	 * Copyright information
	 */
	private String copyright;
	
	/**
	 * ICP record number
	 */
	private String icp;
	
	/**
	 * ICP record link
	 */
	private String icplink;
	
	private boolean boxedLayout = false;
	
	private boolean navToggle = false;

	@Column(name = "system_main_title", length = 36)
	public String getSystemMainTitle() {
		return systemMainTitle;
	}

	public void setSystemMainTitle(String systemMainTitle) {
		this.systemMainTitle = systemMainTitle;
	}
	
	@Column(name = "system_keyword", length = 200)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "system_logo_path", length = 100)
	public String getSystemLogoPath() {
		return systemLogoPath;
	}

	public void setSystemLogoPath(String systemLogoPath) {
		this.systemLogoPath = systemLogoPath;
	}


	@Column(name = "style", length = 12)
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Column(name = "companyName", length = 100)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "address", length = 1024)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "customerFax", length = 100)
	public String getCustomerFax() {
		return customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	@Column(name = "copyright", length = 100)
	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	@Column(name = "icp", length = 100)
	public String getIcp() {
		return icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	@Column(name = "icplink", length = 1000)
	public String getIcplink() {
		return icplink;
	}

	public void setIcplink(String icplink) {
		this.icplink = icplink;
	}

	@Column(name = "webUrl", length = 1000)
	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	
	//English

	private String systemMainTitleEn;
	
	private String keywordEn;
	
	private String systemLogoPathEn;

	private String companyNameEn;

	private String webUrlEn;

	private String emailEn;

	private String addressEn;

	private String phoneEn;

	private String customerFaxEn;
	
	private String copyrightEn;

	private String icpEn;
	
	private String icplinkEn;
	

	@Column(name = "system_main_title_en", length = 36)
	public String getSystemMainTitleEn() {
		return systemMainTitleEn;
	}

	public void setSystemMainTitleEn(String systemMainTitleEn) {
		this.systemMainTitleEn = systemMainTitleEn;
	}
	
	@Column(name = "system_keyword_en", length = 200)
	public String getKeywordEn() {
		return keywordEn;
	}

	public void setKeywordEn(String keywordEn) {
		this.keywordEn = keywordEn;
	}

	@Column(name = "system_logo_path_en", length = 100)
	public String getSystemLogoPathEn() {
		return systemLogoPathEn;
	}

	public void setSystemLogoPathEn(String systemLogoPathEn) {
		this.systemLogoPathEn = systemLogoPathEn;
	}

	@Column(name = "companyName_en", length = 100)
	public String getCompanyNameEn() {
		return companyNameEn;
	}

	public void setCompanyNameEn(String companyNameEn) {
		this.companyNameEn = companyNameEn;
	}

	@Column(name = "email_en", length = 50)
	public String getEmailEn() {
		return emailEn;
	}

	public void setEmailEn(String emailEn) {
		this.emailEn = emailEn;
	}

	@Column(name = "address_en", length = 1024)
	public String getAddressEn() {
		return addressEn;
	}

	public void setAddressEn(String addressEn) {
		this.addressEn = addressEn;
	}

	@Column(name = "phone_en", length = 100)
	public String getPhoneEn() {
		return phoneEn;
	}

	public void setPhoneEn(String phoneEn) {
		this.phoneEn = phoneEn;
	}

	@Column(name = "customerFax_en", length = 100)
	public String getCustomerFaxEn() {
		return customerFaxEn;
	}

	public void setCustomerFaxEn(String customerFaxEn) {
		this.customerFaxEn = customerFaxEn;
	}

	@Column(name = "copyright_en", length = 100)
	public String getCopyrightEn() {
		return copyrightEn;
	}

	public void setCopyrightEn(String copyrightEn) {
		this.copyrightEn = copyrightEn;
	}

	@Column(name = "icp_en", length = 100)
	public String getIcpEn() {
		return icpEn;
	}

	public void setIcpEn(String icpEn) {
		this.icpEn = icpEn;
	}

	@Column(name = "icplink_en", length = 1000)
	public String getIcplinkEn() {
		return icplinkEn;
	}

	public void setIcplinkEn(String icplinkEn) {
		this.icplinkEn = icplinkEn;
	}

	@Column(name = "webUrl_en", length = 1000)
	public String getWebUrlEn() {
		return webUrlEn;
	}

	public void setWebUrlEn(String webUrlEn) {
		this.webUrlEn = webUrlEn;
	}

	@Column(name = "nav_toggle")
	public boolean isNavToggle() {
		return navToggle;
	}

	public void setNavToggle(boolean navToggle) {
		this.navToggle = navToggle;
	}

	@Column(name = "boxed_layout")
	public boolean isBoxedLayout() {
		return boxedLayout;
	}

	public void setBoxedLayout(boolean boxedLayout) {
		this.boxedLayout = boxedLayout;
	}

	

}
