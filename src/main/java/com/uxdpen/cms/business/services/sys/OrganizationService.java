package com.uxdpen.cms.business.services.sys;

import java.util.List;

import com.uxdpen.cms.business.common.result.Json;
import com.uxdpen.cms.business.entities.sys.Organization;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>组织机构service</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface OrganizationService {

	/**
	 * Create organization
	 * 创建组织机构
	 * 
	 * @param organization
	 * 			Organization
	 * @return
	 * 			Long
	 */
	public Long createOrganization(Organization organization);

	/**
	 * Update organization
	 * 修改组织机构
	 * 
	 * @param organization
	 * 			
	 */
	public void updateOrganization(Organization organization);

	/**
	 * Delete organization
	 * 删除组织机构
	 * 
	 * @param organizationId
	 * 			Organization id
	 */
	public void deleteOrganizationById(Organization organization);
	
	/**
	 * Delete organization
	 * 删除组织机构
	 * 
	 * @param ids
	 * 			Organization id set
	 */
	public void deleteOrganizationByIds(String ids);
    
	/**
	 * Find organization through Id
	 * 通过Id查找组织机构
	 * 
	 * @param organizationId
	 * 			Organization id
	 * @return
	 * 			Object
	 */
	public Organization findOne(Long organizationId);
    
	/**
	 * Output of sub nodes in JSON format
	 * 按json格式输出归属的子节点
	 * 
	 * @param excludeOraganization
	 * 			Exclude oraganization
	 * @return
	 * 			Json
	 */
	public Json findAllWithExcludeToJson(Organization excludeOraganization);
 
	/**
     * Move organization
     * 移动组织机构
     * 
     * @param source
     * 			Old organization
     * @param target
     * 			Target organization
     */
	public void move(Organization source, Organization target);
  
	/**
	 * Query all the sub companies under the current user
	 * 查询当前用户下的所有子公司
	 * 
	 * @param loginUser
	 * 			Login user
	 * @return
	 * 			Object collection
	 */
	public List<Organization> findAllOrganizationByUser(User loginUser);
	
	/**
     * Find out all the organizational structure through customercode.
     * 通过customercode查出所有的组织机构
     * 
     * @param customerCode
     * 			Customer code
     * @return
     * 			Json
     */
	public Json findAllByCustomerCode(String customerCode);

	/**
	 * All the child nodes are returned to the List type through the parent node.
	 * 通过父节点查询所有的子节点 返回List类型
	 * 
	 * @param parentId
	 * 			Parent organization id
	 * @return
	 * 			Object collection
	 */	
	public List<Long> getOrganizationsByParentId(Long parentId);

	/**
	 * Through the parent node ID filter out all the child nodes to return to the JSON format
	 * 通过父节点ID 过滤出所有的子节点 返回JSON格式
	 * 
	 * @param parentId
	 * 			Parent organization id
	 * @return
	 * 			Json
	 */
	public Json getOrgByParentIToJson(Long parentId);

	/**
	 * Query the user through the organization Id
	 * 通过组织机构Id查询用户
	 * 
	 * @param orgId
	 * 			Organization id
	 * @return
	 * 			Object collection
	 */
	public List<User> getUsersByOrgId(String customerCode, List<Long> orgIds);
	
	/**
	 * Through the name of the organization, the organization of the organization of the fuzzy query
	 * 通过组织机构的名字，模糊查询出所有的组织机构
	 * 
	 * @param orgName
	 * 			Organization name
	 * @return
	 * 			Object collection
	 */
	public List<Long> getOrgsByName(String orgName,User loginUser);
	
	/**
	 * Delete all the sub agencies under the current mechanism
	 * 删除当前机构下的所有子机构
	 * 
	 * @param o
	 *        Organization
	 * @return
	 *        int
	 */
	public int deleteChildren(Organization o);
}
