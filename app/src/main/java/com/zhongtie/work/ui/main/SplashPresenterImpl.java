package com.zhongtie.work.ui.main;

import android.support.annotation.WorkerThread;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.data.LoginUserInfoEntity_Table;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.SyncUtil;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.widget.DownloadManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_COMPANY;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public class SplashPresenterImpl extends BasePresenterImpl<SplashContract.View> implements SplashContract.Presenter {
    @Override
    public void initSync() {
        mView.showSync();
        //同步
        addDispose(SyncUtil.syncCompanyList()
                .delay(1000, TimeUnit.MILLISECONDS)
                .onErrorReturn(throwable -> new ArrayList<>())
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

    /**
     * 下载 最新的数据库
     * 如果下载的是本地选择的最新的数据库
     *
     * @param companyEntity 公司信息
     */
    @WorkerThread
    private void downCompanyDB(CompanyEntity companyEntity) {
        if (!TextUtil.isEmpty(companyEntity.getDburl())) {
            L.e("-----------", "正在下载" + companyEntity.getDburl());
            DownloadManager.getInstance().download(companyEntity, null);
            int companyId = SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
            if (companyId == companyEntity.getId()) {
                SwitchCompanyUtil.switchCompany(companyEntity.getId())
                        .map(s -> {
                            FlowManager.init(App.getInstance());
                            return "";
                        }).blockingFirst();
            }
        }
    }
}
