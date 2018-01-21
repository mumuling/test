package com.zhongtie.work.data;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.19
 */

public class SafeEventEntity {

    /**
     * id : 927
     * event_userid : 719
     * user_pic : https://api.023ztjs.com/picture/420624198708294710.jpg
     * user_name : 肖焕鑫
     * event_publishtime : 2017-09-25t21:32:26
     * event_local : 中央公园西
     * event_unit : 联调
     * event_time : 2017-09-25t00:00:00
     * event_workerteam : 保安
     * event_troubletype : 安全管理
     * event_detail : 站厅层设备区保安脱岗，且已多次出现次现象。
     * event_changemust : 登记岗保安必须保持在岗状态，禁止随意脱岗。
     * event_pic : 2895
     * event_checker : 肖焕鑫
     * event_review : 肖焕鑫,范建伟
     * event_related : 陈浩
     * event_read : 专职安全员
     * signlist : []
     * replylist : []
     * reviewlist : [{"userid":719,"username":"肖焕鑫","userpic":"https://api.023ztjs.com/picture/420624198708294710.jpg","time":"","url":""},{"userid":729,"username":"范建伟","userpic":"https://api.023ztjs.com/picture/410304197406261551.jpg","time":"","url":""}]
     * state : 审批中
     * butstate : {"edit":1,"reply":1,"sign":1,"check":0,"print":0}
     */

    public int id;
    public int event_userid;
    public String user_pic;
    public String user_name;
    public String event_publishtime;
    public String event_local;
    public String event_unit;
    public String event_time;
    public String event_workerteam;
    public String event_troubletype;
    public String event_detail;
    public String event_changemust;
    public String event_pic;
    public String event_checker;
    public String event_review;
    public String event_related;
    public String event_read;
    public String state;
    public ButstateBean butstate;
    public List<ApproveEntity> signlist;
    public List<ReplyEntity> replylist;
    public List<ApproveEntity> reviewlist;

    public static class ButstateBean {
        /**
         * edit : 1
         * reply : 1
         * sign : 1
         * check : 0
         * print : 0
         */

        public int edit;
        public int reply;
        public int sign;
        public int check;
        public int print;
    }

    public static class ReviewlistBean {
        /**
         * userid : 719
         * username : 肖焕鑫
         * userpic : https://api.023ztjs.com/picture/420624198708294710.jpg
         * time :
         * url :
         */

        public int userid;
        public String username;
        public String userpic;
        public String time;
        public String url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_userid() {
        return event_userid;
    }

    public void setEvent_userid(int event_userid) {
        this.event_userid = event_userid;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEvent_publishtime() {
        return event_publishtime;
    }

    public void setEvent_publishtime(String event_publishtime) {
        this.event_publishtime = event_publishtime;
    }

    public String getEvent_local() {
        return event_local;
    }

    public void setEvent_local(String event_local) {
        this.event_local = event_local;
    }

    public String getEvent_unit() {
        return event_unit;
    }

    public void setEvent_unit(String event_unit) {
        this.event_unit = event_unit;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_workerteam() {
        return event_workerteam;
    }

    public void setEvent_workerteam(String event_workerteam) {
        this.event_workerteam = event_workerteam;
    }

    public String getEvent_troubletype() {
        return event_troubletype;
    }

    public void setEvent_troubletype(String event_troubletype) {
        this.event_troubletype = event_troubletype;
    }

    public String getEvent_detail() {
        return event_detail;
    }

    public void setEvent_detail(String event_detail) {
        this.event_detail = event_detail;
    }

    public String getEvent_changemust() {
        return event_changemust;
    }

    public void setEvent_changemust(String event_changemust) {
        this.event_changemust = event_changemust;
    }

    public String getEvent_pic() {
        return event_pic;
    }

    public void setEvent_pic(String event_pic) {
        this.event_pic = event_pic;
    }

    public String getEvent_checker() {
        return event_checker;
    }

    public void setEvent_checker(String event_checker) {
        this.event_checker = event_checker;
    }

    public String getEvent_review() {
        return event_review;
    }

    public void setEvent_review(String event_review) {
        this.event_review = event_review;
    }

    public String getEvent_related() {
        return event_related;
    }

    public void setEvent_related(String event_related) {
        this.event_related = event_related;
    }

    public String getEvent_read() {
        return event_read;
    }

    public void setEvent_read(String event_read) {
        this.event_read = event_read;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ButstateBean getButstate() {
        return butstate;
    }

    public void setButstate(ButstateBean butstate) {
        this.butstate = butstate;
    }

    public List<ApproveEntity> getSignlist() {
        return signlist;
    }

    public void setSignlist(List<ApproveEntity> signlist) {
        this.signlist = signlist;
    }

    public List<ReplyEntity> getReplylist() {
        return replylist;
    }

    public void setReplylist(List<ReplyEntity> replylist) {
        this.replylist = replylist;
    }

    public List<ApproveEntity> getReviewlist() {
        return reviewlist;
    }

    public void setReviewlist(List<ApproveEntity> reviewlist) {
        this.reviewlist = reviewlist;
    }
}
