package com.zhongtie.work.ui.login;

import android.text.TextUtils;

import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.Api;
import com.zhongtie.work.ui.base.BasePresenterImpl;


/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login() {
        String user = mView.getUserPhone();
        String pw = mView.getUserPassword();

        if (TextUtils.isEmpty(user)) {
            mView.showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            mView.showToast("请输入登录密码");
            return;
        }

        addDispose(Http.createRetroService(Api.class).login(user, pw)
                .compose(Network.networkDialog(mView))
                .subscribe(s -> mView.loginSuccess()
                        , throwable -> mView.loginFail()));
    }


}
