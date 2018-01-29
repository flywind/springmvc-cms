package com.uxdpen.cms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PageParameter implements Serializable{
	private static final long serialVersionUID = 1L;
	private int page;
	private Long rowsCount;
	private int rows;
	private String sort;
	private String sortName;
	private String order;
	private Date startTime;
	private Date endTime;
	private String targetTime;
	private Map<String,Parameter> parasMap = new HashMap<String,Parameter>();
	/***
	 * 用于select ? where ? and(a or b or c)查询
	 */
	private List<Map<String,Parameter>> parameterGroup = new ArrayList<Map<String,Parameter>>();
	public enum QueryType{
		EQUALS("="),GREATER_THAN(">"),LESS_THAN("<"),LIKE("like"),NOT_EQUALS("!="),
		GREATER_THAN_OR_EQUALS(">="),LESS_THAN_OR_EQUALS("<="),IN_INT("in"),IN_CHAR("in"),IS_NOT_NULL("is not null");
		private String sign;
		private QueryType(String sign){
			this.sign = sign;
		}
		public String getSign(){
			return this.sign;
		}
	}
	public void putParameterGroup(Map<String,Parameter> parameterGrop){
		this.parameterGroup.add(parameterGrop);
	}
	/***
	 * 用于select ? where ? and(a or b or c)查询
	 */
	public List<Map<String,Parameter>> getParameterGroup(){
		return this.parameterGroup;
	}
	public String getTargetTime() {
		return targetTime;
	}
	public void setTargetTime(String targetTime) {
		this.targetTime = targetTime;
	}
	public void putParameter(String name,Parameter parameter){
		this.parasMap.put(name, parameter);
	}
	public Map<String,Parameter> getParameters(){
		return parasMap;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getRowsCount() {
		return rowsCount;
	}
	public void setRowsCount(Long rowsCount) {
		this.rowsCount = rowsCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
