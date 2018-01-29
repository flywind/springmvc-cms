package com.uxdpen.cms.business.dao.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.sys.OrganizationDao;
import com.uxdpen.cms.business.entities.sys.Organization;

/**
 * <p>Organization Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class OrganizationDaoImpl extends AbstractFBaseDao<Organization> implements OrganizationDao {

	@Override
	public Long createOrganization(Organization organization) {
		return super.save(organization);
	}

	@Override
	public void updateOrganization(Organization organization) {
		super.update(organization);
	}

	@Override
	public void deleteOrganization(Long organizationId) {
		super.deleteById(Organization.class, organizationId);     
	}
	
	public int deleteOrganizationByLikeParentIds(String parentIds) {
        final String hql = "DELETE FROM Organization WHERE parentIds like :makeSelfAsParentIds";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("makeSelfAsParentIds", parentIds + "%");
        return super.executeHql(hql, params);

	}

	@Override
	public Organization findOne(Long organizationId) {
		Organization  organization =super.getById(Organization.class, organizationId);
		return organization;
	}

	@Override
	public List<Organization> findAll() {
		String hql = "from Organization";
		return super.query(hql);
	}

	@Override
	public List<Organization> findAllWithExclude(Organization excludeOraganization) {
		final String hql = "from Organization where id!=:id and parentIds not like :makeSelfAsParentIds";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", excludeOraganization.getId());
		params.put("makeSelfAsParentIds", excludeOraganization.makeSelfAsParentIds() + "%");
		return super.query(hql, params);
       
	}

	@Override
	public void move(Organization source, Organization target) {
		String hql = "update Organization set parentId=:parentId,parentIds=:parentIds where id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", target.getId());
		params.put("parentIds", target.getParentIds());
		params.put("id", source.getId());
		super.executeHql(hql, params);

        String sql = "update td_r_organization set parent_ids=concat(:pids1, substring(parent_ids, length(:pids2))) where parent_ids like :pids3";
        Map<String, Object> parame2 = new HashMap<String, Object>();
        parame2.put("pids1", target.makeSelfAsParentIds());
        parame2.put("pids2", source.makeSelfAsParentIds());
        parame2.put("pids3", source.makeSelfAsParentIds() + "%");
        super.executeSql(sql, parame2);
	}

	@Override
	public List<Long> getOrganizationsByParentId(Long parentId) {
		
		Organization  oragnization = super.getById(Organization.class, parentId);
		List<Long>  ids= new ArrayList<Long>();
		if(null!=oragnization){
			String parentIds =oragnization.getParentIds();
			parentIds = parentIds+parentId+"/";
			String hql = "from  Organization where  parentIds like:parentIds";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parentIds", "%"+parentIds+"%");
			List<Organization>  organizations = super.query(hql, params);
		
			for(Organization organization:organizations){
				ids.add(organization.getId());
			}
		}
		return ids;
	}

	@Override
	public List<Organization> getOrgByParentId(Long parentId) {
		
		Organization  oragnization = super.getById(Organization.class, parentId);
		String parentIds =oragnization.getParentIds();
		parentIds = parentIds+parentId+"/";
		String hql = "from  Organization where  parentIds like:parentIds";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentIds", "%"+parentIds+"%");
		List<Organization>  organizations = super.query(hql, params);
		return organizations;
	}

	@Override
	public List<Organization> getOrgsByName(String orgName) {
	  String hql = "from Organization where name like:name";
	  Map<String, Object> params = new HashMap<String, Object>();
	  params.put("name", "%"+orgName+"%");
	  List<Organization>  organizations = super.query(hql, params);
	  return organizations;
	}

	@Override
	public List<Organization> getAllByCustomerCode(String customerCode) {
		String  hql = "from Organization where customerCode=:customerCode";
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("customerCode", customerCode);
		 List<Organization>  organizations = super.query(hql, params);
		return organizations;
	}
	
}
