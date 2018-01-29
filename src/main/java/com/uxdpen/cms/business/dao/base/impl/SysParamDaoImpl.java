package com.uxdpen.cms.business.dao.base.impl;



import java.util.HashMap;import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.SysParamDao;
import com.uxdpen.cms.business.entities.base.SysParam;

@Repository
public class SysParamDaoImpl extends  AbstractFBaseDao<SysParam> implements SysParamDao {

	/**
	 * Query parameter array by service type
	 * 通过业务类型 查询参数数组
	 * 
	 * @param businessType
	 * 			Business Type
	 * @return
	 * 			Object collection
	 */
	@Override
	public List<SysParam> getAllParamByBusinessType(int businessType) {
	  String hql ="from  SysParam where businessType=:businessType order by sort asc";
	  Map<String,Object>  param = new HashMap<String,Object>();
	  param.put("businessType", businessType);
	  List<SysParam>  sysParms=  super.query(hql, param);
	  return sysParms;
	}	

}
