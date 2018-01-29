package com.uxdpen.cms.business.dao.cms.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.cms.CategoryDao;
import com.uxdpen.cms.business.entities.cms.Category;

@Repository
public class CategoryDaoImpl extends AbstractFBaseDao<Category> implements CategoryDao{

	public List<Category> getAllCategory(){
		StringBuilder hql = new StringBuilder("FROM Category WHERE isShow = 1 ORDER BY seqNum ASC");
		
		return super.query(hql.toString());
	}
}
