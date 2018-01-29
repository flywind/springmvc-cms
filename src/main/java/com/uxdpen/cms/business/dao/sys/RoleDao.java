package com.uxdpen.cms.business.dao.sys;

import java.util.List;
import java.util.Set;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Role;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>Role Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface RoleDao extends FBaseDao<Role> {
	
	/**
	 * Get all the characters used in the advanced search
     * 获取所有的角色，应用于高级搜索
     * 
     * @param role
     *        	Role
     * @param paging
     *        	Pagination
     * @param session
     *        	System base info
     * @return
     *        	Object collection
     */
    public List<Role> findAll(Role role, FPage paging, FSysInfo session);
    
    /**
     * According to the role name, check whether this role has been existing
     * 根据角色名，检查是否已经存在此角色
     * 
     * @param roleName
     *        	Role name
     * @param customerCode
     *        	Customer code
     * @return
     *        	true:exist False:does not exist
     */
    public boolean checkRoleExist(String roleName, String customerCode);
    
    /**
     * All the roles that are related through the user ID
     * 通过用户id查询出关联的所有的角色
     * 
     * @param userId
     *        	User Id
     * @return
     *        	Object collection
     */
    public Set<Role> getRolesByUserId(Long userId);
    
    /**
     * Query the role created by the creator and customer
     * 通过创建者和customer查询出自己创建的角色
     * 
     * @param loginUser
     * 			Login user
     * @return
     * 			Object collection
     */
    public List<Role> getRoleByLoginUser(User loginUser);
    
    /**
     * 通过用户id查询出关联的角色
     * 
     * @param userId
     *       	User Id
     * @return
     *       	Object collection
     */
    public List<Role> getRoleByUser(Long userId);
    
}
