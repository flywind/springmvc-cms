package com.uxdpen.cms.business.dao.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.cms.Work;


/**
 * <p>Work Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface WorkDao extends FBaseDao<Work>{

	public List<Work> findAll(Work work, FPage paging, FSysInfo session);
	
	public List<Work> findAll(Work work, FPage paging, String customerCode);
	
	public List<Work> getListForLoop(Work work, FPage page, String customerCode);
	
	public List<Work> getListForHot(Work work, FPage page, String customerCode);
	
	public List<Work> getAllList(Work example, FPage page, String customerCode);

}
