package com.zhongtie.work.ui.main;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.CacheAddWrongTable;
import com.zhongtie.work.db.CacheAddWrongTable_Table;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.SyncUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_COMPANY;
import static com.zhongtie.work.ui.login.LoginPresenter.LOGIN_USER_COMPANY_NAME;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

public class MainPresenterImpl extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    @Override
    public void fetchInitData() {
        if (App.getInstance().getUser() != null) {
            LoginUserInfoEntity userInfoEntity = App.getInstance().getUser();
            String company = SharePrefUtil.getUserPre().getString(LOGIN_USER_COMPANY_NAME, "");
            mView.setUserCompany(company);
            mView.setUserInfo(userInfoEntity);
            fetchCompanyList();
            syncUserWrongLocalData();
        }
    }

    /**
     * 上传本地未上传违规
     */
    private void syncUserWrongLocalData() {
        Flowable.fromCallable(() -> SQLite.select().from(CacheAddWrongTable.class).where(CacheAddWrongTable_Table.status.eq(0)).queryList())
                .flatMap(Flowable::fromIterable)
                .flatMap(cacheAddWrongTable -> Http.netServer(UserApi.class)
                        .addWrong(cacheAddWrongTable.getUserId(), cacheAddWrongTable.getByUserId() + "", cacheAddWrongTable.getContent())
                        .map(integerResult -> {
                            cacheAddWrongTable.setAddWrongId(integerResult.getData());
                            cacheAddWrongTable.setStatus(1);
                            cacheAddWrongTable.save();
                            return integerResult.getCode();
                        }))
                .toList()
                .toFlowable()
                .compose(Network.netorkIO())
                .subscribe(objects -> {
                }, throwable -> {
                });


    }

    @Override
    public void switchSelectCompany(CompanyEntity companyEntity) {
        int companyId = SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
        if (companyId != companyEntity.getId()) {
            SwitchCompanyUtil.switchCompany(companyEntity.getId())
                    .map(s -> {
                        FlowManager.init(App.getInstance());
                        return "";
                    })
                    .delay(500, TimeUnit.MILLISECONDS)
                    .compose(Network.networkDialog(mView, "正在切换公司"))
                    .subscribe(s -> {
                        SharePrefUtil.getUserPre().putString(LOGIN_USER_COMPANY_NAME, companyEntity.getName());
                        SharePrefUtil.getUserPre().putInt(LOGIN_USER_COMPANY, companyEntity.getId());
                        mView.setUserCompany(companyEntity.getName());
                    }, throwable -> mView.showToast("切换失败"));
        }
    }

    private void fetchCompanyList() {
        addDispose(SyncUtil.syncCompanyList()
                .compose(Network.netorkIO())
                .subscribe(lists -> mView.onSyncCompanySuccess(), throwable -> {
                    throwable.printStackTrace();
                    mView.onSyncCompanyFail();
                    mView.showToast(HttpException.getErrorMessage(throwable));
                }));

        Flowable.fromCallable(() -> SQLite.select().from(CompanyEntity.class).queryList()).compose(Network.netorkIO())
                .subscribe(companyEntities -> mView.setAllCompanyList(companyEntities), throwable -> {
                });
    }


}
