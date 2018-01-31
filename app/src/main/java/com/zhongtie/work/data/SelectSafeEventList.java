package com.zhongtie.work.data;

import java.util.List;

/**
 * @author: Chaek
 * @date: 2018/1/31
 */

public class SelectSafeEventList {

    /**
     * time : 2017-11-13
     * events : [{"id":"948","time":"2017-11-13","unit":"十号线供电设备","local":"红土地"},{"id":"949","time":"2017-11-13","unit":"十号线供电设备项目部","local":"红土地"},{"id":"950","time":"2017-11-13","unit":"1","local":"1"}]
     */

    private String time;
    private List<SelectSafeEventEntity> events;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<SelectSafeEventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<SelectSafeEventEntity> events) {
        this.events = events;
    }

    public static class SelectSafeEventEntity {
        /**
         * id : 948
         * time : 2017-11-13
         * unit : 十号线供电设备
         * local : 红土地
         */

        private String id;
        private String time;
        private String unit;
        private String local;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
}
