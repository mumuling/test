package com.zhongtie.work.data;

import com.zhongtie.work.event.BaseEvent;

import java.io.Serializable;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

public class TeamNameEntity implements BaseEvent, Serializable {

    private String teamName;
    private int teamId;
    private boolean isSelect;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
