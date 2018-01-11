package com.zhongtie.work.data;

import com.zhongtie.work.data.create.CreateUserEntity;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.11
 */

public class CompanyTeamEntity {
    private String teamName;
    private boolean isSelect;
    private boolean isExpansion;
    private boolean isAt;

    public boolean isAt() {
        return isAt;
    }

    public void setAt(boolean at) {
        isAt = at;
    }

    private List<CreateUserEntity> teamUserEntities;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<CreateUserEntity> getTeamUserEntities() {
        return teamUserEntities;
    }

    public void setTeamUserEntities(List<CreateUserEntity> teamUserEntities) {
        this.teamUserEntities = teamUserEntities;
    }

    public boolean isExpansion() {
        return isExpansion;
    }

    public void setExpansion(boolean expansion) {
        isExpansion = expansion;
    }
}
