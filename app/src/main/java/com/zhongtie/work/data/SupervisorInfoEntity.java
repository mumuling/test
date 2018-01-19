package com.zhongtie.work.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhongtie.work.event.BaseEvent;

/**
 * 督导信息
 * Auth:Cheek
 * date:2018.1.13
 */

public class SupervisorInfoEntity implements BaseEvent {
    /**
     * Auth:Cheek
     * date:2018.1.9
     */

    public static class SafeSupervisionEntity {

        /**
         * event_id : 1101
         * user_name : 超人
         * user_picture : http://47.100.3.212:82/510202199911123456.jpg
         * event_time : 2018-01-05
         * company_name : 重庆分公司
         * state : 新进展
         * event_local :
         * event_unit :
         */

        @JSONField(name = "event_id")
        private String eventId;
        @JSONField(name = "user_name")
        private String userName;
        @JSONField(name = "user_picture")
        private String userPicture;
        @JSONField(name = "event_time")
        private String eventTime;
        @JSONField(name = "company_name")
        private String companyName;
        private String state;
        @JSONField(name = "event_local")
        private String eventLocal;
        @JSONField(name = "event_unit")
        private String eventUnit;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(String userPicture) {
            this.userPicture = userPicture;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getEventLocal() {
            return eventLocal;
        }

        public void setEventLocal(String eventLocal) {
            this.eventLocal = eventLocal;
        }

        public String getEventUnit() {
            return eventUnit;
        }

        public void setEventUnit(String eventUnit) {
            this.eventUnit = eventUnit;
        }
    }
}
