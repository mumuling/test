package com.zhongtie.work.data.create;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class EventTypeEntity {
    private String category;
    private int categoryId;
    private boolean isCheck;


    public EventTypeEntity(String category, int categoryId, boolean isCheck) {
        this.category = category;
        this.categoryId = categoryId;
        this.isCheck = isCheck;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

}
