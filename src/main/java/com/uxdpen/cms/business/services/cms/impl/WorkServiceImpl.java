package com.uxdpen.cms.business.services.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxdpen.cms.business.common.utils.FBaseUtil;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.base.SysParamDao;
import com.uxdpen.cms.business.dao.cms.WorkDao;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.base.SysParam;
import com.uxdpen.cms.business.entities.cms.Work;
import com.uxdpen.cms.business.services.cms.WorkService;

@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	private WorkDao exampleDao;
	
	@Autowired
	private SysParamDao sysParamDao;
	
	//@FLog(infokey=FLogConstants.CREATE_WORK, optype = FLogConstants.CREATE)
	public Long create(Work o){
		return exampleDao.save(o);
	}
	
	//@FLog(infokey=FLogConstants.UPDATE_WORK, optype = FLogConstants.UPDATE)
	public void update(Work o){
		exampleDao.update(o);
	}
	
	//@FLog(infokey=FLogConstants.DELETE_WORK, optype = FLogConstants.DELETE)
	public void delete(Work o){
		exampleDao.delete(o);
	}
	
	//@FLog(infokey=FLogConstants.DELETE_WORK, optype = FLogConstants.DELETE)
	public boolean deleteById(Long id){
		return exampleDao.deleteById(Work.class, id);
	}
	
	//@FLog(infokey=FLogConstants.DELETE_WORK, optype = FLogConstants.DELETE)
	public void deleteByIds(String ids){
		List<Long> list = FBaseUtil.idsToList(ids);
		for(Long id : list){
			exampleDao.deleteById(Work.class, id);
		}
	}
	
	//@FLog(infokey=FLogConstants.QUERY_WORK)
	public Work getById(Long id){
		return exampleDao.getById(Work.class, id);
	}
	
	//@FLog(infokey=FLogConstants.QUERY_WORK)
	public List<Work> findAll(Work example, FPage paging, FSysInfo session, String lanage){
		List<Work> list = exampleDao.findAll(example, paging, session);
		
		for(Work t : list){
			List<SysParam> sysParams = sysParamDao.getAllParamByBusinessType(3);
			for(SysParam s : sysParams){
				if(s.getParamKey() == t.getType()){
					if("zh-cn".equalsIgnoreCase(lanage)){
						t.setTypeName(s.getParamValue());
					}else{
						t.setTypeName(s.getParamValueEn());
					}
					
				}
			}
		}
		
		return list;
	}
	
	//@FLog(infokey=FLogConstants.QUERY_WORK)
	public List<Work> findAll(Work example, FPage paging, String customerCode){
		return exampleDao.findAll(example, paging, customerCode);
	}
	
	//@FLog(infokey=FLogConstants.QUERY_WORK)
	public List<Work> getListForLoop(Work example, FPage page, String customerCode){
		return exampleDao.getListForLoop(example, page, customerCode);
	}
	
	//@FLog(infokey=FLogConstants.QUERY_WORK)
	public List<Work> getListForHot(Work example, FPage page, String customerCode){
		return exampleDao.getListForHot(example, page, customerCode);
	}
	
	public List<Work> getAllList(Work example, FPage page, String customerCode){
		return exampleDao.getAllList(example, page, customerCode);
	}
}
