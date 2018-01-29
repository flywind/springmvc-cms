package com.uxdpen.cms.business.services.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.FriendLink;

/**
 * <p>Friend Link Service</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface FriendLinkService {
	
	/**
	 * Create friend link
	 * 添加友情链接
	 * 
	 * @param link
	 * 			FriendLink
	 * @return
	 * 			Long
	 */
	public Long create(FriendLink link);
	
	/**
	 * Update friend link
	 * 修改友情链接
	 * 
	 * @param link
	 * 			FriendLink
	 */
	public void update(FriendLink link);
	
	/**
	 * Delete links based on ID
	 * 根据ID删除友情链接
	 * 
	 * @param linkId
	 * 			FriendLink id
	 */
	public void delete(Long linkId);
	
	/**
	 * Delete links based on IDS
	 * 根据ID删除友情链接
	 * 
	 * @param ids
	 * 			FriendLink id set
	 */
	public void deleteByIds(String ids);

	/**
	 * Obtain friendship links according to conditions
	 * 根据条件获取友情链接
	 * 
	 * @param link
	 * 			friendship link
	 * @param paging
	 * 			Pagination
	 * @param session
	 * 			System base info
	 * @return
	 * 			Object collection
	 */
	public List<FriendLink> findAll(FriendLink link, FPage paging, FSysInfo session);
	
	/**
	 * According to ID query link
	 * 根据ID查询友情链接
	 * 
	 * @param id
	 * 			FriendLink id
	 * @return
	 * 			Object
	 */
	public FriendLink getById(Long id);
	
	/**
	 * Obtain friendship links according to conditions
	 * 根据条件获取友情链接
	 * 
	 * @param customerCode
	 * 			Customer code
	 * @return
	 * 			Object collection
	 */
	public List<FriendLink> findAll(String customerCode);
	
}
