package com.uxdpen.cms.business.common.result;

import com.uxdpen.cms.business.common.utils.JSONSerializer;


/**
 * <p>JSON module.</p>
 * 
 * @author flywind(飞风)
 * @date 2016年6月21日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Json implements java.io.Serializable {

	/**
	 * result 1:success 0:failed
	 */
	private int success = 0;
	
	private String errorCode = null;
	
	private String msg = "Operation failed";
	
	private Object obj = null;

	public int isSuccess() {
		return success;
	}
	
	public int getSuccess() {
		return success;
	}
	
	public void setSuccess(int success) {
		this.success = success;
	}

	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = JSONSerializer.serialize(obj);
	}

	@Override
	public String toString(){
		return obj.toString();
	}
	
}
