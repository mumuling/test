package com.zhongtie.work.ui.main;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.CompanyEntity_Table;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.data.LoginUserInfoEntity_Table;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.widget.DownloadManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public class SplashPresenterImpl extends BasePresenterImpl<SplashContract.View> implements SplashContract.Presenter {
    @Override
    public void initSync() {
        mView.showSync();
        //同步
        addDispose(Http.netServer(UserApi.class).fetchCompanyList(0)
                .map(new NetWorkFunc1<>())
                .flatMap(Flowable::fromIterable)
                .map(companyEntity -> {
                    CompanyEntity company = SQLite.select().from(CompanyEntity.class).where(CompanyEntity_Table.id.eq(companyEntity.getId())).querySingle();
                    if (company != null) {
                        long oldTime = TimeUtils.converter(company.getDbupdatetime());
                        long newTime = TimeUtils.converter(companyEntity.getDbupdatetime());
                        if (newTime > oldTime) {
                            if (!TextUtil.isEmpty(companyEntity.getDburl())) {
                                L.e("-----------", "正在下载" + companyEntity.getDburl());
                                DownloadManager.getInstance().download(companyEntity, null);
                            }
                        }
                        companyEntity.save();
                    } else {
                        if (!TextUtil.isEmpty(companyEntity.getDburl())) {
                            L.e("-----------", "正在下载" + companyEntity.getDburl());
                            DownloadManager.getInstance().download(companyEntity, null);
                        }
                        companyEntity.save();
                    }
                    return companyEntity;
                }).toList()
                .toFlowable()
                .delay(500, TimeUnit.MILLISECONDS)
                .map(companyEntities -> {
                    if (Cache.isUserLogin()) {
                        LoginUserInfoEntity userInfoEntity = SQLite.select().from(LoginUserInfoEntity.class)
                                .where(LoginUserInfoEntity_Table.id.eq(Cache.getUserID())).querySingle();
                        App.getInstance().setUserInfo(userInfoEntity);
                        return "";
                    }
                    return "";
                })
                .compose(Network.netorkIO())
                .subscribe(item -> {
                    if (Cache.isUserLogin()) {
                        mView.startMainView();
                    } else {
                        mView.userLogin();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.showToast(HttpException.getErrorMessage(throwable));
                }));


    }
}
