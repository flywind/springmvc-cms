package com.uxdpen.cms.business.services.base;

import java.util.List;

import com.uxdpen.cms.business.entities.base.SysParam;

/**
 * <p>系统参数查询接口</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface SysParamService
{
	/**
	 * Query parameter array by service type
	 * 通过业务类型查询参数数组
	 * 
	 * @param businessType
	 * 			Business type
	 * @return
	 * 			Object collection
	 */
   public  List<SysParam>   getAllParamByBusinessType(int businessType);
    
}
