package com.doc.common;

/**
 * 系统异常枚举
 * code： xx（系统）xx（业务）xx（序号）
 */
public enum SysStatusEnum implements ResponseStatusEnum {

	/**
	 * 错误的请求
	 */
	PARAMETER_REQUEST_ERROR(100001, "请求参数错误"),
	PARAMETER_VALIDATE_ERROR(100002,"参数校验错误"),
	SOURCE_NOT_FOUND_ERROR(100003, "资源未找到"),
	METHOD_NOT_ALLOWED_ERROR(100004, "请求方法错误"),
	SERVER_ERROR(100005, "服务器异常"),
	WRITE_ERROR(100006,"渲染界面错误"),

	FORBIDDEN(100101, "权限不足");

	SysStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}