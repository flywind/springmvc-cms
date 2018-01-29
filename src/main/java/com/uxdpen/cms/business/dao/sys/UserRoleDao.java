package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.entities.sys.UserRole;

/**
 * <p>User Role Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface UserRoleDao extends FBaseDao<UserRole> {
	
	/**
	 * Check whether the role is used by the user
	 * 检查角色是否被用户使用
	 * 
	 * @param roleId
	 *       	 Role id
	 * @return
	 *        	True: used, false: not used
	 */
	public boolean checkRoleIsUsing(Long roleId);
	
	/**
	 * Delete management table by userid
	 * 通过userid删除管理表
	 * 
	 * @param userId
	 * 			User id
	 * @return
	 * 			boolean
	 */
	public boolean delUserRoleByUserId(Long userId);
	
	/**
	 * Through the user ID query all their own roles
	 * 通过用户id查询所有的自己所拥有的角色
	 * 
	 * @param userId
	 * 			User id
	 * @return
	 * 			Object collecton
	 */
	public List<UserRole> findRoleByUserId(Long userId);
	
	/**
	 * Batch add user role management
	 * 批量新增用户角色管理表
	 * 
	 * @param userId
	 * 			User id
	 * @param roleIds
	 * 			Role id set
	 * 			
	 */
	public void  bathSaveUserRole(Long userId,List<Long> roleIds);
	
	/**
	 * Batch delete user and role management
	 * 批量删除用户与角色管理表
	 * 
	 * @param userId
	 * 			User id
	 * @param roleIds
	 * 			Role id set
	 */
	public void  bathDeleteUserRole(Long userId,List<Long> roleIds);

}
