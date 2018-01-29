package com.uxdpen.cms.business.services.cms;

import java.util.List;

import com.uxdpen.cms.business.entities.cms.Tag;

public interface TagService {

	public Long createTag(Tag t);
	
	public void updateTag(Tag t);
	
	public Boolean deleteTagById(Long id);
	
	public void deleteTag(Tag t);
	
	public List<Tag> getAllTags();
}
