package com.uxdpen.cms.business.entities.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uxdpen.cms.business.common.constants.FBaseConstants;

/**
 * <p>
 * International resource information model base class
 * 国际化资源信息模型基类
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月7日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public abstract class FProp {
	
	/**
	 * Gets the value of the table_name column in the international resource information
	 * 获取国际化资源信息中的table_name列的值
	 * 
	 * @return
	 *        table_name column value
	 */
	public abstract String getTableName();
	
	/**
	 * Get all the attributes of the resource information
	 * 获取所有资源信息的属性名
	 * 
	 * @return
	 *        Attribute name set
	 */
	public List<String> getFieldNames() {
		List<String> names = new ArrayList<String>();
		Class<?> c = this.getClass();
		Field[] f = c.getDeclaredFields();
		if (null != f) {
			for (int i = 0; i < f.length; i++) {
				names.add(f[i].getName());
			}
		}
		return names;
	}
	
	/**
	 * Gets the value of the property
	 * 获取属性的值
	 * 
	 * @param fieldName
	 *        Attribute name
	 * @return
	 *        Attribute value
	 */
	public String getFieldValue(String fieldName) {
		try {
			Class<?> c = this.getClass();
			String mName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Class<?>[] cs = null;
			Method m = c.getMethod(mName, cs);
			Object[] args = null;
			Object val = m.invoke(this, args);
			return (String) val;
		} catch (Exception e) {
			return FBaseConstants.EMPTY_STRING;
		}
	}
	
	/**
	 * Set attribute value
	 * 设置属性值
	 * 
	 * @param fieldName
	 *        Attribute name
	 * @param value
	 *        Attribute value
	 */
	public void setFieldValue(String fieldName, String value) {
		try {
			Class<?> c = this.getClass();
			String mName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Class<?>[] cs = {String.class};
			Method m = c.getMethod(mName, cs);
			Object[] args = {value};
			m.invoke(this, args);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Initialize the international resource information for the foreground display
	 * 初始化国际化资源信息，用于前台显示
	 * 
	 * @param i18n
	 *        i18n
	 */
	public void initI18N(Map<String, String> i18n) {
		List<String> fields = getFieldNames();
		for (int i = 0; i < fields.size(); i++) {
			String field = fields.get(i);
			String value = i18n.get(field);
			setFieldValue(field, value);
		}
	}

}
