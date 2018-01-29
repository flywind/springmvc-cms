package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.constants.FSysConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.OperationLogDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.OperationLog;

/**
 * <p>操作日志的DAO实现</p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class OperationLogDaoImpl extends AbstractFBaseDao<OperationLog>implements OperationLogDao {

	@Override
	public List<OperationLog> findAll(FSysInfo session, OperationLog log, FPage paging) {
		String customerCode = session.getCustomerCode();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "FROM OperationLog log WHERE log.customerCode = :customerCode";
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		
		if (null != log) {

			if (null != log.getUsername()) {
				hql += " AND log.username LIKE :username";
				params.put(FSysConstants.USER_NAME, "%" + log.getUsername().trim() + "%");
			}
	
			if (null != log.getName()) {
				hql += " AND log.name LIKE :name";
				params.put(FBaseConstants.NAME_STRING, "%" + log.getName().trim() + "%");
			}
			//Operation type (1 add, 2 delete, 3 modify, 4 query)
			if (log.getType() != 0) {
				hql += " AND log.type = :type";
				params.put(FBaseConstants.TYPE_STRING, log.getType());
			}

			if (null != log.getStartTime()) {
				hql += " AND log.createTime >= :startTime";
				params.put(FBaseConstants.START_TIME, log.getStartTime());
			}
			if (null != log.getEndTime()) {
				hql += " AND log.createTime <= :endTime";
				params.put(FBaseConstants.END_TIME, log.getEndTime());
			}
		}
		
		List<OperationLog> opLogList = this.query(hql, params);
		return opLogList;
	}

}
