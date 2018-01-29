package com.uxdpen.cms.business.dao.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.cms.Discuss;

public interface DiscussDao extends FBaseDao<Discuss> {
	
	public int deleteDiscussByPostsId(Long id);

	public List<Discuss> getDiscussByPostsId(Long id);
	
	public List<Discuss> getAllDiscuss(Discuss d, FPage page);
}
