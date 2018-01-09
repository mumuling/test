package com.zhongtie.work.ui.safesupervision;

import com.zhongtie.work.model.SafeSupervisionEnity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface SafeSupervisionContract {
    interface View extends BaseView {

        void setSafeSupervisionList(List<SafeSupervisionEnity> supervisionList);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchPageList(String date, int type, int page);
    }
}
