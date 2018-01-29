package com.uxdpen.cms.domain;

import java.io.Serializable;

public class PageInfoBean implements Serializable {
	
	private static final long serialVersionUID = 4536458165540069462L;

	public static final int COMMON_RECORD_COUNT_IN_ONE_PAGE = 10;
	//每页记录数
	private int recordCountInOnePage;
	//当前页
	private int curPageNo;
	//总页数
	private int totalPageNo;
	//总记录数
	private int totalRecordCount = 0;
	
	/** 按哪个字段排序 */
	private String sortName;
	
	/** 排序标记(升序ASC， 降序DESC) */
	private String sortOrder = "asc";
	

	/*
	 * 以下4个字段用于接收前台页面的 分页，排序 参数
	 */
	/**
	 * 当前显示第几页
	 */
	private Integer page;
	
	/**
	 * 一页显示几条记录
	 */
	private Integer rows;
	
	/**
	 * 排序字段名称
	 */
	private String sort;
	
	/**
	 * 排序方式：升序/降序
	 */
	private String order;
	

	public PageInfoBean(){
		
	}
	
	public PageInfoBean(int curPageNo) {
		this.curPageNo = curPageNo;
		this.recordCountInOnePage = 10;
	}
	
	public PageInfoBean(int curPageNo, int recordCountInOnePage)
	   {
	     this.curPageNo = curPageNo;
	     this.recordCountInOnePage = recordCountInOnePage;
	   }

	public int getRecordCountInOnePage() {
		return recordCountInOnePage;
	}

	public void setRecordCountInOnePage(int recordCountInOnePage) {
		this.recordCountInOnePage = recordCountInOnePage;
	}

	public int getCurPageNo() {
		return curPageNo;
	}

	public void setCurPageNo(int curPageNo) {
		this.curPageNo = curPageNo;
	}
	
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	
	
	 public int getTotalPageNo() {
		return totalPageNo;
	}

	public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getPage() {
	    if(this.page == null){
	        return 1;
	    }
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
	    if(this.rows == null){
	        return 20;
	    }
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	//总页数
	public void doReviseData() {
		if (this.curPageNo <= 0) {
			this.curPageNo = 1;
		}
		if (this.recordCountInOnePage <= 0) {
			this.recordCountInOnePage = 10;
		}

		if (this.totalRecordCount < 0) {
			this.totalRecordCount = 0;
		}

		this.totalPageNo = (int) Math.ceil(this.totalRecordCount
				/ this.recordCountInOnePage);
		if (this.totalPageNo == 0) {
			this.totalPageNo = 1;
		}

	}

}
