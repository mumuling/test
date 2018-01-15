package com.zhongtie.work.data;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

public class KeyValueEntity<T, R> {
    private T title;
    private R content;


    public KeyValueEntity(T title, R content) {
        this.title = title;
        this.content = content;
    }

    public T getTitle() {
        return title;
    }

    public void setTitle(T title) {
        this.title = title;
    }

    public R getContent() {
        return content;
    }

    public void setContent(R content) {
        this.content = content;
    }
}
