package com.uxdpen.cms.business.entities.sys;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;

@Entity
@Table(name="td_s_resource")
public class Resource extends FBase{

	private static final long serialVersionUID = 7303535303934983468L;
	
	/**
	 * Resource name
	 */
	private String name; 
	
	/**
	 * Resource name
	 */
	private String nameEn;
	
	/**
	 * Resource name key value
	 */
	private String resourceKey; 
	
    /**
     * Resource type
     */
    private String type; 
    
    /**
     * Resource path
     */
    private String url; 
    
    /**
     * Permission string
     */
    private String permission; 
    
    /**
     * Parent number
     */
    private Long parentId; 
    
    /**
     * Parent numbered list
     */
    private String parentIds; 
    
    /**
     * Sub menu list
     */
    private List<Resource> childResource;
    
    /**
     * Menu level
     */
    private int level;
    
    /**
     * Menu ordering
     */
    private int seqNum = 1;
    
    /**
     * Menu style
     */
    private String ioncCls;
    
    /**
     * Super administrator role
     */
    private boolean administrator = false;
    
    private String activeStyle;
    
    public Resource() {
    	
    }
    
    public Resource(Long id, String name, Long parentId) {
    	this.setId(id);
    	this.name = name;
    	this.parentId = parentId;
    }
    
    /**
     * Is available, the default is true
     */
    private Boolean available = Boolean.TRUE;

    public static enum ResourceType {
        menu("Menu"), button("Button");

        private final String info;
        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "name_en", nullable = true, length = 100)
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Column(name = "type", nullable = true, length = 50)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "url", nullable = true, length = 200)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "permission", nullable = true, length = 100)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    @Transient
    public boolean isRootNode() {
        return parentId == 0;
    }

    @Transient
    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    @Column(name = "level", nullable = true, length = 3)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "seq_num", nullable = true, length = 3)
	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	@Column(name = "icon_cls", nullable = true, length = 100)
	public String getIoncCls() {
		return ioncCls;
	}

	public void setIoncCls(String ioncCls) {
		this.ioncCls = ioncCls;
	}

	@Transient
	public List<Resource> getChildResource() {
		return childResource;
	}

	public void setChildResource(List<Resource> childResource) {
		this.childResource = childResource;
	}
	
	@Transient
	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	@Column(name = "resource_key", nullable = true, length = 100)
	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	@Transient
	public String getActiveStyle() {
		return activeStyle;
	}

	public void setActiveStyle(String activeStyle) {
		this.activeStyle = activeStyle;
	}

}
