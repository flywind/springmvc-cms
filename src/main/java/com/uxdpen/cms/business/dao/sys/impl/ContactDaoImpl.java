package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.ContactDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.Contact;

/**
 * <p>Contact Dao impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class ContactDaoImpl extends AbstractFBaseDao<Contact> implements ContactDao {

	@Override
	public List<Contact> findAll(Contact c, FPage paging, FSysInfo session) {
		StringBuilder hql = new StringBuilder("FROM Contact");
		StringBuilder countHql = new StringBuilder("SELECT COUNT(id) FROM Contact");
		StringBuilder condition = new StringBuilder(" WHERE customerCode = :customerCode");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, session.getCustomerCode());
		
		if (null != c.getName()) {
			condition.append(" AND name LIKE :name");
			params.put("name", "%" + c.getName().trim() + "%");
		}
		
		if (null != paging) { 
			condition.append(" order by " + paging.getSortName() + " " + paging.getSortOrder());
		}
		
		countHql.append(condition);
		Long count = super.count(countHql.toString(), params);
		paging.setRowCount(count.intValue());
		
		hql.append(condition);
		return super.query(hql.toString(), params, paging.getPageNumber(), paging.getPageSize());
	}

}
