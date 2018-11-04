/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package com.how2java.tmall.util;

public class Result {
	public static int SUCCESS_CODE = 0;
	public static int FAIL_CODE = 1;
	
	int code;
	String message;
	Object data;
	
	private Result(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static Result success() {
		return new Result(SUCCESS_CODE,null,null);
	}
	public static Result success(Object data) {
		return new Result(SUCCESS_CODE,"",data);
	}
	public static Result fail(String message) {
		return new Result(FAIL_CODE,message,null);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}

/**
* 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
