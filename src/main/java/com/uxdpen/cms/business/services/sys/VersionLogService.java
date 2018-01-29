package com.uxdpen.cms.business.services.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.VersionLog;

/**
 * <p>操作首页版本日志的Service</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月7日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface VersionLogService {
	
	/**
	 * Add a version log
	 * 添加版本日志
	 * 
	 * @param log
	 *            VersionLog
	 * @return 
	 * 			Long
	 */
	public Long create(VersionLog log);
	
	
	/**
	 * Edit version log
	 * 编辑版本日志
	 * 
	 * @param log
	 *        VersionLog
	 */
	public void update(VersionLog log);
	
	/**
	 * Delete version log
	 * 删除版本日志
	 * 
	 * @param log
	 *        VersionLog
	 */
	public void delete(VersionLog log);
	
	/**
	 * Delete version log
	 * 删除版本日志
	 * 
	 * @param id
	 *            VersionLog id
	 */
	public void delete(Long id);
	
	/**
	 * Delete version log
	 * 删除版本日志
	 * 
	 * @param ids
	 *            VersionLog id set
	 */
	public void deleteByIds(String ids);
	
	/**
	 * Get the corresponding log object based on ID
     * 根据id获取对应的日志对象
     * 
     * @param logId
     *        VersionLog id
     * @return
     *        Object
     */
    public VersionLog getById(Long logId);
	
	/**
	 * Query version log
	 * 查询版本日志
	 * 
	 * @param log
	 *        VersionLog
	 * @param paging
	 *        Pagination
	 * @param session
	 *        System base info
	 * @return
	 * 			Object collecton
	 */
	public List<VersionLog> findAll(VersionLog log, FPage paging, FSysInfo session);

}
