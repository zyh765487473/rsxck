package com.qfwebsite.rsx.error;


public class RequestFailedException extends HttpException {

    public RequestFailedException(int code) {
        super(code);
    }

    public RequestFailedException(int code, String msg) {
        super(code, msg);
    }
}
