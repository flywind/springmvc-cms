package com.uxdpen.cms.business.common.constants;

/**
 * <p>
 * Log constants
 * 系统日志与操作日志的常量类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月29日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface FLogConstants {
	
	/**
	 * Operating result (success)
	 */
	int SUCCESS_LOG = 1;
	
	/**
	 * Operation result internationalization key (success)
	 */
	String OPERATION_RESULT_SUCCESS_LOG = "operation-result-success-log";
	
	/**
	 * Operation result (failure)
	 */
	int FAIL_LOG = 2;
	
	/**
	 * Operation result internationalization key (failure)
	 */
	String OPERATION_RESULT_FAIL_LOG = "operation-result-fail-log";
	
	/**
	 * System log
	 */
	int SYS_LOG = 1;
	
	/**
	 * Operation log
	 */
	int OPERATION_LOG = 2;
	
	/**
	 * Log type (add)
	 */
	int CREATE = 1;
	
	/**
	 * Log type "create" international key
	 */
	String OPERATION_TYPE_CREATE_LOG = "operation-type-create-log";
	
	/**
	 * Log type (delete)
	 */
	int DELETE = 2;
	
	/**
	 * The internationalization of the log type "deletion" key
	 */
	String OPERATION_TYPE_DELETE_LOG = "operation-type-delete-log";
	
	/**
	 * Log type (modified)
	 */
	int UPDATE = 3;
	
	/**
	 * The internationalization of the "Edit" of the log type key
	 */
	String OPERATION_TYPE_UPDATE_LOG = "operation-type-update-log";
	
	/**
	 * Log type (query)
	 */
	int SELECT = 4;
	
	/**
	 * Internationalization of the log type "query" key
	 */
	String OPERATION_TYPE_SELECT_LOG = "operation-type-select-log";
	
	/**
	 * Log type (login)
	 */
	int LOGIN = 5;
	
	/**
	 * Log type "login" of the internationalization of key
	 */
	String OPERATION_TYPE_LOGIN_LOG = "operation-type-login-log";
	
	/**
	 * Log type (log off or exit)
	 */
	int LOGOFF = 6;
	
	/**
	 * Log type "write off" internationalization key
	 */
	String OPERATION_TYPE_LOGOFF_LOG = "operation-type-logoff-log";
	
	/**
	 * Log type (modify password)
	 */
	int PASSWORD_UPDATE = 7;
	
	/**
	 * Log type "modify the password" of the internationalization of key
	 */
	String OPERATION_TYPE_PASSWORD_UPDATE_LOG = "operation-type-password-update-log";
	
	/**
	 * Log type (search)
	 */
	int SEARCH = 8;
	
	/**
	 * Log type "search" the internationalization of key
	 */
	String OPERATION_TYPE_SEARCH_LOG = "operation-type-search-log";
	
	/**
	 * Retrieve password
	 */
	int FIND_PASSWORD = 9;
	
	/**
	 * Internationalization of log type "retrieve password" key
	 */
	String OPERATION_TYPE_FIND_PASSWORD_LOG = "operation-type-find-password-log";
	
	/**
	 * Reset password
	 */
	int RESET_PASSWORD = 10;
	
	/**
	 * The internationalization of the log type "reset password" key
	 */
	String OPERATION_TYPE_RESET_PASSWORD_LOG = "operation-type-reset-password-log";
	
	/**
	 * Operation time
	 */
	String OPERATION_TIME_SORT_NAME = "opTime";
	
	/**
	 * Log in system log key
	 */
	String LOGIN_SYS_LOG = "login-sys-log";
	
	/**
	 * System exit log key
	 */
	String LOGOFF_SYS_LOG = "logoff-sys-log";
	
	/**
	 * Login user name does not exist
	 */
	String LOGIN_SYS_LOG_USERNAME_NOTEXIST = "login-sys-log-username-notexist-exception";
	
	/**
	 * Login account is locked
	 */
	String LOGIN_SYS_LOG_USERNAME_LOCK = "login-sys-log-username-lock-exception";
	
	/**
	 * Login password error
	 */
	String LOGIN_SYS_LOG_PASSWORD_ERROR = "login-sys-log-password-error-exception";
	
	/**
	 * Authentication failure
	 */
	String LOGIN_SYS_LOG_AUTHENTICATION_FAIL = "login-sys-log-authentication-fail-exception";
	
	
	/**
	 * Creating the role of the log key
	 */
	String CREATE_ROLE = "create-role-sys-log";
	
	/**
	 * Check the role of the log key
	 */
	String QUERY_ROLE = "query-role-sys-log";
	
	/**
	 * Delete role log key
	 */
	String DELETE_ROLE = "delete-role-sys-log";
	
	/**
	 * Modify the role of the log key
	 */
	String UPDATE_ROLE = "update-role-sys-log";
	
	/**
	 * Create a log of resources key
	 */
	String CREATE_RESOURCE = "create-resource-sys-log";
	
	/**
	 * Modify resource log key
	 */
	String UPDATE_RESOURCE = "update-resource-sys-log";
	
	/**
	 * Delete the log key
	 */
	String DELETE_RESOURCE = "delete-resource-sys-log";
	
	/**
	 * Query resource list log key
	 */
	String QUERY_RESOURCE = "query-resource-sys-log";
	
	/**
	 * Create user log key
	 */
	String CREATE_USER = "create-user-sys-log";
	
	/**
	 * Modify user log key
	 */
	String UPDATE_USER = "update-user-sys-log";
	
	/**
	 * Modify password log key
	 */
	String UPDATE_PASSWORD = "update-password-sys-log";
	
	/**
	 * Delete user log key
	 */
	String DELETE_USER = "delete-user-sys-log";
	
	/**
	 * Query user list log key
	 */
	String SELECT_USER = "select-user-sys-log";
	
	/**
	 * Create an organizational log key
	 */
	String CREATE_ORGANIZATION = "create-organization-sys-log";
	
	/**
	 * Modify organization log key
	 */
	String UPDATE_ORGANIZATION = "update-organization-sys-log";
	
	/**
	 * Delete organization log key
	 */
	String DELETE_ORGANIZATION = "delete-organization-sys-log";
	
	/**
	 * Query organization list key
	 */
	String SELECT_ORGANIZATION = "select-organization-sys-log";
	
	/**
	 * Query articles
	 */
	String QUERY_TECHNOLOGY = "query-technology-op-log";
	
	/**
	 * Add article
	 */
	String CREATE_TECHNOLOGY = "create-technology-op-log";
	
	/**
	 * Modify the article
	 */
	String UPDATE_TECHNOLOGY = "update-technology-op-log";
	
	/**
	 * Delete articles
	 */
	String DELETE_TECHNOLOGY = "delete-technology-op-log";
	
	/**
	 * Query works
	 */
	String QUERY_WORK = "query-work-op-log";
	
	/**
	 * Add work
	 */
	String CREATE_WORK = "create-work-op-log";
	
	/**
	 * Modify the work
	 */
	String UPDATE_WORK = "update-work-op-log";
	
	/**
	 * Delete work
	 */
	String DELETE_WORK = "delete-work-op-log";

}
