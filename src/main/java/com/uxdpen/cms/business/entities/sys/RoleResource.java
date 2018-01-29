package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="td_s_role_resource")
public class RoleResource {
	
	/**
	 * Id
	 */
	private Long id;

	/**
	 * Role id
	 */
	private Long roleId;
	
	/**
	 * Resource id
	 */
	private Long resourceId;
	
	public RoleResource() {
		
	}
	
	public RoleResource(Long roleId, Long resourceId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	public Long getId(){
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "role_id", nullable = false)
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "resource_id", nullable = false)
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
}
