package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.sys.SystemSetingDao;
import com.uxdpen.cms.business.entities.sys.SystemSeting;


/**
 * <p>System Seting Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class SystemSetingDaoImpl extends AbstractFBaseDao<SystemSeting>  implements  SystemSetingDao
{

	@Override
	public SystemSeting querySysSetingByCustomerCode(String customerCode) {
		String hql ="from SystemSeting where customerCode=:customerCode";
		Map<String,Object>  params = new HashMap<String,Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		List<SystemSeting> systemSetings = super.query(hql, params);
		SystemSeting systemSeting = new SystemSeting();
		if(null!=systemSetings&&systemSetings.size()>0){
			systemSeting=systemSetings.get(0);
		}
		return systemSeting;
	}
   
}
