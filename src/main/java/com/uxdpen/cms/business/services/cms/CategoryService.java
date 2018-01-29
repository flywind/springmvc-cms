package com.uxdpen.cms.business.services.cms;

import java.util.List;

import com.uxdpen.cms.business.entities.cms.Category;

public interface CategoryService {

	public Long createCategory(Category c);
	
	public void updateCategory(Category c);
	
	public void deleteCategory(Category c);
	
	public Boolean deleteCategoryById(Long id);
	
	public List<Category> getAllCategory();
}
