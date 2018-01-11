package com.zhongtie.work.ui.select;

import java.util.Comparator;

import com.zhongtie.work.data.ProjectTeamEntity;

public class PinyinComparator implements Comparator<ProjectTeamEntity> {
    @Override
    public int compare(ProjectTeamEntity o1, ProjectTeamEntity o2) {
        if (o1.getCharacter().equals("@") || o2.getCharacter().equals("#")) {
            return -1;
        } else if (o1.getCharacter().equals("#")
                || o2.getCharacter().equals("@")) {
            return 1;
        } else {
            return o1.getCharacter().compareTo(o2.getCharacter());
        }
    }


}
