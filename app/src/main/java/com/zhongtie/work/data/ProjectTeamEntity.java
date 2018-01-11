package com.zhongtie.work.data;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class ProjectTeamEntity {
    private String projectTeamName;
    private String character;
    private int projectTeamID;

    public ProjectTeamEntity(String projectTeamName) {
        this.projectTeamName = projectTeamName;
    }

    public String getProjectTeamName() {
        return projectTeamName;
    }

    public void setProjectTeamName(String projectTeamName) {
        this.projectTeamName = projectTeamName;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getProjectTeamID() {
        return projectTeamID;
    }

    public void setProjectTeamID(int projectTeamID) {
        this.projectTeamID = projectTeamID;
    }
}
