package com.zhongtie.work.ui.rewardpunish.presenter;

import android.support.annotation.StringRes;

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

        void showStatusView(int edit, int agree, int retreat, int sign, int cancel);

        void showPrint(int print);

        void consentPunishSuccess();

        void sendBackSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void getDetailInfo(int safeOrderID);


        /**
         * 同意
         */
        void consentPunish(String signPath);

        /**
         * 退回
         *
         * @param content 理由
         */
        void sendBackPunish(String signPath, String content);

        /**
         * 作废
         */
        void cancellationPunish(String signPath);

        void signPunish(String signPath);
    }
}
