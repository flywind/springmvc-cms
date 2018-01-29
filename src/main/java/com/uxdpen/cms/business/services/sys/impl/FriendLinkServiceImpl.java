package com.uxdpen.cms.business.services.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.FriendLinkDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.FriendLink;
import com.uxdpen.cms.business.services.sys.FriendLinkService;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {
	
	@Autowired
	private FriendLinkDao friendLinkDao;
	
	@Override
	public Long create(FriendLink link){
		return friendLinkDao.save(link);
	}
	
	@Override
	public void update(FriendLink link){
		friendLinkDao.update(link);
	}
	
	@Override
	public void delete(Long linkId){
		friendLinkDao.deleteById(FriendLink.class, linkId);
	}
	
	@Override
	public void deleteByIds(String ids){
		List<Long> list = FBaseUtil.idsToList(ids);
		for(Long id : list){
			friendLinkDao.deleteById(FriendLink.class, id);
		}
	}

	@Override
	public List<FriendLink> findAll(FriendLink link, FPage paging, FSysInfo session) {
		List<FriendLink> links = friendLinkDao.findAll(link, paging, session);
		return links;
	}
	

	public FriendLink getById(Long id){
		return friendLinkDao.getById(FriendLink.class, id);
	}
	
	public List<FriendLink> findAll(String customerCode){
		return friendLinkDao.findAll(customerCode);
	}
}
