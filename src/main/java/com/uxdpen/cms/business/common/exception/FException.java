package com.uxdpen.cms.business.common.exception;

/**
 * <p>
 * Custom exception
 * 自定义异常类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings("serial")
public class FException extends Exception {
	
	/**
	 * Exception type key
	 */
	private String typeKey;
	
	/**
	 * Create an exception
	 * 
	 * @param typeKey
	 *        Exception type key
	 */
	public FException(String typeKey) {
		super();
		this.typeKey = typeKey;
	}
	
	
	/**
	 * Create an exception and specify an exception specification
	 * 创建一个异常，并指定异常说明
	 * 
	 * @param typeKey
	 *        Exception type key
	 * @param message
	 *        Exception description information
	 */
	public FException(String typeKey, String message) {
		super(message);
		this.typeKey = typeKey;
	}
	
	/**
	 * Create an exception, and specify exception description information and exception objects
	 * 创建一个异常，并指定异常说明信息和异常对象
	 * 
	 * @param typeKey
	 *        Exception type key
	 * @param message
	 *        Exception description information
	 * @param cause
	 *        Specific abnormal objects
	 */
	public FException(String typeKey, String message, Exception cause) {
		super(message, cause);
		this.typeKey = typeKey;
	}

	/**
	 * Get exception type key, through this key can get the specific information from the resource file
	 * 获取异常类型key，通过此key可以从资源文件中获取具体的异常信息
	 * 
	 * @return String
	 *         Exception description information
	 */
	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}
	
}
