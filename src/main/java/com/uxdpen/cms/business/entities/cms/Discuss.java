package com.uxdpen.cms.business.entities.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;
import com.uxdpen.cms.business.entities.sys.User;

@Entity
@Table(name="td_c_discuss")
public class Discuss extends FBase{

	private static final long serialVersionUID = 1L;
	
	private String author;
	
	private Long postsId;
	
	private String reply;
	
	private String message;
	
	private User discussUser;
	
	@Column(name = "author", nullable = true, length = 50)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "reply", nullable = true, length = 500)
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Column(name = "message", nullable = true, columnDefinition="TEXT")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "post_id", nullable = false)
	public Long getPostsId() {
		return postsId;
	}

	public void setPostsId(Long postsId) {
		this.postsId = postsId;
	}

	@Transient
	public User getDiscussUser() {
		return discussUser;
	}

	public void setDiscussUser(User discussUser) {
		this.discussUser = discussUser;
	}

	
}
