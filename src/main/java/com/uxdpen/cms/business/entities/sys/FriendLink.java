package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name="td_s_friendlink")
public class FriendLink extends FBase{

	private static final long serialVersionUID = 3661948733128979052L;
	
	/**
	 * Friend link name
	 */
	private String linkName;

    /**
     * Friend link url
     */
    private String linkUrl;

    /**
     * Friend link logo
     */
    private String linkLogo;
    
    /**
     * Seq number
     */
    private int seqNum = 1;
    

    @Column(name = "name", nullable = true, length = 100)
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Column(name = "link", nullable = true, length = 300)
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Column(name = "logo", nullable = true, length = 300)
	public String getLinkLogo() {
		return linkLogo;
	}

	public void setLinkLogo(String linkLogo) {
		this.linkLogo = linkLogo;
	}

	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

}
