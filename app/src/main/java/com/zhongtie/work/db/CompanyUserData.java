package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

@Table(database = CompanyDB.class, name = "work_u_user")
public class CompanyUserData extends BaseModel {
    @PrimaryKey
    public int id;
    @Column(name = "user_company")
    public int company;
    @Column(name = "user_account")
    public String userAccount;
    @Column(name = "user_name")
    public String name;
    @Column(name = "user_sex")
    public String sex;
    @Column(name = "user_identitycode")
    public String idencode;
    @Column(name = "user_position")
    public String duty;
    @Column(name = "user_worktype")
    public String worktype;
    @Column(name = "user_unit")
    public String unit;
    @Column(name = "user_learn")
    public String learn;
    @Column(name = "user_health")
    public String health;
    @Column(name = "user_onjob")
    public int onjob;
    @Column(name = "user_picture")
    public String photo;
    @Column(name = "user_insure")
    public String insure;
    @Column(name = "user_workerteam")
    public String workteam;
//    @Column
//    public String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdencode() {
        return idencode;
    }

    public void setIdencode(String idencode) {
        this.idencode = idencode;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public int getOnjob() {
        return onjob;
    }

    public void setOnjob(int onjob) {
        this.onjob = onjob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    public String getWorkteam() {
        return workteam;
    }

    public void setWorkteam(String workteam) {
        this.workteam = workteam;
    }
}
