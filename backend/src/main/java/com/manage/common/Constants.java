package com.manage.common;

public class Constants {

    /*jwt的角色放入request的key*/
    public static final String JWT_ROLES_KEY = "jwt_roles_key";
    public static final String JWT_SUB_KEY = "jwt_sub_key";
    public static final String JWT_ACCOUNT_KEY = "jwt_account_key";

    /*管理员角色*/
    public static final String SYSTEM_TYPE = "SYSTEM_USER";
    public static final String ADMIN_TYPE = "ADMIN_USER";
    public static final String NOMAL_TYPE = "NOMAL_USER";
    public static final String DEFAULT_TYPE = "DEFAULT_USER";


    public static final Integer MENU_TYPE_SYSTEM = 1;
    public static final Integer MENU_TYPE_DEFAULT = 2;
    public static final Integer MENU_DOC_PARENTID = 10;

    // 状态 0有效、1无效
    public static final Integer STATUE_VALID = 1;
    public static final Integer STATUE_INVALID = 0;

    // --------------------分页常量-------------------
    /*当前页*/
    public static final Integer PAGEHELPER_PAGE_CURRENT = 0;
    /*每页显示条数*/
    public static final Integer PAGEHELPER_PAGE_SIZE = 10;

    // 文件访问地址
    public static final String DEPART_FILE_PATH = "#/view/file_manage";

    //
    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";


}
