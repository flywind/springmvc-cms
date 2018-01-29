package com.uxdpen.cms.business.services.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.ContactDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Contact;
import com.uxdpen.cms.business.services.sys.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactDao contactDao;

	@Override
	public Long create(Contact c) {
		return contactDao.save(c);
	}

	@Override
	public void update(Contact c) {
		contactDao.update(c);
	}

	@Override
	public void delete(Long id) {
		contactDao.deleteById(Contact.class, id);
	}
	
	@Override
	public void deleteByIds(String ids){
		List<Long> list = FBaseUtil.idsToList(ids);
		for(Long id : list){
			contactDao.deleteById(Contact.class, id);
		}
	}

	@Override
	public List<Contact> findAll(Contact c, FPage paging, FSysInfo session) {
		return contactDao.findAll(c, paging, session);
	}

	@Override
	public Contact getById(Long id) {
		return contactDao.getById(Contact.class, id);
	}

}
