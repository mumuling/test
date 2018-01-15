package com.zhongtie.work.event;

import com.zhongtie.work.data.ProjectTeamEntity;

/**
 * Auth: Chaek
 * Date: 2018/1/15
 */

public class SelectCompanyEvent implements BaseEvent {
    private int type;
    private ProjectTeamEntity data;

    public SelectCompanyEvent(int type, ProjectTeamEntity data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ProjectTeamEntity getData() {
        return data;
    }

    public void setData(ProjectTeamEntity data) {
        this.data = data;
    }
}
