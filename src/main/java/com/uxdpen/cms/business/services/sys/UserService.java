package com.uxdpen.cms.business.services.sys;

import java.util.List;
import java.util.Set;

import com.uxdpen.cms.business.common.result.Json;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.sys.Role;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>用户管理接口</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface UserService {
	
	/**
	 * Create user
     * 创建用户
     * 
     * @param user
     * 		User	
     */
    public Long createUser(User user);
    /**
     * Update user
     * 修改用户
     * 
     * @param user
     * 			User
     */

    public void updateUser(User user);
   
    /**
     * Delete user
     * 删除用户
     * 
     * @param user
     * 			User
     */
    public void deleteUser(User user);
    
    /**
     * Delete user
     * 删除用户
     * 
     * @param ids
     * 			User set
     */
    public void deleteByIds(String ids);

    /**
     * Update password
     * 修改密码
     * 
     * @param user
     *        	User
     * @param newPassword
     *        New password
     * @param confirmPassword
     *        Confirm password
     */
    public void updatePassword(User user, String newPassword, String confirmPassword) throws Exception;

  
    /**
     * Find users through ID
     * 通过ID查找用户
     * 
     * @param userId
     * 			User id
     * @return
     * 			Object
     */
    public User findOne(Long userId);
   
    /**
     * By logging in to the user to query all users
     * 通过登录用户查询所有用户
     * 
     * @param loginUser
     * 			Login user
     * @return
     * 			Object collecton
     */
    public List<User> findAll(User loginUser,FPage pageingEnrity);

    /**
     * Search user based on user name
     * 根据用户名查找用户
     * 
     * @param username
     * 			User name
     * @return
     * 			Object
     */
    public User findByUsername(String username,String customerCode);
    
    /**
     * Search user based on user name
     * 根据用户名查找用户
     * 
     * @param username
     * 			User name
     * @return
     * 			Object
     */
    public User findByUsername(String username);

    /**
     * Find the role according to the user name
     * 根据用户名查找其角色
     * 
     * @param username
     * 			User name
     * @return
     * 			Role set
     */
    public Set<String> findRoles( String username,String customerCode);
    
    /**
     * Find the role according to the user name
     * 根据用户名查找其角色
     * 
     * @param username
     * 			User name
     * @return
     * 			Role set
     */
    public Set<String> findRoles( String username);
    
    /**
     * By the user to query their own role
     * 通过用户查询自己所拥有的角色
     * 
     * @return
     * 			Json
     */
    public List<Role> findOwnRole(User LoginUser);
    
    /**
     * Inquire all the characters you created
     * 查询所有自己创建的角色
     * 
     * @return
     * 			Json
     */
    public List<Role> findAllRoles(User loginUser);

    /**
     * Find the permissions according to the user name
     * 根据用户名查找其权限
     * 
     * @param username
     * 			User name
     * @return
     * 			Permission set
     */
    public Set<String> findPermissions(String username,String customerCode);
    
    /**
     * Find the permissions according to the user name
     * 根据用户名查找其权限
     * 
     * @param username
     * 			User name
     * @return
     * 			Permission set
     */
    public Set<String> findPermissions(String username);
    
    /**
     * View user via user name or user name
     * 通过用户名或者用户真实姓名查看用户
     * 
     * @param userName
     * 			User name
     * @param readName
     * 			Real name
     * @return 
     * 			Object collecton
     */
    public List<User> findByUserNameAndReadName(User loginUser,String userName,String readName,String companyId ,String customerCode,FPage pageingEntity);
    
    /**
     * The relationship between user and role
     * 创建用户与角色的关系
     * 
     * @param userId 
     * 			User id
     * @param roleIds
     * 			Role id set
     */
    public void createUserAndRole(Long userId,String roleIds);
    
    /**
     * Update user and role management
     * 更新用户与角色管理表
     * 
     * @param userId
     * 			User id
     * @param roleIds
     * 			Role id set
     */
    public void updateUserAndRole(Long userId,String roleIds);
    
    /**
     * By the user name to detect whether the database already exists
     * 通过用户名检测数据库中是否已经存在
     * 
     * @param userName
     * 			User name
     * @param customerCode
     * 			Customer code
     * @return
     * 			
     */
    public boolean checkUserExist(String userName,String customerCode);
    
    /**
     * Universal user name, mailbox or phone number to check whether there is the user
     * 通用用户名、邮箱或手机号检查是否存在该用户
     * 
     * @param username
     *        User name
     * @param emailOrPhone
     *        Email or phone
     * @param customerCode
     *        Customer code
     * @return
     *        True: exists, false: does not exist
     */
    public User findByUsername(String username, String emailOrPhone, String customerCode);
    
    /**
     * Improve the User object, increase the organization's name and role name
	 * 完善User对象，增加组织机构名和角色名
	 * 
	 * @param user
	 * 			User
	 * @return
	 * 			Object
	 */
	public User perfectUser(User user);
    
}
