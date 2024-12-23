package com.qfwebsite.rsx.utils;

import lombok.Data;

@Data
public class HttpCode {

    /**
     * common
     */
    public static final int SUCCESS = 1;

    public static final int FAILED = 2;

    public static final int PARAMS_INVALID = 3;

    public static final int INNER_ERROR = 4;

    public static final int TIME_OUT = 5;

    public static final int AUTH_FAILED = 6;

    public static final int WITHOUT_PRIVILEGE = 7;

    public static final int NOT_FOUND = 8;

    // 登录错误
    public static final int LOGIN_ERROR = 1001;

    // 账号不存在
    public static final int ACCOUNT_NOT_EXISTENT = 1002;

    // 账号已过期
    public static final int ACCOUNT_NOT_EXPIRE = 1003;

    // 公词产品
    public static final int GENERAL_INVALID = 2001;

    // 用户ID不对应产品
    public static final int NAME_ID_INVALID = 2002;

    // 有效期错误
    public static final int VALIDITY_TIME_INVALID = 2003;

    // 已注册
    public static final int VALIDITY_REGISTRATION_STATUS = 2004;

    // 已上架
    public static final int VALIDITY_UP_STATUS = 2005;

    // 链接不能为空
    public static final int VALIDITY_LINK = 2005;

    // 不是公库数据不能分配
    public static final int GEN_ERROR = 2006;
}
