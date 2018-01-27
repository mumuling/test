package com.zhongtie.work.ui.rewardpunish;

import com.zhongtie.work.data.SupervisorInfoEntity;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/16
 */

class PresenterImpl extends BasePresenterImpl<RewardPunishContract.View> implements RewardPunishContract.Presenter {
    @Override
    public void fetchPageList(String date, int type, int page) {
        List<SupervisorInfoEntity> entityList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entityList.add(new SupervisorInfoEntity());
        }
        mView.setSafeSupervisionList(entityList);
    }

    @Override
    public void fetchInit() {

    }
}
