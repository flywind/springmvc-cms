package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.constants.FSysConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.sys.UserRoleDao;
import com.uxdpen.cms.business.entities.sys.UserRole;

/**
 * <p>User Role Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class UserRoleDaoImpl extends AbstractFBaseDao<UserRole> implements UserRoleDao {

	@Override
	public boolean checkRoleIsUsing(Long roleId) {
		String checkHql = "SELECT count(id) FROM UserRole ur WHERE ur.roleId = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, roleId);
		Long useCount = this.count(checkHql, params);
		return useCount > 0 ? true : false;
	}

	@Override
	public boolean delUserRoleByUserId(Long userId) {
		String deleteHql ="delete from UserRole where userId=:userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FSysConstants.USER_ID, userId);
		int count=super.executeHql(deleteHql, params);
		return count > 0 ? true : false;
	}

	@Override
	public List<UserRole> findRoleByUserId(Long userId) {
		String hql ="from UserRole where userId=:userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FSysConstants.USER_ID, userId);
		return this.query(hql, params);
	}

	@Override
	public void bathSaveUserRole(Long userId, List<Long> roleIds) {
		if(null!=roleIds&&roleIds.size()>0){
			for(Long roleId :roleIds){
				UserRole UserRole =new UserRole();
				UserRole.setRoleId(roleId);
				UserRole.setUserId(userId);
				this.save(UserRole);
			}
		}
		
	}

	@Override
	public void bathDeleteUserRole(Long userId, List<Long> roleIds) {
	    StringBuffer  sb= new StringBuffer();
		if(null!=roleIds&&roleIds.size()>0){
			for(Long roleId:roleIds){
				sb.append(roleId).append(",");
			}
			String inStr = sb.substring(0, sb.length() - 1);
			String delHql = "DELETE FROM UserRole WHERE userId = :userId AND roleId IN (" + inStr + ")";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FSysConstants.USER_ID, userId);
		    this.executeHql(delHql, params);
		}else{
			return;
		}
			
	}

}
