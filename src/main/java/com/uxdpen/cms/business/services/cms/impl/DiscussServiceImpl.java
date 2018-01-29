package com.uxdpen.cms.business.services.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.exception.FException;
import com.uxdpen.cms.business.common.exception.FExceptionKey;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.cms.DiscussDao;
import com.uxdpen.cms.business.dao.sys.UserDao;
import com.uxdpen.cms.business.entities.cms.Discuss;
import com.uxdpen.cms.business.entities.sys.User;
import com.uxdpen.cms.business.services.cms.DiscussService;

@Service
public class DiscussServiceImpl implements DiscussService {
	
	@Autowired
	private DiscussDao discussDao;
	
	@Autowired
	private UserDao userDao;

	public Long createDiscuss(Discuss d){
		return discussDao.save(d);
	}
	
	public void updateDiscuss(Discuss d){
		discussDao.update(d);
	}
	
	public void deleteDiscuss(Discuss d){
		discussDao.delete(d);
	}
	
	public Boolean deleteDiscussById(Long id){
		return discussDao.deleteById(Discuss.class, id);
	}
	
	public void deleteDsicussByIds(List<String> ids) throws Exception{
		
		for(String id : ids){
			Boolean result = discussDao.deleteById(Discuss.class, Long.parseLong(id));
			if(!result){
				throw new FException(FExceptionKey.DELETE_ERROR);
			}
		}
		
	}
	
	public List<Discuss> getDiscussByPostsId(Long id){
		List<Discuss> discusses = discussDao.getDiscussByPostsId(id);
		if(null != discusses && discusses.size()>0){
			for(Discuss d : discusses){
				if(null != d.getAuthor()){
					User user = userDao.findByUsername(d.getAuthor());
					d.setDiscussUser(user);
				}
				
			}
		}
		
		return discusses;
	}
	
	public List<Discuss> getAllDiscuss(Discuss d, FPage page){
		return discussDao.getAllDiscuss(d, page);
	}
}
