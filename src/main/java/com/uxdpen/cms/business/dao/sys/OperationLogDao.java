package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.OperationLog;

/**
 * <p>Operation log Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月29日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface OperationLogDao extends FBaseDao<OperationLog> {
	
	/**
	 * Get the operation log list. Support advanced search and paging query
	 * 获取操作日志列表。支持高级搜索和分页查询
	 * 
	 * @param session
	 *        	System base info
	 * @param log
	 *        	Log
	 * @param paging
	 *        	Pagination
	 * @return
	 *        	Object collection
	 */
	public List<OperationLog> findAll(FSysInfo session, OperationLog log, FPage paging);

}
