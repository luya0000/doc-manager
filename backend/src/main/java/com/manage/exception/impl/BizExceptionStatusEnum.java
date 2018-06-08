package com.manage.exception.impl;


import com.manage.exception.ResponseStatusEnum;

/**
 * 所有业务异常的枚举
 */
public enum BizExceptionStatusEnum implements ResponseStatusEnum {

    /**
     * 用户：1xxx，角色：2xxx，小组：3xxx，权限：4xxx，文件：5xxx
     */
    AUTH_REQUEST_ERROR(100001, "账号密码错误"),
    USER_CHG_PWD_ERROR(100002, "修改密码错误，用户不存在或者密码错误"),;

    BizExceptionStatusEnum(int code, String message) {
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