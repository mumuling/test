package com.zhongtie.work.ui.main.presenter;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.data.LoginUserInfoEntity_Table;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.task.sync.SyncCompanyUtil;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.TextUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_NAME;
import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_PASSWORD;
import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_ID;
import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_NAME;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public class SplashPresenterImpl extends BasePresenterImpl<SplashContract.View> implements SplashContract.Presenter {
    @Override
    public void initSync() {
        mView.showSync();
        //同步
//        Http.netServer(SyncApi.class)
//                .createLocalData(1)
//                .compose(Network.networkIO())
//                .subscribe(new Consumer<Result<String>>() {
//                    @Override
//                    public void accept(Result<String> stringResult) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });

        addDispose(SyncCompanyUtil.syncCompanyList()
                .delay(1000, TimeUnit.MILLISECONDS)
                .flatMap(cacheCompanyTables -> {
                    //每次走一次登录接口验证 登录权限 所以打开APP必须联网
                    if (Cache.isUserLogin()) {
                        String cacheName = SharePrefUtil.getUserPre().getString(LOGIN_USER_NAME, "");
                        String pw = SharePrefUtil.getUserPre().getString(LOGIN_USER_PASSWORD, "");
                        if (TextUtil.isEmpty(cacheName) || TextUtil.isEmpty(pw)) {
                            return Flowable.just("");
                        }
                        return Http.netServer(UserApi.class).login(cacheName, pw).map(new NetWorkFunc1<>())
                                .map(s -> {
                                    LoginUserInfoEntity userInfoEntity = SQLite.select().from(LoginUserInfoEntity.class)
                                            .where(LoginUserInfoEntity_Table.id.eq(s)).querySingle();
                                    if (userInfoEntity != null) {
                                        SharePrefUtil.getUserPre().putString(SELECT_COMPANY_NAME, userInfoEntity.getCompanyname());
                                        SharePrefUtil.getUserPre().putInt(SELECT_COMPANY_ID, userInfoEntity.getCompany());
                                        App.getInstance().setUserInfo(userInfoEntity);
                                    }
                                    return s;
                                });
                    }
                    return Flowable.just("");
                })
                .compose(Network.networkIO())
                .subscribe(item -> {
                    if (!TextUtil.isEmpty(item)) {
                        mView.startMainView();
                    } else {
                        mView.userLogin();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.hideSync();
                    if (throwable.getMessage().equals("登录失败")) {
                        mView.userLogin();
                    } else {
                        mView.showRetryDialog();
                        mView.showToast(HttpException.getErrorMessage(throwable));
                    }
                }));
    }


}
