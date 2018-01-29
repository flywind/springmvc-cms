package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.VersionLog;

/**
 * <p>Version Log Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月7日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface VersionLogDao extends FBaseDao<VersionLog> {
	
	/**
	 * Query version log
	 * 查询版本日志
	 * 
	 * @param log
	 *        	VersionLog
	 * @param paging
	 *        	Pagination
	 * @param session
	 *        	System base info
	 * @return
	 * 			Object collection
	 */
	public List<VersionLog> findAll(VersionLog log, FPage paging, FSysInfo session);
	

}
