package com.manage.common;


/**
 * 所有业务异常的枚举
 * code： xx（模块）xx（业务）xx（序号）
 */
public enum BizStatusEnum implements ResponseStatusEnum {

    OK(200, "OK"),
    ERROR(-1, "ERROR"),
    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(100001, "账号密码错误"),
    USER_CHG_PWD_ERROR(100002,"修改密码错误，用户不存在或者密码错误");

    BizStatusEnum(int code, String message) {
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