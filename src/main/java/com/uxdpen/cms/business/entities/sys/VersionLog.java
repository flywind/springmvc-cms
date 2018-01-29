package com.uxdpen.cms.business.entities.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>首页版本日志模型类</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月7日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Entity
@Table(name = "td_s_version_log")
public class VersionLog {
	
	/**
	 * Id
	 */
	private Long id;
	
	/**
	 * Title
	 */
	private String title;
	
	/**
	 * Version
	 */
	private String version;
	
	/**
	 * Log content
	 */
	private String content;
	
	/**
	 * Customer code
	 */
	private String customerCode;
	
	/**
	 * Release time
	 */
	private Date time;
	
	/**
	 * Describe
	 */
	private String description;
	
	/**
	 * Whether hidden (0: display, 1: hidden)
	 */
	private Integer hide = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", length = 500)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "version", length = 500)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "content", columnDefinition = "TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "customer_code", nullable = false, length = 36)
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time", length = 7)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "hide")
	public Integer getHide() {
		return hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}
	
	
	
	
	//English
	
	private String titleEn;
	
	private String descriptionEn;

	private String contentEn;

	@Column(name = "title_en", length = 500)
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	
	@Column(name = "description_en", length = 500)
	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	
	@Column(name = "content_en", columnDefinition = "TEXT")
	public String getContentEn() {
		return contentEn;
	}

	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}
}
