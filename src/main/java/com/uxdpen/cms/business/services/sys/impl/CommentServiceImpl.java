package com.uxdpen.cms.business.services.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.CommentDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Comment;
import com.uxdpen.cms.business.services.sys.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public Long create(Comment c) {
		return commentDao.save(c);
	}

	@Override
	public void update(Comment c) {
		commentDao.update(c);
	}

	@Override
	public void delete(Long id) {
		commentDao.deleteById(Comment.class, id);
	}
	
	@Override
	public void deleteByIds(String ids) {
		List<Long> list = FBaseUtil.idsToList(ids);
		for(Long id : list){
			commentDao.deleteById(Comment.class, id);
		}
	}

	@Override
	public List<Comment> findAll(Comment c, FPage paging, FSysInfo session) {
		return commentDao.findAll(c, paging, session);
	}
	
	public List<Comment> findAllByContentId(Long id, FPage paging, String customerCode){
		return commentDao.findAllByContentId(id, paging, customerCode);
	}

	@Override
	public Comment getById(Long id) {
		return commentDao.getById(Comment.class, id);
	}

}
