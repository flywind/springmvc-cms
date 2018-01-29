package com.uxdpen.cms.business.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.constants.FSysConstants;
import com.uxdpen.cms.business.dao.base.AbstractFBaseDao;
import com.uxdpen.cms.business.dao.base.FPage;
import com.uxdpen.cms.business.dao.sys.UserDao;
import com.uxdpen.cms.business.entities.sys.User;

/**
 * <p>User Dao Impl</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月22日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class UserDaoImpl extends AbstractFBaseDao<User> implements UserDao {
	
	@Override
	public List<User> findAll(String customerCode,List<Long> companyIds) {
		String hql = "from User where customerCode=:customerCode and companyId in(:companyIds) ";
		Query query=super.getSession().createQuery(hql); 
		query.setParameter(FBaseConstants.CUSTOMER_CODE, customerCode);
		query.setParameterList(FSysConstants.COMPANY_IDS, companyIds);
		@SuppressWarnings("unchecked")
		List<User> users = query.list();
		return users;
	}

	@Override
	public User findByUsername(String username ,String customerCode) {
		String hql = "from User where username=:username and customerCode=:customerCode";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FSysConstants.USER_NAME, username);
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		return super.getByHql(hql, params);
	}
	

	@Override
	public User findByUsername(String username) {
		String hql = "from User where username=:username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FSysConstants.USER_NAME, username);
		return super.getByHql(hql, params);
	}

	@Override
	public List<User> findByUserNameAndReadName(User loginUser,String userName, String readName,String companyId, String customerCode,FPage pageingEntity) {
	   
		StringBuffer  hqlSf = new StringBuffer("from User ");
		StringBuilder countHql = new StringBuilder("SELECT COUNT(id) FROM User ");
		StringBuilder condition = new StringBuilder(" WHERE customerCode = :customerCode AND creater = :creater");
		
	    Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		params.put(FBaseConstants.CREATER, loginUser.getUsername());
	    if(null!=userName&&!"".equals(userName)){
	    	condition=condition.append(" and  username like:username");
	    	params.put(FSysConstants.USER_NAME, "%"+userName+"%");
	    }
	    if(null!=readName&&!"".equals(readName)){
	    	condition=condition.append(" and name like:name");
	    	
	    	params.put(FSysConstants.NAME, "%"+readName+"%");
	    }
	    if(null!=companyId&&!"".equals(companyId)){
	    	condition=condition.append(" and companyId=:companyId");
	    	params.put(FSysConstants.COMPANY_ID, Long.parseLong(companyId));
	    }
	    condition=condition.append(" order by lastUpdateTime desc");
	    Long count = super.count(countHql.toString()+condition.toString(), params);
		pageingEntity.setRowCount(count.intValue());
		List<User> users=null;
	    users= super.query(hqlSf.toString()+condition.toString(), params,pageingEntity.getPageNumber(), pageingEntity.getPageSize());
		return users;
	}

	@Override
	public List<User> findAllByCreater(String createrName, String customerCode,FPage pageingEntity) {
		
		String hql = "from User where creater=:creater and customerCode=:customerCode  order by lastUpdateTime desc";
		String countHql = "SELECT COUNT(id) FROM User where creater=:creater and customerCode=:customerCode  order by lastUpdateTime desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FSysConstants.CREATER, createrName);
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		Long count = super.count(countHql.toString(), params);
		pageingEntity.setRowCount(count.intValue());
		List<User> users =super.query(hql, params, pageingEntity.getPageNumber(), pageingEntity.getPageSize());
		
		return users;
	}
	
	@Override
	public User findByUsername(String username, String emailOrPhone, String customerCode) {
		String hql = "FROM User WHERE customerCode=:customerCode AND username = :username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FBaseConstants.CUSTOMER_CODE, customerCode);
		params.put(FSysConstants.USER_NAME, username);
		if (emailOrPhone.contains("@")) {
			hql += " AND email = :email";
			params.put(FSysConstants.EMAIL, emailOrPhone);
		} else {
			hql += " AND mobile = :mobile";
			params.put(FSysConstants.MOBILE, emailOrPhone);
		}
		return this.getByHql(hql, params);
	}
	
}
