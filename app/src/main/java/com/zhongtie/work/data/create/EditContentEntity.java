package com.zhongtie.work.data.create;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class EditContentEntity {
    private String title;
    private String hint;
    private String content;

    public EditContentEntity(String title, String hint, String content) {
        this.title = title;
        this.hint = hint;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
