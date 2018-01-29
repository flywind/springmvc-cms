package com.uxdpen.cms.business.dao.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.cms.Technology;

/**
 * <p>Technology Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface TechnologyDao extends FBaseDao<Technology>{

	/**
	 * Get all the technical articles
	 * 获得所有技术文章
	 * 
	 * @param technology
	 * 			technical articles
	 * @param paging
	 * 			Pagination
	 * @param session
	 * 			System base info
	 * @return
	 * 			Object collection
	 */
	public List<Technology> findAll(Technology technology, FPage paging, FSysInfo session);
	
	/**
	 * Get all the technical articles
	 * 获得所有技术文章
	 * 
	 * @param technology
	 * 			technical articles
	 * @param paging
	 * 			Pagination
	 * @param customerCode
	 * 			Customer code
	 * @return
	 * 			Object collection
	 */
	public List<Technology> findAll(Technology technology, FPage paging, String customerCode);
	
	/**
	 * Obtain a collection of technical articles according to the query condition
	 * 根据查询条件获得技术文章集合
	 * 
	 * @param example
	 * 			technical articles
	 * @param page
	 * 			Pagination
	 * @param customerCode
	 * 			Customer code
	 * @return
	 * 			Object collection
	 */
	public List<Technology> getListForLoop(Technology example, FPage page, String customerCode);
	
	/**
	 * Obtain a collection of technical articles according to the query condition
	 * 根据查询条件获得技术文章集合
	 * 
	 * @param example
	 * 			technical articles
	 * @param page
	 * 			Pagination
	 * @param customerCode
	 * 			Customer code
	 * @return
	 * 			Object collection
	 */
	public List<Technology> getListForHot(Technology example, FPage page, String customerCode);
}
