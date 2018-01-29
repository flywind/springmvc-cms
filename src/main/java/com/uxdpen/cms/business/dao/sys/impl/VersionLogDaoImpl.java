package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.VersionLogDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.VersionLog;

/**
 * <p>Version Log Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月7日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class VersionLogDaoImpl extends AbstractFBaseDao<VersionLog>implements VersionLogDao {
	
	@Override
	public List<VersionLog> findAll(VersionLog log, FPage paging, FSysInfo session) {
		StringBuilder hql = new StringBuilder("FROM VersionLog where customerCode=:customerCode");
		StringBuilder countHql = new StringBuilder("select count(t.id) from VersionLog t where t.customerCode=:customerCode");
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String,Object> paramsc = new HashMap<String, Object>();
		
		if(null != log){
			if (log.getHide() == 0) {
				hql.append(" and hide = :hide");
				params.put("hide", 0);
				
				countHql.append(" and hide = :hide");
				paramsc.put("hide", 0);
			}
			
			if (log.getHide() == 1) {
				hql.append(" and hide = :hide");
				params.put("hide", 1);
				
				countHql.append(" and hide = :hide");
				paramsc.put("hide", 1);
			}
			
			if (StringUtils.isNoneBlank(log.getTitle())) {
				hql.append(" and title LIKE :title");
				params.put("title", "%" + log.getTitle().trim() + "%");
				
				countHql.append(" and title LIKE :title");
				paramsc.put("title", "%" + log.getTitle().trim() + "%");
			}
			
			if (StringUtils.isNoneBlank(log.getTitleEn())) {
				hql.append(" and titleEn LIKE :titleEn");
				params.put("titleEn", "%" + log.getTitleEn().trim() + "%");
				
				countHql.append(" and titleEn LIKE :titleEn");
				paramsc.put("titleEn", "%" + log.getTitleEn().trim() + "%");
			}
		}
		
		paramsc.put("customerCode", session.getCustomerCode());
		Long total = this.count(countHql.toString(), paramsc);
		paging.setRowCount(total.intValue()); 
		
		params.put("customerCode", session.getCustomerCode());
		
		hql.append(" ORDER BY time DESC");
		
		return this.query(hql.toString(), params, paging.getPageNumber(), paging.getPageSize());
	}

}
