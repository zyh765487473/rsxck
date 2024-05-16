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

    // 价格错误
    public static final int INVALID_PRICE = 1002;

    // 请求paypal异常
    public static final int PAY_HTTP_EX = 1003;
}
