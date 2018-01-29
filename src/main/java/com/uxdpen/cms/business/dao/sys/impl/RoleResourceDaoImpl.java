package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.sys.RoleResourceDao;
import com.uxdpen.cms.business.entities.sys.RoleResource;

/**
 * <p>Role Resource Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class RoleResourceDaoImpl extends AbstractFBaseDao<RoleResource> implements RoleResourceDao {

	@Override
	public int deleteRoleResourceByRoleId(Long roleId) {
		String deleteHql = "DELETE FROM RoleResource rr WHERE rr.roleId = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, roleId);
		return this.executeHql(deleteHql, params);
	}
	
	@Override
	public int deleteRoleResourceByResourceId(Long resourceId) {
		String deleteHql = "DELETE FROM RoleResource rr WHERE rr.resourceId = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, resourceId);
		return this.executeHql(deleteHql, params);
	}
	
	@Override
	public boolean checkResourceIsUsing(Long resourceId) {
		String checkHql = "SELECT count(id) FROM RoleResource ur WHERE ur.resourceId = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, resourceId);
		Long useCount = this.count(checkHql, params);
		return useCount > 0 ? true : false;
	}
	
	@Override
	public void createRoleResources(List<Long> resourceIds, Long roleId) {
		for (Long resId : resourceIds) {
			RoleResource rr = new RoleResource(roleId, resId);
			this.save(rr);
		}
	}
	
	@Override
	public int deleteRoleResources(String resourceIds, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, roleId);
		
		//Remove the resources (permissions) of all the characters created by this role first.
		StringBuilder delChildHql = new StringBuilder("DELETE FROM RoleResource rr WHERE rr.resourceId IN (");
		delChildHql.append(resourceIds).append(") AND rr.roleId IN (SELECT ur.roleId FROM UserRole ur WHERE ur.userId IN (");
		delChildHql.append("SELECT u.id FROM User u WHERE u.parentId LIKE CONCAT((");
		delChildHql.append("SELECT CONCAT(u2.parentId, u2.id, '/') FROM User u2 WHERE u2.id IN (");
		delChildHql.append("SELECT ur2.userId FROM UserRole ur2 WHERE ur2.roleId = :id)), '%')))");
		this.executeHql(delChildHql.toString(), params);
		
		String delHql = "DELETE FROM RoleResource WHERE roleId = :id AND resourceId IN (" + resourceIds + ")";
		return this.executeHql(delHql, params);
	}
}
