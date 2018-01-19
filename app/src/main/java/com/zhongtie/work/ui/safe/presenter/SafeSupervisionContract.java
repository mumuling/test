package com.zhongtie.work.ui.safe.presenter;

import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface SafeSupervisionContract {
    interface View extends BaseView {

        void setSafeSupervisionList(List<SupervisorInfoEntity.SafeSupervisionEntity> supervisionList, int type);

        void setSafeEventCountList(HashMap<String, String> eventCountData);

        void fetchPageFail(int type);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchPageList(String date, int type, int page);

        void fetchInit();

        void getSafeDateEventCount();
    }
}
