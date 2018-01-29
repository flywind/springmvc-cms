package com.uxdpen.cms.business.services.sys;

import java.util.List;
import java.util.Set;

import com.uxdpen.cms.business.common.exception.FException;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.Role;
import com.uxdpen.cms.business.entities.sys.RoleResource;

/**
 * <p>类或接口的功能描述</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface RoleService {
	
	/**
	 * Create role
	 * 创建角色
	 * 
	 * @param role
	 *        Role
	 * @return
	 *        Role id
	 */
	public Long createRole(Role role);
	
	/**
	 * Update role
	 * 修改角色
	 * 
	 * @param role
	 *        Role
	 * @param oldDesc
	 *        Description of the role of the editor
	 * @param resourceIds
	 *        Resource id set
	 * @param oldResIds
	 *        Resource ID set before editing
	 */
    public void updateRole(Role role, String oldDesc, String resourceIds, String oldResIds) throws FException;
    
    /**
     * Delete role
     * 删除角色
     * 
     * @param role
     *        Role
     */
    public void deleteRole(Role role) throws FException;
    
    /**
     * According to ID to obtain the corresponding role
     * 根据id获取对应的角色
     * 
     * @param roleId
     *        Role id
     * @return
     *        Object
     */
    public Role getRoleById(Long roleId);
    
    
    /**
     * Get all the characters used in the advanced search
     * 获取所有的角色，应用于高级搜索
     * 
     * @param role
     *        Role
     * @param paging
     *        Pagination
     * @param session
     *        System base info
     * @return
     *        Object collecton
     */
    public List<Role> findAll(Role role, FPage paging, FSysInfo session);

    /**
     * Get a list of character identifiers based on role numbers
     * 根据角色编号得到角色标识符列表
     * 
     * @param roleIds
     *        Role id set
     * @return
     *        Role set
     */
    public Set<String> findRoles(Long... roleIds);
    
    /**
     * According to the role name, check whether this role has been existing
     * 根据角色名，检查是否已经存在此角色
     * 
     * @param roleName
     *        Role name
     * @param customerCode
     *        Customer code
     * @return
     *        True: the existence, false: does not exist
     */
    public boolean checkRoleExist(String roleName, String customerCode);
    
    /**
     * Create role and resource related objects
     * 创建角色与资源关联对象
     * 
     * @param roleRes
     *        Role and resource related objects
     * @return
     *        Long
     */
    public Long createRoleResource(RoleResource roleRes);

    /**
     * According to the resource ID set, a number of roles and resource related objects are created at a time.
     * 根据资源id集，一次创建多个角色与资源关联对象
     * 
     * @param resourcesIds
     * 			Resource id set
     * @param roleId
     *        Role id
     */
    public void createRoleResources(String resourcesIds, Long roleId);
    
    /**
     * According to the role of ID, access to all the resources associated
     * 根据角色id，获取关联的所有资源
     * 
     * @param roleId
     *        Role id
     * @return
     *        Object collecton
     */
    public List<Resource> getResourcesByRoleId(Long roleId);
    
    /**
     * The role of the association through the user ID query
     * 通过用户id查询出关联的角色
     * 
     * @param userId
     *       User id
     * @return
     *       Object collecton
     */
    public List<Role> getRoleByUser(Long userId);
    
    

}
