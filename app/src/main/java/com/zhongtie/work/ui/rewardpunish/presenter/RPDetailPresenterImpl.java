package com.zhongtie.work.ui.rewardpunish.presenter;


import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.Result;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.RewardPunishApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.upload.UploadUtil;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * @author Chaek
 * @date: 2018/1/12
 */
public class RPDetailPresenterImpl extends BasePresenterImpl<RPDetailContract.View> implements RPDetailContract.Presenter {

    private List<Object> mPunishItemList;

    private int mPunishId;
    private boolean isInit;

    /**
     * 获取安全奖惩详细信息
     *
     * @param punishId 安全处罚编号
     */
    @Override
    public void getDetailInfo(int punishId) {
        this.mPunishId = punishId;
        if (!isInit) {
            mView.initLoading();
        }
        addDispose(fetchPunishDetailFlowable()
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(Network.networkIO())
                .subscribe(this::showViewData, throwable -> {
                    throwable.printStackTrace();
                    mView.initFail();
                }));
    }

    private void showViewData(RewardPunishDetailEntity data) {
        isInit = true;
        mView.setItemList(mPunishItemList);
        mView.setHeadTitle(data);
        mView.showStatusView(data.edit, data.consentStatius, data.sendBackStatius, data.signStatius, data.cancelStatus);
        mView.showPrint(data.printStatus);
        mView.initSuccess();
    }

    private Flowable<RewardPunishDetailEntity> fetchPunishDetailFlowable() {
        return Http.netServer(RewardPunishApi.class)
                .punishDetails(Cache.getUserID(), mPunishId)
                .map(new NetWorkFunc1<>())
                .map(new PunishDetailFun());
    }


    /**
     * 同意
     *
     * @param signPath 签名地址
     */
    @Override
    public void consentPunish(String signPath) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).consentPunish(Cache.getUserID(), mPunishId, img.getPicname()))
                .flatMap(stringResult -> fetchPunishDetailFlowable())
                .compose(Network.networkDialog(mView))
                .subscribe(data -> {
                    showViewData(data);
                    mView.showToast(R.string.punish_change_success);
                    mView.consentPunishSuccess();
                }, Throwable::printStackTrace));
    }

    /**
     * 退回
     *
     * @param signPath 签名路径
     * @param content  理由
     */
    @Override
    public void sendBackPunish(String signPath, String content) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).sendBackPunish(Cache.getUserID(), mPunishId, img.getPicname(), content))
                .flatMap(stringResult -> fetchPunishDetailFlowable())
                .compose(Network.networkDialog(mView))
                .subscribe(data -> {
                    showViewData(data);
                    mView.showToast(R.string.punish_change_success);
                    mView.sendBackSuccess();
                }, Throwable::printStackTrace));
    }


    /**
     * 作废
     *
     * @param signPath 签名地址
     */
    @Override
    public void cancellationPunish(String signPath) {
        addDispose(Http.netServer(RewardPunishApi.class).cancelPunish(Cache.getUserID(), mPunishId)
                .flatMap(stringResult -> fetchPunishDetailFlowable())
                .compose(Network.networkDialog(mView))
                .subscribe(data -> {
                    showViewData(data);
                    mView.showToast(R.string.punish_change_success);
                }, Throwable::printStackTrace));
    }

    /**
     * 同意处罚
     *
     * @param signPath 签名路径
     */
    @Override
    public void signPunish(String signPath) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).signPunish(Cache.getUserID(), mPunishId, img.getPicname()))
                .flatMap(stringResult -> fetchPunishDetailFlowable())
                .compose(Network.networkDialog(mView))
                .subscribe(data -> {
                    showViewData(data);
                    mView.showToast(R.string.punish_change_success);
                }, throwable -> {
                }));

    }

    private class PunishDetailFun implements Function<RewardPunishDetailEntity, RewardPunishDetailEntity> {

        @Override
        public RewardPunishDetailEntity apply(RewardPunishDetailEntity data) throws Exception {
            mPunishItemList = new ArrayList<>();
            EditContentEntity rectifyEditContent = new EditContentEntity("摘要", "", data.getSummary());
            mPunishItemList.add(rectifyEditContent);
            EditContentEntity describeEditContent = new EditContentEntity("详细情况", "", data.getContent());
            mPunishItemList.add(describeEditContent);
            TransformationPunishModel transModel = new TransformationPunishModel(data);
            mPunishItemList.add(transModel.fetchPunishUserItem());
            mPunishItemList.add(transModel.fetchPunishLeaderItem());
            mPunishItemList.add(transModel.fetchReadList());
            return data;
        }
    }


}
