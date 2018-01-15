package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

@Table(database = ZhongtieDb.class, name = "sync_company_table")
public class SyncCompanyDB {

    /**
     * id : 5
     * name : 华东分公司
     * sign : 0
     * dbupdatetime :
     * dburl :
     */

    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private int sign;
    @Column
    private String dbupdatetime;
    @Column
    private String dburl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getDbupdatetime() {
        return dbupdatetime;
    }

    public void setDbupdatetime(String dbupdatetime) {
        this.dbupdatetime = dbupdatetime;
    }

    public String getDburl() {
        return dburl;
    }

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }
}
