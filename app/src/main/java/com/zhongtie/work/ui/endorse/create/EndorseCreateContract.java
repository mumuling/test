package com.zhongtie.work.ui.endorse.create;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public interface EndorseCreateContract {
    interface View extends BaseView {

        void setItemList(List<Object> itemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getItemList(int safeOrderID);


        void setSelectUserInfoList(String title, List createUserEntities);
    }
}
