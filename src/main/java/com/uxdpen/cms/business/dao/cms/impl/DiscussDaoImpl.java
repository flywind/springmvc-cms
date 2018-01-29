package com.uxdpen.cms.business.dao.cms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.cms.DiscussDao;
import com.uxdpen.cms.business.entities.cms.Discuss;

@Repository
public class DiscussDaoImpl extends AbstractFBaseDao<Discuss> implements DiscussDao {

	public int deleteDiscussByPostsId(Long id){
		StringBuilder hql = new StringBuilder("DELETE FROM Discuss WHERE postsId = :id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.ID_STRING, id);
		return this.executeHql(hql.toString(), params);
	}
	
	public List<Discuss> getDiscussByPostsId(Long id){
		
		StringBuilder hql = new StringBuilder("FROM Discuss WHERE isOpen = 1");
		StringBuilder condition = new StringBuilder(" AND postsId = :id");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(FBaseConstants.ID_STRING, id);
		
		hql.append(condition);
		return super.query(hql.toString(), params);
	}
	
	public List<Discuss> getAllDiscuss(Discuss d, FPage page){
		StringBuilder hql = new StringBuilder("FROM Discuss");
		StringBuilder countHql = new StringBuilder("SELECT COUNT(id) FROM Discuss");
		StringBuilder condition = new StringBuilder(" WHERE 1=1");
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(d.getMessage() != null){
			condition.append(" AND message Like :message");
			params.put("message", "%"+d.getMessage()+"%");
		}
		if(d.getPostsId() != null){
			condition.append(" AND postsId = :postsId");
			params.put("postsId", d.getPostsId());
		}
		if(d.getAuthor() != null){
			condition.append(" AND author = :author");
			params.put("author", d.getAuthor());
		}
		if(null != page){
			condition.append(" ORDER BY "+ page.getSortName()+ " "+page.getSortOrder());
		}
		
		countHql.append(condition);
		Long rows = super.count(countHql.toString(), params);
		
		page.setRowCount(rows.intValue());
		
		//int totalPages = JQueryUtils.findTotalPages(rows.intValue(), page.getPageSize());
		//page.setPageCount(totalPages);
		
		hql.append(condition);
		
		return super.query(hql.toString(), params, page.getPageNumber(), page.getPageSize());
		
	}
}
