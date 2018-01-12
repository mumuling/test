package com.zhongtie.work.data.create;

import java.util.List;

/**
 * 类别
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CreateTypeItem {

    private String title;
    private List<CategoryData> mTypeItemList;

    public CreateTypeItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CategoryData> getTypeItemList() {
        return mTypeItemList;
    }

    public void setTypeItemList(List<CategoryData> typeItemList) {
        mTypeItemList = typeItemList;
    }
}
