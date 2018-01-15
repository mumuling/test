package com.zhongtie.work.ui.main;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public interface SplashContract {

    interface View extends BaseView {

        void showSync();

        void userLogin();

        void startMainView();

    }

    interface Presenter extends BasePresenter<View> {

        void initSync();

    }
}
