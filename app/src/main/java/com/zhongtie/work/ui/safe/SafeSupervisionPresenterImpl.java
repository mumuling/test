package com.zhongtie.work.ui.safe;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.db.SafeSupervisionEntity;
import com.zhongtie.work.model.EventCountData;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionPresenterImpl extends BasePresenterImpl<SafeSupervisionContract.View> implements SafeSupervisionContract.Presenter {
    @Override
    public void fetchPageList(String date, int type, int page) {
        List<SafeSupervisionEntity> safeSupervisionEnities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            safeSupervisionEnities.add(new SafeSupervisionEntity());
        }
        mView.setSafeSupervisionList(safeSupervisionEnities);
    }

    @Override
    public void fetchInit() {
        int company=Cache.getUserUserCompany();
        Http.netServer(SafeApi.class)
                .safeEventListMonthCount(Cache.getUserID(),company)
                .map(new NetWorkFunc1<>())
                .compose(Network.netorkIO())
                .subscribe(new Consumer<List<EventCountData>>() {
                    @Override
                    public void accept(List<EventCountData> eventCountData) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


    }
}
