package com.uxdpen.cms.business.services.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.cms.Work;

/**
 * <p>Work Service</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface WorkService {
	
	public Long create(Work o);
	
	public void update(Work o);
	
	public void delete(Work o);
	
	public boolean deleteById(Long id);
	
	public void deleteByIds(String ids);
	
	public Work getById(Long id);

	public List<Work> findAll(Work example, FPage paging, FSysInfo session, String lanage);
	
	public List<Work> findAll(Work example, FPage paging, String customerCode);
	
	public List<Work> getListForLoop(Work example, FPage paging, String customerCode);
	
	public List<Work> getListForHot(Work example, FPage paging, String customerCode);
	
	public List<Work> getAllList(Work example, FPage page, String customerCode);
}
