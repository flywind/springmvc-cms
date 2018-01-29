package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>User Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface UserDao extends FBaseDao<User> {
	
	/**
	 * All users through the customerCode query
     * 通过 customerCode 查询所有的用户
     * 
     * @param customerCode
     * 			Customer code
     * @return
     * 			Object collecton
     */
    List<User> findAll(String customerCode,List<Long> organizationIds);
   
    /**
     * Query user via user name and customer account
     * 通过用户名和客户账户查询用户
     * 
     * @param username
     * 			User name
     * @param customerCode
     * 			Customer code
     * @return
     * 			Object collecton
     */
    User findByUsername(String username,String customerCode);
    
    /**
     * Query user via user name
     * 通过用户名查询用户
     * 
     * @param username
     * 			User name
     * @return
     * 			Object
     */
    User findByUsername(String username);
    
    /**
     * Find users by name and real name
     * 通过用户名和真实姓名查找用户
     * 
     * @param userName
     * 			User name
     * @param readName
     * 			Real name
     * @param companyId
     * 			Company id
     * @return
     * 			Object collecton
     */
    List<User> findByUserNameAndReadName(User loginUser,String userName, String readName,String companyId,String CustomerCode,FPage pageingEntity);
    
    /**
     * Search the user through the creator and customer
     * 通过创建者和客户查用户
     * 
     * @param createrName
     * 			Creater name
     * @param customerCode
     * 			Customer code
     * @return
     * 			Object collecton
     */
    public List<User> findAllByCreater(String createrName, String customerCode,FPage pageingEnrity);
    
    /**
     * General user name, mailbox or phone number to find users
     * 通用用户名、邮箱或手机号查找用户
     * 
     * @param username
     *        	User name
     * @param emailOrPhone
     *       	 Email or phone
     * @param customerCode
     *        	Customer code
     * @return
     *        	Object
     */
    public User findByUsername(String username, String emailOrPhone, String customerCode);
}
