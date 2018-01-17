package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Auth:Cheek
 * date:2018.1.17
 */

@Table(database = ZhongtieDb.class, name = "cache_create_event_table")
public class CacheSafeEventTable {
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private int eventId;

    @Column
    private int userid;
    @Column
    private int company;
    @Column
    private String time;

    @Column
    private String local;

    @Column
    private String unit;
    @Column
    private String troubletype;
    @Column
    private int workerteam;
    @Column
    private String detail;
    @Column
    private String changemust;
    @Column
    private String pic;
    @Column
    private String checker;
    @Column
    private String review;
    @Column
    private String related;
    @Column
    private String at;
    @Column
    private String read;

    @Column
    private int uploadStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getWorkerteam() {
        return workerteam;
    }

    public void setWorkerteam(int workerteam) {
        this.workerteam = workerteam;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getChangemust() {
        return changemust;
    }

    public void setChangemust(String changemust) {
        this.changemust = changemust;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getTroubletype() {
        return troubletype;
    }

    public void setTroubletype(String troubletype) {
        this.troubletype = troubletype;
    }
}
