package com.zhongtie.work.data.create;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CreateUserEntity {
    private String userName;
    private String userPic;
    private int userId;

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
