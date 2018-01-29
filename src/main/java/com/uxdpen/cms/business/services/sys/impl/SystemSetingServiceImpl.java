package com.uxdpen.cms.business.services.sys.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.dao.sys.SystemSetingDao;
import com.uxdpen.cms.business.entities.sys.SystemSeting;
import com.uxdpen.cms.business.services.sys.SystemSetingService;


/**
 * <p>自定义设置处理服务类</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Service
public class SystemSetingServiceImpl implements SystemSetingService{

	@Autowired
	private SystemSetingDao  systemSetingDao;
	
	@Override
	public Long saveSystemSeting(SystemSeting sysSetingVo) 
	{	
	    systemSetingDao.saveOrUpdate(sysSetingVo);
	    return sysSetingVo.getId();
	}
	@Override
	public SystemSeting querySysSetingByCustomerCode(String customerCode) {
	
		return systemSetingDao.querySysSetingByCustomerCode(customerCode);
	}

}
