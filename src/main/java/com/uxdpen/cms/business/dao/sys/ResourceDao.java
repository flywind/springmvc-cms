package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>Resource Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface ResourceDao extends FBaseDao<Resource> {
	
	/**
	 * Check out all the resources
	 * 查询出所有的资源
	 * 
	 * @return
	 *        Object collection
	 */
    List<Resource> findAll(String customerCode);
    
    /**
     * 通过登录用户，获取当前用户拥有的所有资源
     * 
     * @param loginUser
     *        Login user
     * @return
     *        Object collection
     */
    public List<Resource> findResourceByLoginUser(User loginUser);
    
    /**
     * Obtain the resources it owns according to role ID
     * 根据角色id获取其拥有的资源
     * 
     * @param roleId
     *        Role Id
     * @return
     *        Object collection
     */
    public List<Resource> getResourcesByRoleId(Long roleId);
    
    /**
     * Whether the current resource exists child resources
     * 当前资源是否存在子资源
     * 
     * @param resourceId
     *        Resource Id
     * @return
     *        True:exists, false:does not exist
     */
    public boolean hasChildResource(Long resourceId);
}
