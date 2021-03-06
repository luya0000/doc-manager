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
    USER_CHG_PWD_ERROR(100002, "修改密码错误，用户不存在或者密码错误"),
    USER_ADD_ERROR(100003, "添加用户失败"),
    USER_UPDATE_ERROR(100004, "更新用户失败"),
    USER_DELETE_ERROR(100005, "删除用户失败"),
    USER_SELETE_ERROR(100006, "查询用户失败"),
    USER_OLD_NEW_PWD_ERROR(100007, "修改密码错误，两次密码不一致"),
    USER_PWD_DIFF_ERROR(100008, "两次密码不一致"),

    /*角色*/
    ROLE_SELETE_ERROR(200001, "查询角色失败"),
    ROLE_ADD_ERROR(200002, "添加角色失败"),
    ROLE_UPDATE_ERROR(200003, "更新角色失败"),
    ROLE_DELETE_ERROR(200004, "删除角色失败"),

    /*权限*/
    PERMIS_SELETE_ERROR(400001, "查询权限失败"),
    PERMIS_ADD_ERROR(400002, "添加权限失败"),
    PERMIS_DELETE_ERROR(400003, "删除权限失败"),


    USER_HAS_NO_ROLE_ERROR(300007, "权限不足"),

    /*文件*/
    FILE_NOT_EXIST_ERROR(500001, "文件或文件夹不存在"),
    FILE_NOT_FILE_ERROR(500002, "不是文件"),
    FILE_NOT_DIR_ERROR(500003, "不是文件夹"),
    FILE_CREATE_ERROR(500004, "创建文件失败"),
    FILE_DELETE_ERROR(500005, "删除文件失败"),
    FILE_RENAME_ERROR(500006, "文件改名失败"),
    FILE_COPY_ERROR(500007, "文件复制失败"),
    FILE_NOT_EXEC_ERROR(500008, "无权限操作当前文件或文件夹"),


    ;

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