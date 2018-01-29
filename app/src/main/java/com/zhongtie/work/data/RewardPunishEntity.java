package com.zhongtie.work.data;

/**
 * @author: Cheek
 * @date: 2018.1.29
 */

public class RewardPunishEntity {
    private int punishId;
    private int createUserId;
    private int punishAmount;
    private int status;
    private String punishCompany;
    private String createUserPic;
    private String createUserName;
    private String createTime;
    private String punishTeamLeader;


    public int getPunishId() {
        return punishId;
    }

    public void setPunishId(int punishId) {
        this.punishId = punishId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
