package com.uxdpen.cms.business.services.sys.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.exception.FException;
import com.uxdpen.cms.business.common.exception.FExceptionKey;
import com.uxdpen.cms.business.common.result.Json;
import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.OrganizationDao;
import com.uxdpen.cms.business.dao.sys.ResourceDao;
import com.uxdpen.cms.business.dao.sys.RoleDao;
import com.uxdpen.cms.business.dao.sys.UserDao;
import com.uxdpen.cms.business.dao.sys.UserRoleDao;
import com.uxdpen.cms.business.entities.sys.Organization;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.Role;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.entities.sys.UserRole;
import com.uxdpen.cms.business.services.sys.PasswordHelper;
import com.uxdpen.cms.business.services.sys.UserService;

@SuppressWarnings("unchecked")
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private ResourceDao resDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
    private PasswordHelper passwordHelper;

	@Override
	//@FLog(infokey = FLogConstants.CREATE_USER, optype = FLogConstants.CREATE, logtype = FLogConstants.SYS_LOG)
	public Long createUser(User user) {	
		//加密密码
        passwordHelper.encryptPassword(user);
		return userDao.save(user);
	}

	@Override
	//@FLog(infokey = FLogConstants.UPDATE_USER, optype = FLogConstants.UPDATE, logtype = FLogConstants.SYS_LOG)
	public void updateUser(User user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	//@FLog(infokey = FLogConstants.DELETE_USER, optype = FLogConstants.DELETE, logtype = FLogConstants.SYS_LOG)
	public void deleteUser(User user) {
		
		userRoleDao.delUserRoleByUserId(user.getId());
		userDao.deleteById(User.class, user.getId());
	}
	
	@Override
	//@FLog(infokey = FLogConstants.DELETE_USER, optype = FLogConstants.DELETE, logtype = FLogConstants.SYS_LOG)
	public void deleteByIds(String ids){
		
	}
	
	@Override
	//@FLog(infokey = FLogConstants.UPDATE_PASSWORD, optype = FLogConstants.PASSWORD_UPDATE, logtype = FLogConstants.SYS_LOG)
	public void updatePassword(User user, String newPassword, String confirmPassword) throws Exception {

		if (!FBaseUtil.equals(newPassword, confirmPassword)) {
			throw new FException(FExceptionKey.TWO_PASSWORDS_NOT_SAME);
		}
		
		newPassword = DigestUtils.md5Hex(newPassword);
		if (FBaseUtil.equals(user.getPassword(), newPassword)) {
			throw new FException(FExceptionKey.NEW_PASSWORD_WITH_OLD_PASSWORD_THE_SAME);
		}
		
		user.setPassword(newPassword);
		user.setInitPwd(false);
		userDao.update(user);
	}

	@Override
	public User findOne(Long userId) {
		User user = userDao.getById(User.class, userId);
		return perfectUser(user);
	}

	@Override
	//@FLog(infokey = FLogConstants.SELECT_USER, optype = FLogConstants.SELECT, logtype = FLogConstants.SYS_LOG)
	public List<User> findAll(User loginUser,FPage pageingEnrity) {	
		List<User> userList = userDao.findAllByCreater(loginUser.getUsername(),loginUser.getCustomerCode(),pageingEnrity);
		for(User user :userList){
			perfectUser(user);
		}
		return userList;
	}
	@Override
	public User perfectUser(User user) {
		Set<Role> roleSet = roleDao.getRolesByUserId(user.getId());
		StringBuilder nameSb = new StringBuilder();
		if (null != roleSet && !roleSet.isEmpty()) {
			for (Role role : roleSet) {
				String name = role.getName();
				nameSb.append(name).append(",");
			}
		}
		if (nameSb.length() > 0) {
			user.setRoleNames(nameSb.toString().substring(0, nameSb.length() - 1));
			nameSb.delete(0, nameSb.length() - 1);
		}

		Organization organization = organizationDao.getById(Organization.class,
				user.getCompanyId());
		if (null != organization) {
			user.setCompanyName(organization.getName());
		}
		return user;
	}

	@Override
	public User findByUsername(String username, String customerCode) {
		return userDao.findByUsername(username, customerCode);
	}

	@Override
	public Set<String> findRoles(String username, String customerCode) {
		User user = findByUsername(username, customerCode);
		if (user == null) {
			return Collections.EMPTY_SET;
		}

		Set<Role> roleSet = roleDao.getRolesByUserId(user.getId());

		Set<String> roles = new HashSet<String>();

		if (null != roleSet && !roleSet.isEmpty()) {
			for (Role role : roleSet) {
				String roleName = role.getName();
				roles.add(roleName);
			}
		}
		return roles;
	}

	@Override
	public Set<String> findRoles(String username) {
		User user = findByUsername(username);
		if (user == null) {
			return Collections.EMPTY_SET;
		}

		Set<Role> roleSet = roleDao.getRolesByUserId(user.getId());

		Set<String> roles = new HashSet<String>();

		if (null != roleSet && !roleSet.isEmpty()) {
			for (Role role : roleSet) {
				String roleName = role.getName();
				roles.add(roleName);
			}
		}
		return roles;
	}

	@Override
	public Set<String> findPermissions(String username, String customerCode) {
		User user = findByUsername(username, customerCode);
		if (user == null) {
			return Collections.EMPTY_SET;
		}
		Set<String> ids = new HashSet<String>();
		Set<Role> roleSet = roleDao.getRolesByUserId(user.getId());
		if (null != roleSet && !roleSet.isEmpty()) {
			for (Role role : roleSet) {
				List<Resource> resList = resDao.getResourcesByRoleId(role.getId());
				if (null != resList && !resList.isEmpty()) {
					for (Resource res : resList) {
						if (res.getLevel() == 0) {
							continue;
						}
						ids.add(res.getPermission());
					}
				}
			}
		}

		return ids;
	}

	@Override
	public Set<String> findPermissions(String username) {
		User user = findByUsername(username);
		if (user == null) {
			return Collections.EMPTY_SET;
		}
		Set<String> ids = new HashSet<String>();
		Set<Role> roleSet = roleDao.getRolesByUserId(user.getId());
		if (null != roleSet && !roleSet.isEmpty()) {
			for (Role role : roleSet) {
				List<Resource> resList = resDao.getResourcesByRoleId(role.getId());
				if (null != resList && !resList.isEmpty()) {
					for (Resource res : resList) {
						ids.add(res.getPermission());
					}
				}
			}
		}

		return ids;
	}

	@Override
	public List<User> findByUserNameAndReadName(User loginUser,String userName, String readName, String companyId,String customerCode,FPage pageingEntity) {

		List<User> users = userDao.findByUserNameAndReadName(loginUser,userName, readName, companyId,customerCode,pageingEntity);
		User removeUser = null;
		for(User user:users){
			if(user.getId()==loginUser.getId()){
				removeUser=user;
			}
			perfectUser(user);
		}
		
		users.remove(removeUser);
		return users;
	}

	@Override
	public List<Role> findAllRoles(User loginUser) {
		return roleDao.getRoleByLoginUser(loginUser);
	}

	@Override
	public void createUserAndRole(Long userId, String roleIds) {
		if (!"".equals(roleIds) && null != roleIds) {
			String[] roleStrs = roleIds.split(",");
			for (String roleId : roleStrs) {
				UserRole userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(Long.parseLong(roleId));
				userRoleDao.save(userRole);
			}

		}

	}

	@Override
	public boolean checkUserExist(String  userName, String customerCode) {
		User user = findByUsername(userName, customerCode);
		if (null == user) {
			return false;
		}
		return true;
	}
	
	@Override
	public User findByUsername(String username, String emailOrPhone, String customerCode) {
		User user = userDao.findByUsername(username, emailOrPhone, customerCode);
		return user;
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public void updateUserAndRole(Long userId, String roleIds) {
		
		List<UserRole>  ownUserRoleList= userRoleDao.findRoleByUserId(userId);
		List<Long> oldResIdList = new ArrayList<Long>();
		for(UserRole userRole: ownUserRoleList ){
			oldResIdList.add(userRole.getRoleId());
		}
		
		List<Long> newResIds = new ArrayList<Long>();;
		if (!"".equals(roleIds) && null != roleIds) {
			String[] roleStrs = roleIds.split(",");
			for (String roleId : roleStrs) {
				newResIds.add(Long.parseLong(roleId));
			}
		}
		
		List<Long> addResIds = FBaseUtil.addIds(newResIds, oldResIdList);
		List<Long> delResIds = FBaseUtil.delIds(newResIds, oldResIdList);
		
		userRoleDao.bathSaveUserRole(userId, addResIds);
		
		userRoleDao.bathDeleteUserRole(userId,delResIds);
		

	}

	@Override
	public List<Role> findOwnRole(User LoginUser) {
		List<UserRole>  userRoleList=userRoleDao.findRoleByUserId(LoginUser.getId());
		List<Role> roles = new ArrayList<Role>();
		for(UserRole userRole:userRoleList){
			Role  role=roleDao.getById(Role.class, userRole.getRoleId());
			roles.add(role);
		}
		return roles;
	}

}
