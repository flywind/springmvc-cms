package com.uxdpen.cms.business.services.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.cms.Discuss;

public interface DiscussService {

	public Long createDiscuss(Discuss d);
	
	public void updateDiscuss(Discuss d);
	
	public void deleteDiscuss(Discuss d);
	
	public Boolean deleteDiscussById(Long id);
	
	public List<Discuss> getDiscussByPostsId(Long id);
	
	public List<Discuss> getAllDiscuss(Discuss d, FPage page);
	
	public void deleteDsicussByIds(List<String> ids) throws Exception;
}
