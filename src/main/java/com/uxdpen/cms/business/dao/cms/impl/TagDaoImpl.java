package com.uxdpen.cms.business.dao.cms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.cms.TagDao;
import com.uxdpen.cms.business.entities.cms.Tag;

@Repository
public class TagDaoImpl extends AbstractFBaseDao<Tag> implements TagDao {

	public List<Tag> getAllTags(){
		StringBuilder hql = new StringBuilder("FROM Tag WHERE isShow = 1 ORDER BY seqNum ASC");
		return super.query(hql.toString());
	}
}
