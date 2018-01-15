package com.zhongtie.work.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.List;

/**
 * 用户分组
 * Auth: Chaek
 * Date: 2018/1/15
 */

@Table(database = CompanyDB.class, name = "work_u_usergroup")
public class CompanyUserGroupTable {
    @PrimaryKey
    private int id;
    @Column(name = "usergroup_name")
    private String groupName;
    @Column(name = "usergroup_userid", typeConverter = ListStringTypeConverter.class)
    private List<Integer> userList;
    @Column(name = "usergroup_indexid")
    private int indexid;
    @Column(name = "usergroup_company")
    private int company;
    @Column(name = "usergroup_leaderflag")
    private int leaderflag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Integer> getUserList() {
        return userList;
    }

    public void setUserList(List<Integer> userList) {
        this.userList = userList;
    }

    public int getIndexid() {
        return indexid;
    }

    public void setIndexid(int indexid) {
        this.indexid = indexid;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getLeaderflag() {
        return leaderflag;
    }

    public void setLeaderflag(int leaderflag) {
        this.leaderflag = leaderflag;
    }
}
