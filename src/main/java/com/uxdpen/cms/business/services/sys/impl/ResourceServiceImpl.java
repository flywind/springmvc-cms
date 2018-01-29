package com.uxdpen.cms.business.services.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.exception.FException;
import com.uxdpen.cms.business.common.exception.FExceptionKey;
import com.uxdpen.cms.business.common.result.Json;
import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.sys.ResourceDao;
import com.uxdpen.cms.business.dao.sys.RoleResourceDao;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.RoleResource;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.services.sys.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	private static final Logger logger = Logger.getLogger(ResourceServiceImpl.class);
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
	private RoleResourceDao roleResDao;
	
	@Override
	//@FLog(infokey = FLogConstants.CREATE_RESOURCE, optype = FLogConstants.CREATE, logtype = FLogConstants.SYS_LOG)
	public Long createResource(Resource resource) {
		 return resourceDao.save(resource);
	}
	
	public Long createRoleRes(RoleResource roleRes) {
		return roleResDao.save(roleRes);
	}

	@Override
	//@FLog(infokey = FLogConstants.UPDATE_RESOURCE, logtype = FLogConstants.SYS_LOG, optype = FLogConstants.UPDATE)
	public void updateResource(Resource resource) throws FException {
		Resource oldResource = this.getResourceById(resource.getId());
		
		boolean isModify = false;
	
		if (!FBaseUtil.equals(resource.getName(), oldResource.getName())) {
			oldResource.setName(resource.getName());
			isModify = true;
		}
		
		if (!FBaseUtil.equals(resource.getNameEn(), oldResource.getNameEn())) {
			oldResource.setNameEn(resource.getNameEn());
			isModify = true;
		}
		
		if (!FBaseUtil.equals(resource.getType(), oldResource.getType())) {
			oldResource.setType(resource.getType());
			isModify = true;
			
			boolean hasChild = resourceDao.hasChildResource(resource.getId());
			if (hasChild) {
				throw new FException(FExceptionKey.RESOURCE_TYPE_NOT_ALLOW_UPDATE);
			}
		}

		if (!FBaseUtil.equals(resource.getUrl(), oldResource.getUrl())) {
			oldResource.setUrl(resource.getUrl());
			isModify = true;
		}
		
		if (!FBaseUtil.equals(resource.getPermission(), oldResource.getPermission())) {
			oldResource.setPermission(resource.getPermission());
			isModify = true;
		}
		
		if (!FBaseUtil.equals(resource.getIoncCls(), oldResource.getIoncCls())) {
			oldResource.setIoncCls(resource.getIoncCls());
			isModify = true;
		}
		
		if (resource.getSeqNum() != oldResource.getSeqNum()) {
			oldResource.setSeqNum(resource.getSeqNum());
			isModify = true;
		}
		
		if (!isModify) {
			throw new FException(FExceptionKey.NOT_UPDATE_ANY_INFO);
		}
		
		oldResource.setLastUpdatePerson(resource.getLastUpdatePerson());
		oldResource.setLastUpdateTime(new Date());
		resourceDao.update(oldResource);
	}

	@Override
	//@FLog(infokey = FLogConstants.DELETE_RESOURCE, optype = FLogConstants.DELETE, logtype = FLogConstants.SYS_LOG)
	public void deleteResource(Resource resource) throws FException {
		Long rId = resource.getId();
		
		boolean hasChild = resourceDao.hasChildResource(rId);
		if (hasChild) {
			throw new FException(FExceptionKey.RESOURCE_DELETE_FAIL_HAS_CHILD);
		}
		
		if (!resource.isAdministrator()) {
			boolean isUsing = roleResDao.checkResourceIsUsing(rId);
			if (isUsing) {
				throw new FException(FExceptionKey.RESOURCE_IS_USING);
			}
		} else {
			roleResDao.deleteRoleResourceByResourceId(rId);
		}
		
		resourceDao.deleteById(Resource.class, rId);
	}

	@Override
	public Resource getResourceById(Long resourceId) {
		return resourceDao.getById(Resource.class, resourceId);
	}
	
	@Override
	public Resource findAllForTree(String customerCode, Set<String> permissions) {
		
		//查找所有菜单
		List<Resource> list = resourceDao.findAll(customerCode);
		Map<Long, Resource> map = new HashMap<Long, Resource>();
		if (null != list && !list.isEmpty()) {
			Resource root = list.get(FBaseConstants.FIRST_INDEX);
			map.put(root.getId(), root);
			for (int i = 1; i < list.size(); i++) {
				Resource child = list.get(i);
				map.put(child.getId(), child);
				Resource mapRes = map.get(child.getParentId());
				if (null != mapRes) {
					List<Resource> childList = mapRes.getChildResource();
					if (null == childList) {
						childList = new ArrayList<Resource>();
						mapRes.setChildResource(childList);
					}
					
					if (hasPermission(permissions, child)) {
						childList.add(child);
					}
				}
			}
			return root;
		}
		return null;
	}
	
	@Override
	public Json findResourceByLoginUserToJson(User loginUser) {
		Json json = new Json();
		List<Resource> resources = resourceDao.findResourceByLoginUser(loginUser);
		json.setObj(resources);
		return json;
	}
	
	private boolean hasPermission(Set<String> permissions, Resource resource) {
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }

}
