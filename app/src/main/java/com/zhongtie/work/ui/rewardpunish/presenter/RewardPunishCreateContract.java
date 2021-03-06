package com.zhongtie.work.ui.rewardpunish.presenter;

import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.SelectSafeEventEntity;
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

        /**
         * 获取输入的处罚金额
         *
         * @return 处罚金额
         */
        int getPunishAmount();

        /**
         * 处罚单位
         *
         * @return 选择的单位名称
         */
        ProjectTeamEntity getPunishUnit();

        /**
         * 获取输入的地址
         *
         * @return 地址
         */
        String getCreateCode();

        /**
         * 选择对应的安全督导信息
         *
         * @return 督导信息
         */
        SelectSafeEventEntity getSafeEventData();

        void createSuccess();

        void setHeadEditInfo(RewardPunishDetailEntity data);
    }

    interface Presenter extends BasePresenter<View> {
        void getRewardPunishItemList(int safeOrderID);

        /**
         * 创建或编辑安全奖罚
         */
        void createRewardPunish();

        void setSelectUserInfoList(String title, List createUserEntities);
    }
}
