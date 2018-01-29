package com.uxdpen.cms.business.services.cms;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.cms.Technology;

/**
 * <p>Technology Service</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface TechnologyService {
	
	/**
	 * Add technical articles 
	 * 添加技术文章
	 * 
	 * @param o
	 * 			Technology
	 * @return
	 * 			Long
	 */
	public Long create(Technology o);
	
	/**
	 * Modify technical articles
	 * 修改技术文章
	 * 
	 * @param o
	 * 			Technology
	 */
	public void update(Technology o);
	
	/**
	 * Delete technical articles
	 * 删除技术文章
	 * 
	 * @param o
	 * 			Technology
	 */
	public void delete(Technology o);
	
	/**
	 * Delete articles according to ID
	 * 根据ID删除文章
	 * 
	 * @param id
	 * 			Technology id
	 * @return
	 * 			boolean
	 */
	public boolean deleteById(Long id);
	
	/**
	 * Delete technical articles with IDS
	 * 跟IDS删除技术文章
	 * 
	 * @param ids
	 * 			Technology id set
	 */
	public void deleteByIds(String ids);
	
	/**
	 * According to ID query technology articles
	 * 根据ID查询技术文章
	 * 
	 * @param id
	 * 			Technology id
	 * @return
	 * 			Objec
	 */
	public Technology getById(Long id);

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
	 *  @param lanage
	 * 			Lanage
	 * @return
	 * 			Object collection
	 */
	public List<Technology> findAll(Technology technology, FPage paging, FSysInfo session, String lanage);
	
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
	public List<Technology> getListForLoop(Technology technology, FPage paging, String customerCode);
	
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
	public List<Technology> getListForHot(Technology technology, FPage paging, String customerCode);
}
