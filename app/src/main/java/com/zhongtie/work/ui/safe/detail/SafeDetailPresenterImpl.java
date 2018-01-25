package com.zhongtie.work.ui.safe.detail;


import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Flowable;

/**
 * 安全督导详情
 * Date: 2018/1/12
 *
 * @author Chaek
 */

public class SafeDetailPresenterImpl extends BasePresenterImpl<SafeDetailContract.View> implements SafeDetailContract.Presenter {

    /**
     * 是否初始化
     */
    private boolean isInit;
    private List<Object> mAdapterItemList = new ArrayList<>();
    private SafeEventEntity eventEntity;
    /**
     * 是否隐藏显示check user
     */
    private boolean isShowCheck;
    /**
     * 是否显示隐藏Hide 类别其它
     */
    private boolean isHideNullItem;

    /**
     * 改变检查的隐藏数据的英寸状态
     */
    @Override
    public void changeCheckListState() {
        if (isShowCheck) {
            //隐藏也就是移除所有的检查人信息
            Iterator<Object> iterator = mAdapterItemList.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof EndorseUserEntity) {
                    iterator.remove();
                }
            }
            isShowCheck = false;
            mView.setCheckCount(0);
        } else {
            //显示隐藏人信息
            SafeEventModel safeEventModel = new SafeEventModel(eventEntity);
            CommonItemType checkUser = safeEventModel.fetchCheckUserList();
            mView.setCheckCount(checkUser.getTypeItemList().size());
            mAdapterItemList.addAll(isHideNullItem ? 1 : 2, checkUser.getTypeItemList());
            isShowCheck = true;
            mView.setCheckCount(checkUser.getTypeItemList().size());
        }
    }

    @Override
    public void getEventDetailItemList(int safeOrderID) {
        if (!isInit) {
            mView.initLoading();
        }
        addDispose(Http.netServer(SafeApi.class)
                .eventDetails(Cache.getUserID(), safeOrderID)
                .map(new NetWorkFunc1<>())
                .compose(Network.networkIO())
                .subscribe(safeEventEntity -> {
                    this.eventEntity = safeEventEntity;
                    mView.setEventStatus(safeEventEntity.butstate);
                    setTitleUserInfo(safeEventEntity);
                    initItemList(safeEventEntity);
                    isInit = true;
                }, throwable -> {
                    throwable.printStackTrace();
                    String msg = throwable.getMessage();
                    if ("您无权查看".equals(msg)) {
                        mView.noLookAuthority();
                    } else {
                        mView.initFail();
                    }
                }));
    }

    @Override
    public void checkUserSign(String imagePath) {
        addDispose(UploadUtil.uploadSignPNG(imagePath)
                .flatMap(uploadData -> Http.netServer(SafeApi.class).eventSign(Cache.getUserID(), mView.fetchEventId(), uploadData.getPicname()))
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> getEventDetailItemList(mView.fetchEventId()), throwable -> {
                }));
    }

    @Override
    public void validateEvent(String imagePath) {
        addDispose(UploadUtil.uploadSignPNG(imagePath)
                .flatMap(uploadData -> Http.netServer(SafeApi.class).validateEvent(Cache.getUserID(), mView.fetchEventId(), uploadData.getPicname()))
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    //刷新界面
                    mView.showToast(R.string.validate_success);
                    getEventDetailItemList(mView.fetchEventId());
                }, throwable -> {
                }));
    }


    private void initItemList(SafeEventEntity safeEventEntity) {
        addDispose(Flowable.fromCallable(() -> {
            //初始化
            mAdapterItemList = new ArrayList<>();
            isHideNullItem = true;

            //未填写情况 劳务公司整改内容等等
            if (!TextUtil.isEmpty(safeEventEntity.event_changemust)) {
                EditContentEntity mRectifyEditContent = new EditContentEntity("整改要求", "", safeEventEntity.event_changemust);
                mAdapterItemList.add(mRectifyEditContent);
                isHideNullItem = false;
            }

            SafeEventModel baseSafeModel = new SafeEventModel(safeEventEntity);

            //整改内容
            CommonItemType checkUserItem = baseSafeModel.fetchCheckUserList();
            mAdapterItemList.add(checkUserItem);
            //检查人的由主list 展示 避免过多情况引起性能问题
            if (isShowCheck) {
                mView.setCheckCount(checkUserItem.getTypeItemList().size());
                mAdapterItemList.addAll(checkUserItem.getTypeItemList());
            } else {
                mView.setCheckCount(0);
            }
            checkUserItem.setTypeItemList(null);

            //添加验证认
            mAdapterItemList.add(baseSafeModel.getModifyReviewList());

            //整改认为空的话不添加 也就是Hide 整改认一行
            CommonItemType relate = baseSafeModel.getRelatedList();
            if (!relate.getTypeItemList().isEmpty()) {
                mAdapterItemList.add(relate);
            }
            mAdapterItemList.add(baseSafeModel.fetchReadList());
            String replyTitle = "已回复(" + safeEventEntity.replylist.size() + ")";
            mAdapterItemList.add(replyTitle);
            mAdapterItemList.addAll(safeEventEntity.replylist);
            List<ApproveEntity> approveEntities = baseSafeModel.getDetailReviewUserList();
            String approveTitle = "已验证(" + approveEntities.size() + ")";
            mAdapterItemList.add(approveTitle);
            mAdapterItemList.addAll(approveEntities);

            return isHideNullItem;
        })
                .compose(Network.networkIO())
                .subscribe(isHideNullItem -> {
                    mView.initSuccess();
                    mView.setItemList(mAdapterItemList, isHideNullItem);
                }, throwable -> mView.initFail()));
    }


    private void setTitleUserInfo(SafeEventEntity titleUserInfo) {
        mView.setSafeDetailInfo(titleUserInfo);
    }


}
