package com.zhongtie.work.ui.rewardpunish.detail;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.safe.SafeCreateContract;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface RPDetailContract {
    interface View extends SafeCreateContract.View {

    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);

        void setSelectImageList(List<String> selectImgList);

        void setSelectUserInfoList(String title, List createUserEntities);

    }
}
