package com.zhongtie.work.ui.safe.presenter;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface SafeCreateContract {
    interface View extends BaseView {

        void setItemList(List<Object> itemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);

        void setSelectImageList(List<String> selectImgList);

        void setSelectUserInfoList(String title, List createUserEntities);

        void createSafeOrder();
    }
}
