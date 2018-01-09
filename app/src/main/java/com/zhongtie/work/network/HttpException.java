package com.zhongtie.work.network;


import com.alibaba.fastjson.JSONException;
import com.zhongtie.work.BuildConfig;

import java.io.IOException;


public class HttpException extends RuntimeException {
    public static int ERROR_ORDER_EXECUTED = -3;
    public int code;

    public HttpException(String message, int code) {
        super(message);
        this.code = code;
    }


    /**
     * getErrorMessage
     *
     * @param throwable
     * @return
     */
    public static String getErrorMessage(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }
        if (throwable == null) {
            return "遇到了错误";
        }
        if (isNetworkException(throwable)) {
            if (throwable instanceof retrofit2.HttpException) {
                int code = ((retrofit2.HttpException) throwable).code();
                if (code >= 400 && code <= 499) {
                    if (code == 404) {
                        return "服务不存在,请联系管理员";
                    } else {
                        return "服务器未知错误,请联系管理员";
                    }
                } else if (code >= 500) {
                    return "服务器内部错误,请联系管理员";
                } else {
                    return "请求出错Code:" + code;
                }
            }
            return "无法连接到服务器";
        } else if (isHttpException(throwable) != null) {
            return throwable.getMessage();
        } else if (throwable instanceof JSONException) {
            return "数据解析错误";
        } else {
            return "未知错误，请向我们反馈问题（" + throwable.getClass().getSimpleName() + "）";
        }
    }

    /**
     * show throwable message
     *
     * @param throwable
     */

    public static void toastMessage(Throwable throwable) {
//        ViewUtils.showToast(getErrorMessage(throwable), Toast.LENGTH_LONG);
    }

    /**
     * is internal http exception type, and return it
     *
     * @param throwable
     * @return
     */
    public static HttpException isHttpException(Throwable throwable) {
        if (!(throwable instanceof HttpException)) {
            Throwable causeThrowable = throwable;
            while ((causeThrowable = causeThrowable.getCause()) != null) {
                if (causeThrowable instanceof HttpException) {
                    return (HttpException) causeThrowable;
                }
            }
            return null;
        } else {
            return (HttpException) throwable;
        }
    }

    /**
     * @param throwable
     * @return
     */
    public static boolean isNetworkException(Throwable throwable) {
        if (!(throwable instanceof retrofit2.HttpException) && !(throwable instanceof IOException)) {
            Throwable causeThrowable = throwable;
            while ((causeThrowable = causeThrowable.getCause()) != null) {
                if (causeThrowable instanceof retrofit2.HttpException || causeThrowable instanceof IOException) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }
}
