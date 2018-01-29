package com.uxdpen.cms.business.entities.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name="td_c_work")
public class Work extends FBase {

	private static final long serialVersionUID = -8043482215105081359L;
	
	/**
	 * Title
	 */
	private String title;
	
	/**
	 * Search Title
	 */
	private String searchTitle;
	
	/**
	 * Small image
	 */
	private String smallImgurl;
	
	/**
	 * Type
	 */
	private Integer type;
	
	private String typeName;
	
	/**
	 * Whether to recommend
	 */
	private Boolean isHot = Boolean.FALSE;
	
	/**
	 * Content
	 */
	private String content;

	@Column(name = "title", nullable = true, length = 200)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "smallImgurl", nullable = true, length = 400)
	public String getSmallImgurl() {
		return smallImgurl;
	}

	public void setSmallImgurl(String smallImgurl) {
		this.smallImgurl = smallImgurl;
	}

	@Column(name = "type", nullable = true, length = 3)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "content", nullable = true, columnDefinition="TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Transient
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
	//english
	private String titleEn;
	
	private String contentEn;

	@Column(name = "title_en", nullable = true, length = 200)
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}


	@Column(name = "content_en", nullable = true, columnDefinition="TEXT")
	public String getContentEn() {
		return contentEn;
	}

	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}

	@Column(name = "is_hot", length = 1)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Transient
	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

}
