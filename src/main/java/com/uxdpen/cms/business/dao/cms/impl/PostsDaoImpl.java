package com.uxdpen.cms.business.dao.cms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.cms.PostsDao;
import com.uxdpen.cms.business.entities.cms.Posts;

@Repository
public class PostsDaoImpl extends AbstractFBaseDao<Posts> implements PostsDao {

	public List<Posts> getAllPosts(Posts posts, FPage page){
		StringBuilder hql = new StringBuilder("FROM Posts");
		StringBuilder countHql = new StringBuilder("SELECT COUNT(id) FROM Posts");
		StringBuilder condition = new StringBuilder(" WHERE isOpen = 1");
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(posts.getIsHome()){
			condition.append(" AND categoryId != 1");
		}
		
		if(posts.getIsHot()){
			condition.append(" AND isHot = 1");
		}
		
		if(null != posts.getAuthor()){
			condition.append(" AND author = :author");
			params.put("author", posts.getAuthor());
		}
		
		if(null != posts.getTitle()){
			condition.append(" AND title Like :stitle");
			params.put("stitle", "%"+posts.getTitle().trim()+"%");
		}
		if(null != posts.getCategoryId()){
			if(posts.getCategoryId() != 0L){
				condition.append(" AND categoryId = :categoryId");
				params.put("categoryId", posts.getCategoryId());
			}
		}
		
		if(null != posts.getTagId()){
			condition.append(" AND tagId = :tagId");
			params.put("tagId", posts.getTagId());
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
