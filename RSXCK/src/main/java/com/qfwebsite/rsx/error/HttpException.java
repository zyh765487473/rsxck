package com.qfwebsite.rsx.error;

public class HttpException extends RuntimeException{
    private int code;

    public HttpException() {
        super();
    }

    public HttpException(int code) {
        this.code = code;
    }

    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
