package com.uxdpen.cms.business.dao.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.cms.Posts;

public interface PostsDao extends FBaseDao<Posts> {

	public List<Posts> getAllPosts(Posts posts, FPage page);
}
