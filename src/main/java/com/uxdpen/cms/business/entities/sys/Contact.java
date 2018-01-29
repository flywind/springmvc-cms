package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name="td_s_contact_us")
public class Contact extends FBase{

	private static final long serialVersionUID = 6741787050269884724L;
	
	/**
	 * Name
	 */
	private String name;
	
	/**
	 * Email
	 */
	private String email;
	
	/**
	 * Title
	 */
	private String title;
	
	/**
	 * Content
	 */
	private String content;
	
	/**
	 * Ip
	 */
	private String ip;
	
	@Column(name = "name", nullable = true, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", nullable = true, length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "title", nullable = true, length = 100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = true, length = 300)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ip", nullable = true, length = 100)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
