package com.zhongtie.work.ui.endorse;

import com.zhongtie.work.data.EndorseEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

public interface EndorseListContract {
    interface View extends BaseView {

        void setSafeSupervisionList(List<EndorseEntity> supervisionList);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchPageList(String date, int type, int page);
    }
}