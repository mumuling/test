package com.zhongtie.work.ui.main;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.sync.SyncUserPic;
import com.zhongtie.work.task.SyncOfficeEventTask;
import com.zhongtie.work.task.SyncWrongTask;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.sync.SyncCompanyUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_ID;
import static com.zhongtie.work.ui.login.LoginPresenter.SELECT_COMPANY_NAME;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

public class MainPresenterImpl extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    @Override
    public void fetchInitData() {
        if (App.getInstance().getUser() != null) {
            LoginUserInfoEntity userInfoEntity = App.getInstance().getUser();
            String company = SharePrefUtil.getUserPre().getString(SELECT_COMPANY_NAME, "");
            mView.setUserCompany(company);
            mView.setUserInfo(userInfoEntity);
            fetchCompanyList();
            syncUserPic();
            SyncOfficeEventTask.execute();
            SyncWrongTask.execute();
        }
    }

    private void syncUserPic() {
        SyncUserPic.getInstance().startDownload();
    }


    @Override
    public void switchSelectCompany(CompanyEntity companyEntity) {
        int companyId = SharePrefUtil.getUserPre().getInt(SELECT_COMPANY_ID, 0);
        if (companyId != companyEntity.getId()) {
            SwitchCompanyUtil.switchCompany(companyEntity.getId())
                    .map(s -> {
                        FlowManager.init(App.getInstance());
                        return "";
                    })
                    .delay(500, TimeUnit.MILLISECONDS)
                    .compose(Network.networkDialog(mView, "正在切换公司"))
                    .subscribe(s -> {
                        SharePrefUtil.getUserPre().putString(SELECT_COMPANY_NAME, companyEntity.getName());
                        SharePrefUtil.getUserPre().putInt(SELECT_COMPANY_ID, companyEntity.getId());
                        mView.setUserCompany(companyEntity.getName());
                    }, throwable -> mView.showToast("切换失败"));
        }
    }

    private void fetchCompanyList() {
        addDispose(SyncCompanyUtil.syncCompanyList()
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
