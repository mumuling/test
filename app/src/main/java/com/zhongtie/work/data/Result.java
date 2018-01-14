package com.zhongtie.work.data;

/**
 * 返回统一model
 */

public class Result<T> {
    private boolean success;
    private String msg;
    private T data;
    private int code;
    private T list;

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
