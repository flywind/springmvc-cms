package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name="td_s_comment")
public class Comment extends FBase{

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
	 * ID is commented on.
	 */
	private Long contentId;
	
	/**
	 * Reply message
	 */
	private String reply;
	
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

	@Column(name = "reply", nullable = true, length = 300)
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Column(name = "content_id",nullable = false)
	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

}
