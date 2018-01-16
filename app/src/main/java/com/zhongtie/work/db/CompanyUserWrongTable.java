package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Auth:Cheek
 * date:2018.1.16
 */

@Table(database = CompanyDB.class, name = "work_u_wrong")
public class CompanyUserWrongTable {

    @PrimaryKey
    private int id;
    @Column(name = "wrong_userid")
    private int userId;
    @Column(name = "wrong_detail")
    private String content;
    @Column(name = "wrong_time")
    private String time;
    @Column(name = "wrong_byid")
    private int byId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getById() {
        return byId;
    }

    public void setById(int byId) {
        this.byId = byId;
    }
}
