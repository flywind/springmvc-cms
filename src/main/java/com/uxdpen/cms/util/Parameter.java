package com.uxdpen.cms.util;

import java.util.Date;
import java.util.List;

import com.uxdpen.cms.util.PageParameter.QueryType;

public class Parameter{
	private Object paraValue;
	private QueryType queryType;
	public Parameter(String paraValue,QueryType queryType){
		if(paraValue == null || paraValue == ""){
			return;
		}
		this.queryType = queryType;
		if(queryType == QueryType.LIKE){
			this.paraValue = "%"+paraValue+"%";
		}else if(queryType == QueryType.IS_NOT_NULL){
			this.paraValue = " ";
		}else
		this.paraValue = paraValue;
	}
	public Parameter(Integer paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = paraValue;
	}
	public Parameter(Character paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = paraValue;
	}
	public Parameter(Date paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = paraValue;
	}
	public Parameter(Double paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = paraValue;
	}
	public Parameter(Long paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = paraValue;
	}
	public Parameter(List<?> paraValue,QueryType queryType){
		if(paraValue == null || paraValue.isEmpty()){
			return;
		}
		this.queryType = queryType;
		this.paraValue = "";
		if(queryType == QueryType.IN_CHAR){
			for(Object obj : paraValue){
				this.paraValue += "'" +obj+"',";
			}
		}else
		if(queryType == QueryType.IN_INT){
			for(Object obj : paraValue){
				this.paraValue += obj+",";
			}
		}else{
			try {
				throw new Exception("<<参数类型与参数值不匹配异常>>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.paraValue = "("+String.valueOf(this.paraValue).substring(0,String.valueOf(this.paraValue).length() - 1)+")";
	}
	public Parameter(String[] paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = "";
		if(queryType == QueryType.IN_CHAR){
			for(Object obj : paraValue){
				this.paraValue += "'" +obj+"',";
			}
		}else
		if(queryType == QueryType.IN_INT){
			for(Object obj : paraValue){
				this.paraValue += obj+",";
			}
		}else{
			try {
				throw new Exception("<<参数类型与参数值不匹配异常>>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.paraValue = "("+String.valueOf(this.paraValue).substring(0,String.valueOf(this.paraValue).length() - 1)+")";
	}
	public Parameter(Object paraValue,QueryType queryType){
		if(paraValue == null){
			return;
		}
		this.queryType = queryType;
		this.paraValue = paraValue;
	}
	
	public Object getParaValue() {
		return paraValue;
	}
	public void setParaValue(Object paraValue) {
		this.paraValue = paraValue;
	}
	public QueryType getQueryType() {
		return queryType;
	}
	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	
}
