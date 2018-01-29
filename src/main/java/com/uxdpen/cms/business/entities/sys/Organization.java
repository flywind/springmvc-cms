package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;

/**
 * <p>Organization实体-组织架构</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Entity
@Table(name="td_s_organization")
public class Organization extends FBase {

	private static final long serialVersionUID = 7842276180927947503L;

	/**
	 * Organization name
	 */
	private String name;

	/**
	 * Parent id
	 */
	private Long parentId;

	/**
	 * Parent numbered list, such as 1/2/
	 */
	private String parentIds;
	
	/**
	 *  Organization type: 1 area, (2 agents, 3 distributors, 4 sub units), 5 customers
	 */
	private Integer type;

	/**
	 * Linkman
	 */
	private String contactName;

	/**
	 * Work telephone
	 */
	private String contactNumber;

	/**
	 * Unit address
	 */
	private String address;

	/**
	 * Unit email
	 */
	private String email;

	/**
	 * Unit fax
	 */

	private String customerFax;

	/** 
	 * Unit web site name
	  */
	private String customerSiteName;
	
	/**
	 * Is available, the default is false
	 */
	private Boolean available = Boolean.TRUE;

	/**
	 * Name of superior unit
	 */
	private String parentName;

	@Column(name = "name", nullable = true, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", columnDefinition = "SMALLINT")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "contact_name", length = 100)
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "contact_number", length = 20)
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "parent_id", nullable = true)
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "parent_ids", nullable = true, length = 1000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Column(name = "available", nullable = true, length = 1)
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Column(name = "email", length = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "customerFax", length = 200)
	public String getCustomerFax() {
		return customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	@Transient
	public boolean isRootNode() {
		return parentId == 0;
	}

	@Transient
	public String makeSelfAsParentIds() {
		return getParentIds() + getId() + "/";
	}

	@Column(name = "customerSiteName", length = 200)
	public String getCustomerSiteName() {
		return customerSiteName;
	}

	public void setCustomerSiteName(String customerSiteName) {
		this.customerSiteName = customerSiteName;
	}

	@Transient
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
