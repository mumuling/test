package com.zhongtie.work.ui.rewardpunish;

import com.zhongtie.work.db.SafeSupervisionEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.HashMap;
import java.util.List;

public interface RewardPunishContract {
    interface View extends BaseView {

        void setSafeSupervisionList(List<SafeSupervisionEntity> supervisionList);

        void setSafeEventCountList(HashMap<String, String> eventCountData);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchPageList(String date, int type, int page);

        void fetchInit();
    }
}