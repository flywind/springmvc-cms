package com.uxdpen.cms.business.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.result.Json;
import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.sys.OrganizationDao;
import com.uxdpen.cms.business.dao.sys.UserDao;
import com.uxdpen.cms.business.entities.sys.Organization;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.services.sys.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private UserDao userDao;

	@Override
	//@FLog(infokey = FLogConstants.CREATE_ORGANIZATION, optype = FLogConstants.CREATE, logtype = FLogConstants.OPERATION_LOG)
	public Long createOrganization(Organization organization) {
		return organizationDao.save(organization);
	}

	@Override
	//@FLog(infokey = FLogConstants.UPDATE_ORGANIZATION, optype = FLogConstants.UPDATE, logtype = FLogConstants.OPERATION_LOG)
	public void updateOrganization(Organization organization) {
		organizationDao.updateOrganization(organization);
	}

	@Override
	//@FLog(infokey = FLogConstants.DELETE_ORGANIZATION, optype = FLogConstants.DELETE, logtype = FLogConstants.OPERATION_LOG)
	public void deleteOrganizationById(Organization organization) {
		organizationDao.deleteById(Organization.class, organization.getId());
	}
	
	@Override
	//@FLog(infokey = FLogConstants.DELETE_ORGANIZATION, optype = FLogConstants.DELETE, logtype = FLogConstants.OPERATION_LOG)
	public void deleteOrganizationByIds(String ids){
		List<Long> list = FBaseUtil.idsToList(ids);
		for(Long id : list){
			organizationDao.deleteById(Organization.class, id);
		}
	}

	@Override
	public Organization findOne(Long organizationId) {
		return organizationDao.findOne(organizationId);
	}
	


	@Override
	public Json findAllWithExcludeToJson(Organization excludeOraganization) {
		Json json = new Json();
		List<Organization> organizations = organizationDao.findAllWithExclude(excludeOraganization);
		json.setObj(organizations);
		return json;
	}

	@Override
	public void move(Organization source, Organization target) {
		organizationDao.move(source, target);
	}

	@Override
	public List<Organization> findAllOrganizationByUser(User loginUser) {
		
		List<Long> ids = new ArrayList<Long>();
		
		ids = getOrganizationsByParentId(loginUser.getCompanyId());
		
		ids.add(loginUser.getCompanyId());
		List<Organization> organizations = new ArrayList<Organization>();
		for (Long id : ids) {
			Organization organization = organizationDao.getById(Organization.class, id);
			organizations.add(organization);
		}
		
		return organizations;
	}

	@Override
	public List<Long> getOrganizationsByParentId(Long parentId) {
		
		return organizationDao.getOrganizationsByParentId(parentId);
	}

	@Override
	public Json getOrgByParentIToJson(Long parentId) {
		Json json = new Json();
		List<Organization> organizations = organizationDao.getOrgByParentId(parentId);
		Organization organization = organizationDao.getById(Organization.class, parentId);
		organizations.add(organization);
		json.setObj(organizations);
		return json;
	}

	@Override
	public List<User> getUsersByOrgId(String customerCode, List<Long> organizationIds) {
		return userDao.findAll(customerCode, organizationIds);
	}

	@Override
	public List<Long> getOrgsByName(String orgName,User loginUser) {
		
		List<Long> ids = new ArrayList<Long>();
		List<Long> orgIds = new ArrayList<Long>();
		
		ids = getOrganizationsByParentId(loginUser.getCompanyId());
		
		ids.add(loginUser.getCompanyId());
		for (Long id : ids) {
			Organization organization = organizationDao.getById(Organization.class, id);
			if(organization.getName().contains(orgName)){
				orgIds.add(id);	
			}
			
		}
		return orgIds;
	}

	@Override
	public Json findAllByCustomerCode(String customerCode) {
		Json json = new Json();
		List<Organization> organizations = organizationDao.getAllByCustomerCode(customerCode);
		json.setObj(organizations);
		return json;
	}
	
	@Override
	public int deleteChildren(Organization o) {
		String parentIds = o.getParentIds() + o.getId() + "/";
		return organizationDao.deleteOrganizationByLikeParentIds(parentIds);
	}
}
