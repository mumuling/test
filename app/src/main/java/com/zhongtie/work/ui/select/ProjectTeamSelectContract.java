package com.zhongtie.work.ui.select;

import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.model.SafeSupervisionEnity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;
import com.zhongtie.work.ui.safesupervision.SafeSupervisionContract;

import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public interface ProjectTeamSelectContract {
    interface View extends BaseView {

        void setProjectTeamListData(List<ProjectTeamEntity> supervisionList);
    }

    interface Presenter extends BasePresenter<View> {
        void getProjectTeamListData();

        void searchProjectTeamList(String searchContent);

    }
}
