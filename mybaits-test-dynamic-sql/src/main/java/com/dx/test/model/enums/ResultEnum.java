package com.dx.test.model.enums;

public enum ResultEnum {
	Success(200, "Success", "成功"), //
	Faile(9999, "Faile", "失败"), 
	
	// 用户登录
	UserNameOrPasswordNull(10000,"UserNameOrPasswordNull","用户或密码不允许为空"),//
	LoginFail(10001,"LoginFail","登录失败：用户或密码不正确"),// 
	
	
	;

	private Integer code;
	private String message;
	private String desc;

	ResultEnum(int code, String message, String desc) {
		this.code = code;
		this.message = message;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDesc() {
		return desc;
	}
}
