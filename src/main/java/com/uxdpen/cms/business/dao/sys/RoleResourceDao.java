package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.entities.sys.RoleResource;

/**
 * <p>Role Resource Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface RoleResourceDao extends FBaseDao<RoleResource> {

	/**
	 * To delete the relationship between role and resources based on role ID
	 * 根据角色id删除角色与资源的关联关系
	 * 
	 * @param roleId
	 *        	Role id
	 * @return
	 *        	int
	 */
	public int deleteRoleResourceByRoleId(Long roleId);
	
	/**
	 * Check if the resource is used according to the resource ID
	 * 根据资源id检查资源是否被用
	 * 
	 * @param resourceId
	 * 			Resource id
	 * @return 
	 * 			boolean
	 */
	public boolean checkResourceIsUsing(Long resourceId);
	
	/**
	 * Relationship between role and resources in batch creation
	 * 批量创建角色与资源的关联关系
	 * 
	 * @param resourceIds
	 *        	Resource id set
	 * @param roleId
	 *        	Role id
	 */
	public void createRoleResources(List<Long> resourceIds, Long roleId);
	
	/**
	 * Relationship between role and resource in batch deletion
	 * 批量删除角色与资源的关联关系
	 * 
	 * @param resourceIds
	 *        	Comma separated resource ID set
	 * @param roleId
	 *        	Role id
	 * @return
	 *        	int
	 */
	public int deleteRoleResources(String resourceIds, Long roleId);
	
	/**
	 * Based on the relationship between the role and resources of resource ID
	 * 根据资源id删除角色与资源的关联关系
	 * 
	 * @param resourceId
	 *        	Resource id
	 * @return
	 *        	int
	 */
	public int deleteRoleResourceByResourceId(Long resourceId);
}
