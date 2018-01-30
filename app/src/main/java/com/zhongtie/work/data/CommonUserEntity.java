package com.zhongtie.work.data;

import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.event.BaseEvent;

import java.io.Serializable;

/**
 * 最基本的用户展示数据
 * Date: 2018/1/11
 *
 * @author Chaek
 */

public class CommonUserEntity implements BaseEvent, Serializable {
    private String userName;
    private String userPic;
    private int userId;
    private boolean isSelect;
    private boolean isAt;


    public CommonUserEntity(ApproveEntity review) {
        setUserId(review.userid);
        setUserPic(review.userpic);
        setUserName(review.username);
        setSelect(true);
    }

    public CommonUserEntity(RPRecordEntity rpRecordEntity) {
        setUserName(rpRecordEntity.getUserName());
        setUserId(rpRecordEntity.getUserID());
        setUserPic(rpRecordEntity.getUserPic());
        setSelect(true);
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isAt() {
        return isAt;
    }

    public void setAt(boolean at) {
        isAt = at;
    }


    public CommonUserEntity() {
    }

    public CommonUserEntity(String userName, String userPic, int userId) {
        this.userName = userName;
        this.userPic = userPic;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        if (userPic == null)
            return "";
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CommonUserEntity convertUser(CompanyUserData companyUserData) {
        userName = companyUserData.getName();
        userPic = companyUserData.getPhoto();
        userId = companyUserData.getId();
        return this;
    }

    @Override
    public String toString() {
        return userId + "";
    }
}
