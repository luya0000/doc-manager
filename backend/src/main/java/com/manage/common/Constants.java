package com.manage.common;

public class Constants {

    /*jwt的角色放入request的key*/
    public static final String JWT_ROLES_KEY = "jwt_roles_key";
    public static final String JWT_SUB_KEY = "jwt_sub_key";
    public static final String JWT_ACCOUNT_KEY = "jwt_account_key";
    /**/
    public static final String ROLE_SPLITOR = "|";

    /*管理员角色*/
    public static final String SYSTEM_TYPE = "0";
    public static final String ADMIN_TYPE = "1";
    public static final String NOMAL_TYPE = "2";

    // 状态 0有效、1无效
    public static final String STATUE_VALID = "1";
    public static final String STATUE_INVALID = "0";

    // --------------------分页常量-------------------
    /*当前页*/
    public static final Integer PAGEHELPER_PAGE_CURRENT = 0;
    /*每页显示条数*/
    public static final Integer PAGEHELPER_PAGE_SIZE = 10;


}
