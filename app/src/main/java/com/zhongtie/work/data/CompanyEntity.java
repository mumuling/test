package com.zhongtie.work.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zhongtie.work.db.ZhongtieDb;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

@Table(database = ZhongtieDb.class, name = "company_list_table")
public class CompanyEntity extends BaseModel {

    /**
     * id : 1
     * name : 重庆分公司
     * sign : 0
     */

    @PrimaryKey
    public int id;
    @Column
    public String name;
    @Column
    public int sign;

    @Column
    public String dbupdatetime;

    @Column
    public String dburl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
