package com.uxdpen.cms.business.services.sys;

import java.util.List;
import java.util.Map;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.OperationLog;
import com.uxdpen.cms.business.entities.sys.SysLog;


/**
 * <p>操作与系统日志的Service</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface LogService {
	
	/**
	 * Get the operation log list. Support advanced search and paging query
	 * 获取操作日志列表。支持高级搜索和分页查询
	 * 
	 * @param log
	 *        OperationLog
	 * @param paging
	 *        Pagination
	 * @param session
	 *        System base info
	 * @return
	 *        Object collection
	 */
	public List<OperationLog> findOperationLog(OperationLog log, FPage paging, FSysInfo session);
	
	/**
	 * Create operation log
	 * 创建操作日志
	 * 
	 * @param log
	 *        OperationLog
	 */
	public void createOperationLog(OperationLog log);
	
	/**
	 * Delete operation log
	 * 删除操作日志
	 * 
	 * @param params
	 * 		     Query parameters
	 */
	public void deteleOperationLog(Map<String, Object> params);
	
	/**
	 * Get system log list. Support advanced search and paging query
	 * 获取系统日志列表。支持高级搜索和分页查询
	 * 
	 * @param log
	 *        SysLog
	 * @param paging
	 *        Pagination
	 * @param session
	 *        System base info
	 * @return
	 *        Object collection
	 */
	public List<SysLog> findSysLog(SysLog log, FPage paging, FSysInfo session);
	
	/**
	 * Create system log
	 * 创建系统日志
	 * 
	 * @param log
	 *        SysLog
	 */
	public void createSysLog(SysLog log);
	
	/**
	 * Delete system log
	 * 删除系统日志
	 * 
	 * @param params
	 * 		     Query parameters
	 */
	public void deteleSysLog(Map<String, Object> params);
	
}
