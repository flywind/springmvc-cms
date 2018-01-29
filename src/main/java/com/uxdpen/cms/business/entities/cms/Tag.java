package com.uxdpen.cms.business.entities.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="td_c_tags")
public class Tag {
	
	private Long id;
	
	private int seqNum = 1;
	
	private Boolean isShow = Boolean.TRUE;
	
	private String value;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	public Long getId(){
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "is_show")
	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	@Column(name = "tag_value", nullable = false, length = 100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "seq_num", nullable = true, length = 3)
	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	
}
