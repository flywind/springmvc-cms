package com.uxdpen.cms.business.common.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.uxdpen.cms.business.common.constants.FBaseConstants;
import com.uxdpen.cms.business.common.constants.FLogConstants;


/**
 * <p>
 * Log annotation class
 * 记录日志的注解类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FLog {
	
	/**
	 * Log information key, for the realization of internationalization
	 * 
	 * @return
	 *        Log information key
	 */
	String infokey() default FBaseConstants.EMPTY_STRING;
	
	/**
	 * Operation type (1 add, 2 delete, 3 modify, 4 query, 5 login, 6 cancellation), the default for the query
	 * 
	 * @return
	 *        Operation type
	 */
	int optype() default FLogConstants.SELECT;

	/**
	 * Log (1 system log, 2 operation log), the default is the operation log
	 * 
	 * @return
	 *        Log type
	 */
	int logtype() default FLogConstants.OPERATION_LOG;
	
}
