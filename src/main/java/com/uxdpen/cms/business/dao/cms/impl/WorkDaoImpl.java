package com.uxdpen.cms.business.dao.cms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.cms.WorkDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.cms.Work;

/**
 * <p>Work Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class WorkDaoImpl extends AbstractFBaseDao<Work> implements WorkDao {

	@Override
	public List<Work> findAll(Work example, FPage paging, FSysInfo session) {
		StringBuilder hql = new StringBuilder("FROM Work");
		StringBuilder countHql = new StringBuilder("SELECT COUNT(id) FROM Work");
		StringBuilder condition = new StringBuilder(" WHERE customerCode = :customerCode");
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, session.getCustomerCode());
		
		if(example != null){
			
			if (StringUtils.isNoneBlank(example.getTitle())) {
				condition.append(" and title LIKE :title");
				params.put("title", "%" + example.getTitle().trim() + "%");
			}
			
			if (StringUtils.isNoneBlank(example.getTitleEn())) {
				condition.append(" and titleEn LIKE :titleEn");
				params.put("titleEn", "%" + example.getTitleEn().trim() + "%");
			}
			
			if (example.getType() != 0){
				condition.append(" and type =:type");
				params.put("type", example.getType());
			}
		}
		
		if (null != paging) { 
			condition.append(" order by " + paging.getSortName() + " " + paging.getSortOrder());
		}
		
		countHql.append(condition);
		Long count = super.count(countHql.toString(), params);
		paging.setRowCount(count.intValue());
		
		hql.append(condition);
		return super.query(hql.toString(), params, paging.getPageNumber(), paging.getPageSize());
	}
	
	@Override
	public List<Work> findAll(Work example, FPage paging, String customerCode) {
		StringBuilder hql = new StringBuilder("FROM Work");
		StringBuilder countHql = new StringBuilder("SELECT COUNT(id) FROM Work");
		StringBuilder condition = new StringBuilder(" WHERE customerCode = :customerCode");
		condition.append(" and type =:type");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		params.put("type", example.getType());
		
		if (null != example.getTitle()) {
			condition.append(" AND name LIKE :title");
			params.put("title", "%" + example.getTitle().trim() + "%");
		}
		
		if (null != paging) { 
			condition.append(" order by " + paging.getSortName() + " " + paging.getSortOrder());
		}
		
		countHql.append(condition);
		Long count = super.count(countHql.toString(), params);
		paging.setRowCount(count.intValue());
		
		hql.append(condition);
		return super.query(hql.toString(), params, paging.getPageNumber(), paging.getPageSize());
	}
	
	public List<Work> getListForLoop(Work example, FPage page, String customerCode){
		
		String countHql = "select count(t.id) from Work t where t.customerCode=:customerCode";
		String hql = "from Work where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		
		hql += " and isOpen = 1 and type=:type";
		params.put("type", example.getType());
		countHql += " and t.isOpen = 1 and t.type=:type";
		paramsc.put("type", example.getType());
		
		if(StringUtils.isNotEmpty(example.getTitle())){
			hql += " and title like:title";
			params.put("title", "%"+example.getTitle()+"%");
			countHql += " and title like:title";
			paramsc.put("title", "%"+example.getTitle()+"%");
			
		}
		
		if(StringUtils.isNotEmpty(example.getTitleEn())){
			hql += " and titleEn like:titleEn";
			params.put("titleEn", "%"+example.getTitleEn()+"%");
			countHql += " and titleEn like:titleEn";
			paramsc.put("titleEn", "%"+example.getTitleEn()+"%");
			
		}

		paramsc.put("customerCode", customerCode);
		Long total = this.count(countHql, paramsc);
		page.setRowCount(total.intValue()); 
		
		params.put("customerCode", customerCode);
		
		//int totalPages = JQueryUtils.findTotalPages(total.intValue(), page.getPageSize());
		//page.setPageCount(totalPages);
		
		return this.query(hql, params, page.getPageNumber(), page.getPageSize());
	}
	
	public List<Work> getListForHot(Work example, FPage page, String customerCode){
		
		String countHql = "select count(t.id) from Work t where t.customerCode=:customerCode";
		String hql = "from Work where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		
		hql += " and isOpen = 1 and isHot = 1";
		countHql += " and t.isOpen = 1  and t.isHot = 1";

		paramsc.put("customerCode", customerCode);
		Long total = this.count(countHql, paramsc);
		page.setRowCount(total.intValue()); 
		
		params.put("customerCode", customerCode);
		
		//int totalPages = JQueryUtils.findTotalPages(total.intValue(), page.getPageSize());
		//page.setPageCount(totalPages);
		
		return this.query(hql, params, page.getPageNumber(), page.getPageSize());
	}
	
	public List<Work> getAllList(Work example, FPage page, String customerCode){
		
		String countHql = "select count(t.id) from Work t where t.customerCode=:customerCode";
		String hql = "from Work where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		
		hql += " and isOpen =:isOpen order by id DESC";
		countHql += " and t.isOpen =:isOpen";

		paramsc.put("customerCode", customerCode);
		
		paramsc.put("isOpen", example.getIsOpen());
		Long total = this.count(countHql, paramsc);
		page.setRowCount(total.intValue()); 
		
		params.put("customerCode", customerCode);
		params.put("isOpen", example.getIsOpen());
		
		//int totalPages = JQueryUtils.findTotalPages(total.intValue(), page.getPageSize());
		//page.setPageCount(totalPages);
		
		List<Work> ws = this.query(hql, params, page.getPageNumber(), page.getPageSize());
		
		return ws;
	}
}
