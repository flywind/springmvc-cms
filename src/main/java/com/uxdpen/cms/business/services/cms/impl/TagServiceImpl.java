package com.uxdpen.cms.business.services.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.dao.cms.TagDao;
import com.uxdpen.cms.business.entities.cms.Tag;
import com.uxdpen.cms.business.services.cms.TagService;

@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagDao tagDao;
	
	public Long createTag(Tag t){
		return tagDao.save(t);
	}
	
	public void updateTag(Tag t){
		tagDao.update(t);
	}

	public Boolean deleteTagById(Long id){
		return tagDao.deleteById(Tag.class, id);
	}
	
	public void deleteTag(Tag t){
		tagDao.delete(t);
	}
	
	public List<Tag> getAllTags(){
		return tagDao.getAllTags();
	}
}
