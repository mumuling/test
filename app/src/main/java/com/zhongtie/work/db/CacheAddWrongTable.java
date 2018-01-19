package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Auth:Cheek
 * date:2018.1.16
 */

@Table(database = ZhongtieDb.class, name = "cache_user_wrong_table")
public class CacheAddWrongTable extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private int userId;
    @Column
    private int byUserId;
    @Column
    private String content;
    @Column
    private String time;
    @Column
    private int addWrongId;

    /**
     * 0 上次未上传 1上传失败
     */
    @Column
    private int status;

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

    public int getByUserId() {
        return byUserId;
    }

    public void setByUserId(int byUserId) {
        this.byUserId = byUserId;
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

    public int getStatus() {
        return status;
    }

    public int getAddWrongId() {
        return addWrongId;
    }

    public void setAddWrongId(int addWrongId) {
        this.addWrongId = addWrongId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
