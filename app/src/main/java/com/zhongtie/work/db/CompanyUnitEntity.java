package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.db.CompanyDB;
import com.zhongtie.work.util.HanziToPinyin;

/**
 * 工作单位
 * Auth: Chaek
 * Date: 2018/1/11
 */
@Table(database = CompanyDB.class, name = "work_s_unit")
public class CompanyUnitEntity {

    @PrimaryKey
    private int id;
    @Column(name = "unit_name")
    private String name;
    @Column(name = "unit_companyid")
    private int company;

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


    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public ProjectTeamEntity convert() {
        ProjectTeamEntity teamEntity = new ProjectTeamEntity(name);
        teamEntity.setCharacter(HanziToPinyin.getInstance().get(name.substring(0, 1)).get(0).target.substring(0, 1));
        teamEntity.setProjectTeamID(id);
        teamEntity.setCompanyID(company);
        return teamEntity;
    }
}
