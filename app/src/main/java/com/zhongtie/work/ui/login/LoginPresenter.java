package com.zhongtie.work.ui.login;

import android.text.TextUtils;

import com.zhongtie.work.app.App;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.SharePrefUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    public static final String LOGIN_USER_NAME = "login_user_name";
    public static final String LOGIN_USER_ID = "login_user_id";
    public static final String LOGIN_USER_COMPANY = "login_user_company";
    public static final String LOGIN_USER_COMPANY_NAME = "login_user_company_name";

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

//        addDispose(Http.netSetver(Api.class).login(user, pw)
//                .delay(500, TimeUnit.MILLISECONDS)
//                .map(new NetWorkFunc1<>())
//                .concatMap(userId -> Http.netSetver(Api.class).userInfo(userId))
//                .compose(Network.networkDialog(mView, "正在登录..."))
//                .map(new SwitchUserCompany())
//                .compose(Network.netorkIO())
//                .subscribe(data -> {
//                            SharePrefUtil.getUserPre().putString(LOGIN_USER_NAME, user);
//                            SharePrefUtil.getUserPre().putString(LOGIN_USER_ID, String.valueOf(data.getId()));
//                            SharePrefUtil.getUserPre().putInt(LOGIN_USER_COMPANY, data.getCompany());
//                            mView.loginSuccess();
//                        }
//                        , throwable -> {
//                        }));
        addDispose(Flowable.just("1")
                .delay(500, TimeUnit.MILLISECONDS)
                .concatMap(userId -> Http.netSetver(UserApi.class).userInfo(userId))
                .map(new NetWorkFunc1<>())
                .map(new SwitchUserCompany())
                .compose(Network.networkDialog(mView, "正在登录..."))
                .subscribe(data -> {
                            SharePrefUtil.getUserPre().putString(LOGIN_USER_NAME, user);
                            SharePrefUtil.getUserPre().putString(LOGIN_USER_ID, String.valueOf(data.getId()));
                            SharePrefUtil.getUserPre().putString(LOGIN_USER_COMPANY_NAME, data.companyname);
                            SharePrefUtil.getUserPre().putInt(LOGIN_USER_COMPANY, data.getCompany());
                            App.getInstance().setUserInfo(data);
                            mView.loginSuccess();
                        }
                        , throwable -> {
                            mView.loginFail();
                        }));
    }

    @Override
    public void fetchCacheUserName() {
        String cacheName = SharePrefUtil.getUserPre().getString(LOGIN_USER_NAME, "");
        mView.setLastLoginUserName(cacheName);
    }

    /**
     * 切换公司数据
     */
    private class SwitchUserCompany implements Function<LoginUserInfoEntity, LoginUserInfoEntity> {
        @Override
        public LoginUserInfoEntity apply(LoginUserInfoEntity companyUserData) throws Exception {
            companyUserData.save();
            int oldCompany = SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
            if (oldCompany != companyUserData.getCompany()) {
                new SwitchCompanyUtil().changeCompany(companyUserData.getCompany());
            }
            return companyUserData;
        }
    }

}
