package com.uxdpen.cms.business.services.sys;

import com.uxdpen.cms.business.entities.sys.SystemSeting;

/**
 * <p>
 * 自定义设置服务接口
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface SystemSetingService {
	
	/**
	 * Update custom data
	 * 更新自定义数据
	 * 
	 * @param sysSetingVo
	 * 			SystemSeting
	 * @return
	 * 			Long
	 */
	public Long saveSystemSeting(SystemSeting sysSetingVo);

	/**
	 * 通过customer查询自定义列表
	 * 
	 * @param customerCode
	 * 			Customer code
	 * @return
	 * 			Object
	 */
	public SystemSeting querySysSetingByCustomerCode(String customerCode);

}
