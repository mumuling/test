package com.zhongtie.work.ui.main;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.CompanyEntity_Table;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.SwitchCompanyUtil;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UserApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.SharePrefUtil;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.widget.DownloadManager;

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
        }
    }

    @Override
    public void switchSelectCompany(CompanyEntity companyEntity) {
        int companyId = SharePrefUtil.getUserPre().getInt(LOGIN_USER_COMPANY, 0);
        if (companyId != companyEntity.getId()) {
            SwitchCompanyUtil.switchCompany(companyEntity.getId())
                    .map(s -> {
//                        FlowManager.destroy();
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
                        companyEntity.update();
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
                .compose(Network.netorkIO())
                .subscribe(lists -> {
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.showToast(HttpException.getErrorMessage(throwable));
                }));

        Flowable.fromCallable(() -> SQLite.select().from(CompanyEntity.class).queryList()).compose(Network.netorkIO())
                .subscribe(companyEntities -> mView.setAllCompanyList(companyEntities), throwable -> {
                });


    }
}
