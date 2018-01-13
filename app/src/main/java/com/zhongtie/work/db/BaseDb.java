package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

@Table(database = AppDB.class, name = "user")
public class BaseDb extends BaseModel {
    @PrimaryKey
    public int id;
    public int company;
    public String name;
    public String sex;
    public String idencode;
    public String duty;
    public String worktype;
    public String unit;
    public String learn;
    public String health;
    public String onjob;
    public String photo;
    public String insure;
    public String workteam;
    public String url;

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

    public String getOnjob() {
        return onjob;
    }

    public void setOnjob(String onjob) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
