package com.zhongtie.work.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: Cheek
 * @date: 2018.1.29
 */

public class RewardPunishEntity {

    /*
    * {
            "tax_number":"888888",
            "tax_unit":"第一项目部",
            "tax_userid":1165,
            "username":"超人",
            "userpic":"https://api.023ztjs.com/picture/510202199911123456.jpg",
            "tax_leader":1294,
            "leadername":"粟川",
            "tax_money":5888,
            "state":"已退回",
             "tax_publishtime":"2018-01-07 19:55:18"
        }
    **/

    @JSONField(name = "tax_number")
    private String punishCode;
    @JSONField(name = "tax_userid")
    private int createUserId;

    @JSONField(name = "tax_money")
    private int punishAmount;

    @JSONField(name = "state")
    private String status;

    @JSONField(name = "tax_unit")
    private String punishCompany;

    @JSONField(name = "userpic")
    private String createUserPic;

    @JSONField(name = "username")
    private String createUserName;

    @JSONField(name = "tax_publishtime")
    private String createTime;

    @JSONField(name = "leadername")
    private String punishTeamLeader;


    public void setPunishCode(String punishCode) {
        this.punishCode = punishCode;
    }

    public String getPunishCode() {
        return punishCode;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(int punishAmount) {
        this.punishAmount = punishAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPunishCompany() {
        return punishCompany;
    }

    public void setPunishCompany(String punishCompany) {
        this.punishCompany = punishCompany;
    }

    public String getCreateUserPic() {
        return createUserPic;
    }

    public void setCreateUserPic(String createUserPic) {
        this.createUserPic = createUserPic;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPunishTeamLeader() {
        return punishTeamLeader;
    }

    public void setPunishTeamLeader(String punishTeamLeader) {
        this.punishTeamLeader = punishTeamLeader;
    }
}
