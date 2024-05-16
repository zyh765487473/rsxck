package com.qfwebsite.rsx.utils;

public class ResponseUtils {
    public static SimpleResponse createOkResponse() {
        return new SimpleResponse(HttpCode.SUCCESS, "success");
    }

    public static SimpleResponse createOkResponse(int code, String message) {
        return new SimpleResponse(code, message);
    }

    public static <T> SimpleResponse<T> createOkResponse(T data) {
        return new SimpleResponse<>(HttpCode.SUCCESS, "success", data);
    }

    public static SimpleResponse createResponse(int code, String message) {
        return new SimpleResponse(code, message);
    }

    public static <T> SimpleResponse<T> createResponse(int code, String message, T data) {
        return new SimpleResponse<>(code, message, data);
    }

    //该接口只适用于内部管理后台使用，其它不建议使用
    public static SimpleResponse innerErrorResponse() {
        return new SimpleResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
    }

    //该接口只适用于内部管理后台使用，其它不建议使用
    public static SimpleResponse errorResponse(String msg){
        return new SimpleResponse(HttpCode.FAILED, msg);
    }

    public static SimpleResponse authFailedResponse(){
        return new SimpleResponse(HttpCode.AUTH_FAILED, "授权失败，请登录");
    }

    public static SimpleResponse timeOutResponse(){
        return new SimpleResponse(HttpCode.TIME_OUT, "TIME OUT");
    }

    public static SimpleResponse appInnerErrorResponse() {
        return new SimpleResponse(HttpCode.INNER_ERROR, "INNER ERROR");
    }
}
