package com.zhongtie.work.data;

import com.zhongtie.work.event.BaseEvent;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

public class CompanyTeamUserEntity implements BaseEvent {
    private String userName;
    private boolean isSelect;
    private boolean isAt;
    private int userID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
