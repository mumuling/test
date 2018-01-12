package com.zhongtie.work.ui.rewardpunish;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface RewardPunishCreateContract {
    interface View extends BaseView {

        void setItemList(List<Object> itemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);


        void setSelectUserInfoList(String title, List createUserEntities);
    }
}
