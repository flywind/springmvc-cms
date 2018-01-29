package com.uxdpen.cms.business.entities.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;
import com.uxdpen.cms.business.entities.sys.User;

@Entity
@Table(name="td_c_posts")
public class Posts extends FBase {

	private static final long serialVersionUID = -8043482215105081359L;
	
	/**
	 * Title
	 */
	private String title;
	
	/**
	 * Author
	 */
	private String author;
	
	
	/**
	 * 我的赞
	 * Voter
	 */
	private String voter;
	
	/**
	 * 浏览量
	 * Clicks
	 */
	private Long clicks = 0L;
	
	
	/**
	 * Whether to recommend
	 */
	private Boolean isHot = Boolean.FALSE;
	
	/**
	 * Content
	 */
	private String content;
	
	private Long categoryId;
	
	private Long tagId;
	
	private String timeAgo;
	
	private Integer countDiscuss;
	
	private Integer countVoter;
	
	private String tagName;
	
	private String categoryName;
	
	private String subContent;
	
	private Boolean isHome = Boolean.FALSE;
	
	private String picUrl;
	
	private User user;
	
	private Boolean hasPic;
	
	@Column(name = "author", nullable = true, length = 100)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	@Column(name = "voter", nullable = true, length = 1500)
	public String getVoter() {
		return voter;
	}

	public void setVoter(String voter) {
		this.voter = voter;
	}

	@Column(name = "title", nullable = true, length = 500)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = true, columnDefinition="TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "clicks", nullable = true)
	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	@Column(name = "is_hot", length = 1)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Column(name = "category_id", nullable = false)
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "tag_id", nullable = false)
	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	@Transient
	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	@Transient
	public Integer getCountDiscuss() {
		return countDiscuss;
	}

	public void setCountDiscuss(Integer countDiscuss) {
		this.countDiscuss = countDiscuss;
	}

	@Transient
	public Integer getCountVoter() {
		return countVoter;
	}

	public void setCountVoter(Integer countVoter) {
		this.countVoter = countVoter;
	}

	@Transient
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Transient
	public String getSubContent() {
		return subContent;
	}

	public void setSubContent(String subContent) {
		this.subContent = subContent;
	}

	@Transient
	public Boolean getIsHome() {
		return isHome;
	}

	public void setIsHome(Boolean isHome) {
		this.isHome = isHome;
	}

	@Transient
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Transient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "pic_url", nullable = true, length = 1000)
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Transient
	public Boolean getHasPic() {
		return hasPic;
	}

	public void setHasPic(Boolean hasPic) {
		this.hasPic = hasPic;
	}
	
	
	
}
