package com.uxdpen.cms.business.common.result;

import java.util.List;

import com.uxdpen.cms.business.common.utils.JSONSerializer;
import com.uxdpen.cms.business.entities.base.FBase;

/**
 * <p>EasyUI DataGrid module.</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Grid implements java.io.Serializable {

	private Long total = 0L;
	private String rows;
	
	public void setRows(List<? extends FBase> data) {
		this.rows = JSONSerializer.serialize(data);
	}
	/**
	 * @return the rows
	 */
	public String getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}
	/**
	 * version
	 */
	private String version;

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
}
