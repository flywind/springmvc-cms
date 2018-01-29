package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="td_s_user_role")
public class UserRole {
	
	/**
	 * Id
	 */
	private Long id;

	/**
	 * User id
	 */
	private Long userId;
	
	/**
	 * Role id
	 */
	private Long roleId;
	
	public UserRole(){}
	
	public UserRole(Long userId, Long roleId){
		this.userId = userId;
		this.roleId = roleId;
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

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "role_id", nullable = false)
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
