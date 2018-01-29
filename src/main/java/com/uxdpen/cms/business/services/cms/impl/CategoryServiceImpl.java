package com.uxdpen.cms.business.services.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.dao.cms.CategoryDao;
import com.uxdpen.cms.business.entities.cms.Category;
import com.uxdpen.cms.business.services.cms.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	public Long createCategory(Category c){
		return categoryDao.save(c);
	}
	
	public void updateCategory(Category c){
		categoryDao.update(c);
	}
	
	public void deleteCategory(Category c){
		categoryDao.delete(c);
	}
	
	public Boolean deleteCategoryById(Long id){
		return categoryDao.deleteById(Category.class, id);
	}
	
	public List<Category> getAllCategory(){
		return categoryDao.getAllCategory();
	}
}
