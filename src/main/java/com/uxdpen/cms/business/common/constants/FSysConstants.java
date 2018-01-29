package com.uxdpen.cms.business.common.constants;

/**
 * <p>
 * System contants
 * 系统管理的常量
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月29日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface FSysConstants {
	
	/**
	 * Session stored in key
	 */
	public static final String SESSION = "session";
	
	/**
	 * Resource type - menu
	 */
	public static final String RESOURCE_TYPE_MENU = "menu";
	
	/**
	 * Resource type - button
	 */
	public static final String RESOURCE_TYPE_BUTTON = "button";
	
	/**
	 * Administrator role name
	 */
	public static final String ROLE_SYS = "role_sys";
	
	/**
	 * User ID
	 */
	public static final String USER_ID="userId";
	
	/**
	 * Role ID
	 */
	public static final String ROLE_ID="roleId";
	
	/**
	 * User login name
	 */
	public static final String USER_NAME="username";
	/**
	 * Creator
	 */
	public static final String CREATER="creater";
	
	/**
	 * User real name
	 */
	public static final String NAME="name";
	
	/**
	 * Phone number
	 */
	public static final String MOBILE="mobile";
	
	/**
	 * Email
	 */
	public static final String EMAIL = "email";
	
	/**
	 * Unit Id
	 */
	public static final String COMPANY_ID="companyId";
	
	/**
	 * Organizational institution ids
	 */
	public static final String COMPANY_IDS="companyIds";
	/**
	 * src
	 */
	public static final String SRC="src";
	/**
	 * main
	 */
	public static final String MAIN="main";
	/**
	 * webapp
	 */
	public static final String WEBAPP="webapp";
	/**
	 * assets
	 */
	public static final String ASSETS="assets";
	/**
	 * images
	 */
	public static final String IMAGES="images";
	
	/**
	 * Message header when sending a message key
	 */
	public static final String SEND_EMAIL_TITLE = "send-email-title";
	
	/**
	 * Mail content when sending a message key
	 */
	public static final String SEND_EMAIL_CONTENT = "send-email-content";
	
	/**
	 * User name and email or phone number does not match
	 */
	public static final String USERNAME_WITH_EMAIL_OR_PASSWORD_MATCH = "username-with-email-or-password-match";
	
	/**
	 * Cell phone number resources key
	 */
	public static final String PHONE_NUMBER = "phone-number";
	
	/**
	 * Mailbox resource key
	 */
	public static final String EMAIL_ADDRESS = "email-address";
	
	/**
	 * Send verification code exception
	 */
	public static final String SEND_INDENTIFYING_CODE_ERROR = "send-indentifying-code-error";
	
	/**
	 * Verification code
	 */
	public static final String IDENTIFYING_CODE = "identifyingCode-label";
	
	/**
	 * Verification code information (including valid time)
	 */
	public static final String IDENTIFYING_CODE_INFO = "identifying-code-info";
	
	/**
	 * Invalid authentication code
	 */
	public static final String INDENTIFYING_CODE_INVALID = "indentifying-code-invalid";
	
	/**
	 * The user does not exist, please confirm whether the user is deleted
	 */
	public static final String FIND_BACK_PASSWORD_FAIL_USER_NOT_EXIST = "find-back-password-fail-user-not-exist";
	
}
