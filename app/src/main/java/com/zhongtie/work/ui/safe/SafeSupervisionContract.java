package com.zhongtie.work.ui.safe;

import com.zhongtie.work.db.SafeSupervisionEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface SafeSupervisionContract {
    interface View extends BaseView {

        void setSafeSupervisionList(List<SafeSupervisionEntity> supervisionList);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchPageList(String date, int type, int page);

        void fetchInit();
    }
}
