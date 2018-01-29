package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name="td_s_role")
public class Role extends FBase{
	

	private static final long serialVersionUID = 5439668664729253873L;

	/**
     * Role identification procedures to determine the use, such as "admin""
     */
    private String name; 
    
    /**
     * Role description
     */
    private String description; 
    
    /**
     * Is available, if not available, will not be added to the user
     */
    private Boolean available = Boolean.FALSE; 
    
    private String resourceNames;

    public Role() {
    }

    public Role(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = true, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "available", nullable = true, length = 1)
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

	@Transient
	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}
}
