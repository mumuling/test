package com.zhongtie.work.data.create;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全督导类别
 * Date: 2018/1/11
 *
 * @author Chaek
 */
public class SelectEventTypeItem {

    private String title;
    private List<EventTypeEntity> mTypeItemList;

    public SelectEventTypeItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EventTypeEntity> getTypeItemList() {
        return mTypeItemList;
    }

    public List<EventTypeEntity> getCheckEventTypeList() {
        List<EventTypeEntity> checkList = new ArrayList<>();
        for (EventTypeEntity data : mTypeItemList) {
            if (data.isCheck()) {
                checkList.add(data);
            }
        }
        return checkList;
    }

    public void setTypeItemList(List<EventTypeEntity> typeItemList) {
        mTypeItemList = typeItemList;
    }

    public String getCheckEventTypeString() {
        StringBuilder builder = new StringBuilder();
        for (EventTypeEntity data : mTypeItemList) {
            if (data.isCheck()) {
                builder.append(data.getCategory());
                builder.append(",");
            }
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - 1, builder.length());
        }
        return builder.toString();
    }
}
