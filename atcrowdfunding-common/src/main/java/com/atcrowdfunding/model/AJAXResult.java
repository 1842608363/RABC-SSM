package com.atcrowdfunding.model;

/**
 * 
 * @author 王海啸
 * 封住Ajax结果
 */
public class AJAXResult {
	
	private Object data;
	private boolean success;
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
}
