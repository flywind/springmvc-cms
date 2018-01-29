package com.uxdpen.cms.business.dao.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.entities.cms.Category;

public interface CategoryDao extends FBaseDao<Category> {

	public List<Category> getAllCategory();
}
