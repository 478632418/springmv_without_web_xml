package com.dx.test.model.vo;

public class BaseResult {
	private Integer code; // 状态编码
	private String message; // 返回信息
	private String desc; // 返回信息描述

	public BaseResult(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public BaseResult(Integer code, String message, String desc) {
		super();
		this.code = code;
		this.message = message;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
