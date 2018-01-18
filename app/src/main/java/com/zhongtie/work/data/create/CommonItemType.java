package com.zhongtie.work.data.create;

import com.alibaba.fastjson.JSON;
import com.zhongtie.work.data.TeamNameEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一列表type
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CommonItemType<T> {

    private String title;
    private String hint;
    private int icon;
    private boolean isEdit;
    private List<T> mTypeItemList;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }


    public CommonItemType(String title, String hint, int icon, boolean isEdit) {
        this.title = title;
        this.hint = hint;
        this.icon = icon;
        this.isEdit = isEdit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<T> getTypeItemList() {
        if (mTypeItemList == null) {
            mTypeItemList = new ArrayList<>();
        }
        return mTypeItemList;
    }

    public String getSelectUserIDList() {
        StringBuilder builder = new StringBuilder();
        List<String> strings=new ArrayList<>();
        for (T data : mTypeItemList) {
            CreateUserEntity userEntity = (CreateUserEntity) data;
            builder.append(userEntity.getUserId());
            builder.append(",");
            strings.add(userEntity.getUserId()+"");
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - 1, builder.length());
        }


        return builder.toString();
    }

    public String getTeamIDList() {
        StringBuilder builder = new StringBuilder();
        for (T data : mTypeItemList) {
            TeamNameEntity userEntity = (TeamNameEntity) data;
            builder.append(userEntity.getTeamId());
            builder.append(",");
        }
        if (builder.length() > 0)
            builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }


    public void setTypeItemList(List<T> typeItemList) {
        mTypeItemList = typeItemList;
    }
}
