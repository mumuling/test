package com.zhongtie.work.ui.safe.presenter;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.db.SafeSupervisionEntity;
import com.zhongtie.work.model.EventCountData;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionPresenterImpl extends BasePresenterImpl<SafeSupervisionContract.View> implements SafeSupervisionContract.Presenter {
    @Override
    public void fetchPageList(String date, int type, int page) {
        int state = type - 1;
        List<SafeSupervisionEntity> safeSupervisionEnities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            safeSupervisionEnities.add(new SafeSupervisionEntity());
        }
        mView.setSafeSupervisionList(safeSupervisionEnities,type);
//        addDispose(Http.netServer(SafeApi.class)
//                .safeEventList(Cache.getUserID(), Cache.getSelectCompany(), date, state)
//                .compose(Network.convertIO())
//                .subscribe(safeSupervisionEntities -> mView.setSafeSupervisionList(safeSupervisionEntities, type), throwable -> {
//                    mView.fetchPageFail(type);
//                }));

    }

    @Override
    public void fetchInit() {
        getSafeDateEventCount();
    }

    @Override
    public void getSafeDateEventCount() {
        int company = Cache.getUserUserCompany();
        addDispose(Http.netServer(SafeApi.class)
                .safeEventListMonthCount(Cache.getUserID(), company)
                .map(new NetWorkFunc1<>())
                .compose(Network.netorkIO())
                .map(eventCountData -> {
                    HashMap<String, String> map = new HashMap<>(eventCountData.size());
                    for (int i = 0; i < eventCountData.size(); i++) {
                        EventCountData data = eventCountData.get(i);
                        map.put(data.getDay(), data.getCount() + "");
                    }
                    return map;
                })
                .compose(Network.netorkIO())
                .subscribe(eventCountData -> {
                    mView.setSafeEventCountList(eventCountData);
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }
}
