package com.zhongtie.work.data.create;

import com.zhongtie.work.event.BaseEvent;

import java.io.Serializable;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CreateUserEntity implements BaseEvent, Serializable {
    private String userName;
    private String userPic;
    private int userId;
    private boolean isSelect;
    private boolean isAt;
    private int userID;

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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public CreateUserEntity() {
    }

    public CreateUserEntity(String userName, String userPic, int userId) {
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
}
