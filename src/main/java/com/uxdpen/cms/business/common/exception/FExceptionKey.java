package com.uxdpen.cms.business.common.exception;

/**
 * <p>
 * Custom exception of the key
 * 自定义异常类的key
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface FExceptionKey {
	
	/**
	 * Unknown anomaly
	 */
	public final static String UNKNOWN_EXCEPTION = "unknown-exception";
	
	/**
	 * Role name already exists
	 */
	public final static String ROLE_NAME_EXIST = "role-name-exist";
	
	/**
	 * Role is being used by other resources
	 */
	public final static String ROLE_IS_USING = "role-is-using";
	
	/**
	 * No information was modified at the time of the editor
	 */
	public final String NOT_UPDATE_ANY_INFO = "not-update-any-info";
	
	/**
	 * Resources are not allowed to modify the type of resources when resources have sub resources.
	 */
	public final String RESOURCE_TYPE_NOT_ALLOW_UPDATE = "resource-type-not-allow-update";
	
	/**
	 * Exist child resources, delete resource failure
	 */
	public final String RESOURCE_DELETE_FAIL_HAS_CHILD = "resource-delete-fail-has-child";
	
	/**
	 * Resources are being used by role
	 */
	public final static String RESOURCE_IS_USING = "resource_is_using";
	
	/**
	 * Password and confirm password is not the same
	 */
	public final static String TWO_PASSWORDS_NOT_SAME = "two-passwords-not-same";
	
	/**
	 * The new password is the same as the old one
	 */
	public final static String NEW_PASSWORD_WITH_OLD_PASSWORD_THE_SAME = "new-password-with-old-password-the-same";
	
	public final static String DELETE_ERROR = "delete-error";
	
}
