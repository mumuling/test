package com.zhongtie.work.event;

/**
 * Created by Shall on 2015-07-13.
 */
public class EventData {
    /**
     * 标识
     */
    public int flag;
    public Object data;

    public EventData(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public EventData(int flag, Object data) {
        this.flag = flag;
        this.data = data;
    }
}