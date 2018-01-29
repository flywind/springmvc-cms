package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.constants.FSysConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.sys.ResourceDao;
import com.uxdpen.cms.business.entities.sys.Resource;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>Resource Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class ResourceDaoImpl extends AbstractFBaseDao<Resource> implements ResourceDao {

	@Override
	public List<Resource> findAll(String customerCode) {
		String hql = "FROM Resource WHERE customerCode = :customerCode ORDER BY parentId, seqNum ASC";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		return super.query(hql,params);
	}
	
	@Override
	public List<Resource> findResourceByLoginUser(User loginUser) {
		String hql = "FROM Resource r WHERE r.id IN (SELECT rr.resourceId FROM RoleResource rr WHERE rr.roleId IN "
				+ "(SELECT ur.roleId FROM UserRole ur WHERE ur.userId = :userId))";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FSysConstants.USER_ID, loginUser.getId());
		return this.query(hql, params);
	}
	
	@Override
	public List<Resource> getResourcesByRoleId(Long roleId) {
		String hql = "SELECT r FROM Resource r, RoleResource res WHERE res.roleId = :id AND res.resourceId = r.id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, roleId);
		return this.query(hql, params);
	}
	
	@Override
	public boolean hasChildResource(Long resourceId) {
		Resource resource = this.getById(Resource.class, resourceId);
		
		String parentId = resource.getParentIds() + resource.getId() + "/";
		String hql = "SELECT count(id) FROM Resource WHERE parentIds = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, parentId);
		boolean result = this.count(hql, params) > 0 ? true : false;
		return result;
	}
}
