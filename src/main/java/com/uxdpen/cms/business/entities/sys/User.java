package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.uxdpen.cms.business.entities.base.FBase;

/**
 * <p>td_s_user实体-用户</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Entity
@Table(name="td_s_user")
public class User extends FBase{

	private static final long serialVersionUID = 4149487371186929802L;

    /**
     * User name
     */
	//@NotNull(message="用户名不能为空")
	//@Size(min=1, max=16, message="用户名长度为1-16字符")
    private String username;
    
    /**
     * Real name
     */
    private String name;
    
    /**
     * Password
     */
    //@NotNull(message="密码不能为空")  
   // @Size(min=1, max=25, message="密码长度为1-25字符")  
    private String password; 
    
    /**
     * Mobile
     */
    private String mobile;
    
    /**
     * Phone
     */
    private String phone;
    
    /**
     * Email
     */
    //@Pattern(regexp="^[0-9a-zA-Z]{1,}@[0-9a-zA-Z]{1,}\\.(com|cn)$", message="邮箱格式不正确") 
    private String email;
    
    /**
     * Personal avatar path
     */  
    private String picUrl;
    
    /**
     * Comma separated role name set
     */
    private String roleNames;
    
    /**
     * Organization name
     */
    private String companyName;
    
    /**
     * Whether lock
     */
    private Boolean locked = Boolean.FALSE;
    
    /**
     * Whether the initial password(true/1:Yes，false/0:No)
     */
    private boolean initPwd = true;
    
    /**
	 * Whether display
	 */
	private Boolean isAdmin = Boolean.FALSE;
    
    /**
     * Parent object id
     */
    private String parentId;
    
    /**
     * Salt
     */
    private String salt;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Column(name = "user_name", nullable = true, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = true, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name = "locked", nullable = true)
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    
    @Transient
	public String getRoleNames() {
		return roleNames;
	}
    
    public void setRoleNames(String roleNames) {
    	this.roleNames = roleNames;
    }

    @Transient
    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "name", nullable = true, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mobile", nullable = true, length = 100)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "phone", nullable = true, length = 100)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", nullable = true, length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	@Column(name = "pic_url", nullable = true, length = 100)
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name = "is_init_pwd", nullable = true)
	public boolean isInitPwd() {
		return initPwd;
	}

	public void setInitPwd(boolean initPwd) {
		this.initPwd = initPwd;
	}

	@Column(name = "parent_id", length = 400)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "is_admin", length = 1)
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSalt() {
		return salt;
	}

	@Column(name = "salt", length = 400)
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	 @Transient
	public String getCredentialsSalt() {
        return username + salt;
    }
}
