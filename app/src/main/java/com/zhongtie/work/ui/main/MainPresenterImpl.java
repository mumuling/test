package com.zhongtie.work.ui.main;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.CompanyEntity;
import com.zhongtie.work.data.LoginUserInfoEntity;
import com.zhongtie.work.db.ZhongtieDb;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.Api;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Auth:Cheek
 * date:2018.1.14
 */

public class MainPresenterImpl extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    @Override
    public void fetchInitData() {
        if (App.getInstance().getUser() != null) {
            LoginUserInfoEntity userInfoEntity = App.getInstance().getUser();
            mView.setUserCompany(userInfoEntity.getCompanyname());
            mView.setUserInfo(userInfoEntity);
            fetchCompanyList();

        }


    }

    private void fetchCompanyList() {

        Flowable<List<CompanyEntity>> listFlowable = Http.createRetroService(Api.class).fetchCompanyList(0)
                .map(new NetWorkFunc1<>())
                .map(companyEntities -> {
                    FlowManager.getDatabase(ZhongtieDb.class).executeTransaction(databaseWrapper ->
                            FastStoreModelTransaction.saveBuilder(FlowManager.getModelAdapter(CompanyEntity.class)).
                                    addAll(companyEntities).build().execute(databaseWrapper));
                    return companyEntities;
                });
        Flowable<List<CompanyEntity>> cacheList = Flowable.fromCallable(() -> SQLite.select().from(CompanyEntity.class).queryList());

        Flowable.concat(cacheList, listFlowable)
                .compose(Network.netorkIO())
                .subscribe(new Consumer<List<CompanyEntity>>() {
                    @Override
                    public void accept(List<CompanyEntity> companyEntities) throws Exception {
                        mView.setAllCompanyList(companyEntities);
                    }
                }, throwable -> {
                });


    }
}
