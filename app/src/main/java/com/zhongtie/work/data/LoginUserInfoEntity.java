package com.zhongtie.work.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.zhongtie.work.db.ZhongtieDb;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

@Table(database = ZhongtieDb.class, name = "login_user_info_table")
public class LoginUserInfoEntity extends com.raizlabs.android.dbflow.structure.BaseModel{

    /**
     * id : 572
     * name : 李红超
     * company : 1
     * companyname : 重庆分公司
     * account : lihongchao
     * sex : 男
     * identity : 130229198707051012
     * picture : /picture/130229198707051012.jpg
     * duty : 执法大队队长
     * worktype : 注册安全工程师
     * sign : https://api.023ztjs.com/upload//day_171115/201711151929171929.png
     */

    @PrimaryKey
    public String id;
    @Column
    public String name;
    @Column
    public int company;
    @Column
    public String companyname;
    @Column
    public String account;
    @Column
    public String sex;
    @Column
    public String identity;
    @Column
    public String picture;
    @Column
    public String duty;
    @Column
    public String worktype;
    @Column
    public String sign;


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

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
