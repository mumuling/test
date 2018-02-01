package com.zhongtie.work.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhongtie.work.util.JsonUtil;

import java.util.List;

/**
 * 安全惩罚详情
 * Auth:Cheek
 * date:2018.1.27
 */

public class RewardPunishDetailEntity {

    /*
    * {
      "id": 31,
      "tax_unit": "unit111",
      "tax_money": 444,
      "tax_summary": "summary111",
      "tax_detail": "detail111",
      "tax_eventid": 1102,
      "eventusername": "超人",
      "eventlocal": "测试",
      "eventunit": "项目二部",
      "tax_userid": 1,
      "taxusername": "田晓元",
      "taxuserpic": "https://api.023ztjs.com/picture/610324198702113446.jpg",
      "tax_safer": 9,
      "saferusername": "刘金鑫",
      "saferuserpic": "https://api.023ztjs.com/picture/500235199101254019.jpg",
      "tax_safersign": "signurl",
      "tax_safertime": "2018/1/31 11:30:06",
      "tax_leader": 6,
      "leaderusername": "黄维荣",
      "leaderuserpic": "https://api.023ztjs.com/picture/532127197511201115.jpg",
      "tax_leadersign": "signurl",
      "tax_leadertime": "2018/1/31 15:36:11",
      "readerlist": [
        {
          "id": 2,
          "name": "十号线通信项目"
        },
        {
          "id": 4,
          "name": "十号线信号项目"
        }
      ],
      "sendBackList": "[]",
      "atuserlist": [
        {
          "id": 1,
          "name": "田晓元",
          "pic": "https://api.023ztjs.com/picture/610324198702113446.jpg"
        },
        {
          "id": 2,
          "name": "赵凯",
          "pic": "https://api.023ztjs.com/picture/510703198802291318.jpg"
        }
      ],
      "edit": 1,
      "agree": 0,
      "retreat": 0,
      "sign": 0,
      "cancel": 1,
      "print": 0
    }*/
    private int id;

    @JSONField(name = "tax_number")
    private String punishCode;

    @JSONField(name = "tax_publishtime")
    private String punishTime;
    /**
     * 选择的单位
     */
    @JSONField(name = "tax_unit")
    private String punishCompany;
    /**
     * 处罚金额
     */
    @JSONField(name = "tax_money")
    private int punishAmount;
    /**
     * 简介
     */
    @JSONField(name = "tax_detail")
    private String summary;

    /**
     * 详细内容
     */
    @JSONField(name = "tax_summary")
    private String content;
    @JSONField(name = "tax_status")
    private String punishState;

    @JSONField(name = "tax_eventid")
    private int safeEventId;
    @JSONField(name = "eventusername")
    private String safeEventUserName;
    @JSONField(name = "eventlocal")
    private String safeEventSite;
    @JSONField(name = "eventunit")
    private String safeEventCompany;
    @JSONField(name = "tax_userid")
    private int createUserId;
    /**
     * 创建用户名称
     */
    @JSONField(name = "taxusername")
    private String createUserName;
    /**
     * 创建用户头像
     */
    @JSONField(name = "taxuserpic")
    private String createUserPic;
    @JSONField(name = "tax_publishtime")
    private String createTime;

    /**
     * 安全监察编号 就是安全监察人的ID
     */
    @JSONField(name = "tax_safer")
    private int supervisionId;


    @JSONField(name = "saferusername")
    private String supervisionUserName;
    /**
     * 头像
     */
    @JSONField(name = "saferuserpic")
    private String supervisionUserPic;

    @JSONField(name = "tax_safersign")
    private String supervisionUserSignImg;
    @JSONField(name = "tax_safertime")
    private String supervisionSignTime;

    @JSONField(name = "tax_leader")
    private int punishLeaderId;
    @JSONField(name = "leaderusername")
    private String punishLeaderName;
    @JSONField(name = "leaderuserpic")
    private String punishLeaderPic;
    @JSONField(name = "tax_leadersign")
    private String punishLeadSign;
    @JSONField(name = "tax_leadertime")
    private String punishLeaderSignTime;


    /**
     * 查阅组
     */
    private List<PunishReadEntity> readerlist;


    private String gobacklist;
    /**
     * 退回理由
     */
    private List<PunishGoBackEntity> sendBackList;

    private List<PunishAtUserEntity> atuserlist;


    public int edit;
    @JSONField(name = "agree")
    public int consentStatius;
    @JSONField(name = "retreat")
    public int sendBackStatius;
    @JSONField(name = "sign")
    public int signStatius;
    @JSONField(name = "cancel")
    public int cancelStatus;
    @JSONField(name = "print")
    public int printStatus;

    public String getPunishCode() {
        return punishCode;
    }


    public String getPunishState() {
        return punishState;
    }

    public void setPunishState(String punishState) {
        this.punishState = punishState;
    }

    public void setPunishCode(String punishCode) {
        this.punishCode = punishCode;
    }

    public String getPunishCompany() {
        return punishCompany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(String punishTime) {
        this.punishTime = punishTime;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getSupervisionSignTime() {
        return supervisionSignTime;
    }

    public void setSupervisionSignTime(String supervisionSignTime) {
        this.supervisionSignTime = supervisionSignTime;
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

    public String getPunishLeaderSignTime() {
        return punishLeaderSignTime;
    }

    public void setPunishLeaderSignTime(String punishLeaderSignTime) {
        this.punishLeaderSignTime = punishLeaderSignTime;
    }

    public List<PunishReadEntity> getReaderlist() {
        return readerlist;
    }

    public void setReaderlist(List<PunishReadEntity> readerlist) {
        this.readerlist = readerlist;
    }

    public String getGobacklist() {
        return gobacklist;
    }

    public void setGobacklist(String gobacklist) {
        this.gobacklist = gobacklist;
    }

    public List<PunishGoBackEntity> getSendBackList() {
        if (sendBackList == null) {
            sendBackList = JsonUtil.getPersons(gobacklist, PunishGoBackEntity.class);
        }
        return sendBackList;
    }

    public void setSendBackList(List<PunishGoBackEntity> sendBackList) {
        this.sendBackList = sendBackList;
    }

    public List<PunishAtUserEntity> getAtuserlist() {
        return atuserlist;
    }

    public void setAtuserlist(List<PunishAtUserEntity> atuserlist) {
        this.atuserlist = atuserlist;
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
}
