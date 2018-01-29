package com.uxdpen.cms.business.entities.base;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="td_s_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="javaClassName")
public class SysParam  extends FBase
{
	private static final long serialVersionUID = 8887L;

	
	/**
	 * Business type
	 */
    private int  businessType;
    
    /**
     * Page hidden value
     */
    private int  paramKey;
    
    /**
	 * Sort
	 * */
	private Integer sort;
    
    /**
     * Page display value
     */
    private String paramValue;
    
    /**
     * Page display value(English)
     */
    private String paramValueEn;
    
  	private String description;

    @Column(name = "business_type", length =4)
	public int getBusinessType() {
		return businessType;
	}
 
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
   
	@Column(name = "param_key", length =4)
   public int getParamKey() {
		return paramKey;
	}

	public void setParamKey(int paramKey) {
		this.paramKey = paramKey;
	}
   
	@Column(name="param_value",length=100)
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@Column(name="param_value_en",length=100)
	public String getParamValueEn() {
		return paramValueEn;
	}

	public void setParamValueEn(String paramValueEn) {
		this.paramValueEn = paramValueEn;
	}

	@Column(name="description",length=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
   	
}
