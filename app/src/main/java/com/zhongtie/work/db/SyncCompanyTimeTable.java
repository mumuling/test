package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 记录同步成功时间
 * Auth:Cheek
 * date:2018.1.16
 */

@Table(database = ZhongtieDb.class, name = "company_syn_time_table")
public class SyncCompanyTimeTable extends BaseModel{
    @PrimaryKey
    private int companyID;
    @Column
    private long synTime;

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public long getSynTime() {
        return synTime;
    }

    public void setSynTime(long synTime) {
        this.synTime = synTime;
    }
}
