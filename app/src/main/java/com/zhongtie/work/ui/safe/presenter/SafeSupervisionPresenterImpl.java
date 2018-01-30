package com.zhongtie.work.ui.safe.presenter;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.model.EventCountData;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.Calendar;
import java.util.HashMap;

/**
 * date:2018.1.9
 *
 * @author Chaek
 */

public class SafeSupervisionPresenterImpl extends BasePresenterImpl<SafeSupervisionContract.View> implements SafeSupervisionContract.Presenter {
    @Override
    public void fetchPageList(String date, int type, int page) {
        int state = type - 1;
        //选择的公司
        int companyId = Cache.getSelectCompany();
        addDispose(Http.netServer(SafeApi.class)
                .safeEventList(Cache.getUserID(), companyId, date, state)
                .compose(Network.convertIO())
                .subscribe(safeSupervisionEntities -> mView.setSafeSupervisionList(safeSupervisionEntities, type), throwable -> {
                    mView.fetchPageFail(type);
                }));
    }

    @Override
    public void fetchInit() {
    }

    @Override
    public void getSafeDateEventCount() {

    }
}
