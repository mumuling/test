package com.zhongtie.work.network;

/**
 * 网络错误异常
 */
public class NetWorkException extends RuntimeException {
    public NetWorkException() {
        ////
    }

    public NetWorkException(String detailMessage) {
        super(detailMessage);
    }

    public NetWorkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetWorkException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
