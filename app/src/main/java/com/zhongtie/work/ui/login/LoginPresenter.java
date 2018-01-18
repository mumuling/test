package com.zhongtie.work.ui.login;

import android.text.TextUtils;

import com.zhongtie.work.app.App;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.sync.SyncCompanyUtil;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.SharePrefUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Function;


/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    public static final String LOGIN_USER_NAME = "login_user_name";
    public static final String LOGIN_USER_ID = "login_user_id";
    /**
     * 选择的公司ID
     */
    public static final String SELECT_COMPANY_ID = "login_user_company";
    /**
     * 切换的公司ID
     */
    public static final String SELECT_COMPANY_NAME = "login_user_company_name";
    /**
     * 登录用户的公司ID
     */
    public static final String USER_COMPANY_ID = "user_company";
    /**
     * 登录用户的公司名称
     */
    public static final String USER_COMPANY_NAME = "";

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

        addDispose(Http.netServer(UserApi.class).login(user, pw)
                .delay(500, TimeUnit.MILLISECONDS)
                .map(new NetWorkFunc1<>())
                .concatMap(userId -> Http.netServer(UserApi.class).userInfo(userId))
                .map(new NetWorkFunc1<>())
                .map(new SwitchUserCompany())
                .compose(Network.networkDialog(mView, "正在登录..."))
                .subscribe(data -> {
                            SharePrefUtil.getUserPre().putString(LOGIN_USER_NAME, user);
                            SharePrefUtil.getUserPre().putString(LOGIN_USER_ID, String.valueOf(data.getId()));
                            SharePrefUtil.getUserPre().putString(SELECT_COMPANY_NAME, data.companyname);
                            SharePrefUtil.getUserPre().putInt(SELECT_COMPANY_ID, data.getCompany());
                            SharePrefUtil.getUserPre().putString(USER_COMPANY_NAME, data.companyname);
                            SharePrefUtil.getUserPre().putInt(USER_COMPANY_ID, data.getCompany());
                            App.getInstance().setUserInfo(data);
                            mView.loginSuccess();
                        }
                        , throwable -> mView.loginFail()));
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
            int oldCompany = SharePrefUtil.getUserPre().getInt(SELECT_COMPANY_ID, 0);
            if (oldCompany != companyUserData.getCompany()) {
                SwitchCompanyUtil.changeCompany(companyUserData.getCompany());
            }
            //登录则开始遍历数据库 确定是否是领导组
            SyncCompanyUtil.findUserLeaderGroup(Integer.valueOf(companyUserData.getId()));

            return companyUserData;
        }
    }

}
