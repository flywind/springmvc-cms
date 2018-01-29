package com.uxdpen.cms.business.dao.sys;

import java.util.List;

import com.uxdpen.cms.business.dao.base.FBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.entities.base.FSysInfo;
import com.uxdpen.cms.business.entities.sys.FriendLink;

/**
 * <p>Friend link Dao</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface FriendLinkDao extends FBaseDao<FriendLink>{

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
