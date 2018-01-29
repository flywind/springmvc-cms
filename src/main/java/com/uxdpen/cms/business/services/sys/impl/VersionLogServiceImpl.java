package com.uxdpen.cms.business.services.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.VersionLogDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.VersionLog;
import com.uxdpen.cms.business.services.sys.VersionLogService;

/**
 * <p>操作首页版本日志的Service实现</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月7日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Service
public class VersionLogServiceImpl implements VersionLogService {
	
	@Autowired
	private VersionLogDao logDao;

	@Override
	public Long create(VersionLog log) {
		return logDao.save(log);
	}
	
	@Override
	public void update(VersionLog log) {
		logDao.update(log);
	}

	@Override
	public void delete(VersionLog log) {
		logDao.delete(log);
	}
	
	@Override
	public void delete(Long id) {
		logDao.deleteById(VersionLog.class, id);
	}
	
	@Override
	public void deleteByIds(String ids){
		List<Long> list = FBaseUtil.idsToList(ids);
		for(Long id : list){
			logDao.deleteById(VersionLog.class, id);
		}
	}
	
	@Override
	public VersionLog getById(Long logId) {
		return logDao.getById(VersionLog.class, logId);
	}

	@Override
	public List<VersionLog> findAll(VersionLog log, FPage paging, FSysInfo session) {
		List<VersionLog> logs = logDao.findAll(log, paging, session);
		return logs;
	}

}
