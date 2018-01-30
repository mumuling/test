package com.zhongtie.work.ui.rewardpunish.presenter;

import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.Result;
import com.zhongtie.work.data.RewardPunishEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.HttpException;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.RewardPunishApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author Chaek
 * @date: 2018/1/16
 */

public class RewardPunishListPresenterImpl extends BasePresenterImpl<RewardPunishContract.View> implements RewardPunishContract.Presenter {
    @Override
    public void fetchPageList(String date, int type, int page) {
        addDispose(getListFlowable(date, type)
                .map(new NetWorkFunc1<>())
                .compose(Network.networkIO())
                .subscribe(list -> {
                    mView.setPunishList(list);
                }, throwable -> {
                    mView.showToast(HttpException.getErrorMessage(throwable));
                    mView.loadListFail();
                }));
    }

    private Flowable<Result<List<RewardPunishEntity>>> getListFlowable(String date, int type) {
        String dates[] = date.split("-");
        String year = dates[0];
        String month = dates[1];
        if (type == 1) {
            return Http.netServer(RewardPunishApi.class).punishMeReadList(Cache.getUserID(), Cache.getSelectCompany(), year, month);
        }
        return Http.netServer(RewardPunishApi.class).punishMeCreateList(Cache.getUserID(), Cache.getSelectCompany(), year, month);
    }

    @Override
    public void fetchInit() {


    }
}
