package com.uxdpen.cms.business.services.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.constants.FLogConstants;
import com.uxdpen.cms.business.common.constants.FSysConstants;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.base.MongoBaseDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.OperationLog;
import com.uxdpen.cms.business.entities.sys.SysLog;
import com.uxdpen.cms.business.services.sys.LogService;


/**
 * <p>操作与系统日志的Service实现</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Service
public class LogServiceImpl implements LogService {
	
	private static final Logger logger = Logger.getLogger(LogServiceImpl.class);
	
	private static final String OP_LOG_TABLE_NAME = "td_s_operation_log";
	
	private static final String SYS_LOG_TABLE_NAME = "td_s_sys_log";

	private static final String ANY_STRING = ".*";

	private static final String GREAT_THAN = "$gte";
	
	private static final String LESS_THAN = "$lte";
	
	@Autowired
	private MongoBaseDao<OperationLog> opLogDao;
	
	@Autowired
	private MongoBaseDao<SysLog> sysLogDao;
	
	@Override
	public List<OperationLog> findOperationLog(OperationLog log, FPage paging, FSysInfo session) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (null != log) {
			
			if (StringUtils.isNotEmpty(log.getUsername())) {
				
				Pattern pattern = Pattern.compile(ANY_STRING + log.getUsername().trim() + ANY_STRING);
				params.put(FSysConstants.USER_NAME, pattern);
			}
			
			if (StringUtils.isNotEmpty(log.getName())) {
				Pattern pattern = Pattern.compile(ANY_STRING + log.getName().trim() + ANY_STRING);
				params.put(FBaseConstants.NAME_STRING, pattern);
			}
			
			if (log.getType() != 0) {
				params.put(FBaseConstants.TYPE_STRING, log.getType());
			}
			
			if (log.getResult() != 0) {
				params.put("result", log.getResult());
			}
			
			BasicDBObject greatAndLessThan = new BasicDBObject();
			if (null != log.getStartTime()) {
				greatAndLessThan.append(GREAT_THAN, log.getStartTime());
				params.put(FLogConstants.OPERATION_TIME_SORT_NAME, greatAndLessThan);
			}
			if (null != log.getEndTime()) {
				greatAndLessThan.append(LESS_THAN, log.getEndTime());
				params.put(FLogConstants.OPERATION_TIME_SORT_NAME, greatAndLessThan);
			}
		}
		
		Long count = opLogDao.count(params, OP_LOG_TABLE_NAME);
		paging.setRowCount(count.intValue());
		
		return opLogDao.findAll(OperationLog.class, params, OP_LOG_TABLE_NAME, paging);
	}

	@Override
	public void createOperationLog(OperationLog log) {
		
		opLogDao.save(log, OP_LOG_TABLE_NAME);
	}
	
	@Override
	public void deteleOperationLog(Map<String, Object> params){
		sysLogDao.remove(params, OP_LOG_TABLE_NAME);
	}
	
	@Override
	public List<SysLog> findSysLog(SysLog log, FPage paging, FSysInfo session) {
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (null != log) {
			
			if (StringUtils.isNotEmpty(log.getUsername())) {
				
				Pattern pattern = Pattern.compile(ANY_STRING + log.getUsername().trim() + ANY_STRING);
				params.put(FSysConstants.USER_NAME, pattern);
			}
			
			if (StringUtils.isNotEmpty(log.getName())) {
				Pattern pattern = Pattern.compile(ANY_STRING + log.getName().trim() + ANY_STRING);
				params.put(FBaseConstants.NAME_STRING, pattern);
			}
			
			if (log.getType() != 0) {
				params.put(FBaseConstants.TYPE_STRING, log.getType());
			}
			
			if (log.getResult() != 0) {
				params.put("result", log.getResult());
			}
			
			BasicDBObject greatAndLessThan = new BasicDBObject();
			if (null != log.getStartTime()) {
				greatAndLessThan.append(GREAT_THAN, log.getStartTime());
				params.put(FLogConstants.OPERATION_TIME_SORT_NAME, greatAndLessThan);
			}
			if (null != log.getEndTime()) {
				greatAndLessThan.append(LESS_THAN, log.getEndTime());
				params.put(FLogConstants.OPERATION_TIME_SORT_NAME, greatAndLessThan);
			}
		}
		Long count = sysLogDao.count(params, SYS_LOG_TABLE_NAME);
		paging.setRowCount(count.intValue());
		
		return sysLogDao.findAll(SysLog.class, params, SYS_LOG_TABLE_NAME, paging);
	}
	
	@Override
	public void createSysLog(SysLog log) {
		
		sysLogDao.save(log, SYS_LOG_TABLE_NAME);
	}
	
	@Override
	public void deteleSysLog(Map<String, Object> params){
		sysLogDao.remove(params, SYS_LOG_TABLE_NAME);
	}

}
