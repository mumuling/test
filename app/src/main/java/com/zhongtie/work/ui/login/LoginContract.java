package com.zhongtie.work.ui.login;

import com.zhongtie.work.ui.base.BasePresenter;
import com.zhongtie.work.ui.base.BaseView;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public interface LoginContract {
    interface View extends BaseView {

        void loginSuccess();

        void loginFail();

        String getUserPhone();

        String getUserPassword();
    }

    interface Presenter extends BasePresenter<View> {
        void login();
    }
}
