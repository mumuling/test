package com.zhongtie.work.ui.rewardpunish.detail;

import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface RPDetailContract {
    interface View extends BaseView {
        /**
         * 设置安全督导显示的ITEM
         *
         * @param itemList item数据源
         */
        void setItemList(List<Object> itemList);

        void setHeadTitle(RewardPunishDetailEntity detailEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void getDetailInfo(int safeOrderID);

    }
}
