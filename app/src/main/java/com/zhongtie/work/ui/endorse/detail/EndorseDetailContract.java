package com.zhongtie.work.ui.endorse.detail;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;
import com.zhongtie.work.ui.safe.presenter.SafeCreateContract;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface EndorseDetailContract {
    interface View extends BaseView{
        /**
         *
         * @param itemList item数据源
         */
        void setItemList(List<Object> itemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);

        void setSelectImageList(List<String> selectImgList);

        void setSelectUserInfoList(String title, List createUserEntities);
    }
}
