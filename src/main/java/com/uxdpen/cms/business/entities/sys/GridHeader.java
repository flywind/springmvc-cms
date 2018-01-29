package com.uxdpen.cms.business.entities.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uxdpen.cms.business.entities.base.FBase;

/**
 * <p>自定义表字段名称</p>
 * 
 * @author flywind 2158
 * @date 2015年12月16日
 * @since 1.0
 */
@Entity
@Table(name="td_s_grid_header")
public class GridHeader extends FBase {

	private static final long serialVersionUID = 4842578860532959764L;

	/**
	 * datagrid表头field的值
	 */
	private String field;
	
	/**
	 * datagrid表头title的值-中文语言
	 */
	private String titleZhCn;
	
	/**
	 * datagrid表头title的值-英文语言
	 */
	private String titleEn;
	
	/**
	 * 表的名字
	 */
	private String tableName;
	
	/**
	 * 国际化信息，在中文语言下存放的是中文，在英文语言下存放的是英文
	 */
	private String value;
	
	/**
	 * 默认构造方法
	 */
	public GridHeader() {
		
	}
	
	/**
	 * 构造方法
	 * 
	 * @param tableName
	 *        对应的实体类名称
	 * @param field
	 *        国际化属性名
	 * @param value
	 *        国际化属性值
	 */
	public GridHeader(String tableName, String field, String value) {
		this.tableName = tableName;
		this.field = field;
		this.value = value;
	}

	@Column(name="field",length=100)
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Column(name="table_name",length=200)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name="title_zh_cn",length=1000)
	public String getTitleZhCn() {
		return titleZhCn;
	}

	public void setTitleZhCn(String titleZhCn) {
		this.titleZhCn = titleZhCn;
	}

	@Column(name="title_en",length=1000)
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	@Transient
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
