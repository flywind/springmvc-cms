package com.uxdpen.cms.business.services.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Comment;

/**
 * <p>Comment Service</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface CommentService {

	/**
	 * Create comment
	 * 添加评论
	 * 
	 * @param c
	 * 			Comment
	 * @return
	 * 			Long
	 */
	public Long create(Comment c);
	
	/**
	 * Update comment
	 * 修改评论
	 * 
	 * @param c
	 * 			Comment
	 */
	public void update(Comment c);
	
	/**
	 * Delete comments based on comments ID
	 * 根据评论ID删除评论
	 * 
	 * @param id
	 * 			Comment id
	 */
	public void delete(Long id);
	
	/**
	 * Delete comments based on comments IDS
	 * 根据评论IDS删除评论
	 * 
	 * @param ids
	 * 			Comment id set
	 */
	public void deleteByIds(String ids);

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
	
	/**
	 * Get comments based on ID
	 * 根据ID获得评论
	 * 
	 * @param id
	 * 			Comment id
	 * @return
	 * 			Object
	 */
	public Comment getById(Long id);
}
