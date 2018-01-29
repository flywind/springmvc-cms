package com.uxdpen.cms.business.dao.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.entities.cms.Tag;

public interface TagDao extends FBaseDao<Tag> {

	public List<Tag> getAllTags();
}
