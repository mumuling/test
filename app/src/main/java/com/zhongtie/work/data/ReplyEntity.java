package com.zhongtie.work.data;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class ReplyEntity {
    /**
     * userid : 729
     * username : 范建伟
     * userpic : https://api.023ztjs.com/picture/410304197406261551.jpg
     * time :
     * url :
     */

    public int userid;
    public String username;
    public String userpic;
    public String time;
    public String url;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
