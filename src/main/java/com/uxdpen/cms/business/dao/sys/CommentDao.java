package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Comment;

/**
 * <p>Comment Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface CommentDao extends FBaseDao<Comment>{

	/**
	 * Get comments based on conditions
	 * 根据条件获得评论
	 * 
	 * @param c
	 * 			Comment
	 * @param paging
	 * 			Pagination
	 * @param session
	 * 			System base info
	 * @return
	 * 			Object collection
	 */
	public List<Comment> findAll(Comment c, FPage paging, FSysInfo session);
	
	/**
	 * Get comments based on conditions
	 * 根据条件获得评论
	 * 
	 * @param id
	 * 			ID(Primary key)
	 * @param paging
	 * 			Pagination
	 * @param customerCode
	 * 			
	 * @return
	 * 			Object collection
	 */
	public List<Comment> findAllByContentId(Long id, FPage paging, String customerCode);
	
}
