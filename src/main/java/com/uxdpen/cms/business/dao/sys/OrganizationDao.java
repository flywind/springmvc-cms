package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.entities.sys.Organization;

/**
 * <p>Organization Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface OrganizationDao extends FBaseDao<Organization> {

	/**
	 * Create an organizational structure
	 * 创建一个组织机构
	 * 
	 * @param organization
	 * 			organizational
	 * @return
	 * 			Long
	 */
	public Long createOrganization(Organization organization);
	
	/**
	 * Change organization
	 * 修改组织机构
	 * 
	 * @param organization
	 * 			organizational
	 */
    public void updateOrganization(Organization organization);
    
    /**
     * Delete organization
     * 删除组织机构
     * 
     * @param organizationId
     * 			Organization Id
     */
    public void deleteOrganization(Long organizationId);

    /**
     * Obtain organizational structure through Id
     * 通过Id获取组织机构
     * 
     * @param organizationId
     * 			Organization Id
     * @return
     * 			Object collection
     */
    public Organization findOne(Long organizationId);
    
    /**
     * Query all organization
     * 查询所有的组织机构
     * 
     * @return
     * 			Object collection
     */
    public  List<Organization> findAll();

    /**
     * Not included in the organization
     * 不包含的组织机构
     * 
     * @param excludeOraganization
     * 			Exclude oraganization
     * @return
     * 			Object collection
     */
    public List<Organization> findAllWithExclude(Organization excludeOraganization);

    /**
     * Move organization
     * 移动组织机构
     * 
     * @param source
     * 			Old organization
     * @param target
     * 			Target organization
     */
    public  void move(Organization source, Organization target);
    
    /**
     * Delete child nodes from parent node
     * 通过父节点删除子节点
     * 
     * @param parentIds
     * 			Parent id set
     * @return
     * 			int
     */
    public int deleteOrganizationByLikeParentIds(String parentIds);
    
    /**
     * Query child nodes via parentId
     * 通过parentId 查询子节点
     * 
     * @param parentId
     * 			Parent Id
     * @return
     * 			Object collection
     */
    public  List<Long>  getOrganizationsByParentId(Long parentId);
    
    /**
     * Query through the parent node ID all the organization
     * 通过父节点ID 查询出所有的组织机构
     * 
     * @param parentId
     * 			Parent Id
     * @return
     * 			Object collection
     */
    public  List<Organization>  getOrgByParentId(Long parentId);
    
    /**
     * Query organization by organization name
     * 通过组织机构名字查询组织机构
     * 
     * @param orgName
     * 			Organization name
     * @return
     * 			Object collection
     */
    public List<Organization>  getOrgsByName(String orgName);
    
    /**
     * Find out all the organizational structure through customercode.
     * 通过customercode查出所有的组织机构
     * 
     * @param customerCode
     * 			Customer code
     * @return
     * 			Object collection
     */
    public List<Organization>  getAllByCustomerCode(String customerCode);
}
