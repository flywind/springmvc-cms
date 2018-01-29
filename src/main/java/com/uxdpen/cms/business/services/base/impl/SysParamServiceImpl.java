package com.uxdpen.cms.business.services.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.dao.base.SysParamDao;
import com.uxdpen.cms.business.entities.base.SysParam;
import com.uxdpen.cms.business.services.base.SysParamService;

@Service
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	private  SysParamDao sysParamDao;
	
	@Override
	public  List<SysParam> getAllParamByBusinessType(int businessType)
	{	
		List<SysParam>   sysParams = sysParamDao.getAllParamByBusinessType(businessType);
		
		return sysParams;
	}

}
