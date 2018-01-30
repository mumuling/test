package com.zhongtie.work.data;

import android.support.annotation.StringRes;

/**
 * 安全惩罚详情
 * Auth:Cheek
 * date:2018.1.27
 */

public class RewardPunishDetailEntity {


    private String punishCode;
    private String punishCompany;

    private int punishAmount;
    private String summary;

    private String content;

    private int safeEventId;
    private String safeEventUserName;
    private String safeEventSite;
    private String safeEventCompany;

    private int createUserId;
    private String createUserName;
    /**
     * 创建用户头像
     */
    private String createUserPic;
    private String createTime;


    /**
     * 安全监察编号
     */
    private int supervisionId;
    private String supervisionUserName;
    /**
     * 头像
     */
    private String supervisionUserPic;
    /**
     * 签名
     */
    private String supervisionUserSignImg;

    private int punishLeaderId;
    private String punishLeaderName;

    private String punishLeaderPic;
    private String punishLeadSign;

    private String punishSignTime;

    private String readGroupId;
    private String readGroupName;

    /**
     * 退回理由
     */
    private String sendBackReason;

    private String sendBackSign;

    private String sendBackTime;

    private String atUserId;
    private String atUserName;
    private String atUserPic;

    private int consentStatius;
    private int sendBackStatius;
    private int signStatius;
    private int cancelStatus;
    private int printStatus;

    public String getPunishCode() {
        return punishCode;
    }

    public void setPunishCode(String punishCode) {
        this.punishCode = punishCode;
    }

    public String getPunishCompany() {
        return punishCompany;
    }

    public void setPunishCompany(String punishCompany) {
        this.punishCompany = punishCompany;
    }

    public int getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(int punishAmount) {
        this.punishAmount = punishAmount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSafeEventId() {
        return safeEventId;
    }

    public void setSafeEventId(int safeEventId) {
        this.safeEventId = safeEventId;
    }

    public String getSafeEventUserName() {
        return safeEventUserName;
    }

    public void setSafeEventUserName(String safeEventUserName) {
        this.safeEventUserName = safeEventUserName;
    }

    public String getSafeEventSite() {
        return safeEventSite;
    }

    public void setSafeEventSite(String safeEventSite) {
        this.safeEventSite = safeEventSite;
    }

    public String getSafeEventCompany() {
        return safeEventCompany;
    }

    public void setSafeEventCompany(String safeEventCompany) {
        this.safeEventCompany = safeEventCompany;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserPic() {
        return createUserPic;
    }

    public void setCreateUserPic(String createUserPic) {
        this.createUserPic = createUserPic;
    }

    public int getSupervisionId() {
        return supervisionId;
    }

    public void setSupervisionId(int supervisionId) {
        this.supervisionId = supervisionId;
    }

    public String getSupervisionUserName() {
        return supervisionUserName;
    }

    public void setSupervisionUserName(String supervisionUserName) {
        this.supervisionUserName = supervisionUserName;
    }

    public String getSupervisionUserPic() {
        return supervisionUserPic;
    }

    public void setSupervisionUserPic(String supervisionUserPic) {
        this.supervisionUserPic = supervisionUserPic;
    }

    public String getSupervisionUserSignImg() {
        return supervisionUserSignImg;
    }

    public void setSupervisionUserSignImg(String supervisionUserSignImg) {
        this.supervisionUserSignImg = supervisionUserSignImg;
    }

    public int getPunishLeaderId() {
        return punishLeaderId;
    }

    public void setPunishLeaderId(int punishLeaderId) {
        this.punishLeaderId = punishLeaderId;
    }

    public String getPunishLeaderName() {
        return punishLeaderName;
    }

    public void setPunishLeaderName(String punishLeaderName) {
        this.punishLeaderName = punishLeaderName;
    }

    public String getPunishLeaderPic() {
        return punishLeaderPic;
    }

    public void setPunishLeaderPic(String punishLeaderPic) {
        this.punishLeaderPic = punishLeaderPic;
    }

    public String getPunishLeadSign() {
        return punishLeadSign;
    }

    public void setPunishLeadSign(String punishLeadSign) {
        this.punishLeadSign = punishLeadSign;
    }

    public String getPunishSignTime() {
        return punishSignTime;
    }

    public void setPunishSignTime(String punishSignTime) {
        this.punishSignTime = punishSignTime;
    }

    public String getReadGroupId() {
        return readGroupId;
    }

    public void setReadGroupId(String readGroupId) {
        this.readGroupId = readGroupId;
    }

    public String getReadGroupName() {
        return readGroupName;
    }

    public void setReadGroupName(String readGroupName) {
        this.readGroupName = readGroupName;
    }

    public String getSendBackReason() {
        return sendBackReason;
    }

    public void setSendBackReason(String sendBackReason) {
        this.sendBackReason = sendBackReason;
    }

    public String getSendBackSign() {
        return sendBackSign;
    }

    public void setSendBackSign(String sendBackSign) {
        this.sendBackSign = sendBackSign;
    }

    public String getSendBackTime() {
        return sendBackTime;
    }

    public void setSendBackTime(String sendBackTime) {
        this.sendBackTime = sendBackTime;
    }

    public String getAtUserId() {
        return atUserId;
    }

    public void setAtUserId(String atUserId) {
        this.atUserId = atUserId;
    }

    public String getAtUserName() {
        return atUserName;
    }

    public void setAtUserName(String atUserName) {
        this.atUserName = atUserName;
    }

    public String getAtUserPic() {
        return atUserPic;
    }

    public void setAtUserPic(String atUserPic) {
        this.atUserPic = atUserPic;
    }

    public int getConsentStatius() {
        return consentStatius;
    }

    public void setConsentStatius(int consentStatius) {
        this.consentStatius = consentStatius;
    }

    public int getSendBackStatius() {
        return sendBackStatius;
    }

    public void setSendBackStatius(int sendBackStatius) {
        this.sendBackStatius = sendBackStatius;
    }

    public int getSignStatius() {
        return signStatius;
    }

    public void setSignStatius(int signStatius) {
        this.signStatius = signStatius;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public int getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(int printStatus) {
        this.printStatus = printStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
