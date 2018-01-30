package com.zhongtie.work.ui.rewardpunish.presenter;

import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.HashMap;
import java.util.List;

public interface RewardPunishContract {
    interface View extends BaseView {


        void setPunishList(List<RewardPunishEntity> list);

        void loadListFail();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchPageList(String date, int type, int page);

        void fetchInit();
    }
}