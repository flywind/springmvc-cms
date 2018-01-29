package com.uxdpen.cms.business.services.sys.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.exception.FException;
import com.uxdpen.cms.business.common.exception.FExceptionKey;
import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.ResourceDao;
import com.uxdpen.cms.business.dao.sys.RoleDao;
import com.uxdpen.cms.business.dao.sys.RoleResourceDao;
import com.uxdpen.cms.business.dao.sys.UserRoleDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.Role;
import com.uxdpen.cms.business.entities.sys.RoleResource;
import com.uxdpen.cms.business.services.sys.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final static Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Autowired
    private RoleDao roleDao;
	
	@Autowired
	private ResourceDao resDao;
	
	@Autowired
	private RoleResourceDao roleResDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	//@FLog(infokey = FLogConstants.CREATE_ROLE, optype = FLogConstants.CREATE, logtype = FLogConstants.SYS_LOG)
	public Long createRole(Role role) {
		return roleDao.save(role);
	}

	@Override
	//@FLog(infokey = FLogConstants.UPDATE_ROLE, optype = FLogConstants.UPDATE, logtype = FLogConstants.SYS_LOG)
	public void updateRole(Role role, String oldDesc, String resourceIds, String oldResIds) throws FException {
		boolean isModify = false;
		
		if (!FBaseUtil.equals(role.getDescription(), oldDesc)) {
			updateRole(role);
			isModify = true;
		}
		
		List<Long> newResIds = FBaseUtil.idsToList(resourceIds);
		List<Long> oldResIdList = FBaseUtil.idsToList(oldResIds);

		List<Long> addResIds = FBaseUtil.addIds(newResIds, oldResIdList);
		List<Long> delResIds = FBaseUtil.delIds(newResIds, oldResIdList);
		
		if (addResIds.size() == 0 && delResIds.size() == 0 && !isModify) {
			throw new FException(FExceptionKey.NOT_UPDATE_ANY_INFO);
		}
		
		roleResDao.createRoleResources(addResIds, role.getId());

		roleResDao.deleteRoleResources(FBaseUtil.listToIds(delResIds), role.getId());
		if (!isModify) {
			updateRole(role);
		}
	}

	@Override
	//@FLog(infokey = FLogConstants.DELETE_ROLE, logtype = FLogConstants.SYS_LOG, optype = FLogConstants.DELETE)
	public void deleteRole(Role role) throws FException {

		boolean isUsing = userRoleDao.checkRoleIsUsing(role.getId());
		if (isUsing) {
			throw new FException(FExceptionKey.ROLE_IS_USING);
		}
		
		roleResDao.deleteRoleResourceByRoleId(role.getId());
		
		roleDao.delete(role);
	}

	@Override
	public Role getRoleById(Long roleId) {
		return roleDao.getById(Role.class, roleId);
	}
	
	@Override
	//@FLog(infokey = FLogConstants.QUERY_ROLE, optype = FLogConstants.SELECT, logtype = FLogConstants.SYS_LOG)
	public List<Role> findAll(Role role, FPage paging, FSysInfo session) {
		List<Role> roles = roleDao.findAll(role, paging, session);
		return roles;
	}
	
	@Override
    public Set<String> findRoles(Long... roleIds) {
        Set<String> roles = new HashSet<String>();
        for(Long roleId : roleIds) {
            Role role = getRoleById(roleId);
            if(role != null) {
                roles.add(role.getName());
            }
        }
        return roles;
    }
	
	@Override
	public boolean checkRoleExist(String roleName, String customerCode) {
		return roleDao.checkRoleExist(roleName, customerCode);
	}
	
	@Override
	public Long createRoleResource(RoleResource roleRes) {
		return roleResDao.save(roleRes);
	}
	
	@Override
	public void createRoleResources(String resourcesIds, Long roleId) {
		String[] resIds = FBaseUtil.split(resourcesIds, ",");
		for (String resId : resIds) {
			RoleResource roleRes = new RoleResource(roleId, Long.parseLong(resId));
			roleResDao.save(roleRes);
		}
	}
	
	@Override
	public List<Resource> getResourcesByRoleId(Long roleId) {
		return resDao.getResourcesByRoleId(roleId);
	}
	
	@Override
	public List<Role> getRoleByUser(Long userId) {
		return roleDao.getRoleByUser(userId);
	}
	
	private void updateRole(Role role) {
		Role tmpRole = roleDao.getById(Role.class, role.getId());
		tmpRole.setDescription(role.getDescription());
		tmpRole.setLastUpdatePerson(role.getLastUpdatePerson());
		tmpRole.setLastUpdateTime(new Date());
		roleDao.update(tmpRole);
	}

}
