package com.zhongtie.work.ui.rewardpunish.presenter;


import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.RewardPunishApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Chaek
 * @date: 2018/1/12
 */
public class RPDetailPresenterImpl extends BasePresenterImpl<RPDetailContract.View> implements RPDetailContract.Presenter {

    /**
     * 描述编辑数据
     */
    private EditContentEntity mDescribeEditContent;
    /**
     * 整改内容
     */
    private EditContentEntity mRectifyEditContent;

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
        addDispose(Http.netServer(RewardPunishApi.class)
                .punishDetails(Cache.getUserID(), punishId)
                .map(new NetWorkFunc1<>())
                .map(data -> {
                    mPunishItemList = new ArrayList<>();
                    mRectifyEditContent = new EditContentEntity("摘要", "", data.getSummary());
                    mPunishItemList.add(mRectifyEditContent);
                    mDescribeEditContent = new EditContentEntity("详细情况", "", data.getContent());
                    mPunishItemList.add(mDescribeEditContent);
                    TransformationPunishModel transModel = new TransformationPunishModel(data);
                    mPunishItemList.add(transModel.fetchPunishUserItem());
                    mPunishItemList.add(transModel.fetchPunishLeaderItem());
                    mPunishItemList.add(transModel.fetchReadList());
                    return data;
                })
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(Network.networkIO())
                .subscribe(data -> {
                    isInit = true;
                    mView.setItemList(mPunishItemList);
                    mView.setHeadTitle(data);
                    mView.showStatusView(data.edit, data.consentStatius, data.sendBackStatius, data.signStatius, data.cancelStatus);
                    mView.showPrint(data.printStatus);
                    mView.initSuccess();
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.initFail();
                }));
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
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    getDetailInfo(mPunishId);
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
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    mView.showToast(R.string.punish_change_success);
                    mView.sendBackSuccess();
                    getDetailInfo(mPunishId);
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
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    mView.showToast(R.string.punish_change_success);
                    getDetailInfo(mPunishId);
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
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    mView.showToast(R.string.punish_change_success);
                    getDetailInfo(mPunishId);
                }, throwable -> {
                }));

    }


}
