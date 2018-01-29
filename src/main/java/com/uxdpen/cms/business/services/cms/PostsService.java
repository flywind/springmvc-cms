package com.uxdpen.cms.business.services.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.cms.Posts;

public interface PostsService {

	public Long createPost(Posts p);
	
	public void deletePosts(Posts p);
	
	public void deletePostsForSupperByIds(List<String> ids) throws Exception;
	
	public void deletePostsByIds(String userName, List<String> ids) throws Exception;
	
	public void updatePosts(Posts p);
	
	public Posts getPostsById(Long id);
	
	public List<Posts> getAllPosts(Posts posts, FPage page, String lang);
}
