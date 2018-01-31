package com.zhongtie.work.data;

import com.zhongtie.work.event.BaseEvent;

public class SelectSafeEventEntity implements BaseEvent {
    /**
     * id : 948
     * time : 2017-11-13
     * unit : 十号线供电设备
     * local : 红土地
     */

    private int id;
    private String time;
    private String unit;
    private String local;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}