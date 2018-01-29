package com.uxdpen.cms.business.services.sys;

import java.util.Set;

import com.uxdpen.cms.business.common.exception.FException;
import com.uxdpen.cms.business.common.result.Json;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.RoleResource;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>资源service</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface ResourceService {

	/**
	 * Create Resource
	 * 创建资源
	 * 
	 * @param resource
	 *        Resource
	 * @return
	 *        Long
	 */
	public Long createResource(Resource resource);
	
	/**
	 * On the relationship between role and resources
	 * 创建角色与资源的关联关系
	 * 
	 * @param roleRes
	 *        Role and Resource
	 * @return
	 *        Long
	 */
	public Long createRoleRes(RoleResource roleRes);
	
	/**
	 * Update Resource
	 * 编辑资源信息
	 * 
	 * @param resource
	 *        Resource
	 * @throws FException
	 *         Update exception
	 */
    public void updateResource(Resource resource) throws FException;
    
    /**
     * Delete Resource
     * 删除资源
     * 
     * @param resource
     *        Resource
     * @throws FException
     *         Delete exception
     */
    public void deleteResource(Resource resource) throws FException;

    /**
     * Obtain the corresponding resources according to ID
     * 根据id获取对应的资源
     * 
     * @param resourceId
     *        Resource id
     * @return
     *        Object
     */
    public Resource getResourceById(Long resourceId);
    
    /**
     * Get all the resources (tree structure)
     * 获取所有的资源(树形结构)
     * 
     * @param customerCode
     *        Customer code
     * @param permissions
     *        Permission set
     * @return
     *        Object
     */
    public Resource findAllForTree(String customerCode, Set<String> permissions);
    
    /**
     * Get all the resources that the current user owns by logging in to the user
     * 通过登录用户，获取当前用户拥有的所有资源
     * 
     * @param loginUser
     *        Login user
     * @return
     *        Json
     */
    public Json findResourceByLoginUserToJson(User loginUser);
}
