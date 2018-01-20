package com.zhongtie.work.data;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class EndorseUserEntity {

    /**
     * userid : 1294
     * username : 粟川
     * userpic : https://api.023ztjs.com/picture/510202198810101322.jpg
     * time : 2018-01-20 13:47:39
     * url : 20180120134739080844.png
     * detail : 你了哥哥哥哥哩哩啦啦
     * pic : 3395
     */

    public int userid;
    public String username;
    public String userpic;
    public String time;
    public String url;
    public String detail;
    public String pic;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
